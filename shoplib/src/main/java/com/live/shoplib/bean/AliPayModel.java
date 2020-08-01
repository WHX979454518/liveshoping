package com.live.shoplib.bean;

import com.hn.library.http.BaseResponseModel;

/**
 * 作者：Mr.Xu
 * 时间：2018/1/8
 */
public class AliPayModel extends BaseResponseModel{


    /**
     * d : {"sign":"app_id=2017080708078715&method=alipay.trade.app.pay&charset=UTF-8&sign_type=RSA2&timestamp=2018-01-08+16%3A18%3A30&version=1.0&notify_url=http%3A%2F%2F39.108.66.203%3A8001%2Fv1%2Fshop%2Fcallpay%2FaliCall&biz_content=%7B%22body%22%3A%22%5Cu64ad%5Cu5416%5Cu76f4%5Cu64ad%5Cu5546%5Cu54c1%5Cu8d2d%5Cu4e70%22%2C%22subject%22%3A%22%5Cu64ad%5Cu5416%5Cu76f4%5Cu64ad%5Cu5546%5Cu54c1%5Cu8d2d%5Cu4e70%22%2C%22out_trade_no%22%3A%22263U7S20180102092914%22%2C%22timeout_express%22%3A%2230m%22%2C%22total_amount%22%3A0.01%2C%22product_code%22%3A%22QUICK_MSECURITY_PAY%22%2C%22passback_params%22%3A%22order%257C0%257C0%22%7D&format=json&alipay_sdk=alipay-sdk-php-20161101&sign=qPODMqvSA8XMvJWGQ4XA3zpfXrSZPRT%2FVqn0rb4%2Fnv6We7FABRv%2FJV6t10CrrRw4fMt07pIupp9aDIQZtwisXnWyTwA47LY0JUPkW%2FrQumriROsakTojPGPvTCaj8Ds4bbuyH%2BngYF%2F4xnx2lo%2B8gds4cI4XvcBXQ2FARGyH8M2ip31RlMBVxHgpc6OShOTRgk7szyxFTe06weRCz%2FM7vQ9PNV8iCBmsNMvBTP7gXjs1T%2BZL9l34RJJ7kEnJy2Eyh%2FJZfxQoeMOyctcJLAI9h4U2XEhLdwCgMyR4oi1on%2FavVNMNuP%2FG%2FIsmrQ1rQHi4Nd5SfxAH1wxKrXUixKh0Lw%3D%3D"}
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
         * sign : app_id=2017080708078715&method=alipay.trade.app.pay&charset=UTF-8&sign_type=RSA2&timestamp=2018-01-08+16%3A18%3A30&version=1.0&notify_url=http%3A%2F%2F39.108.66.203%3A8001%2Fv1%2Fshop%2Fcallpay%2FaliCall&biz_content=%7B%22body%22%3A%22%5Cu64ad%5Cu5416%5Cu76f4%5Cu64ad%5Cu5546%5Cu54c1%5Cu8d2d%5Cu4e70%22%2C%22subject%22%3A%22%5Cu64ad%5Cu5416%5Cu76f4%5Cu64ad%5Cu5546%5Cu54c1%5Cu8d2d%5Cu4e70%22%2C%22out_trade_no%22%3A%22263U7S20180102092914%22%2C%22timeout_express%22%3A%2230m%22%2C%22total_amount%22%3A0.01%2C%22product_code%22%3A%22QUICK_MSECURITY_PAY%22%2C%22passback_params%22%3A%22order%257C0%257C0%22%7D&format=json&alipay_sdk=alipay-sdk-php-20161101&sign=qPODMqvSA8XMvJWGQ4XA3zpfXrSZPRT%2FVqn0rb4%2Fnv6We7FABRv%2FJV6t10CrrRw4fMt07pIupp9aDIQZtwisXnWyTwA47LY0JUPkW%2FrQumriROsakTojPGPvTCaj8Ds4bbuyH%2BngYF%2F4xnx2lo%2B8gds4cI4XvcBXQ2FARGyH8M2ip31RlMBVxHgpc6OShOTRgk7szyxFTe06weRCz%2FM7vQ9PNV8iCBmsNMvBTP7gXjs1T%2BZL9l34RJJ7kEnJy2Eyh%2FJZfxQoeMOyctcJLAI9h4U2XEhLdwCgMyR4oi1on%2FavVNMNuP%2FG%2FIsmrQ1rQHi4Nd5SfxAH1wxKrXUixKh0Lw%3D%3D
         */

        private String sign;

        public void setSign(String sign) {
            this.sign = sign;
        }

        public String getSign() {
            return sign;
        }
    }
}
