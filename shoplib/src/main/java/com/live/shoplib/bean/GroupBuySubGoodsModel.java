package com.live.shoplib.bean;

import com.hn.library.http.BaseResponseModel;

/**
 * 作者：Mr.Xu
 * 时间：2017/12/28
 */
public class GroupBuySubGoodsModel extends BaseResponseModel{


    /**
     * d : {"emptyIdentifier":"172"}
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
         * emptyIdentifier : 172
         */

        private String order_id;
        private String group_order_id;

        public String getOrder_id() {
            return order_id;
        }

        public void setOrder_id(String order_id) {
            this.order_id = order_id;
        }


        public String getGroup_order_id() {
            return group_order_id;
        }

        public void setGroup_order_id(String group_order_id) {
            this.group_order_id = group_order_id;
        }
    }
}
