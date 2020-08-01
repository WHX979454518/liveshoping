package com.live.shoplib.ui;

import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.hn.library.base.BaseActivity;
import com.hn.library.global.HnUrl;
import com.hn.library.http.HnHttpUtils;
import com.hn.library.http.HnResponseHandler;
import com.hn.library.utils.HnPrefUtils;
import com.hn.library.utils.HnToastUtils;
import com.hn.library.utils.HnUtils;
import com.hn.library.view.FrescoImageView;
import com.live.shoplib.R;
import com.live.shoplib.ShopActFacade;
import com.live.shoplib.bean.SellerCenterModel;
import com.live.shoplib.ui.dialog.HelpWithdrawDialog;
import com.loopj.android.http.RequestParams;
import com.reslibrarytwo.LevelView;

/**
 * 卖家中心
 * 作者：Mr.Xu
 * 时间：2018/1/3
 */
@Route(path = "/shoplib/SellerCenterAct")
public class SellerCenterAct extends BaseActivity {

    private ImageView mIvBack;
    private FrescoImageView mIvIco;
    private TextView mTvName;
    private TextView mTvID;
    private TextView mTvOrderNum;
    private TextView mTvCollectNum;
    private LinearLayout mLLSellerOrder;
    private TextView mTvShopWaitPay;
    private TextView mTvShopWaitDeliver;
    private TextView mTvShopWaitGet;
    private TextView mTvShopGoods;
    private ImageView mIvQues;
    private TextView mTvAllMoney;
    private TextView mTvDealMoney;
    private TextView mTvWithdrawMoney;
    private TextView mTvWithdraw;
    private TextView mTvAuth;
    private LinearLayout mLLAuth;
    private TextView mTvStoreEdit;
    private TextView mTvStoreSort;
    private TextView mTvGoodsManager;
    private TextView mTvFreight;
    private TextView mTvMyRoomManager;
    private TextView mTvMyBack;

    private RelativeLayout mRlPay;
    private RelativeLayout mRlDelive;
    private RelativeLayout mRlGet;
    private RelativeLayout mRlRefun;
    private TextView mTvPayNum;
    private TextView mTvDeliverNum;
    private TextView mTvGetNum;
    private TextView mTvHistory;
    private TextView mTvRefundNum;
    private LevelView mLevelView;


    private boolean authDetails = false;
    private String authDetailsStu;
    private SellerCenterModel.DEntity bean;
    private String store_id;

    @Override
    public int getContentViewId() {
        return R.layout.act_seller_center;
    }

    @Override
    public void onCreateNew(Bundle savedInstanceState) {
        setShowTitleBar(false);
        initView();
        store_id = getIntent().getStringExtra("store_id");
    }

    @Override
    public void getInitData() {

    }

