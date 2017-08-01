package com.handarui.androidbase.api;

import com.handarui.androidbase.bean.AppVersionBean;
import com.zhexinit.ov.common.bean.ResponseBean;

import retrofit2.http.GET;
import rx.Observable;

/**
 * Created by xubo on 2017/6/20.
 */
public interface HomeApiService {

    // TODO: 2017/6/20 需要删除
    @GET("app_update")
    Observable<ResponseBean<AppVersionBean>> update_app();

}
