package com.live.shoplib.bean;

import android.graphics.Bitmap;

import com.hn.library.http.BaseResponseModel;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：Mr.Xu
 * 时间：2017/12/21
 */
public class EvaPublicModel extends BaseResponseModel {


    /**
     * d : {"goods_list":[{"goods_attr":"内存:2G 颜色:红色","goods_id":"100090","goods_img":"FoPFq_kieLe-iGF7ZJDcCR0s0-oe"},{"goods_attr":"内存:2G 颜色:红色","goods_id":"100091","goods_img":"FvZ_eWplp4BkoKB3ZC7IrAcajMKT"}],"order_id":"69"}
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
         * goods_list : [{"goods_attr":"内存:2G 颜色:红色","goods_id":"100090","goods_img":"FoPFq_kieLe-iGF7ZJDcCR0s0-oe"},{"goods_attr":"内存:2G 颜色:红色","goods_id":"100091","goods_img":"FvZ_eWplp4BkoKB3ZC7IrAcajMKT"}]
         * order_id : 69
         */

        private String order_id;
        private List<GoodsListEntity> goods_list;

        public void setOrder_id(String order_id) {
            this.order_id = order_id;
        }

        public void setGoods_list(List<GoodsListEntity> goods_list) {
            this.goods_list = goods_list;
        }

        public String getOrder_id() {
            return order_id;
        }

        public List<GoodsListEntity> getGoods_list() {
            return goods_list;
        }

        public static class GoodsListEntity {
            /**
             * goods_attr : 内存:2G 颜色:红色
             * goods_id : 100090
             * goods_img : FoPFq_kieLe-iGF7ZJDcCR0s0-oe
             */

            private String goods_attr;
            private String goods_id;
            private String goods_img;
            private List<String> imgs = new ArrayList<>();
            private String content;
            private String img;
            private String grade = "5";

            public List<String> getImgs() {
                return imgs;
            }

            public void setImgs(List<String> imgs) {
                this.imgs = imgs;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public String getImg() {
                return img;
            }

            public void setImg(String img) {
                this.img = img;
            }

            public String getGrade() {
                return grade;
            }

            public void setGrade(String grade) {
                this.grade = grade;
            }

            public void setGoods_attr(String goods_attr) {
                this.goods_attr = goods_attr;
            }

            public void setGoods_id(String goods_id) {
                this.goods_id = goods_id;
            }

            public void setGoods_img(String goods_img) {
                this.goods_img = goods_img;
            }

            public String getGoods_attr() {
                return goods_attr;
            }

            public String getGoods_id() {
                return goods_id;
            }

            public String getGoods_img() {
                return goods_img;
            }
        }
    }
}
