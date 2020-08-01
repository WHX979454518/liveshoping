package com.hotniao.live.model;

import com.hn.library.http.BaseResponseModel;

import java.util.List;

/**
 * Copyright (C) 2016,深圳市红鸟网络科技股份有限公司 All rights reserved.
 * 类描述：主页面
 * 创建人：刘勇
 * 创建时间：2017/2/22 16:22
 * 修改人：刘勇
 * 修改时间：2017/2/22 16:34
 * 修改备注：
 * Version:  1.0.0
 */

public class HnSearchUserModel extends BaseResponseModel {


    /**
     * d : {"user":{"items":[{"is_follow":"N","user_avatar":"http://static.greenlive.1booker.com//upload/image/20171031/1509431484387323.png","user_fans_total":"13","user_follow_total":"16","user_id":"21","user_nickname":"189887935605"},{"is_follow":"N","user_avatar":"http://static.greenlive.1booker.com//upload/image/20171018/1508325236696177.png","user_fans_total":"12","user_follow_total":"6","user_id":"11","user_nickname":"对比艺术"},{"is_follow":"N","user_avatar":"https://fireniao-1252571077.image.myqcloud.com/image/2017/12/06/2d89862c52a1c1de4c5539365788940d","user_fans_total":"11","user_follow_total":"10","user_id":"10","user_nickname":"刘测试"},{"is_follow":"N","user_avatar":"http://static.greenlive.1booker.com//upload/image/20171030/1509357007525991.png","user_fans_total":"9","user_follow_total":"13","user_id":"13","user_nickname":"花花11号"},{"is_follow":"N","user_avatar":"http://static.greenlive.1booker.com/upload/image/20171116/1510800530563106.png","user_fans_total":"8","user_follow_total":"6","user_id":"27","user_nickname":"15274717642"},{"is_follow":"N","user_avatar":"https://fireniao-1252571077.image.myqcloud.com/image/2017/12/06/1512529867359.png","user_fans_total":"7","user_follow_total":"4","user_id":"12","user_nickname":"了咯了T恤"},{"is_follow":"N","user_avatar":"http://static.greenlive.1booker.com//upload/image/20171108/1510103783213824.png","user_fans_total":"6","user_follow_total":"14","user_id":"22","user_nickname":"18689462661"},{"is_follow":"N","user_avatar":"http://static.greenlive.1booker.com//upload/image/20171109/1510194892628099.png","user_fans_total":"6","user_follow_total":"3","user_id":"29","user_nickname":"137604081731"},{"is_follow":"N","user_avatar":"http://static.greenlive.1booker.com/upload/image/20171116/1510795580561635.jpg","user_fans_total":"5","user_follow_total":"2","user_id":"209","user_nickname":"火鸟用户_111519209"},{"is_follow":"N","user_avatar":"http://static.greenlive.1booker.com/upload/image/20171116/1510799525929744.png","user_fans_total":"4","user_follow_total":"0","user_id":"212","user_nickname":"火鸟用户_111519212"}],"next":2,"page":1,"pagesize":10,"pagetotal":10,"prev":1,"total":99}}
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
         * user : {"items":[{"is_follow":"N","user_avatar":"http://static.greenlive.1booker.com//upload/image/20171031/1509431484387323.png","user_fans_total":"13","user_follow_total":"16","user_id":"21","user_nickname":"189887935605"},{"is_follow":"N","user_avatar":"http://static.greenlive.1booker.com//upload/image/20171018/1508325236696177.png","user_fans_total":"12","user_follow_total":"6","user_id":"11","user_nickname":"对比艺术"},{"is_follow":"N","user_avatar":"https://fireniao-1252571077.image.myqcloud.com/image/2017/12/06/2d89862c52a1c1de4c5539365788940d","user_fans_total":"11","user_follow_total":"10","user_id":"10","user_nickname":"刘测试"},{"is_follow":"N","user_avatar":"http://static.greenlive.1booker.com//upload/image/20171030/1509357007525991.png","user_fans_total":"9","user_follow_total":"13","user_id":"13","user_nickname":"花花11号"},{"is_follow":"N","user_avatar":"http://static.greenlive.1booker.com/upload/image/20171116/1510800530563106.png","user_fans_total":"8","user_follow_total":"6","user_id":"27","user_nickname":"15274717642"},{"is_follow":"N","user_avatar":"https://fireniao-1252571077.image.myqcloud.com/image/2017/12/06/1512529867359.png","user_fans_total":"7","user_follow_total":"4","user_id":"12","user_nickname":"了咯了T恤"},{"is_follow":"N","user_avatar":"http://static.greenlive.1booker.com//upload/image/20171108/1510103783213824.png","user_fans_total":"6","user_follow_total":"14","user_id":"22","user_nickname":"18689462661"},{"is_follow":"N","user_avatar":"http://static.greenlive.1booker.com//upload/image/20171109/1510194892628099.png","user_fans_total":"6","user_follow_total":"3","user_id":"29","user_nickname":"137604081731"},{"is_follow":"N","user_avatar":"http://static.greenlive.1booker.com/upload/image/20171116/1510795580561635.jpg","user_fans_total":"5","user_follow_total":"2","user_id":"209","user_nickname":"火鸟用户_111519209"},{"is_follow":"N","user_avatar":"http://static.greenlive.1booker.com/upload/image/20171116/1510799525929744.png","user_fans_total":"4","user_follow_total":"0","user_id":"212","user_nickname":"火鸟用户_111519212"}],"next":2,"page":1,"pagesize":10,"pagetotal":10,"prev":1,"total":99}
         */

