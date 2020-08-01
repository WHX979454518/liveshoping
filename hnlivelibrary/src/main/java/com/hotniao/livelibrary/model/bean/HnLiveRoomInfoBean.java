package com.hotniao.livelibrary.model.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.List;

/**
 * Copyright (C) 2017,深圳市红鸟网络科技股份有限公司 All rights reserved.
 * 项目名称：youbo
 * 类描述：进入房间的相关信息
 * 创建人：mj
 * 创建时间：2017/9/19 19:39
 * 修改人：Administrator
 * 修改时间：2017/9/19 19:39
 * 修改备注：
 * Version:  1.0.0
 */
public class HnLiveRoomInfoBean implements Parcelable {


    /**
     * anchor : {"anchor_level":"0","anchor_ranking":"0","chat_room_id":"9_8","is_follow":"N","user_avatar":"","user_dot":"1","user_id":"8","user_level":"0","user_nickname":""}
     * live : {"anchor_category_id":"1","anchor_game_category_code":"","anchor_game_category_id":"0","anchor_live_fee":"0","anchor_live_img":"123","anchor_live_onlines":"2","anchor_live_pay":"0","anchor_live_play_flv":"http://10888.liveplay.myqcloud.com/live/10888_8.flv","anchor_live_play_m3u8":"http://10888.liveplay.myqcloud.com/live/10888_8.m3u8","anchor_live_play_rtmp":"rtmp://10888.liveplay.myqcloud.com/live/10888_8","anchor_live_title":"123","barrage_fee":"0","notices":["string1","string2","string3","string4","string5"],"share_url":""}
     * push_goods : {"goods_id":"测试内容79f4","goods_img":"","goods_name":"测试内容3i42","goods_price":"测试内容a2x7"}
     * store : {"goods_sales":"测试内容n42g","icon":"测试内容0idt","id":"测试内容jv83","name":"测试内容lr9w","total_collect":"测试内容vbfq"}
     * user : {"free_time":"0","is_anchor_admin":"N","is_pay":"Y","prohibit_talk_time":"0","user_coin":"","user_id":"1","user_is_member":"N","user_nickname":""}
     * ws_url :
     */

    private AnchorEntity anchor;
    private LiveEntity live;
    private PushGoodsEntity push_goods;
    private StoreEntity store;
    private UserEntity user;
    private String ws_url;

    public void setAnchor(AnchorEntity anchor) {
        this.anchor = anchor;
    }

    public void setLive(LiveEntity live) {
        this.live = live;
    }

    public void setPush_goods(PushGoodsEntity push_goods) {
        this.push_goods = push_goods;
    }

