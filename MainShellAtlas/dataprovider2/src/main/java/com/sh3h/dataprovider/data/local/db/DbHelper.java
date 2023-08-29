package com.sh3h.dataprovider.data.local.db;

import android.content.Context;


import com.sh3h.dataprovider.data.local.config.ConfigHelper;
import com.sh3h.localprovider.DBManager;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;


@Singleton
public class DbHelper {
    private static final String TAG = "DbHelper";

    private Context mContext;
    private final ConfigHelper mConfigHelper;
    private boolean mIsInit;

    @Inject
    public DbHelper(ConfigHelper configHelper) {
        mConfigHelper = configHelper;
        mIsInit = false;
    }

    public void setContext(Context context) {
        mContext = context;
    }

    /**
     * initialize
     */
    public synchronized boolean init() {
        if (!mIsInit) {
            mIsInit = true;
            return DBManager.getInstance().init(mConfigHelper.getDBFilePath().getPath(), mContext);
        }

        return true;
    }

    /**
     * destroy
     */
    public synchronized void destroy() {
        if (mIsInit) {
            mIsInit = false;
            DBManager.getInstance().destroy();
        }
    }



}
