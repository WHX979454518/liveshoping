package com.live.shoplib.bean;

import com.hn.library.http.BaseResponseModel;

import java.util.List;

/**
 * create by Mr.x
 * on 2018/5/3
 */

public class GoodsTypeModel extends BaseResponseModel{

    /**
     * d : {"list":[{"id":"3","name":"衣服1"},{"id":"7","name":"手机"},{"id":"22","name":"衣服2"}]}
     */

    private DBean d;

    public DBean getD() {
        return d;
    }

    public void setD(DBean d) {
        this.d = d;
    }

    public static class DBean {
        private List<ListBean> list;

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean {
            /**
             * id : 3
             * name : 衣服1
             */

            private String id;
            private String name;
            private boolean check = false;

            public boolean isCheck() {
                return check;
            }

            public void setCheck(boolean check) {
                this.check = check;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }
        }
    }
}
