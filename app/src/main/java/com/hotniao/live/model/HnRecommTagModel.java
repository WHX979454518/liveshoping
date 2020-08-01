package com.hotniao.live.model;

import com.hn.library.http.BaseResponseModel;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：Mr.Xu
 * 时间：2018/2/6
 */
public class HnRecommTagModel extends BaseResponseModel{


    /**
     * d : {"list":["adasd","qweqwewqe"]}
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
         * list : ["adasd","qweqwewqe"]
         */

        private ArrayList<String> list;

        public void setList(ArrayList<String> list) {
            this.list = list;
        }

        public ArrayList<String> getList() {
            return list;
        }
    }
}
