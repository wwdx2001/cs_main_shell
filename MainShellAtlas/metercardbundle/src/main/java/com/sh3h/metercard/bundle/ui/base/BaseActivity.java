package com.sh3h.metercard.bundle.ui.base;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.inputmethod.InputMethodManager;

import com.sh3h.metercard.bundle.MCBApplication;
import com.sh3h.metercard.bundle.R;
import com.sh3h.metercard.bundle.injection.component.ActivityComponent;
import com.sh3h.metercard.bundle.injection.module.ActivityModule;
import com.sh3h.metercard.bundle.injection.component.DaggerActivityComponent;

public class BaseActivity extends AppCompatActivity {
    private ActivityComponent mActivityComponent;
    private boolean mStartAnimation;
    private boolean mEndAnimation;
    //private AlertDialog mDialog;
    private Toolbar mToolbar;
    private ProgressDialog mProgressDialog;

    public BaseActivity() {
        mActivityComponent = null;
        mStartAnimation = false;
        mEndAnimation = false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setForwardAnimation();

    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);

        if (mStartAnimation) {
            // 设置切换动画，从右边进入，左边退出
            overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
        }
    }

    @Override
    public void startActivityForResult(Intent intent, int requestCode) {
        super.startActivityForResult(intent, requestCode);

        if (mStartAnimation) {
            // 设置切换动画，从右边进入，左边退出
            overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
        }
    }

    @Override
    public void finish() {
        super.finish();

        if (mEndAnimation) {
            // 设置切换动画，从左边进入，右边退出
            overridePendingTransition(R.anim.in_from_left, R.anim.out_to_right);
        }
    }

//    public Toolbar initToolBar(int resId){
//        mToolbar = (Toolbar) findViewById(R.id.toolbar);
//        mToolbar.setNavigationIcon(R.mipmap.arrow);
//        mToolbar.setTitle(resId);
//        setSupportActionBar(mToolbar);
//        return mToolbar;
//    }

//    /**
//     * 子标题
//     * @param title
//     * @param subTitle
//     */
//    public Toolbar initToolBar(int title,int subTitle){
//        mToolbar = (Toolbar) findViewById(R.id.toolbar);
//        mToolbar.setNavigationIcon(R.mipmap.arrow);
//        mToolbar.setTitle(title);
//        mToolbar.setSubtitle(subTitle);
//        setSupportActionBar(mToolbar);
//        return mToolbar;
//    }

    public void setToolBarSubTitle(int subTitle){
        mToolbar.setSubtitle(subTitle);
    }

    public void setToolBarSubTitle(CharSequence subTitle){
        mToolbar.setSubtitle(subTitle);
    }

    public ActivityComponent getActivityComponent() {
        if (mActivityComponent == null) {
            mActivityComponent = DaggerActivityComponent.builder()
                    .activityModule(new ActivityModule(this))
                    .applicationComponent(MCBApplication.get(this).getComponent())
                    .build();

        }

        return mActivityComponent;
    }


    public void setStartAnimation(boolean animation) {
        mStartAnimation = animation;
    }

    public void setEndAnimation(boolean animation) {
        mEndAnimation = animation;
    }

    public void destroy() {
        finish();
    }

    public void setForwardAnimation() {
        setStartAnimation(true);
        setEndAnimation(false);
    }

    public void setBackwardAnimation() {
        setStartAnimation(false);
        setEndAnimation(true);
    }

    public void setBothAnimation() {
        setStartAnimation(true);
        setEndAnimation(true);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_DOWN) {
            finish();
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }

    public void showProgress(String message) {
        if(mProgressDialog == null ){
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        }
        mProgressDialog.setMessage(message);
        mProgressDialog.show();
    }

    public void hideProgress() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
            mProgressDialog = null;
        }
    }

    protected void hideKeyboard(IBinder binder, int flags) {
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(binder, flags);
    }

}

