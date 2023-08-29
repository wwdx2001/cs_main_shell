package com.sh3h.mainshell_chaobiaoji.ui.setting;

import android.content.ComponentName;
import android.content.Intent;
import android.os.Build;
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
import com.sh3h.datautil.data.entity.DUConfigXmlFile;
import com.sh3h.datautil.util.EventPosterHelper;
import com.sh3h.mainshell_chaobiaoji.MainApplication;
import com.sh3h.mainshell_chaobiaoji.R;
import com.sh3h.mainshell_chaobiaoji.adapter.ListViewAdapter;
import com.sh3h.mainshell_chaobiaoji.event.UIBusEvent;
//import com.sh3h.mainshell.event.countly.CountlyEvent;
import com.sh3h.mainshell_chaobiaoji.ui.base.ParentActivity;
import com.sh3h.mainshell_chaobiaoji.ui.installation.ApkInstallationActivity;
import com.sh3h.mainshell_chaobiaoji.util.SystemUtil;
import com.sh3h.mobileutil.util.ApplicationsUtil;
import com.sh3h.mobileutil.util.LogUtil;
import com.sh3h.mobileutil.util.TextUtil;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.sh3h.datautil.data.entity.DUConfigXmlFile.SHOWN_AS_SETTING;
import static com.sh3h.mainshell_chaobiaoji.AppChannel.JIANGMENG_CHANNEL;
import static com.sh3h.mainshell_chaobiaoji.AppChannel.WEIHAI_CHANNEL;
import static com.sh3h.mainshell_chaobiaoji.MainApplication.SUPER_ADMIN_DEFAULT;
import static com.sh3h.mainshell_chaobiaoji.MainApplication.SUPER_ADMIN_JM;
import static com.sh3h.mainshell_chaobiaoji.MainApplication.SUPER_PASSWORD_DEFAULT;
import static com.sh3h.mainshell_chaobiaoji.MainApplication.SUPER_PASSWORD_JM;

/**
 * Created by xulongjun on 2016/5/23.
 */
