package com.hotniao.live.model;

import com.hn.library.http.BaseResponseModel;

/**
 * 作者：Mr.Xu
 * 时间：2018/1/15
 */
public class OrderPayModel extends BaseResponseModel{


    /**
     * d : {"recharge_id":"380"}
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
         * recharge_id : 380
         */

        private String recharge_id;

        public void setRecharge_id(String recharge_id) {
            this.recharge_id = recharge_id;
        }

        public String getRecharge_id() {
            return recharge_id;
        }
    }
}
