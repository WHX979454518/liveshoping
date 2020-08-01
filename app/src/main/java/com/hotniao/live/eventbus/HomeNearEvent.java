package com.hotniao.live.eventbus;

/**
 * 作者：Mr.Xu
 * 时间：2017/12/12
 */
public class HomeNearEvent {
    private String city;
    private String pro;

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPro() {
        return pro;
    }

    public void setPro(String pro) {
        this.pro = pro;
    }

    public HomeNearEvent(String city, String pro) {
        this.city = city;
        this.pro = pro;

    }
}
