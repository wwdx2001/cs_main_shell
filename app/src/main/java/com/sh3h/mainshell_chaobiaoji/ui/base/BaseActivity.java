package com.sh3h.mainshell_chaobiaoji.ui.base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.inputmethod.InputMethodManager;

import com.afollestad.materialdialogs.MaterialDialog;
import com.sh3h.mainshell_chaobiaoji.MainApplication;
import com.sh3h.mainshell_chaobiaoji.R;
import com.sh3h.mainshell_chaobiaoji.injection.component.ActivityComponent;
import com.sh3h.mainshell_chaobiaoji.injection.component.DaggerActivityComponent;
import com.sh3h.mainshell_chaobiaoji.injection.module.ActivityModule;
import com.umeng.analytics.MobclickAgent;

//import ly.count.android.sdk.Countly;

public class BaseActivity extends AppCompatActivity {
    private ActivityComponent mActivityComponent;
    private boolean mStartAnimation;
    private boolean mEndAnimation;
    private static MaterialDialog mMaterialDialog = null;
    //private boolean mIsCountlyStarted;

    public BaseActivity() {
        mActivityComponent = null;
        mStartAnimation = true;
        mEndAnimation = true;
        //mIsCountlyStarted = false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setForwardAnimation();
        MainApplication.get(this).addActivity(this);
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (MainApplication.get(this).isMonitorInit()) {
            switch (MainApplication.get(this).getMonitorType()) {
                case NONE:
                    break;
                case COUTLY:
                    //Countly.sharedInstance().onStart(this);
                    //mIsCountlyStarted = true;
                    break;
                case UMENG:
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (MainApplication.get(this).isMonitorInit()) {
            switch (MainApplication.get(this).getMonitorType()) {
                case NONE:
                    break;
                case COUTLY:
                    break;
                case UMENG:
                    MobclickAgent.onPageStart(getClass().getName());
                    MobclickAgent.onResume(this);
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        if (MainApplication.get(this).isMonitorInit()) {
            switch (MainApplication.get(this).getMonitorType()) {
                case NONE:
                    break;
                case COUTLY:
                    break;
                case UMENG:
                    MobclickAgent.onPageEnd(getClass().getName());
                    MobclickAgent.onPause(this);
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    protected void onStop() {
        super.onStop();

        if (MainApplication.get(this).isMonitorInit()) {
            switch (MainApplication.get(this).getMonitorType()) {
                case NONE:
                    break;
                case COUTLY:
//                    if (mIsCountlyStarted) {
//                        mIsCountlyStarted = false;
//                        Countly.sharedInstance().onStop();
//                    }
                    break;
                case UMENG:
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        MainApplication.get(this).removeActivity(this);

//        LogUtil.i(TAG, String.format("onDestroy %s", getClass().getName()));
//        destroyReceiver(mainApplication);
//        destroyDataManager();
//        destroyDataDaemon();
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

    public ActivityComponent getActivityComponent() {
        if (mActivityComponent == null) {

            mActivityComponent = DaggerActivityComponent.builder()
                    .activityModule(new ActivityModule(this))
                    .applicationComponent(MainApplication.get(this).getComponent())
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

    public void showProgress(int id) {
        hideProgress();

        mMaterialDialog = new MaterialDialog.Builder(this)
                .content(id)
                .progress(true, 0)
                .cancelable(false)
                .progressIndeterminateStyle(false)
                .build();
        mMaterialDialog.show();
    }

    public void hideProgress() {
        if (mMaterialDialog != null) {
            mMaterialDialog.dismiss();
            mMaterialDialog = null;
        }
    }

    protected void hideKeyboard(IBinder binder, int flags) {
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(binder, flags);
    }
}
