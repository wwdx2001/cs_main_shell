package com.mainshell.datautil.data.entity;


public abstract class DURequest implements IDURequest {
    protected IDUHandler duHandler;

    public DURequest() {
        duHandler = null;
    }

    public IDUHandler getDuHandler() {
        return duHandler;
    }

    public void setDuHandler(IDUHandler duHandler) {
        this.duHandler = duHandler;
    }
}
