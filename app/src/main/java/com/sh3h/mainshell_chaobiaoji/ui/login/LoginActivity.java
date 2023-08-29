package com.sh3h.mainshell_chaobiaoji.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.sh3h.datautil.data.local.config.SystemConfig;
import com.sh3h.datautil.util.NetworkUtil;
import com.sh3h.mainshell_chaobiaoji.MainApplication;
import com.sh3h.mainshell_chaobiaoji.R;
//import com.sh3h.mainshell.event.countly.CountlyEvent;
import com.sh3h.mainshell_chaobiaoji.ui.base.BaseActivity;
import com.sh3h.mainshell_chaobiaoji.ui.main.MainActivity;
import com.sh3h.mainshell_chaobiaoji.util.ConstDataUtil;
import com.sh3h.mainshell_chaobiaoji.util.DeviceUtil;
import com.sh3h.mainshell_chaobiaoji.util.GuideHelper;
import com.sh3h.mainshell_chaobiaoji.util.SystemUtil;
import com.sh3h.mobileutil.util.ApplicationsUtil;
import com.sh3h.mobileutil.util.TextUtil;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static com.sh3h.mainshell_chaobiaoji.AppChannel.JIANGMENG_CHANNEL;
import static com.sh3h.mainshell_chaobiaoji.AppChannel.WEIHAI_CHANNEL;
import static com.sh3h.mainshell_chaobiaoji.MainApplication.SUPER_ADMIN_DEFAULT;
import static com.sh3h.mainshell_chaobiaoji.MainApplication.SUPER_ADMIN_JM;
import static com.sh3h.mainshell_chaobiaoji.MainApplication.SUPER_PASSWORD_DEFAULT;
import static com.sh3h.mainshell_chaobiaoji.MainApplication.SUPER_PASSWORD_JM;
//import ly.count.android.sdk.Countly;

/**
 * Created by dengzhimin on 2016/5/18.
 */
public class LoginActivity extends BaseActivity implements LoginMvpView {
    @Inject
    LoginPresenter mLoginPresenter;

    @BindView(R.id.et_username)
    EditText mUserNameEditText;

    @BindView(R.id.et_password)
    EditText mPasswordEditText;

    @BindView(R.id.btn_submit)
    Button mSubmitButton;

    @BindView(R.id.tv_version)
    TextView mVersionTextView;

    @BindView(R.id.txt_setting)
    TextView mTxtSetting;

    private static final String TAG = "LoginActivity";
    private Unbinder unbind;

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

    private boolean isExit;
    private boolean isFirstLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (MainApplication.get(this).isGreenTheme()) {
            setContentView(R.layout.activity_login_green);
        } else {
            setContentView(R.layout.activity_login);
        }

