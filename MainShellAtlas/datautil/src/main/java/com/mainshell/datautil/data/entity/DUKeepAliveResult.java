package com.mainshell.datautil.data.entity;

/**
 * Created by xulongjun on 2016/3/15.
 */
@Deprecated
public class DUKeepAliveResult extends DUResponse {
    private long time;

    public DUKeepAliveResult() {
    }

    public DUKeepAliveResult(long time) {
        this.time = time;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }
}
