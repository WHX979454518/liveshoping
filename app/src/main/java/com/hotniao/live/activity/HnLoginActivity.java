package com.hotniao.live.activity;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.hn.library.base.BaseActivity;
import com.hn.library.base.BaseRequestStateListener;
import com.hn.library.global.NetConstant;
import com.hn.library.manager.HnAppManager;
import com.hn.library.model.HnLoginModel;
import com.hn.library.utils.HnPrefUtils;
import com.hn.library.utils.HnToastUtils;
import com.hn.library.utils.HnUtils;
import com.hn.library.view.FrescoImageView;
import com.hn.library.view.HnEditText;
import com.hotniao.live.HnApplication;
import com.hotniao.live.HnMainActivity;
import com.hotniao.live.R;
import com.hotniao.live.activity.bindPhone.HnFirstBindPhoneActivity;
import com.hotniao.live.biz.home.HnHomeCate;
import com.hotniao.live.biz.login.HnLoginBiz;
import com.hn.library.global.HnConstants;
import com.hn.library.global.HnUrl;
import com.hn.library.view.CommDialog;
import com.hotniao.live.eventbus.HnMultiEvent;
import com.hotniao.live.model.HnLoginEvent;
import com.hotniao.live.utils.HnUiUtils;
import com.hotniao.live.utils.HnUserUtil;
import com.hotniao.live.widget.HnButtonTextWatcher;
import com.imlibrary.loginAndRegister.TCLoginMgr;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * Copyright (C) 2017,深圳市红鸟网络科技股份有限公司 All rights reserved.
 * 项目名称：红鸟电商
 * 类描述：登录
 * 创建人：阳石柏
 * 创建时间：2017/3/6 16:16
 * 修改人：阳石柏
 * 修改时间：2017/3/6 16:16
 * 修改备注：
 * Version:  1.0.0
 */
@Route(path = "/main/HnLoginActivity", group = "app")
public class HnLoginActivity extends BaseActivity implements BaseRequestStateListener {

    /**
     * 接口返回登录状态失效
     */
    public static final String LOGIN_FAILURE = "loginFailure";

    @BindView(R.id.iv_icon)
    FrescoImageView mIvIcon;
    @BindView(R.id.et_phone)
    HnEditText mEtPhone;
    @BindView(R.id.et_pwd)
    HnEditText mEtPwd;
    @BindView(R.id.tv_login)
    TextView mTvLogin;
    @BindView(R.id.tv_for_pwd)
    TextView tvForPwd;
    @BindView(R.id.tv_reg_pro)
    TextView tvRegPro;
    @BindView(R.id.mIvEye)
    ImageView mIvEye;
    @BindView(R.id.mLlRoot)
    LinearLayout mLlRoot;
    @BindView(R.id.mLlContent)
    LinearLayout mLlContent;

    private Intent inte;
    private HnButtonTextWatcher mWatcher;
    private EditText[] mEts;
    private boolean isLookPwd = false;
    private ShareAction mShareAction;
    private UMShareAPI mUMShareAPI;


    /**
     * 设备id
     */
    private String mAndroidId;
    /**
     * 手机号
     */
    private String mPhoneStr;
    /**
     * 密码
     */
    private String mPasswordStr;

    private HnLoginBiz mHnLoginBiz;

    /**
     * 腾讯云登录
     */
    private TCLoginMgr mTcLoginMgr;

    /**
     * 登录失效
     *
     * @param activity
     * @param isLoginFailure
     */
    public static void luncher(Activity activity, boolean isLoginFailure) {
        activity.startActivity(new Intent(activity, HnLoginActivity.class).putExtra(LOGIN_FAILURE, isLoginFailure));
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_login;
    }


    @SuppressLint("LongLogTag")
    @Override
    public void onCreateNew(Bundle savedInstanceState) {
        mUMShareAPI = UMShareAPI.get(this);
        mShareAction = new ShareAction(this);
        Log.e("################################", "Login create");

        HnHomeCate.getCateData();
        //标题栏
        setShowBack(false);
        setShowTitleBar(false);
        setTitle(R.string.login);
        //初始化数据
        initData();
        //设置监听
        setListener();

    }

    @Override
    public void getInitData() {

    }

    @SuppressLint("LongLogTag")
    @Override
    protected void onPause() {
        super.onPause();
        Log.e("################################", "Login pause");
    }

    /**
     * 初始化数据
     */
    private void initData() {


        //设置下划线
        tvRegPro.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG); //下划线
        tvRegPro.getPaint().setAntiAlias(true);//抗锯齿
        tvRegPro.setText(Html.fromHtml(getString(R.string.log_pro)));

        EventBus.getDefault().register(this);
        mAndroidId = HnUserUtil.getUniqueid();
        //初始化登录业务逻辑类
        mHnLoginBiz = new HnLoginBiz(this);
        mHnLoginBiz.setLoginListener(this);
        //退出登录时结束掉主界面
//        HnAppManager.getInstance().finishActivity(HnMainActivity.class);
        Bundle bundle = getIntent().getExtras();

