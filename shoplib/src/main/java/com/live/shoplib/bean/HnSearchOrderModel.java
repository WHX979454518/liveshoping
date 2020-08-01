package com.live.shoplib.bean;

import com.hn.library.http.BaseResponseModel;

import java.util.List;

/**
 * 作者：Mr.Xu
 * 时间：2018/1/29
 */
public class HnSearchOrderModel extends BaseResponseModel {


    /**
     * d : [{"details":[{"goods_attr":"内存:2G 颜色:白色","goods_id":"100095","goods_img":"FpyWf15wHhERzVlPeYaxp7PsyjT3","goods_name":"苹果手机","goods_number":"1","goods_price":"3.00","id":"10","order_id":"4","sku_id":"4","status":"0"}],"goods_num":1,"icon":"","name":"第一个商铺","order_amount":"6.00","order_id":"4","order_sn":"264U4S20171220164236","order_status":"0","pay_status":"0","shipping_status":"0","store_id":"3"},{"details":[{"goods_attr":"内存:2G 颜色:白色","goods_id":"100095","goods_img":"FpyWf15wHhERzVlPeYaxp7PsyjT3","goods_name":"苹果手机","goods_number":"1","goods_price":"3.00","id":"10","order_id":"4","sku_id":"4","status":"0"}],"goods_num":4,"icon":"","name":"第一个商铺","order_amount":"13.00","order_id":"3","order_sn":"264U3S20171220164236","order_status":"1","pay_status":"2","shipping_status":"1","store_id":"3"},{"details":[{"goods_attr":"内存:2G 颜色:白色","goods_id":"100095","goods_img":"FpyWf15wHhERzVlPeYaxp7PsyjT3","goods_name":"苹果手机","goods_number":"1","goods_price":"3.00","id":"10","order_id":"4","sku_id":"4","status":"0"}],"goods_num":4,"icon":"","name":"第一个商铺","order_amount":"13.00","order_id":"2","order_sn":"264U3S20171220164204","order_status":"0","pay_status":"0","shipping_status":"1","store_id":"3"},{"details":[{"goods_attr":"内存:2G 颜色:白色","goods_id":"100095","goods_img":"FpyWf15wHhERzVlPeYaxp7PsyjT3","goods_name":"苹果手机","goods_number":"1","goods_price":"3.00","id":"10","order_id":"4","sku_id":"4","status":"0"}],"goods_num":4,"icon":"","name":"第一个商铺","order_amount":"13.00","order_id":"1","order_sn":"264U3S20171220164040","order_status":"0","pay_status":"0","shipping_status":"0","store_id":"3"}]
     */

    private List<HnOrderListBean> d;

    public void setD(List<HnOrderListBean> d) {
        this.d = d;
    }

    public List<HnOrderListBean> getD() {
        return d;
    }

}
