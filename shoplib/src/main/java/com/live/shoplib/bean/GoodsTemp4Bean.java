package com.live.shoplib.bean;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Map;

/**
 * Created by Administrator on 2018/4/20.
 */

public class GoodsTemp4Bean {


    /**
     * market_price : 0.00
     * price : 2.00
     * sku_id : 29
     * spec_ids : 27:28:30
     * spec_text : 含量:100mL;大小:微型;长度:1
     * stock : 1
     */

    private String market_price;
    private String price;
    private String sku_id;
    private String spec_ids;
    private String spec_text;
    private String stock;

    public String getMarket_price() {
        return market_price;
    }

    public void setMarket_price(String market_price) {

        Gson gson=new Gson();
        Type type=new TypeToken<Map<String,String>>(){}.getType();
        Map<String,String> map=gson.fromJson("",type);
        for (Map.Entry<String,String> entry : map.entrySet()){
            Log.v("", entry.getKey()+"|"+entry.getValue());
        }

        this.market_price = market_price;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getSku_id() {
        return sku_id;
    }

    public void setSku_id(String sku_id) {
        this.sku_id = sku_id;
    }

    public String getSpec_ids() {
        return spec_ids;
    }

    public void setSpec_ids(String spec_ids) {
        this.spec_ids = spec_ids;
    }

    public String getSpec_text() {
        return spec_text;
    }

    public void setSpec_text(String spec_text) {
        this.spec_text = spec_text;
    }

    public String getStock() {
        return stock;
    }

    public void setStock(String stock) {
        this.stock = stock;
    }
}
