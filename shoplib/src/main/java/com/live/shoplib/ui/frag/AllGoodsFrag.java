package com.live.shoplib.ui.frag;


import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import androidx.annotation.RequiresApi;

import com.hn.library.base.baselist.BaseViewHolder;
import com.hn.library.base.baselist.CommRecyclerAdapter;
import com.hn.library.global.HnUrl;
import com.hn.library.http.HnResponseHandler;
import com.hn.library.refresh.PtrFrameLayout;
import com.hn.library.utils.HnRefreshDirection;
import com.hn.library.utils.HnToastUtils;
import com.hn.library.utils.StringUtils;
import com.hn.library.view.FrescoImageView;
import com.live.shoplib.R;
import com.live.shoplib.ShopActFacade;
import com.live.shoplib.bean.GoodsListModel;
import com.live.shoplib.ui.ShopDetailsAct;
import com.loopj.android.http.RequestParams;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

/**
 * 搜索商品
 * 作者：Mr.Xu
 * 时间：2017/12/7
 */
@RequiresApi(api = Build.VERSION_CODES.M)
public class AllGoodsFrag extends BaseScollFragment {

    private List<GoodsListModel.DEntity.ItemsEntity> mData = new ArrayList<>();
    private String column_id;
    private String store_id;

    public static AllGoodsFrag newInstance(String column_id, String store_id) {
        AllGoodsFrag frag = new AllGoodsFrag();
        Bundle bundle = new Bundle();
        bundle.putString("column_id", column_id);
        bundle.putString("store_id", store_id);
        frag.setArguments(bundle);
        return frag;
    }

    @Override
    protected String setTAG() {
        column_id = getArguments().getString("column_id");
        store_id = getArguments().getString("store_id");
        return "All商品";
    }

    @Override
    protected CommRecyclerAdapter setAdapter() {
        mSpring.setMode(PtrFrameLayout.Mode.LOAD_MORE);
        setGridManager(true);
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
                return R.layout.item_goods;
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
        if (!TextUtils.isEmpty(store_id)) params.add("store_id", store_id);
        if (!TextUtils.isEmpty(column_id)) params.add("column_id", column_id);
        return params;
    }

    @Override
    protected String setRequestUrl() {
        return HnUrl.GOODS_LIST;
    }

    @Override
    protected HnResponseHandler setResponseHandler(final HnRefreshDirection state) {
        return new HnResponseHandler<GoodsListModel>(GoodsListModel.class) {
            @Override
            public void hnSuccess(String response) {
                if (mRecycler == null) return;
                refreshFinish();
                refreshComplete();
                if (model.getD().getItems() == null) {
                    setEmpty("暂无商品", R.drawable.no_goods);
                    return;
                }
                if (HnRefreshDirection.TOP == state) {
                    mData.clear();
                }
                mData.addAll(model.getD().getItems());
                if (mAdapter != null)
                    mAdapter.notifyDataSetChanged();
                setEmpty("暂无商品", R.drawable.no_goods);
            }

            @Override
            public void hnErr(int errCode, String msg) {
                if (mRecycler == null) return;
                refreshFinish();
                refreshComplete();
                HnToastUtils.showToastShort(msg);
                setEmpty("暂无商品", R.drawable.no_goods);
            }

        };
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }


    @Override
    public void pullToRefresh() {
        getData(HnRefreshDirection.TOP,page=1);
    }

    @Override
    public void refreshComplete() {
        if (this.getActivity() instanceof ShopDetailsAct) {
            ((ShopDetailsAct) (this.getActivity())).refreshComplete();
        }
    }

    @Override
    public View getScrollableView() {
        return mRecycler;
    }
}
