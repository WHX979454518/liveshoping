package com.hotniao.live.activity.account;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.hn.library.base.BaseActivity;
import com.hn.library.global.HnConstants;
import com.hn.library.global.HnUrl;
import com.hn.library.http.HnHttpUtils;
import com.hn.library.http.HnResponseHandler;
import com.hn.library.tab.CommonTabLayout;
import com.hn.library.tab.entity.TabEntity;
import com.hn.library.tab.listener.CustomTabEntity;
import com.hn.library.tab.listener.OnTabSelectListener;
import com.hn.library.utils.HnToastUtils;
import com.hn.library.utils.HnUtils;
import com.hn.library.view.CommDialog;
import com.hotniao.live.HnApplication;
import com.hotniao.live.R;
import com.hotniao.live.activity.HnWebActivity;
import com.hotniao.live.activity.bindPhone.HnFirstBindPhoneActivity;
import com.hotniao.live.activity.withdraw.HnWithDrawWriteActivity;
import com.hotniao.live.adapter.MyTabPagerAdapter;
import com.hotniao.live.fragment.billRecord.HnBillExchangeFragment;
import com.hotniao.live.fragment.billRecord.HnBillReceiveFragment;
import com.hotniao.live.model.HnWithDrawIdModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * Copyright (C) 2017,深圳市红鸟网络科技股份有限公司 All rights reserved.
 * 项目名称：红鸟电商
 * 类描述：收益详情
 * 创建人：mj
 * 创建时间：2017/3/6 16:16
 * 修改人：
 * 修改时间：2017/3/6 16:16
 * 修改备注：
 * Version:  1.0.0
 */
@Route(path = "/app/HnUserBillEarningActivity")
public class HnUserBillEarningActivity extends BaseActivity implements ViewPager.OnPageChangeListener, OnTabSelectListener {


    @BindView(R.id.billy_tab_layout)
    CommonTabLayout mBillyTab;
    @BindView(R.id.vp_bill)
    ViewPager mVpBill;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.mTvGetMoney)
    TextView mTvGetMoney;
    @BindView(R.id.mTvExchange)
    TextView mTvExchange;
    @BindView(R.id.mTvWithdrawMoney)
    TextView mTvWithdrawMoney;
    @BindView(R.id.mTvDetails)
    TextView mTvDetails;
    @BindView(R.id.mTvWithdraw)
    TextView mTvWithdraw;

    //标识符  用于默认显示第几个tab
    private int pos = 0;
    //tab  标题
    private String[] tabTitles;
    //集合    存放fargment
    private List<Fragment> mFragments;
    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();
    private HnWithDrawIdModel.DBean bean;

    @Override
    public int getContentViewId() {
        return R.layout.activity_user_bill_earnings;
    }

    @Override
    public void onCreateNew(Bundle savedInstanceState) {
        setShowBack(true);
        setShowTitleBar(true);
        setTitle(R.string.account_detail);
        //初始化数据
        initData();
        //初始化视图
        initView();
        mSubtitle.setVisibility(View.VISIBLE);
        mSubtitle.setTextColor(getResources().getColor(R.color.comm_text_color_black_s));
        mSubtitle.setText("规则");
        mSubtitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bean == null) return;
                HnWebActivity.luncher(HnUserBillEarningActivity.this, "规则", HnUrl.SERVER+"/user/app/cashAgreement", HnWebActivity.Rule);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        getData();

    }


    private void getData() {
        HnHttpUtils.postRequest(HnUrl.USER_WITHDRAW_INDEX, null, HnUrl.USER_WITHDRAW_INDEX, new HnResponseHandler<HnWithDrawIdModel>(HnWithDrawIdModel.class) {
            @Override
            public void hnSuccess(String response) {
                if (isFinishing()) return;
                if (model.getC() == 0) {
                    if (isFinishing() || model.getD() == null) {
                        return;
                    }else {
                        bean = model.getD();
                        mTvGetMoney.setText(HnUtils.setTwoPoint(model.getD().getUser().getCoin()));
                        mTvWithdrawMoney.setText(HnUtils.setTwoPoint(model.getD().getUser().getCash()));
                    }
                }
            }

            @Override
            public void hnErr(int errCode, String msg) {
                HnToastUtils.showToastShort(msg);
            }
        });
    }

    /**
     * 初始化数据
     */
    private void initData() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            pos = bundle.getInt(HnConstants.Intent.DATA, 0);
        }
    }

    /**
     * 初始化视图
     */
    private void initView() {

        tabTitles = getResources().getStringArray(R.array.user_billy2);
        for (int i = 0; i < tabTitles.length; i++) {
            mTabEntities.add(new TabEntity(tabTitles[i], 0, 0));
        }

        mFragments = new ArrayList<>();
        //收礼
        mFragments.add(HnBillReceiveFragment.newInstance());
        //兑换
        mFragments.add(HnBillExchangeFragment.newInstance());
//        //开播
//        mFragments.add(HnBillStartLiveFragment.newInstance(0));
//        //邀请
//        mFragments.add(HnBillInviteFragment.newInstance(0));

        mVpBill.setOffscreenPageLimit(tabTitles.length);
        mVpBill.setAdapter(new MyTabPagerAdapter(getSupportFragmentManager(), mFragments, tabTitles));
        mBillyTab.setTabData(mTabEntities);
        //设置默认选择tab
        mBillyTab.setCurrentTab(pos);
        mVpBill.setCurrentItem(pos);
        //事件监听
        mVpBill.addOnPageChangeListener(this);
        mBillyTab.setOnTabSelectListener(this);
    }


    @Override
    public void getInitData() {

    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        mBillyTab.setCurrentTab(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onTabSelect(int position) {
        mVpBill.setCurrentItem(position);
    }

    @Override
    public void onTabReselect(int position) {

    }

    @OnClick({ R.id.mTvWithdraw, R.id.mTvExchange, R.id.mTvDetails})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.mTvDetails:
                HnUserBillRechargeAndWithdrawActivity.luncher(this, HnUserBillRechargeAndWithdrawActivity.WITHDRAW);
                break;
            case R.id.mTvExchange:
                openActivity(HnExchangeCoinAct.class);
                break;
            case R.id.mTvWithdraw:
                if (TextUtils.isEmpty(HnApplication.getmUserBean().getUser_phone())) {
                    CommDialog.newInstance(HnUserBillEarningActivity.this).setClickListen(new CommDialog.TwoSelDialog() {
                        @Override
                        public void leftClick() {

                        }

                        @Override
                        public void rightClick() {
                            openActivity(HnFirstBindPhoneActivity.class);
                        }
                    }).setTitle(getString(R.string.bind_phone)).setContent(getString(R.string.now_goto_bind_phone)).show();

                    return;
                }
                openActivity(HnWithDrawWriteActivity.class);
                break;
        }
    }

}
