package com.sh3h.mainshell.ui.welcome;


import android.content.Context;
import android.os.Build;

import com.mainshell.datautil.data.DataManager;
import com.mainshell.datautil.data.entity.DUDeviceInfo;
import com.mainshell.datautil.data.entity.DULoginInfo;
import com.mainshell.datautil.data.entity.base.DUEntityResult;
import com.mainshell.datautil.data.entity.result.DUDeviceResult;
import com.mainshell.datautil.data.local.config.ConfigHelper;
import com.mainshell.datautil.data.local.preference.PreferencesHelper;
import com.mainshell.datautil.data.local.preference.UserSession;
import com.mainshell.datautil.util.NetworkUtil;
import com.sh3h.mainshell.R;
import com.sh3h.mainshell.ui.base.ParentPresenter;
import com.sh3h.mainshell.util.DeviceUtil;
import com.sh3h.mobileutil.util.LogUtil;
import com.sh3h.mobileutil.util.TextUtil;

import java.io.File;

import javax.inject.Inject;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static com.mainshell.datautil.data.local.config.SystemConfig.REGION_CHUANSHA;


public class WelcomePresenter extends ParentPresenter<WelcomeMvpView> {
    private static final String TAG = "WelcomePresenter";
    private final ConfigHelper mConfigHelper;
    private final PreferencesHelper mPreferencesHelper;

    @Inject
    public WelcomePresenter(DataManager dataManager,
                            ConfigHelper configHelper,
                            PreferencesHelper preferencesHelper) {
        super(dataManager);
        mConfigHelper = configHelper;
        mPreferencesHelper = preferencesHelper;
    }

    /**
     * initialize
     */
    public void init() {
        mSubscription.add(mDataManager.init()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<Void>() {
                    @Override
                    public void onCompleted() {
                        LogUtil.i(TAG, "---init onCompleted---");
                        getMvpView().onFinished(WelcomeMvpView.Operation.INIT);
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.i(TAG, "---init onError---");
                        getMvpView().onError(WelcomeMvpView.Operation.INIT, e.getMessage());
                    }

                    @Override
                    public void onNext(Void aVoid) {
                        LogUtil.i(TAG, "---init onNext---");
                        getMvpView().showProgress(50);
                    }
                }));
    }

    /**
     * register
     *
     * @param context application
     */
    public void register(final Context context) {
        String key = mConfigHelper.getKey();
        if (TextUtil.isNullOrEmpty(key)) {
            if (!NetworkUtil.isNetworkConnected(context)) {
                getMvpView().onError(WelcomeMvpView.Operation.REGISTER,
                        context.getString(R.string.text_not_authorizing));
                return;
            }
        } else {
            getMvpView().onFinished(WelcomeMvpView.Operation.REGISTER);
            return;
        }

        String deviceId = DeviceUtil.getDeviceID(context);
        String macAddress;
        String region = mConfigHelper.getRegion();
        if (!TextUtil.isNullOrEmpty(region) && region.equals(REGION_CHUANSHA)) {
            macAddress = "c4:43:8f:f4:82:75";
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                macAddress = DeviceUtil.getMACAddress("wlan0");
                if (TextUtil.isNullOrEmpty(macAddress)) {
                    macAddress = DeviceUtil.getLocalMacAddress(context);
                }
            } else {
                macAddress = DeviceUtil.getLocalMacAddress(context);
            }
        }

        if (TextUtil.isNullOrEmpty(deviceId) || TextUtil.isNullOrEmpty(macAddress)) {
            getMvpView().onError(WelcomeMvpView.Operation.REGISTER,
                    "deviceId or macAddress is error!!!");
            return;
        }

        DUDeviceInfo duDeviceInfo = new DUDeviceInfo(macAddress, deviceId);
        mSubscription.add(mDataManager.register(duDeviceInfo)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<Boolean>() {
                    @Override
                    public void onCompleted() {
                        LogUtil.i(TAG, "---authorize onCompleted---");
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.i(TAG, "---authorize onError---");
                        getMvpView().onError(WelcomeMvpView.Operation.REGISTER, e.getMessage());
                    }

                    @Override
                    public void onNext(Boolean aBoolean) {
                        LogUtil.i(TAG, "---authorize onNext---");
                        getMvpView().showProgress(100);

                        if (aBoolean) {
                            getMvpView().onFinished(WelcomeMvpView.Operation.REGISTER);
                        } else {
                            getMvpView().onError(WelcomeMvpView.Operation.REGISTER,
                                    context.getString(R.string.text_failure_to_authorize));
                        }
                    }
                }));
    }

    public void checkUser(boolean isNetworkConnected) {
        UserSession userSession = mPreferencesHelper.getUserSession();
        if (userSession.isLoginFirst()) {
            getMvpView().jumpActivity(true);
            return;
        }

        if (!isNetworkConnected && isUserConfigExisting()) {
            getMvpView().jumpActivity(false);
            getMvpView().showMessage(R.string.not_network_login_success);
            return;
        }

        mSubscription.add(mDataManager.login(new DULoginInfo(userSession.getAccount(),
                userSession.get_password(), "plt"))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Boolean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.i(TAG, "---checkUser onError---" + e.getMessage());
                        getMvpView().jumpActivity(true);
                    }

                    @Override
                    public void onNext(Boolean aBoolean) {
                        if (aBoolean) {
                            getMvpView().showMessage(R.string.network_login_success);
                        } else {
                            getMvpView().showMessage(R.string.network_login_failed);
                        }

                        if (!isUserConfigExisting()) {
                            getMvpView().jumpActivity(true);
                        } else {
                            getMvpView().jumpActivity(!aBoolean);
                        }
                    }
                })
        );
    }

    private boolean isUserConfigExisting() {
        File file = mConfigHelper.getUserConfigFilePath();
        return file.exists();
    }
}
