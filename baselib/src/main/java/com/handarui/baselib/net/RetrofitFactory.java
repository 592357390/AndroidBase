package com.handarui.baselib.net;

import android.content.Context;

import com.handarui.baselib.AndroidBase;
import com.squareup.moshi.Moshi;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.moshi.MoshiConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Retrofit service工厂类
 * Created by guofe on 2015/9/11.
 */
public class RetrofitFactory {

    /**
     * 用来生成Restful Service的Retrofit单例
     */
    private static Retrofit retrofitInstance;

    /**
     * https的Retrofit单例
     */
    private static Retrofit httpsRetrofitInstance;

    public static void init(Context context) {
        // 初始化Retrofit
        Moshi moshi = MoshiFactor.create();

        retrofitInstance = new Retrofit.Builder()
                .baseUrl(AndroidBase.getApiBasePath())
                .client(OkHttpClientManager.getOkHttpClient())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(MoshiConverterFactory.create(moshi))
                .build();

        httpsRetrofitInstance = new Retrofit.Builder()
                .baseUrl(AndroidBase.getApiHttpsBasePath())
                .client(OkHttpClientManager.getSslOkHttpClient())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(MoshiConverterFactory.create(moshi))
                .build();
    }

    /**
     * 获取http AppService的单例
     *
     * @return
     */
    public static <T> T createRestService(Class<T> service) {
        return retrofitInstance.create(service);
    }

    /**
     * 获取https AppService的单例
     *
     * @return
     */
    public static <T> T createHttpsRestService(Class<T> service) {
        return httpsRetrofitInstance.create(service);
    }
}
