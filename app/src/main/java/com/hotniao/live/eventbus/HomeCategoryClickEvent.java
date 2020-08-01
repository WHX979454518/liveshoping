package com.hotniao.live.eventbus;

/**
 * Created by Alan on 2019/7/29.
 */
public class HomeCategoryClickEvent {
    private String category = "";

    public HomeCategoryClickEvent(String category) {
        this.category = category;
    }

    public String getCategory() {
        return category;
    }
}
