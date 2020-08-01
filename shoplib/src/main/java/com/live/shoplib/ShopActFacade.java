package com.live.shoplib;

import android.text.TextUtils;

import com.alibaba.android.arouter.launcher.ARouter;
import com.hn.library.global.NetConstant;
import com.hn.library.utils.HnPrefUtils;
import com.hn.library.utils.HnToastUtils;
import com.live.shoplib.bean.GoodsCommitModel;
import com.live.shoplib.bean.GroupBuyTransationBean;
import com.live.shoplib.bean.MyRecAddressModel;
import com.live.shoplib.bean.SpikeTransationBean;
import com.live.shoplib.ui.GoodsCommitAct;

import java.util.ArrayList;

/**
 * 商品外观模式
 * 统一控制所有界面跳转
 * 作者：Mr.Xu
 * 时间：2017/12/20
 */
public class ShopActFacade {


    //订单详情
    public static void openOrderDetails(String order_id, boolean isSeller) {
        ARouter.getInstance().build("/shoplib/OrderDetailsAct").withString("id", order_id).withBoolean("isSeller", isSeller).navigation();
    }

    //运费设置
    public static void openFreightSetAct(String store_id) {
        ARouter.getInstance().build("/shoplib/TeKotlinAct").withString("tag", "FreightSetAct").withString("store_id", store_id).navigation();
    }

    //商品详情
    public static void openGoodsDetails(String goods_id, boolean hint) {
        ARouter.getInstance().build("/shoplib/GoodsDetailsAct").withString("goods_id", goods_id).withBoolean("hint", hint).navigation();
    }

    public static void openGoodsDetails(String goods_id) {
        ARouter.getInstance().build("/shoplib/GoodsDetailsAct").withString("goods_id", goods_id).withBoolean("hint", false).navigation();
    }

    //商品详情（第一张带有视频）
    public static void openGoodsVideoDetails(String goods_id) {
        ARouter.getInstance().build("/app/GoodsHaveVideoDetailsAct").withString("goods_id", goods_id).withBoolean("hint", false).navigation();
    }

    //秒杀商品详情
    public static void openSpikeGoodsDetails(String goods_id) {
        ARouter.getInstance().build("/shoplib/SpikeGoodsDetailsAct").withString("goods_id", goods_id).withBoolean("hint", false).navigation();
    }

    //团购商品详情
    public static void openGroupBuyGoodsDetails(String goods_id,String group_buy_id) {
        ARouter.getInstance().build("/shoplib/GroupBuyGoodsDetailsAct").withString("goods_id", goods_id).withString("group_buy_id", group_buy_id).withBoolean("hint", false).navigation();
    }

    //商家详情
    public static void openShopDetails(String store_id) {
        ARouter.getInstance().build("/shoplib/ShopDetailsAct").withString("store_id", store_id).navigation();
    }

    //商品订单
    public static void openShopOrder(boolean isSeller, int pos) {
        ARouter.getInstance().build("/shoplib/ShopOrderAct").withInt("pos", pos).withBoolean("isSeller", isSeller).navigation();
    }

    //足迹
    public static void openTrack() {
        ARouter.getInstance().build("/shoplib/TrackAct").navigation();
    }

    //退款详情
    public static void openRefundDetails(String refund_id) {
        ARouter.getInstance().build("/shoplib/RefundDetailsAct").withString("refund_id", refund_id).navigation();
    }

    //退款详情-店铺
    public static void openRefundDetails(String refund_id, String store_id) {
        ARouter.getInstance().build("/shoplib/RefundDetailsAct").withString("store_id", store_id).withString("refund_id", refund_id).navigation();
    }

    //退款列表
    public static void openRefundGoods() {
        ARouter.getInstance().build("/shoplib/RefundGoodsAct").navigation();
    }

    //退款列表
    public static void openRefundGoods(String store_id) {
        ARouter.getInstance().build("/shoplib/RefundGoodsAct").withString("store_id", store_id).navigation();
    }

    //用户评论
    public static void openEvaGoods(String goods_id) {
        ARouter.getInstance().build("/shoplib/EvaGoodsAct").withString("goods_id", goods_id).navigation();
    }

    //用户评论
    public static void openEvaGoods(String order_id, boolean isOrder) {
        ARouter.getInstance().build("/shoplib/EvaGoodsAct").withString("order_id", order_id).withBoolean("isOrder", isOrder).navigation();
    }

