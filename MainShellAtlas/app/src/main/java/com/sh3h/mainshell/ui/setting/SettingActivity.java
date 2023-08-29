package com.sh3h.mainshell.ui.setting;

import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.mainshell.datautil.data.entity.DUConfigXmlFile;
import com.mainshell.datautil.data.local.config.ConfigHelper;
import com.mainshell.datautil.util.EventPosterHelper;
import com.mainshell.datautil.util.NetworkUtil;
import com.sh3h.mainshell.MainApplication;
import com.sh3h.mainshell.R;
import com.sh3h.mainshell.adapter.ListViewAdapter;
import com.sh3h.mainshell.event.UIBusEvent;
import com.sh3h.mainshell.event.countly.CountlyEvent;
import com.sh3h.mainshell.ui.base.NetworkDialog;
import com.sh3h.mainshell.ui.base.ParentActivity;
import com.sh3h.mainshell.ui.installation.ApkInstallationActivity;
import com.sh3h.mainshell.ui.welcome.WelcomeActivity;
import com.sh3h.mainshell.util.ConstDataUtil;
import com.sh3h.mainshell.util.SystemUtil;
import com.sh3h.mobileutil.util.ApplicationsUtil;
import com.sh3h.mobileutil.util.LogUtil;
import com.sh3h.mobileutil.util.TextUtil;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
//import ly.count.android.sdk.Countly;

import static com.mainshell.datautil.data.entity.DUConfigXmlFile.SHOWN_AS_SETTING;

//import com.sh3h.mainshell.event.umeng.UMengEvent;


/**
 * Created by xulongjun on 2016/5/23.
 */
