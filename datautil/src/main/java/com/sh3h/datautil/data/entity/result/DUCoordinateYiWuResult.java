package com.sh3h.datautil.data.entity.result;



public class DUCoordinateYiWuResult {
    private boolean success;
    private String message;
    private double B1;
    private double L1;
    private double x1;
    private double y1;

    public DUCoordinateYiWuResult(boolean success, String message, double b1, double l1,double x1,double y1) {
        this.success = success;
        this.message = message;
        B1 = b1;
        L1 = l1;
        this.x1 = x1;
        this.y1 = y1;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public double getB1() {
        return B1;
    }

    public void setB1(double b1) {
        B1 = b1;
    }

    public double getL1() {
        return L1;
    }

    public void setL1(double l1) {
        L1 = l1;
    }

    public double getX1() {
        return x1;
    }

    public void setX1(double x1) {
        this.x1 = x1;
    }

    public double getY1() {
        return y1;
    }

    public void setY1(double y1) {
        this.y1 = y1;
    }
}
