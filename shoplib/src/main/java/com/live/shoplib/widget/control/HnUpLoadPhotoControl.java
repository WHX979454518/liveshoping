package com.live.shoplib.widget.control;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.hn.library.HnBaseApplication;
import com.hn.library.utils.HnDateUtils;
import com.hn.library.utils.HnFileUtils;
import com.tencent.cos.xml.CosXmlService;
import com.tencent.cos.xml.CosXmlServiceConfig;
import com.tencent.cos.xml.exception.CosXmlClientException;
import com.tencent.cos.xml.exception.CosXmlServiceException;
import com.tencent.cos.xml.listener.CosXmlProgressListener;
import com.tencent.cos.xml.listener.CosXmlResultListener;
import com.tencent.cos.xml.model.CosXmlRequest;
import com.tencent.cos.xml.model.CosXmlResult;
import com.tencent.cos.xml.model.object.PutObjectRequest;
import com.tencent.qcloud.core.auth.QCloudCredentialProvider;

import java.io.File;


/**
 * Copyright (C) 2016,深圳市红鸟网络科技股份有限公司 All rights reserved.
 * 类描述：上传图片
 * 创建人：刘勇
 * 创建时间：2017/2/22 16:22
 * 修改人：刘勇
 * 修改时间：2017/2/22 16:34
 * 修改备注：
 * Version:  1.0.0
 */

public class HnUpLoadPhotoControl {
    private static CosXmlService cosXmlService;
    public static final int UploadImage = 1;
    public static final int UploadVideo = 2;


    public static final String appID = "1259480780";//hn
    public static final String region = "ap-chengdu";
    public static final String secretID = "AKIDvc8VdbLvQhx1qQus0tbCakgMjvmQJK1U";
    public static final String secretKey = "N1RcXKr8ByazA5pJZZJsMdgYHl0zmNDn";
    public static final String bucket = "dianedian-1259480780";
    public static final String headUrl = "https://dianedian-1259480780.cos.ap-chengdu.myqcloud.com";

    /**
     * 初始化腾讯上传
     */
    public static void initTenceUpload() {
        String peristenceId = null;
        //创建COSClientConfig对象，根据需要修改默认的配置参数
        CosXmlServiceConfig serviceConfig = new CosXmlServiceConfig.Builder()
                .setAppidAndRegion(appID, region)
                .setDebuggable(true)
                .builder();
        cosXmlService = new CosXmlService(HnBaseApplication.getContext(), serviceConfig,
                (QCloudCredentialProvider) new LocalCredentialProvider(secretID, secretKey, 600));
    }

    public static void getTenSign( File file) {
        upload(file,UploadImage);
    }



    public static void upload(final File file, final int type) {
        if(cosXmlService==null){
            initTenceUpload();
        }
        final String path = file.getAbsolutePath();
        String cosPath = null;
        if (UploadImage == type)
            cosPath = "/image/" + HnDateUtils.today() + "/" + System.currentTimeMillis() + "." + path.substring(path.lastIndexOf(".") + 1);
        else if (UploadVideo == type)
            cosPath = "/video/" + HnDateUtils.today() + "/" + System.currentTimeMillis() + "." + path.substring(path.lastIndexOf(".") + 1);

        final String   finalCosPath=cosPath;
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucket, cosPath, path);
        putObjectRequest.setSign(600,null,null); //若不调用，则默认使用sdk中sign duration（60s）
        putObjectRequest.setProgressListener(new CosXmlProgressListener() {
            @Override
            public void onProgress(long progress, long max) {
                float result = (float) (progress * 100.0/max);
                final float finalProgress = result * 100;
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        if (mListener != null) {
//                            mListener.uploadProgress((int) finalProgress);
                        }
                    }
                });
            }
        });
        cosXmlService.putObjectAsync(putObjectRequest, new CosXmlResultListener() {
            @Override
            public void onSuccess(CosXmlRequest request, final CosXmlResult result) {
                Log.w("TEST", "success =" + result.accessUrl);
                if (UploadImage == type)
                    HnFileUtils.deleteFile(file);
                if (result != null) {
                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            if (mListener != null) {
                                if (UploadVideo == type) {
                                    if(!result.accessUrl.toLowerCase().startsWith("http")){
                                        mListener.uploadSuccess("http://"+result.accessUrl, null);
                                    }else {
                                        mListener.uploadSuccess(result.accessUrl, null);
                                    }
                                } else {
                                    String s = "";
                                    if (path.toLowerCase().endsWith("png") || path.toLowerCase().endsWith("jpg")) {
                                        s = headUrl + finalCosPath;
                                    }else {
                                        s=result.accessUrl;
                                    }
                                    mListener.uploadSuccess(s, "");
                                }
                            }
                        }
                    });
                }
            }

            @Override
            public void onFail(CosXmlRequest cosXmlRequest, CosXmlClientException clientException, CosXmlServiceException serviceException)  {

                String errorMsg = clientException != null ? clientException.toString() : serviceException.toString();
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        if (mListener != null) {
                            mListener.uploadError(-1, "上传失败");
                        }
                    }
                });
            }
        });

    }

    private static UpStutaListener mListener;


    public static void removeUpStutaListener() {
        mListener=null;
    }
    public static void setUpStutaListener(UpStutaListener listener) {
        mListener = listener;
    }

    public interface UpStutaListener {
        void uploadSuccess(String key, Object token);

        void uploadError(int code, String msg);
    }
}
