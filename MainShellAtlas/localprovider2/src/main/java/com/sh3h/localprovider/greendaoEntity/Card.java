package com.sh3h.localprovider.greendaoEntity;

/**
 * Created by zhangjing on 2017/4/13.
 */

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;

/**
 * 表卡信息
 */
@Entity(nameInDb = "MR_CARD")
public class Card {

    @Id(autoincrement = true)
    @Property(nameInDb = "ID")
    private Long ID;//ID

    @NotNull
    private String CARD_ID;//表卡编号

    @NotNull
    private String CARD_NAME;//表卡

    @NotNull
    private String CARD_ADDRESS;//表卡地址

    @NotNull
    private String BOOK_ID;//册本编号

    @NotNull
    private int BOOK_SORT_INDEX;//册内序号

    @NotNull
    private String CUSTOMER_ID;//客户编号

    @NotNull
    private String SUBCOM_CODE;//站点

    private String MOBILE1;//手机1

    private String MOBILE2;//手机2

    private String MOBILE3;//手机3

    private String tel1;//固定电话1

    private String tel2;//固定电话2

    private String tel3;//固定电话3

    @NotNull
    private int PAY_METHOD;//缴费方式

    @NotNull
    private int PRICE_CODE;//用水性质

    @NotNull
    private int METERCARD_STATE;//表卡状态

    private long CREATE_DATE;//立户日期

    @NotNull
    private String INSTALL_POSITION;//表位

    @NotNull
    private String SEAL_NUMBER;//钢印号

    @NotNull
    private String BAR_CODE;//条形码

    @NotNull
    private int CALIBER_ID;//口径

    @NotNull
    private int CALIBER_RANGE_ID;//量程

    @NotNull
    private int MODEL_ID;//水表型号

    @NotNull
    private int PRODUCER_ID;//厂商

    @NotNull
    private int METER_TYPE;//表分类

    @NotNull
    private int METER_TYPE2;//表分类2

    @NotNull
    private String MAIN_CARD_ID;//总表编号

    private long INSTALL_DATE;//装表日期

    private long REPLACE_DATE;//换表日期

    @NotNull
    private int INITIATE_READING;//新表起码

    @NotNull
    private int TERMINATE_READING;//旧表底码

    @NotNull
    private int METER_STATE;//水表状态

    private int PERSON_COUNT;//人口数

    private double LOCATION_LG;//经度

    private double LOCATION_LT;//纬度

    @NotNull
    private int DOWNLOAD_TYPE;//下载类型

    @NotNull
    private int IS_MODIFIED;//是否修改

    @NotNull
    private int IS_UPLPADED;//是否上传

    private String EXTEND;//扩展字段

    @Generated(hash = 1763343517)
    public Card(Long ID, @NotNull String CARD_ID, @NotNull String CARD_NAME,
            @NotNull String CARD_ADDRESS, @NotNull String BOOK_ID,
            int BOOK_SORT_INDEX, @NotNull String CUSTOMER_ID,
            @NotNull String SUBCOM_CODE, String MOBILE1, String MOBILE2,
            String MOBILE3, String tel1, String tel2, String tel3, int PAY_METHOD,
            int PRICE_CODE, int METERCARD_STATE, long CREATE_DATE,
            @NotNull String INSTALL_POSITION, @NotNull String SEAL_NUMBER,
            @NotNull String BAR_CODE, int CALIBER_ID, int CALIBER_RANGE_ID,
            int MODEL_ID, int PRODUCER_ID, int METER_TYPE, int METER_TYPE2,
            @NotNull String MAIN_CARD_ID, long INSTALL_DATE, long REPLACE_DATE,
            int INITIATE_READING, int TERMINATE_READING, int METER_STATE,
            int PERSON_COUNT, double LOCATION_LG, double LOCATION_LT,
            int DOWNLOAD_TYPE, int IS_MODIFIED, int IS_UPLPADED, String EXTEND) {
        this.ID = ID;
        this.CARD_ID = CARD_ID;
        this.CARD_NAME = CARD_NAME;
        this.CARD_ADDRESS = CARD_ADDRESS;
        this.BOOK_ID = BOOK_ID;
        this.BOOK_SORT_INDEX = BOOK_SORT_INDEX;
        this.CUSTOMER_ID = CUSTOMER_ID;
        this.SUBCOM_CODE = SUBCOM_CODE;
        this.MOBILE1 = MOBILE1;
        this.MOBILE2 = MOBILE2;
        this.MOBILE3 = MOBILE3;
        this.tel1 = tel1;
        this.tel2 = tel2;
        this.tel3 = tel3;
        this.PAY_METHOD = PAY_METHOD;
        this.PRICE_CODE = PRICE_CODE;
        this.METERCARD_STATE = METERCARD_STATE;
        this.CREATE_DATE = CREATE_DATE;
        this.INSTALL_POSITION = INSTALL_POSITION;
        this.SEAL_NUMBER = SEAL_NUMBER;
        this.BAR_CODE = BAR_CODE;
        this.CALIBER_ID = CALIBER_ID;
        this.CALIBER_RANGE_ID = CALIBER_RANGE_ID;
        this.MODEL_ID = MODEL_ID;
        this.PRODUCER_ID = PRODUCER_ID;
        this.METER_TYPE = METER_TYPE;
        this.METER_TYPE2 = METER_TYPE2;
        this.MAIN_CARD_ID = MAIN_CARD_ID;
        this.INSTALL_DATE = INSTALL_DATE;
        this.REPLACE_DATE = REPLACE_DATE;
        this.INITIATE_READING = INITIATE_READING;
        this.TERMINATE_READING = TERMINATE_READING;
        this.METER_STATE = METER_STATE;
        this.PERSON_COUNT = PERSON_COUNT;
        this.LOCATION_LG = LOCATION_LG;
        this.LOCATION_LT = LOCATION_LT;
        this.DOWNLOAD_TYPE = DOWNLOAD_TYPE;
        this.IS_MODIFIED = IS_MODIFIED;
        this.IS_UPLPADED = IS_UPLPADED;
        this.EXTEND = EXTEND;
    }

