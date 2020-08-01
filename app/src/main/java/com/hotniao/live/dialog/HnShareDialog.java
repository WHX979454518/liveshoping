package com.hotniao.live.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.hn.library.utils.HnLogUtils;
import com.hn.library.utils.HnToastUtils;
import com.hotniao.live.R;
import com.hotniao.live.eventbus.HnSelectLiveCateEvent;
import com.hotniao.live.model.HnHomeLiveCateModel;
import com.hotniao.live.utils.HnUiUtils;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

/**
 * Copyright (C) 2016,深圳市红鸟网络科技股份有限公司 All rights reserved.
 * 类描述：分享
 * 创建人：刘勇
 * 创建时间：2017/2/22 16:22
 * 修改人：刘勇
 * 修改时间：2017/2/22 16:34
 * 修改备注：
 * Version:  1.0.0
 */

public class HnShareDialog extends DialogFragment implements View.OnClickListener {


    private Activity mActivity;
    private static HnShareDialog dialog;

    private SHARE_MEDIA platform = null;
    private static UMShareAPI mShareAPI = null;
    private static ShareAction mShareAction;
    private static String mContent,mImagUrl,mUrl;

    public static HnShareDialog newInstance(UMShareAPI shareAPI, ShareAction shareAction, String content, String logo, String url) {
        dialog = new HnShareDialog();
        mShareAPI = shareAPI;
        mShareAction = shareAction;
        mContent=content;
        mImagUrl=logo;
        mUrl=url;
        return dialog;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = getActivity();
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View view = View.inflate(mActivity, R.layout.dialog_share, null);
        view.findViewById(R.id.mTvQQ).setOnClickListener(this);
        view.findViewById(R.id.mTvWx).setOnClickListener(this);
        view.findViewById(R.id.mTvSina).setOnClickListener(this);


        Dialog dialog = new Dialog(mActivity, R.style.PXDialog);
        dialog.setContentView(view);
        Window window = dialog.getWindow();
        window.setGravity(Gravity.BOTTOM);
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = (mActivity.getWindowManager().getDefaultDisplay().getWidth());
//        params.height = (int) (mActivity.getWindowManager().getDefaultDisplay().getWidth() * 0.8);
        window.setAttributes(params);
        return dialog;
    }


    public HnShareDialog setItemShareListener(onShareDialogListener listener) {
        this.listener = listener;
        return dialog;
    }

    private onShareDialogListener listener;

    @Override
    public void onClick(View v) {

        if(true) {
            HnToastUtils.showToastShort("演示平台暂未开通此功能");
            return;
        }

        switch (v.getId()) {
            case R.id.mTvQQ:
                platform = SHARE_MEDIA.QQ;

                break;
            case R.id.mTvSina:
                platform = SHARE_MEDIA.SINA;
                break;
            case R.id.mTvWx:
                platform = SHARE_MEDIA.WEIXIN;
                break;
        }
        mShareAction.setPlatform(platform).withMedia(new UMImage(mActivity, mImagUrl)).withTargetUrl(mUrl).withText(mContent).withTitle(HnUiUtils.getString(R.string.app_name))
                .setCallback(umShareListener).share();
    }

    public interface onShareDialogListener {
        void sureClick();
    }

    private UMShareListener umShareListener = new UMShareListener() {
        @Override
        public void onResult(SHARE_MEDIA share_media) {
            dismiss();
        }

        @Override
        public void onError(SHARE_MEDIA share_media, Throwable throwable) {
            HnLogUtils.e("a");
            dismiss();
        }

        @Override
        public void onCancel(SHARE_MEDIA share_media) {
            HnLogUtils.e("a");
            dismiss();
        }
    };

}
