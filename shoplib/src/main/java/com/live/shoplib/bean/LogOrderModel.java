package com.live.shoplib.bean;

import com.hn.library.http.BaseResponseModel;

import java.util.List;

/**
 * 作者：Mr.Xu
 * 时间：2018/1/8
 */
public class LogOrderModel extends BaseResponseModel{



    /**
     * d : {"logistic_code":"3101449726243","shipper_name":"韵达快递","state":"3","traces":[{"AcceptStation":"到达：云南富宁县公司 已揽件","AcceptTime":"2017-11-10 18:08:10"},{"AcceptStation":"到达：云南富宁县公司 已揽件","AcceptTime":"2017-11-12 11:56:08"},{"AcceptStation":"到达：云南昆明分拨中心","AcceptTime":"2017-11-12 23:28:38"},{"AcceptStation":"到达：云南昆明分拨中心 发往：广东广州分拨中心","AcceptTime":"2017-11-12 23:30:53"},{"AcceptStation":"到达：广东广州分拨中心 上级站点：云南昆明分拨中心","AcceptTime":"2017-11-13 22:16:36"},{"AcceptStation":"到达：广东广州分拨中心 发往：广东佛山分拨中心","AcceptTime":"2017-11-13 23:11:41"},{"AcceptStation":"到达：广东佛山分拨中心 发往：广东顺德区公司","AcceptTime":"2017-11-14 05:24:11"},{"AcceptStation":"到达：广东顺德区公司 发往：广东顺德区公司勒流振兴分部(0757-25532258)","AcceptTime":"2017-11-14 05:26:31"},{"AcceptStation":"到达：广东顺德区公司勒流振兴分部 上级站点：广东顺德区公司 发往：","AcceptTime":"2017-11-14 06:40:03"},{"AcceptStation":"到达：广东顺德区公司勒流振兴分部 指定：陈少平(13534421035) 派送","AcceptTime":"2017-11-14 09:36:10"},{"AcceptStation":"到达：广东顺德区公司勒流振兴分部 由 已签收 签收","AcceptTime":"2017-11-14 23:11:56"}]}
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
         * logistic_code : 3101449726243
         * shipper_name : 韵达快递
         * state : 3
         * traces : [{"AcceptStation":"到达：云南富宁县公司 已揽件","AcceptTime":"2017-11-10 18:08:10"},{"AcceptStation":"到达：云南富宁县公司 已揽件","AcceptTime":"2017-11-12 11:56:08"},{"AcceptStation":"到达：云南昆明分拨中心","AcceptTime":"2017-11-12 23:28:38"},{"AcceptStation":"到达：云南昆明分拨中心 发往：广东广州分拨中心","AcceptTime":"2017-11-12 23:30:53"},{"AcceptStation":"到达：广东广州分拨中心 上级站点：云南昆明分拨中心","AcceptTime":"2017-11-13 22:16:36"},{"AcceptStation":"到达：广东广州分拨中心 发往：广东佛山分拨中心","AcceptTime":"2017-11-13 23:11:41"},{"AcceptStation":"到达：广东佛山分拨中心 发往：广东顺德区公司","AcceptTime":"2017-11-14 05:24:11"},{"AcceptStation":"到达：广东顺德区公司 发往：广东顺德区公司勒流振兴分部(0757-25532258)","AcceptTime":"2017-11-14 05:26:31"},{"AcceptStation":"到达：广东顺德区公司勒流振兴分部 上级站点：广东顺德区公司 发往：","AcceptTime":"2017-11-14 06:40:03"},{"AcceptStation":"到达：广东顺德区公司勒流振兴分部 指定：陈少平(13534421035) 派送","AcceptTime":"2017-11-14 09:36:10"},{"AcceptStation":"到达：广东顺德区公司勒流振兴分部 由 已签收 签收","AcceptTime":"2017-11-14 23:11:56"}]
         */

        private String logistic_code;
        private String shipper_name;
        private String state;
        private List<TracesEntity> traces;

        public void setLogistic_code(String logistic_code) {
            this.logistic_code = logistic_code;
        }

        public void setShipper_name(String shipper_name) {
            this.shipper_name = shipper_name;
        }

        public void setState(String state) {
            this.state = state;
        }

        public void setTraces(List<TracesEntity> traces) {
            this.traces = traces;
        }

        public String getLogistic_code() {
            return logistic_code;
        }

        public String getShipper_name() {
            return shipper_name;
        }

        public String getState() {
            return state;
        }

        public List<TracesEntity> getTraces() {
            return traces;
        }

        public static class TracesEntity {
            /**
             * AcceptStation : 到达：云南富宁县公司 已揽件
             * AcceptTime : 2017-11-10 18:08:10
             */

            private String AcceptStation;
            private String AcceptTime;

            public void setAcceptStation(String AcceptStation) {
                this.AcceptStation = AcceptStation;
            }

            public void setAcceptTime(String AcceptTime) {
                this.AcceptTime = AcceptTime;
            }

            public String getAcceptStation() {
                return AcceptStation;
            }

            public String getAcceptTime() {
                return AcceptTime;
            }
        }
    }
}