        initDependency();
        initViewData();
        isExit = false;
        mLoginPresenter.isFirstLogin();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        unbind.unbind();
        mLoginPresenter.detachView();
        if (isExit) {
            System.exit(0);
        }
    }

    /**
     * 初始化依赖
     */
    private void initDependency() {
        unbind = ButterKnife.bind(this);
        getActivityComponent().inject(this);
        mLoginPresenter.attachView(this);

        String appChannel = MainApplication.get(this).getAppChannel();
        if (!TextUtil.isNullOrEmpty(appChannel)
                && appChannel.equalsIgnoreCase(WEIHAI_CHANNEL)) {
            mTxtSetting.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 初始化View数据
     */
    private void initViewData() {
        String version = SystemUtil.getVersionName(MainApplication.get(this));
        mVersionTextView.setText(String.format("Version: %s", version));
        mLoginPresenter.initLoginData();
    }

    @Override
    public void loginSuccess(ErrorCode errorCode) {
        hideProgress();

        boolean isSwitchingUser = false;
        switch (errorCode) {
            case NETWORK_LOGIN_SUCCESS:
                ApplicationsUtil.showMessage(this, R.string.network_login_success);
                isSwitchingUser = true;
                break;
            case NOT_NETWORK_LOGIN_SUCCESS:
                ApplicationsUtil.showMessage(this, R.string.not_network_login_success);
                isSwitchingUser = false;
                break;
            default:
                return;
        }

        setUserData();
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra(ConstDataUtil.IS_FROM_LAUNCHER, true);
        intent.putExtra(ConstDataUtil.IS_FIRST_LOGIN, isFirstLogin);
        intent.putExtra(ConstDataUtil.IS_SWITCHING_USER, isSwitchingUser);
        startActivity(intent);
        finish();
    }

    private void setUserData() {
        if (MainApplication.get(this).isMonitorInit()) {
            if (MainApplication.get(this).getMonitorType() == SystemConfig.MonitorType.COUTLY) {
                Map<String, String> map = new HashMap<>();
                map.put("account", mUserNameEditText.getText().toString());
                map.put("password", mPasswordEditText.getText().toString());
                //Countly.sharedInstance().userData.setCustomUserData(map);
                //Countly.sharedInstance().userData.save();
            } else if (MainApplication.get(this).getMonitorType() == SystemConfig.MonitorType.UMENG) {
                //MobclickAgent.onProfileSignIn(mUserNameEditText.getText().toString());
            }
        }
    }

    @Override
    public void loginFailed(ErrorCode errorCode) {
        hideProgress();
        switch (errorCode) {
            case FIRST_TIME_MUST_NETWORK_LOGIN:
                ApplicationsUtil.showMessage(this, R.string.first_time_must_network_login);
                break;
            case NETWORK_LOGIN_FAILED:
                ApplicationsUtil.showMessage(this, R.string.network_login_failed);
                break;
            case NOT_NETWORK_ACCOUNT_OR_PASSWORD_ERROR:
                ApplicationsUtil.showMessage(this, R.string.not_network_account_or_password_error);
                break;
            case NOT_NETWORK:
                ApplicationsUtil.showMessage(this, R.string.text_network_not_connected);
                break;
        }
    }

    @Override
    public void onError(String message) {
        hideProgress();
        if (!TextUtil.isNullOrEmpty(message)) {
            ApplicationsUtil.showMessage(this, message);
        }
    }

    @Override
    public void isFirstLogin(boolean flag) {
        //是第一次登录,主界面中对应新手引导必须和这里的新手引导是一套
        isFirstLogin = flag;

        if (flag) {
            //是第一次登录
            GuideHelper guideHelper = new GuideHelper(LoginActivity.this);
            GuideHelper.TipData tipData1 = new GuideHelper.TipData(R.drawable.login1, mPasswordEditText);
            guideHelper.addPage(tipData1);

            GuideHelper.TipData tipData2 = new GuideHelper.TipData(R.drawable.login2, Gravity.LEFT | Gravity.TOP, mTxtSetting);
            guideHelper.addPage(tipData2);

            guideHelper.show(false);
        }
    }

    @Override
    public void loginFailed(String error) {
        ApplicationsUtil.showMessage(this, error);
    }

    @Override
    public void initEditTextData(String account, String password) {
        if (!TextUtils.isEmpty(account)) {
            mUserNameEditText.setText(account);
            mUserNameEditText.setSelection(account.length());
        }

        if (!TextUtils.isEmpty(password)) {
            mPasswordEditText.setText(password);
            mPasswordEditText.setSelection(password.length());
        }
    }

    @Override
    public void saveNetWorkResult(ErrorCode errorCode, String msg) {
        if (errorCode == null) {
            ApplicationsUtil.showMessage(this, msg);
            return;
        }

        switch (errorCode) {
            case SAVE_NETWORK_FAILED:
                ApplicationsUtil.showMessage(this, R.string.text_saving_failure);
                break;
            case SAVE_NETWORK_SUCCESS:
                ApplicationsUtil.showMessage(this, R.string.text_saving_success);
                isExit = true;
                finish();
                break;
        }
    }

    @OnClick(R.id.txt_setting)
    public void netWorkSetting(View view) {
        createNetWorkDialog();
    }

    @OnClick(R.id.btn_submit)
    public void login(Button button) {
        String account = mUserNameEditText.getText().toString().trim();
        String password = mPasswordEditText.getText().toString().trim();

        if (MainApplication.get(this).isMonitorInit()) {
            if (MainApplication.get(this).getMonitorType() == SystemConfig.MonitorType.COUTLY) {
                //Countly.sharedInstance().recordEvent(CountlyEvent.LOGIN, 1);
            } else if (MainApplication.get(this).getMonitorType() == SystemConfig.MonitorType.UMENG) {
                /*
                Map<String ,String> data = new HashMap<>();
                data.put("name", "login");
                MobclickAgent.onEvent(this, UMengEvent.CLICK, data);
                */
            }
        }

        if (TextUtils.isEmpty(account)) {
            ApplicationsUtil.showMessage(this, R.string.text_username_not_null);
            return;
        }

        if (TextUtils.isEmpty(password)) {
            ApplicationsUtil.showMessage(this, R.string.text_password_not_null);
            return;
        }

        String appChannel = MainApplication.get(this).getAppChannel();
        if (!TextUtil.isNullOrEmpty(appChannel)) {
            if ((appChannel.equals(JIANGMENG_CHANNEL)
                    && account.equals(SUPER_ADMIN_JM)
                    && password.equals(SUPER_PASSWORD_JM))
                    || (!appChannel.equals(JIANGMENG_CHANNEL)
                    && account.equals(SUPER_ADMIN_DEFAULT)
                    && password.equals(SUPER_PASSWORD_DEFAULT))) {
                createNetWorkDialog();
                return;
            }
        }

        showProgress(R.string.dialog_login);
        //PackageInfo info = SystemUtil.getPackageInfo(getBaseContext());
        String appIdentify = getPackageName();//info == null ? "com.sh3h.mainshell" : info.packageName;
        //String appIdentify = DeviceUtil.getDeviceID(this);
//        if (TextUtil.isNullOrEmpty(appIdentify)) {
//            appIdentify = getPackageName();
//        }
        mLoginPresenter.login(account, password, appIdentify,
                NetworkUtil.isNetworkConnected(MainApplication.get(this)),
                DeviceUtil.getDeviceID(MainApplication.get(this)),
                MainApplication.get(this).getAppChannel());
    }

    private void createNetWorkDialog() {
        baseURI = TextUtil.getString(mLoginPresenter.getBaseUri());
        brokeURL = TextUtil.getString(mLoginPresenter.getBrokerUrl());
        reservedBaseURI = TextUtil.getString(mLoginPresenter.getReservedBaseUri());
        reservedBrokeURL = TextUtil.getString(mLoginPresenter.getReservedBrokerUrl());
        countlyUri = TextUtil.getString(mLoginPresenter.getCountlyUri());
        boolean isUsingReservedAddress = mLoginPresenter.isUsingReservedAddress();

        MaterialDialog dialog = new MaterialDialog.Builder(this)
                .title(R.string.text_setting_network)
                .customView(R.layout.dialog_setting_network, true)
                .positiveText(R.string.text_ok)
                .negativeText(R.string.text_cancel)
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        mLoginPresenter.saveNetwork(baseUriTextView.getText().toString(),
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
    protected void onResume() {
        super.onResume();
        //MobclickAgent.onPageStart(mPageName);
    }

    @Override
    protected void onPause() {
        super.onPause();
        //MobclickAgent.onPageEnd(mPageName);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_DOWN) {
            isExit = true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