    //发表评论
    public static void openEvaPublic(String order_id) {
        ARouter.getInstance().build("/shoplib/EvaPublicAct").withString("order_id", order_id).navigation();
    }

    //发表成功
    public static void openEvaResult(String order_id) {
        ARouter.getInstance().build("/shoplib/EvaResultAct").withString("order_id", order_id).navigation();
    }

    //物流信息
    public static void openGoodsLogistics(String order_id) {
        ARouter.getInstance().build("/shoplib/GoodsLogisticsAct").withString("order_id", order_id).navigation();
    }

    //申请退款
    public static void openApplyBack(boolean both, String order_id, String details_id) {
        ARouter.getInstance().build("/shoplib/ApplyBackAct").withString("order_id", order_id).withString("details_id", details_id).withBoolean("both", both).navigation();
    }

    //收获地址
    public static void openAddressReceiving() {
        ARouter.getInstance().build("/shoplib/AddressReceivingAct").navigation();
    }

    //收获地址
    public static void openAddressReceiving(boolean select) {
        ARouter.getInstance().build("/shoplib/AddressReceivingAct").withBoolean("select", select).navigation();
    }

    //编辑地址
    public static void openAddressAddEdit(MyRecAddressModel.DEntity bean) {
        ARouter.getInstance().build("/shoplib/AddressAddEditAct").withSerializable("bean", bean).navigation();
    }

    //新加地址
    public static void openAddressAddEdit() {
        ARouter.getInstance().build("/shoplib/AddressAddEditAct").navigation();
    }

    //添加购物车
    public static void openGoodsCar() {
        ARouter.getInstance().build("/shoplib/GoodsCarAct").navigation();
    }

    //打开提交订单界面
    public static void openGoodsCommit(GoodsCommitModel bean) {
        ARouter.getInstance().build("/shoplib/GoodsCommitAct").withSerializable("bean", bean).withInt(GoodsCommitAct.COMMIT_TYPE,GoodsCommitAct.NORMAL_COMMIT_TYPE).navigation();
    }

    //打开提交秒杀订单界面
    public static void openSpikeGoodsCommit(SpikeTransationBean bean) {
        ARouter.getInstance().build("/shoplib/GoodsCommitAct").withSerializable("spikeBean", bean).withInt(GoodsCommitAct.COMMIT_TYPE,GoodsCommitAct.SPIKE_COMMIT_TYPE).navigation();
    }

    //打开提交团购订单界面
    public static void openGroupBuyGoodsCommit(GroupBuyTransationBean bean) {
        ARouter.getInstance().build("/shoplib/GoodsCommitAct").withSerializable("groupBean", bean).withInt(GoodsCommitAct.COMMIT_TYPE,GoodsCommitAct.GROUP_BUY_COMMIT_TYPE).navigation();
    }

    //打开分享邀请界面
    public static void openShareGroupDetails(String orderId,String groupBuyOrderId) {
        ARouter.getInstance().build("/shoplib/GroupBuyDetailsAct").withString("orderId", orderId).withString("groupBuyOrderId", groupBuyOrderId).navigation();
    }
    //去支付
    public static void openPayDetails(String order_id, String money) {
        ARouter.getInstance().build("/shoplib/PayDetailsAct").withString("order_id", order_id).withString("money", money).withBoolean("isGroupBuy", false).navigation();
    }

    //去支付
    public static void openPayDetails(String order_id, String money, boolean isGroupBuy) {
        ARouter.getInstance().build("/shoplib/PayDetailsAct").withString("order_id", order_id).withString("money", money).withBoolean("isGroupBuy", isGroupBuy).navigation();
    }


    //支付状态
    public static void openPayResponse(String order_id, boolean state, String type, String money) {
        ARouter.getInstance().build("/shoplib/PayResponseAct").withString("type", type).withString("money", money).withString("order_id", order_id).withBoolean("state", state).navigation();
    }

    //回放列表
    public static void openBackList(String user_id, String title) {
        ARouter.getInstance().build("/shoplib/BackListAct").withString("title", title).withString("user_id", user_id).navigation();
    }

    //访问记录
    public static void openHistoryRecordAct() {
        ARouter.getInstance().build("/shoplib/TeKotlinAct").withString("tag", "HistoryRecordAct").navigation();
    }
    //网页
    public static void openWeb(String title,String url,String type) {
        ARouter.getInstance().build("/app/HnWebActivity").withString("type", type).withString("url",url).withString("title",title).navigation();
    }

