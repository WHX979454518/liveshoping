package com.hotniao.live.utils;

import android.content.ClipboardManager;
import android.content.Context;
import android.hardware.Camera;
import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.provider.Settings;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.hn.library.utils.HnLogUtils;
import com.hn.library.utils.HnToastUtils;
import com.hotniao.live.HnApplication;
import com.hotniao.live.R;
import com.hotniao.live.widget.HnNetDialog;

/**
 * Copyright (C) 2017,深圳市红鸟网络科技股份有限公司 All rights reserved.
 * 项目名称：红鸟电商
 * 类描述：用户信息Util
 * 创建人：阳石柏
 * 创建时间：2017/8/10 17:18
 * 修改人：阳石柏
 * 修改时间：2017/8/10 17:18
 * 修改备注：
 * Version:  1.0.0
 */

public class HnUserUtil {

    // 音频获取源
    public static int audioSource = MediaRecorder.AudioSource.MIC;
    // 设置音频采样率，44100是目前的标准，但是某些设备仍然支持22050，16000，11025
    public static int sampleRateInHz = 44100;
    // 设置音频的录制的声道CHANNEL_IN_STEREO为双声道，CHANNEL_CONFIGURATION_MONO为单声道
    public static int channelConfig = AudioFormat.CHANNEL_IN_STEREO;
    // 音频数据格式:PCM 16位每个样本。保证设备支持。PCM 8位每个样本。不一定能得到设备支持。
    public static int audioFormat = AudioFormat.ENCODING_PCM_16BIT;
    // 缓冲区字节大小
    public static int bufferSizeInBytes = 0;

    /**
     * 密码是否可见
     *
     * @param editText
     * @param imageView
     * @param isVisiable
     */
    public static void switchPwdisVis(EditText editText, ImageView imageView, boolean isVisiable) {

        String trim = editText.getText().toString().trim();

        editText.setTransformationMethod(isVisiable ? HideReturnsTransformationMethod.getInstance() : PasswordTransformationMethod.getInstance());
        imageView.setImageResource(isVisiable ? R.drawable.eye_on : R.drawable.eye_off);
        editText.setSelection(trim.length());
    }


    public static void setSexImg(String sex, ImageView ivSex) {

        if (!TextUtils.isEmpty(sex)) {
            if (HnUiUtils.getString(R.string.woman).equals(sex)) {
                ivSex.setVisibility(View.VISIBLE);
                ivSex.setImageResource(R.mipmap.female);
            } else if (HnUiUtils.getString(R.string.male).equals(sex)) {
                ivSex.setVisibility(View.VISIBLE);
                ivSex.setImageResource(R.mipmap.male);
            } else if (HnUiUtils.getString(R.string.edit_secrecy).equalsIgnoreCase(sex)) {
                ivSex.setVisibility(View.GONE);
            }
        }
    }




    /**
     * 获取手机唯一标识
     *
     * @return
     */
    public static String getUniqueid() {
        String uniqueid = Settings.Secure.getString(HnApplication.getContext().getContentResolver(), Settings.Secure.ANDROID_ID);
        return uniqueid;
    }

    /**
     * 可设定[确定]和[取消]内容及颜色的交互对话框
     */
    public static HnNetDialog netDialog(Context context, String ok, String cancel, int okColorId, int cancelColorId, String title, String description, View.OnClickListener listener) {
        return HnNetDialog.builder(context, R.style.PXDialog)
                .isCanceledOnTouchOutside(true)
                .setView(R.layout.live_dialog_net_layout)
                .setOKText(ok)
                .setCancelText(cancel)
                .okBtnTxtColor(okColorId)
                .cancelBtnTxtColor(cancelColorId)
                .addListener(listener)
                .setTitle(title)
                .setDescription(description);
    }



    /**
     * 判断摄像头是否可用
     * 主要针对6.0 之前的版本，现在主要是依靠try...catch... 报错信息，感觉不太好，
     * 以后有更好的方法的话可适当替换
     */
    public static boolean isCameraCanUse() {
        boolean canUse = true;
        Camera mCamera = null;
        try {
            mCamera = Camera.open();
            // setParameters 是针对魅族MX5 做的。MX5 通过Camera.open() 拿到的Camera
            // 对象不为null
            Camera.Parameters mParameters = mCamera.getParameters();
            mCamera.setParameters(mParameters);

        } catch (Exception e) {
            canUse = false;
        }
        if (mCamera != null) {
            mCamera.release();
        }
        return canUse;
    }

    /**
     * 判断是否有录音权限
     */
    public static boolean isAudioPermission() {

        bufferSizeInBytes = 0;
        bufferSizeInBytes = AudioRecord.getMinBufferSize(sampleRateInHz,
                channelConfig, audioFormat);
        AudioRecord audioRecord = new AudioRecord(audioSource, sampleRateInHz,
                channelConfig, audioFormat, bufferSizeInBytes);
        //开始录制音频
        try {
            // 防止某些手机崩溃，例如联想
            audioRecord.startRecording();
            HnLogUtils.d("mainActivity", "try");

        } catch (IllegalStateException e) {
            e.printStackTrace();
            HnLogUtils.d("mainActivity", "catch");
        }

        /**
         * 根据开始录音判断是否有录音权限
         */
        int recordingState = audioRecord.getRecordingState();
        HnLogUtils.d("mainActivity", "recordingState为:" + recordingState);
        if (recordingState != AudioRecord.RECORDSTATE_RECORDING) {
            HnLogUtils.d("mainActivity", "不等于ing返回false");
            return false;
        }

        HnLogUtils.d("mainActivity", "返回true");
        audioRecord.stop();
        audioRecord.release();
        audioRecord = null;

        return true;
    }

    /**
     * 复制
     *
     * @param content
     * @param context
     */
    public static void copy(String content, Context context) {
        ClipboardManager cmb = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        cmb.setText(content.trim());
        HnToastUtils.showToastShort("已成功复制到剪贴板");
    }
}
