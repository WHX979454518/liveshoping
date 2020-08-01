package com.hotniao.live.fragment;

import android.text.TextUtils;
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
import com.hotniao.live.model.HnSendGiftLogModel;
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
public class HnConsumeHisPayFrag extends CommListFragment {
    private CommRecyclerAdapter mAdapter;
    private List<HnSendGiftLogModel.DBean.RecordListBean.ItemsBean> mData = new ArrayList<>();

    public static HnBillBuyVipFragment newInstance() {
        HnBillBuyVipFragment fragment = new HnBillBuyVipFragment();
        return fragment;
    }

    @Override
    protected String setTAG() {
        return "消费记录-消费";
    }

    @Override
    protected CommRecyclerAdapter setAdapter() {
        mLoadingLayout.setStatus(HnLoadingLayout.Success);
        mAdapter = new CommRecyclerAdapter() {
            @Override
            protected void onBindView(BaseViewHolder holder, int position) {
                holder.setTextViewText(R.id.mTvMoney,"购买礼物"+ mData.get(position).getGift().getLive_gift_name());
                holder.setTextViewText(R.id.mTvPay, mData.get(position).getGift().getConsume() + HnApplication.getmConfig().getCoin());

                //时间
                if(!TextUtils.isEmpty( mData.get(position).getGift().getTime())){

                        holder.setTextViewText(R.id.mTvDate1,HnDateUtils.dateFormat( mData.get(position).getGift().getTime(),"yyyy-MM-dd"));
                        holder.setTextViewText(R.id.mTvDate2, HnDateUtils.dateFormat( mData.get(position).getGift().getTime(),"HH:mm:ss"));
                }
            }

            @Override
            protected int getLayoutID(int position) {
                return R.layout.item_consume_add;
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
        return HnUrl.SEND_GIFT_LOG;
    }

    @Override
    protected HnResponseHandler setResponseHandler(final HnRefreshDirection state) {
        return new HnResponseHandler<HnSendGiftLogModel>(HnSendGiftLogModel.class) {
            @Override
            public void hnSuccess(String response) {
                if (mActivity == null) return;
                refreshFinish();
                if (model.getD().getRecord_list() == null) {
                    setEmpty(getString(R.string.now_no_record), R.drawable.empty_com);
                    return;
                }
                if (HnRefreshDirection.TOP == state) {
                    mData.clear();
                }
                mData.addAll(model.getD().getRecord_list().getItems());
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
