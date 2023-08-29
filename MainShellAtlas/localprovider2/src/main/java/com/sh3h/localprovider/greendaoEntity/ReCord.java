package com.sh3h.localprovider.greendaoEntity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Property;

/**
 * 抄表数据
 */
@Entity(nameInDb = "MR_RECORD")
public class ReCord {
    @Id(autoincrement = true)
    @Property(nameInDb = "ID")
    private Long    ID;                //ID
    @NotNull
    private int     TASK_ID;           //抄表任务编号
    @NotNull
    private int     RECORD_ID;         //抄表记录编号
    @NotNull
    private String  CARD_ID;           //表卡编号
    @NotNull
    private String  SUBCOM_CODE;       //站点编号
    @NotNull
    private String  BOOK_ID;           //册本编号
    @NotNull
    private int     BOOK_SORT_INDEX;   //册内序号
    @NotNull
    private int     BILLING_MONTH;     //账务年月
    @NotNull
    private int     READ_YEAR;         //抄表年
    @NotNull
    private int     READ_MONTH;        //抄表月
    @NotNull
    private int     READ_TIMES;        //抄次
    private int     RECORD_ENTRY_TIME; //抄表录入时间
    @NotNull
    private int     METER_READER;      //抄表员
    private int     READING;           //本次抄码
    private int     READING1;          //本次抄码1
    private int     READ_WATER;        //抄见水量
    private int     READ_DATE;         //本次抄表日期
    private int     READ_STATE;        //本次抄表状态
    private int     READ_STATE_TIMES;  //状态连续次数
    private int     RECORD_STATE_LGLD; //量高量低状态
    private int     RECORD_REASON_LGLD;//量高量低原因
    private String  RECORD_REMARK;     //抄表备注
    @NotNull
    private int     AVG_WATER3;        //3月平均量
    @NotNull
    private int     AVG_WATER6;        //6月平均量
    @NotNull
    private int     AVG_WATER12;       //12月平均量
    @NotNull
    private int     LAST_READING;      //上次抄码
    private int     LAST_READING1;     //上次抄码1
    private int     LAST_READ_WATER;   //上次抄见水量
    private int     LAST_READ_STATE;   //上次抄表状态
    private Long    LAST_READ_DATE;    //上次抄表日期
    private int     LAST_STATE_TIMES;  //状态连续次数
    @NotNull
    private int     METER_TYPE;        //表分类
    @NotNull
    private int     METER_TYPE2;       //表分类2
    @NotNull
    private String  MAIN_CARD_ID;      //总表编号
    @NotNull
    private int     TERMINATE_READING; //旧表底码
    @NotNull
    private int     INITIATE_READING;  //新表起码
    @NotNull
    private String  SEAL_NUMBER;       //钢印号
    @NotNull
    private String  BAR_CODE;          //条形码
    @NotNull
    private int     CALIBER_ID;        //口径
    @NotNull
    private int     CALIBER_RANGE_ID;  //量程
    @NotNull
    private int     MODEL_ID;          //水表型号
    @NotNull
    private int     PRODUCER_ID;       //厂商
    @NotNull
    private int     STATE;             //状态标识
    private Long    INSTALL_DATE;      //装表时间
    private Long    REPLACE_DATE;      //换表时间
    private double  LOCATION_LG;       //经度
    private double  LOCATION_LT;       //纬度
    @NotNull
    private int     DOWNLOAD_TYPE;     //下载类型
    @NotNull
    private int     IS_UPLPADED;       //是否上传
    private String  EXTEND;            //扩展字段
    @Generated(hash = 805146346)
    public ReCord(Long ID, int TASK_ID, int RECORD_ID, @NotNull String CARD_ID,
            @NotNull String SUBCOM_CODE, @NotNull String BOOK_ID,
            int BOOK_SORT_INDEX, int BILLING_MONTH, int READ_YEAR, int READ_MONTH,
            int READ_TIMES, int RECORD_ENTRY_TIME, int METER_READER, int READING,
            int READING1, int READ_WATER, int READ_DATE, int READ_STATE,
            int READ_STATE_TIMES, int RECORD_STATE_LGLD, int RECORD_REASON_LGLD,
            String RECORD_REMARK, int AVG_WATER3, int AVG_WATER6, int AVG_WATER12,
            int LAST_READING, int LAST_READING1, int LAST_READ_WATER,
            int LAST_READ_STATE, Long LAST_READ_DATE, int LAST_STATE_TIMES,
            int METER_TYPE, int METER_TYPE2, @NotNull String MAIN_CARD_ID,
            int TERMINATE_READING, int INITIATE_READING,
            @NotNull String SEAL_NUMBER, @NotNull String BAR_CODE, int CALIBER_ID,
            int CALIBER_RANGE_ID, int MODEL_ID, int PRODUCER_ID, int STATE,
            Long INSTALL_DATE, Long REPLACE_DATE, double LOCATION_LG,
            double LOCATION_LT, int DOWNLOAD_TYPE, int IS_UPLPADED, String EXTEND) {
        this.ID = ID;
        this.TASK_ID = TASK_ID;
        this.RECORD_ID = RECORD_ID;
        this.CARD_ID = CARD_ID;
        this.SUBCOM_CODE = SUBCOM_CODE;
        this.BOOK_ID = BOOK_ID;
        this.BOOK_SORT_INDEX = BOOK_SORT_INDEX;
        this.BILLING_MONTH = BILLING_MONTH;
        this.READ_YEAR = READ_YEAR;
        this.READ_MONTH = READ_MONTH;
        this.READ_TIMES = READ_TIMES;
        this.RECORD_ENTRY_TIME = RECORD_ENTRY_TIME;
        this.METER_READER = METER_READER;
        this.READING = READING;
        this.READING1 = READING1;
        this.READ_WATER = READ_WATER;
        this.READ_DATE = READ_DATE;
        this.READ_STATE = READ_STATE;
        this.READ_STATE_TIMES = READ_STATE_TIMES;
        this.RECORD_STATE_LGLD = RECORD_STATE_LGLD;
        this.RECORD_REASON_LGLD = RECORD_REASON_LGLD;
        this.RECORD_REMARK = RECORD_REMARK;
        this.AVG_WATER3 = AVG_WATER3;
        this.AVG_WATER6 = AVG_WATER6;
        this.AVG_WATER12 = AVG_WATER12;
        this.LAST_READING = LAST_READING;
        this.LAST_READING1 = LAST_READING1;
        this.LAST_READ_WATER = LAST_READ_WATER;
        this.LAST_READ_STATE = LAST_READ_STATE;
        this.LAST_READ_DATE = LAST_READ_DATE;
        this.LAST_STATE_TIMES = LAST_STATE_TIMES;
        this.METER_TYPE = METER_TYPE;
        this.METER_TYPE2 = METER_TYPE2;
        this.MAIN_CARD_ID = MAIN_CARD_ID;
        this.TERMINATE_READING = TERMINATE_READING;
        this.INITIATE_READING = INITIATE_READING;
        this.SEAL_NUMBER = SEAL_NUMBER;
        this.BAR_CODE = BAR_CODE;
        this.CALIBER_ID = CALIBER_ID;
        this.CALIBER_RANGE_ID = CALIBER_RANGE_ID;
        this.MODEL_ID = MODEL_ID;
        this.PRODUCER_ID = PRODUCER_ID;
        this.STATE = STATE;
        this.INSTALL_DATE = INSTALL_DATE;
        this.REPLACE_DATE = REPLACE_DATE;
        this.LOCATION_LG = LOCATION_LG;
        this.LOCATION_LT = LOCATION_LT;
        this.DOWNLOAD_TYPE = DOWNLOAD_TYPE;
        this.IS_UPLPADED = IS_UPLPADED;
        this.EXTEND = EXTEND;
    }
    @Generated(hash = 1140642954)
    public ReCord() {
    }
    public Long getID() {
        return this.ID;
    }
    public void setID(Long ID) {
        this.ID = ID;
    }
    public int getTASK_ID() {
        return this.TASK_ID;
    }
    public void setTASK_ID(int TASK_ID) {
        this.TASK_ID = TASK_ID;
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
    public int getBILLING_MONTH() {
        return this.BILLING_MONTH;
    }
    public void setBILLING_MONTH(int BILLING_MONTH) {
        this.BILLING_MONTH = BILLING_MONTH;
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
    public int getRECORD_ENTRY_TIME() {
        return this.RECORD_ENTRY_TIME;
    }
    public void setRECORD_ENTRY_TIME(int RECORD_ENTRY_TIME) {
        this.RECORD_ENTRY_TIME = RECORD_ENTRY_TIME;
    }
    public int getMETER_READER() {
        return this.METER_READER;
    }
    public void setMETER_READER(int METER_READER) {
        this.METER_READER = METER_READER;
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
    public int getREAD_WATER() {
        return this.READ_WATER;
    }
    public void setREAD_WATER(int READ_WATER) {
        this.READ_WATER = READ_WATER;
    }
    public int getREAD_DATE() {
        return this.READ_DATE;
    }
    public void setREAD_DATE(int READ_DATE) {
        this.READ_DATE = READ_DATE;
    }
    public int getREAD_STATE() {
        return this.READ_STATE;
    }
    public void setREAD_STATE(int READ_STATE) {
        this.READ_STATE = READ_STATE;
    }
    public int getREAD_STATE_TIMES() {
        return this.READ_STATE_TIMES;
    }
    public void setREAD_STATE_TIMES(int READ_STATE_TIMES) {
        this.READ_STATE_TIMES = READ_STATE_TIMES;
    }
    public int getRECORD_STATE_LGLD() {
        return this.RECORD_STATE_LGLD;
    }
    public void setRECORD_STATE_LGLD(int RECORD_STATE_LGLD) {
        this.RECORD_STATE_LGLD = RECORD_STATE_LGLD;
    }
    public int getRECORD_REASON_LGLD() {
        return this.RECORD_REASON_LGLD;
    }
    public void setRECORD_REASON_LGLD(int RECORD_REASON_LGLD) {
        this.RECORD_REASON_LGLD = RECORD_REASON_LGLD;
    }
    public String getRECORD_REMARK() {
        return this.RECORD_REMARK;
    }
    public void setRECORD_REMARK(String RECORD_REMARK) {
        this.RECORD_REMARK = RECORD_REMARK;
    }
    public int getAVG_WATER3() {
        return this.AVG_WATER3;
    }
    public void setAVG_WATER3(int AVG_WATER3) {
        this.AVG_WATER3 = AVG_WATER3;
    }
    public int getAVG_WATER6() {
        return this.AVG_WATER6;
    }
    public void setAVG_WATER6(int AVG_WATER6) {
        this.AVG_WATER6 = AVG_WATER6;
    }
    public int getAVG_WATER12() {
        return this.AVG_WATER12;
    }
    public void setAVG_WATER12(int AVG_WATER12) {
        this.AVG_WATER12 = AVG_WATER12;
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
    public int getLAST_READ_WATER() {
        return this.LAST_READ_WATER;
    }
    public void setLAST_READ_WATER(int LAST_READ_WATER) {
        this.LAST_READ_WATER = LAST_READ_WATER;
    }
    public int getLAST_READ_STATE() {
        return this.LAST_READ_STATE;
    }
    public void setLAST_READ_STATE(int LAST_READ_STATE) {
        this.LAST_READ_STATE = LAST_READ_STATE;
    }
    public Long getLAST_READ_DATE() {
        return this.LAST_READ_DATE;
    }
    public void setLAST_READ_DATE(Long LAST_READ_DATE) {
        this.LAST_READ_DATE = LAST_READ_DATE;
    }
    public int getLAST_STATE_TIMES() {
        return this.LAST_STATE_TIMES;
    }
    public void setLAST_STATE_TIMES(int LAST_STATE_TIMES) {
        this.LAST_STATE_TIMES = LAST_STATE_TIMES;
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
    public int getTERMINATE_READING() {
        return this.TERMINATE_READING;
    }
    public void setTERMINATE_READING(int TERMINATE_READING) {
        this.TERMINATE_READING = TERMINATE_READING;
    }
    public int getINITIATE_READING() {
        return this.INITIATE_READING;
    }
    public void setINITIATE_READING(int INITIATE_READING) {
        this.INITIATE_READING = INITIATE_READING;
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
    public int getSTATE() {
        return this.STATE;
    }
    public void setSTATE(int STATE) {
        this.STATE = STATE;
    }
    public Long getINSTALL_DATE() {
        return this.INSTALL_DATE;
    }
    public void setINSTALL_DATE(Long INSTALL_DATE) {
        this.INSTALL_DATE = INSTALL_DATE;
    }
    public Long getREPLACE_DATE() {
        return this.REPLACE_DATE;
    }
    public void setREPLACE_DATE(Long REPLACE_DATE) {
        this.REPLACE_DATE = REPLACE_DATE;
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
