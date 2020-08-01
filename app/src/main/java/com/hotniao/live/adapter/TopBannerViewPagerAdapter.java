package com.hotniao.live.adapter;

import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.hotniao.live.fragment.ConcentrationFrag;
import com.hotniao.live.fragment.home.HomeHotFrag;
import com.live.shoplib.ui.GoodsCategoryListFrag;

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

public class TopBannerViewPagerAdapter extends FragmentPagerAdapter {

    private List<Fragment> mFragment = new ArrayList<>();
    private String[] mTitle;
    private String[] ids;

    @RequiresApi(api = Build.VERSION_CODES.M)
    public TopBannerViewPagerAdapter(FragmentManager fm, String[] mTitle, String[] ids) {
        super(fm);
        this.mTitle = mTitle;
        this.ids = ids;
        initFragment();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void initFragment() {
        mFragment.add(new ConcentrationFrag());
        for (String id : ids) {
            //TODO 为了测试写死id，到时要放开为原来的数据
//            mFragment.add(GoodsCategoryListFrag.newInstance("29"));
            mFragment.add(GoodsCategoryListFrag.newInstance(id));
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