    public void setStore(StoreEntity store) {
        this.store = store;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public void setWs_url(String ws_url) {
        this.ws_url = ws_url;
    }

    public AnchorEntity getAnchor() {
        return anchor;
    }

    public LiveEntity getLive() {
        return live;
    }

    public PushGoodsEntity getPush_goods() {
        return push_goods;
    }

    public StoreEntity getStore() {
        return store;
    }

    public UserEntity getUser() {
        return user;
    }

    public String getWs_url() {
        return ws_url;
    }

    public static class AnchorEntity implements Parcelable {

        /**
         * anchor_level : 0
         * anchor_ranking : 0
         * chat_room_id : 9_8
         * is_follow : N
         * user_avatar :
         * user_dot : 1
         * user_id : 8
         * user_level : 0
         * user_nickname :
         */

        private String anchor_level;
        private String anchor_ranking;
        private String chat_room_id;
        private String is_follow;
        private String user_avatar;
        private String user_dot;
        private String user_id;
        private String user_level;
        private String user_nickname;

        public void setAnchor_level(String anchor_level) {
            this.anchor_level = anchor_level;
        }

        public void setAnchor_ranking(String anchor_ranking) {
            this.anchor_ranking = anchor_ranking;
        }

        public void setChat_room_id(String chat_room_id) {
            this.chat_room_id = chat_room_id;
        }

        public void setIs_follow(String is_follow) {
            this.is_follow = is_follow;
        }

        public void setUser_avatar(String user_avatar) {
            this.user_avatar = user_avatar;
        }

        public void setUser_dot(String user_dot) {
            this.user_dot = user_dot;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public void setUser_level(String user_level) {
            this.user_level = user_level;
        }

        public void setUser_nickname(String user_nickname) {
            this.user_nickname = user_nickname;
        }

        public String getAnchor_level() {
            return anchor_level;
        }

        public String getAnchor_ranking() {
            return anchor_ranking;
        }

        public String getChat_room_id() {
            return chat_room_id;
        }

        public String getIs_follow() {
            return is_follow;
        }

        public String getUser_avatar() {
            return user_avatar;
        }

        public String getUser_dot() {
            return user_dot;
        }

        public String getUser_id() {
            return user_id;
        }

        public String getUser_level() {
            return user_level;
        }

        public String getUser_nickname() {
            return user_nickname;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.anchor_level);
            dest.writeString(this.anchor_ranking);
            dest.writeString(this.chat_room_id);
            dest.writeString(this.is_follow);
            dest.writeString(this.user_avatar);
            dest.writeString(this.user_dot);
            dest.writeString(this.user_id);
            dest.writeString(this.user_level);
            dest.writeString(this.user_nickname);
        }

        public AnchorEntity() {
        }

        protected AnchorEntity(Parcel in) {
            this.anchor_level = in.readString();
            this.anchor_ranking = in.readString();
            this.chat_room_id = in.readString();
            this.is_follow = in.readString();
            this.user_avatar = in.readString();
            this.user_dot = in.readString();
            this.user_id = in.readString();
            this.user_level = in.readString();
            this.user_nickname = in.readString();
        }

        public static final Creator<AnchorEntity> CREATOR = new Creator<AnchorEntity>() {
            public AnchorEntity createFromParcel(Parcel source) {
                return new AnchorEntity(source);
            }

            public AnchorEntity[] newArray(int size) {
                return new AnchorEntity[size];
            }
        };
    }

    public static class LiveEntity implements Parcelable {
        /**
         * anchor_category_id : 1
         * anchor_game_category_code :
         * anchor_game_category_id : 0
         * anchor_live_fee : 0
         * anchor_live_img : 123
         * anchor_live_onlines : 2
         * anchor_live_pay : 0
         * anchor_live_play_flv : http://10888.liveplay.myqcloud.com/live/10888_8.flv
         * anchor_live_play_m3u8 : http://10888.liveplay.myqcloud.com/live/10888_8.m3u8
         * anchor_live_play_rtmp : rtmp://10888.liveplay.myqcloud.com/live/10888_8
         * anchor_live_title : 123
         * barrage_fee : 0
         * notices : ["string1","string2","string3","string4","string5"]
         * share_url :
         */

        private String anchor_category_id;
        private String anchor_game_category_code;
        private String anchor_game_category_id;
        private String anchor_live_fee;
        private String anchor_live_img;
        private String anchor_live_onlines;
        private String anchor_live_pay;
        private String anchor_live_play_flv;
        private String anchor_live_play_m3u8;
        private String anchor_live_play_rtmp;
        private String anchor_live_title;
        private String barrage_fee;
        private String share_url;
        private List<String> notices;

        public void setAnchor_category_id(String anchor_category_id) {
            this.anchor_category_id = anchor_category_id;
        }

        public void setAnchor_game_category_code(String anchor_game_category_code) {
            this.anchor_game_category_code = anchor_game_category_code;
        }

        public void setAnchor_game_category_id(String anchor_game_category_id) {
            this.anchor_game_category_id = anchor_game_category_id;
        }

        public void setAnchor_live_fee(String anchor_live_fee) {
            this.anchor_live_fee = anchor_live_fee;
        }

        public void setAnchor_live_img(String anchor_live_img) {
            this.anchor_live_img = anchor_live_img;
        }

        public void setAnchor_live_onlines(String anchor_live_onlines) {
            this.anchor_live_onlines = anchor_live_onlines;
        }

        public void setAnchor_live_pay(String anchor_live_pay) {
            this.anchor_live_pay = anchor_live_pay;
        }

        public void setAnchor_live_play_flv(String anchor_live_play_flv) {
            this.anchor_live_play_flv = anchor_live_play_flv;
        }

        public void setAnchor_live_play_m3u8(String anchor_live_play_m3u8) {
            this.anchor_live_play_m3u8 = anchor_live_play_m3u8;
        }

        public void setAnchor_live_play_rtmp(String anchor_live_play_rtmp) {
            this.anchor_live_play_rtmp = anchor_live_play_rtmp;
        }

        public void setAnchor_live_title(String anchor_live_title) {
            this.anchor_live_title = anchor_live_title;
        }

        public void setBarrage_fee(String barrage_fee) {
            this.barrage_fee = barrage_fee;
        }

        public void setShare_url(String share_url) {
            this.share_url = share_url;
        }

        public void setNotices(List<String> notices) {
            this.notices = notices;
        }

        public String getAnchor_category_id() {
            return anchor_category_id;
        }

        public String getAnchor_game_category_code() {
            return anchor_game_category_code;
        }

        public String getAnchor_game_category_id() {
            return anchor_game_category_id;
        }

        public String getAnchor_live_fee() {
            return anchor_live_fee;
        }

        public String getAnchor_live_img() {
            return anchor_live_img;
        }

        public String getAnchor_live_onlines() {
            return anchor_live_onlines;
        }

        public String getAnchor_live_pay() {
            return anchor_live_pay;
        }

        public String getAnchor_live_play_flv() {
            return anchor_live_play_flv;
        }

        public String getAnchor_live_play_m3u8() {
            return anchor_live_play_m3u8;
        }

        public String getAnchor_live_play_rtmp() {
            return anchor_live_play_rtmp;
        }

        public String getAnchor_live_title() {
            return anchor_live_title;
        }

        public String getBarrage_fee() {
            return barrage_fee;
        }

        public String getShare_url() {
            return share_url;
        }

        public List<String> getNotices() {
            return notices;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.anchor_category_id);
            dest.writeString(this.anchor_game_category_code);
            dest.writeString(this.anchor_game_category_id);
            dest.writeString(this.anchor_live_fee);
            dest.writeString(this.anchor_live_img);
            dest.writeString(this.anchor_live_onlines);
            dest.writeString(this.anchor_live_pay);
            dest.writeString(this.anchor_live_play_flv);
            dest.writeString(this.anchor_live_play_m3u8);
            dest.writeString(this.anchor_live_play_rtmp);
            dest.writeString(this.anchor_live_title);
            dest.writeString(this.barrage_fee);
            dest.writeString(this.share_url);
            dest.writeStringList(this.notices);
        }

        public LiveEntity() {
        }

        protected LiveEntity(Parcel in) {
            this.anchor_category_id = in.readString();
            this.anchor_game_category_code = in.readString();
            this.anchor_game_category_id = in.readString();
            this.anchor_live_fee = in.readString();
            this.anchor_live_img = in.readString();
            this.anchor_live_onlines = in.readString();
            this.anchor_live_pay = in.readString();
            this.anchor_live_play_flv = in.readString();
            this.anchor_live_play_m3u8 = in.readString();
            this.anchor_live_play_rtmp = in.readString();
            this.anchor_live_title = in.readString();
            this.barrage_fee = in.readString();
            this.share_url = in.readString();
            this.notices = in.createStringArrayList();
        }

        public static final Creator<LiveEntity> CREATOR = new Creator<LiveEntity>() {
            public LiveEntity createFromParcel(Parcel source) {
                return new LiveEntity(source);
            }

            public LiveEntity[] newArray(int size) {
                return new LiveEntity[size];
            }
        };
    }

