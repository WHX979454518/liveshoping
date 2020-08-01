package com.live.shoplib.bean;

import com.hn.library.http.BaseResponseModel;

import java.util.List;

/**
 * create by Mr.x
 * on 2018/6/29
 */

public class ShopItemBean extends BaseResponseModel {


    /**
     * d : {"goods":{"items":[{"goods_id":"7","goods_img":"https://redbird-zbds-1252571077.image.myqcloud.com/image/2018/06/07/1528359697393.png","goods_live_recommend":"1","goods_name":"海藻面膜","goods_price":"49.00"},{"goods_id":"16","goods_img":"http://redbird-zbds-1252571077.cos.ap-guangzhou.myqcloud.com/14cd896903cb29d95719711f2f8b5082.png","goods_live_recommend":"0","goods_name":"试试的4","goods_price":"100.00"},{"goods_id":"15","goods_img":"http://redbird-zbds-1252571077.image.myqcloud.com/image/20180619/1529402352369734.jpg","goods_live_recommend":"0","goods_name":"试试的11","goods_price":"50.00"},{"goods_id":"12","goods_img":"https://redbird-zbds-1252571077.image.myqcloud.com/image/2018/06/11/1528698714724.png","goods_live_recommend":"1","goods_name":"y一张图片","goods_price":"30.00"},{"goods_id":"13","goods_img":"https://redbird-zbds-1252571077.image.myqcloud.com/image/2018/06/11/1528699084753.png","goods_live_recommend":"0","goods_name":"4张图片","goods_price":"50.00"},{"goods_id":"19","goods_img":"http://redbird-zbds-1252571077.cos.ap-guangzhou.myqcloud.com/d9916149cb0bd27c9959376107a4b122.png","goods_live_recommend":"0","goods_name":"小龙虾","goods_price":"125.00"},{"goods_id":"18","goods_img":"https://redbird-zbds-1252571077.image.myqcloud.com/image/2018/06/21/1529546597606.png","goods_live_recommend":"1","goods_name":"那个意思","goods_price":"79.00"},{"goods_id":"17","goods_img":"https://redbird-zbds-1252571077.image.myqcloud.com/image/2018/06/19/1529403454116.png","goods_live_recommend":"0","goods_name":"一切一下","goods_price":"56.00"},{"goods_id":"11","goods_img":"http://redbird-zbds-1252571077.cos.ap-guangzhou.myqcloud.com/04e1cf353b2a135fcfab1a2debb90b55.png","goods_live_recommend":"0","goods_name":"lanna足贴效果偷偷告诉你一次我要给自己买银色五金制品协会理事","goods_price":"25.00"}],"next":1,"page":1,"pagesize":10,"pagetotal":1,"prev":1,"total":9}}
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
         * goods : {"items":[{"goods_id":"7","goods_img":"https://redbird-zbds-1252571077.image.myqcloud.com/image/2018/06/07/1528359697393.png","goods_live_recommend":"1","goods_name":"海藻面膜","goods_price":"49.00"},{"goods_id":"16","goods_img":"http://redbird-zbds-1252571077.cos.ap-guangzhou.myqcloud.com/14cd896903cb29d95719711f2f8b5082.png","goods_live_recommend":"0","goods_name":"试试的4","goods_price":"100.00"},{"goods_id":"15","goods_img":"http://redbird-zbds-1252571077.image.myqcloud.com/image/20180619/1529402352369734.jpg","goods_live_recommend":"0","goods_name":"试试的11","goods_price":"50.00"},{"goods_id":"12","goods_img":"https://redbird-zbds-1252571077.image.myqcloud.com/image/2018/06/11/1528698714724.png","goods_live_recommend":"1","goods_name":"y一张图片","goods_price":"30.00"},{"goods_id":"13","goods_img":"https://redbird-zbds-1252571077.image.myqcloud.com/image/2018/06/11/1528699084753.png","goods_live_recommend":"0","goods_name":"4张图片","goods_price":"50.00"},{"goods_id":"19","goods_img":"http://redbird-zbds-1252571077.cos.ap-guangzhou.myqcloud.com/d9916149cb0bd27c9959376107a4b122.png","goods_live_recommend":"0","goods_name":"小龙虾","goods_price":"125.00"},{"goods_id":"18","goods_img":"https://redbird-zbds-1252571077.image.myqcloud.com/image/2018/06/21/1529546597606.png","goods_live_recommend":"1","goods_name":"那个意思","goods_price":"79.00"},{"goods_id":"17","goods_img":"https://redbird-zbds-1252571077.image.myqcloud.com/image/2018/06/19/1529403454116.png","goods_live_recommend":"0","goods_name":"一切一下","goods_price":"56.00"},{"goods_id":"11","goods_img":"http://redbird-zbds-1252571077.cos.ap-guangzhou.myqcloud.com/04e1cf353b2a135fcfab1a2debb90b55.png","goods_live_recommend":"0","goods_name":"lanna足贴效果偷偷告诉你一次我要给自己买银色五金制品协会理事","goods_price":"25.00"}],"next":1,"page":1,"pagesize":10,"pagetotal":1,"prev":1,"total":9}
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
             * items : [{"goods_id":"7","goods_img":"https://redbird-zbds-1252571077.image.myqcloud.com/image/2018/06/07/1528359697393.png","goods_live_recommend":"1","goods_name":"海藻面膜","goods_price":"49.00"},{"goods_id":"16","goods_img":"http://redbird-zbds-1252571077.cos.ap-guangzhou.myqcloud.com/14cd896903cb29d95719711f2f8b5082.png","goods_live_recommend":"0","goods_name":"试试的4","goods_price":"100.00"},{"goods_id":"15","goods_img":"http://redbird-zbds-1252571077.image.myqcloud.com/image/20180619/1529402352369734.jpg","goods_live_recommend":"0","goods_name":"试试的11","goods_price":"50.00"},{"goods_id":"12","goods_img":"https://redbird-zbds-1252571077.image.myqcloud.com/image/2018/06/11/1528698714724.png","goods_live_recommend":"1","goods_name":"y一张图片","goods_price":"30.00"},{"goods_id":"13","goods_img":"https://redbird-zbds-1252571077.image.myqcloud.com/image/2018/06/11/1528699084753.png","goods_live_recommend":"0","goods_name":"4张图片","goods_price":"50.00"},{"goods_id":"19","goods_img":"http://redbird-zbds-1252571077.cos.ap-guangzhou.myqcloud.com/d9916149cb0bd27c9959376107a4b122.png","goods_live_recommend":"0","goods_name":"小龙虾","goods_price":"125.00"},{"goods_id":"18","goods_img":"https://redbird-zbds-1252571077.image.myqcloud.com/image/2018/06/21/1529546597606.png","goods_live_recommend":"1","goods_name":"那个意思","goods_price":"79.00"},{"goods_id":"17","goods_img":"https://redbird-zbds-1252571077.image.myqcloud.com/image/2018/06/19/1529403454116.png","goods_live_recommend":"0","goods_name":"一切一下","goods_price":"56.00"},{"goods_id":"11","goods_img":"http://redbird-zbds-1252571077.cos.ap-guangzhou.myqcloud.com/04e1cf353b2a135fcfab1a2debb90b55.png","goods_live_recommend":"0","goods_name":"lanna足贴效果偷偷告诉你一次我要给自己买银色五金制品协会理事","goods_price":"25.00"}]
             * next : 1
             * page : 1
             * pagesize : 10
             * pagetotal : 1
             * prev : 1
             * total : 9
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
                 * goods_id : 7
                 * goods_img : https://redbird-zbds-1252571077.image.myqcloud.com/image/2018/06/07/1528359697393.png
                 * goods_live_recommend : 1
                 * goods_name : 海藻面膜
                 * goods_price : 49.00
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
