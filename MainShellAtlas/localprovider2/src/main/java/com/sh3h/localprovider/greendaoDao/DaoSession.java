package com.sh3h.localprovider.greendaoDao;

import java.util.Map;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.AbstractDaoSession;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.identityscope.IdentityScopeType;
import org.greenrobot.greendao.internal.DaoConfig;

import com.sh3h.localprovider.greendaoEntity.ArrearAge;
import com.sh3h.localprovider.greendaoEntity.ArrearAgeTotal;
import com.sh3h.localprovider.greendaoEntity.Caliber;
import com.sh3h.localprovider.greendaoEntity.CaliberRange;
import com.sh3h.localprovider.greendaoEntity.Card;
import com.sh3h.localprovider.greendaoEntity.Charge;
import com.sh3h.localprovider.greendaoEntity.MeterModel;
import com.sh3h.localprovider.greendaoEntity.MeterProducer;
import com.sh3h.localprovider.greendaoEntity.MeterWorker;
import com.sh3h.localprovider.greendaoEntity.Multimedia;
import com.sh3h.localprovider.greendaoEntity.PreviousRecord;
import com.sh3h.localprovider.greendaoEntity.Price;
import com.sh3h.localprovider.greendaoEntity.PriceCategory;
import com.sh3h.localprovider.greendaoEntity.PriceDetail;
import com.sh3h.localprovider.greendaoEntity.Priceitem;
import com.sh3h.localprovider.greendaoEntity.ReadState;
import com.sh3h.localprovider.greendaoEntity.ReCord;
import com.sh3h.localprovider.greendaoEntity.Task;
import com.sh3h.localprovider.greendaoEntity.Word;

