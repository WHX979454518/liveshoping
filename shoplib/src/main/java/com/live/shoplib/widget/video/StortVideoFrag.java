package com.live.shoplib.widget.video;

import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.hn.library.base.BaseFragment;
import com.hn.library.utils.HnFileUtils;
import com.hn.library.view.FrescoImageView;
import com.live.shoplib.R;
import com.tencent.rtmp.ITXLivePlayListener;
import com.tencent.rtmp.TXLiveConstants;
import com.tencent.rtmp.TXLivePlayConfig;
import com.tencent.rtmp.TXLivePlayer;
import com.tencent.rtmp.ui.TXCloudVideoView;

import java.util.Locale;

/**
 * 作者：Mr.Xu
 * 时间：2018/1/2
 */
public class StortVideoFrag extends BaseFragment implements ITXLivePlayListener {

    private TXCloudVideoView mVideoView;
    private TextView mTvTimeCur;
    private SeekBar mSeekBar;
    private TextView mTvTimeAll;
    private ImageView mIvBtn;
    private FrescoImageView mIvVideo;

    private String mPlayUrl;
    private String ico;

    private long mTrackingTouchTS = 0;
    private boolean mStartSeek = false;
    private boolean mVideoPause = false;
    private boolean mPlaying = false;

    private TXLivePlayer mTXLivePlayer;
    private TXLivePlayConfig mPlayConfig = new TXLivePlayConfig();

    private int mUrlPlayType = TXLivePlayer.PLAY_TYPE_VOD_MP4;

    public static Fragment newInstance(String url, String ico) {
        StortVideoFrag fragment = new StortVideoFrag();
        Bundle bundle = new Bundle();
        bundle.putString("url", url);
        bundle.putString("ico", ico);
        fragment.setArguments(bundle);
        return fragment;
    }


    @Override
    public int getContentViewId() {
        return R.layout.frag_store_video;
    }

    @Override
    public void initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        mVideoView = mRootView.findViewById(R.id.mVideoView);
        mTvTimeCur = mRootView.findViewById(R.id.mTvTimeCur);
        mSeekBar = mRootView.findViewById(R.id.mSeekBar);
        mTvTimeAll = mRootView.findViewById(R.id.mTvTimeAll);
        mIvBtn = mRootView.findViewById(R.id.mIvBtn);
        mIvVideo = mRootView.findViewById(R.id.mIvVideo);

        mPlayUrl = getArguments().getString("url");
//        if (TextUtils.isEmpty(mPlayUrl))
//            mPlayUrl = "http://200024424.vod.myqcloud.com/200024424_709ae516bdf811e6ad39991f76a4df69.f20.mp4";