public class SettingActivity extends ParentActivity implements SettingMvpView,
        View.OnClickListener, CompoundButton.OnCheckedChangeListener, AdapterView.OnItemClickListener {

    private static final String TAG = "SettingActivity";
    private static final String POSITIVE = "POSITIVE";
    private static final String NEGATIVE = "NEGATIVE";
    private static final int DEFAULT_INDEX = -1;

    private static final String PARAMS = "params";
    private static final String ACCOUNT = "account";
    private static final String PASSWORD = "password";
    //向子程序发送用户id
    private static final String USER_ID = "userId";
    //向子程序发送用户名
    private static final String USER_NAME = "userName";
    private static final String DEPARTMENT = "department";
    private static final String DEPARTMENT_ID = "departmentId";
    private static final String ROLES = "roles";
    private static final String ACCESS_TOKEN = "accessToken";
    private static final String EXTENDED_INFO = "extendedInfo";
    private static final String NEW_TASK_ACTIVITY = "newTaskActivity";
    private static final String PHOTO_QUALITY = "photoQuality";
    private static final String NETWORK = "network";

    @Inject
    SettingPresenter mSettingPresenter;

    @Inject
    EventPosterHelper mEventPosterHelper;

    @Inject
    Bus mEventBus;

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

    //list's headView
    private View mHeadView;

    //恢复厂设置，承载安装的apk信息
    //private List<String> packageNames;
    //恢复厂还是切换用户
    //private boolean restoryFactoryOrChangeUser;

    public SettingActivity() {
        filterList = null;
        filterIndex = DEFAULT_INDEX;
        typeListIndex = DEFAULT_INDEX;
        monitorListIndex = DEFAULT_INDEX;
        //restoryFactoryOrChangeUser = false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        getActivityComponent().inject(this);
        mSettingPresenter.attachView(this);
        mEventBus.register(this);
        mUnbinder = ButterKnife.bind(this);

        Intent intent = getIntent();
        initTitle();
        initViews();
        LogUtil.i(TAG, "---onCreate---" + this);
    }

    private void initTitle() {
        mToolbar.setTitle(getResources().getString(R.string.title_setting));
        setSupportActionBar(mToolbar);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mToolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
        } else {
            mToolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_arrow_back_white_24dp));
        }
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
        //Map<String, String> data = new HashMap<>();
        switch (v.getId()) {
            case R.id.fs_cv_apk_installed:
                Intent intent = new Intent(this, ApkInstallationActivity.class);
                startActivity(intent);
                //data.put("Operate", CountlyEvent.Operate.INTENT);
                //data.put("To", "ApkInstallationActivity");
                break;
            case R.id.fs_cv_style:
                updateStyle(!mStyleSwitch.isChecked(), true, true);
                //data.put("Operate", CountlyEvent.Operate.SWITCH);
                break;
            case R.id.fs_cv_image_quality:
                updateQuality(!mQualitySwitch.isChecked(), true, true);
                //data.put("Operate", CountlyEvent.Operate.SWITCH);
                break;
            case R.id.fs_cv_update:
                updateVersion(!mUpdateSwitch.isChecked(), true, true);
                //data.put("Operate", CountlyEvent.Operate.SWITCH);
                break;
            case R.id.fs_cv_network:
                popupAuthDialog();
                //data.put("Operate", CountlyEvent.Operate.DIALOG);
                break;
            case R.id.fs_cv_keep_alive:
                keepAliveIntervalSetting();
                //data.put("Operate", CountlyEvent.Operate.DIALOG);
                break;
            case R.id.fs_cv_clearing_history:
                clearHistory();
                //data.put("Operate", CountlyEvent.Operate.DIALOG);
                break;
            case R.id.fs_cv_restoring_factory:
                //restoryFactoryOrChangeUser = true;
                restoreFactory();
                //data.put("Operate", CountlyEvent.Operate.DIALOG);
                break;
            case R.id.fs_cv_version:
                checkVersion();
                //data.put("Operate", CountlyEvent.Operate.DIALOG);
                break;
            case R.id.fs_cv_gps:
                setGpsType();
                //data.put("Operate", CountlyEvent.Operate.DIALOG);
                break;
            case R.id.fs_cv_monit:
                setMonitorType();
                //data.put("Operate", CountlyEvent.Operate.DIALOG);
                break;
            case R.id.fs_cv_exit_system:
                //exitSystem();
                //data.put("Operate", CountlyEvent.Operate.DIALOG);
                break;
            case R.id.fs_cv_change_user:
                //restoryFactoryOrChangeUser = false;
                switchUser();
                //data.put("Operate", CountlyEvent.Operate.DIALOG);
                break;
            default:
                break;
        }

        if (MainApplication.get(this).isMonitorInit()) {
            switch (MainApplication.get(this).getMonitorType()) {
                case NONE:
                    break;
                case COUTLY:
                    //Countly.sharedInstance().recordEvent(CountlyEvent.CLICK, data, 1);
                    break;
                case UMENG:
                    //MobclickAgent.onEvent(this, UMengEvent.CLICK, data);
                    break;
                default:
                    break;
            }
        }
    }

    private void popupAuthDialog() {
        final String appChannel = MainApplication.get(this).getAppChannel();
        if (!TextUtil.isNullOrEmpty(appChannel) && appChannel.equals(WEIHAI_CHANNEL)) {
            setNetWork();
            return;
        }

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
                        ApplicationsUtil.showMessage(SettingActivity.this, R.string.text_super_admin_password_error);
                    }
                }).build();
        if (dialog.getCustomView() != null) {
            accountEditText = (EditText) dialog.getCustomView().findViewById(R.id.ed_account);
            passwordEditText = (EditText) dialog.getCustomView().findViewById(R.id.et_password);
        }
        dialog.show();
    }

    private void setNetWork() {
        baseURI = TextUtil.getString(mSettingPresenter.getBaseUri());
        brokeURL = TextUtil.getString(mSettingPresenter.getBrokerUrl());
        reservedBaseURI = TextUtil.getString(mSettingPresenter.getReservedBaseUri());
        reservedBrokeURL = TextUtil.getString(mSettingPresenter.getReservedBrokerUrl());
        countlyUri = TextUtil.getString(mSettingPresenter.getCountlyUri());
        boolean isUsingReservedAddress = mSettingPresenter.isUsingReservedAddress();

        MaterialDialog dialog = new MaterialDialog.Builder(this)
                .title(R.string.text_setting_network)
                .customView(R.layout.dialog_setting_network, true)
                .positiveText(R.string.text_ok)
                .negativeText(android.R.string.cancel)
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        Log.i(TAG, which.name());
                        if (which.name().equals(POSITIVE)) {
                            mSettingPresenter.saveNetWorkSetting(baseUriTextView.getText().toString(),
                                    reservedBaseUriTextView.getText().toString(),
                                    brokeUrlTextView.getText().toString(),
                                    reservedBrokeUrlTextView.getText().toString(),
                                    countlyUriTextView.getText().toString(),
                                    reservedAddressCheckBox.isChecked());
                            //  mSettingPresenter.saveCountlyUri(countlyUriTextView.getText().toString());
                        }
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
                            mSettingPresenter.wipeData(MainApplication.get(SettingActivity.this), true);
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
                            mSettingPresenter.wipeData(MainApplication.get(SettingActivity.this), false);
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
    public void exitSystem(String operation) {
        mEventPosterHelper.postEventSafely(new UIBusEvent.ExitingSystem());
        finish();
        /*long delay = 2000;
        if (TextUtil.isNullOrEmpty(operation)) {
            delay = 10;
        } else if (operation.equals("clearHistory")) {
            MainApplication.get(this).sendClearingCacheMessage();
        } else if (operation.equals("restoreFactory")) {
            MainApplication.get(this).sendRestoringFactoryMessage();
        }

        if (delay == 2000) {
            ApplicationsUtil.showMessage(this, R.string.text_exit_two_seconds_later);
        }

        Observable.timer(delay, TimeUnit.MILLISECONDS)
                .subscribe(new Subscriber<Long>() {
                    @Override
                    public void onCompleted() {
                        mEventPosterHelper.postEventSafely(new UIBusEvent.ExitingSystem());
                        finish();
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.e(TAG, e.getMessage());
                    }

                    @Override
                    public void onNext(Long aLong) {

                    }
                });*/
    }

    @Override
    public void onSaveStyle() {
        mEventPosterHelper.postEventSafely(new UIBusEvent.GridStyle());
    }

    @Override
    public void onSaveQuality(boolean isNormal) {
        MainApplication.get(this).sendPhotoQualityMessage(isNormal);
    }

    @Override
    public void onSaveNetWorkSetting(boolean isOuter) {
        MainApplication.get(this).sendNetworkMessage(isOuter);
        exitSystem(null);
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

            intent.putExtra(ACCOUNT, mSettingPresenter.getAccount());
            intent.putExtra(PASSWORD, mSettingPresenter.getPassword());
            intent.putExtra(USER_ID, mSettingPresenter.getUserId());
            intent.putExtra(USER_NAME, mSettingPresenter.getUserName());
            intent.putExtra(DEPARTMENT, mSettingPresenter.getDepartment());
            intent.putExtra(DEPARTMENT_ID, mSettingPresenter.getDepartmentId());
            intent.putExtra(ROLES, !TextUtil.isNullOrEmpty(mSettingPresenter.getRoles()) ? mSettingPresenter.getRoles() : component.getRoles());
            intent.putExtra(ACCESS_TOKEN, mSettingPresenter.getAccessToken());
            if (!TextUtil.isNullOrEmpty(component.getParam())) {
                intent.putExtra(PARAMS, component.getParam());
            }
            intent.putExtra(EXTENDED_INFO, TextUtil.getString(getExtendedInfo()));
            if (checkIfNewTaskActivity(component.getExtendInfo())) {
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            }

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

    private boolean checkIfNewTaskActivity(String json) {
        try {
            if (TextUtil.isNullOrEmpty(json)) {
                return false;
            }

            JSONObject jsonObject = new JSONObject(json);
            return jsonObject.getBoolean(NEW_TASK_ACTIVITY);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    private String getExtendedInfo() {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put(PHOTO_QUALITY, mSettingPresenter.isPhotoQualityNormal() ? "normal" : "high");
            jsonObject.put(NETWORK, !mSettingPresenter.isUsingReservedAddress());
            return jsonObject.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
