package com.sh3h.mainshell.injection.component;

import android.content.Context;

import com.mainshell.datautil.data.DataManager;
import com.mainshell.datautil.data.local.config.ConfigHelper;
import com.mainshell.datautil.data.local.preference.PreferencesHelper;
import com.mainshell.datautil.data.local.xml.XmlHelper;
import com.mainshell.datautil.injection.annotation.ApplicationContext;
import com.mainshell.datautil.util.EventPosterHelper;
import com.sh3h.mainshell.MainApplication;
import com.sh3h.mainshell.broadcast.KeepAliveBroadcast;
import com.sh3h.mainshell.injection.module.ApplicationModule;
import com.sh3h.mainshell.location.GpsLocation;
import com.sh3h.mainshell.service.FileService;
import com.sh3h.mainshell.service.KeepAliveService;
import com.sh3h.mainshell.util.PermissionsChecker;
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