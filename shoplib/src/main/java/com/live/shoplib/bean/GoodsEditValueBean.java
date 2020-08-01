package com.live.shoplib.bean;

import java.util.ArrayList;

/**
 * Created by Administrator on 2018/4/18.
 */

public class GoodsEditValueBean {
    @Override
    public String toString() {
        return "GoodsEditValueBean{" +
                "name=" + name +
                ", attr_name=" + attr_name +
                ", ware='" + ware + '\'' +
                ", price='" + price + '\'' +
                '}';
    }

    ArrayList<String> name;
    String attr_name;
    String attr_edit;
    String edit_key;
    String ware = "";
    String price = "";




    public String getAttr_edit() {
        return attr_edit;
    }

    public void setAttr_edit(String attr_edit) {
        this.attr_edit = attr_edit;
    }

    public String getEdit_key() {
        return edit_key;
    }

    public void setEdit_key(String edit_key) {
        this.edit_key = edit_key;
    }

    public String getAttr_name() {
        return attr_name;
    }

    public void setAttr_name(String attr_name) {
        this.attr_name = attr_name;
    }

    public ArrayList<String> getName() {
        return name;
    }

    public void setName(ArrayList<String> name) {
        this.name = name;
    }

    public String getWare() {
        return ware;
    }

    public void setWare(String ware) {
        this.ware = ware;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
