package com.sh3h.mainshell_chaobiaoji.ui.login;

import com.sh3h.mainshell_chaobiaoji.ui.base.MvpView;

/**
 * Created by dengzhimin on 2016/5/18.
 */
public interface LoginMvpView extends MvpView {

    enum ErrorCode{
        FIRST_TIME_MUST_NETWORK_LOGIN,//首次必须在线登录
        NOT_NETWORK,//无网
        NETWORK_LOGIN_FAILED,//在线登录失败
        NOT_NETWORK_LOGIN_SUCCESS,//离线登录成功
        NETWORK_LOGIN_SUCCESS,//在线登录成功
        NOT_NETWORK_ACCOUNT_OR_PASSWORD_ERROR,//离线登录失败，用户名或密码错误
        SAVE_NETWORK_FAILED,//保存网络设置失败
        SAVE_NETWORK_SUCCESS;//保存网络设置成功
    }

    void loginSuccess(ErrorCode errorCode);
    void loginFailed(ErrorCode errorCode);
    void loginFailed(String error);

    /**
     * 初始化账号和密码EditText数据
     * @param account
     * @param password
     */
    void initEditTextData(String account, String password);

    void saveNetWorkResult(ErrorCode errorCode, String msg);

    void onError(String message);

    void isFirstLogin(boolean flag);

}
