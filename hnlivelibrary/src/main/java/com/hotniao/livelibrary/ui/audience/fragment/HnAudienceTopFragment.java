package com.hotniao.livelibrary.ui.audience.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;

import com.hn.library.base.BaseFragment;
import com.hotniao.livelibrary.R;
import com.hotniao.livelibrary.config.HnLiveConstants;
import com.hotniao.livelibrary.model.HnLiveListModel;
import com.hotniao.livelibrary.model.event.HnLiveEvent;
import com.hotniao.livelibrary.widget.viewpager.HnControlScrollViewPager;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

/**
 * Copyright (C) 2017,深圳市红鸟网络科技股份有限公司 All rights reserved.
 * 项目名称：红鸟电商
 * 类描述：用户直播间  -- 空
 * 创建人：阳石柏
 * 创建时间：2017/3/6 16:16
 * 修改人：阳石柏
 * 修改时间：2017/3/6 16:16
 * 修改备注：
 * Version:  1.0.0
 */
public class HnAudienceTopFragment extends BaseFragment   {

     /**布局控件*/
     HnControlScrollViewPager mViewPager;
     RelativeLayout mRootExit;


    /**iewpager两个界面*/
    private  int page = 2;
    /**直播所需要的数据*/
    private HnLiveListModel.LiveListBean bean;



    public static HnAudienceTopFragment newInstance(HnLiveListModel.LiveListBean bean) {
        HnAudienceTopFragment fragment = new HnAudienceTopFragment();
        Bundle b = new Bundle();
        b.putParcelable("data",bean);
        fragment.setArguments(b);
        return fragment;
    }



    @Override
    public int getContentViewId() {
        return R.layout.live_layout_info_fragment;
    }

    @Override
    public void initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
          //初始化视图
        mViewPager= (HnControlScrollViewPager) mRootView.findViewById(R.id.view_pager);
        mRootExit= (RelativeLayout) mRootView.findViewById(R.id.root_view);
        EventBus.getDefault().register(this);

    }

    @Override
    protected void initData() {
        Bundle  bundle=getArguments();
        if(bundle!=null){
            bean=bundle.getParcelable("data");
        }
        mViewPager.setAdapter(new FragmentPagerAdapter(getChildFragmentManager()) {
            @Override
            public Fragment getItem(int position) {

                if (position == 0) {
                    return new HnTopEmptyFragment();

                } else if (position == 1) {

                    return HnAudienceInfoFragment.newInstance(bean);

                }
                return null;
            }

            @Override
            public int getCount() {
                return page;
            }
        });
        mViewPager.setCurrentItem(1);



    }
    @Override
    protected void initEvent() {

    }



    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().post(this);
    }

    @Subscribe
    public   void   onEventBusCallBack(HnLiveEvent event){
        if(event!=null){
            if(HnLiveConstants.EventBus.Scroll_viewPager.equals(event.getType())){//主播在线，可以左右滑动；主播不在线，禁止左右滑动
                boolean  isCanScroll= (boolean) event.getObj();
                mViewPager.setIsCanScroll(isCanScroll);
            }
        }
    }
}
