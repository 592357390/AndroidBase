package com.handarui.baselib;

/**
 * Created by xubo on 2017/6/20.
 */

public class ResponseBean<T> {

    public T result;
    public String message;
    public int code;

    public ResponseBean() {
    }

    public String toString() {
        return this.code == 0 ? (this.message == null ? (this.result != null ? this.result.toString() : null) : (this.result != null ? this.message + ": " + this.result.toString() : this.message)) : this.code + " on " + this.message;
    }
}
