package com.handarui.baselib.net;

import android.content.Context;
import android.text.TextUtils;

import com.handarui.baselib.AndroidBase;
import com.handarui.baselib.common.Constants;
import com.orhanobut.logger.Logger;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * api请求的拦截器
 * Created by guofe on 2015/12/25.
 */
public class RequestInterceptor implements Interceptor {

    private final Context context;

    public RequestInterceptor(Context context) {
        this.context = context;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request.Builder builder = chain.request().newBuilder();

        /** 设备信息*/
        if (!TextUtils.isEmpty(AndroidBase.getDeviceInfo())) {
            builder.header(Constants.HEADER_DEVIECE_INFO, AndroidBase.getDeviceInfo());
        }

        if (TokenManager.getToken(context) != null) {
            builder.header(Constants.HEADER_TOKEN_NAME, TokenManager.getToken(context));
        }
        Request request = builder.build();

        long t1 = System.nanoTime();
        Logger.t("API").i(String.format("Sending request %s on %s%n%s",
                request.url(), chain.connection(), request.headers()));

        Response response = chain.proceed(request);

        if (response.isSuccessful()) {
            // 检查是否是登录请求
            checkLogin(response);
            checkLogout(response);

            String requestUrl = response.request().method() + " " + response.request().url().toString();
            int responseCode = response.code();
            String message = responseCode + " on " + requestUrl;
            Logger.t("API").i(message);
        }

        long t2 = System.nanoTime();
        Logger.t("API").i(String.format("Received response for %s in %.1fms%n%s",
                response.request().url(), (t2 - t1) / 1e6d, response.headers()));

        return response;
    }

    /**
     * 检查是否是登录请求,并存储token
     *
     * @param response
     */
    private void checkLogin(Response response) {
        String token = null;
        String path = response.request().url().uri().getPath();
        if (path.indexOf(AndroidBase.getLoginUrl()) >= 0) {
            token = response.header(Constants.HEADER_TOKEN_NAME, null);
            if (token != null) {
                TokenManager.setToken(context, token);
            }
        }
        Logger.i("checkLogin", "token " + token);
    }

    /**
     * 检查是否是注销请求，并清除TOKEN
     *
     * @param response
     */
    private void checkLogout(Response response) {
        if (response.request().url().uri().getPath().indexOf(AndroidBase.getLogoutUrl()) >= 0) {
            Logger.i("checkLogout", "removeToken");
            TokenManager.removeToken(context);
        }
    }
}

