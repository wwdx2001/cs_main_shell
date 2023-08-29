package com.mainshell.dataprovider.greendaoDao;

import java.util.Map;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.AbstractDaoSession;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.identityscope.IdentityScopeType;
import org.greenrobot.greendao.internal.DaoConfig;

import com.mainshell.dataprovider.greendaoEntity.Track;
import com.mainshell.dataprovider.greendaoEntity.Word;

import com.mainshell.dataprovider.greendaoDao.TrackDao;
import com.mainshell.dataprovider.greendaoDao.WordDao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see org.greenrobot.greendao.AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig trackDaoConfig;
    private final DaoConfig wordDaoConfig;

    private final TrackDao trackDao;
    private final WordDao wordDao;

    public DaoSession(Database db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        trackDaoConfig = daoConfigMap.get(TrackDao.class).clone();
        trackDaoConfig.initIdentityScope(type);

        wordDaoConfig = daoConfigMap.get(WordDao.class).clone();
        wordDaoConfig.initIdentityScope(type);

        trackDao = new TrackDao(trackDaoConfig, this);
        wordDao = new WordDao(wordDaoConfig, this);

        registerDao(Track.class, trackDao);
        registerDao(Word.class, wordDao);
    }
    
    public void clear() {
        trackDaoConfig.clearIdentityScope();
        wordDaoConfig.clearIdentityScope();
    }

    public TrackDao getTrackDao() {
        return trackDao;
    }

    public WordDao getWordDao() {
        return wordDao;
    }

}