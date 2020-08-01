package com.live.shoplib.bean;

import com.hn.library.http.BaseResponseModel;

/**
 * 作者：Mr.Xu
 * 时间：2018/1/3
 */
public class SellerCenterModel extends BaseResponseModel{


    /**
     * d : {"order":{"drop_shipping":0,"non_payment":2,"refund":4,"wait_receiving":0},"store":{"account":"13424509686","collect":1,"icon":"FvZ_eWplp4BkoKB3ZC7IrAcajMKT","name":"第一个商铺","pay":"支付宝","sale":3,"store_id":"3","total_cash":"0.00","total_deal":"0.00","total_money":"0","userId":"264"}}
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
         * order : {"drop_shipping":0,"non_payment":2,"refund":4,"wait_receiving":0}
         * store : {"account":"13424509686","collect":1,"icon":"FvZ_eWplp4BkoKB3ZC7IrAcajMKT","name":"第一个商铺","pay":"支付宝","sale":3,"store_id":"3","total_cash":"0.00","total_deal":"0.00","total_money":"0","userId":"264"}
         */


        private OrderEntity order;
        private StoreEntity store;

        public void setOrder(OrderEntity order) {
            this.order = order;
        }

        public void setStore(StoreEntity store) {
            this.store = store;
        }

        public OrderEntity getOrder() {
            return order;
        }

        public StoreEntity getStore() {
            return store;
        }

        public static class OrderEntity {
            /**
             * drop_shipping : 0
             * non_payment : 2
             * refund : 4
             * wait_receiving : 0
             */

            private int drop_shipping;
            private int non_payment;
            private int refund;
            private int wait_receiving;

            public void setDrop_shipping(int drop_shipping) {
                this.drop_shipping = drop_shipping;
            }

            public void setNon_payment(int non_payment) {
                this.non_payment = non_payment;
            }

            public void setRefund(int refund) {
                this.refund = refund;
            }

            public void setWait_receiving(int wait_receiving) {
                this.wait_receiving = wait_receiving;
            }

            public int getDrop_shipping() {
                return drop_shipping;
            }

            public int getNon_payment() {
                return non_payment;
            }

            public int getRefund() {
                return refund;
            }

            public int getWait_receiving() {
                return wait_receiving;
            }
        }

        public static class StoreEntity {
            /**
             * account : 13424509686
             * collect : 1
             * icon : FvZ_eWplp4BkoKB3ZC7IrAcajMKT
             * name : 第一个商铺
             * pay : 支付宝
             * sale : 3
             * store_id : 3
             * total_cash : 0.00
             * total_deal : 0.00
             * total_money : 0
             * userId : 264
             */

            private String account;
            private String anchor_level;
            private int collect;
            private String icon;
            private String name;
            private String pay;
            private int sale;
            private String store_id;
            private String total_cash;
            private String total_deal;
            private String total_money;
            private String store_certification_status;
            private String userId;
            private int withdraw;

            public String getAnchor_level() {
                return anchor_level;
            }

            public void setAnchor_level(String anchor_level) {
                this.anchor_level = anchor_level;
            }

            public int getWithdraw() {
                return withdraw;
            }

            public void setWithdraw(int withdraw) {
                this.withdraw = withdraw;
            }

            public String getStore_certification_status() {
                return store_certification_status;
            }

            public void setStore_certification_status(String store_certification_status) {
                this.store_certification_status = store_certification_status;
            }

            public void setAccount(String account) {
                this.account = account;
            }

            public void setCollect(int collect) {
                this.collect = collect;
            }

            public void setIcon(String icon) {
                this.icon = icon;
            }

            public void setName(String name) {
                this.name = name;
            }

            public void setPay(String pay) {
                this.pay = pay;
            }

            public void setSale(int sale) {
                this.sale = sale;
            }

            public void setStore_id(String store_id) {
                this.store_id = store_id;
            }

            public void setTotal_cash(String total_cash) {
                this.total_cash = total_cash;
            }

            public void setTotal_deal(String total_deal) {
                this.total_deal = total_deal;
            }

            public void setTotal_money(String total_money) {
                this.total_money = total_money;
            }

            public void setUserId(String userId) {
                this.userId = userId;
            }

            public String getAccount() {
                return account;
            }

            public int getCollect() {
                return collect;
            }

            public String getIcon() {
                return icon;
            }

            public String getName() {
                return name;
            }

            public String getPay() {
                return pay;
            }

            public int getSale() {
                return sale;
            }

            public String getStore_id() {
                return store_id;
            }

            public String getTotal_cash() {
                return total_cash;
            }

            public String getTotal_deal() {
                return total_deal;
            }

            public String getTotal_money() {
                return total_money;
            }

            public String getUserId() {
                return userId;
            }
        }
    }
}
