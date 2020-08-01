package com.live.shoplib.ui;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.hn.library.base.BaseActivity;
import com.hn.library.global.HnUrl;
import com.hn.library.http.HnHttpUtils;
import com.hn.library.http.HnResponseHandler;
import com.hn.library.utils.HnToastUtils;
import com.hn.library.utils.HnUtils;
import com.hn.library.view.FrescoImageView;
import com.live.shoplib.R;
import com.live.shoplib.ShopActFacade;
import com.live.shoplib.bean.ApplyBackModel;
import com.live.shoplib.bean.ApplyBackSubModel;
import com.live.shoplib.bean.OrderModel;
import com.live.shoplib.bean.OrderRefreshEvent;
import com.loopj.android.http.RequestParams;

import org.greenrobot.eventbus.Subscribe;

/**
 * 申请退款/退货
 * 作者：Mr.Xu
 * 时间：2017/12/20
 */
@Route(path = "/shoplib/ApplyBackAct")
public class ApplyBackAct extends BaseActivity {

    private FrescoImageView mIvGoodsIco;
    private TextView mTvGoodsName;
    private LinearLayout mLLTag1;
    private LinearLayout mLLOnce;
    private CheckBox mBoxOnce;
    private LinearLayout mLLBoth;
    private CheckBox mBoxBoth;
    private EditText mTvMoney;
    private TextView mTvMaxMoney;
    private ImageView mIvMin;
    private TextView mTvNum;
    private ImageView mIvAdd;
    private LinearLayout mLLTag2;
    private TextView mTvMoney2;
    private EditText mEdMsg;
    private TextView mTvApply;

    private ApplyBackModel.DBean bean;

    private boolean both = false;
    private String order_id, details_id = "";
    private double tempMoney = 0f;
    private double maxMoney = 0f;

    @Override
    public int getContentViewId() {
        return R.layout.act_apply_back;
    }

    @SuppressLint("WrongViewCast")
    @Override
    public void onCreateNew(Bundle savedInstanceState) {
        setTitle("申请退款");
        setShowBack(true);

        both = getIntent().getBooleanExtra("both", false);
        order_id = getIntent().getStringExtra("order_id");
        details_id = getIntent().getStringExtra("details_id");
        mIvGoodsIco = (FrescoImageView) findViewById(R.id.mIvGoodsIco);
        mTvGoodsName = (TextView) findViewById(R.id.mTvGoodsName);
        mLLTag1 = (LinearLayout) findViewById(R.id.mLLTag1);
        mLLOnce = (LinearLayout) findViewById(R.id.mLLOnce);
        mBoxOnce = (CheckBox) findViewById(R.id.mBoxOnce);
        mLLBoth = (LinearLayout) findViewById(R.id.mLLBoth);
        mBoxBoth = (CheckBox) findViewById(R.id.mBoxBoth);
        mTvMoney = (EditText) findViewById(R.id.mTvMoney);
        mTvMaxMoney = (TextView) findViewById(R.id.mTvMaxMoney);
        mIvMin = (ImageView) findViewById(R.id.mIvMin);
        mTvNum = (TextView) findViewById(R.id.mTvNum);
        mIvAdd = (ImageView) findViewById(R.id.mIvAdd);
        mLLTag2 = (LinearLayout) findViewById(R.id.mLLTag2);
        mTvMoney2 = (TextView) findViewById(R.id.mTvMoney2);
        mEdMsg = (EditText) findViewById(R.id.mEdMsg);
        mTvApply = (TextView) findViewById(R.id.mTvApply);

        mLLTag1.setVisibility(both ? View.VISIBLE : View.GONE);
        mLLTag2.setVisibility(both ? View.GONE : View.VISIBLE);
        getDetails(details_id);




    }

    @Override
    public void getInitData() {

    }


    TextWatcher mTextWatcher = new TextWatcher() {

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            //TextView.setText(s);//将输入的内容实时显示
        }

        @Override
        public void afterTextChanged(Editable s) {
            if (s.toString().matches("^[0-9]+[.][0-9]+$") ||
                    s.toString().matches("^[0-9]+")) {
                try {
                    if (Double.valueOf(s.toString()) > maxMoney) {
                        mTvMoney.setText("" + tempMoney);
                        HnToastUtils.showToastShort("退款金额有误");
                    } else {
                        tempMoney = Double.valueOf(s.toString());
                    }
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
            }
        }
    };

