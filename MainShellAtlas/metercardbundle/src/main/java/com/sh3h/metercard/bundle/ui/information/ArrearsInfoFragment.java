package com.sh3h.metercard.bundle.ui.information;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sh3h.dataprovider.data.entity.DUQianFeiXX;
import com.sh3h.dataprovider.data.entity.DUQianFeiXXInfo;
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
 * 欠费信息
 */
public class ArrearsInfoFragment extends ParentFragment
        implements SwipeRefreshLayout.OnRefreshListener, ArrearsInfoMvpView {

    private View mView;
    private Unbinder mUnbinder;

    @Inject
    ArrearsInfoPresenter mPresenter;

    @BindView(R2.id.swipe_refresh_layout)
    SwipeRefreshLayout mSwipeRefreshLayout;

    @BindView(R2.id.recycler_view_arrears_info)
    RecyclerView mRecyclerView;

    private SimpleArrayRecyclerAdapter mRecyclerAdapter;
    private List<DUQianFeiXX> mArrayList;
    private boolean isRefreshing = true;

    public ArrearsInfoFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((BaseActivity) getActivity()).getActivityComponent().inject(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_arrears_info, container, false);
        mUnbinder = ButterKnife.bind(this, mView);
        mPresenter.attachView(this);
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
        DUQianFeiXXInfo duQianFeiXXInfo = new DUQianFeiXXInfo();
        duQianFeiXXInfo.setLocal(true);
        duQianFeiXXInfo.setCustomerId(_bundle.getString(ConstDataUtil.S_CID));
        mPresenter.getQianFeiXXs(duQianFeiXXInfo);
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
    public void onGetQianFeiXXs(List<DUQianFeiXX> duQianFeiXXList) {
        mArrayList = duQianFeiXXList;
        mRecyclerAdapter.setNewData(duQianFeiXXList);
        if (isRefreshing) {
            mSwipeRefreshLayout.setRefreshing(false);
            isRefreshing = !isRefreshing;
        }
    }

    private class SimpleArrayRecyclerAdapter extends BaseRecyclerViewAdapter {

        public SimpleArrayRecyclerAdapter(int layoutResId, List data) {
            super(R.layout.item_qianfeixx, data);
        }

        @Override
        protected void convert(BaseViewHolder holder, Object item) {
            DUQianFeiXX duQianFeiXX = (DUQianFeiXX) item;
            holder.setText(R.id.tv_zhangwuny, String.valueOf(duQianFeiXX.getZhangwuny()));//账务年月
            holder.setText(R.id.tv_chaoci, String.valueOf(duQianFeiXX.getChaoci()));//抄次
            holder.setText(R.id.tv_shuiliang, String.valueOf(duQianFeiXX.getKaizhangsl()));//水量
            holder.setText(R.id.tv_je, String.valueOf(duQianFeiXX.getJe()));//金额
        }
    }
}
