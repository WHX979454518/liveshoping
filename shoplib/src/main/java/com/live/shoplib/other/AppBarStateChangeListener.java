package com.live.shoplib.other;


import com.google.android.material.appbar.AppBarLayout;

/**
 * 店铺介绍，择叠监听
 * 作者：Mr.Xu
 * 时间：2017/12/11
 */
public abstract class AppBarStateChangeListener implements AppBarLayout.OnOffsetChangedListener {
    public enum State {
        EXPANDED,
        COLLAPSED,
        IDLE
    }

    private State mCurrentState = State.IDLE;

    @Override
    public final void onOffsetChanged(AppBarLayout appBarLayout, int i) {
        float percent = Float.valueOf(Math.abs(i)) / Float.valueOf(appBarLayout.getTotalScrollRange());

        if (i == 0) {
            onStateChanged(appBarLayout, State.EXPANDED, 0);
            mCurrentState = State.EXPANDED;
        } else if (Math.abs(i) >= appBarLayout.getTotalScrollRange()) {
            onStateChanged(appBarLayout, State.COLLAPSED, 1);
            mCurrentState = State.COLLAPSED;
        } else {
            onStateChanged(appBarLayout, State.IDLE, percent);
            mCurrentState = State.IDLE;
        }
    }

    public abstract void onStateChanged(AppBarLayout appBarLayout, State state, float percent);
}

