package com.hotniao.live.model;

import com.hn.library.http.BaseResponseModel;

import java.util.List;

/**
 * 作者：Mr.Xu
 * 时间：2018/2/5
 */
public class HnHomeModel extends BaseResponseModel{


    /**
     * d : {"official":[{"official_news_id":"2","official_news_logo":"http://boyazhibo-1255530974.picgz.myqcloud.com/image/20180129/1517190890353390.png","official_news_title":"aasdfvasd","official_news_update_time":"1517190900","share":"http://h5.boyazhibo.com/officialDetails?id=2"},{"official_news_id":"1","official_news_logo":"http://boyazhibo-1255530974.picgz.myqcloud.com/image/20180129/1517190665322353.png","official_news_title":"qweqwe","official_news_update_time":"1517190670","share":"http://h5.boyazhibo.com/officialDetails?id=1"}],"preview":[{"live_preview_id":"13","live_preview_time":"1517818643","live_preview_title":"1","store_name":"","user_nickname":"134"},{"live_preview_id":"3","live_preview_time":"1517816643","live_preview_title":"1","store_name":"","user_nickname":"134"},{"live_preview_id":"4","live_preview_time":"1517816643","live_preview_title":"1","store_name":"","user_nickname":"134"},{"live_preview_id":"5","live_preview_time":"1517816643","live_preview_title":"1","store_name":"","user_nickname":"134"},{"live_preview_id":"6","live_preview_time":"1517816643","live_preview_title":"1","store_name":"","user_nickname":"134"},{"live_preview_id":"7","live_preview_time":"1517816643","live_preview_title":"1","store_name":"","user_nickname":"134"},{"live_preview_id":"8","live_preview_time":"1517816643","live_preview_title":"1","store_name":"","user_nickname":"134"},{"live_preview_id":"9","live_preview_time":"1517816643","live_preview_title":"1","store_name":"","user_nickname":"134"},{"live_preview_id":"10","live_preview_time":"1517816643","live_preview_title":"1","store_name":"","user_nickname":"134"}],"type":[{"id":"4","name":"母婴","recommend_image":"http://boyazhibo-1255530974.picgz.myqcloud.com/image/20180205/1517810288271142.jpg"}]}
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
         * official : [{"official_news_id":"2","official_news_logo":"http://boyazhibo-1255530974.picgz.myqcloud.com/image/20180129/1517190890353390.png","official_news_title":"aasdfvasd","official_news_update_time":"1517190900","share":"http://h5.boyazhibo.com/officialDetails?id=2"},{"official_news_id":"1","official_news_logo":"http://boyazhibo-1255530974.picgz.myqcloud.com/image/20180129/1517190665322353.png","official_news_title":"qweqwe","official_news_update_time":"1517190670","share":"http://h5.boyazhibo.com/officialDetails?id=1"}]
         * preview : [{"live_preview_id":"13","live_preview_time":"1517818643","live_preview_title":"1","store_name":"","user_nickname":"134"},{"live_preview_id":"3","live_preview_time":"1517816643","live_preview_title":"1","store_name":"","user_nickname":"134"},{"live_preview_id":"4","live_preview_time":"1517816643","live_preview_title":"1","store_name":"","user_nickname":"134"},{"live_preview_id":"5","live_preview_time":"1517816643","live_preview_title":"1","store_name":"","user_nickname":"134"},{"live_preview_id":"6","live_preview_time":"1517816643","live_preview_title":"1","store_name":"","user_nickname":"134"},{"live_preview_id":"7","live_preview_time":"1517816643","live_preview_title":"1","store_name":"","user_nickname":"134"},{"live_preview_id":"8","live_preview_time":"1517816643","live_preview_title":"1","store_name":"","user_nickname":"134"},{"live_preview_id":"9","live_preview_time":"1517816643","live_preview_title":"1","store_name":"","user_nickname":"134"},{"live_preview_id":"10","live_preview_time":"1517816643","live_preview_title":"1","store_name":"","user_nickname":"134"}]
         * type : [{"id":"4","name":"母婴","recommend_image":"http://boyazhibo-1255530974.picgz.myqcloud.com/image/20180205/1517810288271142.jpg"}]
         */

