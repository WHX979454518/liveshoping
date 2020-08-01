package com.hotniao.live.model;

import com.hn.library.http.BaseResponseModel;

import java.util.List;

/**
 * 作者：Mr.Xu
 * 时间：2018/2/5
 */
public class LiveRecommendModel extends BaseResponseModel{


    /**
     * d : {"itmes":[{"live_preview_id":"13","live_preview_time":"1517829545","live_preview_title":"1","store_name":"","user_id":"10004","user_nickname":"134"}]}
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
         * itmes : [{"live_preview_id":"13","live_preview_time":"1517829545","live_preview_title":"1","store_name":"","user_id":"10004","user_nickname":"134"}]
         */

        private List<ItmesEntity> itmes;

        public void setItmes(List<ItmesEntity> itmes) {
            this.itmes = itmes;
        }

        public List<ItmesEntity> getItmes() {
            return itmes;
        }

        public static class ItmesEntity {

            /**
             * live_preview_id : 13
             * live_preview_time : 1517829545
             * live_preview_title : 1
             * store_name :
             * user_id : 10004
             * user_nickname : 134
             */

            private String live_preview_id;
            private String store_id;
            private String live_preview_time;
            private String live_preview_title;
            private String store_name;
            private String user_avatar;
            private String user_id;
            private String user_nickname;

            public String getStore_id() {
                return store_id;
            }

            public void setStore_id(String store_id) {
                this.store_id = store_id;
            }

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

            public void setUser_id(String user_id) {
                this.user_id = user_id;
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

            public String getUser_id() {
                return user_id;
            }

            public String getUser_nickname() {
                return user_nickname;
            }
        }
    }
}
