package com.hotniao.live.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Display;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.EditorInfo;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.FragmentTransaction;

import com.hn.library.base.BaseActivity;
import com.hn.library.global.HnUrl;
import com.hn.library.http.HnHttpUtils;
import com.hn.library.http.HnResponseHandler;
import com.hn.library.utils.HnToastUtils;
import com.hn.library.view.FrescoImageView;
import com.hotniao.live.R;
import com.hotniao.live.eventbus.RefreshCommentEvent;
import com.hotniao.live.fragment.SecondLevelCommentFragment;
import com.hotniao.live.model.CommentModel;
import com.hotniao.live.model.LittleVideoFirstCommentModel;
import com.hotniao.live.model.SpikeTitleModel;
import com.live.shoplib.bean.OrderRefreshEvent;
import com.live.shoplib.bean.PayFinishEvent;
import com.live.shoplib.ui.frag.BaseScollFragment;
import com.loopj.android.http.RequestParams;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;


/**
 * 秒杀
 * 作者：Alan
 * 时间：2017/12/18
 */

public class SecondLevelCommentAct extends BaseActivity {

    private String commentId = "";
    private String head_url = "";
    private String user_nickname = "";
    private String create_time = "";
    private String content = "";
    private ImageView comment_religion_close;
    private TextView comment_content;
    private TextView comment_publisher_username;
    private TextView add_comment_edittext;
    private TextView comment_time;
    private FrescoImageView comment_publisher_head_img;

    private List<BaseScollFragment> mFragments = new ArrayList<>();

    public static void open(Context context, String commentId, String head_url,String user_nickname, String create_time, String content) {
        Intent intent = new Intent(context, SecondLevelCommentAct.class);
        intent.putExtra("commentId", commentId);
        intent.putExtra("head_url", head_url);
        intent.putExtra("user_nickname", user_nickname);
        intent.putExtra("create_time", create_time);
        intent.putExtra("content", content);

        context.startActivity(intent);
    }

    @Override
    public int getContentViewId() {
        return R.layout.show_comment_layout;
    }

    @Override
    protected void onResume() {
        super.onResume();
        EventBus.getDefault().post(new OrderRefreshEvent(-1));
    }


    @Override
    public void onCreateNew(Bundle savedInstanceState) {
        initWindowSize();
        setShowTitleBar(false);
        EventBus.getDefault().register(this);
        commentId = getIntent().getStringExtra("commentId");
        head_url = getIntent().getStringExtra("head_url");
        user_nickname = getIntent().getStringExtra("user_nickname");
        create_time = getIntent().getStringExtra("create_time");
        content = getIntent().getStringExtra("content");
        initViews();
    }

    private void initViews() {
        comment_publisher_head_img = findViewById(R.id.comment_publisher_head_img);
        comment_publisher_username = findViewById(R.id.comment_publisher_username);
        add_comment_edittext = findViewById(R.id.add_comment_edittext);
        comment_content = findViewById(R.id.comment_content);
        comment_time = findViewById(R.id.comment_time);
        comment_religion_close = findViewById(R.id.comment_religion_close);
        comment_religion_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        initContent();
        initCommentLayout();
        initCommentEditText();

    }


    private void initContent() {
        comment_publisher_head_img.setImageURI(HnUrl.setImageUri(head_url));
        comment_time.setText(create_time);
        comment_publisher_username.setText(user_nickname);
        comment_content.setText(content);
    }

    private void initWindowSize() {
        Display display = getWindowManager().getDefaultDisplay(); // 为获取屏幕宽、高
        Window window = getWindow();
        window.setGravity(Gravity.BOTTOM);
        ViewGroup.LayoutParams windowLayoutParams = window.getAttributes(); // 获取对话框当前的参数值
        windowLayoutParams.height = (int) (display.getHeight() * 0.8); // 高度设置为屏幕的0.8
    }


    private void initCommentLayout() {
        SecondLevelCommentFragment fragment;
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        try {
            fragment = SecondLevelCommentFragment.newInstance(commentId);
            transaction.addToBackStack(null);
            transaction.add(R.id.second_level_comment_layout, fragment);
        } catch (Exception e) {
            return;
        }
        transaction.show(fragment);
        transaction.commitAllowingStateLoss();
    }

    private void initCommentEditText() {
        add_comment_edittext.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if (actionId == EditorInfo.IME_ACTION_SEND) {
                    String commentContent = add_comment_edittext.getText().toString();
                    if (TextUtils.isEmpty(commentContent)) {
                        HnToastUtils.showToastShort("请填写评论内容");
                        return true;
                    }
                    sendComment(commentId,commentContent);
                    return false;
                }
                return false;
            }
        });
    }

    private void sendComment(final String id, String comment) {
        RequestParams param = new RequestParams();
        param.put("type", CommentModel.COMMENT_COMMENT_TYPE);
        param.put("id", id);
        param.put("comment", comment);

        HnHttpUtils.postRequest(HnUrl.LITTLE_VIDEO_COMMENT, param, TAG, new HnResponseHandler<CommentModel>(CommentModel.class) {
            @Override
            public void hnSuccess(String response) {
                EventBus.getDefault().post(new RefreshCommentEvent(id, LittleVideoFirstCommentModel.COMMENT_TYPE));
                add_comment_edittext.setText("");
                HnToastUtils.showToastShort("评论成功");
            }

            @Override
            public void hnErr(int errCode, String msg) {

            }
        });
    }


    @Override
    public void getInitData() {

    }



    @Override
    public void onBackPressed() {
        finish();
    }

    @Subscribe
    public void onFinishEvent(PayFinishEvent event) {
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
