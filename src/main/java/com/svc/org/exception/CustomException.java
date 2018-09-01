package com.svc.org.exception;

public class CustomException extends Exception {
    private String msg;

    public CustomException(String message) {
        super(message);
        this.msg = message;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
