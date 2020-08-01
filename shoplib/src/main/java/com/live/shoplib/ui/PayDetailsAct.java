package com.live.shoplib.ui;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alipay.sdk.app.PayTask;
import com.hn.library.base.BaseActivity;
import com.hn.library.global.HnUrl;
import com.hn.library.http.BaseResponseModel;
import com.hn.library.http.HnHttpUtils;
import com.hn.library.http.HnResponseHandler;
import com.hn.library.pay.PayResult;
import com.hn.library.utils.HnPrefUtils;
import com.hn.library.utils.HnToastUtils;
import com.hn.library.utils.HnUiUtils;
import com.live.shoplib.R;
import com.live.shoplib.ShopActFacade;
import com.live.shoplib.bean.AliPayModel;
import com.live.shoplib.bean.OrderRefreshEvent;
import com.live.shoplib.bean.PayFinishEvent;
import com.live.shoplib.bean.WxPayModel;
import com.loopj.android.http.RequestParams;
import com.tencent.mm.sdk.modelpay.PayReq;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.Map;

/**
 * 作者：Mr.Xu
 * 时间：2017/12/28
 */
@Route(path = "/shoplib/PayDetailsAct")
public class PayDetailsAct extends BaseActivity {

    private static final int SDK_PAY_FLAG = 1;
    public static String money;
    private TextView mTvMoney;
    private CheckBox mBoxAli;
    private CheckBox mBoxWx;
    private CheckBox mBoxTest;
    private TextView mTvCommitPay;
    private String order_id = "";
    private Boolean isGroupBuy = false;
    private IWXAPI mWxapi;
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
                    EventBus.getDefault().post(new PayFinishEvent());
                    EventBus.getDefault().post(new OrderRefreshEvent(-1));
                    ShopActFacade.openPayResponse(order_id, true, "aliPay", getIntent().getStringExtra("money"));
                } else if ((TextUtils.equals(resultStatus, "8000"))) {
                    HnToastUtils.showToastShort("支付结果未知!");
                    ShopActFacade.openPayResponse(order_id, false, "aliPay", getIntent().getStringExtra("money"));
                    finish();
                } else if ((TextUtils.equals(resultStatus, "4000"))) {
                    HnToastUtils.showToastShort("订单支付失败!");
                    ShopActFacade.openPayResponse(order_id, false, "aliPay", getIntent().getStringExtra("money"));
                    finish();
                } else if ((TextUtils.equals(resultStatus, "5000"))) {
                    HnToastUtils.showToastShort("重复请求!");
                    ShopActFacade.openPayResponse(order_id, false, "aliPay", getIntent().getStringExtra("money"));
                    finish();
                } else if ((TextUtils.equals(resultStatus, "6001"))) {
                    HnToastUtils.showToastShort("支付已取消!");
                    ShopActFacade.openPayResponse(order_id, false, "aliPay", getIntent().getStringExtra("money"));
                    finish();
                } else if ((TextUtils.equals(resultStatus, "6002"))) {
                    HnToastUtils.showToastShort("网络异常!");
                    ShopActFacade.openPayResponse(order_id, false, "aliPay", getIntent().getStringExtra("money"));
                    finish();
                } else {
                    HnToastUtils.showToastShort("未知错误!");
                    ShopActFacade.openPayResponse(order_id, false, "aliPay", getIntent().getStringExtra("money"));
                    finish();
                }
            }
        }
    };

    @Override
    public int getContentViewId() {
        return R.layout.act_pay_details;
    }

    @Override
    public void onCreateNew(Bundle savedInstanceState) {
        setTitle("支付");
        setShowBack(true);
        EventBus.getDefault().register(this);
        mTvMoney = (TextView) findViewById(R.id.mTvMoney);
        mBoxAli = (CheckBox) findViewById(R.id.mBoxAli);
        mBoxWx = (CheckBox) findViewById(R.id.mBoxWx);
        mBoxTest = (CheckBox) findViewById(R.id.mBoxTest);
        mTvCommitPay = (TextView) findViewById(R.id.mTvCommitPay);

    }

    @Override
    public void getInitData() {
        money = getIntent().getStringExtra("money");
        mTvMoney.setText(getIntent().getStringExtra("money"));
        order_id = getIntent().getStringExtra("order_id");
        isGroupBuy = getIntent().getBooleanExtra("isGroupBuy", false);
        if (TextUtils.isEmpty(order_id)) {
            HnToastUtils.showToastShort("暂无订单信息");
            finish();
            return;
        }

        mBoxWx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBoxAli.setChecked(false);
                mBoxWx.setChecked(true);
                mBoxTest.setChecked(false);
            }
        });

        mBoxAli.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBoxAli.setChecked(true);
                mBoxWx.setChecked(false);
                mBoxTest.setChecked(false);
            }
        });
        mBoxTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBoxAli.setChecked(false);
                mBoxWx.setChecked(false);
                mBoxTest.setChecked(true);
            }
        });

        mTvCommitPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mBoxAli.isChecked()) {
                    toAliPay(order_id);
                } else if (mBoxWx.isChecked()) {
                    toWxPay(order_id);
                } else {
                    toTest(order_id);
                }
            }
        });
    }

    public void toAliPay(final String order_id) {
        RequestParams param = new RequestParams();
        param.put("mold", "0");
        param.put("order_id", order_id);
        param.put("type", "aliPay");
        HnHttpUtils.postRequest(HnUrl.TO_PAY, param, "去支付", new HnResponseHandler<AliPayModel>(AliPayModel.class) {
            @Override
            public void hnSuccess(String response) {
//
                if (isFinishing() || model.getD() == null) return;
                Runnable payRunnable = new Runnable() {
                    @Override
                    public void run() {
                        PayTask alipay = new PayTask(PayDetailsAct.this);
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

    public void toTest(final String order_id) {
        RequestParams param = new RequestParams();
        param.put("mold", "0");
        param.put("order_id", order_id);
        param.put("type", "local");
        HnHttpUtils.postRequest(HnUrl.TO_PAY, param, "去支付", new HnResponseHandler<BaseResponseModel>(BaseResponseModel.class) {
            @Override
            public void hnSuccess(String response) {
                ShopActFacade.openPayResponse(order_id, true, "test", getIntent().getStringExtra("money"));
                EventBus.getDefault().post(new PayFinishEvent());
            }

            @Override
            public void hnErr(int errCode, String msg) {
                HnToastUtils.showToastShort(msg);
            }
        });
    }

    
    
    /*#############################  支付宝  ###############################*/

    public void toWxPay(final String order_id) {
        final RequestParams param = new RequestParams();
        param.put("mold", "0");
        param.put("order_id", order_id);
        param.put("type", "wxPay");

        HnHttpUtils.postRequest(HnUrl.TO_PAY, param, "去支付", new HnResponseHandler<WxPayModel>(WxPayModel.class) {
            @Override
            public void hnSuccess(String response) {
                if (isFinishing() || model.getD() == null) return;
                HnPrefUtils.setString("order_id", order_id);
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

    @Subscribe
    public void onFinishEvent(PayFinishEvent event) {
        if (isGroupBuy){
//            order_id
        }
        finish();
    }


    private void finishJoinGroup() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
