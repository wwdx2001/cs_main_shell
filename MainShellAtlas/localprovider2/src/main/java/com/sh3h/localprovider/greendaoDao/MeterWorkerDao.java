package com.sh3h.localprovider.greendaoDao;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import com.sh3h.localprovider.greendaoEntity.MeterWorker;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "MR_METERWORKER".
*/
public class MeterWorkerDao extends AbstractDao<MeterWorker, Long> {

    public static final String TABLENAME = "MR_METERWORKER";

    /**
     * Properties of entity MeterWorker.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property ID = new Property(0, Long.class, "ID", true, "ID");
        public final static Property WORK_ID = new Property(1, int.class, "WORK_ID", false, "WORK__ID");
        public final static Property CARD_ID = new Property(2, String.class, "CARD_ID", false, "CARD__ID");
        public final static Property WORK_TYPE = new Property(3, int.class, "WORK_TYPE", false, "WORK__TYPE");
        public final static Property REASON = new Property(4, int.class, "REASON", false, "REASON");
        public final static Property COMPLATE_DATE = new Property(5, long.class, "COMPLATE_DATE", false, "COMPLATE__DATE");
        public final static Property OLD_TERMINATE_READING = new Property(6, int.class, "OLD_TERMINATE_READING", false, "OLD__TERMINATE__READING");
        public final static Property NEW_INITIATE_READING = new Property(7, int.class, "NEW_INITIATE_READING", false, "NEW__INITIATE__READING");
        public final static Property OPRATOR = new Property(8, int.class, "OPRATOR", false, "OPRATOR");
        public final static Property END_DATE = new Property(9, long.class, "END_DATE", false, "END__DATE");
        public final static Property WORK_STATE = new Property(10, int.class, "WORK_STATE", false, "WORK__STATE");
        public final static Property CREATOR = new Property(11, int.class, "CREATOR", false, "CREATOR");
        public final static Property CREAT_DATE = new Property(12, long.class, "CREAT_DATE", false, "CREAT__DATE");
        public final static Property RECEIPTOR = new Property(13, int.class, "RECEIPTOR", false, "RECEIPTOR");
        public final static Property RECEIPTOR_DATE = new Property(14, long.class, "RECEIPTOR_DATE", false, "RECEIPTOR__DATE");
        public final static Property OLD_SEAL_NUMBER = new Property(15, String.class, "OLD_SEAL_NUMBER", false, "OLD__SEAL__NUMBER");
        public final static Property NEW_SEAL_NUMBER = new Property(16, String.class, "NEW_SEAL_NUMBER", false, "NEW__SEAL__NUMBER");
        public final static Property OLD_BAR_CODE = new Property(17, String.class, "OLD_BAR_CODE", false, "OLD__BAR__CODE");
        public final static Property NEW_BAR_CODE = new Property(18, String.class, "NEW_BAR_CODE", false, "NEW__BAR__CODE");
        public final static Property OLD_PRODUCER_ID = new Property(19, int.class, "OLD_PRODUCER_ID", false, "OLD__PRODUCER__ID");
        public final static Property OLD_MODEL_ID = new Property(20, int.class, "OLD_MODEL_ID", false, "OLD__MODEL__ID");
        public final static Property OLD_CALIBER_ID = new Property(21, int.class, "OLD_CALIBER_ID", false, "OLD__CALIBER__ID");
        public final static Property OLD_CALIBER_RANGE_ID = new Property(22, int.class, "OLD_CALIBER_RANGE_ID", false, "OLD__CALIBER__RANGE__ID");
        public final static Property NEW_PRODUCER_ID = new Property(23, int.class, "NEW_PRODUCER_ID", false, "NEW__PRODUCER__ID");
        public final static Property NEW_MODEL_ID = new Property(24, int.class, "NEW_MODEL_ID", false, "NEW__MODEL__ID");
        public final static Property NEW_CALIBER_ID = new Property(25, int.class, "NEW_CALIBER_ID", false, "NEW__CALIBER__ID");
        public final static Property NEW_CALIBER_RANGE_ID = new Property(26, int.class, "NEW_CALIBER_RANGE_ID", false, "NEW__CALIBER__RANGE__ID");
    }


    public MeterWorkerDao(DaoConfig config) {
        super(config);
    }
    
    public MeterWorkerDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"MR_METERWORKER\" (" + //
                "\"ID\" INTEGER PRIMARY KEY AUTOINCREMENT ," + // 0: ID
                "\"WORK__ID\" INTEGER NOT NULL ," + // 1: WORK_ID
                "\"CARD__ID\" TEXT NOT NULL ," + // 2: CARD_ID
                "\"WORK__TYPE\" INTEGER NOT NULL ," + // 3: WORK_TYPE
                "\"REASON\" INTEGER NOT NULL ," + // 4: REASON
                "\"COMPLATE__DATE\" INTEGER NOT NULL ," + // 5: COMPLATE_DATE
                "\"OLD__TERMINATE__READING\" INTEGER NOT NULL ," + // 6: OLD_TERMINATE_READING
                "\"NEW__INITIATE__READING\" INTEGER NOT NULL ," + // 7: NEW_INITIATE_READING
                "\"OPRATOR\" INTEGER NOT NULL ," + // 8: OPRATOR
                "\"END__DATE\" INTEGER NOT NULL ," + // 9: END_DATE
                "\"WORK__STATE\" INTEGER NOT NULL ," + // 10: WORK_STATE
                "\"CREATOR\" INTEGER NOT NULL ," + // 11: CREATOR
                "\"CREAT__DATE\" INTEGER NOT NULL ," + // 12: CREAT_DATE
                "\"RECEIPTOR\" INTEGER NOT NULL ," + // 13: RECEIPTOR
                "\"RECEIPTOR__DATE\" INTEGER NOT NULL ," + // 14: RECEIPTOR_DATE
                "\"OLD__SEAL__NUMBER\" TEXT NOT NULL ," + // 15: OLD_SEAL_NUMBER
                "\"NEW__SEAL__NUMBER\" TEXT," + // 16: NEW_SEAL_NUMBER
                "\"OLD__BAR__CODE\" TEXT NOT NULL ," + // 17: OLD_BAR_CODE
                "\"NEW__BAR__CODE\" TEXT," + // 18: NEW_BAR_CODE
                "\"OLD__PRODUCER__ID\" INTEGER NOT NULL ," + // 19: OLD_PRODUCER_ID
                "\"OLD__MODEL__ID\" INTEGER NOT NULL ," + // 20: OLD_MODEL_ID
                "\"OLD__CALIBER__ID\" INTEGER NOT NULL ," + // 21: OLD_CALIBER_ID
                "\"OLD__CALIBER__RANGE__ID\" INTEGER NOT NULL ," + // 22: OLD_CALIBER_RANGE_ID
                "\"NEW__PRODUCER__ID\" INTEGER NOT NULL ," + // 23: NEW_PRODUCER_ID
                "\"NEW__MODEL__ID\" INTEGER NOT NULL ," + // 24: NEW_MODEL_ID
                "\"NEW__CALIBER__ID\" INTEGER NOT NULL ," + // 25: NEW_CALIBER_ID
                "\"NEW__CALIBER__RANGE__ID\" INTEGER NOT NULL );"); // 26: NEW_CALIBER_RANGE_ID
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"MR_METERWORKER\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, MeterWorker entity) {
        stmt.clearBindings();
 
        Long ID = entity.getID();
        if (ID != null) {
            stmt.bindLong(1, ID);
        }
        stmt.bindLong(2, entity.getWORK_ID());
        stmt.bindString(3, entity.getCARD_ID());
        stmt.bindLong(4, entity.getWORK_TYPE());
        stmt.bindLong(5, entity.getREASON());
        stmt.bindLong(6, entity.getCOMPLATE_DATE());
        stmt.bindLong(7, entity.getOLD_TERMINATE_READING());
        stmt.bindLong(8, entity.getNEW_INITIATE_READING());
        stmt.bindLong(9, entity.getOPRATOR());
        stmt.bindLong(10, entity.getEND_DATE());
        stmt.bindLong(11, entity.getWORK_STATE());
        stmt.bindLong(12, entity.getCREATOR());
        stmt.bindLong(13, entity.getCREAT_DATE());
        stmt.bindLong(14, entity.getRECEIPTOR());
        stmt.bindLong(15, entity.getRECEIPTOR_DATE());
        stmt.bindString(16, entity.getOLD_SEAL_NUMBER());
 
        String NEW_SEAL_NUMBER = entity.getNEW_SEAL_NUMBER();
        if (NEW_SEAL_NUMBER != null) {
            stmt.bindString(17, NEW_SEAL_NUMBER);
        }
        stmt.bindString(18, entity.getOLD_BAR_CODE());
 
        String NEW_BAR_CODE = entity.getNEW_BAR_CODE();
        if (NEW_BAR_CODE != null) {
            stmt.bindString(19, NEW_BAR_CODE);
        }
        stmt.bindLong(20, entity.getOLD_PRODUCER_ID());
        stmt.bindLong(21, entity.getOLD_MODEL_ID());
        stmt.bindLong(22, entity.getOLD_CALIBER_ID());
        stmt.bindLong(23, entity.getOLD_CALIBER_RANGE_ID());
        stmt.bindLong(24, entity.getNEW_PRODUCER_ID());
        stmt.bindLong(25, entity.getNEW_MODEL_ID());
        stmt.bindLong(26, entity.getNEW_CALIBER_ID());
        stmt.bindLong(27, entity.getNEW_CALIBER_RANGE_ID());
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, MeterWorker entity) {
        stmt.clearBindings();
 
        Long ID = entity.getID();
        if (ID != null) {
            stmt.bindLong(1, ID);
        }
        stmt.bindLong(2, entity.getWORK_ID());
        stmt.bindString(3, entity.getCARD_ID());
        stmt.bindLong(4, entity.getWORK_TYPE());
        stmt.bindLong(5, entity.getREASON());
        stmt.bindLong(6, entity.getCOMPLATE_DATE());
        stmt.bindLong(7, entity.getOLD_TERMINATE_READING());
        stmt.bindLong(8, entity.getNEW_INITIATE_READING());
        stmt.bindLong(9, entity.getOPRATOR());
        stmt.bindLong(10, entity.getEND_DATE());
        stmt.bindLong(11, entity.getWORK_STATE());
        stmt.bindLong(12, entity.getCREATOR());
        stmt.bindLong(13, entity.getCREAT_DATE());
        stmt.bindLong(14, entity.getRECEIPTOR());
        stmt.bindLong(15, entity.getRECEIPTOR_DATE());
        stmt.bindString(16, entity.getOLD_SEAL_NUMBER());
 
        String NEW_SEAL_NUMBER = entity.getNEW_SEAL_NUMBER();
        if (NEW_SEAL_NUMBER != null) {
            stmt.bindString(17, NEW_SEAL_NUMBER);
        }
        stmt.bindString(18, entity.getOLD_BAR_CODE());
 
        String NEW_BAR_CODE = entity.getNEW_BAR_CODE();
        if (NEW_BAR_CODE != null) {
            stmt.bindString(19, NEW_BAR_CODE);
        }
        stmt.bindLong(20, entity.getOLD_PRODUCER_ID());
        stmt.bindLong(21, entity.getOLD_MODEL_ID());
        stmt.bindLong(22, entity.getOLD_CALIBER_ID());
        stmt.bindLong(23, entity.getOLD_CALIBER_RANGE_ID());
        stmt.bindLong(24, entity.getNEW_PRODUCER_ID());
        stmt.bindLong(25, entity.getNEW_MODEL_ID());
        stmt.bindLong(26, entity.getNEW_CALIBER_ID());
        stmt.bindLong(27, entity.getNEW_CALIBER_RANGE_ID());
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    @Override
    public MeterWorker readEntity(Cursor cursor, int offset) {
        MeterWorker entity = new MeterWorker( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // ID
            cursor.getInt(offset + 1), // WORK_ID
            cursor.getString(offset + 2), // CARD_ID
            cursor.getInt(offset + 3), // WORK_TYPE
            cursor.getInt(offset + 4), // REASON
            cursor.getLong(offset + 5), // COMPLATE_DATE
            cursor.getInt(offset + 6), // OLD_TERMINATE_READING
            cursor.getInt(offset + 7), // NEW_INITIATE_READING
            cursor.getInt(offset + 8), // OPRATOR
            cursor.getLong(offset + 9), // END_DATE
            cursor.getInt(offset + 10), // WORK_STATE
            cursor.getInt(offset + 11), // CREATOR
            cursor.getLong(offset + 12), // CREAT_DATE
            cursor.getInt(offset + 13), // RECEIPTOR
            cursor.getLong(offset + 14), // RECEIPTOR_DATE
            cursor.getString(offset + 15), // OLD_SEAL_NUMBER
            cursor.isNull(offset + 16) ? null : cursor.getString(offset + 16), // NEW_SEAL_NUMBER
            cursor.getString(offset + 17), // OLD_BAR_CODE
            cursor.isNull(offset + 18) ? null : cursor.getString(offset + 18), // NEW_BAR_CODE
            cursor.getInt(offset + 19), // OLD_PRODUCER_ID
            cursor.getInt(offset + 20), // OLD_MODEL_ID
            cursor.getInt(offset + 21), // OLD_CALIBER_ID
            cursor.getInt(offset + 22), // OLD_CALIBER_RANGE_ID
            cursor.getInt(offset + 23), // NEW_PRODUCER_ID
            cursor.getInt(offset + 24), // NEW_MODEL_ID
            cursor.getInt(offset + 25), // NEW_CALIBER_ID
            cursor.getInt(offset + 26) // NEW_CALIBER_RANGE_ID
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, MeterWorker entity, int offset) {
        entity.setID(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setWORK_ID(cursor.getInt(offset + 1));
        entity.setCARD_ID(cursor.getString(offset + 2));
        entity.setWORK_TYPE(cursor.getInt(offset + 3));
        entity.setREASON(cursor.getInt(offset + 4));
        entity.setCOMPLATE_DATE(cursor.getLong(offset + 5));
        entity.setOLD_TERMINATE_READING(cursor.getInt(offset + 6));
        entity.setNEW_INITIATE_READING(cursor.getInt(offset + 7));
        entity.setOPRATOR(cursor.getInt(offset + 8));
        entity.setEND_DATE(cursor.getLong(offset + 9));
        entity.setWORK_STATE(cursor.getInt(offset + 10));
        entity.setCREATOR(cursor.getInt(offset + 11));
        entity.setCREAT_DATE(cursor.getLong(offset + 12));
        entity.setRECEIPTOR(cursor.getInt(offset + 13));
        entity.setRECEIPTOR_DATE(cursor.getLong(offset + 14));
        entity.setOLD_SEAL_NUMBER(cursor.getString(offset + 15));
        entity.setNEW_SEAL_NUMBER(cursor.isNull(offset + 16) ? null : cursor.getString(offset + 16));
        entity.setOLD_BAR_CODE(cursor.getString(offset + 17));
        entity.setNEW_BAR_CODE(cursor.isNull(offset + 18) ? null : cursor.getString(offset + 18));
        entity.setOLD_PRODUCER_ID(cursor.getInt(offset + 19));
        entity.setOLD_MODEL_ID(cursor.getInt(offset + 20));
        entity.setOLD_CALIBER_ID(cursor.getInt(offset + 21));
        entity.setOLD_CALIBER_RANGE_ID(cursor.getInt(offset + 22));
        entity.setNEW_PRODUCER_ID(cursor.getInt(offset + 23));
        entity.setNEW_MODEL_ID(cursor.getInt(offset + 24));
        entity.setNEW_CALIBER_ID(cursor.getInt(offset + 25));
        entity.setNEW_CALIBER_RANGE_ID(cursor.getInt(offset + 26));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(MeterWorker entity, long rowId) {
        entity.setID(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(MeterWorker entity) {
        if(entity != null) {
            return entity.getID();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(MeterWorker entity) {
        return entity.getID() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
