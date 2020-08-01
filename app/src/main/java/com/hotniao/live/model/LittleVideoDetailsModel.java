package com.hotniao.live.model;

import com.hn.library.http.BaseResponseModel;

/**
 * 作者：Mr.Xu
 * 时间：2018/2/6
 */
public class LittleVideoDetailsModel extends BaseResponseModel {

    private DEntity d;

    public void setD(DEntity d) {
        this.d = d;
    }

    public DEntity getD() {
        return d;
    }

    public static class DEntity {
        private String user_nickname;

        private String url;

        private String user_avatar;

        private String user_id;

        private String create_time;

        private String videos_id;
        private String article_id;

        private String title;
        private String content;

        private String praise_user_arr;

        private String face;

        private String comment_num;

        private String praise_num;

        private String hot_num;

        private int has_praise;

        private int has_follow;

        public int getHas_follow() {
            return has_follow;
        }

        public boolean isFollow() {
            return has_follow == 1;
        }

        public void setHas_follow(int has_follow) {
            this.has_follow = has_follow;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getArticle_id() {
            return article_id;
        }

        public void setArticle_id(String article_id) {
            this.article_id = article_id;
        }

        public void setUser_nickname(String user_nickname) {
            this.user_nickname = user_nickname;
        }

        public String getUser_nickname() {
            return this.user_nickname;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getUrl() {
            return this.url;
        }

        public void setUser_avatar(String user_avatar) {
            this.user_avatar = user_avatar;
        }

        public String getUser_avatar() {
            return this.user_avatar;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public String getUser_id() {
            return this.user_id;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        public String getCreate_time() {
            return this.create_time+"000";
        }

        public void setVideos_id(String videos_id) {
            this.videos_id = videos_id;
        }

        public String getVideos_id() {
            return this.videos_id;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getTitle() {
            return this.title;
        }

        public void setPraise_user_arr(String praise_user_arr) {
            this.praise_user_arr = praise_user_arr;
        }

        public String getPraise_user_arr() {
            return this.praise_user_arr;
        }

        public void setFace(String face) {
            this.face = face;
        }

        public String getFace() {
            return this.face;
        }

        public void setComment_num(String comment_num) {
            this.comment_num = comment_num;
        }

        public String getComment_num() {
            return this.comment_num;
        }

        public void setPraise_num(String praise_num) {
            this.praise_num = praise_num;
        }

        public String getPraise_num() {
            return this.praise_num;
        }

        public void setHot_num(String hot_num) {
            this.hot_num = hot_num;
        }

        public String getHot_num() {
            return this.hot_num;
        }

        public void setHas_praise(int has_praise) {
            this.has_praise = has_praise;
        }

        public int getHas_praise() {
            return this.has_praise;
        }
    }


}