public class SettingActivity extends ParentActivity implements SettingMvpView,
        View.OnClickListener, CompoundButton.OnCheckedChangeListener, AdapterView.OnItemClickListener {
    @Inject
    SettingPresenter mSettingPresenter;

    @Inject
    EventPosterHelper mEventPosterHelper;

    @Inject
    Bus mEventBus;

    @Inject
    NetworkDialog mNetworkDialog;

    // @BindView(R.id.fs_cv_apk_installed)
    CardView mApkInstallCardView;

    // @BindView(R.id.fs_cv_style)
    CardView mStyleCardView;

    // @BindView(R.id.fs_tv_style_r)
    TextView mStyleTextView;

    TextView mQualityTextView;

    TextView mUpdateTextView;

    // @BindView(R.id.fs_sw_style_r)
    Switch mStyleSwitch;

    Switch mQualitySwitch;

    Switch mUpdateSwitch;

    // @BindView(R.id.fs_cv_image_quality)
    CardView mQualityCardView;

    CardView mUpdateCardView;

    // @BindView(R.id.fs_cv_data_sync)
    CardView mSyncCardView;

    // @BindView(R.id.fs_cv_clearing_history)
    CardView mClearingCardView;

    // @BindView(R.id.fs_cv_restoring_factory)
    CardView mRestoringCardView;

    // @BindView(R.id.fs_cv_version)
    CardView mVersionCardView;

    // @BindView(R.id.fs_cv_keep_alive)
    CardView mKeepAliveCardView;

    // @BindView(R.id.fs_cv_network)
    CardView mNetworkCardView;

    // @BindView(R.id.fs_cv_gps)
    CardView mGpsCardView;

    CardView mMonitorCardView;

    CardView mExitSystemCardView;

    CardView mChangeUserCardView;

    // @BindView(R.id.fs_tv_version_r)
    TextView mVersionTextView;

    @BindView(R.id.listView_setting)
    ListView mListView;

    @BindView(R.id.toolbar_setting)
    Toolbar mToolbar;

    private static final String TAG = "SettingActivity";
    private static final String POSITIVE = "POSITIVE";
    private static final String NEGATIVE = "NEGATIVE";
    private static final int DEFAULT_INDEX = -1;

    private Unbinder mUnbinder;
    private int[] filterList;
    private int filterIndex;

    private int typeListIndex;
    /**
     * gps定位方式的数据
     */
    private String[] typeListValues;
    private String[] typeListkey;

    private int monitorListIndex;
    /**
     * 系统监控方式数据
     */
    private String[] monitorTypeValues;
    private String[] monitorTypeKey;

    private ListViewAdapter adapter;

    //list's headView
    private View mHeadView;


    public SettingActivity() {
        filterList = null;
        filterIndex = DEFAULT_INDEX;
        typeListIndex = DEFAULT_INDEX;
        monitorListIndex = DEFAULT_INDEX;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        getActivityComponent().inject(this);
        mSettingPresenter.attachView(this);
        mEventBus.register(this);
        mUnbinder = ButterKnife.bind(this);

        initTitle();
        initViews();
        LogUtil.i(TAG, "---onCreate---" + this);
    }

    private void initTitle() {
        mToolbar.setTitle(getResources().getString(R.string.title_setting));
        setSupportActionBar(mToolbar);
    }

    protected void initViews() {
        mHeadView = getLayoutInflater().inflate(R.layout.setting_head, null);
        //listView添加头部
        mListView.addHeaderView(mHeadView, null, true);

        findId();
        mSettingPresenter.init();
        getSettingData();
        getProperties();
        setVersionTextView();
        setOnClick();
    }

    private void findId() {
        mApkInstallCardView = (CardView) mHeadView.findViewById(R.id.fs_cv_apk_installed);
        mStyleCardView = (CardView) mHeadView.findViewById(R.id.fs_cv_style);
        mStyleTextView = (TextView) mHeadView.findViewById(R.id.fs_tv_style_r);
        mQualityTextView = (TextView) mHeadView.findViewById(R.id.fs_tv_quality_r);
        mUpdateTextView = (TextView) mHeadView.findViewById(R.id.fs_tv_update_r);
        mStyleSwitch = (Switch) mHeadView.findViewById(R.id.fs_sw_style_r);
        mQualityCardView = (CardView) mHeadView.findViewById(R.id.fs_cv_image_quality);
        mUpdateCardView = (CardView) mHeadView.findViewById(R.id.fs_cv_update);
        mQualitySwitch = (Switch) mHeadView.findViewById(R.id.fs_sw_quality_r);
        mUpdateSwitch = (Switch) mHeadView.findViewById(R.id.fs_sw_update_r);
        mSyncCardView = (CardView) mHeadView.findViewById(R.id.fs_cv_data_sync);
        mClearingCardView = (CardView) mHeadView.findViewById(R.id.fs_cv_clearing_history);
        mRestoringCardView = (CardView) mHeadView.findViewById(R.id.fs_cv_restoring_factory);
        mVersionCardView = (CardView) mHeadView.findViewById(R.id.fs_cv_version);
        mKeepAliveCardView = (CardView) mHeadView.findViewById(R.id.fs_cv_keep_alive);
        mNetworkCardView = (CardView) mHeadView.findViewById(R.id.fs_cv_network);
        mGpsCardView = (CardView) mHeadView.findViewById(R.id.fs_cv_gps);
        mVersionTextView = (TextView) mHeadView.findViewById(R.id.fs_tv_version_r);
        mMonitorCardView = (CardView) mHeadView.findViewById(R.id.fs_cv_monit);
        mExitSystemCardView = (CardView) mHeadView.findViewById(R.id.fs_cv_exit_system);
        mChangeUserCardView = (CardView) mHeadView.findViewById(R.id.fs_cv_change_user);
    }

    /**
     * 获取数据
     */
    private void getSettingData() {
        DUConfigXmlFile duConfigXmlFile = mSettingPresenter.getDuConfigXmlFile();
        List<DUConfigXmlFile.Component> components = new ArrayList<>();
        if ((duConfigXmlFile != null) && (duConfigXmlFile.getComponentList() != null)) {
            List<DUConfigXmlFile.Component> componentList = duConfigXmlFile.getComponentList();
            for (DUConfigXmlFile.Component component : componentList) {
                if (component.isValid()
                        && (!TextUtil.isNullOrEmpty(component.getShowAs()))
                        && component.getShowAs().equals(SHOWN_AS_SETTING)) {
                    components.add(component);
                }
            }
        }

        if (adapter != null) {
            adapter.setData(components);
            adapter.notifyDataSetChanged();
        } else {
            adapter = new ListViewAdapter(components, SettingActivity.this);
            mListView.setAdapter(adapter);
            mListView.setOnItemClickListener(this);
        }
    }

    /**
     * 获取配置信息
     */
    private void getProperties() {
        typeListkey = getResources().getStringArray(R.array.setting_gpstype_key);
        typeListValues = getResources().getStringArray(R.array.setting_gpstype_value);
        String gpsType = mSettingPresenter.getGpsType();
        for (int i = 0; i < typeListValues.length; i++) {
            if (typeListValues[i].equals(gpsType)) {
                typeListIndex = i;
                break;
            }
        }

        monitorTypeKey = getResources().getStringArray(R.array.setting_monitortype_key);
        monitorTypeValues = getResources().getStringArray(R.array.setting_monitortype_value);
        String monitorType = mSettingPresenter.getMonitorType();
        for (int i = 0; i < monitorTypeValues.length; i++) {
            if (monitorTypeValues[i].equals(monitorType)) {
                monitorListIndex = i;
                break;
            }
        }
    }

    /**
     * 设置版本信息
     */
    private void setVersionTextView() {
        String version = SystemUtil.getVersionName(MainApplication.get(this));
        mVersionTextView.setText(version);
    }

    private void setOnClick() {
        mApkInstallCardView.setOnClickListener(this);
        mStyleCardView.setOnClickListener(this);
        mStyleSwitch.setOnCheckedChangeListener(this);
        mQualitySwitch.setOnCheckedChangeListener(this);
        mUpdateSwitch.setOnCheckedChangeListener(this);
        mKeepAliveCardView.setOnClickListener(this);
        mQualityCardView.setOnClickListener(this);
        mUpdateCardView.setOnClickListener(this);
        mNetworkCardView.setOnClickListener(this);
        mVersionCardView.setOnClickListener(this);
        mClearingCardView.setOnClickListener(this);
        mRestoringCardView.setOnClickListener(this);
        mGpsCardView.setOnClickListener(this);
        mMonitorCardView.setOnClickListener(this);
        mExitSystemCardView.setOnClickListener(this);
        mChangeUserCardView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fs_cv_apk_installed:
                Intent intent = new Intent(this, ApkInstallationActivity.class);
                startActivity(intent);
                break;
            case R.id.fs_cv_style:
                updateStyle(!mStyleSwitch.isChecked(), true, true);
                break;
            case R.id.fs_cv_image_quality:
                updateQuality(!mQualitySwitch.isChecked(), true, true);
                break;
            case R.id.fs_cv_update:
                updateVersion(!mUpdateSwitch.isChecked(), true, true);
                break;
            case R.id.fs_cv_network:
                setNetWork();
                break;
            case R.id.fs_cv_keep_alive:
                keepAliveIntervalSetting();
                break;
            case R.id.fs_cv_clearing_history:
                clearHistory();
                break;
            case R.id.fs_cv_restoring_factory:
                restoreFactory();
                break;
            case R.id.fs_cv_version:
                checkVersion();
                break;
            case R.id.fs_cv_gps:
                setGpsType();
                break;
            case R.id.fs_cv_monit:
                setMonitorType();
                break;
            case R.id.fs_cv_exit_system:
                //exitSystem();
                break;
            case R.id.fs_cv_change_user:
                switchUser();
                break;
            default:
                break;
        }
    }

    private void setNetWork() {
        mNetworkDialog.show(this, new NetworkDialog.OnSaveListener() {
            @Override
            public void onSave(boolean b) {
                if (b) {
                    ApplicationsUtil.showMessage(SettingActivity.this,
                            R.string.text_quit_and_login);
                }
            }
        });
    }

    private void clearHistory() {
        new MaterialDialog.Builder(this)
                .title(R.string.text_prompt)
                .content(R.string.text_clearing_all)
                .positiveText(R.string.text_ok)
                .negativeText(R.string.text_cancel)
                .onAny(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        Log.i(TAG, which.name());
                        if (which.name().equals(POSITIVE)) {
                            mSettingPresenter.clearHistory();
                        }
                    }
                })
                .show();
    }

    private void restoreFactory() {
        new MaterialDialog.Builder(this)
                .title(R.string.text_prompt)
                .content(R.string.text_restoring_all)
                .positiveText(R.string.text_ok)
                .negativeText(R.string.text_cancel)
                .onAny(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        Log.i(TAG, which.name());
                        if (which.name().equals(POSITIVE)) {
                            mSettingPresenter.restoreFactory();
                        }
                    }
                })
                .show();
    }

    private void checkVersion() {
        new MaterialDialog.Builder(this)
                .title(R.string.text_prompt)
                .content(R.string.text_checking_version)
                .positiveText(R.string.text_ok)
                .negativeText(R.string.text_cancel)
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        LogUtil.i(TAG, "---checkVersion---ok");
                        updateFileList(mSettingPresenter.getDuUpdateXmlFile());
//                        Activity activity = getActivity();
//                        if (activity instanceof MainActivity) {
//                            ((MainActivity) activity).checkVersion();
//                        }
                    }
                })
                .show();
    }

