package com.hotniao.live.activity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.hn.library.base.BaseActivity;
import com.hn.library.global.HnUrl;
import com.hn.library.global.NetConstant;
import com.hn.library.http.BaseResponseModel;
import com.hn.library.http.HnHttpUtils;
import com.hn.library.http.HnResponseHandler;
import com.hn.library.utils.HnPrefUtils;
import com.hn.library.utils.HnToastUtils;
import com.hn.library.utils.HnUtils;
import com.hn.library.view.FrescoImageView;
import com.hotniao.live.R;
import com.hotniao.live.model.PlayBackModel;
import com.hotniao.livelibrary.biz.CareEvent;
import com.hotniao.livelibrary.biz.audience.HnAudienceViewBiz;
import com.hotniao.livelibrary.config.HnLiveUrl;
import com.hotniao.livelibrary.control.HnUserControl;
import com.live.shoplib.ShopDialogFacade;
import com.live.shoplib.bean.StoreDetailsModel;
import com.live.shoplib.ui.dialog.ShareDialog;
import com.loopj.android.http.RequestParams;
import com.tencent.rtmp.ITXLivePlayListener;
import com.tencent.rtmp.TXLiveConstants;
import com.tencent.rtmp.TXLivePlayConfig;
import com.tencent.rtmp.TXLivePlayer;
import com.tencent.rtmp.ui.TXCloudVideoView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.Locale;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Copyright (C) 2016,深圳市红鸟网络科技股份有限公司 All rights reserved.
 * 类描述：播放回放视频
 * 创建人：刘勇
 * 创建时间：2017/2/22 16:22
 * 修改人：刘勇
 * 修改时间：2017/2/22 16:34
 * 修改备注：
 * Version:  1.0.0
 */
