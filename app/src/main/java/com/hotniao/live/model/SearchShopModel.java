package com.hotniao.live.model;

import com.hn.library.http.BaseResponseModel;

import java.util.List;

/**
 * 作者：Mr.Xu
 * 时间：2017/12/12
 */
public class SearchShopModel extends BaseResponseModel{

    /**
     * d : {"store":{"items":[{"goods_list":[{"goods_id":"100090","goods_img":"FoPFq_kieLe-iGF7ZJDcCR0s0-oe","goods_name":"努比亚手机"}],"icon":"","id":"3","name":"第一个商铺","store_uid":"100090"},{"goods_list":[{"goods_id":"100091","goods_img":"FvZ_eWplp4BkoKB3ZC7IrAcajMKT","goods_name":"华为手机"}],"icon":"","id":"4","name":"第二个商铺","store_uid":"100091"}],"next":1,"page":1,"pagesize":10,"pagetotal":1,"prev":1,"total":2}}
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
         * store : {"items":[{"goods_list":[{"goods_id":"100090","goods_img":"FoPFq_kieLe-iGF7ZJDcCR0s0-oe","goods_name":"努比亚手机"}],"icon":"","id":"3","name":"第一个商铺","store_uid":"100090"},{"goods_list":[{"goods_id":"100091","goods_img":"FvZ_eWplp4BkoKB3ZC7IrAcajMKT","goods_name":"华为手机"}],"icon":"","id":"4","name":"第二个商铺","store_uid":"100091"}],"next":1,"page":1,"pagesize":10,"pagetotal":1,"prev":1,"total":2}
         */

        private StoreEntity store;

        public void setStore(StoreEntity store) {
            this.store = store;
        }

        public StoreEntity getStore() {
            return store;
        }

        public static class StoreEntity {
            /**
             * items : [{"goods_list":[{"goods_id":"100090","goods_img":"FoPFq_kieLe-iGF7ZJDcCR0s0-oe","goods_name":"努比亚手机"}],"icon":"","id":"3","name":"第一个商铺","store_uid":"100090"},{"goods_list":[{"goods_id":"100091","goods_img":"FvZ_eWplp4BkoKB3ZC7IrAcajMKT","goods_name":"华为手机"}],"icon":"","id":"4","name":"第二个商铺","store_uid":"100091"}]
             * next : 1
             * page : 1
             * pagesize : 10
             * pagetotal : 1
             * prev : 1
             * total : 2
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
                 * goods_list : [{"goods_id":"100090","goods_img":"FoPFq_kieLe-iGF7ZJDcCR0s0-oe","goods_name":"努比亚手机"}]
                 * icon :
                 * id : 3
                 * name : 第一个商铺
                 * store_uid : 100090
                 */

                private String icon;
                private String id;
                private String name;
                private String store_uid;
                private String   anchor_level;
                private List<GoodsListEntity> goods_list;

                public String getAnchor_level() {
                    return anchor_level;
                }

                public void setAnchor_level(String anchor_level) {
                    this.anchor_level = anchor_level;
                }

                public void setIcon(String icon) {
                    this.icon = icon;
                }

                public void setId(String id) {
                    this.id = id;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public void setStore_uid(String store_uid) {
                    this.store_uid = store_uid;
                }

                public void setGoods_list(List<GoodsListEntity> goods_list) {
                    this.goods_list = goods_list;
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

                public String getStore_uid() {
                    return store_uid;
                }

                public List<GoodsListEntity> getGoods_list() {
                    return goods_list;
                }

                public static class GoodsListEntity {
                    /**
                     * goods_id : 100090
                     * goods_img : FoPFq_kieLe-iGF7ZJDcCR0s0-oe
                     * goods_name : 努比亚手机
                     */

                    private String goods_id;
                    private String goods_img;
                    private String goods_name;

                    public void setGoods_id(String goods_id) {
                        this.goods_id = goods_id;
                    }

                    public void setGoods_img(String goods_img) {
                        this.goods_img = goods_img;
                    }

                    public void setGoods_name(String goods_name) {
                        this.goods_name = goods_name;
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
                }
            }
        }
    }
}