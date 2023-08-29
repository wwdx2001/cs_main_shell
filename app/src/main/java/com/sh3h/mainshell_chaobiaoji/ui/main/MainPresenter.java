package com.sh3h.mainshell_chaobiaoji.ui.main;


import android.content.Context;

import com.sh3h.datautil.data.DataManager;
import com.sh3h.datautil.data.entity.DUConfigXmlFile;
import com.sh3h.datautil.data.entity.DULogoutInfo;
import com.sh3h.datautil.data.entity.DUUpdateXmlFile;
import com.sh3h.datautil.data.local.config.ConfigHelper;
import com.sh3h.datautil.data.local.config.SystemConfig;
import com.sh3h.datautil.data.local.preference.PreferencesHelper;
import com.sh3h.datautil.data.local.preference.UserSession;
import com.sh3h.datautil.data.local.xml.XmlHelper;
import com.sh3h.mainshell_chaobiaoji.ui.base.ParentPresenter;
import com.sh3h.mainshell_chaobiaoji.util.DeviceUtil;
import com.sh3h.mobileutil.util.LogUtil;
import com.sh3h.mobileutil.util.TextUtil;

import java.util.List;

import javax.inject.Inject;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by dengzhimin on 2016/5/20.
 */
public class MainPresenter extends ParentPresenter<MainMvView> {
    private static final String TAG = "MainPresenter";
    private final ConfigHelper mConfigHelper;
    private final PreferencesHelper mPreferencesHelper;
    private final XmlHelper mXmlHelper;
    private final UserSession mUserSession;

    @Inject
    public MainPresenter(DataManager dataManager,
                         ConfigHelper configHelper,
                         PreferencesHelper preferencesHelper,
                         XmlHelper xmlHelper) {
        super(dataManager);
        mConfigHelper = configHelper;
        mPreferencesHelper = preferencesHelper;
        mXmlHelper = xmlHelper;
        mUserSession = preferencesHelper.getUserSession();
    }

    public void initSubTitle() {
        //设置MainActivity界面SubTitle
        getMvpView().setSubTitle(mPreferencesHelper.getUserSession().getUserName());
    }

    public boolean isGridStyle() {
        return mConfigHelper.isGridStyle();
    }

    public String getApksFolderPath() {
        return mConfigHelper.getApksFolderPath().getPath();
    }

    public boolean isAutoUpdate() {
        SystemConfig systemConfig = mConfigHelper.getSystemConfig();
        return systemConfig.getBoolean(SystemConfig.PARAM_SYS_UPDATING_VERSION_AUTO, false);
    }

    public String getAccount() {
        return mUserSession.getAccount();
    }

    public String getPassword() {
        return mUserSession.get_password();
    }

    public int getUserId() {
        return mUserSession.getUserId();
    }

    public String getUserName() {
        return mUserSession.getUserName();
    }

    public String getDepartment() {
        return mUserSession.getDepartmentName();
    }

    public int getDepartmentId() {
        return mUserSession.getDepartmentId();
    }

    public String getRoles() {
        return mUserSession.get_roles();
    }

    public String getAccessToken() {
        return mUserSession.getAccessToken();
    }

    public boolean isPhotoQualityNormal() {
        return mConfigHelper.isPhotoQualityNormal();
    }

    public boolean isUsingReservedAddress() {
        return mConfigHelper.isUsingReservedAddress();
    }

