package com.hotniao.live.model;

import com.hn.library.http.BaseResponseModel;

/**
 * 作者：Alan
 * 时间：2019/7/31
 */
public class CommentModel extends BaseResponseModel {

    public static final String COMMENT_VIDEO_TYPE = "1";
    public static final String COMMENT_ARTICLE_TYPE = "2";
    public static final String COMMENT_COMMENT_TYPE = "3";

    private DEntity d;

    public void setD(DEntity d) {
        this.d = d;
    }

    public DEntity getD() {
        return d;
    }

    public static class DEntity {

    }
}
