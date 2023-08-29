package com.sh3h.metercard.bundle.ui.information;


import com.sh3h.dataprovider.data.entity.DUQianFeiXX;
import com.sh3h.metercard.bundle.ui.base.MvpView;

import java.util.List;

public interface ArrearsInfoMvpView extends MvpView {
    void onError(String message);

    void onGetQianFeiXXs(List<DUQianFeiXX> duQianFeiXXList);
}
