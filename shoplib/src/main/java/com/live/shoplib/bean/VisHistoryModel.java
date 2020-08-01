package com.live.shoplib.bean;

import com.hn.library.http.BaseResponseModel;

import java.util.List;

/**
 * Created by Administrator on 2018/4/23.
 */

public class VisHistoryModel extends BaseResponseModel {


    /**
     * d : {"users":{"items":[{"user_avatar":"http://thirdqq.qlogo.cn/qqapp/1106454033/12998FEC913A70090C089A8B8060C195/100","user_nickname":"唯**"}],"next":2,"page":1,"pagesize":1,"pagetotal":155,"prev":1,"total":155}}
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
         * users : {"items":[{"user_avatar":"http://thirdqq.qlogo.cn/qqapp/1106454033/12998FEC913A70090C089A8B8060C195/100","user_nickname":"唯**"}],"next":2,"page":1,"pagesize":1,"pagetotal":155,"prev":1,"total":155}
         */

        private UsersBean users;

        public UsersBean getUsers() {
            return users;
        }

        public void setUsers(UsersBean users) {
            this.users = users;
        }

        public static class UsersBean {
            /**
             * items : [{"user_avatar":"http://thirdqq.qlogo.cn/qqapp/1106454033/12998FEC913A70090C089A8B8060C195/100","user_nickname":"唯**"}]
             * next : 2
             * page : 1
             * pagesize : 1
             * pagetotal : 155
             * prev : 1
             * total : 155
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
                 * user_avatar : http://thirdqq.qlogo.cn/qqapp/1106454033/12998FEC913A70090C089A8B8060C195/100
                 * user_nickname : 唯**
                 */

                private String user_avatar;
                private String user_nickname;

                public String getUser_avatar() {
                    return user_avatar;
                }

                public void setUser_avatar(String user_avatar) {
                    this.user_avatar = user_avatar;
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
