package com.hotniao.livelibrary.model.bean;

import java.io.Serializable;
import java.util.List;

/**
 * @创建者 mj
 * @创建时间 2016/8/24 11:58
 * @描述 ${私信详情返回Data}
 */
public class PrivateLetterDetail implements Serializable {


    /**
     * user : {"is_follow":"N"}
     * user_dialog : [{"dialog_id":"0","from_user":{"user_avatar":"http://static.greenlive.1booker.com//upload/image/20171017/1508229933469682.png","user_id":"9","user_nickname":"天行"},"to_user":{"user_avatar":"","user_id":"8","user_nickname":"2"},"msg":"qwe","time":"1508826721"}]
     */

    private UserBean user;
    private List<UserDialogBean> user_dialog;

    public UserBean getUser() {
        return user;
    }

    public void setUser(UserBean user) {
        this.user = user;
    }

    public List<UserDialogBean> getUser_dialog() {
        return user_dialog;
    }

    public void setUser_dialog(List<UserDialogBean> user_dialog) {
        this.user_dialog = user_dialog;
    }

    public static class UserBean {
        /**
         * is_follow : N
         */

        private String is_follow;

        public String getIs_follow() {
            return is_follow;
        }

        public void setIs_follow(String is_follow) {
            this.is_follow = is_follow;
        }
    }



}
