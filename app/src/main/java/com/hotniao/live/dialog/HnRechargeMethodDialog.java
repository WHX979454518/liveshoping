package com.hotniao.live.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RadioGroup;

import androidx.fragment.app.DialogFragment;

import com.hn.library.base.BaseActivity;
import com.hn.library.base.BaseRequestStateListener;
import com.hn.library.utils.HnToastUtils;
import com.hotniao.live.R;
import com.hotniao.live.biz.user.account.HnMyAccountBiz;

/**
 * Copyright (C) 2017,深圳市红鸟网络科技股份有限公司 All rights reserved.
 * 类描述：付款方式
 * 创建人：刘勇
 * 创建时间：2017/9/5 18:23
 * 修改人：刘勇
 * 修改时间：2017/9/5 18:23
 * 修改备注：
 * Version:  1.0.0
 */
public class HnRechargeMethodDialog extends DialogFragment implements View.OnClickListener, BaseRequestStateListener {

    private Activity mActivity;
    //我的账户业务逻辑类，我的账户相关业务
    private HnMyAccountBiz mHnMyAccountBiz;
    private static HnRechargeMethodDialog dialog;
    private String id;

    public static HnRechargeMethodDialog getInstance(String id) {
        Bundle bundle = new Bundle();
        bundle.putString("id", id);
        dialog = new HnRechargeMethodDialog();
        dialog.setArguments(bundle);
        return dialog;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = getActivity();
        Bundle bundle = getArguments();
        if (bundle != null) {
            id = bundle.getString("id");
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        mHnMyAccountBiz = new HnMyAccountBiz((BaseActivity) mActivity);
        mHnMyAccountBiz.setBaseRequestStateListener(this);
        View view = View.inflate(mActivity, R.layout.dialog_recharge_method, null);
        Dialog dialog = new Dialog(mActivity, R.style.ProgressDialog);
        dialog.setContentView(view);
        dialog.setCanceledOnTouchOutside(true);
        Window alertWindow = dialog.getWindow();
        alertWindow.setGravity(Gravity.CENTER);
        //初始化组件
        initView(view);
        WindowManager.LayoutParams params = alertWindow.getAttributes();
        params.width = (int) (mActivity.getWindowManager().getDefaultDisplay().getWidth() * 0.8f);
        alertWindow.setAttributes(params);

        return dialog;
    }

    private void initView(View view) {
        view.findViewById(R.id.mIvCancle).setOnClickListener(this);
        RadioGroup mRg = (RadioGroup) view.findViewById(R.id.mRg);
        mRg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.mRbAliPay:
                        mHnMyAccountBiz.requestToTestRecharge(id);
                        break;
                    case R.id.mRbWxPay:
                        mHnMyAccountBiz.requestToTestRecharge(id);
                        break;
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.mIvCancle:
                dismiss();
                break;
        }

    }


    public HnRechargeMethodDialog setClickListen(SelDialogListener listener) {
        this.listener = listener;
        return dialog;
    }

    private SelDialogListener listener;

    public interface SelDialogListener {
        void sureClick(String id);
    }

    @Override
    public void requesting() {

    }

    @Override
    public void requestSuccess(String type, String response, Object obj) {
        if ("fourth_recharge".equals(type)) {
            if (listener != null) {
                listener.sureClick(id);
            }
            dismiss();
        }
    }

    @Override
    public void requestFail(String type, int code, String msg) {
        if ("fourth_recharge".equals(type)) {
            HnToastUtils.showToastShort(msg);
        }
    }


}
