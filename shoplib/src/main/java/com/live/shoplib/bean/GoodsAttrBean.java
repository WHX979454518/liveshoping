package com.live.shoplib.bean;

import java.util.List;

/**
 * 作者：Mr.Xu
 * 时间：2017/12/25
 */
public class GoodsAttrBean {

    /**
     * goods_id : 100117
     * goods_name : 红米5 Plus
     * goods_img : http://dev.static.fireniao.com/image/20171226/1514256058917136.jpg
     * goods_price : 999.00
     * store_id : 7
     * spec : [{"group":"颜色","list":[{"id":"368","spec_value":"白色","spec_image":"","is_select":"Y"}]},{"group":"尺寸","list":[{"id":"369","spec_value":"大","spec_image":"","is_select":"N"},{"id":"370","spec_value":"中","spec_image":"","is_select":"Y"}]},{"group":"舒适","list":[{"id":"371","spec_value":"舒服","spec_image":"","is_select":"Y"}]}]
     * sku :
     * spec_sku :
     */

    private String sku_id;
    private String spec_text;
    private int num = 1;
    private int num_all = 0;

    private String goods_id;
    private String goods_name;
    private String goods_img;
    private String goods_price;
    private String store_id;
    private List<SpecEntity> spec;

    public String getSku_id() {
        return sku_id;
    }

    public void setSku_id(String sku_id) {
        this.sku_id = sku_id;
    }

    public int getNum() {
        return num;
    }

    public String getSpec_text() {
        return spec_text;
    }

    public void setSpec_text(String spec_text) {
        this.spec_text = spec_text;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public int getNum_all() {
        return num_all;
    }

    public void setNum_all(int num_all) {
        this.num_all = num_all;
    }

    /**
     * sku : [{"sku_id":"23","spec_ids":["368","369","371"],"price":"1.00","stock":"1","ids":"368:369:371"}]
     */



    private List<SkuEntity> sku;

    public void setGoods_id(String goods_id) {
        this.goods_id = goods_id;
    }

    public void setGoods_name(String goods_name) {
        this.goods_name = goods_name;
    }

    public void setGoods_img(String goods_img) {
        this.goods_img = goods_img;
    }

    public void setGoods_price(String goods_price) {
        this.goods_price = goods_price;
    }

    public void setStore_id(String store_id) {
        this.store_id = store_id;
    }

    public void setSpec(List<SpecEntity> spec) {
        this.spec = spec;
    }

    public String getGoods_id() {
        return goods_id;
    }

    public String getGoods_name() {
        return goods_name;
    }

    public String getGoods_img() {
        return goods_img;
    }

    public String getGoods_price() {
        return goods_price;
    }

    public String getStore_id() {
        return store_id;
    }


    public List<SpecEntity> getSpec() {
        return spec;
    }

    public void setSku(List<SkuEntity> sku) {
        this.sku = sku;
    }

    public List<SkuEntity> getSku() {
        return sku;
    }

    public static class SpecEntity {
        /**
         * group : 颜色
         * list : [{"id":"368","spec_value":"白色","spec_image":"","is_select":"Y"}]
         */

        private String group;
        private List<ListEntity> list;


        public void setGroup(String group) {
            this.group = group;
        }

        public void setList(List<ListEntity> list) {
            this.list = list;
        }

        public String getGroup() {
            return group;
        }

        public List<ListEntity> getList() {
            return list;
        }

        public static class ListEntity {
            /**
             * id : 368
             * spec_value : 白色
             * spec_image :
             * is_select : Y
             */

            private String id;
            private String spec_value;
            private String spec_image;
            private String is_select;
            private boolean check;

            public boolean isCheck() {
                return check;
            }

            public void setCheck(boolean check) {
                this.check = check;
            }


            public void setId(String id) {
                this.id = id;
            }

            public void setSpec_value(String spec_value) {
                this.spec_value = spec_value;
            }

            public void setSpec_image(String spec_image) {
                this.spec_image = spec_image;
            }

            public void setIs_select(String is_select) {
                this.is_select = is_select;
            }

            public String getId() {
                return id;
            }

            public String getSpec_value() {
                return spec_value;
            }

            public String getSpec_image() {
                return spec_image;
            }

            public String getIs_select() {
                return is_select;
            }
        }
    }

    public static class SkuEntity {
        /**
         * sku_id : 23
         * spec_ids : ["368","369","371"]
         * price : 1.00
         * stock : 1
         * ids : 368:369:371
         */

        private String sku_id;
        private String spec_text;
        private String price;
        private String stock;
        private String ids;
        private List<String> spec_ids;

        public String getSpec_text() {
            return spec_text;
        }

        public void setSpec_text(String spec_text) {
            this.spec_text = spec_text;
        }

        public void setSku_id(String sku_id) {
            this.sku_id = sku_id;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public void setStock(String stock) {
            this.stock = stock;
        }

        public void setIds(String ids) {
            this.ids = ids;
        }

        public void setSpec_ids(List<String> spec_ids) {
            this.spec_ids = spec_ids;
        }

        public String getSku_id() {
            return sku_id;
        }

        public String getPrice() {
            return price;
        }

        public String getStock() {
            return stock;
        }

        public String getIds() {
            return ids;
        }

        public List<String> getSpec_ids() {
            return spec_ids;
        }
    }
}
