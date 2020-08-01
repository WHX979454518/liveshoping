package com.live.shoplib.bean;

import com.hn.library.http.BaseResponseModel;

import java.util.List;

/**
 * 作者：Mr.Xu
 * 时间：2017/12/22
 */
public class LogisticsModel extends BaseResponseModel {


    /**
     * d : [{"code":"AJ","id":"1","name":"安捷快递"},{"code":"AXD","id":"3","name":"安信达快递"},{"code":"EMS","id":"15","name":"EMS"},{"code":"SF","id":"56","name":"顺丰快递"},{"code":"STO","id":"60","name":"申通快递"},{"code":"YTO","id":"80","name":"圆通速递"},{"code":"ZTO","id":"88","name":"中通速递"}]
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
         * code : AJ
         * id : 1
         * name : 安捷快递
         */

        private String code;
        private String id;
        private String name;

        public void setCode(String code) {
            this.code = code;
        }

        public void setId(String id) {
            this.id = id;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getCode() {
            return code;
        }

        public String getId() {
            return id;
        }

        public String getName() {
            return name;
        }
    }
}
