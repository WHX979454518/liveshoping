package com.hotniao.live.dialog.pop;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

import com.hotniao.live.R;


/**
 * 首页-分类菜单 弃用
 * 作者：Mr.Xu
 * 时间：2017/12/6
 */
public class SortMenu extends PopupWindow  {

    private View pop;


    public SortMenu(Context context) {
        pop = View.inflate(context, R.layout.pop_sort_menu, null);
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
        this.setAnimationStyle(WinAnimProxy.getAnim(WinAnimProxy.Type.tranLeft));

    }


    public void showPop() {
        if (this != null && !this.isShowing() && pop != null) {
            this.showAtLocation(pop, Gravity.BOTTOM, 0, 0);
        }
    }
    public void showPop(View view,int x,int y) {
        if (this != null && !this.isShowing() && view != null) {
            this.showAsDropDown(view, x, y);
        }
    }


    public void dismissPop() {
        if (this != null || !this.isShowing()) {
            this.dismiss();
        }
    }

}