package com.hotniao.live.activity;

import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hn.library.base.BaseActivity;
import com.hn.library.base.BaseRequestStateListener;
import com.hn.library.global.HnUrl;
import com.hn.library.http.BaseResponseModel;
import com.hn.library.http.HnHttpUtils;
import com.hn.library.http.HnResponseHandler;
import com.hn.library.manager.HnAppManager;
import com.hn.library.utils.HnLogUtils;
import com.hn.library.utils.HnToastUtils;
import com.hn.library.utils.HnUtils;
import com.hn.library.view.FrescoImageView;
import com.hotniao.live.R;
import com.hotniao.live.biz.live.anchorAuth.HnAnchorAuthenticationBiz;
import com.hotniao.live.model.AuthDetailsModel;
import com.hotniao.live.utils.HnUiUtils;
import com.hotniao.live.widget.HnButtonTextWatcher;
import com.live.shoplib.ui.dialog.HelpAuthDialog;
import com.loopj.android.http.RequestParams;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Copyright (C) 2017,深圳市红鸟网络科技股份有限公司 All rights reserved.
 * 项目名称：红鸟电商
 * 类描述：实名认证界面(用户)
 * 创建人：mj
 * 创建时间：2017/3/6 16:16
 * 修改人：
 * 修改时间：
 * 修改备注：
 * Version:  1.0.0
 */
public class HnAuthenticationActivity extends BaseActivity implements BaseRequestStateListener {


    @BindView(R.id.apply_name_tv)
    EditText mApplyNameTv;
    @BindView(R.id.apply_idcard_tv)
    EditText mApplyIdcardTv;
    @BindView(R.id.et_phone)
    EditText mEtPhone;
    @BindView(R.id.mIvId1)
    FrescoImageView mIvId1;
    @BindView(R.id.mIvId2)
    FrescoImageView mIvId2;
    @BindView(R.id.mBoxSure)
    CheckBox mBoxSure;


    @BindView(R.id.tv_submit_certification)
    TextView tv_submit_certification;
    @BindView(R.id.tv_sendcode)
    TextView tv_sendcode;
    @BindView(R.id.et_phone_verify_code)//验证码
    EditText et_phone_verify_code;



    @BindView(R.id.realname_auth_layout)//通过
            LinearLayout realname_auth_layout;
    @BindView(R.id.realname_authing_layout)//审核中
            LinearLayout realname_authing_layout;
    @BindView(R.id.realname_auth_fail_layout)//未通过
            LinearLayout realname_auth_fail_layout;
    @BindView(R.id.realname_authing_return)//返回
            TextView realname_authing_return;
    @BindView(R.id.realname_auth_try_again)//再试一次
            TextView realname_auth_try_again;
    @BindView(R.id.phone_rl)//手机验证码
            RelativeLayout phone_rl;

    @BindView(R.id.chbox_ll)//申请即代表同意
            LinearLayout chbox_ll;
    @BindView(R.id.tv_through)//请认真核对资料，信息填写不准确，会影响审核结果~
            TextView tv_through;
    @BindView(R.id.mTvHelp)//手持身份证照
            TextView mTvHelp;




    TimeCount mTime;



    private final static String select_one = "select_one";
    private final static String select_two = "select_two";
    private EditText[] mEts;
    private HnButtonTextWatcher mWatcher;

    //是否同意开播提醒  0:不支持  1：支持
    private boolean isCheck = true;
    //身份证正面照
    private String onePath;
    //身份证反面照
    private String twoPath;
    //手持身份证照
    private String threePath;
    //开播提醒H5地址
    private String rule_url;
    //主播实名认证逻辑类，处理主播认证详情相关业务
    private HnAnchorAuthenticationBiz mHnAnchorAuthenticationBiz;
    private boolean hadAuth = false;


