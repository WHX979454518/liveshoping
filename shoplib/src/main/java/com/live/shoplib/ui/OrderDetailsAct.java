package com.live.shoplib.ui;

import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.hn.library.base.BaseActivity;
import com.hn.library.base.baselist.BaseViewHolder;
import com.hn.library.base.baselist.CommRecyclerAdapter;
import com.hn.library.global.HnUrl;
import com.hn.library.http.HnHttpUtils;
import com.hn.library.http.HnResponseHandler;
import com.hn.library.utils.HnToastUtils;
import com.hn.library.view.CommDialog;
import com.hn.library.view.FrescoImageView;
import com.live.shoplib.R;
import com.live.shoplib.ShopActFacade;
import com.live.shoplib.ShopDialogFacade;
import com.live.shoplib.ShopRequest;
import com.live.shoplib.bean.OrderModel;
import com.live.shoplib.bean.OrderRefreshEvent;
import com.loopj.android.http.RequestParams;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

/**
 * 订单详情
 * 作者：Mr.Xu
 * 时间：2017/12/20
 * ARouter.getInstance().build("/shoplib/OrderDetailsAct").navigation();
 */
@Route(path = "/shoplib/OrderDetailsAct")
public class OrderDetailsAct extends BaseActivity {

    private TextView mTvGetName;
    private TextView mTvGetPhone;
    private TextView mTvGetAddress;
    private FrescoImageView mIvUserIco;
    private TextView mTvUserName;
    private RecyclerView mRecycler;
    private TextView mTvGoodsPrice;
    private TextView mTvFreight;
    private TextView mTvAllPrice;
    private TextView mTvConnection;
    private LinearLayout tag;
    private TextView mTvCancel;
    private TextView mTvDelete;
    private TextView mTvQuery;
    private TextView mTvToEva;
    private TextView mTvPay;
    private TextView mTvSure;
    private TextView mTvBack;
    private TextView mTvEva;
    private TextView mTvOrderMsg;
    private TextView mTvToDeliver;
    private TextView mTvLookRefundDetail;

    private List<OrderModel.DEntity.DetailsEntity.DetailsEntitys> mData = new ArrayList<>();
    private CommRecyclerAdapter mAdapter;
    private OrderModel.DEntity bean;
    private String order_id;

    private boolean isSeller = false;

    @Override
    public int getContentViewId() {
        return R.layout.act_order_details;
    }

    @Override
    public void onCreateNew(Bundle savedInstanceState) {
        setTitle("订单详情");
        setShowBack(true);
        initId();
        initGoods();
    }

