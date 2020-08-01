package com.live.shoplib.ui;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.hn.library.base.BaseActivity;
import com.hn.library.base.baselist.BaseViewHolder;
import com.hn.library.base.baselist.CommRecyclerAdapter;
import com.hn.library.base.baselist.OnItemClickListener;
import com.hn.library.global.HnUrl;
import com.hn.library.http.HnHttpUtils;
import com.hn.library.http.HnResponseHandler;
import com.hn.library.utils.HnToastUtils;
import com.hn.library.view.FrescoImageView;
import com.live.shoplib.R;
import com.live.shoplib.ShopActFacade;
import com.live.shoplib.bean.HnShopSortBean;
import com.loopj.android.http.RequestParams;

import java.util.ArrayList;
import java.util.List;

/**
 * create by Mr.x
 * on 2018/6/29
 */
@Route(path = "/shoplib/HnShopAct")
public class HnShopAct extends BaseActivity {

    RecyclerView mRecyclerSort;
    RecyclerView mRecyclerGoods;

    private List<HnShopSortBean.DBean.ListBean> mData = new ArrayList<>();
    private List<HnShopSortBean.DBean.ListBean.TwoListBean> mData2 = new ArrayList<>();
    private CommRecyclerAdapter mAdapter;
    private CommRecyclerAdapter mAdapter2;

    @Override
    public int getContentViewId() {
        return R.layout.act_shop;
    }

    @Override
    public void onCreateNew(Bundle savedInstanceState) {
        setTitle("全部分类");
        setShowBack(true);
        mRecyclerSort = findViewById(R.id.mRecyclerSort);
        mRecyclerGoods = findViewById(R.id.mRecyclerGoods);
        mRecyclerSort.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerGoods.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new CommRecyclerAdapter() {
            @Override
            protected void onBindView(BaseViewHolder holder, int position) {
                View mViewTag = holder.getView(R.id.mViewTag);
                TextView mTvTitle = holder.getView(R.id.mTvTitle);
                RelativeLayout mRlItem = holder.getView(R.id.mRlItem);
                mTvTitle.setText(mData.get(position).getName());
                if (mData.get(position).isCheck()) {
                    mTvTitle.setTextColor(getResources().getColor(R.color.main_color));
                    mRlItem.setBackgroundColor(Color.TRANSPARENT);
                    mViewTag.setVisibility(View.VISIBLE);
                } else {
                    mRlItem.setBackgroundColor(Color.WHITE);
                    mTvTitle.setTextColor(getResources().getColor(R.color.black));
                    mViewTag.setVisibility(View.GONE);
                }
            }

            @Override
            protected int getLayoutID(int position) {
                return R.layout.item_shop_sort;
            }

            @Override
            public int getItemCount() {
                return mData.size();
            }
        };

        mAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                for (int i = 0; i < mData.size(); i++) {
                    if (position == i) {
                        mData.get(i).setCheck(true);
                    } else {
                        mData.get(i).setCheck(false);
                    }
                }
                mData2 = mData.get(position).getTwo_list();
                mAdapter.notifyDataSetChanged();
                mAdapter2.notifyDataSetChanged();
            }
        });


        mAdapter2 = new CommRecyclerAdapter() {
            @Override
            protected void onBindView(BaseViewHolder holder, int position) {
                holder.setTextViewText(R.id.mTvTitle, mData2.get(position).getName());
                RecyclerView mRecycler3 = holder.getView(R.id.mRecycler3);
                mRecycler3.setLayoutManager(new GridLayoutManager(HnShopAct.this, 3));
                mRecycler3.setAdapter(new ThreeAdapter(mData2.get(position).getThree_list()));
            }

            @Override
            protected int getLayoutID(int position) {
                return R.layout.item_shop_sort_2;
            }

            @Override
            public int getItemCount() {
                return mData2.size();
            }
        };


        mRecyclerSort.setAdapter(mAdapter);
        mRecyclerGoods.setAdapter(mAdapter2);


    }

    @Override
    public void getInitData() {
        getDataSort();
    }

    protected void getDataSort() {
        RequestParams param = new RequestParams();
        param.put("type", "2");
        HnHttpUtils.postRequest(HnUrl.SHOP_SORT, param, TAG, new HnResponseHandler<HnShopSortBean>(HnShopSortBean.class) {
            @Override
            public void hnSuccess(String response) {
                mData.clear();
                mData.addAll(model.getD().getList());
                mData.get(0).setCheck(true);
                mData2.clear();
                mData2.addAll(model.getD().getList().get(0).getTwo_list());
                mAdapter.notifyDataSetChanged();
                mAdapter2.notifyDataSetChanged();
            }

            @Override
            public void hnErr(int errCode, String msg) {
                if (mRecyclerGoods == null) return;
                HnToastUtils.showToastShort(msg);
            }
        });
    }

    class ThreeAdapter extends CommRecyclerAdapter {

        List<HnShopSortBean.DBean.ListBean.TwoListBean.ThreeListBean> mData3 = new ArrayList<>();

        public ThreeAdapter(List<HnShopSortBean.DBean.ListBean.TwoListBean.ThreeListBean> mData3) {
            this.mData3 = mData3;
        }

        @Override
        protected void onBindView(BaseViewHolder holder, final int position) {
            FrescoImageView mIvIcon = holder.getView(R.id.mIvIcon);
            mIvIcon.setImageURI(HnUrl.setImageUri(mData3.get(position).getIcon()));
            holder.setTextViewText(R.id.mTvShop, mData3.get(position).getName());
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ShopActFacade.openShopGoodsList(mData3.get(position).getId());
                }
            });
        }

        @Override
        protected int getLayoutID(int position) {
            return R.layout.item_shop_sort_3;
        }

        @Override
        public int getItemCount() {
            return mData3.size();
        }
    }

}
