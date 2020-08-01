package com.hotniao.live.model;

import com.hn.library.http.BaseResponseModel;

/**
 * 作者：Mr.Xu
 * 时间：2018/1/11
 */
public class AuthDetailsModel extends BaseResponseModel{


    /**
     * d : {"user_back_img":"http://static.greenlive.1booker.com//upload/image/20171017/1508211188184399.png","user_certification_create_time":"1508211213","user_certification_id":"64","user_certification_number":"412829198903206458","user_certification_result":"","user_certification_status":"Y","user_certification_type":"1","user_certification_update_time":"0","user_front_img":"http://static.greenlive.1booker.com//upload/image/20171017/1508211180449998.png","user_id":"264","user_img":"http://static.greenlive.1booker.com//upload/image/20171017/1508211196406323.png","user_phone":"18820992517","user_realname":"六天"}
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
         * user_back_img : http://static.greenlive.1booker.com//upload/image/20171017/1508211188184399.png
         * user_certification_create_time : 1508211213
         * user_certification_id : 64
         * user_certification_number : 412829198903206458
         * user_certification_result :
         * user_certification_status : Y
         * user_certification_type : 1
         * user_certification_update_time : 0
         * user_front_img : http://static.greenlive.1booker.com//upload/image/20171017/1508211180449998.png
         * user_id : 264
         * user_img : http://static.greenlive.1booker.com//upload/image/20171017/1508211196406323.png
         * user_phone : 18820992517
         * user_realname : 六天
         */

        private String user_back_img;
        private String user_certification_create_time;
        private String user_certification_id;
        private String user_certification_number;
        private String user_certification_result;
        private String user_certification_status;
        private String user_certification_type;
        private String user_certification_update_time;
        private String user_front_img;
        private String user_id;
        private String user_img;
        private String user_phone;
        private String user_realname;

        public void setUser_back_img(String user_back_img) {
            this.user_back_img = user_back_img;
        }

        public void setUser_certification_create_time(String user_certification_create_time) {
            this.user_certification_create_time = user_certification_create_time;
        }

        public void setUser_certification_id(String user_certification_id) {
            this.user_certification_id = user_certification_id;
        }

        public void setUser_certification_number(String user_certification_number) {
            this.user_certification_number = user_certification_number;
        }

        public void setUser_certification_result(String user_certification_result) {
            this.user_certification_result = user_certification_result;
        }

        public void setUser_certification_status(String user_certification_status) {
            this.user_certification_status = user_certification_status;
        }

        public void setUser_certification_type(String user_certification_type) {
            this.user_certification_type = user_certification_type;
        }

        public void setUser_certification_update_time(String user_certification_update_time) {
            this.user_certification_update_time = user_certification_update_time;
        }

        public void setUser_front_img(String user_front_img) {
            this.user_front_img = user_front_img;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public void setUser_img(String user_img) {
            this.user_img = user_img;
        }

        public void setUser_phone(String user_phone) {
            this.user_phone = user_phone;
        }

        public void setUser_realname(String user_realname) {
            this.user_realname = user_realname;
        }

        public String getUser_back_img() {
            return user_back_img;
        }

        public String getUser_certification_create_time() {
            return user_certification_create_time;
        }

        public String getUser_certification_id() {
            return user_certification_id;
        }

        public String getUser_certification_number() {
            return user_certification_number;
        }

        public String getUser_certification_result() {
            return user_certification_result;
        }

        public String getUser_certification_status() {
            return user_certification_status;
        }

        public String getUser_certification_type() {
            return user_certification_type;
        }

        public String getUser_certification_update_time() {
            return user_certification_update_time;
        }

        public String getUser_front_img() {
            return user_front_img;
        }

        public String getUser_id() {
            return user_id;
        }

        public String getUser_img() {
            return user_img;
        }

        public String getUser_phone() {
            return user_phone;
        }

        public String getUser_realname() {
            return user_realname;
        }
    }
}
