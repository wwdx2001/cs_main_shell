package com.sh3h.dataprovider;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.sh3h.dataprovider.greendaoDao.DaoMaster;
import com.sh3h.dataprovider.greendaoDao.DaoSession;
import com.sh3h.dataprovider.greendaoDao.TrackDao;
import com.sh3h.dataprovider.greendaoDao.WordDao;
import com.sh3h.dataprovider.greendaoEntity.Track;
import com.sh3h.dataprovider.greendaoEntity.Word;
import com.sh3h.mobileutil.util.TextUtil;

import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.query.WhereCondition;

import java.util.List;
import java.util.Locale;

/**
 * Created by liurui on 2015/8/3.
 */
public class DataProviderImpl implements
        IDataProvider {

    private Database mDatabase;
    private DaoMaster.DevOpenHelper mDevOpenHelper;
    private DaoMaster mDaoMaster;
    private DaoSession mDaoSession;

    private TrackDao mTrackDao;
    private WordDao mWordDao;

    public DataProviderImpl() {

    }

    @Override
    public synchronized boolean init(String path, Context context) {
        mDevOpenHelper = new DaoMaster.DevOpenHelper(context, path, null);
        mDatabase = mDevOpenHelper.getWritableDb();
//        mDatabase = openHelper.getEncryptedWritableDb(MY_PWD);
        if (mDatabase == null) {
            return false;
        }
        mDaoMaster = new DaoMaster(mDatabase);
        mDaoSession = mDaoMaster.newSession();

        mTrackDao = mDaoSession.getTrackDao();
        mWordDao = mDaoSession.getWordDao();
        return true;
    }

    @Override
    public synchronized void destroy() {
        if (mDatabase != null) {
            mDatabase.close();
            mDatabase = null;
        }

        if (mDaoSession != null) {
            mDaoSession.clear();
            mDaoSession = null;
        }

        if (mDevOpenHelper != null) {
            mDevOpenHelper.close();
            mDevOpenHelper = null;
        }
    }

    @Override
    public synchronized void clearAllTables() {
        if (mTrackDao != null) {
            mTrackDao.deleteAll();
        }

        if (mWordDao != null) {
            mWordDao.deleteAll();
        }
    }

    @Override
    public synchronized void insertTrack(Track track) {
        if (mTrackDao == null) {
            return;
        }
        mTrackDao.insert(track);
    }

    @Override
    public synchronized void clearTrack(int userId, int reservedCount) {
        if (mTrackDao == null) {
            return;
        }

        long count = mTrackDao.queryBuilder().where(TrackDao.Properties.S_USERID.eq(String.valueOf(userId))).count();
        if (count <= reservedCount) {
            return;
        }

        count -= reservedCount;
        String sql = String.format(Locale.CHINESE,
                "%s in (select %s from %s where %s = \"%d\" order by %s limit %d)",
                TrackDao.Properties.ID.columnName, TrackDao.Properties.ID.columnName, TrackDao.TABLENAME,
                TrackDao.Properties.S_USERID.columnName, userId, TrackDao.Properties.L_TIME.columnName, count);
        mTrackDao.queryBuilder().where(new WhereCondition.StringCondition(sql))
                .buildDelete().executeDeleteWithoutDetachingEntities();
    }

    @Override
    public synchronized List<Word> getWords() {
        if (mWordDao == null) {
            return null;
        }
        return mWordDao.loadAll();
    }

    @Override
    public synchronized void insertWordList(List<Word> wordsList) {
        if (mWordDao == null) {
            return;
        }
        mWordDao.insertInTx(wordsList);
    }
}
