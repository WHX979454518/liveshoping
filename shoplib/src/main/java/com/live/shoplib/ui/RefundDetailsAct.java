package com.live.shoplib.ui;

import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.hn.library.base.BaseActivity;
import com.hn.library.global.HnUrl;
import com.hn.library.http.BaseResponseModel;
import com.hn.library.http.HnHttpUtils;
import com.hn.library.http.HnResponseHandler;
import com.hn.library.utils.HnToastUtils;
import com.hn.library.utils.HnUtils;
import com.hn.library.view.CommDialog;
import com.hn.library.view.FrescoImageView;
import com.live.shoplib.R;
import com.live.shoplib.ShopActFacade;
import com.live.shoplib.ShopDialogFacade;
import com.live.shoplib.bean.ApplyBackSubModel;
import com.live.shoplib.bean.OrderRefreshEvent;
import com.live.shoplib.bean.RefundDetailsModel;
import com.live.shoplib.ui.dialog.RefundDialog;
import com.loopj.android.http.RequestParams;
import com.tencent.openqq.protocol.imsdk.msg;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * 退货详情
 * 作者：Mr.Xu
 * 时间：2017/12/18
 */
@Route(path = "/shoplib/RefundDetailsAct")
public class RefundDetailsAct extends BaseActivity {

    private TextView mTvType, mTvDate, mTvReason, mTvMoney, mTvRefundType, mTvApplyDate,
            mTvRefunId, mTvConnection, mTvLog, mTvRefuse, mTvAddAddress, mTvAgree, mTvRefuseMsg;
    private View mLineTag, mLineTag2;
    private LinearLayout mLLSelection;
    private FrescoImageView mIvGoodsIco;
    private TextView mTvGoodsName;
    private TextView mTvGoodsMsg;
    private TextView mTvGoodsNum;
    private LinearLayout mLLGoods;

    private RefundDetailsModel.DEntity.DetailsEntity bean;
    private RefundDetailsModel.DEntity.DialogueEntity chat;

    private String store_id;
    private String refund_id;
    private boolean isSeller = false;

    @Override
    public int getContentViewId() {
        return R.layout.act_refund_details;
    }

