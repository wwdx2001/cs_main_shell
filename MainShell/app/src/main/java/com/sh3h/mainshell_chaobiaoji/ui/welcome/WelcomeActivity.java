package com.sh3h.mainshell_chaobiaoji.ui.welcome;


import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.view.KeyEvent;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.sh3h.datautil.util.NetworkUtil;
import com.sh3h.mainshell_chaobiaoji.MainApplication;
import com.sh3h.mainshell_chaobiaoji.R;
import com.sh3h.mainshell_chaobiaoji.ui.base.BaseActivity;
import com.sh3h.mainshell_chaobiaoji.ui.login.LoginActivity;
import com.sh3h.mainshell_chaobiaoji.ui.main.MainActivity;
import com.sh3h.mainshell_chaobiaoji.util.ConstDataUtil;
import com.sh3h.mainshell_chaobiaoji.util.DeviceUtil;
import com.sh3h.mainshell_chaobiaoji.util.PermissionsChecker;
import com.sh3h.mainshell_chaobiaoji.util.SystemUtil;
import com.sh3h.mobileutil.util.ApplicationsUtil;
import com.sh3h.mobileutil.util.LogUtil;
import com.sh3h.mobileutil.util.TextUtil;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.sh3h.mainshell_chaobiaoji.AppChannel.JIANGMENG_CHANNEL;
import static com.sh3h.mainshell_chaobiaoji.AppChannel.WEIHAI_CHANNEL;
import static com.sh3h.mainshell_chaobiaoji.MainApplication.SUPER_ADMIN_DEFAULT;
import static com.sh3h.mainshell_chaobiaoji.MainApplication.SUPER_ADMIN_JM;
import static com.sh3h.mainshell_chaobiaoji.MainApplication.SUPER_PASSWORD_DEFAULT;
import static com.sh3h.mainshell_chaobiaoji.MainApplication.SUPER_PASSWORD_JM;

public class WelcomeActivity extends BaseActivity implements WelcomeMvpView, View.OnClickListener {
    @Inject
    WelcomePresenter mWelcomePresenter;

    @Inject
    PermissionsChecker mPermissionsChecker;

    @BindView(R.id.ll_background)
    LinearLayout mLinearLayout;

    @BindView(R.id.iv_logo)
    ImageView mImageView;

    @BindView(R.id.tv_state)
    TextView mStateTextView;

    @BindView(R.id.pb_loading)
    ProgressBar mProgressBar;

    @BindView(R.id.tv_version)
    TextView mVersionTextView;

    private static final int REQUEST_CODE = 0;//请求码

    private static final String TAG = "WelcomeActivity";
    //private static final String POSITIVE = "POSITIVE";
    //private static final String NEGATIVE = "NEGATIVE";
    private static final int PERMISSION_REQUEST_CODE = 0; // 系统权限管理页面的参数

    private Unbinder mUnbinder;

    private boolean isExit;

    private boolean canSetNetWork;
    private String baseURI;
    private String brokeURL;
    private String reservedBaseURI;
    private String reservedBrokeURL;
    private String countlyUri;

    private EditText baseUriTextView;
    private EditText brokeUrlTextView;
    private EditText reservedBaseUriTextView;
    private EditText reservedBrokeUrlTextView;
    private EditText countlyUriTextView;
    private CheckBox reservedAddressCheckBox;

    private EditText accountEditText;
    private EditText passwordEditText;

    private static final String[] PERMISSIONS = new String[]{
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_WIFI_STATE,
            Manifest.permission.ACCESS_NETWORK_STATE,
            Manifest.permission.CHANGE_WIFI_STATE,
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.INTERNET,
            Manifest.permission.WAKE_LOCK
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (MainApplication.get(this).isGreenTheme()) {
            setContentView(R.layout.activity_welcome_green);
        } else {
            setContentView(R.layout.activity_welcome);
        }
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
            checkGpsOpened();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        //MobclickAgent.onPageStart(TAG);
    }

