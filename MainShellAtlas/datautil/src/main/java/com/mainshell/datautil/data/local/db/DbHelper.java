package com.mainshell.datautil.data.local.db;

import android.content.Context;

import com.mainshell.dataprovider.DBManager;
import com.mainshell.dataprovider.greendaoEntity.Track;
import com.mainshell.dataprovider.greendaoEntity.Word;
import com.mainshell.datautil.data.entity.DULocationTrack;
import com.mainshell.datautil.data.entity.result.DUWordsResult;
import com.mainshell.datautil.data.local.config.ConfigHelper;
import com.mainshell.datautil.exception.DUException;
import com.mainshell.datautil.injection.annotation.ApplicationContext;
import com.sh3h.mobileutil.util.LogUtil;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;
import rx.Subscriber;

@Singleton
public class DbHelper {
    private static final String TAG = "DbHelper";

    private final Context mContext;
    private final ConfigHelper mConfigHelper;
    private boolean mIsInit;

    @Inject
    public DbHelper(@ApplicationContext Context context,
                    ConfigHelper configHelper) {
        mContext = context;
        mConfigHelper = configHelper;
        mIsInit = false;
    }

    /**
     * initialize
     */
    public synchronized boolean init() {
        if (!mIsInit) {
            mIsInit = true;
            return DBManager.getInstance().init(mConfigHelper.getDBFilePath().getPath(), mContext);
        }

        return true;
    }

    /**
     * destroy
     */
    public synchronized void destroy() {
        if (mIsInit) {
            mIsInit = false;
            DBManager.getInstance().destroy();
        }
    }

    /**
     * clear all tables
     */
    public synchronized boolean clearData() {
        try {
            // if db is opened failure, return also
            if (!init()) {
                return false;
            }

            DBManager.getInstance().clearAllTables();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            LogUtil.i(TAG, String.format("---clearData---%s", e.getMessage()));
        }

        return false;
    }

    /**
     * save the track
     *
     * @param duLocationTrack
     */
    public synchronized boolean saveTrack(DULocationTrack duLocationTrack, int shangchuangBZ,
                             boolean needClearTrack) {
        try {
            // if db is opened failure, return also
            if (!init()) {
                return false;
            }

            if (duLocationTrack != null) {
                DBManager.getInstance().insertTrack(duLocationTrack2Track(duLocationTrack, shangchuangBZ));
                if (needClearTrack) {
                    DBManager.getInstance().clearTrack(duLocationTrack.getUserId(),
                            mConfigHelper.getKeepAliveReservedCount());
                }
                return true;
            } else {
                LogUtil.e(TAG, "---saveTrack: duLocationTrack is null---");
            }
        } catch (Exception e) {
            e.printStackTrace();
            LogUtil.e(TAG, String.format("---saveTrack---%s", e.getMessage()));
        }

        return false;
    }

//    public Observable<Boolean> isDownAllWords() {
//        return Observable.create(new Observable.OnSubscribe<Boolean>() {
//            @Override
//            public void call(Subscriber<? super Boolean> subscriber) {
//                if (subscriber.isUnsubscribed()) {
//                    return;
//                }
//
//                try {
//                    init();
//                    List<Word> wordList = DBManager.getInstance().getWords();
//                    if (wordList == null || wordList.size() <= 0) {
//                        subscriber.onNext(true);
//                    } else {
//                        subscriber.onNext(false);
//                    }
//                } catch (Exception e) {
//                    e.printStackTrace();
//                    subscriber.onError(new Throwable(e.getMessage()));
//                } finally {
//                    subscriber.onCompleted();
//                }
//            }
//        });
//    }

    /**
     * insert all words
     * @param duWordsResults
     * @return
     */
    public Observable<Boolean> insertWords(final List<DUWordsResult> duWordsResults) {
        return Observable.create(new Observable.OnSubscribe<Boolean>() {
            @Override
            public void call(Subscriber<? super Boolean> subscriber) {
                if (subscriber.isUnsubscribed()) {
                    return;
                }

                try {
                    if (duWordsResults == null){
                        throw new NullPointerException(DUException.PARAM_NULL.getName());
                    }

                    // if db is opened failure, return also
                    if (!init()) {
                        throw new Exception(DUException.DB_ERROR.getName());
                    }

                    DBManager.getInstance().insertWordList(duWordsResults2WordList(duWordsResults));
                    subscriber.onNext(true);
                } catch (Exception e) {
                    e.printStackTrace();
                    subscriber.onError(new Throwable(e.getMessage()));
                } finally {
                    subscriber.onCompleted();
                }
            }
        });
    }

    private List<Word> duWordsResults2WordList(List<DUWordsResult> duWordsResults){
        List<Word> wordsList = new ArrayList<>();
        for (DUWordsResult duWordsResult : duWordsResults){
            wordsList.add(new Word(
                    null,
                    duWordsResult.getWid(),
                    duWordsResult.getwName(),
                    duWordsResult.getwValue(),
                    duWordsResult.getwValueEx(),
                    duWordsResult.getwGroup(),
                    duWordsResult.getwParentId(),
                    duWordsResult.getwRemark()
            ));
        }
        return wordsList;
    }

    private Track duLocationTrack2Track(DULocationTrack duLocationTrack, int shangChuangBZ) {
        return new Track(
                null,
                String.valueOf(duLocationTrack.getLocation().getUserId()),
                duLocationTrack.getLocation().getDeviceId(),
                duLocationTrack.getLocation().getLocationType(),
                duLocationTrack.getLocation().getLongitude(),
                duLocationTrack.getLocation().getLatitude(),
                duLocationTrack.getLocation().getRadius(),
                duLocationTrack.getLocation().getAltitude(),
                duLocationTrack.getLocation().getDirection(),
                duLocationTrack.getLocation().getSpeed(),
                duLocationTrack.getLocation().getTime(),
                shangChuangBZ,
                duLocationTrack.getLocation().getExtend());
    }

}
