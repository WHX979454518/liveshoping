package com.live.shoplib.bean;

import com.hn.library.http.BaseResponseModel;

import java.util.List;

/**
 * 作者：Mr.Xu
 * 时间：2018/1/3
 */
public class BackModel extends BaseResponseModel{



    /**
     * d : {"videos":{"items":[{"category_name":["NBA","免费"],"end_time":"0","image_url":"","onlines":"0","playback_url":"","start_time":"1508749383","time":"0","title":""}],"next":1,"page":1,"pagesize":20,"pagetotal":1,"prev":1,"total":1}}
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
         * videos : {"items":[{"category_name":["NBA","免费"],"end_time":"0","image_url":"","onlines":"0","playback_url":"","start_time":"1508749383","time":"0","title":""}],"next":1,"page":1,"pagesize":20,"pagetotal":1,"prev":1,"total":1}
         */

        private VideosEntity videos;

        public void setVideos(VideosEntity videos) {
            this.videos = videos;
        }

        public VideosEntity getVideos() {
            return videos;
        }

        public static class VideosEntity {
            /**
             * items : [{"category_name":["NBA","免费"],"end_time":"0","image_url":"","onlines":"0","playback_url":"","start_time":"1508749383","time":"0","title":""}]
             * next : 1
             * page : 1
             * pagesize : 20
             * pagetotal : 1
             * prev : 1
             * total : 1
             */

            private int next;
            private int page;
            private int pagesize;
            private int pagetotal;
            private int prev;
            private int total;
            private List<ItemsEntity> items;

            public void setNext(int next) {
                this.next = next;
            }

            public void setPage(int page) {
                this.page = page;
            }

            public void setPagesize(int pagesize) {
                this.pagesize = pagesize;
            }

            public void setPagetotal(int pagetotal) {
                this.pagetotal = pagetotal;
            }

            public void setPrev(int prev) {
                this.prev = prev;
            }

            public void setTotal(int total) {
                this.total = total;
            }

            public void setItems(List<ItemsEntity> items) {
                this.items = items;
            }

            public int getNext() {
                return next;
            }

            public int getPage() {
                return page;
            }

            public int getPagesize() {
                return pagesize;
            }

            public int getPagetotal() {
                return pagetotal;
            }

            public int getPrev() {
                return prev;
            }

            public int getTotal() {
                return total;
            }

            public List<ItemsEntity> getItems() {
                return items;
            }

            public static class ItemsEntity {
                /**
                 * category_name : ["NBA","免费"]
                 * end_time : 0
                 * image_url :
                 * onlines : 0
                 * playback_url :
                 * start_time : 1508749383
                 * time : 0
                 * title :
                 */

                private String end_time;
                private String image_url;
                private String onlines;
                private String playback_url;
                private String start_time;
                private String time;
                private String share;
                private String title;
                private List<String> category_name;

                public String getShare() {
                    return share;
                }

                public void setShare(String share) {
                    this.share = share;
                }

                public void setEnd_time(String end_time) {
                    this.end_time = end_time;
                }

                public void setImage_url(String image_url) {
                    this.image_url = image_url;
                }

                public void setOnlines(String onlines) {
                    this.onlines = onlines;
                }

                public void setPlayback_url(String playback_url) {
                    this.playback_url = playback_url;
                }

                public void setStart_time(String start_time) {
                    this.start_time = start_time;
                }

                public void setTime(String time) {
                    this.time = time;
                }

                public void setTitle(String title) {
                    this.title = title;
                }

                public void setCategory_name(List<String> category_name) {
                    this.category_name = category_name;
                }

                public String getEnd_time() {
                    return end_time;
                }

                public String getImage_url() {
                    return image_url;
                }

                public String getOnlines() {
                    return onlines;
                }

                public String getPlayback_url() {
                    return playback_url;
                }

                public String getStart_time() {
                    return start_time;
                }

                public String getTime() {
                    return time;
                }

                public String getTitle() {
                    return title;
                }

                public List<String> getCategory_name() {
                    return category_name;
                }
            }
        }
    }
}
