package com.sh3h.mainshell_chaobiaoji.ui.setting;

import com.sh3h.datautil.data.DataManager;
import com.sh3h.datautil.data.entity.DUConfigXmlFile;
import com.sh3h.datautil.data.entity.DUUpdateXmlFile;
import com.sh3h.datautil.data.local.config.ConfigHelper;
import com.sh3h.datautil.data.local.config.SystemConfig;
import com.sh3h.datautil.data.local.preference.PreferencesHelper;
import com.sh3h.datautil.data.local.preference.UserSession;
import com.sh3h.datautil.data.local.xml.XmlHelper;
import com.sh3h.mainshell_chaobiaoji.MainApplication;
import com.sh3h.mainshell_chaobiaoji.R;
import com.sh3h.mainshell_chaobiaoji.ui.base.ParentPresenter;
import com.sh3h.mobileutil.util.LogUtil;
import com.sh3h.mobileutil.util.TextUtil;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by xulongjun on 2016/5/23.
 */
public class SettingPresenter extends ParentPresenter<SettingMvpView> {

    private static final String TAG = "SettingPresenter";
    private final ConfigHelper mConfigHelper;
    private final PreferencesHelper mPreferencesHelper;
    private final XmlHelper mXmlHelper;

    @Inject
    public SettingPresenter(DataManager dataManager,
                            ConfigHelper configHelper,
                            PreferencesHelper preferencesHelper,
                            XmlHelper xmlHelper) {
        super(dataManager);
        mConfigHelper = configHelper;
        mPreferencesHelper = preferencesHelper;
        mXmlHelper = xmlHelper;
    }

    public void init() {
        LogUtil.i(TAG, "---init---");
        getMvpView().initStyle(mConfigHelper.isGridStyle());
        getMvpView().initImageQuality(mConfigHelper.isPhotoQualityNormal());
        getMvpView().initUpdateVersion(mConfigHelper.isUpdateVersion());
        getMvpView().initKeepAliveInterval(mConfigHelper.getKeepAliveInterval());
    }

    public String getAccount() {
        UserSession userSession = mPreferencesHelper.getUserSession();
        return userSession.getAccount();
    }

    public String getPassword() {
        UserSession userSession = mPreferencesHelper.getUserSession();
        return userSession.get_password();
    }

    public int getUserId() {
        UserSession userSession = mPreferencesHelper.getUserSession();
        return userSession.getUserId();
    }

    public String getUserName() {
        UserSession userSession = mPreferencesHelper.getUserSession();
        return userSession.getUserName();
    }

    public String getDepartment() {
        UserSession userSession = mPreferencesHelper.getUserSession();
        return userSession.getDepartmentName();
    }

    public int getDepartmentId() {
        UserSession userSession = mPreferencesHelper.getUserSession();
        return userSession.getDepartmentId();
    }

    public String getRoles() {
        UserSession userSession = mPreferencesHelper.getUserSession();
        return userSession.get_roles();
    }

    public String getAccessToken() {
        UserSession userSession = mPreferencesHelper.getUserSession();
        return userSession.getAccessToken();
    }

    public boolean isPhotoQualityNormal() {
        return mConfigHelper.isPhotoQualityNormal();
    }

