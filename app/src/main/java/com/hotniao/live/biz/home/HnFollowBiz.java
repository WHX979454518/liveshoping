package com.hotniao.live.biz.home;

import android.text.TextUtils;

import com.hn.library.base.BaseActivity;
import com.hn.library.base.BaseRequestStateListener;
import com.hn.library.global.NetConstant;
import com.hn.library.http.HnHttpUtils;
import com.hn.library.http.HnResponseHandler;
import com.hn.library.utils.HnPrefUtils;
import com.hn.library.global.HnUrl;
import com.hotniao.live.model.HnHomeHotModel;
import com.hotniao.live.model.HnHomeLiveModel;
import com.hotniao.live.model.bean.HnHomeHotBean;
import com.hotniao.livelibrary.model.HnLiveListModel;
import com.hotniao.livelibrary.model.HnLiveRoomInfoModel;
import com.hotniao.livelibrary.widget.dialog.HnUserDetailDialog;
import com.loopj.android.http.RequestParams;

/**
 * Copyright (C) 2017,深圳市红鸟网络科技股份有限公司 All rights reserved.
 * 项目名称：youbo
 * 类描述：业务逻辑类，用于关注界面的网络请求以及业务逻辑
 * 创建人：mj
 * 创建时间：2017/9/19 12:51
 * 修改人：Administrator
 * 修改时间：2017/9/19 12:51
 * 修改备注：
 * Version:  1.0.0
 */
public class HnFollowBiz {

    private String TAG = "HnFollowBiz";
    private BaseActivity context;

    private BaseRequestStateListener listener;

    public HnFollowBiz(BaseActivity context) {
        this.context = context;
    }

    public void setBaseRequestStateListener(BaseRequestStateListener listener) {
        this.listener = listener;
    }

    /**
     * 网络请求：获取关注列表
     *
     * @param mPage
     */
    public void requestToFollowList(int mPage) {
        RequestParams param = new RequestParams();
        param.put("page", mPage + "");
        param.put("pagesize", 20 + "");

        HnHttpUtils.postRequest(HnUrl.Follow_Live_List, param, TAG, new HnResponseHandler<HnHomeLiveModel>(context, HnHomeLiveModel.class) {
            @Override
            public void hnSuccess(String response) {
                if (model.getC() == 0) {
                    if (listener != null) {
                        listener.requestSuccess("follow_live_list", response, model);
                    }
                } else {
                    if (listener != null) {
                        listener.requestFail("follow_live_list", model.getC(), model.getM());
                    }
                }
            }

            @Override
            public void hnErr(int errCode, String msg) {
                if (listener != null) {
                    listener.requestFail("follow_live_list", errCode, msg);
                }
            }
        });
    }

    /**
     * 网络请求：获取附近列表
     *
     * @param mPage
     */
    public void requestToNearList(int mPage, String city,String province) {
        RequestParams param = new RequestParams();
        param.put("page", mPage + "");
        param.put("pagesize", 10 + "");
        if (!TextUtils.isEmpty(city))
            param.put("city", city.trim());
        if (!TextUtils.isEmpty(province))
            param.put("province", province.trim());

        HnHttpUtils.postRequest(HnUrl.NEAR_Live_List, param, TAG, new HnResponseHandler<HnHomeLiveModel>(context, HnHomeLiveModel.class) {
            @Override
            public void hnSuccess(String response) {
                if (model.getC() == 0) {
                    if (listener != null) {
                        listener.requestSuccess("near_live_list", response, model);
                    }
                } else {
                    if (listener != null) {
                        listener.requestFail("near_live_list", model.getC(), model.getM());
                    }
                }
            }

            @Override
            public void hnErr(int errCode, String msg) {
                if (listener != null) {
                    listener.requestFail("near_live_list", errCode, msg);
                }
            }
        });
    }

}
