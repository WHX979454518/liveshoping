package com.hotniao.live.activity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.FragmentTransaction;

import com.bumptech.glide.Glide;
import com.hn.library.base.BaseActivity;
import com.hn.library.base.BaseRequestStateListener;
import com.hn.library.global.HnUrl;
import com.hn.library.http.HnHttpUtils;
import com.hn.library.http.HnResponseHandler;
import com.hn.library.utils.HnDateUtils;
import com.hn.library.utils.HnToastUtils;
import com.hn.library.view.FrescoImageView;
import com.hotniao.live.R;
import com.hotniao.live.biz.user.follow.HnFollowBiz;
import com.hotniao.live.eventbus.RefreshCommentEvent;
import com.hotniao.live.fragment.FirstLevelCommentFragment;
import com.hotniao.live.model.CommentModel;
import com.hotniao.live.model.LittleVideoDetailsModel;
import com.hotniao.live.model.LittleVideoFirstCommentModel;
import com.hotniao.live.model.PraiseModel;
import com.live.shoplib.bean.OrderRefreshEvent;
import com.live.shoplib.bean.PayFinishEvent;
import com.loopj.android.http.RequestParams;
import com.xiao.nicevideoplayer.NiceVideoPlayer;
import com.xiao.nicevideoplayer.NiceVideoPlayerController;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import fm.jiecao.jcvideoplayer_lib.JCUserAction;
import fm.jiecao.jcvideoplayer_lib.JCUserActionStandard;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;


/**
 * 秒杀
 * 作者：Alan
 * 时间：2017/12/18
 */

public class LittleVideoDetailsAct extends BaseActivity implements BaseRequestStateListener {

    //我的关注逻辑类，处理我的关注相关业务
    private HnFollowBiz mHnFollowBiz;
    FrescoImageView video_publisher_head_img;
    TextView video_publisher_username;
    TextView video_publish_time;
    TextView follow_video_publisher;
    EditText little_video_comment_edittext;
    TextView little_video_like_number;
    TextView little_video_comment_number;
    NiceVideoPlayer video_player;
    ImageView video_comment;
    ImageView video_like;
//    SuperPlayerView superPlayerView;

    JCVideoPlayerStandard jcVideoPlayerStandard;
    ImageView back;
    private int count;

    private String videoId = "";
    private LittleVideoDetailsModel.DEntity videoDetails;

    public static void open(Context context, String videoId) {
        Intent intent = new Intent(context, LittleVideoDetailsAct.class);
        intent.putExtra("videoId", videoId);
        context.startActivity(intent);
    }

    @Override
    public int getContentViewId() {
        return R.layout.act_little_video_details;
    }

    @Override
    protected void onResume() {
        super.onResume();
        EventBus.getDefault().post(new OrderRefreshEvent(-1));
    }


    @Override
    public void onCreateNew(Bundle savedInstanceState) {
        setShowTitleBar(false);
        EventBus.getDefault().register(this);
        videoId = getIntent().getStringExtra("videoId");
        initViews();

    }

