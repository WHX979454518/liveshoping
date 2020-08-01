package com.hotniao.live.model;

import com.hn.library.http.BaseResponseModel;
import com.hn.library.utils.CommonUtils;
import com.live.shoplib.utils.DataTimeUtils;
import com.xiao.nicevideoplayer.LogUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：Mr.Xu
 * 时间：2018/2/6
 */
public class ConcentrationSpikeModel extends BaseResponseModel {

    private static final int SHOW_TIME_NUMBER = 5;

    private DEntity d;

    public void setD(DEntity d) {
        this.d = d;
    }

    public DEntity getD() {
        return d;
    }

    public static class DEntity {
        private List<SpikeGoods> seckill;
        private List<SpikePeriod> time_list;

        public List<SpikeGoods> getSeckill() {
            return seckill;
        }

        public void setSeckill(List<SpikeGoods> seckill) {
            this.seckill = seckill;
        }

        public List<SpikePeriod> getTime_list() {

            return dealDataSizeToFive(time_list);
        }

        //保证界面上的数据展示为包含当前时间段的最多五个数据
        private List<SpikePeriod> dealDataSizeToFive(List<SpikePeriod> time_list) {

            if (!CommonUtils.hasItem(time_list) || time_list.size() <= SHOW_TIME_NUMBER) {
                LogUtil.d("要显示的时间段少于" + SHOW_TIME_NUMBER + "time_list的size为:" + time_list.size());
                return time_list;
            }
            int currentPosition = 0;
            for (int i = 0; i < time_list.size(); i++) {
                if ("进行中".equals(time_list.get(i).getSpikeState())) {
                    currentPosition = i;
                    LogUtil.d("要显示的时间段当前时间段处于第" + (i + 1) + "个");
                    break;
                }
            }
            //subList（a,b）方法截取List是截取的position为a+1到b的数据，所以fromIndex要减一，就为fromIndex-1
            if (time_list.size() - 1 - currentPosition >= SHOW_TIME_NUMBER - 1) {
                List sub = time_list.subList(currentPosition-1, currentPosition + SHOW_TIME_NUMBER - 1);
                LogUtil.d("要显示的时间段后面大于等于" + (SHOW_TIME_NUMBER - 1) + "总size为:" + time_list.size() + "from:" + (currentPosition-1)
                        + "to" + (currentPosition + SHOW_TIME_NUMBER - 1));
                return new ArrayList(sub);
            } else {
                //subList（a,b）方法截取List是截取的position为a+1到b的数据，所以fromIndex要减一，就为fromIndex-1
                List sub = time_list.subList(time_list.size() - 1 - SHOW_TIME_NUMBER, time_list.size() - 1);
                LogUtil.d("要显示的时间段后面小于" + (SHOW_TIME_NUMBER - 1) + "总size为:" + time_list.size() + "from:" + (time_list.size() - 1 - SHOW_TIME_NUMBER)
                        + "to" + (time_list.size() - 1));
                return new ArrayList(sub);
            }
        }

        public void setTime_list(List<SpikePeriod> time_list) {
            this.time_list = time_list;
        }
    }


    public static class SpikeGoods {
        private String goods_img;
        private String goods_id;
        private String sec_price;
        private String goods_price;

        public String getGoods_img() {
            return goods_img;
        }

        public void setGoods_img(String goods_img) {
            this.goods_img = goods_img;
        }

        public String getGoods_id() {
            return goods_id;
        }

        public void setGoods_id(String goods_id) {
            this.goods_id = goods_id;
        }

        public String getSec_price() {
            return sec_price;
        }

        public void setSec_price(String sec_price) {
            this.sec_price = sec_price;
        }

        public String getGoods_price() {
            return goods_price;
        }

        public void setGoods_price(String goods_price) {
            this.goods_price = goods_price;
        }
    }

    public static class SpikePeriod {
        private String starttime;
        private String endtime;

        public String getStarttime() {
            return starttime;
        }

        public void setStarttime(String starttime) {
            this.starttime = starttime;
        }

        public String getEndtime() {
            return endtime;
        }

        public void setEndtime(String endtime) {
            this.endtime = endtime;
        }

        public String getSpikeState() {
            if (DataTimeUtils.getNowTimestamp() >= Long.valueOf(starttime) && DataTimeUtils.getNowTimestamp() <= Long.valueOf(endtime)) {
                return "进行中";
            }
            if (DataTimeUtils.getNowTimestamp() <= Long.valueOf(starttime)) {
                return "即将开场";
            }
            if (DataTimeUtils.getNowTimestamp() >= Long.valueOf(endtime)) {
                return "已结束";
            }
            return "即将开场";
        }
    }


}
