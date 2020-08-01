package com.live.shoplib.bean;

import com.hn.library.http.BaseResponseModel;

import java.util.List;

/**
 * 作者：Mr.Xu
 * 时间：2017/12/27
 */
public class ShareGroupBuyModel extends BaseResponseModel {




    private DEntity d;

    public void setD(DEntity d) {
        this.d = d;
    }

    public DEntity getD() {
        return d;
    }

    public static class DEntity {
        private String group_order_id;
        private List<Userinfo> userinfo;
        private Group_goods_info group_goods_info;
        private long start_time;
        private String invite_url;
        private int last_num;


        public String getGroup_order_id() {
            return group_order_id;
        }

        public void setGroup_order_id(String group_order_id) {
            this.group_order_id = group_order_id;
        }

        public List<Userinfo> getUserinfo() {
            return userinfo;
        }

        public void setUserinfo(List<Userinfo> userinfo) {
            this.userinfo = userinfo;
        }

        public Group_goods_info getGroup_goods_info() {
            return group_goods_info;
        }

        public void setGroup_goods_info(Group_goods_info group_goods_info) {
            this.group_goods_info = group_goods_info;
        }

        public long getStart_time() {
            return start_time;
        }

        public void setStart_time(long start_time) {
            this.start_time = start_time;
        }

        public String getInvite_url() {
            return invite_url;
        }

        public void setInvite_url(String invite_url) {
            this.invite_url = invite_url;
        }

        public int getLast_num() {
            return last_num;
        }

        public void setLast_num(int last_num) {
            this.last_num = last_num;
        }
    }

    public static class Userinfo {

        private String user_avatar;
        private String user_nickname;
        public void setUser_avatar(String user_avatar) {
            this.user_avatar = user_avatar;
        }
        public String getUser_avatar() {
            return user_avatar;
        }

        public void setUser_nickname(String user_nickname) {
            this.user_nickname = user_nickname;
        }
        public String getUser_nickname() {
            return user_nickname;
        }

    }
    public static class Group_goods_info {

        private int goods_id;
        private String goods_name;
        private String goods_img;
        private String group_price;
        private String goods_price;
        public void setGoods_id(int goods_id) {
            this.goods_id = goods_id;
        }
        public int getGoods_id() {
            return goods_id;
        }

        public void setGoods_name(String goods_name) {
            this.goods_name = goods_name;
        }
        public String getGoods_name() {
            return goods_name;
        }

        public void setGoods_img(String goods_img) {
            this.goods_img = goods_img;
        }
        public String getGoods_img() {
            return goods_img;
        }

        public String getGroup_price() {
            return group_price;
        }

        public void setGroup_price(String group_price) {
            this.group_price = group_price;
        }

        public String getGoods_price() {
            return goods_price;
        }

        public void setGoods_price(String goods_price) {
            this.goods_price = goods_price;
        }
    }
}
