package com.live.shoplib.bean;

import com.hn.library.http.BaseResponseModel;

/**
 * 作者：Mr.Xu
 * 时间：2018/1/22
 */
public class GoodsCarNumModel extends BaseResponseModel{



    /**
     * d : {"num":3}
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
         * num : 3
         */

        private int num;

        public void setNum(int num) {
            this.num = num;
        }

        public int getNum() {
            return num;
        }
    }
}
