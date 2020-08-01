package com.hotniao.live.model;

import com.hn.library.http.BaseResponseModel;

/**
 * 作者：Mr.Xu
 * 时间：2017/12/19
 */
public class OrderInforModel extends BaseResponseModel{


    /**
     * d : {"order":{"drop_shipping":0,"non_payment":10,"received":0,"refund":0,"wait_receiving":0},"profile":{"gift_buy":10,"sale":5,"storeId":3}}
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
         * order : {"drop_shipping":0,"non_payment":10,"received":0,"refund":0,"wait_receiving":0}
         * profile : {"gift_buy":10,"sale":5,"storeId":3}
         */

        private OrderEntity order;
        private ProfileEntity profile;

        public void setOrder(OrderEntity order) {
            this.order = order;
        }

        public void setProfile(ProfileEntity profile) {
            this.profile = profile;
        }

        public OrderEntity getOrder() {
            return order;
        }

        public ProfileEntity getProfile() {
            return profile;
        }

        public static class OrderEntity {
            /**
             * drop_shipping : 0
             * non_payment : 10
             * received : 0
             * refund : 0
             * wait_receiving : 0
             */

            private int drop_shipping;
            private int non_payment;
            private int received;
            private int refund;
            private int wait_receiving;

            public void setDrop_shipping(int drop_shipping) {
                this.drop_shipping = drop_shipping;
            }

            public void setNon_payment(int non_payment) {
                this.non_payment = non_payment;
            }

            public void setReceived(int received) {
                this.received = received;
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

            public int getReceived() {
                return received;
            }

            public int getRefund() {
                return refund;
            }

            public int getWait_receiving() {
                return wait_receiving;
            }
        }

        public static class ProfileEntity {
            /**
             * gift_buy : 10
             * sale : 5
             * storeId : 3
             */

            private int buy;
            private int sale;
            private String storeId;

            public void setBuy(int buy) {
                this.buy = buy;
            }

            public void setSale(int sale) {
                this.sale = sale;
            }

            public void setStoreId(String storeId) {
                this.storeId = storeId;
            }

            public int getBuy() {
                return buy;
            }

            public int getSale() {
                return sale;
            }

            public String getStoreId() {
                return storeId;
            }
        }
    }
}
