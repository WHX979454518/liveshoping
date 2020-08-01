package com.hotniao.live.biz.user.userinfo;

import android.graphics.Bitmap;
import android.net.Uri;

import com.hn.library.base.BaseActivity;
import com.hn.library.base.BaseRequestStateListener;
import com.hn.library.http.BaseResponseModel;
import com.hn.library.http.HnHttpUtils;
import com.hn.library.http.HnResponseHandler;
import com.hn.library.picker.photo_picker.HnPhotoUtils;
import com.hn.library.utils.EncryptUtils;
import com.hn.library.utils.HnDateUtils;
import com.hn.library.utils.HnUtils;
import com.hotniao.live.model.OrderInforModel;
import com.hotniao.livelibrary.control.HnUserControl;
import com.hn.library.model.HnLoginModel;
import com.hn.library.global.HnUrl;
import com.hotniao.live.dialog.HnEditHeaderDialog;
import com.hotniao.livelibrary.control.HnUpLoadPhotoControl;
import com.loopj.android.http.RequestParams;
import com.tencent.openqq.protocol.imsdk.msg;

import java.io.File;

/**
 * Copyright (C) 2017,深圳市红鸟网络科技股份有限公司 All rights reserved.
 * 项目名称：youbo
 * 类描述：业务逻辑类，用于处理我的界面的网络请求以及业务逻辑
 * 创建人：mj
 * 创建时间：2017/9/5 14:05
 * 修改人：Administrator
 * 修改时间：2017/9/5 14:05
 * 修改备注：
 * Version:  1.0.0
 */
public class HnMineFragmentBiz {

    private String TAG = "HnMineFragmentBiz";
    private BaseActivity context;
    private BaseRequestStateListener listener;


    public HnMineFragmentBiz(BaseActivity context) {
        this.context = context;
    }

    public void setBaseRequestStateListener(BaseRequestStateListener listener) {
        this.listener = listener;
    }

    /**
     * 网络请求：用于获取用户信息
     */
    public void requestToUserInfo() {
        HnUserControl.getProfile(new HnUserControl.OnUserInfoListener() {
            @Override
            public void onSuccess(String uid, HnLoginModel model, String response) {
                if (listener != null) {
                    listener.requestSuccess("user_info", response, model);
                }
            }

            @Override
            public void onError(int errCode, String msg) {
                if (listener != null) {
                    listener.requestFail("user_info", errCode, msg);
                }
            }
        });
    }

    /**
     * 网络请求：用于获取商品信息
     */
    public void requestToOrderInfo() {
        RequestParams param = new RequestParams();
        HnHttpUtils.postRequest(HnUrl.ORDER_INFO, param, "订单消息", new HnResponseHandler<OrderInforModel>(context, OrderInforModel.class) {
            @Override
            public void hnSuccess(String response) {
                if (listener != null) {
                    listener.requestSuccess("order_info", response, model);
                }
            }

            @Override
            public void hnErr(int errCode, String msg) {
                if (listener != null) {
                    listener.requestFail("order_info", errCode, msg);
                }
            }
        });
    }


    /**
     * 网络请求：保存用户昵称
     *
     * @param nick
     */
    public void requestToSavaNick(final String nick) {
        RequestParams param = new RequestParams();
        param.put("user_nickname", nick);
        HnHttpUtils.postRequest(HnUrl.SAVE_USER_INFO, param, "编辑昵称", new HnResponseHandler<BaseResponseModel>(context, BaseResponseModel.class) {
            @Override
            public void hnSuccess(String response) {
                if (model.getC() == 0) {
                    if (listener != null) {
                        listener.requestSuccess("save_nick", response, nick);
                    }
                } else {
                    if (listener != null) {
                        listener.requestFail("save_nick", model.getC(), model.getM());
                    }
                }
            }

            @Override
            public void hnErr(int errCode, String msg) {
                if (listener != null) {
                    listener.requestFail("save_nick", errCode, msg);
                }
            }
        });
    }

    /**
     * 网络请求：保存用户签名
     *
     * @param
     */
    public void requestToSavaIntro(final String intro) {
        RequestParams param = new RequestParams();
        param.put("user_intro", intro);
        HnHttpUtils.postRequest(HnUrl.SAVE_USER_INFO, param, "编辑昵称", new HnResponseHandler<BaseResponseModel>(context, BaseResponseModel.class) {
            @Override
            public void hnSuccess(String response) {
                if (model.getC() == 0) {
                    if (listener != null) {
                        listener.requestSuccess("save_intro", response, intro);
                    }
                } else {
                    if (listener != null) {
                        listener.requestFail("save_sintro", model.getC(), model.getM());
                    }
                }
            }

            @Override
            public void hnErr(int errCode, String msg) {
                if (listener != null) {
                    listener.requestFail("save_sex", errCode, msg);
                }
            }
        });
    }


    /**
     * 网络请求：保存用户头像
     *
     * @param
     */
    public void requestToSavaHeader(final String avatar) {
        RequestParams param = new RequestParams();
        param.put("user_avatar", avatar);
        HnHttpUtils.postRequest(HnUrl.SAVE_USER_INFO, param, "编辑昵称", new HnResponseHandler<BaseResponseModel>(context, BaseResponseModel.class) {
            @Override
            public void hnSuccess(String response) {
                if (model.getC() == 0) {
                    if (listener != null) {
                        listener.requestSuccess("save_avator", response, avatar);
                    }
                } else {
                    if (listener != null) {
                        listener.requestFail("save_avator", model.getC(), model.getM());
                    }
                }
            }

            @Override
            public void hnErr(int errCode, String msg) {
                if (listener != null) {
                    listener.requestFail("save_avator", errCode, msg);
                }
            }
        });
    }


    /**
     * 更新头像
     */
    public void updateHeader() {
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
                    requestToSavaHeader(key + "");
                }
            }

            @Override
            public void uploadError(int code, String msg) {
                if (listener != null) {
                    listener.requestFail("upload_pic_file", 1, "上传图片失败");
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


}
