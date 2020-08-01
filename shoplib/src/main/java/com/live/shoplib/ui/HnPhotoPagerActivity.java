package com.live.shoplib.ui;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.core.view.ViewCompat;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.hn.library.base.BaseActivity;
import com.hn.library.global.HnUrl;
import com.hn.library.utils.HnDimenUtil;
import com.live.shoplib.R;
import com.live.shoplib.other.img.AnimUtil;
import com.live.shoplib.other.img.CircleIndicator;
import com.live.shoplib.other.img.PinchImageView;
import com.live.shoplib.other.img.PinchImageViewPager;

import java.io.File;
import java.util.ArrayList;


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
public class HnPhotoPagerActivity extends BaseActivity {
    PinchImageViewPager mViewPager;
    CircleIndicator mCircleIndicator;


    public static final String KEY_START_POS = "start_pos";
    public static final String KEY_START_X = "start_x";
    public static final String KEY_START_Y = "start_y";
    public static final String KEY_START_W = "start_w";
    public static final String KEY_START_H = "start_h";
    public static final String KEY_PHOTO_LIST = "photo_list";

    protected ArrayList<String> mPhotoList;

    protected long ANIM_TIME = 300;
    protected ValueAnimator mValueAnimator;

    //开始时的位置(ViewPager的位置)
    protected int mStartPosition;
    //动画开始 坐标x,y 和宽高
    protected int mStartX, mStartY, mStartW, mStartH;
    private boolean isToFinish = false;

    public static void launcher(Activity activity, int position, ArrayList<String> values) {
        Intent intent = new Intent(activity, HnPhotoPagerActivity.class);
        Bundle args = new Bundle();
        args.putInt(KEY_START_POS, position);
        args.putStringArrayList(KEY_PHOTO_LIST, values);
        intent.putExtras(args);
        activity.startActivity(intent);
    }

    public static void launcher(Activity activity, View view, int position, String value) {
        if (TextUtils.isEmpty(value)) {
            return;
        }

        ArrayList<String> values = new ArrayList<>();
        values.add(value);

        launcher(activity, view, position, values);
    }

    /**
     * 图片地址需要包含 Http.BASE_IMAGE_URL
     */
    public static void launcher(Activity activity, View view, int position, ArrayList<String> values) {
        Intent intent = new Intent(activity, HnPhotoPagerActivity.class);
        Bundle args = new Bundle();
        args.putInt(KEY_START_POS, Math.min(values.size(), Math.max(0, position)));

        int[] rt = new int[2];
        view.getLocationOnScreen(rt);

        float w = view.getMeasuredWidth();
        float h = view.getMeasuredHeight();

        float x = rt[0] + w / 2;
        float y = rt[1] + h / 2;

        args.putInt(KEY_START_X, rt[0]);
        args.putInt(KEY_START_Y, rt[1]);
        args.putInt(KEY_START_W, (int) w);
        args.putInt(KEY_START_H, (int) h);
        args.putStringArrayList(KEY_PHOTO_LIST, values);

        intent.putExtras(args);
        activity.startActivity(intent);
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_photo_pager;
    }

    @Override
    public void onCreateNew(Bundle savedInstanceState) {
        setShowTitleBar(false);
        mViewPager = findViewById(R.id.mViewPager);
        mCircleIndicator = findViewById(R.id.circle_indicator_view);
        final Bundle extras = getIntent().getExtras();
        mStartPosition = extras.getInt(KEY_START_POS);
        mPhotoList = extras.getStringArrayList(KEY_PHOTO_LIST);
        mStartX = extras.getInt(KEY_START_X);
        mStartY = extras.getInt(KEY_START_Y);
        mStartW = extras.getInt(KEY_START_W);
        mStartH = extras.getInt(KEY_START_H);
    }

    @Override
    public void getInitData() {
        initWindow();

        ImageAdapter imageAdapter = new ImageAdapter();
        mViewPager.setAdapter(imageAdapter);
        mCircleIndicator.setViewPager(mViewPager);

        mViewPager.setCurrentItem(mStartPosition);

        mViewPager.addOnPageChangeListener(new PinchImageViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                PinchImageViewPager.ItemInfo curItemInfo = mViewPager.getCurItemInfo();
                if (curItemInfo != null) {
                    ViewGroup viewGroup = (ViewGroup) curItemInfo.object;
                    mViewPager.setMainPinchImageView((PinchImageView) viewGroup.getChildAt(0));
                }
            }
        });
    }

    private void initWindow() {
        Window window = getWindow();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                window.setStatusBarColor(Color.TRANSPARENT);
            } else {
                window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            }
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        } else {
            window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
    }

    private void startAnimation() {
        mCircleIndicator.setVisibility(View.GONE);
        mValueAnimator = AnimUtil.startArgb(mRlParent, Color.TRANSPARENT, Color.BLACK, ANIM_TIME);
        final int screenWidth = HnDimenUtil.getScreenWidth(this);
        final int screenHeight = HnDimenUtil.getScreenHeight(this);
        mViewPager.setX(mStartX + mStartW / 2 - screenWidth / 2);
        mViewPager.setY(mStartY + mStartH / 2 - screenHeight / 2);
        mViewPager.setScaleX((mStartW + 0f) / screenWidth);
        mViewPager.setScaleY((mStartH + 0f) / screenHeight);
        mViewPager.animate().x(0).y(0).scaleX(1).scaleY(1).setInterpolator(new DecelerateInterpolator()).setDuration(ANIM_TIME).start();
        mViewPager.animate().setListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                if (mPhotoList.size() > 1) {
                    mCircleIndicator.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }

    private void animToFinish() {
        if (isToFinish) {
            return;
        }
        isToFinish = true;
        mCircleIndicator.setVisibility(View.GONE);
        AnimUtil.startArgb(mRlParent, Color.BLACK, Color.TRANSPARENT, ANIM_TIME);
        ViewCompat.animate(mViewPager).alpha(0).scaleX(0.2f).scaleY(0.2f)
                .setInterpolator(new AccelerateInterpolator())
                .setDuration(ANIM_TIME)
                .withEndAction(new Runnable() {
                    @Override
                    public void run() {
                        Window window = getWindow();
                        WindowManager.LayoutParams attributes = window.getAttributes();
                        attributes.alpha = 0;
                        window.setAttributes(attributes);
                        isToFinish = false;
                        finish();
                    }
                }).start();
    }

    @Override
    public void onBackPressed() {
        animToFinish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mValueAnimator != null)
            mValueAnimator.cancel();
        if (mViewPager != null)
            mViewPager.animate().cancel();
    }

    class ImageAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return mPhotoList == null ? 0 : mPhotoList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {

            RelativeLayout layout = new RelativeLayout(HnPhotoPagerActivity.this);
            PinchImageView imageView = new PinchImageView(HnPhotoPagerActivity.this);
            imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);

            String url = mPhotoList.get(position);
            File file = new File(url);

            if (file.exists()) {

                Glide.with(HnPhotoPagerActivity.this.getApplicationContext())
                        .load(file)
                        .placeholder(R.drawable.default_live)
                        .into(imageView);
            } else {

                Glide.with(HnPhotoPagerActivity.this.getApplicationContext())
                        .load(HnUrl.setImageUrl(url))
                        .placeholder(R.drawable.default_live)
                        .into(imageView);
            }

            layout.addView(imageView, new RelativeLayout.LayoutParams(-1, -1));
            container.addView(layout, new ViewGroup.LayoutParams(-1, -1));
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    animToFinish();
                }
            });

            imageView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {

                    return true;
                }
            });
            return layout;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }

}











