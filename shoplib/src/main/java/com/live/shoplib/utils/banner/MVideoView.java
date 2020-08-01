package com.live.shoplib.utils.banner;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.VideoView;


public class MVideoView extends VideoView
{
    public MVideoView(Context context)
    {
        super(context);
    }

    public MVideoView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    public MVideoView(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    {
        //super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = getDefaultSize(getWidth(), widthMeasureSpec);
        int height = getDefaultSize(getHeight(), heightMeasureSpec);
        setMeasuredDimension(width, height);
    }
}
