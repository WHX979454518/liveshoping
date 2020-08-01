package com.hotniao.live.model;

import com.hn.library.http.BaseResponseModel;

import java.util.List;

/**
 * 作者：Mr.Xu
 * 时间：2018/2/5
 */
public class RecommModel extends BaseResponseModel{


    /**
     * d : {"items":[{"goods_list":[{"goods_id":"2","goods_max_price":"0.00","goods_name":"商品1","goods_price":"1.00"}],"img":"测试内容k1th","user_id":"测试内容8f42"}],"next":1,"page":1,"pagesize":10,"pagetotal":1,"prev":1,"total":6}
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
         * items : [{"goods_list":[{"goods_id":"2","goods_max_price":"0.00","goods_name":"商品1","goods_price":"1.00"}],"img":"测试内容k1th","user_id":"测试内容8f42"}]
         * next : 1
         * page : 1
         * pagesize : 10
         * pagetotal : 1
         * prev : 1
         * total : 6
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
             * goods_list : [{"goods_id":"2","goods_max_price":"0.00","goods_name":"商品1","goods_price":"1.00"}]
             * img : 测试内容k1th
             * user_id : 测试内容8f42
             */

            private String img;
            private String icon;
            private String name;
            private String intro;
            private String store_id;
            private String  total_collect;
            private String  anchor_level;
            private String  total_order;
            private String   city;
            private String user_id;
            private String anchor_is_live;
            private List<GoodsListEntity> goods_list;

            public String getAnchor_level() {
                return anchor_level;
            }

            public void setAnchor_level(String anchor_level) {
                this.anchor_level = anchor_level;
            }

            public String getIcon() {
                return icon;
            }

            public void setIcon(String icon) {
                this.icon = icon;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getIntro() {
                return intro;
            }

            public void setIntro(String intro) {
                this.intro = intro;
            }

            public String getStore_id() {
                return store_id;
            }

            public void setStore_id(String store_id) {
                this.store_id = store_id;
            }

            public String getTotal_collect() {
                return total_collect;
            }

            public void setTotal_collect(String total_collect) {
                this.total_collect = total_collect;
            }

            public String getTotal_order() {
                return total_order;
            }

            public void setTotal_order(String total_order) {
                this.total_order = total_order;
            }

            public String getCity() {
                return city;
            }

            public void setCity(String city) {
                this.city = city;
            }

            public String getAnchor_is_live() {
                return anchor_is_live;
            }

            public void setAnchor_is_live(String anchor_is_live) {
                this.anchor_is_live = anchor_is_live;
            }

            public void setImg(String img) {
                this.img = img;
            }

            public void setUser_id(String user_id) {
                this.user_id = user_id;
            }

            public void setGoods_list(List<GoodsListEntity> goods_list) {
                this.goods_list = goods_list;
            }

            public String getImg() {
                return img;
            }

            public String getUser_id() {
                return user_id;
            }

            public List<GoodsListEntity> getGoods_list() {
                return goods_list;
            }

            public static class GoodsListEntity {
                /**
                 * goods_id : 2
                 * goods_max_price : 0.00
                 * goods_name : 商品1
                 * goods_price : 1.00
                 */

                private String goods_id;
                private String goods_max_price;
                private String goods_name;
                private String goods_price;
                private String goods_img;

                public String getGoods_img() {
                    return goods_img;
                }

                public void setGoods_img(String goods_img) {
                    this.goods_img = goods_img;
                }

                public void setGoods_id(String goods_id) {
                    this.goods_id = goods_id;
                }

                public void setGoods_max_price(String goods_max_price) {
                    this.goods_max_price = goods_max_price;
                }

                public void setGoods_name(String goods_name) {
                    this.goods_name = goods_name;
                }

                public void setGoods_price(String goods_price) {
                    this.goods_price = goods_price;
                }

                public String getGoods_id() {
                    return goods_id;
                }

                public String getGoods_max_price() {
                    return goods_max_price;
                }

                public String getGoods_name() {
                    return goods_name;
                }

                public String getGoods_price() {
                    return goods_price;
                }
            }
        }
    }
}
