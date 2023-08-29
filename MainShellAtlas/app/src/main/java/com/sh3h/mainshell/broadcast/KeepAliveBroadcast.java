package com.sh3h.mainshell.broadcast;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.SystemClock;
import android.util.Config;

import com.mainshell.datautil.data.DataManager;
import com.mainshell.datautil.data.entity.DULocationTrack;
import com.mainshell.datautil.data.entity.base.DUEntityResult;
import com.mainshell.datautil.data.entity.result.DUTrackResult;
import com.mainshell.datautil.data.local.config.ConfigHelper;
import com.mainshell.datautil.data.local.config.SystemConfig;
import com.mainshell.datautil.data.local.preference.PreferencesHelper;
import com.sh3h.mainshell.MainApplication;
import com.sh3h.mainshell.location.GpsLocation;
import com.sh3h.mainshell.util.DeviceUtil;
import com.sh3h.mobileutil.util.LogUtil;
import com.sh3h.mobileutil.util.TextUtil;

import org.json.JSONObject;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by dengzhimin on 2016/5/24.
 */
public class KeepAliveBroadcast extends BroadcastReceiver {
    public final static String KEEP_ALIVE_BROADCAST_ACTION = "com.sh3h.mainshell.keepalivebroadcast";
    private final static String TAG = "KeepAliveBroadcast";
    private final static int DELAY_IN_MILLISECONDS_DEFAULT = SystemConfig.KEEP_LIVE_INTERVAL_DEFAULT_VALUE;

    //    private DUKeepAlive mDuKeepAlive;
    private DULocationTrack mDULocationTrack;
    private DULocationTrack.Location mLocation;
    // notice: don't use inject here
    private MainApplication mMainApplication;
    private DataManager mDataManager;
    private ConfigHelper mConfigHelper;
    private PreferencesHelper mPreferencesHelper;
    private GpsLocation mGpsLocation;
    private Subscription mSubscription;
    private static boolean mNeedClearTrack = true;
    private static long mDelayInMilliseconds;

    @Override
    public void onReceive(Context context, Intent intent) {
        LogUtil.i(TAG, "----onReceive----");

        try {
            if ((context != null)
                    && (intent != null)
                    && intent.getAction().equals(KeepAliveBroadcast.class.getCanonicalName())) {
                mMainApplication = MainApplication.get(context);
                mDataManager = mMainApplication.getDataManager();
                mConfigHelper = mMainApplication.getConfigHelper();
                mPreferencesHelper = mMainApplication.getPreferencesHelper();
                mGpsLocation = mMainApplication.getGpsLocation();
                mDelayInMilliseconds = mConfigHelper.getKeepAliveInterval();

                mDULocationTrack = new DULocationTrack();
                mLocation = mDULocationTrack.new Location();
                mDULocationTrack.setLocation(mLocation);

//          getAppInfo(context);
                getUserInfo();
                getDeviceInfo();
                getLocation();
                getDate();
                getDeviceToken();
                sendData();
            }
        } catch (Exception e) {
            e.printStackTrace();
            LogUtil.e(TAG, e.getMessage());
            schedule();
        }
    }

    private void schedule() {
        if (mDelayInMilliseconds <= 0) {
            mDelayInMilliseconds = DELAY_IN_MILLISECONDS_DEFAULT;
        }

        schedule(mMainApplication, mDelayInMilliseconds);
    }

