package com.sh3h.localprovider.greendaoEntity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;

/**
 * 缴费记录
 */
@Entity(nameInDb = "MR_CHARGE")
public class Charge {
    @Id(autoincrement = true)
    @Property(nameInDb = "ID")
    private Long    ID;                //ID
    @NotNull
    private int     FEE_ID;            //主键
    @NotNull
    private String  CARD_ID;           //表卡编号
    @NotNull
    private String  CARD_NAME;         //表卡名称
    @NotNull
    private String  CARD_ADDRESS;      //表卡地址
    @NotNull
    private String  MAIN_CARD_ID;      //总表编号
    @NotNull
    private String  CUSTOMER_ID;       //客户编号
    @NotNull
    private int     CUSTOMER_TYPE;     //客户类型
    @NotNull
    private String  SUBCOM_CODE;       //站点编号
    @NotNull
    private String  BOOK_ID;           //册本编号
    @NotNull
    private int     BOOK_SORT_INDEX;   //册内序号
    @NotNull
    private int     PAY_METHOD;     //收费方式
    @NotNull
    private int     PRICE_LIST_ID;     //调价号
    @NotNull
    private int     PRICE_CODE;     //用水性质
    @NotNull
    private int     PRODUCER_ID;     //厂家
    @NotNull
    private int     MODELLD;     //水表
    @NotNull
    private int     CALIBER_ID;     //水表口径
    @NotNull
    private int     CALIBER_RANGE_ID;     //量程
    @NotNull
    private int     BILLING_MONTH;     //账务年月
    @NotNull
    private int     READ_YEAR;     //抄表年
    @NotNull
    private int     READ_MONTH;     //抄表月
    @NotNull
    private int     READ_TIMES;     //抄次
    @NotNull
    private int     LAST_READ_WATER;     //上次抄见水量
    @NotNull
    private int     LAST_READING;     //上次抄码
    @NotNull
    private int     LAST_READ_STATE;   //上次抄表状态
    @NotNull
    private Long    LAST_READ_DATE;    //上次抄表时间
    @NotNull
    private int     READING;     //本次抄码
    @NotNull
    private int     READ_STATE;   //抄表状态
    @NotNull
    private String  READ_STATE_CN;//抄表状态中文
    @NotNull
    private Long    READ_DATE;//本次抄表时间
    @NotNull
    private int     METER_READER;     //抄表员
    @NotNull
    private int     RECORD_ENTRY_USER;     //录入员
    @NotNull
    private int     NEXT_READ_DATE;     //下次抄表日期
    @NotNull
    private int     ACC_WATER;   //开账水量
    @NotNull
    private double  LAST_BALANCE;   //上次余
    @NotNull
    private double  BALANCE;   //本次余
    @NotNull
    private double  ACC_MONEY;   //账单金额
    @NotNull
    private double  ACC_CHECK_MONEY;  //开账金额
    @NotNull
    private Long    CHECK_TIME;   //开账时间
    @NotNull
    private int     CHECK_DATE;     //开账日期
    @NotNull
    private int     CHECK_USER;   //开账员
    @NotNull
    private double  PI_1MONEY;   //用水费
    @NotNull
    private double  PI_2MONEY;   //排水费
    @NotNull
    private double  PI_3MONEY;   //垃圾费
    @NotNull
    private double  PI_4MONEY;   //水资源费
    @NotNull
    private double  PI_5MONEY;   //城市附加费
    @NotNull
    private double  PI_6MONEY;   //其他费用
    @NotNull
    private Long    CHARGE_LIMIT_DATE;//缴费期限
    @NotNull
    private double  DUELATE_FEE;   //应收违约金
    @NotNull
    private double  DELATE_FEE;   //减免违约金
    @NotNull
    private double  ACTUALLY_LATE_FEE;//实收违约金
    @NotNull
    private Long    PAY_TIME;     //收费时间
    @NotNull
    private int     PAY_DATE;     //收费日期
    @NotNull
    private int     CASHIER;   //收费员
    @NotNull
    private int     PWID;     //收费点
    @NotNull
    private int     PST_METHOD;   //收费途径
    @NotNull
    private int     PAY_STATE;     //收费标识
    @NotNull
    private int     PSUB_TOTALLD;   //收费小计编号
    @NotNull
    private int     P_TOTALLD;   //收费汇总编号
    @NotNull
    private Long    CHARGE_TIME;     //销帐时间
    @NotNull
    private int     PRINT_TIMES;   //打印次数
    @NotNull
    private Long    PRINT_DATE;     //打印时间
    @NotNull
    private String  INVOICE_CODE;   //发票代码
    @NotNull
    private int     INVOICE_YEAR;   //打印次数
    @NotNull
    private String  INVOICE_NUMBER;   //发票代码
    @NotNull
    private int     BANK_BATCH_FLAG;   //送盘编号
    @NotNull
    private int     BANK_BATCH_ID;   //送盘批号
    @NotNull
    private Long    BANK_SEND_TIME;  //打印时间
    @NotNull
    private int     LAST_FEE_ID;   //上次水费编号
    @NotNull
    private int     ORIGINAL_FEE_ID;   //原始feeid
    @NotNull
    private int     ADJUST_ID;   //账务处理记录id
    @NotNull
    private int     ADJUST_TYPE;   //账务处理类型
    @NotNull
    private int     FEE_STATE;   //记录状态
    @NotNull
    private int     PROCESS_STATE;   //流程状态
    @NotNull
    private boolean IS_VAT_USER;    //是否增值税用户
    @NotNull
    private int     PERSON_COUNT;   //流程状态
    @Generated(hash = 1295820336)
    public Charge(Long ID, int FEE_ID, @NotNull String CARD_ID,
            @NotNull String CARD_NAME, @NotNull String CARD_ADDRESS,
            @NotNull String MAIN_CARD_ID, @NotNull String CUSTOMER_ID,
            int CUSTOMER_TYPE, @NotNull String SUBCOM_CODE,
            @NotNull String BOOK_ID, int BOOK_SORT_INDEX, int PAY_METHOD,
            int PRICE_LIST_ID, int PRICE_CODE, int PRODUCER_ID, int MODELLD,
            int CALIBER_ID, int CALIBER_RANGE_ID, int BILLING_MONTH, int READ_YEAR,
            int READ_MONTH, int READ_TIMES, int LAST_READ_WATER, int LAST_READING,
            int LAST_READ_STATE, @NotNull Long LAST_READ_DATE, int READING,
            int READ_STATE, @NotNull String READ_STATE_CN, @NotNull Long READ_DATE,
            int METER_READER, int RECORD_ENTRY_USER, int NEXT_READ_DATE,
            int ACC_WATER, double LAST_BALANCE, double BALANCE, double ACC_MONEY,
            double ACC_CHECK_MONEY, @NotNull Long CHECK_TIME, int CHECK_DATE,
            int CHECK_USER, double PI_1MONEY, double PI_2MONEY, double PI_3MONEY,
            double PI_4MONEY, double PI_5MONEY, double PI_6MONEY,
            @NotNull Long CHARGE_LIMIT_DATE, double DUELATE_FEE, double DELATE_FEE,
            double ACTUALLY_LATE_FEE, @NotNull Long PAY_TIME, int PAY_DATE,
            int CASHIER, int PWID, int PST_METHOD, int PAY_STATE, int PSUB_TOTALLD,
            int P_TOTALLD, @NotNull Long CHARGE_TIME, int PRINT_TIMES,
            @NotNull Long PRINT_DATE, @NotNull String INVOICE_CODE,
            int INVOICE_YEAR, @NotNull String INVOICE_NUMBER, int BANK_BATCH_FLAG,
            int BANK_BATCH_ID, @NotNull Long BANK_SEND_TIME, int LAST_FEE_ID,
            int ORIGINAL_FEE_ID, int ADJUST_ID, int ADJUST_TYPE, int FEE_STATE,
            int PROCESS_STATE, boolean IS_VAT_USER, int PERSON_COUNT) {
        this.ID = ID;
        this.FEE_ID = FEE_ID;
        this.CARD_ID = CARD_ID;
        this.CARD_NAME = CARD_NAME;
        this.CARD_ADDRESS = CARD_ADDRESS;
        this.MAIN_CARD_ID = MAIN_CARD_ID;
        this.CUSTOMER_ID = CUSTOMER_ID;
        this.CUSTOMER_TYPE = CUSTOMER_TYPE;
        this.SUBCOM_CODE = SUBCOM_CODE;
        this.BOOK_ID = BOOK_ID;
        this.BOOK_SORT_INDEX = BOOK_SORT_INDEX;
        this.PAY_METHOD = PAY_METHOD;
        this.PRICE_LIST_ID = PRICE_LIST_ID;
        this.PRICE_CODE = PRICE_CODE;
        this.PRODUCER_ID = PRODUCER_ID;
        this.MODELLD = MODELLD;
        this.CALIBER_ID = CALIBER_ID;
        this.CALIBER_RANGE_ID = CALIBER_RANGE_ID;
        this.BILLING_MONTH = BILLING_MONTH;
        this.READ_YEAR = READ_YEAR;
        this.READ_MONTH = READ_MONTH;
        this.READ_TIMES = READ_TIMES;
        this.LAST_READ_WATER = LAST_READ_WATER;
        this.LAST_READING = LAST_READING;
        this.LAST_READ_STATE = LAST_READ_STATE;
        this.LAST_READ_DATE = LAST_READ_DATE;
        this.READING = READING;
        this.READ_STATE = READ_STATE;
        this.READ_STATE_CN = READ_STATE_CN;
        this.READ_DATE = READ_DATE;
        this.METER_READER = METER_READER;
        this.RECORD_ENTRY_USER = RECORD_ENTRY_USER;
        this.NEXT_READ_DATE = NEXT_READ_DATE;
        this.ACC_WATER = ACC_WATER;
        this.LAST_BALANCE = LAST_BALANCE;
        this.BALANCE = BALANCE;
        this.ACC_MONEY = ACC_MONEY;
        this.ACC_CHECK_MONEY = ACC_CHECK_MONEY;
        this.CHECK_TIME = CHECK_TIME;
        this.CHECK_DATE = CHECK_DATE;
        this.CHECK_USER = CHECK_USER;
        this.PI_1MONEY = PI_1MONEY;
        this.PI_2MONEY = PI_2MONEY;
        this.PI_3MONEY = PI_3MONEY;
        this.PI_4MONEY = PI_4MONEY;
        this.PI_5MONEY = PI_5MONEY;
        this.PI_6MONEY = PI_6MONEY;
        this.CHARGE_LIMIT_DATE = CHARGE_LIMIT_DATE;
        this.DUELATE_FEE = DUELATE_FEE;
        this.DELATE_FEE = DELATE_FEE;
        this.ACTUALLY_LATE_FEE = ACTUALLY_LATE_FEE;
        this.PAY_TIME = PAY_TIME;
        this.PAY_DATE = PAY_DATE;
        this.CASHIER = CASHIER;
        this.PWID = PWID;
        this.PST_METHOD = PST_METHOD;
        this.PAY_STATE = PAY_STATE;
        this.PSUB_TOTALLD = PSUB_TOTALLD;
        this.P_TOTALLD = P_TOTALLD;
        this.CHARGE_TIME = CHARGE_TIME;
        this.PRINT_TIMES = PRINT_TIMES;
        this.PRINT_DATE = PRINT_DATE;
        this.INVOICE_CODE = INVOICE_CODE;
        this.INVOICE_YEAR = INVOICE_YEAR;
        this.INVOICE_NUMBER = INVOICE_NUMBER;
        this.BANK_BATCH_FLAG = BANK_BATCH_FLAG;
        this.BANK_BATCH_ID = BANK_BATCH_ID;
        this.BANK_SEND_TIME = BANK_SEND_TIME;
        this.LAST_FEE_ID = LAST_FEE_ID;
        this.ORIGINAL_FEE_ID = ORIGINAL_FEE_ID;
        this.ADJUST_ID = ADJUST_ID;
        this.ADJUST_TYPE = ADJUST_TYPE;
        this.FEE_STATE = FEE_STATE;
        this.PROCESS_STATE = PROCESS_STATE;
        this.IS_VAT_USER = IS_VAT_USER;
        this.PERSON_COUNT = PERSON_COUNT;
    }
    @Generated(hash = 1698010988)
    public Charge() {
    }
    public Long getID() {
        return this.ID;
    }
    public void setID(Long ID) {
        this.ID = ID;
    }
    public int getFEE_ID() {
        return this.FEE_ID;
    }
    public void setFEE_ID(int FEE_ID) {
        this.FEE_ID = FEE_ID;
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
    public String getMAIN_CARD_ID() {
        return this.MAIN_CARD_ID;
    }
    public void setMAIN_CARD_ID(String MAIN_CARD_ID) {
        this.MAIN_CARD_ID = MAIN_CARD_ID;
    }
    public String getCUSTOMER_ID() {
        return this.CUSTOMER_ID;
    }
    public void setCUSTOMER_ID(String CUSTOMER_ID) {
        this.CUSTOMER_ID = CUSTOMER_ID;
    }
    public int getCUSTOMER_TYPE() {
        return this.CUSTOMER_TYPE;
    }
    public void setCUSTOMER_TYPE(int CUSTOMER_TYPE) {
        this.CUSTOMER_TYPE = CUSTOMER_TYPE;
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
    public int getPAY_METHOD() {
        return this.PAY_METHOD;
    }
    public void setPAY_METHOD(int PAY_METHOD) {
        this.PAY_METHOD = PAY_METHOD;
    }
    public int getPRICE_LIST_ID() {
        return this.PRICE_LIST_ID;
    }
    public void setPRICE_LIST_ID(int PRICE_LIST_ID) {
        this.PRICE_LIST_ID = PRICE_LIST_ID;
    }
    public int getPRICE_CODE() {
        return this.PRICE_CODE;
    }
    public void setPRICE_CODE(int PRICE_CODE) {
        this.PRICE_CODE = PRICE_CODE;
    }
    public int getPRODUCER_ID() {
        return this.PRODUCER_ID;
    }
    public void setPRODUCER_ID(int PRODUCER_ID) {
        this.PRODUCER_ID = PRODUCER_ID;
    }
    public int getMODELLD() {
        return this.MODELLD;
    }
    public void setMODELLD(int MODELLD) {
        this.MODELLD = MODELLD;
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
    public int getREADING() {
        return this.READING;
    }
    public void setREADING(int READING) {
        this.READING = READING;
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
    public Long getREAD_DATE() {
        return this.READ_DATE;
    }
    public void setREAD_DATE(Long READ_DATE) {
        this.READ_DATE = READ_DATE;
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
    public int getNEXT_READ_DATE() {
        return this.NEXT_READ_DATE;
    }
    public void setNEXT_READ_DATE(int NEXT_READ_DATE) {
        this.NEXT_READ_DATE = NEXT_READ_DATE;
    }
    public int getACC_WATER() {
        return this.ACC_WATER;
    }
    public void setACC_WATER(int ACC_WATER) {
        this.ACC_WATER = ACC_WATER;
    }
    public double getLAST_BALANCE() {
        return this.LAST_BALANCE;
    }
    public void setLAST_BALANCE(double LAST_BALANCE) {
        this.LAST_BALANCE = LAST_BALANCE;
    }
    public double getBALANCE() {
        return this.BALANCE;
    }
    public void setBALANCE(double BALANCE) {
        this.BALANCE = BALANCE;
    }
    public double getACC_MONEY() {
        return this.ACC_MONEY;
    }
    public void setACC_MONEY(double ACC_MONEY) {
        this.ACC_MONEY = ACC_MONEY;
    }
    public double getACC_CHECK_MONEY() {
        return this.ACC_CHECK_MONEY;
    }
    public void setACC_CHECK_MONEY(double ACC_CHECK_MONEY) {
        this.ACC_CHECK_MONEY = ACC_CHECK_MONEY;
    }
    public Long getCHECK_TIME() {
        return this.CHECK_TIME;
    }
    public void setCHECK_TIME(Long CHECK_TIME) {
        this.CHECK_TIME = CHECK_TIME;
    }
    public int getCHECK_DATE() {
        return this.CHECK_DATE;
    }
    public void setCHECK_DATE(int CHECK_DATE) {
        this.CHECK_DATE = CHECK_DATE;
    }
    public int getCHECK_USER() {
        return this.CHECK_USER;
    }
    public void setCHECK_USER(int CHECK_USER) {
        this.CHECK_USER = CHECK_USER;
    }
    public double getPI_1MONEY() {
        return this.PI_1MONEY;
    }
    public void setPI_1MONEY(double PI_1MONEY) {
        this.PI_1MONEY = PI_1MONEY;
    }
    public double getPI_2MONEY() {
        return this.PI_2MONEY;
    }
    public void setPI_2MONEY(double PI_2MONEY) {
        this.PI_2MONEY = PI_2MONEY;
    }
    public double getPI_3MONEY() {
        return this.PI_3MONEY;
    }
    public void setPI_3MONEY(double PI_3MONEY) {
        this.PI_3MONEY = PI_3MONEY;
    }
    public double getPI_4MONEY() {
        return this.PI_4MONEY;
    }
    public void setPI_4MONEY(double PI_4MONEY) {
        this.PI_4MONEY = PI_4MONEY;
    }
    public double getPI_5MONEY() {
        return this.PI_5MONEY;
    }
    public void setPI_5MONEY(double PI_5MONEY) {
        this.PI_5MONEY = PI_5MONEY;
    }
    public double getPI_6MONEY() {
        return this.PI_6MONEY;
    }
    public void setPI_6MONEY(double PI_6MONEY) {
        this.PI_6MONEY = PI_6MONEY;
    }
    public Long getCHARGE_LIMIT_DATE() {
        return this.CHARGE_LIMIT_DATE;
    }
    public void setCHARGE_LIMIT_DATE(Long CHARGE_LIMIT_DATE) {
        this.CHARGE_LIMIT_DATE = CHARGE_LIMIT_DATE;
    }
    public double getDUELATE_FEE() {
        return this.DUELATE_FEE;
    }
    public void setDUELATE_FEE(double DUELATE_FEE) {
        this.DUELATE_FEE = DUELATE_FEE;
    }
    public double getDELATE_FEE() {
        return this.DELATE_FEE;
    }
    public void setDELATE_FEE(double DELATE_FEE) {
        this.DELATE_FEE = DELATE_FEE;
    }
    public double getACTUALLY_LATE_FEE() {
        return this.ACTUALLY_LATE_FEE;
    }
    public void setACTUALLY_LATE_FEE(double ACTUALLY_LATE_FEE) {
        this.ACTUALLY_LATE_FEE = ACTUALLY_LATE_FEE;
    }
    public Long getPAY_TIME() {
        return this.PAY_TIME;
    }
    public void setPAY_TIME(Long PAY_TIME) {
        this.PAY_TIME = PAY_TIME;
    }
    public int getPAY_DATE() {
        return this.PAY_DATE;
    }
    public void setPAY_DATE(int PAY_DATE) {
        this.PAY_DATE = PAY_DATE;
    }
    public int getCASHIER() {
        return this.CASHIER;
    }
    public void setCASHIER(int CASHIER) {
        this.CASHIER = CASHIER;
    }
    public int getPWID() {
        return this.PWID;
    }
    public void setPWID(int PWID) {
        this.PWID = PWID;
    }
    public int getPST_METHOD() {
        return this.PST_METHOD;
    }
    public void setPST_METHOD(int PST_METHOD) {
        this.PST_METHOD = PST_METHOD;
    }
    public int getPAY_STATE() {
        return this.PAY_STATE;
    }
    public void setPAY_STATE(int PAY_STATE) {
        this.PAY_STATE = PAY_STATE;
    }
    public int getPSUB_TOTALLD() {
        return this.PSUB_TOTALLD;
    }
    public void setPSUB_TOTALLD(int PSUB_TOTALLD) {
        this.PSUB_TOTALLD = PSUB_TOTALLD;
    }
    public int getP_TOTALLD() {
        return this.P_TOTALLD;
    }
    public void setP_TOTALLD(int P_TOTALLD) {
        this.P_TOTALLD = P_TOTALLD;
    }
    public Long getCHARGE_TIME() {
        return this.CHARGE_TIME;
    }
    public void setCHARGE_TIME(Long CHARGE_TIME) {
        this.CHARGE_TIME = CHARGE_TIME;
    }
    public int getPRINT_TIMES() {
        return this.PRINT_TIMES;
    }
    public void setPRINT_TIMES(int PRINT_TIMES) {
        this.PRINT_TIMES = PRINT_TIMES;
    }
    public Long getPRINT_DATE() {
        return this.PRINT_DATE;
    }
    public void setPRINT_DATE(Long PRINT_DATE) {
        this.PRINT_DATE = PRINT_DATE;
    }
    public String getINVOICE_CODE() {
        return this.INVOICE_CODE;
    }
    public void setINVOICE_CODE(String INVOICE_CODE) {
        this.INVOICE_CODE = INVOICE_CODE;
    }
    public int getINVOICE_YEAR() {
        return this.INVOICE_YEAR;
    }
    public void setINVOICE_YEAR(int INVOICE_YEAR) {
        this.INVOICE_YEAR = INVOICE_YEAR;
    }
    public String getINVOICE_NUMBER() {
        return this.INVOICE_NUMBER;
    }
    public void setINVOICE_NUMBER(String INVOICE_NUMBER) {
        this.INVOICE_NUMBER = INVOICE_NUMBER;
    }
    public int getBANK_BATCH_FLAG() {
        return this.BANK_BATCH_FLAG;
    }
    public void setBANK_BATCH_FLAG(int BANK_BATCH_FLAG) {
        this.BANK_BATCH_FLAG = BANK_BATCH_FLAG;
    }
    public int getBANK_BATCH_ID() {
        return this.BANK_BATCH_ID;
    }
    public void setBANK_BATCH_ID(int BANK_BATCH_ID) {
        this.BANK_BATCH_ID = BANK_BATCH_ID;
    }
    public Long getBANK_SEND_TIME() {
        return this.BANK_SEND_TIME;
    }
    public void setBANK_SEND_TIME(Long BANK_SEND_TIME) {
        this.BANK_SEND_TIME = BANK_SEND_TIME;
    }
    public int getLAST_FEE_ID() {
        return this.LAST_FEE_ID;
    }
    public void setLAST_FEE_ID(int LAST_FEE_ID) {
        this.LAST_FEE_ID = LAST_FEE_ID;
    }
    public int getORIGINAL_FEE_ID() {
        return this.ORIGINAL_FEE_ID;
    }
    public void setORIGINAL_FEE_ID(int ORIGINAL_FEE_ID) {
        this.ORIGINAL_FEE_ID = ORIGINAL_FEE_ID;
    }
    public int getADJUST_ID() {
        return this.ADJUST_ID;
    }
    public void setADJUST_ID(int ADJUST_ID) {
        this.ADJUST_ID = ADJUST_ID;
    }
    public int getADJUST_TYPE() {
        return this.ADJUST_TYPE;
    }
    public void setADJUST_TYPE(int ADJUST_TYPE) {
        this.ADJUST_TYPE = ADJUST_TYPE;
    }
    public int getFEE_STATE() {
        return this.FEE_STATE;
    }
    public void setFEE_STATE(int FEE_STATE) {
        this.FEE_STATE = FEE_STATE;
    }
    public int getPROCESS_STATE() {
        return this.PROCESS_STATE;
    }
    public void setPROCESS_STATE(int PROCESS_STATE) {
        this.PROCESS_STATE = PROCESS_STATE;
    }
    public boolean getIS_VAT_USER() {
        return this.IS_VAT_USER;
    }
    public void setIS_VAT_USER(boolean IS_VAT_USER) {
        this.IS_VAT_USER = IS_VAT_USER;
    }
    public int getPERSON_COUNT() {
        return this.PERSON_COUNT;
    }
    public void setPERSON_COUNT(int PERSON_COUNT) {
        this.PERSON_COUNT = PERSON_COUNT;
    }
}
