package com.sh3h.mainshell.ui.setting;

import com.sh3h.mainshell.ui.base.MvpView;

import java.util.List;

/**
 * Created by xulongjun on 2016/5/23.
 */
public interface SettingMvpView extends MvpView {
    void initStyle(boolean isGrid);
    void initImageQuality(boolean isQuality);
    void initUpdateVersion(boolean isUpdateVersion);
    void onError(String message);
    void initKeepAliveInterval(int keepAliveInterval);
    void showMessage(String message);
    void showMessage(int message);
    void destroyMonitor();
    void exitSystem();
    void onSaveStyle();
}
