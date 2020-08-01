package com.hn.library.model;

import java.io.Serializable;

/**
 * Copyright (C) 2017,深圳市红鸟网络科技股份有限公司 All rights reserved.
 * 项目名称：HJN_v1.2.1
 * 类描述：注册第二步返回的数据
 * 创建人：mj
 * 创建时间：2017/9/4 12:51
 * 修改人：Administrator
 * 修改时间：2017/9/4 12:51
 * 修改备注：
 * Version:  1.0.0
 */
public class HnLoginBean implements Serializable {

    private TimBean tim;

    /**
     * access_token : IDRk67iDu0yB9YFWFu1g/a3mnqBdCUPdWencukSrKIAvWB4kWye9wBP9OEv2e3dgVEY=
     * anchor_level : 0
     * anchor_ranking : 0
     * user_avatar :
     * user_birth :
     * user_coin : 0
     * user_collect_total : 0
     * user_consume_total : 0
     * user_dot : 0
     * user_exp : 0
     * user_fans_total : 0
     * user_follow_total : 0
     * user_id : 6
     * user_intro :
     * user_invite_code :
     * user_invite_total : 0
     * user_is_anchor : N
     * user_is_certification : N
     * user_is_member : N
     * user_lat : 0.000000
     * user_level : 0
     * user_lng : 0.000000
     * user_member_expire_time : 0
     * user_nickname : 18122711280
     * user_phone : 18122711280
     * user_sex : 1
     * user_token_expire_time : 1510211549
     */

    private String access_token;
    private String anchor_level;
    private String anchor_ranking;
    private String user_avatar;
    private String user_birth;
    private String user_coin;
    private String user_collect_total;
    private String user_consume_total;
    private String user_dot;
    private String user_exp;
    private String user_fans_total;
    private String user_follow_total;
    private String user_id;
    private String user_intro;
    private String user_invite_code;
    private String user_invite_total;
    private String user_is_anchor;
    private String user_is_certification;
    private String user_is_member;
    private String user_lat;
    private String user_level;
    private String user_lng;
    private String user_member_expire_time;
    private String user_nickname;
    private String user_phone;
    private String user_sex;
    private String user_token_expire_time;
    private String ws_url;
    /**
     * shop : {"icon":"测试内容ze69","id":"测试内容4574","name":"测试内容54ul"}
     */

    private ShopEntity shop;
    /**
     * store : {"icon":"测试内容rkaq","id":"测试内容xg88","is_open":"0","name":"测试内容x28g"}
     */

    private StoreEntity store;
    /**
     * track : 测试内容gh36
     */

    private String track;

    public String getWs_url() {
        return ws_url;
    }

    public void setWs_url(String ws_url) {
        this.ws_url = ws_url;
    }

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public String getAnchor_level() {
        return anchor_level;
    }

    public void setAnchor_level(String anchor_level) {
        this.anchor_level = anchor_level;
    }

    public String getAnchor_ranking() {
        return anchor_ranking;
    }

    public void setAnchor_ranking(String anchor_ranking) {
        this.anchor_ranking = anchor_ranking;
    }

    public String getUser_avatar() {
        return user_avatar;
    }

    public void setUser_avatar(String user_avatar) {
        this.user_avatar = user_avatar;
    }

    public String getUser_birth() {
        return user_birth;
    }

    public void setUser_birth(String user_birth) {
        this.user_birth = user_birth;
    }

    public String getUser_coin() {
        return user_coin;
    }

    public void setUser_coin(String user_coin) {
        this.user_coin = user_coin;
    }

    public String getUser_collect_total() {
        return user_collect_total;
    }

    public void setUser_collect_total(String user_collect_total) {
        this.user_collect_total = user_collect_total;
    }

    public String getUser_consume_total() {
        return user_consume_total;
    }

    public void setUser_consume_total(String user_consume_total) {
        this.user_consume_total = user_consume_total;
    }

    public String getUser_dot() {
        return user_dot;
    }

    public void setUser_dot(String user_dot) {
        this.user_dot = user_dot;
    }

    public String getUser_exp() {
        return user_exp;
    }

