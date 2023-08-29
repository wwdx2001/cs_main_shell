package com.sh3h.localprovider.greendaoEntity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by tang on 2017/4/14.
 * 表务记录
 */
@Entity(nameInDb = "MR_METERWORKER")
public class MeterWorker {
    @Id(autoincrement = true)
    @Property(nameInDb = "ID")
    private Long ID;           //ID
    @NotNull
    private int WORK_ID;      //工单编号
    @NotNull
    private String CARD_ID;    //表卡编号
    @NotNull
    private int WORK_TYPE;      //工单类型
    @NotNull
    private int REASON;      //原因
    @NotNull
    private Long COMPLATE_DATE;    //完成时间
    @NotNull
    private int OLD_TERMINATE_READING;      //旧表底码
    @NotNull
    private int NEW_INITIATE_READING;      //新表起码
    @NotNull
    private int OPRATOR;      //操作人
    @NotNull
    private Long END_DATE;    //截止时间
    @NotNull
    private int WORK_STATE;      //工单状态
    @NotNull
    private int CREATOR;      //创建人
    @NotNull
    private Long CREAT_DATE;    //创建时间
    @NotNull
    private int RECEIPTOR;      //回填人
    @NotNull
    private Long RECEIPTOR_DATE; //回填时间
    @NotNull
    private String OLD_SEAL_NUMBER;    //旧表钢印号

    private String NEW_SEAL_NUMBER;    //新表钢印号
    @NotNull
    private String OLD_BAR_CODE;    //旧表条形码

