package com.svc.org.bean;

public class ResponseEntity<T> {
    private int status;
    private T data;
    private String msg;

    public ResponseEntity() {
    }

    public ResponseEntity(int status, T data, String msg) {
        this.status = status;
        this.data = data;
        this.msg = msg;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

}
