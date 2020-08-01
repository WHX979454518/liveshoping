package com.live.shoplib.bean;

/**
 * 作者：Mr.Xu
 * 时间：2018/1/3
 */
public class OrderRefreshEvent {

    private int pos;
    private String keys;


    public String getKeys() {
        return keys;
    }

    public void setKeys(String keys) {
        this.keys = keys;
    }

    public OrderRefreshEvent(int pos, String keys) {

        this.pos = pos;
        this.keys = keys;
    }

    public OrderRefreshEvent(int pos) {
        this.pos = pos;
    }

    public int getPos() {
        return pos;
    }

    public void setPos(int pos) {
        this.pos = pos;
    }
}