@Route(path = "/app/HnPlayBackVideoActivity")
public class HnPlayBackVideoActivity extends BaseActivity implements ITXLivePlayListener {
    @BindView(R.id.mVideoView)
    TXCloudVideoView mVideoView;
    @BindView(R.id.mIvPlay)
    ImageView mIvPlay;
    @BindView(R.id.mTvTime)
    TextView mTvTime;
    @BindView(R.id.mSeekbar)
    SeekBar mSeekBar;
    @BindView(R.id.mTvTime2)
    TextView mTvTime2;
    @BindView(R.id.fiv_header)
    FrescoImageView fivHeader;
    @BindView(R.id.iv_vip)
    ImageView ivVip;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_online)
    TextView tvOnline;
    @BindView(R.id.ll_anc_info)
    LinearLayout llAncInfo;
    @BindView(R.id.tv_follow)
    TextView tvFollow;
    @BindView(R.id.rl_info)
    RelativeLayout rlInfo;
    @BindView(R.id.recy_online)
    RecyclerView recyOnline;
    @BindView(R.id.tv_close)
    ImageView tvClose;
    @BindView(R.id.rl_top)
    RelativeLayout rlTop;
    @BindView(R.id.tv_coin)
    TextView tvCoin;
    @BindView(R.id.mTvShopName)
    TextView mTvShopName;
    @BindView(R.id.mTvShopId)
    TextView mTvShopId;
    @BindView(R.id.mLLStoreMsg)
    LinearLayout mLLStoreMsg;
    @BindView(R.id.mTvNetSpeed)
    TextView mTvNetSpeed;
    @BindView(R.id.mIvGoods)
    FrescoImageView mIvGoods;
    @BindView(R.id.mTvMoney)
    TextView mTvMoney;
    @BindView(R.id.mRlRecommend)
    RelativeLayout mRlRecommend;
    @BindView(R.id.rl_top_con)
    RelativeLayout rlTopCon;
    @BindView(R.id.mIvShop)
    ImageView mIvShop;
    @BindView(R.id.mIvShare)
    ImageView mIvShare;
    //点播相关
    private long mTrackingTouchTS = 0;
    private boolean mStartSeek = false;
    private boolean mVideoPause = false;
    private boolean mPlaying = false;

    private String mPlayUrl;
    private int mUrlPlayType = TXLivePlayer.PLAY_TYPE_LIVE_FLV;

    private TXLivePlayer mTXLivePlayer;
    private TXLivePlayConfig mPlayConfig = new TXLivePlayConfig();
    private PlayBackModel.DEntity bean;
    private String mOwn_id;
    private String share;
    private HnAudienceViewBiz mHnAudienceViewBiz;

    /**
     * 跳转
     *
     * @param activity
     * @param uid      用户id
     * @param url      播放链接
     */
    public static void luncher(Activity activity, String uid, String url, String share) {
        activity.startActivity(new Intent(activity, HnPlayBackVideoActivity.class).putExtra("uid", uid).putExtra("url", url).putExtra("share", share));
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_play_back_video;
    }

    @Override
    public void onCreateNew(Bundle savedInstanceState) {
        setShowTitleBar(false);
        EventBus.getDefault().register(this);
        share = getIntent().getStringExtra("share");
        mTvNetSpeed.setVisibility(View.GONE);
        tvOnline.setVisibility(View.GONE);
        mHnAudienceViewBiz = new HnAudienceViewBiz();
        mOwn_id = HnPrefUtils.getString(NetConstant.User.UID, "");
        mPlayUrl = getIntent().getStringExtra("url");
        checkPlayUrl();
        startPlay();
        requestData(getIntent().getStringExtra("uid"));

    }

    private void requestData(final String anchor_user_id) {
        RequestParams param = new RequestParams();
        param.put("anchor_user_id", anchor_user_id);
        HnHttpUtils.postRequest(HnUrl.PLAY_BACK_DEL, param, "回放详情", new HnResponseHandler<PlayBackModel>(PlayBackModel.class) {
            @Override
            public void hnSuccess(String response) {
                if (isFinishing() || model.getD() == null) return;

                bean = model.getD();
                tvCoin.setText(bean.getAnchor().getUser_dot() + "  总订单:" + bean.getStore().getTotal_order() + "单");
                //店铺
                if (bean.getStore() != null && bean.getStore().getName() != null) {
                    mLLStoreMsg.setVisibility(View.VISIBLE);
                    mTvShopName.setText(bean.getStore().getName());
                    mTvShopId.setText("店铺ID:" + bean.getStore().getId());
                } else {
                    mLLStoreMsg.setVisibility(View.INVISIBLE);
                }
                //关注
                String isFollow = bean.getAnchor().getIs_follow();
                if (TextUtils.isEmpty(isFollow) || "N".equals(isFollow)) {//未关注
                    tvFollow.setVisibility(View.VISIBLE);
                } else {
                    tvFollow.setVisibility(View.GONE);
                }
                //头像
                String avator = bean.getAnchor().getUser_avatar();
                fivHeader.setImageURI(avator);
                tvName.setText(bean.getAnchor().getUser_nickname());
                String ownUid = HnPrefUtils.getString(NetConstant.User.UID, "");
                if (TextUtils.equals(anchor_user_id, ownUid)) {
                    mIvShop.setVisibility(View.GONE);
                }
            }

            @Override
            public void hnErr(int errCode, String msg) {
                HnToastUtils.showToastShort(msg);
            }
        });
    }

    @Override
    public void getInitData() {
        setLisener();
    }

    private void setLisener() {
        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean bFromUser) {
                if (mTvTime != null) {
                    mTvTime.setText(String.format(Locale.CHINA, "%02d:%02d:%02d", progress / 3600, (progress % 3600) / 60, (progress % 3600) % 60));
                    mTvTime2.setText(String.format(Locale.CHINA, "%02d:%02d:%02d", seekBar.getMax() / 3600, (seekBar.getMax() % 3600) / 60, (seekBar.getMax() % 3600) % 60));
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                mStartSeek = true;
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mTXLivePlayer.seek(seekBar.getProgress());
                mTrackingTouchTS = System.currentTimeMillis();
                mStartSeek = false;
            }
        });

    }

    @OnClick({R.id.mIvPlay, R.id.tv_close, R.id.tv_coin, R.id.fiv_header, R.id.tv_follow, R.id.mIvShare, R.id.mIvShop})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.mIvShare://分享
                if (bean == null) return;
                new ShareDialog(ShareDialog.Type.Back, getSupportFragmentManager())
                        .setLiveShare(bean.getAnchor().getUser_nickname(), bean.getAnchor().getUser_id(),
                                bean.getAnchor().getAnchor_live_title(), bean.getAnchor().getUser_avatar(),
                                share)
                        .show();
                break;
            case R.id.mIvShop://店铺
                if (bean == null) return;
                ShopDialogFacade.showShopListDialog(getFragmentManager(), bean.getAnchor().getUser_id());
                break;
            case R.id.tv_follow://关注
                if (bean == null) return;
                addFollow(bean.getUser().getUser_id(), bean.getAnchor().getUser_id());
                break;
            case R.id.fiv_header://主播头像
                if (bean == null) return;
                mHnAudienceViewBiz.showUserInfoDialog(bean.getAnchor().getUser_id(), mOwn_id, this, 2, bean.getAnchor().getUser_id(), "N");
                break;
            case R.id.tv_coin://金币
