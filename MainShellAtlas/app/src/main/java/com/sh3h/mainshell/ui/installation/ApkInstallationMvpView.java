package com.sh3h.mainshell.ui.installation;

import com.mainshell.datautil.data.entity.DUApkInfoResultEx;
import com.sh3h.mainshell.ui.base.MvpView;

import java.util.List;

/**
 * Created by xulongjun on 2016/5/18.
 */
public interface ApkInstallationMvpView extends MvpView {
    enum ErrorCode {
        NO_NETWORK,
        PARAM_INVALID
    }

    void onGetApkList(List<DUApkInfoResultEx> duApkInfoResultExList);
    void onCompleted();
    void onError(String message);
    void onError(ErrorCode errorCode);
}
