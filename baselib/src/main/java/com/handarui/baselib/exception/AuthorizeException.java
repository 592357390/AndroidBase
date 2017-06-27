package com.handarui.baselib.exception;

/**
 * 身份验证失效异常
 * Created by xubo on 15/11/27.
 */
public class AuthorizeException extends Exception {

    public AuthorizeException() {
        super();
    }

    public AuthorizeException(final String message) {
        super(message);
    }

    public AuthorizeException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public AuthorizeException(final Throwable cause) {
        super(cause);
    }
}