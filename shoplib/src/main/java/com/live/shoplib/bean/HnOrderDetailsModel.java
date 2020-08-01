package com.live.shoplib.bean;

import com.hn.library.http.BaseResponseModel;

import java.util.List;

/**
 * 作者：Mr.Xu
 * 时间：2017/12/11
 */
public class HnOrderDetailsModel extends BaseResponseModel {


    /**
     * d : {"items":[{"details":[{"goods_attr":"内存:2G 颜色:白色","goods_id":"100095","goods_img":"FpyWf15wHhERzVlPeYaxp7PsyjT3","goods_name":"苹果手机","goods_number":"1","goods_price":"3.00","id":"64","order_id":"78","sku_id":"4","status":"0"}],"goods_num":1,"icon":"FpyWf15wHhERzVlPeYaxp7PsyjT3","order_status":"0","pay_status":"0","shipping_status":"0","name":"第二个商铺","order_amount":"6.00","order_id":"78","order_sn":"4U20171218143651","store_id":"4"},{"details":[{"goods_attr":"内存:2G 颜色:白色","goods_id":"100095","goods_img":"FpyWf15wHhERzVlPeYaxp7PsyjT3","goods_name":"苹果手机","goods_number":"1","goods_price":"3.00","id":"64","order_id":"78","sku_id":"4","status":"0"}],"goods_num":1,"icon":"FpyWf15wHhERzVlPeYaxp7PsyjT3","order_status":"0","pay_status":"0","shipping_status":"0","name":"第一个商铺","order_amount":"9.00","order_id":"77","order_sn":"3U20171218143651","store_id":"3"},{"details":[{"goods_attr":"内存:2G 颜色:白色","goods_id":"100095","goods_img":"FpyWf15wHhERzVlPeYaxp7PsyjT3","goods_name":"苹果手机","goods_number":"1","goods_price":"3.00","id":"64","order_id":"78","sku_id":"4","status":"0"}],"goods_num":1,"icon":"FpyWf15wHhERzVlPeYaxp7PsyjT3","order_status":"0","pay_status":"0","shipping_status":"0","name":"第二个商铺","order_amount":"6.00","order_id":"76","order_sn":"4U20171218115050","store_id":"4"},{"details":[{"goods_attr":"内存:2G 颜色:白色","goods_id":"100095","goods_img":"FpyWf15wHhERzVlPeYaxp7PsyjT3","goods_name":"苹果手机","goods_number":"1","goods_price":"3.00","id":"64","order_id":"78","sku_id":"4","status":"0"}],"goods_num":1,"icon":"FpyWf15wHhERzVlPeYaxp7PsyjT3","order_status":"0","pay_status":"0","shipping_status":"0","name":"第一个商铺","order_amount":"9.00","order_id":"75","order_sn":"3U20171218115050","store_id":"3"},{"details":[{"goods_attr":"内存:2G 颜色:白色","goods_id":"100095","goods_img":"FpyWf15wHhERzVlPeYaxp7PsyjT3","goods_name":"苹果手机","goods_number":"1","goods_price":"3.00","id":"64","order_id":"78","sku_id":"4","status":"0"}],"goods_num":1,"icon":"FpyWf15wHhERzVlPeYaxp7PsyjT3","order_status":"0","pay_status":"0","shipping_status":"0","name":"第二个商铺","order_amount":"6.00","order_id":"74","order_sn":"4U20171218115049","store_id":"4"},{"details":[{"goods_attr":"内存:2G 颜色:白色","goods_id":"100095","goods_img":"FpyWf15wHhERzVlPeYaxp7PsyjT3","goods_name":"苹果手机","goods_number":"1","goods_price":"3.00","id":"64","order_id":"78","sku_id":"4","status":"0"}],"goods_num":1,"icon":"FpyWf15wHhERzVlPeYaxp7PsyjT3","order_status":"0","pay_status":"0","shipping_status":"0","name":"第一个商铺","order_amount":"9.00","order_id":"73","order_sn":"3U20171218115049","store_id":"3"},{"details":[{"goods_attr":"内存:2G 颜色:白色","goods_id":"100095","goods_img":"FpyWf15wHhERzVlPeYaxp7PsyjT3","goods_name":"苹果手机","goods_number":"1","goods_price":"3.00","id":"64","order_id":"78","sku_id":"4","status":"0"}],"goods_num":1,"icon":"FpyWf15wHhERzVlPeYaxp7PsyjT3","order_status":"0","pay_status":"0","shipping_status":"0","name":"第二个商铺","order_amount":"6.00","order_id":"72","order_sn":"4U20171218115048","store_id":"4"},{"details":[{"goods_attr":"内存:2G 颜色:白色","goods_id":"100095","goods_img":"FpyWf15wHhERzVlPeYaxp7PsyjT3","goods_name":"苹果手机","goods_number":"1","goods_price":"3.00","id":"64","order_id":"78","sku_id":"4","status":"0"}],"goods_num":1,"icon":"FpyWf15wHhERzVlPeYaxp7PsyjT3","order_status":"0","pay_status":"0","shipping_status":"0","name":"第一个商铺","order_amount":"9.00","order_id":"71","order_sn":"3U20171218115048","store_id":"3"},{"details":[{"goods_attr":"内存:2G 颜色:白色","goods_id":"100095","goods_img":"FpyWf15wHhERzVlPeYaxp7PsyjT3","goods_name":"苹果手机","goods_number":"1","goods_price":"3.00","id":"64","order_id":"78","sku_id":"4","status":"0"}],"goods_num":1,"icon":"FpyWf15wHhERzVlPeYaxp7PsyjT3","order_status":"0","pay_status":"0","shipping_status":"0","name":"第二个商铺","order_amount":"6.00","order_id":"70","order_sn":"4U20171218113306","store_id":"4"},{"details":[{"goods_attr":"内存:2G 颜色:白色","goods_id":"100095","goods_img":"FpyWf15wHhERzVlPeYaxp7PsyjT3","goods_name":"苹果手机","goods_number":"1","goods_price":"3.00","id":"64","order_id":"78","sku_id":"4","status":"0"}],"goods_num":1,"icon":"FpyWf15wHhERzVlPeYaxp7PsyjT3","order_status":"0","pay_status":"0","shipping_status":"0","name":"第一个商铺","order_amount":"9.00","order_id":"69","order_sn":"3U20171218113306","store_id":"3"}],"next":1,"page":1,"pagesize":10,"pagetotal":1,"prev":1,"total":10}
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
         * items : [{"details":[{"goods_attr":"内存:2G 颜色:白色","goods_id":"100095","goods_img":"FpyWf15wHhERzVlPeYaxp7PsyjT3","goods_name":"苹果手机","goods_number":"1","goods_price":"3.00","id":"64","order_id":"78","sku_id":"4","status":"0"}],"goods_num":1,"icon":"FpyWf15wHhERzVlPeYaxp7PsyjT3","order_status":"0","pay_status":"0","shipping_status":"0","name":"第二个商铺","order_amount":"6.00","order_id":"78","order_sn":"4U20171218143651","store_id":"4"},{"details":[{"goods_attr":"内存:2G 颜色:白色","goods_id":"100095","goods_img":"FpyWf15wHhERzVlPeYaxp7PsyjT3","goods_name":"苹果手机","goods_number":"1","goods_price":"3.00","id":"64","order_id":"78","sku_id":"4","status":"0"}],"goods_num":1,"icon":"FpyWf15wHhERzVlPeYaxp7PsyjT3","order_status":"0","pay_status":"0","shipping_status":"0","name":"第一个商铺","order_amount":"9.00","order_id":"77","order_sn":"3U20171218143651","store_id":"3"},{"details":[{"goods_attr":"内存:2G 颜色:白色","goods_id":"100095","goods_img":"FpyWf15wHhERzVlPeYaxp7PsyjT3","goods_name":"苹果手机","goods_number":"1","goods_price":"3.00","id":"64","order_id":"78","sku_id":"4","status":"0"}],"goods_num":1,"icon":"FpyWf15wHhERzVlPeYaxp7PsyjT3","order_status":"0","pay_status":"0","shipping_status":"0","name":"第二个商铺","order_amount":"6.00","order_id":"76","order_sn":"4U20171218115050","store_id":"4"},{"details":[{"goods_attr":"内存:2G 颜色:白色","goods_id":"100095","goods_img":"FpyWf15wHhERzVlPeYaxp7PsyjT3","goods_name":"苹果手机","goods_number":"1","goods_price":"3.00","id":"64","order_id":"78","sku_id":"4","status":"0"}],"goods_num":1,"icon":"FpyWf15wHhERzVlPeYaxp7PsyjT3","order_status":"0","pay_status":"0","shipping_status":"0","name":"第一个商铺","order_amount":"9.00","order_id":"75","order_sn":"3U20171218115050","store_id":"3"},{"details":[{"goods_attr":"内存:2G 颜色:白色","goods_id":"100095","goods_img":"FpyWf15wHhERzVlPeYaxp7PsyjT3","goods_name":"苹果手机","goods_number":"1","goods_price":"3.00","id":"64","order_id":"78","sku_id":"4","status":"0"}],"goods_num":1,"icon":"FpyWf15wHhERzVlPeYaxp7PsyjT3","order_status":"0","pay_status":"0","shipping_status":"0","name":"第二个商铺","order_amount":"6.00","order_id":"74","order_sn":"4U20171218115049","store_id":"4"},{"details":[{"goods_attr":"内存:2G 颜色:白色","goods_id":"100095","goods_img":"FpyWf15wHhERzVlPeYaxp7PsyjT3","goods_name":"苹果手机","goods_number":"1","goods_price":"3.00","id":"64","order_id":"78","sku_id":"4","status":"0"}],"goods_num":1,"icon":"FpyWf15wHhERzVlPeYaxp7PsyjT3","order_status":"0","pay_status":"0","shipping_status":"0","name":"第一个商铺","order_amount":"9.00","order_id":"73","order_sn":"3U20171218115049","store_id":"3"},{"details":[{"goods_attr":"内存:2G 颜色:白色","goods_id":"100095","goods_img":"FpyWf15wHhERzVlPeYaxp7PsyjT3","goods_name":"苹果手机","goods_number":"1","goods_price":"3.00","id":"64","order_id":"78","sku_id":"4","status":"0"}],"goods_num":1,"icon":"FpyWf15wHhERzVlPeYaxp7PsyjT3","order_status":"0","pay_status":"0","shipping_status":"0","name":"第二个商铺","order_amount":"6.00","order_id":"72","order_sn":"4U20171218115048","store_id":"4"},{"details":[{"goods_attr":"内存:2G 颜色:白色","goods_id":"100095","goods_img":"FpyWf15wHhERzVlPeYaxp7PsyjT3","goods_name":"苹果手机","goods_number":"1","goods_price":"3.00","id":"64","order_id":"78","sku_id":"4","status":"0"}],"goods_num":1,"icon":"FpyWf15wHhERzVlPeYaxp7PsyjT3","order_status":"0","pay_status":"0","shipping_status":"0","name":"第一个商铺","order_amount":"9.00","order_id":"71","order_sn":"3U20171218115048","store_id":"3"},{"details":[{"goods_attr":"内存:2G 颜色:白色","goods_id":"100095","goods_img":"FpyWf15wHhERzVlPeYaxp7PsyjT3","goods_name":"苹果手机","goods_number":"1","goods_price":"3.00","id":"64","order_id":"78","sku_id":"4","status":"0"}],"goods_num":1,"icon":"FpyWf15wHhERzVlPeYaxp7PsyjT3","order_status":"0","pay_status":"0","shipping_status":"0","name":"第二个商铺","order_amount":"6.00","order_id":"70","order_sn":"4U20171218113306","store_id":"4"},{"details":[{"goods_attr":"内存:2G 颜色:白色","goods_id":"100095","goods_img":"FpyWf15wHhERzVlPeYaxp7PsyjT3","goods_name":"苹果手机","goods_number":"1","goods_price":"3.00","id":"64","order_id":"78","sku_id":"4","status":"0"}],"goods_num":1,"icon":"FpyWf15wHhERzVlPeYaxp7PsyjT3","order_status":"0","pay_status":"0","shipping_status":"0","name":"第一个商铺","order_amount":"9.00","order_id":"69","order_sn":"3U20171218113306","store_id":"3"}]
         * next : 1
         * page : 1
         * pagesize : 10
         * pagetotal : 1
         * prev : 1
         * total : 10
         */

