package com.sh3h.mainshell_chaobiaoji.injection.component;

import android.content.Context;

import com.sh3h.datautil.data.DataManager;
import com.sh3h.datautil.data.local.config.ConfigHelper;
import com.sh3h.datautil.data.local.preference.PreferencesHelper;
import com.sh3h.datautil.data.local.xml.XmlHelper;
import com.sh3h.datautil.injection.annotation.ApplicationContext;
import com.sh3h.datautil.util.EventPosterHelper;
import com.sh3h.mainshell_chaobiaoji.MainApplication;
import com.sh3h.mainshell_chaobiaoji.broadcast.KeepAliveBroadcast;
import com.sh3h.mainshell_chaobiaoji.injection.module.ApplicationModule;
import com.sh3h.mainshell_chaobiaoji.location.GpsLocation;
import com.sh3h.mainshell_chaobiaoji.service.FileService;
import com.sh3h.mainshell_chaobiaoji.service.KeepAliveService;
import com.sh3h.mainshell_chaobiaoji.util.PermissionsChecker;
import com.squareup.otto.Bus;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {
    void inject(MainApplication mainApplication);
    void inject(KeepAliveService keepAliveService);
    void inject(KeepAliveBroadcast keepAliveBroadcast);
    void inject(FileService apkService);

    @ApplicationContext Context context();
    DataManager dataManager();
    PreferencesHelper preferencesHelper();
    ConfigHelper configHelper();
    Bus eventBus();
    GpsLocation gpsLocation();
    XmlHelper xmlHelper();
    EventPosterHelper eventPosterHelper();
    PermissionsChecker permissionsChecher();
}