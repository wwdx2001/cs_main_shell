package com.sh3h.metercard.bundle.ui.information;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sh3h.dataprovider.data.entity.DUHuanBiaoJL;
import com.sh3h.dataprovider.data.entity.DUHuanBiaoJLInfo;
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
 * 换表信息
 */
public class ChangeTableFragment extends ParentFragment implements SwipeRefreshLayout.OnRefreshListener, ChangeTableMvpView {

    @Inject
    ChangeTablePresenter mPresenter;

    @BindView(R2.id.recycler_view_change_table)
    RecyclerView mRecyclerView;

    @BindView(R2.id.swipe_refresh_layout)
    SwipeRefreshLayout mSwipeRefreshLayout;

    private View mView;
    private Unbinder mUnbinder;
    private SimpleArrayRecyclerAdapter mRecyclerAdapter;

    private List<DUHuanBiaoJL> mArrayList;
    private boolean isRefreshing = true;

    public ChangeTableFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((BaseActivity) getActivity()).getActivityComponent().inject(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_chang_table, container, false);
        mUnbinder = ButterKnife.bind(this, mView);
        mPresenter.attachView(this);
        initSwipeRefreshLayout();
        initRecyclerView();
        initData();
        return mView;
    }

    private void initRecyclerView() {
        mArrayList = new ArrayList();
       /* for (int i = 0; i < 20; i++) {
            HuanBiaoJL huanBiaoJL = new HuanBiaoJL("1", "2", "3", 23, "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", 6
                    , 3, 4, 5, 23, "23", 1234, "1234", "123", "23456");
            mArrayList.add(huanBiaoJL);
        }*/
        mRecyclerAdapter = new SimpleArrayRecyclerAdapter(0, mArrayList);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mRecyclerView.getContext(), LinearLayoutManager.VERTICAL, false));
        mRecyclerView.setAdapter(mRecyclerAdapter);
    }

    private void initData() {
        Bundle _bundle = getArguments();
        if (_bundle == null || TextUtil.isNullOrEmpty(_bundle.getString(ConstDataUtil.S_CID))) {
            return;
        }
        DUHuanBiaoJLInfo duHuanBiaoJLInfo = new DUHuanBiaoJLInfo();
        duHuanBiaoJLInfo.setLocal(true);
        duHuanBiaoJLInfo.setCustomerId(_bundle.getString(ConstDataUtil.S_CID));
        mPresenter.getHuanBiaoXXs(duHuanBiaoJLInfo);
    }


    private void initSwipeRefreshLayout() {
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.holo_blue_bright);
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
    public void onGetHuanBiaoXXs(List<DUHuanBiaoJL> duHuanBiaoJLs) {
        mArrayList = duHuanBiaoJLs;
        mRecyclerAdapter.setNewData(duHuanBiaoJLs);
        if (isRefreshing) {
            mSwipeRefreshLayout.setRefreshing(false);
            isRefreshing = !isRefreshing;
        }
    }

    private class SimpleArrayRecyclerAdapter extends BaseRecyclerViewAdapter {
        public SimpleArrayRecyclerAdapter(int layoutResId, List data) {
            super(R.layout.item_huanbiaojl, data);
        }

        @Override
        protected void convert(BaseViewHolder holder, Object item) {
            DUHuanBiaoJL duhuanBiaoJL = (DUHuanBiaoJL) item;
            holder.setText(R.id.tv_huanbiaorqn, TextUtil.format(duhuanBiaoJL.getHuanbiaohtrq(),
                    TextUtil.FORMAT_DATE_YEAR));//换表年份
            holder.setText(R.id.tv_huanbiaorqyr, TextUtil.format(duhuanBiaoJL.getHuanbiaohtrq(),
                    TextUtil.FORMAT_DATE_NO_YEAR_SLASH));//换表日期
            holder.setText(R.id.tv_xinbiaobh, String.valueOf(duhuanBiaoJL.getXinbiaobh()));//新表编号
            holder.setText(R.id.tv_xinbiaoqima,
                    String.valueOf(duhuanBiaoJL.getXinbiaodm()));//新表起码
            holder.setText(R.id.tv_jiubiaozs,
                    String.valueOf(duhuanBiaoJL.getJiubiaocm()));//旧表指数
            holder.setText(R.id.tv_huanbiaoyuan, String.valueOf(duhuanBiaoJL.getHuitianr() != null ?
                    String.valueOf(duhuanBiaoJL.getHuitianr()) : TextUtil.EMPTY));//换表员
        }
    }
}
