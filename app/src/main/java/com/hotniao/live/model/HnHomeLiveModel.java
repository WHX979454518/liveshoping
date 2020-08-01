package com.hotniao.live.model;

import com.hn.library.http.BaseResponseModel;

import java.util.List;

/**
 * 作者：Mr.Xu
 * 时间：2017/12/12
 */
public class HnHomeLiveModel extends BaseResponseModel {


    /**
     * d : {"store":{"items":[{"anchor_id":"5","anchor_live_img":"http://q.qlogo.cn/qqapp/1106454033/E6156A70A5B0C0834C60B30E7C7C624A/100","anchor_live_title":"快捷键","anchor_local":"广东省深圳市","anchor_live_onlines":"1","anchor_is_live":"Y","store_id":"10004","table":"v广告费","user_id":"10006"}],"page":1,"pagesize":10,"pagetotal":1,"total":1,"prev":1,"next":1},"article":[{"id":"2","title":"宋黛格子学院风连衣裙马甲女针织毛衣日系连衣裙背心宽松女中长款","logo":"http://qtyc-1256019144.picgz.myqcloud.com/image/20180320/1521526765144270.png","like_num":"0","comment_num":"0","add_time":"1521526826"}]}
     */

    private DEntity d;

    public DEntity getD() {
        return d;
    }

    public void setD(DEntity d) {
        this.d = d;
    }

    public static class DEntity {
        /**
         * store : {"items":[{"anchor_id":"5","anchor_live_img":"http://q.qlogo.cn/qqapp/1106454033/E6156A70A5B0C0834C60B30E7C7C624A/100","anchor_live_title":"快捷键","anchor_local":"广东省深圳市","anchor_live_onlines":"1","anchor_is_live":"Y","store_id":"10004","table":"v广告费","user_id":"10006"}],"page":1,"pagesize":10,"pagetotal":1,"total":1,"prev":1,"next":1}
         * article : [{"id":"2","title":"宋黛格子学院风连衣裙马甲女针织毛衣日系连衣裙背心宽松女中长款","logo":"http://qtyc-1256019144.picgz.myqcloud.com/image/20180320/1521526765144270.png","like_num":"0","comment_num":"0","add_time":"1521526826"}]
         */

        private StoreEntity store;
        private List<ArticleBean> article;

        public StoreEntity getStore() {
            return store;
        }

        public void setStore(StoreEntity store) {
            this.store = store;
        }

        public List<ArticleBean> getArticle() {
            return article;
        }

        public void setArticle(List<ArticleBean> article) {
            this.article = article;
        }

        public static class StoreEntity {
            /**
             * items : [{"anchor_id":"5","anchor_live_img":"http://q.qlogo.cn/qqapp/1106454033/E6156A70A5B0C0834C60B30E7C7C624A/100","anchor_live_title":"快捷键","anchor_local":"广东省深圳市","anchor_live_onlines":"1","anchor_is_live":"Y","store_id":"10004","table":"v广告费","user_id":"10006"}]
             * page : 1
             * pagesize : 10
             * pagetotal : 1
             * total : 1
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

            public int getTotal() {
                return total;
            }

            public void setTotal(int total) {
                this.total = total;
            }

            public int getPrev() {
                return prev;
            }

            public void setPrev(int prev) {
                this.prev = prev;
            }

            public int getNext() {
                return next;
            }

            public void setNext(int next) {
                this.next = next;
            }

            public List<ItemsEntity> getItems() {
                return items;
            }

            public void setItems(List<ItemsEntity> items) {
                this.items = items;
            }

            public static class ItemsEntity {
                /**
                 * anchor_id : 5
                 * anchor_live_img : http://q.qlogo.cn/qqapp/1106454033/E6156A70A5B0C0834C60B30E7C7C624A/100
                 * anchor_live_title : 快捷键
                 * anchor_local : 广东省深圳市
                 * anchor_live_onlines : 1
                 * anchor_is_live : Y
                 * store_id : 10004
                 * table : v广告费
                 * user_id : 10006
                 */

                private String anchor_user_id;


                private String anchor_id;

