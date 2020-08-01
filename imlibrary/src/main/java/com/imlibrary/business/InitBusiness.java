package com.imlibrary.business;

import android.content.Context;
import android.content.Intent;

import com.imlibrary.constant.TCConstants;
import com.imlibrary.loginAndRegister.TCLoginMgr;
import com.tencent.TIMGroupSettings;
import com.tencent.TIMLogLevel;
import com.tencent.TIMManager;
import com.tencent.TIMUserStatusListener;


/**
 * 初始化
 * 包括imsdk等
 */
public class InitBusiness {

    private static final String TAG = InitBusiness.class.getSimpleName();

    private InitBusiness() {
    }

    public static void start(Context context) {
        initImsdk(context);
    }

    public static void start(Context context, int logLevel) {
        TIMManager.getInstance().setLogLevel(TIMLogLevel.values()[logLevel]);
        initImsdk(context);
    }


    /**
     * 初始化imsdk
     */
    private static void initImsdk(final Context context) {
        //初始化imsdk
        TIMManager.getInstance().init(context);
        //禁止服务器自动代替上报已读
        TIMManager.getInstance().disableAutoReport();
        //初始化群设置
        TIMManager.getInstance().initGroupSettings(new TIMGroupSettings());
        //注册sig失效监听回调
        TIMManager.getInstance().setUserStatusListener(new TIMUserStatusListener() {
            @Override
            public void onForceOffline() {
//                LocalBroadcastManager.getInstance(context.getApplicationContext()).sendBroadcast(new Intent(TCConstants.EXIT_APP));
            }

            @Override
            public void onUserSigExpired() {
//                TCLoginMgr.getInstance().reLogin();
            }
        });

    }


}
