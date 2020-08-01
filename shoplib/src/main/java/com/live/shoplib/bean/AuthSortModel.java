package com.live.shoplib.bean;

import com.hn.library.http.BaseResponseModel;

import java.util.List;

/**
 * 作者：Mr.Xu
 * 时间：2018/1/3
 */
public class AuthSortModel extends BaseResponseModel{


    /**
     * d : {"shop_certification":[{"id":"1","type":"user_shop","name":"个人卖家认证","content":"个人卖家认证。。。","logo":"","create_time":"0","update_time":"0"},{"id":"2","type":"flagship_shop","name":"旗舰店认证","content":"旗舰店认证。。。","logo":"","create_time":"0","update_time":"0"}]}
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
         * shop_certification : [{"id":"1","type":"user_shop","name":"个人卖家认证","content":"个人卖家认证。。。","logo":"","create_time":"0","update_time":"0"},{"id":"2","type":"flagship_shop","name":"旗舰店认证","content":"旗舰店认证。。。","logo":"","create_time":"0","update_time":"0"}]
         */

        private List<ShopCertificationEntity> shop_certification;

        public void setShop_certification(List<ShopCertificationEntity> shop_certification) {
            this.shop_certification = shop_certification;
        }

        public List<ShopCertificationEntity> getShop_certification() {
            return shop_certification;
        }

        public static class ShopCertificationEntity {
            /**
             * id : 1
             * type : user_shop
             * name : 个人卖家认证
             * content : 个人卖家认证。。。
             * logo :
             * create_time : 0
             * update_time : 0
             */

            private String id;
            private String type;
            private String name;
            private String content;
            private String logo;
            private String create_time;
            private String update_time;

            public void setId(String id) {
                this.id = id;
            }

            public void setType(String type) {
                this.type = type;
            }

            public void setName(String name) {
                this.name = name;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public void setLogo(String logo) {
                this.logo = logo;
            }

            public void setCreate_time(String create_time) {
                this.create_time = create_time;
            }

            public void setUpdate_time(String update_time) {
                this.update_time = update_time;
            }

            public String getId() {
                return id;
            }

            public String getType() {
                return type;
            }

            public String getName() {
                return name;
            }

            public String getContent() {
                return content;
            }

            public String getLogo() {
                return logo;
            }

            public String getCreate_time() {
                return create_time;
            }

            public String getUpdate_time() {
                return update_time;
            }
        }
    }
}
