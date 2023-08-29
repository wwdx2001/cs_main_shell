package com.sh3h.metercard.bundle.ui.information;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.sh3h.metercard.bundle.R;
import com.sh3h.metercard.bundle.R2;
import com.sh3h.metercard.bundle.ui.base.ParentActivity;
import com.sh3h.metercard.bundle.util.ConstDataUtil;
import com.sh3h.mobileutil.util.TextUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class CustomerInformationActivity extends ParentActivity {

    @BindView(R2.id.customer_info_toolbar)
    Toolbar mToolbar;
    @BindView(R2.id.tab_layout_customer_info)
    TabLayout mTabLayout;
    @BindView(R2.id.customer_info_viewpager)
    ViewPager mViewPager;

    private Unbinder unBinder;
    private CustomInfoViewPageAdapter viewPageAdapter;
    private Bundle bundle;

    private String mCh;
    private String mCid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_information);
        getActivityComponent().inject(this);
        unBinder = ButterKnife.bind(this);
        initViews();
        initTitle();
        initTabLayout();
        bundle = getBundle();
        //toTop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unBinder.unbind();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_customer_info, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R2.id.mci_action_update:
                //加载数据
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void initViews() {
        if (mToolbar == null) {
            mToolbar = (Toolbar)findViewById(R.id.customer_info_toolbar);
        }

        if (mTabLayout == null) {
            mTabLayout = (TabLayout)findViewById(R.id.tab_layout_customer_info);
        }

        if (mViewPager == null) {
            mViewPager = (ViewPager)findViewById(R.id.customer_info_viewpager);
        }
    }

    private void initTabLayout() {
        viewPageAdapter = new CustomInfoViewPageAdapter(getSupportFragmentManager(), bundle);
        mViewPager.setAdapter(viewPageAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
    }

    private void initTitle() {
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(getResources().getString(R.string.customer_info));
    }

    private Bundle getBundle() {
        Intent intent = getIntent();
        mCh = TextUtil.getString(intent.getStringExtra(ConstDataUtil.S_CH));
        mCid = TextUtil.getString(intent.getStringExtra(ConstDataUtil.S_CID));

        Bundle bundle = new Bundle();
        bundle.putString(ConstDataUtil.S_CID, mCid);
        bundle.putString(ConstDataUtil.S_CH, mCh);
        return bundle;
    }

    private class CustomInfoViewPageAdapter extends FragmentPagerAdapter {

        private static final int BASICINFORMATION_FRAGMENT = 0;//基本信息
        private static final int READWATER_FRAGMENT = 1;//近期抄表
        private static final int READPAY_FRAGMENT = 2;//近期缴费
        private static final int ARREARSWATER_FRAGMENT = 3;//欠费信息
        private static final int CHANGEWATER_FRAGMENT = 4;//换表信息

        private static final int COUNT = 5;
        private Bundle mBundle;

        public CustomInfoViewPageAdapter(FragmentManager fm, Bundle bundle) {
            super(fm);
            mBundle = bundle;
        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragment = null;
            switch (position) {
                case BASICINFORMATION_FRAGMENT:
                    fragment = new BasicInformationFragment();
                    break;
                case READWATER_FRAGMENT:
                    fragment = new RecentMeterReadingFragment();
                    break;
                case READPAY_FRAGMENT:
                    fragment = new RecentPayFragment();
                    break;
                case ARREARSWATER_FRAGMENT:
                    fragment = new ArrearsInfoFragment();
                    break;
                case CHANGEWATER_FRAGMENT:
                    fragment = new ChangeTableFragment();
                    break;
            }
            if (mBundle != null) {
                fragment.setArguments(mBundle);
            }
            return fragment;
        }

        @Override
        public int getCount() {
            return COUNT;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            String title = null;
            switch (position) {
                case BASICINFORMATION_FRAGMENT:
                    title = getResources().getString(R.string.label_basic_information);
                    break;
                case READWATER_FRAGMENT:
                    title = getResources().getString(R.string.label_recent_meter_reading);
                    break;
                case READPAY_FRAGMENT:
                    title = getResources().getString(R.string.label_recent_pay);
                    break;
                case ARREARSWATER_FRAGMENT:
                    title = getResources().getString(R.string.label_arrears_info);
                    break;
                case CHANGEWATER_FRAGMENT:
                    title = getResources().getString(R.string.label_change_table_info);
                    break;
            }
            return title;
        }
    }
}