    private String NEW_BAR_CODE;    //新表条形码
    @NotNull
    private int OLD_PRODUCER_ID;      //旧表厂家
    @NotNull
    private int OLD_MODEL_ID;      //旧表水表
    @NotNull
    private int OLD_CALIBER_ID;      //旧表口径
    @NotNull
    private int OLD_CALIBER_RANGE_ID;      //旧表量程
    @NotNull
    private int NEW_PRODUCER_ID;      //新表厂家
    @NotNull
    private int NEW_MODEL_ID;      //新表水表
    @NotNull
    private int NEW_CALIBER_ID;      //新表口径
    @NotNull
    private int NEW_CALIBER_RANGE_ID; //新表量程
    @Generated(hash = 664753698)
    public MeterWorker(Long ID, int WORK_ID, @NotNull String CARD_ID,
            int WORK_TYPE, int REASON, @NotNull Long COMPLATE_DATE,
            int OLD_TERMINATE_READING, int NEW_INITIATE_READING, int OPRATOR,
            @NotNull Long END_DATE, int WORK_STATE, int CREATOR,
            @NotNull Long CREAT_DATE, int RECEIPTOR, @NotNull Long RECEIPTOR_DATE,
            @NotNull String OLD_SEAL_NUMBER, String NEW_SEAL_NUMBER,
            @NotNull String OLD_BAR_CODE, String NEW_BAR_CODE, int OLD_PRODUCER_ID,
            int OLD_MODEL_ID, int OLD_CALIBER_ID, int OLD_CALIBER_RANGE_ID,
            int NEW_PRODUCER_ID, int NEW_MODEL_ID, int NEW_CALIBER_ID,
            int NEW_CALIBER_RANGE_ID) {
        this.ID = ID;
        this.WORK_ID = WORK_ID;
        this.CARD_ID = CARD_ID;
        this.WORK_TYPE = WORK_TYPE;
        this.REASON = REASON;
        this.COMPLATE_DATE = COMPLATE_DATE;
        this.OLD_TERMINATE_READING = OLD_TERMINATE_READING;
        this.NEW_INITIATE_READING = NEW_INITIATE_READING;
        this.OPRATOR = OPRATOR;
        this.END_DATE = END_DATE;
        this.WORK_STATE = WORK_STATE;
        this.CREATOR = CREATOR;
        this.CREAT_DATE = CREAT_DATE;
        this.RECEIPTOR = RECEIPTOR;
        this.RECEIPTOR_DATE = RECEIPTOR_DATE;
        this.OLD_SEAL_NUMBER = OLD_SEAL_NUMBER;
        this.NEW_SEAL_NUMBER = NEW_SEAL_NUMBER;
        this.OLD_BAR_CODE = OLD_BAR_CODE;
        this.NEW_BAR_CODE = NEW_BAR_CODE;
        this.OLD_PRODUCER_ID = OLD_PRODUCER_ID;
        this.OLD_MODEL_ID = OLD_MODEL_ID;
        this.OLD_CALIBER_ID = OLD_CALIBER_ID;
        this.OLD_CALIBER_RANGE_ID = OLD_CALIBER_RANGE_ID;
        this.NEW_PRODUCER_ID = NEW_PRODUCER_ID;
        this.NEW_MODEL_ID = NEW_MODEL_ID;
        this.NEW_CALIBER_ID = NEW_CALIBER_ID;
        this.NEW_CALIBER_RANGE_ID = NEW_CALIBER_RANGE_ID;
    }
    @Generated(hash = 194048356)
    public MeterWorker() {
    }
    public Long getID() {
        return this.ID;
    }
    public void setID(Long ID) {
        this.ID = ID;
    }
    public int getWORK_ID() {
        return this.WORK_ID;
    }
    public void setWORK_ID(int WORK_ID) {
        this.WORK_ID = WORK_ID;
    }
    public String getCARD_ID() {
        return this.CARD_ID;
    }
    public void setCARD_ID(String CARD_ID) {
        this.CARD_ID = CARD_ID;
    }
    public int getWORK_TYPE() {
        return this.WORK_TYPE;
    }
    public void setWORK_TYPE(int WORK_TYPE) {
        this.WORK_TYPE = WORK_TYPE;
    }
    public int getREASON() {
        return this.REASON;
    }
    public void setREASON(int REASON) {
        this.REASON = REASON;
    }
    public Long getCOMPLATE_DATE() {
        return this.COMPLATE_DATE;
    }
    public void setCOMPLATE_DATE(Long COMPLATE_DATE) {
        this.COMPLATE_DATE = COMPLATE_DATE;
    }
    public int getOLD_TERMINATE_READING() {
        return this.OLD_TERMINATE_READING;
    }
    public void setOLD_TERMINATE_READING(int OLD_TERMINATE_READING) {
        this.OLD_TERMINATE_READING = OLD_TERMINATE_READING;
    }
    public int getNEW_INITIATE_READING() {
        return this.NEW_INITIATE_READING;
    }
    public void setNEW_INITIATE_READING(int NEW_INITIATE_READING) {
        this.NEW_INITIATE_READING = NEW_INITIATE_READING;
    }
    public int getOPRATOR() {
        return this.OPRATOR;
    }
    public void setOPRATOR(int OPRATOR) {
        this.OPRATOR = OPRATOR;
    }
    public Long getEND_DATE() {
        return this.END_DATE;
    }
    public void setEND_DATE(Long END_DATE) {
        this.END_DATE = END_DATE;
    }
    public int getWORK_STATE() {
        return this.WORK_STATE;
    }
    public void setWORK_STATE(int WORK_STATE) {
        this.WORK_STATE = WORK_STATE;
    }
    public int getCREATOR() {
        return this.CREATOR;
    }
    public void setCREATOR(int CREATOR) {
        this.CREATOR = CREATOR;
    }
    public Long getCREAT_DATE() {
        return this.CREAT_DATE;
    }
    public void setCREAT_DATE(Long CREAT_DATE) {
        this.CREAT_DATE = CREAT_DATE;
    }
    public int getRECEIPTOR() {
        return this.RECEIPTOR;
    }
    public void setRECEIPTOR(int RECEIPTOR) {
        this.RECEIPTOR = RECEIPTOR;
    }
    public Long getRECEIPTOR_DATE() {
        return this.RECEIPTOR_DATE;
    }
    public void setRECEIPTOR_DATE(Long RECEIPTOR_DATE) {
        this.RECEIPTOR_DATE = RECEIPTOR_DATE;
    }
    public String getOLD_SEAL_NUMBER() {
        return this.OLD_SEAL_NUMBER;
    }
    public void setOLD_SEAL_NUMBER(String OLD_SEAL_NUMBER) {
        this.OLD_SEAL_NUMBER = OLD_SEAL_NUMBER;
    }
    public String getNEW_SEAL_NUMBER() {
        return this.NEW_SEAL_NUMBER;
    }
    public void setNEW_SEAL_NUMBER(String NEW_SEAL_NUMBER) {
        this.NEW_SEAL_NUMBER = NEW_SEAL_NUMBER;
    }
    public String getOLD_BAR_CODE() {
        return this.OLD_BAR_CODE;
    }
    public void setOLD_BAR_CODE(String OLD_BAR_CODE) {
        this.OLD_BAR_CODE = OLD_BAR_CODE;
    }
    public String getNEW_BAR_CODE() {
        return this.NEW_BAR_CODE;
    }
    public void setNEW_BAR_CODE(String NEW_BAR_CODE) {
        this.NEW_BAR_CODE = NEW_BAR_CODE;
    }
    public int getOLD_PRODUCER_ID() {
        return this.OLD_PRODUCER_ID;
    }
    public void setOLD_PRODUCER_ID(int OLD_PRODUCER_ID) {
        this.OLD_PRODUCER_ID = OLD_PRODUCER_ID;
    }
    public int getOLD_MODEL_ID() {
        return this.OLD_MODEL_ID;
    }
    public void setOLD_MODEL_ID(int OLD_MODEL_ID) {
        this.OLD_MODEL_ID = OLD_MODEL_ID;
    }
    public int getOLD_CALIBER_ID() {
        return this.OLD_CALIBER_ID;
    }
    public void setOLD_CALIBER_ID(int OLD_CALIBER_ID) {
        this.OLD_CALIBER_ID = OLD_CALIBER_ID;
    }
    public int getOLD_CALIBER_RANGE_ID() {
        return this.OLD_CALIBER_RANGE_ID;
    }
    public void setOLD_CALIBER_RANGE_ID(int OLD_CALIBER_RANGE_ID) {
        this.OLD_CALIBER_RANGE_ID = OLD_CALIBER_RANGE_ID;
    }
    public int getNEW_PRODUCER_ID() {
        return this.NEW_PRODUCER_ID;
    }
    public void setNEW_PRODUCER_ID(int NEW_PRODUCER_ID) {
        this.NEW_PRODUCER_ID = NEW_PRODUCER_ID;
    }
    public int getNEW_MODEL_ID() {
        return this.NEW_MODEL_ID;
    }
    public void setNEW_MODEL_ID(int NEW_MODEL_ID) {
        this.NEW_MODEL_ID = NEW_MODEL_ID;
    }
    public int getNEW_CALIBER_ID() {
        return this.NEW_CALIBER_ID;
    }
    public void setNEW_CALIBER_ID(int NEW_CALIBER_ID) {
        this.NEW_CALIBER_ID = NEW_CALIBER_ID;
    }
    public int getNEW_CALIBER_RANGE_ID() {
        return this.NEW_CALIBER_RANGE_ID;
    }
    public void setNEW_CALIBER_RANGE_ID(int NEW_CALIBER_RANGE_ID) {
        this.NEW_CALIBER_RANGE_ID = NEW_CALIBER_RANGE_ID;
    }


}
