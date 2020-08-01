package com.live.shoplib.bean;

import com.hn.library.http.BaseResponseModel;

import java.util.List;

/**
 * create by Mr.x
 * on 2018/7/3
 */

public class GoodsListItemBean extends BaseResponseModel {


    /**
     * d : {"goods":{"items":[{"goods_id":"100095","goods_img":"FpyWf15wHhERzVlPeYaxp7PsyjT3","goods_live_recommend":"0","goods_name":"苹果手机","goods_price":"3.00"}],"next":1,"page":1,"pagesize":10,"pagetotal":1,"prev":1,"total":1}}
     */

    private DBean d;

    public DBean getD() {
        return d;
    }

    public void setD(DBean d) {
        this.d = d;
    }

    public static class DBean {
        /**
         * goods : {"items":[{"goods_id":"100095","goods_img":"FpyWf15wHhERzVlPeYaxp7PsyjT3","goods_live_recommend":"0","goods_name":"苹果手机","goods_price":"3.00"}],"next":1,"page":1,"pagesize":10,"pagetotal":1,"prev":1,"total":1}
         */

        private GoodsBean goods;

        public GoodsBean getGoods() {
            return goods;
        }

        public void setGoods(GoodsBean goods) {
            this.goods = goods;
        }

        public static class GoodsBean {
            /**
             * items : [{"goods_id":"100095","goods_img":"FpyWf15wHhERzVlPeYaxp7PsyjT3","goods_live_recommend":"0","goods_name":"苹果手机","goods_price":"3.00"}]
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
            private List<ItemsBean> items;

            public int getNext() {
                return next;
            }

            public void setNext(int next) {
                this.next = next;
            }

            public int getPage() {
                return page;
            }

            public void setPage(int page) {
                this.page = page;
            }

            public int getPagesize() {
                return pagesize;
            }

            public void setPagesize(int pagesize) {
                this.pagesize = pagesize;
            }

            public int getPagetotal() {
                return pagetotal;
            }

            public void setPagetotal(int pagetotal) {
                this.pagetotal = pagetotal;
            }

            public int getPrev() {
                return prev;
            }

            public void setPrev(int prev) {
                this.prev = prev;
            }

            public int getTotal() {
                return total;
            }

            public void setTotal(int total) {
                this.total = total;
            }

            public List<ItemsBean> getItems() {
                return items;
            }

            public void setItems(List<ItemsBean> items) {
                this.items = items;
            }

            public static class ItemsBean {
                /**
                 * goods_id : 100095
                 * goods_img : FpyWf15wHhERzVlPeYaxp7PsyjT3
                 * goods_live_recommend : 0
                 * goods_name : 苹果手机
                 * goods_price : 3.00
                 */

                private String goods_id;
                private String goods_img;
                private String goods_live_recommend;
                private String goods_name;
                private String goods_price;

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

                public String getGoods_live_recommend() {
                    return goods_live_recommend;
                }

                public void setGoods_live_recommend(String goods_live_recommend) {
                    this.goods_live_recommend = goods_live_recommend;
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
            }
        }
    }
}
