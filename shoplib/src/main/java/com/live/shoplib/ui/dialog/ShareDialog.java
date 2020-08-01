package com.live.shoplib.ui.dialog;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;


import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

import com.hn.library.HnBaseApplication;
import com.hn.library.global.HnUrl;
import com.hn.library.http.BaseResponseModel;
import com.hn.library.http.HnHttpUtils;
import com.hn.library.http.HnResponseHandler;
import com.hn.library.utils.HnPrefUtils;
import com.hn.library.utils.HnToastUtils;
import com.hn.library.utils.HnUiUtils;
import com.live.shoplib.R;
import com.loopj.android.http.RequestParams;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;


/**
 * 分享统一处理
 * 作者：Mr.X
 * 时间：10:20
 */
@SuppressLint("ValidFragment")
public class ShareDialog extends DialogFragment implements View.OnClickListener {
    private UMShareAPI mShareAPI = null;
    private ShareAction mShareAction = null;
    private Activity activity;
    public Type type = Type.Goods;
    private String img;
    private String url;
    private FragmentManager manager;
    private String commTitle = "我在点E点，播你所想，购你所爱";

    public enum Type {
        Goods, Store, Live, Back
    }

    @SuppressLint("ValidFragment")
    public ShareDialog(Type type, FragmentManager manager) {
        this.type = type;
        this.manager = manager;
    }

    public void setActivityResult(int requestCode, int resultCode, Intent data) {
        mShareAPI.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = getActivity();
        mShareAPI = UMShareAPI.get(activity);
        mShareAction = new ShareAction(activity);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View view = View.inflate(activity, R.layout.dialog_live_share, null);
        view.findViewById(R.id.mTvQQ).setOnClickListener(this);
        view.findViewById(R.id.mTvWx).setOnClickListener(this);
        view.findViewById(R.id.mTvSina).setOnClickListener(this);
        view.findViewById(R.id.mTvFriend).setOnClickListener(this);


        Dialog dialog = new Dialog(activity, R.style.PXDialog);
        dialog.setContentView(view);
        Window window = dialog.getWindow();
        window.setGravity(Gravity.BOTTOM);
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = (activity.getWindowManager().getDefaultDisplay().getWidth());
        window.setAttributes(params);
        return dialog;
    }


    @Override
    public void onClick(View v) {

        if(true) {
            HnToastUtils.showToastShort("演示平台暂未开通此功能");
            return;
        }

        SHARE_MEDIA platform = null;
        if (v.getId() == R.id.mTvQQ) {
            platform = SHARE_MEDIA.QQ;
        } else if (v.getId() == R.id.mTvSina) {
            platform = SHARE_MEDIA.SINA;
        } else if (v.getId() == R.id.mTvWx) {
            platform = SHARE_MEDIA.WEIXIN;
        } else if (v.getId() == R.id.mTvFriend) {
            platform = SHARE_MEDIA.WEIXIN_CIRCLE;
        }

        switch (type) {
            case Goods:
                toShare(platform, commTitle, "这个商品真不错，快来瞅瞅", url, img);
                break;
            case Store:
                toShare(platform, commTitle, "【" + userName + "】分享给你一个好店铺", url, img);
                break;
            case Back:
                toShare(platform, commTitle, "【" + anchorName + "】直播了一场热闹的直播【" + liveTitle + "】,速来观看...", url, img);
                break;
            case Live:
                toShare(platform, commTitle, "【" + anchorName + "】正在点E点间【" + anchorId + "】,快来围观吧...", url, img);
                break;
        }

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
        if (!TextUtils.isEmpty(content)) mShareAction.withText(content);
        //加入图片
        if (TextUtils.isEmpty(img))
            img = HnPrefUtils.getString("def_img", "");
        else img = HnUrl.setImageUrl(img);
        if (TextUtils.isEmpty(img)) img = HnUrl.DEFRLT_IMG;

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
            if (type == Type.Live) shareSuccess(anchorId);
        }

        @Override
        public void onCancel(SHARE_MEDIA platform) {

        }
    };

    public ShareDialog show() {
        this.show(manager, "" + type.name());
        return this;
    }


    /*############################################################################################*/

    private String goodsName;
    private String userName;
    private String anchorName;
    private String anchorId;
    private String liveTitle;

    public ShareDialog setGoodsShare(String goodsName, String img, String url) {
        this.goodsName = goodsName;
        this.img = img;
        this.url = url;
        return this;
    }

    public ShareDialog setStoreShare(String userName, String img, String url) {
        this.userName = userName;
        this.img = img;
        this.url = url;
        return this;
    }

    public ShareDialog setLiveShare(String anchorName, String anchorId, String liveTitle, String img, String url) {
        this.anchorName = anchorName;
        this.anchorId = anchorId;
        this.liveTitle = liveTitle;
        this.img = img;
        this.url = url;
        return this;
    }

    /*############################################################################################*/

    private void shareSuccess(String mAnchorId) {
        RequestParams params = new RequestParams();
        params.put("anchor_user_id", mAnchorId);
        HnHttpUtils.postRequest("/live/room/addShareLog", params, "分享成功记录", new HnResponseHandler<BaseResponseModel>(BaseResponseModel.class) {
            @Override
            public void hnSuccess(String response) {
            }

            @Override
            public void hnErr(int errCode, String msg) {
            }
        });
    }


}
