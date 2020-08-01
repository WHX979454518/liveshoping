package com.hotniao.live.activity.account;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alipay.sdk.app.PayTask;
import com.hn.library.base.BaseActivity;
import com.hn.library.base.baselist.BaseViewHolder;
import com.hn.library.base.baselist.CommRecyclerAdapter;
import com.hn.library.global.HnUrl;
import com.hn.library.http.BaseResponseModel;
import com.hn.library.http.HnHttpUtils;
import com.hn.library.http.HnResponseHandler;
import com.hn.library.pay.PayResult;
import com.hn.library.utils.HnPrefUtils;
import com.hn.library.utils.HnToastUtils;
import com.hn.library.utils.HnUiUtils;
import com.hn.library.utils.HnUtils;
import com.hotniao.live.R;
import com.hotniao.live.model.ExchangeCoinModel;
import com.hotniao.live.model.HnProfileMode;
import com.hotniao.live.model.MoneyPayModel;
import com.hotniao.live.model.OrderPayModel;
import com.hotniao.live.model.PayLogModel;
import com.hotniao.live.model.bean.HnProfileBean;
import com.live.shoplib.ShopActFacade;
import com.live.shoplib.bean.AliPayModel;
import com.live.shoplib.bean.WxPayModel;
import com.live.shoplib.ui.PayResponseAct;
import com.loopj.android.http.RequestParams;
import com.tencent.mm.sdk.modelpay.PayReq;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 消费记录
 * 作者：Mr.Xu
 * 时间：2018/1/5
 */
@Route(path = "/app/HnRechargeAct")
public class HnRechargeAct extends BaseActivity {
    @BindView(R.id.mTvCoin)
    TextView mTvCoin;
    @BindView(R.id.mRecycler)
    RecyclerView mRecycler;
    @BindView(R.id.mBoxAli)
    CheckBox mBoxAli;
    @BindView(R.id.mBoxWx)
    CheckBox mBoxWx;
    private static final int SDK_PAY_FLAG = 1;
    private IWXAPI mWxapi;

    private CommRecyclerAdapter mAdapter;
    private List<HnProfileBean.RechargeComboBean> mData = new ArrayList<>();

    @Override
    public int getContentViewId() {
        setTitle("充值");
        setShowBack(true);
        return R.layout.act_recharge;
    }

