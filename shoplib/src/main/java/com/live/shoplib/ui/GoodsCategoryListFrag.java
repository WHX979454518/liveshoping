package com.live.shoplib.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.DrawableRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.hn.library.base.BaseFragment;
import com.hn.library.tab.SlidingTabLayout;
import com.live.shoplib.R;
import com.live.shoplib.ui.frag.GoodsListFrag;
import com.live.shoplib.utils.rollingutils.SlidingFragmentViewPager;

import java.util.ArrayList;
import java.util.List;

/**
 * create by Mr.x
 * on 2018/7/3
 */

public class GoodsCategoryListFrag extends BaseFragment implements ViewPager.OnPageChangeListener {

    public SlidingTabLayout mSlidView;
//    public NoHorizontalViewpager mViewPager;
    public ViewPager mViewPager;
    public ArrayList<com.live.shoplib.ui.frag.GoodsListFrag> fragments = new ArrayList<>();
    public String[] mTitles;
    public String goodsCategoryId;

    public boolean acs = true;



//    private List<OrderTypeBean.DataBean.WhereBean> whereBeanList = new ArrayList<OrderTypeBean.DataBean.WhereBean>();
    List<String> strings = new ArrayList();
    private  List<Fragment> fragmentss = new ArrayList();
    private SlidingFragmentViewPager slidingFragmentViewPager;
//    ViewPager one_viewpager;
//    ArrayList<OrderTypeBean.DataBean.WhereBean.ListBean> list;



    public static GoodsCategoryListFrag newInstance(String id) {
        GoodsCategoryListFrag fragment = new GoodsCategoryListFrag();
        Bundle bundle = new Bundle();
        bundle.putString("id", id);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public int getContentViewId() {
        return R.layout.act_goods_list;
    }

    @Override
    public void initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        mSlidView = mRootView.findViewById(R.id.mSlidView);
        mViewPager =  mRootView.findViewById(R.id.mViewPager);

        Bundle bundle = getArguments();
        if (bundle != null) {
            goodsCategoryId = bundle.getString("id", "");
        }
//        mTitles = getResources().getStringArray(R.array.shop_list);
        fragments.add(GoodsListFrag.get(goodsCategoryId, "1",acs));
        fragments.add(GoodsListFrag.get(goodsCategoryId, "2",!acs));
        fragments.add(GoodsListFrag.get(goodsCategoryId, "3",!acs));
        fragments.add(GoodsListFrag.get(goodsCategoryId, "4",acs));


        fragmentss.add(GoodsListFrag.get(goodsCategoryId, "1",acs));
        fragmentss.add(GoodsListFrag.get(goodsCategoryId, "2",!acs));
        fragmentss.add(GoodsListFrag.get(goodsCategoryId, "3",!acs));
        fragmentss.add(GoodsListFrag.get(goodsCategoryId, "4",acs));
        strings.add("默认");
        strings.add("销量");
        strings.add("信用");
        strings.add("价格");

        slidingFragmentViewPager = new SlidingFragmentViewPager(getChildFragmentManager(), strings, fragmentss, getActivity());
        mViewPager.setAdapter(slidingFragmentViewPager);
        mSlidView.setViewPager(mViewPager);
        mViewPager.setOffscreenPageLimit(strings.size());
        mSlidView.updateTabSelection(0);

        //todo 待确定sace的具体用处

//                for (int i = 0; i < fragments.size(); i++) {
//                    fragments.get(i).setSace(sace);
//                }

//        mViewPager.setOffscreenPageLimit(fragments.size());
//        mViewPager.setAdapter(new PagerAdapter(getChildFragmentManager(), fragments, mTitles));
//        mSlidView.setViewPager(mViewPager);
//        mViewPager.addOnPageChangeListener(this);

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
    protected void initData() {

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

        private List<com.live.shoplib.ui.frag.GoodsListFrag> mFragments;
        private String[] mTitles;

        public PagerAdapter(FragmentManager fm, List<com.live.shoplib.ui.frag.GoodsListFrag> mFragments, String[] mTitles) {
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
