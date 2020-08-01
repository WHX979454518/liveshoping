package com.hn.library.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Build;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;

import com.hn.library.HnBaseApplication;

/**
 * Copyright (C) 2016,深圳市红鸟网络科技股份有限公司 All rights reserved.
 * 项目名称：
 * 类描述：UI工具类
 * 创建人：Kevin
 * 创建时间：2016/4/26 10:53
 * 修改人：Kevin
 * 修改时间：2016/4/26 10:53
 * 修改备注：
 * Version: 1.0.0
 */
public class HnUiUtils {

    /**
     * 设置全屏
     * @param activity
     */
    public static void setFullScreen(Activity activity){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = activity.getWindow();
            //全屏显示
            window.getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
    }

    /**
     * 设置标题栏的padding,避免全屏显示后内容显示在状态栏中
     */
    public static void setTitlePadding(Activity activity, LinearLayout llTitle) {
        //当系统版本为5.0以上版本时采用全屏显示 ,通过在Fragment中动态设置标题栏的padding和height来调整显示
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            int statusHeight = HnUtils.getStatusBarHeight(activity);
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) llTitle.getLayoutParams();
            params.height = statusHeight + HnDimenUtil.dp2px(activity, 48.0f);
            llTitle.setLayoutParams(params);
            llTitle.setPadding(0, statusHeight, 0, 0);
        }
    }



    /**
     * 根据传入的类(class)打开指定的activity
     *
     * @param pClass
     */
    public static void openActivity(Context context,Class<?> pClass) {
        Intent itent = new Intent();
        itent.setClass(context, pClass);
        context.startActivity(itent);
    }
    /**
     * 得到上下文
     */
    public static Context getContext() {
        return HnBaseApplication.getContext();
    }

    /**
     * 得到Resource对象
     */
    public static Resources getResources() {
        return getContext().getResources();
    }

    /**
     * 得到string.xml中的字符
     */
    public static String getString(int resId) {
        return getResources().getString(resId);
    }

}
