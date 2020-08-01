package com.hotniao.live.model;

import com.hn.library.http.BaseResponseModel;

/**
 * 作者：Mr.Xu
 * 时间：2018/1/12
 */
public class PlayBackModel extends BaseResponseModel{


    /**
     * d : {"anchor":{"anchor_level":"测试内容v8tm","anchor_ranking":"测试内容lkd2","chat_room_id":"264_","is_follow":"N","user_avatar":"","user_dot":"0.00","user_id":1,"user_level":"0","user_nickname":"火鸟12071009576"},"push_goods":{"goods_id":"100091","goods_img":"http://dev.static.fireniao.com/image/20171222/1513933350937476.png","goods_name":"华为手机","goods_price":"2.00"},"store":{"goods_sales":"0","icon":"FvZ_eWplp4BkoKB3ZC7IrAcajMKT","id":"3","name":"没错我就是小刘","total_collect":"0"},"user":{"user_coin":"326.00","user_id":"264","user_nickname":"火鸟12071009576"},"ws_url":"ws://120.76.165.88:1234/ws?rid=264&uid=264&token=&extra=eyJ1c2VyIjp7InVzZXJfaWQiOiIyNjQiLCJ1c2VyX2F2YXRhciI6IiIsInVzZXJfbmlja25hbWUiOiJcdTcwNmJcdTllMWYxMjA3MTAwOTU3NiIsInVzZXJfbGV2ZWwiOiIwIiwidXNlcl9pc19tZW1iZXIiOiJOIiwiaXNfbGV2ZWxfZWZmZWN0IjoiTiJ9fQ%3D%3D"}
     */

    private DEntity d;

    public void setD(DEntity d) {
        this.d = d;
    }

    public DEntity getD() {
        return d;
    }

    public static class DEntity {
        /**
         * anchor : {"anchor_level":"测试内容v8tm","anchor_ranking":"测试内容lkd2","chat_room_id":"264_","is_follow":"N","user_avatar":"","user_dot":"0.00","user_id":1,"user_level":"0","user_nickname":"火鸟12071009576"}
         * push_goods : {"goods_id":"100091","goods_img":"http://dev.static.fireniao.com/image/20171222/1513933350937476.png","goods_name":"华为手机","goods_price":"2.00"}
         * store : {"goods_sales":"0","icon":"FvZ_eWplp4BkoKB3ZC7IrAcajMKT","id":"3","name":"没错我就是小刘","total_collect":"0"}
         * user : {"user_coin":"326.00","user_id":"264","user_nickname":"火鸟12071009576"}
         * ws_url : ws://120.76.165.88:1234/ws?rid=264&uid=264&token=&extra=eyJ1c2VyIjp7InVzZXJfaWQiOiIyNjQiLCJ1c2VyX2F2YXRhciI6IiIsInVzZXJfbmlja25hbWUiOiJcdTcwNmJcdTllMWYxMjA3MTAwOTU3NiIsInVzZXJfbGV2ZWwiOiIwIiwidXNlcl9pc19tZW1iZXIiOiJOIiwiaXNfbGV2ZWxfZWZmZWN0IjoiTiJ9fQ%3D%3D
         */

        private AnchorEntity anchor;
        private PushGoodsEntity push_goods;
        private StoreEntity store;
        private UserEntity user;
        private String ws_url;

        public void setAnchor(AnchorEntity anchor) {
            this.anchor = anchor;
        }

        public void setPush_goods(PushGoodsEntity push_goods) {
            this.push_goods = push_goods;
        }

        public void setStore(StoreEntity store) {
            this.store = store;
        }

        public void setUser(UserEntity user) {
            this.user = user;
        }

        public void setWs_url(String ws_url) {
            this.ws_url = ws_url;
        }

        public AnchorEntity getAnchor() {
            return anchor;
        }

        public PushGoodsEntity getPush_goods() {
            return push_goods;
        }

        public StoreEntity getStore() {
            return store;
        }

        public UserEntity getUser() {
            return user;
        }

        public String getWs_url() {
            return ws_url;
        }

        public static class AnchorEntity {
            /**
             * anchor_level : 测试内容v8tm
             * anchor_ranking : 测试内容lkd2
             * chat_room_id : 264_
             * is_follow : N
             * user_avatar :
             * user_dot : 0.00
             * user_id : 1
             * user_level : 0
             * user_nickname : 火鸟12071009576
             */

            private String anchor_level;
            private String anchor_ranking;
            private String anchor_live_title;
            private String chat_room_id;
            private String is_follow;
            private String user_avatar;
            private String user_dot;
            private String user_id;
            private String user_level;
            private String user_nickname;

            public String getAnchor_live_title() {
                return anchor_live_title;
            }

            public void setAnchor_live_title(String anchor_live_title) {
                this.anchor_live_title = anchor_live_title;
            }

            public void setAnchor_level(String anchor_level) {
                this.anchor_level = anchor_level;
            }

            public void setAnchor_ranking(String anchor_ranking) {
                this.anchor_ranking = anchor_ranking;
            }

            public void setChat_room_id(String chat_room_id) {
                this.chat_room_id = chat_room_id;
            }

            public void setIs_follow(String is_follow) {
                this.is_follow = is_follow;
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

            public void setUser_level(String user_level) {
                this.user_level = user_level;
            }

            public void setUser_nickname(String user_nickname) {
                this.user_nickname = user_nickname;
            }

            public String getAnchor_level() {
                return anchor_level;
            }

            public String getAnchor_ranking() {
                return anchor_ranking;
            }

            public String getChat_room_id() {
                return chat_room_id;
            }

            public String getIs_follow() {
                return is_follow;
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

            public String getUser_level() {
                return user_level;
            }

            public String getUser_nickname() {
                return user_nickname;
            }
        }

        public static class PushGoodsEntity {
            /**
             * goods_id : 100091
             * goods_img : http://dev.static.fireniao.com/image/20171222/1513933350937476.png
             * goods_name : 华为手机
             * goods_price : 2.00
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

        public static class StoreEntity {
            /**
             * goods_sales : 0
             * icon : FvZ_eWplp4BkoKB3ZC7IrAcajMKT
             * id : 3
             * name : 没错我就是小刘
             * total_collect : 0
             */

            private String goods_sales;
            private String icon;
            private String id;
            private String name;
            private String total_collect;
            private String total_order;

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

        public static class UserEntity {
            /**
             * user_coin : 326.00
             * user_id : 264
             * user_nickname : 火鸟12071009576
             */

            private String user_coin;
            private String user_id;
            private String user_nickname;

            public void setUser_coin(String user_coin) {
                this.user_coin = user_coin;
            }

            public void setUser_id(String user_id) {
                this.user_id = user_id;
            }

            public void setUser_nickname(String user_nickname) {
                this.user_nickname = user_nickname;
            }

            public String getUser_coin() {
                return user_coin;
            }

            public String getUser_id() {
                return user_id;
            }

            public String getUser_nickname() {
                return user_nickname;
            }
        }
    }
}
