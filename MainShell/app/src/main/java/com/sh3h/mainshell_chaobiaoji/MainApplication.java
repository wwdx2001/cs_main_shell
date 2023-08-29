package com.sh3h.mainshell_chaobiaoji;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Application;
import android.app.Notification;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.PowerManager;
import android.os.Process;
import android.os.RemoteException;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.multidex.MultiDex;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.huawei.hms.api.ConnectionResult;
import com.huawei.hms.api.HuaweiApiAvailability;
import com.huawei.hms.api.HuaweiApiClient;
import com.huawei.hms.support.api.client.PendingResult;
import com.huawei.hms.support.api.client.ResultCallback;
import com.huawei.hms.support.api.push.HuaweiPush;
import com.huawei.hms.support.api.push.TokenResult;
import com.sh3h.datautil.data.DataManager;
import com.sh3h.datautil.data.entity.DUFileResult;
import com.sh3h.datautil.data.entity.DULogoutInfo;
import com.sh3h.datautil.data.entity.DUTokenInfo;
import com.sh3h.datautil.data.entity.DUUpdateXmlFile;
import com.sh3h.datautil.data.entity.result.DUTokenResult;
import com.sh3h.datautil.data.local.config.ConfigHelper;
import com.sh3h.datautil.data.local.config.SystemConfig;
import com.sh3h.datautil.data.local.preference.PreferencesHelper;
import com.sh3h.datautil.data.local.preference.UserSession;
import com.sh3h.datautil.data.local.xml.XmlHelper;
import com.sh3h.datautil.util.EventPosterHelper;
import com.sh3h.ipc.IMainService;
import com.sh3h.ipc.IRemoteServiceCallback;
import com.sh3h.ipc.location.MyLocation;
import com.sh3h.ipc.module.MyModule;
import com.sh3h.mainshell_chaobiaoji.broadcast.KeepAliveBroadcast;
import com.sh3h.mainshell_chaobiaoji.broadcast.NetworkReceiver;
import com.sh3h.mainshell_chaobiaoji.event.UIBusEvent;
import com.sh3h.mainshell_chaobiaoji.injection.component.ApplicationComponent;
import com.sh3h.mainshell_chaobiaoji.injection.component.DaggerApplicationComponent;
import com.sh3h.mainshell_chaobiaoji.injection.module.ApplicationModule;
import com.sh3h.mainshell_chaobiaoji.location.GpsLocation;
import com.sh3h.mainshell_chaobiaoji.provider.SharedData;
import com.sh3h.mainshell_chaobiaoji.service.FileService;
import com.sh3h.mainshell_chaobiaoji.service.HostService;
import com.sh3h.mainshell_chaobiaoji.service.KeepAliveService;
import com.sh3h.mainshell_chaobiaoji.track.BaiduTrack;
import com.sh3h.mainshell_chaobiaoji.util.DeviceUtil;
import com.sh3h.mainshell_chaobiaoji.util.PatchUtils;
import com.sh3h.mainshell_chaobiaoji.util.ProcessManager;
import com.sh3h.mainshell_chaobiaoji.util.SignUtils;
import com.sh3h.mainshell_chaobiaoji.util.SoundUtils;
import com.sh3h.mobileutil.util.ApplicationsUtil;
import com.sh3h.mobileutil.util.LogUtil;
import com.sh3h.mobileutil.util.TextUtil;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;
import com.umeng.analytics.MobclickAgent;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.message.IUmengRegisterCallback;
import com.umeng.message.MsgConstant;
import com.umeng.message.PushAgent;
import com.umeng.message.UTrack;
import com.umeng.message.UmengMessageHandler;
import com.umeng.message.UmengNotificationClickHandler;
import com.umeng.message.common.UmLog;
import com.umeng.message.entity.UMessage;

import org.android.agoo.huawei.HuaWeiRegister;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static com.sh3h.datautil.data.entity.result.DUTokenResult.TOKEN_STATE_EXIT;
import static com.sh3h.datautil.data.entity.result.DUTokenResult.TOKEN_STATE_UPDATE;
import static com.sh3h.ipc.module.MyModule.CLEAR_CACHE;
import static com.sh3h.ipc.module.MyModule.GPS_NOT_OPENED;
import static com.sh3h.ipc.module.MyModule.LOGOUT_MAIN_SYSTEM;
import static com.sh3h.ipc.module.MyModule.LOGOUT_SUB_SYSTEM;
import static com.sh3h.ipc.module.MyModule.PUSH_MESSAGE;
import static com.sh3h.ipc.module.MyModule.RESTORE_FACTORY;
import static com.sh3h.mainshell_chaobiaoji.service.HostService.BINDING_NAME;

//import com.sh3h.ipc.IMapAidlInterface;

public class MainApplication extends Application implements GpsLocation.MRLocationListener {
    private static final String TAG = "MainApplication";
    public static final String MAP_PACKAGE_NAME = "com.sh3h.citymap";
    public static final String MAP_AIDL_SERVICE_NAME = "com.sh3h.citymap.service.MapAidlService";
    //public static final String HOST_AIDL_SERVICE_NAME = "com.sh3h.mainshell.service.HostService";
    private static final String PACKAGE_BASE_NAME = "com.sh3h";
    public static final String UPDATE_STATUS_ACTION = "com.umeng.message.example.action.UPDATE_STATUS";
    private static final String HOST_PROCESS_NAME = "com.sh3h.mainshell:host";
    private static final String REMOTE_PROCESS_NAME = "com.sh3h.mainshell:remote";
    private static final String CHANNEL_PROCESS_NAME = "com.sh3h.mainshell:channel";
    private static final int MESSAGE_ERROR_TYPE = -1;
    public static final String SUPER_ADMIN_JM = "rhadmin";
    public static final String SUPER_PASSWORD_JM = "rhadmin";
    public static final String SUPER_ADMIN_DEFAULT = "sh3h";
    public static final String SUPER_PASSWORD_DEFAULT = "sh3h";
    public static final int APP_INSTALLING_REQUEST_CODE = 22333;

    @Inject
    Bus mEventBus;

    @Inject
    DataManager mDataManager;

    @Inject
    ConfigHelper mConfigHelper;

    @Inject
    XmlHelper mXmlHelper;

    @Inject
    PreferencesHelper mPreferencesHelper;

    @Inject
    GpsLocation mGpsLocation;

    @Inject
    EventPosterHelper mEventPosterHelper;

    private ApplicationComponent mApplicationComponent;

    private long timeError;

    private FileService fileService;

    //private IMapAidlInterface mapAidlInterface;
    private IMainService mainService;
    //private IRemoteService remoteService;

    private boolean isMonitorInit;

    private boolean isKeepAliveStarted;

    private boolean isUmengPushStarted;

    private Subscription mSubscription;

    private BufferedReader mBufferedReader;//release later to save time

    private boolean mIsMainShellProcess;
    private boolean isScreenOn;
    private PowerManager.WakeLock wakeLock;
    private HuaweiApiClient huaweiApiClient;
    private String hwPushToken;
    private boolean isHwPushConnecting;
    private BroadcastReceiver mNetworkReceiver;
    private boolean mIsGreenTheme;
    private Handler uPushHandler;
    private BaiduTrack baiduTrack;
    private boolean isBaiduTrackStarted;
    private List<Activity> mActivityList;
    private String appChannel;
    private boolean isTokenCheckingReady;
    private boolean isTokenCheckingFinish;
    //private Set<String> messageCache;

    public MainApplication() {
        mApplicationComponent = null;
        timeError = 0;
        fileService = null;
        //mapAidlInterface = null;
        mainService = null;
        //remoteService = null;
        isMonitorInit = false;
        isKeepAliveStarted = false;
        mSubscription = null;
        isUmengPushStarted = false;
        mIsMainShellProcess = false;
        isScreenOn = true;
        wakeLock = null;
        huaweiApiClient = null;
        hwPushToken = null;
        isHwPushConnecting = false;
        mNetworkReceiver = null;
        mIsGreenTheme = false;
        uPushHandler = null;
        baiduTrack = null;
        isBaiduTrackStarted = false;
        mActivityList = null;
        appChannel = null;
        isTokenCheckingReady = false;
        isTokenCheckingFinish = true;
        //messageCache = null;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        CrashHandler crashHandler = CrashHandler.getInstance();
        crashHandler.init(this);

        //LeakCanary.install(this);

        mApplicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .build();
        mApplicationComponent.inject(this);

        mEventBus.register(this);
        initLogger();
        //startUmengPush();
        initUpush();
        if (mIsMainShellProcess) {
            setAppChannel();
            setAppTheme();
            IntentFilter filter = new IntentFilter();
            filter.addAction(Intent.ACTION_SCREEN_OFF);
            filter.addAction(Intent.ACTION_SCREEN_ON);
            registerReceiver(mBatInfoReceiver, filter);
            //startHWPush();
            bindHostService();
            monitorNetworkState();
            mActivityList = new ArrayList<>();
            //messageCache = Collections.synchronizedSet(new HashSet<String>());
        }
    }

    private void startHWPush() {
        String manufacture = TextUtil.getString(android.os.Build.MANUFACTURER);
        if (isConnected()
                || !manufacture.equalsIgnoreCase("HUAWEI")
                || isHwPushConnecting
                || !mIsMainShellProcess) {
            return;
        }

        isHwPushConnecting = true;
        huaweiApiClient = new HuaweiApiClient.Builder(this)
                .addApi(HuaweiPush.PUSH_API)
                .addConnectionCallbacks(new HuaweiApiClient.ConnectionCallbacks() {
                    @Override
                    public void onConnected() {
                        LogUtil.i(TAG, "onConnected, IsConnected: "
                                + huaweiApiClient.isConnected());
                        onToken();
                        isHwPushConnecting = false;
                    }

                    @Override
                    public void onConnectionSuspended(int cause) {
                        LogUtil.i(TAG, "onConnectionSuspended, cause: "
                                + cause + ", IsConnected: " + huaweiApiClient.isConnected());
                        huaweiApiClient.connect();
                        isHwPushConnecting = true;
                    }
                })
                .addOnConnectionFailedListener(new HuaweiApiClient.OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
                        LogUtil.i(TAG, "onConnectionFailed, ErrorCode: "
                                + connectionResult.getErrorCode());
                        isHwPushConnecting = false;
                    }
                }).build();

        LogUtil.i(TAG, "onStart, ErrorCode: "
                + HuaweiApiAvailability.getInstance().isHuaweiMobileServicesAvailable(this));
        huaweiApiClient.connect();
    }

    private boolean isConnected() {
        return huaweiApiClient != null && huaweiApiClient.isConnected();
    }

