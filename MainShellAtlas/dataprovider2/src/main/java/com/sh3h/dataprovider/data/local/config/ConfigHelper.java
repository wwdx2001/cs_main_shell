package com.sh3h.dataprovider.data.local.config;


import android.content.Context;
import android.content.res.AssetManager;
import android.os.Environment;
import android.util.Log;

import com.sh3h.dataprovider.data.local.preference.PreferencesHelper;
import com.sh3h.dataprovider.data.local.preference.UserSession;
import com.sh3h.dataprovider.util.FileUtil;
import com.sh3h.mobileutil.util.TextUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Calendar;
import java.util.Date;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;
import rx.Subscriber;

@Singleton
public class ConfigHelper {
    private static final String TAG = "ConfigHelper";

    public static final File STORAGE_PATH = Environment.getExternalStorageDirectory();
    /**
     * 根目录
     */
    public static final String FOLDER_ROOT = "sh3h/metercard";
    /**
     * 配置文件目录
     */
    public static final String FOLDER_CONFIG = FOLDER_ROOT + "/config";
    /**
     * 多用户配置文件目录
     */
    public static final String FOLDER_USER = FOLDER_ROOT + "/user";
    /**
     * 数据配置文件目录
     */
    public static final String FOLDER_DATA = FOLDER_ROOT + "/data";
    /**
     * 激活文件目录
     */
    public static final String FOLDER_KEY = FOLDER_ROOT + "/key";

    /**
     * 图片存储目录
     */
    public static final String FOLDER_IMAGES = FOLDER_ROOT + "/images";

    /**
     * 图片存储目录
     */
    public static final String QIANMING_IMAGES = FOLDER_IMAGES + "/signature";

    /**
     * 声音存储目录
     */
    public static final String FOLDER_SOUNDS = FOLDER_ROOT + "/sounds";

    /**
     * 视频存储目录
     */
    public static final String FOLDER_VIDEOS = FOLDER_ROOT + "/videos";

    /**
     * 更新文件存储目录
     */
    public static final String FOLDER_UPDATE = FOLDER_ROOT + "/update";

    /**
     * apk文件目录
     */
    public static final String FOLDER_APKS = FOLDER_ROOT + "/apks";

    /**
     * www文件存储目录
     */
    public static final String FOLDER_WWW = FOLDER_ROOT + "/www";

    /**
     * 日志文件存储目录
     */
    public static final String FOLDER_LOG = FOLDER_ROOT + "/log";

    /**
     * 用户配置文件名
     */
    public static final String FILE_USER_CONFIG = "user.properties";

    /**
     * 用户登录信息文件名
     */
    public static final String FILE_USER_LOGIN = "login.properties";

    /**
     * 版本记录号
     */
    public static final String FILE_VERSION_UPDATE = "versions.properties";
    /**
     * 系统参数文件名
     */
    public static final String FILE_SYSTEM_CONFIG = "system.properties";

    /**
     * Map参数文件名
     */
    public static final String FILE_MAP_CONFIG = "map.properties";

    /**
     * 下载的配置文件
     */
    public static final String FILE_OTHER_CONFIG = "other.properties";

    /**
     * 用户配置文件名
     */
    public static final String FILE_USER = "users.xml";
    /**
     * 在线用户配置文件名
     */
    public static final String FILE_ONLINE_USER = "online-users.xml";
    /**
     * 词语配置文件名
     */
    public static final String FILE_WORDS = "words.xml";
    /**
     * 数据库文件名
     */
    public static final String DB_NAME = "main.db";
    /**
     * 激活文件名
     */
    public static final String FILE_KEY_CONFIG = "key.properties";
    /**
     * 更新文件
     */
    public static final String FILE_UPDATE_XML = "update.xml";
    /**
     * 配置文件
     */
    public static final String FILE_CONFIG_XML = "config.xml";


    private Context mContext;
    private final PreferencesHelper mPreferencesHelper;
    private final SystemConfig mSystemConfig;
    private final VersionConfig mVersionConfig;
    private final KeyConfig mKeyConfig;
    private final UserConfig mUserConfig;
    private final OtherConfig mOtherConfig;