    @Override
    public void getInitData() {
        order_id = getIntent().getStringExtra("id");
        isSeller = getIntent().getBooleanExtra("isSeller", false);
        mTvConnection.setText(isSeller ? "联系买家" : "联系卖家");

        if (TextUtils.isEmpty(order_id)) {
            HnToastUtils.showToastShort("暂无数据");
            finish();
            return;
        } else {
            getDetails(order_id);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        getDetails(order_id);
    }

    private void initId() {
        mTvGetName = (TextView) findViewById(R.id.mTvGetName);
        mTvGetPhone = (TextView) findViewById(R.id.mTvGetPhone);
        mTvGetAddress = (TextView) findViewById(R.id.mTvGetAddress);
        mIvUserIco = (FrescoImageView) findViewById(R.id.mIvUserIco);
        mTvUserName = (TextView) findViewById(R.id.mTvUserName);
        mRecycler = (RecyclerView) findViewById(R.id.mRecycler);
        mTvGoodsPrice = (TextView) findViewById(R.id.mTvGoodsPrice);
        mTvFreight = (TextView) findViewById(R.id.mTvFreight);
        mTvAllPrice = (TextView) findViewById(R.id.mTvAllPrice);
        mTvConnection = (TextView) findViewById(R.id.mTvConnection);
        tag = (LinearLayout) findViewById(R.id.tag);
        mTvCancel = (TextView) findViewById(R.id.mTvCancel);
        mTvDelete = (TextView) findViewById(R.id.mTvDelete);
        mTvQuery = (TextView) findViewById(R.id.mTvQuery);
        mTvToEva = (TextView) findViewById(R.id.mTvToEva);
        mTvPay = (TextView) findViewById(R.id.mTvPay);
        mTvSure = (TextView) findViewById(R.id.mTvSure);
        mTvBack = (TextView) findViewById(R.id.mTvBack);
        mTvEva = (TextView) findViewById(R.id.mTvEva);
        mTvOrderMsg = (TextView) findViewById(R.id.mTvOrderMsg);
        mTvToDeliver = (TextView) findViewById(R.id.mTvToDeliver);
        mTvLookRefundDetail = (TextView) findViewById(R.id.mTvLookRefundDetail);
    }


    private void initGoods() {
        mRecycler.setLayoutManager(new LinearLayoutManager(this));
        mRecycler.setAdapter(mAdapter = new CommRecyclerAdapter() {
            @Override
            protected void onBindView(BaseViewHolder holder, final int position) {
                //商品图
                ((FrescoImageView) holder.getView(R.id.mIvGoodsIco)).setImageURI(Uri.parse(HnUrl.setImageUrl(mData.get(position).getGoods_img())));
                //商品名
                holder.setTextViewText(R.id.mTvGoodsName, mData.get(position).getGoods_name());
                //商品信息
                holder.setTextViewText(R.id.mTvGoodsMsg, mData.get(position).getGoods_attr());
                //价钱
                holder.setTextViewText(R.id.mTvPrice, mData.get(position).getGoods_price());
                //数量
                holder.setTextViewText(R.id.mTvGoodsNum, mData.get(position).getGoods_number());
                //状态 0 正常订单 1 退货订单 2 退货完成 3 退款关闭
                TextView mTvGoodsTag = holder.getView(R.id.mTvGoodsTag);
                TextView mTvGoodsRefend = holder.getView(R.id.mTvGoodsRefend);

                mTvGoodsTag.setText("");
                mTvGoodsRefend.setVisibility(View.GONE);
                switch (mData.get(position).getStatus()) {
                    case "0":
                        switch (bean.getOrder_status()) {
                            case "0":
                                break;
                            case "3":
                                if (!isSeller)
                                    mTvGoodsRefend.setVisibility(View.VISIBLE);
                                mTvGoodsRefend.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        ShopActFacade.openApplyBack(false, bean.getOrderId(), bean.getDetails().getDetails().get(position).getDetails_id());
                                    }
                                });
                                break;
                            case "4":
                                if (!isSeller)
                                    mTvGoodsRefend.setVisibility(View.VISIBLE);
                                mTvGoodsRefend.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        ShopActFacade.openApplyBack(true, bean.getOrderId(), bean.getDetails().getDetails().get(position).getDetails_id());
                                    }
                                });
                                break;
                        }
                        //去申请
                        break;
                    case "1":
                        if(Integer.valueOf(mData.get(position).getGoods_number())==Integer.valueOf(mData.get(position).getRefund_number())){
                            mTvGoodsTag.setText("退款中");
                        }else {
                            mTvGoodsTag.setText("部分退款中");
                        }
                        //退款详情
                        break;
                    case "2":
                        mTvGoodsTag.setText("退款成功");
                        //退款详情
                        break;
                    case "3":
                        mTvGoodsTag.setText("退款关闭");
                        //退款详情
                        break;
                    default:
                        break;
                }
                mTvGoodsTag.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (isSeller) {
                            ShopActFacade.openRefundDetails(mData.get(position).getRefund_id(), bean.getDetails().getStore_id());
                        } else {
                            ShopActFacade.openRefundDetails(mData.get(position).getRefund_id());
                        }
                    }
                });
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ShopActFacade.openGoodsDetails(mData.get(position).getGoods_id());
                    }
                });
            }

            @Override
            protected int getLayoutID(int position) {
                return R.layout.item_order_goods_msg;
            }

            @Override
            public int getItemCount() {
                return mData.size();
            }
        });

    }


    public void onOrderDetailsClick(View v) {
        if (v == mTvConnection) {//客服
            if (bean == null || bean.getDialogue() == null) return;
            ShopActFacade.openPrivateChat(
                    isSeller ? bean.getOrder().getBuy_uid() : bean.getDialogue().getStore_uid(),
                    isSeller ? bean.getDialogue().getUser_name() : bean.getDialogue().getName(),
                    bean.getDialogue().getCharId());
        } else if (v == mTvSure) {//确定收货
            boolean isRefend = false;
            for (int i = 0; i < mData.size(); i++) {
                if (TextUtils.equals(mData.get(i).getStatus(), "1")) {
                    isRefend = true;
                }
            }
            if (isRefend) {
                CommDialog.newInstance(this)
                        .setTitle("提示")
                        .setContent("该订单中存在退款中的商品，确认收货会关闭退款")
                        .setRightText("确认收货")
                        .setClickListen(new CommDialog.TwoSelDialog() {
                            @Override
                            public void leftClick() {

                            }

                            @Override
                            public void rightClick() {
                                ShopRequest.sureOrder(order_id, new ShopRequest.OnRespondNothing() {
                                    @Override
                                    public void finishs() {
                                        EventBus.getDefault().post(new OrderRefreshEvent(-1));
                                        getDetails(order_id);
                                    }
                                });
                            }
                        })
                        .show();
            } else {
                CommDialog.newInstance(this)
                        .setTitle("确认收货")
                        .setContent("是否确认收货该商品")
                        .setRightText("确定")
                        .setClickListen(new CommDialog.TwoSelDialog() {
                            @Override
                            public void leftClick() {

                            }

                            @Override
                            public void rightClick() {
                                ShopRequest.sureOrder(order_id, new ShopRequest.OnRespondNothing() {
                                    @Override
                                    public void finishs() {
                                        EventBus.getDefault().post(new OrderRefreshEvent(-1));
                                        getDetails(order_id);
                                    }
                                });
                            }
                        })
                        .show();
            }
        } else if (v == mTvCancel) {//取消订单
            CommDialog.newInstance(this)
                    .setTitle("取消订单")
                    .setContent("是否确认取消订单")
                    .setClickListen(new CommDialog.TwoSelDialog() {
                        @Override
                        public void leftClick() {

                        }

                        @Override
                        public void rightClick() {
                            ShopRequest.cancelOrder(order_id, new ShopRequest.OnRespondNothing() {
                                @Override
                                public void finishs() {
                                    EventBus.getDefault().post(new OrderRefreshEvent(-1));
                                    getDetails(order_id);
                                    HnToastUtils.showToastShort("订单取消成功");
                                }
                            });
                        }
                    })
                    .show();


        } else if (v == mTvDelete) {//删除订单
            ShopRequest.deleteOrder(order_id, new ShopRequest.OnRespondNothing() {
                @Override
                public void finishs() {
                    EventBus.getDefault().post(new OrderRefreshEvent(-1));
                    getDetails(order_id);
                }
            });
        } else if (v == mTvQuery) {//查看物流
            ShopActFacade.openGoodsLogistics(order_id);
        } else if (v == mTvToEva) {//去评价
            ShopActFacade.openEvaPublic(order_id);
        } else if (v == mTvPay) {//去支付
            if (bean == null) return;
            if(TextUtils.equals(bean.getDetails().getStore_status(),"0")){
                HnToastUtils.showToastShort("店铺已被冻结，暂时无法支付");
                return;
            }
            ShopActFacade.openPayDetails(order_id, bean.getDetails().getOrder_amount());
        } else if (v == mTvBack) {
            if (bean == null) return;
            if ("F".equals(whichType(bean))) {
//                HnToastUtils.showToastShort("退款成功");
            } else if ("B".equals(whichType(bean))) {
//                HnToastUtils.showToastShort("退款中");
            }
        } else if (v == mTvToDeliver) {//去发货
            ShopDialogFacade.showLogistrics(getFragmentManager(),
                    order_id, "", 2);
        } else if (v == mTvEva) {
            if (bean == null) return;
            ShopActFacade.openEvaGoods(bean.getOrderId(), true);
        }
    }


    private String whichType(OrderModel.DEntity entity) {
        int back = 0;
        int finish = 0;
        int all = entity.getDetails().getDetails().size();
        for (int i = 0; i < all; i++) {
            //0 正常订单 1 退货订单 2 退货完成 3 退款关闭
            String state = entity.getDetails().getDetails().get(i).getStatus();
            switch (state) {
                case "0":
                    break;
                case "1":
                    back++;
                    break;
                case "2":
                case "3":
                    finish++;
                    break;
            }
        }
        if (finish == all) {
            return "F";
        } else if (back == all) {
            return "B";
        } else {
            return "N";
        }
    }


    public void getDetails(String order_id) {
        RequestParams param = new RequestParams();
        param.put("order_id", order_id);
        HnHttpUtils.postRequest(HnUrl.ORDER_DETAILS, param, "订单详情", new HnResponseHandler<OrderModel>(OrderModel.class) {
            @Override
            public void hnSuccess(String response) {
                if (isDestroyed()) return;
                setClickStatueBrfore();
                bean = model.getD();
                //收货
                mTvGetName.setText("收货人：" + bean.getConsignee().getName());
                mTvGetAddress.setText(bean.getConsignee().getAddress());
                mTvGetPhone.setText(bean.getConsignee().getPhone());
                //商品
                mData.clear();
                mData.addAll(bean.getDetails().getDetails());
                mAdapter.notifyDataSetChanged();
                //金额
                mTvGoodsPrice.setText(bean.getDetails().getGoods_amount());
                if(Double.valueOf(bean.getDetails().getShop_fee())!=0){
                    mTvFreight.setText("¥" + bean.getDetails().getShop_fee());
                } else {
                    mTvFreight.setText("包邮");
                }
                mTvAllPrice.setText(bean.getDetails().getOrder_amount());
                mIvUserIco.setImageURI(HnUrl.setImageUri(HnUrl.setImageUrl(bean.getDetails().getStore_icon())));
                mTvUserName.setText(bean.getDetails().getStore_name());
                //订单信息
                String msg1 = "订单编号:" + bean.getOrder().getOrder_sn();
                String msg2 = "下单时间:" + bean.getOrder().getTime();
                String msg3 = "发货时间:" + bean.getOrder().getShipping_time();
                mTvOrderMsg.setVisibility(View.VISIBLE);
                mTvOrderMsg.setText(msg1 + "\n\n" + msg2 + (TextUtils.isEmpty(bean.getOrder().getShipping_time()) ? "" : "\n\n" + msg3));
                //0 待支付 1 确认收货,待评价 2 已取消 3 待发货 4 已发货 待收货 5 已评价【已完成】
                if (isSeller) {
                    switch (bean.getOrder_status()) {
                        case "0"://待支付
                            break;
                        case "1"://确认收货,待评价
                            mTvQuery.setVisibility(View.VISIBLE);//查看物流
                            break;
                        case "2"://已取消
                            mTvDelete.setVisibility(View.VISIBLE);//删除订单
                            break;
                        case "3"://待发货
                            if ("F".equals(whichType(bean))) {
//                                mTvBack.setText("退款完成");
                                mTvToDeliver.setVisibility(View.VISIBLE);
                            } else if ("B".equals(whichType(bean))) {

                            } else {
                                mTvToDeliver.setVisibility(View.VISIBLE);
                            }
                            break;
                        case "4"://已发货 待收货
                            mTvQuery.setVisibility(View.VISIBLE);//查看物流
                            break;
                        case "5"://已评价【已完成】
                            mTvEva.setVisibility(View.VISIBLE);//评价
                            mTvQuery.setVisibility(View.VISIBLE);//查看物流
                            break;
                    }

                } else {
                    switch (bean.getOrder_status()) {
                        case "0"://待支付
                            mTvCancel.setVisibility(View.VISIBLE);//取消订单
                            mTvPay.setVisibility(View.VISIBLE);//去支付
                            break;
                        case "1"://确认收货,待评价

                            mTvQuery.setVisibility(View.VISIBLE);//查看物流
                            mTvToEva.setVisibility(View.VISIBLE);//去评价
                            break;
                        case "2"://已取消
                            mTvDelete.setVisibility(View.VISIBLE);//删除订单
                            break;
                        case "3"://待发货
                            if ("F".equals(whichType(bean))) {
                                mTvBack.setVisibility(View.VISIBLE);//退款
                                mTvBack.setText("退款完成");
                                mTvDelete.setVisibility(View.VISIBLE);
                            } else if ("B".equals(whichType(bean))) {
                                mTvBack.setVisibility(View.GONE);//退款
                                mTvBack.setText("退款中");
                            } else {
                                mTvBack.setVisibility(View.GONE);//退款
                            }
                            break;
                        case "4"://已发货 待收货
                            if ("F".equals(whichType(bean))) {//退款完成
                                mTvSure.setVisibility(View.GONE);//确认收货
                            } else if ("B".equals(whichType(bean))) {
                                mTvSure.setVisibility(View.VISIBLE);//退款中
                            } else {
                                mTvSure.setVisibility(View.VISIBLE);
                            }

                            mTvQuery.setVisibility(View.VISIBLE);//查看物流

                            break;
                        case "5"://已评价【已完成】
                            mTvDelete.setVisibility(View.VISIBLE);//删除订单
                            mTvQuery.setVisibility(View.VISIBLE);//查看物流
                            break;
                    }
                }
            }

            @Override
            public void hnErr(int errCode, String msg) {
                HnToastUtils.showToastShort(msg);
            }
        });
    }

    /**
     * 点击按钮之后先隐藏所有按钮
     */
    private void setClickStatueBrfore() {
        mTvSure.setVisibility(View.GONE);
        mTvQuery.setVisibility(View.GONE);//查看物流
        mTvToEva.setVisibility(View.GONE);//去评价
        mTvDelete.setVisibility(View.GONE);//删除订单
        mTvBack.setVisibility(View.GONE);//退款
        mTvCancel.setVisibility(View.GONE);//取消订单
        mTvPay.setVisibility(View.GONE);//去付款
        mTvToDeliver.setVisibility(View.GONE);//去付款
        mTvLookRefundDetail.setVisibility(View.GONE);//去付款
    }
}
