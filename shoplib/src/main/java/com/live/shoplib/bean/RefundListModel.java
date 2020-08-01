package com.live.shoplib.bean;

import com.hn.library.http.BaseResponseModel;

import java.util.List;

/**
 * 作者：Mr.Xu
 * 时间：2017/12/21
 */
public class RefundListModel extends BaseResponseModel{


    /**
     * d : {"items":[{"add_time":"2017-12-20 17:33:21","goods_attr":"内存:2G 颜色:白色","goods_id":"100091","goods_img":"FvZ_eWplp4BkoKB3ZC7IrAcajMKT","goods_name":"华为手机","goods_price":"2.00","id":"9","num":"1","order_amount":"2.00","status":"1"},{"add_time":"2017-12-20 17:33:17","goods_attr":"内存:2G 颜色:红色","goods_id":"100090","goods_img":"FoPFq_kieLe-iGF7ZJDcCR0s0-oe","goods_name":"努比亚手机","goods_price":"1.00","id":"8","num":"1","order_amount":"1.00","status":"1"},{"add_time":"2017-12-20 17:33:08","goods_attr":"内存:2G 颜色:红色","goods_id":"100090","goods_img":"FoPFq_kieLe-iGF7ZJDcCR0s0-oe","goods_name":"努比亚手机","goods_price":"1.00","id":"7","num":"1","order_amount":"1.00","status":"1"},{"add_time":"2017-12-20 17:33:05","goods_attr":"内存:2G 颜色:白色","goods_id":"100091","goods_img":"FvZ_eWplp4BkoKB3ZC7IrAcajMKT","goods_name":"华为手机","goods_price":"2.00","id":"6","num":"1","order_amount":"2.00","status":"1"}],"next":1,"page":1,"pagesize":10,"pagetotal":1,"prev":1,"total":4}
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
         * items : [{"add_time":"2017-12-20 17:33:21","goods_attr":"内存:2G 颜色:白色","goods_id":"100091","goods_img":"FvZ_eWplp4BkoKB3ZC7IrAcajMKT","goods_name":"华为手机","goods_price":"2.00","id":"9","num":"1","order_amount":"2.00","status":"1"},{"add_time":"2017-12-20 17:33:17","goods_attr":"内存:2G 颜色:红色","goods_id":"100090","goods_img":"FoPFq_kieLe-iGF7ZJDcCR0s0-oe","goods_name":"努比亚手机","goods_price":"1.00","id":"8","num":"1","order_amount":"1.00","status":"1"},{"add_time":"2017-12-20 17:33:08","goods_attr":"内存:2G 颜色:红色","goods_id":"100090","goods_img":"FoPFq_kieLe-iGF7ZJDcCR0s0-oe","goods_name":"努比亚手机","goods_price":"1.00","id":"7","num":"1","order_amount":"1.00","status":"1"},{"add_time":"2017-12-20 17:33:05","goods_attr":"内存:2G 颜色:白色","goods_id":"100091","goods_img":"FvZ_eWplp4BkoKB3ZC7IrAcajMKT","goods_name":"华为手机","goods_price":"2.00","id":"6","num":"1","order_amount":"2.00","status":"1"}]
         * next : 1
         * page : 1
         * pagesize : 10
         * pagetotal : 1
         * prev : 1
         * total : 4
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
             * add_time : 2017-12-20 17:33:21
             * goods_attr : 内存:2G 颜色:白色
             * goods_id : 100091
             * goods_img : FvZ_eWplp4BkoKB3ZC7IrAcajMKT
             * goods_name : 华为手机
             * goods_price : 2.00
             * id : 9
             * num : 1
             * order_amount : 2.00
             * status : 1
             */

            private String add_time;
            private String goods_attr;
            private String goods_id;
            private String goods_img;
            private String goods_name;
            private String goods_price;
            private String id;
            private String num;
            private String order_amount;
            private String status;

            public void setAdd_time(String add_time) {
                this.add_time = add_time;
            }

            public void setGoods_attr(String goods_attr) {
                this.goods_attr = goods_attr;
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

            public void setId(String id) {
                this.id = id;
            }

            public void setNum(String num) {
                this.num = num;
            }

            public void setOrder_amount(String order_amount) {
                this.order_amount = order_amount;
            }

            public void setStatus(String status) {
                this.status = status;
            }

            public String getAdd_time() {
                return add_time;
            }

            public String getGoods_attr() {
                return goods_attr;
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

            public String getId() {
                return id;
            }

            public String getNum() {
                return num;
            }

            public String getOrder_amount() {
                return order_amount;
            }

            public String getStatus() {
                return status;
            }
        }
    }
}
