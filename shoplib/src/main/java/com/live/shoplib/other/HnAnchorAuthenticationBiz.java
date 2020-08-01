package com.live.shoplib.other;

import android.graphics.Bitmap;
import android.net.Uri;

import com.hn.library.base.BaseActivity;
import com.hn.library.base.BaseRequestStateListener;
import com.hn.library.global.HnUrl;
import com.hn.library.http.HnHttpUtils;
import com.hn.library.http.HnResponseHandler;
import com.hn.library.picker.photo_picker.HnPhotoUtils;
import com.hn.library.utils.EncryptUtils;
import com.hn.library.utils.HnDateUtils;
import com.hn.library.utils.HnUtils;
import com.hn.library.view.WaitProDialog;
import com.live.shoplib.bean.HnAuthDetailModel;
import com.live.shoplib.ui.dialog.HnEditHeaderDialog;
import com.live.shoplib.ui.dialog.HnUpFileDialog;
import com.live.shoplib.widget.control.HnUpLoadPhotoControl;

import java.io.File;

/**
 * Copyright (C) 2017,深圳市红鸟网络科技股份有限公司 All rights reserved.
 * 项目名称：youbo
 * 类描述：业务逻辑类，用于处理主播实名认证界面的网络请求以及业务逻辑
 * 创建人：mj
 * 创建时间：2017/9/7 10:03
 * 修改人：Administrator
 * 修改时间：2017/9/7 10:03
 * 修改备注：
 * Version:  1.0.0
 */
public class HnAnchorAuthenticationBiz {

    private String TAG = "HnAnchorAuthenticationBiz";
    private BaseActivity context;

    private BaseRequestStateListener listener;

    public HnAnchorAuthenticationBiz(BaseActivity context) {
        this.context = context;
    }

    public void setLoginListener(BaseRequestStateListener listener) {
        this.listener = listener;
    }


    /**
     * 网络请求：获取用户的主播状态信息
     */
    public void reuqestToAnchorAuthStatusInfo() {
        HnHttpUtils.postRequest(HnUrl.CERTIFICATION_CHECK, null, TAG, new HnResponseHandler<HnAuthDetailModel>(context, HnAuthDetailModel.class) {
            @Override
            public void hnSuccess(String response) {
                if (model.getC() == 0) {
                    if (listener != null) {
                        listener.requestSuccess("AnchorAuthStatusInfo", response, model.getD());
                    }
                } else {
                    if (listener != null) {
                        listener.requestFail("AnchorAuthStatusInfo", model.getC(), model.getM());
                    }
                }
            }

            @Override
            public void hnErr(int errCode, String msg) {
                if (listener != null) {
                    listener.requestFail("AnchorAuthStatusInfo", errCode, msg);
                }
            }
        });
    }


    /**
     * 上传图片文件
     *
     * @param select 标识添加的第几张   身份证正面照/身份证正面照/手持身份证正面照
     */
    public void uploadPicFile(final String select) {
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
                        requestToGetToken(picFile, select);
                    }
                }
            }
        });

    }

    public void uploadPicFile(final String select, boolean crop) {
        HnEditHeaderDialog mHeaderDialog = HnEditHeaderDialog.newInstance();
        mHeaderDialog.setCropPhoto(crop);
        mHeaderDialog.show(context.getSupportFragmentManager(), "header");
        mHeaderDialog.setOnImageCallBack(new HnEditHeaderDialog.OnImageCallBack() {
            @Override
            public void onImage(Bitmap bitmap, Uri uri) {
                if (bitmap != null) {
                    //对文件名进行MD5加密   YYYYMMDD +md5
                    String fileName = HnDateUtils.getCurrentDate("yyyyMMdd").toUpperCase() + EncryptUtils.encryptMD5ToString(HnUtils.createRandom(false, 5)) + ".png";
                    File picFile = HnPhotoUtils.bitmapToFile(bitmap, fileName);
                    if (picFile.exists()) {
                        requestToGetToken(picFile, select);
                    }
                }
            }
        });

    }

    public void uploadPicFile(final String select, BaseRequestStateListener listener) {
        HnEditHeaderDialog mHeaderDialog = HnEditHeaderDialog.newInstance();
        mHeaderDialog.show(context.getSupportFragmentManager(), "header");
        this.listener = listener;
        mHeaderDialog.setOnImageCallBack(new HnEditHeaderDialog.OnImageCallBack() {
            @Override
            public void onImage(Bitmap bitmap, Uri uri) {
                if (bitmap != null) {
                    //对文件名进行MD5加密   YYYYMMDD +md5
                    String fileName = HnDateUtils.getCurrentDate("yyyyMMdd").toUpperCase() + EncryptUtils.encryptMD5ToString(HnUtils.createRandom(false, 5)) + ".png";
                    File picFile = HnPhotoUtils.bitmapToFile(bitmap, fileName);
                    if (picFile.exists()) {
                        requestToGetToken(picFile, select);
                    }
                }
            }
        });

    }

    public void uploadPicFileVideo(final String select) {
        HnUpFileDialog upFileDialog = HnUpFileDialog.newInstance();
        upFileDialog.show(context.getSupportFragmentManager(), "upFileDialog");
        upFileDialog.setOnVideoCallBack(new HnUpFileDialog.onVideoCallBack() {
            @Override
            public void onSuccess(String path) {
                if (path != null) {
                    //对文件名进行MD5加密   YYYYMMDD +md5
                    File videoFile = new File(path);
                    if (videoFile.exists()) {
                        requestToGetTokenVideo(videoFile, select);
                    }
                }
            }
        });
    }

    /**
     * 获取token
     *
     * @param
     * @param file   上传到文件
     * @param select
     */
    public void requestToGetToken(final File file, final String select) {
        if (listener != null) {
            listener.requesting();
        }
        HnUpLoadPhotoControl.getTenSign(file);
        HnUpLoadPhotoControl.setUpStutaListener(new HnUpLoadPhotoControl.UpStutaListener() {


            @Override
            public void uploadSuccess(String key, Object token) {
                if (listener != null) {
                    listener.requestSuccess("upload_pic_file", key, select);
                }
            }

            @Override
            public void uploadError(int code, String msg) {
                if (listener != null) {
                    listener.requestFail("upload_pic_file", code, "上传图片失败");
                }
            }
        });

    }

    private WaitProDialog dialog;

    public void requestToGetTokenVideo(final File file, final String select) {
//        if (listener != null) {
//            listener.requesting();
//        }
        HnUpLoadPhotoControl.getTenSign(file);
        HnUpLoadPhotoControl.setUpStutaListener(new HnUpLoadPhotoControl.UpStutaListener() {

//            @Override
//            public void uploadSuccess(String key, Object token, int type) {
//
//            }
//
//            @Override
//            public void uploadProgress(int progress) {
//                if (dialog == null) {
//                    dialog = WaitProDialog.newInstance(context);
//                    dialog.show();
//                } else {
//                    if (!dialog.isShowing()) dialog.isShowing();
//                }
//                dialog.setPrecent(progress);
//            }

            @Override
            public void uploadSuccess(String key, Object token) {
                if (dialog != null) dialog.dismiss();
                if (listener != null) {
                    listener.requestSuccess("upload_video_file", key, select);
                }
            }

            @Override
            public void uploadError(int code, String msg) {
                if (dialog != null) dialog.dismiss();
                if (listener != null) {
                    listener.requestFail("upload_video_file", code, "上传视频失败");
                }
            }
        });

    }


}
