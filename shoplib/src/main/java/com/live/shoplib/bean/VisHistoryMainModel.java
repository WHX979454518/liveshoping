package com.live.shoplib.bean;

import com.hn.library.http.BaseResponseModel;

/**
 * Created by Administrator on 2018/4/23.
 */

public class VisHistoryMainModel extends BaseResponseModel {

    /**
     * d : {"new_customer":"0","potential_customer":"0","return_customer":"0","today_new_customer":"测试内容36g8","today_potential_customer":"测试内容8123","today_return_customer":1,"total_customer":"0","total_order_price":"0.00","total_return_rate":"0.00%"}
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
         * new_customer : 0
         * potential_customer : 0
         * return_customer : 0
         * today_new_customer : 测试内容36g8
         * today_potential_customer : 测试内容8123
         * today_return_customer : 1
         * total_customer : 0
         * total_order_price : 0.00
         * total_return_rate : 0.00%
         */

        private String new_customer;
        private String potential_customer;
        private String return_customer;
        private String today_new_customer;
        private String today_potential_customer;
        private int today_return_customer;
        private String total_customer;
        private String total_order_price;
        private String total_return_rate;

        public String getNew_customer() {
            return new_customer;
        }

        public void setNew_customer(String new_customer) {
            this.new_customer = new_customer;
        }

        public String getPotential_customer() {
            return potential_customer;
        }

        public void setPotential_customer(String potential_customer) {
            this.potential_customer = potential_customer;
        }

        public String getReturn_customer() {
            return return_customer;
        }

        public void setReturn_customer(String return_customer) {
            this.return_customer = return_customer;
        }

        public String getToday_new_customer() {
            return today_new_customer;
        }

        public void setToday_new_customer(String today_new_customer) {
            this.today_new_customer = today_new_customer;
        }

        public String getToday_potential_customer() {
            return today_potential_customer;
        }

        public void setToday_potential_customer(String today_potential_customer) {
            this.today_potential_customer = today_potential_customer;
        }

        public int getToday_return_customer() {
            return today_return_customer;
        }

        public void setToday_return_customer(int today_return_customer) {
            this.today_return_customer = today_return_customer;
        }

        public String getTotal_customer() {
            return total_customer;
        }

        public void setTotal_customer(String total_customer) {
            this.total_customer = total_customer;
        }

        public String getTotal_order_price() {
            return total_order_price;
        }

        public void setTotal_order_price(String total_order_price) {
            this.total_order_price = total_order_price;
        }

        public String getTotal_return_rate() {
            return total_return_rate;
        }

        public void setTotal_return_rate(String total_return_rate) {
            this.total_return_rate = total_return_rate;
        }
    }
}
