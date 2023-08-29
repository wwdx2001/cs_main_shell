package com.sh3h.localprovider.greendaoDao;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import com.sh3h.localprovider.greendaoEntity.ArrearAgeTotal;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "MR_ARREARAGETOTAL".
*/
public class ArrearAgeTotalDao extends AbstractDao<ArrearAgeTotal, Long> {

    public static final String TABLENAME = "MR_ARREARAGETOTAL";

    /**
     * Properties of entity ArrearAgeTotal.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property ID = new Property(0, Long.class, "ID", true, "ID");
        public final static Property CHARGE_COUNT = new Property(1, int.class, "CHARGE_COUNT", false, "CHARGE__COUNT");
        public final static Property CHARGE_MONEY = new Property(2, double.class, "CHARGE_MONEY", false, "CHARGE__MONEY");
        public final static Property WATER_FEE = new Property(3, double.class, "WATER_FEE", false, "WATER__FEE");
        public final static Property LATE_FEE = new Property(4, double.class, "LATE_FEE", false, "LATE__FEE");
    }


    public ArrearAgeTotalDao(DaoConfig config) {
        super(config);
    }
    
    public ArrearAgeTotalDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"MR_ARREARAGETOTAL\" (" + //
                "\"ID\" INTEGER PRIMARY KEY AUTOINCREMENT ," + // 0: ID
                "\"CHARGE__COUNT\" INTEGER NOT NULL ," + // 1: CHARGE_COUNT
                "\"CHARGE__MONEY\" REAL NOT NULL ," + // 2: CHARGE_MONEY
                "\"WATER__FEE\" REAL NOT NULL ," + // 3: WATER_FEE
                "\"LATE__FEE\" REAL NOT NULL );"); // 4: LATE_FEE
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"MR_ARREARAGETOTAL\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, ArrearAgeTotal entity) {
        stmt.clearBindings();
 
        Long ID = entity.getID();
        if (ID != null) {
            stmt.bindLong(1, ID);
        }
        stmt.bindLong(2, entity.getCHARGE_COUNT());
        stmt.bindDouble(3, entity.getCHARGE_MONEY());
        stmt.bindDouble(4, entity.getWATER_FEE());
        stmt.bindDouble(5, entity.getLATE_FEE());
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, ArrearAgeTotal entity) {
        stmt.clearBindings();
 
        Long ID = entity.getID();
        if (ID != null) {
            stmt.bindLong(1, ID);
        }
        stmt.bindLong(2, entity.getCHARGE_COUNT());
        stmt.bindDouble(3, entity.getCHARGE_MONEY());
        stmt.bindDouble(4, entity.getWATER_FEE());
        stmt.bindDouble(5, entity.getLATE_FEE());
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    @Override
    public ArrearAgeTotal readEntity(Cursor cursor, int offset) {
        ArrearAgeTotal entity = new ArrearAgeTotal( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // ID
            cursor.getInt(offset + 1), // CHARGE_COUNT
            cursor.getDouble(offset + 2), // CHARGE_MONEY
            cursor.getDouble(offset + 3), // WATER_FEE
            cursor.getDouble(offset + 4) // LATE_FEE
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, ArrearAgeTotal entity, int offset) {
        entity.setID(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setCHARGE_COUNT(cursor.getInt(offset + 1));
        entity.setCHARGE_MONEY(cursor.getDouble(offset + 2));
        entity.setWATER_FEE(cursor.getDouble(offset + 3));
        entity.setLATE_FEE(cursor.getDouble(offset + 4));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(ArrearAgeTotal entity, long rowId) {
        entity.setID(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(ArrearAgeTotal entity) {
        if(entity != null) {
            return entity.getID();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(ArrearAgeTotal entity) {
        return entity.getID() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
