package com.sh3h.mainshell_chaobiaoji.ui.main;


import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.sh3h.datautil.data.entity.DUConfigXmlFile;
import com.sh3h.datautil.data.local.config.SystemConfig;
import com.sh3h.ipc.module.MyModule;
import com.sh3h.mainshell_chaobiaoji.AppChannel;
import com.sh3h.mainshell_chaobiaoji.MainApplication;
import com.sh3h.mainshell_chaobiaoji.R;
import com.sh3h.mainshell_chaobiaoji.adapter.RecyclerViewAdapter;
import com.sh3h.mainshell_chaobiaoji.event.UIBusEvent;
import com.sh3h.mainshell_chaobiaoji.service.FileService;
import com.sh3h.mainshell_chaobiaoji.ui.base.ParentActivity;
import com.sh3h.mainshell_chaobiaoji.ui.setting.SettingActivity;
import com.sh3h.mainshell_chaobiaoji.util.ConstDataUtil;
import com.sh3h.mainshell_chaobiaoji.util.GuideHelper;
import com.sh3h.mobileutil.util.ApplicationsUtil;
import com.sh3h.mobileutil.util.LogUtil;
import com.sh3h.mobileutil.util.TextUtil;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import co.paulburke.android.itemtouchhelper.helper.SimpleItemTouchHelperCallback;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
//import ly.count.android.sdk.Countly;


/**
 * Created by dengzhimin on 2016/5/20.
 */
