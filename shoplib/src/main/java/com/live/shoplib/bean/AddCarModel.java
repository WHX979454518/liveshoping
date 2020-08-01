package com.live.shoplib.bean;

import com.hn.library.http.BaseResponseModel;

/**
 * 作者：Mr.Xu
 * 时间：2017/12/27
 */
public class AddCarModel extends BaseResponseModel {


    /**
     * d : {"cart_id":"11"}
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
         * cart_id : 11
         */

        private String cart_id;

        public void setCart_id(String cart_id) {
            this.cart_id = cart_id;
        }

        public String getCart_id() {
            return cart_id;
        }
    }
}
