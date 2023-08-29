package com.sh3h.mainshell_chaobiaoji.ui.welcome;


import android.content.Context;
import android.os.Build;

import com.sh3h.datautil.data.DataManager;
import com.sh3h.datautil.data.entity.DUDeviceInfo;
import com.sh3h.datautil.data.entity.DULoginInfo;
import com.sh3h.datautil.data.local.config.ConfigHelper;
import com.sh3h.datautil.data.local.preference.PreferencesHelper;
import com.sh3h.datautil.data.local.preference.UserSession;
import com.sh3h.datautil.util.NetworkUtil;
import com.sh3h.mainshell_chaobiaoji.AppChannel;
import com.sh3h.mainshell_chaobiaoji.MainApplication;
import com.sh3h.mainshell_chaobiaoji.R;
import com.sh3h.mainshell_chaobiaoji.ui.base.ParentPresenter;
import com.sh3h.mainshell_chaobiaoji.util.DeviceUtil;
import com.sh3h.mobileutil.util.LogUtil;
import com.sh3h.mobileutil.util.TextUtil;

import java.io.File;

import javax.inject.Inject;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static com.sh3h.mainshell_chaobiaoji.AppChannel.CHUANSHA_CHANNEL;
import static com.sh3h.mainshell_chaobiaoji.AppChannel.DAZHONG_CHANNEL;


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
        String appChannel = MainApplication.get(context).getAppChannel();
        if (TextUtil.isNullOrEmpty(appChannel)) {
            getMvpView().onError(WelcomeMvpView.Operation.REGISTER, "channel is error!!!");
            return;
        } else if (appChannel.equals(CHUANSHA_CHANNEL)) {
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

        if (appChannel.equals(DAZHONG_CHANNEL)) {
            String temp = macAddress;
            macAddress = deviceId;
            deviceId = temp;
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
                        LogUtil.e(TAG, "---authorize onError---:" + e.getMessage());
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

    public String getBaseUri() {
        return mConfigHelper.getBaseUri();
    }

    public String getBrokerUrl() {
        return mConfigHelper.getBrokerUrl();
    }

    public String getReservedBaseUri() {
        return mConfigHelper.getReservedBaseUri();
    }

    public String getReservedBrokerUrl() {
        return mConfigHelper.getReservedBrokerUrl();
    }

    public String getCountlyUri() {
        return mConfigHelper.getCountlyUri();
    }

    public boolean isUsingReservedAddress() {
        return mConfigHelper.isUsingReservedAddress();
    }

    public void saveNetWorkSetting(String baseUri, String reservedBaseUri,
                                   String brokeUrl, String reservedBrokeUrl,
                                   String countlyUri, boolean isUsingReservedAddress) {
        LogUtil.i(TAG, "---saveNetWork---");
        if (TextUtil.isNullOrEmpty(baseUri)
                || TextUtil.isNullOrEmpty(reservedBaseUri)
                || TextUtil.isNullOrEmpty(brokeUrl)
                || TextUtil.isNullOrEmpty(reservedBrokeUrl)
                || TextUtil.isNullOrEmpty(countlyUri)) {
            getMvpView().onError(WelcomeMvpView.Operation.SAVE, "param is invalid");
            return;
        }

        mSubscription.add(mConfigHelper.saveNetWorkSetting(baseUri, reservedBaseUri,
                brokeUrl, reservedBrokeUrl, countlyUri, isUsingReservedAddress)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<Boolean>() {
                    @Override
                    public void onCompleted() {
                        LogUtil.i(TAG, "---saveNetWork onCompleted---");
                        getMvpView().onSaveNetWorkSetting();
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.i(TAG, "---saveNetWork onError---" + e.getMessage());
                        getMvpView().onError(WelcomeMvpView.Operation.SAVE, e.getMessage());
                    }

                    @Override
                    public void onNext(Boolean aBoolean) {
                        LogUtil.i(TAG, "---saveNetWork onNext---");
                        if (aBoolean) {
                            getMvpView().showMessage(R.string.text_saving_success);
                        } else {
                            getMvpView().showMessage(R.string.text_saving_failure);
                        }
                    }
                }));
    }

    public void checkUser(boolean isNetworkConnected, String packageName, String deviceId, String appChannel) {
        UserSession userSession = mPreferencesHelper.getUserSession();
        if (userSession.isLoginFirst()) {
            getMvpView().jumpActivity(true);
            return;
        }

        if (!isNetworkConnected && isUserConfigExisting()) {
            if (TextUtil.isNullOrEmpty(appChannel)
                    || !appChannel.equalsIgnoreCase(AppChannel.GAS_GROUP_CHANNEL)) {
                getMvpView().jumpActivity(false);
                getMvpView().showMessage(R.string.not_network_login_success);
                return;
            }
        }

        mSubscription.add(mDataManager.login(new DULoginInfo(userSession.getAccount(),
                userSession.get_password(), packageName, deviceId))
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
