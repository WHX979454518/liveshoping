package com.live.shoplib.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.DrawableRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.hn.library.base.BaseActivity;
import com.hn.library.tab.SlidingTabLayout;
import com.hn.library.tab.listener.OnTabSelectListener;
import com.live.shoplib.R;
import com.live.shoplib.ui.frag.GoodsListFrag;

import java.util.ArrayList;
import java.util.List;

/**
 * create by Mr.x
 * on 2018/7/3
 */
@Route(path = "/shoplib/GoodsListAct")
public class GoodsListAct extends BaseActivity implements ViewPager.OnPageChangeListener {

    public SlidingTabLayout mSlidView;
    public ViewPager mViewPager;
    public ArrayList<GoodsListFrag> fragments = new ArrayList<>();
    public String[] mTitles;

    public boolean acs = true;
    public boolean sace = true;

    @Override
    public int getContentViewId() {
        return R.layout.act_goods_list;
    }

    @Override
    public void onCreateNew(Bundle savedInstanceState) {
        setTitle("商品列表");
        setShowBack(true);
        mSlidView = findViewById(R.id.mSlidView);
        mViewPager = findViewById(R.id.mViewPager);

        mTitles = getResources().getStringArray(R.array.shop_list);
        fragments.add(GoodsListFrag.get(getIntent().getStringExtra("id"), "1",acs));
        fragments.add(GoodsListFrag.get(getIntent().getStringExtra("id"), "2",!acs));
        fragments.add(GoodsListFrag.get(getIntent().getStringExtra("id"), "3",!acs));
        fragments.add(GoodsListFrag.get(getIntent().getStringExtra("id"), "4",acs));
        setShowSubTitle(true);
        mSubtitle.setBackgroundDrawable(getResources().getDrawable(R.drawable.img_s));
        mSubtitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0; i < fragments.size(); i++) {
                    fragments.get(i).setSace(sace);
                }
                sace = !sace;
                mSubtitle.setBackgroundDrawable(getResources().getDrawable(sace ? R.drawable.img_s : R.drawable.img_l));
            }
        });
        mViewPager.setOffscreenPageLimit(fragments.size());
        mViewPager.setAdapter(new PagerAdapter(getSupportFragmentManager(), fragments, mTitles));
        mSlidView.setViewPager(mViewPager);
        mViewPager.addOnPageChangeListener(this);

        mSlidView.getTitleView(3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mViewPager.getCurrentItem() != 3) {
                    mSlidView.setCurrentTab(3);
                    mViewPager.setCurrentItem(3);
                } else {
                    fragments.get(3).setSort();
                    if (acs) {
                        acs = false;
                        setRightDrawable(mSlidView.getTitleView(3), R.drawable.price_down);//倒叙
                    } else {
                        acs = true;
                        setRightDrawable(mSlidView.getTitleView(3), R.drawable.price_up);//正叙
                    }
                }
            }
        });
        setRightDrawable(mSlidView.getTitleView(3), R.drawable.price);//倒叙
    }

    @Override
    public void getInitData() {

    }


    public void setRightDrawable(TextView textView, @DrawableRes int rightRes) {
        textView.setCompoundDrawablesWithIntrinsicBounds(0, 0, rightRes, 0);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        if (position == 3) {
            if (acs) {
                setRightDrawable(mSlidView.getTitleView(3), R.drawable.price_up);//正叙
            } else {
                setRightDrawable(mSlidView.getTitleView(3), R.drawable.price_down);//倒叙
            }
        } else {
            setRightDrawable(mSlidView.getTitleView(3), R.drawable.price);//倒叙
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }


    class PagerAdapter extends FragmentPagerAdapter {

        private List<GoodsListFrag> mFragments;
        private String[] mTitles;

        public PagerAdapter(FragmentManager fm, List<GoodsListFrag> mFragments, String[] mTitles) {
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
            Fragment fragment = mFragments.get(position);
            return fragment;
        }
    }

}