//    @Override
//    public void onUpdateFailed(ConnectionResult result) {
//        LogUtil.i(TAG, "onUpdateFailed, ErrorCode: " + result.getErrorCode());
//        isHwPushConnecting = false;
//    }

    private void onToken() {
        // 异步调用方式
        PendingResult<TokenResult> tokenResult = HuaweiPush.HuaweiPushApi.getToken(huaweiApiClient);
        tokenResult.setResultCallback(new ResultCallback<TokenResult>() {
            @Override
            public void onResult(TokenResult result) {
                LogUtil.i(TAG, "onToken is ok");
                setPassByMsg(true);
            }
        });
    }

    // 设置透传消息开关
    private void setPassByMsg(boolean flag) {
        HuaweiPush.HuaweiPushApi.enableReceiveNormalMsg(huaweiApiClient, flag);
    }

    public void setHwPushToken(String hwPushToken) {
        this.hwPushToken = hwPushToken;
    }

    public String getHwPushToken() {
        return hwPushToken;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        mEventBus.unregister(this);
        if (mIsMainShellProcess) {
            unregisterReceiver(mBatInfoReceiver);
        }
    }

    @Override
    public void updateLocation(boolean isLocated, GpsLocation.MRLocation mrLocation, long time) {
        try {
            if (mainService != null) {
                MyLocation myLocation = new MyLocation(isLocated, mrLocation.getLongitude(),
                        mrLocation.getLatitude(), mrLocation.getDirection(), mrLocation.getAccuracy(),
                        mrLocation.getLocalLongitude(), mrLocation.getLocalLatitude());
                Log.i(TAG, "setLocation" + mrLocation.toString());
                mainService.setLocation(myLocation);
            }

            if (time > 0) {
                timeError = time - System.currentTimeMillis();
            }
        } catch (RemoteException exception) {
            exception.printStackTrace();
            Log.e(TAG, "---updateLocation---" + exception.getMessage());
        }
    }

    public static MainApplication get(Context context) {
        return (MainApplication) context.getApplicationContext();
    }

    public ApplicationComponent getComponent() {
        return mApplicationComponent;
    }

    // Needed to replace the component with a test specific one
    public void setComponent(ApplicationComponent applicationComponent) {
        mApplicationComponent = applicationComponent;
    }

    public DataManager getDataManager() {
        return mDataManager;
    }

    public ConfigHelper getConfigHelper() {
        return mConfigHelper;
    }

    public PreferencesHelper getPreferencesHelper() {
        return mPreferencesHelper;
    }

    public GpsLocation getGpsLocation() {
        return mGpsLocation;
    }

    public void setTimeError(long timeError) {
        this.timeError = timeError;

        try {
            if (mainService != null) {
                mainService.setTimeError(timeError);
            }
        } catch (RemoteException exception) {
            exception.printStackTrace();
            Log.e(TAG, "---setTimeError---" + exception.getMessage());
        }
    }

    public long getCurrentTime() {
        long time = new Date().getTime();
        return time + timeError;
    }

    public Date getCurrentDate() {
        return new Date(getCurrentTime());
    }

    public SystemConfig.MonitorType getMonitorType() {
        return mConfigHelper.getMonitorType();
    }

    public boolean isMonitorInit() {
        return isMonitorInit;
    }

    public void initMonitor() {
        if (isMonitorInit) {
            return;
        }

        SystemConfig.MonitorType monitorType = getMonitorType();
        if (monitorType == SystemConfig.MonitorType.NONE) {

        } else if (monitorType == SystemConfig.MonitorType.COUTLY) {
//            String countlyUri = mConfigHelper.getCountlyUri();
//            if (TextUtil.isNullOrEmpty(countlyUri)) {
//                countlyUri = SystemConfig.DEFAULT_COUNTLY_SERVER_URI;
//            }
//
//            //开启调试日志
//            Countly.sharedInstance().setLoggingEnabled(true);
//            //初始化Countly
//            Countly.sharedInstance().init(this, countlyUri, "839ca93face2d2046379ebc1845033b4c4e06778");
//            //启用崩溃报告
//            Countly.sharedInstance().enableCrashReporting();
//            //设置地理位置
//            Countly.sharedInstance().setLocation(11.11, 22.22);
//            //无网络连接 关闭每隔60秒上传更新session
//            if (!NetworkUtil.isNetworkConnected(this)) {
//                Countly.sharedInstance().setDisableUpdateSessionRequests(true);
//            }
//
//            isMonitorInit = true;
        } else if (monitorType == SystemConfig.MonitorType.BUGLY) {
            /* Bugly SDK初始化
            * 参数1：上下文对象
            * 参数2：APPID，平台注册时得到,注意替换成你的appId
            * 参数3：是否开启调试模式，调试模式下会输出'CrashReport'tag的日志
            */
//            CrashReport.UserStrategy strategy = new CrashReport.UserStrategy(this);
//            strategy.setAppChannel("sh3h");  //设置渠道
//            strategy.setAppVersion(SystemUtil.getVersionName(this));      //App的版本
//            strategy.setAppPackageName(getPackageName());  //App的包名
//            CrashReport.initCrashReport(getApplicationContext(), "32b43124d2",
//                    mConfigHelper.isDebugMode(), strategy);
//            CrashReport.setUserId(DeviceUtil.getDeviceID(this));
//            isMonitorInit = true;
        } else if (monitorType == SystemConfig.MonitorType.UMENG) {
            //开启调试模式
            MobclickAgent.setDebugMode(mConfigHelper.isDebugMode());
            // SDK在统计Fragment时，需要关闭Activity自带的页面统计，
            // 然后在每个页面中重新集成页面统计的代码(包括调用了 onResume 和 onPause 的Activity)。
            MobclickAgent.openActivityDurationTrack(false);
            //设置统计场景类型
            MobclickAgent.setScenarioType(this, MobclickAgent.EScenarioType.E_UM_NORMAL);
            //设置是否对日志信息进行加密, 默认false(不加密)
            MobclickAgent.enableEncrypt(false);
            MobclickAgent.onProfileSignIn(DeviceUtil.getDeviceID(this));
            isMonitorInit = true;
        }
    }

    public void destroyMonitor() {
        if (isMonitorInit) {
            isMonitorInit = false;
//            if (Countly.sharedInstance().isInitialized()) {
//                Countly.sharedInstance().halt();
//            }
        }
    }

    /**
     * init gps location module
     */
    public void initGpsLocation() {
        boolean b = false;
        String gpsType = mConfigHelper.getGpsType();
        String locationUrl = mConfigHelper.getLocationUrl();
        int scanSpan = mConfigHelper.getLocationScanSpan();
        switch (gpsType) {
            case SystemConfig.GPS_TYPE_NONE:
                break;
            case SystemConfig.GPS_TYPE_SYSTEM:
                b = mGpsLocation.initLocation(false, locationUrl, scanSpan);
                break;
            case SystemConfig.GPS_TYPE_BAIDU:
                b = mGpsLocation.initLocation(true, locationUrl, scanSpan);
                break;
            default:
                break;
        }

        if (b) {
            mGpsLocation.registerMRLocationListener(this);
        }

        isTokenCheckingReady = true;
    }

    /**
     * destroy gps location module
     */
    public void destroyGpsLocation() {
        boolean b = false;
        String gpsType = mConfigHelper.getGpsType();
        switch (gpsType) {
            case SystemConfig.GPS_TYPE_NONE:
                break;
            case SystemConfig.GPS_TYPE_SYSTEM:
                b = mGpsLocation.destroyLocation(false);
                break;
            case SystemConfig.GPS_TYPE_BAIDU:
                b = mGpsLocation.destroyLocation(true);
                break;
            default:
                break;
        }
        if (b) {
            mGpsLocation.unRegisterMRLocationListener(this);
        }
    }

    public boolean isGpsLocated() {
        return mGpsLocation.isGpsLocated();
    }

    public GpsLocation.MRLocation getMRLocation() {
        return mGpsLocation.getMRLocation();
    }

    public void saveSharedData(Map<String, String> map) {
        if (map == null || map.size() <= 0) {
            return;
        }

        ContentValues[] contentValuesList = new ContentValues[map.size()];
        int i = 0;
        for (Map.Entry<String, String> entry : map.entrySet()) {
            ContentValues contentValues = new ContentValues();
            contentValues.put(SharedData.SD_KEY, entry.getKey());
            contentValues.put(SharedData.SD_VALUE, entry.getValue());
            contentValuesList[i++] = contentValues;
        }

        int count = getContentResolver().bulkInsert(SharedData.CONTENT_URI, contentValuesList);
        LogUtil.i(TAG, "" + count);
    }

    public void saveSharedData(String key, String value) {
        if (TextUtil.isNullOrEmpty(key) || TextUtil.isNullOrEmpty(value)) {
            return;
        }

        ContentValues contentValues = new ContentValues();
        contentValues.put(SharedData.SD_KEY, key);
        contentValues.put(SharedData.SD_VALUE, value);
        Uri uri = getContentResolver().insert(SharedData.CONTENT_URI, contentValues);
        if (uri != null) {
            LogUtil.i(TAG, TextUtil.getString(uri.toString()));
        }
    }

    public void destroy() {
        stopBaiduTrack();
        releaseNetworkState();
        stopKeepAlive();
        destroyGpsLocation();
        stopPush();
        unbindFileService();
        destroyMonitor();

        try {
            if (mainService != null) {
                mainService.exitSystem();
            }

            getContentResolver().delete(SharedData.CONTENT_URI, null, null);
        } catch (RemoteException exception) {
            exception.printStackTrace();
            Log.e(TAG, "---onDestroy---" + exception.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG, "---onDestroy---" + e.getMessage());
        }
    }

    public void bindFileService() {
        Log.i(TAG, "---bindFileService---1");
        if (fileService == null) {
            Log.i(TAG, "---bindFileService---2");
            Intent intent = FileService.getStartIntent(this);
            bindService(intent, fileServiceConnection, Context.BIND_AUTO_CREATE);
            intent.putExtra(FileService.FIRST_TIME, true);
            startService(intent);
        }
    }

    public void starFileService(Intent intent) {
        Log.i(TAG, "---starFileService---1");
        if (fileService != null) {
            Log.i(TAG, "---starFileService---2");
            startService(intent);
        }
    }

    public void unbindFileService() {
        Log.i(TAG, "---unbindFileService---1");
        if (fileService != null) {
            Log.i(TAG, "---unbindFileService---2");
            Intent intent = FileService.getStartIntent(this);
            stopService(intent);
            unbindService(fileServiceConnection);
            fileService = null;
        }
    }

    public DUFileResult getDUFileResult() {
        return (fileService != null) ? fileService.getDUFileResult() : null;
    }

    public boolean isFileServiceOperationFinished() {
        return fileService != null && fileService.isOperationFinished();
    }

    public void startKeepAlive() {
        if (!isKeepAliveStarted) {
            int interval = mConfigHelper.getKeepAliveInterval();
            isKeepAliveStarted = true;
            KeepAliveBroadcast.schedule(this, 100);
        }
    }

    public void stopKeepAlive() {
        if (isKeepAliveStarted) {
            isKeepAliveStarted = false;
            KeepAliveBroadcast.cancel(this);
        }
    }