    @Generated(hash = 52700939)
    public Card() {
    }

    public Long getID() {
        return this.ID;
    }

    public void setID(Long ID) {
        this.ID = ID;
    }

    public String getCARD_ID() {
        return this.CARD_ID;
    }

    public void setCARD_ID(String CARD_ID) {
        this.CARD_ID = CARD_ID;
    }

    public String getCARD_NAME() {
        return this.CARD_NAME;
    }

    public void setCARD_NAME(String CARD_NAME) {
        this.CARD_NAME = CARD_NAME;
    }

    public String getCARD_ADDRESS() {
        return this.CARD_ADDRESS;
    }

    public void setCARD_ADDRESS(String CARD_ADDRESS) {
        this.CARD_ADDRESS = CARD_ADDRESS;
    }

    public String getBOOK_ID() {
        return this.BOOK_ID;
    }

    public void setBOOK_ID(String BOOK_ID) {
        this.BOOK_ID = BOOK_ID;
    }

    public int getBOOK_SORT_INDEX() {
        return this.BOOK_SORT_INDEX;
    }

    public void setBOOK_SORT_INDEX(int BOOK_SORT_INDEX) {
        this.BOOK_SORT_INDEX = BOOK_SORT_INDEX;
    }

    public String getCUSTOMER_ID() {
        return this.CUSTOMER_ID;
    }

    public void setCUSTOMER_ID(String CUSTOMER_ID) {
        this.CUSTOMER_ID = CUSTOMER_ID;
    }

    public String getSUBCOM_CODE() {
        return this.SUBCOM_CODE;
    }

    public void setSUBCOM_CODE(String SUBCOM_CODE) {
        this.SUBCOM_CODE = SUBCOM_CODE;
    }

    public String getMOBILE1() {
        return this.MOBILE1;
    }

    public void setMOBILE1(String MOBILE1) {
        this.MOBILE1 = MOBILE1;
    }

    public String getMOBILE2() {
        return this.MOBILE2;
    }

    public void setMOBILE2(String MOBILE2) {
        this.MOBILE2 = MOBILE2;
    }

    public String getMOBILE3() {
        return this.MOBILE3;
    }

    public void setMOBILE3(String MOBILE3) {
        this.MOBILE3 = MOBILE3;
    }

    public String getTel1() {
        return this.tel1;
    }

    public void setTel1(String tel1) {
        this.tel1 = tel1;
    }

    public String getTel2() {
        return this.tel2;
    }

    public void setTel2(String tel2) {
        this.tel2 = tel2;
    }

    public String getTel3() {
        return this.tel3;
    }

    public void setTel3(String tel3) {
        this.tel3 = tel3;
    }

    public int getPAY_METHOD() {
        return this.PAY_METHOD;
    }

    public void setPAY_METHOD(int PAY_METHOD) {
        this.PAY_METHOD = PAY_METHOD;
    }

    public int getPRICE_CODE() {
        return this.PRICE_CODE;
    }

    public void setPRICE_CODE(int PRICE_CODE) {
        this.PRICE_CODE = PRICE_CODE;
    }

    public int getMETERCARD_STATE() {
        return this.METERCARD_STATE;
    }

    public void setMETERCARD_STATE(int METERCARD_STATE) {
        this.METERCARD_STATE = METERCARD_STATE;
    }

    public long getCREATE_DATE() {
        return this.CREATE_DATE;
    }

    public void setCREATE_DATE(long CREATE_DATE) {
        this.CREATE_DATE = CREATE_DATE;
    }

