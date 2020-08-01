package com.live.shoplib.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Alan on 2019/6/20.
 */
public class GroupBuyTransationBean implements Serializable {
    private String group_buy_id;
    private String goods_id;
    private String group_order_id="";
    private String store_id;
    private String num;
    private String group_price;
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

    public GoodsCommitModel.DEntity.OrderDetailsEntity getOrder_details() {
        return order_details;
    }

    public void setOrder_details(GoodsCommitModel.DEntity.OrderDetailsEntity order_details) {
        this.order_details = order_details;
    }


    public String getGroup_order_id() {
        return group_order_id;
    }

    public void setGroup_order_id(String group_order_id) {
        this.group_order_id = group_order_id;
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

    public String getSpec_sku() {
        return spec_sku;
    }

    public void setSpec_sku(String spec_sku) {
        this.spec_sku = spec_sku;
    }

    public String getGroup_buy_id() {
        return group_buy_id;
    }

    public void setGroup_buy_id(String group_buy_id) {
        this.group_buy_id = group_buy_id;
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

    public String getGroup_price() {
        return group_price;
    }

    public void setGroup_price(String group_price) {
        this.group_price = group_price;
    }
}
