package com.hotniao.live.model;

/**
 * Copyright (C) 2016,深圳市红鸟网络科技股份有限公司 All rights reserved.
 * 类描述：定位信息
 * 创建人：刘勇
 * 创建时间：2017/2/22 16:22
 * 修改人：刘勇
 * 修改时间：2017/2/22 16:34
 * 修改备注：
 * Version:  1.0.0
 */

public class HnLocationEntity  {

    private  String mLat;//经度
    private String mLng;//纬度
    private String mCity;
    private String mProvince;

    public HnLocationEntity(String mLat, String mLng, String mCity,String mProvince) {
        this.mLat = mLat;
        this.mLng = mLng;
        this.mCity = mCity;
        this.mProvince = mProvince;
    }

    public String getmProvince() {
        return mProvince;
    }

    public void setmProvince(String mProvince) {
        this.mProvince = mProvince;
    }

    public String getmLat() {
        return mLat;
    }

    public void setmLat(String mLat) {
        this.mLat = mLat;
    }

    public String getmLng() {
        return mLng;
    }

    public void setmLng(String mLng) {
        this.mLng = mLng;
    }

    public String getmCity() {
        return mCity;
    }

    public void setmCity(String mCity) {
        this.mCity = mCity;
    }
}
