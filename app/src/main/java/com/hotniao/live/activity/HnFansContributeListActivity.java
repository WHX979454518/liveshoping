package com.hotniao.live.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.hn.library.base.BaseActivity;
import com.hotniao.live.R;
import com.hotniao.live.fragment.HnCoinContriFrag;
import com.hotniao.live.fragment.HnShopContriFrag;

import butterknife.BindView;

/**
 * Copyright (C) 2016,深圳市红鸟网络科技股份有限公司 All rights reserved.
 * 类描述：粉丝排行榜
 * 创建人：刘勇
 * 创建时间：2017/2/22 16:22
 * 修改人：刘勇
 * 修改时间：2017/2/22 16:34
 * 修改备注：
 * Version:  1.0.0
 */
@Route(path = "/app/HnFansContributeListActivity")
public class HnFansContributeListActivity extends BaseActivity {
    @BindView(R.id.mRg)
    RadioGroup mRg;
    @BindView(R.id.mVp)
    ViewPager mVp;

    @Override
    public int getContentViewId() {
        return R.layout.activity_fans_contribute_list;
    }

    @Override
    public void onCreateNew(Bundle savedInstanceState) {
        setTitle(R.string.fans_con_list);
        setShowBack(true);
        final boolean isHaveShop = getIntent().getBooleanExtra("isHaveShop", false);
        if (!isHaveShop) {
            findViewById(R.id.mRg).setVisibility(View.GONE);
            setTitle("贡献榜");
        }
        mRg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                int position = 0;
                switch (checkedId) {
                    case R.id.mRbTotal:
                        position = 0;
                        break;
                    case R.id.mRbBuy:
                        position = 1;
                        break;
                }
                mVp.setCurrentItem(position);
            }
        });

        mVp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                RadioButton childAt = (RadioButton) mRg.getChildAt(position);
                childAt.setChecked(true);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        mVp.setOffscreenPageLimit(isHaveShop ? 2 : 1);
        mVp.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
//                Fragment fragment = HnPlatformListFragment.newInstance();
//                Bundle bundle=new Bundle();
//                bundle.putBoolean("isAnchor", false);
//                bundle.putString("url", HnUrl.LIVE_RANK_ANCHORFANS);//接口
//                bundle.putString("type",HnPlatformListFragment.FansContribute);//排行榜类型：粉丝贡献榜，平台榜
//                bundle.putString("anchorId",getIntent().getStringExtra("userId"));//主播id
//                bundle.putInt("fansType",position);//贡献榜类型
//                fragment.setArguments(bundle);
                Fragment fragment = null;
                if (position == 0) {
                    fragment = HnCoinContriFrag.newInstance(getIntent().getStringExtra("userId"));
                } else {
                    fragment = HnShopContriFrag.newInstance(getIntent().getStringExtra("userId"));
                }
                return fragment;
            }

            @Override
            public int getCount() {
                return isHaveShop ? 2 : 1;
            }
        });
    }

    @Override
    public void getInitData() {

    }


}
