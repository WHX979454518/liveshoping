package com.hotniao.live.model;

import com.hn.library.http.BaseResponseModel;

import java.util.List;

/**
 * 作者：Mr.Xu
 * 时间：2018/2/2
 */
public class OfficeAnnoModel extends BaseResponseModel {


    /**
     * d : {"items":[{"share":"测试内容c0v5","official_news_id":"2","official_news_logo":"http://boyazhibo-1255530974.picgz.myqcloud.com/image/20180129/1517190890353390.png","official_news_title":"aasdfvasd","official_news_update_tiem":"1517190900"},{"share":"测试内容c0v5","official_news_id":"1","official_news_logo":"http://boyazhibo-1255530974.picgz.myqcloud.com/image/20180129/1517190665322353.png","official_news_title":"qweqwe","official_news_update_tiem":"1517190670"}]}
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
         * items : [{"share":"测试内容c0v5","official_news_id":"2","official_news_logo":"http://boyazhibo-1255530974.picgz.myqcloud.com/image/20180129/1517190890353390.png","official_news_title":"aasdfvasd","official_news_update_tiem":"1517190900"},{"share":"测试内容c0v5","official_news_id":"1","official_news_logo":"http://boyazhibo-1255530974.picgz.myqcloud.com/image/20180129/1517190665322353.png","official_news_title":"qweqwe","official_news_update_tiem":"1517190670"}]
         */

        private List<ItemsEntity> items;

        public void setItems(List<ItemsEntity> items) {
            this.items = items;
        }

        public List<ItemsEntity> getItems() {
            return items;
        }

        public static class ItemsEntity {
            /**
             * share : 测试内容c0v5
             * official_news_id : 2
             * official_news_logo : http://boyazhibo-1255530974.picgz.myqcloud.com/image/20180129/1517190890353390.png
             * official_news_title : aasdfvasd
             * official_news_update_tiem : 1517190900
             */

            private String share;
            private String official_news_id;
            private String official_news_logo;
            private String official_news_title;
            private String official_news_update_time;

            public void setShare(String share) {
                this.share = share;
            }

            public void setOfficial_news_id(String official_news_id) {
                this.official_news_id = official_news_id;
            }

            public void setOfficial_news_logo(String official_news_logo) {
                this.official_news_logo = official_news_logo;
            }

            public void setOfficial_news_title(String official_news_title) {
                this.official_news_title = official_news_title;
            }

            public String getShare() {
                return share;
            }

            public String getOfficial_news_id() {
                return official_news_id;
            }

            public String getOfficial_news_logo() {
                return official_news_logo;
            }

            public String getOfficial_news_title() {
                return official_news_title;
            }

            public String getOfficial_news_update_time() {
                return official_news_update_time;
            }

            public void setOfficial_news_update_time(String official_news_update_time) {
                this.official_news_update_time = official_news_update_time;
            }
        }
    }
}