        private List<OfficialEntity> official;
        private List<PreviewEntity> preview;
        private List<TypeEntity> type;

        public void setOfficial(List<OfficialEntity> official) {
            this.official = official;
        }

        public void setPreview(List<PreviewEntity> preview) {
            this.preview = preview;
        }

        public void setType(List<TypeEntity> type) {
            this.type = type;
        }

        public List<OfficialEntity> getOfficial() {
            return official;
        }

        public List<PreviewEntity> getPreview() {
            return preview;
        }

        public List<TypeEntity> getType() {
            return type;
        }

        public static class OfficialEntity {
            /**
             * official_news_id : 2
             * official_news_logo : http://boyazhibo-1255530974.picgz.myqcloud.com/image/20180129/1517190890353390.png
             * official_news_title : aasdfvasd
             * official_news_update_time : 1517190900
             * share : http://h5.boyazhibo.com/officialDetails?id=2
             */

            private String official_news_id;
            private String official_news_logo;
            private String official_news_title;
            private String official_news_update_time;
            private String share;

            public void setOfficial_news_id(String official_news_id) {
                this.official_news_id = official_news_id;
            }

            public void setOfficial_news_logo(String official_news_logo) {
                this.official_news_logo = official_news_logo;
            }

            public void setOfficial_news_title(String official_news_title) {
                this.official_news_title = official_news_title;
            }

            public void setOfficial_news_update_time(String official_news_update_time) {
                this.official_news_update_time = official_news_update_time;
            }

            public void setShare(String share) {
                this.share = share;
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

            public String getShare() {
                return share;
            }
        }

        public static class PreviewEntity {
            /**
             * live_preview_id : 13
             * live_preview_time : 1517818643
             * live_preview_title : 1
             * store_name :
             * user_nickname : 134
             */

            private String live_preview_id;
            private String live_preview_time;
            private String live_preview_title;
            private String store_name;
            private String user_avatar;
            private String user_nickname;

            public String getUser_avatar() {
                return user_avatar;
            }

            public void setUser_avatar(String user_avatar) {
                this.user_avatar = user_avatar;
            }

            public void setLive_preview_id(String live_preview_id) {
                this.live_preview_id = live_preview_id;
            }

            public void setLive_preview_time(String live_preview_time) {
                this.live_preview_time = live_preview_time;
            }

            public void setLive_preview_title(String live_preview_title) {
                this.live_preview_title = live_preview_title;
            }

            public void setStore_name(String store_name) {
                this.store_name = store_name;
            }

            public void setUser_nickname(String user_nickname) {
                this.user_nickname = user_nickname;
            }

            public String getLive_preview_id() {
                return live_preview_id;
            }

            public String getLive_preview_time() {
                return live_preview_time;
            }

            public String getLive_preview_title() {
                return live_preview_title;
            }

            public String getStore_name() {
                return store_name;
            }

            public String getUser_nickname() {
                return user_nickname;
            }
        }

        public static class TypeEntity {
            /**
             * id : 4
             * name : 母婴
             * recommend_image : http://boyazhibo-1255530974.picgz.myqcloud.com/image/20180205/1517810288271142.jpg
             */

            private String id;
            private String name;
            private String recommend_image;

            public void setId(String id) {
                this.id = id;
            }

            public void setName(String name) {
                this.name = name;
            }

            public void setRecommend_image(String recommend_image) {
                this.recommend_image = recommend_image;
            }

            public String getId() {
                return id;
            }

            public String getName() {
                return name;
            }

            public String getRecommend_image() {
                return recommend_image;
            }
        }
    }
}
