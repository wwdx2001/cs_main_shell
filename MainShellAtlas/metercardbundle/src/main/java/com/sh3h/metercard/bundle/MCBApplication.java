package com.sh3h.metercard.bundle;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.sh3h.dataprovider.data.DataManager;
import com.sh3h.dataprovider.data.local.config.ConfigHelper;
import com.sh3h.dataprovider.util.EventPosterHelper;
import com.sh3h.metercard.bundle.event.UIBusEvent;
import com.sh3h.metercard.bundle.injection.component.ApplicationComponent;
import com.sh3h.metercard.bundle.injection.module.ApplicationModule;
import com.sh3h.metercard.bundle.injection.component.DaggerApplicationComponent;
import com.sh3h.mobileutil.util.LogUtil;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import javax.inject.Inject;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


public class MCBApplication extends Application {
    @Inject
    Bus mEventBus;

    @Inject
    DataManager mDataManager;

    @Inject
    EventPosterHelper mEventPosterHelper;

    @Inject
    ConfigHelper mConfigHelper;
    private static final String TAG = "MCBApplication";
    private static MCBApplication mcbApplication = null;

    private ApplicationComponent mApplicationComponent;
    private boolean mIsConfigInit;
    private Subscription mSubscription;


    @Override
    public void onCreate() {
        super.onCreate();

        mcbApplication = this;
        mApplicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .build();
        mApplicationComponent.inject(this);
        mDataManager.init(this, mEventBus);
        mEventBus.register(this);
        Log.i(TAG, "---onCreate---");
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        mEventBus.unregister(this);
    }

    public ApplicationComponent getComponent() {
        return mApplicationComponent;
    }

    // Needed to replace the component with a test specific one
    public void setComponent(ApplicationComponent applicationComponent) {
        mApplicationComponent = applicationComponent;
    }

    public static MCBApplication get(Context context) {
        return mcbApplication != null ? mcbApplication : (MCBApplication) context.getApplicationContext();
    }

    public void initConfig() {
        if (mIsConfigInit) {
            mEventPosterHelper.postEventSafely(new UIBusEvent.InitResult(true));
            return;
        }

        if (mSubscription != null && !mSubscription.isUnsubscribed()) {
            mSubscription.unsubscribe();
            Log.i(TAG, "---initConfig---2");
        }

        Log.i(TAG, "---initConfig---3");
        mSubscription = mConfigHelper.initDefaultConfigs()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<Void>() {
                    @Override
                    public void onCompleted() {
                        Log.i(TAG, "---initConfig onCompleted---");
                        mDataManager.initLogger();
                        mEventPosterHelper.postEventSafely(new UIBusEvent.InitResult(true));
                        mIsConfigInit = true;
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "---initConfig onError---" + e.getMessage());
                        mEventPosterHelper.postEventSafely(new UIBusEvent.InitResult(false));
                    }

                    @Override
                    public void onNext(Void aVoid) {
                        Log.i(TAG, "---initConfig onNext---");
                    }
                });
    }

    public boolean isConfigInit() {
        return mIsConfigInit;
    }

    @Subscribe
    public void onInitResult(UIBusEvent.InitResult initResult) {
        LogUtil.i(TAG, "---onInitResult---" + initResult.isSuccess());
        if (initResult.isSuccess()) {

        }
    }
}
