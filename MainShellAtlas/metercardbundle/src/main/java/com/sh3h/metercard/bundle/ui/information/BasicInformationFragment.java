package com.sh3h.metercard.bundle.ui.information;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sh3h.dataprovider.data.entity.DUCard;
import com.sh3h.dataprovider.data.entity.DUCardInfo;
import com.sh3h.metercard.bundle.R;
import com.sh3h.metercard.bundle.R2;
import com.sh3h.metercard.bundle.ui.base.BaseActivity;
import com.sh3h.metercard.bundle.ui.base.ParentFragment;
import com.sh3h.metercard.bundle.util.ConstDataUtil;
import com.sh3h.mobileutil.util.TextUtil;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


/**
 * 用户基本信息
 */
public class BasicInformationFragment extends ParentFragment implements SwipeRefreshLayout.OnRefreshListener,
        View.OnClickListener, BasicInformationMvpView, View.OnLongClickListener {

    @Inject
    BasicInformationPresenter mPresenter;
    /**
     * 用户信息
     */
    @BindView(R2.id.txt_bianhao)
    TextView bianhao;//客户编号

    @BindView(R2.id.txt_leibie)
    TextView leibie;//客户类别

    @BindView(R2.id.txt_cebenhao)
    TextView cebenhao;//册本号

    @BindView(R2.id.txt_huming)
    TextView huming;//客户名称

    @BindView(R2.id.txt_dizhi)
    TextView dizhi;//客户地址

    @BindView(R2.id.txt_jiaofeiFS)
    TextView jiaofeiFS;//缴费方式

    @BindView(R2.id.txt_fangDongDH)
    TextView fangDongDH;//联系电话_房东

    @BindView(R2.id.txt_fangKeDH)
    TextView fangKeDH;//联系人手机

    @BindView(R2.id.txt_jieTi)
    TextView jieTi;//是否阶梯
    /**
     * 水表信息
     */
    @BindView(R2.id.txt_shuibiaocj)
    TextView shuibiaocj;//厂家名称

    @BindView(R2.id.txt_biaoxing)
    TextView biaoxing;//表类型

    @BindView(R2.id.txt_biaohao)
    TextView biaohao;//水表钢印号

    @BindView(R2.id.txt_koujing)
    TextView koujing;//口径

    @BindView(R2.id.txt_biaoFenLei)
    TextView biaoFenLei;//表分类

    @BindView(R2.id.txt_biaoWei)
    TextView biaoWei;//表位

    /**
     * 价格信息
     */
    @BindView(R2.id.txt_jianhao)
    TextView jianhao;//简号


    private View mView;

    private Unbinder mUnbinder;
    private boolean isRefreshing = true;

    public BasicInformationFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((BaseActivity) getActivity()).getActivityComponent().inject(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_basic_infomation, container, false);
        mUnbinder = ButterKnife.bind(this, mView);
        mPresenter.attachView(this);
        initData();
        return mView;
    }

    private void initData() {
        Bundle _bundle = getArguments();
        if (_bundle == null || TextUtil.isNullOrEmpty(_bundle.getString(ConstDataUtil.S_CID))) {
            return;
        }
        DUCardInfo duCardInfo = new DUCardInfo();
        duCardInfo.setCustomerId(_bundle.getString(ConstDataUtil.S_CID));
        duCardInfo.setFilterType(DUCardInfo.FilterType.SEARCHING_ONE);
        mPresenter.getOneCard(duCardInfo);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mUnbinder.unbind();
        mPresenter.detachView();
    }

    @Override
    public void onRefresh() {
    }

    @Override
    protected void lazyLoad() {

    }

    @Override
    public void onGetCardFinish(DUCard duCard) {
        if (duCard == null) {
            return;
        }
        bianhao.setText(duCard.getCid());//客户编号
        leibie.setText(duCard.getKehubh());//客户类型
        cebenhao.setText(duCard.getCh());//册本号
        huming.setText(duCard.getKehumc());//客户名称
        dizhi.setText(duCard.getDizhi());//地址
        jiaofeiFS.setText(duCard.getShoufeifs());//缴费方式
        fangDongDH.setText(TextUtil.getString(duCard.getLianxidh()));//联系电话
        fangKeDH.setText(TextUtil.getString(duCard.getLianxisj()));//联系人手机
        if (duCard.getShifoujt() == 0)
            jieTi.setText("否");
        else
            jieTi.setText("是");

        /**
         * 水表信息
         */
        shuibiaocj.setText(duCard.getShuibiaocj());//水表厂家
        biaoxing.setText(duCard.getBiaoxing());//表类型
        biaohao.setText(duCard.getShuibiaogyh());//水表钢印号
        koujing.setText(duCard.getKoujingmc());//口径
        biaoWei.setText(duCard.getBiaowei());//表位
        biaoFenLei.setText(duCard.getShuibiaoflmc());
        //价格信息
        jianhao.setText(duCard.getJianhaomc());//简号

        fangDongDH.setOnClickListener(this);//电话拨打
        fangKeDH.setOnClickListener(this);//手机拨打
        jianhao.setOnLongClickListener(this);
        fangDongDH.setOnLongClickListener(this);
        fangKeDH.setOnLongClickListener(this);

    }

    @Override
    public void onError(String message) {

    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public boolean onLongClick(View v) {
        return false;
    }
}
