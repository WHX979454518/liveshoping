package com.live.shoplib.ui.dialog;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatDialogFragment;

import com.hn.library.http.HnHttpUtils;
import com.hn.library.http.HnUploadHandler;
import com.hn.library.http.UploadFileResponseModel;
import com.hn.library.picker.photo_picker.HnPhotoUtils;
import com.hn.library.utils.HnLogUtils;
import com.hn.library.utils.HnToastUtils;
import com.hn.library.utils.HnUtils;
import com.hn.library.view.WaitProDialog;
import com.live.shoplib.R;
import com.live.shoplib.ui.StoreEditAct;

import java.io.FileNotFoundException;


/**
 * Copyright (C) 2017,深圳市红鸟网络科技股份有限公司 All rights reserved.
 * 项目名称：
 * 类描述：上传头像
 * 创建人：阳石柏
 * 创建时间：2017/3/6 16:16
 * 修改人：阳石柏
 * 修改时间：2017/3/6 16:16
 * 修改备注：
 * Version:  1.0.0
 */
public class HnUpFileDialog extends AppCompatDialogFragment implements View.OnClickListener {

    private static HnUpFileDialog sDialog;

    private Activity mActivity;
    private Uri mTempPhotoUri;
    private Uri mCroppedPhotoUri;
    private float mWidth = 160.0f;
    private float mHeight = 160.0f;
    private static final int UP_VIDEO = 9998;
    private String TAG = "HnEditHeaderDialog";
    private WaitProDialog dialog;

    public static HnUpFileDialog newInstance() {
        Bundle args = new Bundle();
        sDialog = new HnUpFileDialog();
        sDialog.setArguments(args);
        return sDialog;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = getActivity();

        mTempPhotoUri = HnPhotoUtils.generateTempImageUri(mActivity);
        mCroppedPhotoUri = HnPhotoUtils.generateTempCroppedImageUri(mActivity);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        View view = View.inflate(mActivity, R.layout.dialog_up_video, null);

        view.findViewById(R.id.tv_cancel).setOnClickListener(this);
        view.findViewById(R.id.tv_video).setOnClickListener(this);

        Dialog dialog = new Dialog(mActivity, R.style.PXDialog);
        dialog.setContentView(view);
        dialog.setCanceledOnTouchOutside(true);
        Window alertWindow = dialog.getWindow();
        alertWindow.setGravity(Gravity.BOTTOM);

        WindowManager.LayoutParams params = alertWindow.getAttributes();
        params.width = mActivity.getWindowManager().getDefaultDisplay().getWidth();
        params.height = dp2px(mActivity, 100.0f);
        alertWindow.setAttributes(params);

        return dialog;
    }

    public int dp2px(Context context, float dpValue) {
        return (int) (dpValue * context.getResources().getDisplayMetrics().density + 0.5f);
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.tv_cancel) {
            dialogDismiss();
        } else if (i == R.id.tv_video) {
            Intent intentPick = new Intent(Intent.ACTION_GET_CONTENT);
            intentPick.setType("video/*");
            if (intentPick.resolveActivity(getActivity().getPackageManager()) != null) {
                startActivityForResult(intentPick, UP_VIDEO);
            }
        }
    }


    /**
     * dialog消失
     */
    private void dialogDismiss() {
        if (sDialog != null) {
            sDialog.dismiss();
            sDialog = null;
        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                // Cropped photo was returned
                case UP_VIDEO: {
                    Uri mTempPhotoUri = HnPhotoUtils.generateTempImageUri(getActivity());
                    Uri uri;
                    boolean isWritable = false;
                    if (data != null && data.getData() != null) {
                        uri = data.getData();
                        String path = HnUtils.getPath(getActivity(), uri);
                        Log.i("文件路径", "uri=" + data.getData());
                        onVideoCallBack.onSuccess(path);
                    } else {
                        uri = mTempPhotoUri;
                        isWritable = true;
                    }
                    final Uri toCrop;
                    if (isWritable) {
                        toCrop = uri;
                    } else {
                        toCrop = mTempPhotoUri;
                        try {
                            HnPhotoUtils.savePhotoFromUriToUri(getActivity(), uri,
                                    toCrop, false);
                        } catch (SecurityException e) {

                        }
                    }
                    break;
                }
            }
        } else if (resultCode == Activity.RESULT_CANCELED) {
            if (getDialog().isShowing()) {
                getDialog().dismiss();
            }
        }
    }

//
    public Bitmap getVideoThumbnail(String filePath) {
        Bitmap bitmap = null;
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        try {
            retriever.setDataSource(filePath);
            bitmap = retriever.getFrameAtTime();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (RuntimeException e) {
            e.printStackTrace();
        } finally {
            try {
                retriever.release();
            } catch (RuntimeException e) {
                e.printStackTrace();
            }
        }
        return bitmap;
    }

    /**
     * 对外提供图片设置接口
     */
    public interface onVideoCallBack {

        void onSuccess(String path);

    }

    private onVideoCallBack onVideoCallBack;

    public void setOnVideoCallBack(onVideoCallBack onVideoCallBack) {
        this.onVideoCallBack = onVideoCallBack;
    }
}
