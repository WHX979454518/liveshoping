package com.live.shoplib.bean;

import com.hn.library.http.BaseResponseModel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/4/16.
 */

public class GoodsAddModel extends BaseResponseModel implements Serializable {


    /**
     * d : {"columns":[{"id":"46","name":"美妆"},{"id":"47","name":"彩妆"},{"id":"48","name":"护肤"},{"id":"49","name":"洗护类"}],"goods":{"column_id":"46","goods_attr":{},"goods_detail":"测试内容84t7","goods_detail_imgs":"<p><img src=\"http://boyazhibo-1255530974.picgz.myqcloud.com/image/20180411/1523413515229480.png\" title=\"1523413515229480.png\" alt=\"1511409568526749.png\"/><\/p>","goods_id":"9","goods_img":"http://boyazhibo-1255530974.picgz.myqcloud.com/image/20180411/1523413509495557.png","goods_imgs":"http://boyazhibo-1255530974.picgz.myqcloud.com/image/20180411/1523413524195417.png,http://boyazhibo-1255530974.picgz.myqcloud.com/image/20180411/1523413524814206.png,http://boyazhibo-1255530974.picgz.myqcloud.com/image/20180411/1523413524849298.png,http://boyazhibo-1255530974.picgz.myqcloud.com/image/20180411/1523413524864996.png,http://boyazhibo-1255530974.picgz.myqcloud.com/image/20180411/1523413524505986.png","goods_name":"测试测试","goods_price":"2.00","goods_promise":["7天包退"],"goods_sn":"测试测试","goods_stock":"3","goods_stock_notice":"1","store_goods_category_name":"护肤品类"},"promises":[{"id":"1","name":"7天包退"},{"id":"2","name":"15天包换"},{"id":"3","name":"退货赔运费"},{"id":"4","name":"包邮"}],"sku":[{}],"spec":[{}]}
     */

    private GoodsAddModel.DBean d;

    public GoodsAddModel.DBean getD() {
        return d;
    }

    public void setD(GoodsAddModel.DBean d) {
        this.d = d;
    }

    public static class DBean implements Serializable {
        @Override
        public String toString() {
            return "DBean{" +
                    "goods=" + goods +
                    ", columns=" + columns +
                    ", promises=" + promises +
                    ", sku=" + sku +
                    ", spec=" + spec +
                    '}';
        }

        /**
         * columns : [{"id":"46","name":"美妆"},{"id":"47","name":"彩妆"},{"id":"48","name":"护肤"},{"id":"49","name":"洗护类"}]
         * goods : {"column_id":"46","goods_attr":{},"goods_detail":"测试内容84t7","goods_detail_imgs":"<p><img src=\"http://boyazhibo-1255530974.picgz.myqcloud.com/image/20180411/1523413515229480.png\" title=\"1523413515229480.png\" alt=\"1511409568526749.png\"/><\/p>","goods_id":"9","goods_img":"http://boyazhibo-1255530974.picgz.myqcloud.com/image/20180411/1523413509495557.png","goods_imgs":"http://boyazhibo-1255530974.picgz.myqcloud.com/image/20180411/1523413524195417.png,http://boyazhibo-1255530974.picgz.myqcloud.com/image/20180411/1523413524814206.png,http://boyazhibo-1255530974.picgz.myqcloud.com/image/20180411/1523413524849298.png,http://boyazhibo-1255530974.picgz.myqcloud.com/image/20180411/1523413524864996.png,http://boyazhibo-1255530974.picgz.myqcloud.com/image/20180411/1523413524505986.png","goods_name":"测试测试","goods_price":"2.00","goods_promise":["7天包退"],"goods_sn":"测试测试","goods_stock":"3","goods_stock_notice":"1","store_goods_category_name":"护肤品类"}
         * promises : [{"id":"1","name":"7天包退"},{"id":"2","name":"15天包换"},{"id":"3","name":"退货赔运费"},{"id":"4","name":"包邮"}]
         * sku : [{}]
         * spec : [{}]
         */


        private GoodsAddModel.DBean.GoodsBean goods;
        private List<GoodsAddModel.DBean.ColumnsBean> columns;
        private List<GoodsAddModel.DBean.PromisesBean> promises;
        private Object sku;
        private Object spec;

        public GoodsAddModel.DBean.GoodsBean getGoods() {
            return goods;
        }

        public void setGoods(GoodsAddModel.DBean.GoodsBean goods) {
            this.goods = goods;
        }

        public List<GoodsAddModel.DBean.ColumnsBean> getColumns() {
            return columns;
        }

        public void setColumns(List<GoodsAddModel.DBean.ColumnsBean> columns) {
            this.columns = columns;
        }

        public List<GoodsAddModel.DBean.PromisesBean> getPromises() {
            return promises;
        }

        public void setPromises(List<GoodsAddModel.DBean.PromisesBean> promises) {
            this.promises = promises;
        }

        public Object getSku() {
            return sku;
        }

        public void setSku(Object sku) {
            this.sku = sku;
        }

        public Object getSpec() {

            return spec;
        }

        public void setSpec(Object spec) {
            this.spec = spec;
        }

        public static class GoodsBean implements Serializable {

