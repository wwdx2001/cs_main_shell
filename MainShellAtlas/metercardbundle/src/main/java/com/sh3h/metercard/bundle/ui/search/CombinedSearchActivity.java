package com.sh3h.metercard.bundle.ui.search;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.sh3h.metercard.bundle.R;
import com.sh3h.metercard.bundle.R2;
import com.sh3h.metercard.bundle.ui.base.ParentActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class CombinedSearchActivity extends ParentActivity implements
        View.OnClickListener {

    private Unbinder mUnbinder;

    @BindView(R2.id.edit_text_address)
    EditText mAddress;  //地址

    @BindView(R2.id.edit_text_card_id)
    EditText mCardId;  //表号

    @BindView(R2.id.edit_text_customer_name)
    EditText mCustomerID;  //户号

    @BindView(R2.id.edit_text_telephone)
    EditText mTelephone;  //电话

    @BindView(R2.id.edit_text_volume)
    EditText mVolumn;  //册本

    @BindView(R2.id.tv_search_result)
    TextView mSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_combined_search);
        getActivityComponent().inject(this);
        initToolBar(R.string.combined_search_activity);
        mUnbinder = ButterKnife.bind(this);
        //toTop();
        //mSearch.setOnClickListener(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mUnbinder.unbind();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R2.id.tv_search_result:
                Intent intent = new Intent(this,SearchResultActivity.class);
                startActivity(intent);
                break;
        }
    }
}
