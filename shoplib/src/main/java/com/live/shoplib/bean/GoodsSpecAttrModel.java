package com.live.shoplib.bean;

import com.hn.library.http.BaseResponseModel;

import java.util.ArrayList;
import java.util.List;

/**
 * create by Mr.x
 * on 2018/5/3
 */

public class GoodsSpecAttrModel extends BaseResponseModel{


    /**
     * d : {"list":[{"value":["S码","M码","L码","XL码"],"field":"尺寸1","id":"3"},{"value":["S码","M码","L码","XL码"],"field":"颜色","id":"4"}]}
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
             * value : ["S码","M码","L码","XL码"]
             * field : 尺寸1
             * id : 3
             */

            private String field;
            private String id;
            private ArrayList<String> value;
            private boolean check = false;

            public boolean isCheck() {
                return check;
            }

            public void setCheck(boolean check) {
                this.check = check;
            }

            public String getField() {
                return field;
            }

            public void setField(String field) {
                this.field = field;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public ArrayList<String> getValue() {
                return value;
            }

            public void setValue(ArrayList<String> value) {
                this.value = value;
            }
        }
    }
}