//                if (bean == null) return;asd
//                ARouter.getInstance().build("/app/HnFansContributeListActivity")
//                        .withString("userId", bean.getAnchor().getUser_id()).navigation();
                boolean isHaveShop = false;
                if (bean.getStore() != null && !TextUtils.isEmpty(bean.getStore().getId()))
                    isHaveShop = true;
                ARouter.getInstance().build("/app/HnFansContributeListActivity")
                        .withString("userId", bean.getAnchor().getUser_id()).withBoolean("isHaveShop", isHaveShop).navigation();
                break;
            case R.id.tv_close://关闭
                finish();
                break;
            case R.id.mIvPlay://开播
                if (mPlaying) {
                    if (mVideoPause) {
                        mTXLivePlayer.resume();
                        if (mIvPlay != null) {
                            mIvPlay.setBackgroundResource(R.mipmap.playback_begin_nor);
                        }
                    } else {
                        mTXLivePlayer.pause();
                        if (mIvPlay != null) {
                            mIvPlay.setBackgroundResource(R.mipmap.playback_stop_nor);
                        }
                    }
                    mVideoPause = !mVideoPause;
                } else {
                    if (mIvPlay != null) {
                        mIvPlay.setBackgroundResource(R.mipmap.playback_begin_nor);
                    }
                    startPlay();
                }
                break;
        }
    }


    public void addFollow(String uid, String mAnchorId) {
        RequestParams param = new RequestParams();
        param.put("user_id", mAnchorId);
        if (!TextUtils.isEmpty(mAnchorId))
            param.put("anchor_user_id", mAnchorId);
        HnHttpUtils.postRequest(HnLiveUrl.ADDFOLLOW, param, "添加关注", new HnResponseHandler<BaseResponseModel>(BaseResponseModel.class) {
            @Override
            public void hnSuccess(String response) {
                tvFollow.setVisibility(View.GONE);
                HnToastUtils.showToastShort("关注成功");
            }

            @Override
            public void hnErr(int errCode, String msg) {
                HnToastUtils.showToastShort(msg);
            }
        });
    }


    @Subscribe
    public void showEvent( CareEvent careEvent){
        tvFollow.setVisibility(careEvent.isShow()?View.VISIBLE:View.GONE);
    }


    @Override
    public void onPlayEvent(int event, Bundle param) {
        if (event == TXLiveConstants.PLAY_EVT_PLAY_BEGIN) {
            if (mIvPlay != null) {
                mIvPlay.setBackgroundResource(R.mipmap.playback_begin_nor);
            }
        } else if (event == TXLiveConstants.PLAY_EVT_PLAY_PROGRESS) {
            if (mStartSeek) {
                return;
            }
            int progress = param.getInt(TXLiveConstants.EVT_PLAY_PROGRESS);
            int duration = param.getInt(TXLiveConstants.EVT_PLAY_DURATION);
            long curTS = System.currentTimeMillis();
            // 避免滑动进度条松开的瞬间可能出现滑动条瞬间跳到上一个位置
            if (Math.abs(curTS - mTrackingTouchTS) < 500) {
                return;
            }
            mTrackingTouchTS = curTS;

            if (mSeekBar != null) {
                mSeekBar.setProgress(progress);
            }
            if (mTvTime != null) {
                mTvTime.setText(String.format(Locale.CHINA, "%02d:%02d:%02d",
                        progress / 3600, (progress % 3600) / 60, progress % 60));
                mTvTime2.setText(String.format(Locale.CHINA, "%02d:%02d:%02d",
                        duration / 3600, (duration % 3600) / 60, duration % 60));
            }

            if (mSeekBar != null) {
                mSeekBar.setMax(duration);
            }
        } else if (event == TXLiveConstants.PLAY_ERR_NET_DISCONNECT) {

        } else if (event == TXLiveConstants.PLAY_EVT_PLAY_END) {
            stopPlay(false);
            mVideoPause = false;
            if (mTvTime != null) {
                mTvTime.setText(String.format(Locale.CHINA, "%s", "00:00:00"));
                mTvTime2.setText(String.format(Locale.CHINA, "%s", "00:00:00"));
            }
            if (mSeekBar != null) {
                mSeekBar.setProgress(0);
            }
            if (mIvPlay != null) {
                mIvPlay.setBackgroundResource(R.mipmap.playback_stop_nor);
            }
        }
    }


    @Override
    public void onNetStatus(Bundle status) {
//        if (mVideoView != null) {
//            mVideoView.setLogText(status, null, 0);
//        }
//        if (status.getInt(TXLiveConstants.NET_STATUS_VIDEO_WIDTH) > status.getInt(TXLiveConstants.NET_STATUS_VIDEO_HEIGHT)) {
//            if (mTXLivePlayer != null)
//                mTXLivePlayer.setRenderRotation(TXLiveConstants.RENDER_ROTATION_LANDSCAPE);
//        } else if (mTXLivePlayer != null)
//            mTXLivePlayer.setRenderRotation(TXLiveConstants.RENDER_ROTATION_PORTRAIT);
    }

    private void startPlay() {
        if (mTXLivePlayer == null) {
            mTXLivePlayer = new TXLivePlayer(this);
        }
        mTXLivePlayer.setPlayerView(mVideoView);
        mTXLivePlayer.setRenderRotation(TXLiveConstants.RENDER_ROTATION_PORTRAIT);
        mTXLivePlayer.setRenderMode(TXLiveConstants.RENDER_MODE_ADJUST_RESOLUTION);
        mTXLivePlayer.setPlayListener(this);

        //极速模式
        mPlayConfig.setAutoAdjustCacheTime(true);
        mPlayConfig.setMinAutoAdjustCacheTime(1);
        mPlayConfig.setMaxAutoAdjustCacheTime(1);
        //设置播放器重连间隔. 秒
        mPlayConfig.setConnectRetryInterval(10);
        mTXLivePlayer.setConfig(mPlayConfig);

        mTXLivePlayer.startPlay(mPlayUrl, mUrlPlayType);
        mPlaying = true;

    }

    protected void stopPlay(boolean clearLastFrame) {
        if (mTXLivePlayer != null) {
            mTXLivePlayer.setPlayListener(null);
            mTXLivePlayer.stopPlay(clearLastFrame);

            mPlaying = false;
        }
    }

    private void checkPlayUrl() {
        if (TextUtils.isEmpty(mPlayUrl) || (!mPlayUrl.startsWith("http://") && !mPlayUrl.startsWith("https://") && !mPlayUrl.startsWith("rtmp://"))) {
//            Toast.makeText(getApplicationContext(), "播放地址不合法，目前仅支持rtmp,flv,hls,mp4播放方式!", Toast.LENGTH_SHORT).show();
        }

        if (mPlayUrl.startsWith("http://") || mPlayUrl.startsWith("https://")) {
            if (mPlayUrl.contains(".flv")) {
                mUrlPlayType = TXLivePlayer.PLAY_TYPE_VOD_FLV;
            } else if (mPlayUrl.contains(".m3u8")) {
                mUrlPlayType = TXLivePlayer.PLAY_TYPE_VOD_HLS;
            } else if (mPlayUrl.toLowerCase().contains(".mp4")) {
                mUrlPlayType = TXLivePlayer.PLAY_TYPE_VOD_MP4;
            } else {
                Toast.makeText(getApplicationContext(), "播放地址不合法，点播目前仅支持flv,hls,mp4播放方式!", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(getApplicationContext(), "播放地址不合法，点播目前仅支持flv,hls,mp4播放方式!", Toast.LENGTH_SHORT).show();
        }
    }


    boolean isAutoPause = false;

    @Override
    protected void onResume() {
        super.onResume();
        mVideoView.onResume();
        if (isAutoPause) {
            if (!mVideoPause) {
                mTXLivePlayer.resume();
            } else {
                startPlay();
            }
            isAutoPause = false;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mTXLivePlayer.isPlaying()) {
            isAutoPause = true;
            mVideoView.onPause();
            mTXLivePlayer.pause();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mVideoView != null) {
            mVideoView.onDestroy();
            mVideoView = null;
        }
        EventBus.getDefault().unregister(this);
        stopPlay(true);
        mTXLivePlayer = null;

    }
}
