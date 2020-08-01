package com.hotniao.live.model;

import com.hn.library.http.BaseResponseModel;

import java.util.List;

/**
 * Created by Alan on 2019/6/17.
 */
public class SpikeListModel extends BaseResponseModel {


    private SpikeList d;

    public SpikeList getD() {
        return d;
    }

    public void setD(SpikeList d) {
        this.d = d;
    }

    public static class SpikeList {

        private Long starttime;
        private Long endtime;

        private GoodsList goods;
//        private List<Goods> goods;

        public Long getStarttime() {
            return starttime;
        }

        public void setStarttime(Long starttime) {
            this.starttime = starttime;
        }

        public Long getEndtime() {
            return endtime;
        }

        public void setEndtime(Long endtime) {
            this.endtime = endtime;
        }

                public GoodsList getGoods() {
            return goods;
        }

        public void setGoods(GoodsList goods) {
            this.goods = goods;
        }
    }

    public static class GoodsList{
        private List<Goods> items;

        public List<Goods> getItems() {
            return items;
        }

        public void setItems(List<Goods> items) {
            this.items = items;
        }
    }

    public static class Goods {

        private String goods_id;
        private String sec_goods_id;
        private String group_buy_id;
        private String goods_name;
        private String goods_img;
        private String goods_type;
        private int sec_goods_stock;
        private int group_goods_stock;
        private int max_number;
        private double sec_price;
        private double goods_price;
        private int goods_sales;


        public String getSec_goods_id() {
            return sec_goods_id;
        }

        public void setSec_goods_id(String sec_goods_id) {
            this.sec_goods_id = sec_goods_id;
        }

        public String getGroup_buy_id() {
            return group_buy_id;
        }

        public void setGroup_buy_id(String group_buy_id) {
            this.group_buy_id = group_buy_id;
        }

        public String getGoods_id() {
            return goods_id;
        }

        public void setGoods_id(String goods_id) {
            this.goods_id = goods_id;
        }

        public void setGoods_name(String goods_name) {
            this.goods_name = goods_name;
        }

        public String getGoods_name() {
            return goods_name;
        }

        public void setGoods_img(String goods_img) {
            this.goods_img = goods_img;
        }

        public String getGoods_img() {
            return goods_img;
        }

        public String getGoods_type() {
            return goods_type;
        }

        public int getGroup_goods_stock() {
            return group_goods_stock;
        }

        public void setGroup_goods_stock(int group_goods_stock) {
            this.group_goods_stock = group_goods_stock;
        }

        public void setGoods_type(String goods_type) {
            this.goods_type = goods_type;
        }

        public int getSec_goods_stock() {
            return sec_goods_stock;
        }

        public int getMax_number() {
            return max_number;
        }

        public void setMax_number(int max_number) {
            this.max_number = max_number;
        }

        public void setSec_goods_stock(int sec_goods_stock) {
            this.sec_goods_stock = sec_goods_stock;
        }

        public void setSec_price(double sec_price) {
            this.sec_price = sec_price;
        }

        public double getSec_price() {
            return sec_price;
        }

        public void setGoods_price(double goods_price) {
            this.goods_price = goods_price;
        }

        public double getGoods_price() {
            return goods_price;
        }

        public int getGoods_sales() {
            return goods_sales;
        }

        public void setGoods_sales(int goods_sales) {
            this.goods_sales = goods_sales;
        }
    }

}
