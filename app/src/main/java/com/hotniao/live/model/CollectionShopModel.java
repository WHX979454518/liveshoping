package com.hotniao.live.model;

import com.hn.library.http.BaseResponseModel;

import java.util.List;

/**
 * 作者：Mr.Xu
 * 时间：2018/1/5
 */
public class CollectionShopModel extends BaseResponseModel {

    /**
     * d : {"items":[{"goods":[{"goods_img":"http://dev.static.fireniao.com/image/20171222/1513933350937476.png"}],"icon":"FvZ_eWplp4BkoKB3ZC7IrAcajMKT","id":"4","name":"第二个商铺"}],"next":1,"page":1,"pagesize":10,"pagetotal":1,"prev":1,"total":1}
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
         * items : [{"goods":[{"goods_img":"http://dev.static.fireniao.com/image/20171222/1513933350937476.png"}],"icon":"FvZ_eWplp4BkoKB3ZC7IrAcajMKT","id":"4","name":"第二个商铺"}]
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
             * goods : [{"goods_img":"http://dev.static.fireniao.com/image/20171222/1513933350937476.png"}]
             * icon : FvZ_eWplp4BkoKB3ZC7IrAcajMKT
             * id : 4
             * name : 第二个商铺
             */

            private String icon;
            private String id;
            private String name;
            private String store_uid;
            private List<GoodsEntity> goods;

            public String getStore_uid() {
                return store_uid;
            }

            public void setStore_uid(String store_uid) {
                this.store_uid = store_uid;
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

            public void setGoods(List<GoodsEntity> goods) {
                this.goods = goods;
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

            public List<GoodsEntity> getGoods() {
                return goods;
            }

            public static class GoodsEntity {
                /**
                 * goods_img : http://dev.static.fireniao.com/image/20171222/1513933350937476.png
                 */

                private String goods_img;

                public void setGoods_img(String goods_img) {
                    this.goods_img = goods_img;
                }

                public String getGoods_img() {
                    return goods_img;
                }
            }
        }
    }
}
