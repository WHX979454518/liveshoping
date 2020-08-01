package com.hotniao.live.model;

import com.hn.library.http.BaseResponseModel;

import java.util.List;

/**
 * 首页分类
 * 作者：Mr.X
 * 时间：11:06
 */
public class HnHomeLiveCateModel extends BaseResponseModel {


    /**
     * d : [{"icon":"3","id":"3","is_food":"0","name":"第一个类型"},{"icon":"4","id":"4","is_food":"0","name":"第2个类型"}]
     */

    private List<DEntity> d;

    public void setD(List<DEntity> d) {
        this.d = d;
    }

    public List<DEntity> getD() {
        return d;
    }

    public static class DEntity {
        /**
         * icon : 3
         * id : 3
         * is_food : 0
         * name : 第一个类型
         */

        private String icon;
        private String id;
        private String is_food;
        private String name;

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public void setId(String id) {
            this.id = id;
        }

        public void setIs_food(String is_food) {
            this.is_food = is_food;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getIcon() {
            return icon;
        }

        public String getId() {
            return id;
        }

        public String getIs_food() {
            return is_food;
        }

        public String getName() {
            return name;
        }
    }
}