    private void initView() {
        mIvBack = (ImageView) findViewById(R.id.mIvBack);
        mIvIco = (FrescoImageView) findViewById(R.id.mIvIco);
        mTvName = (TextView) findViewById(R.id.mTvName);
        mTvHistory = (TextView) findViewById(R.id.mTvHistory);
        mTvID = (TextView) findViewById(R.id.mTvID);
        mTvOrderNum = (TextView) findViewById(R.id.mTvOrderNum);
        mTvCollectNum = (TextView) findViewById(R.id.mTvCollectNum);
        mLLSellerOrder = (LinearLayout) findViewById(R.id.mLLSellerOrder);
        mTvShopWaitPay = (TextView) findViewById(R.id.mTvShopWaitPay);
        mTvShopWaitDeliver = (TextView) findViewById(R.id.mTvShopWaitDeliver);
        mTvShopWaitGet = (TextView) findViewById(R.id.mTvShopWaitGet);
        mTvShopGoods = (TextView) findViewById(R.id.mTvShopGoods);
        mIvQues = (ImageView) findViewById(R.id.mIvQues);
        mTvAllMoney = (TextView) findViewById(R.id.mTvAllMoney);
        mTvDealMoney = (TextView) findViewById(R.id.mTvDealMoney);
        mTvWithdrawMoney = (TextView) findViewById(R.id.mTvWithdrawMoney);
        mTvWithdraw = (TextView) findViewById(R.id.mTvWithdraw);
        mTvAuth = (TextView) findViewById(R.id.mTvAuth);
        mLLAuth = (LinearLayout) findViewById(R.id.mLLAuth);
        mTvStoreEdit = (TextView) findViewById(R.id.mTvStoreEdit);
        mTvStoreSort = (TextView) findViewById(R.id.mTvStoreSort);
        mTvGoodsManager = (TextView) findViewById(R.id.mTvGoodsManager);
        mTvFreight = (TextView) findViewById(R.id.mTvFreight);
        mTvMyRoomManager = (TextView) findViewById(R.id.mTvMyRoomManager);
        mTvMyBack = (TextView) findViewById(R.id.mTvMyBack);
        mLevelView = findViewById(R.id.mLevelView);

        mRlDelive = findViewById(R.id.mRlDeliver);
        mRlPay = findViewById(R.id.mRlPay);
        mRlGet = findViewById(R.id.mRlGet);
        mRlRefun = findViewById(R.id.mRlRefund);
        mTvPayNum = (TextView) findViewById(R.id.mTvPayNum);
        mTvDeliverNum = (TextView) findViewById(R.id.mTvDeliverNum);
        mTvGetNum = (TextView) findViewById(R.id.mTvGetNum);
        mTvRefundNum = (TextView) findViewById(R.id.mTvRefundNum);


    }


    @Override
    protected void onResume() {
        super.onResume();
        requestData();
    }

    private void requestData() {
        RequestParams param = new RequestParams();
        HnHttpUtils.postRequest(HnUrl.SELL_CENTER, param, "卖家中心", new HnResponseHandler<SellerCenterModel>(SellerCenterModel.class) {
            @Override
            public void hnSuccess(String response) {
                if (isFinishing() || model.getD() == null) return;
                bean = model.getD();
                mIvIco.setImageURI(Uri.parse(HnUrl.setImageUrl(model.getD().getStore().getIcon())));
                mTvName.setText(model.getD().getStore().getName());
                mTvOrderNum.setText(model.getD().getStore().getSale() + "");
                mTvCollectNum.setText(model.getD().getStore().getCollect() + "");
                try {
                    mLevelView.setLevelAnchor(Integer.valueOf(model.getD().getStore().getAnchor_level()));
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                    mLevelView.setLevelAnchor(1);
                }
                mTvID.setText("店铺ID：" + model.getD().getStore().getStore_id());
                mTvAllMoney.setText(HnUtils.setTwoPoint(model.getD().getStore().getTotal_money()));
                mTvDealMoney.setText(HnUtils.setTwoPoint(model.getD().getStore().getTotal_deal()));
                mTvWithdrawMoney.setText(HnUtils.setTwoPoint(model.getD().getStore().getTotal_cash()));
                authDetailsStu = model.getD().getStore().getStore_certification_status();

                if (model.getD().getOrder() != null) {
                    SellerCenterModel.DEntity.OrderEntity order = model.getD().getOrder();
                    setOrderNum(mTvPayNum, order.getNon_payment());
                    setOrderNum(mTvDeliverNum, order.getDrop_shipping());
                    setOrderNum(mTvGetNum, order.getWait_receiving());
                    setOrderNum(mTvRefundNum, order.getRefund());
                }

                Drawable drawable = getResources().getDrawable(R.mipmap.more_list);
                drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                switch (model.getD().getStore().getStore_certification_status()) {
                    case "Y"://通过
                        authDetails = true;
                        mTvAuth.setText("恭喜您，认证审核成功");
                        mTvAuth.setTextColor(getResources().getColor(R.color.main_color));
                        mTvAuth.setCompoundDrawables(null, null, null, null);
                        break;
                    case "N"://拒绝
                        authDetails = false;
                        mTvAuth.setText("很遗憾，认证审核不通过");
                        mTvAuth.setTextColor(getResources().getColor(R.color.comm_text_color_black_s));
                        mTvAuth.setCompoundDrawables(null, null, drawable, null);
                        break;
                    case "C"://审核中
                        authDetails = true;
                        mTvAuth.setText("认证审核中，请耐心等待");
                        mTvAuth.setTextColor(getResources().getColor(R.color.main_color));
                        mTvAuth.setCompoundDrawables(null, null, null, null);
                        break;
                    case "Z"://不存在
                        authDetails = false;
                        mTvAuth.setText("需要通过认证后才能开店直播");
                        mTvAuth.setTextColor(getResources().getColor(R.color.comm_text_color_black_s));
                        mTvAuth.setCompoundDrawables(null, null, drawable, null);
                        break;
                }
            }

            @Override
            public void hnErr(int errCode, String msg) {
                HnToastUtils.showToastShort(msg);
            }
        });
    }

