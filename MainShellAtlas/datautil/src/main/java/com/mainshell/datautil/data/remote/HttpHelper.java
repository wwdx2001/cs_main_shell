package com.mainshell.datautil.data.remote;

import android.content.Context;

import com.mainshell.datautil.data.entity.DUConfigXmlFile;
import com.mainshell.datautil.data.entity.DUDeviceInfo;
import com.mainshell.datautil.data.entity.DUKeepAliveInfo;
import com.mainshell.datautil.data.entity.DUKeepAliveResult;
import com.mainshell.datautil.data.entity.DULocationTrack;
import com.mainshell.datautil.data.entity.DULoginInfo;
import com.mainshell.datautil.data.entity.base.DUEntitiesResult;
import com.mainshell.datautil.data.entity.base.DUEntityResult;
import com.mainshell.datautil.data.entity.result.DUApkInfoResult;
import com.mainshell.datautil.data.entity.result.DUCheckResult;
import com.mainshell.datautil.data.entity.result.DUDeviceResult;
import com.mainshell.datautil.data.entity.result.DULoginResult;
import com.mainshell.datautil.data.entity.result.DUTrackResult;
import com.mainshell.datautil.data.entity.result.DUUserResult;
import com.mainshell.datautil.data.entity.result.DUUserResultEx;
import com.mainshell.datautil.data.entity.result.DUWordsResult;
import com.mainshell.datautil.data.local.config.ConfigHelper;
import com.mainshell.datautil.data.local.config.SystemConfig;
import com.mainshell.datautil.exception.DUException;
import com.mainshell.datautil.injection.annotation.ApplicationContext;
import com.squareup.otto.Bus;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;
import rx.functions.Func1;

@Singleton
public class HttpHelper {
    private final Context mContext;
    private final ConfigHelper mConfigHelper;
    private final Bus mBus;
    private boolean isConnected;
    private RestfulApiService restfulApiService;

    @Inject
    public HttpHelper(@ApplicationContext Context context,
                      ConfigHelper configHelper,
                      Bus bus) {
        mContext = context;
        mConfigHelper = configHelper;
        mBus = bus;
        isConnected = false;
        restfulApiService = null;
    }

    /**
     * register the device
     *
     * @param duDeviceInfo
     * @return
     */
    public Observable<DUEntityResult<DUDeviceResult>> register(DUDeviceInfo duDeviceInfo) {
        if (!connect()) {
            return Observable.error(new NullPointerException(DUException.HTTP_ERROR.getName()));
        }

        return restfulApiService.register(duDeviceInfo);
    }

