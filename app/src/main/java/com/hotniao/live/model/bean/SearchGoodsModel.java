package com.hotniao.live.model.bean;

import com.hn.library.http.BaseResponseModel;

import java.util.List;

/**
 * 作者：Mr.Xu
 * 时间：2017/12/12
 */
public class SearchGoodsModel extends BaseResponseModel {

    /**
     * d : {"goods":{"items":[{"goods_id":"100121","goods_img":"http://dev.static.fireniao.com/image/20171226/1514267949531021.jpeg","goods_name":"1234","goods_price":"10.00","goods_sales":"0"},{"goods_id":"100120","goods_img":"http://dev.static.fireniao.com/image/20171226/1514256203820352.png","goods_name":"衣服1","goods_price":"1.00","goods_sales":"0"}],"page":1,"pagesize":10,"pagetotal":1,"total":2,"prev":1,"next":1}}
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
         * goods : {"items":[{"goods_id":"100121","goods_img":"http://dev.static.fireniao.com/image/20171226/1514267949531021.jpeg","goods_name":"1234","goods_price":"10.00","goods_sales":"0"},{"goods_id":"100120","goods_img":"http://dev.static.fireniao.com/image/20171226/1514256203820352.png","goods_name":"衣服1","goods_price":"1.00","goods_sales":"0"}],"page":1,"pagesize":10,"pagetotal":1,"total":2,"prev":1,"next":1}
         */

        private GoodsEntity goods;

        public void setGoods(GoodsEntity goods) {
            this.goods = goods;
        }

        public GoodsEntity getGoods() {
            return goods;
        }

        public static class GoodsEntity {
            /**
             * items : [{"goods_id":"100121","goods_img":"http://dev.static.fireniao.com/image/20171226/1514267949531021.jpeg","goods_name":"1234","goods_price":"10.00","goods_sales":"0"},{"goods_id":"100120","goods_img":"http://dev.static.fireniao.com/image/20171226/1514256203820352.png","goods_name":"衣服1","goods_price":"1.00","goods_sales":"0"}]
             * page : 1
             * pagesize : 10
             * pagetotal : 1
             * total : 2
             * prev : 1
             * next : 1
             */

            private int page;
            private int pagesize;
            private int pagetotal;
            private int total;
            private int prev;
            private int next;
            private List<ItemsEntity> items;

            public void setPage(int page) {
                this.page = page;
            }

            public void setPagesize(int pagesize) {
                this.pagesize = pagesize;
            }

            public void setPagetotal(int pagetotal) {
                this.pagetotal = pagetotal;
            }

            public void setTotal(int total) {
                this.total = total;
            }

            public void setPrev(int prev) {
                this.prev = prev;
            }

            public void setNext(int next) {
                this.next = next;
            }

            public void setItems(List<ItemsEntity> items) {
                this.items = items;
            }

            public int getPage() {
                return page;
            }

            public int getPagesize() {
                return pagesize;
            }

            public int getPagetotal() {
                return pagetotal;
            }

            public int getTotal() {
                return total;
            }

            public int getPrev() {
                return prev;
            }

            public int getNext() {
                return next;
            }

            public List<ItemsEntity> getItems() {
                return items;
            }

            public static class ItemsEntity {
                /**
                 * goods_id : 100121
                 * goods_img : http://dev.static.fireniao.com/image/20171226/1514267949531021.jpeg
                 * goods_name : 1234
                 * goods_price : 10.00
                 * goods_sales : 0
                 */

                private String goods_id;
                private String goods_img;
                private String goods_name;
                private String goods_price;
                private String goods_sales;

                public void setGoods_id(String goods_id) {
                    this.goods_id = goods_id;
                }

                public void setGoods_img(String goods_img) {
                    this.goods_img = goods_img;
                }

                public void setGoods_name(String goods_name) {
                    this.goods_name = goods_name;
                }

                public void setGoods_price(String goods_price) {
                    this.goods_price = goods_price;
                }

                public void setGoods_sales(String goods_sales) {
                    this.goods_sales = goods_sales;
                }

                public String getGoods_id() {
                    return goods_id;
                }

                public String getGoods_img() {
                    return goods_img;
                }

                public String getGoods_name() {
                    return goods_name;
                }

                public String getGoods_price() {
                    return goods_price;
                }

                public String getGoods_sales() {
                    return goods_sales;
                }
            }
        }
    }
}