package com.sh3h.localprovider.greendaoEntity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;

/**
 * 抄表记录
 */
@Entity(nameInDb = "MR_PREVIOUSRECORD")
public class PreviousRecord {
    @Id(autoincrement = true)
    @Property(nameInDb = "ID")
    private Long ID;            //ID
    @NotNull
    private int RECORD_Id;      //抄表记录编号
    @NotNull
    private String CARD_ID;     //表卡编号
    @NotNull
    private String SUBCOM_CODE; //站点编号
    @NotNull
    private String BOOK_ID;     //册本编号
    @NotNull
    private int BOOK_SORT_INDEX;//册内序号
    @NotNull
    private int READING;        //本次抄码
    private int READING1;       //本次抄码1
    @NotNull
    private int READ_STATE;     //抄表状态
    @NotNull
    private String READ_STATE_CN;//抄表状态中文
    @NotNull
    private int BILLING_MONTH;  //抄表状态中文
    @NotNull
    private long READ_DATE;     //本次抄表时间
    @NotNull
    private int READ_YEAR;      //抄表年
    @NotNull
    private int READ_MONTH;     //抄表月
    @NotNull
    private int READ_TIMES;     //抄次
    @NotNull
    private int METER_READER;   //抄表员
    @NotNull
    private int RECORD_ENTRY_USER;//录入员
    @NotNull
    private long RECORD_ENTRY_TIME;//录入时间
    @NotNull
    private int RECORD_METHOD;//抄表方式
    @NotNull
    private int RECORD_TYPE;//抄表类型
    @NotNull
    private int READ_WATER;//抄见水量
    @NotNull
    private int AVG_READ_WATER3;//前3个月的平均水量
    @NotNull
    private int AVG_READ_WATER6;//前6个月的平均水量
    @NotNull
    private int AVG_READ_WATER12;//前12个月的平均水量
    @NotNull
    private int RECORD_STATE_LGID;//量高量低状态
    @NotNull
    private int RECORD_REASON_LGID;//量高量低原因
    private String REMARK;         //抄表备注
    private int READCORD_REASON_APPEN;//加抄原因
    @NotNull
    private int STATE;   //抄表记录状态标识
    @NotNull
    private String BAR_CODE;         //条形码
    @NotNull
    private String SEAL_NUMBER;      //钢印号
    @NotNull
    private int METER_TYPE;           //表分类
    private String MAIN_CARD_ID;      //总表编号
    @NotNull
    private String TERMINATE_READING; //旧表抄码
    @NotNull
    private int INITIATE_READING;     //新表起码
    @NotNull
    private int CALIBER_ID;           //水表口径
    @NotNull
    private Long METER_REPLACE_DATE;  //换表日期
    @NotNull
    private int METER_STATE;           //水表状态
    @NotNull
    private int ACC_WATER;             //开账水量
    @NotNull
    private double ACC_CHECK_MONEY;   //开账金额
    @NotNull
    private int LAST_READ_WATER;      //上次抄见水量
    @NotNull
    private int LAST_READING;         //上次抄码
    private int LAST_READING1;        //上次抄码1
    @NotNull
    private int LAST_READ_STATE;      //上次抄表状态
    @NotNull
    private String LAST_READ_STATE_CN;   //上次抄表状态中文
    @NotNull
    private Long LAST_READ_DATE;     //上次抄表时间
    @Generated(hash = 695549907)
    public PreviousRecord(Long ID, int RECORD_Id, @NotNull String CARD_ID,
            @NotNull String SUBCOM_CODE, @NotNull String BOOK_ID,
            int BOOK_SORT_INDEX, int READING, int READING1, int READ_STATE,
            @NotNull String READ_STATE_CN, int BILLING_MONTH, long READ_DATE,
            int READ_YEAR, int READ_MONTH, int READ_TIMES, int METER_READER,
            int RECORD_ENTRY_USER, long RECORD_ENTRY_TIME, int RECORD_METHOD,
            int RECORD_TYPE, int READ_WATER, int AVG_READ_WATER3,
            int AVG_READ_WATER6, int AVG_READ_WATER12, int RECORD_STATE_LGID,
            int RECORD_REASON_LGID, String REMARK, int READCORD_REASON_APPEN,
            int STATE, @NotNull String BAR_CODE, @NotNull String SEAL_NUMBER,
            int METER_TYPE, String MAIN_CARD_ID, @NotNull String TERMINATE_READING,
            int INITIATE_READING, int CALIBER_ID, @NotNull Long METER_REPLACE_DATE,
            int METER_STATE, int ACC_WATER, double ACC_CHECK_MONEY,
            int LAST_READ_WATER, int LAST_READING, int LAST_READING1,
            int LAST_READ_STATE, @NotNull String LAST_READ_STATE_CN,
            @NotNull Long LAST_READ_DATE) {
        this.ID = ID;
        this.RECORD_Id = RECORD_Id;
        this.CARD_ID = CARD_ID;
        this.SUBCOM_CODE = SUBCOM_CODE;
        this.BOOK_ID = BOOK_ID;
        this.BOOK_SORT_INDEX = BOOK_SORT_INDEX;
        this.READING = READING;
        this.READING1 = READING1;
        this.READ_STATE = READ_STATE;
        this.READ_STATE_CN = READ_STATE_CN;
        this.BILLING_MONTH = BILLING_MONTH;
        this.READ_DATE = READ_DATE;
        this.READ_YEAR = READ_YEAR;
        this.READ_MONTH = READ_MONTH;
        this.READ_TIMES = READ_TIMES;
        this.METER_READER = METER_READER;
        this.RECORD_ENTRY_USER = RECORD_ENTRY_USER;
        this.RECORD_ENTRY_TIME = RECORD_ENTRY_TIME;
        this.RECORD_METHOD = RECORD_METHOD;
        this.RECORD_TYPE = RECORD_TYPE;
        this.READ_WATER = READ_WATER;
        this.AVG_READ_WATER3 = AVG_READ_WATER3;
        this.AVG_READ_WATER6 = AVG_READ_WATER6;
        this.AVG_READ_WATER12 = AVG_READ_WATER12;
        this.RECORD_STATE_LGID = RECORD_STATE_LGID;
        this.RECORD_REASON_LGID = RECORD_REASON_LGID;
        this.REMARK = REMARK;
        this.READCORD_REASON_APPEN = READCORD_REASON_APPEN;
        this.STATE = STATE;
        this.BAR_CODE = BAR_CODE;
        this.SEAL_NUMBER = SEAL_NUMBER;
        this.METER_TYPE = METER_TYPE;
        this.MAIN_CARD_ID = MAIN_CARD_ID;
        this.TERMINATE_READING = TERMINATE_READING;
        this.INITIATE_READING = INITIATE_READING;
        this.CALIBER_ID = CALIBER_ID;
        this.METER_REPLACE_DATE = METER_REPLACE_DATE;
        this.METER_STATE = METER_STATE;
        this.ACC_WATER = ACC_WATER;
        this.ACC_CHECK_MONEY = ACC_CHECK_MONEY;
        this.LAST_READ_WATER = LAST_READ_WATER;
        this.LAST_READING = LAST_READING;
        this.LAST_READING1 = LAST_READING1;
        this.LAST_READ_STATE = LAST_READ_STATE;
        this.LAST_READ_STATE_CN = LAST_READ_STATE_CN;
        this.LAST_READ_DATE = LAST_READ_DATE;
    }
    @Generated(hash = 1631521890)
    public PreviousRecord() {
    }
    public Long getID() {
        return this.ID;
    }
    public void setID(Long ID) {
        this.ID = ID;
    }
    public int getRECORD_Id() {
        return this.RECORD_Id;
    }
    public void setRECORD_Id(int RECORD_Id) {
        this.RECORD_Id = RECORD_Id;
    }
    public String getCARD_ID() {
        return this.CARD_ID;
    }
    public void setCARD_ID(String CARD_ID) {
        this.CARD_ID = CARD_ID;
    }
    public String getSUBCOM_CODE() {
        return this.SUBCOM_CODE;
    }
    public void setSUBCOM_CODE(String SUBCOM_CODE) {
        this.SUBCOM_CODE = SUBCOM_CODE;
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
    public int getREADING() {
        return this.READING;
    }
    public void setREADING(int READING) {
        this.READING = READING;
    }
    public int getREADING1() {
        return this.READING1;
    }
    public void setREADING1(int READING1) {
        this.READING1 = READING1;
    }
    public int getREAD_STATE() {
        return this.READ_STATE;
    }
    public void setREAD_STATE(int READ_STATE) {
        this.READ_STATE = READ_STATE;
    }
    public String getREAD_STATE_CN() {
        return this.READ_STATE_CN;
    }
    public void setREAD_STATE_CN(String READ_STATE_CN) {
        this.READ_STATE_CN = READ_STATE_CN;
    }
    public int getBILLING_MONTH() {
        return this.BILLING_MONTH;
    }
    public void setBILLING_MONTH(int BILLING_MONTH) {
        this.BILLING_MONTH = BILLING_MONTH;
    }
    public long getREAD_DATE() {
        return this.READ_DATE;
    }
    public void setREAD_DATE(long READ_DATE) {
        this.READ_DATE = READ_DATE;
    }
    public int getREAD_YEAR() {
        return this.READ_YEAR;
    }
    public void setREAD_YEAR(int READ_YEAR) {
        this.READ_YEAR = READ_YEAR;
    }
    public int getREAD_MONTH() {
        return this.READ_MONTH;
    }
    public void setREAD_MONTH(int READ_MONTH) {
        this.READ_MONTH = READ_MONTH;
    }
    public int getREAD_TIMES() {
        return this.READ_TIMES;
    }
    public void setREAD_TIMES(int READ_TIMES) {
        this.READ_TIMES = READ_TIMES;
    }
    public int getMETER_READER() {
        return this.METER_READER;
    }
    public void setMETER_READER(int METER_READER) {
        this.METER_READER = METER_READER;
    }
    public int getRECORD_ENTRY_USER() {
        return this.RECORD_ENTRY_USER;
    }
    public void setRECORD_ENTRY_USER(int RECORD_ENTRY_USER) {
        this.RECORD_ENTRY_USER = RECORD_ENTRY_USER;
    }
    public long getRECORD_ENTRY_TIME() {
        return this.RECORD_ENTRY_TIME;
    }
    public void setRECORD_ENTRY_TIME(long RECORD_ENTRY_TIME) {
        this.RECORD_ENTRY_TIME = RECORD_ENTRY_TIME;
    }
    public int getRECORD_METHOD() {
        return this.RECORD_METHOD;
    }
    public void setRECORD_METHOD(int RECORD_METHOD) {
        this.RECORD_METHOD = RECORD_METHOD;
    }
    public int getRECORD_TYPE() {
        return this.RECORD_TYPE;
    }
    public void setRECORD_TYPE(int RECORD_TYPE) {
        this.RECORD_TYPE = RECORD_TYPE;
    }
    public int getREAD_WATER() {
        return this.READ_WATER;
    }
    public void setREAD_WATER(int READ_WATER) {
        this.READ_WATER = READ_WATER;
    }
    public int getAVG_READ_WATER3() {
        return this.AVG_READ_WATER3;
    }
    public void setAVG_READ_WATER3(int AVG_READ_WATER3) {
        this.AVG_READ_WATER3 = AVG_READ_WATER3;
    }
    public int getAVG_READ_WATER6() {
        return this.AVG_READ_WATER6;
    }
    public void setAVG_READ_WATER6(int AVG_READ_WATER6) {
        this.AVG_READ_WATER6 = AVG_READ_WATER6;
    }
    public int getAVG_READ_WATER12() {
        return this.AVG_READ_WATER12;
    }
    public void setAVG_READ_WATER12(int AVG_READ_WATER12) {
        this.AVG_READ_WATER12 = AVG_READ_WATER12;
    }
    public int getRECORD_STATE_LGID() {
        return this.RECORD_STATE_LGID;
    }
    public void setRECORD_STATE_LGID(int RECORD_STATE_LGID) {
        this.RECORD_STATE_LGID = RECORD_STATE_LGID;
    }
    public int getRECORD_REASON_LGID() {
        return this.RECORD_REASON_LGID;
    }
    public void setRECORD_REASON_LGID(int RECORD_REASON_LGID) {
        this.RECORD_REASON_LGID = RECORD_REASON_LGID;
    }
    public String getREMARK() {
        return this.REMARK;
    }
    public void setREMARK(String REMARK) {
        this.REMARK = REMARK;
    }
    public int getREADCORD_REASON_APPEN() {
        return this.READCORD_REASON_APPEN;
    }
    public void setREADCORD_REASON_APPEN(int READCORD_REASON_APPEN) {
        this.READCORD_REASON_APPEN = READCORD_REASON_APPEN;
    }
    public int getSTATE() {
        return this.STATE;
    }
    public void setSTATE(int STATE) {
        this.STATE = STATE;
    }
    public String getBAR_CODE() {
        return this.BAR_CODE;
    }
    public void setBAR_CODE(String BAR_CODE) {
        this.BAR_CODE = BAR_CODE;
    }
    public String getSEAL_NUMBER() {
        return this.SEAL_NUMBER;
    }
    public void setSEAL_NUMBER(String SEAL_NUMBER) {
        this.SEAL_NUMBER = SEAL_NUMBER;
    }
    public int getMETER_TYPE() {
        return this.METER_TYPE;
    }
    public void setMETER_TYPE(int METER_TYPE) {
        this.METER_TYPE = METER_TYPE;
    }
    public String getMAIN_CARD_ID() {
        return this.MAIN_CARD_ID;
    }
    public void setMAIN_CARD_ID(String MAIN_CARD_ID) {
        this.MAIN_CARD_ID = MAIN_CARD_ID;
    }
    public String getTERMINATE_READING() {
        return this.TERMINATE_READING;
    }
    public void setTERMINATE_READING(String TERMINATE_READING) {
        this.TERMINATE_READING = TERMINATE_READING;
    }
    public int getINITIATE_READING() {
        return this.INITIATE_READING;
    }
    public void setINITIATE_READING(int INITIATE_READING) {
        this.INITIATE_READING = INITIATE_READING;
    }
    public int getCALIBER_ID() {
        return this.CALIBER_ID;
    }
    public void setCALIBER_ID(int CALIBER_ID) {
        this.CALIBER_ID = CALIBER_ID;
    }
    public Long getMETER_REPLACE_DATE() {
        return this.METER_REPLACE_DATE;
    }
    public void setMETER_REPLACE_DATE(Long METER_REPLACE_DATE) {
        this.METER_REPLACE_DATE = METER_REPLACE_DATE;
    }
    public int getMETER_STATE() {
        return this.METER_STATE;
    }
    public void setMETER_STATE(int METER_STATE) {
        this.METER_STATE = METER_STATE;
    }
    public int getACC_WATER() {
        return this.ACC_WATER;
    }
    public void setACC_WATER(int ACC_WATER) {
        this.ACC_WATER = ACC_WATER;
    }
    public double getACC_CHECK_MONEY() {
        return this.ACC_CHECK_MONEY;
    }
    public void setACC_CHECK_MONEY(double ACC_CHECK_MONEY) {
        this.ACC_CHECK_MONEY = ACC_CHECK_MONEY;
    }
    public int getLAST_READ_WATER() {
        return this.LAST_READ_WATER;
    }
    public void setLAST_READ_WATER(int LAST_READ_WATER) {
        this.LAST_READ_WATER = LAST_READ_WATER;
    }
    public int getLAST_READING() {
        return this.LAST_READING;
    }
    public void setLAST_READING(int LAST_READING) {
        this.LAST_READING = LAST_READING;
    }
    public int getLAST_READING1() {
        return this.LAST_READING1;
    }
    public void setLAST_READING1(int LAST_READING1) {
        this.LAST_READING1 = LAST_READING1;
    }
    public int getLAST_READ_STATE() {
        return this.LAST_READ_STATE;
    }
    public void setLAST_READ_STATE(int LAST_READ_STATE) {
        this.LAST_READ_STATE = LAST_READ_STATE;
    }
    public String getLAST_READ_STATE_CN() {
        return this.LAST_READ_STATE_CN;
    }
    public void setLAST_READ_STATE_CN(String LAST_READ_STATE_CN) {
        this.LAST_READ_STATE_CN = LAST_READ_STATE_CN;
    }
    public Long getLAST_READ_DATE() {
        return this.LAST_READ_DATE;
    }
    public void setLAST_READ_DATE(Long LAST_READ_DATE) {
        this.LAST_READ_DATE = LAST_READ_DATE;
    }


}
