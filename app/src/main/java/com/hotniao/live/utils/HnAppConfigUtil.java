package com.hotniao.live.utils;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.hn.library.HnBaseApplication;
import com.hn.library.global.HnConstants;
import com.hn.library.global.HnUrl;
import com.hn.library.http.HnHttpUtils;
import com.hn.library.http.HnResponseHandler;
import com.hn.library.model.HnConfigModel;
import com.hn.library.utils.HnPrefUtils;

/**
 * Copyright (C) 2016,深圳市红鸟网络科技股份有限公司 All rights reserved.
 * 类描述：获取配置
 * 创建人：刘勇
 * 创建时间：2017/2/22 16:22
 * 修改人：刘勇
 * 修改时间：2017/2/22 16:34
 * 修改备注：
 * Version:  1.0.0
 */

public class HnAppConfigUtil {

    public static void getConfig() {
        HnHttpUtils.getRequest(HnUrl.USER_APP_CONFIG, null, HnUrl.USER_APP_CONFIG, new HnResponseHandler<HnConfigModel>(HnConfigModel.class) {
            @Override
            public void hnSuccess(String response) {
                if (model.getC() == 0) {
                    HnPrefUtils.setString(HnConstants.Setting.USER_CONFIG_MSG, response);
                    HnBaseApplication.setmConfig(model.getD());
                } else {
                    String config = HnPrefUtils.getString(HnConstants.Setting.USER_CONFIG_MSG, "");
                    if (!TextUtils.isEmpty(config)) {
                        Gson g = new Gson();
                        HnConfigModel hnConfigModel = g.fromJson(config, HnConfigModel.class);
                        HnBaseApplication.setmConfig(hnConfigModel.getD());
                    }
                }
            }

            @Override
            public void hnErr(int errCode, String msg) {
                String config = HnPrefUtils.getString(HnConstants.Setting.USER_CONFIG_MSG, "");
                if (!TextUtils.isEmpty(config)) {
                    Gson g = new Gson();
                    HnConfigModel hnConfigModel = g.fromJson(config, HnConfigModel.class);
                    HnBaseApplication.setmConfig(hnConfigModel.getD());
                }
            }
        });
    }
}
