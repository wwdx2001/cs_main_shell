package com.sh3h.mainshell_chaobiaoji.ui.welcome;


import com.sh3h.mainshell_chaobiaoji.ui.base.MvpView;

public interface WelcomeMvpView extends MvpView {
    public enum Operation {
        INIT,
        REGISTER,
        SAVE
    }

    void showProgress(int length);

    void onError(Operation operation, String message);

    void onFinished(Operation operation);

    void onSaveNetWorkSetting();

    void showMessage(String message);

    void showMessage(int message);

    void jumpActivity(boolean loginOrMain);
}
