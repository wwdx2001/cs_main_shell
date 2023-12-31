package com.sh3h.localprovider.greendaoDao;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import com.sh3h.localprovider.greendaoEntity.ReCord;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "MR_RECORD".
*/
public class ReCordDao extends AbstractDao<ReCord, Long> {

    public static final String TABLENAME = "MR_RECORD";

    /**
     * Properties of entity ReCord.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property ID = new Property(0, Long.class, "ID", true, "ID");
        public final static Property TASK_ID = new Property(1, int.class, "TASK_ID", false, "TASK__ID");
        public final static Property RECORD_ID = new Property(2, int.class, "RECORD_ID", false, "RECORD__ID");
        public final static Property CARD_ID = new Property(3, String.class, "CARD_ID", false, "CARD__ID");
        public final static Property SUBCOM_CODE = new Property(4, String.class, "SUBCOM_CODE", false, "SUBCOM__CODE");
        public final static Property BOOK_ID = new Property(5, String.class, "BOOK_ID", false, "BOOK__ID");
        public final static Property BOOK_SORT_INDEX = new Property(6, int.class, "BOOK_SORT_INDEX", false, "BOOK__SORT__INDEX");
        public final static Property BILLING_MONTH = new Property(7, int.class, "BILLING_MONTH", false, "BILLING__MONTH");
        public final static Property READ_YEAR = new Property(8, int.class, "READ_YEAR", false, "READ__YEAR");
        public final static Property READ_MONTH = new Property(9, int.class, "READ_MONTH", false, "READ__MONTH");
        public final static Property READ_TIMES = new Property(10, int.class, "READ_TIMES", false, "READ__TIMES");
        public final static Property RECORD_ENTRY_TIME = new Property(11, int.class, "RECORD_ENTRY_TIME", false, "RECORD__ENTRY__TIME");
        public final static Property METER_READER = new Property(12, int.class, "METER_READER", false, "METER__READER");
        public final static Property READING = new Property(13, int.class, "READING", false, "READING");
        public final static Property READING1 = new Property(14, int.class, "READING1", false, "READING1");
        public final static Property READ_WATER = new Property(15, int.class, "READ_WATER", false, "READ__WATER");
        public final static Property READ_DATE = new Property(16, int.class, "READ_DATE", false, "READ__DATE");
        public final static Property READ_STATE = new Property(17, int.class, "READ_STATE", false, "READ__STATE");
        public final static Property READ_STATE_TIMES = new Property(18, int.class, "READ_STATE_TIMES", false, "READ__STATE__TIMES");
        public final static Property RECORD_STATE_LGLD = new Property(19, int.class, "RECORD_STATE_LGLD", false, "RECORD__STATE__LGLD");
        public final static Property RECORD_REASON_LGLD = new Property(20, int.class, "RECORD_REASON_LGLD", false, "RECORD__REASON__LGLD");
        public final static Property RECORD_REMARK = new Property(21, String.class, "RECORD_REMARK", false, "RECORD__REMARK");
        public final static Property AVG_WATER3 = new Property(22, int.class, "AVG_WATER3", false, "AVG__WATER3");
        public final static Property AVG_WATER6 = new Property(23, int.class, "AVG_WATER6", false, "AVG__WATER6");
        public final static Property AVG_WATER12 = new Property(24, int.class, "AVG_WATER12", false, "AVG__WATER12");
        public final static Property LAST_READING = new Property(25, int.class, "LAST_READING", false, "LAST__READING");
        public final static Property LAST_READING1 = new Property(26, int.class, "LAST_READING1", false, "LAST__READING1");
        public final static Property LAST_READ_WATER = new Property(27, int.class, "LAST_READ_WATER", false, "LAST__READ__WATER");
        public final static Property LAST_READ_STATE = new Property(28, int.class, "LAST_READ_STATE", false, "LAST__READ__STATE");
        public final static Property LAST_READ_DATE = new Property(29, Long.class, "LAST_READ_DATE", false, "LAST__READ__DATE");
        public final static Property LAST_STATE_TIMES = new Property(30, int.class, "LAST_STATE_TIMES", false, "LAST__STATE__TIMES");
        public final static Property METER_TYPE = new Property(31, int.class, "METER_TYPE", false, "METER__TYPE");
        public final static Property METER_TYPE2 = new Property(32, int.class, "METER_TYPE2", false, "METER__TYPE2");
        public final static Property MAIN_CARD_ID = new Property(33, String.class, "MAIN_CARD_ID", false, "MAIN__CARD__ID");
        public final static Property TERMINATE_READING = new Property(34, int.class, "TERMINATE_READING", false, "TERMINATE__READING");
        public final static Property INITIATE_READING = new Property(35, int.class, "INITIATE_READING", false, "INITIATE__READING");
        public final static Property SEAL_NUMBER = new Property(36, String.class, "SEAL_NUMBER", false, "SEAL__NUMBER");
        public final static Property BAR_CODE = new Property(37, String.class, "BAR_CODE", false, "BAR__CODE");
        public final static Property CALIBER_ID = new Property(38, int.class, "CALIBER_ID", false, "CALIBER__ID");
        public final static Property CALIBER_RANGE_ID = new Property(39, int.class, "CALIBER_RANGE_ID", false, "CALIBER__RANGE__ID");
        public final static Property MODEL_ID = new Property(40, int.class, "MODEL_ID", false, "MODEL__ID");
        public final static Property PRODUCER_ID = new Property(41, int.class, "PRODUCER_ID", false, "PRODUCER__ID");
        public final static Property STATE = new Property(42, int.class, "STATE", false, "STATE");
        public final static Property INSTALL_DATE = new Property(43, Long.class, "INSTALL_DATE", false, "INSTALL__DATE");
        public final static Property REPLACE_DATE = new Property(44, Long.class, "REPLACE_DATE", false, "REPLACE__DATE");
        public final static Property LOCATION_LG = new Property(45, double.class, "LOCATION_LG", false, "LOCATION__LG");
        public final static Property LOCATION_LT = new Property(46, double.class, "LOCATION_LT", false, "LOCATION__LT");
        public final static Property DOWNLOAD_TYPE = new Property(47, int.class, "DOWNLOAD_TYPE", false, "DOWNLOAD__TYPE");
        public final static Property IS_UPLPADED = new Property(48, int.class, "IS_UPLPADED", false, "IS__UPLPADED");
        public final static Property EXTEND = new Property(49, String.class, "EXTEND", false, "EXTEND");
    }


    public ReCordDao(DaoConfig config) {
        super(config);
    }
    
    public ReCordDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"MR_RECORD\" (" + //
                "\"ID\" INTEGER PRIMARY KEY AUTOINCREMENT ," + // 0: ID
                "\"TASK__ID\" INTEGER NOT NULL ," + // 1: TASK_ID
                "\"RECORD__ID\" INTEGER NOT NULL ," + // 2: RECORD_ID
                "\"CARD__ID\" TEXT NOT NULL ," + // 3: CARD_ID
                "\"SUBCOM__CODE\" TEXT NOT NULL ," + // 4: SUBCOM_CODE
                "\"BOOK__ID\" TEXT NOT NULL ," + // 5: BOOK_ID
                "\"BOOK__SORT__INDEX\" INTEGER NOT NULL ," + // 6: BOOK_SORT_INDEX
                "\"BILLING__MONTH\" INTEGER NOT NULL ," + // 7: BILLING_MONTH
                "\"READ__YEAR\" INTEGER NOT NULL ," + // 8: READ_YEAR
                "\"READ__MONTH\" INTEGER NOT NULL ," + // 9: READ_MONTH
                "\"READ__TIMES\" INTEGER NOT NULL ," + // 10: READ_TIMES
                "\"RECORD__ENTRY__TIME\" INTEGER NOT NULL ," + // 11: RECORD_ENTRY_TIME
                "\"METER__READER\" INTEGER NOT NULL ," + // 12: METER_READER
                "\"READING\" INTEGER NOT NULL ," + // 13: READING
                "\"READING1\" INTEGER NOT NULL ," + // 14: READING1
                "\"READ__WATER\" INTEGER NOT NULL ," + // 15: READ_WATER
                "\"READ__DATE\" INTEGER NOT NULL ," + // 16: READ_DATE
                "\"READ__STATE\" INTEGER NOT NULL ," + // 17: READ_STATE
                "\"READ__STATE__TIMES\" INTEGER NOT NULL ," + // 18: READ_STATE_TIMES
                "\"RECORD__STATE__LGLD\" INTEGER NOT NULL ," + // 19: RECORD_STATE_LGLD
                "\"RECORD__REASON__LGLD\" INTEGER NOT NULL ," + // 20: RECORD_REASON_LGLD
                "\"RECORD__REMARK\" TEXT," + // 21: RECORD_REMARK
                "\"AVG__WATER3\" INTEGER NOT NULL ," + // 22: AVG_WATER3
                "\"AVG__WATER6\" INTEGER NOT NULL ," + // 23: AVG_WATER6
                "\"AVG__WATER12\" INTEGER NOT NULL ," + // 24: AVG_WATER12
                "\"LAST__READING\" INTEGER NOT NULL ," + // 25: LAST_READING
                "\"LAST__READING1\" INTEGER NOT NULL ," + // 26: LAST_READING1
                "\"LAST__READ__WATER\" INTEGER NOT NULL ," + // 27: LAST_READ_WATER
                "\"LAST__READ__STATE\" INTEGER NOT NULL ," + // 28: LAST_READ_STATE
                "\"LAST__READ__DATE\" INTEGER," + // 29: LAST_READ_DATE
                "\"LAST__STATE__TIMES\" INTEGER NOT NULL ," + // 30: LAST_STATE_TIMES
                "\"METER__TYPE\" INTEGER NOT NULL ," + // 31: METER_TYPE
                "\"METER__TYPE2\" INTEGER NOT NULL ," + // 32: METER_TYPE2
                "\"MAIN__CARD__ID\" TEXT NOT NULL ," + // 33: MAIN_CARD_ID
                "\"TERMINATE__READING\" INTEGER NOT NULL ," + // 34: TERMINATE_READING
                "\"INITIATE__READING\" INTEGER NOT NULL ," + // 35: INITIATE_READING
                "\"SEAL__NUMBER\" TEXT NOT NULL ," + // 36: SEAL_NUMBER
                "\"BAR__CODE\" TEXT NOT NULL ," + // 37: BAR_CODE
                "\"CALIBER__ID\" INTEGER NOT NULL ," + // 38: CALIBER_ID
                "\"CALIBER__RANGE__ID\" INTEGER NOT NULL ," + // 39: CALIBER_RANGE_ID
                "\"MODEL__ID\" INTEGER NOT NULL ," + // 40: MODEL_ID
                "\"PRODUCER__ID\" INTEGER NOT NULL ," + // 41: PRODUCER_ID
                "\"STATE\" INTEGER NOT NULL ," + // 42: STATE
                "\"INSTALL__DATE\" INTEGER," + // 43: INSTALL_DATE
                "\"REPLACE__DATE\" INTEGER," + // 44: REPLACE_DATE
                "\"LOCATION__LG\" REAL NOT NULL ," + // 45: LOCATION_LG
                "\"LOCATION__LT\" REAL NOT NULL ," + // 46: LOCATION_LT
                "\"DOWNLOAD__TYPE\" INTEGER NOT NULL ," + // 47: DOWNLOAD_TYPE
                "\"IS__UPLPADED\" INTEGER NOT NULL ," + // 48: IS_UPLPADED
                "\"EXTEND\" TEXT);"); // 49: EXTEND
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"MR_RECORD\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, ReCord entity) {
        stmt.clearBindings();
 
        Long ID = entity.getID();
        if (ID != null) {
            stmt.bindLong(1, ID);
        }
        stmt.bindLong(2, entity.getTASK_ID());
        stmt.bindLong(3, entity.getRECORD_ID());
        stmt.bindString(4, entity.getCARD_ID());
        stmt.bindString(5, entity.getSUBCOM_CODE());
        stmt.bindString(6, entity.getBOOK_ID());
        stmt.bindLong(7, entity.getBOOK_SORT_INDEX());
        stmt.bindLong(8, entity.getBILLING_MONTH());
        stmt.bindLong(9, entity.getREAD_YEAR());
        stmt.bindLong(10, entity.getREAD_MONTH());
        stmt.bindLong(11, entity.getREAD_TIMES());
        stmt.bindLong(12, entity.getRECORD_ENTRY_TIME());
        stmt.bindLong(13, entity.getMETER_READER());
        stmt.bindLong(14, entity.getREADING());
        stmt.bindLong(15, entity.getREADING1());
        stmt.bindLong(16, entity.getREAD_WATER());
        stmt.bindLong(17, entity.getREAD_DATE());
        stmt.bindLong(18, entity.getREAD_STATE());
        stmt.bindLong(19, entity.getREAD_STATE_TIMES());
        stmt.bindLong(20, entity.getRECORD_STATE_LGLD());
        stmt.bindLong(21, entity.getRECORD_REASON_LGLD());
 
        String RECORD_REMARK = entity.getRECORD_REMARK();
        if (RECORD_REMARK != null) {
            stmt.bindString(22, RECORD_REMARK);
        }
        stmt.bindLong(23, entity.getAVG_WATER3());
        stmt.bindLong(24, entity.getAVG_WATER6());
        stmt.bindLong(25, entity.getAVG_WATER12());
        stmt.bindLong(26, entity.getLAST_READING());
        stmt.bindLong(27, entity.getLAST_READING1());
        stmt.bindLong(28, entity.getLAST_READ_WATER());
        stmt.bindLong(29, entity.getLAST_READ_STATE());
 
        Long LAST_READ_DATE = entity.getLAST_READ_DATE();
        if (LAST_READ_DATE != null) {
            stmt.bindLong(30, LAST_READ_DATE);
        }
        stmt.bindLong(31, entity.getLAST_STATE_TIMES());
        stmt.bindLong(32, entity.getMETER_TYPE());
        stmt.bindLong(33, entity.getMETER_TYPE2());
        stmt.bindString(34, entity.getMAIN_CARD_ID());
        stmt.bindLong(35, entity.getTERMINATE_READING());
        stmt.bindLong(36, entity.getINITIATE_READING());
        stmt.bindString(37, entity.getSEAL_NUMBER());
        stmt.bindString(38, entity.getBAR_CODE());
        stmt.bindLong(39, entity.getCALIBER_ID());
        stmt.bindLong(40, entity.getCALIBER_RANGE_ID());
        stmt.bindLong(41, entity.getMODEL_ID());
        stmt.bindLong(42, entity.getPRODUCER_ID());
        stmt.bindLong(43, entity.getSTATE());
 
        Long INSTALL_DATE = entity.getINSTALL_DATE();
        if (INSTALL_DATE != null) {
            stmt.bindLong(44, INSTALL_DATE);
        }
 
        Long REPLACE_DATE = entity.getREPLACE_DATE();
        if (REPLACE_DATE != null) {
            stmt.bindLong(45, REPLACE_DATE);
        }
        stmt.bindDouble(46, entity.getLOCATION_LG());
        stmt.bindDouble(47, entity.getLOCATION_LT());
        stmt.bindLong(48, entity.getDOWNLOAD_TYPE());
        stmt.bindLong(49, entity.getIS_UPLPADED());
 
        String EXTEND = entity.getEXTEND();
        if (EXTEND != null) {
            stmt.bindString(50, EXTEND);
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, ReCord entity) {
        stmt.clearBindings();
 
        Long ID = entity.getID();
        if (ID != null) {
            stmt.bindLong(1, ID);
        }
        stmt.bindLong(2, entity.getTASK_ID());
        stmt.bindLong(3, entity.getRECORD_ID());
        stmt.bindString(4, entity.getCARD_ID());
        stmt.bindString(5, entity.getSUBCOM_CODE());
        stmt.bindString(6, entity.getBOOK_ID());
        stmt.bindLong(7, entity.getBOOK_SORT_INDEX());
        stmt.bindLong(8, entity.getBILLING_MONTH());
        stmt.bindLong(9, entity.getREAD_YEAR());
        stmt.bindLong(10, entity.getREAD_MONTH());
        stmt.bindLong(11, entity.getREAD_TIMES());
        stmt.bindLong(12, entity.getRECORD_ENTRY_TIME());
        stmt.bindLong(13, entity.getMETER_READER());
        stmt.bindLong(14, entity.getREADING());
        stmt.bindLong(15, entity.getREADING1());
        stmt.bindLong(16, entity.getREAD_WATER());
        stmt.bindLong(17, entity.getREAD_DATE());
        stmt.bindLong(18, entity.getREAD_STATE());
        stmt.bindLong(19, entity.getREAD_STATE_TIMES());
        stmt.bindLong(20, entity.getRECORD_STATE_LGLD());
        stmt.bindLong(21, entity.getRECORD_REASON_LGLD());
 
        String RECORD_REMARK = entity.getRECORD_REMARK();
        if (RECORD_REMARK != null) {
            stmt.bindString(22, RECORD_REMARK);
        }
        stmt.bindLong(23, entity.getAVG_WATER3());
        stmt.bindLong(24, entity.getAVG_WATER6());
        stmt.bindLong(25, entity.getAVG_WATER12());
        stmt.bindLong(26, entity.getLAST_READING());
        stmt.bindLong(27, entity.getLAST_READING1());
        stmt.bindLong(28, entity.getLAST_READ_WATER());
        stmt.bindLong(29, entity.getLAST_READ_STATE());
 
        Long LAST_READ_DATE = entity.getLAST_READ_DATE();
        if (LAST_READ_DATE != null) {
            stmt.bindLong(30, LAST_READ_DATE);
        }
        stmt.bindLong(31, entity.getLAST_STATE_TIMES());
        stmt.bindLong(32, entity.getMETER_TYPE());
        stmt.bindLong(33, entity.getMETER_TYPE2());
        stmt.bindString(34, entity.getMAIN_CARD_ID());
        stmt.bindLong(35, entity.getTERMINATE_READING());
        stmt.bindLong(36, entity.getINITIATE_READING());
        stmt.bindString(37, entity.getSEAL_NUMBER());
        stmt.bindString(38, entity.getBAR_CODE());
        stmt.bindLong(39, entity.getCALIBER_ID());
        stmt.bindLong(40, entity.getCALIBER_RANGE_ID());
        stmt.bindLong(41, entity.getMODEL_ID());
        stmt.bindLong(42, entity.getPRODUCER_ID());
        stmt.bindLong(43, entity.getSTATE());
 
        Long INSTALL_DATE = entity.getINSTALL_DATE();
        if (INSTALL_DATE != null) {
            stmt.bindLong(44, INSTALL_DATE);
        }
 
        Long REPLACE_DATE = entity.getREPLACE_DATE();
        if (REPLACE_DATE != null) {
            stmt.bindLong(45, REPLACE_DATE);
        }
        stmt.bindDouble(46, entity.getLOCATION_LG());
        stmt.bindDouble(47, entity.getLOCATION_LT());
        stmt.bindLong(48, entity.getDOWNLOAD_TYPE());
        stmt.bindLong(49, entity.getIS_UPLPADED());
 
        String EXTEND = entity.getEXTEND();
        if (EXTEND != null) {
            stmt.bindString(50, EXTEND);
        }
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    @Override
    public ReCord readEntity(Cursor cursor, int offset) {
        ReCord entity = new ReCord( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // ID
            cursor.getInt(offset + 1), // TASK_ID
            cursor.getInt(offset + 2), // RECORD_ID
            cursor.getString(offset + 3), // CARD_ID
            cursor.getString(offset + 4), // SUBCOM_CODE
            cursor.getString(offset + 5), // BOOK_ID
            cursor.getInt(offset + 6), // BOOK_SORT_INDEX
            cursor.getInt(offset + 7), // BILLING_MONTH
            cursor.getInt(offset + 8), // READ_YEAR
            cursor.getInt(offset + 9), // READ_MONTH
            cursor.getInt(offset + 10), // READ_TIMES
            cursor.getInt(offset + 11), // RECORD_ENTRY_TIME
            cursor.getInt(offset + 12), // METER_READER
            cursor.getInt(offset + 13), // READING
            cursor.getInt(offset + 14), // READING1
            cursor.getInt(offset + 15), // READ_WATER
            cursor.getInt(offset + 16), // READ_DATE
            cursor.getInt(offset + 17), // READ_STATE
            cursor.getInt(offset + 18), // READ_STATE_TIMES
            cursor.getInt(offset + 19), // RECORD_STATE_LGLD
            cursor.getInt(offset + 20), // RECORD_REASON_LGLD
            cursor.isNull(offset + 21) ? null : cursor.getString(offset + 21), // RECORD_REMARK
            cursor.getInt(offset + 22), // AVG_WATER3
            cursor.getInt(offset + 23), // AVG_WATER6
            cursor.getInt(offset + 24), // AVG_WATER12
            cursor.getInt(offset + 25), // LAST_READING
            cursor.getInt(offset + 26), // LAST_READING1
            cursor.getInt(offset + 27), // LAST_READ_WATER
            cursor.getInt(offset + 28), // LAST_READ_STATE
            cursor.isNull(offset + 29) ? null : cursor.getLong(offset + 29), // LAST_READ_DATE
            cursor.getInt(offset + 30), // LAST_STATE_TIMES
            cursor.getInt(offset + 31), // METER_TYPE
            cursor.getInt(offset + 32), // METER_TYPE2
            cursor.getString(offset + 33), // MAIN_CARD_ID
            cursor.getInt(offset + 34), // TERMINATE_READING
            cursor.getInt(offset + 35), // INITIATE_READING
            cursor.getString(offset + 36), // SEAL_NUMBER
            cursor.getString(offset + 37), // BAR_CODE
            cursor.getInt(offset + 38), // CALIBER_ID
            cursor.getInt(offset + 39), // CALIBER_RANGE_ID
            cursor.getInt(offset + 40), // MODEL_ID
            cursor.getInt(offset + 41), // PRODUCER_ID
            cursor.getInt(offset + 42), // STATE
            cursor.isNull(offset + 43) ? null : cursor.getLong(offset + 43), // INSTALL_DATE
            cursor.isNull(offset + 44) ? null : cursor.getLong(offset + 44), // REPLACE_DATE
            cursor.getDouble(offset + 45), // LOCATION_LG
            cursor.getDouble(offset + 46), // LOCATION_LT
            cursor.getInt(offset + 47), // DOWNLOAD_TYPE
            cursor.getInt(offset + 48), // IS_UPLPADED
            cursor.isNull(offset + 49) ? null : cursor.getString(offset + 49) // EXTEND
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, ReCord entity, int offset) {
        entity.setID(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setTASK_ID(cursor.getInt(offset + 1));
        entity.setRECORD_ID(cursor.getInt(offset + 2));
        entity.setCARD_ID(cursor.getString(offset + 3));
        entity.setSUBCOM_CODE(cursor.getString(offset + 4));
        entity.setBOOK_ID(cursor.getString(offset + 5));
        entity.setBOOK_SORT_INDEX(cursor.getInt(offset + 6));
        entity.setBILLING_MONTH(cursor.getInt(offset + 7));
        entity.setREAD_YEAR(cursor.getInt(offset + 8));
        entity.setREAD_MONTH(cursor.getInt(offset + 9));
        entity.setREAD_TIMES(cursor.getInt(offset + 10));
        entity.setRECORD_ENTRY_TIME(cursor.getInt(offset + 11));
        entity.setMETER_READER(cursor.getInt(offset + 12));
        entity.setREADING(cursor.getInt(offset + 13));
        entity.setREADING1(cursor.getInt(offset + 14));
        entity.setREAD_WATER(cursor.getInt(offset + 15));
        entity.setREAD_DATE(cursor.getInt(offset + 16));
        entity.setREAD_STATE(cursor.getInt(offset + 17));
        entity.setREAD_STATE_TIMES(cursor.getInt(offset + 18));
        entity.setRECORD_STATE_LGLD(cursor.getInt(offset + 19));
        entity.setRECORD_REASON_LGLD(cursor.getInt(offset + 20));
        entity.setRECORD_REMARK(cursor.isNull(offset + 21) ? null : cursor.getString(offset + 21));
        entity.setAVG_WATER3(cursor.getInt(offset + 22));
        entity.setAVG_WATER6(cursor.getInt(offset + 23));
        entity.setAVG_WATER12(cursor.getInt(offset + 24));
        entity.setLAST_READING(cursor.getInt(offset + 25));
        entity.setLAST_READING1(cursor.getInt(offset + 26));
        entity.setLAST_READ_WATER(cursor.getInt(offset + 27));
        entity.setLAST_READ_STATE(cursor.getInt(offset + 28));
        entity.setLAST_READ_DATE(cursor.isNull(offset + 29) ? null : cursor.getLong(offset + 29));
        entity.setLAST_STATE_TIMES(cursor.getInt(offset + 30));
        entity.setMETER_TYPE(cursor.getInt(offset + 31));
        entity.setMETER_TYPE2(cursor.getInt(offset + 32));
        entity.setMAIN_CARD_ID(cursor.getString(offset + 33));
        entity.setTERMINATE_READING(cursor.getInt(offset + 34));
        entity.setINITIATE_READING(cursor.getInt(offset + 35));
        entity.setSEAL_NUMBER(cursor.getString(offset + 36));
        entity.setBAR_CODE(cursor.getString(offset + 37));
        entity.setCALIBER_ID(cursor.getInt(offset + 38));
        entity.setCALIBER_RANGE_ID(cursor.getInt(offset + 39));
        entity.setMODEL_ID(cursor.getInt(offset + 40));
        entity.setPRODUCER_ID(cursor.getInt(offset + 41));
        entity.setSTATE(cursor.getInt(offset + 42));
        entity.setINSTALL_DATE(cursor.isNull(offset + 43) ? null : cursor.getLong(offset + 43));
        entity.setREPLACE_DATE(cursor.isNull(offset + 44) ? null : cursor.getLong(offset + 44));
        entity.setLOCATION_LG(cursor.getDouble(offset + 45));
        entity.setLOCATION_LT(cursor.getDouble(offset + 46));
        entity.setDOWNLOAD_TYPE(cursor.getInt(offset + 47));
        entity.setIS_UPLPADED(cursor.getInt(offset + 48));
        entity.setEXTEND(cursor.isNull(offset + 49) ? null : cursor.getString(offset + 49));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(ReCord entity, long rowId) {
        entity.setID(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(ReCord entity) {
        if(entity != null) {
            return entity.getID();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(ReCord entity) {
        return entity.getID() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
