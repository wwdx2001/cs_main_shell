package com.sh3h.datautil.data;

import android.content.Context;

import com.sh3h.datautil.data.entity.DUConfigXmlFile;
import com.sh3h.datautil.data.entity.DUDeviceInfo;
import com.sh3h.datautil.data.entity.DUFile;
import com.sh3h.datautil.data.entity.DUFileResult;
import com.sh3h.datautil.data.entity.DULocationTrack;
import com.sh3h.datautil.data.entity.DULoginInfo;
import com.sh3h.datautil.data.entity.DULogoutInfo;
import com.sh3h.datautil.data.entity.DUTokenInfo;
import com.sh3h.datautil.data.entity.base.DUEntitiesResult;
import com.sh3h.datautil.data.entity.base.DUEntityResult;
import com.sh3h.datautil.data.entity.result.DUApkInfoResult;
import com.sh3h.datautil.data.entity.result.DUCheckResult;
import com.sh3h.datautil.data.entity.result.DUCoordinateGasGroupResult;
import com.sh3h.datautil.data.entity.result.DUCoordinateJiangMenResult;
import com.sh3h.datautil.data.entity.result.DUCoordinateResult;
import com.sh3h.datautil.data.entity.result.DUCoordinateYiWuResult;
import com.sh3h.datautil.data.entity.result.DUDeviceResult;
import com.sh3h.datautil.data.entity.result.DUTokenResult;
import com.sh3h.datautil.data.entity.result.DUTrackResult;
import com.sh3h.datautil.data.entity.result.DUUserResult;
import com.sh3h.datautil.data.entity.result.DUUserResultEx;
import com.sh3h.datautil.data.entity.result.DUWordsResult;
import com.sh3h.datautil.data.local.config.ConfigHelper;
import com.sh3h.datautil.data.local.db.DbHelper;
import com.sh3h.datautil.data.local.json.UMengMessage;
import com.sh3h.datautil.data.local.preference.PreferencesHelper;
import com.sh3h.datautil.data.local.preference.UserSession;
import com.sh3h.datautil.data.local.xml.XmlHelper;
import com.sh3h.datautil.data.remote.Downloader;
import com.sh3h.datautil.data.remote.HttpHelper;
import com.sh3h.datautil.data.remote.Parser;
import com.sh3h.datautil.event.DataBusEvent;
import com.sh3h.datautil.exception.DUException;
import com.sh3h.datautil.injection.annotation.ApplicationContext;
import com.sh3h.datautil.util.EventPosterHelper;
import com.sh3h.mobileutil.util.LogUtil;
import com.sh3h.mobileutil.util.TextUtil;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.plugins.RxJavaErrorHandler;
import rx.plugins.RxJavaPlugins;

@Singleton
public class DataManager {
    private static final String TAG = "DataManager";

    private final Context mContext;
    private final HttpHelper mHttpHelper;
    private final DbHelper mDbHelper;
    private final PreferencesHelper mPreferencesHelper;
    private final ConfigHelper mConfigHelper;
    private final EventPosterHelper mEventPoster;
    private final Downloader mDownloader;
    private final Parser mParser;
    private final XmlHelper mXmlHelper;
    private final UMengMessage mUMengMessage;

    @Inject
    public DataManager(@ApplicationContext Context context,
                       HttpHelper httpHelper,
                       PreferencesHelper preferencesHelper,
                       DbHelper dbHelper,
                       ConfigHelper configHelper,
                       Downloader downloader,
                       EventPosterHelper eventPosterHelper,
                       Parser parser,
                       XmlHelper xmlHelper,
                       UMengMessage uMengMessage) {
        mContext = context;
        mHttpHelper = httpHelper;
        mPreferencesHelper = preferencesHelper;
        mDbHelper = dbHelper;
        mConfigHelper = configHelper;
        mDownloader = downloader;
        mEventPoster = eventPosterHelper;
        mParser = parser;
        mXmlHelper = xmlHelper;
        mUMengMessage = uMengMessage;
    }

