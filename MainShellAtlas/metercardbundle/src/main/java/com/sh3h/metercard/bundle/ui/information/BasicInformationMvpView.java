package com.sh3h.metercard.bundle.ui.information;


import com.sh3h.dataprovider.data.entity.DUCard;
import com.sh3h.metercard.bundle.ui.base.MvpView;

public interface BasicInformationMvpView extends MvpView {
    void onGetCardFinish(DUCard duCard);

    void onError(String message);
}
