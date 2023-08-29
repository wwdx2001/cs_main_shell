package com.sh3h.dataprovider.data.entity;

/**
 * 返回值基类
 * Created by limeng on 2016/9/1.
 */
public class DUBaseResult<T> {
    private int responseCode;
    private String responseError;
    private T entity;

    public DUBaseResult(T entity) {
        this.entity = entity;
    }

    public DUBaseResult() {

    }


    public T getEntity() {
        return entity;
    }

    public void setEntity(T entity) {
        this.entity = entity;
    }

    public int getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(int responseCode) {
        this.responseCode = responseCode;
    }

    public String getResponseError() {
        return responseError;
    }

    public void setResponseError(String responseError) {
        this.responseError = responseError;
    }
}