//    public void bindMapAidlService() {
//        Log.i(TAG, "---bindMapAidlService---1");
//        if (mapAidlInterface == null) {
//            Log.i(TAG, "---bindMapAidlService---2");
//            Intent intent = new Intent(MAP_AIDL_SERVICE_NAME);
//            intent = createExplicitFromImplicitIntent(this, intent);
//            if (intent != null) {
//                bindService(intent, mapServiceConnection, BIND_AUTO_CREATE);
//            }
//        }
//    }
//
//    public void unbindMapAidlService() {
//        Log.i(TAG, "---unbindMapAidlService---1");
//        if (mapAidlInterface != null) {
//            Log.i(TAG, "---unbindMapAidlService---2");
//            unbindService(mapServiceConnection);
//            mapAidlInterface = null;
//        }
//    }

    public void bindHostService() {
        Log.i(TAG, "---bindHostService---1");
        if (mainService == null) {
            Log.i(TAG, "---bindHostService---2");
            Intent intent = new Intent(this, HostService.class);
            intent.putExtra(BINDING_NAME, IMainService.class.getName());
            //intent.setAction(IMainService.class.getName());
            bindService(intent, mainConnection, BIND_AUTO_CREATE);
        }

//        if (remoteService == null) {
//            Log.i(TAG, "---bindHostService---3");
//            Intent intent = new Intent(this, HostService.class);
//            //intent.putExtra(BINDING_NAME, IRemoteService.class.getName());
//            intent.setAction(IRemoteService.class.getName());
//            bindService(intent, remoteConnection, Context.BIND_AUTO_CREATE);
//        }
    }

    public void unbindHostService() {
        Log.i(TAG, "---unbindHostService---1");
        if (mainService != null) {
            Log.i(TAG, "---unbindHostService---2");
            unbindService(mainConnection);
            try {
                int[] pIds = mainService.getPids();
                if (pIds != null && pIds.length > 0) {
                    for (int pid : pIds) {
                        Process.killProcess(pid);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            mainService = null;
        }

//        if (remoteService != null) {
//            Log.i(TAG, "---unbindHostService---3");
//            unbindService(remoteConnection);
//            remoteService = null;
//        }
    }

    public boolean killChildApps() {
        return true;
    }

    public void startPush() {
//        if (isUmengPushStarted) {
//            // do nothing
//        } else {
//            mqttHandler.start();
//        }

        //startHWPush();
        if (isUmengPushStarted) {
            PushAgent.getInstance(this).onAppStart();
        }
    }

    public void stopPush() {
//        if (isUmengPushStarted) {
//            // do nothing
//        } else {
//            mqttHandler.stop();
//        }
    }

    public void sendClearingCacheMessage() {
        mHandler.sendMessage(mHandler.obtainMessage(MY_MSG_CLEAR_CACHE));
    }

    public void sendRestoringFactoryMessage() {
        mHandler.sendMessage(mHandler.obtainMessage(MY_MSG_RESTORE_FACTORY));
    }

    public void sendPhotoQualityMessage(boolean isNormal) {
        mHandler.sendMessage(mHandler.obtainMessage(MY_MSG_PHOTO_QUALITY, isNormal));
        saveSharedData(MyModule.PHOTO_QUALITY, isNormal ? "normal" : "high");
    }

    public void sendNetworkMessage(boolean isOuter) {
        mHandler.sendMessage(mHandler.obtainMessage(MY_MSG_NETWORK_CHANGED, isOuter));
        saveSharedData(MyModule.OUTER_NETWORK, isOuter ? "outer" : "inner");
    }

    public void sendGpsNotOpenedMessage() {
        mHandler.sendMessage(mHandler.obtainMessage(MY_MSG_GPS_NOT_OPENED));
    }

    public void sendLogoutSystemMessage() {
        mHandler.sendMessage(mHandler.obtainMessage(MY_MSG_LOGOUT_SYSTEM));
    }

    public void startBaiduTrack() {
        if (mConfigHelper.needBaiduTrack() && !isBaiduTrackStarted) {
            isBaiduTrackStarted = true;
            baiduTrack = new BaiduTrack(this);
            baiduTrack.init();
        }
    }

    public void stopBaiduTrack() {
        if (isBaiduTrackStarted && baiduTrack != null) {
            isBaiduTrackStarted = false;
            baiduTrack.destroy();
        }
    }

    public void clearCacheTrack() {
        if (isBaiduTrackStarted && baiduTrack != null) {
            baiduTrack.clearCacheTrack();
        }
    }

    public void add2WhiteList() {
        // 在Android 6.0及以上系统，若定制手机使用到doze模式，请求将应用添加到白名单。
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            String packageName = getPackageName();
            PowerManager powerManager = (PowerManager) getSystemService(Context.POWER_SERVICE);
            boolean isIgnoring = powerManager.isIgnoringBatteryOptimizations(packageName);
            if (!isIgnoring) {
                Intent intent = new Intent(Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setData(Uri.parse("package:" + packageName));
                try {
                    startActivity(intent);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    public void addActivity(Activity activity) {
        if (mActivityList != null && activity != null) {
            mActivityList.add(activity);
        }
    }

    public void removeActivity(Activity activity) {
        if (mActivityList != null && activity != null) {
            mActivityList.remove(activity);
        }
    }

    public void checkToken() {
        LogUtil.i(TAG, "checkToken");
        if (!isTokenCheckingReady || !isTokenCheckingFinish) {
            LogUtil.i(TAG, "checkToken return");
            return;
        }

        if ((mSubscription != null) && (!mSubscription.isUnsubscribed())) {
            mSubscription.unsubscribe();
        }

        LogUtil.i(TAG, "checkToken ready");
        isTokenCheckingFinish = false;
        DUTokenInfo duTokenInfo = new DUTokenInfo(
                mPreferencesHelper.getUserSession().getUserId(),
                DeviceUtil.getDeviceID(this),
                mPreferencesHelper.getUserSession().getAccessToken());
        mSubscription = mDataManager.checkToken(duTokenInfo)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<DUTokenResult>() {
                    @Override
                    public void onCompleted() {
                        LogUtil.i(TAG, "checkToken onCompleted");
                        isTokenCheckingFinish = true;
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.e(TAG, "checkToken onError: " + e.getMessage());
                        isTokenCheckingFinish = true;
                    }

                    @Override
                    public void onNext(DUTokenResult duTokenResult) {
                        LogUtil.i(TAG, "checkToken onNext");
                        if (duTokenResult.getTokenState() == TOKEN_STATE_UPDATE) {
                            String accessToken = duTokenResult.getAccessToken();
                            if (!TextUtil.isNullOrEmpty(accessToken)) {
                                UserSession userSession = mPreferencesHelper.getUserSession();
                                userSession.setAccessToken(accessToken);
                                userSession.save();
                            }
                        } else if (duTokenResult.getTokenState() == TOKEN_STATE_EXIT) {
                            sendLogoutSystemMessage();
                        }
                    }
                });
    }

    public void checkMessageCache() {
//        final String CHECK_MESSAGE_CACHE = "checkMessageCache";
//        if (messageCache.contains(CHECK_MESSAGE_CACHE)) {
//            messageCache.remove(CHECK_MESSAGE_CACHE);
//            if (messageCache.size() > 0) {
//                messageCache.clear();
//                ApplicationsUtil.showMessage(this, R.string.text_new_message_not_handle);
//            }
//        } else {
//            messageCache.add(CHECK_MESSAGE_CACHE);
//        }
    }

    private void initLogger() {
        mIsMainShellProcess = false;
        String processName = TextUtil.getString(getProcessName());
        if (processName.startsWith(HOST_PROCESS_NAME)) {
            mDataManager.initLogger("host");
        } else if (processName.startsWith(REMOTE_PROCESS_NAME)) {
            mDataManager.initLogger("remote");
        } else if (processName.startsWith(CHANNEL_PROCESS_NAME)) {
            mDataManager.initLogger("channel");
        } else {
            mDataManager.initLogger("main");
            mIsMainShellProcess = true;
        }
        releaseIO();
    }

    private void initUpush() {
//        try {
//            isUmengPushStarted = false;
//            ApplicationInfo appInfo =
//                    getPackageManager().getApplicationInfo(getPackageName(), PackageManager.GET_META_DATA);
//            String channel = appInfo.metaData.getString("UMENG_CHANNEL");
//            if (TextUtil.isNullOrEmpty(channel)
//                    || !channel.equalsIgnoreCase("umeng")) {
//                LogUtil.i(TAG, "initUpush: not umeng");
//                return;
//            }
//            LogUtil.i(TAG, "initUpush: umeng");
//        } catch (Exception e) {
//            e.printStackTrace();
//            LogUtil.e(TAG, e.getMessage());
//        }

        isUmengPushStarted = false;
        String processName = TextUtil.getString(getProcessName());
        LogUtil.i(TAG, processName);
        boolean isHostProcess = processName.startsWith(HOST_PROCESS_NAME);
        boolean isRemoteProcess = processName.startsWith(REMOTE_PROCESS_NAME);
        boolean isChannelProcess = processName.startsWith(CHANNEL_PROCESS_NAME);
        mIsMainShellProcess = !(isHostProcess || isRemoteProcess || isChannelProcess);
        releaseIO();
        if (isHostProcess || isRemoteProcess) {
            LogUtil.i(TAG, "initUpush: host or remote process");
            return;
        } else {
            LogUtil.i(TAG, "initUpush: main or channel process");
        }

        //设置LOG开关，默认为false
        UMConfigure.setLogEnabled(true);
        //初始化组件化基础库, 统计SDK/推送SDK/分享SDK都必须调用此初始化接口
        UMConfigure.init(this, UMConfigure.DEVICE_TYPE_PHONE, "8e517a58c51b97f76016c08a576b2cc5");

        PushAgent pushAgent = PushAgent.getInstance(this);
        uPushHandler = new Handler(getMainLooper());

        String manufacturer = android.os.Build.MANUFACTURER;
        if (!TextUtil.isNullOrEmpty(manufacturer) && manufacturer.equalsIgnoreCase("oppo")) {
            // sdk关闭通知声音
            pushAgent.setNotificationPlaySound(MsgConstant.NOTIFICATION_PLAY_SDK_DISABLE);
        } else {
            //sdk开启通知声音
            pushAgent.setNotificationPlaySound(MsgConstant.NOTIFICATION_PLAY_SDK_ENABLE);
        }

        // 通知声音由服务端控制
//		mPushAgent.setNotificationPlaySound(MsgConstant.NOTIFICATION_PLAY_SERVER);

//		mPushAgent.setNotificationPlayLights(MsgConstant.NOTIFICATION_PLAY_SDK_DISABLE);
//		mPushAgent.setNotificationPlayVibrate(MsgConstant.NOTIFICATION_PLAY_SDK_DISABLE);


        UmengMessageHandler messageHandler = new UmengMessageHandler() {
            /**
             * 自定义消息的回调方法
             */
            @Override
            public void dealWithCustomMessage(final Context context, final UMessage msg) {
                uPushHandler.post(new Runnable() {

                    @Override
                    public void run() {
                        // TODO Auto-generated method stub
                        // 对自定义消息的处理方式，点击或者忽略
                        boolean isClickOrDismissed = true;
                        if (isClickOrDismissed) {
                            //自定义消息的点击统计
                            UTrack.getInstance(getApplicationContext()).trackMsgClick(msg);
                        } else {
                            //自定义消息的忽略统计
                            UTrack.getInstance(getApplicationContext()).trackMsgDismissed(msg);
                        }
                        Toast.makeText(context, msg.custom, Toast.LENGTH_LONG).show();
                    }
                });
            }

            /**
             * 自定义通知栏样式的回调方法
             */
            @Override
            public Notification getNotification(Context context, UMessage msg) {
                parseMessageType(msg);
                turnOnScreen();
                String manufacturer = android.os.Build.MANUFACTURER;
                if (!TextUtil.isNullOrEmpty(manufacturer) && manufacturer.equalsIgnoreCase("oppo")) {
                    LogUtil.i(TAG, "getNotification: use my sound");
                    SoundUtils.playSoundByMedia(MainApplication.this, R.raw.new_message);
                } else {
                    LogUtil.i(TAG, "getNotification: use umeng sound");
                }
                switch (msg.builder_id) {
                    case 1:
                        Notification.Builder builder = new Notification.Builder(context);
                        RemoteViews myNotificationView = new RemoteViews(context.getPackageName(), R.layout.notification_view);
                        myNotificationView.setTextViewText(R.id.notification_title, msg.title);
                        myNotificationView.setTextViewText(R.id.notification_text, msg.text);
                        myNotificationView.setImageViewBitmap(R.id.notification_large_icon, getLargeIcon(context, msg));
                        myNotificationView.setImageViewResource(R.id.notification_small_icon, getSmallIconId(context, msg));
                        builder.setContent(myNotificationView)
                                .setSmallIcon(getSmallIconId(context, msg))
                                .setTicker(msg.ticker)
                                .setAutoCancel(true);

                        return builder.getNotification();
                    default:
                        //默认为0，若填写的builder_id并不存在，也使用默认。
                        return super.getNotification(context, msg);
                }
            }

            private void parseMessageType(UMessage msg) {
                String messageTitle = String.format(Locale.CHINESE, "%s: %s",
                        getResources().getString(R.string.text_new_message), TextUtil.format(new Date(), TextUtil.FORMAT_DATE_NO_SECOND));
                String messageInfo;
                boolean isLogout = false;
                try {
                    JSONObject jsonObject = new JSONObject(msg.text);
                    int type = jsonObject.getInt("type");
                    messageInfo = mDataManager.getMessageInfo(type);
                    if (type == 99999999) {
                        isLogout = true;
                    }
                    //JSONArray content = jsonObject.getJSONArray("content");
//                    switch (type) {
//                        case 201: // 热线工单
//                            messageType = getResources().getString(R.string.text_hot_line_sheet);
//                            break;
//                        case 1201: // 表务工单
//                            messageType = getResources().getString(R.string.text_work_sheet);
//                            break;
//                        case 99999999: // 登出系统
//                            messageTitle = getResources().getString(R.string.text_prompt);
//                            messageType = getResources().getString(R.string.text_logout_system);
//                            isLogout = true;
//                            break;
//                        default:
//                            messageType = getResources().getString(R.string.text_other_sheet);
//                            break;
//                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    LogUtil.e(TAG, e.getMessage());
                    messageInfo = getResources().getString(R.string.text_other_sheet);
                }

                if (isLogout) {
                    mHandler.sendMessage(mHandler.obtainMessage(MY_MSG_LOGOUT_SYSTEM));
                } else {
                    mHandler.sendMessage(mHandler.obtainMessage(MY_MSG_PUSH, TextUtil.getString(msg.text)));
                }

                if (AppChannel.GAS_GROUP_CHANNEL.equalsIgnoreCase(getAppChannel())) {
                    //TBD
                } else {
                    msg.text = AppChannel.JIANGMENG_CHANNEL.equalsIgnoreCase(getAppChannel()) ? msg.title : messageInfo;
                    msg.title = messageTitle;
                }
            }
        };
        pushAgent.setMessageHandler(messageHandler);

        /**
         * 自定义行为的回调处理，参考文档：高级功能-通知的展示及提醒-自定义通知打开动作
         * UmengNotificationClickHandler是在BroadcastReceiver中被调用，故
         * 如果需启动Activity，需添加Intent.FLAG_ACTIVITY_NEW_TASK
         * */
        UmengNotificationClickHandler notificationClickHandler = new UmengNotificationClickHandler() {
            @Override
            public void dealWithCustomAction(Context context, UMessage msg) {
                Toast.makeText(context, msg.custom, Toast.LENGTH_LONG).show();
            }
        };
        //使用自定义的NotificationHandler，来结合友盟统计处理消息通知，参考http://bbs.umeng.com/thread-11112-1-1.html
        //CustomNotificationHandler notificationClickHandler = new CustomNotificationHandler();
        pushAgent.setNotificationClickHandler(notificationClickHandler);


        //注册推送服务 每次调用register都会回调该接口
        pushAgent.register(new IUmengRegisterCallback() {
            @Override
            public void onSuccess(String deviceToken) {
                UmLog.i(TAG, "device token: " + deviceToken);
                sendBroadcast(new Intent(UPDATE_STATUS_ACTION));
                mHandler.sendMessage(mHandler.obtainMessage(MY_MAG_UMENG_TOKEN, TextUtil.getString(deviceToken)));
            }

            @Override
            public void onFailure(String s, String s1) {
                UmLog.i(TAG, "register failed: " + s + " " + s1);
                sendBroadcast(new Intent(UPDATE_STATUS_ACTION));
                mHandler.sendMessage(mHandler.obtainMessage(MY_MAG_UMENG_TOKEN, ""));
            }
        });

        HuaWeiRegister.register(this);
        isUmengPushStarted = true;
    }

//    private void startUmengPush() {
////        try {
////            ApplicationInfo appInfo =
////                    getPackageManager().getApplicationInfo(getPackageName(), PackageManager.GET_META_DATA);
////            String channel = appInfo.metaData.getString("UMENG_CHANNEL");
////            if (TextUtil.isNullOrEmpty(channel)
////                    || !channel.equalsIgnoreCase("umeng")) {
////                LogUtil.i(TAG, "startUmengPush: not umeng");
////                return;
////            }
////            LogUtil.i(TAG, "startUmengPush: umeng");
////        } catch (Exception e) {
////            e.printStackTrace();
////            LogUtil.e(TAG, e.getMessage());
////        }
//
//        isUmengPushStarted = false;
//        String processName = TextUtil.getString(getProcessName());
//        LogUtil.i(TAG, processName);
//        boolean isHostProcess = processName.startsWith(HOST_PROCESS_NAME);
//        boolean isRemoteProcess = processName.startsWith(REMOTE_PROCESS_NAME);
//        boolean isChannelProcess = processName.startsWith(CHANNEL_PROCESS_NAME);
//        mIsMainShellProcess = !(isHostProcess || isRemoteProcess || isChannelProcess);
//        releaseIO();
//        if (isHostProcess || isRemoteProcess) {
//            LogUtil.i(TAG, "startUmengPush: host or remote process");
//            return;
//        } else {
//            LogUtil.i(TAG, "startUmengPush: main or channel process");
//        }
//
//        PushAgent pushAgent = PushAgent.getInstance(this);
//        pushAgent.setDebugMode(mConfigHelper.isDebugMode());
//
//        //sdk开启通知声音
//        pushAgent.setNotificationPlaySound(MsgConstant.NOTIFICATION_PLAY_SDK_ENABLE);
//        // sdk关闭通知声音
////		mPushAgent.setNotificationPlaySound(MsgConstant.NOTIFICATION_PLAY_SDK_DISABLE);
//        // 通知声音由服务端控制
////		mPushAgent.setNotificationPlaySound(MsgConstant.NOTIFICATION_PLAY_SERVER);
//
////		mPushAgent.setNotificationPlayLights(MsgConstant.NOTIFICATION_PLAY_SDK_DISABLE);
////		mPushAgent.setNotificationPlayVibrate(MsgConstant.NOTIFICATION_PLAY_SDK_DISABLE);
//
//
//        UmengMessageHandler messageHandler = new UmengMessageHandler() {
//            /**
//             * 自定义消息的回调方法
//             * */
//            @Override
//            public void dealWithCustomMessage(final Context context, final UMessage msg) {
//                new Handler().post(new Runnable() {
//
//                    @Override
//                    public void run() {
//                        // TODO Auto-generated method stub
//                        // 对自定义消息的处理方式，点击或者忽略
//                        boolean isClickOrDismissed = true;
//                        if (isClickOrDismissed) {
//                            //自定义消息的点击统计
//                            UTrack.getInstance(getApplicationContext()).trackMsgClick(msg);
//                        } else {
//                            //自定义消息的忽略统计
//                            UTrack.getInstance(getApplicationContext()).trackMsgDismissed(msg);
//                        }
//                        Toast.makeText(context, msg.custom, Toast.LENGTH_LONG).show();
//                    }
//                });
//            }
//
//            /**
//             * 自定义通知栏样式的回调方法
//             * */
//            @Override
//            public Notification getNotification(Context context, UMessage msg) {
//                mHandler.sendMessage(mHandler.obtainMessage(MY_MSG_PUSH, TextUtil.getString(msg.text)));
//                msg.text = getResources().getString(R.string.text_click_to_detail);
//                switch (msg.builder_id) {
//                    case 1:
//                        Notification.Builder builder = new Notification.Builder(context);
//                        RemoteViews myNotificationView = new RemoteViews(context.getPackageName(), R.layout.notification_view);
//                        myNotificationView.setTextViewText(R.id.notification_title, msg.title);
//                        myNotificationView.setTextViewText(R.id.notification_text, msg.text);
//                        myNotificationView.setImageViewBitmap(R.id.notification_large_icon, getLargeIcon(context, msg));
//                        myNotificationView.setImageViewResource(R.id.notification_small_icon, getSmallIconId(context, msg));
//                        builder.setContent(myNotificationView)
//                                .setSmallIcon(getSmallIconId(context, msg))
//                                .setTicker(msg.ticker)
//                                .setAutoCancel(true);
//                        return builder.getNotification();
//                    default:
//                        //默认为0，若填写的builder_id并不存在，也使用默认。
//                        return super.getNotification(context, msg);
//                }
//            }
//        };
//        pushAgent.setMessageHandler(messageHandler);
//
//        /**
//         * 自定义行为的回调处理
//         * UmengNotificationClickHandler是在BroadcastReceiver中被调用，故
//         * 如果需启动Activity，需添加Intent.FLAG_ACTIVITY_NEW_TASK
//         * */
//        UmengNotificationClickHandler notificationClickHandler = new UmengNotificationClickHandler() {
//            @Override
//            public void dealWithCustomAction(Context context, UMessage msg) {
//                Toast.makeText(context, msg.custom, Toast.LENGTH_LONG).show();
//            }
//        };
//        //使用自定义的NotificationHandler，来结合友盟统计处理消息通知
//        //参考http://bbs.umeng.com/thread-11112-1-1.html
//        //CustomNotificationHandler notificationClickHandler = new CustomNotificationHandler();
//        pushAgent.setNotificationClickHandler(notificationClickHandler);
//
//
//        //注册推送服务 每次调用register都会回调该接口
//        pushAgent.register(new IUmengRegisterCallback() {
//            @Override
//            public void onSuccess(String deviceToken) {
//                UmLog.i(TAG, "device token: " + deviceToken);
//                MainApplication.this.deviceToken = deviceToken;
//                sendBroadcast(new Intent(UPDATE_STATUS_ACTION));
//                //startKeepAlive();
//            }
//
//            @Override
//            public void onFailure(String s, String s1) {
//                UmLog.i(TAG, "register failed: " + s + " " + s1);
//                sendBroadcast(new Intent(UPDATE_STATUS_ACTION));
//            }
//        });
//
//        //此处是完全自定义处理设置
////        mPushAgent.setPushIntentServiceClass(MyPushIntentService.class);
//
//        pushAgent.onAppStart();
//        isUmengPushStarted = true;
//    }

    private String getProcessName() {
        try {
            File file = new File("/proc/" + android.os.Process.myPid() + "/" + "cmdline");
            mBufferedReader = new BufferedReader(new FileReader(file));
            return mBufferedReader.readLine();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * release reader IO
     */
    private void releaseIO() {
        if (mBufferedReader != null) {
            try {
                mBufferedReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            mBufferedReader = null;
        }
    }

    private void setAppChannel() {
        try {
            appChannel = null;
            ApplicationInfo appInfo =
                    getPackageManager().getApplicationInfo(getPackageName(), PackageManager.GET_META_DATA);
            appChannel = appInfo.metaData.getString("UMENG_CHANNEL");
        } catch (Exception e) {
            e.printStackTrace();
            LogUtil.e(TAG, e.getMessage());
        }
    }

    public String getAppChannel() {
        return appChannel;
    }

    private void setAppTheme() {
        if (!TextUtil.isNullOrEmpty(appChannel)
                && (appChannel.equalsIgnoreCase(AppChannel.GAS_GROUP_CHANNEL)
                || appChannel.equalsIgnoreCase(AppChannel.DAZHONG_CHANNEL))) {
            mIsGreenTheme = true;
            LogUtil.i(TAG, "setTheme: green");
            //super.setTheme(R.style.AppGreenTheme);
        } else {
            mIsGreenTheme = false;
            LogUtil.i(TAG, "setTheme: blue");
        }
    }

    public boolean isGreenTheme() {
        return this.mIsGreenTheme;
    }

    @Subscribe
    public void onFileEnd(UIBusEvent.FileEnd fileEnd) {
        LogUtil.i(TAG, "---onFileEnd 1---");

        if (TextUtil.isNullOrEmpty(fileEnd.getAppName())
                || TextUtil.isNullOrEmpty(fileEnd.getAppId())
                || TextUtil.isNullOrEmpty(fileEnd.getPackageName())
                || TextUtil.isNullOrEmpty(fileEnd.getUrl())
                || mActivityList == null
                || mActivityList.size() <= 0) {
            LogUtil.i(TAG, "---onFileEnd 2---");
            return;
        }

        if (fileEnd.isSuccess()) {
            ApplicationsUtil.showMessage(this, R.string.text_download_success);
            if (!fileEnd.isApkEnd()) {
                return;
            }

            String path = url2Path(fileEnd.getUrl());
            if (!TextUtil.isNullOrEmpty(path)) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                //判断是否是AndroidN以及更高的版本
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    Uri contentUri = FileProvider.getUriForFile(this,
                            BuildConfig.APPLICATION_ID + ".fileProvider", new File(path));
                    intent.setDataAndType(contentUri, "application/vnd.android.package-archive");
                } else {
                    intent.setDataAndType(Uri.fromFile(new File(path)), "application/vnd.android.package-archive");
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                }
                mActivityList.get(mActivityList.size() - 1).startActivityForResult(intent, APP_INSTALLING_REQUEST_CODE);

//                Uri uri = Uri.fromFile(new File(path));
//                Intent intent = new Intent(Intent.ACTION_VIEW);
//                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                intent.setDataAndType(uri, "application/vnd.android.package-archive");
//                startActivity(intent);
            }
        } else {
            ApplicationsUtil.showMessage(this, R.string.text_download_failure);
        }
    }

    private String url2Path(String url) {
        if (TextUtil.isNullOrEmpty(url)) {
            return null;
        }

        int start = url.lastIndexOf("/") + 1;
        if (start <= 0) {
            return null;
        }

        String name = url.substring(start);
        if (TextUtil.isNullOrEmpty(name)) {
            return null;
        }

        int end = name.indexOf("_");
        if (end <= 0) {
            return null;
        }

        name = name.substring(0, end);
        if (TextUtil.isNullOrEmpty(name)) {
            return null;
        }

        File file = new File(mConfigHelper.getApksFolderPath(), name + ".apk");
        if (file.exists()) {
            return file.getPath();
        }
        file = new File(mConfigHelper.getApksFolderPath(), name + ".patch");
        return patchApk(file);
    }

    //合成
    private String patchApk(File file) {
        //下载的patch文件是否存在
        if (!file.exists()) {
            return null;
        }
        //生成apk的路径
        String patchApkPath = file.getPath().replace(".patch", ".apk");
        String oldApkPath = getOldApkPath();
        //判断老版本的路径
        if (oldApkPath == null) {
            return null;
        }
        if (PatchUtils.patch(oldApkPath, patchApkPath, file.getPath()) == 0) {
            //判断合成版本的签名
            String signatureNew = SignUtils.getUnInstalledApkSignature(getBaseContext(), patchApkPath);
            String signatureSource = SignUtils.getInstalledApkSignature(getBaseContext(), getPackageName());
            if (signatureNew != null && signatureSource != null) {
                return patchApkPath;
            }
        }
        return null;
    }

    private String getOldApkPath() {
        String oldApkSource;
        try {
            ApplicationInfo appInfo = getPackageManager().getApplicationInfo(getPackageName(), 0);
            oldApkSource = appInfo.sourceDir;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            oldApkSource = null;
            LogUtil.e(TAG, e.getMessage());
        }

        return oldApkSource;
    }

    /***
     * Android L (lollipop, API 21) introduced a new problem when trying to invoke implicit intent,
     * "java.lang.IllegalArgumentException: Service Intent must be explicit"
     * <p>
     * If you are using an implicit intent, and know only 1 target would answer this intent,
     * This method will help you turn the implicit intent into the explicit form.
     * <p>
     * Inspired from SO answer: http://stackoverflow.com/a/26318757/1446466
     *
     * @param context
     * @param implicitIntent - The original implicit intent
     * @return Explicit Intent created from the implicit original intent
     */
    private static Intent createExplicitFromImplicitIntent(Context context, Intent implicitIntent) {
        // Retrieve all services that can match the given intent
        PackageManager pm = context.getPackageManager();
        List<ResolveInfo> resolveInfo = pm.queryIntentServices(implicitIntent, 0);

        // Make sure only one match was found
        if (resolveInfo == null || resolveInfo.size() != 1) {
            return null;
        }

        // Get component info and create ComponentName
        ResolveInfo serviceInfo = resolveInfo.get(0);
        String packageName = serviceInfo.serviceInfo.packageName;
        String className = serviceInfo.serviceInfo.name;
        ComponentName component = new ComponentName(packageName, className);

        // Create a new intent. Use the old one for extras and such reuse
        Intent explicitIntent = new Intent(implicitIntent);

        // Set the component to be explicit
        explicitIntent.setComponent(component);

        return explicitIntent;
    }

    public static class MainReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (Intent.ACTION_PACKAGE_ADDED.equals(intent.getAction())) {
                Uri uri = intent.getData();
                LogUtil.i(TAG, "---ACTION_PACKAGE_ADDED----" + uri);

                String packageName = uri.toString();
                if (packageName.contains(PACKAGE_BASE_NAME)) {
                    postEvent(context, packageName, Intent.ACTION_PACKAGE_ADDED);
                } else {
                    LogUtil.i(TAG, "---ACTION_PACKAGE_ADDED----other apk");
                }
            } else if (Intent.ACTION_PACKAGE_REMOVED.equals(intent.getAction())) {
                Uri uri = intent.getData();
                LogUtil.i(TAG, "---ACTION_PACKAGE_REMOVED----" + uri);

                String packageName = uri.toString();
                if (packageName.contains(PACKAGE_BASE_NAME)) {
                    postEvent(context, packageName, Intent.ACTION_PACKAGE_REMOVED);
                } else {
                    LogUtil.i(TAG, "---ACTION_PACKAGE_REMOVED----other apk");
                }
            } else if (Intent.ACTION_PACKAGE_CHANGED.equals(intent.getAction())) {
                Uri uri = intent.getData();
                LogUtil.i(TAG, "---ACTION_PACKAGE_CHANGED----" + uri);
            } else if (Intent.ACTION_PACKAGE_RESTARTED.equals(intent.getAction())) {
                Uri uri = intent.getData();
                LogUtil.i(TAG, "---ACTION_PACKAGE_RESTARTED----" + uri);
            } /*else if (ConnectivityManager.CONNECTIVITY_ACTION.equals(intent.getAction())
                    || WifiManager.WIFI_STATE_CHANGED_ACTION.equals(intent.getAction())
                    || WifiManager.NETWORK_STATE_CHANGED_ACTION.equals(intent.getAction())) {//监听网络状态的改变
                // 监听网络连接，包括wifi和移动数据的打开和关闭，以及连接上可用的连接都会接到监听
                // 监听wifi的打开与关闭，与wifi的连接无关
                // 监听wifi的连接状态即是否连上了一个有效无线路由
                if (!NetworkUtil.isNetworkConnected(context)){
                    return;
                }

                Context c = context.getApplicationContext();
                if (c instanceof MainApplication) {
                    MainApplication mainApplication = (MainApplication) c;
//                    if (mainApplication.isMonitorInit) {
//                        boolean disable = !NetworkUtil.isNetworkConnected(context);
//                        //Countly.sharedInstance().setDisableUpdateSessionRequests(disable);
//                    }
                    if (mainApplication.mIsMainShellProcess) {
                        mainApplication.startHWPush();
                    }
                }

//                //当网络发生变化时，启动消息推送服务
//                Intent mqttIntent = new Intent(context, MQTTService.class);
//                context.startService(mqttIntent);
            }*/
        }

        private void postEvent(Context context, String packageName, String action) {
            Context c = context.getApplicationContext();
            if (c instanceof MainApplication) {
                MainApplication mainApplication = (MainApplication) c;
                int start = packageName.indexOf(":");
                if (start <= 0) {
                    return;
                }

                packageName = packageName.substring(start + 1);
                if (TextUtil.isNullOrEmpty(packageName)) {
                    return;
                }

//                if (packageName.equals(MAP_PACKAGE_NAME)) {
//                    mainApplication.unbindHostService();
//                    mainApplication.bindHostService();
//                }

                mainApplication.loadAndMatchXml(packageName, action);
            }
        }
    }

    private void loadAndMatchXml(final String packageName, final String action) {
        LogUtil.i(TAG, "---loadAndMatchXml---");
        if ((mSubscription != null) && (!mSubscription.isUnsubscribed())) {
            mSubscription.unsubscribe();
        }

        mSubscription = mXmlHelper.loadAndMatchXml(false)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<Boolean>() {
                    @Override
                    public void onCompleted() {
                        LogUtil.i(TAG, "---loadAndMatchXml onCompleted---");
                        mEventPosterHelper.postEventSafely(new UIBusEvent.ApkStatus(
                                packageName, packageName, action));
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.i(TAG, "---loadAndMatchXml onError---" + e.getMessage());
                        mEventPosterHelper.postEventSafely(new UIBusEvent.ApkStatus(
                                packageName, packageName, action));
                    }

                    @Override
                    public void onNext(Boolean aBoolean) {
                        LogUtil.i(TAG, "---loadAndMatchXml onNext---");
                    }
                });
    }

    private ServiceConnection fileServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            LogUtil.i(TAG, "---onServiceConnected---");
            if (service instanceof FileService.FileServiceBinder) {
                fileService = ((FileService.FileServiceBinder) service).getService();
                mEventPosterHelper.postEventSafely(new UIBusEvent.FileServiceReady());
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            LogUtil.i(TAG, "---onServiceDisconnected---");
            fileService = null;
        }
    };

//    private ServiceConnection mapServiceConnection = new ServiceConnection() {
//        @Override
//        public void onServiceConnected(ComponentName name, IBinder service) {
//            mapAidlInterface = IMapAidlInterface.Stub.asInterface(service);
//            LogUtil.i(TAG, "---ServiceConnection onServiceConnected---");
//        }
//
//        @Override
//        public void onServiceDisconnected(ComponentName name) {
//            LogUtil.i(TAG, "---ServiceConnection onServiceDisconnected---");
//            mapAidlInterface = null;
//        }
//    };

    private ServiceConnection mainConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mainService = IMainService.Stub.asInterface(service);
            LogUtil.i(TAG, "---mainConnection onServiceConnected---");
            try {
                mainService.registerCallback(mCallback);
            } catch (RemoteException e) {
                e.printStackTrace();
                LogUtil.e(TAG, e.getMessage());
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            LogUtil.i(TAG, "---mainConnection onServiceDisconnected---");
            try {
                mainService.unregisterCallback(mCallback);
            } catch (RemoteException e) {
                e.printStackTrace();
                LogUtil.e(TAG, e.getMessage());
            }

            mainService = null;
        }
    };

//    private ServiceConnection remoteConnection = new ServiceConnection() {
//        @Override
//        public void onServiceConnected(ComponentName name, IBinder service) {
//            remoteService = IRemoteService.Stub.asInterface(service);
//            LogUtil.i(TAG, "---remoteConnection onServiceConnected---");
//            try {
//                remoteService.registerCallback(mCallback);
//            } catch (RemoteException e) {
//                e.printStackTrace();
//                LogUtil.e(TAG, e.getMessage());
//            }
//        }
//
//        @Override
//        public void onServiceDisconnected(ComponentName name) {
//            LogUtil.i(TAG, "---remoteConnection onServiceDisconnected---");
//            try {
//                remoteService.unregisterCallback(mCallback);
//            } catch (RemoteException e) {
//                e.printStackTrace();
//                LogUtil.e(TAG, e.getMessage());
//            }
//            remoteService = null;
//        }
//    };

    private IRemoteServiceCallback mCallback = new IRemoteServiceCallback.Stub() {
        public void locationChanged(MyLocation myLocation) {
            //LogUtil.i(TAG, "locationChanged");
            //mHandler.sendMessage(mHandler.obtainMessage(MY_MSG_LOCATION, myLocation));
        }

        /**
         * This is called by the remote service regularly to tell us about
         * new values.  Note that IPC calls are dispatched through a thread
         * pool running in each process, so the code executing here will
         * NOT be running in our main thread like most other things -- so,
         * to update the UI, we need to use a Handler to hop over there.
         */
        public void moduleChanged(MyModule myModule) {
            LogUtil.i(TAG, "moduleChanged");
            mHandler.sendMessage(mHandler.obtainMessage(MY_MSG_MODULE, myModule));
        }

        public void exitSystem() {
            mHandler.sendMessage(mHandler.obtainMessage(MY_MSG_EXIT));
        }
    };

    private static final int MY_MSG_LOCATION = 1;
    private static final int MY_MSG_MODULE = 2;
    private static final int MY_MSG_EXIT = 3;
    private static final int MY_MSG_PUSH = 4;
    private static final int MY_MSG_CLEAR_CACHE = 5;
    private static final int MY_MSG_RESTORE_FACTORY = 6;
    private static final int MY_MSG_PHOTO_QUALITY = 7;
    private static final int MY_MSG_NETWORK_CHANGED = 8;
    private static final int MY_MSG_GPS_NOT_OPENED = 9;
    private static final int MY_MSG_LOGOUT_SYSTEM = 10;
    private static final int MY_MAG_UMENG_TOKEN = 11;
    private Handler mHandler = new MyHandler(this);

    private static class MyHandler extends Handler {
        private MainApplication mMainApplication;

        public MyHandler(MainApplication mainApplication) {
            mMainApplication = mainApplication;
        }

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MY_MSG_LOCATION:
                    break;
                case MY_MSG_MODULE:
                    //mCallbackText.setText("Received from service: " + msg.arg1);
                    if (msg.obj instanceof MyModule) {
                        parseMyModule((MyModule) msg.obj);
                    }
                    break;
                case MY_MSG_EXIT:
                    exit();
                    break;
                case MY_MSG_PUSH:
                    if (msg.obj instanceof String) {
                        pushMessage((String) msg.obj);
                    }
                    break;
                case MY_MSG_CLEAR_CACHE:
                    clearCache();
                    break;
                case MY_MSG_RESTORE_FACTORY:
                    restoreFactory();
                    break;
                case MY_MSG_PHOTO_QUALITY:
                    if (msg.obj instanceof Boolean) {
                        setPhotoQuality((boolean) msg.obj);
                    }
                    break;
                case MY_MSG_NETWORK_CHANGED:
                    if (msg.obj instanceof Boolean) {
                        setNetworkChanged((boolean) msg.obj);
                    }
                    break;
                case MY_MSG_GPS_NOT_OPENED:
                    setGpsNotOpened();
                    break;
                case MY_MSG_LOGOUT_SYSTEM:
                    setLogoutSystem();
                    break;
                case MY_MAG_UMENG_TOKEN:
                    if (msg.obj instanceof String) {
                        saveUmengToken((String) msg.obj);
                    }
                    break;
                default:
                    super.handleMessage(msg);
            }
        }

        private void parseMyModule(MyModule myModule) {
            if ((myModule == null)
                    || TextUtil.isNullOrEmpty(myModule.getInfo())) {
                LogUtil.e(TAG, "parseMyModule is error<1>");
                return;
            }

            try {
                String info = myModule.getInfo();
                JSONObject jsonObject = new JSONObject(info);
                String packageName = jsonObject.getString(MyModule.PACKAGE_NAME);
                String activity = jsonObject.getString(MyModule.ACTIVITY_NAME);
                JSONArray jsonArray = jsonObject.getJSONArray(MyModule.DATA);
                if (TextUtil.isNullOrEmpty(packageName)
                        || packageName.equals(mMainApplication.getPackageName())
                        || TextUtil.isNullOrEmpty(activity)
                        || (jsonArray == null)
                        || (jsonArray.length() <= 0)) {
                    LogUtil.e(TAG, "parseMyModule is error<2>");
                    return;
                }

                for (int i = 0; i < jsonArray.length(); i++) {
                    String str = jsonArray.getString(i);
                    if (str.startsWith(MyModule.COUNT) && str.contains(MyModule.SEPARATOR)) {
                        parseCount(packageName, activity, str);
                    } else if (str.startsWith(PUSH_MESSAGE) && str.contains(MyModule.SEPARATOR)) {
                        parsePushMessage(packageName, activity, str);
                    } else if (str.startsWith(MyModule.POPUP_MESSAGE) && str.contains(MyModule.SEPARATOR)) {
                        popupMessage(str);
                    } else if (str.equals(MyModule.LOGOUT_SUB_SYSTEM)) {
                        LogUtil.i(TAG, "LOGOUT_SUB_SYSTEM");
                    } else if (str.equals(MyModule.LOGOUT_MAIN_SYSTEM)) {
                        LogUtil.i(TAG, "LOGOUT_MAIN_SYSTEM");
                    } else if (str.equals(MyModule.SUB_BOUND)) {
                        parseSubBound();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                LogUtil.e(TAG, e.getMessage());
            }
        }

        private void parseCount(String packageName, String activity, String data) {
            int index = data.indexOf(MyModule.SEPARATOR);
            if (index <= 0) {
                return;
            }

            String str = data.substring(index + 1);
            if (TextUtil.isNullOrEmpty(str)) {
                return;
            }

            mMainApplication.mEventPosterHelper.postEventSafely(new UIBusEvent.UpdatingComponent(
                    packageName, activity, TextUtil.getInt(str)));
        }

        private void parsePushMessage(String packageName, String activity, String data) {
//            int index = data.indexOf(MyModule.SEPARATOR);
//            if (index <= 0) {
//                return;
//            }
//
//            String str = data.substring(index + 1);
//            if (TextUtil.isNullOrEmpty(str)) {
//                return;
//            }
        }

        private void popupMessage(String data) {
            int index = data.indexOf(MyModule.SEPARATOR);
            if (index <= 0) {
                return;
            }

            try {
                String message = TextUtil.getString(data.substring(index + 1));
                if (TextUtil.isNullOrEmpty(message)) {
                    return;
                }

                ContentResolver contentResolver = mMainApplication.getContentResolver();
                Uri uri = SharedData.CONTENT_URI.buildUpon().appendEncodedPath(PUSH_MESSAGE + message.hashCode()).build();
                int count = contentResolver.delete(uri, null, null);
                LogUtil.i(TAG, "popupMessage: " + count);
            } catch (Exception e) {
                e.printStackTrace();
                LogUtil.e(TAG, e.getMessage());
            }
        }

        private void parseSubBound() {
            LogUtil.i(TAG, "SUB_BOUND");
            mMainApplication.initGpsLocation();
            mMainApplication.bindFileService();
            mMainApplication.initMonitor();
            //mMainApplication.startPush();
            mMainApplication.startKeepAlive();
            mMainApplication.startBaiduTrack();
        }

        private void pushMessage(String message) {
            if (TextUtil.isNullOrEmpty(message)) {
                return;
            }

//            if (message.equals("logout")) {
//                LogUtil.i(TAG, "pushMessage logout");
//                mMainApplication.sendLogoutSystemMessage();
//                return;
//            }

            LogUtil.i(TAG, "<<<pushMessage>>>" + message);
            mMainApplication.sendPushMessage(message);
        }

        private void exit() {
            mMainApplication.unbindHostService();
            MobclickAgent.onProfileSignOff();
            MobclickAgent.onKillProcess(mMainApplication);
            //System.exit(0);
            android.os.Process.killProcess(android.os.Process.myPid());
        }

        private void clearCache() {
            mMainApplication.clearCache();
        }

        private void restoreFactory() {
            mMainApplication.restoreFactory();
        }

        private void setPhotoQuality(boolean isNormal) {
            mMainApplication.setPhotoQuality(isNormal);
        }

        private void setNetworkChanged(boolean isOuter) {
            mMainApplication.setNetworkChanged(isOuter);
        }

        private void setGpsNotOpened() {
            mMainApplication.setGpsNotOpened();
        }

        private void setLogoutSystem() {
            mMainApplication.setLogoutSystem();
        }

        private void saveUmengToken(String token) {
            UserSession userSession = mMainApplication.mPreferencesHelper.getUserSession();
            if (TextUtil.isNullOrEmpty(token)) {
                token = TextUtil.getString(userSession.getUmengToken());
            }
            userSession.setUmengToken(token);
            userSession.save();
        }
    }

    public void turnOnScreen() {
        LogUtil.i(TAG, "turnOnScreen");

        try {
            //获取电源管理器对象
            PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
            //获取PowerManager.WakeLock对象,后面的参数|表示同时传入两个值,最后的是LogCat里用的Tag
            PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.ACQUIRE_CAUSES_WAKEUP
                    | PowerManager.SCREEN_DIM_WAKE_LOCK, "bright");
            //点亮屏幕
            wl.acquire();
            //释放
            wl.release();
        } catch (Exception e) {
            e.printStackTrace();
            LogUtil.e(TAG, e.getMessage());
        }

        //initGpsLocation();
        //startKeepAlive();
    }

    /**
     * 获取电源锁，保持该服务在屏幕熄灭时仍然获取CPU时，保持运行
     */
    private void acquireWakeLock() {
        if (null == wakeLock) {
            PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
            wakeLock = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK
                    | PowerManager.ON_AFTER_RELEASE, getClass()
                    .getCanonicalName());
            if (null != wakeLock) {
                LogUtil.i(TAG, "call acquireWakeLock");
                wakeLock.acquire();
            }
        }
    }

    // 释放设备电源锁
    private void releaseWakeLock() {
        if (null != wakeLock && wakeLock.isHeld()) {
            LogUtil.i(TAG, "call releaseWakeLock");
            wakeLock.release();
            wakeLock = null;
        }
    }

