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
 * 抄表任务
 */
@Entity(nameInDb = "MR_TASK")
public class Task {

    @Id(autoincrement = true)
    @Property(nameInDb = "ID")
    private Long ID;            //ID

    @NotNull
    private int TASK_ID;//任务编号

    @NotNull
    private int METER_READER;//抄表员

    @NotNull
    private long TASK_DISPATCH_DATE;//任务派发时间

    @NotNull
    private int BILLING_MONTH;//账务年月

    @NotNull
    private String BOOK_ID;//册本编号

    @NotNull
    private String BOOK_NAME;//册本名称

    @NotNull
    private int METER_CYCLE;//抄表周期

    @NotNull
    private int READ_TIMES;//抄次

    @NotNull
    private int READ_DAY;//工次

    @NotNull
    private String SUBCOM_CODE;//站点编号

    @NotNull
    private int TASK_TYPE;//任务类别

    @NotNull
    private int TASK_ATTRIBUTE;//任务属性

    @NotNull
    private int READ_COUNT;//总数

    @NotNull
    private int SAVE_COUNT;//已抄数

    @NotNull
    private int CHECK_OUT_COUNT;//审核数

    @NotNull
    private int STATE;//任务状态

    @NotNull
    private int DOWNLOAD_TYPE;//下载类型

    @NotNull
    private int IS_MODIFIED;//是否修改

    @NotNull
    private int IS_UPLPADED;//是否上传

    private String EXTEND;//扩展字段

    @Generated(hash = 392437600)
    public Task(Long ID, int TASK_ID, int METER_READER, long TASK_DISPATCH_DATE,
            int BILLING_MONTH, @NotNull String BOOK_ID, @NotNull String BOOK_NAME,
            int METER_CYCLE, int READ_TIMES, int READ_DAY,
            @NotNull String SUBCOM_CODE, int TASK_TYPE, int TASK_ATTRIBUTE,
            int READ_COUNT, int SAVE_COUNT, int CHECK_OUT_COUNT, int STATE,
            int DOWNLOAD_TYPE, int IS_MODIFIED, int IS_UPLPADED, String EXTEND) {
        this.ID = ID;
        this.TASK_ID = TASK_ID;
        this.METER_READER = METER_READER;
        this.TASK_DISPATCH_DATE = TASK_DISPATCH_DATE;
        this.BILLING_MONTH = BILLING_MONTH;
        this.BOOK_ID = BOOK_ID;
        this.BOOK_NAME = BOOK_NAME;
        this.METER_CYCLE = METER_CYCLE;
        this.READ_TIMES = READ_TIMES;
        this.READ_DAY = READ_DAY;
        this.SUBCOM_CODE = SUBCOM_CODE;
        this.TASK_TYPE = TASK_TYPE;
        this.TASK_ATTRIBUTE = TASK_ATTRIBUTE;
        this.READ_COUNT = READ_COUNT;
        this.SAVE_COUNT = SAVE_COUNT;
        this.CHECK_OUT_COUNT = CHECK_OUT_COUNT;
        this.STATE = STATE;
        this.DOWNLOAD_TYPE = DOWNLOAD_TYPE;
        this.IS_MODIFIED = IS_MODIFIED;
        this.IS_UPLPADED = IS_UPLPADED;
        this.EXTEND = EXTEND;
    }

    @Generated(hash = 733837707)
    public Task() {
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

    public int getMETER_READER() {
        return this.METER_READER;
    }

    public void setMETER_READER(int METER_READER) {
        this.METER_READER = METER_READER;
    }

    public long getTASK_DISPATCH_DATE() {
        return this.TASK_DISPATCH_DATE;
    }

    public void setTASK_DISPATCH_DATE(long TASK_DISPATCH_DATE) {
        this.TASK_DISPATCH_DATE = TASK_DISPATCH_DATE;
    }

    public int getBILLING_MONTH() {
        return this.BILLING_MONTH;
    }

    public void setBILLING_MONTH(int BILLING_MONTH) {
        this.BILLING_MONTH = BILLING_MONTH;
    }

    public String getBOOK_ID() {
        return this.BOOK_ID;
    }

    public void setBOOK_ID(String BOOK_ID) {
        this.BOOK_ID = BOOK_ID;
    }

    public String getBOOK_NAME() {
        return this.BOOK_NAME;
    }

    public void setBOOK_NAME(String BOOK_NAME) {
        this.BOOK_NAME = BOOK_NAME;
    }

    public int getMETER_CYCLE() {
        return this.METER_CYCLE;
    }

    public void setMETER_CYCLE(int METER_CYCLE) {
        this.METER_CYCLE = METER_CYCLE;
    }

    public int getREAD_TIMES() {
        return this.READ_TIMES;
    }

    public void setREAD_TIMES(int READ_TIMES) {
        this.READ_TIMES = READ_TIMES;
    }

    public int getREAD_DAY() {
        return this.READ_DAY;
    }

    public void setREAD_DAY(int READ_DAY) {
        this.READ_DAY = READ_DAY;
    }

    public String getSUBCOM_CODE() {
        return this.SUBCOM_CODE;
    }

    public void setSUBCOM_CODE(String SUBCOM_CODE) {
        this.SUBCOM_CODE = SUBCOM_CODE;
    }

    public int getTASK_TYPE() {
        return this.TASK_TYPE;
    }

    public void setTASK_TYPE(int TASK_TYPE) {
        this.TASK_TYPE = TASK_TYPE;
    }

    public int getTASK_ATTRIBUTE() {
        return this.TASK_ATTRIBUTE;
    }

    public void setTASK_ATTRIBUTE(int TASK_ATTRIBUTE) {
        this.TASK_ATTRIBUTE = TASK_ATTRIBUTE;
    }

    public int getREAD_COUNT() {
        return this.READ_COUNT;
    }

    public void setREAD_COUNT(int READ_COUNT) {
        this.READ_COUNT = READ_COUNT;
    }

    public int getSAVE_COUNT() {
        return this.SAVE_COUNT;
    }

    public void setSAVE_COUNT(int SAVE_COUNT) {
        this.SAVE_COUNT = SAVE_COUNT;
    }

    public int getCHECK_OUT_COUNT() {
        return this.CHECK_OUT_COUNT;
    }

    public void setCHECK_OUT_COUNT(int CHECK_OUT_COUNT) {
        this.CHECK_OUT_COUNT = CHECK_OUT_COUNT;
    }

    public int getSTATE() {
        return this.STATE;
    }

    public void setSTATE(int STATE) {
        this.STATE = STATE;
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
