package com.sh3h.datautil.exception;


import android.text.TextUtils;

import com.sh3h.mobileutil.util.TextUtil;

public enum DUException {
    PARAM_NULL(1, "param is null"),
    PARAM_ERROR(2, "param is error"),
    RETURN_NULL(3, "return is null"),
    DB_ERROR(4, "db is error"),
    LOGIN_FAILURE(5, "failure to login"),
    HTTP_ERROR(6, "http is error");

    private int id;
    private String name;

    DUException(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public static DUException toDUException(String name) {
        if (TextUtil.isNullOrEmpty(name)) {
            return null;
        }

        switch (name) {
            case "param is null":
                return PARAM_NULL;
            case "param is error":
                return PARAM_ERROR;
            case "return is null":
                return RETURN_NULL;
            case "db is error":
                return DB_ERROR;
            case "failure to login":
                return LOGIN_FAILURE;
            case "http is error":
                return HTTP_ERROR;
            default:
                return null;
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