    /**
     * login
     *
     * @param duLoginInfo
     * @return
     */
    public Observable<DUUserResultEx> login(final DULoginInfo duLoginInfo) {
        if (!connect()) {
            return Observable.error(new NullPointerException(DUException.HTTP_ERROR.getName()));
        }

        final DUUserResultEx duUserResultEx = new DUUserResultEx();
        return restfulApiService.login(duLoginInfo)
                .concatMap(new Func1<DUEntityResult<DULoginResult>, Observable<? extends DUEntityResult<DUUserResult>>>() {
                    @Override
                    public Observable<? extends DUEntityResult<DUUserResult>> call(DUEntityResult<DULoginResult> duLoginResultDUEntityResult) {
                        int code = duLoginResultDUEntityResult.getCode();

                        if (code != DUEntityResult.SUCCESS_CODE) {
                            return Observable.error(new Exception(DUException.LOGIN_FAILURE.getName()));
                        }

                        DULoginResult duLoginResult = duLoginResultDUEntityResult.getData();
                        if (duLoginResult == null) {
                            return Observable.error(new Exception(DUException.LOGIN_FAILURE.getName()));
                        }

                        duUserResultEx.setAccessToken(duLoginResult.getAccessToken());
                        return restfulApiService.getUserInfo(duLoginResult.getUserId());
                    }
                })
                .concatMap(new Func1<DUEntityResult<DUUserResult>, Observable<? extends DUUserResultEx>>() {
                    @Override
                    public Observable<? extends DUUserResultEx> call(DUEntityResult<DUUserResult> duUserResultDUEntityResult) {
                        int code = duUserResultDUEntityResult.getCode();
                        if (code != DUEntityResult.SUCCESS_CODE) {
                            return Observable.error(new Exception(DUException.LOGIN_FAILURE.getName()));
                        }

                        DUUserResult duUserResult = duUserResultDUEntityResult.getData();
                        if (duUserResult == null) {
                            return Observable.error(new Exception(DUException.LOGIN_FAILURE.getName()));
                        }

                        duUserResultEx.setDuUserResult(duUserResult);
                        return Observable.just(duUserResultEx);
                    }
                });
//                .flatMap(new Func1<DUEntityResult<DULoginResult>, Observable<DUEntityResult<DUUserResult>>>() {
//                    @Override
//                    public Observable<DUEntityResult<DUUserResult>> call(DUEntityResult<DULoginResult> duEntityResult) {
//                        int code = duEntityResult.getCode();
//
//                        if ((code == DUEntityResult.FAIL_CODE)
//                                || (code != DUEntityResult.SUCCESS_CODE)) {
//                            return Observable.error(new Exception(DUException.LOGIN_FAILURE.getName()));
//                        }
//
//                        DULoginResult duLoginResult = duEntityResult.getData();
//                        if (duLoginResult == null) {
//                            return Observable.error(new Exception(DUException.LOGIN_FAILURE.getName()));
//                        }
//
//                        return restfulApiService.getUserInfo(duLoginResult.getUserId());
//                    }
//                });
    }

    /**
     * get the list of apks
     *
     * @param userId
     * @param deviceId
     * @return
     */
    public Observable<DUEntitiesResult<DUApkInfoResult>> getApkList(int userId, String deviceId) {
        if (!connect()) {
            return Observable.error(new NullPointerException(DUException.HTTP_ERROR.getName()));
        }

        return restfulApiService.getApkList(userId, deviceId);
    }

    public Observable<DUEntityResult<DUCheckResult>> getAppCheck(String appId, int version) {
        if (!connect()) {
            return Observable.error(new NullPointerException(DUException.HTTP_ERROR.getName()));
        }

        return restfulApiService.getAppCheck(appId, version);
    }

    public Observable<DUEntitiesResult<DUConfigXmlFile.Component>> getConfigCheck(int userId) {
        if (!connect()) {
            return Observable.error(new NullPointerException(DUException.HTTP_ERROR.getName()));
        }

        return restfulApiService.getConfigCheck(userId);
    }

    public Observable<DUEntityResult<DUCheckResult>> getDataCheck(String appId, int version) {
        if (!connect()) {
            return Observable.error(new NullPointerException(DUException.HTTP_ERROR.getName()));
        }

        return restfulApiService.getDataCheck(appId, version);
    }

    public Observable<DUEntityResult<DUTrackResult>> sendTrack(DULocationTrack duLocationTrack) {
        if (!connect()) {
            return Observable.error(new NullPointerException(DUException.HTTP_ERROR.getName()));
        }

        return restfulApiService.sendTrack(duLocationTrack);
    }

    public Observable<DUEntitiesResult<DUWordsResult>> getWords(String group) {
        if (!connect()) {
            return Observable.error(new NullPointerException(DUException.HTTP_ERROR.getName()));
        }

        return restfulApiService.getWords(group);
    }

    private boolean connect() {
        if (isConnected) {
            return true;
        }

        SystemConfig systemConfig = mConfigHelper.getSystemConfig();
        String baseUrl;
        if (systemConfig.getBoolean(SystemConfig.PARAM_USING_RESERVED_ADDRESS, false)) {
            baseUrl = systemConfig.getString(SystemConfig.PARAM_RESERVED_SERVER_BASE_URI);
        } else {
            baseUrl = systemConfig.getString(SystemConfig.PARAM_SERVER_BASE_URI);
        }
        restfulApiService = RestfulApiService.Factory.newInstance(mBus, baseUrl);
        isConnected = (restfulApiService != null);
        return isConnected;
    }
}
