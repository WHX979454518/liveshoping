package com.hotniao.live.model;

import java.util.List;

public class HnMyFansBean {


    /**
     * fans : {"items":[{"anchor_level":"0","is_follow":"N","user_avatar":"http://static.greenlive.1booker.com//upload/image/20171017/1508229933469682.png","user_fans_total":"0","user_follow_total":"0","user_id":"9","user_level":"0","user_nickname":"天行","user_sex":"2"}],"next":1,"page":1,"pagesize":20,"pagetotal":1,"prev":1,"total":1}
     */

    private FansBean fans;

    public FansBean getFans() {
        return fans;
    }

    public void setFans(FansBean fans) {
        this.fans = fans;
    }

    public static class FansBean {
        /**
         * items : [{"anchor_level":"0","is_follow":"N","user_avatar":"http://static.greenlive.1booker.com//upload/image/20171017/1508229933469682.png","user_fans_total":"0","user_follow_total":"0","user_id":"9","user_level":"0","user_nickname":"天行","user_sex":"2"}]
         * next : 1
         * page : 1
         * pagesize : 20
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
             * anchor_level : 0
             * is_follow : N
             * user_avatar : http://static.greenlive.1booker.com//upload/image/20171017/1508229933469682.png
             * user_fans_total : 0
             * user_follow_total : 0
             * user_id : 9
             * user_level : 0
             * user_nickname : 天行
             * user_sex : 2
             */

            private String anchor_level;
            private String is_follow;
            private String user_avatar;
            private String user_fans_total;
            private String user_follow_total;
            private String user_id;
            private String user_level;
            private String user_nickname;
            private String user_sex;
            private String user_intro;

            public String getUser_intro() {
                return user_intro;
            }

            public void setUser_intro(String user_intro) {
                this.user_intro = user_intro;
            }

            public String getAnchor_level() {
                return anchor_level;
            }

            public void setAnchor_level(String anchor_level) {
                this.anchor_level = anchor_level;
            }

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

            public String getUser_level() {
                return user_level;
            }

            public void setUser_level(String user_level) {
                this.user_level = user_level;
            }

            public String getUser_nickname() {
                return user_nickname;
            }

            public void setUser_nickname(String user_nickname) {
                this.user_nickname = user_nickname;
            }

            public String getUser_sex() {
                return user_sex;
            }

            public void setUser_sex(String user_sex) {
                this.user_sex = user_sex;
            }
        }
    }
}
