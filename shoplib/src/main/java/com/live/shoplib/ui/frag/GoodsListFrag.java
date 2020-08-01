package com.live.shoplib.ui.frag;


import android.os.Bundle;
import android.view.View;

import androidx.recyclerview.widget.GridLayoutManager;

import com.hn.library.base.baselist.BaseViewHolder;
import com.hn.library.base.baselist.CommRecyclerAdapter;
import com.hn.library.global.HnUrl;
import com.hn.library.http.HnResponseHandler;
import com.hn.library.refresh.PtrFrameLayout;
import com.hn.library.utils.HnRefreshDirection;
import com.hn.library.utils.HnToastUtils;
import com.hn.library.view.FrescoImageView;
import com.live.shoplib.R;
import com.live.shoplib.ShopActFacade;
import com.live.shoplib.bean.GoodsListItemBean;
import com.loopj.android.http.RequestParams;
import com.reslibrarytwo.CommListFragment;
import com.tencent.cos.xml.utils.StringUtils;
import com.tencent.openqq.protocol.imsdk.msg;

import java.util.ArrayList;

/**
 * create by Mr.x
 * on 2018/7/3
 */
public class GoodsListFrag extends CommListFragment {
    public ArrayList<GoodsListItemBean.DBean.GoodsBean.ItemsBean> mData = new ArrayList<>();
    public boolean sace = false;
    public boolean asc = true;

    public static GoodsListFrag get(String id, String type, Boolean asc) {
        GoodsListFrag frag = new GoodsListFrag();
        Bundle bundle = new Bundle();
        bundle.putString("id", id);
        bundle.putString("type", type);
        bundle.putBoolean("asc", asc);
        frag.setArguments(bundle);
        return frag;
    }

    @Override
    protected String setTAG() {
        return "商品列表";
    }

    @Override
    protected CommRecyclerAdapter setAdapter() {
        asc = getArguments().getBoolean("asc");
        mSpring.setMode(PtrFrameLayout.Mode.LOAD_MORE);
        mRecycler.setLayoutManager(new GridLayoutManager(getActivity(), sace ? 3 : 2));
        return new CommRecyclerAdapter() {
            @Override
            protected void onBindView(BaseViewHolder holder, final int position) {
                FrescoImageView mIvGoods = holder.getView(R.id.mIvGoods);
                mIvGoods.setImageURI(HnUrl.setImageUri(mData.get(position).getGoods_img()));
                holder.setTextViewText(R.id.mTvTitle, mData.get(position).getGoods_name());
                holder.setTextViewText(R.id.mTvPrice, mData.get(position).getGoods_price());
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ShopActFacade.openGoodsDetails(mData.get(position).getGoods_id());
                    }
                });
            }

            @Override
            protected int getLayoutID(int position) {
                return sace ? R.layout.item_main_shop : R.layout.item_main_shop_l;
            }

            @Override
            public int getItemCount() {
                return mData.size();
            }
        };
    }

    public void setSace(Boolean sace) {
        this.sace = sace;
        mRecycler.setLayoutManager(new GridLayoutManager(getActivity(), sace ? 3 : 2));
        mAdapter.notifyDataSetChanged();
    }

    public void setSort() {
        this.asc = !asc;
        getData(HnRefreshDirection.TOP, page = 1);
    }

    @Override
    protected RequestParams setRequestParam() {
        RequestParams params = new RequestParams();

        if (!StringUtils.isEmpty(getArguments().getString("id"))) {
            params.put("id", getArguments().getString("id"));
        }
        if (StringUtils.isEmpty(getArguments().getString("type"))) {
            HnToastUtils.showCenterToast("type is null");
            return params;
        }
        params.put("sort", asc ? "asc" : "desc");//	排序 asc OR desc
        params.put("type", getArguments().getString("type"));
        return params;
    }

    @Override
    protected String setRequestUrl() {
        return HnUrl.SHOP_GOODS_LIST;
    }

    @Override
    protected HnResponseHandler setResponseHandler(final HnRefreshDirection state) {
        return new HnResponseHandler<GoodsListItemBean>(GoodsListItemBean.class) {
            @Override
            public void hnSuccess(String response) {
                if (mActivity == null) return;
                refreshFinish();
                if (model.getD() == null) {
                    setEmpty("暂无商品", R.drawable.home_no_attention);
                    return;
                }
                if (HnRefreshDirection.TOP == state) {
                    mData.clear();
                }
                mData.addAll(model.getD().getGoods().getItems());
                if (mAdapter != null) {
                    mAdapter.notifyDataSetChanged();
                }
                if (mData.size() == 0) setEmpty("暂无商品", R.drawable.home_no_attention);
            }

            @Override
            public void hnErr(int errCode, String msg) {
                setEmpty("暂无商品", R.drawable.home_no_attention);
            }
        };
    }

}