    private void setOrderNum(TextView tv, int num) {
        if (0 < num) {
            tv.setVisibility(View.VISIBLE);
            if (num > 99) tv.setText("99+");
            else tv.setText(num + "");
        } else {
            tv.setVisibility(View.GONE);
        }
    }

    public void onSellerCenterClick(View view) {

        if (view == mLLAuth) {//实名认证
            if (authDetails) {
                if (!"Y".equals(authDetailsStu))
                    ShopActFacade.openStoreAut("");
            } else {
                if ("N".equals(authDetailsStu)) {//拒绝
                    ShopActFacade.openStoreAut("");
                } else {
                    ShopActFacade.openAuthSort();
                }
            }
            return;
        } else if (view == mIvBack) {
            finish();
            return;
        }


        if (!TextUtils.isEmpty(store_id) && !TextUtils.equals("0", store_id)) {
            if (view == mLLSellerOrder) {
                ShopActFacade.openShopOrder(true, 0);
            } else if (view == mRlPay) {
                ShopActFacade.openShopOrder(true, 1);
            } else if (view == mRlDelive) {
                ShopActFacade.openShopOrder(true, 2);
            } else if (view == mRlGet) {
                ShopActFacade.openShopOrder(true, 3);
            } else if (view == mRlRefun) {
                ShopActFacade.openRefundGoods(store_id);
            } else if (view == mIvQues) {
                HelpWithdrawDialog.newInstance(this).show();
            } else if (view == mTvWithdraw) {//提现
                if (bean == null) return;
                ShopActFacade.openWithDrawWrite(HnUtils.setTwoPoint(bean.getStore().getTotal_cash()), bean.getStore().getWithdraw());
            } else if (view == mTvStoreEdit) {//商品编辑
                if (bean == null) return;
                ShopActFacade.openStoreEdit(bean.getStore().getStore_id());
            } else if (view == mTvStoreSort) {//商品分类
                if (bean == null) return;
                ShopActFacade.openStoreGroup(bean.getStore().getStore_id());
            } else if (view == mTvGoodsManager) {//商品管理
                ShopActFacade.openMyStoreAct(bean.getStore().getStore_id());
//                ShopActFacade.openStoreGoods();
            } else if (view == mTvFreight) {//运费设置
                ShopActFacade.openFreightSetAct(bean.getStore().getStore_id());
            } else if (view == mTvMyRoomManager) {//我的房管
                ShopActFacade.openMyAdminActivity();
            } else if (view == mTvMyBack) {
                if (bean == null) return;
                ShopActFacade.openBackList(bean.getStore().getUserId(), "店铺精彩回放");
            } else if (view == mTvHistory) {
                ShopActFacade.openHistoryRecordAct();
            } else {
                ShopActFacade.openWeb("店铺等级", HnUrl.USER_LEVEL_ANCHOR, "level");
            }
        } else {
            HnToastUtils.showToastShort("请先进行店铺认证");
        }


    }

}
