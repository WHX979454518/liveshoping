package com.hotniao.livelibrary.biz;

/**
 * create by Mr.x
 * on 2018/6/11
 */

public class CareEvent {
    public CareEvent(boolean show) {
        this.show = show;
    }


    public boolean isShow() {
        return show;
    }

    public void setShow(boolean show) {
        this.show = show;
    }

    boolean show;

}
