package com.hotniao.live.fragment.search;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import com.hn.library.base.baselist.BaseViewHolder;
import com.hn.library.base.baselist.CommRecyclerAdapter;
import com.hn.library.global.HnUrl;
import com.hn.library.http.HnResponseHandler;
import com.hn.library.refresh.PtrFrameLayout;
import com.hn.library.utils.HnRefreshDirection;
import com.hn.library.view.FrescoImageView;
import com.hotniao.live.R;
import com.hotniao.live.activity.ShopDetailsActivity;
import com.hotniao.live.model.CollectionShopModel;
import com.hotniao.live.model.TheStoreModel;
import com.hotniao.live.model.bean.SearchGoodsModel;
import com.live.shoplib.ShopActFacade;
import com.loopj.android.http.RequestParams;
import com.reslibrarytwo.CommListFragment;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

/**
 * 我的商铺
 */
public class MyShopFragment extends CommListFragment {

    private List<TheStoreModel.DBean> mData = new ArrayList<>();


    @Override
    protected String setTAG() {
        mSpring.setMode(PtrFrameLayout.Mode.NONE);
        return "我的商铺";
    }

    @Override
    protected CommRecyclerAdapter setAdapter() {
        return new CommRecyclerAdapter() {
            @Override
            protected void onBindView(BaseViewHolder holder, final int position) {

                final TheStoreModel.DBean.ShopBean shopBean =mData.get(position).getShop();
                final TheStoreModel.DBean.UserBean userBean =mData.get(position).getUser();
                if(null!=shopBean){
                    holder.getView(R.id.tv_name).setVisibility(View.VISIBLE);
                    holder.getView(R.id.tv_button).setVisibility(View.VISIBLE);
                }
                //名字
                holder.setTextViewText(R.id.tv_name, shopBean.getShop_store_name());
//                holder.setTextViewText(R.id.tv_button, mData.get(position).getName());

                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//                        ShopActFacade.openShopDetails(mData.get(position).getId());
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("shopBean",shopBean);
                        bundle.putSerializable("userBean",userBean);
                        Intent intent = new Intent(getActivity(), ShopDetailsActivity.class);
                        intent.putExtras(bundle);
                        mActivity.startActivity(intent);
                    }
                });
            }

            @Override
            protected int getLayoutID(int position) {
                return R.layout.item_myshow_shop;
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
//        return HnUrl.COLLECT_SHOP_LIST;
        return HnUrl.CERTIFICATION_DETAIL;
    }

    @Override
    protected HnResponseHandler setResponseHandler(final HnRefreshDirection state) {
        return new HnResponseHandler<TheStoreModel>(TheStoreModel.class) {
            @Override
            public void hnSuccess(String response) {
                if (mActivity == null) return;
                refreshFinish();
                if (model.getD() == null) {
                    setEmpty("您暂时没有入驻任何店铺哦", R.mipmap.nottojoin);
                }else if(null!=model.getD().getShop()&&model.getD().getShop().getIs_null()==false&&null!=model.getD().getUser()&&model.getD().getUser().getIs_null()==false){
                    mData.add(model.getD());
                    if (mAdapter != null)
                        mAdapter.notifyDataSetChanged();
                    setEmpty("您暂时没有入驻任何店铺哦", R.mipmap.nottojoin);
                }else {
//                    mData.add(model.getD());
                    if (mAdapter != null)
                        mAdapter.notifyDataSetChanged();
                    setEmpty("您暂时没有入驻任何店铺哦", R.mipmap.nottojoin);
                }
//                if (HnRefreshDirection.TOP == state) {
//                    mData.clear();
//                }
//                CollectionShopModel.DEntity.ItemsEntity c  = new CollectionShopModel.DEntity.ItemsEntity();
//                CollectionShopModel.DEntity.ItemsEntity.GoodsEntity goodsEntity = new CollectionShopModel.DEntity.ItemsEntity.GoodsEntity();
//                goodsEntity.setGoods_img("lalallala");
//                goodsEntity.setGoods_img("");
//                List<CollectionShopModel.DEntity.ItemsEntity.GoodsEntity> list = new ArrayList<>();
//                list.add(goodsEntity);
//                c.setGoods(list);
//                mData.add(c);

            }

            @Override
            public void hnErr(int errCode, String msg) {
                setEmpty("您暂时没有入驻任何店铺哦", R.mipmap.nottojoin);
            }
        };
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }


}
