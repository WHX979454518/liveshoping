package com.hotniao.live.fragment.search;


import android.net.Uri;
import android.text.TextUtils;
import android.view.View;

import com.hn.library.base.baselist.BaseViewHolder;
import com.hn.library.base.baselist.CommRecyclerAdapter;
import com.hn.library.global.HnUrl;
import com.hn.library.http.HnResponseHandler;
import com.hn.library.utils.HnRefreshDirection;
import com.hn.library.utils.HnToastUtils;
import com.hn.library.utils.StringUtils;
import com.hn.library.view.FrescoImageView;
import com.hotniao.live.R;
import com.hotniao.live.model.CollectGoodsModel;
import com.hotniao.live.model.bean.SearchGoodsEvent;
import com.hotniao.live.model.bean.SearchGoodsModel;
import com.live.shoplib.ShopActFacade;
import com.loopj.android.http.RequestParams;
import com.reslibrarytwo.CommListFragment;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

/**
 * 收藏商品
 * 作者：Mr.Xu
 * 时间：2017/12/7
 */
public class CollectGoodsFrag extends CommListFragment {

    private List<CollectGoodsModel.DEntity.ItemsEntity> mData = new ArrayList<>();
    private String key;

    @Override
    protected String setTAG() {
        setGridManager(true);
        return "搜索-商品";
    }

    @Override
    protected CommRecyclerAdapter setAdapter() {

        return new CommRecyclerAdapter() {
            @Override
            protected void onBindView(BaseViewHolder holder, final int position) {
                FrescoImageView mIvIco = holder.getView(R.id.mIvIco);
                mIvIco.setImageURI(HnUrl.setImageUri(mData.get(position).getGoods_img()));
                //钱
                StringUtils.setNum(holder.getView(R.id.mTvMoney), mData.get(position).getGoods_price());
                //描述
                holder.setTextViewText(R.id.mTvName, mData.get(position).getGoods_name());
                //销售
//                StringUtils.setNum(holder.getView(R.id.mTvSell), mData.get(position).getGoods_sales());
                holder.getView(R.id.linearLayout).setVisibility(View.GONE);

                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ShopActFacade.openGoodsDetails(mData.get(position).getGoods_id());
                    }
                });

            }

            @Override
            protected int getLayoutID(int position) {
                return R.layout.item_search_goods;
            }

            @Override
            public int getItemCount() {
                return mData.size();
            }
        };
    }

    @Override
    protected RequestParams setRequestParam() {
        RequestParams params = new RequestParams();
        return params;
    }

    @Override
    protected String setRequestUrl() {
        return HnUrl.COLLECTION_GOODS;
    }

    @Override
    protected HnResponseHandler setResponseHandler(final HnRefreshDirection state) {
        return new HnResponseHandler<CollectGoodsModel>(CollectGoodsModel.class) {
            @Override
            public void hnSuccess(String response) {
                if (mActivity == null) return;
                refreshFinish();
                if (model.getD() == null) {
                    setEmpty("收藏为空", R.drawable.home_no_attention);
                    return;
                }
                if (HnRefreshDirection.TOP == state) {
                    mData.clear();
                }
                mData.addAll(model.getD().getItems());
                if (mAdapter != null)
                    mAdapter.notifyDataSetChanged();
                if (mData.size() == 0) setEmpty("收藏为空", R.drawable.home_no_attention);
            }

            @Override
            public void hnErr(int errCode, String msg) {
                setEmpty("收藏为空", R.drawable.home_no_attention);
            }
        };
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

}
