package com.live.shoplib;


import com.hn.library.global.HnUrl;
import com.hn.library.http.BaseResponseModel;
import com.hn.library.http.HnHttpUtils;
import com.hn.library.http.HnResponseHandler;
import com.hn.library.utils.HnToastUtils;
import com.loopj.android.http.RequestParams;

/**
 * 做一些普通单方面请求
 * 作者：Mr.Xu
 * 时间：2017/12/22
 */
public class ShopRequest {

    //商品删除
    public static void delShopGoods(String goods_id, OnRespondNothing respondNothing) {
        RequestParams param = new RequestParams();
        param.put("goods_id", goods_id);
        request("商品删除", HnUrl.GOODS_DEL, param, respondNothing);
    }

    //隐私
    public static void storePrivate(String store_id, String value, OnRespondNothing respondNothing) {
        RequestParams param = new RequestParams();
        param.put("store_id", store_id);
        param.put("value", value);
        request("隐私", HnUrl.STORE_PRIVATE, param, respondNothing);
    }

    //发表评价
    public static void annoOrder(String details, String order_id, OnRespondNothing respondNothing) {
        RequestParams param = new RequestParams();
        param.put("order_id", order_id);
        param.put("details", details);
        request("发表评价", HnUrl.ANNO_ORDER, param, respondNothing);
    }

    //删除订单
    public static void deleteOrder(String order_id, OnRespondNothing respondNothing) {
        RequestParams param = new RequestParams();
        param.put("order_id", order_id);
        request("删除订单", HnUrl.ORDER_DEL, param, respondNothing);
    }

    //删除失效商品
    public static void deleteLoseGoods(String cartList, OnRespondNothing respondNothing) {
        RequestParams param = new RequestParams();
        param.put("cartList", cartList);
        request("删除失效商品", HnUrl.GOODS_CAR_DEL, param, respondNothing);
    }


    //取消订单
    public static void cancelOrder(String order_id, OnRespondNothing respondNothing) {
        RequestParams param = new RequestParams();
        param.put("order_id", order_id);
        request("取消订单", HnUrl.ORDER_CANCEL, param, respondNothing);
    }

    //确认收货
    public static void sureOrder(String order_id, OnRespondNothing respondNothing) {
        RequestParams param = new RequestParams();
        param.put("order_id", order_id);
        request("确认收货", HnUrl.ORDER_SURE, param, respondNothing);
    }

    //发货
    public static void sendGoods(String code, String order_id, String shipper_code, OnRespondNothing respondNothing) {
        RequestParams param = new RequestParams();
        param.put("code", code);
        param.put("order_id", order_id);
        param.put("shipper_code", shipper_code);
        request("发货", HnUrl.GOODS_SEND, param, respondNothing);
    }

    //收藏商品
    public static void collectGoods(String goods_id, String type, OnRespondNothing respondNothing) {
        RequestParams param = new RequestParams();
        param.put("goods_id", goods_id);
        param.put("type", type);
        request("收藏商品", HnUrl.COLLECT_GOODS, param, respondNothing);
    }

    //收藏店家
    public static void collectShop(String store_id, String type, OnRespondNothing respondNothing) {
        RequestParams param = new RequestParams();
        param.put("store_id", store_id);
        param.put("type", type);
        request("收藏店铺", HnUrl.COLLECT_SHOP, param, respondNothing);
    }


    //店铺栏目编辑
    public static void editStoreGroup(String column_id, String column_name, OnRespondNothing respondNothing) {
        RequestParams param = new RequestParams();
        param.put("column_id", column_id);
        param.put("column_name", column_name);
        request("店铺栏目编辑", HnUrl.EDIT_STORE_GROUP, param, respondNothing);
    }

    //店铺栏目编辑
    public static void editGoodsType(String id, String name, String store_id, OnRespondNothing respondNothing) {
        RequestParams param = new RequestParams();
        param.put("id", id);
        param.put("name", name);
        param.put("store_id", store_id);
        request("店铺类型编辑", HnUrl.EDIT_GOODS_TYPE, param, respondNothing);
    }

    //店铺栏目编辑
    public static void editStoreGroupGoods(String column_id, String goods_id, OnRespondNothing respondNothing) {
        RequestParams param = new RequestParams();
        param.put("column_id", column_id);
        param.put("goods_id", goods_id);
        request("店铺栏目编辑商品", HnUrl.EDIT_STORE_GROUP_GOODS, param, respondNothing);
    }

    //店铺栏目编辑
    public static void addStoreGroup(String column_name, OnRespondNothing respondNothing) {
        RequestParams param = new RequestParams();
        param.put("column_name", column_name);
        request("店铺栏目编辑", HnUrl.ADD_STORE_GROUP, param, respondNothing);
    }

    //店铺类型
    public static void addGoodsType(String name, String store_id, OnRespondNothing respondNothing) {
        RequestParams param = new RequestParams();
        param.put("name", name);
        param.put("store_id", store_id);
        request("类型编辑", HnUrl.ADD_GOODS_TYPE, param, respondNothing);
    }

    //店铺类型
    public static void addGoodsSpceAttr(String id, String name, String store_id,String type,OnRespondNothing respondNothing) {
        RequestParams param = new RequestParams();
        param.put("id", id);
        param.put("name", name);
        param.put("store_id", store_id);
        param.put("type", type);
        request("编辑", HnUrl.ADD_GOODS_Spec_Attr, param, respondNothing);
    }

    private static void request(String TAG, String url, RequestParams param, final OnRespondNothing respondNothing) {
        HnHttpUtils.postRequest(url, param, TAG, new HnResponseHandler<BaseResponseModel>(BaseResponseModel.class) {
            @Override
            public void hnSuccess(String response) {
                if (respondNothing != null) respondNothing.finishs();
            }

            @Override
            public void hnErr(int errCode, String msg) {
                HnToastUtils.showToastShort(msg);
            }
        });
    }


    public interface OnRespondNothing {
        void finishs();
    }
}
