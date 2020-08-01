package com.hotniao.live.model;

import com.hn.library.http.BaseResponseModel;

/**
 * 作者：Alan
 * 时间：2019/7/31
 */
public class PraiseModel extends BaseResponseModel {

    public static final String ARTICLE_TYPE = "2";
    public static final String VIDEO_TYPE = "1";

    private DEntity d;

    public void setD(DEntity d) {
        this.d = d;
    }

    public DEntity getD() {
        return d;
    }

    public static class DEntity {
        private String praise_id;

        public String getPraise_id() {
            return praise_id;
        }

        public void setPraise_id(String praise_id) {
            this.praise_id = praise_id;
        }
    }
}
