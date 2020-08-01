package com.live.shoplib.bean;

import com.hn.library.http.BaseResponseModel;

import java.util.List;

/**
 * 作者：Mr.Xu
 * 时间：2018/1/2
 */
public class StoreDetailsModel extends BaseResponseModel{


    /**
     * d : {"address":"不告诉你","city":"深圳市","column":[{"id":"0","name":"全部"},{"id":"1","name":"栏目1"},{"id":"2","name":"栏目2"}],"district":"南山区","goods_num":"2","goods_sales":"0","icon":"FvZ_eWplp4BkoKB3ZC7IrAcajMKT","id":"3","img":["FvZ_eWplp4BkoKB3ZC7IrAcajMKT","FvZ_eWplp4BkoKB3ZC7IrAcajMKT","FvZ_eWplp4BkoKB3ZC7IrAcajMKT"],"is_collect":"Y","name":"第一个商铺","notice":"第一个商铺","province":"广东省","share":"分享还没有做哦,但这就是分享","total_collect":"0","user_id":"264","video":""}
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
         * column : [{"id":"0","name":"全部"},{"id":"1","name":"栏目1"},{"id":"2","name":"栏目2"}]
         * district : 南山区
         * goods_num : 2
         * goods_sales : 0
         * icon : FvZ_eWplp4BkoKB3ZC7IrAcajMKT
         * id : 3
         * img : ["FvZ_eWplp4BkoKB3ZC7IrAcajMKT","FvZ_eWplp4BkoKB3ZC7IrAcajMKT","FvZ_eWplp4BkoKB3ZC7IrAcajMKT"]
         * is_collect : Y
         * name : 第一个商铺
         * notice : 第一个商铺
         * province : 广东省
         * share : 分享还没有做哦,但这就是分享
         * total_collect : 0
         * user_id : 264
         * video :
         */

        private String address;
        private String city;
        private String district;
        private String goods_num;
        private String goods_sales;
        private String icon;
        private String id;
        private String is_collect;
        private String live_status;
        private String name;
        private String notice;
        private String province;
        private String share;
        private String total_collect;
        private String  total_order;
        private String user_id;
        private String video;
        private List<ColumnEntity> column;
        private List<String> img;
        /**
         * dialogue : {"charId":"测试内容d8xs","id":"测试内容79j7","name":"测试内容c50p","store_uid":"测试内容y39b"}
         */

        private DialogueEntity dialogue;

        public String getLive_status() {
            return live_status;
        }

        public void setLive_status(String live_status) {
            this.live_status = live_status;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getTotal_order() {
            return total_order;
        }

        public void setTotal_order(String total_order) {
            this.total_order = total_order;
        }

        public void setDistrict(String district) {
            this.district = district;
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

        public void setId(String id) {
            this.id = id;
        }

        public void setIs_collect(String is_collect) {
            this.is_collect = is_collect;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setNotice(String notice) {
            this.notice = notice;
        }

        public void setProvince(String province) {
            this.province = province;
        }

        public void setShare(String share) {
            this.share = share;
        }

        public void setTotal_collect(String total_collect) {
            this.total_collect = total_collect;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public void setVideo(String video) {
            this.video = video;
        }

        public void setColumn(List<ColumnEntity> column) {
            this.column = column;
        }

        public void setImg(List<String> img) {
            this.img = img;
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

        public String getGoods_num() {
            return goods_num;
        }

        public String getGoods_sales() {
            return goods_sales;
        }

        public String getIcon() {
            return icon;
        }

        public String getId() {
            return id;
        }

        public String getIs_collect() {
            return is_collect;
        }

        public String getName() {
            return name;
        }

        public String getNotice() {
            return notice;
        }

        public String getProvince() {
            return province;
        }

        public String getShare() {
            return share;
        }

        public String getTotal_collect() {
            return total_collect;
        }

        public String getUser_id() {
            return user_id;
        }

        public String getVideo() {
            return video;
        }

        public List<ColumnEntity> getColumn() {
            return column;
        }

        public List<String> getImg() {
            return img;
        }

        public void setDialogue(DialogueEntity dialogue) {
            this.dialogue = dialogue;
        }

        public DialogueEntity getDialogue() {
            return dialogue;
        }

        public static class ColumnEntity {
            /**
             * id : 0
             * name : 全部
             */

            private String id;
            private String name;

            public void setId(String id) {
                this.id = id;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getId() {
                return id;
            }

            public String getName() {
                return name;
            }
        }

        public static class DialogueEntity {
            private String charId;
            private String id;
            private String name;
            private String store_uid;
            private String user_name;

            public String getCharId() {
                return charId;
            }

            public void setCharId(String charId) {
                this.charId = charId;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getStore_uid() {
                return store_uid;
            }

            public void setStore_uid(String store_uid) {
                this.store_uid = store_uid;
            }

            public String getUser_name() {
                return user_name;
            }

            public void setUser_name(String user_name) {
                this.user_name = user_name;
            }
        }
    }
}