//    private void process() {
//        LogUtil.i(TAG, "process 1");
//        if (isScreenOn) {
//            LogUtil.i(TAG, "process 2");
//            return;
//        }
//
//        int interval = mConfigHelper.getKeepAliveInterval();
//        if (interval <= 0) {
//            interval = SystemConfig.KEEP_LIVE_INTERVAL_DEFAULT_VALUE;
//        }
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                LogUtil.i(TAG, "process 3");
//                startService(new Intent(MainApplication.this, KeepAliveService.class));
//                Message message = mHandler.obtainMessage();
//                message.what = 1;
//                mHandler.sendMessage(message);
//            }
//        }, interval);
//    }
//
//    private Handler mHandler = new Handler() {
//        @Override
//        public void handleMessage(Message msg) {
//            switch (msg.what) {
//                case 1:
//                    process();
//                    break;
//            }
//        }
//    };

    private final BroadcastReceiver mBatInfoReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(final Context context, final Intent intent) {
            final String action = intent.getAction();
            if (Intent.ACTION_SCREEN_ON.equals(action)) {
                LogUtil.i(TAG, "-----------------screen is on...");
                // release power
                isScreenOn = true;
                releaseWakeLock();
                startKeepAlive();
                stopService(KeepAliveService.getStartIntent(context));
            } else if (Intent.ACTION_SCREEN_OFF.equals(action)) {
                LogUtil.i(TAG, "----------------- screen is off...");
                // acquire power
                isScreenOn = false;
                acquireWakeLock();
                stopKeepAlive();
                startService(KeepAliveService.getStartIntent(context));
            }
        }
    };

    //@TargetApi(24)
    private void monitorNetworkState() {
//        if (Build.VERSION.SDK_INT >= 24) {
//            try {
//                ConnectivityManager connectivityManager =
//                        (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
//                connectivityManager.requestNetwork(new NetworkRequest.Builder().build(),
//                        new ConnectivityManager.NetworkCallback() {
//                            @Override
//                            public void onAvailable(Network network) {
//                                super.onAvailable(network);
//                                startHWPush();
//                            }
//                        });
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }

        mNetworkReceiver = new NetworkReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(mNetworkReceiver, intentFilter);
    }

    private void releaseNetworkState() {
        if (mNetworkReceiver != null) {
            unregisterReceiver(mNetworkReceiver);
            mNetworkReceiver = null;
        }
    }

    private void killProcess() {
        try {
            DUUpdateXmlFile duUpdateXmlFile = mXmlHelper.getDuUpdateXmlFile();
            if ((duUpdateXmlFile == null)
                    || (duUpdateXmlFile.getApplicationList() == null)
                    || (duUpdateXmlFile.getApplicationList().size() <= 0)) {
                return;
            }

            List<DUUpdateXmlFile.Application> applicationList = duUpdateXmlFile.getApplicationList();
            List<Integer> pIds = new ArrayList<>();

            // 当前的系统版本超过5.0
            if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

                List<ProcessManager.Process> processes = ProcessManager.getRunningProcesses();
                for (ProcessManager.Process process : processes) {
                    // 进程ID号
                    int pid = process.pid;
                    // 用户ID 类似于Linux的权限不同，ID也就不同 比如 root等
                    int uid = process.uid;
                    // 进程名，默认是包名或者由属性android：process=""指定
                    String processName = process.getPackageName();
                    if (processName.equals(getPackageName())) {
                        continue;
                    }

                    for (DUUpdateXmlFile.Application application : applicationList) {
                        if (processName.equals(application.getPackageName())) {
                            pIds.add(pid);
                            LogUtil.i(TAG, processName);
                            break;
                        }
                    }
                }
            } else {
                // 通过调用ActivityManager的getRunningAppProcesses()方法获得系统里所有正在运行的进程
                ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
                List<ActivityManager.RunningAppProcessInfo> appProcessList = manager.getRunningAppProcesses();
                for (ActivityManager.RunningAppProcessInfo appProcessInfo : appProcessList) {
                    // 进程ID号
                    int pid = appProcessInfo.pid;
                    // 用户ID 类似于Linux的权限不同，ID也就不同 比如 root等
                    int uid = appProcessInfo.uid;
                    // 进程名，默认是包名或者由属性android：process=""指定
                    String processName = appProcessInfo.processName;
                    if (processName.equals(getPackageName())) {
                        continue;
                    }

                    for (DUUpdateXmlFile.Application application : applicationList) {
                        if (processName.equals(application.getPackageName())) {
                            pIds.add(pid);
                            LogUtil.i(TAG, processName);
                            break;
                        }
                    }
                }
            }

            if (pIds.size() > 0) {
                for (Integer pid : pIds) {
                    Process.killProcess(pid);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void sendPushMessage(String message) {
        try {
            if (mainService != null /*&& messageCache.add(message)*/) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put(MyModule.PACKAGE_NAME, getPackageName());
                jsonObject.put(MyModule.ACTIVITY_NAME, getPackageName());

                JSONArray jsonArray = new JSONArray();
                jsonArray.put(String.format(Locale.CHINESE, "%s%s%s",
                        PUSH_MESSAGE, MyModule.SEPARATOR, message));
                jsonObject.put(MyModule.DATA, jsonArray);

                mainService.setMyModule(new MyModule(jsonObject.toString()));
                saveSharedData(PUSH_MESSAGE + message.hashCode(), message);
            }
        } catch (Exception exception) {
            exception.printStackTrace();
            Log.e(TAG, "---updateLocation---" + exception.getMessage());
        }
    }

    private void clearCache() {
        try {
            if (mainService != null) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put(MyModule.PACKAGE_NAME, getPackageName());
                jsonObject.put(MyModule.ACTIVITY_NAME, getPackageName());

                JSONArray jsonArray = new JSONArray();
                jsonArray.put(CLEAR_CACHE);
                jsonObject.put(MyModule.DATA, jsonArray);

                mainService.setMyModule(new MyModule(jsonObject.toString()));
            }
        } catch (Exception exception) {
            exception.printStackTrace();
            Log.e(TAG, "---clearCache---" + exception.getMessage());
        }
    }

    private void restoreFactory() {
        try {
            if (mainService != null) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put(MyModule.PACKAGE_NAME, getPackageName());
                jsonObject.put(MyModule.ACTIVITY_NAME, getPackageName());

                JSONArray jsonArray = new JSONArray();
                jsonArray.put(RESTORE_FACTORY);
                jsonObject.put(MyModule.DATA, jsonArray);

                mainService.setMyModule(new MyModule(jsonObject.toString()));
            }
        } catch (Exception exception) {
            exception.printStackTrace();
            Log.e(TAG, "---restoreFactory---" + exception.getMessage());
        }
    }

    private void setPhotoQuality(boolean isNormal) {
        try {
            if (mainService != null) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put(MyModule.PACKAGE_NAME, getPackageName());
                jsonObject.put(MyModule.ACTIVITY_NAME, getPackageName());

                JSONArray jsonArray = new JSONArray();
                jsonArray.put(String.format(Locale.CHINESE, "%s%s%s",
                        MyModule.PHOTO_QUALITY, MyModule.SEPARATOR, isNormal ? "normal" : "high"));
                jsonObject.put(MyModule.DATA, jsonArray);

                mainService.setMyModule(new MyModule(jsonObject.toString()));
            }
        } catch (Exception exception) {
            exception.printStackTrace();
            Log.e(TAG, "---setPhotoQuality---" + exception.getMessage());
        }
    }

    private void setNetworkChanged(boolean isOuter) {
        try {
            if (mainService != null) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put(MyModule.PACKAGE_NAME, getPackageName());
                jsonObject.put(MyModule.ACTIVITY_NAME, getPackageName());

                JSONArray jsonArray = new JSONArray();
                jsonArray.put(String.format(Locale.CHINESE, "%s%s%s",
                        MyModule.OUTER_NETWORK, MyModule.SEPARATOR, isOuter));
                jsonObject.put(MyModule.DATA, jsonArray);

                mainService.setMyModule(new MyModule(jsonObject.toString()));
            }
        } catch (Exception exception) {
            exception.printStackTrace();
            Log.e(TAG, "---setNetworkChanged---" + exception.getMessage());
        }
    }

    private void setGpsNotOpened() {
        try {
            if (mainService != null) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put(MyModule.PACKAGE_NAME, getPackageName());
                jsonObject.put(MyModule.ACTIVITY_NAME, getPackageName());

                JSONArray jsonArray = new JSONArray();
                jsonArray.put(GPS_NOT_OPENED);
                jsonObject.put(MyModule.DATA, jsonArray);

                mainService.setMyModule(new MyModule(jsonObject.toString()));
            }
        } catch (Exception exception) {
            exception.printStackTrace();
            Log.e(TAG, "---setGpsNotOpened---" + exception.getMessage());
        }
    }

    private void setLogoutSystem() {
        try {
            LogUtil.i(TAG, "setLogoutSystem 1");
            if (mainService != null) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put(MyModule.PACKAGE_NAME, getPackageName());
                jsonObject.put(MyModule.ACTIVITY_NAME, getPackageName());

                JSONArray jsonArray = new JSONArray();
                jsonArray.put(isSubApkLaunched() ? LOGOUT_SUB_SYSTEM : LOGOUT_MAIN_SYSTEM);
                jsonObject.put(MyModule.DATA, jsonArray);

                mainService.setMyModule(new MyModule(jsonObject.toString()));
                LogUtil.i(TAG, "setLogoutSystem 2");

                logoutSystem();
            }
        } catch (Exception exception) {
            exception.printStackTrace();
            Log.e(TAG, "---setLogoutSystem---" + exception.getMessage());
        }
    }

    private boolean isSubApkLaunched() {
        try {
            ActivityManager am = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
            List<ActivityManager.RunningAppProcessInfo> processInfoList = am.getRunningAppProcesses();
            int[] pIds = mainService.getPids();
            if (pIds != null && pIds.length > 0) {
                for (int pid : pIds) {
                    boolean found = false;
                    for (ActivityManager.RunningAppProcessInfo processInfo : processInfoList) {
                        if (processInfo.pid == pid) {
                            found = true;
                            break;
                        }
                    }

                    if (!found) {
                        return true;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    private void logoutSystem() {
        LogUtil.i(TAG, "logoutSystem");
        if ((mSubscription != null) && (!mSubscription.isUnsubscribed())) {
            mSubscription.unsubscribe();
        }

        mSubscription = Observable.timer(3, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<Long>() {
                    @Override
                    public void onCompleted() {
                        LogUtil.i(TAG, "logoutSystem onCompleted");
                        if (mActivityList != null && mActivityList.size() > 0) {
                            Activity activity = mActivityList.get(mActivityList.size() - 1);
                            new MaterialDialog.Builder(activity)
                                    .title(R.string.text_prompt)
                                    .content(R.string.text_logout)
                                    .positiveText(R.string.text_ok)
                                    .cancelable(false)
                                    .onPositive(new MaterialDialog.SingleButtonCallback() {
                                        @Override
                                        public void onClick(@NonNull MaterialDialog materialDialog,
                                                            @NonNull DialogAction dialogAction) {
                                            logout();
                                        }
                                    })
                                    .show();
                        } else {
                            logout();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.e(TAG, "logoutSystem onError" + e.getMessage());
                    }

                    @Override
                    public void onNext(Long aLong) {
                        LogUtil.i(TAG, "logoutSystem onNext");
                    }
                });
    }

    private void logout() {
        LogUtil.i(TAG, "logout");
        if ((mSubscription != null) && (!mSubscription.isUnsubscribed())) {
            mSubscription.unsubscribe();
        }

        DULogoutInfo duLogoutInfo = new DULogoutInfo(
                mPreferencesHelper.getUserSession().getUserId(),
                DeviceUtil.getDeviceID(this));
        mSubscription = mDataManager.logout(duLogoutInfo)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<Boolean>() {
                    @Override
                    public void onCompleted() {
                        LogUtil.i(TAG, "logout onCompleted");
                        quit();
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.e(TAG, "logout onError" + e.getMessage());
                        quit();
                    }

                    @Override
                    public void onNext(Boolean aBoolean) {
                        LogUtil.i(TAG, "logout onNext");
                    }
                });
    }

    private void quit() {
        if (mActivityList != null && mActivityList.size() > 0) {
            int i = mActivityList.size() - 1;
            for (; i >= 0; i--) {
                mActivityList.get(i).finish();
            }
        } else {
            ApplicationsUtil.showMessage(MainApplication.this, "退出系统");
            destroy();
        }
    }

//    private int getMessageType(String message) {
//        try {
//            JSONObject jsonObject = new JSONObject(message);
//            return jsonObject.getInt("type");
//        } catch (Exception e) {
//            e.printStackTrace();
//            LogUtil.e(TAG, e.getMessage());
//            return MESSAGE_ERROR_TYPE;
//        }
//    }
}
