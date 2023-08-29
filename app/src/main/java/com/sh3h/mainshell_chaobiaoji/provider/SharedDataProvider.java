package com.sh3h.mainshell_chaobiaoji.provider;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import java.util.HashMap;


public class SharedDataProvider extends ContentProvider {
    // Defines the database name
    private static final String DB_NAME = "sh3h.db";
    private static final String DB_TABLE = "SharedData";
    private static final int DB_VERSION = 1;

    // A string that defines the SQL statement for creating a table
    private static final String SQL_CREATE_MAIN = "CREATE TABLE " + DB_TABLE +
            " (" + SharedData.SD_KEY + " TEXT NOT NULL PRIMARY KEY, " +
            SharedData.SD_VALUE + " TEXT NOT NULL);";

    // Creates a UriMatcher object.
    private static final UriMatcher uriMatcher;

    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(SharedData.AUTHORITY, "item", SharedData.ITEM);
        uriMatcher.addURI(SharedData.AUTHORITY, "item/*", SharedData.ITEM_KEY);
        //uriMatcher.addURI(SharedData.AUTHORITY, "pos/#", SharedData.ITEM_POS);
    }

    private static final HashMap<String, String> articleProjectionMap;

    static {
        articleProjectionMap = new HashMap<>();
        articleProjectionMap.put(SharedData.SD_KEY, SharedData.SD_KEY);
        articleProjectionMap.put(SharedData.SD_VALUE, SharedData.SD_VALUE);
    }

    private ContentResolver mContentResolver;
    private DBHelper mDBHelper;

    @Override
    public boolean onCreate() {
        Context context = getContext();
        if (context != null) {
            mContentResolver = context.getContentResolver();
            mDBHelper = new DBHelper(context);
            return true;
        }

        return false;
    }

    @Override
    public Cursor query(@NonNull Uri uri, String[] projection, String selection, String[] selectionArgs,
                        String sortOrder) {
        SQLiteDatabase db = mDBHelper.getReadableDatabase();
        SQLiteQueryBuilder sqlBuilder = new SQLiteQueryBuilder();
        String limit = null;

        switch (uriMatcher.match(uri)) {
            case SharedData.ITEM: {
                sqlBuilder.setTables(DB_TABLE);
                sqlBuilder.setProjectionMap(articleProjectionMap);
                break;
            }
            case SharedData.ITEM_KEY: {
                String key = uri.getPathSegments().get(1);
                sqlBuilder.setTables(DB_TABLE);
                sqlBuilder.setProjectionMap(articleProjectionMap);
                sqlBuilder.appendWhere(SharedData.SD_KEY + "=\"" + key + "\"");
                break;
            }
//            case SharedData.ITEM_POS: {
//                String pos = uri.getPathSegments().get(1);
//                sqlBuilder.setTables(DB_TABLE);
//                sqlBuilder.setProjectionMap(articleProjectionMap);
//                limit = pos + ", 1";
//                break;
//            }
            default:
                throw new IllegalArgumentException("Error Uri: " + uri);
        }

        Cursor cursor = sqlBuilder.query(db, projection, selection, selectionArgs,
                null, null, TextUtils.isEmpty(sortOrder) ? SharedData.DEFAULT_SORT_ORDER : sortOrder, limit);
        cursor.setNotificationUri(mContentResolver, uri);
        return cursor;
    }

    @Override
    public Uri insert(@NonNull Uri uri, ContentValues values) {
        if (uriMatcher.match(uri) != SharedData.ITEM) {
            throw new IllegalArgumentException("Error Uri: " + uri);
        }

        SQLiteDatabase db = mDBHelper.getWritableDatabase();
        long id = db.replace(DB_TABLE, null, values);
        if (id < 0) {
            throw new SQLiteException("Unable to insert " + values + " for " + uri);
        }

        Uri newUri = ContentUris.withAppendedId(uri, id);
        mContentResolver.notifyChange(newUri, null);
        return newUri;
    }

    @Override
    public int bulkInsert(@NonNull Uri uri, @NonNull ContentValues[] values) {
        if (uriMatcher.match(uri) != SharedData.ITEM) {
            throw new IllegalArgumentException("Error Uri: " + uri);
        }

        int count = 0;
        try {
            SQLiteDatabase db = mDBHelper.getWritableDatabase();
            try {
                db.beginTransaction();
                for (ContentValues value : values) {
                    long id = db.replace(DB_TABLE, null, value);
                    if (id > 0)
                        count++;
                }
                db.setTransactionSuccessful();
            } catch (Exception e) {
                // Your error handling
            } finally {
                db.endTransaction();
            }
            mContentResolver.notifyChange(uri, null);
        } catch (Exception e) {
            // Your error handling
        }

        return count;
    }

    @Override
    public int update(@NonNull Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        SQLiteDatabase db = mDBHelper.getWritableDatabase();
        int count = 0;

        switch (uriMatcher.match(uri)) {
            case SharedData.ITEM: {
                count = db.update(DB_TABLE, values, selection, selectionArgs);
                break;
            }
            case SharedData.ITEM_KEY: {
                String key = uri.getPathSegments().get(1);
                count = db.update(DB_TABLE, values, SharedData.SD_KEY + "=\"" + key + "\""
                        + (!TextUtils.isEmpty(selection) ? " and (" + selection + ')' : ""), selectionArgs);
                break;
            }
            default:
                throw new IllegalArgumentException("Error Uri: " + uri);
        }

        mContentResolver.notifyChange(uri, null);
        return count;
    }

    @Override
    public int delete(@NonNull Uri uri, String selection, String[] selectionArgs) {
        SQLiteDatabase db = mDBHelper.getWritableDatabase();
        int count = 0;

        switch (uriMatcher.match(uri)) {
            case SharedData.ITEM: {
                count = db.delete(DB_TABLE, selection, selectionArgs);
                break;
            }
            case SharedData.ITEM_KEY: {
                String key = uri.getPathSegments().get(1);
                count = db.delete(DB_TABLE, SharedData.SD_KEY + "=\"" + key + "\""
                        + (!TextUtils.isEmpty(selection) ? " and (" + selection + ')' : ""), selectionArgs);
                break;
            }
            default:
                throw new IllegalArgumentException("Error Uri: " + uri);
        }

        mContentResolver.notifyChange(uri, null);
        return count;
    }

    @Override
    public String getType(@NonNull Uri uri) {
        switch (uriMatcher.match(uri)) {
            case SharedData.ITEM:
                return SharedData.CONTENT_TYPE;
            case SharedData.ITEM_KEY:
                //case SharedData.ITEM_POS:
                return SharedData.CONTENT_ITEM_TYPE;
            default:
                throw new IllegalArgumentException("Error Uri: " + uri);
        }
    }

    /**
     * Helper class that actually creates and manages the provider's underlying data repository.
     */
    protected static final class DBHelper extends SQLiteOpenHelper {
        /*
         * Instantiates an open helper for the provider's SQLite data repository
         * Do not do database creation and upgrade here.
         */
        DBHelper(Context context) {
            super(context, DB_NAME, null, 1);
        }

        /*
         * Creates the data repository. This is called when the provider attempts to open the
         * repository and SQLite reports that it doesn't exist.
         */
        @Override
        public void onCreate(SQLiteDatabase db) {
            // Creates the main table
            db.execSQL(SQL_CREATE_MAIN);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + DB_TABLE);
        }
    }

}
