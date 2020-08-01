package com.hotniao.live.activity.withdraw;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.hn.library.base.BaseActivity;
import com.hn.library.http.HnHttpUtils;
import com.hn.library.http.HnResponseHandler;
import com.hn.library.utils.HnRegexUtils;
import com.hn.library.utils.HnToastUtils;
import com.hn.library.utils.HnUtils;
import com.hn.library.view.CommDialog;
import com.hotniao.live.R;
import com.hn.library.global.HnUrl;
import com.hotniao.live.model.HnWithDrawIdModel;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Copyright (C) 2016,深圳市红鸟网络科技股份有限公司 All rights reserved.
 * 类描述：填写提现账户
 * 创建人：刘勇
 * 创建时间：2017/2/22 16:22
 * 修改人：刘勇
 * 修改时间：2017/2/22 16:34
 * 修改备注：
 * Version:  1.0.0
 */
@Route(path = "/app/HnWithDrawWriteActivity")
public class HnWithDrawWriteActivity extends BaseActivity {
    @BindView(R.id.mEtZfb)
    EditText mEtZfb;
    @BindView(R.id.mEtMoney)
    EditText mEtMoney;
    @BindView(R.id.mTvMoney)
    TextView mTvMoney;
    @BindView(R.id.mTvAllDraw)
    TextView mTvAllDraw;

    private HnWithDrawIdModel.DBean bean;
    private boolean isAllDraw = true;
    private String money = "0";
    private String type = "";
    private String sellMoney = "";
    private int withdraw = 0;
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
            editStart = mEtMoney.getSelectionStart();
            editEnd = mEtMoney.getSelectionEnd();
            if (TextUtils.isEmpty(s.toString()) || TextUtils.equals("0", s.toString())) {
                mTvMoney.setText("可提现金额:" + sellMoney + getString(R.string.unit));
            } else {
                if (Double.valueOf(s.toString()) > Double.valueOf(sellMoney)) {
                    s.delete(editStart - 1, editEnd);
                    if(TextUtils.isEmpty(s.toString())){
                        mEtMoney.setText("0");
                    }else {
                        mEtMoney.setText(Integer.valueOf(s.toString()) + "");
                    }
                    mEtMoney.setSelection(editStart);
                }
                if(TextUtils.isEmpty(s.toString())){

                }else {
                    mTvMoney.setText("额外扣除" +
                            (Double.parseDouble(s.toString()) * withdraw / 100f)
                            + "手续费（费率" + withdraw + "%）");
                }
            }
        }
    };

    @Override
    public int getContentViewId() {
        return R.layout.activity_withdraw_write;
    }

    @Override
    public void onCreateNew(Bundle savedInstanceState) {
        sellMoney = getIntent().getStringExtra("sellMoney");
        withdraw = getIntent().getIntExtra("withdraw", 0);
        setShowBack(true);
        setTitle(R.string.withdraw_coin);
        getData();
        mTvAllDraw.setSelected(isAllDraw);
    }

    @Override
    public void getInitData() {

    }

    @OnClick({R.id.mTvNext, R.id.mTvAllDraw})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.mTvAllDraw://全部提现按钮
//                if (isAllDraw) {
//                    isAllDraw = false;
//                    mTvAllDraw.setSelected(isAllDraw);
//                    mEtMoney.setEnabled(!isAllDraw);
//
//                } else {
                isAllDraw = true;
                mTvAllDraw.setSelected(isAllDraw);

                if (TextUtils.isEmpty(sellMoney)) {
                    mEtMoney.setText(money);
                    mEtMoney.setSelection(money.length());
                } else {
                    double e = Double.valueOf(sellMoney) - HnUtils.mulDouble(Double.valueOf(sellMoney), (double) (withdraw / 100f));
                    mEtMoney.setText(HnUtils.setTwoPoint(e + ""));
                    mEtMoney.setSelection(mEtMoney.getText().toString().length());
                }
