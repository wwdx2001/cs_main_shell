package com.sh3h.metercard.bundle.ui.information;

import com.sh3h.dataprovider.data.DataManager;
import com.sh3h.dataprovider.data.entity.DUQianFeiXXInfo;
import com.sh3h.metercard.bundle.ui.base.ParentPresenter;

import javax.inject.Inject;

public class ArrearsInfoPresenter extends ParentPresenter<ArrearsInfoMvpView> {

    private static final String TAG = "ArrearsWaterPresenter";

    @Inject
    public ArrearsInfoPresenter(DataManager dataManager) {
        super(dataManager);
    }

    /**
     * 获取欠费信息
     *
     * @param duQianFeiXXInfo
     */
    public void getQianFeiXXs(DUQianFeiXXInfo duQianFeiXXInfo) {
        /*mSubscription.add(mDataManager.getQianFeiXXs(duQianFeiXXInfo)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<DUBaseResults<DUQianFeiXX>>() {
                    @Override
                    public void onCompleted() {
                        LogUtil.i(TAG, "---getQianFeiXXs onCompleted---");
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.i(TAG, String.format("---getQianFeiXXs onError: %s---", e.getMessage()));
                        getMvpView().onError(e.getMessage());
                    }

                    @Override
                    public void onNext(DUBaseResults<DUQianFeiXX> duBaseResults) {
                        LogUtil.i(TAG, "---getQianFeiXXs onNext---");
                        getMvpView().onGetQianFeiXXs(duBaseResults.getList());
                    }
                })
        );*/
    }
}
