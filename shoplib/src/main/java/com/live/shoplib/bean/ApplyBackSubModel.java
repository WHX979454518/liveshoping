package com.live.shoplib.bean;

import com.hn.library.http.BaseResponseModel;

/**
 * 作者：Mr.Xu
 * 时间：2017/12/29
 */
public class ApplyBackSubModel extends BaseResponseModel{


    /**
     * d : {"refund_id":"22"}
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
         * refund_id : 22
         */

        private String refund_id;

        public void setRefund_id(String refund_id) {
            this.refund_id = refund_id;
        }

        public String getRefund_id() {
            return refund_id;
        }
    }
}
