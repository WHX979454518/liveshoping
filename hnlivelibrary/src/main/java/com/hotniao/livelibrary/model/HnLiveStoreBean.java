package com.hotniao.livelibrary.model;

/**
 * 作者：Mr.Xu
 * 时间：2018/1/10
 */
public class HnLiveStoreBean {

    /**
     * data : {"goods":{"img":"测试内容237i","price":"天行"},"store":{"icon":"测试内容5v83","id":"测试内容1152","name":"测试内容vny9"}}
     * msg :
     * type : recommend
     */

    private DataEntity data;
    private String msg;
    private String type;

    public void setData(DataEntity data) {
        this.data = data;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setType(String type) {
        this.type = type;
    }

    public DataEntity getData() {
        return data;
    }

    public String getMsg() {
        return msg;
    }

    public String getType() {
        return type;
    }

    public static class DataEntity {
        /**
         * goods : {"img":"测试内容237i","price":"天行"}
         * store : {"icon":"测试内容5v83","id":"测试内容1152","name":"测试内容vny9"}
         */

        private GoodsEntity goods;
        private StoreEntity store;

        public void setGoods(GoodsEntity goods) {
            this.goods = goods;
        }

        public void setStore(StoreEntity store) {
            this.store = store;
        }

        public GoodsEntity getGoods() {
            return goods;
        }

        public StoreEntity getStore() {
            return store;
        }

        public static class GoodsEntity {
            /**
             * img : 测试内容237i
             * price : 天行
             */

            private String img;
            private String price;

            public void setImg(String img) {
                this.img = img;
            }

            public void setPrice(String price) {
                this.price = price;
            }

            public String getImg() {
                return img;
            }

            public String getPrice() {
                return price;
            }
        }

        public static class StoreEntity {
            /**
             * icon : 测试内容5v83
             * id : 测试内容1152
             * name : 测试内容vny9
             */

            private String icon;
            private String id;
            private String name;

            public void setIcon(String icon) {
                this.icon = icon;
            }

            public void setId(String id) {
                this.id = id;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getIcon() {
                return icon;
            }

            public String getId() {
                return id;
            }

            public String getName() {
                return name;
            }
        }
    }
}
