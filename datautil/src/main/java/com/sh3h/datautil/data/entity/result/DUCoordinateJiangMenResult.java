package com.sh3h.datautil.data.entity.result;

/**
 * Created by TSJ259 on 2018/1/22.
 */

public class DUCoordinateJiangMenResult {
    private boolean success;
    private String coor;

    public DUCoordinateJiangMenResult(boolean success, String coor) {
        this.success = success;
        this.coor = coor;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getCoor() {
        return coor;
    }

    public void setCoor(String coor) {
        this.coor = coor;
    }
}
