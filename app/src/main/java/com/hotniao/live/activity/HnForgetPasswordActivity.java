package com.hotniao.live.activity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.hn.library.base.BaseActivity;
import com.hn.library.base.BaseRequestStateListener;
import com.hn.library.utils.HnToastUtils;
import com.hn.library.utils.HnUtils;
import com.hn.library.view.HnEditText;
import com.hotniao.live.R;
import com.hotniao.live.biz.forgetPwd.HnForgetPwdBiz;
import com.hn.library.global.HnConstants;
import com.hotniao.live.utils.HnUiUtils;
import com.hotniao.live.utils.HnUserUtil;
import com.hotniao.live.widget.HnButtonTextWatcher;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Copyright (C) 2017,深圳市红鸟网络科技股份有限公司 All rights reserved.
 * 项目名称：红鸟电商
 * 类描述： 忘记密码
 * 创建人：阳石柏
 * 创建时间：2017/3/6 16:16
 * 修改人：阳石柏
 * 修改时间：2017/3/6 16:16
 * 修改备注：
 * Version:  1.0.0
 */
public class HnForgetPasswordActivity extends BaseActivity implements BaseRequestStateListener {


    @BindView(R.id.for_phone_et)
    HnEditText mForPhoneEt;
    @BindView(R.id.for_code_btn)
    TextView mForCodeBtn;
    @BindView(R.id.for_code_et)
    HnEditText mForCodeEt;
    @BindView(R.id.for_password_et)
    HnEditText mForPasswordEt;
    @BindView(R.id.iv_check)
    ImageView mIvCheck;
    @BindView(R.id.for_longin_btn)
    TextView mForLonginBtn;
    private boolean isVisiable = true;
    private EditText[] mEts;
    private HnButtonTextWatcher mWatcher;
    //忘记密码业务逻辑类，用户处理注册相关业务
    private HnForgetPwdBiz mHnForgetPwdBiz;
    private String phomeStr;


    @Override
    public int getContentViewId() {
        return R.layout.activity_forget_password;
    }

    @Override
    public void onCreateNew(Bundle savedInstanceState) {
        //初始化数据
        initData();
        //设置监听
        setListener();

    }


    /**
     * 初始化数据
     */
    private void initData() {
        //标题栏
        setShowBack(true);
        setTitle(HnUiUtils.getString(R.string.fgtpwd_title));
        String phone = getIntent().getStringExtra("phone");
        if (!TextUtils.isEmpty(phone) && mForPhoneEt != null) {
            mForPhoneEt.setText(phone);
            mForPhoneEt.setSelection(phone.length());
        }
        //初始化忘记密码业务逻辑类
        mHnForgetPwdBiz = new HnForgetPwdBiz(this);
        mHnForgetPwdBiz.setRegisterListener(this);
    }

    /**
     * 对控件设置监听  当用户输入数据时，才可提交
     */
    private void setListener() {
        mEts = new EditText[]{mForPhoneEt, mForCodeEt, mForPasswordEt};
        mWatcher = new HnButtonTextWatcher(mForLonginBtn, mEts);
        mForPhoneEt.addTextChangedListener(mWatcher);
        mForCodeEt.addTextChangedListener(mWatcher);
        mForPasswordEt.addTextChangedListener(mWatcher);
    }

    @Override
    public void getInitData() {

    }

    @OnClick({R.id.for_code_btn, R.id.for_longin_btn, R.id.iv_check})
    public void onClick(View view) {
        HnUtils.hideSoftInputFrom(mForPhoneEt, HnForgetPasswordActivity.this);
        HnUtils.hideSoftInputFrom(mForCodeEt, HnForgetPasswordActivity.this);
        HnUtils.hideSoftInputFrom(mForPasswordEt, HnForgetPasswordActivity.this);
        switch (view.getId()) {
            case R.id.iv_check:
                HnUserUtil.switchPwdisVis(mForPasswordEt, mIvCheck, isVisiable);
                isVisiable = !isVisiable;
                break;
            case R.id.for_code_btn://发送验证码
                phomeStr = mForPhoneEt.getText().toString().trim();
                mHnForgetPwdBiz.requestToSendSms(phomeStr);
                break;
            case R.id.for_longin_btn://找回密码
                //手机号码
                phomeStr = mForPhoneEt.getText().toString().trim();
                //验证码
                String regCodeStr = mForCodeEt.getText().toString().trim();
                //密码
                String regPasswordStr = mForPasswordEt.getText().toString().trim();
                mHnForgetPwdBiz.requestToForgetPwd(phomeStr, regCodeStr, regPasswordStr);
                break;
        }
    }


    /**
     * 请求中
     */
    @Override
    public void requesting() {
        mForLonginBtn.setEnabled(false);
        showDoing(getResources().getString(R.string.loading), null);
    }

    /**
     * 请求成功
     *
     * @param type
     * @param response
     * @param obj
     */
    @Override
    public void requestSuccess(String type, String response, Object obj) {
        done();
        mForLonginBtn.setEnabled(true);
        if ("forget_pwd_sms".equals(type)) {//成功获取验证码
            HnToastUtils.showToastShort(HnUiUtils.getString(R.string.send_mess_suc));
            TimeCount timeCount = new TimeCount(HnConstants.Time.COUNTDOWN, 1000);
            timeCount.start();
        } else {//忘记密码回调
            HnToastUtils.showToastShort(HnUiUtils.getString(R.string.fgtpwd_find_pwd_succeed));
            finish();
//            HnAppManager.getInstance().finishActivity(HnRegisterActivity.class);
        }
    }

    /**
     * 请求失败
     *
     * @param type
     * @param code
     * @param msg
     */
    @Override
    public void requestFail(String type, int code, String msg) {
        done();
        HnToastUtils.showToastShort(msg);
        if (!"forget_pwd_sms".equals(type)) {
            mForLonginBtn.setEnabled(true);
        }
    }

    /**
     * 倒计时
     */
    class TimeCount extends CountDownTimer {
        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {//计时过程
            try {
                mForCodeBtn.setText("    "+ millisUntilFinished / 1000 + "s    ");
                mForCodeBtn.setEnabled(false);
                mForCodeBtn.setBackgroundResource(R.drawable.verify_shape);
                mForCodeBtn.setTextColor(getResources().getColor(R.color.comm_text_color_black_s));
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onFinish() {//计时完毕
            try {
                mForCodeBtn.setEnabled(true);
                mForCodeBtn.setText(HnUiUtils.getString(R.string.rgst_get_captcha));
                mForCodeBtn.setBackgroundResource(R.drawable.verify_shape_main);
                mForCodeBtn.setTextColor(getResources().getColor(R.color.main_dark_color));
            } catch (NullPointerException e) {
                e.printStackTrace();
            }

        }
    }


}
