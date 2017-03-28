package com.necer.carexpend.bean;

/**
 * Created by necer on 2017/3/7.
 */

public class HttpResult<T> {

    private int status;
    private String msg;
    private T result;


    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }
}
