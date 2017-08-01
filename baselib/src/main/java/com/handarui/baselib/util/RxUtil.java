package com.handarui.baselib.util;

import com.handarui.baselib.AndroidBase;
import com.handarui.baselib.exception.AuthorizeException;
import com.handarui.baselib.exception.CallCanceledException;
import com.handarui.baselib.exception.CommonException;
import com.handarui.baselib.exception.DisconnectException;
import com.orhanobut.logger.Logger;
import com.zhexinit.ov.common.bean.ResponseBean;

import java.io.IOException;
import java.util.Collection;

import rx.android.schedulers.AndroidSchedulers;
import retrofit2.HttpException;
import rx.Observable;
import rx.Single;
import rx.Subscription;
import rx.functions.Func0;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * RxJava相关工具类
 * Created by guofe on 2016/1/22.
 */
public class RxUtil {

    /**
     * 剥离ResponseBean
     *
     * @param call
     * @param <T>
     * @return
     */
    public static <T> Single<T> wrapRestCall(final Observable<ResponseBean<T>> call) {
        return call.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(new Func1<ResponseBean<T>, Observable<T>>() {
                    @Override
                    public Observable<T> call(final ResponseBean<T> response) {
                        if (response.getCode() == 0) {
                            return Observable.just(response.getResult());
                        } else {
                            return Observable.error(new CommonException(response.getCode(), response.getMessage()));
                        }
                    }
                }, new Func1<Throwable, Observable<? extends T>>() {
                    @Override
                    public Observable<? extends T> call(final Throwable throwable) {
                        Logger.e("API ERROR", throwable.toString());
                        if (throwable instanceof CallCanceledException) {
                            return Observable.never();
                        } else if (throwable instanceof HttpException) {
                            HttpException httpException = (HttpException) throwable;
                            if (httpException.code() == 403 || httpException.code() == 401) {
                                return Observable.error(new AuthorizeException());
                            }
                        } else if (throwable instanceof IOException) {
                            if (!NetWorkUtils.isNetWorkUseful(AndroidBase.getContext())) {
                                return Observable.error(new DisconnectException());
                            } else {
                                return Observable.error(throwable);
                            }
                        }
                        return Observable.error(throwable);
                    }
                }, new Func0<Observable<? extends T>>() {

                    @Override
                    public Observable<? extends T> call() {
                        return Observable.empty();
                    }
                }).toSingle();
    }

    /**
     * 取消订阅
     *
     * @param subscriptions
     */
    public static void unsubscribeIfNotNull(Subscription... subscriptions) {
        for (Subscription subscription : subscriptions) {
            if (subscription != null) {
                subscription.unsubscribe();
            }
        }
    }

    /**
     * 取消订阅
     *
     * @param subscriptions
     */
    public static void unsubscribeIfNotNull(Collection<Subscription> subscriptions) {
        for (Subscription subscription : subscriptions) {
            if (subscription != null) {
                subscription.unsubscribe();
            }
        }
    }
}
