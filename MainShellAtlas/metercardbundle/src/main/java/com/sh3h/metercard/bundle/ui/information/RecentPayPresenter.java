package com.sh3h.metercard.bundle.ui.information;


import com.sh3h.dataprovider.data.entity.DUJiaoFeiXXInfo;
import com.sh3h.dataprovider.data.DataManager;
import com.sh3h.metercard.bundle.ui.base.ParentPresenter;

import javax.inject.Inject;

public class RecentPayPresenter extends ParentPresenter<RecentPayMvpView> {

    private static final String TAG = "PayWaterPresenter";

    @Inject
    public RecentPayPresenter(DataManager dataManager) {
        super(dataManager);
    }

    /**
     * 获取缴费信息
     *
     * @param duJiaoFeiXXInfo
     */
    public void getJiaoFeiXXs(DUJiaoFeiXXInfo duJiaoFeiXXInfo) {
        /*mSubscription.add(mDataManager.getJiaoFeiXXs(duJiaoFeiXXInfo)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<DUBaseResults<DUJiaoFeiXX>>() {
                    @Override
                    public void onCompleted() {
                        LogUtil.i(TAG, "---getJiaoFeiXXs onCompleted---");
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.i(TAG, String.format("---getJiaoFeiXXs onError: %s---", e.getMessage()));
                        getMvpView().onError(e.getMessage());
                    }

                    @Override
                    public void onNext(DUBaseResults<DUJiaoFeiXX> duBaseResults) {
                        LogUtil.i(TAG, "---getJiaoFeiXXs onNext---");
                        getMvpView().onGetJiaoFeiXXs(duBaseResults.getList());
                    }
                })
        );*/
    }
}