//    private void exitSystem() {
//        new MaterialDialog.Builder(this)
//                .title(R.string.text_prompt)
//                .content(R.string.text_if_exit_system)
//                .positiveText(R.string.text_ok)
//                .negativeText(R.string.text_cancel)
//                .onPositive(new MaterialDialog.SingleButtonCallback() {
//                    @Override
//                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
//                        LogUtil.i(TAG, "---exitSystem---ok");
//
//                        finish();
//                        mEventPosterHelper.postEventSafely(new UIBusEvent.ExitingSystem());
//                    }
//                })
//                .show();
//    }

    private void keepAliveIntervalSetting() {
        if ((filterIndex == DEFAULT_INDEX)
                || (filterList == null)
                || (filterList.length <= 0)) {
            return;
        }

        List<String> contents = new ArrayList<>();
        for (int i = 0; i < filterList.length; i++) {
            int value = filterList[i] / 1000;
            String str;
            if (value <= 60) {
                str = String.format(Locale.CHINESE, "%d%s",
                        value, getResources().getString(R.string.text_second));
            } else {
                value /= 60;
                str = String.format(Locale.CHINESE, "%d%s",
                        value, getResources().getString(R.string.text_minute));
            }
            contents.add(str);
        }

        Integer[] disabledIndices = new Integer[filterList.length];
        for (int i = 0; i < filterList.length; i++) {
            disabledIndices[i] = i;
        }

        new MaterialDialog.Builder(this)
                .title(R.string.text_setting_keep_alive)
                .items(contents)
                .itemsDisabledIndices(disabledIndices)
                .itemsCallbackSingleChoice(filterIndex,
                        new MaterialDialog.ListCallbackSingleChoice() {
                            @Override
                            public boolean onSelection(MaterialDialog dialog, View view,
                                                       int which, CharSequence text) {
                                if ((which >= 0) && (which < filterList.length)) {
                                    filterIndex = which;
                                    mSettingPresenter.saveKeepAliveInterval(filterList[which]);
                                }
                                return true;
                            }
                        })
                .positiveText(R.string.text_ok)
                .negativeText(R.string.text_cancel)
                .show();
    }

    /**
     * 设置定位方式
     */
    private void setGpsType() {
        if ((typeListkey == null) || (typeListkey.length <= 0)) {
            return;
        }

        Integer[] disabledIndices = new Integer[typeListkey.length];
        for (int i = 0; i < typeListkey.length; i++) {
            disabledIndices[i] = i;
        }

        new MaterialDialog.Builder(this)
                .title(R.string.text_setting_gps_type)
                .items(typeListkey)
                .itemsDisabledIndices(disabledIndices)
                .itemsCallbackSingleChoice(typeListIndex, new MaterialDialog.ListCallbackSingleChoice() {
                    @Override
                    public boolean onSelection(MaterialDialog materialDialog, View view, int which, CharSequence text) {
                        if ((which >= 0) && (which < typeListkey.length) && (typeListIndex != which)) {
                            typeListIndex = which;
                            mSettingPresenter.saveGpsType(typeListValues[which]);
                        }
                        return true;
                    }
                })
                .positiveText(R.string.text_ok)
                .negativeText(R.string.text_cancel)
                .show();
    }

    /**
     * 设置监控方式
     */
    private void setMonitorType() {
        if ((monitorTypeKey == null) || (monitorTypeKey.length <= 0)) {
            return;
        }

        Integer[] disabledIndices = new Integer[monitorTypeKey.length];
        for (int i = 0; i < monitorTypeKey.length; i++) {
            disabledIndices[i] = i;
        }

        new MaterialDialog.Builder(this)
                .title(R.string.text_setting_monitor_type)
                .items(monitorTypeKey)
                .itemsDisabledIndices(disabledIndices)
                .itemsCallbackSingleChoice(monitorListIndex, new MaterialDialog.ListCallbackSingleChoice() {
                    @Override
                    public boolean onSelection(MaterialDialog materialDialog, View view, int which, CharSequence text) {
                        if ((which >= 0)
                                && (which < monitorTypeKey.length)
                                && (monitorListIndex != which)) {
                            monitorListIndex = which;
                            mSettingPresenter.saveMonitorType(monitorTypeValues[which]);
                        }
                        return true;
                    }
                })
                .positiveText(R.string.text_ok)
                .negativeText(R.string.text_cancel)
                .show();
    }

    private void switchUser() {
        new MaterialDialog.Builder(this)
                .title(R.string.text_prompt)
                .content(R.string.text_if_change_user)
                .positiveText(R.string.text_ok)
                .negativeText(R.string.text_cancel)
                .onAny(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog,
                                        @NonNull DialogAction which) {
                        Log.i(TAG, which.name());
                        if (which.name().equals(POSITIVE)) {
                            mSettingPresenter.switchUser();
                        }
                    }
                })
                .show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        mEventBus.unregister(this);
        mUnbinder.unbind();
        mSettingPresenter.detachView();
        LogUtil.i(TAG, "---onDestroy---");
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void initStyle(boolean isGrid) {
        updateStyle(isGrid, true, false);
    }

    @Override
    public void initImageQuality(boolean isQuality) {
        updateQuality(isQuality, true, false);
    }

    @Override
    public void initUpdateVersion(boolean isUpdateVersion) {
        updateVersion(isUpdateVersion, true, false);
    }

    @Override
    public void onError(String message) {
    }

    @Override
    public void initKeepAliveInterval(int keepAliveInterval) {
        if ((filterList == null) || (filterList.length <= 0)) {
            filterList = getResources().getIntArray(R.array.keep_alive_items);
        }

        filterIndex = DEFAULT_INDEX;
        if (filterList != null) {
            for (int i = 0; i < filterList.length; i++) {
                if (filterList[i] == keepAliveInterval) {
                    filterIndex = i;
                    break;
                }
            }
        }
    }

    @Override
    public void showMessage(String message) {
        if (!TextUtil.isNullOrEmpty(message)) {
            ApplicationsUtil.showMessage(this, message);
        }
    }

    @Override
    public void showMessage(int message) {
        ApplicationsUtil.showMessage(this, message);
    }

    @Override
    public void exitSystem() {
        mEventPosterHelper.postEventSafely(new UIBusEvent.ExitingSystem());
        finish();
    }

    @Override
    public void onSaveStyle() {
        mEventPosterHelper.postEventSafely(new UIBusEvent.GridStyle());
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()) {
            case R.id.fs_sw_style_r:
                updateStyle(isChecked, false, true);
                break;
            case R.id.fs_sw_quality_r:
                updateQuality(isChecked, false, true);
                break;
            case R.id.fs_sw_update_r:
                updateVersion(isChecked, false, true);
                break;
        }
    }

    @Override
    public void destroyMonitor() {
        MainApplication.get(this).destroyMonitor();
    }

    private void updateStyle(boolean isGrid, boolean isChangingSwitch, boolean needSaving) {
        if (isGrid) {
            mStyleTextView.setText(R.string.text_system_style_grid);
            if (isChangingSwitch) {
                mStyleSwitch.setChecked(true);
            }
        } else {
            mStyleTextView.setText(R.string.text_system_style_list);
            if (isChangingSwitch) {
                mStyleSwitch.setChecked(false);
            }
        }
//      mEventPosterHelper.postEventSafely(new UIBusEvent.ChangeDisplayStyle(isGrid));

        if (needSaving) {
            mSettingPresenter.saveStyle(isGrid);
        }
    }

    private void updateQuality(boolean isNormal, boolean isChangingSwitch, boolean needSaving) {
        if (isNormal) {
            mQualityTextView.setText(R.string.text_image_quality_normal);
            if (isChangingSwitch) {
                mQualitySwitch.setChecked(true);
            }
        } else {
            mQualityTextView.setText(R.string.text_image_quality_high);
            if (isChangingSwitch) {
                mQualitySwitch.setChecked(false);
            }
        }

        if (needSaving) {
            mSettingPresenter.saveQuality(isNormal);
        }
    }

    private void updateVersion(boolean isChecked, boolean isChangingSwitch, boolean needSaving) {
        if (isChecked) {
            mUpdateTextView.setText(R.string.text_setting_automatic);
            if (isChangingSwitch) {
                mUpdateSwitch.setChecked(true);
            }
        } else {
            mUpdateTextView.setText(R.string.text_system_byhand);
            if (isChangingSwitch) {
                mUpdateSwitch.setChecked(false);
            }
        }

        if (needSaving) {
            mSettingPresenter.saveUpdateVersion(isChecked);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        //listView　某一项点击事件
       /* DUConfigXmlFile.Component component= (DUConfigXmlFile.Component) parent.getAdapter().getItem(position-1);*/
        try {
            DUConfigXmlFile.Component component = adapter.getItem(position - 1);
            Intent intent = new Intent(Intent.ACTION_VIEW);
            ComponentName cn = new ComponentName(component.getPackageName(), component.getActivity());
            intent.setComponent(cn);
            startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
            LogUtil.i(TAG, e.getMessage());
        }
    }

    @Subscribe
    public void onApkStatus(UIBusEvent.ApkStatus apkStatus) {
        LogUtil.i(TAG, "---onApkStatus---");
        getSettingData();
    }
}