//                mEtMoney.setEnabled(!isAllDraw);
//                }
                break;
            case R.id.mTvNext://下一步
                if (TextUtils.isEmpty(mEtZfb.getText().toString().trim())) {
                    HnToastUtils.showToastShort(mEtZfb.getHint().toString());
                    return;
                }
                if (!HnRegexUtils.isMobileExact(mEtZfb.getText().toString().trim()) && !HnRegexUtils.isEmail(mEtZfb.getText().toString().trim())) {
                    HnToastUtils.showToastShort(getString(R.string.please_true_zfb_id));
                    return;
                }
                if (TextUtils.isEmpty(mEtMoney.getText().toString().trim())) {
                    HnToastUtils.showToastShort(getString(R.string.please_write_withdraw_money));
                    return;
                }

                if (!TextUtils.isEmpty(sellMoney)) {
                    if (Double.parseDouble(mEtMoney.getText().toString().trim()) < 100) {
                        HnToastUtils.showToastShort(getString(R.string.with_draw_dayu_zreo));
                        return;
                    }
                }
                if (TextUtils.isEmpty(sellMoney)) {
                    if (Double.parseDouble(mEtMoney.getText().toString().trim()) > Double.parseDouble(money)) {
                        HnToastUtils.showToastShort(getString(R.string.the_amount_is_more_than_the_withdrawal_amount));
                        return;
                    }
                } else {
                    if (Double.parseDouble(mEtMoney.getText().toString().trim()) > Double.parseDouble(sellMoney)) {
                        HnToastUtils.showToastShort(getString(R.string.the_amount_is_more_than_the_withdrawal_amount));
                        return;
                    }

                    if ((Double.valueOf(mEtMoney.getText().toString().trim()) +
                            HnUtils.mulDouble(Double.parseDouble(mEtMoney.getText().toString().trim()), (double) (withdraw / 100f)))
                            > Double.parseDouble(sellMoney)) {
                        CommDialog.newInstance(this)
                                .setTitle("提示")
                                .setContent("剩余零钱不足以支付提现手续费¥" +
                                        HnUtils.setTwoPoint(
                                                HnUtils.mulDouble(Double.parseDouble(mEtMoney.getText().toString().trim()),
                                                        (double) (withdraw / 100f)) + "")
                                )

                                .setClickListen(new CommDialog.OneSelDialog() {
                                    @Override
                                    public void sureClick() {

                                    }
                                })
                                .showDialog();

                        return;
                    }

                }


                HnWithDrawVerificationActivity.luncher(HnWithDrawWriteActivity.this,
                        mEtMoney.getText().toString(),
                        mEtZfb.getText().toString(),
                        type,
                        TextUtils.isEmpty(sellMoney) ? "1" : "2");
                break;
        }


    }

    private void getData() {
        HnHttpUtils.postRequest(HnUrl.USER_WITHDRAW_INDEX, null, HnUrl.USER_WITHDRAW_INDEX, new HnResponseHandler<HnWithDrawIdModel>(HnWithDrawIdModel.class) {
            @Override
            public void hnSuccess(String response) {
                if (isFinishing()) return;
                if (model.getC() == 0) {
                    if (model.getD().getUser() == null) return;
                    bean = model.getD();
                    type = model.getD().getUser().getPay();
                    mEtZfb.setHint(getString(R.string.please_write_your) + model.getD().getUser().getPay() + getString(R.string.withdraw_id));

                    if (TextUtils.isEmpty(sellMoney)) {
                        mTvMoney.setText("可提现金额:" + model.getD().getUser().getCash() + getString(R.string.unit));
                    } else {
                        mTvMoney.setText("可提现金额:" + sellMoney + getString(R.string.unit));
                        mEtMoney.addTextChangedListener(mTextWatcher);
                    }
                    money = model.getD().getUser().getCash();
                    if (!TextUtils.isEmpty(model.getD().getUser().getAccount())) {
                        mEtZfb.setText(model.getD().getUser().getAccount());
//                        mEtZfb.setEnabled(false);
                    }
                }
            }

            @Override
            public void hnErr(int errCode, String msg) {
                HnToastUtils.showToastShort(msg);
            }
        });
    }
}