    public void destroy() {
        mDbHelper.destroy();
    }

    public void clearData() {
        mDbHelper.clearData();
    }

    public Observable<Void> init() {
        final int NONE = 0;
        final int CLEARING = 1; // clear history
        final int RECOVERY = 2; // restore factory

        return Observable.create(new Observable.OnSubscribe<Integer>() {
            @Override
            public void call(Subscriber<? super Integer> subscriber) {
                if (subscriber.isUnsubscribed()) {
                    return;
                }

                boolean result = false;
                UserSession userSession = mPreferencesHelper.getUserSession();
                if (userSession.isRecovery()) {
                    result = mPreferencesHelper.clearUserSession();
                    subscriber.onNext(result ? RECOVERY : NONE);
                } else if (userSession.isClearing()) {
                    userSession.setClearing(false);
                    result = mPreferencesHelper.saveUserSession();
                    subscriber.onNext(result ? CLEARING : NONE);
                } else {
                    subscriber.onNext(NONE);
                }

                subscriber.onCompleted();
            }
        }).concatMap(new Func1<Integer, Observable<? extends Void>>() {
            @Override
            public Observable<? extends Void> call(Integer integer) {
                switch (integer) {
                    case CLEARING:
                        return mConfigHelper.clearAndInitConfigs(false);
                    case RECOVERY:
                        return mConfigHelper.clearAndInitConfigs(true);
                    case NONE:
                    default:
                        return mConfigHelper.initDefaultConfigs();
                }
            }
        });
    }

    /**
     * register the device
     *
     * @param duDeviceInfo device information
     * @return observable
     */
    public Observable<Boolean> register(DUDeviceInfo duDeviceInfo) {
        return mHttpHelper.register(duDeviceInfo)
                .map(new Func1<DUEntityResult<DUDeviceResult>, Boolean>() {
                    @Override
                    public Boolean call(DUEntityResult<DUDeviceResult> duEntityResult) {
                        if ((duEntityResult.getCode() != DUEntityResult.SUCCESS_CODE)
                                || (duEntityResult.getData() == null)) {
                            String message = duEntityResult.getMessage();
                            if (TextUtil.isNullOrEmpty(message)) {
                                message = "the result is error!";
                            }

                            throw new IllegalArgumentException(message);
                        }

                        DUDeviceResult duDeviceResult = duEntityResult.getData();
                        String key = duDeviceResult.getActiveCode();
                        return !TextUtil.isNullOrEmpty(key) && mConfigHelper.setKey(key);
                    }
                });
    }

    /**
     * login
     *
     * @param duLoginInfo
     * @return
     */
    public Observable<Boolean> login(final DULoginInfo duLoginInfo) {
        return mHttpHelper.login(duLoginInfo)
                .map(new Func1<DUUserResultEx, Boolean>() {
                    @Override
                    public Boolean call(DUUserResultEx duUserResultEx) {
                        String accessToken = duUserResultEx.getAccessToken();
                        if (TextUtil.isNullOrEmpty(accessToken)) {
                            throw new NullPointerException(DUException.RETURN_NULL.getName());
                        }

                        DUUserResult duUserResult = duUserResultEx.getDuUserResult();
                        if (duUserResult == null) {
                            throw new NullPointerException(DUException.RETURN_NULL.getName());
                        }

                        UserSession userSession = mPreferencesHelper.getUserSession();
                        userSession.setAccount(duLoginInfo.getAccount());
                        userSession.set_password(duLoginInfo.getPwd());
                        userSession.setUserId(duUserResult.getUserId());
                        userSession.setUserName(duUserResult.getUserName());
                        userSession.set_roles(duUserResult.getRoles());
                        userSession.setLoginFirst(false);
                        userSession.setAccessToken(accessToken);
                        userSession.setDepartmentName("sh3h");
                        userSession.setDepartmentId(1);
                        return userSession.save();
                    }
                });
    }

