package com.live.shoplib.bean;

import com.google.gson.annotations.SerializedName;
import com.hn.library.http.BaseResponseModel;

/**
 * 作者：Mr.Xu
 * 时间：2018/1/10
 */
public class WxPayModel extends BaseResponseModel{


    /**
     * d : {"data":{"appid":"wx540e507519d3971c","partnerid":"1494345302","prepayid":"wx20180110183603d1d1b2509c0512531505","package":"Sign=WXPay","noncestr":"q3O1vcKvKkvaLlS1","timestamp":1515580563,"sign":"6AECC7B834E9311D3597855FA2102CD4"}}
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
         * data : {"appid":"wx540e507519d3971c","partnerid":"1494345302","prepayid":"wx20180110183603d1d1b2509c0512531505","package":"Sign=WXPay","noncestr":"q3O1vcKvKkvaLlS1","timestamp":1515580563,"sign":"6AECC7B834E9311D3597855FA2102CD4"}
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
             * partnerid : 1494345302
             * prepayid : wx20180110183603d1d1b2509c0512531505
             * package : Sign=WXPay
             * noncestr : q3O1vcKvKkvaLlS1
             * timestamp : 1515580563
             * sign : 6AECC7B834E9311D3597855FA2102CD4
             */

            private String appid;
            private String partnerid;
            private String prepayid;
            @SerializedName("package")
            private String packageX;
            private String noncestr;
            private int timestamp;
            private String sign;

            public void setAppid(String appid) {
                this.appid = appid;
            }

            public void setPartnerid(String partnerid) {
                this.partnerid = partnerid;
            }

            public void setPrepayid(String prepayid) {
                this.prepayid = prepayid;
            }

            public void setPackageX(String packageX) {
                this.packageX = packageX;
            }

            public void setNoncestr(String noncestr) {
                this.noncestr = noncestr;
            }

            public void setTimestamp(int timestamp) {
                this.timestamp = timestamp;
            }

            public void setSign(String sign) {
                this.sign = sign;
            }

            public String getAppid() {
                return appid;
            }

            public String getPartnerid() {
                return partnerid;
            }

            public String getPrepayid() {
                return prepayid;
            }

            public String getPackageX() {
                return packageX;
            }

            public String getNoncestr() {
                return noncestr;
            }

            public int getTimestamp() {
                return timestamp;
            }

            public String getSign() {
                return sign;
            }
        }
    }
}
