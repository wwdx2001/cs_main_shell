package com.sh3h.dataprovider.data;

import android.content.Context;

import com.sh3h.dataprovider.data.local.config.ConfigHelper;
import com.sh3h.dataprovider.data.local.db.DbHelper;
import com.sh3h.dataprovider.data.local.preference.PreferencesHelper;
import com.sh3h.dataprovider.data.remote.HttpHelper;
import com.sh3h.dataprovider.util.EventPosterHelper;
import com.sh3h.mobileutil.util.LogUtil;
import com.squareup.otto.Bus;

import javax.inject.Inject;



public class DataManager {
    private static final String TAG = "DataManager";

    private Context mContext;
    private final HttpHelper mHttpHelper;
    private final DbHelper mDbHelper;
    private final PreferencesHelper mPreferencesHelper;
    private final ConfigHelper mConfigHelper;
    private final EventPosterHelper mEventPoster;

    @Inject
    public DataManager(HttpHelper httpHelper,
                       PreferencesHelper preferencesHelper,
                       DbHelper dbHelper,
                       ConfigHelper configHelper,
                       EventPosterHelper eventPosterHelper) {
        mHttpHelper = httpHelper;
        mPreferencesHelper = preferencesHelper;
        mDbHelper = dbHelper;
        mConfigHelper = configHelper;
        mEventPoster = eventPosterHelper;
    }

    public void init(Context context, Bus bus) {
        mContext = context;
        mHttpHelper.init(context, bus);
        mPreferencesHelper.setContext(context);
        mDbHelper.setContext(context);
        mConfigHelper.setContext(context);
        mEventPoster.setBus(bus);
    }

    /**
     * initialize logger file
     */
    public void initLogger() {
        LogUtil.initLogger(mConfigHelper.getLogFilePath().getPath());
        mDbHelper.init();
    }

    /**
     * release all resources
     */
    public void destroy() {
        mDbHelper.destroy();
    }
}
