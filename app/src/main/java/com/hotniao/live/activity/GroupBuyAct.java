package com.hotniao.live.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.fragment.app.FragmentTransaction;

import com.hn.library.base.BaseActivity;
import com.hn.library.utils.HnDateUtils;
import com.hn.library.view.ColorCountDownText;
import com.hotniao.live.fragment.SpikeOrGroupBuyItemFragment;
import com.hotniao.livelibrary.util.HourCountDownTimer;
import com.hotniao.livelibrary.util.MyCountDownTimer;
import com.live.shoplib.R;
import com.live.shoplib.bean.OrderRefreshEvent;
import com.live.shoplib.bean.PayFinishEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;


/**
 * 团购
 * 作者：Alan
 * 时间：2019/6/17
 */

public class GroupBuyAct extends BaseActivity {
    private ColorCountDownText countDownText;

    public static void open(Context context) {
        context.startActivity(new Intent(context, GroupBuyAct.class));
    }

    @Override
    public int getContentViewId() {
        return R.layout.act_group_buy;
    }

    @Override
    protected void onResume() {
        super.onResume();
        EventBus.getDefault().post(new OrderRefreshEvent(-1));
    }

    @Override
    public void onCreateNew(Bundle savedInstanceState) {
        setShowTitleBar(false);
        EventBus.getDefault().register(this);
        //todo 待确定时间戳传值
        initViews(0L);
    }

    private void initViews(Long timestamp) {
        SpikeOrGroupBuyItemFragment fragment;
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        try {
            fragment = SpikeOrGroupBuyItemFragment.newInstance(timestamp,timestamp, false);
            transaction.addToBackStack(null);
            transaction.add(R.id.group_buy_listview_content_layout, fragment);
        } catch (Exception e) {
            return;
        }
        transaction.show(fragment);
        transaction.commitAllowingStateLoss();
        countDownText = findViewById(R.id.group_buy_countdown_text);
        countDownText.setIsBlackStyle(true);
        countDownText.setCountTime(HnDateUtils.getToday24TimeStamp() - HnDateUtils.getNowTimeStamp());
        findViewById(R.id.mIvBack).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void setShowBack(boolean isShow) {
        super.setShowBack(isShow);
        mBack.setOnClickListener(isShow ? new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        } : null);
    }


    @Override
    public void getInitData() {

    }


    @Override
    public void onBackPressed() {
        finish();
    }

    @Subscribe
    public void onFinishEvent(PayFinishEvent event) {
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