    public String getINSTALL_POSITION() {
        return this.INSTALL_POSITION;
    }

    public void setINSTALL_POSITION(String INSTALL_POSITION) {
        this.INSTALL_POSITION = INSTALL_POSITION;
    }

    public String getSEAL_NUMBER() {
        return this.SEAL_NUMBER;
    }

    public void setSEAL_NUMBER(String SEAL_NUMBER) {
        this.SEAL_NUMBER = SEAL_NUMBER;
    }

    public String getBAR_CODE() {
        return this.BAR_CODE;
    }

    public void setBAR_CODE(String BAR_CODE) {
        this.BAR_CODE = BAR_CODE;
    }

    public int getCALIBER_ID() {
        return this.CALIBER_ID;
    }

    public void setCALIBER_ID(int CALIBER_ID) {
        this.CALIBER_ID = CALIBER_ID;
    }

    public int getCALIBER_RANGE_ID() {
        return this.CALIBER_RANGE_ID;
    }

    public void setCALIBER_RANGE_ID(int CALIBER_RANGE_ID) {
        this.CALIBER_RANGE_ID = CALIBER_RANGE_ID;
    }

    public int getMODEL_ID() {
        return this.MODEL_ID;
    }

    public void setMODEL_ID(int MODEL_ID) {
        this.MODEL_ID = MODEL_ID;
    }

    public int getPRODUCER_ID() {
        return this.PRODUCER_ID;
    }

    public void setPRODUCER_ID(int PRODUCER_ID) {
        this.PRODUCER_ID = PRODUCER_ID;
    }

    public int getMETER_TYPE() {
        return this.METER_TYPE;
    }

    public void setMETER_TYPE(int METER_TYPE) {
        this.METER_TYPE = METER_TYPE;
    }

    public int getMETER_TYPE2() {
        return this.METER_TYPE2;
    }

    public void setMETER_TYPE2(int METER_TYPE2) {
        this.METER_TYPE2 = METER_TYPE2;
    }

    public String getMAIN_CARD_ID() {
        return this.MAIN_CARD_ID;
    }

    public void setMAIN_CARD_ID(String MAIN_CARD_ID) {
        this.MAIN_CARD_ID = MAIN_CARD_ID;
    }

    public long getINSTALL_DATE() {
        return this.INSTALL_DATE;
    }

    public void setINSTALL_DATE(long INSTALL_DATE) {
        this.INSTALL_DATE = INSTALL_DATE;
    }

    public long getREPLACE_DATE() {
        return this.REPLACE_DATE;
    }

    public void setREPLACE_DATE(long REPLACE_DATE) {
        this.REPLACE_DATE = REPLACE_DATE;
    }

    public int getINITIATE_READING() {
        return this.INITIATE_READING;
    }

    public void setINITIATE_READING(int INITIATE_READING) {
        this.INITIATE_READING = INITIATE_READING;
    }

    public int getTERMINATE_READING() {
        return this.TERMINATE_READING;
    }

    public void setTERMINATE_READING(int TERMINATE_READING) {
        this.TERMINATE_READING = TERMINATE_READING;
    }

    public int getMETER_STATE() {
        return this.METER_STATE;
    }

    public void setMETER_STATE(int METER_STATE) {
        this.METER_STATE = METER_STATE;
    }

    public int getPERSON_COUNT() {
        return this.PERSON_COUNT;
    }

    public void setPERSON_COUNT(int PERSON_COUNT) {
        this.PERSON_COUNT = PERSON_COUNT;
    }

    public double getLOCATION_LG() {
        return this.LOCATION_LG;
    }

    public void setLOCATION_LG(double LOCATION_LG) {
        this.LOCATION_LG = LOCATION_LG;
    }

    public double getLOCATION_LT() {
        return this.LOCATION_LT;
    }

    public void setLOCATION_LT(double LOCATION_LT) {
        this.LOCATION_LT = LOCATION_LT;
    }

    public int getDOWNLOAD_TYPE() {
        return this.DOWNLOAD_TYPE;
    }

    public void setDOWNLOAD_TYPE(int DOWNLOAD_TYPE) {
        this.DOWNLOAD_TYPE = DOWNLOAD_TYPE;
    }

    public int getIS_MODIFIED() {
        return this.IS_MODIFIED;
    }

    public void setIS_MODIFIED(int IS_MODIFIED) {
        this.IS_MODIFIED = IS_MODIFIED;
    }

    public int getIS_UPLPADED() {
        return this.IS_UPLPADED;
    }

    public void setIS_UPLPADED(int IS_UPLPADED) {
        this.IS_UPLPADED = IS_UPLPADED;
    }

    public String getEXTEND() {
        return this.EXTEND;
    }

    public void setEXTEND(String EXTEND) {
        this.EXTEND = EXTEND;
    }
}