    public void saveKeepAliveInterval(int keepAliveInterval) {
        LogUtil.i(TAG, String.format("---saveKeepAliveInterval--- keepAliveInterval"));

        if (mConfigHelper.getKeepAliveInterval() == keepAliveInterval) {
            LogUtil.i(TAG, String.format("---saveKeepAliveInterval---repeat"));
            return;
        }

        mSubscription.add(mConfigHelper.saveKeepAliveInterval(keepAliveInterval)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<Boolean>() {
                    @Override
                    public void onCompleted() {
                        LogUtil.i(TAG, "---saveKeepAliveInterval onCompleted---");
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.i(TAG, "---saveKeepAliveInterval onError---" + e.getMessage());
                        getMvpView().showMessage(e.getMessage());
                    }

                    @Override
                    public void onNext(Boolean aBoolean) {
                        LogUtil.i(TAG, "---saveKeepAliveInterval onNext---");
                        if (aBoolean) {
                            getMvpView().showMessage(R.string.text_saving_success);
                        } else {
                            getMvpView().showMessage(R.string.text_saving_failure);
                        }
                    }
                }));
    }

    public void saveStyle(boolean isGrid) {
        LogUtil.i(TAG, String.format("---saveStyle---isGrid: %s", isGrid ? "true" : "false"));
        if (mConfigHelper.isGridStyle() == isGrid) {
            LogUtil.i(TAG, String.format("---saveStyle---repeat"));
            return;
        }

        mSubscription.add(mConfigHelper.saveGridStyle(isGrid)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<Boolean>() {
                    @Override
                    public void onCompleted() {
                        LogUtil.i(TAG, "---saveStyle onCompleted---");
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.i(TAG, "---saveStyle onError---" + e.getMessage());
                        getMvpView().showMessage(e.getMessage());
                    }

                    @Override
                    public void onNext(Boolean aBoolean) {
                        LogUtil.i(TAG, "---saveStyle onNext---");
                        if (aBoolean) {
                            getMvpView().showMessage(R.string.text_saving_success);
                            getMvpView().onSaveStyle();
                        } else {
                            getMvpView().showMessage(R.string.text_saving_failure);
                        }
                    }
                }));
    }

    public void saveNetWorkSetting(String baseUri, String reservedBaseUri,
                                   String brokeUrl, String reservedBrokeUrl,
                                   String countlyUri, final boolean isUsingReservedAddress) {
        LogUtil.i(TAG, "---saveNetWork---");
        if (TextUtil.isNullOrEmpty(baseUri)
                || TextUtil.isNullOrEmpty(reservedBaseUri)
                || TextUtil.isNullOrEmpty(brokeUrl)
                || TextUtil.isNullOrEmpty(reservedBrokeUrl)
                || TextUtil.isNullOrEmpty(countlyUri)) {
            getMvpView().showMessage("param is invalid");
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
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.i(TAG, "---saveNetWork onError---" + e.getMessage());
                        getMvpView().showMessage(e.getMessage());
                    }

                    @Override
                    public void onNext(Boolean aBoolean) {
                        LogUtil.i(TAG, "---saveNetWork onNext---");
                        if (aBoolean) {
                            getMvpView().showMessage(R.string.text_saving_success);
                            getMvpView().onSaveNetWorkSetting(!isUsingReservedAddress);
                        } else {
                            getMvpView().showMessage(R.string.text_saving_failure);
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

    public void saveCountlyUri(String countlyUri) {
        if (mConfigHelper.getCountlyUri().equals(countlyUri)) {
            LogUtil.i(TAG, String.format("---saveCountlyUri---repeat"));
            return;
        }
        mSubscription.add(mConfigHelper.saveCountlyUri(countlyUri)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<Boolean>() {
                    @Override
                    public void onCompleted() {
                        LogUtil.i(TAG, "---saveCountlyUri onCompleted---");
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.i(TAG, "---saveCountlyUri onError---" + e.getMessage());
                        getMvpView().showMessage(e.getMessage());
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
                })
        );
    }

    //存储Gps定位方式
    public void saveGpsType(String typeListValue) {
        if (mConfigHelper.getGpsType().equals(typeListValue)) {
            LogUtil.i(TAG, String.format("---saveGpsType---repeat"));
            return;
        }

        mSubscription.add(mConfigHelper.saveGpsType(typeListValue)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<Boolean>() {
                    @Override
                    public void onCompleted() {
                        LogUtil.i(TAG, "---saveGpsType onCompleted---");
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.i(TAG, "---saveGpsType onError---" + e.getMessage());
                        getMvpView().showMessage(e.getMessage());
                    }

                    @Override
                    public void onNext(Boolean aBoolean) {
                        LogUtil.i(TAG, "---saveGpsType onNext---");
                        if (aBoolean) {
                            getMvpView().showMessage(R.string.text_saving_success);
                            getMvpView().showMessage(R.string.text_quit_and_login);
                        } else {
                            getMvpView().showMessage(R.string.text_saving_failure);
                        }
                    }
                }));
    }

    //获取gps定位方式
    public String getGpsType() {
        return mConfigHelper.getGpsType();
    }

    /**
     * 获取config.xml树文件
     *
     * @return
     */
    public DUConfigXmlFile getDuConfigXmlFile() {
        return mXmlHelper.getDuConfigXmlFile();
    }

    public DUUpdateXmlFile getDuUpdateXmlFile() {
        return mXmlHelper.getDuUpdateXmlFile();
    }

    public String getMonitorType() {
        return mConfigHelper.getMonitorType().getName();
    }

    /**
     * 保存监控方式
     *
     * @param monitorTypeValue
     */
    public void saveMonitorType(final String monitorTypeValue) {
        if (getMonitorType().equals(monitorTypeValue)) {
            LogUtil.i(TAG, String.format("---saveGpsType---repeat"));
            return;
        }

        mSubscription.add(mConfigHelper.saveMonitorType(monitorTypeValue)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<Boolean>() {
                    @Override
                    public void onCompleted() {
                        LogUtil.i(TAG, "---saveMonitorType onCompleted---");
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.i(TAG, "---saveMonitorType onError---" + e.getMessage());
                        getMvpView().showMessage(e.getMessage());
                    }

                    @Override
                    public void onNext(Boolean aBoolean) {
                        LogUtil.i(TAG, "---saveMonitorType onNext---");
                        if (aBoolean) {
                            getMvpView().showMessage(R.string.text_saving_success);
                            getMvpView().showMessage(R.string.text_quit_and_login);
                            SystemConfig.MonitorType monitorType =
                                    SystemConfig.MonitorType.toMonitorType(monitorTypeValue);
                            if (monitorType == SystemConfig.MonitorType.NONE) {
                                getMvpView().destroyMonitor();
                            }
                        } else {
                            getMvpView().showMessage(R.string.text_saving_failure);
                        }
                    }
                }));
    }

    public void saveQuality(final boolean isNormal) {
        LogUtil.i(TAG, String.format("---saveQuality---isNormal: %s", isNormal ? "true" : "false"));
        if (mConfigHelper.isPhotoQualityNormal() == isNormal) {
            LogUtil.i(TAG, String.format("---saveQuality---repeat"));
            return;
        }

        mSubscription.add(mConfigHelper.savePhotoQuality(isNormal)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<Boolean>() {
                    @Override
                    public void onCompleted() {
                        LogUtil.i(TAG, "---saveQuality onCompleted---");
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.i(TAG, "---saveQuality onError---" + e.getMessage());
                        getMvpView().showMessage(e.getMessage());
                    }

                    @Override
                    public void onNext(Boolean aBoolean) {
                        LogUtil.i(TAG, "---saveQuality onNext---");
                        if (aBoolean) {
                            getMvpView().showMessage(R.string.text_saving_success);
                            getMvpView().onSaveQuality(isNormal);
                        } else {
                            getMvpView().showMessage(R.string.text_saving_failure);
                        }
                    }
                }));
    }

    public void saveUpdateVersion(boolean flag) {
        LogUtil.i(TAG, String.format("---saveUpdateVersion---flag: %s", flag ? "true" : "false"));
        if (mConfigHelper.isUpdateVersion() == flag) {
            LogUtil.i(TAG, String.format("---saveUpdateVersion---repeat"));
            return;
        }

        mSubscription.add(mConfigHelper.saveUpdateVersion(flag)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<Boolean>() {
                    @Override
                    public void onCompleted() {
                        LogUtil.i(TAG, "---saveUpdateVersion onCompleted---");
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.i(TAG, "---saveUpdateVersion onError---" + e.getMessage());
                        getMvpView().showMessage(e.getMessage());
                    }

                    @Override
                    public void onNext(Boolean aBoolean) {
                        LogUtil.i(TAG, "---saveUpdateVersion onNext---");
                        if (aBoolean) {
                            getMvpView().showMessage(R.string.text_saving_success);
                        } else {
                            getMvpView().showMessage(R.string.text_saving_failure);
                        }
                    }
                }));
    }

    /**
     *
     */
    public void clearHistory() {
        mSubscription.add(mConfigHelper.clearHistory()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<Boolean>() {
                    @Override
                    public void onCompleted() {
                        LogUtil.i(TAG, "---clearHistory onCompleted---");
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.i(TAG, "---clearHistory onError---" + e.getMessage());
                        getMvpView().showMessage(e.getMessage());
                    }

                    @Override
                    public void onNext(Boolean aBoolean) {
                        LogUtil.i(TAG, "---clearHistory onNext---");
                        if (aBoolean) {
                            getMvpView().exitSystem("clearHistory");
                        } else {
                            getMvpView().showMessage(R.string.text_clearing_history_failure);
                        }
                    }
                }));
    }

    public void wipeData(final MainApplication mainApplication, final boolean isClearingCache) {
        mSubscription.add(Observable.create(new Observable.OnSubscribe<Boolean>() {
            @Override
            public void call(Subscriber<? super Boolean> subscriber) {
                mainApplication.clearCacheTrack();
                subscriber.onNext(mainApplication.killChildApps());
                subscriber.onCompleted();
            }
        }).concatMap(new Func1<Boolean, Observable<Boolean>>() {
            @Override
            public Observable<Boolean> call(Boolean aBoolean) {
                return mConfigHelper.wipeData(isClearingCache);
            }
        }).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<Boolean>() {
                    @Override
                    public void onCompleted() {
                        LogUtil.i(TAG, "---clearHistory onCompleted---");
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.i(TAG, "---clearHistory onError---" + e.getMessage());
                        getMvpView().showMessage(e.getMessage());
                    }

                    @Override
                    public void onNext(Boolean aBoolean) {
                        LogUtil.i(TAG, "---clearHistory onNext---");
                        if (aBoolean) {
                            getMvpView().exitSystem(isClearingCache ? "clearHistory" : "restoreFactory");
                        } else {
                            getMvpView().showMessage(isClearingCache ? R.string.text_clearing_history_failure : R.string.text_restoring_failure);
                        }
                    }
                }));
    }

    /**
     *
     */
    public void restoreFactory() {
        mSubscription.add(mConfigHelper.restoreFactory()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<Boolean>() {
                    @Override
                    public void onCompleted() {
                        LogUtil.i(TAG, "---restoreFactory onCompleted---");
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.i(TAG, "---restoreFactory onError---" + e.getMessage());
                        getMvpView().showMessage(e.getMessage());
                    }

                    @Override
                    public void onNext(Boolean aBoolean) {
                        LogUtil.i(TAG, "---restoreFactory onNext---");
                        if (aBoolean) {
                            getMvpView().exitSystem("restoreFactory");
                        } else {
                            getMvpView().showMessage(R.string.text_restoring_failure);
                        }
                    }
                }));
    }

    public void switchUser() {
        mSubscription.add(mConfigHelper.clearAccount()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<Boolean>() {
                    @Override
                    public void onCompleted() {
                        LogUtil.i(TAG, "---switchUser onCompleted---");
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.i(TAG, "---switchUser onError---" + e.getMessage());
                        getMvpView().showMessage(e.getMessage());
                    }

                    @Override
                    public void onNext(Boolean aBoolean) {
                        LogUtil.i(TAG, "---switchUser onNext---");
                        if (aBoolean) {
                            getMvpView().exitSystem(null);
                        } else {
                            getMvpView().showMessage(R.string.text_restoring_failure);
                        }
                    }
                }));
    }
}
