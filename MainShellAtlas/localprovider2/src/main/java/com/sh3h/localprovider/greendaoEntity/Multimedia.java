package com.sh3h.localprovider.greendaoEntity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;

/**
 * 多媒体信息
 */
@Entity(nameInDb = "MR_MULTIMEDIA")
public class Multimedia {

    @Id(autoincrement = true)
    @Property(nameInDb = "ID")
    private Long ID;            //ID
    @NotNull
    private int  FILE_TYPE;     //文件类型
    @NotNull
    private String FILE_NAME;   //文件名称

    private String FILE_HASH;   //文件HASH

    private String FILE_URL;    //文件URL
    @NotNull
    private int  UPLOAD_FLAG;   //上传标志
    @NotNull
    private Long FILE_TIME;     //文件时间
    @NotNull
    private int  METER_READER;   //抄表员
    @NotNull
    private int  TASK_ID;       //抄表任务编号
    @NotNull
    private String BOOK_ID;       //册本编号
    @NotNull
    private int  RECORD_ID;       //抄表记录编号
    @NotNull
    private String 	CARD_ID;      //表卡编号
    private String 	LOCATION_LG;  //经度
    private String 	LOCATION_LT;  //纬度
    private String EXTEND;      //扩展信息

    @Generated(hash = 385787953)
    public Multimedia(Long ID, int FILE_TYPE, @NotNull String FILE_NAME,
            String FILE_HASH, String FILE_URL, int UPLOAD_FLAG,
            @NotNull Long FILE_TIME, int METER_READER, int TASK_ID,
            @NotNull String BOOK_ID, int RECORD_ID, @NotNull String CARD_ID,
            String LOCATION_LG, String LOCATION_LT, String EXTEND) {
        this.ID = ID;
        this.FILE_TYPE = FILE_TYPE;
        this.FILE_NAME = FILE_NAME;
        this.FILE_HASH = FILE_HASH;
        this.FILE_URL = FILE_URL;
        this.UPLOAD_FLAG = UPLOAD_FLAG;
        this.FILE_TIME = FILE_TIME;
        this.METER_READER = METER_READER;
        this.TASK_ID = TASK_ID;
        this.BOOK_ID = BOOK_ID;
        this.RECORD_ID = RECORD_ID;
        this.CARD_ID = CARD_ID;
        this.LOCATION_LG = LOCATION_LG;
        this.LOCATION_LT = LOCATION_LT;
        this.EXTEND = EXTEND;
    }

    @Generated(hash = 998561682)
    public Multimedia() {
    }

    public Long getID() {
        return this.ID;
    }

    public void setID(Long ID) {
        this.ID = ID;
    }

    public int getFILE_TYPE() {
        return this.FILE_TYPE;
    }

    public void setFILE_TYPE(int FILE_TYPE) {
        this.FILE_TYPE = FILE_TYPE;
    }

    public String getFILE_NAME() {
        return this.FILE_NAME;
    }

    public void setFILE_NAME(String FILE_NAME) {
        this.FILE_NAME = FILE_NAME;
    }

    public String getFILE_HASH() {
        return this.FILE_HASH;
    }

    public void setFILE_HASH(String FILE_HASH) {
        this.FILE_HASH = FILE_HASH;
    }

    public String getFILE_URL() {
        return this.FILE_URL;
    }

    public void setFILE_URL(String FILE_URL) {
        this.FILE_URL = FILE_URL;
    }

    public int getUPLOAD_FLAG() {
        return this.UPLOAD_FLAG;
    }

    public void setUPLOAD_FLAG(int UPLOAD_FLAG) {
        this.UPLOAD_FLAG = UPLOAD_FLAG;
    }

    public Long getFILE_TIME() {
        return this.FILE_TIME;
    }

    public void setFILE_TIME(Long FILE_TIME) {
        this.FILE_TIME = FILE_TIME;
    }

    public String getEXTEND() {
        return this.EXTEND;
    }

    public void setEXTEND(String EXTEND) {
        this.EXTEND = EXTEND;
    }

    public int getMETER_READER() {
        return this.METER_READER;
    }

    public void setMETER_READER(int METER_READER) {
        this.METER_READER = METER_READER;
    }

    public int getTASK_ID() {
        return this.TASK_ID;
    }

    public void setTASK_ID(int TASK_ID) {
        this.TASK_ID = TASK_ID;
    }

    public String getBOOK_ID() {
        return this.BOOK_ID;
    }

    public void setBOOK_ID(String BOOK_ID) {
        this.BOOK_ID = BOOK_ID;
    }

    public int getRECORD_ID() {
        return this.RECORD_ID;
    }

    public void setRECORD_ID(int RECORD_ID) {
        this.RECORD_ID = RECORD_ID;
    }

    public String getCARD_ID() {
        return this.CARD_ID;
    }

    public void setCARD_ID(String CARD_ID) {
        this.CARD_ID = CARD_ID;
    }

    public String getLOCATION_LG() {
        return this.LOCATION_LG;
    }

    public void setLOCATION_LG(String LOCATION_LG) {
        this.LOCATION_LG = LOCATION_LG;
    }

    public String getLOCATION_LT() {
        return this.LOCATION_LT;
    }

    public void setLOCATION_LT(String LOCATION_LT) {
        this.LOCATION_LT = LOCATION_LT;
    }
}
