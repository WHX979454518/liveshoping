package com.reslibrarytwo.marquee;

import android.view.View;

/**
 * 作者：Mr.Xu
 * 时间：2018/2/6
 */
public interface OnItemClickListener<V extends View, E> {
    void onItemClickListeners(V mView, E mData, int mPosition);
}