package com.live.shoplib.ui;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.hn.library.base.BaseActivity;
import com.hn.library.manager.HnAppManager;
import com.hn.library.utils.HnPrefUtils;
import com.hn.library.utils.HnToastUtils;
import com.live.shoplib.R;
import com.live.shoplib.ShopActFacade;
import com.live.shoplib.bean.PayFinishEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

/**
 * 支付结果
 * 作者：Mr.Xu
 * 时间：2017/12/28
 */
@Route(path = "/shoplib/PayResponseAct")
public class PayResponseAct extends BaseActivity {

    private ImageView mIvState;
    private TextView mTvState;
    private LinearLayout mLLFail;
    private TextView mTvOrder;
    private TextView mTvAgain;

    private String order_id = "";
    private String group_order_id = "";


    @Override
    public int getContentViewId() {
        return R.layout.act_pay_response;
    }

    @Override
    public void onCreateNew(Bundle savedInstanceState) {
        setTitle("付款结果");
        setShowBack(true);
        EventBus.getDefault().register(this);
        mIvState = (ImageView) findViewById(R.id.mIvState);
        mTvState = (TextView) findViewById(R.id.mTvState);
        mLLFail = (LinearLayout) findViewById(R.id.mLLFail);
        mTvOrder = (TextView) findViewById(R.id.mTvOrder);
        mTvAgain = (TextView) findViewById(R.id.mTvAgain);

    }

    @Override
    public void getInitData() {

        final boolean state = getIntent().getBooleanExtra("state", false);
        order_id = getIntent().getStringExtra("order_id");
        group_order_id = HnPrefUtils.getString(order_id, "");

        if (TextUtils.isEmpty(order_id)) {
            HnToastUtils.showToastShort("暂无订单");
            finish();
            return;
        }

        mIvState.setImageResource(state ? R.drawable.pay_of_suc : R.drawable.pay_of_fail);
        mTvState.setText(state ? "支付成功" : "付款失败");

        if (state && !TextUtils.isEmpty(group_order_id)) {
            mTvAgain.setText("邀请好友参团");
        } else {
            mTvAgain.setText(state ? "返回" : "重新付款");
        }
        mLLFail.setVisibility(state ? View.GONE : View.VISIBLE);

        mTvOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                ShopActFacade.openOrderDetails(order_id,false);
                ShopActFacade.openShopOrder(false,0);
                finish();
            }
        });

        mTvAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (state) {
                    HnAppManager.getInstance().finishActivity(ShopOrderAct.class);
                    if (!TextUtils.isEmpty(group_order_id)) {
                        ShopActFacade.openShareGroupDetails(order_id, group_order_id);
                        finish();
                        return;
                    }
                    finish();
                } else {
                    ShopActFacade.openPayDetails(order_id, getIntent().getStringExtra("money"));
                }
            }
        });

    }


    @Subscribe
    public void onFinishEvent(PayFinishEvent event){
        finish();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