    public void downloadConfig(final boolean firstTime, final boolean isSwitchingUser) {
        LogUtil.i(TAG, "---downloadConfig---1");
        mSubscription.add(mDataManager.downloadConfigFile()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Boolean>() {
                    @Override
                    public void onCompleted() {
                        LogUtil.i(TAG, "---downloadConfig---onCompleted");
                        getMvpView().onDownloadConfig();
                        loadAndMatchXml(firstTime, isSwitchingUser);
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.i(TAG, "---downloadConfig---onError: " + e.getMessage());
                        getMvpView().onDownloadConfig();
                        loadAndMatchXml(firstTime, isSwitchingUser);
                    }

                    @Override
                    public void onNext(Boolean aBoolean) {
                        LogUtil.i(TAG, "---downloadConfig---onNext: " + aBoolean);
                    }
                })
        );
    }

    /**
     * load config.xml and update.xml, then match the contents of two xml files
     *
     * @param firstTime
     */
    private void loadAndMatchXml(final boolean firstTime, boolean isSwitchingUser) {
        LogUtil.i(TAG, "---loadAndMatchXml---");
        mSubscription.add(mXmlHelper.loadAndMatchXml(isSwitchingUser)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<Boolean>() {
                    @Override
                    public void onCompleted() {
                        LogUtil.i(TAG, "---loadAndMatchXml onCompleted---");
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.i(TAG, "---loadAndMatchXml onError---" + e.getMessage());
                        getMvpView().onError(e.getMessage());
                    }

                    @Override
                    public void onNext(Boolean aBoolean) {
                        LogUtil.i(TAG, "---loadAndMatchXml onNext---");
                        getMvpView().onLoadAndMatchXml(aBoolean, firstTime);
                    }
                }));
    }

    /**
     * load interface words and download to local
     */
    public void downloadWords(boolean firstTime) {
        LogUtil.i(TAG, "---downloadWords---");
        if (!firstTime) {
            LogUtil.i(TAG, "---downloadWords return---");
            return;
        }

        mSubscription.add(mDataManager.downloadWords("all")
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<Boolean>() {
                    @Override
                    public void onCompleted() {
                        LogUtil.i(TAG, "---downloadWords onCompleted---");
                        getMvpView().onDownloadWords();
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.i(TAG, "---downloadWords onError---" + e.getMessage());
                        getMvpView().onError(e.getMessage());
                    }

                    @Override
                    public void onNext(Boolean aBoolean) {
                        LogUtil.i(TAG, "---downloadWords onNext---");
                    }
                }));
    }

    public DUConfigXmlFile getDuConfigXmlFile() {
        return mXmlHelper.getDuConfigXmlFile();
    }

    public DUUpdateXmlFile getDuUpdateXmlFile() {
        return mXmlHelper.getDuUpdateXmlFile();
    }

    public void saveComponentList(List<DUConfigXmlFile.Component> componentList,
                                  final boolean needHint) {
        LogUtil.i(TAG, "saveComponentList");
        if ((componentList == null) || (componentList.size() <= 0)) {
            LogUtil.e(TAG, "saveComponentList return 1");
            return;
        }

        DUConfigXmlFile duConfigXmlFile = getDuConfigXmlFile();
        if ((duConfigXmlFile == null)
                || (duConfigXmlFile.getComponentList() == null)
                || (duConfigXmlFile.getComponentList().size() <= 0)) {
            LogUtil.e(TAG, "saveComponentList return 2");
            return;
        }

        List<DUConfigXmlFile.Component> allComponentList = duConfigXmlFile.getComponentList();
        for (DUConfigXmlFile.Component component1 : componentList) {
            for (DUConfigXmlFile.Component component2 : allComponentList) {
                if (!TextUtil.isNullOrEmpty(component1.getPackageName())
                        && !TextUtil.isNullOrEmpty(component2.getPackageName())
                        && !TextUtil.isNullOrEmpty(component1.getActivity())
                        && !TextUtil.isNullOrEmpty(component2.getActivity())
                        && component1.getPackageName().equals(component2.getPackageName())
                        && component1.getActivity().equals(component2.getActivity())) {
                    component2.setCount(component1.getCount());
                    component2.setOrder(component1.getOrder());
                    break;
                }
            }
        }

        mXmlHelper.saveComponentList()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<Boolean>() {
                    @Override
                    public void onCompleted() {
                        LogUtil.i(TAG, "---saveComponentList onCompleted---");
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.i(TAG, "---saveComponentList onError---" + e.getMessage());
                        getMvpView().onError(e.getMessage());
                    }

                    @Override
                    public void onNext(Boolean aBoolean) {
                        LogUtil.i(TAG, "---saveComponentList onNext---" + aBoolean);
                        if (needHint) {
                            getMvpView().onSaveComponentList(aBoolean);
                        }
                    }
                });
    }

    public void logout(Context context) {
        DULogoutInfo duLogoutInfo = new DULogoutInfo(
                mPreferencesHelper.getUserSession().getUserId(),
                DeviceUtil.getDeviceID(context));
        mSubscription.add(mDataManager.logout(duLogoutInfo)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<Boolean>() {
                    @Override
                    public void onCompleted() {
                        LogUtil.i(TAG, "logout onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.e(TAG, "logout onError" + e.getMessage());
                        getMvpView().onLogout(false);
                    }

                    @Override
                    public void onNext(Boolean aBoolean) {
                        LogUtil.i(TAG, "logout onNext");
                        getMvpView().onLogout(aBoolean);
                    }
                }));
    }
}
