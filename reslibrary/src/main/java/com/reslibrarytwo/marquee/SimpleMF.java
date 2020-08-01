package com.reslibrarytwo.marquee;

import android.content.Context;
import android.widget.TextView;


/**
 * 作者：Mr.Xu
 * 时间：2018/2/6
 */
public class SimpleMF <E extends CharSequence> extends MarqueeFactory<TextView, E> {
    public SimpleMF(Context mContext) {
        super(mContext);
    }

    @Override
    public TextView generateMarqueeItemView(E data) {
        TextView mView = new TextView(mContext);
        mView.setText(data);
        return mView;
    }
}