    private void initViews() {
//        findViewById(R.id.mIvBack).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                finish();
//            }
//        });
        video_publisher_head_img = findViewById(R.id.video_publisher_head_img);
        video_publisher_username = findViewById(R.id.video_publisher_username);
        video_publish_time = findViewById(R.id.video_publish_time);
        little_video_comment_number = findViewById(R.id.little_video_comment_number);
        follow_video_publisher = findViewById(R.id.follow_video_publisher);
        little_video_comment_edittext = findViewById(R.id.little_video_comment_edittext);
        video_like = findViewById(R.id.video_like);

        little_video_like_number = findViewById(R.id.little_video_like_number);
        video_comment = findViewById(R.id.video_comment);

        video_player = findViewById(R.id.video_player);

        jcVideoPlayerStandard = (JCVideoPlayerStandard) findViewById(R.id.videoplayer);
        back = findViewById(R.id.icon_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
//        superPlayerView = findViewById(R.id.super_video_player);
        initPlayer();
        initSuperPlayer();
        initCommentLayout();
        initCommentEditText();
        initVideoLike();
        initFollow();
    }

    private void initFollow() {
        mHnFollowBiz = new HnFollowBiz(this);
        mHnFollowBiz.setRegisterListener(this);
        follow_video_publisher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (videoDetails.isFollow()) {
                    mHnFollowBiz.cancelFollow(videoDetails.getUser_id(), 0);

                } else if (!videoDetails.isFollow()) {
                    mHnFollowBiz.addFollow(videoDetails.getUser_id(), 0);
                }
            }
        });

    }

    private void initVideoLike() {
        video_like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                praiseOrCancelPraise(videoDetails.getHas_praise() != 1, PraiseModel.VIDEO_TYPE, videoDetails.getVideos_id());
            }
        });
    }

    private void initCommentLayout() {
        FirstLevelCommentFragment fragment;
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        try {
            fragment = FirstLevelCommentFragment.newInstance(videoId, LittleVideoFirstCommentModel.VIDEO_TYPE);
            transaction.addToBackStack(null);
            transaction.add(R.id.show_comment_layout, fragment);
        } catch (Exception e) {
            return;
        }
        transaction.show(fragment);
        transaction.commitAllowingStateLoss();
    }

    private void initCommentEditText() {
        little_video_comment_edittext.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if (actionId == EditorInfo.IME_ACTION_SEND) {
                    String commentContent = little_video_comment_edittext.getText().toString();
                    if (TextUtils.isEmpty(commentContent)) {
                        HnToastUtils.showToastShort("请填写评论内容");
                        return true;
                    }
                    sendComment(videoDetails.getVideos_id(),commentContent);
                    return false;
                }
                return false;
            }
        });

    }

    private void sendComment(String id, String comment) {
        RequestParams param = new RequestParams();
        param.put("type", CommentModel.COMMENT_VIDEO_TYPE);
        param.put("id", id);
        param.put("comment", comment);

        HnHttpUtils.postRequest(HnUrl.LITTLE_VIDEO_COMMENT, param, TAG, new HnResponseHandler<CommentModel>(CommentModel.class) {
            @Override
            public void hnSuccess(String response) {
                EventBus.getDefault().post(new RefreshCommentEvent(videoDetails.getVideos_id(), LittleVideoFirstCommentModel.VIDEO_TYPE));
                little_video_comment_edittext.setText("");
                HnToastUtils.showToastShort("评论成功");
            }

            @Override
            public void hnErr(int errCode, String msg) {

            }
        });
    }

    private void initPlayer() {
        NiceVideoPlayerController controller = new NiceVideoPlayerController(this);
        controller.setTitle("");
        controller.setLeftBackClickOnTinyWindow(new NiceVideoPlayerController.LeftBackClickOnTinyWindow() {
            @Override
            public void onClick() {
                finish();
            }
        });
        video_player.setController(controller);
        video_player.setVideoMode(NiceVideoPlayer.CENTER_MODE);
    }

    private void initSuperPlayer() {
//        superPlayerView = findViewById(R.id.super_video_player);
    }

    private void praiseOrCancelPraise(final boolean isPraise, final String type, String id) {
        RequestParams params = new RequestParams();
        params.put("type", type);
        params.put("id", id);
        HnHttpUtils.postRequest(isPraise ? HnUrl.LITTLE_VIDEO_PRAISE : HnUrl.LITTLE_VIDEO_CANCEL_PRAISE, params, "取消点赞短视频或者文章", new HnResponseHandler<PraiseModel>(PraiseModel.class) {
            @Override
            public void hnSuccess(String response) {
                changePraiseData(isPraise);
                refreshVideoDetailsUI();
                HnToastUtils.showToastShort(isPraise ? "点赞成功" : "取消点赞成功");
            }

            @Override
            public void hnErr(int errCode, String msg) {
            }
        });
    }

    private void changePraiseData(boolean isPraise) {
        videoDetails.setHas_praise(isPraise ? 1 : 0);
        videoDetails.setPraise_num(isPraise ? (Integer.valueOf(videoDetails.getPraise_num()) + 1) + "" : (Integer.valueOf(videoDetails.getPraise_num()) - 1) + "");
    }

