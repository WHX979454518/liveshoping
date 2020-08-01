package com.live.shoplib.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Alan on 2019/6/20.
 */
public class SpikeTransationBean implements Serializable {
    private String sec_goods_id;
    private String goods_id;
    private String store_id;
    private String num;
    private String sec_price;
    private String spec_sku;
    private String sku_id;
    private List<String> spec_ids;
    private GoodsCommitModel.DEntity.OrderDetailsEntity  order_details;


    public String getGoods_id() {
        return goods_id;
    }

    public void setGoods_id(String goods_id) {
        this.goods_id = goods_id;
    }

    public String getSpec_sku() {
        return spec_sku;
    }

    public void setSpec_sku(String spec_sku) {
        this.spec_sku = spec_sku;
    }

    public String getSku_id() {
        return sku_id;
    }

    public void setSku_id(String sku_id) {
        this.sku_id = sku_id;
    }

    public List<String> getSpec_ids() {
        return spec_ids;
    }

    public void setSpec_ids(List<String> spec_ids) {
        this.spec_ids = spec_ids;
    }

    public GoodsCommitModel.DEntity.OrderDetailsEntity getOrder_details() {
        return order_details;
    }

    public void setOrder_details(GoodsCommitModel.DEntity.OrderDetailsEntity order_details) {
        this.order_details = order_details;
    }

    public String getSec_goods_id() {
        return sec_goods_id;
    }

    public void setSec_goods_id(String sec_goods_id) {
        this.sec_goods_id = sec_goods_id;
    }

    public String getStore_id() {
        return store_id;
    }

    public void setStore_id(String store_id) {
        this.store_id = store_id;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getSec_price() {
        return sec_price;
    }

    public void setSec_price(String sec_price) {
        this.sec_price = sec_price;
    }
}
