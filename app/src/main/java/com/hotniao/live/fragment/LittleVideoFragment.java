package com.hotniao.live.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.viewpager.widget.ViewPager;

import com.hn.library.base.BaseFragment;
import com.hn.library.global.HnUrl;
import com.hn.library.http.HnHttpUtils;
import com.hn.library.http.HnResponseHandler;
import com.hn.library.tab.SlidingTabLayout;
import com.hotniao.live.R;
import com.hotniao.live.adapter.LittleVideoScrollViewPagerAdapter;
import com.hotniao.live.model.SpikeTitleModel;
import com.reslibrarytwo.marquee.Util;

/**
 * 创建人：Alan
 * 使用 LittleVideoFragment
 */
public class LittleVideoFragment extends BaseFragment {

    SlidingTabLayout mSlidingTab;
    ViewPager mViewPager;


    @Override
    public int getContentViewId() {

        return R.layout.act_little_video;
    }

    @Override
    public void initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mSlidingTab = mRootView.findViewById(R.id.little_video_mSlidingTab);
        mViewPager = mRootView.findViewById(R.id.little_video_view_pager);
    }


    @Override
    protected void initEvent() {

    }

    @Override
    protected void initData() {
        getLittleVideoCategoryData();
        bindData(new String[]{"关注", "推荐", "视频","文字"});
    }


    public void getLittleVideoCategoryData() {
        //todo 待确定获取短视频分类的接口
        HnHttpUtils.postRequest(HnUrl.SPIKE_TIME_LIST, null, TAG, new HnResponseHandler<SpikeTitleModel>(SpikeTitleModel.class) {
            @Override
            public void hnSuccess(String response) {
                //todo 处理返回的信息
                //bindData()
            }

            @Override
            public void hnErr(int errCode, String msg) {
            }
        });
    }

    private void bindData(final String[] dataArray) {
        if (dataArray == null || dataArray.length == 0) {
            return;
        }
        LittleVideoScrollViewPagerAdapter adapter = new LittleVideoScrollViewPagerAdapter(getChildFragmentManager(), dataArray);
        mViewPager.setAdapter(adapter);
        mSlidingTab.setTabWidth(Util.px2Dp(getContext(),Util.getWindowWidth(getActivity()) / 4));

        mSlidingTab.setViewPager(mViewPager, dataArray);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        //todo 确定设置选中的index
        mViewPager.setCurrentItem(0);
    }


}
