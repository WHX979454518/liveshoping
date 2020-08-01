package com.hotniao.live.model;

import com.hn.library.http.BaseResponseModel;

import java.io.Serializable;
import java.util.List;

/**
 * 作者：Mr.Xu
 * 时间：2018/1/11
 */
public class ClassificationModel extends BaseResponseModel{

    private List<DBean> d;

    public List<DBean> getD() {
        return d;
    }

    public void setD(List<DBean> d) {
        this.d = d;
    }

    public static class DBean implements Serializable {
        /**
         * category_id : 1
         * field : 精选
         * create_time : 1561947782
         */

        private String category_id;
        private String field;
        private String create_time;

        public String getCategory_id() {
            return category_id;
        }

        public void setCategory_id(String category_id) {
            this.category_id = category_id;
        }

        public String getField() {
            return field;
        }

        public void setField(String field) {
            this.field = field;
        }

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }
    }
}
