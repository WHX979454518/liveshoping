package com.hotniao.live.model;

import com.hn.library.http.BaseResponseModel;

import java.util.List;

/**
 * 作者：Mr.Xu
 * 时间：2018/2/6
 */
public class BlackListModel extends BaseResponseModel{


    /**
     * d : {"blacks":{"items":[{"user_follow_total":"7","user_fans_total":"3","user_id":"10002","user_nickname":"一片冷清","user_avatar":"http://wx.qlogo.cn/mmopen/vi_32/icx0hAndY0S3Yic9t3YxaHQibr5wAOOicib0E3iczvyAfjibNLkCBuZBgDL97bTfWlEz5swJtOV8BvB78R3RPvUb97iaaA/132","is_black":"Y","user_intro":"","anchor_level":"-1"},{"user_follow_total":"15","user_fans_total":"6","user_id":"10003","user_nickname":"阳光","user_avatar":"http://boyazhibo-1255530974.picgz.myqcloud.com/image/2018/01/24/18aff1431b4640dac1a5aaba7490bb34","is_black":"Y","user_intro":"看看咯哦哦哦哦哦咯嘻嘻嘻嘻嘻嘻我啊看咯啦啦看看咯哦咯","anchor_level":"-1"}],"page":1,"pagesize":20,"pagetotal":1,"total":2,"prev":1,"next":1}}
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
         * blacks : {"items":[{"user_follow_total":"7","user_fans_total":"3","user_id":"10002","user_nickname":"一片冷清","user_avatar":"http://wx.qlogo.cn/mmopen/vi_32/icx0hAndY0S3Yic9t3YxaHQibr5wAOOicib0E3iczvyAfjibNLkCBuZBgDL97bTfWlEz5swJtOV8BvB78R3RPvUb97iaaA/132","is_black":"Y","user_intro":"","anchor_level":"-1"},{"user_follow_total":"15","user_fans_total":"6","user_id":"10003","user_nickname":"阳光","user_avatar":"http://boyazhibo-1255530974.picgz.myqcloud.com/image/2018/01/24/18aff1431b4640dac1a5aaba7490bb34","is_black":"Y","user_intro":"看看咯哦哦哦哦哦咯嘻嘻嘻嘻嘻嘻我啊看咯啦啦看看咯哦咯","anchor_level":"-1"}],"page":1,"pagesize":20,"pagetotal":1,"total":2,"prev":1,"next":1}
         */

        private BlacksEntity blacks;

        public void setBlacks(BlacksEntity blacks) {
            this.blacks = blacks;
        }

        public BlacksEntity getBlacks() {
            return blacks;
        }

        public static class BlacksEntity {
            /**
             * items : [{"user_follow_total":"7","user_fans_total":"3","user_id":"10002","user_nickname":"一片冷清","user_avatar":"http://wx.qlogo.cn/mmopen/vi_32/icx0hAndY0S3Yic9t3YxaHQibr5wAOOicib0E3iczvyAfjibNLkCBuZBgDL97bTfWlEz5swJtOV8BvB78R3RPvUb97iaaA/132","is_black":"Y","user_intro":"","anchor_level":"-1"},{"user_follow_total":"15","user_fans_total":"6","user_id":"10003","user_nickname":"阳光","user_avatar":"http://boyazhibo-1255530974.picgz.myqcloud.com/image/2018/01/24/18aff1431b4640dac1a5aaba7490bb34","is_black":"Y","user_intro":"看看咯哦哦哦哦哦咯嘻嘻嘻嘻嘻嘻我啊看咯啦啦看看咯哦咯","anchor_level":"-1"}]
             * page : 1
             * pagesize : 20
             * pagetotal : 1
             * total : 2
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

            public void setPage(int page) {
                this.page = page;
            }

            public void setPagesize(int pagesize) {
                this.pagesize = pagesize;
            }

            public void setPagetotal(int pagetotal) {
                this.pagetotal = pagetotal;
            }

            public void setTotal(int total) {
                this.total = total;
            }

            public void setPrev(int prev) {
                this.prev = prev;
            }

            public void setNext(int next) {
                this.next = next;
            }

            public void setItems(List<ItemsEntity> items) {
                this.items = items;
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

            public int getTotal() {
                return total;
            }

            public int getPrev() {
                return prev;
            }

            public int getNext() {
                return next;
            }

            public List<ItemsEntity> getItems() {
                return items;
            }

            public static class ItemsEntity {
                /**
                 * user_follow_total : 7
                 * user_fans_total : 3
                 * user_id : 10002
                 * user_nickname : 一片冷清
                 * user_avatar : http://wx.qlogo.cn/mmopen/vi_32/icx0hAndY0S3Yic9t3YxaHQibr5wAOOicib0E3iczvyAfjibNLkCBuZBgDL97bTfWlEz5swJtOV8BvB78R3RPvUb97iaaA/132
                 * is_black : Y
                 * user_intro :
                 * anchor_level : -1
                 */

                private String user_follow_total;
                private String user_fans_total;
                private String user_id;
                private String user_nickname;
                private String user_avatar;
                private String is_black;
                private String user_intro;
                private String store_id;
                private String anchor_level;

                public String getStore_id() {
                    return store_id;
                }

                public void setStore_id(String store_id) {
                    this.store_id = store_id;
                }

                public void setUser_follow_total(String user_follow_total) {
                    this.user_follow_total = user_follow_total;
                }

                public void setUser_fans_total(String user_fans_total) {
                    this.user_fans_total = user_fans_total;
                }

                public void setUser_id(String user_id) {
                    this.user_id = user_id;
                }

                public void setUser_nickname(String user_nickname) {
                    this.user_nickname = user_nickname;
                }

                public void setUser_avatar(String user_avatar) {
                    this.user_avatar = user_avatar;
                }

                public void setIs_black(String is_black) {
                    this.is_black = is_black;
                }

                public void setUser_intro(String user_intro) {
                    this.user_intro = user_intro;
                }

                public void setAnchor_level(String anchor_level) {
                    this.anchor_level = anchor_level;
                }

                public String getUser_follow_total() {
                    return user_follow_total;
                }

                public String getUser_fans_total() {
                    return user_fans_total;
                }

                public String getUser_id() {
                    return user_id;
                }

                public String getUser_nickname() {
                    return user_nickname;
                }

                public String getUser_avatar() {
                    return user_avatar;
                }

                public String getIs_black() {
                    return is_black;
                }

                public String getUser_intro() {
                    return user_intro;
                }

                public String getAnchor_level() {
                    return anchor_level;
                }
            }
        }
    }
}
