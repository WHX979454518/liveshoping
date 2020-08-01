package com.live.shoplib.ui;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.hn.library.base.BaseActivity;
import com.hn.library.base.baselist.BaseViewHolder;
import com.hn.library.base.baselist.CommRecyclerAdapter;
import com.hn.library.global.HnUrl;
import com.hn.library.http.BaseResponseModel;
import com.hn.library.http.HnHttpUtils;
import com.hn.library.http.HnResponseHandler;
import com.hn.library.utils.HnToastUtils;
import com.hn.library.view.CommDialog;
import com.live.shoplib.R;
import com.live.shoplib.bean.LogOrderModel;
import com.loopj.android.http.RequestParams;
import com.tencent.openqq.protocol.imsdk.msg;

import java.util.ArrayList;
import java.util.List;

/**
 * 商品物流 物流信息
 * 作者：Mr.Xu
 * 时间：2017/12/20
 */
@Route(path = "/shoplib/GoodsLogisticsAct")
public class GoodsLogisticsAct extends BaseActivity {

    private TextView mTvState;
    private TextView mTvCompany;
    private TextView mTvId;
    private RecyclerView mRecycler;
    private LinearLayout mLLLog, mLLEmpty;
    private CommRecyclerAdapter mAdapter;
    private List<LogOrderModel.DEntity.TracesEntity> mData = new ArrayList<>();

    @Override
    public int getContentViewId() {
        return R.layout.act_goods_log;
    }

    @Override
    public void onCreateNew(Bundle savedInstanceState) {
        setTitle("物流信息");
        setShowBack(true);
        mTvState = (TextView) findViewById(R.id.mTvState);
        mTvCompany = (TextView) findViewById(R.id.mTvCompany);
        mTvId = (TextView) findViewById(R.id.mTvId);
        mRecycler = (RecyclerView) findViewById(R.id.mRecycler);
        mLLEmpty = findViewById(R.id.mLLEmpty);
        mLLLog = findViewById(R.id.mLLLog);
        mRecycler.setLayoutManager(new LinearLayoutManager(this));
        mRecycler.setAdapter(mAdapter = new CommRecyclerAdapter() {
            @Override
            protected void onBindView(BaseViewHolder holder, int position) {

                View mLine1 = holder.getView(R.id.mLine1);
                View mLine2 = holder.getView(R.id.mLine2);
                TextView mTvMsg1 = holder.getView(R.id.mTvMsg1);
                TextView mTvMsg2 = holder.getView(R.id.mTvMsg2);

                if (position == 0) {
                    mLine1.setVisibility(View.INVISIBLE);
                    mLine2.setVisibility(View.VISIBLE);
                } else if (position == mData.size() - 1) {
                    mLine1.setVisibility(View.VISIBLE);
                    mLine2.setVisibility(View.INVISIBLE);
                } else {
                    mLine1.setVisibility(View.VISIBLE);
                    mLine2.setVisibility(View.VISIBLE);
                }
                mTvMsg1.setText(mData.get(position).getAcceptStation());
                mTvMsg2.setText(mData.get(position).getAcceptTime());

            }

            @Override
            protected int getLayoutID(int position) {
                return R.layout.item_goods_log;
            }

            @Override
            public int getItemCount() {
                return mData.size();
            }
        });
    }

    @Override
    public void getInitData() {
        String order_id = getIntent().getStringExtra("order_id");
        requestLog(order_id);
    }


    public void requestLog(String order_id) {
        RequestParams param = new RequestParams();
        param.put("order_id", order_id);
        HnHttpUtils.postRequest(HnUrl.LOG_ORDER, param, "订单物流", new HnResponseHandler<LogOrderModel>(LogOrderModel.class) {
            @Override
            public void hnSuccess(String response) {

                if (isFinishing()) return;
                if (model.getD() == null || model.getD().getTraces().size() == 0 || TextUtils.isEmpty(model.getD().getState()) ){
                    mLLLog.setVisibility(View.GONE);
                    return;
                }

                mData.clear();
                mData.addAll(model.getD().getTraces());
                mAdapter.notifyDataSetChanged();
                //物流状态: 0-无轨迹，1-已揽收，2-在途中 201-到达派件城市，3-签收,4-问题件
                switch (model.getD().getState()) {
                    case "0":
                        mTvState.setText("无轨迹");
                        break;
                    case "1":
                        mTvState.setText("已揽收");
                        break;
                    case "2":
                        mTvState.setText("在途中");
                        break;
                    case "201":
                        mTvState.setText("到达派件城市");
                        break;
                    case "3":
                        mTvState.setText("签收");
                        break;
                    case "4":
                        mTvState.setText("问题件");
                        break;
                }

                mTvCompany.setText("承运公司：" + model.getD().getShipper_name());
                mTvId.setText("运单编号：" + model.getD().getLogistic_code());
            }

            @Override
            public void hnErr(int errCode, String msg) {
                HnToastUtils.showToastShort(msg);
            }
        });
    }
}
