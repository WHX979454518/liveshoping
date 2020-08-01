package com.hotniao.live.fragment.search;

import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.hn.library.base.baselist.BaseViewHolder;
import com.hn.library.base.baselist.CommRecyclerAdapter;
import com.hn.library.global.HnUrl;
import com.hn.library.http.HnResponseHandler;
import com.hn.library.utils.HnRefreshDirection;
import com.hn.library.utils.HnToastUtils;
import com.hn.library.view.FrescoImageView;
import com.hotniao.live.R;
import com.hotniao.live.model.HnHomeHotModel;
import com.hotniao.live.model.SearchShopModel;
import com.hotniao.live.model.bean.SearchGoodsEvent;
import com.hotniao.live.utils.HnUiUtils;
import com.live.shoplib.ShopActFacade;
import com.loopj.android.http.RequestParams;
import com.reslibrarytwo.CommListFragment;
import com.reslibrarytwo.LevelView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

/**
 * 搜索商店
 * 作者：Mr.Xu
 * 时间：2017/12/7
 */
public class SearchShopFrag extends CommListFragment {

    private List<SearchShopModel.DEntity.StoreEntity.ItemsEntity> mData = new ArrayList<>();
    private String key;

    public static CommListFragment newInstance(String key) {
        CommListFragment listFragment = new SearchShopFrag();
        Bundle bundle = new Bundle();
        bundle.putString("key", key);
        listFragment.setArguments(bundle);
        return listFragment;
    }

    @Override
    protected String setTAG() {
        EventBus.getDefault().register(this);
        key = getArguments().getString("key");
        return "搜索-商店";
    }

    @Override
    protected CommRecyclerAdapter setAdapter() {
        return new CommRecyclerAdapter() {
            @Override
            protected void onBindView(BaseViewHolder holder, final int position) {
                int size = mData.get(position).getGoods_list().size();
                FrescoImageView mIvIco1 = holder.getView(R.id.mIvIco1);
                FrescoImageView mIvIco2 = holder.getView(R.id.mIvIco2);
                FrescoImageView mIvIco3 = holder.getView(R.id.mIvIco3);
                LevelView mLevelView = holder.getView(R.id.mLevelView);
                mLevelView.setLevelAnchor(Integer.valueOf(mData.get(position).getAnchor_level()));
                if (size >= 1) {
                    mIvIco1.setImageURI(Uri.parse(HnUrl.setImageUrl(mData.get(position).getGoods_list().get(0).getGoods_img())));
                    mIvIco1.setVisibility(View.VISIBLE);
                } else {
                    mIvIco1.setVisibility(View.INVISIBLE);
                }
                if (size >= 2) {
                    mIvIco2.setImageURI(Uri.parse(HnUrl.setImageUrl(mData.get(position).getGoods_list().get(1).getGoods_img())));
                    mIvIco2.setVisibility(View.VISIBLE);
                } else {
                    mIvIco2.setVisibility(View.INVISIBLE);
                }
                if (size >= 3) {
                    mIvIco3.setImageURI(Uri.parse(HnUrl.setImageUrl(mData.get(position).getGoods_list().get(2).getGoods_img())));
                    mIvIco3.setVisibility(View.VISIBLE);
                } else {
                    mIvIco3.setVisibility(View.INVISIBLE);
                }
                if (size == 0) {
                    mIvIco1.setVisibility(View.GONE);
                    mIvIco2.setVisibility(View.GONE);
                    mIvIco3.setVisibility(View.GONE);
                }

                //名字
                holder.setTextViewText(R.id.mTvShopName, mData.get(position).getName());

                FrescoImageView mIvIco = holder.getView(R.id.mIvIco);
                mIvIco.setImageURI(HnUrl.setImageUri(mData.get(position).getIcon()));


                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ShopActFacade.openShopDetails(mData.get(position).getId());
                    }
                });
            }

            @Override
            protected int getLayoutID(int position) {
                return R.layout.item_search_shop;
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
        return HnUrl.SEARCH_SHOP;
    }

    @Override
    protected HnResponseHandler setResponseHandler(final HnRefreshDirection state) {
        return new HnResponseHandler<SearchShopModel>(SearchShopModel.class) {
            @Override
            public void hnSuccess(String response) {
                if (mActivity == null) return;
                refreshFinish();
                if (model.getD().getStore() == null) {
                    setEmpty(HnUiUtils.getString(R.string.now_no_search_record), R.drawable.home_no_search);
                    return;
                }
                if (HnRefreshDirection.TOP == state) {
                    mData.clear();
                }
                mData.addAll(model.getD().getStore().getItems());
                if (mAdapter != null)
                    mAdapter.notifyDataSetChanged();
                setEmpty(HnUiUtils.getString(R.string.now_no_search_record), R.drawable.home_no_search);
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
