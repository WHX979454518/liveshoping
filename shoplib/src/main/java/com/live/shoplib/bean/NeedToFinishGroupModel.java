package com.live.shoplib.bean;

import com.hn.library.http.BaseResponseModel;

import java.util.List;

/**
 * 作者：Mr.Xu
 * 时间：2017/12/21
 */
public class NeedToFinishGroupModel extends BaseResponseModel {


    private DEntity d;

    public void setD(DEntity d) {
        this.d = d;
    }

    public DEntity getD() {
        return d;
    }

    public static class DEntity {
        /**
         * "group_order_ids": [
         *             {
         *                 "group_order_id": 1, // 团购订单id
         *                 "user_info": {  // 用户信息
         *                     "user_avatar": "url", // 用户头像
         *                     "user_nickname": "阿花" // 用户昵称
         *                 },
         *                 "finish_time": 1560742255000, // 订单超时时间
         *                 "is_complete": 0 // 是否成团
         *             },
         *         ]
         *     }
         */

        private List<GroupListItem> group_order_ids;

        public List<GroupListItem> getGroup_order_ids() {
            return group_order_ids;
        }

        public void setGroup_order_ids(List<GroupListItem> group_order_ids) {
            this.group_order_ids = group_order_ids;
        }

        public static class GroupListItem {

            private String group_order_id;
            private UserInfo user_info;
            private Long finish_time;
            private int is_complete;
            private int last_num;

            public int getLast_num() {
                return last_num;
            }

            public void setLast_num(int last_num) {
                this.last_num = last_num;
            }

            public String getGroup_order_id() {
                return group_order_id;
            }

            public void setGroup_order_id(String group_order_id) {
                this.group_order_id = group_order_id;
            }

            public UserInfo getUser_info() {
                return user_info;
            }

            public void setUser_info(UserInfo user_info) {
                this.user_info = user_info;
            }

            public Long getFinish_time() {
                return finish_time;
            }

            public void setFinish_time(Long finish_time) {
                this.finish_time = finish_time;
            }

            public boolean getIs_complete() {
                return is_complete==1;
            }

            public void setIs_complete(int is_complete) {
                this.is_complete = is_complete;
            }
        }

        public static class UserInfo {

            private String user_avatar;
            private String user_nickname;

            public String getUser_nickname() {
                return user_nickname;
            }

            public void setUser_nickname(String user_nickname) {
                this.user_nickname = user_nickname;
            }

            public String getUser_avatar() {
                return user_avatar;
            }

            public void setUser_avatar(String user_avatar) {
                this.user_avatar = user_avatar;
            }
        }

    }
}
