package com.hotniao.live.biz.register;

import android.text.TextUtils;

import com.hn.library.base.BaseActivity;
import com.hn.library.base.BaseRequestStateListener;
import com.hn.library.global.NetConstant;
import com.hn.library.http.HnHttpUtils;
import com.hn.library.http.HnResponseHandler;
import com.hn.library.utils.HnLogUtils;
import com.hn.library.utils.HnPrefUtils;
import com.hn.library.utils.HnRegexUtils;
import com.hn.library.utils.HnToastUtils;
import com.hotniao.live.R;
import com.hn.library.global.HnConstants;
import com.hn.library.global.HnUrl;
import com.hn.library.model.HnLoginModel;
import com.hotniao.live.model.HnRegisterCodeModel;
import com.hn.library.model.HnLoginBean;
import com.hotniao.live.utils.HnUserUtil;
import com.loopj.android.http.RequestParams;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Copyright (C) 2017,深圳市红鸟网络科技股份有限公司 All rights reserved.
 * 项目名称：youbo
 * 类描述：业务逻辑类，用于处理注册界面的网络请求以及业务逻辑
 * 创建人：mj
 * 创建时间：2017/9/4 10:56
 * 修改人：Administrator
 * 修改时间：2017/9/4 10:56
 * 修改备注：
 * Version:  1.0.0
 */
public class HnRegisterBiz {

    private String  TAG="HnRegisterBiz";
    private BaseActivity context;
    private BaseRequestStateListener listener;

    private boolean    isCanSendSms=true;//是否可以发送验证码
    private long       time=60;
    private Disposable observable;

    public HnRegisterBiz(BaseActivity  context) {
        this.context=context;
    }

    public  void   setRegisterListener(BaseRequestStateListener listener){
        this.listener=listener;
    }

    /**
     * 网络请求：注册功能第一步
     * @param phone   手机号码
     * @param nick    昵称
     * @param pwd     密码
     */
    public void requestToRegisterFirstStep(String phone, String nick, String pwd) {
        if (TextUtils.isEmpty(phone) ||!HnRegexUtils.isMobileExact(phone)) {
            if(listener!=null){
                listener.requestFail("register",0,context.getResources().getString(R.string.phone_account));
            }
            return;
        }
        if (TextUtils.isEmpty(nick)) {
            if(listener!=null){
                listener.requestFail("register",0,context.getResources().getString(R.string.log_input_nick));
            }
            return;
        }
        if (TextUtils.isEmpty(pwd)) {
            if(listener!=null){
                listener.requestFail("register",0,context.getResources().getString(R.string.please_input_pwd));
            }
            return;
        }
        if(pwd.length()<6||pwd.length()>16){
            if(listener!=null){
                listener.requestFail("register",0,context.getResources().getString(R.string.please_input_pwd));
            }
            return;
        }
        if(listener!=null){
            listener.requesting();
        }
        RequestParams param =new RequestParams();
        param.put("phone", phone);
        param.put("nick", nick);
        param.put("password", pwd);
        HnHttpUtils.postRequest(HnUrl.REGISTER_FIRST_STEP, param, TAG, new HnResponseHandler<HnLoginModel>(context,HnLoginModel.class) {

            @Override
            public void hnSuccess(String response) {
                if (model.getC() == 0) {
                    if(listener!=null){
                        listener.requestSuccess("register",response,model);
                    }
                } else  {
                    if(listener!=null){
                        listener.requestFail("register",model.getC(),model.getM());
                    }
                }
            }

            @Override
            public void hnErr(int errCode, String msg) {
                if(listener!=null){
                    listener.requestFail("register",errCode,msg);
                }
            }
        });


    }

    /**
     * 网络请求:注册功能第二部
     * @param code     验证码
     * @param key      key
     */
    public void requestToRegisterSecondStep(String code, String key) {
        if (TextUtils.isEmpty(code)) {
            if(listener!=null){
                listener.requestFail("register_second_step",0,context.getResources().getString(R.string.log_input_ver));
            }
            return;
        }
        //设备id
       String  mAndroidId = HnUserUtil.getUniqueid();
        if(listener!=null){
            listener.requesting();
        }
        RequestParams param =new RequestParams();
        param.put("key", key);
        param.put("msg_code",code);
        param.put("uniqueid", mAndroidId);
        HnHttpUtils.postRequest(HnUrl.REGISTER_SECOND_STEP, param, TAG, new HnResponseHandler<HnLoginModel>(HnLoginModel.class) {

            @Override
            public void hnSuccess(String response) {
                if (model.getC() == 0) {
                    if(listener!=null){
                        listener.requestSuccess("register_second_step",response,model);
                    }
                    HnLoginBean result = model.getD();
                    if(result!=null&&result.getUser_id()!=null){
                        HnPrefUtils.setString(HnConstants.LogInfo.UID,result.getUser_id());
                        HnPrefUtils.setString(NetConstant.User.TOKEN,result.getAccess_token());
                        HnPrefUtils.setString(NetConstant.User.Webscket_Url,result.getWs_url());
                        HnPrefUtils.setString(NetConstant.User.Unread_Count,"0");
                        HnPrefUtils.setBoolean(NetConstant.User.User_Forbidden,false);
                    }
                } else  {
                    if(listener!=null){
                        listener.requestFail("register_second_step",model.getC(),model.getM());
                    }
                }
            }
            @Override
            public void hnErr(int errCode, String msg) {
                if(listener!=null){
                    listener.requestFail("register_second_step",errCode,msg);
                }
            }
        });



    }