    /**
     * get the list of apks
     *
     * @param userId
     * @param deviceId
     * @return
     */
    public Observable<DUEntitiesResult<DUApkInfoResult>> getApkList(int userId, String deviceId) {
        return mHttpHelper.getApkList(userId, deviceId);
    }

    /**
     * download the apk file and parse it
     *
     * @param duFile
     * @return
     */
    public Observable<DUFileResult> downloadApkFile(final DUFile duFile) {
        Observable<DUFileResult> observable;
        if (!TextUtil.isNullOrEmpty(duFile.getUrl())) {
            observable = mDownloader.downloadFile(duFile);
        } else {
            observable = mHttpHelper.getAppCheck(duFile.getAppId(), duFile.getVersionCode())
                    .concatMap(new Func1<DUEntityResult<DUCheckResult>, Observable<? extends DUFileResult>>() {
                        @Override
                        public Observable<? extends DUFileResult> call(DUEntityResult<DUCheckResult> duEntityResult) {
                            if ((duEntityResult.getCode() != DUEntityResult.SUCCESS_CODE)
                                    || (duEntityResult.getData() == null)
                                    || (duEntityResult.getData().getInnerUrl() == null)
                                    || (duEntityResult.getData().getOuterUrl() == null)) {
                                LogUtil.i(TAG, "---downloadApkFile concatMap 1---");
                                throw new IllegalArgumentException(DUException.PARAM_ERROR.getName());
                            }

                            LogUtil.i(TAG, "---downloadApkFile concatMap 2---");
                            boolean isUsingReservedAddress = mConfigHelper.isUsingReservedAddress();
                            duFile.setUrl(isUsingReservedAddress ? duEntityResult.getData().getInnerUrl() : duEntityResult.getData().getOuterUrl());
                            duFile.setVersionCode(duEntityResult.getData().getVer());
                            return mDownloader.downloadFile(duFile);
                        }
                    });
        }

        return observable
                .doOnCompleted(new Action0() {
                    @Override
                    public void call() {
                        if (TextUtil.isNullOrEmpty(duFile.getAppName())
                                || TextUtil.isNullOrEmpty(duFile.getAppId())
                                || TextUtil.isNullOrEmpty(duFile.getUrl())
                                || TextUtil.isNullOrEmpty(duFile.getPath())) {
                            LogUtil.i(TAG, "---downloadApkFile doOnCompleted---1");
                            throw new IllegalArgumentException(DUException.PARAM_ERROR.getName());
                        }

                        LogUtil.i(TAG, "---downloadApkFile doOnCompleted---2");
                        if (duFile.isExistingLocal()) {
                            mEventPoster.postEventSafely(new DataBusEvent.FileResult(
                                    DataBusEvent.FileResult.FilterType.APK,
                                    duFile.getAppId(), true, null));
                            return;
                        }

                        boolean b = mParser.parseFile(duFile);
                        mEventPoster.postEventSafely(new DataBusEvent.FileResult(
                                DataBusEvent.FileResult.FilterType.APK,
                                duFile.getAppId(), b, null));
                    }
                });
    }

