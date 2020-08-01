package com.hotniao.live.fragment.search;


import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import androidx.recyclerview.widget.GridLayoutManager;

import com.hn.library.base.baselist.BaseViewHolder;
import com.hn.library.base.baselist.CommRecyclerAdapter;
import com.hn.library.global.HnUrl;
import com.hn.library.http.HnResponseHandler;
import com.hn.library.utils.HnRefreshDirection;
import com.hn.library.utils.HnUiUtils;
import com.hn.library.utils.StringUtils;
import com.hn.library.view.FrescoImageView;
import com.hotniao.live.R;
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
 * 搜索商品
 * 作者：Mr.Xu
 * 时间：2017/12/7
 */
public class SearchGoodsFrag extends CommListFragment {

    private List<SearchGoodsModel.DEntity.GoodsEntity.ItemsEntity> mData = new ArrayList<>();
    private String key;

    @Override
    protected String setTAG() {
        EventBus.getDefault().register(this);
        key = getArguments().getString("key");
        setGridManager(true);
        return "搜索-商品";
    }

    public static CommListFragment newInstance(String key){
        CommListFragment listFragment = new SearchGoodsFrag();
        Bundle bundle = new Bundle();
        bundle.putString("key",key);
        listFragment.setArguments(bundle);
        return listFragment;
    }

    @Override
    protected CommRecyclerAdapter setAdapter() {

        mRecycler.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        return new CommRecyclerAdapter() {
            @Override
            protected void onBindView(BaseViewHolder holder, final int position) {
                FrescoImageView mIvIco = holder.getView(R.id.mIvIco);
                mIvIco.setImageURI(Uri.parse(HnUrl.setImageUrl(mData.get(position).getGoods_img())));
                //钱
                StringUtils.setNum(holder.getView(R.id.mTvMoney), mData.get(position).getGoods_price());
                //描述
                holder.setTextViewText(R.id.mTvName, mData.get(position).getGoods_name());
                //销售
                StringUtils.setNum(holder.getView(R.id.mTvSell), mData.get(position).getGoods_sales());


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
        if (!TextUtils.isEmpty(key)) params.add("sKey", key);
        return params;
    }

    @Override
    protected String setRequestUrl() {
        return HnUrl.SEARCH_GOODS;
    }

    @Override
    protected HnResponseHandler setResponseHandler(final HnRefreshDirection state) {
        return new HnResponseHandler<SearchGoodsModel>(SearchGoodsModel.class) {
            @Override
            public void hnSuccess(String response) {
                if (mActivity == null) return;
                refreshFinish();
                if (model.getD().getGoods() == null) {
                    setEmpty(HnUiUtils.getString(R.string.now_no_search_record), R.drawable.home_no_search);
                    return;
                }
                if (HnRefreshDirection.TOP == state) {
                    mData.clear();
                }
                mData.addAll(model.getD().getGoods().getItems());
                if(mAdapter!=null)
                    mAdapter.notifyDataSetChanged();
                if (mData.size() == 0) {

                    setEmpty(HnUiUtils.getString(R.string.now_no_search_record), R.drawable.home_no_search);
                }
            }

            @Override
            public void hnErr(int errCode, String msg) {
                setEmpty(HnUiUtils.getString(R.string.now_no_search_data), R.drawable.home_no_search);
            }
        };
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }


    @Subscribe
    public void onDataRefresh(SearchGoodsEvent event) {
        key = event.getKey();
        page = 1;
        getData(HnRefreshDirection.TOP, page);
    }
}
