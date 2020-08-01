package com.hotniao.live.model;

import com.hn.library.http.BaseResponseModel;

import java.util.List;

/**
 * 作者：Mr.Xu
 * 时间：2017/12/18
 */
public class HnShopContriModel extends BaseResponseModel {


    /**
     * d : {"rank":{"items":[{"order_money":"测试内容6u41","is_follow":"N","is_member":"Y","order_total":"200886.00","user_avatar":"http://static.greenlive.1booker.com//upload/image/20171107/1510018645167166.png","user_id":"6","user_level":"11","user_member_expire_time":"1662608362","user_nickname":"测试"},{"order_money":"测试内容6u41","is_follow":"N","is_member":"Y","order_total":"127273.00","user_avatar":"https://fireniao-1252571077.image.myqcloud.com/image/2017/12/06/1512528544913.png","user_id":"9","user_level":"11","user_member_expire_time":"1616144995","user_nickname":"天行哈哈"},{"order_money":"测试内容6u41","is_follow":"N","is_member":"Y","order_total":"73029.00","user_avatar":"http://static.greenlive.1booker.com//upload/image/20171108/1510103783213824.png","user_id":"22","user_level":"9","user_member_expire_time":"1536204288","user_nickname":"18689462661"},{"order_money":"测试内容6u41","is_follow":"N","is_member":"Y","order_total":"34099.00","user_avatar":"http://static.greenlive.1booker.com//upload/image/20171109/1510193537484689.png","user_id":"20","user_level":"9","user_member_expire_time":"1530601805","user_nickname":"哈哈"},{"order_money":"测试内容6u41","is_follow":"Y","is_member":"N","order_total":"6666.00","user_avatar":"http://static.greenlive.1booker.com//upload/image/20171030/1509357007525991.png","user_id":"13","user_level":"11","user_member_expire_time":"1512624784","user_nickname":"花花11号"},{"order_money":"测试内容6u41","is_follow":"N","is_member":"Y","order_total":"2998.00","user_avatar":"http://static.greenlive.1booker.com//upload/image/20171107/1510041075829795.png","user_id":"26","user_level":"7","user_member_expire_time":"1525746630","user_nickname":"13723701408"},{"order_money":"测试内容6u41","is_follow":"N","is_member":"N","order_total":"1417.00","user_avatar":"https://fireniao-1252571077.image.myqcloud.com/image/2017/12/06/1512529867359.png","user_id":"12","user_level":"11","user_member_expire_time":"1512285644","user_nickname":"了咯了T恤"},{"order_money":"测试内容6u41","is_follow":"N","is_member":"N","order_total":"260.00","user_avatar":"http://tvax2.sinaimg.cn/default/images/default_avatar_female_180.gif","user_id":"222","user_level":"1","user_member_expire_time":"0","user_nickname":"jodie静_57343"},{"order_money":"测试内容6u41","is_follow":"Y","is_member":"Y","order_total":"121.00","user_avatar":"http://static.greenlive.1booker.com//upload/image/20171018/1508325236696177.png","user_id":"11","user_level":"9","user_member_expire_time":"1654658778","user_nickname":"对比艺术"},{"order_money":"测试内容6u41","is_follow":"N","is_member":"N","order_total":"100.00","user_avatar":"http://static.greenlive.1booker.com/upload/image/20171116/1510795695506972.jpg","user_id":"208","user_level":"9","user_member_expire_time":"0","user_nickname":"火鸟用户_111519208"}],"next":2,"page":1,"pagesize":10,"pagetotal":2,"prev":1,"total":15}}
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
         * rank : {"items":[{"order_money":"测试内容6u41","is_follow":"N","is_member":"Y","order_total":"200886.00","user_avatar":"http://static.greenlive.1booker.com//upload/image/20171107/1510018645167166.png","user_id":"6","user_level":"11","user_member_expire_time":"1662608362","user_nickname":"测试"},{"order_money":"测试内容6u41","is_follow":"N","is_member":"Y","order_total":"127273.00","user_avatar":"https://fireniao-1252571077.image.myqcloud.com/image/2017/12/06/1512528544913.png","user_id":"9","user_level":"11","user_member_expire_time":"1616144995","user_nickname":"天行哈哈"},{"order_money":"测试内容6u41","is_follow":"N","is_member":"Y","order_total":"73029.00","user_avatar":"http://static.greenlive.1booker.com//upload/image/20171108/1510103783213824.png","user_id":"22","user_level":"9","user_member_expire_time":"1536204288","user_nickname":"18689462661"},{"order_money":"测试内容6u41","is_follow":"N","is_member":"Y","order_total":"34099.00","user_avatar":"http://static.greenlive.1booker.com//upload/image/20171109/1510193537484689.png","user_id":"20","user_level":"9","user_member_expire_time":"1530601805","user_nickname":"哈哈"},{"order_money":"测试内容6u41","is_follow":"Y","is_member":"N","order_total":"6666.00","user_avatar":"http://static.greenlive.1booker.com//upload/image/20171030/1509357007525991.png","user_id":"13","user_level":"11","user_member_expire_time":"1512624784","user_nickname":"花花11号"},{"order_money":"测试内容6u41","is_follow":"N","is_member":"Y","order_total":"2998.00","user_avatar":"http://static.greenlive.1booker.com//upload/image/20171107/1510041075829795.png","user_id":"26","user_level":"7","user_member_expire_time":"1525746630","user_nickname":"13723701408"},{"order_money":"测试内容6u41","is_follow":"N","is_member":"N","order_total":"1417.00","user_avatar":"https://fireniao-1252571077.image.myqcloud.com/image/2017/12/06/1512529867359.png","user_id":"12","user_level":"11","user_member_expire_time":"1512285644","user_nickname":"了咯了T恤"},{"order_money":"测试内容6u41","is_follow":"N","is_member":"N","order_total":"260.00","user_avatar":"http://tvax2.sinaimg.cn/default/images/default_avatar_female_180.gif","user_id":"222","user_level":"1","user_member_expire_time":"0","user_nickname":"jodie静_57343"},{"order_money":"测试内容6u41","is_follow":"Y","is_member":"Y","order_total":"121.00","user_avatar":"http://static.greenlive.1booker.com//upload/image/20171018/1508325236696177.png","user_id":"11","user_level":"9","user_member_expire_time":"1654658778","user_nickname":"对比艺术"},{"order_money":"测试内容6u41","is_follow":"N","is_member":"N","order_total":"100.00","user_avatar":"http://static.greenlive.1booker.com/upload/image/20171116/1510795695506972.jpg","user_id":"208","user_level":"9","user_member_expire_time":"0","user_nickname":"火鸟用户_111519208"}],"next":2,"page":1,"pagesize":10,"pagetotal":2,"prev":1,"total":15}
         */

