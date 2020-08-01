package com.hotniao.live.activity;


import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.hn.library.base.BaseActivity;
import com.hn.library.http.HnHttpUtils;
import com.hn.library.http.HnResponseHandler;
import com.hn.library.manager.HnAppManager;
import com.hn.library.utils.HnToastUtils;
import com.hn.library.utils.HnUiUtils;
import com.hn.library.utils.HnUtils;
import com.hotniao.live.HnApplication;
import com.hotniao.live.R;
import com.hn.library.global.HnUrl;
import com.hotniao.live.model.HnStopLiveModel;
import com.hotniao.livelibrary.util.HnLiveDateUtils;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Copyright (C) 2017,深圳市红鸟网络科技股份有限公司 All rights reserved.
 * 项目名称：红鸟电商
 * 类描述：主播端结束直播
 * 创建人：mj
 * 创建时间：2017/3/6 16:16
 * 修改人：mj
 * 修改时间：2017/3/6 16:16
 * 修改备注：
 * Version:  1.0.0
 */
@Route(path = "/app/HnAnchorStopLiveActivity")
public class HnAnchorStopLiveActivity extends BaseActivity {


    @BindView(R.id.iv_close)
    ImageView ivClose;
    @BindView(R.id.tv_live_time)
    TextView tvLiveTime;
    @BindView(R.id.tv_people_number)
    TextView tvPeopleNumber;
    @BindView(R.id.tv_zan)
    TextView tvZan;
    @BindView(R.id.tv_u_piao)
    TextView tvUPiao;
    @BindView(R.id.mTvFocus)
    TextView mTvFocus;
    @BindView(R.id.mTvCoin)
    TextView mTvCoin;
    @BindView(R.id.mTvShow)
    TextView mTvShow;
    @BindView(R.id.mLlAddOrder)
    LinearLayout mLlAddOrder;


    @OnClick(R.id.mTvGOHome)
    public void onClick() {
        HnAppManager.getInstance().finishActivity();
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_stop_live;
    }

    @Override
    public void onCreateNew(Bundle savedInstanceState) {
        setShowTitleBar(false);
        mTvCoin.setText(String.format(HnUiUtils.getString(R.string.get_virtual_coin), HnApplication.getmConfig().getDot()));
    }

    @Override
    public void getInitData() {
        requestToExitAnchotLive();
    }


    /**
     * 网络请求：主播退出直播间，结束直播
     */
    public void requestToExitAnchotLive() {
        HnHttpUtils.getRequest(HnUrl.Stop_Live, null, TAG, new HnResponseHandler<HnStopLiveModel>(this, HnStopLiveModel.class) {
            @Override
            public void hnSuccess(String response) {
                if (model.getC() == 0) {
                    if (model.getD() != null && model.getD() != null) {
                        updateUI(model.getD());
                    }
                } else {
                    HnToastUtils.showToastShort(model.getM());
                }
            }

            @Override
            public void hnErr(int errCode, String msg) {
                HnToastUtils.showToastShort(msg);
            }
        });
    }


    /**
     * 更新界面数据
     */
    private void updateUI(HnStopLiveModel.DBean data) {
        if (isFinishing()) return;
        if ("Y".equals(data.getIs_create_record())) mTvShow.setVisibility(View.GONE);
        else mTvShow.setVisibility(View.VISIBLE);
        //直播时长
        String live_time = data.getLive_time();
        if (!TextUtils.isEmpty(live_time)) {
            String time = HnLiveDateUtils.getLiveTime(Long.valueOf(live_time));
            tvLiveTime.setText(time);
        }
        //直播人数
        String live_people_num = data.getAnchor_live_onlines();
        tvPeopleNumber.setText(live_people_num + "");


        //优票
        String dot = data.getUser_dot();
        tvUPiao.setText(HnUtils.setTwoPoints(dot));

        mTvFocus.setText(data.getAnchor_live_follow_total());


        if (data.getStore() == null || TextUtils.isEmpty(data.getStore().getLive_goods_sales())) {
            mLlAddOrder.setVisibility(View.GONE);
        } else {
            mLlAddOrder.setVisibility(View.VISIBLE);
            //收到订单
            tvZan.setText(data.getStore().getLive_goods_sales());

        }

    }


}
