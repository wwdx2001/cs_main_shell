package com.sh3h.datautil.data.local.db;

import com.sh3h.datautil.data.local.config.ConfigHelper;
import com.sh3h.datautil.data.model.Ribot;
//import com.squareup.sqlbrite.BriteDatabase;
//import com.squareup.sqlbrite.SqlBrite;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.Collection;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Func1;

@Deprecated
@Singleton
public class DatabaseHelper {
//    private final BriteDatabase mDb;
//    private final ConfigHelper mConfigHelper;
//
//    @Inject
//    public DatabaseHelper(DbOpenHelper dbOpenHelper, ConfigHelper configHelper) {
//        mDb = SqlBrite.create().wrapDatabaseHelper(dbOpenHelper);
//        mConfigHelper = configHelper;
//    }
//
//    public BriteDatabase getBriteDb() {
//        return mDb;
//    }
//
//    /**
//     * Remove all the data from all the tables in the database.
//     */
//    public Observable<Void> clearTables() {
//        return Observable.create(new Observable.OnSubscribe<Void>() {
//            @Override
//            public void call(Subscriber<? super Void> subscriber) {
//                if (subscriber.isUnsubscribed()) {
//                    return;
//                }
//
//                BriteDatabase.Transaction transaction = mDb.newTransaction();
//                try {
//                    Cursor cursor = mDb.query("SELECT name FROM sqlite_master WHERE type='table'");
//                    while (cursor.moveToNext()) {
//                        mDb.delete(cursor.getString(cursor.getColumnIndex("name")), null);
//                    }
//                    cursor.close();
//                    transaction.markSuccessful();
//                    subscriber.onCompleted();
//                } finally {
//                    transaction.end();
//                }
//            }
//        });
//    }
//
//    public Observable<Ribot> setRibots(final Collection<Ribot> newRibots) {
//        return Observable.create(new Observable.OnSubscribe<Ribot>() {
//            @Override
//            public void call(Subscriber<? super Ribot> subscriber) {
//                if (subscriber.isUnsubscribed()) {
//                    return;
//                }
//
//                BriteDatabase.Transaction transaction = mDb.newTransaction();
//                try {
//                    mDb.delete(Db.RibotProfileTable.TABLE_NAME, null);
//                    for (Ribot ribot : newRibots) {
//                        long result = mDb.insert(Db.RibotProfileTable.TABLE_NAME,
//                                Db.RibotProfileTable.toContentValues(ribot.profile),
//                                SQLiteDatabase.CONFLICT_REPLACE);
//                        if (result >= 0) {
//                            subscriber.onNext(ribot);
//                        }
//                    }
//                    transaction.markSuccessful();
//                    subscriber.onCompleted();
//                } finally {
//                    transaction.end();
//                }
//            }
//        });
//    }
//
//    public Observable<List<Ribot>> getRibots() {
//        return mDb.createQuery(Db.RibotProfileTable.TABLE_NAME,
//                "SELECT * FROM " + Db.RibotProfileTable.TABLE_NAME)
//                .mapToList(new Func1<Cursor, Ribot>() {
//                    @Override
//                    public Ribot call(Cursor cursor) {
//                        return new Ribot(Db.RibotProfileTable.parseCursor(cursor));
//                    }
//                });
//    }
}