//    private void playSuperVideo(String coverUrl, String videoUrl) {
//        if (TextUtils.isEmpty(videoUrl) || TextUtils.isEmpty(coverUrl)) return;
//        SuperPlayerModel model = new SuperPlayerModel();
//
//        model.videoURL = videoUrl;
//
//        superPlayerView.playWithMode(model);
//    }

    private void playVideo(String coverUrl, String videoUrl) {
        if (TextUtils.isEmpty(videoUrl) || TextUtils.isEmpty(coverUrl)) return;
        video_player.getmController().setImage(coverUrl);
        video_player.setUp(videoUrl, null);
        video_player.start();
    }

    @Override
    public void getInitData() {
        refreshVideoDetails(true);
    }


    public void refreshVideoDetails(final boolean isRefreshVideo) {
        RequestParams params = new RequestParams();
        params.put("videos_id", videoId);

        HnHttpUtils.postRequest(HnUrl.LITTLE_VIDEO_DETAILS, params, TAG, new HnResponseHandler<LittleVideoDetailsModel>(LittleVideoDetailsModel.class) {
            @Override
            public void hnSuccess(String response) {
                if (model.getD() == null) {
                    return;
                }
                videoDetails = model.getD();
                refreshVideoDetailsUI();
                if (isRefreshVideo) {
//                    playVideo(videoDetails.getFace(), videoDetails.getUrl());
                    jcVideoPlayerStandard.setUp(videoDetails.getUrl()
                            , JCVideoPlayerStandard.SCREEN_LAYOUT_NORMAL, "");
                    final ImageView imageView = jcVideoPlayerStandard.thumbImageView;
                    imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                    Glide.with(LittleVideoDetailsAct.this)
                            .load(videoDetails.getFace())
                            .into(imageView);
                    jcVideoPlayerStandard.setJcUserAction(new MyUserActionStandard());
                }
            }

            @Override
            public void hnErr(int errCode, String msg) {
            }
        });
    }

    private void refreshVideoDetailsUI() {
        video_publisher_head_img.setImageURI(Uri.parse(videoDetails.getUser_avatar()));
        video_publisher_username.setText(videoDetails.getUser_nickname());
        video_publish_time.setText(HnDateUtils.getMouthAndDayByTimeStamp(Long.valueOf(videoDetails.getCreate_time())));
        little_video_like_number.setText(videoDetails.getPraise_num());
        little_video_comment_number.setText(videoDetails.getComment_num());
        video_like.setImageDrawable(getResources().getDrawable(videoDetails.getHas_praise() == 1 ? R.drawable.little_video_like : R.drawable.little_video_unlike));

        follow_video_publisher.setBackground(getResources().getDrawable(videoDetails.getHas_follow() == 1 ? R.drawable.little_video_followed_bkg : R.drawable.little_video_unfollow_bkg));
        follow_video_publisher.setTextColor(getResources().getColor(videoDetails.getHas_follow() == 1 ? R.color.white : R.color.main_color));
        follow_video_publisher.setText(videoDetails.getHas_follow() == 1 ? "已关注" : "关注");
    }

    @Override
    public void onBackPressed() {
        finish();
        if (JCVideoPlayer.backPress()) {
            return;
        }
        super.onBackPressed();
    }

    @Subscribe
    public void onFinishEvent(PayFinishEvent event) {
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        video_player.release();
        JCVideoPlayer.releaseAllVideos();
    }

    @Override
    public void requesting() {

    }

    @Override
    public void requestSuccess(String type, String response, Object obj) {
        refreshVideoDetails(false);
    }

    @Override
    public void requestFail(String type, int code, String msg) {
        HnToastUtils.showToastShort(msg);
    }


    @Override
    protected void onStop() {
        super.onStop();
      JCVideoPlayer.releaseAllVideos();
    }

    class MyUserActionStandard implements JCUserActionStandard {

        @Override
        public void onEvent(int type, String url, int screen, Object... objects) {
            switch (type) {
                case JCUserAction.ON_CLICK_PAUSE:
                    Log.i("USER_EVENT", "4");
                    break;
                case JCUserActionStandard.ON_CLICK_BLANK:
                    Log.i("USER_EVENT", "16");
                    count++;
                    if(count % 2 == 0){
                        back.setVisibility(View.GONE);
                    }else {
                        back.setVisibility(View.VISIBLE);
                    }
//                    Log.i("USER_EVENT", "ON_CLICK_BLANK" + " title is : " + (objects.length == 0 ? "" : objects[0]) + " url is : " + url + " screen is : " + screen);
                    break;
                default:
                    Log.i("USER_EVENT", "unknow");
                    break;
            }
        }
    }

}
