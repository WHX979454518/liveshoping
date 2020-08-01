package com.hotniao.live.eventbus;

/**
 * 作者：Mr.Xu
 * 时间：2017/12/26
 */
public class StoreAutEvent {
    private String id, type,name,isFood;
    public StoreAutEvent(String id, String type,String name,String isFood) {
        this.id = id;
        this.type = type;
        this.name = name;
        this.isFood = isFood;
    }

    public String getIsFood() {
        return isFood;
    }

    public void setIsFood(String isFood) {
        this.isFood = isFood;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
