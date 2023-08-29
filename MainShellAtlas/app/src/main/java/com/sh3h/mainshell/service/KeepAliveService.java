package com.sh3h.mainshell.service;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.SystemClock;
import android.support.annotation.Nullable;

import com.mainshell.datautil.data.DataManager;
import com.mainshell.datautil.data.entity.DULocationTrack;
import com.mainshell.datautil.data.entity.base.DUEntityResult;
import com.mainshell.datautil.data.entity.result.DUTrackResult;
import com.mainshell.datautil.data.local.config.ConfigHelper;
import com.mainshell.datautil.data.local.config.SystemConfig;
import com.mainshell.datautil.data.local.preference.PreferencesHelper;
import com.sh3h.mainshell.MainApplication;
import com.sh3h.mainshell.location.GpsLocation;
import com.sh3h.mainshell.util.AndroidComponentUtil;
import com.sh3h.mainshell.util.DeviceUtil;
import com.sh3h.mobileutil.util.LogUtil;
import com.sh3h.mobileutil.util.TextUtil;

import org.json.JSONObject;

import javax.inject.Inject;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


/**
 * Created by dengzhimin on 2016/5/24.
 */
public class KeepAliveService extends Service {
    @Inject
    DataManager mDataManager;

    @Inject
    ConfigHelper mConfigHelper;

    @Inject
    PreferencesHelper mPreferencesHelper;

    private static final String TAG = "KeepAliveService";
    private final static int DELAY_IN_MILLISECONDS_DEFAULT = SystemConfig.KEEP_LIVE_INTERVAL_DEFAULT_VALUE;
    private final static int GRAY_SERVICE_ID = 1001;
    private MainApplication mMainApplication;
    private Subscription mSubscription;
    private int interval;
    private boolean mIsScreenOff;

    public static Intent getStartIntent(Context context) {
        return new Intent(context, KeepAliveService.class);
    }

    public static boolean isRunning(Context context) {
        return AndroidComponentUtil.isServiceRunning(context, KeepAliveService.class);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        MainApplication.get(this).getComponent().inject(this);
        LogUtil.i(TAG, "---onCreate---");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        mMainApplication = MainApplication.get(this);
        interval = mConfigHelper.getKeepAliveInterval();
        if (interval <= 0){
            interval = DELAY_IN_MILLISECONDS_DEFAULT;
        }
        LogUtil.i(TAG, "---onStartCommand---" + interval);

        if (Build.VERSION.SDK_INT < 18) {
            startForeground(GRAY_SERVICE_ID, new Notification());//API < 18 ，此方法能有效隐藏Notification上的图标
        } else {
            Intent innerIntent = new Intent(this, GrayInnerService.class);
            startService(innerIntent);
            startForeground(GRAY_SERVICE_ID, new Notification());
        }

        mIsScreenOff = true;
        process();

        return START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mSubscription != null) {
            mSubscription.unsubscribe();
        }

        mIsScreenOff = false;
    }

    private void process() {
        if (mIsScreenOff) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    sendData();
                }
            }, interval);
        }
    }

    private void sendData() {
        LogUtil.i(TAG, "sendData");
        try {
            if ((mSubscription != null) && (!mSubscription.isUnsubscribed())) {
                mSubscription.unsubscribe();
            }

            DULocationTrack duLocationTrack = new DULocationTrack();
            DULocationTrack.Location location = duLocationTrack.new Location();
            duLocationTrack.setLocation(location);

            getUserInfo(duLocationTrack, location);
            getDeviceInfo(location);
            getLocation(duLocationTrack, location);
            getDate(location);
            getDeviceToken(location);

            mSubscription = mDataManager.sendTrack(duLocationTrack, false)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Subscriber<DUEntityResult<DUTrackResult>>() {
                        @Override
                        public void onCompleted() {
                            LogUtil.i(TAG, "onCompleted");
                        }

                        @Override
                        public void onError(Throwable e) {
                            LogUtil.e(TAG, "onError " + e.getMessage());
                        }

                        @Override
                        public void onNext(DUEntityResult<DUTrackResult> duTrackResultDUEntityResult) {
                            long timeError = duTrackResultDUEntityResult.getData().getTime() - System.currentTimeMillis();
                            LogUtil.i(TAG, "onNext timeError: " + timeError);
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
            LogUtil.e(TAG, e.getMessage());
        } finally {
            process();
        }
    }

    /**
     * 获取共享的USER信息
     */
    private void getUserInfo(DULocationTrack duLocationTrack,
                             DULocationTrack.Location location) {
        int userId = mPreferencesHelper.getUserSession().getUserId();
        duLocationTrack.setUserId(userId);
        location.setUserId(userId);
    }

    /**
     * 获取手机设备id，电话号码
     */
    private void getDeviceInfo(DULocationTrack.Location location) {
        location.setDeviceId(DeviceUtil.getDeviceID(this));
    }

    /**
     * 获取经纬度
     */
    private void getLocation(DULocationTrack duLocationTrack,
                             DULocationTrack.Location location) {
        boolean isGpsLocated = mMainApplication.isGpsLocated();
        GpsLocation.MRLocation mrLocation = mMainApplication.getMRLocation();
        if ((!isGpsLocated) || (mrLocation == null)) {
            location.setLongitude(0);
            location.setLatitude(0);
            location.setRadius(0);
            location.setAltitude(0);
            location.setDirection(0);
            location.setSpeed(0);
            location.setLocationType("wgs84");
        } else {
            location.setLongitude(mrLocation.getLongitude());
            location.setLatitude(mrLocation.getLatitude());
            location.setRadius(mrLocation.getAccuracy());
            location.setAltitude(mrLocation.getAltitude());
            location.setDirection(mrLocation.getDirection());
            location.setSpeed(mrLocation.getSpeed());
            location.setLocationType("wgs84");
        }
    }

    private void getDate(DULocationTrack.Location location) {
        location.setTime(System.currentTimeMillis());
    }

    private void getDeviceToken(DULocationTrack.Location location) {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("deviceToken", TextUtil.getString(mMainApplication.getDeviceToken()));
            jsonObject.put("accessToken", mPreferencesHelper.getUserSession().getAccessToken());
            jsonObject.put("hwPushToken", mMainApplication.getHwPushToken());
            location.setExtend(jsonObject.toString());
        } catch (Exception e) {
            e.printStackTrace();
            LogUtil.e(TAG, e.getMessage());
        }
    }
}
