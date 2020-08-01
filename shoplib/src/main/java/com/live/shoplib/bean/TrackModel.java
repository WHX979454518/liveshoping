package com.live.shoplib.bean;

import com.hn.library.http.BaseResponseModel;

import java.util.List;

/**
 * create by Mr.x
 * on 5/7/2018
 */

public class TrackModel extends BaseResponseModel {


    /**
     * d : {"list":[{"goods":[{"date_type":"20180629","goods_id":"7","goods_img":"https://redbird-zbds-1252571077.image.myqcloud.com/image/2018/06/07/1528359697393.png","goods_live_recommend":"1","goods_name":"海藻面膜","goods_price":"49.00"}],"date":"06月29日"},{"goods":[{"date_type":"20180629","goods_id":"12","goods_img":"https://redbird-zbds-1252571077.image.myqcloud.com/image/2018/06/11/1528698714724.png","goods_live_recommend":"0","goods_name":"y一张图片","goods_price":"30.00"}],"date":"06月30日"}]}
     */

    private DBean d;

    public DBean getD() {
        return d;
    }

    public void setD(DBean d) {
        this.d = d;
    }

    public static class DBean {
        private List<ListBean> list;

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean {
            /**
             * goods : [{"date_type":"20180629","goods_id":"7","goods_img":"https://redbird-zbds-1252571077.image.myqcloud.com/image/2018/06/07/1528359697393.png","goods_live_recommend":"1","goods_name":"海藻面膜","goods_price":"49.00"}]
             * date : 06月29日
             */

            private String date;
            private boolean checkAll;
            private boolean canCheck;
            private List<GoodsBean> goods;

            public boolean isCanCheck() {
                return canCheck;
            }

            public void setCanCheck(boolean canCheck) {
                this.canCheck = canCheck;
            }

            public boolean isCheckAll() {
                return checkAll;
            }

            public void setCheckAll(boolean checkAll,boolean set) {
                if(set) {
                    for (int i = 0; i < goods.size(); i++) {
                        goods.get(i).setCheck(checkAll);
                    }
                }
                this.checkAll = checkAll;
            }

            public String getDate() {
                return date;
            }

            public void setDate(String date) {
                this.date = date;
            }

            public List<GoodsBean> getGoods() {
                return goods;
            }

            public void setGoods(List<GoodsBean> goods) {
                this.goods = goods;
            }

            public static class GoodsBean {
                /**
                 * date_type : 20180629
                 * goods_id : 7
                 * goods_img : https://redbird-zbds-1252571077.image.myqcloud.com/image/2018/06/07/1528359697393.png
                 * goods_live_recommend : 1
                 * goods_name : 海藻面膜
                 * goods_price : 49.00
                 */

                private String date_type;
                private String goods_id;
                private String goods_img;
                private String goods_live_recommend;
                private String goods_name;
                private String goods_price;
                private boolean check;

                public boolean getCheck() {
                    return check;
                }

                public void setCheck(boolean check) {

                    this.check = check;
                }

                public String getDate_type() {
                    return date_type;
                }

                public void setDate_type(String date_type) {
                    this.date_type = date_type;
                }

                public String getGoods_id() {
                    return goods_id;
                }

                public void setGoods_id(String goods_id) {
                    this.goods_id = goods_id;
                }

                public String getGoods_img() {
                    return goods_img;
                }

                public void setGoods_img(String goods_img) {
                    this.goods_img = goods_img;
                }

                public String getGoods_live_recommend() {
                    return goods_live_recommend;
                }

                public void setGoods_live_recommend(String goods_live_recommend) {
                    this.goods_live_recommend = goods_live_recommend;
                }

                public String getGoods_name() {
                    return goods_name;
                }

                public void setGoods_name(String goods_name) {
                    this.goods_name = goods_name;
                }

                public String getGoods_price() {
                    return goods_price;
                }

                public void setGoods_price(String goods_price) {
                    this.goods_price = goods_price;
                }
            }
        }
    }
}
