package com.hotniao.live.activity.account;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.hn.library.base.BaseActivity;
import com.hn.library.tab.CommonTabLayout;
import com.hn.library.tab.entity.TabEntity;
import com.hn.library.tab.listener.CustomTabEntity;
import com.hn.library.tab.listener.OnTabSelectListener;
import com.hotniao.live.R;
import com.hotniao.live.adapter.MyTabPagerAdapter;
import com.hn.library.global.HnConstants;
import com.hotniao.live.fragment.billRecord.HnBillBuyVipFragment;
import com.hotniao.live.fragment.billRecord.HnBillLookLiveFragment;
import com.hotniao.live.fragment.billRecord.HnBillSendFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;


/**
 * Copyright (C) 2017,深圳市红鸟网络科技股份有限公司 All rights reserved.
 * 类描述：消费记录
 * 创建人：mj
 * 创建时间：2017/3/6 16:16
 * 修改人：
 * 修改时间：2017/3/6 16:16
 * 修改备注：
 * Version:  1.0.0
 */
public class HnUserBillExpenseActivity extends BaseActivity  implements ViewPager.OnPageChangeListener,OnTabSelectListener {


    @BindView(R.id.billy_tab_layout)
    CommonTabLayout mBillyTab;
    @BindView(R.id.vp_bill)
    ViewPager mVpBill;

    //标识符  用于默认显示第几个tab
    private  int  pos=0;
    //tab  标题
    private String[]  tabTitles;
    //集合    存放fargment
    private List<Fragment> mFragments;
    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();

    @Override
    public int getContentViewId() {
        return R.layout.activity_user_bill;
    }

    @Override
    public void onCreateNew(Bundle savedInstanceState) {
        //初始化数据
        initData();
        //初始化视图
        initView();
    }

    /**
     * 初始化数据
     */
    private void initData() {
        Bundle  bundle=getIntent().getExtras();
        if(bundle!=null){
           pos= bundle.getInt(HnConstants.Intent.DATA);
        }
    }

    /**
     * 初始化视图
     */
    private void initView() {
        setShowBack(true);
        setTitle(R.string.expense_record);
        tabTitles=getResources().getStringArray(R.array.user_billy_expense);
        for (int i = 0; i < tabTitles.length; i++) {
            mTabEntities.add(new TabEntity(tabTitles[i], 0, 0));
        }

        mFragments = new ArrayList<>();
        //送礼
        mFragments.add(HnBillSendFragment.newInstance());
        //看播
        mFragments.add(HnBillLookLiveFragment.newInstance(1));

        //购买VIP
        mFragments.add(HnBillBuyVipFragment.newInstance());

        mVpBill.setOffscreenPageLimit(tabTitles.length);
        mVpBill.setAdapter(new MyTabPagerAdapter(getSupportFragmentManager(),mFragments,tabTitles));
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
}
