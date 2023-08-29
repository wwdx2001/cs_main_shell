package com.sh3h.metercard.bundle.ui.setting;

import android.content.Context;

import com.sh3h.dataprovider.data.DataManager;
import com.sh3h.dataprovider.data.local.config.ConfigHelper;
import com.sh3h.metercard.bundle.ui.base.ParentPresenter;
import com.sh3h.metercard.bundle.util.SystemUtil;

import javax.inject.Inject;


public class SettingPresenter extends ParentPresenter<SettingMvpView> {

    private final ConfigHelper mConfigHelper;

    @Inject
    public SettingPresenter(DataManager dataManager, ConfigHelper configHelper) {
        super(dataManager);
        this.mConfigHelper=configHelper;
    }

    public String getReadingDate(boolean isStart){
        return mConfigHelper.getReadingDate(isStart);
    }


    public void getVersionInfo(Context context) {
        getMvpView().onGetVersionInfo(SystemUtil.getVersionName(context));
    }
}
