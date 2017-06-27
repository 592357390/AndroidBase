package com.handarui.androidbase;

import com.handarui.baselib.exception.AuthorizeException;
import com.handarui.baselib.exception.CommonException;
import com.handarui.baselib.exception.DisconnectException;
import com.orhanobut.logger.Logger;

import rx.functions.Action1;

/**
 * Created by xubo on 2017/6/27.
 */

public class DefaultErrorHandler implements Action1<Throwable> {
    @Override
    public void call(Throwable throwable) {
        Logger.t("API_ERROR").e("request error" + throwable.toString());
        if (throwable instanceof AuthorizeException) {
            //TODO: 提示登录
        } else if (throwable instanceof DisconnectException) {
            // TODO: 2017/6/27 未联网提示
        } else if (throwable instanceof CommonException) {
            CommonException ce = (CommonException) throwable;
            switch (ce.getCode()) {
                case 9999:
                    //TODO: 强制升级
                    break;
            }
        }
        handleErrorIfNeed(throwable);
    }

    public void handleErrorIfNeed(Throwable throwable) {
    }

}
