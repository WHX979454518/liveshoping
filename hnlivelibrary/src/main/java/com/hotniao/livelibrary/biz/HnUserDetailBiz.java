package com.hotniao.livelibrary.biz;

import android.app.Activity;
import android.text.TextUtils;

import com.hn.library.base.BaseActivity;
import com.hn.library.base.BaseRequestStateListener;
import com.hn.library.global.HnUrl;
import com.hn.library.http.BaseResponseModel;
import com.hn.library.http.HnHttpUtils;
import com.hn.library.http.HnResponseHandler;
import com.hn.library.utils.HnRefreshDirection;
import com.hn.library.utils.HnToastUtils;
import com.hotniao.livelibrary.config.HnLiveConstants;
import com.hotniao.livelibrary.config.HnLiveUrl;
import com.hotniao.livelibrary.control.HnUserControl;
import com.hotniao.livelibrary.model.HnUserInfoDetailModel;
import com.loopj.android.http.RequestParams;

import org.greenrobot.eventbus.EventBus;

/**
 * Copyright (C) 2017,深圳市红鸟网络科技股份有限公司 All rights reserved.
 * 项目名称：youbo
 * 类描述：业务逻辑类，用于用户详情界面的网络请求以及业务逻辑
 * 创建人：mj
 * 创建时间：2017/9/13 10:11
 * 修改人：Administrator
 * 修改时间：2017/9/13 10:11
 * 修改备注：
 * Version:  1.0.0
 */
public class HnUserDetailBiz {

    private String TAG = "HnPrivateLetterBiz";
    private Activity context;

    private BaseRequestStateListener listener;

    public HnUserDetailBiz(Activity context) {
        this.context = context;
    }

    public void setBaseRequestStateListener(BaseRequestStateListener listener) {
        this.listener = listener;
    }

    /**
     * 网络请求:获取用户信息
     *
     * @param uid
     * @param mRoomId 房间id
     */
    public void requestToUserDetail(String uid, String mRoomId) {
        if (TextUtils.isEmpty(uid)) return;
        RequestParams param = new RequestParams();
        param.put("user_id", uid);
        param.put("anchor_user_id", mRoomId);
        HnHttpUtils.postRequest(HnLiveUrl.Get_User_Info, param, TAG, new HnResponseHandler<HnUserInfoDetailModel>(HnUserInfoDetailModel.class) {
            @Override
            public void hnSuccess(String response) {
                if (model.getC() == 0) {
                    if (listener != null) {
                        listener.requestSuccess("user_info_detail", response, model);
                    }
                } else {
                    if (listener != null) {
                        listener.requestFail("user_info_detail", model.getC(), model.getM());
                    }
                }
            }

            @Override
            public void hnErr(int errCode, String msg) {
                if (listener != null) {
                    listener.requestFail("user_info_detail", errCode, msg);
                }

            }
        });

    }

    public void setBackDel(String user_id) {
        RequestParams params = new RequestParams();
        params.put("user_id", user_id);
        HnHttpUtils.postRequest(HnUrl.SET_BLACK_DEL, params, "取消黑名单", new HnResponseHandler<BaseResponseModel>(BaseResponseModel.class) {
            @Override
            public void hnSuccess(String response) {
                if (listener != null) {
                    listener.requestSuccess("black_del", response, "");
                }
            }

            @Override
            public void hnErr(int errCode, String msg) {
                if (listener != null) {
                    listener.requestFail("black_del", errCode, msg);
                }
            }
        });
    }

    public void setBackAdd(String user_id) {
        RequestParams params = new RequestParams();
        params.put("user_id", user_id);
        HnHttpUtils.postRequest(HnUrl.SET_BLACK_ADD, params, "添加黑名单", new HnResponseHandler<BaseResponseModel>(BaseResponseModel.class) {
            @Override
            public void hnSuccess(String response) {
                if (listener != null) {
                    listener.requestSuccess("black_add", response, "");
                }
            }

            @Override
            public void hnErr(int errCode, String msg) {
                if (listener != null) {
                    listener.requestFail("black_add", errCode, msg);
                }
            }
        });
    }


    /**
     * 网络请求:是否关注
     *
     * @param isCared 是否已关注
     * @param uid     用户id
     */
    public void requestToFollow(boolean isCared, String uid, String anchorId) {

        if (isCared) {
            HnUserControl.cancelFollow(uid, new HnUserControl.OnUserOperationListener() {
                @Override
                public void onSuccess(String uid, Object obj, String response) {
                    if (listener != null) {
                        listener.requestSuccess("follow", response, "");
                        EventBus.getDefault().post(new CareEvent(true));
                    }
                }

                @Override
                public void onError(String uid, int errCode, String msg) {
                    if (listener != null) {
                        listener.requestFail("follow", errCode, msg);
                    }
                }
            });
        } else {

            HnUserControl.addFollow(uid, anchorId, new HnUserControl.OnUserOperationListener() {
                @Override
                public void onSuccess(String uid, Object obj, String response) {
                    if (listener != null) {
                        listener.requestSuccess("follow", response, "");
                        EventBus.getDefault().post(new CareEvent(false));
                    }
                }

                @Override
                public void onError(String uid, int errCode, String msg) {
                    if (listener != null) {
                        listener.requestFail("follow", errCode, msg);
                    }
                }
            });

        }

    }
}
