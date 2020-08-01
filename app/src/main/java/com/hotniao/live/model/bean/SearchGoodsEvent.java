package com.hotniao.live.model.bean;

/**
 * 作者：Mr.Xu
 * 时间：2017/12/7
 */
public class SearchGoodsEvent {

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    private String key;

    public SearchGoodsEvent(String lastKey) {
        this.key = lastKey;
    }
}
