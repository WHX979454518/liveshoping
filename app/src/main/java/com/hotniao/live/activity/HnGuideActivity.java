package com.hotniao.live.activity;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.hn.library.base.BaseActivity;
import com.hn.library.global.HnConstants;
import com.hn.library.manager.HnAppManager;
import com.hn.library.utils.HnPrefUtils;
import com.hn.library.utils.HnUtils;
import com.hotniao.live.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Copyright (C) 2017,深圳市红鸟网络科技股份有限公司 All rights reserved.
 * 项目名称：红鸟电商
 * 类描述：引导页
 * 创建人：阳石柏
 * 创建时间：2017/3/6 16:16
 * 修改人：阳石柏
 * 修改时间：2017/3/6 16:16
 * 修改备注：
 * Version:  1.0.0
 */
public class HnGuideActivity extends BaseActivity {

    @BindView(R.id.guide_pager)
    ViewPager mGuidePager;

    private List<ImageView> mPicImages;
    private int[] PICS = new int[]{R.drawable.guide_1, R.drawable.guide_2, R.drawable.guide_3};


    @Override
    public int getContentViewId() {
        return R.layout.activity_guide;
    }

    @Override
    public void onCreateNew(Bundle savedInstanceState) {
        setShowTitleBar(false);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                    | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
            window.setNavigationBarColor(Color.TRANSPARENT);
        }
    }


    @Override
    public void getInitData() {
        mPicImages = new ArrayList<>();
        for (int i = 0; i < PICS.length; i++) {
            ImageView iv = new ImageView(this);
            //设置图片
            iv.setImageResource(PICS[i]);
            iv.setScaleType(ImageView.ScaleType.FIT_XY);
            mPicImages.add(iv);
        }


        mGuidePager.setAdapter(new HnGuideAdapter());
        mGuidePager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                if (cur == mPicImages.size() - 1) {
                    switch (event.getAction()) {
                        case MotionEvent.ACTION_DOWN:
                            x1 = event.getX();
                            break;
                        case MotionEvent.ACTION_UP:
                            x2 = event.getX();
                            if (x1 - x2 > HnUtils.dip2px(HnGuideActivity.this, 50) && x1 != 0) {
                                HnPrefUtils.setBoolean(HnConstants.Setting.SPLASH_FIRST_USE, false);
                                openActivity(HnLoginActivity.class);
                                HnAppManager.getInstance().finishActivity(HnGuideActivity.class);
                            }
                            break;
                    }
                }
                return false;
            }
        });
    }

    int cur = 0;
    float x1 = 0;
    float x2 = 0;


    class HnGuideAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            if (mPicImages != null) {
                return mPicImages.size();
            }
            return 0;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ImageView view = mPicImages.get(position);
            container.addView(view);
            cur = position;
            return view;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }
}
