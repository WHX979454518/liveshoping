package com.live.shoplib.bean;

import com.hn.library.http.BaseResponseModel;

/**
 * 作者：Mr.Xu
 * 时间：2017/12/21
 */
public class RefundDetailsModel extends BaseResponseModel {

    /**
     * d : {"details":{"details_id":"69","order_sn":"20180109142726","order_amount":"1.00","goods_id":"100120","status":"1","add_time":"2018-01-09 14:27:26","num":"1","type":"1","refuse_reason":"","code":"","refund_time":"2018-01-09 14:27:26","goods_attr":"颜色:白色;尺寸:小;舒适:舒服","goods_name":"衣服1","goods_price":"1.00","goods_img":"http://dev.static.fireniao.com/image/20171226/1514256203820352.png","store_uid":"267","remark":"hgg"}}
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
         * details : {"details_id":"69","order_sn":"20180109142726","order_amount":"1.00","goods_id":"100120","status":"1","add_time":"2018-01-09 14:27:26","num":"1","type":"1","refuse_reason":"","code":"","refund_time":"2018-01-09 14:27:26","goods_attr":"颜色:白色;尺寸:小;舒适:舒服","goods_name":"衣服1","goods_price":"1.00","goods_img":"http://dev.static.fireniao.com/image/20171226/1514256203820352.png","store_uid":"267","remark":"hgg"}
         */

        private DetailsEntity details;
        /**
         * dialogue : {"charId":"267_263","id":"7","name":"","store_uid":"267"}
         */

        private DialogueEntity dialogue;

        public void setDetails(DetailsEntity details) {
            this.details = details;
        }

        public DetailsEntity getDetails() {
            return details;
        }

        public void setDialogue(DialogueEntity dialogue) {
            this.dialogue = dialogue;
        }

        public DialogueEntity getDialogue() {
            return dialogue;
        }

        public static class DetailsEntity {
            /**
             * details_id : 69
             * order_sn : 20180109142726
             * order_amount : 1.00
             * goods_id : 100120
             * status : 1
             * add_time : 2018-01-09 14:27:26
             * num : 1
             * type : 1
             * refuse_reason :
             * code :
             * refund_time : 2018-01-09 14:27:26
             * goods_attr : 颜色:白色;尺寸:小;舒适:舒服
             * goods_name : 衣服1
             * goods_price : 1.00
             * goods_img : http://dev.static.fireniao.com/image/20171226/1514256203820352.png
             * store_uid : 267
             * remark : hgg
             */

            private String details_id;
            private String order_sn;
            private String order_id;
            private String order_amount;
            private String goods_id;
            private String status;
            private String add_time;
            private String num;
            private String type;
            private String refuse_reason;
            private String code;
            private String refund_time;
            private String goods_attr;
            private String goods_name;
            private String goods_price;
            private String goods_img;
            private String store_uid;
            private String remark;
            private String shipping_status;
            private String update_time;

            public String getUpdate_time() {
                return update_time;
            }

            public void setUpdate_time(String update_time) {
                this.update_time = update_time;
            }

            public String getShipping_status() {
                return shipping_status;
            }

            public void setShipping_status(String shipping_status) {
                this.shipping_status = shipping_status;
            }

            public String getOrder_id() {
                return order_id;
            }

            public void setOrder_id(String order_id) {
                this.order_id = order_id;
            }

            public void setDetails_id(String details_id) {
                this.details_id = details_id;
            }

            public void setOrder_sn(String order_sn) {
                this.order_sn = order_sn;
            }

            public void setOrder_amount(String order_amount) {
                this.order_amount = order_amount;
            }

            public void setGoods_id(String goods_id) {
                this.goods_id = goods_id;
            }

            public void setStatus(String status) {
                this.status = status;
            }

            public void setAdd_time(String add_time) {
                this.add_time = add_time;
            }

            public void setNum(String num) {
                this.num = num;
            }

            public void setType(String type) {
                this.type = type;
            }

            public void setRefuse_reason(String refuse_reason) {
                this.refuse_reason = refuse_reason;
            }

            public void setCode(String code) {
                this.code = code;
            }

            public void setRefund_time(String refund_time) {
                this.refund_time = refund_time;
            }

            public void setGoods_attr(String goods_attr) {
                this.goods_attr = goods_attr;
            }

            public void setGoods_name(String goods_name) {
                this.goods_name = goods_name;
            }

            public void setGoods_price(String goods_price) {
                this.goods_price = goods_price;
            }

            public void setGoods_img(String goods_img) {
                this.goods_img = goods_img;
            }

            public void setStore_uid(String store_uid) {
                this.store_uid = store_uid;
            }

            public void setRemark(String remark) {
                this.remark = remark;
            }

            public String getDetails_id() {
                return details_id;
            }

            public String getOrder_sn() {
                return order_sn;
            }

            public String getOrder_amount() {
                return order_amount;
            }

            public String getGoods_id() {
                return goods_id;
            }

            public String getStatus() {
                return status;
            }

            public String getAdd_time() {
                return add_time;
            }

            public String getNum() {
                return num;
            }

            public String getType() {
                return type;
            }

            public String getRefuse_reason() {
                return refuse_reason;
            }

            public String getCode() {
                return code;
            }

            public String getRefund_time() {
                return refund_time;
            }

            public String getGoods_attr() {
                return goods_attr;
            }

            public String getGoods_name() {
                return goods_name;
            }

            public String getGoods_price() {
                return goods_price;
            }

            public String getGoods_img() {
                return goods_img;
            }

            public String getStore_uid() {
                return store_uid;
            }

            public String getRemark() {
                return remark;
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
    }
}
