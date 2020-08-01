package com.hotniao.live.adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.hotniao.live.fragment.FindSourceItemFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Copyright (C) 2016,深圳市红鸟网络科技股份有限公司 All rights reserved.
 * 类描述：主页面
 * 创建人：刘勇
 * 创建时间：2017/2/22 16:22
 * 修改人：刘勇
 * 修改时间：2017/2/22 16:34
 * 修改备注：
 * Version:  1.0.0
 */

public class FindSourceScrollViewPagerAdapter extends FragmentPagerAdapter {

    private List<FindSourceItemFragment> mFragment = new ArrayList<>();
    private String[] mTitle;
    private String[] ids;

//    public FindSourceScrollViewPagerAdapter(FragmentManager fm, String[] mTitle) {
//        super(fm);
//        this.mTitle = mTitle;
//        initFragment();
//    }

    public FindSourceScrollViewPagerAdapter(FragmentManager fm, String[] mTitle, String[] ids) {
        super(fm);
        this.mTitle = mTitle;
        this.ids = ids;
        initFragment();
    }

    private void initFragment() {
//        for (String title : mTitle) {
        for (String id : ids) {
//            mFragment.add(FindSourceItemFragment.newInstance(""));
            mFragment.add(FindSourceItemFragment.newInstance(id));
        }
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (mTitle != null || mTitle.length > 0)
            return mTitle[position];

        return super.getPageTitle(position);
    }

    @Override
    public Fragment getItem(int position) {
        return mFragment.get(position);
    }

    @Override
    public int getCount() {
        return mFragment != null ? mFragment.size() : 0;
    }


}
