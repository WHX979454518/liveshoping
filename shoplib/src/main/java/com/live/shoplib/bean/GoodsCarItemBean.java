package com.live.shoplib.bean;

/**
 * 作者：Mr.Xu
 * 时间：2017/12/26
 */
public class GoodsCarItemBean {


    /**
     * cart_id : 5
     * goods_id : 100091
     * goods_img : FvZ_eWplp4BkoKB3ZC7IrAcajMKT
     * goods_name : 华为手机
     * goods_spec_details : 颜色:黑色;内核:4G
     * num : 测试内容t4fq
     * price : 3.00
     */

    private String cart_id;
    private String goods_id;
    private String goods_img;
    private String goods_name;
    private String goods_spec_details;
    private int num;
    private float price;
    private String sku_id;
    private boolean check = false;

    public boolean isCheck() {
        return check;
    }

    public void setCheck(boolean check) {
        this.check = check;
    }

    public String getSku_id() {
        return sku_id;
    }

    public void setSku_id(String sku_id) {
        this.sku_id = sku_id;
    }

    public void setCart_id(String cart_id) {
        this.cart_id = cart_id;
    }

    public void setGoods_id(String goods_id) {
        this.goods_id = goods_id;
    }

    public void setGoods_img(String goods_img) {
        this.goods_img = goods_img;
    }

    public void setGoods_name(String goods_name) {
        this.goods_name = goods_name;
    }

    public void setGoods_spec_details(String goods_spec_details) {
        this.goods_spec_details = goods_spec_details;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getCart_id() {
        return cart_id;
    }

    public String getGoods_id() {
        return goods_id;
    }

    public String getGoods_img() {
        return goods_img;
    }

    public String getGoods_name() {
        return goods_name;
    }

    public String getGoods_spec_details() {
        return goods_spec_details;
    }

    public int getNum() {
        return num;
    }

    public float getPrice() {
        return price;
    }
}