    public void onApplyBackClick(View v) {
        if (v == mTvApply) {
            if (both) {
                if (TextUtils.isEmpty(mTvNum.getText().toString().trim()) || 1 > Integer.parseInt(mTvNum.getText().toString().trim())) {
                    HnToastUtils.showToastShort("请添加退款数量");
                    return;
                }

                if (mBoxOnce.isChecked()) {
                    submit(details_id, mTvNum.getText().toString() + "",
                            order_id, mEdMsg.getText().toString(), "1");
                } else {
                    submit(details_id, mTvNum.getText().toString() + "",
                            order_id, mEdMsg.getText().toString(), "2");
                }
            } else {
                submit(details_id, bean.getGoods_list().getGoods_number() + "",
                        order_id, mEdMsg.getText().toString(), "1");
            }
        } else if (v == mLLBoth) {
            mBoxBoth.setChecked(true);
            mBoxOnce.setChecked(false);
        } else if (v == mLLOnce) {
            mBoxBoth.setChecked(false);
            mBoxOnce.setChecked(true);
        } else if (v == mIvMin) {
            if (bean == null) return;
            int num = Integer.valueOf(mTvNum.getText().toString());
            if (num <= 0) {
                return;
            }
            num--;
            mTvNum.setText(num + "");
        } else if (v == mIvAdd) {
            if (bean == null) return;
            int num = Integer.valueOf(mTvNum.getText().toString());
            int maxNum = 0;
            try {
                maxNum = Integer.valueOf(bean.getGoods_list().getGoods_number());
            } catch (Exception e) {
                maxNum = 0;
            }
            if (num + 1 > maxNum) {
                HnToastUtils.showToastShort("已达上限");
                return;
            }
            num++;

            mTvNum.setText(num + "");
        }
    }

    public void getDetails(String details_id) {
        RequestParams param = new RequestParams();
        param.put("details_id", details_id);
        HnHttpUtils.postRequest(HnUrl.APP_BACK_DETAILS, param, "退款订单", new HnResponseHandler<ApplyBackModel>(ApplyBackModel.class) {
            @Override
            public void hnSuccess(String response) {
                if (model.getD() == null || mTvNum == null) return;
                bean = model.getD();
                //图片
                mIvGoodsIco.setImageURI(Uri.parse(HnUrl.setImageUrl(model.getD().getGoods_list().getGoods_img())));
                //商品名
                mTvGoodsName.setText(model.getD().getGoods_list().getGoods_name());
                if (TextUtils.isEmpty(model.getD().getGoods_list().getGoods_price()) || TextUtils.isEmpty(model.getD().getGoods_list().getGoods_number())) {
                    mTvMoney.setText("" + 0);
                    mTvMoney2.setText("" + 0);
                } else {
                    try {
                        if (both)
                            mTvMoney.setText("" + HnUtils.mulDouble(Double.parseDouble(model.getD().getGoods_list().getGoods_price()),
                                    Double.parseDouble(model.getD().getGoods_list().getGoods_number())) + "");
                        else
                            mTvMoney2.setText("" + HnUtils.mulDouble(Double.parseDouble(model.getD().getGoods_list().getGoods_price()),
                                    Double.parseDouble(model.getD().getGoods_list().getGoods_number())) + "");
                    } catch (Exception e) {
                        mTvMoney.setText("" + 0);
                        mTvMoney2.setText("" + 0);
                    }
                }
                int maxNum = 0;
                try {
                    maxNum = Integer.valueOf(bean.getGoods_list().getGoods_number());
                } catch (Exception e) {
                    maxNum = 0;
                }
                mTvNum.setText(maxNum + "");
                tempMoney = HnUtils.mulDouble(Double.parseDouble(model.getD().getGoods_list().getGoods_price()),
                        Double.parseDouble(model.getD().getGoods_list().getGoods_number()));
                maxMoney = tempMoney;
                mTvMaxMoney.setText(HnUtils.mulDouble(Double.parseDouble(model.getD().getGoods_list().getGoods_price()),
                        Double.parseDouble(model.getD().getGoods_list().getGoods_number())) + "");

                mTvMoney.addTextChangedListener(mTextWatcher);

            }

            @Override
            public void hnErr(int errCode, String msg) {
                HnToastUtils.showToastShort(msg);
            }
        });
    }


    //	1 仅退款 2 退货退款
    public void submit(String details_id, String num, String order_id, String remark, String type) {
        RequestParams param = new RequestParams();
        param.put("details_id", details_id);
        param.put("num", num);
        param.put("order_id", order_id);
        param.put("type", type);
        if (!TextUtils.isEmpty(remark)) param.put("remark", remark);
        HnHttpUtils.postRequest(HnUrl.APP_BACK_SUB, param, "退款申请", new HnResponseHandler<ApplyBackSubModel>(ApplyBackSubModel.class) {
            @Override
            public void hnSuccess(String response) {
                if (isFinishing()) return;
                ShopActFacade.openRefundDetails(model.getD().getRefund_id());
                finish();
            }

            @Override
            public void hnErr(int errCode, String msg) {
                HnToastUtils.showToastShort(msg);
            }
        });
    }
}
