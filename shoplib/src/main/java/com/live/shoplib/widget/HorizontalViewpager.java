package com.live.shoplib.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.viewpager.widget.ViewPager;

/**
 * Created by Alan on 2019/7/29.
 */
public class HorizontalViewpager extends ViewPager {


    public HorizontalViewpager(Context context) {
        super(context);
    }

    public HorizontalViewpager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public boolean dispatchTouchEvent(MotionEvent ev) {
        //下面这句话的作用 告诉父view，我的单击事件我自行处理，不要阻碍我。
        getParent().requestDisallowInterceptTouchEvent(true);
        return super.dispatchTouchEvent(ev);
    }

}
