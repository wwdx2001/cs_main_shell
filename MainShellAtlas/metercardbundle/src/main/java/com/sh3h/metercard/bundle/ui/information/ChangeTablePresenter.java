package com.sh3h.metercard.bundle.ui.information;

import com.sh3h.dataprovider.data.DataManager;
import com.sh3h.dataprovider.data.entity.DUHuanBiaoJLInfo;
import com.sh3h.metercard.bundle.ui.base.ParentPresenter;

import javax.inject.Inject;

public class ChangeTablePresenter extends ParentPresenter<ChangeTableMvpView> {

    private static final String TAG = "ChangeWaterPresenter";

    @Inject
    public ChangeTablePresenter(DataManager dataManager) {
        super(dataManager);
    }

    /**
     * 获取换表信息
     *
     * @param duHuanBiaoJLInfo
     */
    public void getHuanBiaoXXs(DUHuanBiaoJLInfo duHuanBiaoJLInfo) {
        /*mSubscription.add(mDataManager.getHuanBiaoXXs(duHuanBiaoJLInfo)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<DUBaseResults<DUHuanBiaoJL>>() {
                    @Override
                    public void onCompleted() {
                        LogUtil.i(TAG, "---getHuanBiaoXXs onCompleted---");
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.i(TAG, String.format("---getHuanBiaoXXs onError: %s---", e.getMessage()));
                        getMvpView().onError(e.getMessage());
                    }

                    @Override
                    public void onNext(DUBaseResults<DUHuanBiaoJL> duBaseResults) {
                        LogUtil.i(TAG, "---getHuanBiaoXXs onNext---");
                        getMvpView().onGetHuanBiaoXXs(duBaseResults.getList());
                    }
                })
        );*/
    }
}
