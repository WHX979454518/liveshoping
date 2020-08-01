package com.hotniao.live.biz.home;

import com.hn.library.base.BaseActivity;
import com.hn.library.base.BaseRequestStateListener;
import com.hn.library.http.HnHttpUtils;
import com.hn.library.http.HnResponseHandler;
import com.hn.library.global.HnUrl;
import com.hotniao.live.model.HnHomeHotModel;
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
public class HnGameBiz {

    private String TAG = "HnHomeBiz";
    private BaseActivity context;

    private BaseRequestStateListener listener;

    public HnGameBiz(BaseActivity context) {
        this.context = context;
    }

    public void setBaseRequestStateListener(BaseRequestStateListener listener) {
        this.listener = listener;
    }

    /**
     * 网络请求:获取首页热门数据
     *
     * @param mPage  页数
     * @param cateId 分类
     */
    public void requestToHotList(int mPage, String cateId) {
        RequestParams param = new RequestParams();
        param.put("page", mPage + "");
        param.put("pagesize", 20 + "");
        if(!"-1".equals(cateId))
        param.put("game_category_id", cateId + "");

        HnHttpUtils.postRequest(HnUrl.Live_Game, param, TAG, new HnResponseHandler<HnHomeHotModel>(context, HnHomeHotModel.class) {
            @Override
            public void hnSuccess(String response) {
                if (model.getC() == 0) {
                    if (listener != null) {
                        listener.requestSuccess("game_list", response, model);
                    }
                } else {
                    if (listener != null) {
                        listener.requestFail("game_list", model.getC(), model.getM());
                    }
                }
            }

            @Override
            public void hnErr(int errCode, String msg) {
                if (listener != null) {
                    listener.requestFail("game_list", errCode, msg);
                }
            }
        });
    }
}
