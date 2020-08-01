package com.hotniao.live.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.DrawableRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.hn.library.base.BaseFragment;
import com.hn.library.daynight.DayNightHelper;
import com.hn.library.tab.SlidingTabLayout;
import com.hn.library.tab.listener.OnTabSelectListener;
import com.hotniao.live.R;
import com.hotniao.live.activity.HnAddressAct;
import com.hotniao.live.activity.HnHomeSearchActivity;
import com.hotniao.live.biz.home.HnHomeCate;
import com.hotniao.live.dialog.HnAllCateDialog;
import com.hotniao.live.eventbus.HnSelectLiveCateEvent;
import com.hotniao.live.eventbus.HomeNearEvent;
import com.hotniao.live.fragment.home.HnFollowFragment;
import com.hotniao.live.fragment.home.HnHomeHotFragment;
import com.hotniao.live.fragment.home.HnHomeLiveFrag;
import com.hotniao.live.fragment.home.HnNearLiveFragment;
import com.hotniao.live.model.HnHomeLiveCateModel;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Copyright (C) 2017,深圳市红鸟网络科技股份有限公司 All rights reserved.
 * 项目名称：红鸟电商
 * 类描述：首页模块
 * 创建人：mj
 * 创建时间：2017/3/6 16:16
 * 修改人：
 * 修改时间：2017/3/6 16:16
 * 修改备注：
 * Version:  1.0.0
 * 已弃用，使用 HnHomeCusFragment
 */
public class HnHomeFragment extends BaseFragment implements ViewPager.OnPageChangeListener {

    @BindView(R.id.home_viewpager)
    ViewPager mHomeViewpager;
    @BindView(R.id.rl_title)
    LinearLayout mRlTitle;

    @BindView(R.id.iv_search)
    ImageView ivSearch;
    @BindView(R.id.mSlidTab)
    SlidingTabLayout mSlidTab;

    private List<Fragment> mFragments;
    private List<HnHomeLiveCateModel.DEntity> mTitles = new ArrayList<>();
    private DayNightHelper mDayNightHelper;

    @Override
    public int getContentViewId() {
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        return R.layout.home_framgnet;
    }

    @Override
    public void initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (HnHomeCate.mCateData.size() < 1) {
            HnHomeCate.getCateData();
            HnHomeCate.setOnCateListener(new HnHomeCate.OnCateListener() {
                @Override
                public void onSuccess() {
                    HnHomeCate.removeListener();
                    setTab();
                }

                @Override
                public void onError(int errCode, String msg) {
                    HnHomeCate.removeListener();
                    setTab();
                }
            });
        } else {
            setTab();
        }
    }


    private void setTab() {
        //fragment集合
        mFragments = new ArrayList<>();
        HnHomeLiveCateModel.DEntity item = new HnHomeLiveCateModel.DEntity();
        //添加热门
        item.setId("-1");
        item.setName("热门");
        mTitles.add(item);
        //关注
        HnHomeLiveCateModel.DEntity item2 = new HnHomeLiveCateModel.DEntity();
        item2.setId("-2");
        item2.setName("关注");
        mTitles.add(item2);
        //附近
        HnHomeLiveCateModel.DEntity item3 = new HnHomeLiveCateModel.DEntity();
        item3.setId("-3");
        item3.setName("附近");
        mTitles.add(item3);
        //网络获取
        mTitles.addAll(HnHomeCate.mCateData);
        for (int i = 0; i < mTitles.size(); i++) {
            switch (mTitles.get(i).getId()) {
                case "-1":
                    mFragments.add(HnHomeHotFragment.getInstance());
                    break;
                case "-2":
                    mFragments.add(new HnFollowFragment());
                    break;
                case "-3":
                    mFragments.add(new HnNearLiveFragment());
                    break;
                default:
                    mFragments.add(new HnHomeLiveFrag(mTitles.get(i).getId()));//TODO
                    break;
            }
        }
        mHomeViewpager.setOffscreenPageLimit(mTitles.size());
        mHomeViewpager.setAdapter(new PagerAdapter(getFragmentManager(), mFragments, mTitles));
        mDayNightHelper = new DayNightHelper();
        refreshUI();
        mSlidTab.setViewPager(mHomeViewpager);
        setRightDrawable(mSlidTab.getTitleView(2), 0);

    }

    @Override
    protected void initEvent() {
        mSlidTab.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                mHomeViewpager.setCurrentItem(position);
            }

            @Override
            public void onTabReselect(int position) {
                //注释掉标题右侧的下拉符号
                if (2 == position) {
                    startActivity(new Intent(getActivity(), HnAddressAct.class));
                    mHomeViewpager.setCurrentItem(2);
                    setRightDrawable(mSlidTab.getTitleView(2), R.drawable.vicinity_lower);
                }
            }
        });
        mHomeViewpager.addOnPageChangeListener(this);
    }

    public void setRightDrawable(TextView textView, @DrawableRes int rightRes) {
        textView.setCompoundDrawablesWithIntrinsicBounds(0, 0, rightRes, 0);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        if (position == 2) {
            //注释掉标题右侧的下拉符号
            setRightDrawable(mSlidTab.getTitleView(2), R.drawable.vicinity_lower);
        } else {
            setRightDrawable(mSlidTab.getTitleView(2), 0);
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }


    @Override
    protected void initData() {

    }

    @OnClick({R.id.iv_search, R.id.mIvTab})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_search: //搜索界面
                mActivity.openActivity(HnHomeSearchActivity.class);
                break;
            case R.id.mIvTab:
                HnAllCateDialog.newInstance(mTitles).setClickListen(new HnAllCateDialog.SelDialogListener() {
                    @Override
                    public void sureClick() {

                    }
                }).show(mActivity.getFragmentManager(), "cate");
                break;
        }
    }

    /**
     * 刷新UI界面
     */
    public void refreshUI() {
        EventBus.getDefault().post(new HnSelectLiveCateEvent(HnSelectLiveCateEvent.REFRESH_UI, "", 0));
        //条目背景颜色
        TypedValue item_color = new TypedValue();
        mActivity.getTheme().resolveAttribute(R.attr.item_bg_color, item_color, true);
        //标题背景色
        mRlTitle.setBackgroundResource(item_color.resourceId);
        //更换图标
        boolean isDay = mDayNightHelper.isDay();
        mSlidTab.setBackgroundResource(isDay ? R.drawable.select_line_indicator : R.drawable.select_line_indicator_dark);
        ivSearch.setImageResource(isDay ? R.drawable.home_search : R.drawable.home_search_dark);
    }


    class PagerAdapter extends FragmentPagerAdapter {

        private List<Fragment> mFragments;
        private List<HnHomeLiveCateModel.DEntity> mTitles;

        public PagerAdapter(FragmentManager fm, List<Fragment> mFragments, List<HnHomeLiveCateModel.DEntity> mTitles) {
            super(fm);
            this.mFragments = mFragments;
            this.mTitles = mTitles;
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTitles.get(position).getName();
        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragment = mFragments.get(position);
            Bundle bundle = new Bundle();
            bundle.putString("cateId", mTitles.get(position).getId());
            fragment.setArguments(bundle);
            return fragment;
        }
    }


    /**
     * 点击分类弹窗切换Tab
     *
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void event(HnSelectLiveCateEvent event) {
        if (event.getPosition() < mTitles.size())
            mHomeViewpager.setCurrentItem(event.getPosition());
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void event(HomeNearEvent event) {
        mSlidTab.getTitleView(2).setText(event.getCity());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
