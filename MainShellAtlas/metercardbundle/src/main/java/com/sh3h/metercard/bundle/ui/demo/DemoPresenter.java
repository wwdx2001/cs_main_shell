package com.sh3h.metercard.bundle.ui.demo;


import com.sh3h.dataprovider.data.DataManager;
import com.sh3h.metercard.bundle.ui.base.BasePresenter;
import com.sh3h.metercard.bundle.ui.base.ParentPresenter;

import javax.inject.Inject;

public class DemoPresenter extends ParentPresenter<DemoMvpView> {
    @Inject
    public DemoPresenter(DataManager dataManager) {
        super(dataManager);
    }
}
