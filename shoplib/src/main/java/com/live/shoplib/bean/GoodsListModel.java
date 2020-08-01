package com.live.shoplib.bean;

import com.hn.library.http.BaseResponseModel;

import java.util.List;

/**
 * 作者：Mr.Xu
 * 时间：2018/1/3
 */
public class GoodsListModel extends BaseResponseModel{

    /**
     * d : {"items":[{"goods_id":"100090","goods_img":"http://dev.static.fireniao.com/image/20171222/1513933350937476.png","goods_name":"努比亚手机","goods_price":"1.00","goods_sales":"0"}],"next":1,"page":1,"pagesize":10,"pagetotal":1,"prev":1,"total":1}
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
         * items : [{"goods_id":"100090","goods_img":"http://dev.static.fireniao.com/image/20171222/1513933350937476.png","goods_name":"努比亚手机","goods_price":"1.00","goods_sales":"0"}]
         * next : 1
         * page : 1
         * pagesize : 10
         * pagetotal : 1
         * prev : 1
         * total : 1
         */

        private int next;
        private int page;
        private int pagesize;
        private int pagetotal;
        private int prev;
        private int total;
        private List<ItemsEntity> items;

        public void setNext(int next) {
            this.next = next;
        }

        public void setPage(int page) {
            this.page = page;
        }

        public void setPagesize(int pagesize) {
            this.pagesize = pagesize;
        }

        public void setPagetotal(int pagetotal) {
            this.pagetotal = pagetotal;
        }

        public void setPrev(int prev) {
            this.prev = prev;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public void setItems(List<ItemsEntity> items) {
            this.items = items;
        }

        public int getNext() {
            return next;
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

        public int getPrev() {
            return prev;
        }

        public int getTotal() {
            return total;
        }

        public List<ItemsEntity> getItems() {
            return items;
        }

        public static class ItemsEntity {
            /**
             * goods_id : 100090
             * goods_img : http://dev.static.fireniao.com/image/20171222/1513933350937476.png
             * goods_name : 努比亚手机
             * goods_price : 1.00
             * goods_sales : 0
             */
            private boolean check;
            private String goods_id;
            private String goods_img;
            private String goods_name;
            private String goods_price;
            private String goods_sales;
            private String goods_stock;

            public String getGoods_stock() {
                return goods_stock;
            }

            public void setGoods_stock(String goods_stock) {
                this.goods_stock = goods_stock;
            }

            public boolean isCheck() {
                return check;
            }

            public void setCheck(boolean check) {
                this.check = check;
            }

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