        /**
         * 退出IM
         */
        TCLoginMgr.imLogout();

        /**
         * 退出极光
         */
        HnApplication.logout();
        /**
         * 清理缓存
         */
        clearCacheData();
        if (bundle != null) {
            if (bundle.getBoolean("isMulLogin", false)) {
                mHnLoginBiz.executeExit(getIntent().getStringExtra("msg"));
                mEtPhone.setText(HnPrefUtils.getString(NetConstant.User.PHONE, ""));
//                mEtPwd.setText(HnPrefUtils.getString(NetConstant.User.PASSWORE, ""));
            }
        }
        //判断登录状态是否失效
        boolean mLoginFailure = getIntent().getBooleanExtra(LOGIN_FAILURE, false);
        if (mLoginFailure) {

            mEtPhone.setText(HnPrefUtils.getString(NetConstant.User.PHONE, ""));

            CommDialog.newInstance(this).setClickListen(new CommDialog.OneSelDialog() {
                @Override
                public void sureClick() {

                }
            }).setTitle(HnUiUtils.getString(R.string.log_out_nitify)).setCanceledOnOutside(false)
                    .setContent(getString(R.string.login_failure_again_login)).show();
        }


    }

    /**
     * 清理缓存
     */
    private void clearCacheData() {
        HnPrefUtils.setString(NetConstant.User.USER_INFO, "");
        HnPrefUtils.setString(NetConstant.User.USER_INFO, "");
        HnPrefUtils.setString(NetConstant.User.UID, "");
        HnPrefUtils.setString(NetConstant.User.STORE_STATE, "");
        HnPrefUtils.setString(NetConstant.User.TOKEN, "");
        HnPrefUtils.setString(NetConstant.User.Webscket_Url, "");
        HnPrefUtils.setString(NetConstant.User.Unread_Count, "0");
        HnPrefUtils.setBoolean(NetConstant.User.User_Forbidden, false);
    }

    /**
     * 对控件设置监听 当用户输入数据时，才可提交
     */
    private void setListener() {
        mEts = new EditText[]{mEtPhone, mEtPwd};
        mWatcher = new HnButtonTextWatcher(mTvLogin, mEts);
        mEtPhone.addTextChangedListener(mWatcher);
        mEtPwd.addTextChangedListener(mWatcher);


//        mLlRoot.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
//            @Override
//            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
//                if (oldBottom - bottom > 100) {
//                    ViewCompat.animate(mIvIcon).scaleX(0).scaleY(0).setDuration(500).start();
////                    ViewCompat.animate(mLlBottom).scaleX(0).scaleY(0).setDuration(100).start();
//                    ViewCompat.animate(mLlContent).translationY((float) (-1.4 * mIvIcon.getMeasuredHeight())).setDuration(500).start();
//                } else if (bottom - oldBottom > 100) {
//                    ViewCompat.animate(mIvIcon).scaleX(1).scaleY(1).setDuration(500).start();
////                    ViewCompat.animate(mLlBottom).scaleX(1).scaleY(1).setDuration(100).start();
//                    ViewCompat.animate(mLlContent).translationY(0).setDuration(500).start();
//                }
//            }
//        });
    }


    @OnClick({R.id.tv_login, R.id.tv_reg_pro, R.id.tv_for_pwd, R.id.mTvRegist,
            R.id.mIvEye, R.id.mIvQq, R.id.mIvSina, R.id.mIvWx})
    public void onClick(View view) {
        mPhoneStr = mEtPhone.getText().toString().trim();
        mPasswordStr = mEtPwd.getText().toString().trim();
        HnUtils.hideSoftInputFrom(mEtPhone, HnLoginActivity.this);
        HnUtils.hideSoftInputFrom(mEtPwd, HnLoginActivity.this);
        switch (view.getId()) {
            case R.id.tv_login: //手机登录
                mHnLoginBiz.executeLogin(mPhoneStr, mPasswordStr, "phone", mAndroidId);
                break;
            case R.id.tv_for_pwd:  //忘记密码
                inte = new Intent(HnLoginActivity.this, HnForgetPasswordActivity.class);
                inte.putExtra("phone", mPhoneStr);
                startActivity(inte);
                break;
            case R.id.tv_reg_pro:  //条款声明
                HnWebActivity.luncher(HnLoginActivity.this, getResources().getString(R.string.log_pro), HnUrl.Web_Url + "login", HnWebActivity.LoginAgree);
                break;
            case R.id.mTvRegist:
                inte = new Intent(HnLoginActivity.this, HnRegistActivity.class);
                startActivity(inte);
                break;
            case R.id.mIvEye:
                if (!isLookPwd) {
                    isLookPwd = true;
                    mEtPwd.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    mIvEye.setImageResource(R.drawable.eye_on);
                    mEtPwd.setSelection(mEtPwd.getText().toString().length());
                } else {
                    isLookPwd = false;
                    mEtPwd.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    mIvEye.setImageResource(R.drawable.eye_off);
                    mEtPwd.setSelection(mEtPwd.getText().toString().length());
                }
                break;
            case R.id.mIvWx:
                if(true) {
                    HnToastUtils.showToastShort("演示平台暂未开通此功能");
                    return;
                }
                mUMShareAPI.doOauthVerify(this, SHARE_MEDIA.WEIXIN, umAuthListener);
                break;
            case R.id.mIvQq:
                if(true) {
                    HnToastUtils.showToastShort("演示平台暂未开通此功能");
                    return;
                }
                mUMShareAPI.doOauthVerify(this, SHARE_MEDIA.QQ, umAuthListener);
                break;
            case R.id.mIvSina:
                if(true) {
                    HnToastUtils.showToastShort("演示平台暂未开通此功能");
                    return;
                }
                mUMShareAPI.doOauthVerify(this, SHARE_MEDIA.SINA, umAuthListener);
                break;

        }
    }


    /**
     * 请求开始
     */
    @Override
    public void requesting() {
        mTvLogin.setEnabled(false);
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
        Log.e("############", response);
        loginTcIm();
    }


    /**
     * 登录腾讯云通信
     */
    private void loginTcIm() {
        toHomeActivty();
        mTvLogin.setEnabled(true);
        if (mTcLoginMgr == null) {
            mTcLoginMgr = TCLoginMgr.getInstance();
        }
        mTcLoginMgr.setTCLoginCallback(new TCLoginMgr.TCLoginCallback() {
            @Override
            public void onSuccess() {
                mTcLoginMgr.removeTCLoginCallback();
                done();
                toHomeActivty();
            }

            @Override
            public void onFailure(int code, String msg) {
                mTcLoginMgr.removeTCLoginCallback();
                done();
                HnToastUtils.showToastShort(msg);
            }
        });
        mTcLoginMgr.imLogin(HnApplication.getmUserBean().getTim().getAccount(), HnApplication.getmUserBean().getTim().getSign(),
                HnApplication.getmUserBean().getTim().getApp_id(), HnApplication.getmUserBean().getTim().getAccount_type());
    }


    private void toHomeActivty() {
        if (TextUtils.isEmpty(HnApplication.getmUserBean().getUser_phone())) {
            startActivity(new Intent(HnLoginActivity.this, HnFirstBindPhoneActivity.class).putExtra("main", true));
            return;
        }
        mTvLogin.setEnabled(true);
        openActivity(HnMainActivity.class);
        HnAppManager.getInstance().finishActivity(HnLoginActivity.class);
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
        mTvLogin.setEnabled(true);

    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMultiLogin(HnMultiEvent event) {
        String loginType = HnPrefUtils.getString(HnConstants.LogInfo.PLATFORMNAME, "");
        String phone = HnPrefUtils.getString(HnConstants.LogInfo.USER_PHONE, "");
        String password = HnPrefUtils.getString(HnConstants.LogInfo.USER_PASSWORD, "");
        if ("phone".equalsIgnoreCase(loginType)) {
            if (mEtPwd != null && mEtPhone != null) {
                mEtPhone.setText(phone);
                mEtPhone.setSelection(phone.length());
                mEtPwd.setText(password);
                mEtPwd.setSelection(password.length());
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mUMShareAPI.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onBackPressed() {
        HnAppManager.getInstance().finishActivity(HnLoginActivity.class);
        HnAppManager.getInstance().exit();
    }

    /**
     * 退出返回
     */
/*    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
            //退出应用程序
            finish();
            HnAppManager.getInstance().exit();
        }
        return super.onKeyDown(keyCode, event);
    }*/
    @SuppressLint("LongLogTag")
    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e("################################", "Login destory");
        EventBus.getDefault().unregister(this);
    }

    /**
     * 登录回调
     **/
    private UMAuthListener umAuthListener = new UMAuthListener() {
        @Override
        public void onComplete(SHARE_MEDIA platform, int action, Map<String, String> data) {
            if (platform == SHARE_MEDIA.QQ) {
                mHnLoginBiz.executeLogin(data.get("access_token"), "", HnLoginBiz.QQ, data.get("uid"));
            } else if (platform == SHARE_MEDIA.WEIXIN) {
                mHnLoginBiz.executeLogin(data.get("access_token"), "", HnLoginBiz.WX, data.get("openid"));
            } else if (platform == SHARE_MEDIA.SINA) {
                mHnLoginBiz.executeLogin(data.get("access_token"), "", HnLoginBiz.SINA, data.get("uid"));
            }
        }

        @Override
        public void onError(SHARE_MEDIA platform, int action, Throwable t) {
            HnToastUtils.showToastShort("授权失败");
        }

        @Override
        public void onCancel(SHARE_MEDIA platform, int action) {
            HnToastUtils.showToastShort("登录取消");
        }
    };


}