        private RankEntity rank;

        public void setRank(RankEntity rank) {
            this.rank = rank;
        }

        public RankEntity getRank() {
            return rank;
        }

        public static class RankEntity {
            /**
             * items : [{"order_money":"测试内容6u41","is_follow":"N","is_member":"Y","order_total":"200886.00","user_avatar":"http://static.greenlive.1booker.com//upload/image/20171107/1510018645167166.png","user_id":"6","user_level":"11","user_member_expire_time":"1662608362","user_nickname":"测试"},{"order_money":"测试内容6u41","is_follow":"N","is_member":"Y","order_total":"127273.00","user_avatar":"https://fireniao-1252571077.image.myqcloud.com/image/2017/12/06/1512528544913.png","user_id":"9","user_level":"11","user_member_expire_time":"1616144995","user_nickname":"天行哈哈"},{"order_money":"测试内容6u41","is_follow":"N","is_member":"Y","order_total":"73029.00","user_avatar":"http://static.greenlive.1booker.com//upload/image/20171108/1510103783213824.png","user_id":"22","user_level":"9","user_member_expire_time":"1536204288","user_nickname":"18689462661"},{"order_money":"测试内容6u41","is_follow":"N","is_member":"Y","order_total":"34099.00","user_avatar":"http://static.greenlive.1booker.com//upload/image/20171109/1510193537484689.png","user_id":"20","user_level":"9","user_member_expire_time":"1530601805","user_nickname":"哈哈"},{"order_money":"测试内容6u41","is_follow":"Y","is_member":"N","order_total":"6666.00","user_avatar":"http://static.greenlive.1booker.com//upload/image/20171030/1509357007525991.png","user_id":"13","user_level":"11","user_member_expire_time":"1512624784","user_nickname":"花花11号"},{"order_money":"测试内容6u41","is_follow":"N","is_member":"Y","order_total":"2998.00","user_avatar":"http://static.greenlive.1booker.com//upload/image/20171107/1510041075829795.png","user_id":"26","user_level":"7","user_member_expire_time":"1525746630","user_nickname":"13723701408"},{"order_money":"测试内容6u41","is_follow":"N","is_member":"N","order_total":"1417.00","user_avatar":"https://fireniao-1252571077.image.myqcloud.com/image/2017/12/06/1512529867359.png","user_id":"12","user_level":"11","user_member_expire_time":"1512285644","user_nickname":"了咯了T恤"},{"order_money":"测试内容6u41","is_follow":"N","is_member":"N","order_total":"260.00","user_avatar":"http://tvax2.sinaimg.cn/default/images/default_avatar_female_180.gif","user_id":"222","user_level":"1","user_member_expire_time":"0","user_nickname":"jodie静_57343"},{"order_money":"测试内容6u41","is_follow":"Y","is_member":"Y","order_total":"121.00","user_avatar":"http://static.greenlive.1booker.com//upload/image/20171018/1508325236696177.png","user_id":"11","user_level":"9","user_member_expire_time":"1654658778","user_nickname":"对比艺术"},{"order_money":"测试内容6u41","is_follow":"N","is_member":"N","order_total":"100.00","user_avatar":"http://static.greenlive.1booker.com/upload/image/20171116/1510795695506972.jpg","user_id":"208","user_level":"9","user_member_expire_time":"0","user_nickname":"火鸟用户_111519208"}]
             * next : 2
             * page : 1
             * pagesize : 10
             * pagetotal : 2
             * prev : 1
             * total : 15
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
                 * order_money : 测试内容6u41
                 * is_follow : N
                 * is_member : Y
                 * order_total : 200886.00
                 * user_avatar : http://static.greenlive.1booker.com//upload/image/20171107/1510018645167166.png
                 * user_id : 6
                 * user_level : 11
                 * user_member_expire_time : 1662608362
                 * user_nickname : 测试
                 */