            @Override
            public String toString() {
                return "GoodsBean{" +
                        "column_id='" + column_id + '\'' +
                        ", goods_attr=" + goods_attr +
                        ", goods_detail='" + goods_detail + '\'' +
                        ", goods_detail_imgs='" + goods_detail_imgs + '\'' +
                        ", goods_id='" + goods_id + '\'' +
                        ", goods_img='" + goods_img + '\'' +
                        ", goods_imgs='" + goods_imgs + '\'' +
                        ", goods_name='" + goods_name + '\'' +
                        ", goods_price='" + goods_price + '\'' +
                        ", goods_sn='" + goods_sn + '\'' +
                        ", goods_stock='" + goods_stock + '\'' +
                        ", goods_stock_notice='" + goods_stock_notice + '\'' +
                        ", store_goods_category_name='" + store_goods_category_name + '\'' +
                        ", goods_promise=" + goods_promise +
                        '}';
            }

            /**
             * column_id : 46
             * goods_attr : {}
             * goods_detail : 测试内容84t7
             * goods_detail_imgs : <p><img src="http://boyazhibo-1255530974.picgz.myqcloud.com/image/20180411/1523413515229480.png" title="1523413515229480.png" alt="1511409568526749.png"/></p>
             * goods_id : 9
             * goods_img : http://boyazhibo-1255530974.picgz.myqcloud.com/image/20180411/1523413509495557.png
             * goods_imgs : http://boyazhibo-1255530974.picgz.myqcloud.com/image/20180411/1523413524195417.png,http://boyazhibo-1255530974.picgz.myqcloud.com/image/20180411/1523413524814206.png,http://boyazhibo-1255530974.picgz.myqcloud.com/image/20180411/1523413524849298.png,http://boyazhibo-1255530974.picgz.myqcloud.com/image/20180411/1523413524864996.png,http://boyazhibo-1255530974.picgz.myqcloud.com/image/20180411/1523413524505986.png
             * goods_name : 测试测试
             * goods_price : 2.00
             * goods_promise : ["7天包退"]
             * goods_sn : 测试测试
             * goods_stock : 3
             * goods_stock_notice : 1
             * store_goods_category_name : 护肤品类
             */

            private String column_id;
            private Object goods_attr;
            private String goods_detail;
            private String goods_detail_imgs;
            private String goods_id;
            private String goods_img;
            private String goods_imgs;
            private String goods_name;
            private String goods_price;
            private String goods_sn;
            private String goods_stock;
            private String goods_stock_notice;
            private String store_goods_category_name;
            private ArrayList<String> goods_promise;

            public Object getGoods_attr() {
                return goods_attr;
            }

            public void setGoods_attr(Object goods_attr) {
                this.goods_attr = goods_attr;
            }

            public String getColumn_id() {
                return column_id;
            }

            public void setColumn_id(String column_id) {
                this.column_id = column_id;
            }


            public String getGoods_detail() {
                return goods_detail;
            }

            public void setGoods_detail(String goods_detail) {
                this.goods_detail = goods_detail;
            }

            public String getGoods_detail_imgs() {
                return goods_detail_imgs;
            }

            public void setGoods_detail_imgs(String goods_detail_imgs) {
                this.goods_detail_imgs = goods_detail_imgs;
            }

            public String getGoods_id() {
                return goods_id;
            }

            public void setGoods_id(String goods_id) {
                this.goods_id = goods_id;
            }

            public String getGoods_img() {
                return goods_img;
            }

            public void setGoods_img(String goods_img) {
                this.goods_img = goods_img;
            }

            public String getGoods_imgs() {
                return goods_imgs;
            }

            public void setGoods_imgs(String goods_imgs) {
                this.goods_imgs = goods_imgs;
            }

            public String getGoods_name() {
                return goods_name;
            }

            public void setGoods_name(String goods_name) {
                this.goods_name = goods_name;
            }

            public String getGoods_price() {
                return goods_price;
            }

            public void setGoods_price(String goods_price) {
                this.goods_price = goods_price;
            }

            public String getGoods_sn() {
                return goods_sn;
            }

            public void setGoods_sn(String goods_sn) {
                this.goods_sn = goods_sn;
            }

            public String getGoods_stock() {
                return goods_stock;
            }

            public void setGoods_stock(String goods_stock) {
                this.goods_stock = goods_stock;
            }

            public String getGoods_stock_notice() {
                return goods_stock_notice;
            }

            public void setGoods_stock_notice(String goods_stock_notice) {
                this.goods_stock_notice = goods_stock_notice;
            }

            public String getStore_goods_category_name() {
                return store_goods_category_name;
            }

            public void setStore_goods_category_name(String store_goods_category_name) {
                this.store_goods_category_name = store_goods_category_name;
            }

            public ArrayList<String> getGoods_promise() {
                return goods_promise;
            }

            public void setGoods_promise(ArrayList<String> goods_promise) {
                this.goods_promise = goods_promise;
            }

        }

        public static class ColumnsBean implements Serializable {

            @Override
            public String toString() {
                return "ColumnsBean{" +
                        "id='" + id + '\'' +
                        ", name='" + name + '\'' +
                        '}';
            }

            /**
             * id : 46
             * name : 美妆
             */

            private String id;
            private String name;

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
        }

        public static class PromisesBean implements Serializable {


            @Override
            public String toString() {
                return "PromisesBean{" +
                        "id='" + id + '\'' +
                        ", name='" + name + '\'' +
                        '}';
            }

            /**
             * id : 1
             * name : 7天包退
             */

            private String id;
            private String name;

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
        }


    }
}
