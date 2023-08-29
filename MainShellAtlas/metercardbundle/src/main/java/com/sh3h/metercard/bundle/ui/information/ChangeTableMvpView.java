package com.sh3h.metercard.bundle.ui.information;


import com.sh3h.dataprovider.data.entity.DUHuanBiaoJL;
import com.sh3h.metercard.bundle.ui.base.MvpView;

import java.util.List;

public interface ChangeTableMvpView extends MvpView {

    void onError(String message);

    void onGetHuanBiaoXXs(List<DUHuanBiaoJL> duHuanBiaoJLs);
}