import com.sh3h.localprovider.greendaoDao.ArrearAgeDao;
import com.sh3h.localprovider.greendaoDao.ArrearAgeTotalDao;
import com.sh3h.localprovider.greendaoDao.CaliberDao;
import com.sh3h.localprovider.greendaoDao.CaliberRangeDao;
import com.sh3h.localprovider.greendaoDao.CardDao;
import com.sh3h.localprovider.greendaoDao.ChargeDao;
import com.sh3h.localprovider.greendaoDao.MeterModelDao;
import com.sh3h.localprovider.greendaoDao.MeterProducerDao;
import com.sh3h.localprovider.greendaoDao.MeterWorkerDao;
import com.sh3h.localprovider.greendaoDao.MultimediaDao;
import com.sh3h.localprovider.greendaoDao.PreviousRecordDao;
import com.sh3h.localprovider.greendaoDao.PriceDao;
import com.sh3h.localprovider.greendaoDao.PriceCategoryDao;
import com.sh3h.localprovider.greendaoDao.PriceDetailDao;
import com.sh3h.localprovider.greendaoDao.PriceitemDao;
import com.sh3h.localprovider.greendaoDao.ReadStateDao;
import com.sh3h.localprovider.greendaoDao.ReCordDao;
import com.sh3h.localprovider.greendaoDao.TaskDao;
import com.sh3h.localprovider.greendaoDao.WordDao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see org.greenrobot.greendao.AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig arrearAgeDaoConfig;
    private final DaoConfig arrearAgeTotalDaoConfig;
    private final DaoConfig caliberDaoConfig;
    private final DaoConfig caliberRangeDaoConfig;
    private final DaoConfig cardDaoConfig;
    private final DaoConfig chargeDaoConfig;
    private final DaoConfig meterModelDaoConfig;
    private final DaoConfig meterProducerDaoConfig;
    private final DaoConfig meterWorkerDaoConfig;
    private final DaoConfig multimediaDaoConfig;
    private final DaoConfig previousRecordDaoConfig;
    private final DaoConfig priceDaoConfig;
    private final DaoConfig priceCategoryDaoConfig;
    private final DaoConfig priceDetailDaoConfig;
    private final DaoConfig priceitemDaoConfig;
    private final DaoConfig readStateDaoConfig;
    private final DaoConfig reCordDaoConfig;
    private final DaoConfig taskDaoConfig;
    private final DaoConfig wordDaoConfig;

    private final ArrearAgeDao arrearAgeDao;
    private final ArrearAgeTotalDao arrearAgeTotalDao;
    private final CaliberDao caliberDao;
    private final CaliberRangeDao caliberRangeDao;
    private final CardDao cardDao;
    private final ChargeDao chargeDao;
    private final MeterModelDao meterModelDao;
    private final MeterProducerDao meterProducerDao;
    private final MeterWorkerDao meterWorkerDao;
    private final MultimediaDao multimediaDao;
    private final PreviousRecordDao previousRecordDao;
    private final PriceDao priceDao;
    private final PriceCategoryDao priceCategoryDao;
    private final PriceDetailDao priceDetailDao;
    private final PriceitemDao priceitemDao;
    private final ReadStateDao readStateDao;
    private final ReCordDao reCordDao;
    private final TaskDao taskDao;
    private final WordDao wordDao;

    public DaoSession(Database db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        arrearAgeDaoConfig = daoConfigMap.get(ArrearAgeDao.class).clone();
        arrearAgeDaoConfig.initIdentityScope(type);

        arrearAgeTotalDaoConfig = daoConfigMap.get(ArrearAgeTotalDao.class).clone();
        arrearAgeTotalDaoConfig.initIdentityScope(type);

        caliberDaoConfig = daoConfigMap.get(CaliberDao.class).clone();
        caliberDaoConfig.initIdentityScope(type);

        caliberRangeDaoConfig = daoConfigMap.get(CaliberRangeDao.class).clone();
        caliberRangeDaoConfig.initIdentityScope(type);

        cardDaoConfig = daoConfigMap.get(CardDao.class).clone();
        cardDaoConfig.initIdentityScope(type);

        chargeDaoConfig = daoConfigMap.get(ChargeDao.class).clone();
        chargeDaoConfig.initIdentityScope(type);

        meterModelDaoConfig = daoConfigMap.get(MeterModelDao.class).clone();
        meterModelDaoConfig.initIdentityScope(type);

        meterProducerDaoConfig = daoConfigMap.get(MeterProducerDao.class).clone();
        meterProducerDaoConfig.initIdentityScope(type);

        meterWorkerDaoConfig = daoConfigMap.get(MeterWorkerDao.class).clone();
        meterWorkerDaoConfig.initIdentityScope(type);

        multimediaDaoConfig = daoConfigMap.get(MultimediaDao.class).clone();
        multimediaDaoConfig.initIdentityScope(type);

        previousRecordDaoConfig = daoConfigMap.get(PreviousRecordDao.class).clone();
        previousRecordDaoConfig.initIdentityScope(type);

        priceDaoConfig = daoConfigMap.get(PriceDao.class).clone();
        priceDaoConfig.initIdentityScope(type);

        priceCategoryDaoConfig = daoConfigMap.get(PriceCategoryDao.class).clone();
        priceCategoryDaoConfig.initIdentityScope(type);

        priceDetailDaoConfig = daoConfigMap.get(PriceDetailDao.class).clone();
        priceDetailDaoConfig.initIdentityScope(type);

        priceitemDaoConfig = daoConfigMap.get(PriceitemDao.class).clone();
        priceitemDaoConfig.initIdentityScope(type);

        readStateDaoConfig = daoConfigMap.get(ReadStateDao.class).clone();
        readStateDaoConfig.initIdentityScope(type);

        reCordDaoConfig = daoConfigMap.get(ReCordDao.class).clone();
        reCordDaoConfig.initIdentityScope(type);

        taskDaoConfig = daoConfigMap.get(TaskDao.class).clone();
        taskDaoConfig.initIdentityScope(type);

        wordDaoConfig = daoConfigMap.get(WordDao.class).clone();
        wordDaoConfig.initIdentityScope(type);

        arrearAgeDao = new ArrearAgeDao(arrearAgeDaoConfig, this);
        arrearAgeTotalDao = new ArrearAgeTotalDao(arrearAgeTotalDaoConfig, this);
        caliberDao = new CaliberDao(caliberDaoConfig, this);
        caliberRangeDao = new CaliberRangeDao(caliberRangeDaoConfig, this);
        cardDao = new CardDao(cardDaoConfig, this);
        chargeDao = new ChargeDao(chargeDaoConfig, this);
        meterModelDao = new MeterModelDao(meterModelDaoConfig, this);
        meterProducerDao = new MeterProducerDao(meterProducerDaoConfig, this);
        meterWorkerDao = new MeterWorkerDao(meterWorkerDaoConfig, this);
        multimediaDao = new MultimediaDao(multimediaDaoConfig, this);
        previousRecordDao = new PreviousRecordDao(previousRecordDaoConfig, this);
        priceDao = new PriceDao(priceDaoConfig, this);
        priceCategoryDao = new PriceCategoryDao(priceCategoryDaoConfig, this);
        priceDetailDao = new PriceDetailDao(priceDetailDaoConfig, this);
        priceitemDao = new PriceitemDao(priceitemDaoConfig, this);
        readStateDao = new ReadStateDao(readStateDaoConfig, this);
        reCordDao = new ReCordDao(reCordDaoConfig, this);
        taskDao = new TaskDao(taskDaoConfig, this);
        wordDao = new WordDao(wordDaoConfig, this);

        registerDao(ArrearAge.class, arrearAgeDao);
        registerDao(ArrearAgeTotal.class, arrearAgeTotalDao);
        registerDao(Caliber.class, caliberDao);
        registerDao(CaliberRange.class, caliberRangeDao);
        registerDao(Card.class, cardDao);
        registerDao(Charge.class, chargeDao);
        registerDao(MeterModel.class, meterModelDao);
        registerDao(MeterProducer.class, meterProducerDao);
        registerDao(MeterWorker.class, meterWorkerDao);
        registerDao(Multimedia.class, multimediaDao);
        registerDao(PreviousRecord.class, previousRecordDao);
        registerDao(Price.class, priceDao);
        registerDao(PriceCategory.class, priceCategoryDao);
        registerDao(PriceDetail.class, priceDetailDao);
        registerDao(Priceitem.class, priceitemDao);
        registerDao(ReadState.class, readStateDao);
        registerDao(ReCord.class, reCordDao);
        registerDao(Task.class, taskDao);
        registerDao(Word.class, wordDao);
    }
    
    public void clear() {
        arrearAgeDaoConfig.clearIdentityScope();
        arrearAgeTotalDaoConfig.clearIdentityScope();
        caliberDaoConfig.clearIdentityScope();
        caliberRangeDaoConfig.clearIdentityScope();
        cardDaoConfig.clearIdentityScope();
        chargeDaoConfig.clearIdentityScope();
        meterModelDaoConfig.clearIdentityScope();
        meterProducerDaoConfig.clearIdentityScope();
        meterWorkerDaoConfig.clearIdentityScope();
        multimediaDaoConfig.clearIdentityScope();
        previousRecordDaoConfig.clearIdentityScope();
        priceDaoConfig.clearIdentityScope();
        priceCategoryDaoConfig.clearIdentityScope();
        priceDetailDaoConfig.clearIdentityScope();
        priceitemDaoConfig.clearIdentityScope();
        readStateDaoConfig.clearIdentityScope();
        reCordDaoConfig.clearIdentityScope();
        taskDaoConfig.clearIdentityScope();
        wordDaoConfig.clearIdentityScope();
    }

    public ArrearAgeDao getArrearAgeDao() {
        return arrearAgeDao;
    }

    public ArrearAgeTotalDao getArrearAgeTotalDao() {
        return arrearAgeTotalDao;
    }

    public CaliberDao getCaliberDao() {
        return caliberDao;
    }

    public CaliberRangeDao getCaliberRangeDao() {
        return caliberRangeDao;
    }

    public CardDao getCardDao() {
        return cardDao;
    }

    public ChargeDao getChargeDao() {
        return chargeDao;
    }

    public MeterModelDao getMeterModelDao() {
        return meterModelDao;
    }

    public MeterProducerDao getMeterProducerDao() {
        return meterProducerDao;
    }

    public MeterWorkerDao getMeterWorkerDao() {
        return meterWorkerDao;
    }

    public MultimediaDao getMultimediaDao() {
        return multimediaDao;
    }

    public PreviousRecordDao getPreviousRecordDao() {
        return previousRecordDao;
    }

    public PriceDao getPriceDao() {
        return priceDao;
    }

    public PriceCategoryDao getPriceCategoryDao() {
        return priceCategoryDao;
    }

    public PriceDetailDao getPriceDetailDao() {
        return priceDetailDao;
    }

    public PriceitemDao getPriceitemDao() {
        return priceitemDao;
    }

    public ReadStateDao getReadStateDao() {
        return readStateDao;
    }

    public ReCordDao getReCordDao() {
        return reCordDao;
    }

    public TaskDao getTaskDao() {
        return taskDao;
    }

    public WordDao getWordDao() {
        return wordDao;
    }

}
