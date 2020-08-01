package com.hotniao.live.fragment;

import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.hn.library.base.baselist.BaseViewHolder;
import com.hn.library.base.baselist.CommRecyclerAdapter;
import com.hn.library.global.HnUrl;
import com.hn.library.http.HnHttpUtils;
import com.hn.library.http.HnResponseHandler;
import com.hn.library.refresh.PtrFrameLayout;
import com.hn.library.utils.HnRefreshDirection;
import com.hn.library.utils.HnUiUtils;
import com.hn.library.view.FrescoImageView;
import com.hotniao.live.model.SpikeListModel;
import com.live.shoplib.R;
import com.live.shoplib.ShopActFacade;
import com.live.shoplib.bean.OrderRefreshEvent;
import com.live.shoplib.utils.DataTimeUtils;
import com.loopj.android.http.RequestParams;
import com.reslibrarytwo.UpdateCommListFragment;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

/**
 * 秒杀或团购的额fragment
 * 作者：Alan
 * 时间：2019/6/15
 */
public class SpikeOrGroupBuyItemFragment extends UpdateCommListFragment {

    private List<SpikeListModel.Goods> mData = new ArrayList<>();

    private Long starttime = 0L;
    private Long endtime = 0L;


    private boolean isSpike = true;

    public static SpikeOrGroupBuyItemFragment newInstance(Long starttime, Long endtime, boolean isSpike) {
        SpikeOrGroupBuyItemFragment fragment = new SpikeOrGroupBuyItemFragment();
        Bundle bundle = new Bundle();
        bundle.putLong("starttime", starttime);
        bundle.putLong("endtime", endtime);
        bundle.putBoolean("isSpike", isSpike);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected String setTAG() {
        EventBus.getDefault().register(this);
        starttime = getArguments().getLong("starttime");
        endtime = getArguments().getLong("endtime");
        isSpike = getArguments().getBoolean("isSpike");

        //原来的fragment有刷新视觉不过在此做保护
        if (!isSpike){
            setMode(PtrFrameLayout.Mode.LOAD_MORE);
        }
        return isSpike ? "秒杀" : "团购";
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected CommRecyclerAdapter setAdapter() {
        return new CommRecyclerAdapter() {
            @Override
            protected void onBindView(BaseViewHolder holder, final int position) {
                bindView(holder, position);
            }

            @Override
            protected int getLayoutID(int position) {
                return R.layout.item_spike;
            }

            @Override
            public int getItemCount() {
                return mData.size();
            }
        };
    }

    private void bindView(BaseViewHolder holder, final int position) {
        SpikeListModel.Goods itemData = mData.get(position);
        ((FrescoImageView) holder.getView(R.id.goods_img)).setImageURI(Uri.parse(HnUrl.setImageUrl(itemData.getGoods_img())));
        ((TextView) holder.getView(R.id.goods_title)).setText(itemData.getGoods_name());
        ((TextView) holder.getView(R.id.goods_type)).setText(itemData.getGoods_type());
        ((TextView) holder.getView(R.id.goods_old_price)).setText("￥" + itemData.getGoods_price());
        if (isSpike) {
            ((TextView) holder.getView(R.id.goods_old_price)).getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
            ((TextView) holder.getView(R.id.goods_price)).setText("￥" + itemData.getSec_price());
            ((TextView) holder.getView(R.id.goods_last_number)).setText("剩余" + itemData.getSec_goods_stock() + "件");

            refreshSpikeBtnAndProgressState(holder, mData.get(position), (TextView) holder.getView(R.id.goods_buy_btn));
            return;
        }else {
            ((TextView) holder.getView(R.id.goods_old_price)).setText("销量：" + itemData.getGoods_sales());
            holder.getView(R.id.goods_last_number).setVisibility(View.GONE);
            holder.getView(R.id.goods_last_number_progress).setVisibility(View.INVISIBLE);
            ((TextView) holder.getView(R.id.goods_price)).setText("￥" + itemData.getGoods_price());

            refreshGroupBuyBtn(itemData, holder, (TextView) holder.getView(R.id.goods_buy_btn));
        }
    }

    private void refreshGroupBuyBtn(final SpikeListModel.Goods goods, BaseViewHolder holder, TextView textView) {
        if (goods.getGroup_goods_stock() > 0) {
            textView.setText("团购");
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ShopActFacade.openGroupBuyGoodsDetails(goods.getGoods_id() , goods.getGroup_buy_id() + "");
                }
            });

            holder.getView(R.id.goods_img).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ShopActFacade.openGroupBuyGoodsDetails(goods.getGoods_id(), goods.getGroup_buy_id() + "");
                }
            });
        } else {
            textView.setText("已售罄");
            textView.setOnClickListener(null);
        }
    }


    private void refreshSpikeBtnAndProgressState(final BaseViewHolder holder, final SpikeListModel.Goods goods, TextView textView) {

        ProgressBar progressBar = holder.getView(R.id.goods_last_number_progress);

        if (DataTimeUtils.getNowTimestamp() >= starttime && DataTimeUtils.getNowTimestamp() <= endtime && goods.getSec_goods_stock() > 0) {
            textView.setText("马上抢");
            ((TextView) holder.getView(R.id.goods_price)).setTextColor(getResources().getColor(R.color.spike_select));
            textView.setBackgroundColor(getResources().getColor(R.color.spike_select));
            progressBar.setVisibility(View.VISIBLE);
            progressBar.setMax(goods.getMax_number());
            progressBar.setProgress(goods.getSec_goods_stock());
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ShopActFacade.openSpikeGoodsDetails(goods.getSec_goods_id());
                }
            });
            holder.getView(R.id.goods_img).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ShopActFacade.openSpikeGoodsDetails(goods.getSec_goods_id());
                }
            });
        }

        if (DataTimeUtils.getNowTimestamp() >= starttime && DataTimeUtils.getNowTimestamp() <= endtime && goods.getSec_goods_stock() == 0) {
            textView.setText("已售罄");
            ((TextView) holder.getView(R.id.goods_price)).setTextColor(getResources().getColor(R.color.spike_select));
            textView.setBackgroundColor(getResources().getColor(R.color.spike_select));
            progressBar.setVisibility(View.VISIBLE);
            progressBar.setMax(goods.getMax_number());
            progressBar.setProgress(goods.getSec_goods_stock());
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ShopActFacade.openSpikeGoodsDetails(goods.getSec_goods_id());
                }
            });
        }

        if (DataTimeUtils.getNowTimestamp() <= starttime) {
            ((TextView) holder.getView(R.id.goods_price)).setTextColor(getResources().getColor(R.color.main_color));
            holder.getView(R.id.goods_last_number).setVisibility(View.GONE);
            progressBar.setVisibility(View.INVISIBLE);
            textView.setText("马上抢");
            textView.setBackgroundColor(getResources().getColor(R.color.comm_hint_text_color_s));
            textView.setClickable(false);
        }

        if (DataTimeUtils.getNowTimestamp() >= endtime) {
            ((TextView) holder.getView(R.id.goods_price)).setTextColor(getResources().getColor(R.color.main_color));
            holder.getView(R.id.goods_last_number).setVisibility(View.GONE);
            progressBar.setVisibility(View.INVISIBLE);
            textView.setText("马上抢");
            textView.setBackgroundColor(getResources().getColor(R.color.comm_hint_text_color_s));
            textView.setOnClickListener(null);
            textView.setClickable(false);


        }
    }


    @Override
    protected RequestParams setRequestParam() {
        RequestParams params = new RequestParams();
        params.put("starttime", starttime.toString());
        params.put("endtime", endtime.toString());
        return params;
    }

    @Override
    protected String setRequestUrl() {
        return isSpike ? HnUrl.SPIKE_LIST : HnUrl.GROUP_BUY_LIST;
    }

    @Override
    protected HnResponseHandler setResponseHandler(final HnRefreshDirection state) {
        return new HnResponseHandler<SpikeListModel>(SpikeListModel.class) {
            @Override
            public void hnSuccess(String response) {

                if (mActivity == null) return;
                refreshFinish();
                if (model.getD().getGoods() == null) {
                    setEmpty(HnUiUtils.getString(R.string.not_spike), R.drawable.empty_com);
                    return;
                }
                if (HnRefreshDirection.BOTTOM != state) {
                    mData.clear();
                }
                mData.addAll(model.getD().getGoods().getItems());

                if (mAdapter != null)
                    mAdapter.notifyDataSetChanged();
                setEmpty(HnUiUtils.getString(R.string.not_spike), R.drawable.empty_com);

            }

            @Override
            public void hnErr(int errCode, String msg) {

                refreshFinish();
                setEmpty(HnUiUtils.getString(R.string.not_spike), R.drawable.empty_com);

            }
        };
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(OrderRefreshEvent event) {
//        if (event.getPos() == -1 || event.getPos() == type) {
//            mSpring.setMode(PtrFrameLayout.Mode.BOTH);
//            getData(HnRefreshDirection.TOP, page = 1);
//        } else if (event.getPos() == -2) {
//            if (TextUtils.isEmpty(event.getKeys())) {
//                mSpring.setMode(PtrFrameLayout.Mode.BOTH);
//                getData(HnRefreshDirection.TOP, page = 1);
//            } else {
//                searchData(HnRefreshDirection.TOP);
//            }
//        }
    }

    private void searchData(final HnRefreshDirection state) {
        this.page = 1;
        mSpring.setMode(PtrFrameLayout.Mode.NONE);
        RequestParams param = setRequestParam();
        HnHttpUtils.postRequest(HnUrl.ORDER_GOODS_SHOP_SEARCH, param, TAG, new HnResponseHandler<SpikeListModel>(SpikeListModel.class) {
            @Override
            public void hnSuccess(String response) {
                if (mActivity == null) return;
                refreshFinish();
                if (model.getD() == null) {
                    setEmpty(HnUiUtils.getString(R.string.not_spike), R.drawable.empty_com);
                    return;
                }
                if (HnRefreshDirection.TOP == state) {
                    mData.clear();
                }
                mData.addAll(model.getD().getGoods().getItems());
                if (mAdapter != null)
                    mAdapter.notifyDataSetChanged();
                setEmpty(HnUiUtils.getString(R.string.not_spike), R.drawable.empty_com);
            }

            @Override
            public void hnErr(int errCode, String msg) {
                if (mActivity == null) return;
                mData.clear();
                mAdapter.notifyDataSetChanged();
                refreshFinish();
                setEmpty(HnUiUtils.getString(R.string.not_spike), R.drawable.empty_com);
            }
        });


    }


}