                private String anchor_live_img;
                private String anchor_live_title;
                private String anchor_local;
                private String anchor_live_onlines;
                private String anchor_is_live;
                private String store_id;
                private String table;
                private String icon;
                private String province;
                private String city;
                private String district;
                private String address;
                private String store_name;
                private String user_id;
                private String total_online_goods;
                private String total_collect;

                public String getTotal_online_goods() {
                    return total_online_goods;
                }

                public void setTotal_online_goods(String total_online_goods) {
                    this.total_online_goods = total_online_goods;
                }

                public String getTotal_collect() {
                    return total_collect;
                }

                public void setTotal_collect(String total_collect) {
                    this.total_collect = total_collect;
                }

                public String getAnchor_user_id() {
                    return anchor_user_id;
                }

                public void setAnchor_user_id(String anchor_user_id) {
                    this.anchor_user_id = anchor_user_id;
                }

                public String getIcon() {
                    return icon;
                }

                public void setIcon(String icon) {
                    this.icon = icon;
                }

                public String getProvince() {
                    return province;
                }

                public void setProvince(String province) {
                    this.province = province;
                }

                public String getCity() {
                    return city;
                }

                public void setCity(String city) {
                    this.city = city;
                }

                public String getDistrict() {
                    return district;
                }

                public void setDistrict(String district) {
                    this.district = district;
                }

                public String getAddress() {
                    return address;
                }

                public void setAddress(String address) {
                    this.address = address;
                }

                public String getStore_name() {
                    return store_name;
                }

                public void setStore_name(String store_name) {
                    this.store_name = store_name;
                }

                public String getAnchor_id() {
                    return anchor_id;
                }

                public void setAnchor_id(String anchor_id) {
                    this.anchor_id = anchor_id;
                }

                public String getAnchor_live_img() {
                    return anchor_live_img;
                }

                public void setAnchor_live_img(String anchor_live_img) {
                    this.anchor_live_img = anchor_live_img;
                }

                public String getAnchor_live_title() {
                    return anchor_live_title;
                }

                public void setAnchor_live_title(String anchor_live_title) {
                    this.anchor_live_title = anchor_live_title;
                }

                public String getAnchor_local() {
                    return anchor_local;
                }

                public void setAnchor_local(String anchor_local) {
                    this.anchor_local = anchor_local;
                }

                public String getAnchor_live_onlines() {
                    return anchor_live_onlines;
                }

                public void setAnchor_live_onlines(String anchor_live_onlines) {
                    this.anchor_live_onlines = anchor_live_onlines;
                }

                public String getAnchor_is_live() {
                    return anchor_is_live;
                }

                public void setAnchor_is_live(String anchor_is_live) {
                    this.anchor_is_live = anchor_is_live;
                }

                public String getStore_id() {
                    return store_id;
                }

                public void setStore_id(String store_id) {
                    this.store_id = store_id;
                }

                public String getTable() {
                    return table;
                }

                public void setTable(String table) {
                    this.table = table;
                }

                public String getUser_id() {
                    return user_id;
                }

                public void setUser_id(String user_id) {
                    this.user_id = user_id;
                }
            }
        }

        public static class ArticleBean {
            /**
             * id : 2
             * title : 宋黛格子学院风连衣裙马甲女针织毛衣日系连衣裙背心宽松女中长款
             * logo : http://qtyc-1256019144.picgz.myqcloud.com/image/20180320/1521526765144270.png
             * like_num : 0
             * comment_num : 0
             * add_time : 1521526826
             */

            private String id;
            private String title;
            private String logo;
            private String like_num;
            private String comment_num;
            private String add_time;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getLogo() {
                return logo;
            }

            public void setLogo(String logo) {
                this.logo = logo;
            }

            public String getLike_num() {
                return like_num;
            }

            public void setLike_num(String like_num) {
                this.like_num = like_num;
            }

            public String getComment_num() {
                return comment_num;
            }

            public void setComment_num(String comment_num) {
                this.comment_num = comment_num;
            }

            public String getAdd_time() {
                return add_time;
            }

            public void setAdd_time(String add_time) {
                this.add_time = add_time;
            }
        }
    }
}
