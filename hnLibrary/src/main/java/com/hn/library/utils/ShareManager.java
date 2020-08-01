package com.hn.library.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

import com.hn.library.global.HnUrl;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;

/**
 * 作者：Mr.Xu
 * 时间：2018/1/8
 */
public class ShareManager {

    private UMShareAPI mShareAPI = null;
    private ShareAction mShareAction = null;

    public ShareManager(Activity activity) {
        mShareAPI = UMShareAPI.get(activity);
        mShareAction = new ShareAction(activity);
    }

    public void setActivityResult(int requestCode, int resultCode, Intent data) {
        mShareAPI.onActivityResult(requestCode, resultCode, data);
    }

    public void toShare(SHARE_MEDIA platform, String title, String content, String url, String img) {
        shareConfig(platform, title, content, url, HnUrl.setImageUrl(img));
    }

    private void shareConfig(SHARE_MEDIA platform, String title, String content, String url, String img) {
        //加入标题
        if (!TextUtils.isEmpty(title)) mShareAction.withTitle(title);
        //分享链接
        if (!TextUtils.isEmpty(url)) mShareAction.withTargetUrl(url);
        //加入内容
        if (!TextUtils.isEmpty(url)) mShareAction.withText(content);
        //加入图片
        if (!TextUtils.isEmpty(url))
            mShareAction.withMedia(new UMImage(HnUiUtils.getContext(), img));
        mShareAction.setPlatform(platform).setCallback(mUMShareListene).share();
    }

    private UMShareListener mUMShareListene = new UMShareListener() {
        @Override
        public void onResult(SHARE_MEDIA platform) {
            HnToastUtils.showToastShort("分享成功");
        }

        @Override
        public void onError(SHARE_MEDIA platform, Throwable throwable) {
            HnToastUtils.showToastShort("分享失败");
        }

        @Override
        public void onCancel(SHARE_MEDIA platform) {

        }
    };

}
