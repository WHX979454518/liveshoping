package com.hotniao.live.model;

import com.hn.library.http.BaseResponseModel;

import java.util.List;

/**
 * 作者：Mr.Xu
 * 时间：2018/1/5
 */
public class HnExchangeListModel extends BaseResponseModel{


    /**
     * d : {"items":[{"create_time":"1515056711","exchange_coin":"4.00","exchange_dot":"2.00"},{"create_time":"1515056630","exchange_coin":"4.00","exchange_dot":"2.00"}],"next":1,"page":1,"pagesize":20,"pagetotal":1,"prev":1,"total":2}
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
         * items : [{"create_time":"1515056711","exchange_coin":"4.00","exchange_dot":"2.00"},{"create_time":"1515056630","exchange_coin":"4.00","exchange_dot":"2.00"}]
         * next : 1
         * page : 1
         * pagesize : 20
         * pagetotal : 1
         * prev : 1
         * total : 2
         */

        private int next;
        private int page;
        private int pagesize;
        private int pagetotal;
        private int prev;
        private int total;
        private String  total_dot;
        private List<ItemsEntity> items;

        public String getTotal_dot() {
            return total_dot;
        }

        public void setTotal_dot(String total_dot) {
            this.total_dot = total_dot;
        }

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
             * create_time : 1515056711
             * exchange_coin : 4.00
             * exchange_dot : 2.00
             */

            private String create_time;
            private String exchange_coin;
            private String exchange_dot;

            public void setCreate_time(String create_time) {
                this.create_time = create_time;
            }

            public void setExchange_coin(String exchange_coin) {
                this.exchange_coin = exchange_coin;
            }

            public void setExchange_dot(String exchange_dot) {
                this.exchange_dot = exchange_dot;
            }

            public String getCreate_time() {
                return create_time;
            }

            public String getExchange_coin() {
                return exchange_coin;
            }

            public String getExchange_dot() {
                return exchange_dot;
            }
        }
    }
}
