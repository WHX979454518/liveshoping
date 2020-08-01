package com.live.shoplib.bean;

import com.hn.library.http.BaseResponseModel;

/**
 * 作者：Mr.Xu
 * 时间：2018/1/3
 */
public class StoreMsgModel extends BaseResponseModel{


    /**
     * d : {"store":{"certification_type":"user_shop","goods_num":"2","goods_sales":"0","icon":"FvZ_eWplp4BkoKB3ZC7IrAcajMKT","intro":"第一个商铺","is_open":"1","name":"第一个商铺","notice":"第一个商铺","realName":"六天","shop_business_licence_img":"","shop_food_circulation_img":"","storeId":"3","total_collect":"0","typeName":"第2个类型","user_phone":"18820992517"}}
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
         * store : {"certification_type":"user_shop","goods_num":"2","goods_sales":"0","icon":"FvZ_eWplp4BkoKB3ZC7IrAcajMKT","intro":"第一个商铺","is_open":"1","name":"第一个商铺","notice":"第一个商铺","realName":"六天","shop_business_licence_img":"","shop_food_circulation_img":"","storeId":"3","total_collect":"0","typeName":"第2个类型","user_phone":"18820992517"}
         */

        private StoreEntity store;

        public void setStore(StoreEntity store) {
            this.store = store;
        }

        public StoreEntity getStore() {
            return store;
        }

        public static class StoreEntity {
            /**
             * certification_type : user_shop
             * goods_num : 2
             * goods_sales : 0
             * icon : FvZ_eWplp4BkoKB3ZC7IrAcajMKT
             * intro : 第一个商铺
             * is_open : 1
             * name : 第一个商铺
             * notice : 第一个商铺
             * realName : 六天
             * shop_business_licence_img :
             * shop_food_circulation_img :
             * storeId : 3
             * total_collect : 0
             * typeName : 第2个类型
             * user_phone : 18820992517
             */

            private String certification_type;
            private String goods_num;
            private String goods_sales;
            private String icon;
            private String intro;
            private String is_open;
            private String name;
            private String notice;
            private String realName;
            private String shop_business_licence_img;
            private String shop_food_circulation_img;
            private String storeId;
            private String total_collect;
            private String typeName;
            private String user_phone;

            public void setCertification_type(String certification_type) {
                this.certification_type = certification_type;
            }

            public void setGoods_num(String goods_num) {
                this.goods_num = goods_num;
            }

            public void setGoods_sales(String goods_sales) {
                this.goods_sales = goods_sales;
            }

            public void setIcon(String icon) {
                this.icon = icon;
            }

            public void setIntro(String intro) {
                this.intro = intro;
            }

            public void setIs_open(String is_open) {
                this.is_open = is_open;
            }

            public void setName(String name) {
                this.name = name;
            }

            public void setNotice(String notice) {
                this.notice = notice;
            }

            public void setRealName(String realName) {
                this.realName = realName;
            }

            public void setShop_business_licence_img(String shop_business_licence_img) {
                this.shop_business_licence_img = shop_business_licence_img;
            }

            public void setShop_food_circulation_img(String shop_food_circulation_img) {
                this.shop_food_circulation_img = shop_food_circulation_img;
            }

            public void setStoreId(String storeId) {
                this.storeId = storeId;
            }

            public void setTotal_collect(String total_collect) {
                this.total_collect = total_collect;
            }

            public void setTypeName(String typeName) {
                this.typeName = typeName;
            }

            public void setUser_phone(String user_phone) {
                this.user_phone = user_phone;
            }

            public String getCertification_type() {
                return certification_type;
            }

            public String getGoods_num() {
                return goods_num;
            }

            public String getGoods_sales() {
                return goods_sales;
            }

            public String getIcon() {
                return icon;
            }

            public String getIntro() {
                return intro;
            }

            public String getIs_open() {
                return is_open;
            }

            public String getName() {
                return name;
            }

            public String getNotice() {
                return notice;
            }

            public String getRealName() {
                return realName;
            }

            public String getShop_business_licence_img() {
                return shop_business_licence_img;
            }

            public String getShop_food_circulation_img() {
                return shop_food_circulation_img;
            }

            public String getStoreId() {
                return storeId;
            }

            public String getTotal_collect() {
                return total_collect;
            }

            public String getTypeName() {
                return typeName;
            }

            public String getUser_phone() {
                return user_phone;
            }
        }
    }
}
