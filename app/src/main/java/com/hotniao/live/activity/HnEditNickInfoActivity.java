package com.hotniao.live.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.hn.library.base.BaseActivity;
import com.hn.library.http.BaseResponseModel;
import com.hn.library.http.HnHttpUtils;
import com.hn.library.http.HnResponseHandler;
import com.hn.library.utils.HnToastUtils;
import com.hotniao.live.HnApplication;
import com.hotniao.live.R;
import com.hn.library.global.HnUrl;
import com.loopj.android.http.RequestParams;

import butterknife.BindView;

/**
 * Copyright (C) 2016,深圳市红鸟网络科技股份有限公司 All rights reserved.
 * 类描述：编辑昵称或签名
 * 创建人：刘勇
 * 创建时间：2017/2/22 16:22
 * 修改人：刘勇
 * 修改时间：2017/2/22 16:34
 * 修改备注：
 * Version:  1.0.0
 */

public class HnEditNickInfoActivity extends BaseActivity {
    @BindView(R.id.mEdSign)
    EditText mEdSign;
    @BindView(R.id.mTvNum)
    TextView mTvNum;

    private int num = 0;


    public static void launcher(Activity activity, String hint, String content, String title) {

        Intent intent = new Intent(activity, HnEditNickInfoActivity.class);
        intent.putExtra("hint", hint);
        intent.putExtra("content", content);
        intent.putExtra("title", title);
        activity.startActivityForResult(intent,"1".equals(title)?1:2 );
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_edit_nick_info;
    }

    @Override
    public void onCreateNew(Bundle savedInstanceState) {
        setShowBack(true);
        setTitle("1".equals(getIntent().getStringExtra("title")) ? "昵称" : "个性签名");
        setShowSubTitle(true);
        mSubtitle.setText(R.string.save);
        mSubtitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveMessage(mEdSign.getText() + "");
            }
        });
    }

    @Override
    public void getInitData() {


        mEdSign.setHint(getIntent().getStringExtra("hint"));
        mEdSign.setText(getIntent().getStringExtra("content") + "");
        mEdSign.setSelection((getIntent().getStringExtra("content") + "").length());
        num = "1".equals(getIntent().getStringExtra("title")) ? 12 : 32;
        mTvNum.setText(mEdSign.getText().length()+"/" + num);
        mEdSign.addTextChangedListener(mTextWatcher);
    }

    /**
     * 创建人：Mr.Xu
     * 方法描述：字数监听
     */
    TextWatcher mTextWatcher = new TextWatcher() {
        private CharSequence temp;
        private int editStart;
        private int editEnd;

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            temp = s;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            //TextView.setText(s);//将输入的内容实时显示
        }

        @Override
        public void afterTextChanged(Editable s) {
            editStart = mEdSign.getSelectionStart();
            editEnd = mEdSign.getSelectionEnd();
            if (temp.length() <= num)
                mTvNum.setText(temp.length() + "/" + num);
            if (temp.length() > num) {
                s.delete(editStart - 1, editEnd);
                int tempSelection = editStart;
                mEdSign.setText(s);
                mEdSign.setSelection(tempSelection);
            }
        }
    };

    /**
     * 保存
     *
     * @param content 简介
     */
    public void saveMessage(final String content) {
        RequestParams param = new RequestParams();
        if("1".equals(getIntent().getStringExtra("title"))){
            param.put("user_nickname",content);
        }else {
            param.put("user_intro", content + "");
        }

        HnHttpUtils.postRequest(HnUrl.SAVE_USER_INFO, param, "SAVE_USER_INFO", new HnResponseHandler<BaseResponseModel>(this, BaseResponseModel.class) {
            @Override
            public void hnSuccess(String response) {
                if("1".equals(getIntent().getStringExtra("title"))){
                    HnApplication.mUserBean.setUser_nickname(content);
                }else {
                    HnApplication.mUserBean.setUser_intro(content);
                }
                finish();
            }

            @Override
            public void hnErr(int errCode, String msg) {
                HnToastUtils.showToastShort(msg + ",请稍后再试~");
            }
        });
    }


}
