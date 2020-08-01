package com.hotniao.live.model;

import com.hn.library.http.BaseResponseModel;

/**
 * 作者：Mr.Xu
 * 时间：2017/12/26
 */
public class AuthStoreMsgModel extends BaseResponseModel{

    /**
     * d : {"user":{"realname":"红包不会","phone":"13420002126","certification_number":"441423199208090050","front_img":"http://boyazhibo-1255530974.cosgz.myqcloud.com/image/2018/01/15/1515996436592.png","back_img":"http://boyazhibo-1255530974.cosgz.myqcloud.com/image/2018/01/15/1515996444330.png"},"shop":{"certification_type":"flagship_shop","food_circulation_img":"http://boyazhibo-1255530974.cosgz.myqcloud.com/image/2018/01/15/1515996463222.png","business_licence_img":"http://boyazhibo-1255530974.cosgz.myqcloud.com/image/2018/01/15/1515996452440.png","store_type_id":"5","store_type_name":"食品","store_certification_status":"C","store_certification_result":"","predict_time":"2018-01-18","time":"1970-01-01"}}
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
         * user : {"realname":"红包不会","phone":"13420002126","certification_number":"441423199208090050","front_img":"http://boyazhibo-1255530974.cosgz.myqcloud.com/image/2018/01/15/1515996436592.png","back_img":"http://boyazhibo-1255530974.cosgz.myqcloud.com/image/2018/01/15/1515996444330.png"}
         * shop : {"certification_type":"flagship_shop","food_circulation_img":"http://boyazhibo-1255530974.cosgz.myqcloud.com/image/2018/01/15/1515996463222.png","business_licence_img":"http://boyazhibo-1255530974.cosgz.myqcloud.com/image/2018/01/15/1515996452440.png","store_type_id":"5","store_type_name":"食品","store_certification_status":"C","store_certification_result":"","predict_time":"2018-01-18","time":"1970-01-01"}
         */

        private UserEntity user;
        private ShopEntity shop;

        public void setUser(UserEntity user) {
            this.user = user;
        }

        public void setShop(ShopEntity shop) {
            this.shop = shop;
        }

        public UserEntity getUser() {
            return user;
        }

        public ShopEntity getShop() {
            return shop;
        }

        public static class UserEntity {
            /**
             * realname : 红包不会
             * phone : 13420002126
             * certification_number : 441423199208090050
             * front_img : http://boyazhibo-1255530974.cosgz.myqcloud.com/image/2018/01/15/1515996436592.png
             * back_img : http://boyazhibo-1255530974.cosgz.myqcloud.com/image/2018/01/15/1515996444330.png
             */

            private String realname;
            private String phone;
            private String certification_number;
            private String front_img;
            private String back_img;

            public void setRealname(String realname) {
                this.realname = realname;
            }

            public void setPhone(String phone) {
                this.phone = phone;
            }

            public void setCertification_number(String certification_number) {
                this.certification_number = certification_number;
            }

            public void setFront_img(String front_img) {
                this.front_img = front_img;
            }

            public void setBack_img(String back_img) {
                this.back_img = back_img;
            }

            public String getRealname() {
                return realname;
            }

            public String getPhone() {
                return phone;
            }

            public String getCertification_number() {
                return certification_number;
            }

            public String getFront_img() {
                return front_img;
            }

            public String getBack_img() {
                return back_img;
            }
        }

        public static class ShopEntity {
            /**
             * certification_type : flagship_shop
             * food_circulation_img : http://boyazhibo-1255530974.cosgz.myqcloud.com/image/2018/01/15/1515996463222.png
             * business_licence_img : http://boyazhibo-1255530974.cosgz.myqcloud.com/image/2018/01/15/1515996452440.png
             * store_type_id : 5
             * store_type_name : 食品
             * store_certification_status : C
             * store_certification_result :
             * predict_time : 2018-01-18
             * time : 1970-01-01
             */

            private String certification_type;
            private String food_circulation_img;
            private String business_licence_img;
            private String store_type_id;
            private String store_type_name;
            private String store_certification_status;
            private String store_certification_result;
            private String predict_time;
            private String time;
            private String shop_certification_type;//认证类型 'user_shop','flagship_shop'

            public String getShop_certification_type() {
                return shop_certification_type;
            }

            public void setShop_certification_type(String shop_certification_type) {
                this.shop_certification_type = shop_certification_type;
            }

            public void setCertification_type(String certification_type) {
                this.certification_type = certification_type;
            }

            public void setFood_circulation_img(String food_circulation_img) {
                this.food_circulation_img = food_circulation_img;
            }

            public void setBusiness_licence_img(String business_licence_img) {
                this.business_licence_img = business_licence_img;
            }

            public void setStore_type_id(String store_type_id) {
                this.store_type_id = store_type_id;
            }

            public void setStore_type_name(String store_type_name) {
                this.store_type_name = store_type_name;
            }

            public void setStore_certification_status(String store_certification_status) {
                this.store_certification_status = store_certification_status;
            }

            public void setStore_certification_result(String store_certification_result) {
                this.store_certification_result = store_certification_result;
            }

            public void setPredict_time(String predict_time) {
                this.predict_time = predict_time;
            }

            public void setTime(String time) {
                this.time = time;
            }

            public String getCertification_type() {
                return certification_type;
            }

            public String getFood_circulation_img() {
                return food_circulation_img;
            }

            public String getBusiness_licence_img() {
                return business_licence_img;
            }

            public String getStore_type_id() {
                return store_type_id;
            }

            public String getStore_type_name() {
                return store_type_name;
            }

            public String getStore_certification_status() {
                return store_certification_status;
            }

            public String getStore_certification_result() {
                return store_certification_result;
            }

            public String getPredict_time() {
                return predict_time;
            }

            public String getTime() {
                return time;
            }
        }
    }
}