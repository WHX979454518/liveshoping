package com.hotniao.live.adapter;



import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.hotniao.live.model.SpikeTitleModel;
import com.hotniao.live.fragment.SpikeOrGroupBuyItemFragment;

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

public class SpikeScrollViewPagerAdapter extends FragmentPagerAdapter {

    private List<SpikeOrGroupBuyItemFragment> mFragment = new ArrayList<>();
    private SpikeTitleModel.SpikeTitleItem[] mTitle;

    public SpikeScrollViewPagerAdapter(FragmentManager fm, SpikeTitleModel.SpikeTitleItem[] mTitle) {
        super(fm);
        this.mTitle = mTitle;
        initFragment();
    }

    private void initFragment() {
        for (SpikeTitleModel.SpikeTitleItem tabEntity : mTitle) {
            mFragment.add( SpikeOrGroupBuyItemFragment.newInstance(tabEntity.getStarttime(),tabEntity.getEndtime(),true));
        }
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (mTitle != null || mTitle.length > 0)
            return mTitle[position].getTitle();

        return super.getPageTitle(position);
    }

    public String getSecondTitle(int position) {
        return mTitle[position].getSecondTitle();
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
