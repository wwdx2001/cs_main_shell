package com.sh3h.metercard.bundle.ui.information;

import com.sh3h.dataprovider.data.DataManager;
import com.sh3h.dataprovider.data.entity.DUCardInfo;
import com.sh3h.metercard.bundle.ui.base.ParentPresenter;

import javax.inject.Inject;

public class BasicInformationPresenter extends ParentPresenter<BasicInformationMvpView> {

    private static final String TAG = "BasicInformationPresenter";

    @Inject
    public BasicInformationPresenter(DataManager dataManager) {
        super(dataManager);
    }

    /**
     * 获取表卡信息
     *
     * @param duCardInfo
     */
    public void getOneCard(final DUCardInfo duCardInfo) {
        /*mSubscription.add(mDataManager.getCard(duCardInfo)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<DUBaseResult<DUCard>>() {
                    @Override
                    public void onCompleted() {
                        LogUtil.i(TAG, "---getOneCard onCompleted---");
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.i(TAG, String.format("---getOneCard onError: %s---", e.getMessage()));
                        getMvpView().onError(e.getMessage());
                    }

                    @Override
                    public void onNext(DUBaseResult<DUCard> duCardDUBaseResults) {
                        LogUtil.i(TAG, "---getOneCard onNext---");
                        DUCard duCard = duCardDUBaseResults.getEntity();
                        if (duCard != null) {
                            getMvpView().onGetCardFinish(duCard);
                        }
                    }
                })
        );*/
    }
}
