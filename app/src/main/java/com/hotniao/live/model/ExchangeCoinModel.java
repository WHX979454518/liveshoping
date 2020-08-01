package com.hotniao.live.model;

import com.hn.library.http.BaseResponseModel;

import java.util.List;

/**
 * 作者：Mr.Xu
 * 时间：2018/1/5
 */
public class ExchangeCoinModel extends BaseResponseModel{


    /**
     * d : {"list":[{"dot":"100.00","id":"1","to_coin":"10.00"},{"dot":"200.00","id":"2","to_coin":"20.00"},{"dot":"3000.00","id":"3","to_coin":"300.00"}],"radio":"2"}
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
         * list : [{"dot":"100.00","id":"1","to_coin":"10.00"},{"dot":"200.00","id":"2","to_coin":"20.00"},{"dot":"3000.00","id":"3","to_coin":"300.00"}]
         * radio : 2
         */

        private String radio;
        private List<ListEntity> list;

        public void setRadio(String radio) {
            this.radio = radio;
        }

        public void setList(List<ListEntity> list) {
            this.list = list;
        }

        public String getRadio() {
            return radio;
        }

        public List<ListEntity> getList() {
            return list;
        }

        public static class ListEntity {
            /**
             * dot : 100.00
             * id : 1
             * to_coin : 10.00
             */

            private String dot;
            private String id;
            private String to_coin;

            public void setDot(String dot) {
                this.dot = dot;
            }

            public void setId(String id) {
                this.id = id;
            }

            public void setTo_coin(String to_coin) {
                this.to_coin = to_coin;
            }

            public String getDot() {
                return dot;
            }

            public String getId() {
                return id;
            }

            public String getTo_coin() {
                return to_coin;
            }
        }
    }
}
