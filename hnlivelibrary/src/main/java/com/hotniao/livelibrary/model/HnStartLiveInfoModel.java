package com.hotniao.livelibrary.model;

import com.hn.library.http.BaseResponseModel;

import java.io.Serializable;
import java.util.List;

/**
 * Copyright (C) 2016,深圳市红鸟网络科技股份有限公司 All rights reserved.
 * 类描述：主页面
 * 创建人：刘勇
 * 创建时间：2017/2/22 16:22
 * 修改人：刘勇
 * 修改时间：2017/2/22 16:34
 * 修改备注：
 * Version:  1.0.0
 */

public class HnStartLiveInfoModel extends BaseResponseModel {


    /**
     * d : {"anchor_live_img":"123","anchor_live_onlines":0,"anchor_live_pay":0,"anchor_live_title":"123","anchor_ranking":"0","notices":["string1","string2","string3","string4","string5"],"play_url":"","play_url_flv":"","play_url_m3u8":"","push_url":"","user_avatar":"","user_dot":"0","user_id":"8","user_nickname":"2","ws_url":""}
     */

    private DBean d;

    public DBean getD() {
        return d;
    }

    public void setD(DBean d) {
        this.d = d;
    }

    public static class DBean implements Serializable {

        /**
         * anchor_live_img : 123
         * anchor_live_onlines : 0
         * anchor_live_pay : 0
         * anchor_live_title : 123
         * anchor_ranking : 0
         * barrage_fee : 1.0
         * notices : ["string1","string2","string3","string4","string5"]
         * play_url :
         * play_url_flv :
         * play_url_m3u8 :
         * push_goods : {"goods_id":"测试内容6nq5","goods_img":"测试内容xn91","goods_name":"测试内容671h","goods_price":"测试内容184d"}
         * push_url :
         * store : {"goods_sales":"测试内容l6z7","icon":"测试内容de6p","id":"测试内容3w0i","name":"测试内容a666","total_collect":"测试内容hk62"}
         * user_avatar :
         * user_dot : 0
         * user_id : 8
         * user_nickname : 2
         * ws_url :
         */

        private String anchor_live_img;
        private int anchor_live_onlines;
        private String anchor_live_pay;
        private String anchor_live_title;
        private String anchor_ranking;
        private String barrage_fee;
        private String play_url;
        private String play_url_flv;
        private String play_url_m3u8;
        private PushGoodsEntity push_goods;
        private String push_url;
        private StoreEntity store;
        private String user_avatar;
        private String user_dot;
        private String user_id;
        private String user_nickname;
        private String ws_url;
        private String share_url;
        private List<String> notices;


        public String getShare_url() {
            return share_url;
        }

        public void setShare_url(String share_url) {
            this.share_url = share_url;
        }

        public void setAnchor_live_img(String anchor_live_img) {
            this.anchor_live_img = anchor_live_img;
        }

        public void setAnchor_live_onlines(int anchor_live_onlines) {
            this.anchor_live_onlines = anchor_live_onlines;
        }

        public void setAnchor_live_pay(String anchor_live_pay) {
            this.anchor_live_pay = anchor_live_pay;
        }

        public void setAnchor_live_title(String anchor_live_title) {
            this.anchor_live_title = anchor_live_title;
        }

        public void setAnchor_ranking(String anchor_ranking) {
            this.anchor_ranking = anchor_ranking;
        }

        public void setBarrage_fee(String barrage_fee) {
            this.barrage_fee = barrage_fee;
        }

        public void setPlay_url(String play_url) {
            this.play_url = play_url;
        }

        public void setPlay_url_flv(String play_url_flv) {
            this.play_url_flv = play_url_flv;
        }

        public void setPlay_url_m3u8(String play_url_m3u8) {
            this.play_url_m3u8 = play_url_m3u8;
        }

        public void setPush_goods(PushGoodsEntity push_goods) {
            this.push_goods = push_goods;
        }

        public void setPush_url(String push_url) {
            this.push_url = push_url;
        }

        public void setStore(StoreEntity store) {
            this.store = store;
        }

        public void setUser_avatar(String user_avatar) {
            this.user_avatar = user_avatar;
        }

        public void setUser_dot(String user_dot) {
            this.user_dot = user_dot;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public void setUser_nickname(String user_nickname) {
            this.user_nickname = user_nickname;
        }

        public void setWs_url(String ws_url) {
            this.ws_url = ws_url;
        }

        public void setNotices(List<String> notices) {
            this.notices = notices;
        }

        public String getAnchor_live_img() {
            return anchor_live_img;
        }

        public int getAnchor_live_onlines() {
            return anchor_live_onlines;
        }

        public String getAnchor_live_pay() {
            return anchor_live_pay;
        }

        public String getAnchor_live_title() {
            return anchor_live_title;
        }

        public String getAnchor_ranking() {
            return anchor_ranking;
        }

        public String getBarrage_fee() {
            return barrage_fee;
        }

        public String getPlay_url() {
            return play_url;
        }

        public String getPlay_url_flv() {
            return play_url_flv;
        }

        public String getPlay_url_m3u8() {
            return play_url_m3u8;
        }

        public PushGoodsEntity getPush_goods() {
            return push_goods;
        }

        public String getPush_url() {
            return push_url;
        }

        public StoreEntity getStore() {
            return store;
        }

        public String getUser_avatar() {
            return user_avatar;
        }

        public String getUser_dot() {
            return user_dot;
        }

        public String getUser_id() {
            return user_id;
        }

        public String getUser_nickname() {
            return user_nickname;
        }

        public String getWs_url() {
            return ws_url;
        }

        public List<String> getNotices() {
            return notices;
        }

        public static class PushGoodsEntity implements Serializable {
            /**
             * goods_id : 测试内容6nq5
             * goods_img : 测试内容xn91
             * goods_name : 测试内容671h
             * goods_price : 测试内容184d
             */

            private String goods_id;
            private String goods_img;
            private String goods_name;
            private String goods_price;

            public void setGoods_id(String goods_id) {
                this.goods_id = goods_id;
            }

            public void setGoods_img(String goods_img) {
                this.goods_img = goods_img;
            }

            public void setGoods_name(String goods_name) {
                this.goods_name = goods_name;
            }

            public void setGoods_price(String goods_price) {
                this.goods_price = goods_price;
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

            public String getGoods_price() {
                return goods_price;
            }
        }

        public static class StoreEntity implements Serializable {
            /**
             * goods_sales : 测试内容l6z7
             * icon : 测试内容de6p
             * id : 测试内容3w0i
             * name : 测试内容a666
             * total_collect : 测试内容hk62
             */

            private String goods_sales;
            private String icon;
            private String id;
            private String name;
            private String status;
            private String total_collect;
            private String total_order;

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }

            public String getTotal_order() {
                return total_order;
            }

            public void setTotal_order(String total_order) {
                this.total_order = total_order;
            }

            public void setGoods_sales(String goods_sales) {
                this.goods_sales = goods_sales;
            }

            public void setIcon(String icon) {
                this.icon = icon;
            }

            public void setId(String id) {
                this.id = id;
            }

            public void setName(String name) {
                this.name = name;
            }

            public void setTotal_collect(String total_collect) {
                this.total_collect = total_collect;
            }

            public String getGoods_sales() {
                return goods_sales;
            }

            public String getIcon() {
                return icon;
            }

            public String getId() {
                return id;
            }

            public String getName() {
                return name;
            }

            public String getTotal_collect() {
                return total_collect;
            }
        }
    }
}
