package com.hotniao.live.adapter;

import android.content.Context;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.hn.library.base.BaseFragment;

import java.util.List;

/**
 * Copyright (C) 2017,深圳市红鸟网络科技股份有限公司 All rights reserved.
 * 项目名称：红鸟电商
 * 类描述：fragment+viewpager  适配器
 * 创建人：Administrator
 * 创建时间：2017/9/11 10:19
 * 修改人：Administrator
 * 修改时间：2017/9/11 10:19
 * 修改备注：
 * Version:  1.0.0
 */
public class MyTabPagerAdapter extends FragmentPagerAdapter {

    private List<Fragment>  mFragments;
    private String[]  mTitles;

    public MyTabPagerAdapter(FragmentManager fm, List<Fragment>  mFragments, String[]  mTitles) {
        super(fm);

        this.mFragments=mFragments;
        this.mTitles=mTitles;
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

