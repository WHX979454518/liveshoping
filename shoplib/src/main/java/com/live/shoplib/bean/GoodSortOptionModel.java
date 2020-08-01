package com.live.shoplib.bean;

import com.google.gson.Gson;
import com.hn.library.http.BaseResponseModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/4/16.
 */

public class GoodSortOptionModel extends BaseResponseModel{


    @Override
    public String toString() {
        return "GoodSortOptionModel{" +
                "d=" + d +
                '}';
    }

    /**
     * d : {"attr":[{"option":["50g净含量","100mL净含量","125mL净含量","500mL净含量","1000mL净含量"],"category_id":"2","field":"规格","id":"2"},{"option":["50g净含量","100mL净含量","125mL净含量","500mL净含量","1000mL净含量"],"category_id":"2","field":"水乳护肤品","id":"3"},{"option":["50g净含量","100mL净含量","125mL净含量","500mL净含量","1000mL净含量"],"category_id":"2","field":"食品类","id":"7"}],"spec":[{"option":["100mL","125mL","500mL","1000mL"],"category_id":"2","field":"含量","id":"2"},{"option":["100mL","125mL","500mL","1000mL"],"category_id":"2","field":"大小","id":"7"},{"option":["100mL","125mL","500mL","1000mL"],"category_id":"2","field":"长度","id":"8"}]}
     */

    private DBean d;

    public DBean getD() {
        return d;
    }

    public void setD(DBean d) {
        this.d = d;
    }

    public static class DBean {
        @Override
        public String toString() {
            return "DBean{" +
                    "attr=" + attr +
                    ", spec=" + spec +
                    '}';
        }

        private List<AttrBean> attr;
        private List<SpecBean> spec;

        public List<AttrBean> getAttr() {
            return attr;
        }

        public void setAttr(List<AttrBean> attr) {
            this.attr = attr;
        }

        public List<SpecBean> getSpec() {
            return spec;
        }

        public void setSpec(List<SpecBean> spec) {
            this.spec = spec;
        }

        public static class AttrBean {

            @Override
            public String toString() {
                return "AttrBean{" +
                        "category_id='" + category_id + '\'' +
                        ", field='" + field + '\'' +
                        ", id='" + id + '\'' +
                        ", option=" + option +
                        '}';
            }

            /**
             * option : ["50g净含量","100mL净含量","125mL净含量","500mL净含量","1000mL净含量"]
             * category_id : 2
             * field : 规格
             * id : 2
             */

            private String category_id;
            private String field;
            private String id;
            private List<String> option;
            private String temp;
            private String edit_key;

            public String getTemp() {
                return temp;
            }

            public String getEdit_key() {
                return edit_key;
            }

            public void setEdit_key(String edit_key) {
                this.edit_key = edit_key;
            }

            public void setTemp(String temp) {
                this.temp = temp;
            }

            public String getCategory_id() {
                return category_id;
            }

            public void setCategory_id(String category_id) {
                this.category_id = category_id;
            }

            public String getField() {
                return field;
            }

            public void setField(String field) {
                this.field = field;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public List<String> getOption() {
                return option;
            }

            public void setOption(List<String> option) {
                this.option = option;
            }
        }

        public static void jsonToJavaBean(String json) {
            Gson gson = new Gson();
            GoodsTemp3Bean person = gson.fromJson("", GoodsTemp3Bean.class);//对于javabean直接给出class实例
        }

        public static class SpecBean {

            @Override
            public String toString() {
                return "SpecBean{" +
                        "category_id='" + category_id + '\'' +
                        ", field='" + field + '\'' +
                        ", id='" + id + '\'' +
                        ", option=" + option +
                        '}';
            }

            /**
             * option : ["100mL","125mL","500mL","1000mL"]
             * category_id : 2
             * field : 含量
             * id : 2
             */

            private String category_id;
            private String field;
            private String id;
            private ArrayList<String> option;
            private ArrayList<String> attr_option;

            public ArrayList<String> getAttr_option() {
                return attr_option;
            }

            public void setAttr_option(ArrayList<String> attr_option) {
                this.attr_option = attr_option;
            }

            public String getCategory_id() {
                return category_id;
            }

            public void setCategory_id(String category_id) {
                this.category_id = category_id;
            }

            public String getField() {
                return field;
            }

            public void setField(String field) {
                this.field = field;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public ArrayList<String> getOption() {
                return option;
            }

            public void setOption(ArrayList<String> option) {
                this.option = option;
            }
        }
    }
}
