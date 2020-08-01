package com.live.shoplib.bean;

import com.hn.library.http.BaseResponseModel;

/**
 * 作者：Mr.Xu
 * 时间：2017/12/29
 */
public class ApplyBackModel extends BaseResponseModel{


    /**
     * d : {"goods_list":{"goods_id":"100091","goods_img":"FvZ_eWplp4BkoKB3ZC7IrAcajMKT","goods_name":"华为手机","goods_number":"1","goods_price":"2.00","id":"242","order_id":"173","refund_number":"0"},"price":2}
     */

    private DBean d;

    public DBean getD() {
        return d;
    }

    public void setD(DBean d) {
        this.d = d;
    }

    public static class DBean {
        /**
         * goods_list : {"goods_id":"100091","goods_img":"FvZ_eWplp4BkoKB3ZC7IrAcajMKT","goods_name":"华为手机","goods_number":"1","goods_price":"2.00","id":"242","order_id":"173","refund_number":"0"}
         * price : 2
         */

        private GoodsListBean goods_list;
        private Double price;

        public GoodsListBean getGoods_list() {
            return goods_list;
        }

        public void setGoods_list(GoodsListBean goods_list) {
            this.goods_list = goods_list;
        }

        public Double getPrice() {
            return price;
        }

        public void setPrice(Double price) {
            this.price = price;
        }

        public static class GoodsListBean {
            /**
             * goods_id : 100091
             * goods_img : FvZ_eWplp4BkoKB3ZC7IrAcajMKT
             * goods_name : 华为手机
             * goods_number : 1
             * goods_price : 2.00
             * id : 242
             * order_id : 173
             * refund_number : 0
             */

            private String goods_id;
            private String goods_img;
            private String goods_name;
            private String goods_number;
            private String goods_price;
            private String id;
            private String order_id;
            private String refund_number;

            public String getGoods_id() {
                return goods_id;
            }

            public void setGoods_id(String goods_id) {
                this.goods_id = goods_id;
            }

            public String getGoods_img() {
                return goods_img;
            }

            public void setGoods_img(String goods_img) {
                this.goods_img = goods_img;
            }

            public String getGoods_name() {
                return goods_name;
            }

            public void setGoods_name(String goods_name) {
                this.goods_name = goods_name;
            }

            public String getGoods_number() {
                return goods_number;
            }

            public void setGoods_number(String goods_number) {
                this.goods_number = goods_number;
            }

            public String getGoods_price() {
                return goods_price;
            }

            public void setGoods_price(String goods_price) {
                this.goods_price = goods_price;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getOrder_id() {
                return order_id;
            }

            public void setOrder_id(String order_id) {
                this.order_id = order_id;
            }

            public String getRefund_number() {
                return refund_number;
            }

            public void setRefund_number(String refund_number) {
                this.refund_number = refund_number;
            }
        }
    }
}
