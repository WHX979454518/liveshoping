package com.hotniao.live.model;

import com.hn.library.http.BaseResponseModel;

import java.io.Serializable;

/**
 * Created by Alan on 2019/7/29.
 */
public class TheStoreModel extends BaseResponseModel implements Serializable{

    /**
     * d : {"user":{"realname":"好的好好的","phone":"13800000010","certification_number":"440223199108292720","front_img":"http://boyazhibo-1255530974.picgz.myqcloud.com/image/2018/06/06/505c68e16cc82dd4bc112720dbe56942.png","back_img":"http://boyazhibo-1255530974.picgz.myqcloud.com/image/2018/06/06/a207c4690fb5e759e013a2d153e7fe28.png","status":"Y"},"shop":{"certification_type":"user_shop","food_circulation_img":"","business_licence_img":"","store_type_id":"19","store_type_name":"美妆","store_certification_status":"Y","store_certification_result":"","shop_certification_type":"user_shop","predict_time":"2018-06-09","time":"2018-06-06"}}
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
         * user : {"realname":"好的好好的","phone":"13800000010","certification_number":"440223199108292720","front_img":"http://boyazhibo-1255530974.picgz.myqcloud.com/image/2018/06/06/505c68e16cc82dd4bc112720dbe56942.png","back_img":"http://boyazhibo-1255530974.picgz.myqcloud.com/image/2018/06/06/a207c4690fb5e759e013a2d153e7fe28.png","status":"Y"}
         * shop : {"certification_type":"user_shop","food_circulation_img":"","business_licence_img":"","store_type_id":"19","store_type_name":"美妆","store_certification_status":"Y","store_certification_result":"","shop_certification_type":"user_shop","predict_time":"2018-06-09","time":"2018-06-06"}
         */

        private UserBean user;
        private ShopBean shop;

        public UserBean getUser() {
            return user;
        }

        public void setUser(UserBean user) {
            this.user = user;
        }

        public ShopBean getShop() {
            return shop;
        }

        public void setShop(ShopBean shop) {
            this.shop = shop;
        }

        public static class UserBean implements Serializable{
            /**
             * realname : 好的好好的
             * phone : 13800000010
             * certification_number : 440223199108292720
             * front_img : http://boyazhibo-1255530974.picgz.myqcloud.com/image/2018/06/06/505c68e16cc82dd4bc112720dbe56942.png
             * back_img : http://boyazhibo-1255530974.picgz.myqcloud.com/image/2018/06/06/a207c4690fb5e759e013a2d153e7fe28.png
             * status : Y
             */

            private String realname;
            private String phone;
            private String certification_number;
            private String front_img;
            private String back_img;
            private String status;
            private Boolean is_null;

            public Boolean getIs_null() {
                return is_null;
            }

            public void setIs_null(Boolean is_null) {
                this.is_null = is_null;
            }

            public String getRealname() {
                return realname;
            }

            public void setRealname(String realname) {
                this.realname = realname;
            }

            public String getPhone() {
                return phone;
            }

            public void setPhone(String phone) {
                this.phone = phone;
            }

            public String getCertification_number() {
                return certification_number;
            }

            public void setCertification_number(String certification_number) {
                this.certification_number = certification_number;
            }

            public String getFront_img() {
                return front_img;
            }

            public void setFront_img(String front_img) {
                this.front_img = front_img;
            }

            public String getBack_img() {
                return back_img;
            }

            public void setBack_img(String back_img) {
                this.back_img = back_img;
            }

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }
        }

        public static class ShopBean implements Serializable{
            /**
             * certification_type : user_shop
             * food_circulation_img :
             * business_licence_img :
             * store_type_id : 19
             * store_type_name : 美妆
             * store_certification_status : Y
             * store_certification_result :
             * shop_certification_type : user_shop
             * predict_time : 2018-06-09
             * time : 2018-06-06
             */

            private String certification_type;
            private String food_circulation_img;
            private String business_licence_img;
            private String store_type_id;
            private String store_type_name;
            private String shop_store_name;
            private String store_certification_status;
            private String store_certification_result;
            private String shop_certification_type;
            private String predict_time;
            private String time;
            private Boolean is_null;


            public String getShop_store_name() {
                return shop_store_name;
            }

            public void setShop_store_name(String shop_store_name) {
                this.shop_store_name = shop_store_name;
            }

            public Boolean getIs_null() {
                return is_null;
            }

            public void setIs_null(Boolean is_null) {
                this.is_null = is_null;
            }

            public String getCertification_type() {
                return certification_type;
            }

            public void setCertification_type(String certification_type) {
                this.certification_type = certification_type;
            }

            public String getFood_circulation_img() {
                return food_circulation_img;
            }

            public void setFood_circulation_img(String food_circulation_img) {
                this.food_circulation_img = food_circulation_img;
            }

            public String getBusiness_licence_img() {
                return business_licence_img;
            }

            public void setBusiness_licence_img(String business_licence_img) {
                this.business_licence_img = business_licence_img;
            }

            public String getStore_type_id() {
                return store_type_id;
            }

            public void setStore_type_id(String store_type_id) {
                this.store_type_id = store_type_id;
            }

            public String getStore_type_name() {
                return store_type_name;
            }

            public void setStore_type_name(String store_type_name) {
                this.store_type_name = store_type_name;
            }

            public String getStore_certification_status() {
                return store_certification_status;
            }

            public void setStore_certification_status(String store_certification_status) {
                this.store_certification_status = store_certification_status;
            }

            public String getStore_certification_result() {
                return store_certification_result;
            }

            public void setStore_certification_result(String store_certification_result) {
                this.store_certification_result = store_certification_result;
            }

            public String getShop_certification_type() {
                return shop_certification_type;
            }

            public void setShop_certification_type(String shop_certification_type) {
                this.shop_certification_type = shop_certification_type;
            }

            public String getPredict_time() {
                return predict_time;
            }

            public void setPredict_time(String predict_time) {
                this.predict_time = predict_time;
            }

            public String getTime() {
                return time;
            }

            public void setTime(String time) {
                this.time = time;
            }
        }
    }
}
