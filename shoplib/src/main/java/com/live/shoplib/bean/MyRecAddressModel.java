package com.live.shoplib.bean;

import com.hn.library.http.BaseResponseModel;

import java.io.Serializable;
import java.util.List;

/**
 * 作者：Mr.Xu
 * 时间：2017/12/20
 */
public class MyRecAddressModel extends BaseResponseModel{


    /**
     * d : [{"area":"南山区","city":"深圳市","detail":"大冲国际中心1222","id":"20","is_default":"1","nick":"小刘","phone":"13424509684","province":"广东省","user_id":"264"},{"area":"1","city":"1","detail":"2","id":"13","is_default":"0","nick":"小刘","phone":"12345678912","province":"123456789123","user_id":"264"},{"area":"南山区","city":"深圳市","detail":"大冲国际中心1222","id":"14","is_default":"0","nick":"小刘","phone":"13424509684","province":"广东省","user_id":"264"},{"area":"南山区","city":"深圳市","detail":"大冲国际中心1222","id":"16","is_default":"0","nick":"小刘","phone":"13424509684","province":"广东省","user_id":"264"},{"area":"南山区","city":"深圳市","detail":"大冲国际中心1222","id":"17","is_default":"0","nick":"小刘","phone":"13424509684","province":"广东省","user_id":"264"},{"area":"南山区","city":"深圳市","detail":"大冲国际中心1222","id":"18","is_default":"0","nick":"小刘","phone":"13424509684","province":"广东省","user_id":"264"},{"area":"南山区","city":"深圳市","detail":"大冲国际中心1222","id":"19","is_default":"0","nick":"小刘","phone":"13424509684","province":"广东省","user_id":"264"}]
     */

    private List<DEntity> d;

    public void setD(List<DEntity> d) {
        this.d = d;
    }

    public List<DEntity> getD() {
        return d;
    }

    public static class DEntity implements Serializable {
        /**
         * area : 南山区
         * city : 深圳市
         * detail : 大冲国际中心1222
         * id : 20
         * is_default : 1
         * nick : 小刘
         * phone : 13424509684
         * province : 广东省
         * user_id : 264
         */

        private String area;
        private String city;
        private String detail;
        private String id;
        private String is_default;
        private String nick;
        private String phone;
        private String province;
        private String user_id;

        public void setArea(String area) {
            this.area = area;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public void setDetail(String detail) {
            this.detail = detail;
        }

        public void setId(String id) {
            this.id = id;
        }

        public void setIs_default(String is_default) {
            this.is_default = is_default;
        }

        public void setNick(String nick) {
            this.nick = nick;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public void setProvince(String province) {
            this.province = province;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public String getArea() {
            return area;
        }

        public String getCity() {
            return city;
        }

        public String getDetail() {
            return detail;
        }

        public String getId() {
            return id;
        }

        public String getIs_default() {
            return is_default;
        }

        public String getNick() {
            return nick;
        }

        public String getPhone() {
            return phone;
        }

        public String getProvince() {
            return province;
        }

        public String getUser_id() {
            return user_id;
        }
    }
}
