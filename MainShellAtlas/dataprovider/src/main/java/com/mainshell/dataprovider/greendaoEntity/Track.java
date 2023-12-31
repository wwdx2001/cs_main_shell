package com.mainshell.dataprovider.greendaoEntity;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT. Enable "keep" sections if you want to edit. 

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Entity mapped to table MS_Track.
 */
@Entity(nameInDb = "MS_TRACK")
public class Track {

    @Id(autoincrement = true)
    @Property(nameInDb = "ID")
    private Long ID;

    @NotNull
    private String S_USERID;

    @NotNull
    private String S_DEVICEID;

    @NotNull
    private String S_LOCATIONTYPE;

    @NotNull
    private double D_LONGITUDE;

    @NotNull
    private double D_LATITUDE;

    private float F_RADIUS;

    private double D_ALTITUDE;

    private float F_DIRECTION;

    private float F_SPEED;

    @NotNull
    private long L_TIME;

    @NotNull
    private int I_SHANGCHUANGBZ;

    private String S_EXTEND;

    @Generated(hash = 1672115350)
    public Track(Long ID, @NotNull String S_USERID, @NotNull String S_DEVICEID,
            @NotNull String S_LOCATIONTYPE, double D_LONGITUDE, double D_LATITUDE, float F_RADIUS,
            double D_ALTITUDE, float F_DIRECTION, float F_SPEED, long L_TIME, int I_SHANGCHUANGBZ,
            String S_EXTEND) {
        this.ID = ID;
        this.S_USERID = S_USERID;
        this.S_DEVICEID = S_DEVICEID;
        this.S_LOCATIONTYPE = S_LOCATIONTYPE;
        this.D_LONGITUDE = D_LONGITUDE;
        this.D_LATITUDE = D_LATITUDE;
        this.F_RADIUS = F_RADIUS;
        this.D_ALTITUDE = D_ALTITUDE;
        this.F_DIRECTION = F_DIRECTION;
        this.F_SPEED = F_SPEED;
        this.L_TIME = L_TIME;
        this.I_SHANGCHUANGBZ = I_SHANGCHUANGBZ;
        this.S_EXTEND = S_EXTEND;
    }

    @Generated(hash = 1672506944)
    public Track() {
    }

    public Long getID() {
        return this.ID;
    }

    public void setID(Long ID) {
        this.ID = ID;
    }

    public String getS_USERID() {
        return this.S_USERID;
    }

    public void setS_USERID(String S_USERID) {
        this.S_USERID = S_USERID;
    }

    public String getS_DEVICEID() {
        return this.S_DEVICEID;
    }

    public void setS_DEVICEID(String S_DEVICEID) {
        this.S_DEVICEID = S_DEVICEID;
    }

    public String getS_LOCATIONTYPE() {
        return this.S_LOCATIONTYPE;
    }

    public void setS_LOCATIONTYPE(String S_LOCATIONTYPE) {
        this.S_LOCATIONTYPE = S_LOCATIONTYPE;
    }

    public double getD_LONGITUDE() {
        return this.D_LONGITUDE;
    }

    public void setD_LONGITUDE(double D_LONGITUDE) {
        this.D_LONGITUDE = D_LONGITUDE;
    }

    public double getD_LATITUDE() {
        return this.D_LATITUDE;
    }

    public void setD_LATITUDE(double D_LATITUDE) {
        this.D_LATITUDE = D_LATITUDE;
    }

    public float getF_RADIUS() {
        return this.F_RADIUS;
    }

    public void setF_RADIUS(float F_RADIUS) {
        this.F_RADIUS = F_RADIUS;
    }

    public double getD_ALTITUDE() {
        return this.D_ALTITUDE;
    }

    public void setD_ALTITUDE(double D_ALTITUDE) {
        this.D_ALTITUDE = D_ALTITUDE;
    }

    public float getF_DIRECTION() {
        return this.F_DIRECTION;
    }

    public void setF_DIRECTION(float F_DIRECTION) {
        this.F_DIRECTION = F_DIRECTION;
    }

    public float getF_SPEED() {
        return this.F_SPEED;
    }

    public void setF_SPEED(float F_SPEED) {
        this.F_SPEED = F_SPEED;
    }

    public long getL_TIME() {
        return this.L_TIME;
    }

    public void setL_TIME(long L_TIME) {
        this.L_TIME = L_TIME;
    }

    public int getI_SHANGCHUANGBZ() {
        return this.I_SHANGCHUANGBZ;
    }

    public void setI_SHANGCHUANGBZ(int I_SHANGCHUANGBZ) {
        this.I_SHANGCHUANGBZ = I_SHANGCHUANGBZ;
    }

    public String getS_EXTEND() {
        return this.S_EXTEND;
    }

    public void setS_EXTEND(String S_EXTEND) {
        this.S_EXTEND = S_EXTEND;
    }





}
