package com.hotniao.live.activity.bindPhone;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.hn.library.base.BaseActivity;
import com.hn.library.utils.HnRegexUtils;
import com.hn.library.utils.HnToastUtils;
import com.hn.library.view.HnEditText;
import com.hotniao.live.R;
import com.hotniao.live.utils.HnUiUtils;
import com.hotniao.live.utils.HnUserUtil;
import com.hotniao.live.widget.HnButtonTextWatcher;


import butterknife.BindView;
import butterknife.OnClick;

/**
 * Copyright (C) 2016,深圳市红鸟网络科技股份有限公司 All rights reserved.
 * 类描述：第一次绑定手机号
 * 创建人：刘勇
 * 创建时间：2017/2/22 16:22
 * 修改人：刘勇
 * 修改时间：2017/2/22 16:34
 * 修改备注：
 * Version:  1.0.0
 */

public class HnFirstBindPhoneActivity extends BaseActivity {
    @BindView(R.id.mEtPhone)
    HnEditText mEtPhone;
    @BindView(R.id.mEtPwd)
    HnEditText mEtPwd;
    @BindView(R.id.mIvEye)
    ImageView mIvEye;
    @BindView(R.id.mEtBind)
    TextView mTvBindView;

    private EditText[] mEts;
    private HnButtonTextWatcher mWatcher;
    private boolean isVisiable = true;
    private boolean isMain = false;


    @Override
    public int getContentViewId() {
        return R.layout.activity_first_bind_phone;
    }

    @Override
    public void onCreateNew(Bundle savedInstanceState) {
        setShowBack(true);
        setTitle(R.string.bind_phone);
        isMain = getIntent().getBooleanExtra("main", false);
        setListener();
    }

    @Override
    public void getInitData() {

    }

    /**
     * 对控件设置监听  当用户输入数据时，才可提交
     */
    private void setListener() {
        mEts = new EditText[]{mEtPhone, mEtPwd};
        mWatcher = new HnButtonTextWatcher(mTvBindView, mEts);
        mEtPhone.addTextChangedListener(mWatcher);
        mEtPwd.addTextChangedListener(mWatcher);
    }


    @OnClick({ R.id.mIvEye, R.id.mEtBind})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.mIvEye:
                HnUserUtil.switchPwdisVis(mEtPwd, mIvEye, isVisiable);
                isVisiable = !isVisiable;
                break;
            case R.id.mEtBind:
                if (TextUtils.isEmpty(mEtPhone.getText().toString()) || !HnRegexUtils.isMobileExact(mEtPhone.getText().toString())) {
                    HnToastUtils.showToastShort(HnUiUtils.getString(R.string.phone_account));
                    return;
                }
                if (TextUtils.isEmpty(mEtPwd.getText().toString())) {
                    HnToastUtils.showToastShort(HnUiUtils.getString(R.string.please_input_pwd));
                    return;
                }
                HnBindPhoneSecondActivity.luncher(this,mEtPhone.getText().toString(),mEtPwd.getText().toString());

                break;
        }
    }

}
