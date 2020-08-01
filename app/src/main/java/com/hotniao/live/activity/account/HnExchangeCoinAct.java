package com.hotniao.live.activity.account;

import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hn.library.base.BaseActivity;
import com.hn.library.base.BaseRequestStateListener;
import com.hn.library.base.baselist.BaseViewHolder;
import com.hn.library.base.baselist.CommRecyclerAdapter;
import com.hn.library.global.HnUrl;
import com.hn.library.http.BaseResponseModel;
import com.hn.library.http.HnHttpUtils;
import com.hn.library.http.HnResponseHandler;
import com.hn.library.model.HnLoginModel;
import com.hn.library.utils.HnToastUtils;
import com.hn.library.utils.HnUtils;
import com.hotniao.live.HnApplication;
import com.hotniao.live.R;
import com.hotniao.live.model.ExchangeCoinModel;
import com.hotniao.livelibrary.control.HnUserControl;
import com.live.shoplib.bean.StoreMsgModel;
import com.loopj.android.http.RequestParams;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 作者：Mr.Xu
 * 时间：2018/1/5
 */
public class HnExchangeCoinAct extends BaseActivity {
    @BindView(R.id.mTvCoin)
    TextView mTvCoin;
    @BindView(R.id.mTvNeedDot)
    TextView mTvNeedDot;
    @BindView(R.id.mRecycler)
    RecyclerView mRecycler;
    @BindView(R.id.mEdInput)
    EditText mEdInput;

    private Double mRadio = Double.valueOf(-1);

    private CommRecyclerAdapter mAdapter;
    private List<ExchangeCoinModel.DEntity.ListEntity> mData = new ArrayList<>();

    @Override
    public int getContentViewId() {
        setTitle("兑换银币");
        setShowBack(true);
        return R.layout.act_exchange_coin;
    }

    @Override
    public void onCreateNew(Bundle savedInstanceState) {
        mTvCoin.setText(HnUtils.setTwoPoint(HnApplication.getmUserBean().getUser_dot()));
        mTvNeedDot.setText("0" + HnApplication.getmConfig().getDot());
        mRecycler.setHasFixedSize(true);
        mRecycler.setLayoutManager(new LinearLayoutManager(this));
        mRecycler.setAdapter(mAdapter = new CommRecyclerAdapter() {
            @Override
            protected void onBindView(BaseViewHolder holder, final int position) {
                holder.setTextViewText(R.id.mTvCoin, mData.get(position).getDot() + HnApplication.getmConfig().getDot());
                holder.setTextViewText(R.id.mTvSubmit, mData.get(position).getTo_coin() + HnApplication.getmConfig().getCoin());
                holder.getView(R.id.mTvSubmit).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        requestSub("", mData.get(position).getId());
                    }
                });
            }

            @Override
            protected int getLayoutID(int position) {
                return R.layout.item_exchange_coin;
            }

            @Override
            public int getItemCount() {
                return mData.size();
            }
        });


        mEdInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String coin = editable.toString().trim();
                if (TextUtils.isEmpty(coin)) {
                    mTvNeedDot.setText("0" + HnApplication.getmConfig().getDot());
                } else {
                    if (-1 == mRadio) {
                        mTvNeedDot.setText("未知" + HnApplication.getmConfig().getDot());
                    } else {
                        Double aDouble = HnUtils.mulDouble(Double.parseDouble(coin), mRadio / 100);
                        mTvNeedDot.setText(HnUtils.setTwoPoint(aDouble.toString()) + HnApplication.getmConfig().getDot());
                    }
                }
            }
        });
    }

    @Override
    public void getInitData() {
        requestData();
    }

    @OnClick(R.id.mTvSubmit)
    public void onViewClicked() {

        if (TextUtils.isEmpty(mEdInput.getText().toString()) || Integer.valueOf(mEdInput.getText().toString()) < 1) {
            HnToastUtils.showToastShort("请输入兑换金额");
        } else {
            requestSub(mEdInput.getText().toString(), "");
        }
    }

    private void requestData() {
        RequestParams param = new RequestParams();
        HnHttpUtils.postRequest(HnUrl.EXCHANGE_COIN_LIST, param, "兑换列表", new HnResponseHandler<ExchangeCoinModel>(ExchangeCoinModel.class) {
            @Override
            public void hnSuccess(String response) {
                if (isFinishing() || model.getD() == null) return;
                if (!TextUtils.isEmpty(model.getD().getRadio())) {
                    try {
                        mRadio = Double.parseDouble(model.getD().getRadio());
                    } catch (Exception e) {
                        mRadio = Double.valueOf(-1);
                    }
                } else {
                    mRadio = Double.valueOf(-1);
                }
                mData.clear();
                mData.addAll(model.getD().getList());
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void hnErr(int errCode, String msg) {
                HnToastUtils.showToastShort(msg);
            }
        });
    }

    private void requestSub(String dot, String exchange_id) {
        RequestParams param = new RequestParams();
        if (!TextUtils.isEmpty(dot)) param.put("dot", dot);
        if (!TextUtils.isEmpty(exchange_id)) param.put("exchange_id", exchange_id);
        HnHttpUtils.postRequest(HnUrl.EXCHANGE_COIN, param, "兑换", new HnResponseHandler<BaseResponseModel>(BaseResponseModel.class) {
            @Override
            public void hnSuccess(String response) {
                if (isFinishing()) return;
                HnUserControl.getProfile(new HnUserControl.OnUserInfoListener() {
                    @Override
                    public void onSuccess(String uid, HnLoginModel model, String response) {
                        mTvCoin.setText(HnUtils.setTwoPoint(HnApplication.getmUserBean().getUser_dot()));
                        HnToastUtils.showToastShort("兑换成功");
                    }

                    @Override
                    public void onError(int errCode, String msg) {

                    }
                });


            }

            @Override
            public void hnErr(int errCode, String msg) {
                HnToastUtils.showToastShort(msg);
            }
        });
    }


   /* public static Double div(Double v1,Double v2){
        BigDecimal b1 = new BigDecimal(v1.toString());
        BigDecimal b2 = new BigDecimal(v2.toString());
        return b1.divide(b2,10,BigDecimal.ROUND_HALF_UP).doubleValue();
    }*/


}
