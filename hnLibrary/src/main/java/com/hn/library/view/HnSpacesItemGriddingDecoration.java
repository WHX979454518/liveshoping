package com.hn.library.view;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.View;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Copyright (C) 2016,深圳市红鸟网络科技股份有限公司 All rights reserved.
 * 项目名称：
 * 类描述：RecyclerView_GridView设置item间距
 * 创建人：刘勇
 * 创建时间：2017/2/22 16:22
 * 修改人：刘勇
 * 修改时间：2017/2/22 16:34
 * 修改备注：
 * Version:  1.0.0
 */

public class HnSpacesItemGriddingDecoration extends RecyclerView.ItemDecoration {
    private int dividerWith = 1;
//    private Paint paint;
    private RecyclerView.LayoutManager layoutManager;

    // 构造方法,可以在这里做一些初始化,比如指定画笔颜色什么的
    public HnSpacesItemGriddingDecoration() {
        initPaint();
//        paint.setColor(0xffff0000);
    }

    private void initPaint() {
//        if (paint == null) {
//            paint = new Paint(Paint.ANTI_ALIAS_FLAG);
//            paint.setIsBalckStyle(Paint.Style.FILL);
//        }
    }

    public HnSpacesItemGriddingDecoration setDividerWith(int dividerWith) {
        this.dividerWith = dividerWith;
        return this;

    }

    public HnSpacesItemGriddingDecoration setDividerColor(int color) {
        initPaint();
//        paint.setColor(color);
        return this;
    }

    /**
     * 指定item之间的间距(就是指定分割线的宽度)   回调顺序 1
     *
     * @param outRect Rect to receive the output.
     * @param view    The child view to decorate
     * @param parent  RecyclerView this ItemDecoration is decorating
     * @param state   The current state of RecyclerView.
     */
    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        if (layoutManager == null) {
            layoutManager = parent.getLayoutManager();
        }
        // 适用 LinearLayoutManager 和 GridLayoutManager
        if (layoutManager instanceof LinearLayoutManager) {
            int orientation = ((LinearLayoutManager) layoutManager).getOrientation();
            if (orientation == LinearLayoutManager.VERTICAL) {
                // 水平分割线将绘制在item底部
                outRect.bottom = dividerWith;
            } else if (orientation == LinearLayoutManager.HORIZONTAL) {
                // 垂直分割线将绘制在item右侧
                outRect.right = dividerWith;
            }
            if (layoutManager instanceof GridLayoutManager) {
                GridLayoutManager.LayoutParams lp = (GridLayoutManager.LayoutParams) view.getLayoutParams();
                // 如果是 GridLayoutManager 则需要绘制另一个方向上的分割线
                if (orientation == LinearLayoutManager.VERTICAL && lp != null && lp.getSpanIndex() > 0) {
                    // 如果列表是垂直方向,则最左边的一列略过
                    outRect.left = dividerWith;
                } else if (orientation == LinearLayoutManager.HORIZONTAL && lp != null && lp.getSpanIndex() > 0) {
                    // 如果列表是水平方向,则最上边的一列略过
                    outRect.top = dividerWith;
                }
            }
        }
    }

    /**
     * 在item 绘制之前调用(就是绘制在 item 的底层)  回调顺序 2
     * 一般分割线在这里绘制
     * 看到canvas,对自定义控件有一定了解的话,就能想到为什么说给RecyclerView设置分割线更灵活了
     *
     * @param c      Canvas to draw into
     * @param parent RecyclerView this ItemDecoration is drawing into
     * @param state  The current state of RecyclerView
     */
//    @Override
//    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
//        super.onDraw(c, parent, state);
//        // 这个值是为了补偿横竖方向上分割线交叉处间隙
//        int offSet = (int) Math.ceil(dividerWith * 1f / 2);
//        for (int i = 0; i < parent.getChildCount(); i++) {
//            View child = parent.getChildAt(i);
//            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
//            int left1 = child.getRight() + params.rightMargin;
//            int right1 = left1 + dividerWith;
//            int top1 = child.getTop() - offSet - params.topMargin;
//            int bottom1 = child.getBottom() + offSet + params.bottomMargin;
//            //绘制分割线(矩形)
//            c.drawRect(left1, top1, right1, bottom1, paint);
//            int left2 = child.getLeft() - offSet - params.leftMargin;
//            int right2 = child.getRight() + offSet + params.rightMargin;
//            int top2 = child.getBottom() + params.bottomMargin;
//            int bottom2 = top2 + dividerWith;
//            //绘制分割线(矩形)
//            c.drawRect(left2, top2, right2, bottom2, paint);
//        }
//    }

    /**
     * 在item 绘制之后调用(就是绘制在 item 的上层)  回调顺序 3
     * 也可以在这里绘制分割线,和上面的方法 二选一
     *
     * @param c      Canvas to draw into
     * @param parent RecyclerView this ItemDecoration is drawing into
     * @param state  The current state of RecyclerView
     */
//    @Override
//    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
//        super.onDrawOver(c, parent, state);
//    }


}