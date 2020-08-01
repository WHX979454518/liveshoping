package com.live.shoplib.bean;

import com.hn.library.http.BaseResponseModel;

/**
 * 作者：Mr.Xu
 * 时间：2018/1/22
 */
public class RuleModel extends BaseResponseModel{


    /**
     * d : {"content":"<p><strong>打发<\/strong><\/p>"}
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
         * content : <p><strong>打发</strong></p>
         */

        private String content;

        public void setContent(String content) {
            this.content = content;
        }

        public String getContent() {
            return content;
        }
    }
}
