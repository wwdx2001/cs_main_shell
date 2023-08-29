package com.sh3h.localprovider;


import android.content.Context;

import com.sh3h.localprovider.greendaoDao.DaoMaster;
import com.sh3h.localprovider.greendaoDao.DaoSession;

import org.greenrobot.greendao.database.Database;

public class DataProviderImpl implements IDataProvider {

    private Database mDatabase;
    private DaoMaster.DevOpenHelper mDevOpenHelper;
    private DaoMaster mDaoMaster;
    private DaoSession mDaoSession;

    public DataProviderImpl() {
    }

    @Override
    public boolean init(String path, Context context) {
        mDevOpenHelper = new DaoMaster.DevOpenHelper(context, path, null);
        mDatabase = mDevOpenHelper.getWritableDb();
//        mDatabase = openHelper.getEncryptedWritableDb(MY_PWD);
        if (mDatabase == null) {
            return false;
        }
        mDaoMaster = new DaoMaster(mDatabase);
        mDaoSession = mDaoMaster.newSession();
        return true;
    }

    @Override
    public void destroy() {
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
}
