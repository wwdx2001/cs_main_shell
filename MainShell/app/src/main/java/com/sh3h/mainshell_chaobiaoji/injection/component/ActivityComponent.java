package com.sh3h.mainshell_chaobiaoji.injection.component;

import com.sh3h.mainshell_chaobiaoji.injection.annotation.PerActivity;
import com.sh3h.mainshell_chaobiaoji.injection.module.ActivityModule;
import com.sh3h.mainshell_chaobiaoji.ui.installation.ApkInstallationActivity;
import com.sh3h.mainshell_chaobiaoji.ui.login.LoginActivity;
import com.sh3h.mainshell_chaobiaoji.ui.main.MainActivity;
import com.sh3h.mainshell_chaobiaoji.ui.setting.SettingActivity;
import com.sh3h.mainshell_chaobiaoji.ui.welcome.WelcomeActivity;

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
}