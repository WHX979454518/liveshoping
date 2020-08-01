package com.hotniao.live.model;

import com.hn.library.http.BaseResponseModel;

import java.util.List;

/**
 * Copyright (C) 2016,深圳市红鸟网络科技股份有限公司 All rights reserved.
 * 类描述：轮播图
 * 创建人：刘勇
 * 创建时间：2017/2/22 16:22
 * 修改人：刘勇
 * 修改时间：2017/2/22 16:34
 * 修改备注：
 * Version:  1.0.0
 */

public class HnBannerModel extends BaseResponseModel {



    /**
     * d : {"carousel":[{"carousel_href":"","carousel_id":"17","carousel_jump":{"data":{"id":"1"},"type":"goods"},"carousel_url":"https://fireniao-1252571077.image.myqcloud.com/image/20171206/1512527478591191.png"}]}
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
         * carousel : [{"carousel_href":"","carousel_id":"17","carousel_jump":{"data":{"id":"1"},"type":"goods"},"carousel_url":"https://fireniao-1252571077.image.myqcloud.com/image/20171206/1512527478591191.png"}]
         */

        private List<CarouselEntity> carousel;

        public void setCarousel(List<CarouselEntity> carousel) {
            this.carousel = carousel;
        }

        public List<CarouselEntity> getCarousel() {
            return carousel;
        }

        public static class CarouselEntity {
            /**
             * carousel_href :
             * carousel_id : 17
             * carousel_jump : {"data":{"id":"1"},"type":"goods"}
             * carousel_url : https://fireniao-1252571077.image.myqcloud.com/image/20171206/1512527478591191.png
             */

            private String carousel_href;
            private String carousel_id;
            private CarouselJumpEntity carousel_jump;
            private String carousel_url;

            public void setCarousel_href(String carousel_href) {
                this.carousel_href = carousel_href;
            }

            public void setCarousel_id(String carousel_id) {
                this.carousel_id = carousel_id;
            }

            public void setCarousel_jump(CarouselJumpEntity carousel_jump) {
                this.carousel_jump = carousel_jump;
            }

            public void setCarousel_url(String carousel_url) {
                this.carousel_url = carousel_url;
            }

            public String getCarousel_href() {
                return carousel_href;
            }

            public String getCarousel_id() {
                return carousel_id;
            }

            public CarouselJumpEntity getCarousel_jump() {
                return carousel_jump;
            }

            public String getCarousel_url() {
                return carousel_url;
            }

            public static class CarouselJumpEntity {
                /**
                 * data : {"id":"1"}
                 * type : goods
                 */

                private DataEntity data;
                private String type;

                public void setData(DataEntity data) {
                    this.data = data;
                }

                public void setType(String type) {
                    this.type = type;
                }

                public DataEntity getData() {
                    return data;
                }

                public String getType() {
                    return type;
                }

                public static class DataEntity {
                    /**
                     * id : 1
                     */

                    private String id;

                    public void setId(String id) {
                        this.id = id;
                    }

                    public String getId() {
                        return id;
                    }
                }
            }
        }
    }
}
