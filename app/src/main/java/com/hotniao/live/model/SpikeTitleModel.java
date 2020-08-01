package com.hotniao.live.model;

import com.hn.library.http.BaseResponseModel;
import com.live.shoplib.utils.DataTimeUtils;


import java.util.List;

/**
 * Created by Alan on 2019/6/17.
 */
public class SpikeTitleModel extends BaseResponseModel {

    /**
     * "d": [
     * {
     * "starttime": "1560742255",
     * "endtime": "1560742256"
     * },
     * {
     * "starttime": "1560742255",
     * "endtime": "1560742256"
     * }
     * ]
     */

    private List<SpikeTitleItem> d;

    public List<SpikeTitleItem> getD() {
        return d;
    }

    public void setD(List<SpikeTitleItem> d) {
        this.d = d;
    }

    public static class SpikeTitleItem {

        Long starttime;
        Long endtime;

        public Long getStarttime() {
            return starttime;
        }

        public void setStarttime(Long starttime) {
            this.starttime = starttime;
        }

        public Long getEndtime() {
            return endtime;
        }

        public void setEndtime(Long endtime) {
            this.endtime = endtime;
        }

        public String getTitle() {
            return DataTimeUtils.timeParseHour(starttime);
        }


        public String getSecondTitle() {
            if (DataTimeUtils.getNowTimestamp() >= starttime && DataTimeUtils.getNowTimestamp() <= endtime) {
                return "进行中";
            }
            if (DataTimeUtils.getNowTimestamp() <= starttime) {
                return "即将开场";
            }
            if (DataTimeUtils.getNowTimestamp() >= endtime) {
                return "已结束";
            }
            return "即将开场";
        }


    }

}
