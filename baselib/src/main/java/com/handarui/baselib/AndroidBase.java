package com.handarui.baselib;

import android.content.Context;
import android.text.TextUtils;

import com.handarui.baselib.net.OkHttpClientManager;
import com.handarui.baselib.net.RetrofitFactory;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;

/**
 * Created by xubo on 2017/6/22.
 */

public class AndroidBase {

    private static Context context;

    private static boolean DEBUG;

    /**
     * 用于TOKEN加密的AES密钥
     */
    private static String AES_KEY_ENCRYPT_TOKEN;

    private static String API_BASE_PATH;

    private static String API_HTTPS_BASE_PATH;

    private static String CER_FILENAME;

    private static String DEVICE_INFO;

    private static boolean AUTO_HTTPS = true;

    /**
     * 登录url
     */
    private static String LOGIN_URL;

    /**
     * 注销url
     */
    private static String LOGOUT_URL;

    public AndroidBase(Context context, String httpPath, String httpsPath, boolean debug, String aesKey,
                       String cerFileName, String deviceInfo, String loginUrl, String logoutUrl, boolean autoHttps) {
        AndroidBase.context = context;
        AndroidBase.API_BASE_PATH = httpPath;
        AndroidBase.API_HTTPS_BASE_PATH = httpsPath;
        AndroidBase.DEBUG = debug;
        AndroidBase.AES_KEY_ENCRYPT_TOKEN = aesKey;
        AndroidBase.CER_FILENAME = cerFileName;
        AndroidBase.DEVICE_INFO = deviceInfo;
        AndroidBase.LOGIN_URL = loginUrl;
        AndroidBase.LOGOUT_URL = logoutUrl;
        AndroidBase.AUTO_HTTPS = autoHttps;
    }

    public static Context getContext() {
        return context;
    }

    public static boolean isDEBUG() {
        return DEBUG;
    }

    public static String getAesKeyEncryptToken() {
        return AES_KEY_ENCRYPT_TOKEN;
    }

    public static String getApiBasePath() {
        return API_BASE_PATH;
    }

    public static String getApiHttpsBasePath() {
        return API_HTTPS_BASE_PATH;
    }

    public static String getCerFilename() {
        return CER_FILENAME;
    }

    public static String getDeviceInfo() {
        return DEVICE_INFO;
    }

    public static String getLoginUrl() {
        return LOGIN_URL;
    }

    public static String getLogoutUrl() {
        return LOGOUT_URL;
    }

    public static boolean isAutoHttps() {
        return AUTO_HTTPS;
    }

    public void init() {
        Logger.addLogAdapter(new AndroidLogAdapter() {
            @Override
            public boolean isLoggable(int priority, String tag) {
                return DEBUG;
            }
        });
        String checkResult = checkStatus();
        if (!TextUtils.isEmpty(checkResult)) {
            Logger.e(checkResult);
        }
        OkHttpClientManager.init(context);
        RetrofitFactory.init(context);
    }

    private String checkStatus() {
        String result = "";
        if (context == null) {
            result = "context is null";
        } else if (TextUtils.isEmpty(API_BASE_PATH)) {
            result = "http path is null";
        } else if (TextUtils.isEmpty(API_HTTPS_BASE_PATH)) {
            if (!AUTO_HTTPS) {
                result = "https path is null";
            }
        }
        return result;
    }

    public static class AndroidBaseBuilder {

        private Context context;

        private boolean DEBUG = false;

        private String AES_KEY_ENCRYPT_TOKEN;

        private String API_BASE_PATH;

        private String API_HTTPS_BASE_PATH;

        private String CER_FILENAME;

        private String DEVICE_INFO;

        private String LOGIN_URL = "login";

        private String LOGOUT_URL = "logout";

        private boolean AUTO_HTTPS = true;

        public AndroidBaseBuilder(Context context, String httpPath) {
            this.context = context;
            this.API_BASE_PATH = httpPath;
        }

        public AndroidBaseBuilder(Context context, String httpPath, String httpsPath) {
            this.context = context;
            this.API_BASE_PATH = httpPath;
            this.API_HTTPS_BASE_PATH = httpsPath;
        }

        public AndroidBaseBuilder setContext(Context context) {
            this.context = context;
            return this;
        }

        public AndroidBaseBuilder setDebug(boolean debug) {
            this.DEBUG = debug;
            return this;
        }

        public AndroidBaseBuilder setAESToken(String aesToken) {
            this.AES_KEY_ENCRYPT_TOKEN = aesToken;
            return this;
        }

        public AndroidBaseBuilder setHTTPPath(String httpPath) {
            this.API_BASE_PATH = httpPath;
            return this;
        }

        public AndroidBaseBuilder setHTTPSPath(String httpsPath) {
            this.API_HTTPS_BASE_PATH = httpsPath;
            return this;
        }

        public AndroidBaseBuilder setCerFileName(String fileName) {
            this.CER_FILENAME = fileName;
            return this;
        }

        public AndroidBaseBuilder setDeviceInfo(String deviceInfo) {
            this.DEVICE_INFO = deviceInfo;
            return this;
        }

        public AndroidBaseBuilder setLoginUrl(String loginUrl) {
            this.LOGIN_URL = loginUrl;
            return this;
        }

        public AndroidBaseBuilder setLogoutUrl(String logoutUrl) {
            this.LOGOUT_URL = logoutUrl;
            return this;
        }

        public AndroidBaseBuilder setAutoHttps(boolean AUTO_HTTPS) {
            this.AUTO_HTTPS = AUTO_HTTPS;
            return this;
        }

        public AndroidBase createAndroidBase() {
            return new AndroidBase(context, API_BASE_PATH, API_HTTPS_BASE_PATH, DEBUG,
                    AES_KEY_ENCRYPT_TOKEN, CER_FILENAME, DEVICE_INFO, LOGIN_URL, LOGOUT_URL, AUTO_HTTPS);
        }

    }
}
