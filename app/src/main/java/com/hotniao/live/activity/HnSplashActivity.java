package com.hotniao.live.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.hn.library.global.NetConstant;
import com.hn.library.utils.HnLogUtils;
import com.hn.library.utils.HnPrefUtils;
import com.hn.library.utils.HnUtils;
import com.hotniao.live.HnApplication;
import com.hotniao.live.HnMainActivity;
import com.hotniao.live.biz.home.HnHomeCate;
import com.hn.library.model.HnLoginBean;
import com.hn.library.utils.PermissionHelper;
import com.hotniao.livelibrary.control.HnUpLoadPhotoControl;
import com.hotniao.livelibrary.control.HnUserControl;
import com.hn.library.model.HnLoginModel;
import com.hn.library.global.HnConstants;
import com.hotniao.live.utils.HnAppConfigUtil;
import com.imlibrary.loginAndRegister.TCLoginMgr;

/**
 * Copyright (C) 2017,深圳市红鸟网络科技股份有限公司 All rights reserved.
 * 项目名称：红鸟电商
 * 类描述：启动页
 * 创建人：mj
 * 创建时间：2017/3/6 16:16
 * 修改人：mj
 * 修改时间：2017/3/6 16:16
 * 修改备注：
 * Version:  1.0.0
 */
public class HnSplashActivity extends AppCompatActivity {

    /**
     * 腾讯云登录
     */
    private TCLoginMgr mTcLoginMgr;
    private static final String TAG = "HnSplashActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        HnApplication.logout();
        HnAppConfigUtil.getConfig();
        HnHomeCate.getCateData();
        goToMainAct();
        HnPrefUtils.setInt(HnConstants.PERMISSION_SIZE, PermissionHelper.getPerSize(this));
    }


    /**
     * 进入首页
     */
    private void goToMainAct() {
        if (!HnUtils.checkConnectionOk(this)) {//无网络
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    toLoginActivity();
                }
            }, 500);

        } else {//有网络
            String token = HnPrefUtils.getString(NetConstant.User.TOKEN, "");
            String uid = HnPrefUtils.getString(NetConstant.User.UID, "");
            if (!TextUtils.isEmpty(token) && !TextUtils.isEmpty(uid)) {
                String userInfo = HnPrefUtils.getString(NetConstant.User.USER_INFO, "");
                if (!TextUtils.isEmpty(userInfo)) {
                    Gson gson = new Gson();
                    final HnLoginModel model = gson.fromJson(userInfo, HnLoginModel.class);
                    if (model != null && model.getC() == 0 && model.getD() != null && model.getD().getTim() != null) {
                        HnHomeCate.setOnCateListener(new HnHomeCate.OnCateListener() {
                            @Override
                            public void onSuccess() {
                                HnHomeCate.removeListener();
                                getCateDataSuccess(model);
                            }

                            @Override
                            public void onError(int errCode, String msg) {
                                HnHomeCate.removeListener();
                                getCateDataSuccess(model);
                            }
                        });
                    } else {
                        onAutoLogin(uid);
                    }
                } else {
                    onAutoLogin(uid);
                }
            } else {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        toLoginActivity();
                    }
                }, 500);
            }
        }
    }

    /**
     * 获取分类成功
     *
     * @param model
     */
    private void getCateDataSuccess(HnLoginModel model) {
        HnLoginBean result = model.getD();

        if (result != null && result.getUser_id() != null) {
            HnApplication.setmUserBean(result);
            HnPrefUtils.setString(NetConstant.User.UID, result.getUser_id());
            HnPrefUtils.setString(NetConstant.User.LEVEL, result.getUser_level());
            HnPrefUtils.setString(NetConstant.User.TOKEN, result.getAccess_token());
            HnPrefUtils.setString(NetConstant.User.Webscket_Url, result.getWs_url());
            HnPrefUtils.setString(NetConstant.User.Unread_Count, "0");
            HnPrefUtils.setBoolean(NetConstant.User.User_Forbidden, false);
            loginTcIm();
        }

    }

    /**
     * 登录腾讯云IM
     */
    private void loginTcIm() {
        if (mTcLoginMgr == null) {
            mTcLoginMgr = TCLoginMgr.getInstance();
        }
        mTcLoginMgr.setTCLoginCallback(new TCLoginMgr.TCLoginCallback() {
            @Override
            public void onSuccess() {
                mTcLoginMgr.removeTCLoginCallback();
                toMainActivity();
            }

            @Override
            public void onFailure(int code, String msg) {
                mTcLoginMgr.removeTCLoginCallback();
                toLoginActivity();
            }
        });
        mTcLoginMgr.imLogin(HnApplication.getmUserBean().getTim().getAccount(), HnApplication.getmUserBean().getTim().getSign(),
                HnApplication.getmUserBean().getTim().getApp_id(), HnApplication.getmUserBean().getTim().getAccount_type());
    }

    /***
     * 获取用户信息
     * @param uid
     */
    private void onAutoLogin(String uid) {
        HnUserControl.getProfile(new HnUserControl.OnUserInfoListener() {
            @Override
            public void onSuccess(String uid, HnLoginModel model, String response) {
                loginTcIm();
            }

            @Override
            public void onError(int errCode, String msg) {
                toLoginActivity();
            }
        });
    }

    /**
     * 进入登录或引导页
     */
    private void toLoginActivity() {
        //如果用户是第一次使用
        boolean isFirstUse = HnPrefUtils.getBoolean(HnConstants.Setting.SPLASH_FIRST_USE, true);

        if (isFirstUse) {
            //引导界面
            openActivity(HnGuideActivity.class);
        } else {
            //进入登录界面
            openActivity(HnLoginActivity.class);
        }

        finish();
    }

    /**
     * 进入主界面
     */
    private void toMainActivity() {
        openActivity(HnMainActivity.class);
        finish();
    }


    /**
     * 根据传入的类(class)打开指定的activity
     *
     * @param pClass
     */
    public void openActivity(Class<?> pClass) {
        Intent itent = new Intent();
        itent.setClass(this, pClass);
        startActivity(itent);
    }


}
