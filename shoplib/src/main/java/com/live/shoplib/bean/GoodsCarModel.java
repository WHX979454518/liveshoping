package com.live.shoplib.bean;

import com.hn.library.http.BaseResponseModel;

import java.util.List;

/**
 * 作者：Mr.Xu
 * 时间：2017/12/26
 */
public class GoodsCarModel extends BaseResponseModel {


    /**
     * d : {"failure":[{"cart_id":"2","goods_id":"100090","goods_img":"FoPFq_kieLe-iGF7ZJDcCR0s0-oe","goods_name":"努比亚手机","goods_spec_details":"颜色:黑色;内核:2G","price":"3.00"},{"cart_id":"4","goods_id":"100090","goods_img":"FoPFq_kieLe-iGF7ZJDcCR0s0-oe","goods_name":"努比亚手机","goods_spec_details":"颜色:黑色;内核:4G","price":"4.00"},{"cart_id":"3","goods_id":"100090","goods_img":"FoPFq_kieLe-iGF7ZJDcCR0s0-oe","goods_name":"努比亚手机","goods_spec_details":"颜色:白色;内核:2G","price":"3.00"}],"items":[{"icon":"测试内容e259","list":[{"cart_id":"5","goods_id":"100091","goods_img":"FvZ_eWplp4BkoKB3ZC7IrAcajMKT","goods_name":"华为手机","goods_spec_details":"颜色:黑色;内核:4G","num":"测试内容t4fq","price":"3.00"}],"storeId":"1","storeName":"第一个商铺"},{"icon":"测试内容e259","list":[{"cart_id":"5","goods_id":"100091","goods_img":"FvZ_eWplp4BkoKB3ZC7IrAcajMKT","goods_name":"华为手机","goods_spec_details":"颜色:黑色;内核:4G","num":"测试内容t4fq","price":"3.00"}],"storeId":"2","storeName":"第二个商铺"}],"next":1,"page":1,"pagesize":10,"pagetotal":1,"prev":1,"total":5}
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
         * failure : [{"cart_id":"2","goods_id":"100090","goods_img":"FoPFq_kieLe-iGF7ZJDcCR0s0-oe","goods_name":"努比亚手机","goods_spec_details":"颜色:黑色;内核:2G","price":"3.00"},{"cart_id":"4","goods_id":"100090","goods_img":"FoPFq_kieLe-iGF7ZJDcCR0s0-oe","goods_name":"努比亚手机","goods_spec_details":"颜色:黑色;内核:4G","price":"4.00"},{"cart_id":"3","goods_id":"100090","goods_img":"FoPFq_kieLe-iGF7ZJDcCR0s0-oe","goods_name":"努比亚手机","goods_spec_details":"颜色:白色;内核:2G","price":"3.00"}]
         * items : [{"icon":"测试内容e259","list":[{"cart_id":"5","goods_id":"100091","goods_img":"FvZ_eWplp4BkoKB3ZC7IrAcajMKT","goods_name":"华为手机","goods_spec_details":"颜色:黑色;内核:4G","num":"测试内容t4fq","price":"3.00"}],"storeId":"1","storeName":"第一个商铺"},{"icon":"测试内容e259","list":[{"cart_id":"5","goods_id":"100091","goods_img":"FvZ_eWplp4BkoKB3ZC7IrAcajMKT","goods_name":"华为手机","goods_spec_details":"颜色:黑色;内核:4G","num":"测试内容t4fq","price":"3.00"}],"storeId":"2","storeName":"第二个商铺"}]
         * next : 1
         * page : 1
         * pagesize : 10
         * pagetotal : 1
         * prev : 1
         * total : 5
         */

        private int next;
        private int page;
        private int pagesize;
        private int pagetotal;
        private int prev;
        private int total;
        private List<GoodsCarItemBean> failure;
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

        public void setFailure(List<GoodsCarItemBean> failure) {
            this.failure = failure;
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

        public List<GoodsCarItemBean> getFailure() {
            return failure;
        }

        public List<ItemsEntity> getItems() {
            return items;
        }


        public static class ItemsEntity {
            /**
             * icon : 测试内容e259
             * list : [{"cart_id":"5","goods_id":"100091","goods_img":"FvZ_eWplp4BkoKB3ZC7IrAcajMKT","goods_name":"华为手机","goods_spec_details":"颜色:黑色;内核:4G","num":"测试内容t4fq","price":"3.00"}]
             * storeId : 1
             * storeName : 第一个商铺
             */

            private String icon;
            private String storeId;
            private String store_uid;
            private String storeName;
            private List<GoodsCarItemBean> list;

            public String getStore_uid() {
                return store_uid;
            }

            public void setStore_uid(String store_uid) {
                this.store_uid = store_uid;
            }

            public void setIcon(String icon) {
                this.icon = icon;
            }

            public void setStoreId(String storeId) {
                this.storeId = storeId;
            }

            public void setStoreName(String storeName) {
                this.storeName = storeName;
            }

            public void setList(List<GoodsCarItemBean> list) {
                this.list = list;
            }

            public String getIcon() {
                return icon;
            }

            public String getStoreId() {
                return storeId;
            }

            public String getStoreName() {
                return storeName;
            }

            public List<GoodsCarItemBean> getList() {
                return list;
            }

        }
    }
}
