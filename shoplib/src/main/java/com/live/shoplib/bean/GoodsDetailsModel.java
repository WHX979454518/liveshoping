package com.live.shoplib.bean;

import com.hn.library.http.BaseResponseModel;

import java.util.List;

/**
 * 作者：Mr.Xu
 * 时间：2017/12/21
 */
public class GoodsDetailsModel extends BaseResponseModel {


    /**
     * d : {"comment":{"attr":"内存:2G 颜色:红色","content":"随时随地","create_time":"1513933938","grade":"3","img":["图片地址"],"user_avatar":"http://static.greenlive.1booker.com/upload/image/20171124/1511504293608245.png","user_name":"哈哈"},"dialogue":{"charId":"测试内容4h57","id":"测试内容67kq","name":"测试内容86u7","store_uid":"测试内容7j12"},"goods":{"column_id":"80","goods_attr":["材质成分:聚酯纤维100%","风格:街头","裙长:中长裙"],"goods_create_time":"0","goods_detail":"<p><img src=\" http://live.timierhouse.com/FpyWf15wHhERzVlPeYaxp7PsyjT3\" style=\"max-width:100%;\"><br><\/p><p><br><\/p>","goods_id":"100095","goods_img":"FpyWf15wHhERzVlPeYaxp7PsyjT3","goods_imgs":"FoPFq_kieLe-iGF7ZJDcCR0s0-oe,FoPFq_kieLe-iGF7ZJDcCR0s0-oe","goods_is_delete":"N","goods_live_recommend":"1","goods_max_price":"3.00","goods_name":"苹果手机","goods_price":"3.00","goods_promise":"七天无理由退货","goods_sales":"0","goods_sn":"3","goods_state":"1","goods_stock":"0","goods_update_time":"0","shop_fee":"10","store_id":"4","total_collect":"0","user_id":"264"},"grade":{"bad":1,"general":1,"good":1,"total":3},"is_collect":"N","share":"测试内容3tq1"}
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
         * comment : {"attr":"内存:2G 颜色:红色","content":"随时随地","create_time":"1513933938","grade":"3","img":["图片地址"],"user_avatar":"http://static.greenlive.1booker.com/upload/image/20171124/1511504293608245.png","user_name":"哈哈"}
         * dialogue : {"charId":"测试内容4h57","id":"测试内容67kq","name":"测试内容86u7","store_uid":"测试内容7j12"}
         * goods : {"column_id":"80","goods_attr":["材质成分:聚酯纤维100%","风格:街头","裙长:中长裙"],"goods_create_time":"0","goods_detail":"<p><img src=\" http://live.timierhouse.com/FpyWf15wHhERzVlPeYaxp7PsyjT3\" style=\"max-width:100%;\"><br><\/p><p><br><\/p>","goods_id":"100095","goods_img":"FpyWf15wHhERzVlPeYaxp7PsyjT3","goods_imgs":"FoPFq_kieLe-iGF7ZJDcCR0s0-oe,FoPFq_kieLe-iGF7ZJDcCR0s0-oe","goods_is_delete":"N","goods_live_recommend":"1","goods_max_price":"3.00","goods_name":"苹果手机","goods_price":"3.00","goods_promise":"七天无理由退货","goods_sales":"0","goods_sn":"3","goods_state":"1","goods_stock":"0","goods_update_time":"0","shop_fee":"10","store_id":"4","total_collect":"0","user_id":"264"}
         * grade : {"bad":1,"general":1,"good":1,"total":3}
         * is_collect : N
         * share : 测试内容3tq1
         */

        private CommentEntity comment;
        private DialogueEntity dialogue;
        private GoodsEntity goods;
        private GradeEntity grade;
        private String is_collect;
        private String details_share;

        public String getDetails_share() {
            return details_share;
        }

        public void setDetails_share(String details_share) {
            this.details_share = details_share;
        }

        private String share;

        public void setComment(CommentEntity comment) {
            this.comment = comment;
        }

        public void setDialogue(DialogueEntity dialogue) {
            this.dialogue = dialogue;
        }

        public void setGoods(GoodsEntity goods) {
            this.goods = goods;
        }

        public void setGrade(GradeEntity grade) {
            this.grade = grade;
        }

