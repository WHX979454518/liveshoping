package com.live.shoplib.bean;

import com.hn.library.http.BaseResponseModel;

import java.util.List;

/**
 * 作者：Mr.Xu
 * 时间：2017/12/21
 */
public class SpikeGoodsDetailsModel extends BaseResponseModel {


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
         * "seckill_info": { // 秒杀信息
         *             "sec_goods_id": "10001", // 秒杀商品id
         *             "goods_id": "7", // 商品id
         *             "sec_goods_stock": "5", //秒杀库存
         *             "sec_price": "45.00", 秒杀价格
         *             "single_limit": "1", 单次最大购买量
         *             "end_time": "1560999600000", // 结束时间
         *             "max_number": "5" // 最大限制
         *         }
         */

        private CommentEntity comment;
        private DialogueEntity dialogue;
        private GoodsEntity goods;
        private GradeEntity grade;
        private String is_collect;
        private String details_share;
        private List<GoodsAttrBean.SpecEntity> spec;
        private List<GoodsAttrBean.SkuEntity> sku;
        private SeckillInfo seckill_info;
        private GroupBuyInfo group_goods;


        public GroupBuyInfo getGroup_goods() {
            return group_goods;
        }

        public void setGroup_goods(GroupBuyInfo group_goods) {
            this.group_goods = group_goods;
        }

        public List<GoodsAttrBean.SkuEntity> getSku() {
            return sku;
        }

        public void setSku(List<GoodsAttrBean.SkuEntity> sku) {
            this.sku = sku;
        }

        public List<GoodsAttrBean.SpecEntity> getSpec() {
            return spec;
        }

        public void setSpec(List<GoodsAttrBean.SpecEntity> spec) {
            this.spec = spec;
        }

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
            private String icon;

            public String getIcon() {
                return icon;
            }

            public void setIcon(String icon) {
                this.icon = icon;
            }

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

        public SeckillInfo getSeckill_info() {
            return seckill_info;
        }

        public void setSeckill_info(SeckillInfo seckill_info) {
            this.seckill_info = seckill_info;
        }

        public static class SeckillInfo{
            private String sec_goods_id;

            private String goods_id;
            private String sec_goods_stock;
            private String sec_price;
            private String single_limit;
            private String end_time;
            private String max_number;
            private String  goods_attr;


            public String getGoods_attr() {
                return goods_attr;
            }

            public void setGoods_attr(String goods_attr) {
                this.goods_attr = goods_attr;
            }

            public String getSec_goods_id() {
                return sec_goods_id;
            }

            public void setSec_goods_id(String sec_goods_id) {
                this.sec_goods_id = sec_goods_id;
            }

            public String getGoods_id() {
                return goods_id;
            }

            public void setGoods_id(String goods_id) {
                this.goods_id = goods_id;
            }

            public String getSec_goods_stock() {
                return sec_goods_stock;
            }

            public void setSec_goods_stock(String sec_goods_stock) {
                this.sec_goods_stock = sec_goods_stock;
            }

            public String getSec_price() {
                return sec_price;
            }

            public void setSec_price(String sec_price) {
                this.sec_price = sec_price;
            }

            public String getSingle_limit() {
                return single_limit;
            }

            public void setSingle_limit(String single_limit) {
                this.single_limit = single_limit;
            }

            public String getEnd_time() {
                return end_time;
            }

            public void setEnd_time(String end_time) {
                this.end_time = end_time;
            }

            public String getMax_number() {
                return max_number;
            }

            public void setMax_number(String max_number) {
                this.max_number = max_number;
            }
        }

        public static class GroupBuyInfo{

            private String group_buy_id;
            private String goods_id;
            private String group_goods_stock;
            private String group_price;
            private String single_limit;
            private String max_number;
            private String group_condition;
            private String sku;
            private String spec;

            public String getGroup_buy_id() {
                return group_buy_id;
            }

            public void setGroup_buy_id(String group_buy_id) {
                this.group_buy_id = group_buy_id;
            }

            public String getGoods_id() {
                return goods_id;
            }

            public void setGoods_id(String goods_id) {
                this.goods_id = goods_id;
            }

            public String getGroup_goods_stock() {
                return group_goods_stock;
            }

            public void setGroup_goods_stock(String group_goods_stock) {
                this.group_goods_stock = group_goods_stock;
            }

            public String getGroup_price() {
                return group_price;
            }

            public void setGroup_price(String group_price) {
                this.group_price = group_price;
            }

            public String getSingle_limit() {
                return single_limit;
            }

            public void setSingle_limit(String single_limit) {
                this.single_limit = single_limit;
            }

            public String getMax_number() {
                return max_number;
            }

            public void setMax_number(String max_number) {
                this.max_number = max_number;
            }

            public String getGroup_condition() {
                return group_condition;
            }

            public void setGroup_condition(String group_condition) {
                this.group_condition = group_condition;
            }

            public String getSku() {
                return sku;
            }

            public void setSku(String sku) {
                this.sku = sku;
            }

            public String getSpec() {
                return spec;
            }

            public void setSpec(String spec) {
                this.spec = spec;
            }
        }
    }
}
