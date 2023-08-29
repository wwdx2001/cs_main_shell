package com.sh3h.dataprovider.data.local.preference;

import android.content.Context;

import javax.inject.Inject;
import javax.inject.Singleton;


@Singleton
public class PreferencesHelper {
    public static final String PREF_FILE_NAME = "android_shanghai3h_pref_file";

    private Context mContext;
    private UserSession mUserSession;
    //private final SharedPreferences mPref;

    @Inject
    public PreferencesHelper() {
        //mPref = context.getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE);
        //mUserSession = new UserSession();
    }

    public void setContext(Context context) {
        mContext = context;
        mUserSession = new UserSession(context);
    }

    public synchronized UserSession getUserSession() {
        return mUserSession;
    }

    public synchronized boolean clearUserSession() {
        return mUserSession.clearUserSession();
    }

    public synchronized boolean saveUserSession() {
        return mUserSession.save();
    }

//    public Observable<Boolean> saveSearchRTPreference(final String s) {
//        return Observable.create(new Observable.OnSubscribe<Boolean>() {
//            @Override
//            public void call(Subscriber<? super Boolean> subscriber) {
//                if (subscriber.isUnsubscribed()) {
//                    return;
//                }
//                try {
//                    subscriber.onNext(mSearchRecode.updateSharedPreferences(s));
//                    subscriber.onCompleted();
//                } catch (Exception e) {
//                    subscriber.onError(e);
//                }
//            }
//        });
//    }
//
//    public Observable<String> getSearchRecode() {
//        return Observable.create(new Observable.OnSubscribe<String>() {
//            @Override
//            public void call(Subscriber<? super String> subscriber) {
//                if (subscriber.isUnsubscribed()) {
//                    return;
//                }
//                try {
//                    subscriber.onNext(mSearchRecode.readSharedPreferences());
//                    subscriber.onCompleted();
//                } catch (Exception e) {
//                    subscriber.onError(e);
//                }
//            }
//        });
//    }
//
//    public Observable<Boolean> clearHistoryRecode() {
//        return Observable.create(new Observable.OnSubscribe<Boolean>() {
//            @Override
//            public void call(Subscriber<? super Boolean> subscriber) {
//                if (subscriber.isUnsubscribed()) {
//                    return;
//                }
//                try {
//                    subscriber.onNext(mSearchRecode.clearSharedPreferences());
//                    subscriber.onCompleted();
//                } catch (Exception e) {
//                    subscriber.onError(e);
//                }
//            }
//        });
//    }
}
