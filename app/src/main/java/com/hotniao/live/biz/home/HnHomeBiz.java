package com.hotniao.live.biz.home;

import android.text.TextUtils;

import com.hn.library.base.BaseActivity;
import com.hn.library.base.BaseRequestStateListener;
import com.hn.library.global.NetConstant;
import com.hn.library.http.HnHttpUtils;
import com.hn.library.http.HnResponseHandler;
import com.hn.library.utils.HnPrefUtils;
import com.hn.library.global.HnUrl;
import com.hotniao.live.model.HnBannerModel;
import com.hotniao.live.model.HnHomeLiveModel;
import com.hotniao.live.model.bean.HnHomeHotBean;
import com.hotniao.livelibrary.model.HnLiveRoomInfoModel;
import com.hotniao.livelibrary.widget.dialog.HnUserDetailDialog;
import com.loopj.android.http.RequestParams;

/**
 * Copyright (C) 2017,深圳市红鸟网络科技股份有限公司 All rights reserved.
 * 项目名称：youbo
 * 类描述：业务逻辑类，用于首页界面的网络请求以及业务逻辑
 * 创建人：mj
 * 创建时间：2017/9/13 13:54
 * 修改人：Administrator
 * 修改时间：2017/9/13 13:54
 * 修改备注：
 * Version:  1.0.0
 */
public class HnHomeBiz {

    private String TAG = "HnHomeBiz";
    private BaseActivity context;

    private BaseRequestStateListener listener;

    public HnHomeBiz(BaseActivity context) {
        this.context = context;
    }

    public void setBaseRequestStateListener(BaseRequestStateListener listener) {
        this.listener = listener;
    }

    /**
     * 网络请求:获取首页热门数据
     *
     * @param mPage  页数
     */
    public void requestToHotList(int mPage) {
        RequestParams param = new RequestParams();
        param.put("page", mPage + "");
        param.put("pagesize", 20 + "");
//        if(!"-1".equals(cateId))
//        param.put("anchor_category_id", cateId + "");

        HnHttpUtils.postRequest(HnUrl.Live_HOT, param, TAG, new HnResponseHandler<HnHomeLiveModel>(context, HnHomeLiveModel.class) {
            @Override
            public void hnSuccess(String response) {
                if (model.getC() == 0) {
                    if (listener != null) {
                        listener.requestSuccess("hot_list", response, model);
                    }
                } else {
                    if (listener != null) {
                        listener.requestFail("hot_list", model.getC(), model.getM());
                    }
                }
            }

            @Override
            public void hnErr(int errCode, String msg) {
                if (listener != null) {
                    listener.requestFail("hot_list", errCode, msg);
                }
            }
        });
    }

    /**
     * 获取轮播图
     */
    public void getBanner() {
        if (listener != null) {
            listener.requesting();
        }
        HnHttpUtils.postRequest(HnUrl.BANNER, null, TAG, new HnResponseHandler<HnBannerModel>(context, HnBannerModel.class) {
            @Override
            public void hnSuccess(String response) {
                if (model.getC() == 0) {
                    if (listener != null) {
                        listener.requestSuccess("banner_data", response, model);
                    }
                } else {
                    if (listener != null) {
                        listener.requestFail("banner_data", model.getC(), model.getM());
                    }
                }
            }

            @Override
            public void hnErr(int errCode, String msg) {
                if (listener != null) {
                    listener.requestFail("banner_data", errCode, msg);
                }
            }
        });

    }

}
