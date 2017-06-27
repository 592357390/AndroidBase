package com.handarui.baselib.exception;

/**
 * 网络连接异常
 * Created by xubo on 2017/6/20.
 */

public class DisconnectException extends Exception {

    public DisconnectException() {
        super();
    }

    public DisconnectException(final String message) {
        super(message);
    }

    public DisconnectException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public DisconnectException(final Throwable cause) {
        super(cause);
    }

}
