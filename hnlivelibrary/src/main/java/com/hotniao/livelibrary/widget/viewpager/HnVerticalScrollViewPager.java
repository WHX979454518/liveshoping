package com.hotniao.livelibrary.widget.viewpager;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;

/**
 * Copyright (C) 2017,深圳市红鸟网络科技股份有限公司 All rights reserved.
 * 项目名称：youbo
 * 类描述：动态设置是否可以上下滑动    该类采用两种方案：1：传递Actviity自动判断是否有键盘弹起，若键盘弹起，则不进行滑动
 * 2：自己动态设置isCanScroll来进行动态控制是否可以滑动，不可传递Activity
 * 创建人：mj
 * 创建时间：2017/10/16 15:22
 * 修改人：Administrator
 * 修改时间：2017/10/16 15:22
 * 修改备注：
 * Version:  1.0.0
 */
public class HnVerticalScrollViewPager extends VerticalViewPager {


    /**
     * 是否可以滑动
     */
    private boolean isCanScroll = true;
    /**
     * 上下文
     */
    private Activity mActivity;
    /**
     * 监听器
     */
    private HnVerticalScrollListener listener;

    public HnVerticalScrollViewPager(Context context) {
        this(context, null);
    }

    public HnVerticalScrollViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (mActivity != null) {
            if (!isSoftShowing()) {
                //允许滑动则应该调用父类的方法
                return super.onTouchEvent(ev);
            } else {
                //禁止滑动则不做任何操作，直接返回true即可
                return true;
            }
        } else {
            if (isCanScroll) {
                //允许滑动则应该调用父类的方法
                return super.onTouchEvent(ev);
            } else {
                //禁止滑动则不做任何操作，直接返回true即可
                return true;
            }
        }

    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent arg0) {
        if (mActivity != null) {
            if (!isSoftShowing()) {
            } else {
                return false;
            }
        } else {
            if (isCanScroll) {

            } else {
                return false;
            }
        }
        return super.onInterceptTouchEvent(arg0);
    }

    //设置是否允许滑动，true是可以滑动，false是禁止滑动
    public void setIsCanScroll(boolean isCanScroll) {
        this.isCanScroll = isCanScroll;
    }


    public interface HnVerticalScrollListener {
        void IsCanScroll(boolean isCanScroll);
    }

    public void setVerticalScrollListener(HnVerticalScrollListener listener) {
        this.listener = listener;
    }


    /**
     * 判断键盘弹起
     *
     * @return
     */
    private boolean isSoftShowing() {
        if (mActivity == null) return false;
        //获取当前屏幕内容的高度
        int screenHeight = mActivity.getWindow().getDecorView().getHeight();
        //获取View可见区域的bottom
        Rect rect = new Rect();
        mActivity.getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);


        DisplayMetrics metrics=new DisplayMetrics();
        mActivity.getWindowManager().getDefaultDisplay().getRealMetrics(metrics);

        return screenHeight - /*metrics.heightPixels*/rect.bottom >150;
    }

    public void setActivity(Activity activity) {
        this.mActivity = activity;
    }


}
