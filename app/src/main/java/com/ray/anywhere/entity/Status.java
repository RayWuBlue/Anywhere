package com.ray.anywhere.entity;

/**
 * Created by Ray on 2016/5/12.
 */
public class Status {
    private int retCode;
    private String message;
    public int getRetCode() {
        return retCode;
    }

    public void setRetCode(int retCode) {
        this.retCode = retCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSuccuss(){
        return retCode==200;
    }

    @Override
    public String toString() {
        return "Status{" +
                "retCode=" + retCode +
                ", message='" + message + '\'' +
                '}';
    }
}
