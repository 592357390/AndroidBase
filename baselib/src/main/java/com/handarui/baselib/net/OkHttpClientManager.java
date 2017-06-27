package com.handarui.baselib.net;

import android.content.Context;

import com.handarui.baselib.AndroidBase;
import com.orhanobut.logger.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.SecureRandom;
import java.security.cert.CertificateFactory;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;

import okhttp3.Cache;
import okhttp3.OkHttpClient;

/**
 * Created by guofe on 2016/1/19.
 */
public class OkHttpClientManager {
    /**
     * http
     */
    private static OkHttpClient okHttpClient;
    /**
     * https
     */
    private static OkHttpClient sslOkHttpClient;

    public static void init(Context context) {

        // 初始化http
        int cacheSize = 5 * 1024 * 1024;
        Cache cache = new Cache(context.getCacheDir(), cacheSize);
        OkHttpClient.Builder okHttpClientBuilder = new OkHttpClient.Builder()
                .cache(cache)
                .addInterceptor(new RequestInterceptor(context))
                .retryOnConnectionFailure(true)
//                .retryOnConnectionFailure(false) //请求失败不重试
                .readTimeout(30, TimeUnit.SECONDS);

        okHttpClient = okHttpClientBuilder.build();

        try {
            InputStream is = context.getAssets().open(AndroidBase.getCerFilename());
            setCertificates(is);
        } catch (IOException e) {
            Logger.e(e.getMessage());
        }
    }

    public static OkHttpClient getOkHttpClient() {
        return okHttpClient;
    }

    public static OkHttpClient getSslOkHttpClient() {
        return sslOkHttpClient;
    }

    /**
     * 获取长超时配置的OkHttpClien克隆体
     *
     * @return
     */
    public static OkHttpClient getLongTimeOutOkHttpClientClone() {
        OkHttpClient okHttpClient = getOkHttpClient().newBuilder().build();
        return okHttpClient;
    }

    /**
     * 获取长超时配置的https OkHttpClien克隆体
     *
     * @return
     */
    public static OkHttpClient getLongTimeOutHttpsOkHttpClientClone() {
        OkHttpClient okHttpClient = getSslOkHttpClient().newBuilder().build();
        return okHttpClient;
    }

    private static void setCertificates(InputStream... certificates) {
        try {
            CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
            KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
            keyStore.load(null);
            int index = 0;
            for (InputStream certificate : certificates) {
                String certificateAlias = Integer.toString(index++);
                keyStore.setCertificateEntry(certificateAlias, certificateFactory.generateCertificate(certificate));

                try {
                    if (certificate != null)
                        certificate.close();
                } catch (IOException e) {
                    Logger.t(OkHttpClientManager.class.getSimpleName()).e(e.toString());
                }
            }

            SSLContext sslContext = SSLContext.getInstance("TLS");

            TrustManagerFactory trustManagerFactory =
                    TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());

            trustManagerFactory.init(keyStore);
            sslContext.init(null, trustManagerFactory.getTrustManagers(), new SecureRandom());

            OkHttpClient.Builder builder = okHttpClient.newBuilder().sslSocketFactory(sslContext.getSocketFactory());
            // 自签证书需要添加
            builder.hostnameVerifier(new NullHostNameVerifier());
            sslOkHttpClient = builder.build();

        } catch (Exception e) {
            Logger.t(OkHttpClientManager.class.getSimpleName()).e(e.toString());
        }

    }
}
