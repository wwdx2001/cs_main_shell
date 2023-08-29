package com.sh3h.mainshell_chaobiaoji.ui.main;

import com.sh3h.mainshell_chaobiaoji.ui.base.MvpView;

/**
 * Created by dengzhimin on 2016/5/20.
 */
public interface MainMvView extends MvpView {

    enum ServiceFlag {
        KEEPALIVE_SERVICE,
    }

    void startServices(ServiceFlag flag);

    void setSubTitle(String title);

    void onLoadAndMatchXml(boolean result, boolean firstTime);

    void onDownloadWords();

    void onError(String message);

    void onSaveComponentList(Boolean aBoolean);

    void onDownloadConfig();

    void onLogout(Boolean aBoolean);
}
