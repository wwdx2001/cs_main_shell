package com.sh3h.metercard.bundle.injection.component;

import android.content.Context;


import com.sh3h.dataprovider.data.DataManager;
import com.sh3h.dataprovider.data.local.config.ConfigHelper;
import com.sh3h.dataprovider.data.local.preference.PreferencesHelper;
import com.sh3h.dataprovider.util.EventPosterHelper;
import com.sh3h.metercard.bundle.MCBApplication;
import com.sh3h.metercard.bundle.injection.annotation.ApplicationContext;
import com.sh3h.metercard.bundle.injection.module.ApplicationModule;
import com.squareup.otto.Bus;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {
    void inject(MCBApplication mcbApplication);

    @ApplicationContext
    Context context();
    DataManager dataManager();
    PreferencesHelper preferencesHelper();
    ConfigHelper configHelper();
    Bus eventBus();
    EventPosterHelper eventPosterHelper();
}
