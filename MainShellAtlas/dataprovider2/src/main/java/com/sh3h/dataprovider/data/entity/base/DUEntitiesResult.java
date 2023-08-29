package com.sh3h.dataprovider.data.entity.base;

import java.util.List;

/**
 * Created by dengzhimin on 2016/6/1.
 */
public class DUEntitiesResult<T> {
    public static final int SUCCESS_CODE = 0;
    private int Code;
    private int StatusCode;
    private String Message;
    private List<T> Data;

    public DUEntitiesResult(int Code, int StatusCode, String Message, List<T> Data){
        this.Code = Code;
        this.StatusCode = StatusCode;
        this.Message = Message;
        this.Data = Data;
    }

    public int getCode() {
        return Code;
    }

    public void setCode(int code) {
        Code = code;
    }

    public int getStatusCode() {
        return StatusCode;
    }

    public void setStatusCode(int statusCode) {
        StatusCode = statusCode;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public List<T> getData() {
        return Data;
    }

    public void setData(List<T> data) {
        Data = data;
    }
}
