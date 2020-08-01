package com.hotniao.live.activity;

import android.os.Bundle;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.hn.library.base.BaseActivity;
import com.hotniao.live.R;
import com.hotniao.live.fragment.HnMsgFragment;

/**
 * Copyright (C) 2016,深圳市红鸟网络科技股份有限公司 All rights reserved.
 * 类描述：我的消息
 * 创建人：刘勇
 * 创建时间：2017/2/22 16:22
 * 修改人：刘勇
 * 修改时间：2017/2/22 16:34
 * 修改备注：
 * Version:  1.0.0
 */
@Route(path = "/app/HnMyMessageActivity")
public class HnMyMessageActivity extends BaseActivity {

    private HnMsgFragment fragment;

    @Override
    public int getContentViewId() {
        return R.layout.activity_message;
    }

    @Override
    public void onCreateNew(Bundle savedInstanceState) {
        setShowBack(true);
        setTitle(R.string.main_msg);
        setShowSubTitle(true);
        mSubtitle.setText(R.string.letter_ignore);
        fragment = HnMsgFragment.newInstance();
        mSubtitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (fragment != null)
                    fragment.ignore();
            }
        });

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.mFrame, fragment)
                .commitAllowingStateLoss();

    }

    @Override
    public void getInitData() {

    }
}
