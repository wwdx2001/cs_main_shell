package com.sh3h.localprovider.greendaoDao;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import com.sh3h.localprovider.greendaoEntity.CaliberRange;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "MR_CALIBERRANGE".
*/
public class CaliberRangeDao extends AbstractDao<CaliberRange, Long> {

    public static final String TABLENAME = "MR_CALIBERRANGE";

    /**
     * Properties of entity CaliberRange.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property RANGE_ID = new Property(0, Long.class, "RANGE_ID", true, "_id");
        public final static Property RANGE_NAME = new Property(1, long.class, "RANGE_NAME", false, "RANGE__NAME");
        public final static Property RANGE_VALUE = new Property(2, long.class, "RANGE_VALUE", false, "RANGE__VALUE");
        public final static Property RANGE_VOLUME = new Property(3, long.class, "RANGE_VOLUME", false, "RANGE__VOLUME");
    }


    public CaliberRangeDao(DaoConfig config) {
        super(config);
    }
    
    public CaliberRangeDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"MR_CALIBERRANGE\" (" + //
                "\"_id\" INTEGER PRIMARY KEY ," + // 0: RANGE_ID
                "\"RANGE__NAME\" INTEGER NOT NULL ," + // 1: RANGE_NAME
                "\"RANGE__VALUE\" INTEGER NOT NULL ," + // 2: RANGE_VALUE
                "\"RANGE__VOLUME\" INTEGER NOT NULL );"); // 3: RANGE_VOLUME
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"MR_CALIBERRANGE\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, CaliberRange entity) {
        stmt.clearBindings();
 
        Long RANGE_ID = entity.getRANGE_ID();
        if (RANGE_ID != null) {
            stmt.bindLong(1, RANGE_ID);
        }
        stmt.bindLong(2, entity.getRANGE_NAME());
        stmt.bindLong(3, entity.getRANGE_VALUE());
        stmt.bindLong(4, entity.getRANGE_VOLUME());
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, CaliberRange entity) {
        stmt.clearBindings();
 
        Long RANGE_ID = entity.getRANGE_ID();
        if (RANGE_ID != null) {
            stmt.bindLong(1, RANGE_ID);
        }
        stmt.bindLong(2, entity.getRANGE_NAME());
        stmt.bindLong(3, entity.getRANGE_VALUE());
        stmt.bindLong(4, entity.getRANGE_VOLUME());
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    @Override
    public CaliberRange readEntity(Cursor cursor, int offset) {
        CaliberRange entity = new CaliberRange( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // RANGE_ID
            cursor.getLong(offset + 1), // RANGE_NAME
            cursor.getLong(offset + 2), // RANGE_VALUE
            cursor.getLong(offset + 3) // RANGE_VOLUME
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, CaliberRange entity, int offset) {
        entity.setRANGE_ID(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setRANGE_NAME(cursor.getLong(offset + 1));
        entity.setRANGE_VALUE(cursor.getLong(offset + 2));
        entity.setRANGE_VOLUME(cursor.getLong(offset + 3));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(CaliberRange entity, long rowId) {
        entity.setRANGE_ID(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(CaliberRange entity) {
        if(entity != null) {
            return entity.getRANGE_ID();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(CaliberRange entity) {
        return entity.getRANGE_ID() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
