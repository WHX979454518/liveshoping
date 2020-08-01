package com.hotniao.live.model;

import com.hn.library.http.BaseResponseModel;

import java.util.List;

/**
 * 作者：Mr.Xu
 * 时间：2018/2/6
 */
public class ConcentrationCategoryModel extends BaseResponseModel {


    private List<GoodsCategory> d;

    public void setD(List<GoodsCategory> d) {
        this.d = d;
    }

    public List<GoodsCategory> getD() {
        return d;
    }

        public static class GoodsCategory{
            private String id;
            private String name;
            private String icon;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getIcon() {
                return icon;
            }

            public void setIcon(String icon) {
                this.icon = icon;
            }
        }



}
