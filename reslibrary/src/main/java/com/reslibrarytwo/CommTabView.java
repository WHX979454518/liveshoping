package com.reslibrarytwo;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;

import androidx.annotation.ColorRes;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.hn.library.tab.CommonTabLayout;
import com.hn.library.tab.entity.TabEntity;
import com.hn.library.tab.listener.CustomTabEntity;
import com.hn.library.tab.listener.OnTabSelectListener;

import java.util.ArrayList;
import java.util.List;


/**
 * 自定义-公用 Tab + ViewPager
 * 作者：Mr.Xu
 * 时间：2017/12/7
 */
public class CommTabView extends LinearLayout implements ViewPager.OnPageChangeListener, OnTabSelectListener {

    private CommonTabLayout mTab;
    private ViewPager mViewPager;
    private View rootView;
    public CheckBox mBoxTab;
    private String[] tabTitles;
    private List<Fragment> mFragments = new ArrayList<>();
    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();

    private MyTabPagerAdapter adapter;

    public CommTabView(Context context) {
        super(context);
        initView();
    }

    public CommTabView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public CommTabView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }


    private void initView() {
        rootView = View.inflate(getContext(), R.layout.comm_tab, this);
        mTab = rootView.findViewById(R.id.mTab);
        mBoxTab = rootView.findViewById(R.id.mBoxTab);
        mViewPager = rootView.findViewById(R.id.mViewPager);
        tabTitles = getResources().getStringArray(R.array.user_billy);
        for (int i = 0; i < tabTitles.length; i++) {
            mTabEntities.add(new TabEntity(tabTitles[i], 0, 0));
        }
        mViewPager.setOffscreenPageLimit(tabTitles.length);
        mViewPager.setAdapter(adapter = new MyTabPagerAdapter(((FragmentActivity) getContext()).getSupportFragmentManager(), mFragments, tabTitles));
        mTab.setTabData(mTabEntities);
        //设置默认选择tab
        mTab.setCurrentTab(0);
        mViewPager.setCurrentItem(0);
        //事件监听
        mViewPager.addOnPageChangeListener(this);
        mTab.setOnTabSelectListener(this);
    }

    /**
     * 初始化颜色
     */
    public void initColor(@ColorRes int textSelColor, @ColorRes int textNorColor, @ColorRes int tabColor) {
        mTab.setIndicatorColor(getResources().getColor(tabColor));
        mTab.setTextSelectColor(getResources().getColor(textSelColor));
        mTab.setTextUnselectColor(getResources().getColor(textNorColor));
    }

    /**
     * 初始化数据
     */
    public void init(String[] tabs, int pos, Fragment... fragments) {
        Fragment[] fg = fragments;
        if (fragments.length != tabs.length)
            throw new IllegalStateException("初始化格式不符合，title和fragmengt数量不匹配");
        //Tab
        tabTitles = tabs;
        mTabEntities.clear();
        for (int i = 0; i < tabTitles.length; i++) {
            mTabEntities.add(new TabEntity(tabTitles[i], 0, 0));
        }
        mTab.setTabData(mTabEntities);
        //Frag
        mFragments.clear();
        for (Fragment fragment : fg) {
            mFragments.add(fragment);
        }
        adapter.notifyDataSetChanged();

        mViewPager.setOffscreenPageLimit(fragments.length);
        mTab.setCurrentTab(pos);
        mViewPager.setCurrentItem(pos);
    }

    /*############################################################################################*/

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        mTab.setCurrentTab(position);

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    /*############################################################################################*/

    @Override
    public void onTabSelect(int position) {
        mViewPager.setCurrentItem(position);
    }

    @Override
    public void onTabReselect(int position) {

    }

    class MyTabPagerAdapter extends FragmentPagerAdapter {

        private List<Fragment> mFragments;
        private String[] mTitles;

        public MyTabPagerAdapter(FragmentManager fm, List<Fragment> mFragments, String[] mTitles) {
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
            return mTitles[position];
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }
    }
}
