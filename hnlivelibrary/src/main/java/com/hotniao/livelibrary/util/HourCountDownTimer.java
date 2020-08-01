package com.hotniao.livelibrary.util;

import android.os.CountDownTimer;
import android.widget.TextView;

import com.hn.library.utils.HnDateUtils;
import com.hn.library.utils.HnLogUtils;

/**
 * Copyright (C) 2016,深圳市红鸟网络科技股份有限公司 All rights reserved.
 * 类描述：倒计时类
 * 创建人：刘勇
 * 创建时间：2017/2/22 16:22
 * 修改人：刘勇
 * 修改时间：2017/2/22 16:34
 * 修改备注：
 * Version:  1.0.0
 */

public class HourCountDownTimer extends CountDownTimer {
    private TextView mTvTime;


    /**
     *
     * @param millisInFuture   剩余时间  毫秒
     * @param countDownInterval 间隔时间  毫秒
     * @param mTvTime   控件
     */
    public HourCountDownTimer(long millisInFuture, long countDownInterval, TextView mTvTime) {
        super(millisInFuture, countDownInterval);
        this.mTvTime = mTvTime;
    }

    @Override
    public void onFinish() {
        mTvTime.setText("done");
    }

    @Override
    public void onTick(long millisUntilFinished) {
        String day = HnDateUtils.getHourString(millisUntilFinished / 1000);
        mTvTime.setText(day);
    }

}
