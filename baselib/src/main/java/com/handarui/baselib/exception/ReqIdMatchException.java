package com.handarui.baselib.exception;

/**
 * Created by xubo on 2017/9/8.
 */

public class ReqIdMatchException extends Exception {

    public ReqIdMatchException() {
        super();
    }

    public ReqIdMatchException(final String message) {
        super(message);
    }

    public ReqIdMatchException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public ReqIdMatchException(final Throwable cause) {
        super(cause);
    }

}
