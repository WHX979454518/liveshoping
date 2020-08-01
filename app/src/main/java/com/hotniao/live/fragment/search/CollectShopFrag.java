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
import com.hn.library.view.FrescoImageView;
import com.hotniao.live.R;
import com.hotniao.live.model.CollectionShopModel;
import com.hotniao.live.model.SearchShopModel;
import com.hotniao.live.model.bean.SearchGoodsEvent;
import com.live.shoplib.ShopActFacade;
import com.loopj.android.http.RequestParams;
import com.reslibrarytwo.CommListFragment;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

/**
 * 收藏商店
 * 作者：Mr.Xu
 * 时间：2017/12/7
 */
public class CollectShopFrag extends CommListFragment {

    private List<CollectionShopModel.DEntity.ItemsEntity> mData = new ArrayList<>();

    @Override
    protected String setTAG() {
        return "收藏商店";
    }

    @Override
    protected CommRecyclerAdapter setAdapter() {
        return new CommRecyclerAdapter() {
            @Override
            protected void onBindView(BaseViewHolder holder, final int position) {

                List<CollectionShopModel.DEntity.ItemsEntity.GoodsEntity> imgs = mData.get(position).getGoods();
                if (imgs == null || imgs.size() < 1) {
                    holder.getView(com.live.shoplib.R.id.mIvIco1).setVisibility(View.GONE);
                    holder.getView(com.live.shoplib.R.id.mIvIco2).setVisibility(View.GONE);
                    holder.getView(com.live.shoplib.R.id.mIvIco3).setVisibility(View.GONE);
                } else {
                    for (int i = 0; i < imgs.size(); i++) {
                        if (i == 0) {
                            holder.getView(com.live.shoplib.R.id.mIvIco1).setVisibility(View.VISIBLE);
                            ((FrescoImageView) holder.getView(com.live.shoplib.R.id.mIvIco1)).setImageURI(Uri.parse(HnUrl.setImageUrl(imgs.get(i).getGoods_img())));
                        } else if (i == 1) {
                            holder.getView(com.live.shoplib.R.id.mIvIco2).setVisibility(View.VISIBLE);
                            ((FrescoImageView) holder.getView(com.live.shoplib.R.id.mIvIco2)).setImageURI(Uri.parse(HnUrl.setImageUrl(imgs.get(i).getGoods_img())));
                        } else if (i == 2) {
                            holder.getView(com.live.shoplib.R.id.mIvIco3).setVisibility(View.VISIBLE);
                            ((FrescoImageView) holder.getView(com.live.shoplib.R.id.mIvIco3)).setImageURI(Uri.parse(HnUrl.setImageUrl(imgs.get(i).getGoods_img())));
                        }
                    }
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
        return params;
    }

    @Override
    protected String setRequestUrl() {
        return HnUrl.COLLECT_SHOP_LIST;
    }

    @Override
    protected HnResponseHandler setResponseHandler(final HnRefreshDirection state) {
        return new HnResponseHandler<CollectionShopModel>(CollectionShopModel.class) {
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
                setEmpty("收藏为空", R.drawable.home_no_attention);
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