    /**
     * 网络请求:发送验证码
     * @param phone
     */
    public void requestToSendSms(String phone,String key) {
        if(TextUtils.isEmpty(phone)){
            if(listener!=null){
                listener.requestFail("sms",0,context.getString(R.string.send_sms_fail));
            }
            return;
        }
        //可以发送验证码
        if(isCanSendSms){

            //验证码定时器
            sendSmstimerTask();
            RequestParams param =new RequestParams();
            param.put("phone", phone);
            param.put("type","register");
            param.put("key", key);
            HnHttpUtils.postRequest(HnUrl.SENDSMS, param, TAG, new HnResponseHandler<HnRegisterCodeModel>(HnRegisterCodeModel.class) {

                @Override
                public void hnSuccess(String response) {
                    if (model.getC() == 0) {
                        if(listener!=null){
                            listener.requestSuccess("sms",response,model);
                        }
                    } else  {
                        if(listener!=null){
                            listener.requestFail("sms",model.getC(),model.getM());
                        }
                    }
                }
                @Override
                public void hnErr(int errCode, String msg) {
                    if(listener!=null){
                        listener.requestFail("sms",errCode,msg);
                    }
                }
            });

         //一分钟未到不可发送要验证码
        }else{
            if(listener!=null){
                HnToastUtils.showToastShort(context.getString(R.string.not_send_sms_again));
            }
        }

    }

    /**
     * 验证码定时器
     */
    public void sendSmstimerTask() {
        isCanSendSms=false;
        time=60;
        observable = Observable.interval(1000, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        long result = time -1;
                        if (result <= 0) {
                            isCanSendSms = true;
                            time = result;
                            if(observable!=null){
                                observable.dispose();
                            }
                        } else {
                            isCanSendSms = false;
                            time = result;
                        }
                        HnLogUtils.i(TAG,"短信定时器:"+aLong+"--->"+result);
                    }
                });
    }

    /**
     * 关闭定时器
     */
    public void closeObservable() {
        if(observable!=null){
            observable.dispose();
        }
    }


    /**
     * 设置是否可以发送验证码
     * @param flag  true:可以   false ：不可以
     */
    public  void  setCanSendSms(boolean flag){
        this.isCanSendSms=flag;
    }

    public void requestToRegisterFirstStep(String phone, String mYzm, String mPwd, String mYqm) {
        if (TextUtils.isEmpty(phone) ||!HnRegexUtils.isMobileExact(phone)) {
            if(listener!=null){
                listener.requestFail("register",0,context.getResources().getString(R.string.phone_account));
            }
            return;
        }
        if (TextUtils.isEmpty(mYzm)) {
            if(listener!=null){
                listener.requestFail("register",0,context.getResources().getString(R.string.log_input_yzm));
            }
            return;
        }
        if (TextUtils.isEmpty(mPwd)) {
            if(listener!=null){
                listener.requestFail("register",0,context.getResources().getString(R.string.please_input_pwd));
            }
            return;
        }
        if(mPwd.length()<6||mPwd.length()>16){
            if(listener!=null){
                listener.requestFail("register",0,context.getResources().getString(R.string.please_input_pwd));
            }
            return;
        }
        //设备id
        String  mAndroidId = HnUserUtil.getUniqueid();
        if(listener!=null){
            listener.requesting();
        }
        RequestParams param =new RequestParams();
        param.put("phone", phone);
        param.put("code",mYzm);
        param.put("password", mPwd);
        if(!TextUtils.isEmpty(mYqm)){
            param.put("invite_code",mYqm);
        }
        HnHttpUtils.postRequest(HnUrl.REGISTER_PHONE, param, TAG, new HnResponseHandler<HnLoginModel>(HnLoginModel.class) {

            @Override
            public void hnSuccess(String response) {
                if (model.getC() == 0) {
                    if(listener!=null){
                        listener.requestSuccess("register_second_step",response,model);
                    }
                    HnLoginBean result = model.getD();
                    if(result!=null&&result.getUser_id()!=null){
                        HnPrefUtils.setString(HnConstants.LogInfo.UID,result.getUser_id());
                        HnPrefUtils.setString(NetConstant.User.USER_INFO, response);
                        HnPrefUtils.setString(NetConstant.User.STORE_STATE, result.getStore().getStatus());
                        HnPrefUtils.setString(NetConstant.User.TOKEN,result.getAccess_token());
                        HnPrefUtils.setString(NetConstant.User.Webscket_Url,result.getWs_url());
                        HnPrefUtils.setString(NetConstant.User.Unread_Count,"0");
                        HnPrefUtils.setBoolean(NetConstant.User.User_Forbidden,false);
                    }
                } else  {
                    if(listener!=null){
                        listener.requestFail("register_second_step",model.getC(),model.getM());
                    }
                }
            }
            @Override
            public void hnErr(int errCode, String msg) {
                if(listener!=null){
                    listener.requestFail("register_second_step",errCode,msg);
                }
            }
        });


    }
}