    @Inject
    public ConfigHelper(PreferencesHelper preferencesHelper) {
        mPreferencesHelper = preferencesHelper;
        mSystemConfig = new SystemConfig();
        mVersionConfig = new VersionConfig();
        mKeyConfig = new KeyConfig();
        mUserConfig = new UserConfig();
        mOtherConfig = new OtherConfig();
    }

    public void setContext(Context context) {
        mContext = context;
    }

    public Observable<Void> initDefaultConfigs() {
        return Observable.create(new Observable.OnSubscribe<Void>() {
            @Override
            public void call(Subscriber<? super Void> subscriber) {
                if (!initDefaultConfigFiles()) {
                    subscriber.onError(new Throwable("initDefaultConfigFiles is failure"));
                } else {
                    subscriber.onCompleted();
                }
            }
        });
    }

    public Observable<Void> clearAndInitConfigs(final boolean isRestoringFactory) {
        return Observable.create(new Observable.OnSubscribe<Void>() {
            @Override
            public void call(Subscriber<? super Void> subscriber) {
                if ((!clearConfigFiles(isRestoringFactory)) || (!initDefaultConfigFiles())) {
                    subscriber.onError(new Throwable("clearConfigs is failure"));
                } else {
                    subscriber.onCompleted();
                }
            }
        });
    }

    public Observable<Void> initUserConfig(final int userId) {
        return Observable.create(new Observable.OnSubscribe<Void>() {
            @Override
            public void call(Subscriber<? super Void> subscriber) {
                if (!initUserConfigFile(userId)) {
                    subscriber.onError(new Throwable("initDefaultConfigFiles is failure"));
                } else {
                    subscriber.onCompleted();
                }
            }
        });
    }

