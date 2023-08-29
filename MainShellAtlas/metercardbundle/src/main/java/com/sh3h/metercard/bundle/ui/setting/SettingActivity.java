package com.sh3h.metercard.bundle.ui.setting;

import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import com.sh3h.metercard.bundle.R;
import com.sh3h.metercard.bundle.R2;
import com.sh3h.metercard.bundle.ui.base.ParentActivity;
import com.sh3h.metercard.bundle.util.ConstDataUtil;
import com.sh3h.mobileutil.util.ApplicationsUtil;
import com.sh3h.mobileutil.util.TextUtil;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


public class SettingActivity extends ParentActivity implements SettingMvpView,
        View.OnClickListener, CompoundButton.OnCheckedChangeListener {

    private final static String TAG = "SettingActivity";
    private Unbinder mUnbinder;

    @Inject
    SettingPresenter mSettingPresenter;

    //常用抄表
    @BindView(R2.id.setting_cv_chaobiao_always)
    CardView mAlwaysChaoBiao;

    //抄表期限
    @BindView(R2.id.setting_cv_chaobiao_limit)
    CardView mChaoBiaoLimit;

    //显示“左手”或“右手”
    @BindView(R2.id.setting_tv_leftorright)
    TextView mTextHandStyle;

    //switch（左右手选择）
    @BindView(R2.id.setting_sw_leftorright)
    Switch mChooseHandStyle;

    //上传日志
    @BindView(R2.id.setting_cv_upload_log)
    CardView mUploadLog;

    //显示版本信息text
    @BindView(R2.id.setting_tv_version_info)
    TextView mVersionInfoText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        getActivityComponent().inject(this);
        mUnbinder = ButterKnife.bind(this);
        initToolBar(R.string.label_system_setting);
        mSettingPresenter.attachView(this);
        //initView();
        //toTop();
    }

    /**
     * 初始化控件
     */
    private void initView() {
        if (mAlwaysChaoBiao == null) {
            mAlwaysChaoBiao = (CardView)findViewById(R.id.setting_cv_chaobiao_always);
        }

        if (mChaoBiaoLimit == null) {
            mChaoBiaoLimit = (CardView)findViewById(R.id.setting_cv_chaobiao_limit);
        }

        if (mTextHandStyle == null) {
            mTextHandStyle = (TextView) findViewById(R.id.setting_tv_leftorright);
        }

        if (mChooseHandStyle == null) {
            mChooseHandStyle = (Switch)findViewById(R.id.setting_sw_leftorright);
        }

        if (mUploadLog == null) {
            mUploadLog = (CardView)findViewById(R.id.setting_cv_upload_log);
        }

        if (mVersionInfoText == null) {
            mVersionInfoText = (TextView)findViewById(R.id.setting_tv_version_info);
        }

        mAlwaysChaoBiao.setOnClickListener(this);
        mChaoBiaoLimit.setOnClickListener(this);
        mChooseHandStyle.setOnCheckedChangeListener(this);
        mUploadLog.setOnClickListener(this);
        initHandStyle();
        initVersionInfo();
    }

    /**
     * 初始化左右手操作
     */
    private void initHandStyle() {
        mChooseHandStyle.setChecked(true);
        mTextHandStyle.setText(R.string.text_setting_left_hand);
    }

    /**
     * 初始化版本信息
     */
    private void initVersionInfo() {
        mSettingPresenter.getVersionInfo(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mUnbinder.unbind();
        mSettingPresenter.detachView();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R2.id.setting_cv_chaobiao_always:
                ApplicationsUtil.showMessage(this, "常用抄表");
                break;
            case R2.id.setting_cv_chaobiao_limit:
                showReadPeriod();
                break;
            case R2.id.setting_cv_upload_log:
                showIsUploadDialog();
                break;
        }
    }

    /**
     * 显示抄表期限(dialog)
     */
    private void showReadPeriod() {
        String startDate = mSettingPresenter.getReadingDate(true);
        if (TextUtil.isNullOrEmpty(startDate)) {
            startDate = "-";
        }

        String endDate = mSettingPresenter.getReadingDate(false);
        if (TextUtil.isNullOrEmpty(endDate)) {
            endDate = "-";
        }

        String period = String.format(ConstDataUtil.LOCALE, "%s: %s%s%s",
                getResources().getString(R.string.text_setting_chaobiao_limit),
                startDate,
                getResources().getString(R.string.text_to),
                endDate);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.text_prompt);
        builder.setMessage(period);
        builder.setPositiveButton(R.string.text_ok, null);
        builder.setNegativeButton(R.string.text_cancel, null);
        builder.show();
    }


    /**
     * 是否上传日志
     */
    private void showIsUploadDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.text_prompt);
        builder.setMessage(R.string.text_is_upload_log);
        //TODO 上传日志操作监听
        builder.setPositiveButton(R.string.text_ok, null);
        builder.setNegativeButton(R.string.text_cancel, null);
        builder.show();
    }


    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()) {
            case R2.id.setting_sw_leftorright:
                updateHandStyle(isChecked, false, true);
                break;
        }
    }

    /**
     * 更新左右手操作
     */
    private void updateHandStyle(boolean isChecked, boolean isChangingSwitch, boolean needSaving) {
        if (isChecked) {
            mTextHandStyle.setText(R.string.text_setting_left_hand);
            if (isChangingSwitch) {
                mChooseHandStyle.setChecked(true);
            }
        } else {
            mTextHandStyle.setText(R.string.text_setting_right_hand);
            if (isChangingSwitch) {
                mChooseHandStyle.setChecked(false);
            }
        }
    }

    @Override
    public void onGetVersionInfo(String version) {
        mVersionInfoText.setText(version);
    }
}
