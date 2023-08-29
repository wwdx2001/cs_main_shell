
package com.sh3h.metercard.bundle.ui.search;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.sh3h.metercard.bundle.R;
import com.sh3h.metercard.bundle.R2;
import com.sh3h.metercard.bundle.ui.adapter.BaseRecyclerViewAdapter;
import com.sh3h.metercard.bundle.ui.adapter.BaseViewHolder;
import com.sh3h.metercard.bundle.ui.base.ParentActivity;
import com.sh3h.metercard.bundle.ui.information.CustomerInformationActivity;
import com.sh3h.metercard.bundle.util.ConstDataUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class SearchResultActivity extends ParentActivity implements
        SwipeRefreshLayout.OnRefreshListener,
        BaseRecyclerViewAdapter.RequestLoadMoreListener, View.OnClickListener {

    private static final String TAG = "SearchResultActivity";
    @BindView(R2.id.search_result_swiperefresh_layout)
    SwipeRefreshLayout mSwipeRefreshLayout;

    @BindView(R2.id.search_result_recycler_view)
    RecyclerView mRecyclerView;

    @BindView(R2.id.search_result_floating_button)
    FloatingActionButton mActionButton;

    @BindView(R2.id.search_result_empty_layout)
    View mEmptyView;

    @BindView(R2.id.search_result_coordinator_layout)
    CoordinatorLayout mLayout;

    Toolbar mToolbar;

    private ArrayList<SearchItem> searchItems;
    private String searchSelection;//查询条件
    private String searchType;//查询类型，全部，手机号.....
    private MyRecyclerViewAdapter adapter;
    private static final int PAGE_SIZE = 10;//每页显示的数据个数
    private int currentPosition = 0;//当前显示的数据总数
    private final int totalSize = 100;//可以显示查询结果的总数
    private int delayMillis = 1000;
    private Unbinder unBinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);
        if (getIntent() != null) {
            searchSelection = getIntent().getStringExtra(ConstDataUtil.SEARCH_SELECTION);
            searchType = getIntent().getStringExtra(ConstDataUtil.SEARCH_TYPE);
        }
        getActivityComponent().inject(this);
        unBinder = ButterKnife.bind(this);
        initToolBar(R.string.search_result);
        initSwipeRefreshLayout();
        initData();
        initListener();
        //toTop();
    }

    private void initSwipeRefreshLayout() {
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.holo_blue_bright);
    }

    private void initData() {
        searchItems = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            SearchItem searchItem = new SearchItem(String.valueOf(i), String.valueOf(i),
                    "23456", "245789", "23456", "13467");
            searchItems.add(searchItem);
        }
        getSupportActionBar().setSubtitle(totalSize + getResources().
                getString(R.string.search_result_count_unit));
        if (searchItems.size() <= 0) {
            mEmptyView.setVisibility(View.VISIBLE);
        } else {
            mEmptyView.setVisibility(View.INVISIBLE);
        }
        adapter = new MyRecyclerViewAdapter(R.layout.search_result_item,
                getObjects(searchItems, currentPosition));
        adapter.openLoadAnimation();
        currentPosition = adapter.getData().size();
        adapter.setLoadingViewId(R.layout.def_loading);
        adapter.setOnLoadMoreListener(this);
        adapter.openLoadMore(PAGE_SIZE, true);//or call mQuickAdapter.setPageSize(PAGE_SIZE);  mQuickAdapter.openLoadMore(true);
        adapter.setOnRecyclerViewItemClickListener(
                new BaseRecyclerViewAdapter.OnRecyclerViewItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        //recyclerView的点击事件
                        toCustomerInfoActivity(position);
                    }
                });
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(adapter);
        //添加滚动事件
        mRecyclerView.addOnScrollListener(
                new RecyclerView.OnScrollListener() {
                    @Override
                    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                        super.onScrollStateChanged(recyclerView, newState);
                        LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                        int firstItemPosition = layoutManager.findFirstVisibleItemPosition();
                        if (firstItemPosition > 10 && newState == 0) {
                            mActionButton.setVisibility(View.VISIBLE);
                        } else {
                            mActionButton.setVisibility(View.GONE);
                        }
                    }
                }
        );
    }

    /**
     * 跳转值详情界面
     *
     * @param position
     */
    private void toCustomerInfoActivity(int position) {
        Intent intent = new Intent(SearchResultActivity.this,
                CustomerInformationActivity.class);
        intent.putExtra(ConstDataUtil.S_CID, searchItems.get(position));
        startActivity(intent);
    }

    private void initListener() {
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mActionButton.setOnClickListener(this);
    }

    /**
     * 获取去之后的PageSize个object
     *
     * @return
     */
    private List getObjects(List lists, int currentpositon) {
        List subList = new ArrayList();
        if (lists.size() > 0) {
            subList = new ArrayList();
            for (int i = currentpositon; i < currentpositon + PAGE_SIZE; i++) {
                if (i < totalSize) {
                    subList.add(lists.get(i));
                } else {
                    break;
                }
            }
        }
        return subList;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unBinder.unbind();
    }

    /**
     * 刷新事件
     */
    @Override
    public void onRefresh() {
        mSwipeRefreshLayout.setRefreshing(false);
    }

    /**
     * 加载更多
     */
    @Override
    public void onLoadMoreRequested() {
        mRecyclerView.post(new Runnable() {
            @Override
            public void run() {
                if (currentPosition >= totalSize) {
                    adapter.notifyDataChangedAfterLoadMore(false);
                } else {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            adapter.notifyDataChangedAfterLoadMore(getObjects(searchItems, currentPosition), true);
                            currentPosition = adapter.getData().size();
                        }
                    }, delayMillis);
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R2.id.search_result_floating_button:
                swipeToTop();
                break;
        }
    }

    /**
     * recyclerView滑至顶部
     */
    private void swipeToTop() {
        mRecyclerView.smoothScrollToPosition(0);//滑到顶部
    }

    private class MyRecyclerViewAdapter extends BaseRecyclerViewAdapter {

        public MyRecyclerViewAdapter(int layoutResId, List data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, Object item) {
            if (item instanceof SearchItem) {
                SearchItem subItem = (SearchItem) item;
                helper.setText(R.id.search_result_cebenhao, subItem.getString1());
                helper.setText(R.id.search_result_ceneixuhao, subItem.getString2());
                helper.setText(R.id.search_result_tv_hu_ming, subItem.getString3());
                helper.setText(R.id.search_result_tv_address, subItem.getString4());
                helper.setText(R.id.search_result_table_number, subItem.getString5());
                helper.setText(R.id.search_result_table_gyh, subItem.getString6());
            }
        }
    }

    public void initToolBar(int resId) {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setNavigationIcon(R.mipmap.ic_keyboard_backspace_white_24dp);
        mToolbar.setNavigationIcon(R.mipmap.arrow);
        mToolbar.setTitle(resId);
        setSupportActionBar(mToolbar);
    }
}