    @Override
    public void onCreateNew(Bundle savedInstanceState) {
        setShowBack(true);
        setTitle("退款详情");
        refund_id = getIntent().getStringExtra("refund_id");
        store_id = getIntent().getStringExtra("store_id");
        if (!TextUtils.isEmpty(store_id)) isSeller = true;

        if (TextUtils.isEmpty(refund_id)) {
            HnToastUtils.showToastShort("暂无记录");
            finish();
            return;
        }

        mTvType = findViewById(R.id.mTvType);
        mTvDate = findViewById(R.id.mTvDate);
        mTvReason = findViewById(R.id.mTvReason);
        mTvMoney = findViewById(R.id.mTvMoney);
        mTvRefundType = findViewById(R.id.mTvRefundType);
        mTvApplyDate = findViewById(R.id.mTvApplyDate);
        mTvRefunId = findViewById(R.id.mTvRefunId);
        mTvConnection = findViewById(R.id.mTvConnection);
        mTvLog = findViewById(R.id.mTvLog);
        mTvRefuse = findViewById(R.id.mTvRefuse);
        mTvAddAddress = findViewById(R.id.mTvAddAddress);
        mTvAgree = findViewById(R.id.mTvAgree);
        mLineTag = findViewById(R.id.mLineTag);
        mLineTag2 = findViewById(R.id.mLineTag2);
        mLLSelection = findViewById(R.id.mLLSelection);
        mLLGoods = findViewById(R.id.mLLGoods);
        mIvGoodsIco = findViewById(R.id.mIvGoodsIco);
        mTvGoodsName = findViewById(R.id.mTvGoodsName);
        mTvGoodsMsg = findViewById(R.id.mTvGoodsMsg);
        mTvGoodsNum = findViewById(R.id.mTvGoodsNum);
        mTvRefuseMsg = findViewById(R.id.mTvRefuseMsg);
        mSubtitle.setVisibility(View.GONE);


        if (TextUtils.isEmpty(store_id)) {
            mSubtitle.setText("再次申请");
            mSubtitle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (bean == null) return;
                    if (TextUtils.equals("1", bean.getShipping_status())) {
                        ShopActFacade.openApplyBack(true, bean.getOrder_id(), bean.getDetails_id());
                        finish();
                    } else {
                        submit(bean.getDetails_id(), bean.getNum(), bean.getOrder_id(), bean.getRemark(), bean.getType());
                    }
                }
            });
            refundDetailsUser();
        } else {
            mSubtitle.setText("确认收货");
            mSubtitle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CommDialog.newInstance(RefundDetailsAct.this)
                            .setTitle("提示")
                            .setContent("是否确认收货")
                            .setClickListen(new CommDialog.TwoSelDialog() {
                                @Override
                                public void leftClick() {

                                }

                                @Override
                                public void rightClick() {
                                    mSubtitle.setVisibility(View.GONE);
                                    refundOperate("6", "", refund_id, store_id);
                                }
                            })
                            .show();
                }
            });
            refundDetailsShop();
        }

    }

    @Override
    public void getInitData() {


    }

    public void onRefundDetails(View view) {
        if (view == mTvAgree) {//同意
            CommDialog.newInstance(this).setClickListen(new CommDialog.TwoSelDialog() {
                @Override
                public void leftClick() {

                }

                @Override
                public void rightClick() {
                    refundOperate("2", "", refund_id, store_id);
                }
            }).setTitle("退款").setContent("是否同意该买家退款申请").setRightText("同意").show();

        } else if (view == mTvRefuse) {//拒绝
            RefundDialog.newInstance(this)
                    .setClickListen(new RefundDialog.OneSelDialog() {
                        @Override
                        public void sureClick(String msg) {
                            refundOperate("5", msg, refund_id, store_id);
                        }
                    }).showDialog();
        } else if (view == mTvLog) {//查看物流
            if (bean == null) return;
            ShopActFacade.openGoodsLogistics(bean.getOrder_id());
        } else if (view == mTvConnection) {//联系
            if (chat == null) return;
            ShopActFacade.openPrivateChat(
                    isSeller ? chat.getId() : chat.getStore_uid(),
                    isSeller ? chat.getUser_name() : chat.getName(),
                    chat.getCharId());
        } else if (view == mLLGoods) {//查看详情
            if (bean == null) return;
            ShopActFacade.openGoodsDetails(bean.getGoods_id());
        } else if (view == mTvAddAddress) {//添加单号
            ShopDialogFacade.showLogistrics(getFragmentManager(), "", refund_id, 1);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(OrderRefreshEvent event) {
        HnToastUtils.showToastShort("提交成功");
        mTvAddAddress.setVisibility(View.GONE);
        refundDetailsUser();
    }


    public void refundDetailsUser() {
        RequestParams param = new RequestParams();
        param.put("refund_id", refund_id);
        HnHttpUtils.postRequest(HnUrl.REFUN_DETAILS, param, "退款详情-用户", new HnResponseHandler<RefundDetailsModel>(RefundDetailsModel.class) {
            @Override
            public void hnSuccess(String response) {
                if (isFinishing()) return;
                bean = model.getD().getDetails();
                chat = model.getD().getDialogue();
                mTvConnection.setText("联系卖家");
                mTvLog.setVisibility(View.GONE);
                mLineTag.setVisibility(View.GONE);
                setBaseData();
            }

            @Override
            public void hnErr(int errCode, String msg) {
                HnToastUtils.showToastShort(msg);
            }
        });
    }


    public void refundDetailsShop() {
        RequestParams param = new RequestParams();
        param.put("refund_id", refund_id);
        param.put("store_id", store_id);
        HnHttpUtils.postRequest(HnUrl.REFUN_DETAILS_SHOP, param, "退款详情-商家", new HnResponseHandler<RefundDetailsModel>(RefundDetailsModel.class) {
            @Override
            public void hnSuccess(String response) {
                if (isFinishing()) return;
                bean = model.getD().getDetails();
                chat = model.getD().getDialogue();
                mTvConnection.setText("联系买家");
                setBaseData();
            }

            @Override
            public void hnErr(int errCode, String msg) {
                HnToastUtils.showToastShort(msg);
            }
        });
    }

    //退款操作-2 同意 5拒绝 6 确认收货
    public void refundOperate(String status, String reason, String refund_id, String store_id) {
        RequestParams param = new RequestParams();
        param.put("refund_id", refund_id);
        param.put("store_id", store_id);
        param.put("status", status);
        if (!TextUtils.isEmpty(reason)) param.put("reason", reason);
        HnHttpUtils.postRequest(HnUrl.REFUN_OPERATE, param, "退款操作", new HnResponseHandler<BaseResponseModel>(BaseResponseModel.class) {
            @Override
            public void hnSuccess(String response) {
                EventBus.getDefault().post(new OrderRefreshEvent(-1));
                refundDetailsShop();
            }

            @Override
            public void hnErr(int errCode, String msg) {
                HnToastUtils.showToastShort(msg);
            }
        });
    }

    //设置基础界面
    private void setBaseData() {
        mTvDate.setText(bean.getUpdate_time());
        mTvReason.setText(bean.getRemark());
        setClickButtonStatue();
        if (TextUtils.isEmpty(bean.getGoods_price()) || TextUtils.isEmpty(bean.getNum())) {
            mTvMoney.setText(0);
        } else {
            try {
                mTvMoney.setText(bean.getOrder_amount());
            } catch (Exception e) {
                mTvMoney.setText(0);
            }
        }
        //退货类型 1只退款 2退货退款

        mIvGoodsIco.setImageURI(Uri.parse(HnUrl.setImageUrl(bean.getGoods_img())));
        mTvGoodsName.setText(bean.getGoods_name());
        mTvGoodsMsg.setText(bean.getGoods_attr());
        mTvGoodsNum.setText(bean.getNum());
        mTvApplyDate.setText("申请时间：" + bean.getAdd_time());
        mTvRefunId.setText("退款编号：" + bean.getOrder_sn());
        if (TextUtils.isEmpty(bean.getRefuse_reason())) {
            mTvRefuseMsg.setVisibility(View.GONE);
        } else {
            mTvRefuseMsg.setVisibility(View.VISIBLE);
            mTvRefuseMsg.setText("原因：" + bean.getRefuse_reason());
        }
        boolean isOnly = TextUtils.equals("1", bean.getType());
        if (isOnly) {
            mTvRefundType.setText("只退款");
        } else {
            mTvRefundType.setText("退货退款");
        }
        switch (bean.getStatus()) {
            case "1"://TODO 1买家已申请
                if (isSeller) {
                    mTvType.setText(isOnly ? "申请退款" : "申请退货退款");
                    mTvRefuse.setVisibility(View.VISIBLE);//同意拒绝
                    mTvAgree.setVisibility(View.VISIBLE);
                    mLineTag2.setVisibility(View.VISIBLE);
                } else {
                    mTvType.setText("等待商家处理");
                    mTvDate.setText("审核流程大概需要1-3个工作日，请耐心等待！");
                }
                break;
            case "2"://TODO 2卖家同意
                if (isSeller) {
                    mTvType.setText("审核通过，等待买家退货");
                } else {
                    mTvType.setText("卖家同意，请退货");
                    mTvAddAddress.setVisibility(View.VISIBLE);//买家添加地址
                }
                break;
            case "3"://TODO 3买家已发货
                if (isSeller) {
                    mTvType.setText("买家已发货");
                    mSubtitle.setVisibility(View.VISIBLE);//卖家确认收货
                    if (isOnly) {
                        mTvLog.setVisibility(View.GONE);
                        mLineTag.setVisibility(View.GONE);
                    } else {
                        mTvLog.setVisibility(View.VISIBLE);
                        mLineTag.setVisibility(View.VISIBLE);
                    }
                } else {
                    mTvType.setText("等待卖家收货并退款");
                }
                break;
            case "4"://TODO 4自己取消
                mTvType.setText("订单取消");
                break;
            case "5"://TODO 5商家拒绝
                if (isSeller) {
                    mTvType.setText("退款关闭");
                    mSubtitle.setVisibility(View.GONE);
                } else {
                    mTvType.setText("卖家拒绝了您的退款申请，退款关闭");
                    mSubtitle.setVisibility(View.VISIBLE);
                    mTvDate.setVisibility(View.GONE);
                }
                break;
            case "6"://TODO 6退款成功
                mTvType.setText("退款成功");
                if (isOnly) {
                    mTvLog.setVisibility(View.GONE);
                    mLineTag.setVisibility(View.GONE);
                } else {
                    mTvLog.setVisibility(View.VISIBLE);
                    mLineTag.setVisibility(View.VISIBLE);
                }
                break;
            case "7"://TODO 7退款关闭
                mTvType.setText("退款关闭");
                if (isOnly) {
                    mTvLog.setVisibility(View.GONE);
                    mLineTag.setVisibility(View.GONE);
                } else {
                    mTvLog.setVisibility(View.VISIBLE);
                    mLineTag.setVisibility(View.VISIBLE);
                }
                break;
        }
    }


    private void setClickButtonStatue() {
        mLineTag.setVisibility(View.GONE);
        mTvLog.setVisibility(View.GONE);
        mTvAddAddress.setVisibility(View.GONE);
        mTvRefuse.setVisibility(View.GONE);
        mLineTag2.setVisibility(View.GONE);
        mTvAgree.setVisibility(View.GONE);
        mSubtitle.setVisibility(View.GONE);
    }

    //	1 仅退款 2 退货退款
    public void submit(String details_id, String num, String order_id, String remark, String type) {
        RequestParams param = new RequestParams();
        param.put("details_id", details_id);
        param.put("num", num);
        param.put("order_id", order_id);
        param.put("type", type);
        if (!TextUtils.isEmpty(remark)) param.put("remark", remark);
        HnHttpUtils.postRequest(HnUrl.APP_BACK_SUB, param, "退款申请", new HnResponseHandler<ApplyBackSubModel>(ApplyBackSubModel.class) {
            @Override
            public void hnSuccess(String response) {
                mSubtitle.setVisibility(View.GONE);
                ShopActFacade.openRefundDetails(model.getD().getRefund_id());
            }

            @Override
            public void hnErr(int errCode, String msg) {
                HnToastUtils.showToastShort(msg);
            }
        });
    }

}
