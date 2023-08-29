package com.sh3h.metercard.bundle.ui.demo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.TextView;

import com.sh3h.metercard.bundle.R;
import com.sh3h.metercard.bundle.R2;
import com.sh3h.metercard.bundle.ui.base.ParentActivity;
import com.sh3h.metercard.bundle.ui.information.CustomerInformationActivity;
import com.sh3h.metercard.bundle.ui.search.CombinedSearchActivity;
import com.sh3h.metercard.bundle.ui.setting.SettingActivity;
import com.sh3h.metercard.bundle.ui.test.TestActivity;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


public class DemoActivity extends ParentActivity implements DemoMvpView {

    @BindView(R2.id.tv_demo)
    TextView mTextView;

    @BindView(R2.id.cv_test)
    CardView mCardView;

    @Inject
    DemoPresenter mDemoPresenter;

    private Unbinder mUnbinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo);

        getActivityComponent().inject(this);
        initToolBar(R.string.text_demo);
        mUnbinder = ButterKnife.bind(this);
        mDemoPresenter.attachView(this);
        checkPermissions();

        if (mTextView == null) {
            mTextView = (TextView)findViewById(R.id.tv_demo);
        }
        mTextView.setText("hello meter card");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mUnbinder.unbind();
        mDemoPresenter.detachView();
    }

    public void onClick(View view) {
        Intent intent = null;
        int id = view.getId();
        if (id == R.id.button_combined_search) {
            intent = new Intent(this, CombinedSearchActivity.class);
            //intent = new Intent();
            //intent.setClassName(this, "com.sh3h.metercard.bundle.ui.search.CombinedSearchActivity");
        } else if (id == R.id.button_customer_info) {
            intent = new Intent(this, CustomerInformationActivity.class);
        } else if (id == R.id.button_to_setting) {
            intent = new Intent(this, SettingActivity.class);
        } else {
            intent = new Intent(this, SettingActivity.class);
        }
//        switch (view.getId()) {
//            case R.id.button_combined_search:
//
//                break;
//            case R.id.button_customer_info:
//                //intent.setClassName(this, "com.sh3h.metercard.bundle.ui.information.CustomerInformationActivity");
//                intent = new Intent(this, CustomerInformationActivity.class);
//                break;
//            case R.id.button_to_setting:
//                //intent.setClassName(this, "com.sh3h.metercard.bundle.ui.setting.SettingActivity");
//                intent = new Intent(this, SettingActivity.class);
//                break;
//            default:
//                //intent.setClassName(this, "com.sh3h.metercard.bundle.ui.demo.DemoActivity");
//                intent = new Intent(this, SettingActivity.class);
//                break;
//        }

        startActivity(intent);
    }
}
