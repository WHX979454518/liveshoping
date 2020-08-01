package com.hotniao.live.eventbus;

/**
 * Created by Administrator on 2018/4/13.
 */

public class BigPicEvent {

    public BigPicEvent(boolean big) {
        this.big = big;
    }

    boolean big ;

    public boolean isBig() {
        return big;
    }

    public void setBig(boolean big) {
        this.big = big;
    }
}
