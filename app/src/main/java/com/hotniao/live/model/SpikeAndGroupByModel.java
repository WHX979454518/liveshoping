package com.hotniao.live.model;

import com.hn.library.http.BaseResponseModel;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by Alan on 2019/6/17.
 */
public class SpikeAndGroupByModel extends BaseResponseModel {

    /**
     *"d":{
     *             "seckill": [
     *                 {
     *                     "goods_id": 1,
     *                     "goods_img": "url"
     *                 },
     *                 {
     *                     "goods_id": 1,
     *                     "goods_img": "url"
     *                 }
     *             ],
     *             "group": [
     *                 {
     *                     "goods_id": 1,
     *                     "goods_img": "url"
     *                 },
     *                 {
     *                     "goods_id": 1,
     *                     "goods_img": "url"
     *                 }
     *             ]
     *         }
     */

    private SpikeOrGroupInfo d;

    public SpikeOrGroupInfo getD() {
        return d;
    }

    public void setD(SpikeOrGroupInfo d) {
        this.d = d;
    }

    public static class SpikeOrGroupItemData {

        String goods_id;
        String goods_img;

        public String getGoods_id() {
            return goods_id;
        }

        public void setGoods_id(String goods_id) {
            this.goods_id = goods_id;
        }

        public String getGoods_img() {
            return goods_img;
        }

        public void setGoods_img(String goods_img) {
            this.goods_img = goods_img;
        }
    }

    public static class SpikeOrGroupInfo {

        ArrayList<SpikeOrGroupItemData> seckill;

        ArrayList<SpikeOrGroupItemData> group;

        public ArrayList<SpikeOrGroupItemData> getSeckill() {
            return seckill;
        }

        public void setSeckill(ArrayList<SpikeOrGroupItemData> seckill) {
            this.seckill = seckill;
        }

        public  ArrayList<SpikeOrGroupItemData> getGroup() {
            return group;
        }

        public void setGroup(ArrayList<SpikeOrGroupItemData> group) {
            this.group = group;
        }
    }
}
