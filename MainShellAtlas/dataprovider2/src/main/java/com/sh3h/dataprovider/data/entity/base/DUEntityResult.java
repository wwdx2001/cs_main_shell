package com.sh3h.dataprovider.data.entity.base;


public class DUEntityResult<T> {
    public static final int SUCCESS_CODE = 0;
    public static final int FAIL_CODE = -1;

    private int Code;
    private int StatusCode;
    private String Message;
    private T Data;

    public DUEntityResult(int Code, int StatusCode, String Message, T Data) {
        this.Code = Code;
        this.StatusCode = StatusCode;
        this.Message = Message;
        this.Data = Data;
    }

    public int getCode() {
        return Code;
    }

    public void setCode(int Code) {
        this.Code = Code;
    }

    public int getStatusCode() {
        return StatusCode;
    }

    public void setStatusCode(int StatusCode) {
        this.StatusCode = StatusCode;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String Message) {
        this.Message = Message;
    }

    public T getData() {
        return Data;
    }

    public void setData(T Data) {
        this.Data = Data;
    }
}
