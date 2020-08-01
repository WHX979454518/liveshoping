package com.live.shoplib.bean;

import com.hn.library.http.BaseResponseModel;

import java.util.List;

/**
 * 作者：Mr.Xu
 * 时间：2017/12/21
 */
public class OrderModel extends BaseResponseModel {


    /**
     * d : {"consignee":{"address":"广东省深圳市南山区不告诉你","name":"小刘","phone":"12345678912"},"details":{"details":[{"goods_id":"100090","goods_img":"FoPFq_kieLe-iGF7ZJDcCR0s0-oe","goods_name":"努比亚手机","goods_number":"1","goods_price":"1.00","id":"1","refund_number":"1","status":"0"},{"goods_id":"100091","goods_img":"FvZ_eWplp4BkoKB3ZC7IrAcajMKT","goods_name":"华为手机","goods_number":"1","goods_price":"2.00","id":"2","refund_number":"1","status":"0"},{"goods_id":"100090","goods_img":"FoPFq_kieLe-iGF7ZJDcCR0s0-oe","goods_name":"努比亚手机","goods_number":"2","goods_price":"4.00","id":"3","refund_number":"2","status":"0"}],"goods_amount":"7.00","order_amount":"9.00","shop_fee":"2.00","store_icon":"","store_id":"3","store_name":"第一个商铺"},"order":{"order_sn":"3U20171218113306","shipping_time":"","store_uid":"264","time":"2017-12-18 11:33:06"},"orderId":"69","order_status":"5"}
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
         * consignee : {"address":"广东省深圳市南山区不告诉你","name":"小刘","phone":"12345678912"}
         * details : {"details":[{"goods_id":"100090","goods_img":"FoPFq_kieLe-iGF7ZJDcCR0s0-oe","goods_name":"努比亚手机","goods_number":"1","goods_price":"1.00","id":"1","refund_number":"1","status":"0"},{"goods_id":"100091","goods_img":"FvZ_eWplp4BkoKB3ZC7IrAcajMKT","goods_name":"华为手机","goods_number":"1","goods_price":"2.00","id":"2","refund_number":"1","status":"0"},{"goods_id":"100090","goods_img":"FoPFq_kieLe-iGF7ZJDcCR0s0-oe","goods_name":"努比亚手机","goods_number":"2","goods_price":"4.00","id":"3","refund_number":"2","status":"0"}],"goods_amount":"7.00","order_amount":"9.00","shop_fee":"2.00","store_icon":"","store_id":"3","store_name":"第一个商铺"}
         * order : {"order_sn":"3U20171218113306","shipping_time":"","store_uid":"264","time":"2017-12-18 11:33:06"}
         * orderId : 69
         * order_status : 5
         */

        private ConsigneeEntity consignee;
        private DetailsEntity details;
        private OrderEntity order;
        private DialogueEntity dialogue;
        private String orderId;
        private String order_status;


        public DialogueEntity getDialogue() {
            return dialogue;
        }

        public void setDialogue(DialogueEntity dialogue) {
            this.dialogue = dialogue;
        }

        public void setConsignee(ConsigneeEntity consignee) {
            this.consignee = consignee;
        }

        public void setDetails(DetailsEntity details) {
            this.details = details;
        }

        public void setOrder(OrderEntity order) {
            this.order = order;
        }

        public void setOrderId(String orderId) {
            this.orderId = orderId;
        }

        public void setOrder_status(String order_status) {
            this.order_status = order_status;
        }

        public ConsigneeEntity getConsignee() {
            return consignee;
        }

        public DetailsEntity getDetails() {
            return details;
        }

        public OrderEntity getOrder() {
            return order;
        }

        public String getOrderId() {
            return orderId;
        }

        public String getOrder_status() {
            return order_status;
        }

        public static class ConsigneeEntity {
            /**
             * address : 广东省深圳市南山区不告诉你
             * name : 小刘
             * phone : 12345678912
             */

            private String address;
            private String name;
            private String phone;

            public void setAddress(String address) {
                this.address = address;
            }

            public void setName(String name) {
                this.name = name;
            }

            public void setPhone(String phone) {
                this.phone = phone;
            }

            public String getAddress() {
                return address;
            }

            public String getName() {
                return name;
            }

            public String getPhone() {
                return phone;
            }
        }

        public static class DetailsEntity {
            /**
             * details : [{"goods_id":"100090","goods_img":"FoPFq_kieLe-iGF7ZJDcCR0s0-oe","goods_name":"努比亚手机","goods_number":"1","goods_price":"1.00","id":"1","refund_number":"1","status":"0"},{"goods_id":"100091","goods_img":"FvZ_eWplp4BkoKB3ZC7IrAcajMKT","goods_name":"华为手机","goods_number":"1","goods_price":"2.00","id":"2","refund_number":"1","status":"0"},{"goods_id":"100090","goods_img":"FoPFq_kieLe-iGF7ZJDcCR0s0-oe","goods_name":"努比亚手机","goods_number":"2","goods_price":"4.00","id":"3","refund_number":"2","status":"0"}]
             * goods_amount : 7.00
             * order_amount : 9.00
             * shop_fee : 2.00
             * store_icon :
             * store_id : 3
             * store_name : 第一个商铺
             */

