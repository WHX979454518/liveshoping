package com.live.shoplib.bean;

import com.hn.library.http.BaseResponseModel;

import java.util.List;

/**
 * Created by Administrator on 2018/4/18.
 */

public class GoodServerModel extends BaseResponseModel {


    /**
     * d : {"promises":[{"id":"1","name":"7天包退"},{"id":"2","name":"15天包换"},{"id":"3","name":"退货赔运费"},{"id":"4","name":"包邮"}]}
     */

    private DBean d;

    public DBean getD() {
        return d;
    }

    public void setD(DBean d) {
        this.d = d;
    }

    public static class DBean {
        private List<PromisesBean> promises;

        public List<PromisesBean> getPromises() {
            return promises;
        }

        public void setPromises(List<PromisesBean> promises) {
            this.promises = promises;
        }

        public static class PromisesBean {
            /**
             * id : 1
             * name : 7天包退
             */

            private String id;
            private String name;
            private boolean check;

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