    //店铺信息
    public static void openStoreMsg(String store_id) {
        ARouter.getInstance().build("/shoplib/StoreMsgAct").withString("store_id", store_id).navigation();
    }

    //卖家中心
    public static void openSellerCenter(String store_id) {
        ARouter.getInstance().build("/shoplib/SellerCenterAct").withString("store_id", store_id).navigation();
    }

    //全部分类
    public static void openShopCenter() {
        ARouter.getInstance().build("/shoplib/HnShopAct").navigation();
    }

    //商品分类
    public static void openShopGoodsList(String id) {
        ARouter.getInstance().build("/shoplib/GoodsListAct").withString("id",id).navigation();
    }

    //认证类型
    public static void openAuthSort() {
        ARouter.getInstance().build("/shoplib/AuthSortAct").navigation();
    }

    //店铺认证
    public static void openStoreAut(String certification_type) {
        ARouter.getInstance().build("/app/StoreAutAct").withString("certification_type", certification_type).navigation();
    }

    //店铺认证
    public static void openStoreAut(String certification_type, boolean again) {
        ARouter.getInstance().build("/app/StoreAutAct").withString("certification_type", certification_type).withBoolean("again", again).navigation();
    }

    //提现
    public static void openWithDrawWrite() {
        ARouter.getInstance().build("/app/HnWithDrawWriteActivity").navigation();
    }

    //提现
    public static void openWithDrawWrite(String sellMoney, int withdraw) {
        ARouter.getInstance().build("/app/HnWithDrawWriteActivity").withString("sellMoney", sellMoney).withInt("withdraw", withdraw).navigation();
    }

    //房管
    public static void openMyAdminActivity() {
        ARouter.getInstance().build("/app/HnMyAdminActivity").navigation();
    }

    //店铺编辑
    public static void openStoreEdit(String store_id) {
        ARouter.getInstance().build("/shoplib/StoreEditAct").withString("store_id", store_id).navigation();
    }

    //店铺栏目
    public static void openStoreGroup(String store_id) {
        ARouter.getInstance().build("/shoplib/StoreGroupAct").withString("store_id", store_id).navigation();
    }


    public static void openEditAttrSpec(String store_id, String id, ArrayList<String> data,String value, String type) {
        ARouter.getInstance().build("/shoplib/EditAttrSpecAct")
                .withStringArrayList("data", data)
                .withString("type", type)
                .withString("id", id)
                .withString("value", value)
                .withString("store_id", store_id)
                .navigation();
    }

    //店铺栏目
    public static void openGoodsType(String store_id) {
        ARouter.getInstance().build("/shoplib/GoodsTypeAct").withString("store_id", store_id).navigation();
    }

    //店铺商品
    public static void openStoreGoods() {
        ARouter.getInstance().build("/shoplib/StoreGoodsAct").navigation();
    }

    //我的店铺
    public static void openMyStoreAct(String store_id) {
        ARouter.getInstance().build("/shoplib/MyStoreAct").withString("store_id", store_id).navigation();
    }

    //我的店铺
    public static void openGoodsAttr(String store_id, String id, String tag) {
        ARouter.getInstance().build("/shoplib/GoodsAttrAct").withString("store_id", store_id).withString("tag", tag).withString("id", id).navigation();
    }

    //确认收货
    public static void openSureOrder(String order_id) {
        ARouter.getInstance().build("/shoplib/SureOrderAct").withString("order_id", order_id).navigation();
    }

    //我的收益
    public static void openEarning() {
        ARouter.getInstance().build("/app/HnUserBillEarningActivity").navigation();
    }

    //客服 对方的id
    public static void openPrivateChat(String uid, String name, String chatId) {
        String uid_own = HnPrefUtils.getString(NetConstant.User.UID, "");
        if (TextUtils.equals(uid_own, uid)) {
            HnToastUtils.showToastShort("无法与自己聊天");
            return;
        }
        ARouter.getInstance().build("/app/HnPrivateChatActivity").withString("DATA", uid).withString("Name", name).withString("ChatRoomId", chatId).navigation();
    }

    //回放
    public static void openPlayBack(String uid, String url, String share) {
        ARouter.getInstance().build("/app/HnPlayBackVideoActivity").withString("uid", uid).withString("url", url).withString("share", share).navigation();
    }

}