        public void setIs_collect(String is_collect) {
            this.is_collect = is_collect;
        }

        public void setShare(String share) {
            this.share = share;
        }

        public CommentEntity getComment() {
            return comment;
        }

        public DialogueEntity getDialogue() {
            return dialogue;
        }

        public GoodsEntity getGoods() {
            return goods;
        }

        public GradeEntity getGrade() {
            return grade;
        }

        public String getIs_collect() {
            return is_collect;
        }

        public String getShare() {
            return share;
        }

        public static class CommentEntity {
            /**
             * attr : 内存:2G 颜色:红色
             * content : 随时随地
             * create_time : 1513933938
             * grade : 3
             * img : ["图片地址"]
             * user_avatar : http://static.greenlive.1booker.com/upload/image/20171124/1511504293608245.png
             * user_name : 哈哈
             */

            private String attr;
            private String content;
            private String create_time;
            private int grade;
            private String user_avatar;
            private String user_name;
            private List<String> img;

            public void setAttr(String attr) {
                this.attr = attr;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public void setCreate_time(String create_time) {
                this.create_time = create_time;
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

            public void setImg(List<String> img) {
                this.img = img;
            }

            public String getAttr() {
                return attr;
            }

            public String getContent() {
                return content;
            }

            public String getCreate_time() {
                return create_time;
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

            public List<String> getImg() {
                return img;
            }
        }

        public static class DialogueEntity {
            /**
             * charId : 测试内容4h57
             * id : 测试内容67kq
             * name : 测试内容86u7
             * store_uid : 测试内容7j12
             */

            private String charId;
            private String id;
            private String name;
            private String  status;
            private String store_uid;

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }

            public void setCharId(String charId) {
                this.charId = charId;
            }

            public void setId(String id) {
                this.id = id;
            }

            public void setName(String name) {
                this.name = name;
            }

            public void setStore_uid(String store_uid) {
                this.store_uid = store_uid;
            }

            public String getCharId() {
                return charId;
            }

            public String getId() {
                return id;
            }

            public String getName() {
                return name;
            }

            public String getStore_uid() {
                return store_uid;
            }
        }

        public static class GoodsEntity {
            /**
             * column_id : 80
             * goods_attr : ["材质成分:聚酯纤维100%","风格:街头","裙长:中长裙"]
             * goods_create_time : 0
             * goods_detail : <p><img src=" http://live.timierhouse.com/FpyWf15wHhERzVlPeYaxp7PsyjT3" style="max-width:100%;"><br></p><p><br></p>
             * goods_id : 100095
             * goods_img : FpyWf15wHhERzVlPeYaxp7PsyjT3
             * goods_imgs : FoPFq_kieLe-iGF7ZJDcCR0s0-oe,FoPFq_kieLe-iGF7ZJDcCR0s0-oe
             * goods_is_delete : N
             * goods_live_recommend : 1
             * goods_max_price : 3.00
             * goods_name : 苹果手机
             * goods_price : 3.00
             * goods_promise : 七天无理由退货
             * goods_sales : 0
             * goods_sn : 3
             * goods_state : 1
             * goods_stock : 0
             * goods_update_time : 0
             * shop_fee : 10
             * store_id : 4
             * total_collect : 0
             * user_id : 264
             */

            private String column_id;
            private String goods_create_time;
            private String goods_detail;
            private String goods_id;
            private String goods_img;
            private List<String> goods_imgs;
            private String goods_is_delete;
            private String goods_live_recommend;
            private String goods_max_price;
            private String goods_name;
            private String goods_price;
            private List<String> goods_promise;
            private String goods_sales;
            private String goods_sn;
            private String goods_state;
            private String goods_stock;
            private String goods_update_time;
            private String shop_fee;
            private String store_id;
            private String total_collect;
            private String user_id;
            private List<String> goods_attr;
            private String trace_url;
            private String trace_id;

            public String getTrace_id() {
                return trace_id;
            }

            public void setTrace_id(String trace_id) {
                this.trace_id = trace_id;
            }

            public String getTrace_url() {
                return trace_url;
            }

            public void setTrace_url(String trace_url) {
                this.trace_url = trace_url;
            }


            public void setColumn_id(String column_id) {
                this.column_id = column_id;
            }

            public void setGoods_create_time(String goods_create_time) {
                this.goods_create_time = goods_create_time;
            }

            public void setGoods_detail(String goods_detail) {
                this.goods_detail = goods_detail;
            }

            public void setGoods_id(String goods_id) {
                this.goods_id = goods_id;
            }

            public void setGoods_img(String goods_img) {
                this.goods_img = goods_img;
            }

            public void setGoods_imgs(List<String> goods_imgs) {
                this.goods_imgs = goods_imgs;
            }

            public void setGoods_is_delete(String goods_is_delete) {
                this.goods_is_delete = goods_is_delete;
            }

            public void setGoods_live_recommend(String goods_live_recommend) {
                this.goods_live_recommend = goods_live_recommend;
            }

            public void setGoods_max_price(String goods_max_price) {
                this.goods_max_price = goods_max_price;
            }

            public void setGoods_name(String goods_name) {
                this.goods_name = goods_name;
            }

            public void setGoods_price(String goods_price) {
                this.goods_price = goods_price;
            }

            public void setGoods_promise(List<String> goods_promise) {
                this.goods_promise = goods_promise;
            }

            public void setGoods_sales(String goods_sales) {
                this.goods_sales = goods_sales;
            }

            public void setGoods_sn(String goods_sn) {
                this.goods_sn = goods_sn;
            }

            public void setGoods_state(String goods_state) {
                this.goods_state = goods_state;
            }

            public void setGoods_stock(String goods_stock) {
                this.goods_stock = goods_stock;
            }

            public void setGoods_update_time(String goods_update_time) {
                this.goods_update_time = goods_update_time;
            }

            public void setShop_fee(String shop_fee) {
                this.shop_fee = shop_fee;
            }

            public void setStore_id(String store_id) {
                this.store_id = store_id;
            }

            public void setTotal_collect(String total_collect) {
                this.total_collect = total_collect;
            }

            public void setUser_id(String user_id) {
                this.user_id = user_id;
            }

            public void setGoods_attr(List<String> goods_attr) {
                this.goods_attr = goods_attr;
            }

            public String getColumn_id() {
                return column_id;
            }

            public String getGoods_create_time() {
                return goods_create_time;
            }

            public String getGoods_detail() {
                return goods_detail;
            }

            public String getGoods_id() {
                return goods_id;
            }

            public String getGoods_img() {
                return goods_img;
            }

            public List<String> getGoods_imgs() {
                return goods_imgs;
            }

            public String getGoods_is_delete() {
                return goods_is_delete;
            }

            public String getGoods_live_recommend() {
                return goods_live_recommend;
            }

            public String getGoods_max_price() {
                return goods_max_price;
            }

            public String getGoods_name() {
                return goods_name;
            }

            public String getGoods_price() {
                return goods_price;
            }

            public List<String> getGoods_promise() {
                return goods_promise;
            }

            public String getGoods_sales() {
                return goods_sales;
            }

            public String getGoods_sn() {
                return goods_sn;
            }

            public String getGoods_state() {
                return goods_state;
            }

            public String getGoods_stock() {
                return goods_stock;
            }

            public String getGoods_update_time() {
                return goods_update_time;
            }

            public String getShop_fee() {
                return shop_fee;
            }

            public String getStore_id() {
                return store_id;
            }

            public String getTotal_collect() {
                return total_collect;
            }

            public String getUser_id() {
                return user_id;
            }

            public List<String> getGoods_attr() {
                return goods_attr;
            }
        }

        public static class GradeEntity {
            /**
             * bad : 1
             * general : 1
             * good : 1
             * total : 3
             */

            private int bad;
            private int general;
            private int good;
            private int total;

            public void setBad(int bad) {
                this.bad = bad;
            }

            public void setGeneral(int general) {
                this.general = general;
            }

            public void setGood(int good) {
                this.good = good;
            }

            public void setTotal(int total) {
                this.total = total;
            }

            public int getBad() {
                return bad;
            }

            public int getGeneral() {
                return general;
            }

            public int getGood() {
                return good;
            }

            public int getTotal() {
                return total;
            }
        }
    }
}
