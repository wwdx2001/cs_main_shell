package com.sh3h.metercard.bundle.ui.information;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sh3h.dataprovider.data.entity.DUChaoBiaoJL;
import com.sh3h.dataprovider.data.entity.DUChaoBiaoJLInfo;
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
 * 近期抄表信息
 */
public class RecentMeterReadingFragment extends ParentFragment
        implements SwipeRefreshLayout.OnRefreshListener, RecentMeterReadingMvpView {

    @Inject
    RecentMeterReadingPresent mPresenter;

    @BindView(R2.id.recycler_view_recent_meter_reading)
    RecyclerView mRecyclerView;

    @BindView(R2.id.swipe_refresh_layout)
    SwipeRefreshLayout mSwipeRefreshLayout;

    private Unbinder mUnbinder;

    private View mView;
    private SimpleArrayRecyclerAdapter mRecyclerAdapter;

    private List<DUChaoBiaoJL> mArrayList;
    private boolean isRefreshing = true;

    public RecentMeterReadingFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((BaseActivity) getActivity()).getActivityComponent().inject(this);
        mPresenter.attachView(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_recent_meter_reading, container, false);
        mUnbinder = ButterKnife.bind(this, mView);
        initSwipeRefreshLayout();
        initRecyclerView();
        initData();
        return mView;
    }

    private void initSwipeRefreshLayout() {
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.holo_blue_bright);
    }

    private void initRecyclerView() {
        mArrayList = new ArrayList();
        mRecyclerAdapter = new SimpleArrayRecyclerAdapter(0, mArrayList);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mRecyclerView.getContext(), LinearLayoutManager.VERTICAL, false));
        mRecyclerView.setAdapter(mRecyclerAdapter);
    }


    private void initData() {
        Bundle _bundle = getArguments();
        if (_bundle == null || TextUtil.isNullOrEmpty(_bundle.getString(ConstDataUtil.S_CID))) {
            return;
        }
        DUChaoBiaoJLInfo duChaoBiaoJLInfo = new DUChaoBiaoJLInfo();
        duChaoBiaoJLInfo.setLocal(true);
        duChaoBiaoJLInfo.setCustomerId(_bundle.getString(ConstDataUtil.S_CID));
        mPresenter.getChaoBiaoJL(duChaoBiaoJLInfo);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mUnbinder.unbind();
        mPresenter.detachView();
    }

    @Override
    protected void lazyLoad() {

    }

    @Override
    public void onRefresh() {
        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onError(String message) {

    }

    @Override
    public void onGetChaoBiaoJLFinish(List<DUChaoBiaoJL> duChaoBiaoJLs) {
        mArrayList = duChaoBiaoJLs;
        mRecyclerAdapter.setNewData(duChaoBiaoJLs);
        if (isRefreshing) {
            mSwipeRefreshLayout.setRefreshing(false);
            isRefreshing = !isRefreshing;
        }
    }


    private class SimpleArrayRecyclerAdapter extends BaseRecyclerViewAdapter {

        public SimpleArrayRecyclerAdapter(int layoutResId, List data) {
            super(R.layout.item_recent_reading, data);
        }

        @Override
        protected void convert(BaseViewHolder holder, Object item) {
            DUChaoBiaoJL duchaoBiaoJL = (DUChaoBiaoJL) item;
            holder.setText(R2.id.tv_chaobiaorqn, TextUtil.format(duchaoBiaoJL.getChaobiaorq(),
                    TextUtil.FORMAT_DATE_YEAR));//抄表年
            String huanBiaoRqyr = TextUtil.format(duchaoBiaoJL.getChaobiaorq(),
                    TextUtil.FORMAT_DATE_NO_YEAR_SLASH);
            holder.setText(R2.id.tv_chaobiaorqyr, huanBiaoRqyr);//抄表日期（无年份）
            holder.setText(R2.id.tv_bencicm, (
                    String.valueOf(((DUChaoBiaoJL) item).getBencicm())));//本次抄码
            holder.setText(R2.id.tv_shangcicm, String.valueOf(duchaoBiaoJL.getShangcicm()));//上次抄码
            holder.setText(R2.id.tv_chaojiansl, String.valueOf(duchaoBiaoJL.getChaojiansl()));//抄见水量
            holder.setText(R2.id.tv_chaobiaozt, String.valueOf(
                    duchaoBiaoJL.getChaobiaozt().trim()));//抄表状态
        }
    }
}
