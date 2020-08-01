package com.hotniao.live.model.bean;

import com.hn.library.http.BaseResponseModel;

import java.util.List;

/**
 * 作者：Mr.Xu
 * 时间：2017/12/12
 */
public class AddressBean extends BaseResponseModel{


    /**
     * emptyIdentifier : 1
     * d : [{"id":"110000","name":"北京","shortname":"北京"},{"id":"120000","name":"天津","shortname":"天津"},{"id":"130000","name":"河北省","shortname":"河北"},{"id":"140000","name":"山西省","shortname":"山西"},{"id":"150000","name":"内蒙古自治区","shortname":"内蒙古"},{"id":"210000","name":"辽宁省","shortname":"辽宁"},{"id":"220000","name":"吉林省","shortname":"吉林"},{"id":"230000","name":"黑龙江省","shortname":"黑龙江"},{"id":"310000","name":"上海","shortname":"上海"},{"id":"320000","name":"江苏省","shortname":"江苏"},{"id":"330000","name":"浙江省","shortname":"浙江"},{"id":"340000","name":"安徽省","shortname":"安徽"},{"id":"350000","name":"福建省","shortname":"福建"},{"id":"360000","name":"江西省","shortname":"江西"},{"id":"370000","name":"山东省","shortname":"山东"},{"id":"410000","name":"河南省","shortname":"河南"},{"id":"420000","name":"湖北省","shortname":"湖北"},{"id":"430000","name":"湖南省","shortname":"湖南"},{"id":"440000","name":"广东省","shortname":"广东"},{"id":"450000","name":"广西壮族自治区","shortname":"广西"},{"id":"460000","name":"海南省","shortname":"海南"},{"id":"500000","name":"重庆","shortname":"重庆"},{"id":"510000","name":"四川省","shortname":"四川"},{"id":"520000","name":"贵州省","shortname":"贵州"},{"id":"530000","name":"云南省","shortname":"云南"},{"id":"540000","name":"西藏自治区","shortname":"西藏"},{"id":"610000","name":"陕西省","shortname":"陕西"},{"id":"620000","name":"甘肃省","shortname":"甘肃"},{"id":"630000","name":"青海省","shortname":"青海"},{"id":"640000","name":"宁夏回族自治区","shortname":"宁夏"},{"id":"650000","name":"新疆维吾尔自治区","shortname":"新疆"},{"id":"810000","name":"香港特别行政区","shortname":"香港"},{"id":"820000","name":"澳门特别行政区","shortname":"澳门"},{"id":"710000","name":"台湾","shortname":"台湾"}]
     */

    private int emptyIdentifier;
    private List<DEntity> d;

    public void setEmptyIdentifier(int emptyIdentifier) {
        this.emptyIdentifier = emptyIdentifier;
    }

    public void setD(List<DEntity> d) {
        this.d = d;
    }

    public int getEmptyIdentifier() {
        return emptyIdentifier;
    }

    public List<DEntity> getD() {
        return d;
    }

    public static class DEntity {
        /**
         * id : 110000
         * name : 北京
         * shortname : 北京
         */

        private String id;
        private String name;
        private String shortname;

        public void setId(String id) {
            this.id = id;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setShortname(String shortname) {
            this.shortname = shortname;
        }

        public String getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public String getShortname() {
            return shortname;
        }
    }
}