    @Override
    public void onCreateNew(Bundle savedInstanceState) {
        mSubtitle.setText("消费记录");
        mSubtitle.setVisibility(View.VISIBLE);
        mSubtitle.setTextColor(getResources().getColor(R.color.comm_text_color_black_s));
        mSubtitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity(HnConsumeHisAct.class);
            }
        });
        mRecycler.setHasFixedSize(true);
        mRecycler.setLayoutManager(new LinearLayoutManager(this));
        mRecycler.setAdapter(mAdapter = new CommRecyclerAdapter() {
            @Override
            protected void onBindView(BaseViewHolder holder, final int position) {
                holder.setTextViewText(R.id.mTvCoin, mData.get(position).getRecharge_combo_coin() + "银币");
                holder.setTextViewText(R.id.mTvSubmit, "¥" + mData.get(position).getRecharge_combo_fee());
                holder.getView(R.id.mTvSubmit).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        requestSub(mData.get(position).getRecharge_combo_id(), mBoxAli.isChecked());
                    }
                });
            }

            @Override
            protected int getLayoutID(int position) {
                return R.layout.item_exchange_coin;
            }

            @Override
            public int getItemCount() {
                return mData.size();
            }
        });
    }

    @Override
    public void getInitData() {
    }

    @Override
    protected void onResume() {
        super.onResume();
        requestData();
    }

    private void requestData() {
        RequestParams param = new RequestParams();
        HnHttpUtils.postRequest(HnUrl.Get_Account, param, "兑换列表", new HnResponseHandler<HnProfileMode>(HnProfileMode.class) {
            @Override
            public void hnSuccess(String response) {
                if (isFinishing() || model.getD() == null) return;
                mData.clear();
                mData.addAll(model.getD().getRecharge_combo());
                mAdapter.notifyDataSetChanged();
                mTvCoin.setText(HnUtils.setTwoPoint(model.getD().getUser().getUser_coin()));
            }

            @Override
            public void hnErr(int errCode, String msg) {
                HnToastUtils.showToastShort(msg);
            }
        });
    }

    private void requestSub(String recharge_combo_id, final boolean ali) {
        RequestParams param = new RequestParams();
        param.put("recharge_combo_id", recharge_combo_id);
        HnHttpUtils.postRequest(HnUrl.ORDER_PAY, param, "充值", new HnResponseHandler<OrderPayModel>(OrderPayModel.class) {
            @Override
            public void hnSuccess(String response) {
                if(isFinishing())return;
                if(ali){
                    toAliPay(model.getD().getRecharge_id());
                }else {
                    toWxPay(model.getD().getRecharge_id());
                }
            }

            @Override
            public void hnErr(int errCode, String msg) {
                HnToastUtils.showToastShort(msg);
            }
        });
    }



    public void toAliPay(final String recharge_id) {
        RequestParams param = new RequestParams();
        param.put("recharge_id", recharge_id);
        param.put("type", "aliPay");
        HnHttpUtils.postRequest(HnUrl.MONEY_PAY, param, "去支付", new HnResponseHandler<AliPayModel>(AliPayModel.class) {
            @Override
            public void hnSuccess(String response) {
//
                if (isFinishing() || model.getD() == null) return;
                Runnable payRunnable = new Runnable() {
                    @Override
                    public void run() {
                        PayTask alipay = new PayTask(HnRechargeAct.this);
                        Map<String, String> result = alipay.payV2(model.getD().getSign(), true);
                        Message msg = new Message();
                        msg.what = SDK_PAY_FLAG;
                        msg.obj = result;
                        mHandler.sendMessage(msg);
                    }
                };
                // 必须异步调用
                Thread payThread = new Thread(payRunnable);
                payThread.start();
            }

            @Override
            public void hnErr(int errCode, String msg) {
                HnToastUtils.showToastShort(msg);
            }
        });
    }

    public void toWxPay(final String recharge_id) {
        final RequestParams param = new RequestParams();
        param.put("recharge_id", recharge_id);
        param.put("type", "wxPay");
        HnHttpUtils.postRequest(HnUrl.MONEY_PAY, param, "去支付", new HnResponseHandler<WxPayModel>(WxPayModel.class) {
            @Override
            public void hnSuccess(String response) {
                if (isFinishing() || model.getD() == null) return;
                HnPrefUtils.setString("order_id", "");
                WxPayModel.DEntity payBean = model.getD();
                if (param != null) {
                    //appId
                    String appid = payBean.getData().getAppid();
                    //随机字符串
                    String noncestr = payBean.getData().getNoncestr();
                    //扩展字段
                    String packageX = payBean.getData().getPackageX();
                    //微信返回的支付交易会话ID
                    String partnerid = payBean.getData().getPartnerid();
                    String prepayid = payBean.getData().getPrepayid();
                    //时间戳
                    String timestamp = payBean.getData().getTimestamp() + "";
                    //签名
                    String sign = payBean.getData().getSign();
                    //微信参数
                    final PayReq req = new PayReq();
                    req.appId = appid;
                    req.nonceStr = noncestr;
                    req.packageValue = packageX;
                    req.partnerId = partnerid;
                    req.prepayId = prepayid;
                    req.timeStamp = timestamp;
                    req.sign = sign;
                    mWxapi = WXAPIFactory.createWXAPI(HnUiUtils.getContext(), appid);
                    mWxapi.registerApp(appid);
                    //在支付之前，如果应用没有注册到微信，应该先调用IWXMsg.registerApp将应用注册到微信
                    mWxapi.sendReq(req);
                }

            }

            @Override
            public void hnErr(int errCode, String msg) {
                HnToastUtils.showToastShort(msg);
            }
        });
    }


    @OnClick({R.id.mBoxAli, R.id.mBoxWx})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.mBoxAli:
                mBoxAli.setChecked(true);
                mBoxWx.setChecked(false);
                break;
            case R.id.mBoxWx:
                mBoxAli.setChecked(false);
                mBoxWx.setChecked(true);
                break;
        }
    }

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            if (msg.what == SDK_PAY_FLAG) {
                PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                /**
                 对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                 */
                String resultMemo = payResult.getMemo();// 同步返回需要验证的信息
                String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                String resultStatus = payResult.getResultStatus();
                if (TextUtils.equals(resultStatus, "9000")) {// 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
                    requestData();
                } else if ((TextUtils.equals(resultStatus, "8000"))) {
                    HnToastUtils.showToastShort("支付结果未知!");
                } else if ((TextUtils.equals(resultStatus, "4000"))) {
                    HnToastUtils.showToastShort("订单支付失败!");
                } else if ((TextUtils.equals(resultStatus, "5000"))) {
                    HnToastUtils.showToastShort("重复请求!");
                } else if ((TextUtils.equals(resultStatus, "6001"))) {
                    HnToastUtils.showToastShort("支付已取消!");
                } else if ((TextUtils.equals(resultStatus, "6002"))) {
                    HnToastUtils.showToastShort("网络异常!");
                } else {
                    HnToastUtils.showToastShort("未知错误!");
                }
            }
        }
    };
}
