package com.live.shoplib.bean;

import com.hn.library.http.BaseResponseModel;

import java.io.Serializable;
import java.util.List;

/**
 * 作者：Mr.Xu
 * 时间：2017/12/28
 */
public class GoodsCommitModel extends BaseResponseModel implements Serializable{


    /**
     * d : {"cartList":"5,6,7,8","order_details":[{"list":[{"goods_id":"100090","goods_img":"FoPFq_kieLe-iGF7ZJDcCR0s0-oe","goods_name":"努比亚手机","num":"1","price":"4.00","sku_id":"5","spec_sku":"内存:2G 颜色:白色"}],"shop_fee":"2","store_id":"3","store_name":"第一个商铺","total_price":12},{"list":[{"goods_id":"100091","goods_img":"FvZ_eWplp4BkoKB3ZC7IrAcajMKT","goods_name":"华为手机","num":"1","price":"2.00","sku_id":"3","spec_sku":"内存:2G 颜色:白色"}],"shop_fee":"3","store_id":"4","store_name":"第二个商铺","total_price":6}],"order_price":18}
     */

    private DEntity d;

    public void setD(DEntity d) {
        this.d = d;
    }

    public DEntity getD() {
        return d;
    }

    public static class DEntity implements Serializable{
        /**
         * cartList : 5,6,7,8
         * order_details : [{"list":[{"goods_id":"100090","goods_img":"FoPFq_kieLe-iGF7ZJDcCR0s0-oe","goods_name":"努比亚手机","num":"1","price":"4.00","sku_id":"5","spec_sku":"内存:2G 颜色:白色"}],"shop_fee":"2","store_id":"3","store_name":"第一个商铺","total_price":12},{"list":[{"goods_id":"100091","goods_img":"FvZ_eWplp4BkoKB3ZC7IrAcajMKT","goods_name":"华为手机","num":"1","price":"2.00","sku_id":"3","spec_sku":"内存:2G 颜色:白色"}],"shop_fee":"3","store_id":"4","store_name":"第二个商铺","total_price":6}]
         * order_price : 18
         */
        private String cartList;
        private float order_price;
        private List<OrderDetailsEntity> order_details;

        public void setCartList(String cartList) {
            this.cartList = cartList;
        }

        public void setOrder_price(float order_price) {
            this.order_price = order_price;
        }

        public void setOrder_details(List<OrderDetailsEntity> order_details) {
            this.order_details = order_details;
        }

        public String getCartList() {
            return cartList;
        }

        public float getOrder_price() {
            return order_price;
        }

        public List<OrderDetailsEntity> getOrder_details() {
            return order_details;
        }

        public static class OrderDetailsEntity implements Serializable{
            /**
             * list : [{"goods_id":"100090","goods_img":"FoPFq_kieLe-iGF7ZJDcCR0s0-oe","goods_name":"努比亚手机","num":"1","price":"4.00","sku_id":"5","spec_sku":"内存:2G 颜色:白色"}]
             * shop_fee : 2
             * store_id : 3
             * store_name : 第一个商铺
             * total_price : 12
             */

            private String shop_fee;
            private String store_id;
            private String store_icon;
            private String store_name;
            private float total_price;
            private List<ListEntity> list;
            private String content = "";

            public void setTotal_price(float total_price) {
                this.total_price = total_price;
            }

            public String getStore_icon() {
                return store_icon;
            }

            public void setStore_icon(String store_icon) {
                this.store_icon = store_icon;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public void setShop_fee(String shop_fee) {
                this.shop_fee = shop_fee;
            }

            public void setStore_id(String store_id) {
                this.store_id = store_id;
            }

            public void setStore_name(String store_name) {
                this.store_name = store_name;
            }

            public void setTotal_price(int total_price) {
                this.total_price = total_price;
            }

            public void setList(List<ListEntity> list) {
                this.list = list;
            }

            public String getShop_fee() {
                return shop_fee;
            }

            public String getStore_id() {
                return store_id;
            }

            public String getStore_name() {
                return store_name;
            }

            public float getTotal_price() {
                return total_price;
            }

            public List<ListEntity> getList() {
                return list;
            }

            public static class ListEntity implements Serializable{
                /**
                 * goods_id : 100090
                 * goods_img : FoPFq_kieLe-iGF7ZJDcCR0s0-oe
                 * goods_name : 努比亚手机
                 * num : 1
                 * price : 4.00
                 * sku_id : 5
                 * spec_sku : 内存:2G 颜色:白色
                 */

                private String goods_id;
                private String goods_img;
                private String goods_name;
                private String num;
                private String price;
                private String sku_id;
                private String spec_sku;


                public void setGoods_id(String goods_id) {
                    this.goods_id = goods_id;
                }

                public void setGoods_img(String goods_img) {
                    this.goods_img = goods_img;
                }

                public void setGoods_name(String goods_name) {
                    this.goods_name = goods_name;
                }

                public void setNum(String num) {
                    this.num = num;
                }

                public void setPrice(String price) {
                    this.price = price;
                }

                public void setSku_id(String sku_id) {
                    this.sku_id = sku_id;
                }

                public void setSpec_sku(String spec_sku) {
                    this.spec_sku = spec_sku;
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

                public String getNum() {
                    return num;
                }

                public String getPrice() {
                    return price;
                }

                public String getSku_id() {
                    return sku_id;
                }

                public String getSpec_sku() {
                    return spec_sku;
                }
            }
        }
    }
}