    @Override
    protected void onPause() {
        super.onPause();
        //MobclickAgent.onPageEnd(TAG);
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
                    popupAuthDialog();
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

        //isExit = true;
        //finish();
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

    @Override
    public void onSaveNetWorkSetting() {
        isExit = true;
        finish();
    }

    private void register() {
        mWelcomePresenter.register(MainApplication.get(this));
    }

    private void checkUser() {
        mWelcomePresenter.checkUser(NetworkUtil.isNetworkConnected(MainApplication.get(this)),
                getPackageName(), DeviceUtil.getDeviceID(MainApplication.get(this)),
                MainApplication.get(this).getAppChannel());
    }

    @Override
    public void jumpActivity(boolean loginOrMain) {
        MainApplication.get(this).initMonitor();
        Intent intent = loginOrMain ? new Intent(this, LoginActivity.class) : new Intent(this, MainActivity.class);
        intent.putExtra(ConstDataUtil.IS_FROM_LAUNCHER, true);
        startActivity(intent);
        finish();
    }

    private void popupAuthDialog() {
        final String appChannel = MainApplication.get(this).getAppChannel();
        MaterialDialog dialog = new MaterialDialog.Builder(this)
                .title(R.string.text_prompt_super_admin_password)
                .customView(R.layout.dialog_auth, true)
                .positiveText(R.string.text_ok)
                .negativeText(android.R.string.cancel)
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        String account = accountEditText.getText().toString();
                        String password = passwordEditText.getText().toString();
                        if (!TextUtil.isNullOrEmpty(appChannel)
                                && !TextUtil.isNullOrEmpty(account)
                                && !TextUtil.isNullOrEmpty(password)) {
                            if ((appChannel.equals(JIANGMENG_CHANNEL)
                                    && account.equals(SUPER_ADMIN_JM)
                                    && password.equals(SUPER_PASSWORD_JM))
                                    || (!appChannel.equals(JIANGMENG_CHANNEL)
                                    && account.equals(SUPER_ADMIN_DEFAULT)
                                    && password.equals(SUPER_PASSWORD_DEFAULT))) {
                                setNetWork();
                                return;
                            }
                        }
                        ApplicationsUtil.showMessage(WelcomeActivity.this, R.string.text_super_admin_password_error);
                    }
                }).build();
        if (dialog.getCustomView() != null) {
            accountEditText = (EditText) dialog.getCustomView().findViewById(R.id.ed_account);
            passwordEditText = (EditText) dialog.getCustomView().findViewById(R.id.et_password);
        }
        dialog.show();
    }

    private void setNetWork() {
        baseURI = TextUtil.getString(mWelcomePresenter.getBaseUri());
        brokeURL = TextUtil.getString(mWelcomePresenter.getBrokerUrl());
        reservedBaseURI = TextUtil.getString(mWelcomePresenter.getReservedBaseUri());
        reservedBrokeURL = TextUtil.getString(mWelcomePresenter.getReservedBrokerUrl());
        countlyUri = TextUtil.getString(mWelcomePresenter.getCountlyUri());
        boolean isUsingReservedAddress = mWelcomePresenter.isUsingReservedAddress();

        MaterialDialog dialog = new MaterialDialog.Builder(this)
                .title(R.string.text_setting_network)
                .customView(R.layout.dialog_setting_network, true)
                .positiveText(R.string.text_ok)
                .negativeText(android.R.string.cancel)
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        mWelcomePresenter.saveNetWorkSetting(baseUriTextView.getText().toString(),
                                reservedBaseUriTextView.getText().toString(),
                                brokeUrlTextView.getText().toString(),
                                reservedBrokeUrlTextView.getText().toString(),
                                countlyUriTextView.getText().toString(),
                                reservedAddressCheckBox.isChecked());
                    }
                }).build();
        if (dialog.getCustomView() != null) {
            baseUriTextView = (EditText) dialog.getCustomView().findViewById(R.id.et_data_address);
            brokeUrlTextView = (EditText) dialog.getCustomView().findViewById(R.id.et_push_address);
            reservedBaseUriTextView = (EditText) dialog.getCustomView().findViewById(R.id.et_reserved_data_address);
            reservedBrokeUrlTextView = (EditText) dialog.getCustomView().findViewById(R.id.et_reserved_push_address);
            countlyUriTextView = (EditText) dialog.getCustomView().findViewById(R.id.et_countly_address);
            reservedAddressCheckBox = (CheckBox) dialog.getCustomView().findViewById(R.id.cb_is_reserved_address);

            baseUriTextView.setText(baseURI);
            baseUriTextView.setSelection(baseURI.length());
            reservedBaseUriTextView.setText(reservedBaseURI);
            reservedBaseUriTextView.setSelection(reservedBaseURI.length());
            brokeUrlTextView.setText(brokeURL);
            brokeUrlTextView.setSelection(brokeURL.length());
            reservedBrokeUrlTextView.setText(reservedBrokeURL);
            reservedBrokeUrlTextView.setSelection(reservedBrokeURL.length());
            countlyUriTextView.setText(countlyUri);
            countlyUriTextView.setSelection(countlyUri.length());
            reservedAddressCheckBox.setChecked(isUsingReservedAddress);
        }
        dialog.show();
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
            checkGpsOpened();
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

    private void checkGpsOpened() {
        String appChannel = MainApplication.get(this).getAppChannel();
        if (!TextUtil.isNullOrEmpty(appChannel)
                && (appChannel.equalsIgnoreCase(JIANGMENG_CHANNEL)
                || appChannel.equalsIgnoreCase(WEIHAI_CHANNEL))) {
            mWelcomePresenter.init();
            return;
        }

        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        // 通过GPS卫星定位，定位级别可以精确到街（通过24颗卫星定位，在室外和空旷的地方定位准确、速度快）
        boolean gps = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        // 通过WLAN或移动网络(3G/2G)确定的位置（也称作AGPS，辅助GPS定位。主要用于在室内或遮盖物（建筑群或茂密的深林等）密集的地方定位）
        boolean network = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        if (gps || network) {
            mWelcomePresenter.init();
        } else {
            new MaterialDialog.Builder(this)
                    .title(R.string.text_prompt)
                    .content(R.string.text_open_gps_and_launch_again)
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
    }
}
