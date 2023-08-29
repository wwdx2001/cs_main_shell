package com.sh3h.mainshell_chaobiaoji.ui.base;


import com.sh3h.datautil.data.DataManager;

import rx.subscriptions.CompositeSubscription;

public class ParentPresenter<P extends MvpView> extends BasePresenter<P> {
    protected final DataManager mDataManager;
    protected CompositeSubscription mSubscription;

    public ParentPresenter(DataManager dataManager) {
        mDataManager = dataManager;
    }

    @Override
    public void attachView(P mvpView) {
        super.attachView(mvpView);
        mSubscription = new CompositeSubscription();
    }

    @Override
    public void detachView() {
        super.detachView();
        mSubscription.unsubscribe();
        mSubscription = null;
    }
}