public class MainActivity extends ParentActivity
        implements MainMvView, RecyclerViewAdapter.OnItemClickListener,
        RecyclerViewAdapter.OnDataChangedListener, SwipeRefreshLayout.OnRefreshListener {

    private static final String TAG = "MainActivity";
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
    private static final String IS_HISTORY = "isHistory";

    /**
     * GridView Vertical Spacing
     **/
    private static final float VERTICAL_SPACING_20 = 20f;

    private static final float VERTICAL_SPACING_0 = 0f;

    /**
     * GridView  Column number
     **/
    private static final int COLUMN_3 = 3;

    private static final int COLUMN_1 = 1;

    @Inject
    MainPresenter mMainPresenter;

    @Inject
    Bus mEventBus;

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @BindView(R.id.rv_main)
    RecyclerView mRecyclerView;

    @BindView(R.id.fab_save)
    FloatingActionButton mFloatingActionButton;

    @BindView(R.id.v_guide_helper)
    View mView;

    @BindView(R.id.swipe_container)
    SwipeRefreshLayout mSwipeRefreshLayout;

    private Unbinder unbinder;
    private MenuItem mSettingMenuItem;
    private List<DUConfigXmlFile.Component> mComponents;
    private RecyclerViewAdapter mRecyclerViewAdapter;
    private long mExitTime;
    private boolean needUpdateFileList;
    private boolean isFileServiceReady;
    private boolean isFromLauncher;
    private boolean isFirstLogin;
    private boolean isSwitchingUser;
    //进度条是正在刷新
    private boolean isRefresh;
    //private boolean isExitingSystem;
    //public static HuaweiApiClient huaweiApiClient;
    private int updatingFileCount;
    private int checkingForUpdatingEndCount;

    //静态代码块，用于实现差分包
    static {
        System.loadLibrary("DiffPatchLibrary");
    }

    public MainActivity() {
        mComponents = null;
        needUpdateFileList = false;
        isFileServiceReady = false;
        isRefresh = false;
        //isExitingSystem = false;
        updatingFileCount = 0;
        checkingForUpdatingEndCount = 0;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getActivityComponent().inject(this);
        mMainPresenter.attachView(this);
        mEventBus.register(this);
        unbinder = ButterKnife.bind(this);

        initTitle();
        initSwipeBackLayout();
        initSwipeRefreshLayout();
        initRecycleView();
        initOthers(savedInstanceState);
        if (savedInstanceState == null) {
            saveSharedData();
        }

        LogUtil.i(TAG, "---onCreate---");
    }

    private void initTitle() {
        mToolbar.setTitle(R.string.title_main);
        setSupportActionBar(mToolbar);
    }

    private void initSwipeBackLayout() {
        getSwipeBackLayout().setEnableGesture(false);
    }

    private void initSwipeRefreshLayout() {
        mSwipeRefreshLayout.setOnRefreshListener(this);
        //mSwipeRefreshWidget.setProgressBackgroundColorSchemeResource(R.color.colorAccent);
        if (MainApplication.get(this).isGreenTheme()) {
            mSwipeRefreshLayout.setColorSchemeResources(R.color.holo_green_bright);
        } else {
            mSwipeRefreshLayout.setColorSchemeResources(R.color.holo_blue_bright);
        }
    }

    private void initRecycleView() {
        mComponents = new ArrayList<>();
        mRecyclerViewAdapter = new RecyclerViewAdapter(mComponents, this,
                MainApplication.get(this).isGreenTheme());
        if (mMainPresenter.isGridStyle()) {
            mRecyclerViewAdapter.setItemType(ConstDataUtil.TYPE_GRID_VIEW);
            mRecyclerView.setLayoutManager(new GridLayoutManager(MainActivity.this, COLUMN_3));
        } else {
            mRecyclerViewAdapter.setItemType(ConstDataUtil.TYPE_LIST_VIEW);
            mRecyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        }

        mRecyclerViewAdapter.setOnItemClickListener(this);
        mRecyclerViewAdapter.setOnDataChangedListener(this);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(mRecyclerViewAdapter);
        ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback(mRecyclerViewAdapter);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(callback);
        itemTouchHelper.attachToRecyclerView(mRecyclerView);
    }

    private void initOthers(Bundle savedInstanceState) {
        whereFrom(savedInstanceState);
        mMainPresenter.initSubTitle();
        initGuideHelper();

        mFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new MaterialDialog.Builder(MainActivity.this)
                        .title(R.string.text_prompt)
                        .content(R.string.text_is_saving)
                        .positiveText(R.string.text_ok)
                        .negativeText(R.string.text_cancel)
                        .cancelable(false)
                        .onPositive(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog materialDialog,
                                                @NonNull DialogAction dialogAction) {
                                LogUtil.i(TAG, "OnDataChanged ok");
                                mFloatingActionButton.setVisibility(View.GONE);
                                mMainPresenter.saveComponentList(mComponents, true);
                            }
                        })
                        .onNegative(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog materialDialog,
                                                @NonNull DialogAction dialogAction) {
                                LogUtil.i(TAG, "OnDataChanged cancel");
                                mFloatingActionButton.setVisibility(View.GONE);
                                updateComponentList(mMainPresenter.getDuConfigXmlFile());
                            }
                        })
                        .show();
            }
        });

        isRefresh = true;
        mSwipeRefreshLayout.setProgressViewOffset(false, 0, 100);
        mSwipeRefreshLayout.setRefreshing(true);

        mMainPresenter.downloadConfig(isFromLauncher, isSwitchingUser);
        //mMainPresenter.downloadWords(isFromLauncher);
    }

    private void saveSharedData() {
        Map<String, String> map = new HashMap<>();
        map.put(ACCOUNT, TextUtil.getString(mMainPresenter.getAccount()));
        map.put(PASSWORD, TextUtil.getString(mMainPresenter.getPassword()));
        map.put(USER_ID, String.valueOf(mMainPresenter.getUserId()));
        map.put(USER_NAME, TextUtil.getString(mMainPresenter.getUserName()));
        map.put(DEPARTMENT, TextUtil.getString(mMainPresenter.getDepartment()));
        map.put(DEPARTMENT_ID, String.valueOf(mMainPresenter.getDepartmentId()));
        map.put(ACCESS_TOKEN, TextUtil.getString(mMainPresenter.getAccessToken()));
        MainApplication.get(this).saveSharedData(map);
    }

    private void saveSharedData(String roles, String params, String extendedInfo) {
        Map<String, String> map = new HashMap<>();
        map.put(ROLES, TextUtil.getString(roles));
        map.put(PARAMS, TextUtil.getString(params));
        map.put(EXTENDED_INFO, TextUtil.getString(extendedInfo));
        MainApplication.get(this).saveSharedData(map);
    }

    /**
     * 启用消息推送服务
     */
    private void starMQTTService() {
//        Intent intent = new Intent(this, MQTTService.class);
//        //intent.setAction(MQTTService.ACTION_START);
//        startService(intent);
    }

    private void resetGridStyle(boolean isGrid) {
        if (isGrid) {
            mRecyclerViewAdapter.setItemType(ConstDataUtil.TYPE_GRID_VIEW);
            mRecyclerView.setLayoutManager(new GridLayoutManager(MainActivity.this, COLUMN_3));
        } else {
            mRecyclerViewAdapter.setItemType(ConstDataUtil.TYPE_LIST_VIEW);
            mRecyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        }

        mRecyclerViewAdapter.notifyDataSetChanged();
    }

    private void whereFrom(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            isFromLauncher = savedInstanceState.getBoolean(ConstDataUtil.IS_FROM_LAUNCHER, false);
            isFirstLogin = false;
            isSwitchingUser = false;
            LogUtil.i(TAG, "---whereFrom savedInstanceState---" + isFromLauncher);
        } else {
            Intent intent = getIntent();
            isFromLauncher = intent.getBooleanExtra(ConstDataUtil.IS_FROM_LAUNCHER, false);
            isFirstLogin = intent.getBooleanExtra(ConstDataUtil.IS_FIRST_LOGIN, false);
            isSwitchingUser = intent.getBooleanExtra(ConstDataUtil.IS_SWITCHING_USER, false);
            LogUtil.i(TAG, "---whereFrom intent---" + isFromLauncher);
        }
    }

    @Override
    public void onRefresh() {
        if (!isRefresh) {
            isRefresh = true;
            mMainPresenter.downloadConfig(false, false);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_setting, menu);
        mSettingMenuItem = menu.findItem(R.id.action_setting);
        mSettingMenuItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Intent intent = new Intent(MainActivity.this, SettingActivity.class);
                startActivity(intent);
                saveMonitorData("SettingActivity");
                return true;
            }
        });
        return true;
    }

    @Override
    public void startServices(ServiceFlag flag) {
    }

    @Override
    public void setSubTitle(String title) {
        if (!TextUtil.isNullOrEmpty(title)) {
            mToolbar.setSubtitle(title);
        }
    }

    @Override
    public void onLoadAndMatchXml(boolean result, boolean firstTime) {
        LogUtil.i(TAG, "---onLoadAndMatchXml---" + result);
        if (result) {
            updateComponentList(mMainPresenter.getDuConfigXmlFile());
        }

        if (firstTime) {
            needUpdateFileList = true;
            updateFileList();
        }
    }

    @Override
    public void onDownloadWords() {

    }

    @Override
    public void onError(String message) {
        if (!TextUtil.isNullOrEmpty(message)) {
            ApplicationsUtil.showMessage(this, message);
            LogUtil.i(TAG, "---onError---" + message);
        }
    }

    @Override
    public void onSaveComponentList(Boolean aBoolean) {
        if (aBoolean) {
            ApplicationsUtil.showMessage(this, R.string.text_saving_success);
        } else {
            ApplicationsUtil.showMessage(this, R.string.text_saving_failure);
        }
    }

    @Override
    public void onDownloadConfig() {
        isRefresh = false;
        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onLogout(Boolean aBoolean) {
        finish();
    }

    /**
     * 首次登录新时代引导
     */
    public void initGuideHelper() {
        if (isFirstLogin) {
            GuideHelper guideHelper = new GuideHelper(MainActivity.this);
            GuideHelper.TipData tipData = new GuideHelper.TipData(R.drawable.setting,
                    Gravity.RIGHT | Gravity.TOP, mView);
            //tipData.setLocation(0, DisplayUtils.dipToPix(MainActivity.this, 100));
            guideHelper.addPage(tipData);
            guideHelper.show(false);
        }
    }

    @Override
    public void onItemClick(DUConfigXmlFile.Component component) {
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            String activityName = component.getActivity();
            int suffixIndex = activityName.indexOf(MyModule.SEPARATOR);
            if (suffixIndex > 0) {
                activityName = activityName.substring(0, suffixIndex);
            }

            ComponentName cn = new ComponentName(component.getPackageName(), activityName);
            intent.setComponent(cn);
            intent.putExtra(ACCOUNT, mMainPresenter.getAccount());
            intent.putExtra(PASSWORD, mMainPresenter.getPassword());
            intent.putExtra(USER_ID, mMainPresenter.getUserId());
            intent.putExtra(USER_NAME, mMainPresenter.getUserName());
            intent.putExtra(DEPARTMENT, mMainPresenter.getDepartment());
            intent.putExtra(DEPARTMENT_ID, mMainPresenter.getDepartmentId());
            intent.putExtra(ROLES, !TextUtil.isNullOrEmpty(mMainPresenter.getRoles()) ? mMainPresenter.getRoles() : component.getRoles());
            intent.putExtra(ACCESS_TOKEN, mMainPresenter.getAccessToken());
            if (component.getName().equals("管网工单")) { //管网工单
                intent.putExtra(IS_HISTORY, false);
            } else if (component.getName().equals("管网历史工单")) { //管网历史工单
                intent.putExtra(IS_HISTORY, true);
            }
            if (!TextUtil.isNullOrEmpty(component.getParam())) {
                intent.putExtra(PARAMS, component.getParam());
            }
            intent.putExtra(EXTENDED_INFO, TextUtil.getString(getExtendedInfo()));
            if (checkIfNewTaskActivity(component.getExtendInfo())) {
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            }
            saveSharedData(intent.getStringExtra(ROLES), intent.getStringExtra(PARAMS), intent.getStringExtra(EXTENDED_INFO));
            startActivity(intent);
            saveMonitorData(component.getActivity());
        } catch (Exception e) {
            e.printStackTrace();
            LogUtil.e(TAG, "---onItemClick---" + e.getMessage());
        }
    }

    @Override
    public void OnDataChanged() {
        mFloatingActionButton.setVisibility(View.VISIBLE);
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
    public void onDestroy() {
        super.onDestroy();

        mEventBus.unregister(this);
        unbinder.unbind();
        mMainPresenter.detachView();
        stopService(FileService.getStartIntent(this));
        MainApplication.get(this).destroy();

        //sendBroadcast(new Intent("EXIT_SYSTEM"));
        //if (isExitingSystem) {
        //    isExitingSystem = false;
        //}

//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    Thread.sleep(1000);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//                System.exit(0);
//            }
//        }).start();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - mExitTime) > 2000) {
                ApplicationsUtil.showMessage(this, R.string.text_quit);
                mExitTime = System.currentTimeMillis();
            } else {
                //MobclickAgent.onKillProcess(this);
                //MobclickAgent.onProfileSignOff();
                mMainPresenter.logout(MainApplication.get(this));
            }

        }
        return true;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(ConstDataUtil.IS_FROM_LAUNCHER, false);
        outState.putBoolean(ConstDataUtil.IS_SWITCHING_USER, false);
        LogUtil.i(TAG, "---onSaveInstanceState---");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == MainApplication.APP_INSTALLING_REQUEST_CODE) {
            LogUtil.i(TAG, "ok");
            String appChannel = MainApplication.get(this).getAppChannel();
            if (!TextUtil.isNullOrEmpty(appChannel)
                    && appChannel.equalsIgnoreCase(AppChannel.GAS_GROUP_CHANNEL)) {
                finish();
            }
        }
    }

    @Subscribe
    public void onChangeStyle(UIBusEvent.GridStyle gridStyle) {
        resetGridStyle(mMainPresenter.isGridStyle());
    }

    @Subscribe
    public void onApkStatus(UIBusEvent.ApkStatus apkStatus) {
        LogUtil.i(TAG, "---onApkStatus---");
        //mMainPresenter.loadAndMatchXml(false);
        onLoadAndMatchXml(true, false);
    }

    @Subscribe
    public void onExitingSystem(UIBusEvent.ExitingSystem exitingSystem) {
        LogUtil.i(TAG, "---onExitingSystem---");
        //isExitingSystem = true;
        finish();
    }

    @Subscribe
    public void onFileServiceReady(UIBusEvent.FileServiceReady fileServiceReady) {
        LogUtil.i(TAG, "---onFileServiceReady---");
        isFileServiceReady = true;
        updateFileList();
    }

    @Subscribe
    public void onUpdatingComponent(UIBusEvent.UpdatingComponent updatingComponent) {
        LogUtil.i(TAG, "---onUpdatingComponent---1");
        if ((mComponents == null) || (mComponents.size() <= 0)) {
            LogUtil.i(TAG, "---onUpdatingComponent---2");
            return;
        }

        String packageName = updatingComponent.getPackageName();
        String activity = updatingComponent.getActivity();
        int count = updatingComponent.getCount();
        if (TextUtil.isNullOrEmpty(packageName) || TextUtil.isNullOrEmpty(activity)) {
            LogUtil.i(TAG, "---onUpdatingComponent---3");
            return;
        }

        boolean found = false;
        for (DUConfigXmlFile.Component component : mComponents) {
            if (component.getPackageName().equals(packageName)
                    && component.getActivity().equals(activity)) {
                component.setCount(count);
                found = true;
                break;
            }
        }

        if (found) {
            mRecyclerViewAdapter.notifyDataSetChanged();
            mMainPresenter.saveComponentList(mComponents, false);
        }
    }

    @Subscribe
    public void onFileEnd(UIBusEvent.FileEnd fileEnd) {
        LogUtil.i(TAG, "onFileEnd: " + updatingFileCount--);

        if (updatingFileCount <= 0) {
            hideProgress();
        }
    }

    private void updateComponentList(DUConfigXmlFile duConfigXmlFile) {
        if ((duConfigXmlFile == null)
                || (duConfigXmlFile.getComponentList() == null)
                || (duConfigXmlFile.getComponentList().size() <= 0)) {
            return;
        }

        mComponents = new ArrayList<>();
        List<DUConfigXmlFile.Component> componentList = duConfigXmlFile.getComponentList();
        for (DUConfigXmlFile.Component component : componentList) {
            if (!component.isValid() || TextUtil.isNullOrEmpty(component.getShowAs())
                    || !component.getShowAs().equals(DUConfigXmlFile.SHOWN_AS_MAIN)) {
                continue;
            }

            //特殊情况处理
            if (filterSpecial(component.getExtendInfo())) {
                continue;
            }

            mComponents.add(component.getClone());
        }
        // TODO 为了测试新加icon
//        DUConfigXmlFile.Component component = new DUConfigXmlFile.Component();
//        component.setFunctionKey("b1aa7f32-76ab-4457-bf14-89bb74d5a556");
//        component.setName("工单登记");
//        component.setIcon("ic_main_order_dengji");
//        component.setPackageName("com.sh3h.taskmanager");
//        component.setActivity("com.sh3h.taskmanager.hotline.register.OrderRegisterActivity");
//        component.setParam("taskmanager");
//        component.setOrder(117);
//        component.setShownAs("main");
//        component.setCount(0);
//        mComponents.add(component.getClone());
//
//        DUConfigXmlFile.Component component2 = new DUConfigXmlFile.Component();
//        component2.setFunctionKey("b1aa7f32-76ab-4457-bf14-89bb74d5a556");
//        component2.setName("热线工单");
//        component2.setIcon("ic_main_order");
//        component2.setPackageName("com.sh3h.taskmanager");
//        component2.setActivity("com.sh3h.taskmanager.hotline.order.HotlineOrderActivity");
//        component2.setParam("taskmanager");
//        component2.setOrder(118);
//        component2.setShownAs("main");
//        component2.setCount(0);
//        mComponents.add(component2.getClone());
//
//        DUConfigXmlFile.Component component3 = new DUConfigXmlFile.Component();
//        component3.setFunctionKey("b1aa7f32-76ab-4457-bf14-89bb74d5a556");
//        component3.setName("热线工单历史");
//        component3.setIcon("ic_mian_order_history");
//        component3.setPackageName("com.sh3h.taskmanager");
//        component3.setActivity("com.sh3h.taskmanager.hotline.order.history.HotlineOrderHistoryActivity");
//        component3.setParam("taskmanager");
//        component3.setOrder(119);
//        component3.setShownAs("main");
//        component3.setCount(0);
//        mComponents.add(component3.getClone());
//
//        DUConfigXmlFile.Component component4 = new DUConfigXmlFile.Component();
//        component4.setFunctionKey("b1aa7f32-76ab-4457-bf14-89bb74d5a556");
//        component4.setName("热线工单查询");
//        component4.setIcon("ic_mian_query");
//        component4.setPackageName("com.sh3h.taskmanager");
//        component4.setActivity("com.sh3h.taskmanager.hotline.query.OrderQueryResultActivity");
//        component4.setParam("taskmanager");
//        component4.setOrder(120);
//        component4.setShownAs("main");
//        component4.setCount(0);
//        mComponents.add(component4.getClone());
//
//        DUConfigXmlFile.Component component5 = new DUConfigXmlFile.Component();
//        component5.setFunctionKey("b1aa7f32-76ab-4457-bf14-89bb74d5a556");
//        component5.setName("热线工单统计");
//        component5.setIcon("ic_main_statistics");
//        component5.setPackageName("com.sh3h.taskmanager");
//        component5.setActivity("com.sh3h.taskmanager.hotline.statistical.HotlineStatisticalPieActivity");
//        component5.setParam("taskmanager");
//        component5.setOrder(121);
//        component5.setShownAs("main");
//        component5.setCount(0);
//        mComponents.add(component5.getClone());

        mRecyclerViewAdapter.setApkInfos(mComponents);
        mRecyclerViewAdapter.notifyDataSetChanged();
    }

    private boolean filterSpecial(String extend) {
        if (!AppChannel.JIANGMENG_CHANNEL.equals(MainApplication.get(this).getAppChannel())) {
            return false;
        }

        String roles = mMainPresenter.getRoles();
        if (TextUtil.isNullOrEmpty(roles) || TextUtil.isNullOrEmpty(extend)) {
            return false;
        }

        String[] roleList = roles.split("@");
        boolean isFilter = true;
        for (String role : roleList) {
            if (extend.contains(role)) {
                isFilter = false;
                break;
            }
        }

        return isFilter;
    }

    private void updateFileList() {
        if (needUpdateFileList && isFileServiceReady) {
            needUpdateFileList = false;
            if (mMainPresenter.isAutoUpdate()) {
                updatingFileCount = updateFileList(mMainPresenter.getDuUpdateXmlFile());
                if (updatingFileCount > 0) {
                    showProgress(R.string.text_app_update);
                    checkingForUpdatingEndCount = 2;
                    checkForUpdatingEnd(5, TimeUnit.SECONDS);
                }
            }
        }
        LogUtil.i(TAG, "updateFileList: " + updatingFileCount);
    }

    private void checkForUpdatingEnd(long delay, TimeUnit unit) {
        Observable.timer(delay, unit)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Long>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Long aLong) {
                        if (MainApplication.get(MainActivity.this).isFileServiceOperationFinished()
                                || checkingForUpdatingEndCount-- <= 0) {
                            hideProgress();
                        } else {
                            checkForUpdatingEnd(1, TimeUnit.MINUTES);
                        }
                    }
                });
    }

    private void saveMonitorData(String toWhere) {
        if (MainApplication.get(this).isMonitorInit()) {
            if (MainApplication.get(this).getMonitorType() == SystemConfig.MonitorType.COUTLY) {
//                Map<String, String> data = new HashMap<>();
//                data.put("Operate", CountlyEvent.Operate.INTENT);
//                data.put("To", toWhere);
//                Countly.sharedInstance().recordEvent(CountlyEvent.CLICK, data, 1);
            } else if (MainApplication.get(this).getMonitorType() == SystemConfig.MonitorType.UMENG) {
                /*
                Map<String ,String> data = new HashMap<>();
                data.put("intent", "SettingActivity");
                data.put("name", "设置");
                MobclickAgent.onEvent(mContext, UMengEvent.CLICK, data);
                */

                /*
                Map<String ,String> data = new HashMap<>();
                data.put("intent", DUApkInfoResultEx.getActivity());
                data.put("name", DUApkInfoResultEx.getName());
                MobclickAgent.onEvent(mContext, UMengEvent.CLICK, data);
                */
            }
        }
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
            jsonObject.put(PHOTO_QUALITY, mMainPresenter.isPhotoQualityNormal() ? "normal" : "high");
            jsonObject.put(NETWORK, !mMainPresenter.isUsingReservedAddress());
            return jsonObject.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