    @OnClick({R.id.mIvId1, R.id.mIvId2, R.id.mTvHelp,R.id.mTvSure,R.id.tv_sendcode,R.id.tv_submit_certification,R.id.realname_authing_return,R.id.realname_auth_try_again})
    public void onClick(View view) {
        if (!hadAuth) {
            HnUtils.hideSoftInputFrom(HnAuthenticationActivity.this, mApplyIdcardTv, mEtPhone, mApplyIdcardTv);
            switch (view.getId()) {
                case R.id.mTvSure:
                    HnWebActivity.luncher(this, "点E点主播协议", HnUrl.LIVE_AGREEMENT, HnWebActivity.Comm);
                    break;
                case R.id.mTvHelp:
                    HelpAuthDialog.newInstance(this).show();
                    break;
                case R.id.mIvId1://身份证正面
                    mHnAnchorAuthenticationBiz.uploadPicFile(select_one);
                    break;
                case R.id.mIvId2://身份证反面
                    mHnAnchorAuthenticationBiz.uploadPicFile(select_two);
                    break;
                case R.id.tv_sendcode://发送验证码

                    if (TextUtils.isEmpty(mEtPhone.getText().toString().trim()) || !mEtPhone.getText().toString().trim().matches("^1[3-8]\\d{9}$")) {
                        HnToastUtils.showToastShort(R.string.log_input_phone);
                        return;
                    }else {
                        sendSMS(mEtPhone.getText().toString().trim());
                    }

                    break;
                case R.id.tv_submit_certification://提交实名信息
                    if (!mBoxSure.isChecked()) {
                        HnToastUtils.showToastShort("请先阅读并同意协议");
                        return;
                    }
                    //姓名
                    String userName = mApplyNameTv.getText().toString().trim();
                    //手机号
                    String phone = mEtPhone.getText().toString().trim();
                    //身份证
                    String userIdCard = mApplyIdcardTv.getText().toString().trim();
                    mHnAnchorAuthenticationBiz.requestToCommitAnchorApply(userName, phone, userIdCard, onePath, twoPath, threePath, isCheck,et_phone_verify_code.getText().toString());
                    break;
                case R.id.realname_authing_return://返回
                    finish();
                    break;
                case R.id.realname_auth_try_again://再试一次
                    realname_auth_layout.setVisibility(View.VISIBLE);
                    realname_authing_layout.setVisibility(View.GONE);
                    realname_auth_fail_layout.setVisibility(View.GONE);
                    tv_submit_certification.setVisibility(View.VISIBLE);
                    break;

            }
        }else {
            switch (view.getId()) {
                case R.id.tv_sendcode://发送验证码
                    mTime.start();
                    break;
            }
        }
    }


    @Override
    public int getContentViewId() {
        return R.layout.activity_auth_detail;
    }

    @Override
    public void onCreateNew(Bundle savedInstanceState) {
        //初始化视图
        initView();
        //设置组件监听
        setListener();
        hadAuth = getIntent().getBooleanExtra("hadAuth", false);
        if (!hadAuth) {
            getRealNameDetails();
            mSubtitle.setVisibility(View.GONE);
//            mEtPhone.setEnabled(false);
//            mApplyNameTv.setEnabled(false);
//            mApplyIdcardTv.setEnabled(false);
//            mSubtitle.setVisibility(View.GONE);
//            mBoxSure.setChecked(true);
//            mBoxSure.setEnabled(false);
        }else {
            mSubtitle.setVisibility(View.VISIBLE);
        }
        mTime = new TimeCount(60000,1000);

    }

    private void getRealNameDetails() {
        HnHttpUtils.postRequest(HnUrl.AUTH_DETAILS, null, TAG, new HnResponseHandler<AuthDetailsModel>(AuthDetailsModel.class) {
            @Override
            public void hnSuccess(String response) {
                if (isFinishing() || model.getD() == null) return;
                if(null==model.getD().getUser_realname()){
                    tv_submit_certification.setVisibility(View.VISIBLE);
                }else {
                    if(model.getD().getUser_certification_status().equals("Y")){
                        tv_submit_certification.setVisibility(View.GONE);
                        realname_auth_layout.setVisibility(View.VISIBLE);
                        realname_authing_layout.setVisibility(View.GONE);
                        realname_auth_fail_layout.setVisibility(View.GONE);
                        mApplyNameTv.setText(model.getD().getUser_realname());
                        mEtPhone.setText(model.getD().getUser_phone());
                        mApplyIdcardTv.setText(model.getD().getUser_certification_id());
                        mIvId1.setImageURI(Uri.parse(HnUrl.setImageUrl(model.getD().getUser_front_img())));
                        mIvId2.setImageURI(Uri.parse(HnUrl.setImageUrl(model.getD().getUser_back_img())));
                        phone_rl.setVisibility(View.GONE);
                        chbox_ll.setVisibility(View.GONE);
                        tv_through.setVisibility(View.GONE);
                        mTvHelp.setCompoundDrawables(null,null,null,null);

                    }else if(model.getD().getUser_certification_status().equals("N")){
                        realname_auth_layout.setVisibility(View.GONE);
                        realname_authing_layout.setVisibility(View.GONE);
                        realname_auth_fail_layout.setVisibility(View.VISIBLE);
                    }else if(model.getD().getUser_certification_status().equals("C")){
                        realname_auth_layout.setVisibility(View.GONE);
                        realname_authing_layout.setVisibility(View.VISIBLE);
                        realname_auth_fail_layout.setVisibility(View.GONE);


                    }
                }
            }

            @Override
            public void hnErr(int errCode, String msg) {
                HnToastUtils.showToastShort(msg);
            }
        });
    }

