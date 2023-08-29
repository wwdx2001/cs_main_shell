package com.sh3h.mainshell.ui.welcome;


import com.sh3h.mainshell.ui.base.MvpView;

public interface WelcomeMvpView extends MvpView {
    enum Operation {
        INIT,
        REGISTER,
        SAVE
    }

    void showProgress(int length);
    void onError(Operation operation, String message);
    void onFinished(Operation operation);
    void showMessage(String message);
    void showMessage(int message);
    void jumpActivity(boolean loginOrMain);
}