    private void sendData() {
        LogUtil.i(TAG, "sendData");

        if (mSubscription != null && !mSubscription.isUnsubscribed()) {
            mSubscription.unsubscribe();
            LogUtil.i(TAG, "sendData unsubscribe");
        }

        mSubscription = mDataManager.sendTrack(mDULocationTrack, mNeedClearTrack)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<DUEntityResult<DUTrackResult>>() {
                    @Override
                    public void onCompleted() {
                        LogUtil.i(TAG, "onCompleted");
                        mNeedClearTrack = false;
                        schedule();
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.e(TAG, "onError " + e.getMessage());
                        schedule();
                    }

                    @Override
                    public void onNext(DUEntityResult<DUTrackResult> duTrackResultDUEntityResult) {
                        long timeError = duTrackResultDUEntityResult.getData().getTime() - System.currentTimeMillis();
                        LogUtil.i(TAG, "onNext timeError: " + timeError);
                        mMainApplication.setTimeError(timeError);
                    }
                });
    }

    /**
     * 获取共享的USER信息
     */
    private void getUserInfo() {
        if ((mPreferencesHelper == null)
                || (mDULocationTrack == null)
                || (mLocation == null)) {
            return;
        }

        int userId = mPreferencesHelper.getUserSession().getUserId();
        mDULocationTrack.setUserId(userId);
        mLocation.setUserId(userId);
    }

    /**
     * 获取手机设备id，电话号码
     */
    private void getDeviceInfo() {
        if ((mMainApplication == null)
                || (mLocation == null)) {
            return;
        }

        mLocation.setDeviceId(DeviceUtil.getDeviceID(mMainApplication));
    }

    /**
     * 获取经纬度
     */
    private void getLocation() {
        if ((mMainApplication == null)
                || (mDULocationTrack == null)
                || (mLocation == null)
                || (mGpsLocation == null)) {
            return;
        }

        boolean isGpsLocated = mGpsLocation.isGpsLocated();
        GpsLocation.MRLocation mrLocation = mGpsLocation.getMRLocation();
        if ((!isGpsLocated) || (mrLocation == null)) {
            mLocation.setLongitude(0);
            mLocation.setLatitude(0);
            mLocation.setRadius(0);
            mLocation.setAltitude(0);
            mLocation.setDirection(0);
            mLocation.setSpeed(0);
            mLocation.setLocationType("wgs84");
        } else {
            mLocation.setLongitude(mrLocation.getLongitude());
            mLocation.setLatitude(mrLocation.getLatitude());
            mLocation.setRadius(mrLocation.getAccuracy());
            mLocation.setAltitude(mrLocation.getAltitude());
            mLocation.setDirection(mrLocation.getDirection());
            mLocation.setSpeed(mrLocation.getSpeed());
            mLocation.setLocationType("wgs84");
        }
    }

    private void getDate() {
        if (mLocation == null) {
            return;
        }

        mLocation.setTime(System.currentTimeMillis());
    }

    private void getDeviceToken() {
        if (mMainApplication == null) {
           return;
        }

        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("deviceToken", TextUtil.getString(mMainApplication.getDeviceToken()));
            jsonObject.put("accessToken", mPreferencesHelper.getUserSession().getAccessToken());
            jsonObject.put("hwPushToken", mMainApplication.getHwPushToken());
            mLocation.setExtend(jsonObject.toString());
        } catch (Exception e) {
            e.printStackTrace();
            LogUtil.e(TAG, e.getMessage());
        }
    }

    public static void schedule(Context context, long delayInMilliseconds) {
        if (delayInMilliseconds <= 0) {
            delayInMilliseconds = DELAY_IN_MILLISECONDS_DEFAULT;
        }

        Intent intent = new Intent(context, KeepAliveBroadcast.class);
        intent.setAction(KeepAliveBroadcast.class.getCanonicalName());
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        long triggerAtMillis = System.currentTimeMillis() + delayInMilliseconds;
        /*if (Build.VERSION.SDK_INT >= 23) {
            if (mIsScreenOff) {
                alarmManager.setExactAndAllowWhileIdle(AlarmManager.ELAPSED_REALTIME_WAKEUP,
                        triggerAtMillis, pendingIntent);
            } else {
                alarmManager.setExact(AlarmManager.RTC_WAKEUP, triggerAtMillis,
                        pendingIntent);
            }
        } else */if (Build.VERSION.SDK_INT >= 19) {
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, triggerAtMillis,
                    pendingIntent);
        } else {
            alarmManager.set(AlarmManager.RTC_WAKEUP, triggerAtMillis,
                    pendingIntent);
        }

        mDelayInMilliseconds = delayInMilliseconds;
    }

    public static void cancel(Context context) {
        Intent intent = new Intent(context, KeepAliveBroadcast.class);
        intent.setAction(KeepAliveBroadcast.class.getCanonicalName());
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(pendingIntent);
    }

//    public static void setScreenOff(Context context, boolean isScreenOff) {
//        try {
//            if (isScreenOff) {
//                cancel(context);
//                context.startService(KeepAliveService.getStartIntent(context));
//            } else {
//                schedule(context, MainApplication.get(context).getConfigHelper().getKeepAliveInterval());
//                context.stopService(KeepAliveService.getStartIntent(context));
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            LogUtil.e(TAG, e.getMessage());
//        }
//    }
}
