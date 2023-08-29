package com.sh3h.mainshell.injection.component;

import com.sh3h.mainshell.injection.annotation.PerActivity;
import com.sh3h.mainshell.injection.module.ActivityModule;
import com.sh3h.mainshell.ui.base.NetworkDialog;
import com.sh3h.mainshell.ui.installation.ApkInstallationActivity;
import com.sh3h.mainshell.ui.login.LoginActivity;
import com.sh3h.mainshell.ui.main.MainActivity;
import com.sh3h.mainshell.ui.setting.SettingActivity;
import com.sh3h.mainshell.ui.welcome.WelcomeActivity;

import dagger.Component;

/**
 * This component inject dependencies to all Activities across the application
 */
@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {
    void inject(LoginActivity loginActivity);
    void inject(MainActivity mainActivity);
    void inject(ApkInstallationActivity apkInstallationActivity);
    void inject(SettingActivity settingActivity);
    void inject(WelcomeActivity welcomeActivity);
    void inject(NetworkDialog networkDialog);
}