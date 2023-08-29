package com.sh3h.localprovider.greendaoDao;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import com.sh3h.localprovider.greendaoEntity.Caliber;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "MR_CALIBER".
*/
public class CaliberDao extends AbstractDao<Caliber, Long> {

    public static final String TABLENAME = "MR_CALIBER";

    /**
     * Properties of entity Caliber.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property ID = new Property(0, Long.class, "ID", true, "ID");
        public final static Property CALIBER_ID = new Property(1, int.class, "CALIBER_ID", false, "CALIBER__ID");
        public final static Property CALIBER_NAME = new Property(2, String.class, "CALIBER_NAME", false, "CALIBER__NAME");
        public final static Property CALIBER_VALUE = new Property(3, int.class, "CALIBER_VALUE", false, "CALIBER__VALUE");
        public final static Property CALIBER_INSPECTION_CYCLE = new Property(4, int.class, "CALIBER_INSPECTION_CYCLE", false, "CALIBER__INSPECTION__CYCLE");
        public final static Property CALIBER_CHANGE_CYCLE = new Property(5, int.class, "CALIBER_CHANGE_CYCLE", false, "CALIBER__CHANGE__CYCLE");
        public final static Property CALIBER_LOW_VOLUME = new Property(6, double.class, "CALIBER_LOW_VOLUME", false, "CALIBER__LOW__VOLUME");
        public final static Property CALIBER_HIGH_VOLUME = new Property(7, double.class, "CALIBER_HIGH_VOLUME", false, "CALIBER__HIGH__VOLUME");
        public final static Property CALIBER_REMARK = new Property(8, String.class, "CALIBER_REMARK", false, "CALIBER__REMARK");
    }


    public CaliberDao(DaoConfig config) {
        super(config);
    }
    
    public CaliberDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"MR_CALIBER\" (" + //
                "\"ID\" INTEGER PRIMARY KEY AUTOINCREMENT ," + // 0: ID
                "\"CALIBER__ID\" INTEGER NOT NULL ," + // 1: CALIBER_ID
                "\"CALIBER__NAME\" TEXT NOT NULL ," + // 2: CALIBER_NAME
                "\"CALIBER__VALUE\" INTEGER NOT NULL ," + // 3: CALIBER_VALUE
                "\"CALIBER__INSPECTION__CYCLE\" INTEGER NOT NULL ," + // 4: CALIBER_INSPECTION_CYCLE
                "\"CALIBER__CHANGE__CYCLE\" INTEGER NOT NULL ," + // 5: CALIBER_CHANGE_CYCLE
                "\"CALIBER__LOW__VOLUME\" REAL NOT NULL ," + // 6: CALIBER_LOW_VOLUME
                "\"CALIBER__HIGH__VOLUME\" REAL NOT NULL ," + // 7: CALIBER_HIGH_VOLUME
                "\"CALIBER__REMARK\" TEXT);"); // 8: CALIBER_REMARK
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"MR_CALIBER\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, Caliber entity) {
        stmt.clearBindings();
 
        Long ID = entity.getID();
        if (ID != null) {
            stmt.bindLong(1, ID);
        }
        stmt.bindLong(2, entity.getCALIBER_ID());
        stmt.bindString(3, entity.getCALIBER_NAME());
        stmt.bindLong(4, entity.getCALIBER_VALUE());
        stmt.bindLong(5, entity.getCALIBER_INSPECTION_CYCLE());
        stmt.bindLong(6, entity.getCALIBER_CHANGE_CYCLE());
        stmt.bindDouble(7, entity.getCALIBER_LOW_VOLUME());
        stmt.bindDouble(8, entity.getCALIBER_HIGH_VOLUME());
 
        String CALIBER_REMARK = entity.getCALIBER_REMARK();
        if (CALIBER_REMARK != null) {
            stmt.bindString(9, CALIBER_REMARK);
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, Caliber entity) {
        stmt.clearBindings();
 
        Long ID = entity.getID();
        if (ID != null) {
            stmt.bindLong(1, ID);
        }
        stmt.bindLong(2, entity.getCALIBER_ID());
        stmt.bindString(3, entity.getCALIBER_NAME());
        stmt.bindLong(4, entity.getCALIBER_VALUE());
        stmt.bindLong(5, entity.getCALIBER_INSPECTION_CYCLE());
        stmt.bindLong(6, entity.getCALIBER_CHANGE_CYCLE());
        stmt.bindDouble(7, entity.getCALIBER_LOW_VOLUME());
        stmt.bindDouble(8, entity.getCALIBER_HIGH_VOLUME());
 
        String CALIBER_REMARK = entity.getCALIBER_REMARK();
        if (CALIBER_REMARK != null) {
            stmt.bindString(9, CALIBER_REMARK);
        }
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    @Override
    public Caliber readEntity(Cursor cursor, int offset) {
        Caliber entity = new Caliber( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // ID
            cursor.getInt(offset + 1), // CALIBER_ID
            cursor.getString(offset + 2), // CALIBER_NAME
            cursor.getInt(offset + 3), // CALIBER_VALUE
            cursor.getInt(offset + 4), // CALIBER_INSPECTION_CYCLE
            cursor.getInt(offset + 5), // CALIBER_CHANGE_CYCLE
            cursor.getDouble(offset + 6), // CALIBER_LOW_VOLUME
            cursor.getDouble(offset + 7), // CALIBER_HIGH_VOLUME
            cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8) // CALIBER_REMARK
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, Caliber entity, int offset) {
        entity.setID(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setCALIBER_ID(cursor.getInt(offset + 1));
        entity.setCALIBER_NAME(cursor.getString(offset + 2));
        entity.setCALIBER_VALUE(cursor.getInt(offset + 3));
        entity.setCALIBER_INSPECTION_CYCLE(cursor.getInt(offset + 4));
        entity.setCALIBER_CHANGE_CYCLE(cursor.getInt(offset + 5));
        entity.setCALIBER_LOW_VOLUME(cursor.getDouble(offset + 6));
        entity.setCALIBER_HIGH_VOLUME(cursor.getDouble(offset + 7));
        entity.setCALIBER_REMARK(cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(Caliber entity, long rowId) {
        entity.setID(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(Caliber entity) {
        if(entity != null) {
            return entity.getID();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(Caliber entity) {
        return entity.getID() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
