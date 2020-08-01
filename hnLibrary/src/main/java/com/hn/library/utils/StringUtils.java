package com.hn.library.utils;

import android.view.View;
import android.widget.TextView;

/**
 * 处理单位
 * 作者：Mr.Xu
 * 时间：2017/12/7
 */
public class StringUtils {

    public static void setNum(View textView, String num) {
        ((TextView) textView).setText(num);
    }


}
