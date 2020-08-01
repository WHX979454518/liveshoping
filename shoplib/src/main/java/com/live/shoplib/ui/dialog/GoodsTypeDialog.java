package com.live.shoplib.ui.dialog;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.live.shoplib.R;

/**
 * create by Mr.x
 * on 2018/5/3
 */

public class GoodsTypeDialog extends PopupWindow {

    private View pop;
    public TextView mTvEdit, mTvAttr, mTvSpec, mTvBack;

    public GoodsTypeDialog(Context context) {
        pop = View.inflate(context, R.layout.dialog_goods_type, null);

        mTvEdit = pop.findViewById(R.id.mTvEdit);
        mTvAttr = pop.findViewById(R.id.mTvAttr);
        mTvSpec = pop.findViewById(R.id.mTvSpec);
        mTvBack = pop.findViewById(R.id.mTvBack);
        pop.findViewById(R.id.mView).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        mTvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        mTvAttr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (click != null) click.setAttr();
                dismiss();
            }
        });
        mTvEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (click != null) click.setEdit();
                dismiss();
            }
        });
        mTvSpec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (click != null) click.setSpec();
                dismiss();
            }
        });

        // 设置SelectPicPopupWindow的View
        this.setContentView(pop);
        // 设置SelectPicPopupWindow弹出窗体的宽
        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        // 设置SelectPicPopupWindow弹出窗体的高
        this.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        // 设置SelectPicPopupWindow弹出窗体可点击
        this.setFocusable(true);
        this.setOutsideTouchable(true);
        // 刷新状态
        this.update();
        // 实例化一个ColorDrawable颜色为半透明
        this.setBackgroundDrawable(new BitmapDrawable());

    }

    public GoodsTypeDialog setClickListen(OnClick click) {
        this.click = click;
        return this;
    }

    public OnClick click;

    public interface OnClick {
        void setEdit();

        void setSpec();

        void setAttr();
    }
}