        ico = getArguments().getString("ico");
        mIvVideo.setImageBitmap(HnFileUtils.createVideoThumbnail(mPlayUrl, 200, 200));
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isHidden()) {
            if (mTXLivePlayer != null) mTXLivePlayer.pause();
        }
    }

    @Override
    protected void initData() {
        checkPlayUrl();
        setLisener();
//        startPlay();
        mIvBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mIvVideo.setVisibility(View.GONE);
                if (mPlaying) {
                    if (mVideoPause) {
                        mTXLivePlayer.resume();
                        if (mIvBtn != null) {
                            mIvBtn.setImageResource(R.drawable.stop);
                            setGone();
                        }
                    } else {
                        mTXLivePlayer.pause();
                        if (mIvBtn != null) {
                            mIvBtn.setImageResource(R.drawable.play);
                        }
                    }
                    mVideoPause = !mVideoPause;
                } else {
                    if (mIvBtn != null) {
                        mIvBtn.setImageResource(R.drawable.stop);
                        setGone();
                    }
                    startPlay();
                }
            }
        });

        mVideoView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                mIvVideo.setVisibility(View.GONE);
                mIvBtn.setVisibility(View.VISIBLE);
                if (mPlaying) {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if (!mTXLivePlayer.isPlaying()) {
                                mIvBtn.setVisibility(View.GONE);
                            }
                        }
                    }, 2000);
                }
                return false;
            }
        });
    }

    private void setGone() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mIvBtn.setVisibility(View.GONE);
            }
        }, 2000);
    }

    @Override
    public void onPlayEvent(int event, Bundle param) {
        if(mIvVideo!=null)mIvVideo.setVisibility(View.GONE);
        if (event == TXLiveConstants.PLAY_EVT_PLAY_BEGIN) {
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
            if (mTvTimeCur != null) {
                mTvTimeCur.setText(String.format(Locale.CHINA, "%02d:%02d:%02d",
                        progress / 3600, (progress % 3600) / 60, progress % 60));
            }
            if (mTvTimeAll != null) {
                mTvTimeAll.setText(String.format(Locale.CHINA, "%02d:%02d:%02d",
                        duration / 3600, (duration % 3600) / 60, duration % 60));
            }
            if (mSeekBar != null) {
                mSeekBar.setMax(duration);
            }
        } else if (event == TXLiveConstants.PLAY_ERR_NET_DISCONNECT) {

        } else if (event == TXLiveConstants.PLAY_EVT_PLAY_END) {
            stopPlay(false);
            mVideoPause = false;
            if (mTvTimeCur != null) {
                mTvTimeCur.setText(String.format(Locale.CHINA, "%s", "00:00:00"));
            }
            if (mTvTimeAll != null) {
                mTvTimeAll.setText(String.format(Locale.CHINA, "%s", "00:00:00"));
            }
            if (mSeekBar != null) {
                mSeekBar.setProgress(0);
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


    private void setLisener() {
        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean bFromUser) {
                if (mTvTimeCur != null) {
                    mTvTimeCur.setText(String.format(Locale.CHINA, "%02d:%02d:%02d",
                            progress / 3600, (progress % 3600) / 60, (progress % 3600) % 60));
                }
                if (mTvTimeAll != null) {
                    mTvTimeAll.setText(String.format(Locale.CHINA, "%02d:%02d:%02d",
                            seekBar.getMax() / 3600, (seekBar.getMax() % 3600) / 60, (seekBar.getMax() % 3600) % 60));
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                mStartSeek = true;
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                if (mTXLivePlayer == null || mActivity == null) return;
                mTXLivePlayer.seek(seekBar.getProgress());
                mTrackingTouchTS = System.currentTimeMillis();
                mStartSeek = false;
            }
        });

    }

    private void startPlay() {
        if (mTXLivePlayer == null) {
            mTXLivePlayer = new TXLivePlayer(getContext());
        }
        mTXLivePlayer.setPlayerView(mVideoView);
        mTXLivePlayer.setRenderRotation(TXLiveConstants.RENDER_ROTATION_PORTRAIT);
        mTXLivePlayer.setRenderMode(TXLiveConstants.RENDER_MODE_FULL_FILL_SCREEN);
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
//            HnToastUtils.showToastShort("播放地址不合法，目前仅支持rtmp,flv,hls,mp4播放方式!");
        }
        if (mPlayUrl.startsWith("http://") || mPlayUrl.startsWith("https://")) {
            if (mPlayUrl.contains(".flv")) {
                mUrlPlayType = TXLivePlayer.PLAY_TYPE_VOD_FLV;
            } else if (mPlayUrl.contains(".m3u8")) {
                mUrlPlayType = TXLivePlayer.PLAY_TYPE_VOD_HLS;
            } else if (mPlayUrl.toLowerCase().contains(".mp4")) {
                mUrlPlayType = TXLivePlayer.PLAY_TYPE_VOD_MP4;
            } else {
//                HnToastUtils.showToastShort("播放地址不合法，目前仅支持rtmp,flv,hls,mp4播放方式!");
            }
        } else {
//            HnToastUtils.showToastShort("播放地址不合法，目前仅支持rtmp,flv,hls,mp4播放方式!");
        }
    }


}
