package com.live.shoplib.bean;

import com.hn.library.http.BaseResponseModel;

import java.util.List;

/**
 * 作者：Mr.Xu
 * 时间：2018/1/4
 */
public class StoreEditModel extends BaseResponseModel {


    /**
     * d : {"address":"不告诉你","city":"深圳市","district":"南山区","icon":"FvZ_eWplp4BkoKB3ZC7IrAcajMKT","id":"3","img":["FvZ_eWplp4BkoKB3ZC7IrAcajMKT","FvZ_eWplp4BkoKB3ZC7IrAcajMKT","FvZ_eWplp4BkoKB3ZC7IrAcajMKT"],"intro":"第一个商铺","name":"第一个商铺","notice":"第一个商铺","phone":"18820992517","province":"广东省","realName":"六天","table":["主营标签1","主营标签2"],"user_id":"264","video":""}
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
         * address : 不告诉你
         * city : 深圳市
         * district : 南山区
         * icon : FvZ_eWplp4BkoKB3ZC7IrAcajMKT
         * id : 3
         * img : ["FvZ_eWplp4BkoKB3ZC7IrAcajMKT","FvZ_eWplp4BkoKB3ZC7IrAcajMKT","FvZ_eWplp4BkoKB3ZC7IrAcajMKT"]
         * intro : 第一个商铺
         * name : 第一个商铺
         * notice : 第一个商铺
         * phone : 18820992517
         * province : 广东省
         * realName : 六天
         * table : ["主营标签1","主营标签2"]
         * user_id : 264
         * video :
         */

        private String address;
        private String city;
        private String district;
        private String icon;
        private String id;
        private String intro;
        private String name;
        private String notice;
        private String phone;
        private String province;
        private String realName;
        private String shop_fee;
        private String user_id;
        private String video;
        private List<String> img;
        private List<String> table;

        public String getShop_fee() {
            return shop_fee;
        }

        public void setShop_fee(String shop_fee) {
            this.shop_fee = shop_fee;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public void setDistrict(String district) {
            this.district = district;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public void setId(String id) {
            this.id = id;
        }

        public void setIntro(String intro) {
            this.intro = intro;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setNotice(String notice) {
            this.notice = notice;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public void setProvince(String province) {
            this.province = province;
        }

        public void setRealName(String realName) {
            this.realName = realName;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public void setVideo(String video) {
            this.video = video;
        }

        public void setImg(List<String> img) {
            this.img = img;
        }

        public void setTable(List<String> table) {
            this.table = table;
        }

        public String getAddress() {
            return address;
        }

        public String getCity() {
            return city;
        }

        public String getDistrict() {
            return district;
        }

        public String getIcon() {
            return icon;
        }

        public String getId() {
            return id;
        }

        public String getIntro() {
            return intro;
        }

        public String getName() {
            return name;
        }

        public String getNotice() {
            return notice;
        }

        public String getPhone() {
            return phone;
        }

        public String getProvince() {
            return province;
        }

        public String getRealName() {
            return realName;
        }

        public String getUser_id() {
            return user_id;
        }

        public String getVideo() {
            return video;
        }

        public List<String> getImg() {
            return img;
        }

        public List<String> getTable() {
            return table;
        }
    }
}
