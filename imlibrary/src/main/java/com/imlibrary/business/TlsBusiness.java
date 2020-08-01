package com.imlibrary.business;

import android.content.Context;

import com.imlibrary.constant.TLSConfiguration;
import com.imlibrary.constant.TLSConstant;
import com.imlibrary.loginAndRegister.TCLoginMgr;
import com.imlibrary.loginAndRegister.TCRegisterMgr;


/**
 * 初始化tls登录模块
 */
public class TlsBusiness {


    private TlsBusiness(){}

    public static void init(Context context){
        TLSConfiguration.setSdkAppid(TLSConstant.SDK_APPID);
        TLSConfiguration.setAccountType(TLSConstant.ACCOUNT_TYPE);
        TLSConfiguration.setTimeout(8000);
        //初始化登录模块
        TCLoginMgr.getInstance().init(context);
        //初始化注册模块
        TCRegisterMgr.getInstance().init(context);
    }

}