    /**
     * download the config
     *
     * @return
     */
    public Observable<Boolean> downloadConfigFile() {
        return mHttpHelper.getConfigCheck(mPreferencesHelper.getUserSession().getUserId())
                .map(new Func1<DUEntitiesResult<DUConfigXmlFile.Component>, Boolean>() {
                    @Override
                    public Boolean call(DUEntitiesResult<DUConfigXmlFile.Component> result) {
                        if ((result.getCode() != DUEntityResult.SUCCESS_CODE)
                                || (result.getData() == null)
                                || (result.getData().size() <= 0)) {
                            LogUtil.i(TAG, "---downloadConfigFile concatMap 1---");
                            throw new IllegalArgumentException(DUException.PARAM_ERROR.getName());
                        }

                        List<DUConfigXmlFile.Component> serverComponentList = result.getData();
                        DUConfigXmlFile localDuConfigXmlFile = mXmlHelper.getDuConfigXmlFile();
                        List<DUConfigXmlFile.Component> newComponentList;
                        if ((localDuConfigXmlFile == null)
                                || (localDuConfigXmlFile.getComponentList() == null)
                                || (localDuConfigXmlFile.getComponentList().size() <= 0)) {
                            LogUtil.i(TAG, "localDuConfigXmlFile hasn't any component");
                            newComponentList = serverComponentList;
                        } else {
                            List<DUConfigXmlFile.Component> localComponentList =
                                    localDuConfigXmlFile.getComponentList();
                            newComponentList = new ArrayList<>();

                            //以服务器上的为准
                            for (DUConfigXmlFile.Component serverComponent : serverComponentList) {
                                String serverKey = serverComponent.getFunctionKey();
                                if (TextUtil.isNullOrEmpty(serverKey)) {
                                    continue;
                                }

                                serverComponent.setValid(false);
                                for (DUConfigXmlFile.Component localComponent : localComponentList) {
                                    String localKey = localComponent.getFunctionKey();
                                    if (TextUtil.isNullOrEmpty(localKey)) {
                                        continue;
                                    }

                                    if (serverKey.equals(localKey)) {
                                        serverComponent.setOrder(localComponent.getOrder());
                                        serverComponent.setCount(localComponent.getCount());
                                        serverComponent.setValid(localComponent.isValid());
                                        break;
                                    }
                                }

                                newComponentList.add(serverComponent);
                            }
                        }

                        DUConfigXmlFile duConfigXmlFile = new DUConfigXmlFile();
                        duConfigXmlFile.setComponentList(newComponentList);
                        mXmlHelper.setDuConfigXmlFile(duConfigXmlFile);
                        mXmlHelper.saveDuConfigXmlFile();
                        return true;
                    }
                });
    }

    /**
     * download the data file and parse it
     *
     * @param duFile
     * @return
     */
    public Observable<DUFileResult> downloadDataFile(final DUFile duFile) {
        return mHttpHelper.getDataCheck(duFile.getAppId(), duFile.getVersionCode())
                .concatMap(new Func1<DUEntityResult<DUCheckResult>, Observable<? extends DUFileResult>>() {
                    @Override
                    public Observable<? extends DUFileResult> call(DUEntityResult<DUCheckResult> duEntityResult) {
                        if ((duEntityResult.getCode() != DUEntityResult.SUCCESS_CODE)
                                || (duEntityResult.getData() == null)
                                || (duEntityResult.getData().getInnerUrl() == null)
                                || (duEntityResult.getData().getOuterUrl() == null)) {
                            LogUtil.i(TAG, "---downloadDataFile concatMap 1---");
                            throw new IllegalArgumentException(DUException.PARAM_ERROR.getName());
                        }

                        LogUtil.i(TAG, "---downloadDataFile concatMap 2---");
                        boolean isUsingReservedAddress = mConfigHelper.isUsingReservedAddress();
                        duFile.setUrl(isUsingReservedAddress ? duEntityResult.getData().getInnerUrl() : duEntityResult.getData().getOuterUrl());
                        duFile.setVersionCode(duEntityResult.getData().getVer());
                        return mDownloader.downloadFile(duFile);
                    }
                })
                .doOnCompleted(new Action0() {
                    @Override
                    public void call() {
                        if (TextUtil.isNullOrEmpty(duFile.getAppName())
                                || TextUtil.isNullOrEmpty(duFile.getAppId())
                                || TextUtil.isNullOrEmpty(duFile.getUrl())
                                || TextUtil.isNullOrEmpty(duFile.getPath())) {
                            LogUtil.i(TAG, "---downloadDataFile doOnCompleted---1");
                            throw new IllegalArgumentException(DUException.PARAM_ERROR.getName());
                        }

                        LogUtil.i(TAG, "---downloadDataFile doOnCompleted---2");
                        boolean b = mParser.parseFile(duFile);
                        mEventPoster.postEventSafely(new DataBusEvent.FileResult(
                                DataBusEvent.FileResult.FilterType.DATA,
                                duFile.getAppId(), b, null));
                    }
                });
    }

