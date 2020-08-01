package com.hotniao.live.model.bean;

import com.hn.library.http.BaseResponseModel;

import java.util.List;

/**
 * 作者：Mr.Xu
 * 时间：2017/12/26
 */
public class StoreAutListModel extends BaseResponseModel{

    /**
     * d : [{"icon":"测试内容ud18","id":"3","name":"第一个类型"},{"icon":"测试内容ud18","id":"4","name":"第2个类型"}]
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
         * icon : 测试内容ud18
         * id : 3
         * name : 第一个类型
         */

        private String icon;
        private String id;
        private String is_food;
        private String name;
        private boolean check;

        public String getIs_food() {
            return is_food;
        }

        public void setIs_food(String is_food) {
            this.is_food = is_food;
        }

        public boolean isCheck() {
            return check;
        }

        public void setCheck(boolean check) {
            this.check = check;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public void setId(String id) {
            this.id = id;
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

        public String getName() {
            return name;
        }
    }
}
