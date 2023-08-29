package com.sh3h.metercard.bundle.ui.information;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sh3h.dataprovider.data.entity.DUJiaoFeiXX;
import com.sh3h.dataprovider.data.entity.DUJiaoFeiXXInfo;
import com.sh3h.metercard.bundle.R;
import com.sh3h.metercard.bundle.R2;
import com.sh3h.metercard.bundle.ui.adapter.BaseRecyclerViewAdapter;
import com.sh3h.metercard.bundle.ui.adapter.BaseViewHolder;
import com.sh3h.metercard.bundle.ui.base.BaseActivity;
import com.sh3h.metercard.bundle.ui.base.ParentFragment;
import com.sh3h.metercard.bundle.util.ConstDataUtil;
import com.sh3h.mobileutil.util.TextUtil;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


/**
 * 近期缴费信息
 */
public class RecentPayFragment extends ParentFragment
        implements SwipeRefreshLayout.OnRefreshListener, RecentPayMvpView {

    @Inject
    RecentPayPresenter mRecentPayPresenter;

    @BindView(R2.id.recycler_view_recent_pay)
    RecyclerView mRecyclerView;

    @BindView(R2.id.swipe_refresh_layout)
    SwipeRefreshLayout mSwipeRefreshLayout;//刷新控件

    private Unbinder mUnbinder;

    private View mView;//页面视图
    private List<DUJiaoFeiXX> mArrayList;//数据

    private SimpleArrayRecyclerAdapter mRecyclerAdapter;//适配器

    private boolean isRefreshing = true;

    public RecentPayFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((BaseActivity) getActivity()).getActivityComponent().inject(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_recent_pay, container, false);
        mUnbinder = ButterKnife.bind(this, mView);
        mRecentPayPresenter.attachView(this);
        initSwipeRefreshLayout();
        initRecyclerView();
        initData();
        return mView;
    }

    private void initData() {
        Bundle _bundle = getArguments();
        if (_bundle == null || TextUtil.isNullOrEmpty(_bundle.getString(ConstDataUtil.S_CID))) {
            return;
        }
        DUJiaoFeiXXInfo duJiaoFeiXXInfo = new DUJiaoFeiXXInfo();
        duJiaoFeiXXInfo.setLocal(true);
        duJiaoFeiXXInfo.setCustomerId(_bundle.getString(ConstDataUtil.S_CID));
        mRecentPayPresenter.getJiaoFeiXXs(duJiaoFeiXXInfo);
    }

    private void initRecyclerView() {
        mArrayList = new ArrayList();
        mRecyclerAdapter = new SimpleArrayRecyclerAdapter(0, mArrayList);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mRecyclerView.getContext(), LinearLayoutManager.VERTICAL, false));
        mRecyclerView.setAdapter(mRecyclerAdapter);
    }

    private void initSwipeRefreshLayout() {
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.holo_blue_bright);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        mUnbinder.unbind();
        mRecentPayPresenter.detachView();
    }


    @Override
    public void onRefresh() {
        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onError(String message) {

    }

    @Override
    public void onGetJiaoFeiXXs(List<DUJiaoFeiXX> duJiaoFeiXXes) {
        mArrayList = duJiaoFeiXXes;
        mRecyclerAdapter.setNewData(duJiaoFeiXXes);
        if (isRefreshing) {
            mSwipeRefreshLayout.setRefreshing(false);
            isRefreshing = !isRefreshing;
        }
    }

    @Override
    protected void lazyLoad() {

    }

    private class SimpleArrayRecyclerAdapter extends BaseRecyclerViewAdapter {

        public SimpleArrayRecyclerAdapter(int layoutResId, List data) {
            super(R.layout.item_jinqijf, data);
        }

        @Override
        protected void convert(BaseViewHolder holder, Object item) {
            DUJiaoFeiXX dujiaoFeiXX = (DUJiaoFeiXX) item;
            holder.setText(R.id.tv_jiaofeirqn, TextUtil.format(dujiaoFeiXX.getShoufeirq(),
                    TextUtil.FORMAT_DATE_YEAR));//缴费年份
            holder.setText(R.id.tv_jiaofeirqyr, TextUtil.format(dujiaoFeiXX.getShoufeirq(),
                    TextUtil.FORMAT_DATE_NO_YEAR_SLASH));//缴费日期
            holder.setText(R.id.tv_zhangwuny, String.valueOf(dujiaoFeiXX.getZhangwuny()));//账务年月
            holder.setText(R.id.tv_feeid, String.valueOf(dujiaoFeiXX.getFeeid()));//水费编号
            holder.setText(R.id.tv_zhangdanje,
                    holder.getView(R.id.tv_zhangdanje).getResources().getString(R.string.label_renminbi)
                            + _f.format(dujiaoFeiXX.getJe()));//账单金额
            holder.setText(R.id.tv_weiyueje,
                    holder.getView(R.id.tv_weiyueje).getResources().getString(R.string.label_renminbi)
                            + _f.format(dujiaoFeiXX.getShishouwyj()));//违约金额
            holder.setText(R.id.tv_shishouje,
                    holder.getView(R.id.tv_shishouje).getResources().getString(R.string.label_renminbi)
                            + _f.format(dujiaoFeiXX.getShishouzje()
                    ));//实收金额
        }
    }
}