    /**
     * 初始化视图
     */
    private void initView() {
        setShowBack(true);
        setTitle(R.string.real_name_title);
        mHnAnchorAuthenticationBiz = new HnAnchorAuthenticationBiz(this);
        mHnAnchorAuthenticationBiz.setLoginListener(this);
    }

    /**
     * 设置组件监听
     */
    private void setListener() {
        mEts = new EditText[]{mApplyNameTv, mApplyIdcardTv, mEtPhone};
        mWatcher = new HnButtonTextWatcher(mSubtitle, mEts);
        mApplyNameTv.addTextChangedListener(mWatcher);
        mApplyIdcardTv.addTextChangedListener(mWatcher);
        mEtPhone.addTextChangedListener(mWatcher);
        mSubtitle.setText("提交");
        mSubtitle.setVisibility(View.VISIBLE);
        mSubtitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mBoxSure.isChecked()) {
                    HnToastUtils.showToastShort("请先阅读并同意协议");
                    return;
                }
                //姓名
                String userName = mApplyNameTv.getText().toString().trim();
                //手机号
                String phone = mEtPhone.getText().toString().trim();
                //身份证
                String userIdCard = mApplyIdcardTv.getText().toString().trim();
                mHnAnchorAuthenticationBiz.requestToCommitAnchorApply(userName, phone, userIdCard, onePath, twoPath, threePath, isCheck,et_phone_verify_code.getText().toString());
            }
        });
    }

    @Override
    public void getInitData() {


    }

    /**
     * 请求中
     */
    @Override
    public void requesting() {
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
        if ("Commit_Anchor_Apply".equals(type)) {//主播申请
//            openActivity(HnAuthStateActivity.class);
//            HnAppManager.getInstance().finishActivity(HnBeforeLiveSettingActivity.class);
//            finish();
            realname_authing_layout.setVisibility(View.VISIBLE);
            realname_auth_layout.setVisibility(View.GONE);
            tv_submit_certification.setVisibility(View.GONE);
        } else if ("upload_pic_file".equals(type)) {//上传身份证照
            HnToastUtils.showToastShort(HnUiUtils.getString(R.string.upload_succeed));
            String key = response;
            String select = (String) obj;
            HnLogUtils.i(TAG, "key：" + key);
            if (!TextUtils.isEmpty(key)) {
                updateUI(select, key);
            }
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
        if ("Commit_Anchor_Apply".equals(type)) {//主播申请
            HnToastUtils.showToastShort(msg);
        } else if ("upload_pic_file".equals(type)) {//上传身份证照
            HnToastUtils.showToastShort(HnUiUtils.getString(R.string.upload_fail));
        } else if ("get_qiniu_token".equals(type)) {//获取七牛token
            HnToastUtils.showToastShort(msg);
        }
    }


    /**
     * 成功上传图片后，更新界面
     *
     * @param select 位置标识 标识添加的第几张  身份证正面照/身份证正面照/手持身份证正面照
     * @param key    图片地址 用于显示和上传给自己的服务器
     */
    private void updateUI(String select, String key) {
        switch (select) {
            case "select_one"://身份证正面照
                if (!TextUtils.isEmpty(key)) {
                    mIvId1.setImageURI(Uri.parse(key));
                    onePath = key;
                }
                break;
            case "select_two"://身份证反面照
                if (!TextUtils.isEmpty(key)) {
                    mIvId2.setImageURI(Uri.parse(key));
                    twoPath = key;
                }
                break;
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mTime.cancel();
    }

    /**
     * Title:
     * Description: 倒计时
     */
    class TimeCount extends CountDownTimer {
        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long l) {
            tv_sendcode.setClickable(false);
//            tv_sendcode.setBackgroundColor(ContextCompat.getColor(HnAuthenticationActivity.this,R.color.color_666666));
            tv_sendcode.setText(l/1000+"s");
        }

        @Override
        public void onFinish() {
            tv_sendcode.setClickable(true);
//            tv_sendcode.setBackgroundColor(ContextCompat.getColor(HnAuthenticationActivity.this,R.color.color_666666));
            tv_sendcode.setText("重新获取验证码");
        }
    }


    /**
     * @param phone//手机号
     */
    private void sendSMS(String phone) {

        RequestParams mParam = new RequestParams();
        mParam.put("phone", phone);//用户名

        HnLogUtils.e(mParam.toString());
        HnHttpUtils.postRequest(HnUrl.VERIFY_CODE_REGISTER, mParam, "VERIFY_CODE_REGISTER", new HnResponseHandler<BaseResponseModel>(this, BaseResponseModel.class) {
            @Override
            public void hnSuccess(String response) {
                if (isFinishing()) return;
                mTime.start();
                HnToastUtils.showToastShort("发送短信成功，请注意接收~");
            }

            @Override
            public void hnErr(int errCode, String msg) {
                HnToastUtils.showToastShort(msg);
            }
        });
    }


}
