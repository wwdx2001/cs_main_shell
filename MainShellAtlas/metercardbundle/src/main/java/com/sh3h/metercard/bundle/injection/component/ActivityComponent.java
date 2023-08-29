package com.sh3h.metercard.bundle.injection.component;

import com.sh3h.metercard.bundle.injection.annotation.PerActivity;
import com.sh3h.metercard.bundle.injection.module.ActivityModule;
import com.sh3h.metercard.bundle.ui.demo.DemoActivity;
import com.sh3h.metercard.bundle.ui.information.ArrearsInfoFragment;
import com.sh3h.metercard.bundle.ui.information.BasicInformationFragment;
import com.sh3h.metercard.bundle.ui.information.ChangeTableFragment;
import com.sh3h.metercard.bundle.ui.information.CustomerInformationActivity;
import com.sh3h.metercard.bundle.ui.information.RecentMeterReadingFragment;
import com.sh3h.metercard.bundle.ui.information.RecentPayFragment;
import com.sh3h.metercard.bundle.ui.search.CombinedSearchActivity;
import com.sh3h.metercard.bundle.ui.search.SearchResultActivity;
import com.sh3h.metercard.bundle.ui.setting.SettingActivity;

import dagger.Component;

@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {
    void inject(DemoActivity firstActivity);

    void inject(CustomerInformationActivity activity);

    void inject(BasicInformationFragment fragment);

    void inject(RecentMeterReadingFragment fragment);

    void inject(RecentPayFragment fragment);

    void inject(ArrearsInfoFragment fragment);

    void inject(ChangeTableFragment fragment);

    void inject(SettingActivity settingActivity);

    void inject(SearchResultActivity activity);

    void inject(CombinedSearchActivity activity);
}