    /**
     * send the track
     *
     * @param duLocationTrack
     * @return
     */
    public Observable<DUEntityResult<DUTrackResult>> sendTrack(final DULocationTrack duLocationTrack,
                                                               final boolean needClearTrack) {
        return mHttpHelper.sendTrack(duLocationTrack)
                .doOnNext(new Action1<DUEntityResult<DUTrackResult>>() {
                    @Override
                    public void call(DUEntityResult<DUTrackResult> duTrackResultDUEntityResult) {
                        if (duTrackResultDUEntityResult.getCode() == DUEntityResult.SUCCESS_CODE) {
                            mDbHelper.saveTrack(duLocationTrack, DULocationTrack.UPLOAD_SUCCESS,
                                    needClearTrack);
                        } else {
                            mDbHelper.saveTrack(duLocationTrack, DULocationTrack.UPLOAD_FAIL,
                                    needClearTrack);
                        }
                    }
                }).doOnError(new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        mDbHelper.saveTrack(duLocationTrack, DULocationTrack.UPLOAD_FAIL,
                                needClearTrack);
                    }
                });
    }

    /**
     * downlad all words
     *
     * @param group
     * @return
     */
    public Observable<Boolean> downloadWords(String group) {
        return mHttpHelper.getWords(group)
                .concatMap(new Func1<DUEntitiesResult<DUWordsResult>, Observable<Boolean>>() {
                    @Override
                    public Observable<Boolean> call(DUEntitiesResult<DUWordsResult> duWordsResultDUEntitiesResult) {
                        if ((duWordsResultDUEntitiesResult.getCode() != DUEntityResult.SUCCESS_CODE)
                                || (duWordsResultDUEntitiesResult.getData() == null)) {
                            LogUtil.i(TAG, "---isDownAllWords concatMap 1---");
                            throw new IllegalArgumentException(DUException.PARAM_ERROR.getName());
                        }

                        return mDbHelper.insertWords(duWordsResultDUEntitiesResult.getData());
                    }
                });
    }

    /**
     * initialize logger file
     */
    public void initLogger(String suffix) {
        LogUtil.initLogger(mConfigHelper.getLogFilePath(suffix).getPath());
    }

    /**
     * close the logger file
     */
    public void closeLogger() {
        LogUtil.closeLogger();
    }

    /**
     * 转换坐标
     *
     * @param longitude
     * @param latitude
     * @return
     */
    public Observable<DUCoordinateResult> transformCoordinateForGasGroup(double longitude, double latitude) {
        return mHttpHelper.transformCoordinateForGasGroup(longitude, latitude)
                .map(new Func1<DUEntityResult<DUCoordinateGasGroupResult>, DUCoordinateResult>() {
                    @Override
                    public DUCoordinateResult call(DUEntityResult<DUCoordinateGasGroupResult> duEntityResult) {
                        if ((duEntityResult.getCode() != DUEntityResult.SUCCESS_CODE)
                                || (duEntityResult.getData() == null)) {
                            String message = duEntityResult.getMessage();
                            if (TextUtil.isNullOrEmpty(message)) {
                                message = "the result is error!";
                            }

                            throw new IllegalArgumentException(message);
                        }

                        DUCoordinateGasGroupResult duCoordinateGasGroupResult = duEntityResult.getData();
                        return new DUCoordinateResult(TextUtil.getDouble(duCoordinateGasGroupResult.getX()),
                                TextUtil.getDouble(duCoordinateGasGroupResult.getY()));
                    }
                });
    }

    public Observable<DUCoordinateResult> transformCoordinateForYiWu(double longitude, double latitude) {
        return mHttpHelper.transformCoordinateForYiWu(latitude, longitude)
                .map(new Func1<DUCoordinateYiWuResult, DUCoordinateResult>() {
                    @Override
                    public DUCoordinateResult call(DUCoordinateYiWuResult duCoordinateYiWuResult) {
                        if (!duCoordinateYiWuResult.isSuccess()) {
                            String message = duCoordinateYiWuResult.getMessage();
                            if (TextUtil.isNullOrEmpty(message)) {
                                message = "the result is error!";
                            }

                            throw new IllegalArgumentException(message);
                        }

                        return new DUCoordinateResult(duCoordinateYiWuResult.getX1(),
                                duCoordinateYiWuResult.getY1());
                    }
                });
    }

    public Observable<DUCoordinateResult> transformCoordinateForJiangMen(double longitude, double latitude) {
        return mHttpHelper.transformCoordinateForJiangMen(longitude, latitude)
                .map(new Func1<DUCoordinateJiangMenResult, DUCoordinateResult>() {
                    @Override
                    public DUCoordinateResult call(DUCoordinateJiangMenResult duCoordinateJiangMenResult) {
                        if (!duCoordinateJiangMenResult.isSuccess()
                                || TextUtil.isNullOrEmpty(duCoordinateJiangMenResult.getCoor())) {
                            throw new IllegalArgumentException("the result of transformCoordinate is error!");
                        }

                        String data = duCoordinateJiangMenResult.getCoor();
                        String[] strings = data.split(",");
                        if (strings.length != 2) {
                            throw new IllegalArgumentException("the data of transformCoordinate is error!");
                        }

                        String longitude = strings[0];
                        String latitude = strings[1];
                        if (!isNumber(longitude)
                                || !isNumber(latitude)) {
                            throw new IllegalArgumentException("the type of transformCoordinate is error!");
                        }

                        return new DUCoordinateResult(Double.parseDouble(longitude),
                                Double.parseDouble(latitude));
                    }
                });
    }

    public Observable<DUTokenResult> checkToken(DUTokenInfo duTokenInfo) {
        return mHttpHelper.checkToken(duTokenInfo)
                .map(new Func1<DUEntityResult<DUTokenResult>, DUTokenResult>() {
                    @Override
                    public DUTokenResult call(DUEntityResult<DUTokenResult> duTokenResultDUEntityResult) {
                        if ((duTokenResultDUEntityResult.getCode() != DUEntityResult.SUCCESS_CODE)
                                || (duTokenResultDUEntityResult.getData() == null)) {
                            String message = duTokenResultDUEntityResult.getMessage();
                            if (TextUtil.isNullOrEmpty(message)) {
                                message = "the result is error!";
                            }

                            throw new IllegalArgumentException(message);
                        }

                        return duTokenResultDUEntityResult.getData();
                    }
                });
    }

    public Observable<Boolean> logout(DULogoutInfo duLogoutInfo) {
        return mHttpHelper.logout(duLogoutInfo)
                .map(new Func1<DUEntityResult<Boolean>, Boolean>() {
                    @Override
                    public Boolean call(DUEntityResult<Boolean> booleanDUEntityResult) {
                        if ((booleanDUEntityResult.getCode() != DUEntityResult.SUCCESS_CODE)
                                || (booleanDUEntityResult.getData() == null)) {
                            String message = booleanDUEntityResult.getMessage();
                            if (TextUtil.isNullOrEmpty(message)) {
                                message = "the result is error!";
                            }

                            throw new IllegalArgumentException(message);
                        }
                        return booleanDUEntityResult.getData();
                    }
                });
    }

    public String getMessageInfo(int type) {
        if (!mUMengMessage.isRead()) {
            mUMengMessage.read();
        }

        return mUMengMessage.getInfo(type);
    }

    private boolean isNumber(String text) {
        return !TextUtil.isNullOrEmpty(text) && text.matches("-?[0-9]+.*[0-9]*");
    }

    static {
        RxJavaPlugins.getInstance().registerErrorHandler(new RxJavaErrorHandler() {
            @Override
            public void handleError(Throwable e) {
                e.printStackTrace();
            }
        });
    }
}