                private String order_money;
                private String is_follow;
                private String is_member;
                private String order_total;
                private String user_avatar;
                private String user_id;
                private String user_level;
                private String user_member_expire_time;
                private String user_nickname;

                public void setOrder_money(String order_money) {
                    this.order_money = order_money;
                }

                public void setIs_follow(String is_follow) {
                    this.is_follow = is_follow;
                }

                public void setIs_member(String is_member) {
                    this.is_member = is_member;
                }

                public void setOrder_total(String order_total) {
                    this.order_total = order_total;
                }

                public void setUser_avatar(String user_avatar) {
                    this.user_avatar = user_avatar;
                }

                public void setUser_id(String user_id) {
                    this.user_id = user_id;
                }

                public void setUser_level(String user_level) {
                    this.user_level = user_level;
                }

                public void setUser_member_expire_time(String user_member_expire_time) {
                    this.user_member_expire_time = user_member_expire_time;
                }

                public void setUser_nickname(String user_nickname) {
                    this.user_nickname = user_nickname;
                }

                public String getOrder_money() {
                    return order_money;
                }

                public String getIs_follow() {
                    return is_follow;
                }

                public String getIs_member() {
                    return is_member;
                }

                public String getOrder_total() {
                    return order_total;
                }

                public String getUser_avatar() {
                    return user_avatar;
                }

                public String getUser_id() {
                    return user_id;
                }

                public String getUser_level() {
                    return user_level;
                }

                public String getUser_member_expire_time() {
                    return user_member_expire_time;
                }

                public String getUser_nickname() {
                    return user_nickname;
                }
            }
        }
    }
}
