package com.sh3h.metercard.bundle.ui.information;

import com.sh3h.dataprovider.data.entity.DUChaoBiaoJL;
import com.sh3h.metercard.bundle.ui.base.MvpView;

import java.util.List;

public interface RecentMeterReadingMvpView extends MvpView {
    void onError(String message);

    void onGetChaoBiaoJLFinish(List<DUChaoBiaoJL> duChaoBiaoJLs);
}
