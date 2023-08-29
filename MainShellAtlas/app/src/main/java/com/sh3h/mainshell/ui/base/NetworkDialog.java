package com.sh3h.mainshell.ui.base;


import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.CheckBox;
import android.widget.EditText;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.mainshell.datautil.data.DataManager;
import com.mainshell.datautil.data.local.config.ConfigHelper;
import com.sh3h.mainshell.MainApplication;
import com.sh3h.mainshell.R;
import com.sh3h.mobileutil.util.ApplicationsUtil;
import com.sh3h.mobileutil.util.LogUtil;
import com.sh3h.mobileutil.util.TextUtil;

import javax.inject.Inject;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

public class NetworkDialog {
    public interface OnSaveListener {
        void onSave(boolean b);
    }
    private static final String TAG = "NetworkDialog";
    private final DataManager mDataManager;
    private final ConfigHelper mConfigHelper;

    private Context mContext;
    private OnSaveListener mOnSaveListener;
    private Subscription mSubscription;

    private String baseURI;
    private String brokeURL;
    private String reservedBaseURI;
    private String reservedBrokeURL;
    private String countlyUri;

    private EditText baseUriTextView;
    //private EditText brokeUrlTextView;
    private EditText reservedBaseUriTextView;
    //private EditText reservedBrokeUrlTextView;
    //private EditText countlyUriTextView;
    private CheckBox reservedAddressCheckBox;

    @Inject
    public NetworkDialog(DataManager dataManager, ConfigHelper configHelper) {
        mDataManager = dataManager;
        mConfigHelper = configHelper;
        mOnSaveListener = null;
        mSubscription = null;
    }

    public void show(Context context, OnSaveListener onSaveListener) {
        mContext = context;
        mOnSaveListener = onSaveListener;
        baseURI = TextUtil.getString(mConfigHelper.getBaseUri());
        brokeURL = TextUtil.getString(mConfigHelper.getBrokerUrl());
        reservedBaseURI = TextUtil.getString(mConfigHelper.getReservedBaseUri());
        reservedBrokeURL = TextUtil.getString(mConfigHelper.getReservedBrokerUrl());
        countlyUri = TextUtil.getString(mConfigHelper.getCountlyUri());
        boolean isUsingReservedAddress = mConfigHelper.isUsingReservedAddress();

        if ((mSubscription != null) && (mSubscription.isUnsubscribed())) {
            mSubscription.unsubscribe();
        }

        MaterialDialog dialog = new MaterialDialog.Builder(context)
                .title(R.string.text_setting_network)
                .customView(R.layout.dialog_setting_network, true)
                .positiveText(R.string.text_ok)
                .negativeText(android.R.string.cancel)
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        save(baseUriTextView.getText().toString(),
                                reservedBaseUriTextView.getText().toString(),
                                brokeURL, //brokeUrlTextView.getText().toString(),
                                reservedBrokeURL, //reservedBrokeUrlTextView.getText().toString(),
                                countlyUri, //countlyUriTextView.getText().toString(),
                                reservedAddressCheckBox.isChecked());
                    }
                }).build();
        if (dialog.getCustomView() != null) {
            baseUriTextView = (EditText) dialog.getCustomView().findViewById(R.id.et_data_address);
            //brokeUrlTextView = (EditText) dialog.getCustomView().findViewById(R.id.et_push_address);
            reservedBaseUriTextView = (EditText) dialog.getCustomView().findViewById(R.id.et_reserved_data_address);
            //reservedBrokeUrlTextView = (EditText) dialog.getCustomView().findViewById(R.id.et_reserved_push_address);
            //countlyUriTextView = (EditText) dialog.getCustomView().findViewById(R.id.et_countly_address);
            reservedAddressCheckBox = (CheckBox) dialog.getCustomView().findViewById(R.id.cb_is_reserved_address);

            baseUriTextView.setText(baseURI);
            baseUriTextView.setSelection(baseURI.length());
            reservedBaseUriTextView.setText(reservedBaseURI);
            reservedBaseUriTextView.setSelection(reservedBaseURI.length());
//            brokeUrlTextView.setText(brokeURL);
//            brokeUrlTextView.setSelection(brokeURL.length());
//            reservedBrokeUrlTextView.setText(reservedBrokeURL);
//            reservedBrokeUrlTextView.setSelection(reservedBrokeURL.length());
//            countlyUriTextView.setText(countlyUri);
//            countlyUriTextView.setSelection(countlyUri.length());
            reservedAddressCheckBox.setChecked(isUsingReservedAddress);
        }
        dialog.show();
    }

    private void save(String baseUri,
                      String reservedBaseUri,
                      String brokeUrl,
                      String reservedBrokeUrl,
                      String countlyUri,
                      boolean isUsingReservedAddress) {
        LogUtil.i(TAG, "save");
        if (TextUtil.isNullOrEmpty(baseUri)
                || TextUtil.isNullOrEmpty(reservedBaseUri)
                || TextUtil.isNullOrEmpty(brokeUrl)
                || TextUtil.isNullOrEmpty(reservedBrokeUrl)
                || TextUtil.isNullOrEmpty(countlyUri)) {
            ApplicationsUtil.showMessage(mContext, "param is invalid");
            LogUtil.e(TAG, "param is invalid");
            return;
        }

        mSubscription = mConfigHelper.saveNetWorkSetting(baseUri, reservedBaseUri,
                brokeUrl, reservedBrokeUrl, countlyUri, isUsingReservedAddress)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<Boolean>() {
                    @Override
                    public void onCompleted() {
                        LogUtil.i(TAG, "save onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.i(TAG, "save onError" + e.getMessage());
                        ApplicationsUtil.showMessage(mContext, e.getMessage());
                        if (mOnSaveListener != null) {
                            mOnSaveListener.onSave(false);
                        }
                    }

                    @Override
                    public void onNext(Boolean aBoolean) {
                        LogUtil.i(TAG, "save onNext " + aBoolean);
                        if (aBoolean) {
                            ApplicationsUtil.showMessage(mContext, R.string.text_saving_success);
                        } else {
                            ApplicationsUtil.showMessage(mContext, R.string.text_saving_failure);
                        }

                        if (mOnSaveListener != null) {
                            mOnSaveListener.onSave(aBoolean);
                        }
                    }
                });
    }
}
