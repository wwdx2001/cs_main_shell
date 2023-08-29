package com.sh3h.mainshell.ui.welcome;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.view.KeyEvent;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.mainshell.datautil.util.NetworkUtil;
import com.sh3h.mainshell.MainApplication;
import com.sh3h.mainshell.R;
import com.sh3h.mainshell.ui.base.BaseActivity;
import com.sh3h.mainshell.ui.base.NetworkDialog;
import com.sh3h.mainshell.ui.login.LoginActivity;
import com.sh3h.mainshell.ui.main.MainActivity;
import com.sh3h.mainshell.util.ConstDataUtil;
import com.sh3h.mainshell.util.PermissionsChecker;
import com.sh3h.mainshell.util.SystemUtil;
import com.sh3h.mobileutil.util.ApplicationsUtil;
import com.sh3h.mobileutil.util.LogUtil;
import com.sh3h.mobileutil.util.TextUtil;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class WelcomeActivity extends BaseActivity implements WelcomeMvpView, View.OnClickListener {
    @Inject
    WelcomePresenter mWelcomePresenter;

    @Inject
    PermissionsChecker mPermissionsChecker;

    @Inject
    NetworkDialog mNetworkDialog;

    @BindView(R.id.iv_logo)
    ImageView mImageView;

    @BindView(R.id.tv_state)
    TextView mStateTextView;

    @BindView(R.id.pb_loading)
    ProgressBar mProgressBar;

    @BindView(R.id.tv_version)
    TextView mVersionTextView;

    private static final String TAG = "WelcomeActivity";
    private static final int PERMISSION_REQUEST_CODE = 0; // 系统权限管理页面的参数

    private Unbinder mUnbinder;
    private boolean isExit;
    private boolean canSetNetWork;

    private static final String[] PERMISSIONS = new String[]{
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_WIFI_STATE,
            Manifest.permission.ACCESS_NETWORK_STATE,
            Manifest.permission.CHANGE_WIFI_STATE,
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.CALL_PHONE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.INTERNET,
            Manifest.permission.WAKE_LOCK,
            Manifest.permission.CAMERA,
            Manifest.permission.RECORD_AUDIO
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        getActivityComponent().inject(this);
        mUnbinder = ButterKnife.bind(this);

        String version = SystemUtil.getVersionName(MainApplication.get(this));
        if (!TextUtil.isNullOrEmpty(version)) {
            mVersionTextView.setText(version);
        }

        mImageView.setOnClickListener(this);

        isExit = false;
        canSetNetWork = false;
        mWelcomePresenter.attachView(this);
        if ((Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
                && mPermissionsChecker.lacksPermissions(PERMISSIONS)) {
            requestPermissions(PERMISSIONS);
        } else {
            mWelcomePresenter.init();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        mUnbinder.unbind();
        mWelcomePresenter.detachView();
        if (isExit) {
            System.exit(0);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_logo:
                if (canSetNetWork) {
                    setNetwork();
                }
                break;
        }
    }

    @Override
    public void showProgress(int length) {

    }

    @Override
    public void onError(Operation operation, String message) {
        if (!TextUtil.isNullOrEmpty(message)) {
            ApplicationsUtil.showMessage(this, message);
            LogUtil.i(TAG, message);
        }

        canSetNetWork = true;
    }

    @Override
    public void onFinished(Operation operation) {
        switch (operation) {
            case INIT:
                register();
                break;
            case REGISTER:
                checkUser();
                break;
            default:
                break;
        }
    }

    @Override
    public void showMessage(String message) {
        if (!TextUtil.isNullOrEmpty(message)) {
            ApplicationsUtil.showMessage(this, message);
            LogUtil.i(TAG, message);
        }
    }

    @Override
    public void showMessage(int message) {
        ApplicationsUtil.showMessage(this, message);
    }

    private void register() {
        mWelcomePresenter.register(MainApplication.get(this));
    }

    private void checkUser() {
        mWelcomePresenter.checkUser(NetworkUtil.isNetworkConnected(MainApplication.get(this)));
    }

    @Override
    public void jumpActivity(boolean loginOrMain) {
        MainApplication.get(this).initMonitor();
        Intent intent = loginOrMain ? new Intent(this, LoginActivity.class) : new Intent(this, MainActivity.class);
        intent.putExtra(ConstDataUtil.IS_FROM_LAUNCHER, true);
        startActivity(intent);
        finish();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_DOWN) {
            isExit = true;
        }

        return super.onKeyDown(keyCode, event);
    }

    //请求权限兼容低版本
    private void requestPermissions(String... permissions) {
        ActivityCompat.requestPermissions(this, permissions, PERMISSION_REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == PERMISSION_REQUEST_CODE
                && hasAllPermissionsGranted(grantResults)) {
            ApplicationsUtil.showMessage(this,
                    R.string.text_permissions_granted_success);
            mWelcomePresenter.init();
        } else {
            popupPermissionDialog();
        }
    }

    private boolean hasAllPermissionsGranted(int[] grantResults) {
        for (int grantResult : grantResults) {
            if (grantResult == PackageManager.PERMISSION_DENIED) {
                return false;
            }
        }
        return true;
    }

    private void popupPermissionDialog() {
        new MaterialDialog.Builder(this)
                .title(R.string.text_prompt)
                .content(R.string.text_lack_permissions)
                .positiveText(R.string.text_ok)
                .cancelable(false)
                .onAny(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog,
                                        @NonNull DialogAction which) {
                        isExit = true;
                        finish();
                    }
                })
                .show();
    }

    private void setNetwork() {
        mNetworkDialog.show(this, new NetworkDialog.OnSaveListener() {
            @Override
            public void onSave(boolean b) {
                if (b) {
                    isExit = true;
                    finish();
                }
            }
        });
    }
}
