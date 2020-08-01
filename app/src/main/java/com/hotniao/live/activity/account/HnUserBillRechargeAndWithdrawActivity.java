package com.hotniao.live.activity.account;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.bigkoo.pickerview.TimePickerView;
import com.bigkoo.pickerview.listener.OnDismissListener;
import com.hn.library.base.BaseActivity;
import com.hn.library.global.HnConstants;
import com.hn.library.utils.HnDateUtils;
import com.hn.library.utils.HnUtils;
import com.hotniao.live.R;
import com.hotniao.live.fragment.billRecord.HnBillRechargeFragment;
import com.hotniao.live.fragment.billRecord.HnBillWithDrawFragment;
import com.hotniao.live.model.WithdrawEvent;

import org.greenrobot.eventbus.EventBus;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Copyright (C) 2016,深圳市红鸟网络科技股份有限公司 All rights reserved.
 * 类描述：充值提现记录
 * 创建人：刘勇
 * 创建时间：2017/2/22 16:22
 * 修改人：刘勇
 * 修改时间：2017/2/22 16:34
 * 修改备注：
 * Version:  1.0.0
 */

public class HnUserBillRechargeAndWithdrawActivity extends BaseActivity {
    public static final int RECHARGE = 1;//充值
    public static final int WITHDRAW = 2;//提现
    private Drawable rightDrawable, leftDrawable;

    public static void luncher(Activity activity, int type) {
        activity.startActivity(new Intent(activity, HnUserBillRechargeAndWithdrawActivity.class).putExtra("type", type));
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_message;
    }

    @Override
    public void onCreateNew(Bundle savedInstanceState) {
        setShowBack(true);
        mSubtitle.setVisibility(View.VISIBLE);
        mSubtitle.setText("全部");

        rightDrawable = getResources().getDrawable(R.drawable.shop_qualification_lower);
        rightDrawable.setBounds(0, 0, rightDrawable.getMinimumWidth(), rightDrawable.getMinimumHeight());

        leftDrawable = getResources().getDrawable(R.drawable.shop_qualification_upper);
        leftDrawable.setBounds(0, 0, rightDrawable.getMinimumWidth(), rightDrawable.getMinimumHeight());
        mSubtitle.setCompoundDrawables(null, null, rightDrawable, null);
        mSubtitle.setTextColor(getResources().getColor(R.color.comm_text_color_black_s));
        mSubtitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mSubtitle.setCompoundDrawables(null, null, rightDrawable, null);
                Calendar startDate = Calendar.getInstance();
                long curt = System.currentTimeMillis();
                String curDate = HnUtils.getDateToString_1(curt);
                String replace = curDate.replace("-", "");
                String year = replace.substring(0, 4);
                String month = replace.substring(4, 6);
                startDate.set(Integer.parseInt(year) - 10, Integer.parseInt(month) - 1, 01);
                Calendar endDate = Calendar.getInstance();
                endDate.set(Integer.parseInt(year), Integer.parseInt(month) - 1, 30);
                TimePickerView view1 = new TimePickerView.Builder(HnUserBillRechargeAndWithdrawActivity.this, new TimePickerView.OnTimeSelectListener() {
                    @Override
                    public void onTimeSelect(Date date, View v) {
                        mSubtitle.setCompoundDrawables(null, null, leftDrawable, null);
                        EventBus.getDefault().post(new WithdrawEvent(new SimpleDateFormat("yyyyMM").format(date)));
                        mSubtitle.setText(new SimpleDateFormat("yyyy-MM").format(date));
                    }

                    @Override
                    public void onAllSelect(View v) {
                        mSubtitle.setCompoundDrawables(null, null, leftDrawable, null);
                        EventBus.getDefault().post(new WithdrawEvent("all"));
                        mSubtitle.setText("全部");
                    }
                })
                        .setType(new boolean[]{true, true, false, false, false, false})
                        .setLabel("", "", "", "", "", "")
                        .isCenterLabel(false)
                        .setContentSize(21)
                        .setRangDate(startDate, endDate)
                        .setDate(Calendar.getInstance())
                        .setDecorView(null)
                        .build();
                view1.show();
                view1.setOnDismissListener(new OnDismissListener() {
                    @Override
                    public void onDismiss(Object o) {
                        mSubtitle.setCompoundDrawables(null, null, leftDrawable, null);
                    }
                });
            }
        });
        if (1 == getIntent().getIntExtra("type", 1)) {
            setTitle(R.string.recharge_record);
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.mFrame, HnBillRechargeFragment.newInstance())
                    .commitAllowingStateLoss();
        } else {
            setTitle(R.string.withdraw_record);
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.mFrame, HnBillWithDrawFragment.newInstance())
                    .commitAllowingStateLoss();
        }

    }

    @Override
    public void getInitData() {

    }
}
