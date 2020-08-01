package com.hotniao.live.model;

import com.hn.library.http.BaseResponseModel;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Alan on 2019/7/29.
 */
public class PhoneModel extends BaseResponseModel implements Serializable {

    private List<DBean> d;

    public List<DBean> getD() {
        return d;
    }

    public void setD(List<DBean> d) {
        this.d = d;
    }

    public static class DBean implements Serializable{
        /**
         * service_phone : 7234565
         */

        private String service_phone;

        public String getService_phone() {
            return service_phone;
        }

        public void setService_phone(String service_phone) {
            this.service_phone = service_phone;
        }
    }
}
