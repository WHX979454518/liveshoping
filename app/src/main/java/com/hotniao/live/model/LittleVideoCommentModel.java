package com.hotniao.live.model;

import com.hn.library.http.BaseResponseModel;

import java.util.List;

/**
 * 作者：Mr.Xu
 * 时间：2018/2/6
 */
public class LittleVideoCommentModel extends BaseResponseModel {

    private DEntity d;

    public void setD(DEntity d) {
        this.d = d;
    }

    public DEntity getD() {
        return d;
    }

    public static class DEntity {
        private List<CommentItem> items;

        public List<CommentItem> getItems() {
            return items;
        }

        public void setItems(List<CommentItem> items) {
            this.items = items;
        }
    }

    public class CommentItem {

        private String comment_id;

        private String comment;

        private String create_time;

        private String user_nickname;

        private String user_avatar;

        private String comment_num;

        public void setComment_id(String comment_id) {
            this.comment_id = comment_id;
        }

        public String getComment_id() {
            return this.comment_id;
        }

        public void setComment(String comment) {
            this.comment = comment;
        }

        public String getComment() {
            return this.comment;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        public String getCreate_time() {
            return this.create_time;
        }

        public void setUser_nickname(String user_nickname) {
            this.user_nickname = user_nickname;
        }

        public String getUser_nickname() {
            return this.user_nickname;
        }

        public void setUser_avatar(String user_avatar) {
            this.user_avatar = user_avatar;
        }

        public String getUser_avatar() {
            return this.user_avatar;
        }

        public void setComment_num(String comment_num) {
            this.comment_num = comment_num;
        }

        public String getComment_num() {
            return this.comment_num;
        }

    }

}