    /**
     * initialize configure files
     *
     * @return true: success
     */
    private boolean initDefaultConfigFiles() {
        try {
            // config
            File configDir = new File(STORAGE_PATH, FOLDER_CONFIG);
            if (!configDir.exists() && configDir.mkdirs()) {
                mSystemConfig.loadDefaultValues();
                mSystemConfig.writeProperties(getSystemConfigFilePath().getPath());

                mOtherConfig.loadDefaultValues();
                mOtherConfig.writeProperties(getOtherConfigFilePath().getPath());
            }

            // data
            File dataDir = new File(STORAGE_PATH, FOLDER_DATA);
            if (!dataDir.exists()) {
                dataDir.mkdirs();
            }

            // image
            File imagesDir = new File(STORAGE_PATH, FOLDER_IMAGES);
            if (!imagesDir.exists()) {
                imagesDir.mkdirs();
            }

            // sound
            File soundsDir = new File(STORAGE_PATH, FOLDER_SOUNDS);
            if (!soundsDir.exists()) {
                soundsDir.mkdirs();
            }

            // log
            File logDir = new File(STORAGE_PATH, FOLDER_LOG);
            if (!logDir.exists()) {
                logDir.mkdirs();
            }

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * clear all configures
     *
     * @return
     */
    private boolean clearConfigFiles(boolean isRestoringFactory) {
        try {
            if (isRestoringFactory) {
                // apks
                File apksConfigDir = new File(STORAGE_PATH, FOLDER_APKS);
                if (apksConfigDir.exists()) {
                    FileUtil.deleteFile(apksConfigDir);
                }

                // configure
                File oldConfigDir = new File(STORAGE_PATH, FOLDER_CONFIG);
                if (oldConfigDir.exists()) {
                    FileUtil.deleteFile(oldConfigDir);
                }

                // update
                File updateConfigDir = new File(STORAGE_PATH, FOLDER_UPDATE);
                if (updateConfigDir.exists()) {
                    FileUtil.deleteFile(updateConfigDir);
                }

                // user
                File oldUserConfigDir = new File(STORAGE_PATH, FOLDER_USER);
                if (oldUserConfigDir.exists()) {
                    FileUtil.deleteFile(oldUserConfigDir);
                }

                // key
                File keyDir = new File(STORAGE_PATH, FOLDER_KEY);
                if (!keyDir.exists()) {
                    FileUtil.deleteFile(keyDir);
                }
            }

            // data
            File oldDataDir = new File(STORAGE_PATH, FOLDER_DATA);
            if (oldDataDir.exists()) {
                FileUtil.deleteFile(oldDataDir);
            }

            // log
            File oldLogDir = new File(STORAGE_PATH, FOLDER_LOG);
            if (oldLogDir.exists()) {
                FileUtil.deleteFile(oldLogDir);
            }

            // image
            File oldImageDir = new File(STORAGE_PATH, FOLDER_IMAGES);
            if (oldImageDir.exists()) {
                FileUtil.deleteFile(oldImageDir);
            }

            // sound
            File oldSoundDir = new File(STORAGE_PATH, FOLDER_SOUNDS);
            if (oldSoundDir.exists()) {
                FileUtil.deleteFile(oldSoundDir);
            }

            // www
            File oldWWWDir = new File(STORAGE_PATH, FOLDER_WWW);
            if (oldWWWDir.exists()) {
                FileUtil.deleteFile(oldWWWDir);
            }

            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean initUserConfigFile(int userId) {
        File dir = new File(STORAGE_PATH, FOLDER_USER + "/" + userId);
        File file = new File(dir, FILE_USER_CONFIG);

        if (!file.exists() && dir.mkdirs()) {
            mUserConfig.loadDefaultConfig();
            mUserConfig.writeProperties(file.getPath());
        } else {
            mUserConfig.readProperties(file.getPath());
        }

        return true;
    }

    /**
     * @return
     */
    public File getConfigFolderPath() {
        return new File(STORAGE_PATH, FOLDER_CONFIG);
    }

    /**
     * get the path of system.properties
     *
     * @return file path
     */
    public File getSystemConfigFilePath() {
        File dir = new File(STORAGE_PATH, FOLDER_CONFIG);
        return new File(dir, FILE_SYSTEM_CONFIG);
    }

    /**
     * @return
     */
    public File getMapConfigFilePath() {
        File dir = new File(STORAGE_PATH, FOLDER_CONFIG);
        return new File(dir, FILE_MAP_CONFIG);
    }

    /**
     * @return
     */
    public File getOtherConfigFilePath() {
        File dir = new File(STORAGE_PATH, FOLDER_CONFIG);
        return new File(dir, FILE_OTHER_CONFIG);
    }

    /**
     * @return
     */
    @Deprecated
    public File getUserFolderPath() {
        UserSession userSession = mPreferencesHelper.getUserSession();
        return new File(STORAGE_PATH, FOLDER_USER + "/" + userSession.getUserId());
    }

    /**
     * @return
     */
    public File getUserConfigFilePath() {
        UserSession userSession = mPreferencesHelper.getUserSession();
        File dir = new File(STORAGE_PATH, FOLDER_USER + "/" + userSession.getUserId());
        return new File(dir, FILE_USER_CONFIG);
    }

    /**
     * get the path of log file
     *
     * @return file path
     */
    public File getLogFilePath() {
        File dir = new File(STORAGE_PATH, FOLDER_LOG);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        String name = String.format("log-%s.txt", TextUtil.format(new Date(), TextUtil.FORMAT_DATE));
        return new File(dir, name);
    }

    /**
     * @return
     */
    public File getKeyFilePath() {
        File dir = new File(STORAGE_PATH, FOLDER_KEY);
        return new File(dir, FILE_KEY_CONFIG);
    }

    /**
     * @return
     */
    @Deprecated
    public File getVersionFilePath() {
        File dir = new File(STORAGE_PATH, FOLDER_UPDATE);
        return new File(dir, FILE_VERSION_UPDATE);
    }

    /**
     * @return
     */
    public File getDBFilePath() {
        File dir = new File(STORAGE_PATH, FOLDER_DATA);
        return new File(dir, DB_NAME);
    }

    /**
     * @return
     */
    public File getImageFolderPath() {
        return new File(STORAGE_PATH, FOLDER_IMAGES);
    }

    /**
     * @return
     */
    public File getVideoFolderPath() {
        return new File(STORAGE_PATH, FOLDER_VIDEOS);
    }

    /**
     * @return
     */
    public File getSoundFolderPath() {
        return new File(STORAGE_PATH, FOLDER_SOUNDS);
    }

    /**
     * @return
     */
    public File getUpdateFolderPath() {
        return new File(STORAGE_PATH, FOLDER_UPDATE);
    }

    /**
     * @return
     */
    public File getUpdateFolderPathByUser() {
        UserSession userSession = mPreferencesHelper.getUserSession();
        return new File(STORAGE_PATH, FOLDER_UPDATE + "/" + userSession.getUserId());
    }

    /**
     * @return
     */
    public File getApksFolderPath() {
        UserSession userSession = mPreferencesHelper.getUserSession();
        return new File(STORAGE_PATH, FOLDER_APKS + "/" + userSession.getUserId());
    }

    /**
     * @return
     */
    public synchronized SystemConfig getSystemConfig() {
        if (!mSystemConfig.isRead()) {
            mSystemConfig.readProperties(getSystemConfigFilePath().getPath());
        }

        return mSystemConfig;
    }

    /**
     * @return
     */
    public synchronized UserConfig getUserConfig() {
        if (!mUserConfig.isRead()) {
            mUserConfig.readProperties(getUserConfigFilePath().getPath());
        }

        return mUserConfig;
    }

    /**
     * @return
     */
    public KeyConfig getKeyConfig() {
        if (!mKeyConfig.isRead()) {
            mKeyConfig.readProperties(getKeyFilePath().getPath());
        }
        return mKeyConfig;
    }

    /**
     * @return
     */
    public synchronized VersionConfig getVersionConfig() {
        if (!mVersionConfig.isRead()) {
            mVersionConfig.readProperties(getVersionFilePath().getPath());
        }

        return mVersionConfig;
    }

    /**
     * @return
     */
    public synchronized OtherConfig getOtherConfig() {
        if (!mOtherConfig.isRead()) {
            mOtherConfig.readProperties(getOtherConfigFilePath().getPath());
        }

        return mOtherConfig;
    }

    public String getKey() {
        return getKeyConfig().getString(KeyConfig.PARAM_KEY);
    }

    public boolean setKey(String key) {
        getKeyConfig().set(KeyConfig.PARAM_KEY, key);
        return getKeyConfig().writeProperties(getKeyFilePath().getPath());
    }

    public boolean isGridStyle() {
        UserConfig userConfig = getUserConfig();
        int style = userConfig.getInteger(UserConfig.PARAM_SYS_HOME_STYLE, UserConfig.HOME_STYLE_GRID);
        return style == UserConfig.HOME_STYLE_GRID;
    }

    public String getUserChangYong() {
        UserConfig userConfig = getUserConfig();
        return userConfig.getString(UserConfig.PARAM_CB_DEFAULT_CYCBZT);

    }

//    public int getUploadingTimeError() {
//        SystemConfig systemConfig = getSystemConfig();
//        return systemConfig.getInteger(SystemConfig.PARAM_SYS_UPLOADING_TIME_ERROR,
//                SystemConfig.DEFALUT_SYS_UPLOADING_TIME_ERROR);
//    }

    public Observable<Boolean> saveGridStyle(boolean isGrid) {
        UserConfig userConfig = getUserConfig();
        userConfig.set(UserConfig.PARAM_SYS_HOME_STYLE,
                isGrid ? UserConfig.HOME_STYLE_GRID : UserConfig.HOME_STYLE_LIST);
        return Observable.create(new Observable.OnSubscribe<Boolean>() {
            @Override
            public void call(Subscriber<? super Boolean> subscriber) {
                boolean b = getUserConfig().writeProperties(getUserConfigFilePath().getPath());
                subscriber.onNext(b);
                subscriber.onCompleted();
            }
        });
    }

    @Deprecated
    public Observable<Boolean> saveUserChangYong(String zhuangTaiBM) {
        UserConfig userConfig = getUserConfig();
        userConfig.set(UserConfig.PARAM_CB_DEFAULT_CYCBZT, zhuangTaiBM);

        return Observable.create(new Observable.OnSubscribe<Boolean>() {
            @Override
            public void call(Subscriber<? super Boolean> subscriber) {
                boolean b = getUserConfig().writeProperties(getUserConfigFilePath().getPath());
                subscriber.onNext(b);
                subscriber.onCompleted();
            }
        });
    }

    public boolean isPhotoQualityNormal() {
        UserConfig userConfig = getUserConfig();
        int quality = userConfig.getInteger(UserConfig.PARAM_SYS_QUALITY_PHOTO, UserConfig.QUALITY_MIDDLE);
        return quality == UserConfig.QUALITY_MIDDLE;
    }

    public Observable<Boolean> savePhotoQuality(boolean isNormal) {
        UserConfig userConfig = getUserConfig();
        userConfig.set(UserConfig.PARAM_SYS_QUALITY_PHOTO,
                isNormal ? UserConfig.QUALITY_MIDDLE : UserConfig.QUALITY_HIGH);
        return Observable.create(new Observable.OnSubscribe<Boolean>() {
            @Override
            public void call(Subscriber<? super Boolean> subscriber) {
                boolean b = getUserConfig().writeProperties(getUserConfigFilePath().getPath());
                subscriber.onNext(b);
                subscriber.onCompleted();
            }
        });
    }

    @Deprecated
    public boolean saveDataVersion(int version) {
        VersionConfig versionConfig = getVersionConfig();
        versionConfig.set(VersionConfig.DATA_VERSION, version);
        return versionConfig.writeProperties(getVersionFilePath().getPath());
    }

//    public boolean isUpdateVersion() {
//        SystemConfig systemConfig = getSystemConfig();
//        return systemConfig.getBoolean(SystemConfig.PARAM_SYS_UPDATING_VERSION_AUTO, false);
//    }
//
//    public boolean isSyncData() {
//        SystemConfig systemConfig = getSystemConfig();
//        return systemConfig.getBoolean(SystemConfig.PARAM_SYS_SYNC_DATA_AUTO, false);
//    }
//
//    public boolean isSingleUpload() {
//        SystemConfig systemConfig = getSystemConfig();
//        return systemConfig.getBoolean(SystemConfig.PARAM_SYS_SINGLE_UPLOAD, false);
//    }
//
//    public boolean isUploadAfterCeben() {
//        SystemConfig systemConfig = getSystemConfig();
//        return systemConfig.getBoolean(SystemConfig.PARAM_SYS_UPLOAD_AFTER_CEBEN, false);
//    }
//
//    public boolean isUploadAll() {
//        SystemConfig systemConfig = getSystemConfig();
//        return systemConfig.getBoolean(SystemConfig.PARAM_SYS_UPLOADING_TOTAL, false);
//    }
//
//    public boolean isDownloadAll() {
//        SystemConfig systemConfig = getSystemConfig();
//        return systemConfig.getBoolean(SystemConfig.PARAM_SYS_DOWNLOADING_TOTAL, false);
//    }
//
//    public String getBaseUri() {
//        SystemConfig systemConfig = getSystemConfig();
//        return systemConfig.getString(SystemConfig.PARAM_SERVER_BASE_URI);
//    }
//
//    public String getBrokerUrl() {
//        SystemConfig systemConfig = getSystemConfig();
//        return systemConfig.getString(SystemConfig.PARAM_BROKER_URL);
//    }
//
//    public int getKeepAliveInterval() {
//        SystemConfig systemConfig = getSystemConfig();
//        return systemConfig.getInteger(SystemConfig.PARAM_KEEP_LIVE_INTERVAL,
//                SystemConfig.KEEP_LIVE_INTERVAL_DEFAULT_VALUE);
//    }
//
//    public boolean isLeftOrRightOperation() {
//        SystemConfig systemConfig = getSystemConfig();
//        return systemConfig.getBoolean(SystemConfig.PARAM_SYS_OPERATION_LEFTORRIGHT, false);
//    }
//
//    public String getRegion() {
//        SystemConfig systemConfig = getSystemConfig();
//        return systemConfig.getString(SystemConfig.PARAM_SYS_REGION);
//    }
//
//    public SystemConfig.MonitorType getMonitorType() {
//        SystemConfig systemConfig = getSystemConfig();
//        String name = systemConfig.getString(SystemConfig.PARAM_SYS_MONITOR_TYPE);
//        return SystemConfig.MonitorType.toMonitorType(name);
//    }
//
//    public String getCountlyUri() {
//        SystemConfig systemConfig = getSystemConfig();
//        return systemConfig.getString(SystemConfig.PARAM_COUNTLY_SERVER_URI);
//    }
//
//    public Observable<Boolean> saveUpdateVersion(boolean flag) {
//        SystemConfig systemConfig = getSystemConfig();
//        systemConfig.set(SystemConfig.PARAM_SYS_UPDATING_VERSION_AUTO,
//                flag);
//        return Observable.create(new Observable.OnSubscribe<Boolean>() {
//            @Override
//            public void call(Subscriber<? super Boolean> subscriber) {
//                boolean b = getSystemConfig().writeProperties(getSystemConfigFilePath().getPath());
//                subscriber.onNext(b);
//                subscriber.onCompleted();
//            }
//        });
//    }
//
//    public Observable<Boolean> saveSyncData(boolean flag) {
//        SystemConfig systemConfig = getSystemConfig();
//        systemConfig.set(SystemConfig.PARAM_SYS_SYNC_DATA_AUTO,
//                flag);
//        return Observable.create(new Observable.OnSubscribe<Boolean>() {
//            @Override
//            public void call(Subscriber<? super Boolean> subscriber) {
//                boolean b = getSystemConfig().writeProperties(getSystemConfigFilePath().getPath());
//                subscriber.onNext(b);
//                subscriber.onCompleted();
//            }
//        });
//    }
//
//    public Observable<Boolean> saveSingleUpload(boolean flag) {
//        SystemConfig systemConfig = getSystemConfig();
//        systemConfig.set(SystemConfig.PARAM_SYS_SINGLE_UPLOAD,
//                flag);
//        return Observable.create(new Observable.OnSubscribe<Boolean>() {
//            @Override
//            public void call(Subscriber<? super Boolean> subscriber) {
//                boolean b = getSystemConfig().writeProperties(getSystemConfigFilePath().getPath());
//                subscriber.onNext(b);
//                subscriber.onCompleted();
//            }
//        });
//    }
//
//    public Observable<Boolean> saveUploadAfterCeben(boolean flag) {
//        SystemConfig systemConfig = getSystemConfig();
//        systemConfig.set(SystemConfig.PARAM_SYS_UPLOAD_AFTER_CEBEN,
//                flag);
//        return Observable.create(new Observable.OnSubscribe<Boolean>() {
//            @Override
//            public void call(Subscriber<? super Boolean> subscriber) {
//                boolean b = getSystemConfig().writeProperties(getSystemConfigFilePath().getPath());
//                subscriber.onNext(b);
//                subscriber.onCompleted();
//            }
//        });
//    }
//
//    public Observable<Boolean> saveUploadAll(boolean flag) {
//        SystemConfig systemConfig = getSystemConfig();
//        systemConfig.set(SystemConfig.PARAM_SYS_UPLOADING_TOTAL,
//                flag);
//        return Observable.create(new Observable.OnSubscribe<Boolean>() {
//            @Override
//            public void call(Subscriber<? super Boolean> subscriber) {
//                boolean b = getSystemConfig().writeProperties(getSystemConfigFilePath().getPath());
//                subscriber.onNext(b);
//                subscriber.onCompleted();
//            }
//        });
//    }
//
//    public Observable<Boolean> saveDownloadAll(boolean flag) {
//        SystemConfig systemConfig = getSystemConfig();
//        systemConfig.set(SystemConfig.PARAM_SYS_DOWNLOADING_TOTAL,
//                flag);
//        return Observable.create(new Observable.OnSubscribe<Boolean>() {
//            @Override
//            public void call(Subscriber<? super Boolean> subscriber) {
//                boolean b = getSystemConfig().writeProperties(getSystemConfigFilePath().getPath());
//                subscriber.onNext(b);
//                subscriber.onCompleted();
//            }
//        });
//    }
//
//    public Observable<Boolean> saveNetWorkSetting(String baseUri, String brokeUrl, String countlyUri) {
//        SystemConfig systemConfig = getSystemConfig();
//        systemConfig.set(SystemConfig.PARAM_SERVER_BASE_URI,
//                baseUri);
//        systemConfig.set(SystemConfig.PARAM_BROKER_URL,
//                brokeUrl);
//        systemConfig.set(SystemConfig.PARAM_COUNTLY_SERVER_URI
//                , countlyUri);
//        return Observable.create(new Observable.OnSubscribe<Boolean>() {
//            @Override
//            public void call(Subscriber<? super Boolean> subscriber) {
//                boolean b = getSystemConfig().writeProperties(getSystemConfigFilePath().getPath());
//                subscriber.onNext(b);
//                subscriber.onCompleted();
//            }
//        });
//    }
//
//    public Observable<Boolean> saveNetWorkSetting(String baseUri, String brokeUrl) {
//        SystemConfig systemConfig = getSystemConfig();
//        systemConfig.set(SystemConfig.PARAM_SERVER_BASE_URI,
//                baseUri);
//        systemConfig.set(SystemConfig.PARAM_BROKER_URL,
//                brokeUrl);
//        return Observable.create(new Observable.OnSubscribe<Boolean>() {
//            @Override
//            public void call(Subscriber<? super Boolean> subscriber) {
//                boolean b = getSystemConfig().writeProperties(getSystemConfigFilePath().getPath());
//                subscriber.onNext(b);
//                subscriber.onCompleted();
//            }
//        });
//    }
//
//    public Observable<Boolean> saveLeftOrRightOperation(boolean flag) {
//        SystemConfig systemConfig = getSystemConfig();
//        systemConfig.set(SystemConfig.PARAM_SYS_OPERATION_LEFTORRIGHT,
//                flag);
//        return Observable.create(new Observable.OnSubscribe<Boolean>() {
//            @Override
//            public void call(Subscriber<? super Boolean> subscriber) {
//                boolean b = getSystemConfig().writeProperties(getSystemConfigFilePath().getPath());
//                subscriber.onNext(b);
//                subscriber.onCompleted();
//            }
//        });
//    }
//
//    public Observable<Boolean> saveKeepAliveInterval(int keepAliveInterval) {
//        SystemConfig systemConfig = getSystemConfig();
//        systemConfig.set(SystemConfig.PARAM_KEEP_LIVE_INTERVAL,
//                keepAliveInterval);
//
//        return Observable.create(new Observable.OnSubscribe<Boolean>() {
//            @Override
//            public void call(Subscriber<? super Boolean> subscriber) {
//                boolean b = getSystemConfig().writeProperties(getSystemConfigFilePath().getPath());
//                subscriber.onNext(b);
//                subscriber.onCompleted();
//            }
//        });
//    }

    public String getReadingDate(boolean isStart) {
        OtherConfig otherConfig = getOtherConfig();
        int date;
        if (isStart) {
            date = otherConfig.getInteger(OtherConfig.READING_START_DATE,
                    OtherConfig.READING_READING_START_DATE);
        } else {
            date = otherConfig.getInteger(OtherConfig.READING_END_DATE,
                    OtherConfig.READING_READING_END_DATE);
        }

        return String.valueOf(date);
    }

    public boolean isCurrentReadingDateValid() {
        OtherConfig otherConfig = getOtherConfig();
        int startDate = otherConfig.getInteger(OtherConfig.READING_START_DATE,
                OtherConfig.READING_READING_START_DATE);
        int endDate = otherConfig.getInteger(OtherConfig.READING_END_DATE,
                OtherConfig.READING_READING_END_DATE);
        if (startDate > endDate) {
            return true;
        }

        Calendar cal = Calendar.getInstance();
        int day = cal.get(Calendar.DATE);//获取日
        return (startDate <= day) && (day <= endDate);
    }

//    public int getMonthCount() {
//        return getSystemConfig().getInteger(SystemConfig.CB_DATA_DOWNLOAD_TIME,
//                SystemConfig.DEFALUT_DOWNLOAD_TIME);
//    }
//
//    public boolean isDownloadingTotal() {
//        return getSystemConfig().getBoolean(SystemConfig.PARAM_SYS_DOWNLOADING_TOTAL, false);
//    }
//
//    public Observable<Boolean> saveCountlyUri(String countlyUri) {
//        SystemConfig systemConfig = getSystemConfig();
//        systemConfig.set(SystemConfig.PARAM_COUNTLY_SERVER_URI, countlyUri);
//        return Observable.create(new Observable.OnSubscribe<Boolean>() {
//            @Override
//            public void call(Subscriber<? super Boolean> subscriber) {
//                boolean b = getSystemConfig().writeProperties(getSystemConfigFilePath().getPath());
//                subscriber.onNext(b);
//                subscriber.onCompleted();
//            }
//        });
//    }
//
//    public String getGpsType() {
//        return getSystemConfig().getString(SystemConfig.PARAM_SYST_GPS_TYPE);
//    }
//
//    public Observable<Boolean> saveGpsType(String typeListValue) {
//        SystemConfig systemConfig = getSystemConfig();
//        systemConfig.set(SystemConfig.PARAM_SYST_GPS_TYPE, typeListValue);
//        return Observable.create(new Observable.OnSubscribe<Boolean>() {
//            @Override
//            public void call(Subscriber<? super Boolean> subscriber) {
//                boolean b = getSystemConfig().writeProperties(getSystemConfigFilePath().getPath());
//                subscriber.onNext(b);
//                subscriber.onCompleted();
//            }
//        });
//    }
//
//    public Observable<Boolean> saveMonitorType(String monitorTypeValue) {
//        SystemConfig systemConfig = getSystemConfig();
//        systemConfig.set(SystemConfig.PARAM_SYS_MONITOR_TYPE, monitorTypeValue);
//        return Observable.create(new Observable.OnSubscribe<Boolean>() {
//            @Override
//            public void call(Subscriber<? super Boolean> subscriber) {
//                boolean b = getSystemConfig().writeProperties(getSystemConfigFilePath().getPath());
//                subscriber.onNext(b);
//                subscriber.onCompleted();
//            }
//        });
//    }

    public String getRoles() {
        return mPreferencesHelper.getUserSession().get_roles();
    }

    public Observable<Boolean> clearHistory() {
        return Observable.create(new Observable.OnSubscribe<Boolean>() {
            @Override
            public void call(Subscriber<? super Boolean> subscriber) {
                if (subscriber.isUnsubscribed()) {
                    return;
                }

                mPreferencesHelper.getUserSession().setClearing(true);
                boolean b = mPreferencesHelper.saveUserSession();
                subscriber.onNext(b);
                subscriber.onCompleted();
            }
        });
    }

    public Observable<Boolean> restoreFactory() {
        return Observable.create(new Observable.OnSubscribe<Boolean>() {
            @Override
            public void call(Subscriber<? super Boolean> subscriber) {
                if (subscriber.isUnsubscribed()) {
                    return;
                }

                mPreferencesHelper.getUserSession().setRecovery(true);
                boolean b = mPreferencesHelper.saveUserSession();
                subscriber.onNext(b);
                subscriber.onCompleted();
            }
        });
    }

    public Observable<Boolean> clearAccount() {
        return Observable.create(new Observable.OnSubscribe<Boolean>() {
            @Override
            public void call(Subscriber<? super Boolean> subscriber) {
                if (subscriber.isUnsubscribed()) {
                    return;
                }

                UserSession userSession = mPreferencesHelper.getUserSession();
                userSession.setAccount("");
                userSession.set_password("");
                boolean b = mPreferencesHelper.saveUserSession();
                subscriber.onNext(b);
                subscriber.onCompleted();
            }
        });
    }

    /** Read Apk Config **/
//    public Observable<List<Component>> readApkConfig(){
//        return Observable.create(new Observable.OnSubscribe<List<Component>>() {
//            @Override
//            public void call(Subscriber<? super List<Component>> subscriber) {
//                File fileDir = getApksFolderPath();
//                if(!fileDir.exists()){//根目录不存在
//                    subscriber.onNext(new ArrayList<Component>());
//                }
//                try{
//                    File fileConfig = new File(getApksFolderPath(), FILE_CONFIG_XML);
//                    if(!fileConfig.exists()){//文件不存在
//                        subscriber.onNext(new ArrayList<Component>());
//                    }
//                    //read apk config file
//                    DUConfigXmlFile duConfigXmlFile = Parser.readConfigXml(new FileInputStream(fileConfig));
//                    subscriber.onNext(duConfigXmlFile.getComponentList());
//                }catch (Exception e){
//                    e.printStackTrace();
//                    subscriber.onError(e);
//                }
//
//            }
//        });
//    }
}
