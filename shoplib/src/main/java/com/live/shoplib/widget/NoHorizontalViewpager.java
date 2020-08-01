package com.live.shoplib.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.viewpager.widget.ViewPager;

/**
 * Created by Alan on 2019/7/25.
 */
public class NoHorizontalViewpager extends ViewPager {


        private boolean DISABLE=false;

        public NoHorizontalViewpager(Context context){

            super(context);

        }

        public NoHorizontalViewpager(Context context, AttributeSet attrs){

            super(context,attrs);
        }

        @Override
        public boolean onInterceptTouchEvent(MotionEvent arg0) {
            return DISABLE&&super.onInterceptTouchEvent(arg0);
        }

        @Override
        public boolean onTouchEvent(MotionEvent arg0) {
            return DISABLE&&super.onTouchEvent(arg0);
        }


}
