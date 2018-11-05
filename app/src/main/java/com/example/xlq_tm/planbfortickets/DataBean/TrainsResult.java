package com.example.xlq_tm.planbfortickets.DataBean;

import java.io.Serializable;

public class TrainsResult<T> implements Serializable {

    private T data;
    private int httpStatus;
    private String messages;
    private boolean status;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public int getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(int httpstatus) {
        this.httpStatus = httpstatus;
    }

    public String getMessages() {
        return messages;
    }

    public void setMessages(String messages) {
        this.messages = messages;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

}
