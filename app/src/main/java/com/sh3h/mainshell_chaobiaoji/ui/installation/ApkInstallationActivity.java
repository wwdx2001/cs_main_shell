package com.sh3h.mainshell_chaobiaoji.ui.installation;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.sh3h.datautil.data.entity.DUApkInfoResultEx;
import com.sh3h.datautil.data.entity.DUFileResult;
import com.sh3h.mainshell_chaobiaoji.MainApplication;
import com.sh3h.mainshell_chaobiaoji.R;
import com.sh3h.mainshell_chaobiaoji.event.UIBusEvent;
import com.sh3h.mainshell_chaobiaoji.ui.base.ParentActivity;
import com.sh3h.mobileutil.util.ApplicationsUtil;
import com.sh3h.mobileutil.util.LogUtil;
import com.sh3h.mobileutil.util.TextUtil;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static android.widget.ImageView.ScaleType.CENTER_CROP;

public class ApkInstallationActivity extends ParentActivity
        implements ApkInstallationMvpView, SwipeRefreshLayout.OnRefreshListener {
    @Inject
    ApkInstallationPresenter mApkInstallationPresenter;

    @Inject
    Bus mBus;

    @BindView(R.id.toolbar_install)
    Toolbar mToolbar;

    @BindView(R.id.swipe_layout)
    SwipeRefreshLayout mSwipeRefreshLayout;

    @BindView(R.id.install_list)
    RecyclerView recyclerView;

    private static final String TAG = "ApkInstallationActivity";

    private Unbinder mUnbinder;
    private MyApkListAdapter mMyApkListAdapter;
    private List<DUApkInfoResultEx> mDUApkInfoResultExList;
    private boolean mIsRefreshing;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apk_install_list);

        getActivityComponent().inject(this);
        mUnbinder = ButterKnife.bind(this);
        mApkInstallationPresenter.attachView(this);
        mBus.register(this);

        initTitle();
        initListView();
        LogUtil.i(TAG, "---onCreate---");
    }

    private void initTitle() {
        mToolbar.setTitle(getResources().getString(R.string.title_apk_install));
        setSupportActionBar(mToolbar);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mToolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
        } else {
            mToolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_arrow_back_white_24dp));
        }
    }

    private void initListView() {
        mDUApkInfoResultExList = new ArrayList<>();
        mMyApkListAdapter = new MyApkListAdapter(this, R.layout.item_apk_info, mDUApkInfoResultExList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(mMyApkListAdapter);
        mIsRefreshing = true;
        mSwipeRefreshLayout.setProgressViewOffset(false, 0, 100);
        if (MainApplication.get(this).isGreenTheme()) {
            mSwipeRefreshLayout.setColorSchemeResources(R.color.holo_green_bright);
        } else {
            mSwipeRefreshLayout.setColorSchemeResources(R.color.holo_blue_bright);
        }
        mSwipeRefreshLayout.setRefreshing(true);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mApkInstallationPresenter.getApkList(MainApplication.get(this));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        LogUtil.i(TAG, "---onDestroy---");
        mUnbinder.unbind();
        mApkInstallationPresenter.detachView();
        mBus.unregister(this);
    }

    @Override
    public void onGetApkList(List<DUApkInfoResultEx> duApkInfoResultExList) {
        LogUtil.i(TAG, "---onDownLoadApkList---1");
        mIsRefreshing = false;
        mSwipeRefreshLayout.setRefreshing(false);
        if ((duApkInfoResultExList == null) || (duApkInfoResultExList.size() <= 0)) {
            LogUtil.i(TAG, "---onDownLoadApkList---2");
            return;
        }

        LogUtil.i(TAG, "---onDownLoadApkList---3");

        DUFileResult duFileResult = MainApplication.get(this).getDUFileResult();
        if ((duFileResult != null)
                && (!TextUtil.isNullOrEmpty(duFileResult.getDuFile().getAppName()))
                && (!TextUtil.isNullOrEmpty(duFileResult.getDuFile().getAppId()))) {
            for (DUApkInfoResultEx duApkInfoResultEx : duApkInfoResultExList) {
                if (duApkInfoResultEx.getAppName().equals(duFileResult.getDuFile().getAppName())
                        && duApkInfoResultEx.getAppId().equals(duFileResult.getDuFile().getAppId())) {
                    duApkInfoResultEx.setProgressbarVisible(true);
                    duApkInfoResultEx.setPercent(duFileResult.getPercent());
                    break;
                }
            }
        }

        mDUApkInfoResultExList = duApkInfoResultExList;
        mMyApkListAdapter.setDataList(mDUApkInfoResultExList);
        mMyApkListAdapter.notifyDataSetChanged();
    }

    @Override
    public void onCompleted() {
        LogUtil.i(TAG, "---onCompleted---");
        mIsRefreshing = false;
        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onError(String message) {
        mIsRefreshing = false;
        mSwipeRefreshLayout.setRefreshing(false);
        if (!TextUtil.isNullOrEmpty(message)) {
            ApplicationsUtil.showMessage(this, message);
            LogUtil.i(TAG, "---onError---" + message);
        }
    }

    @Override
    public void onError(ErrorCode errorCode) {
        mIsRefreshing = false;
        mSwipeRefreshLayout.setRefreshing(false);
        switch (errorCode) {
            case NO_NETWORK:
                LogUtil.i(TAG, "---onError NO_NETWORK---");
                break;
            case PARAM_INVALID:
                LogUtil.i(TAG, "---onError PARAM_INVALID---");
                break;
        }
    }

    @Override
    public void onRefresh() {
        LogUtil.i(TAG, "onRefresh");
        if (!mIsRefreshing) {
            mIsRefreshing = true;
            mApkInstallationPresenter.getApkList(MainApplication.get(this));
        }
    }

    @Subscribe
    public void onFileStart(UIBusEvent.FileStart fileStart) {
        LogUtil.i(TAG, "---onFileStart 1---");
        if (TextUtil.isNullOrEmpty(fileStart.getAppName())
                || TextUtil.isNullOrEmpty(fileStart.getAppId())) {
            LogUtil.i(TAG, "---onFileStart 2---");
            return;
        }

        for (DUApkInfoResultEx duApkInfoResultEx : mDUApkInfoResultExList) {
            if (duApkInfoResultEx.getAppName().equals(fileStart.getAppName())
                    && duApkInfoResultEx.getAppId().equals(fileStart.getAppId())) {
                LogUtil.i(TAG, "---onFileStart 3---");
                duApkInfoResultEx.setProgressbarVisible(true);
                duApkInfoResultEx.setPercent(0);
                mMyApkListAdapter.notifyDataSetChanged();
                break;
            }
        }
    }

    @Subscribe
    public void onFileStep(UIBusEvent.FileStep fileStep) {
        LogUtil.i(TAG, "---onFileStep 1---");

        if (TextUtil.isNullOrEmpty(fileStep.getAppName())
                || TextUtil.isNullOrEmpty(fileStep.getAppId())) {
            LogUtil.i(TAG, "---onFileStep 2---");
            return;
        }

        for (DUApkInfoResultEx duApkInfoResultEx : mDUApkInfoResultExList) {
            if (duApkInfoResultEx.getAppName().equals(fileStep.getAppName())
                    && duApkInfoResultEx.getAppId().equals(fileStep.getAppId())) {
                LogUtil.i(TAG, "---onFileStep 3---" + fileStep.getPercent());
                duApkInfoResultEx.setPercent(fileStep.getPercent());
                mMyApkListAdapter.notifyDataSetChanged();
                break;
            }
        }
    }

    @Subscribe
    public void onFileEnd(UIBusEvent.FileEnd fileEnd) {
        LogUtil.i(TAG, "---onFileEnd 1---");

        if (TextUtil.isNullOrEmpty(fileEnd.getAppName())
                || TextUtil.isNullOrEmpty(fileEnd.getAppId())
                || TextUtil.isNullOrEmpty(fileEnd.getPackageName())
                ) { //|| TextUtil.isNullOrEmpty(fileEnd.getUrl())
            LogUtil.i(TAG, "---onFileEnd 2---");
            return;
        }

        for (DUApkInfoResultEx duApkInfoResultEx : mDUApkInfoResultExList) {
            if (duApkInfoResultEx.getAppName().equals(fileEnd.getAppName())
                    && duApkInfoResultEx.getAppId().equals(fileEnd.getAppId())) {
                LogUtil.i(TAG, "---onFileEnd 3---");
                duApkInfoResultEx.setProgressbarVisible(false);
                mMyApkListAdapter.notifyDataSetChanged();
                break;
            }
        }
    }

    @Subscribe
    public void onApkStatus(UIBusEvent.ApkStatus apkStatus) {
        LogUtil.i(TAG, "---onApkStatus 1---");

        for (DUApkInfoResultEx duApkInfoResultEx : mDUApkInfoResultExList) {
            if (duApkInfoResultEx.getAppId().equals(apkStatus.getAppId())) {
                LogUtil.i(TAG, "---onApkStatus 2---");

                switch (apkStatus.getAction()) {
                    case Intent.ACTION_PACKAGE_ADDED:
                        duApkInfoResultEx.setState(DUApkInfoResultEx.STATE_INSTALLED);
                        break;
                    case Intent.ACTION_PACKAGE_REMOVED:
                        duApkInfoResultEx.setState(DUApkInfoResultEx.STATE_DOWNLOAD);
                        break;
                    default:
                        return;
                }

                mMyApkListAdapter.notifyDataSetChanged();
                break;
            }
        }
    }

    private class MyApkListAdapter extends RecyclerView.Adapter<RecyclerViewHolder> implements View.OnClickListener {
        private Context mContext;
        private int mResource;
        private LayoutInflater mInflater;
        private List<DUApkInfoResultEx> mDUApkInfoResultExList;

        public MyApkListAdapter(Context context,
                                int resource,
                                List<DUApkInfoResultEx> duApkInfoResultExList) {
            mContext = context;
            mResource = resource;
            mInflater = LayoutInflater.from(context);
            mDUApkInfoResultExList = duApkInfoResultExList;
        }

        public void setDataList(List<DUApkInfoResultEx> duApkInfoResultExList) {
            mDUApkInfoResultExList = duApkInfoResultExList;
        }

        @Override
        public int getItemCount() {
            return mDUApkInfoResultExList == null ? 0 : mDUApkInfoResultExList.size();
        }

        @Override
        public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = mInflater.inflate(mResource, parent, false);
            return new RecyclerViewHolder(view);
        }

        @Override
        public void onBindViewHolder(RecyclerViewHolder holder, int position) {
            DUApkInfoResultEx duApkInfoResultEx = mDUApkInfoResultExList.get(position);
            //icon
            holder.mIvApkImage.setScaleType(CENTER_CROP);
            Picasso.with(mContext) //
                    .load(mApkInstallationPresenter.isUsingReservedAddress() ? duApkInfoResultEx.getIconInnerUrl() : duApkInfoResultEx.getIconOuterUrl()) //
                    .placeholder(R.mipmap.bg_place_holder) //
                    .error(R.mipmap.bg_error) //
                    .fit() //
                    .tag(mContext) //
                    .into(holder.mIvApkImage);
            //name
            holder.mTvApkName.setText(duApkInfoResultEx.getAppName());
            //version
            holder.mTvApkVersion.setText(String.format(Locale.CHINESE, "%s %s",
                    getResources().getString(R.string.text_version_separator),
                    duApkInfoResultEx.getVersionName()));
            //button
            if (duApkInfoResultEx.getState() == DUApkInfoResultEx.STATE_INSTALLED) {
                holder.mBtnApkInstall.setText(getString(R.string.text_installed));
            } else if (duApkInfoResultEx.getState() == DUApkInfoResultEx.STATE_UPDATE) {
                holder.mBtnApkInstall.setText(getString(R.string.text_update));
            } else {
                holder.mBtnApkInstall.setText(getString(R.string.text_downLoad));
            }
            //progressbar
            if (duApkInfoResultEx.isProgressbarVisible()) {
                holder.mBtnApkInstall.setVisibility(View.GONE);
                holder.mPbInstall.setProgress(duApkInfoResultEx.getPercent());
                holder.mPbInstall.setVisibility(View.VISIBLE);
            } else {
                holder.mBtnApkInstall.setVisibility(View.VISIBLE);
                holder.mPbInstall.setProgress(0);
                holder.mPbInstall.setVisibility(View.GONE);
            }
            //点击事件
            holder.mBtnApkInstall.setOnClickListener(this);
            holder.mBtnApkInstall.setTag(position);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btn_install:
                    deleteOrDownload(v);
                    break;
                default:
                    break;
            }
        }
    }

    private class RecyclerViewHolder extends RecyclerView.ViewHolder {
        private ImageView mIvApkImage;
        private TextView mTvApkName;
        private TextView mTvApkVersion;
        private Button mBtnApkInstall;
        private ProgressBar mPbInstall;

        public RecyclerViewHolder(View itemView) {
            super(itemView);
            mIvApkImage = (ImageView) itemView.findViewById(R.id.iv_apk_image);
            mTvApkName = (TextView) itemView.findViewById(R.id.tv_apk_name);
            mTvApkVersion = (TextView) itemView.findViewById(R.id.tv_apk_version);
            mBtnApkInstall = (Button) itemView.findViewById(R.id.btn_install);
            mPbInstall = (ProgressBar) itemView.findViewById(R.id.pb_install);
        }
    }

    private void deleteOrDownload(View view) {
        Object object = view.getTag();
        if (object instanceof Integer) {
            int position = (int) object;
            final DUApkInfoResultEx duApkInfoResultEx = mDUApkInfoResultExList.get(position);
            String packageName = duApkInfoResultEx.getPackageName();
            if (duApkInfoResultEx.getState() == DUApkInfoResultEx.STATE_INSTALLED) {
                //ApplicationsUtil.showMessage(ApkInstallationActivity.this, R.string.text_installed);
                if (TextUtil.isNullOrEmpty(packageName)
                        || getPackageName().equals(packageName)) {
                    ApplicationsUtil.showMessage(ApkInstallationActivity.this,
                            R.string.text_apk_not_uninstall);
                } else {
                    deleteApk(packageName);
                }

                return;
            }

            final boolean isUsingReservedAddress = mApkInstallationPresenter.isUsingReservedAddress();
            new MaterialDialog.Builder(this)
                    .title(R.string.text_prompt)
                    .content(R.string.text_is_downloading_newly)
                    .positiveText(R.string.text_ok)
                    .negativeText(R.string.text_cancel)
                    .cancelable(false)
                    .onPositive(new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(@NonNull MaterialDialog materialDialog,
                                            @NonNull DialogAction dialogAction) {
                            downloadFiles(duApkInfoResultEx.getAppName(), duApkInfoResultEx.getAppId(),
                                    duApkInfoResultEx.getPackageName(), duApkInfoResultEx.getVersionCode(),
                                    duApkInfoResultEx.getVersionName(),
                                    isUsingReservedAddress ? duApkInfoResultEx.getAppInnerUrl() : duApkInfoResultEx.getAppOuterUrl(),
                                    isUsingReservedAddress ? duApkInfoResultEx.getDataInnerUrl() : duApkInfoResultEx.getDataOuterUrl(),
                                    true);
                        }
                    })
                    .onNegative(new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(@NonNull MaterialDialog materialDialog,
                                            @NonNull DialogAction dialogAction) {
                            downloadFiles(duApkInfoResultEx.getAppName(), duApkInfoResultEx.getAppId(),
                                    duApkInfoResultEx.getPackageName(), duApkInfoResultEx.getVersionCode(),
                                    duApkInfoResultEx.getVersionName(),
                                    isUsingReservedAddress ? duApkInfoResultEx.getAppInnerUrl() : duApkInfoResultEx.getAppOuterUrl(),
                                    isUsingReservedAddress ? duApkInfoResultEx.getDataInnerUrl() : duApkInfoResultEx.getDataOuterUrl(),
                                    false);
                        }
                    })
                    .show();
        }
    }

    private void deleteApk(String packageName) {
        try {
            Uri packageURI = Uri.parse("package:" + packageName);
            Intent intent = new Intent(Intent.ACTION_DELETE);
            intent.setData(packageURI);
            startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
            LogUtil.e(TAG, "---onItemClick---" + e.getMessage());
        }
    }
}