    public static class PushGoodsEntity implements Parcelable {
        /**
         * goods_id : 测试内容79f4
         * goods_img :
         * goods_name : 测试内容3i42
         * goods_price : 测试内容a2x7
         */

        private String goods_id;
        private String goods_img;
        private String goods_name;
        private String goods_price;

        public void setGoods_id(String goods_id) {
            this.goods_id = goods_id;
        }

        public void setGoods_img(String goods_img) {
            this.goods_img = goods_img;
        }

        public void setGoods_name(String goods_name) {
            this.goods_name = goods_name;
        }

        public void setGoods_price(String goods_price) {
            this.goods_price = goods_price;
        }

        public String getGoods_id() {
            return goods_id;
        }

        public String getGoods_img() {
            return goods_img;
        }

        public String getGoods_name() {
            return goods_name;
        }

        public String getGoods_price() {
            return goods_price;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.goods_id);
            dest.writeString(this.goods_img);
            dest.writeString(this.goods_name);
            dest.writeString(this.goods_price);
        }

        public PushGoodsEntity() {
        }

        protected PushGoodsEntity(Parcel in) {
            this.goods_id = in.readString();
            this.goods_img = in.readString();
            this.goods_name = in.readString();
            this.goods_price = in.readString();
        }

        public static final Creator<PushGoodsEntity> CREATOR = new Creator<PushGoodsEntity>() {
            public PushGoodsEntity createFromParcel(Parcel source) {
                return new PushGoodsEntity(source);
            }

            public PushGoodsEntity[] newArray(int size) {
                return new PushGoodsEntity[size];
            }
        };
    }

    public static class StoreEntity implements Parcelable {


        /**
         * goods_sales : 测试内容n42g
         * icon : 测试内容0idt
         * id : 测试内容jv83
         * name : 测试内容lr9w
         * total_collect : 测试内容vbfq
         */

        private String goods_sales;
        private String icon;
        private String id;
        private String name;
        private String       status;
        private String total_collect;
        private String total_order;

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getTotal_order() {
            return total_order;
        }

        public void setTotal_order(String total_order) {
            this.total_order = total_order;
        }

        public void setGoods_sales(String goods_sales) {
            this.goods_sales = goods_sales;
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

        public void setTotal_collect(String total_collect) {
            this.total_collect = total_collect;
        }

        public String getGoods_sales() {
            return goods_sales;
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

        public String getTotal_collect() {
            return total_collect;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.goods_sales);
            dest.writeString(this.icon);
            dest.writeString(this.id);
            dest.writeString(this.name);
            dest.writeString(this.total_collect);
        }

        public StoreEntity() {
        }

        protected StoreEntity(Parcel in) {
            this.goods_sales = in.readString();
            this.icon = in.readString();
            this.id = in.readString();
            this.name = in.readString();
            this.total_collect = in.readString();
        }

        public static final Creator<StoreEntity> CREATOR = new Creator<StoreEntity>() {
            public StoreEntity createFromParcel(Parcel source) {
                return new StoreEntity(source);
            }

            public StoreEntity[] newArray(int size) {
                return new StoreEntity[size];
            }
        };
    }

    public static class UserEntity implements Parcelable {


        /**
         * free_time : 0
         * is_anchor_admin : N
         * is_pay : Y
         * prohibit_talk_time : 0
         * user_coin :
         * user_id : 1
         * user_is_member : N
         * user_nickname :
         */

        private String free_time;
        private String is_anchor_admin;
        private String is_pay;
        private String prohibit_talk_time;
        private String user_coin;
        private String user_id;
        private String user_is_member;
        private String user_nickname;

        public void setFree_time(String free_time) {
            this.free_time = free_time;
        }

        public void setIs_anchor_admin(String is_anchor_admin) {
            this.is_anchor_admin = is_anchor_admin;
        }

        public void setIs_pay(String is_pay) {
            this.is_pay = is_pay;
        }

        public void setProhibit_talk_time(String prohibit_talk_time) {
            this.prohibit_talk_time = prohibit_talk_time;
        }

        public void setUser_coin(String user_coin) {
            this.user_coin = user_coin;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public void setUser_is_member(String user_is_member) {
            this.user_is_member = user_is_member;
        }

        public void setUser_nickname(String user_nickname) {
            this.user_nickname = user_nickname;
        }

        public String getFree_time() {
            return free_time;
        }

        public String getIs_anchor_admin() {
            return is_anchor_admin;
        }

        public String getIs_pay() {
            return is_pay;
        }

        public String getProhibit_talk_time() {
            return prohibit_talk_time;
        }

        public String getUser_coin() {
            return user_coin;
        }

        public String getUser_id() {
            return user_id;
        }

        public String getUser_is_member() {
            return user_is_member;
        }

        public String getUser_nickname() {
            return user_nickname;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.free_time);
            dest.writeString(this.is_anchor_admin);
            dest.writeString(this.is_pay);
            dest.writeString(this.prohibit_talk_time);
            dest.writeString(this.user_coin);
            dest.writeString(this.user_id);
            dest.writeString(this.user_is_member);
            dest.writeString(this.user_nickname);
        }

        public UserEntity() {
        }

        protected UserEntity(Parcel in) {
            this.free_time = in.readString();
            this.is_anchor_admin = in.readString();
            this.is_pay = in.readString();
            this.prohibit_talk_time = in.readString();
            this.user_coin = in.readString();
            this.user_id = in.readString();
            this.user_is_member = in.readString();
            this.user_nickname = in.readString();
        }

        public static final Creator<UserEntity> CREATOR = new Creator<UserEntity>() {
            public UserEntity createFromParcel(Parcel source) {
                return new UserEntity(source);
            }

            public UserEntity[] newArray(int size) {
                return new UserEntity[size];
            }
        };
    }


    public HnLiveRoomInfoBean() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.anchor, 0);
        dest.writeParcelable(this.live, 0);
        dest.writeParcelable(this.push_goods, 0);
        dest.writeParcelable(this.store, 0);
        dest.writeParcelable(this.user, 0);
        dest.writeString(this.ws_url);
    }

    protected HnLiveRoomInfoBean(Parcel in) {
        this.anchor = in.readParcelable(AnchorEntity.class.getClassLoader());
        this.live = in.readParcelable(LiveEntity.class.getClassLoader());
        this.push_goods = in.readParcelable(PushGoodsEntity.class.getClassLoader());
        this.store = (StoreEntity) in.readSerializable();
        this.user = (UserEntity) in.readSerializable();
        this.ws_url = in.readString();
    }

    public static final Creator<HnLiveRoomInfoBean> CREATOR = new Creator<HnLiveRoomInfoBean>() {
        public HnLiveRoomInfoBean createFromParcel(Parcel source) {
            return new HnLiveRoomInfoBean(source);
        }

        public HnLiveRoomInfoBean[] newArray(int size) {
            return new HnLiveRoomInfoBean[size];
        }
    };
}
