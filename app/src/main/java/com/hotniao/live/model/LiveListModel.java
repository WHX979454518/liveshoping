package com.hotniao.live.model;

import com.hn.library.http.BaseResponseModel;

import java.util.List;

/**
 * Created by Alan on 2019/6/17.
 */
public class LiveListModel extends BaseResponseModel {


    private D d;

    public D getD() {
        return d;
    }

    public void setD(D d) {
        this.d = d;
    }


    public class D {

        private Store store;

        public void setStore(Store store) {
            this.store = store;
        }

        public Store getStore() {
            return store;
        }

    }

    public class Store {

        private List<LiveItem> items;
        private int page;
        private int pagesize;
        private int pagetotal;
        private int total;
        private int prev;
        private int next;

        public void setItems(List<LiveItem> items) {
            this.items = items;
        }

        public List<LiveItem> getItems() {
            return items;
        }

        public void setPage(int page) {
            this.page = page;
        }

        public int getPage() {
            return page;
        }

        public void setPagesize(int pagesize) {
            this.pagesize = pagesize;
        }

        public int getPagesize() {
            return pagesize;
        }

        public void setPagetotal(int pagetotal) {
            this.pagetotal = pagetotal;
        }

        public int getPagetotal() {
            return pagetotal;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public int getTotal() {
            return total;
        }

        public void setPrev(int prev) {
            this.prev = prev;
        }

        public int getPrev() {
            return prev;
        }

        public void setNext(int next) {
            this.next = next;
        }

        public int getNext() {
            return next;
        }
    }

    public class LiveItem {

        private String anchor_id;
        private String anchor_live_img;
        private String anchor_live_title;
        private String anchor_local;
        private String anchor_live_onlines;
        private String anchor_is_live;
        private String store_id;
        private String table;
        private String user_id;
        private String goods_name;
        private String goods_price;
        private String goods_id;
        private String goods_create_time;
        private String anchor_live_like_total;
        private String name;
        private String icon;

        public String getAnchor_live_like_total() {
            return anchor_live_like_total;
        }

        public void setAnchor_live_like_total(String anchor_live_like_total) {
            this.anchor_live_like_total = anchor_live_like_total;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public void setAnchor_id(String anchor_id) {
            this.anchor_id = anchor_id;
        }

        public String getAnchor_id() {
            return anchor_id;
        }

        public void setAnchor_live_img(String anchor_live_img) {
            this.anchor_live_img = anchor_live_img;
        }

        public String getAnchor_live_img() {
            return anchor_live_img;
        }

        public void setAnchor_live_title(String anchor_live_title) {
            this.anchor_live_title = anchor_live_title;
        }

        public String getAnchor_live_title() {
            return anchor_live_title;
        }

        public void setAnchor_local(String anchor_local) {
            this.anchor_local = anchor_local;
        }

        public String getAnchor_local() {
            return anchor_local;
        }

        public void setAnchor_live_onlines(String anchor_live_onlines) {
            this.anchor_live_onlines = anchor_live_onlines;
        }

        public String getAnchor_live_onlines() {
            return anchor_live_onlines;
        }

        public void setAnchor_is_live(String anchor_is_live) {
            this.anchor_is_live = anchor_is_live;
        }

        public Boolean getAnchor_is_live() {
            return "Y".equals(anchor_is_live);
        }

        public void setStore_id(String store_id) {
            this.store_id = store_id;
        }

        public String getStore_id() {
            return store_id;
        }

        public void setTable(String table) {
            this.table = table;
        }

        public String getTable() {
            return table;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public String getUser_id() {
            return user_id;
        }

        public void setGoods_name(String goods_name) {
            this.goods_name = goods_name;
        }

        public String getGoods_name() {
            return goods_name;
        }

        public void setGoods_price(String goods_price) {
            this.goods_price = goods_price;
        }

        public String getGoods_price() {
            return goods_price;
        }

        public void setGoods_id(String goods_id) {
            this.goods_id = goods_id;
        }

        public String getGoods_id() {
            return goods_id;
        }

        public void setGoods_create_time(String goods_create_time) {
            this.goods_create_time = goods_create_time;
        }

        public String getGoods_create_time() {
            return goods_create_time;
        }

    }

}