            private String goods_amount;
            private String order_amount;
            private String shop_fee;
            private String store_icon;
            private String store_status;

            private String store_id;
            private String store_name;
            private List<DetailsEntitys> details;

            public String getStore_status() {
                return store_status;
            }

            public void setStore_status(String store_status) {
                this.store_status = store_status;
            }

            public void setGoods_amount(String goods_amount) {
                this.goods_amount = goods_amount;
            }

            public void setOrder_amount(String order_amount) {
                this.order_amount = order_amount;
            }

            public void setShop_fee(String shop_fee) {
                this.shop_fee = shop_fee;
            }

            public void setStore_icon(String store_icon) {
                this.store_icon = store_icon;
            }

            public void setStore_id(String store_id) {
                this.store_id = store_id;
            }

            public void setStore_name(String store_name) {
                this.store_name = store_name;
            }

            public void setDetails(List<DetailsEntitys> details) {
                this.details = details;
            }

            public String getGoods_amount() {
                return goods_amount;
            }

            public String getOrder_amount() {
                return order_amount;
            }

            public String getShop_fee() {
                return shop_fee;
            }

            public String getStore_icon() {
                return store_icon;
            }

            public String getStore_id() {
                return store_id;
            }

            public String getStore_name() {
                return store_name;
            }

            public List<DetailsEntitys> getDetails() {
                return details;
            }

            public static class DetailsEntitys {
                /**
                 * goods_id : 100090
                 * goods_img : FoPFq_kieLe-iGF7ZJDcCR0s0-oe
                 * goods_name : 努比亚手机
                 * goods_number : 1
                 * goods_price : 1.00
                 * id : 1
                 * refund_number : 1
                 * status : 0
                 */

                private String goods_attr;
                private String goods_id;
                private String goods_img;
                private String goods_name;
                private String goods_number;
                private String goods_price;
                private String details_id;
                private String refund_number;
                private String refund_id;
                private String status;

                public String getRefund_id() {
                    return refund_id;
                }

                public void setRefund_id(String refund_id) {
                    this.refund_id = refund_id;
                }

                public String getGoods_attr() {
                    return goods_attr;
                }

                public void setGoods_attr(String goods_attr) {
                    this.goods_attr = goods_attr;
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

                public void setGoods_number(String goods_number) {
                    this.goods_number = goods_number;
                }

                public void setGoods_price(String goods_price) {
                    this.goods_price = goods_price;
                }


                public void setRefund_number(String refund_number) {
                    this.refund_number = refund_number;
                }

                public void setStatus(String status) {
                    this.status = status;
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

                public String getGoods_number() {
                    return goods_number;
                }

                public String getGoods_price() {
                    return goods_price;
                }

                public String getDetails_id() {
                    return details_id;
                }

                public void setDetails_id(String details_id) {
                    this.details_id = details_id;
                }

                public String getRefund_number() {
                    return refund_number;
                }

                public String getStatus() {
                    return status;
                }
            }
        }

        public static class DialogueEntity {
            private String charId;
            private String id;
            private String name;
            private String store_uid;
            private String user_name;

            public String getCharId() {
                return charId;
            }

            public void setCharId(String charId) {
                this.charId = charId;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getStore_uid() {
                return store_uid;
            }

            public void setStore_uid(String store_uid) {
                this.store_uid = store_uid;
            }

            public String getUser_name() {
                return user_name;
            }

            public void setUser_name(String user_name) {
                this.user_name = user_name;
            }
        }

        public static class OrderEntity {
            /**
             * order_sn : 3U20171218113306
             * shipping_time :
             * store_uid : 264
             * time : 2017-12-18 11:33:06
             */

            private String order_sn;
            private String buy_uid;
            private String shipping_time;
            private String store_uid;
            private String time;

            public String getBuy_uid() {
                return buy_uid;
            }

            public void setBuy_uid(String buy_uid) {
                this.buy_uid = buy_uid;
            }

            public void setOrder_sn(String order_sn) {
                this.order_sn = order_sn;
            }

            public void setShipping_time(String shipping_time) {
                this.shipping_time = shipping_time;
            }

            public void setStore_uid(String store_uid) {
                this.store_uid = store_uid;
            }

            public void setTime(String time) {
                this.time = time;
            }

            public String getOrder_sn() {
                return order_sn;
            }

            public String getShipping_time() {
                return shipping_time;
            }

            public String getStore_uid() {
                return store_uid;
            }

            public String getTime() {
                return time;
            }
        }
    }
}