        private UserBean user;

        public UserBean getUser() {
            return user;
        }

        public void setUser(UserBean user) {
            this.user = user;
        }

        public static class UserBean {
            /**
             * items : [{"is_follow":"N","user_avatar":"http://static.greenlive.1booker.com//upload/image/20171031/1509431484387323.png","user_fans_total":"13","user_follow_total":"16","user_id":"21","user_nickname":"189887935605"},{"is_follow":"N","user_avatar":"http://static.greenlive.1booker.com//upload/image/20171018/1508325236696177.png","user_fans_total":"12","user_follow_total":"6","user_id":"11","user_nickname":"对比艺术"},{"is_follow":"N","user_avatar":"https://fireniao-1252571077.image.myqcloud.com/image/2017/12/06/2d89862c52a1c1de4c5539365788940d","user_fans_total":"11","user_follow_total":"10","user_id":"10","user_nickname":"刘测试"},{"is_follow":"N","user_avatar":"http://static.greenlive.1booker.com//upload/image/20171030/1509357007525991.png","user_fans_total":"9","user_follow_total":"13","user_id":"13","user_nickname":"花花11号"},{"is_follow":"N","user_avatar":"http://static.greenlive.1booker.com/upload/image/20171116/1510800530563106.png","user_fans_total":"8","user_follow_total":"6","user_id":"27","user_nickname":"15274717642"},{"is_follow":"N","user_avatar":"https://fireniao-1252571077.image.myqcloud.com/image/2017/12/06/1512529867359.png","user_fans_total":"7","user_follow_total":"4","user_id":"12","user_nickname":"了咯了T恤"},{"is_follow":"N","user_avatar":"http://static.greenlive.1booker.com//upload/image/20171108/1510103783213824.png","user_fans_total":"6","user_follow_total":"14","user_id":"22","user_nickname":"18689462661"},{"is_follow":"N","user_avatar":"http://static.greenlive.1booker.com//upload/image/20171109/1510194892628099.png","user_fans_total":"6","user_follow_total":"3","user_id":"29","user_nickname":"137604081731"},{"is_follow":"N","user_avatar":"http://static.greenlive.1booker.com/upload/image/20171116/1510795580561635.jpg","user_fans_total":"5","user_follow_total":"2","user_id":"209","user_nickname":"火鸟用户_111519209"},{"is_follow":"N","user_avatar":"http://static.greenlive.1booker.com/upload/image/20171116/1510799525929744.png","user_fans_total":"4","user_follow_total":"0","user_id":"212","user_nickname":"火鸟用户_111519212"}]
             * next : 2
             * page : 1
             * pagesize : 10
             * pagetotal : 10
             * prev : 1
             * total : 99
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
                 * is_follow : N
                 * user_avatar : http://static.greenlive.1booker.com//upload/image/20171031/1509431484387323.png
                 * user_fans_total : 13
                 * user_follow_total : 16
                 * user_id : 21
                 * user_nickname : 189887935605
                 */

                private String is_follow;
                private String user_avatar;
                private String user_fans_total;
                private String user_follow_total;
                private String user_id;
                private String user_nickname;

                public String getIs_follow() {
                    return is_follow;
                }

                public void setIs_follow(String is_follow) {
                    this.is_follow = is_follow;
                }

                public String getUser_avatar() {
                    return user_avatar;
                }

                public void setUser_avatar(String user_avatar) {
                    this.user_avatar = user_avatar;
                }

                public String getUser_fans_total() {
                    return user_fans_total;
                }

                public void setUser_fans_total(String user_fans_total) {
                    this.user_fans_total = user_fans_total;
                }

                public String getUser_follow_total() {
                    return user_follow_total;
                }

                public void setUser_follow_total(String user_follow_total) {
                    this.user_follow_total = user_follow_total;
                }

                public String getUser_id() {
                    return user_id;
                }

                public void setUser_id(String user_id) {
                    this.user_id = user_id;
                }

                public String getUser_nickname() {
                    return user_nickname;
                }

                public void setUser_nickname(String user_nickname) {
                    this.user_nickname = user_nickname;
                }
            }
        }
    }
}
