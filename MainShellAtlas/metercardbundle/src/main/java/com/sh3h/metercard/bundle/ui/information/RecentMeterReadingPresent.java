package com.sh3h.metercard.bundle.ui.information;

import com.sh3h.dataprovider.data.DataManager;
import com.sh3h.dataprovider.data.entity.DUChaoBiaoJLInfo;
import com.sh3h.metercard.bundle.ui.base.ParentPresenter;

import javax.inject.Inject;

public class RecentMeterReadingPresent extends ParentPresenter<RecentMeterReadingMvpView> {

    private static final String TAG = "ReadWaterPresenter";

    @Inject
    public RecentMeterReadingPresent(DataManager dataManager) {
        super(dataManager);
    }


    /**
     * 获取抄表记录
     *
     * @param duChaoBiaoJLInfo
     */
    public void getChaoBiaoJL(DUChaoBiaoJLInfo duChaoBiaoJLInfo) {
        /*mSubscription.add(mDataManager.getChaoBiaoJLs(duChaoBiaoJLInfo)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<DUBaseResults<DUChaoBiaoJL>>() {
                    @Override
                    public void onCompleted() {
                        LogUtil.i(TAG, "---getChaoBiaoJL onCompleted---");
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.i(TAG, String.format("---getChaoBiaoJL onError: %s---", e.getMessage()));
                        getMvpView().onError(e.getMessage());
                    }

                    @Override
                    public void onNext(DUBaseResults<DUChaoBiaoJL> duBaseResults) {
                        LogUtil.i(TAG, "---getChaoBiaoJL onNext---");
                        getMvpView().onGetChaoBiaoJLFinish(duBaseResults.getList());
                    }
                }));*/
    }
}
