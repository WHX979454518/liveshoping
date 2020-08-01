package com.live.shoplib;

import android.app.FragmentManager;

import com.live.shoplib.bean.GoodsDetailsModel;
import com.live.shoplib.bean.SpikeGoodsDetailsModel;
import com.live.shoplib.ui.dialog.AttrDialog;
import com.live.shoplib.ui.dialog.GoodsSelDialog;
import com.live.shoplib.ui.dialog.GroupBuyGoodsSelDialog;
import com.live.shoplib.ui.dialog.JoinGroupBuyGoodsSelDialog;
import com.live.shoplib.ui.dialog.LogisticsDialog;
import com.live.shoplib.ui.dialog.ShopGoodsDialog;
import com.live.shoplib.ui.dialog.ShopSearchDialog;
import com.live.shoplib.ui.dialog.SpikeAttrDialog;
import com.live.shoplib.ui.dialog.SpikeGoodsSelDialog;

/**
 * 商城调用统一管理
 * 作者：Mr.Xu
 * 时间：2017/12/13
 */
public class ShopDialogFacade {


    public static void showShopSearchDialog(FragmentManager manager) {
        ShopSearchDialog dialog = ShopSearchDialog.newInstance();
        dialog.show(manager, "商品搜索");
    }

    //用户直播间-商品列表
    public static void showShopListDialog(FragmentManager manager,String roomId) {
        ShopGoodsDialog dialog = ShopGoodsDialog.newInstance(roomId);
        dialog.show(manager, "商品列表");
    }

    /**
     *
     * @param manager
     * @param order_id
     * @param refend_id
     * @param type  1 退货  2   发货
     */
    public static void showLogistrics(FragmentManager manager, String order_id,String refend_id,int type) {
        LogisticsDialog dialog = LogisticsDialog.newInstance(order_id,refend_id,type);
        dialog.show(manager, "发货");
    }

    public static void showSpikeGoodsAttrPro(FragmentManager manager, SpikeGoodsDetailsModel.DEntity bean, String title) {
        SpikeAttrDialog dialog = SpikeAttrDialog.newInstance(bean, title);
        dialog.show(manager, title);
    }

    public static void showGoodsAttrPro(FragmentManager manager, GoodsDetailsModel.DEntity bean, String title) {
        AttrDialog dialog = AttrDialog.newInstance(bean, title);
        dialog.show(manager, title);
    }

    public static void showGoodsSel(GoodsDetailsModel.DEntity bean, FragmentManager manager,String go, GoodsSelDialog.OnSureClick listner) {
        GoodsSelDialog dialog = GoodsSelDialog.newInstance(bean,go,listner);
        dialog.show(manager, "商品规格");
    }

    public static void showSpikeGoodsSel(SpikeGoodsDetailsModel.DEntity bean, FragmentManager manager, String go, GoodsSelDialog.OnSureClick listner) {
        SpikeGoodsSelDialog dialog = SpikeGoodsSelDialog.newInstance(bean,go,listner);
        dialog.show(manager, "秒杀商品规格");
    }

    public static void showGroupBuyGoodsSel(SpikeGoodsDetailsModel.DEntity bean, FragmentManager manager,String go, GoodsSelDialog.OnSureClick listner) {
        GroupBuyGoodsSelDialog dialog = GroupBuyGoodsSelDialog.newInstance(bean,go,listner);
        dialog.show(manager, "团购商品规格");
    }

    public static void showJoinGroupBuyGoodsSel(SpikeGoodsDetailsModel.DEntity bean, FragmentManager manager,String go,String groupBuyOrderId, JoinGroupBuyGoodsSelDialog.OnSureClick listner) {
        JoinGroupBuyGoodsSelDialog dialog = JoinGroupBuyGoodsSelDialog.newInstance(bean,go,groupBuyOrderId,listner);
        dialog.show(manager, "参加别人的团购商品规格");
    }
}
