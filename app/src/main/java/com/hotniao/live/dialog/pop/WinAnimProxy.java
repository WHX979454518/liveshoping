package com.hotniao.live.dialog.pop;

import com.hotniao.live.R;

/**
 * 窗口动画中间过度类
 * 作者：Mr.Xu
 * 时间：2017/12/6
 */
public class WinAnimProxy {

    public Type type;

    enum Type {
        tranRight, tranLeft
    }

    public static int getAnim(Type type) {
        switch (type) {
            case tranLeft:
                return R.anim.enter_to_left;
            case tranRight:
                return R.anim.enter_to_left;
            default:
                return 0;
        }
    }

}
