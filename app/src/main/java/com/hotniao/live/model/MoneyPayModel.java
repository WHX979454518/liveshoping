package com.hotniao.live.model;

import com.google.gson.annotations.SerializedName;
import com.hn.library.http.BaseResponseModel;

/**
 * 作者：Mr.Xu
 * 时间：2018/1/15
 */
public class MoneyPayModel extends BaseResponseModel{


    /**
     * d : {"data":{"appid":"wx540e507519d3971c","noncestr":"TCZ6hSAhxH0DRlHV","package":"Sign=WXPay","partnerid":"1494345302","prepayid":"wx201801081920584dd48024090437995044","sign":"E85A3D1579DA6F78F9C788C23DA65218","timestamp":1515410458}}
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
         * data : {"appid":"wx540e507519d3971c","noncestr":"TCZ6hSAhxH0DRlHV","package":"Sign=WXPay","partnerid":"1494345302","prepayid":"wx201801081920584dd48024090437995044","sign":"E85A3D1579DA6F78F9C788C23DA65218","timestamp":1515410458}
         */

        private DataEntity data;

        public void setData(DataEntity data) {
            this.data = data;
        }

        public DataEntity getData() {
            return data;
        }

        public static class DataEntity {
            /**
             * appid : wx540e507519d3971c
             * noncestr : TCZ6hSAhxH0DRlHV
             * package : Sign=WXPay
             * partnerid : 1494345302
             * prepayid : wx201801081920584dd48024090437995044
             * sign : E85A3D1579DA6F78F9C788C23DA65218
             * timestamp : 1515410458
             */

            private String appid;
            private String noncestr;
            @SerializedName("package")
            private String packageX;
            private String partnerid;
            private String prepayid;
            private String sign;
            private int timestamp;

            public void setAppid(String appid) {
                this.appid = appid;
            }

            public void setNoncestr(String noncestr) {
                this.noncestr = noncestr;
            }

            public void setPackageX(String packageX) {
                this.packageX = packageX;
            }

            public void setPartnerid(String partnerid) {
                this.partnerid = partnerid;
            }

            public void setPrepayid(String prepayid) {
                this.prepayid = prepayid;
            }

            public void setSign(String sign) {
                this.sign = sign;
            }

            public void setTimestamp(int timestamp) {
                this.timestamp = timestamp;
            }

            public String getAppid() {
                return appid;
            }

            public String getNoncestr() {
                return noncestr;
            }

            public String getPackageX() {
                return packageX;
            }

            public String getPartnerid() {
                return partnerid;
            }

            public String getPrepayid() {
                return prepayid;
            }

            public String getSign() {
                return sign;
            }

            public int getTimestamp() {
                return timestamp;
            }
        }
    }
}
