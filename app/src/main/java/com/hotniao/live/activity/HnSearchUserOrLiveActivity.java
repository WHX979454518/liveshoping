package com.hotniao.live.activity;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.hn.library.base.BaseActivity;
import com.hn.library.base.BaseFragment;
import com.hn.library.tab.SlidingTabLayout;
import com.hn.library.tab.listener.OnTabSelectListener;
import com.hn.library.utils.HnToastUtils;
import com.hn.library.view.HnEditText;
import com.hotniao.live.R;
import com.hotniao.live.adapter.MyTabPagerAdapter;
import com.hotniao.live.eventbus.HnSearchEvent;
import com.hotniao.live.fragment.search.HnSearchLiveFragment;
import com.hotniao.live.fragment.search.HnSearchUserFragment;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

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

public class HnSearchUserOrLiveActivity extends BaseActivity {
    @BindView(R.id.mEtSearch)
    HnEditText mEtSearch;
    @BindView(R.id.mViewPager)
    ViewPager mViewPager;
    @BindView(R.id.mSlidTab)
    SlidingTabLayout mSlidTab;

    private String mSearchContent;
    private String[]       mTitles;
    private List<Fragment> mFragments;


    @Override
    public int getContentViewId() {
        return R.layout.activity_search_user_live;
    }

    @Override
    public void onCreateNew(Bundle savedInstanceState) {
        mTitles=getResources().getStringArray(R.array.search_title);
        setShowTitleBar(false);
        mFragments = new ArrayList<>();
        mFragments.add( HnSearchLiveFragment.getInstance(getIntent().getStringExtra("keyWord")));
        mFragments.add( HnSearchLiveFragment.getInstance(getIntent().getStringExtra("keyWord")));
//        mFragments.add( HnSearchUserFragment.getInstance(getIntent().getStringExtra("keyWord")));

        mViewPager.setOffscreenPageLimit(mTitles.length);
        mViewPager.setAdapter(new MyTabPagerAdapter(getSupportFragmentManager(),mFragments,mTitles));

        mSlidTab.setViewPager(mViewPager);
        mSlidTab.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
            }

            @Override
            public void onTabReselect(int position) {
            }
        });

        mEtSearch.setText(getIntent().getStringExtra("keyWord"));
    }

    @Override
    public void getInitData() {
        mEtSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                                                @Override
                                                public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                                                    if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                                                        ((InputMethodManager) mEtSearch.getContext().getSystemService(Context.INPUT_METHOD_SERVICE))
                                                                .hideSoftInputFromWindow(HnSearchUserOrLiveActivity.this.getCurrentFocus().getWindowToken(),
                                                                        InputMethodManager.HIDE_NOT_ALWAYS);
                                                        String content = mEtSearch.getText().toString().trim();
                                                        if (TextUtils.isEmpty(content)) {
                                                            HnToastUtils.showToastShort("请输入搜索内容");
                                                        } else {
                                                            EventBus.getDefault().post(new HnSearchEvent(content));
                                                        }
                                                        return true;
                                                    }
                                                    return false;
                                                }
                                            }

        );
    }


    @OnClick({R.id.mTvCancel})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.mTvCancel:
                finish();
                break;

        }
    }


}
