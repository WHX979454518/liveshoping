package com.hotniao.live.activity.bindPhone;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.hn.library.base.BaseActivity;
import com.hn.library.base.BaseRequestStateListener;
import com.hn.library.http.BaseResponseModel;
import com.hn.library.http.HnHttpUtils;
import com.hn.library.http.HnResponseHandler;
import com.hn.library.manager.HnAppManager;
import com.hn.library.utils.HnLogUtils;
import com.hn.library.utils.HnToastUtils;
import com.hn.library.view.CommDialog;
import com.hn.library.view.HnEditText;
import com.hn.library.view.HnSendVerifyCodeButton;
import com.hotniao.live.R;
import com.hotniao.live.biz.user.userinfo.HnPhoneAndPwdBiz;
import com.hn.library.global.HnUrl;
import com.hotniao.live.utils.HnUiUtils;
import com.hotniao.live.widget.HnButtonTextWatcher;
import com.loopj.android.http.RequestParams;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Copyright (C) 2016,深圳市红鸟网络科技股份有限公司 All rights reserved.
 * 类描述：更换绑定手机号
 * 创建人：刘勇
 * 创建时间：2017/2/22 16:22
 * 修改人：刘勇
 * 修改时间：2017/2/22 16:34
 * 修改备注：
 * Version:  1.0.0
 */

public class HnChangeBindPhoneActivity extends BaseActivity implements BaseRequestStateListener {


    @BindView(R.id.mTvOldPhone)
    TextView mTvOldPhone;
    @BindView(R.id.mEtPhone)
    HnEditText mEtPhone;
    @BindView(R.id.mBtnSendCode)
    HnSendVerifyCodeButton mBtnSendCode;
    @BindView(R.id.mEtCode)
    HnEditText mEtCode;
    @BindView(R.id.mTvBind)
    TextView mTvBindView;

    private EditText[] mEts;
    private HnButtonTextWatcher mWatcher;

    //业务逻辑类，用于获取验证码，绑定新手机
    private HnPhoneAndPwdBiz mHnPhoneAndPwdBiz;

    public static void luncher(Activity activity, String code, String phone) {
        activity.startActivity(new Intent(activity, HnChangeBindPhoneActivity.class).putExtra("code", code).putExtra("phone", phone));
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_change_bind_phone;
    }

    @Override
    public void onCreateNew(Bundle savedInstanceState) {
        setShowBack(true);
        setTitle(R.string.bind_phone);

        mHnPhoneAndPwdBiz=new HnPhoneAndPwdBiz(this);
        mHnPhoneAndPwdBiz.setBaseRequestStateListener(this);

        mTvOldPhone.setText(getIntent().getStringExtra("phone"));
        //设置监听
        setListener();
    }

    @Override
    public void getInitData() {

    }
    /**
     * 对控件设置监听  当用户输入数据时，才可提交
     */
    private void setListener() {
        mEts = new EditText[]{mEtPhone, mEtCode};
        mWatcher = new HnButtonTextWatcher(mTvBindView, mEts);
        mEtPhone.addTextChangedListener(mWatcher);
        mEtCode.addTextChangedListener(mWatcher);
    }



    @OnClick({R.id.mBtnSendCode, R.id.mTvBind})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.mBtnSendCode:
                if (TextUtils.isEmpty(mEtPhone.getText().toString().trim()) || !mEtPhone.getText().toString().trim().matches("^1[3-8]\\d{9}$")) {
                    HnToastUtils.showToastShort(R.string.log_input_phone);
                    return;
                }
                if (mBtnSendCode.getIsStart()) return;
                sendSMS(mEtPhone.getText().toString().trim());

                break;
            case R.id.mTvBind:
                mHnPhoneAndPwdBiz.requestToChangeBindPhone(mEtPhone.getText().toString(),mEtCode.getText().toString(),getIntent().getStringExtra("code"));
                break;
        }
    }

    /**
     * @param phone//手机号
     */
    private void sendSMS(String phone) {

        RequestParams mParam = new RequestParams();
        mParam.put("phone", phone);//用户名

        HnLogUtils.e(mParam.toString());
        HnHttpUtils.postRequest(HnUrl.VERIFY_CODE_BINDPHONE, mParam, "VERIFY_CODE_BindViewPHONE", new HnResponseHandler<BaseResponseModel>(this, BaseResponseModel.class) {
            @Override
            public void hnSuccess(String response) {
                if (isFinishing()) return;
                mBtnSendCode.startCountDownTimer();
                HnToastUtils.showToastShort("发送短信成功，请注意接收~");
            }

            @Override
            public void hnErr(int errCode, String msg) {
                HnToastUtils.showToastShort(msg);
            }
        });
    }

    @Override
    public void requesting() {
        showDoing(HnUiUtils.getString(R.string.binding),null);
    }

    @Override
    public void requestSuccess(String type, String response, Object obj) {
        if("BindView_change_phone".equals(type)){
            CommDialog.newInstance(this).setClickListen(new CommDialog.OneSelDialog() {
                @Override
                public void sureClick() {
                    HnAppManager.getInstance().finishActivity(HnVerifyPhoneActivity.class);
                    HnAppManager.getInstance().finishActivity(HnHaveBindPhoneActivity.class);
                    HnAppManager.getInstance().finishActivity();
                }
            }).setTitle("绑定成功").setContent("恭喜你，绑定成功").setCanceledOnOutside(false).setRightText(HnUiUtils.getString(R.string.i_know)).show();


        }
    }

    @Override
    public void requestFail(String type, int code, String msg) {
        if("BindView_change_phone".equals(type)){//绑定型手机
            HnToastUtils.showToastShort(msg);

        }
    }
}
