package com.hotniao.live.model;

import com.hn.library.http.BaseResponseModel;

import java.util.List;

/**
 * 作者：Mr.Xu
 * 时间：2018/1/11
 */
public class LittleVideoModel extends BaseResponseModel {


    private DEntity d;

    public void setD(DEntity d) {
        this.d = d;
    }

    public DEntity getD() {
        return d;
    }

    public static class DEntity {

        private List<LittleVideoItem> items;


        public List<LittleVideoItem> getItems() {
            return items;
        }

        public void setItems(List<LittleVideoItem> items) {
            this.items = items;
        }
    }

    public class LittleVideoItem {
        private String user_id;

        private String user_avatar;

        private String create_time;

        private String videos_id;
        private String article_id;

        private String title;

        private String praise_user_arr;

        private String face;

        private String comment_num;

        private String praise_num;

        private String user_nickname;

        private String content;

        private int has_praise;


        public String getArticle_id() {
            return article_id;
        }

        public void setArticle_id(String article_id) {
            this.article_id = article_id;
        }

        public String getUser_avatar() {
            return user_avatar;
        }

        public void setUser_avatar(String user_avatar) {
            this.user_avatar = user_avatar;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getUser_nickname() {
            return user_nickname;
        }

        public void setUser_nickname(String user_nickname) {
            this.user_nickname = user_nickname;
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

        //接口返回为11位的所以在此加000
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

        public void setHas_praise(int has_praise) {
            this.has_praise = has_praise;
        }

        public int getHas_praise() {
            return this.has_praise;
        }
    }


}
