package com.handarui.baselib.exception;

/**
 * Created by konie on 16-8-20.
 */
public class CommonException extends Exception {

    private int code;

    public CommonException(int code, String message) {
        super(message);
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    @Override
    public String toString() {
        return "CommonException code: " + code + " " + super.toString();
    }
}