package com.hotniao.live.biz.home;

import com.hn.library.http.HnHttpUtils;
import com.hn.library.http.HnResponseHandler;
import com.hn.library.model.HnLoginModel;
import com.hn.library.utils.HnLogUtils;
import com.hn.library.global.HnUrl;
import com.hotniao.live.model.HnHomeLiveCateModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Copyright (C) 2016,深圳市红鸟网络科技股份有限公司 All rights reserved.
 * 类描述：主页面
 * 创建人：刘勇
 * 创建时间：2017/2/22 16:22
 * 修改人：刘勇
 * 修改时间：2017/2/22 16:34
 * 修改备注：
 * Version:  1.0.0
 */

public class HnHomeCate {
    public static List<HnHomeLiveCateModel.DEntity> mCateData = new ArrayList<>();

    public static void getCateData() {
        HnHttpUtils.postRequest(HnUrl.Live_NAVBAR, null, HnUrl.Live_NAVBAR, new HnResponseHandler<HnHomeLiveCateModel>(HnHomeLiveCateModel.class) {
            @Override
            public void hnSuccess(String response) {
                if (0 == model.getC()) {
                    if (0 == model.getC() && model.getD() != null) {
                        mCateData.clear();
                        mCateData.addAll(model.getD());
                    }
                    if (mListener != null) {
                        mListener.onSuccess();
                    }
                } else {
                    if (mListener != null) {
                        mListener.onError(model.getC(), model.getM());
                    }
                }
            }

            @Override
            public void hnErr(int errCode, String msg) {
                if (mListener != null) {
                    mListener.onError(errCode, msg);
                }
            }
        });
    }

    public static void setOnCateListener(OnCateListener listener) {
        mListener = listener;
    }

    private static OnCateListener mListener;

    /**
     * 用户信息
     */
    public interface OnCateListener {
        void onSuccess();

        void onError(int errCode, String msg);
    }

    public static void removeListener() {
        mListener = null;
    }

}
