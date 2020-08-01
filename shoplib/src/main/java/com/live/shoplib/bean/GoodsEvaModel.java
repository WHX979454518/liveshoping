package com.live.shoplib.bean;

import com.hn.library.http.BaseResponseModel;

import java.util.List;

/**
 * 作者：Mr.Xu
 * 时间：2017/12/22
 */
public class GoodsEvaModel extends BaseResponseModel {


    /**
     * d : {"items":[{"content":"三十岁","create_time":"1513145128","goods_spec_details":"内存:2G   颜色:白色","grade":"1","img":"","user_avatar":"http://static.greenlive.1booker.com/upload/image/20171124/1511504293608245.png","user_name":"哈哈"},{"content":"随时随地","create_time":"1513145128","goods_spec_details":"内存:2G   颜色:白色","grade":"3","img":"","user_avatar":"http://static.greenlive.1booker.com/upload/image/20171124/1511504293608245.png","user_name":"哈哈"},{"content":"水水水水","create_time":"1513145123","goods_spec_details":"内存:2G   颜色:白色","grade":"5","img":"","user_avatar":"http://static.greenlive.1booker.com/upload/image/20171124/1511504293608245.png","user_name":"哈哈"}],"next":1,"page":1,"pagesize":10,"pagetotal":1,"prev":1,"total":3}
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
         * items : [{"content":"三十岁","create_time":"1513145128","goods_spec_details":"内存:2G   颜色:白色","grade":"1","img":"","user_avatar":"http://static.greenlive.1booker.com/upload/image/20171124/1511504293608245.png","user_name":"哈哈"},{"content":"随时随地","create_time":"1513145128","goods_spec_details":"内存:2G   颜色:白色","grade":"3","img":"","user_avatar":"http://static.greenlive.1booker.com/upload/image/20171124/1511504293608245.png","user_name":"哈哈"},{"content":"水水水水","create_time":"1513145123","goods_spec_details":"内存:2G   颜色:白色","grade":"5","img":"","user_avatar":"http://static.greenlive.1booker.com/upload/image/20171124/1511504293608245.png","user_name":"哈哈"}]
         * next : 1
         * page : 1
         * pagesize : 10
         * pagetotal : 1
         * prev : 1
         * total : 3
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
             * content : 三十岁
             * create_time : 1513145128
             * goods_spec_details : 内存:2G   颜色:白色
             * grade : 1
             * img :
             * user_avatar : http://static.greenlive.1booker.com/upload/image/20171124/1511504293608245.png
             * user_name : 哈哈
             */

            private String content;
            private String create_time;
            private String goods_spec_details;
            private int grade;
            private String user_avatar;
            private String user_name;
            private List<String > img;

            public List<String> getImg() {
                return img;
            }

            public void setImg(List<String> img) {
                this.img = img;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public void setCreate_time(String create_time) {
                this.create_time = create_time;
            }

            public void setGoods_spec_details(String goods_spec_details) {
                this.goods_spec_details = goods_spec_details;
            }

            public void setGrade(int grade) {
                this.grade = grade;
            }

            public void setUser_avatar(String user_avatar) {
                this.user_avatar = user_avatar;
            }

            public void setUser_name(String user_name) {
                this.user_name = user_name;
            }

            public String getContent() {
                return content;
            }

            public String getCreate_time() {
                return create_time;
            }

            public String getGoods_spec_details() {
                return goods_spec_details;
            }

            public int getGrade() {
                return grade;
            }

            public String getUser_avatar() {
                return user_avatar;
            }

            public String getUser_name() {
                return user_name;
            }
        }
    }
}
