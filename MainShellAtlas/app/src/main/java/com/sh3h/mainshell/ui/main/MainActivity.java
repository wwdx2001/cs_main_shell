package com.sh3h.mainshell.ui.main;


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
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.huawei.hms.api.ConnectionResult;
import com.huawei.hms.api.HuaweiApiAvailability;
import com.huawei.hms.api.HuaweiApiClient;
import com.huawei.hms.support.api.push.HuaweiPush;
import com.mainshell.datautil.data.entity.DUConfigXmlFile;
import com.mainshell.datautil.data.local.config.SystemConfig;
import com.sh3h.ipc.module.MyModule;
import com.sh3h.mainshell.MainApplication;
import com.sh3h.mainshell.R;
import com.sh3h.mainshell.adapter.RecyclerViewAdapter;
import com.sh3h.mainshell.event.UIBusEvent;
import com.sh3h.mainshell.event.countly.CountlyEvent;
import com.sh3h.mainshell.service.FileService;
import com.sh3h.mainshell.ui.base.ParentActivity;
import com.sh3h.mainshell.ui.base.RootActivity;
import com.sh3h.mainshell.ui.setting.SettingActivity;
import com.sh3h.mainshell.util.ConstDataUtil;
import com.sh3h.mainshell.util.GuideHelper;
import com.sh3h.mobileutil.util.ApplicationsUtil;
import com.sh3h.mobileutil.util.LogUtil;
import com.sh3h.mobileutil.util.TextUtil;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import co.paulburke.android.itemtouchhelper.helper.SimpleItemTouchHelperCallback;


/**
 * Created by dengzhimin on 2016/5/20.
 */
public class MainActivity extends RootActivity
        implements MainMvView, RecyclerViewAdapter.OnItemClickListener,
        RecyclerViewAdapter.OnDataChangedListener, SwipeRefreshLayout.OnRefreshListener {
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

    private static final String TAG = "MainActivity";
    private static final String PARAMS = "params";
    private static final String ACCOUNT = "account";
    //向子程序发送用户id
    private static final String USER_ID = "userId";
    //向子程序发送用户名
    protected static final String USER_NAME = "userName";

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
    public static HuaweiApiClient huaweiApiClient;

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
        //initSwipeBackLayout();
        initSwipeRefreshLayout();
        initRecycleView();
        initOthers(savedInstanceState);
        //getSwipeBackLayout().setEnableGesture(false);

        LogUtil.i(TAG, "---onCreate---");
    }

    private void initTitle() {
        mToolbar.setTitle(R.string.title_main);
        setSupportActionBar(mToolbar);
    }

    private void initSwipeBackLayout() {
        //getSwipeBackLayout().setEnableGesture(false);
    }

    private void initSwipeRefreshLayout() {
        mSwipeRefreshLayout.setOnRefreshListener(this);
        //mSwipeRefreshWidget.setProgressBackgroundColorSchemeResource(R.color.colorAccent);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.holo_blue_bright);
    }

    private void initRecycleView() {
        mComponents = new ArrayList<>();
        mRecyclerViewAdapter = new RecyclerViewAdapter(mComponents, this,
                mMainPresenter.isAtlasMode(), mMainPresenter.getApksFolderPath());
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
        mMainPresenter.downloadWords(isFromLauncher);
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
            if (mMainPresenter.isAtlasMode()) {
                String activityName = component.getActivity();
                int suffixIndex = activityName.indexOf(MyModule.SEPARATOR);
                if (suffixIndex > 0) {
                    activityName = activityName.substring(0, suffixIndex);
                }

                Intent intent = new Intent();
                intent.setClassName(this, activityName);
                startActivity(intent);
            } else {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                String activityName = component.getActivity();
                int suffixIndex = activityName.indexOf(MyModule.SEPARATOR);
                if (suffixIndex > 0) {
                    activityName = activityName.substring(0, suffixIndex);
                }

                ComponentName cn = new ComponentName(component.getPackageName(), activityName);
                intent.setComponent(cn);
                intent.putExtra(ACCOUNT, mMainPresenter.getAccount());
                intent.putExtra(USER_ID, mMainPresenter.getUserId());
                intent.putExtra(USER_NAME, mMainPresenter.getUserName());
                //intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                if (!TextUtil.isNullOrEmpty(component.getParam())) {
                    intent.putExtra(PARAMS, component.getParam());
                }
                startActivity(intent);
            }
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
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - mExitTime) > 2000) {
                ApplicationsUtil.showMessage(this, "再按一次退出程序");
                mExitTime = System.currentTimeMillis();
            } else {
                finish();
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

    private void updateComponentList(DUConfigXmlFile duConfigXmlFile) {
        if ((duConfigXmlFile == null)
                || (duConfigXmlFile.getComponentList() == null)
                || (duConfigXmlFile.getComponentList().size() <= 0)) {
            return;
        }

        mComponents = new ArrayList<>();
        List<DUConfigXmlFile.Component> componentList = duConfigXmlFile.getComponentList();
        for (DUConfigXmlFile.Component component : componentList) {
            if (component.isValid()
                    && (!TextUtil.isNullOrEmpty(component.getShowAs()))
                    && component.getShowAs().equals(DUConfigXmlFile.SHOWN_AS_MAIN)) {
                mComponents.add(component.getClone());
            }
        }

        mRecyclerViewAdapter.setApkInfos(mComponents);
        mRecyclerViewAdapter.notifyDataSetChanged();
    }

    private void updateFileList() {
        if (needUpdateFileList && isFileServiceReady) {
            needUpdateFileList = false;
            if (mMainPresenter.isAutoUpdate()) {
                updateFileList(mMainPresenter.getDuUpdateXmlFile());
            }
        }
    }
}
