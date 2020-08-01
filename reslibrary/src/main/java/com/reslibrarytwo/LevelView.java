package com.reslibrarytwo;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;


/**
 * create by Mr.x
 * on 2018/7/4
 */
public class LevelView extends LinearLayout {

    private View mRootView;
    private TextView mTvLevel;

    public LevelView(Context context) {
        super(context);
        init();
    }

    public LevelView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        mRootView = inflate(getContext(), R.layout.layout_level, this);
        mTvLevel = mRootView.findViewById(R.id.mTvLevel);
    }

    public void setLevelAnchor(int level){
        if (mTvLevel == null) return;
        mTvLevel.setText("Lv"+level);
        mTvLevel.setCompoundDrawablePadding(0);
        mTvLevel.setPadding(dp2px(20),0,dp2px(6),0);
        if (level < 20) {
            mTvLevel.setBackground(getResources().getDrawable(R.mipmap.level_anchor_1));
        } else if (level < 40) {
            Drawable drawable = getResources().getDrawable(R.mipmap.level_4);
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            mTvLevel.setCompoundDrawables(drawable, null, null, null);
            mTvLevel.setBackground(getResources().getDrawable(R.drawable.shape_level_4));
        } else if (level < 60) {
            Drawable drawable = getResources().getDrawable(R.mipmap.level_6);
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            mTvLevel.setCompoundDrawables(drawable, null, null, null);
            mTvLevel.setBackground(getResources().getDrawable(R.drawable.shape_level_6));
        } else if (level < 80) {
            Drawable drawable = getResources().getDrawable(R.mipmap.level_9);
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            mTvLevel.setCompoundDrawables(drawable, null, null, null);
            mTvLevel.setBackground(getResources().getDrawable(R.drawable.shape_level_9));
        } else {
            Drawable drawable = getResources().getDrawable(R.mipmap.level_10);
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            mTvLevel.setCompoundDrawables(drawable, null, null, null);
            mTvLevel.setBackground(getResources().getDrawable(R.drawable.shape_level_10));
        }
    }

    public void setLevel(int level) {
        if (mTvLevel == null) return;
        mTvLevel.setText(level+"");
        mTvLevel.setCompoundDrawablePadding(dp2px(2));
        mTvLevel.setPadding(dp2px(2),0,dp2px(6),0);
        if (level < 20) {
            Drawable drawable = getResources().getDrawable(R.mipmap.level_1);
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            mTvLevel.setCompoundDrawables(drawable, null, null, null);
            mTvLevel.setBackground(getResources().getDrawable(R.drawable.shape_level_1));
        } else if (level < 30) {
            Drawable drawable = getResources().getDrawable(R.mipmap.level_2);
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            mTvLevel.setCompoundDrawables(drawable, null, null, null);
            mTvLevel.setBackground(getResources().getDrawable(R.drawable.shape_level_2));
        } else if (level < 40) {
            Drawable drawable = getResources().getDrawable(R.mipmap.level_3);
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            mTvLevel.setCompoundDrawables(drawable, null, null, null);
            mTvLevel.setBackground(getResources().getDrawable(R.drawable.shape_level_3));
        } else if (level < 50) {
            Drawable drawable = getResources().getDrawable(R.mipmap.level_4);
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            mTvLevel.setCompoundDrawables(drawable, null, null, null);
            mTvLevel.setBackground(getResources().getDrawable(R.drawable.shape_level_4));
        } else if (level < 60) {
            Drawable drawable = getResources().getDrawable(R.mipmap.level_5);
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            mTvLevel.setCompoundDrawables(drawable, null, null, null);
            mTvLevel.setBackground(getResources().getDrawable(R.drawable.shape_level_5));
        } else if (level < 70) {
            Drawable drawable = getResources().getDrawable(R.mipmap.level_6);
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            mTvLevel.setCompoundDrawables(drawable, null, null, null);
            mTvLevel.setBackground(getResources().getDrawable(R.drawable.shape_level_6));
        } else if (level < 80) {
            Drawable drawable = getResources().getDrawable(R.mipmap.level_7);
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            mTvLevel.setCompoundDrawables(drawable, null, null, null);
            mTvLevel.setBackground(getResources().getDrawable(R.drawable.shape_level_7));
        } else if (level < 90) {
            Drawable drawable = getResources().getDrawable(R.mipmap.level_8);
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            mTvLevel.setCompoundDrawables(drawable, null, null, null);
            mTvLevel.setBackground(getResources().getDrawable(R.drawable.shape_level_8));
        } else if (level < 100) {
            Drawable drawable = getResources().getDrawable(R.mipmap.level_9);
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            mTvLevel.setCompoundDrawables(drawable, null, null, null);
            mTvLevel.setBackground(getResources().getDrawable(R.drawable.shape_level_9));
        } else {
            Drawable drawable = getResources().getDrawable(R.mipmap.level_10);
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            mTvLevel.setCompoundDrawables(drawable, null, null, null);
            mTvLevel.setBackground(getResources().getDrawable(R.drawable.shape_level_10));
        }
    }


    public static int getUserLevelImg(int level) {
        if (level < 20) {
            return R.mipmap.level_1;
        } else if (level < 30) {
            return R.mipmap.level_2;
        } else if (level < 40) {
            return R.mipmap.level_3;
        } else if (level < 50) {
            return R.mipmap.level_4;
        } else if (level < 60) {
            return R.mipmap.level_5;
        } else if (level < 70) {
            return R.mipmap.level_6;
        } else if (level < 80) {
            return R.mipmap.level_7;
        } else if (level < 90) {
            return R.mipmap.level_8;
        } else if (level < 100) {
            return R.mipmap.level_9;
        } else {
            return R.mipmap.level_10;
        }
    }

    public int dp2px( float dpValue) {
        final float scale = getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

}
