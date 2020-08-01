package com.hotniao.livelibrary.model;

/**
 * 作者：Mr.Xu
 * 时间：2018/1/26
 */
public class HnOrderModel {


    /**
     * data : {"store_order_num":"测试内容t5m5"}
     * msg :
     * type : total_order
     */

    private DataEntity data;
    private String msg;
    private String type;

    public void setData(DataEntity data) {
        this.data = data;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setType(String type) {
        this.type = type;
    }

    public DataEntity getData() {
        return data;
    }

    public String getMsg() {
        return msg;
    }

    public String getType() {
        return type;
    }

    public static class DataEntity {
        /**
         * store_order_num : 测试内容t5m5
         */

        private String store_total_order;

        public String getStore_total_order() {
            return store_total_order;
        }

        public void setStore_total_order(String store_total_order) {
            this.store_total_order = store_total_order;
        }
    }
}
