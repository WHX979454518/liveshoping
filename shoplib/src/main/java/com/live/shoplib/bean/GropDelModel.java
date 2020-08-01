package com.live.shoplib.bean;

import com.hn.library.http.BaseResponseModel;

/**
 * Created by Administrator on 2018/4/26.
 */

public class GropDelModel extends BaseResponseModel {


    private DBean d;


    public static class DBean {
        private String success_total;
        private String fail_total;

        public String getSuccess_total() {
            return success_total;
        }

        public void setSuccess_total(String success_total) {
            this.success_total = success_total;
        }

        public String getFail_total() {
            return fail_total;
        }

        public void setFail_total(String fail_total) {
            this.fail_total = fail_total;
        }
    }

    public DBean getD() {
        return d;
    }

    public void setD(DBean d) {
        this.d = d;
    }
}