        private int next;
        private int page;
        private int pagesize;
        private int pagetotal;
        private int prev;
        private int total;
        private List<HnOrderListBean> items;

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

        public void setItems(List<HnOrderListBean> items) {
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

        public List<HnOrderListBean> getItems() {
            return items;
        }

//        public static class ItemsEntity {
//            /**
//             * details : [{"goods_attr":"内存:2G 颜色:白色","goods_id":"100095","goods_img":"FpyWf15wHhERzVlPeYaxp7PsyjT3","goods_name":"苹果手机","goods_number":"1","goods_price":"3.00","id":"64","order_id":"78","sku_id":"4","status":"0"}]
//             * goods_num : 1
//             * icon : FpyWf15wHhERzVlPeYaxp7PsyjT3
//             * order_status : 0
//             * pay_status : 0
//             * shipping_status : 0
//             * name : 第二个商铺
//             * order_amount : 6.00
//             * order_id : 78
//             * order_sn : 4U20171218143651
//             * store_id : 4
//             */
//
//            private int goods_num;
//            private String icon;
//            private int order_status;
//            private int pay_status;
//            private int shipping_status;
//            private String name;
//            private String order_amount;
//            private String order_id;
//            private String order_sn;
//            private String store_id;
//            private String store_uid;
//            private List<DetailsEntity> details;
//
//            public String getStore_uid() {
//                return store_uid;
//            }
//
//            public void setStore_uid(String store_uid) {
//                this.store_uid = store_uid;
//            }
//
//            public int getGoods_num() {
//                return goods_num;
//            }
//
//            public void setGoods_num(int goods_num) {
//                this.goods_num = goods_num;
//            }
//
//            public String getIcon() {
//                return icon;
//            }
//
//            public void setIcon(String icon) {
//                this.icon = icon;
//            }
//
//            public int getOrder_status() {
//                return order_status;
//            }
//
//            public void setOrder_status(int order_status) {
//                this.order_status = order_status;
//            }
//
//            public int getPay_status() {
//                return pay_status;
//            }
//
//            public void setPay_status(int pay_status) {
//                this.pay_status = pay_status;
//            }
//
//            public int getShipping_status() {
//                return shipping_status;
//            }
//
//            public void setShipping_status(int shipping_status) {
//                this.shipping_status = shipping_status;
//            }
//
//            public String getName() {
//                return name;
//            }
//
//            public void setName(String name) {
//                this.name = name;
//            }
//
//            public String getOrder_amount() {
//                return order_amount;
//            }
//
//            public void setOrder_amount(String order_amount) {
//                this.order_amount = order_amount;
//            }
//
//            public String getOrder_id() {
//                return order_id;
//            }
//
//            public void setOrder_id(String order_id) {
//                this.order_id = order_id;
//            }
//
//            public String getOrder_sn() {
//                return order_sn;
//            }
//
//            public void setOrder_sn(String order_sn) {
//                this.order_sn = order_sn;
//            }
//
//            public String getStore_id() {
//                return store_id;
//            }
//
//            public void setStore_id(String store_id) {
//                this.store_id = store_id;
//            }
//
//            public void setDetails(List<DetailsEntity> details) {
//                this.details = details;
//            }
//
//            public List<DetailsEntity> getDetails() {
//                return details;
//            }
//
//            public static class DetailsEntity {
//                /**
//                 * goods_attr : 内存:2G 颜色:白色
//                 * goods_id : 100095
//                 * goods_img : FpyWf15wHhERzVlPeYaxp7PsyjT3
//                 * goods_name : 苹果手机
//                 * goods_number : 1
//                 * goods_price : 3.00
//                 * id : 64
//                 * order_id : 78
//                 * sku_id : 4
//                 * status : 0
//                 */
//
//                private String goods_attr;
//                private String goods_id;
//                private String goods_img;
//                private String goods_name;
//                private String goods_number;
//                private String goods_price;
//                private String id;
//                private String order_id;
//                private String sku_id;
//                private int status;
//
//                public void setGoods_attr(String goods_attr) {
//                    this.goods_attr = goods_attr;
//                }
//
//                public void setGoods_id(String goods_id) {
//                    this.goods_id = goods_id;
//                }
//
//                public void setGoods_img(String goods_img) {
//                    this.goods_img = goods_img;
//                }
//
//                public void setGoods_name(String goods_name) {
//                    this.goods_name = goods_name;
//                }
//
//                public void setGoods_number(String goods_number) {
//                    this.goods_number = goods_number;
//                }
//
//                public void setGoods_price(String goods_price) {
//                    this.goods_price = goods_price;
//                }
//
//                public void setId(String id) {
//                    this.id = id;
//                }
//
//                public void setOrder_id(String order_id) {
//                    this.order_id = order_id;
//                }
//
//                public void setSku_id(String sku_id) {
//                    this.sku_id = sku_id;
//                }
//
//                public void setStatus(int status) {
//                    this.status = status;
//                }
//
//                public String getGoods_attr() {
//                    return goods_attr;
//                }
//
//                public String getGoods_id() {
//                    return goods_id;
//                }
//
//                public String getGoods_img() {
//                    return goods_img;
//                }
//
//                public String getGoods_name() {
//                    return goods_name;
//                }
//
//                public String getGoods_number() {
//                    return goods_number;
//                }
//
//                public String getGoods_price() {
//                    return goods_price;
//                }
//
//                public String getId() {
//                    return id;
//                }
//
//                public String getOrder_id() {
//                    return order_id;
//                }
//
//                public String getSku_id() {
//                    return sku_id;
//                }
//
//                public int getStatus() {
//                    return status;
//                }
//            }
//        }
    }
}
