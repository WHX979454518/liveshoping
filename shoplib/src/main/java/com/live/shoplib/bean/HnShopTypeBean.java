package com.live.shoplib.bean;

import com.hn.library.http.BaseResponseModel;

import java.util.List;

/**
 * create by Mr.x
 * on 2018/7/2
 */

public class HnShopTypeBean extends BaseResponseModel {


    /**
     * d : {"list":[{"c_time":"1530321031","icon":"http://redbird-zbds-1252571077.image.myqcloud.com/image/20180629/1530262436905139.png","id":"14","is_show":"1","name":"分类1_1_1","pid":"13","rank":"2","recommend":"1","recommend_time":"1530321031","status":"1","weigh":"0"},{"c_time":"1530320977","icon":"http://redbird-zbds-1252571077.image.myqcloud.com/image/20180630/1530320975341587.png","id":"17","is_show":"1","name":"分类2_1_1","pid":"16","rank":"2","recommend":"1","recommend_time":"1530320977","status":"1","weigh":"0"},{"c_time":"1530321296","icon":"http://redbird-zbds-1252571077.image.myqcloud.com/image/20180630/1530321295985208.png","id":"21","is_show":"1","name":"分类3_1_1","pid":"20","rank":"2","recommend":"1","recommend_time":"1530321296","status":"1","weigh":"0"}]}
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
             * c_time : 1530321031
             * icon : http://redbird-zbds-1252571077.image.myqcloud.com/image/20180629/1530262436905139.png
             * id : 14
             * is_show : 1
             * name : 分类1_1_1
             * pid : 13
             * rank : 2
             * recommend : 1
             * recommend_time : 1530321031
             * status : 1
             * weigh : 0
             */

            private String c_time;
            private String icon;
            private String id;
            private String is_show;
            private String name;
            private String pid;
            private String rank;
            private String recommend;
            private String recommend_time;
            private String status;
            private String weigh;

            public String getC_time() {
                return c_time;
            }

            public void setC_time(String c_time) {
                this.c_time = c_time;
            }

            public String getIcon() {
                return icon;
            }

            public void setIcon(String icon) {
                this.icon = icon;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getIs_show() {
                return is_show;
            }

            public void setIs_show(String is_show) {
                this.is_show = is_show;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getPid() {
                return pid;
            }

            public void setPid(String pid) {
                this.pid = pid;
            }

            public String getRank() {
                return rank;
            }

            public void setRank(String rank) {
                this.rank = rank;
            }

            public String getRecommend() {
                return recommend;
            }

            public void setRecommend(String recommend) {
                this.recommend = recommend;
            }

            public String getRecommend_time() {
                return recommend_time;
            }

            public void setRecommend_time(String recommend_time) {
                this.recommend_time = recommend_time;
            }

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }

            public String getWeigh() {
                return weigh;
            }

            public void setWeigh(String weigh) {
                this.weigh = weigh;
            }
        }
    }
}