    public void setUser_exp(String user_exp) {
        this.user_exp = user_exp;
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

    public String getUser_intro() {
        return user_intro;
    }

    public void setUser_intro(String user_intro) {
        this.user_intro = user_intro;
    }

    public String getUser_invite_code() {
        return user_invite_code;
    }

    public void setUser_invite_code(String user_invite_code) {
        this.user_invite_code = user_invite_code;
    }

    public String getUser_invite_total() {
        return user_invite_total;
    }

    public void setUser_invite_total(String user_invite_total) {
        this.user_invite_total = user_invite_total;
    }

    public String getUser_is_anchor() {
        return user_is_anchor;
    }

    public void setUser_is_anchor(String user_is_anchor) {
        this.user_is_anchor = user_is_anchor;
    }

    public String getUser_is_certification() {
        return user_is_certification;
    }

    public void setUser_is_certification(String user_is_certification) {
        this.user_is_certification = user_is_certification;
    }

    public String getUser_is_member() {
        return user_is_member;
    }

    public void setUser_is_member(String user_is_member) {
        this.user_is_member = user_is_member;
    }

    public String getUser_lat() {
        return user_lat;
    }

    public void setUser_lat(String user_lat) {
        this.user_lat = user_lat;
    }

    public String getUser_level() {
        return user_level;
    }

    public void setUser_level(String user_level) {
        this.user_level = user_level;
    }

    public String getUser_lng() {
        return user_lng;
    }

    public void setUser_lng(String user_lng) {
        this.user_lng = user_lng;
    }

    public String getUser_member_expire_time() {
        return user_member_expire_time;
    }

    public void setUser_member_expire_time(String user_member_expire_time) {
        this.user_member_expire_time = user_member_expire_time;
    }

    public String getUser_nickname() {
        return user_nickname;
    }

    public void setUser_nickname(String user_nickname) {
        this.user_nickname = user_nickname;
    }

    public String getUser_phone() {
        return user_phone;
    }

    public void setUser_phone(String user_phone) {
        this.user_phone = user_phone;
    }

    public String getUser_sex() {
        return user_sex;
    }

    public void setUser_sex(String user_sex) {
        this.user_sex = user_sex;
    }

    public String getUser_token_expire_time() {
        return user_token_expire_time;
    }

    public void setUser_token_expire_time(String user_token_expire_time) {
        this.user_token_expire_time = user_token_expire_time;
    }


    public TimBean getTim() {
        return tim;
    }

    public void setTim(TimBean tim) {
        this.tim = tim;
    }

    public void setShop(ShopEntity shop) {
        this.shop = shop;
    }

    public ShopEntity getShop() {
        return shop;
    }

    public void setStore(StoreEntity store) {
        this.store = store;
    }

    public StoreEntity getStore() {
        return store;
    }

    public String getTrack() {
        return track;
    }

    public void setTrack(String track) {
        this.track = track;
    }

    public static class TimBean implements Serializable {
        /**
         * account : 测试内容4w3m
         * account_type : 测试内容5296
         * app_id : 测试内容18pr
         * sign : 测试内容24g1
         */

        private String account;
        private String account_type;
        private String app_id;
        private String sign;

        public String getAccount() {
            return account;
        }

        public void setAccount(String account) {
            this.account = account;
        }

        public String getAccount_type() {
            return account_type;
        }

        public void setAccount_type(String account_type) {
            this.account_type = account_type;
        }

        public String getApp_id() {
            return app_id;
        }

        public void setApp_id(String app_id) {
            this.app_id = app_id;
        }

        public String getSign() {
            return sign;
        }

        public void setSign(String sign) {
            this.sign = sign;
        }
    }

    public static class ShopEntity implements Serializable {
        /**
         * icon : 测试内容ze69
         * id : 测试内容4574
         * name : 测试内容54ul
         */

        private String icon;
        private String id;
        private String name;
        private String status;//店铺是否冻结 0 冻结 1正常
        private String is_open;//店铺资质是否打开 1 打开 0. 关闭

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getIs_open() {
            return is_open;
        }

        public void setIs_open(String is_open) {
            this.is_open = is_open;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public void setId(String id) {
            this.id = id;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getIcon() {
            return icon;
        }

        public String getId() {
            return id;
        }

        public String getName() {
            return name;
        }
    }

    public static class StoreEntity implements Serializable {
        /**
         * icon : 测试内容rkaq
         * id : 测试内容xg88
         * is_open : 0
         * name : 测试内容x28g
         */

        private String icon;
        private String id;
        private String is_open;
        private String name;
        private String status;

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public void setId(String id) {
            this.id = id;
        }

        public void setIs_open(String is_open) {
            this.is_open = is_open;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getIcon() {
            return icon;
        }

        public String getId() {
            return id;
        }

        public String getIs_open() {
            return is_open;
        }

        public String getName() {
            return name;
        }
    }
}
