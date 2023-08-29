package com.sh3h.mainshell.ui.login;

import android.text.TextUtils;

import com.mainshell.datautil.data.DataManager;
import com.mainshell.datautil.data.entity.DULoginInfo;
import com.mainshell.datautil.data.local.config.ConfigHelper;
import com.mainshell.datautil.data.local.preference.PreferencesHelper;
import com.mainshell.datautil.data.local.preference.UserSession;
import com.mainshell.datautil.exception.DUException;
import com.sh3h.mainshell.R;
import com.sh3h.mainshell.ui.base.ParentPresenter;
import com.sh3h.mobileutil.util.LogUtil;
import com.sh3h.mobileutil.util.TextUtil;

import javax.inject.Inject;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by dengzhimin on 2016/5/18.
 */
public class LoginPresenter extends ParentPresenter<LoginMvpView> {
    private final static String TAG = "LoginPresenter";
    private PreferencesHelper preferencesHelper;
    private UserSession userSession;
    private ConfigHelper configHelper;

    @Inject
    public LoginPresenter(DataManager dataManager,
                          PreferencesHelper preferencesHelper,
                          ConfigHelper configHelper) {
        super(dataManager);
        this.preferencesHelper = preferencesHelper;
        this.configHelper = configHelper;
    }

    /**
     * 初始化登录数据
     */
    public void initLoginData() {
        userSession = preferencesHelper.getUserSession();
        getMvpView().initEditTextData(userSession.getAccount(), userSession.get_password());
    }

    /**
     * 用户登录
     *
     * @param account            账号
     * @param password           密码
     * @param isNetworkConnected 是否有网络连接
     */
    public void login(String account, String password, String appIdentify, boolean isNetworkConnected) {
        if (!isNetworkConnected) {
            loginNoNetWork(account, password);
            return;
        }

        mSubscription.add(mDataManager.login(new DULoginInfo(account, password, appIdentify))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<Boolean>() {
                    @Override
                    public void onCompleted() {
                        LogUtil.i(TAG, "----onCompleted----");
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.i(TAG, "----onError----" + e.getMessage());
                        DUException duException = DUException.toDUException(e.getMessage());
                        if (duException == null) {
                            getMvpView().onError(e.getMessage());
                        } else {
                            getMvpView().loginFailed(LoginMvpView.ErrorCode.NETWORK_LOGIN_FAILED);
                        }
                    }

                    @Override
                    public void onNext(Boolean aBoolean) {
                        LogUtil.i(TAG, "----onNext----");
                        if (aBoolean) {
                            saveUserConfig();
                        } else {
                            getMvpView().loginFailed(LoginMvpView.ErrorCode.NETWORK_LOGIN_FAILED);
                        }
                    }
                }));
    }

    /**
     * 无网登录
     *
     * @param account  账号
     * @param password 密码
     */
    private void loginNoNetWork(String account, String password) {
        if (TextUtils.isEmpty(userSession.getAccount()) || TextUtils.isEmpty(userSession.get_password())) {
            getMvpView().loginFailed(LoginMvpView.ErrorCode.FIRST_TIME_MUST_NETWORK_LOGIN);
        } else {
            if (account.equals(userSession.getAccount()) && password.equals(userSession.get_password())) {
                getMvpView().loginSuccess(LoginMvpView.ErrorCode.NOT_NETWORK_LOGIN_SUCCESS);
            } else {
                getMvpView().loginFailed(LoginMvpView.ErrorCode.NOT_NETWORK_ACCOUNT_OR_PASSWORD_ERROR);
            }
        }
    }

    private void saveUserConfig() {
        UserSession userSession = preferencesHelper.getUserSession();
        mSubscription.add(configHelper.initUserConfig(userSession.getUserId())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<Void>() {
                    @Override
                    public void onCompleted() {
                        LogUtil.i(TAG, "----onCompleted----");
                        getMvpView().loginSuccess(LoginMvpView.ErrorCode.NETWORK_LOGIN_SUCCESS);
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.i(TAG, "----onError----" + e.getMessage());
                        getMvpView().loginFailed(e.getMessage());
                    }

                    @Override
                    public void onNext(Void aVoid) {
                        LogUtil.i(TAG, "----onNext----");
                    }
                }));
    }

    //判断是否是首次登录
    public void isFirstLogin() {
//        String userName = userSession.getUserName();
//        String pwd = userSession.get_password();
//        if (userName==null|| pwd==null||("".equals(userName))||("".equals(pwd))) {
//            //首次登录
//            getMvpView().isFirstLogin(true);
//        } else {
//            //不是首次登录
//            getMvpView().isFirstLogin(false);
//        }
        getMvpView().isFirstLogin(userSession.isLoginFirst());
    }
}
