package com.hotniao.live.widget;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;


/**
 * Copyright (C) 2017,深圳市红鸟网络科技股份有限公司 All rights reserved.
 * 项目名称：
 * 类描述：实现多个EditText内容不为空或者有CheckBox同时勾选状态下指定按钮是否可以点击功能
 * 创建人：Kevin
 * 创建时间：2017/3/29 19:18
 * 修改人：Kevin
 * 修改时间：2017/3/29 19:18
 * 修改备注：
 * Version:  1.0.0
 */
public class HnButtonTextWatcher implements TextWatcher {

    private TextView[] ets;
    private TextView     mBt;
    private CheckBox   mAgreeCb;
    private int        mTotal;

    public HnButtonTextWatcher(View view, TextView... editTexts) {
        this(view, null, editTexts);
    }

    public HnButtonTextWatcher(View view, CheckBox mCb, TextView... editTexts) {
        ets = editTexts;
        mBt = (TextView) view;
        mAgreeCb = mCb;
    }

    /**
     * 设置勾选框转态
     *
     * @param checked
     */
    public void setChecked(boolean checked) {
        if (mAgreeCb != null) {
            mAgreeCb.setChecked(checked);
        }
    }


    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {

        mTotal = 0;
        if (ets.length == 0)
            return;
        for (TextView et : ets) {
            if ((et != null) && !TextUtils.isEmpty(et.getText().toString().trim())) {
                mTotal = mTotal + 1;
            }
        }

        if ((mAgreeCb != null) && (mBt != null)) {
            mBt.setEnabled((mTotal == ets.length) && mAgreeCb.isChecked());
        } else if (mBt != null) {
            mBt.setEnabled(mTotal == ets.length);
        }

    }
}
