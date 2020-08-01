package com.hotniao.live.biz.live;

import android.graphics.Bitmap;
import android.net.Uri;
import android.text.TextUtils;

import com.hn.library.base.BaseActivity;
import com.hn.library.base.BaseRequestStateListener;
import com.hn.library.http.HnHttpUtils;
import com.hn.library.http.HnResponseHandler;
import com.hn.library.picker.photo_picker.HnPhotoUtils;
import com.hn.library.utils.EncryptUtils;
import com.hn.library.utils.HnDateUtils;
import com.hn.library.utils.HnPrefUtils;
import com.hn.library.utils.HnToastUtils;
import com.hn.library.utils.HnUtils;
import com.hotniao.livelibrary.control.HnUpLoadPhotoControl;
import com.hotniao.livelibrary.model.HnStartLiveInfoModel;
import com.hn.library.global.HnUrl;
import com.hotniao.live.dialog.HnEditHeaderDialog;
import com.loopj.android.http.RequestParams;

import java.io.File;

/**
 * Copyright (C) 2017,深圳市红鸟网络科技股份有限公司 All rights reserved.
 * 项目名称：youbo
 * 类描述：业务逻辑类，用于处理开播之前的设置界面的网络请求以及业务逻辑
 * 创建人：mj
 * 创建时间：2017/9/14 9:05
 * 修改人：Administrator
 * 修改时间：2017/9/14 9:05
 * 修改备注：
 * Version:  1.0.0
 */
public class HnBeforeLiveSettingBiz {

    private String TAG = "HnBeforeLiveSettingBiz";
    private BaseActivity context;

    private BaseRequestStateListener listener;

    public HnBeforeLiveSettingBiz(BaseActivity context) {
        this.context = context;
    }

    public void setBaseRequestStateListener(BaseRequestStateListener listener) {
        this.listener = listener;
    }


    /**
     * 上传图片文件
     */
    public void uploadPicFile() {
        HnEditHeaderDialog mHeaderDialog = HnEditHeaderDialog.newInstance();
        mHeaderDialog.show(context.getSupportFragmentManager(), "header");
        mHeaderDialog.setOnImageCallBack(new HnEditHeaderDialog.OnImageCallBack() {
            @Override
            public void onImage(Bitmap bitmap, Uri uri) {
                if (bitmap != null) {
                    //对文件名进行MD5加密   YYYYMMDD +md5
                    String fileName = HnDateUtils.getCurrentDate("yyyyMMdd").toUpperCase() + EncryptUtils.encryptMD5ToString(HnUtils.createRandom(false, 5)) + ".png";
                    File picFile = HnPhotoUtils.bitmapToFile(bitmap, fileName);
                    if (picFile.exists()) {
                        requestToGetToken(picFile);
                    }
                }
            }
        });

    }

    /**
     * 获取token
     *
     * @param
     * @param file 上传到文件
     */
    public void requestToGetToken(final File file) {
        if (listener != null) {
            listener.requesting();
        }
        HnUpLoadPhotoControl.getTenSign(file);
        HnUpLoadPhotoControl.setUpStutaListener(new HnUpLoadPhotoControl.UpStutaListener() {
            @Override
            public void uploadSuccess(final String key, Object token) {
                if (listener != null) {
                    listener.requestSuccess("upload_pic_file", key, "");
                }
            }

            @Override
            public void uploadError(int code, String msg) {
                if (listener != null) {
                    listener.requestFail("upload_pic_file", 3, "上传图片失败");
                }
            }
        });


    }

    /**
     * 上传图片
     *
     * @param file  上传到文件
     * @param token 七牛token
     * @param url   文件地址前缀  服务器返回 用于拼接
     */


    /**
     * 网络请求:
     */
    public void requestToStartLive(String imgUrl, final String title,
                                   String cityAddress, String lat, String lng,
                                   String city, String province) {

        RequestParams param = new RequestParams();
        if (!TextUtils.isEmpty(title)) {
            param.put("anchor_live_title", title);
        }else {
            HnToastUtils.showToastShort("请输入直播标题");
            return;
        }
        if (!TextUtils.isEmpty(city)) {
            param.put("province", province);
            param.put("city", city);
        }
        if (!TextUtils.isEmpty(lat)) param.put("anchor_lat", lat);
        if (!TextUtils.isEmpty(lng)) param.put("anchor_lng", lng);

        if (!TextUtils.isEmpty(cityAddress)) {
            param.put("anchor_local", cityAddress);
        }
        if (!TextUtils.isEmpty(imgUrl)) {
            param.put("anchor_live_img", imgUrl);
        }
        HnHttpUtils.postRequest(HnUrl.PUSH_STARTLIVE, param, TAG, new HnResponseHandler<HnStartLiveInfoModel>(context, HnStartLiveInfoModel.class) {
            @Override
            public void hnSuccess(String response) {
                if (model.getC() != 0) {
                    if (listener != null) {
                        listener.requestFail("start_live", model.getC(), model.getM());
                    }
                } else {
                    if (listener != null) {
                        HnPrefUtils.setString("title",title);
                        listener.requestSuccess("start_live", response, model);
                    }
                }
            }

            @Override
            public void hnErr(int errCode, String msg) {
                if (listener != null) {
                    listener.requestFail("start_live", errCode, msg);
                }
            }
        });

    }

}
