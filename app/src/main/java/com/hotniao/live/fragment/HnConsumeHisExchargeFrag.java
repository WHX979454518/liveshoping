package com.hotniao.live.fragment;

import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.hn.library.base.baselist.BaseViewHolder;
import com.hn.library.base.baselist.CommRecyclerAdapter;
import com.hn.library.global.HnUrl;
import com.hn.library.http.HnResponseHandler;
import com.hn.library.loadstate.HnLoadingLayout;
import com.hn.library.utils.HnDateUtils;
import com.hn.library.utils.HnRefreshDirection;
import com.hn.library.utils.HnToastUtils;
import com.hotniao.live.HnApplication;
import com.hotniao.live.R;
import com.hotniao.live.fragment.billRecord.HnBillBuyVipFragment;
import com.hotniao.live.model.PayLogModel;
import com.hotniao.livelibrary.util.DataTimeUtils;
import com.loopj.android.http.RequestParams;
import com.reslibrarytwo.CommListFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * 消费记录
 * 作者：Mr.Xu
 * 时间：2018/1/5
 */
public class HnConsumeHisExchargeFrag extends CommListFragment {
    private CommRecyclerAdapter mAdapter;
    private List<PayLogModel.DBean.RechargeListBean.ItemsBean> mData = new ArrayList<>();

    public static HnBillBuyVipFragment newInstance() {
        HnBillBuyVipFragment fragment = new HnBillBuyVipFragment();
        return fragment;
    }

    @Override
    protected String setTAG() {
        return "消费记录-充值";
    }

    @Override
    protected CommRecyclerAdapter setAdapter() {
        mLoadingLayout.setStatus(HnLoadingLayout.Success);
        mAdapter = new CommRecyclerAdapter() {
            @Override
            protected void onBindView(BaseViewHolder holder, int position) {
                holder.setTextViewText(R.id.mTvDate1, mData.get(position).getTime());

                holder.setTextViewText(R.id.mTvCoin, HnApplication.getmConfig().getCoin()+"x"+mData.get(position).getCoin());
                TextView mTvState = holder.getView(R.id.mTvState);
                switch (mData.get(position).getStatus()) {
                    //状态，Y：成功，N：失败，C：充值中
                    case "Y":
                        mTvState.setText("充值成功");
                        holder.setTextViewText(R.id.mTvMoney,"- ¥ "+ mData.get(position).getFee());
                        mTvState.setTextColor(getResources().getColor(R.color.comm_text_color_black));
                        break;
                    case "N":
                        mTvState.setText("充值失败");
                        holder.setTextViewText(R.id.mTvMoney,"¥ "+ mData.get(position).getFee());
                        mTvState.setTextColor(getResources().getColor(R.color.comm_text_color_red));
                        break;
                    case "C":
                        mTvState.setText("充值中");
                        holder.setTextViewText(R.id.mTvMoney,"- ¥ "+ mData.get(position).getFee());
                        mTvState.setTextColor(getResources().getColor(R.color.comm_text_color_black));
                        break;
                }
                //，wxPay：微信，aliPay：支付宝，apple：苹果
                if("wxPay".equals(mData.get(position).getType())){
                    ((ImageView)holder.getView(R.id.mIvType)).setImageResource(R.drawable.wechat_pay);
                }else if("aliPay".equals(mData.get(position).getType())){
                    ((ImageView)holder.getView(R.id.mIvType)).setImageResource(R.drawable.alipay_payment);
                }else {
                    ((ImageView)holder.getView(R.id.mIvType)).setImageResource(R.drawable.apple);
                }

                //时间
                if(!TextUtils.isEmpty( mData.get(position).getTime())){

                        holder.setTextViewText(R.id.mTvDate1,HnDateUtils.dateFormat( mData.get(position).getTime(),"yyyy-MM-dd"));
                        holder.setTextViewText(R.id.mTvDate2, HnDateUtils.dateFormat( mData.get(position).getTime(),"HH:mm:ss"));
                }
            }

            @Override
            protected int getLayoutID(int position) {
                return R.layout.item_exchange_add;
            }

            @Override
            public int getItemCount() {
                return mData.size();
            }
        };
        return mAdapter;
    }

    @Override
    protected RequestParams setRequestParam() {
        RequestParams params = new RequestParams();
        return params;
    }

    @Override
    protected String setRequestUrl() {
        return HnUrl.API_GETPAYLOGS;
    }

    @Override
    protected HnResponseHandler setResponseHandler(final HnRefreshDirection state) {
        return new HnResponseHandler<PayLogModel>(PayLogModel.class) {
            @Override
            public void hnSuccess(String response) {
                if (mActivity == null) return;
                refreshFinish();
                if (model.getD().getRecharge_list() == null) {
                    setEmpty(getString(R.string.now_no_record), R.drawable.empty_com);
                    return;
                }
                if (HnRefreshDirection.TOP == state) {
                    mData.clear();
                }
                mData.addAll(model.getD().getRecharge_list().getItems());
                if (mAdapter != null)
                    mAdapter.notifyDataSetChanged();
                setEmpty(getString(R.string.now_no_record), R.drawable.empty_com);
            }

            @Override
            public void hnErr(int errCode, String msg) {
                if (mActivity == null) return;
                refreshFinish();
                HnToastUtils.showToastShort(msg);
                setEmpty(getString(R.string.now_no_record), R.drawable.empty_com);
            }
        };
    }
}
