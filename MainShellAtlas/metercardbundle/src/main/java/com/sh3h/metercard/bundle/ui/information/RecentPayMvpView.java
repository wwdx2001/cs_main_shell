package com.sh3h.metercard.bundle.ui.information;


import com.sh3h.dataprovider.data.entity.DUJiaoFeiXX;
import com.sh3h.metercard.bundle.ui.base.MvpView;

import java.util.List;

public interface RecentPayMvpView extends MvpView {
    void onError(String message);

    void onGetJiaoFeiXXs(List<DUJiaoFeiXX> duJiaoFeiXXes);
}
