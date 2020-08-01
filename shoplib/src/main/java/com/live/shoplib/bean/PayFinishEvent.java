package com.live.shoplib.bean;

/**
 * 作者：Mr.Xu
 * 时间：2018/1/25
 */
public class PayFinishEvent {
    private String groupBuyOrderId = "";

    public PayFinishEvent(String groupBuyOrderId) {
        this.groupBuyOrderId = groupBuyOrderId;
    }

    public PayFinishEvent() {
    }

    public String getGroupBuyOrderId() {
        return groupBuyOrderId;
    }

}
