package com.live.shoplib.other;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hn.library.base.baselist.BaseViewHolder;
import com.hn.library.base.baselist.CommRecyclerAdapter;
import com.hn.library.utils.HnToastUtils;
import com.live.shoplib.R;
import com.live.shoplib.adapter.SpikeAndGroupSelectGoodModelAdapter;
import com.live.shoplib.bean.GoodsAttrBean;
import com.live.shoplib.widget.FlowTag.FlowTagLayout;
import com.live.shoplib.widget.FlowTag.OnTagClickListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 处理商品-属性参数
 * 作者：Mr.Xu
 * 时间：2017/12/14
 */
public class SpikeAndGroupGoodsDetailsHelper {

    private RecyclerView mRecycler;
    private CommRecyclerAdapter mAdapter;
    private Context mContext;
    private GoodsAttrBean mData;
    private OnClickStateChange stateChange;
    private List<Map<String, String>> mSet = new ArrayList<>();
    private boolean isLive;

    private String id = "";
    private String spec_text = "";
    private String num = "";
    private String price = "";

    public SpikeAndGroupGoodsDetailsHelper(boolean isLive, RecyclerView recyclerView, GoodsAttrBean mData, OnClickStateChange stateChange) {
        this.mContext = recyclerView.getContext();
        this.mRecycler = recyclerView;
        this.mData = mData;
        this.isLive = isLive;
        this.stateChange = stateChange;
        initData();
    }



    /**
     * 组 列表
     */
    private void initData() {
        mRecycler.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        mRecycler.setAdapter(mAdapter = new CommRecyclerAdapter() {
            @Override
            protected void onBindView(BaseViewHolder holder, final int position) {
                LinearLayout mLLNum = holder.getView(R.id.mLLNum);
                ImageView mIvMin = holder.getView(R.id.mIvMin);
                ImageView mIvAdd = holder.getView(R.id.mIvAdd);
                final TextView mTvNum = holder.getView(R.id.mTvNum);
                TextView mTvSecName = holder.getView(R.id.mTvSecName);
                FlowTagLayout recyclerView = holder.getView(R.id.mSecRecycler);

                if (position == mData.getSpec().size()) {
                    mLLNum.setVisibility(View.VISIBLE);
                    mTvNum.setText(mData.getNum() + "");
                    mTvSecName.setText("数量");
                    mIvMin.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (mData.getNum() == 1) {
                                return;
                            }
                            mData.setNum(mData.getNum() - 1);
                            num = mData.getNum() + "";
                            mTvNum.setText(mData.getNum() + "");
                            if (!TextUtils.isEmpty(id) && stateChange != null) {
                                stateChange.onItemClick(id, spec_text, num,price);
                            }
                        }
                    });
                    mIvAdd.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (mData.getNum() >= mData.getNum_all()) {
                                HnToastUtils.showToastShort("已达最大够买量");
                                return;
                            }
                            mData.setNum(mData.getNum() + 1);
                            num = mData.getNum() + "";
                            mTvNum.setText(mData.getNum() + "");
                            if (!TextUtils.isEmpty(id) && stateChange != null)
                                stateChange.onItemClick(id, spec_text, num,price);
                        }
                    });
                } else {
                    mLLNum.setVisibility(View.GONE);
                    mTvSecName.setText(mData.getSpec().get(position).getGroup());
                    //组列表
                    initList(recyclerView, mData.getSpec().get(position).getList(), position, mData.getSpec().get(position).getGroup());
                }
                if (!isLive) {
                    mTvSecName.setTextColor(mContext.getResources().getColor(R.color.comm_text_color_black));
                }

            }

            @Override
            protected int getLayoutID(int position) {
                return R.layout.item_goods_att;
            }

            @Override
            public int getItemCount() {
                return mData.getSpec().size() + 1;
            }
        });
    }

    /**
     * 组item 列表
     */
    private void initList(FlowTagLayout mFlowLayout, final List<GoodsAttrBean.SpecEntity.ListEntity> list, final int cur, final String group) {
        final SpikeAndGroupSelectGoodModelAdapter mAdapter = new SpikeAndGroupSelectGoodModelAdapter(mContext, isLive);
        mFlowLayout.setAdapter(mAdapter);
        mAdapter.addDatas(list);
        mFlowLayout.setOnTagClickListener(new OnTagClickListener() {
            @Override
            public void onItemClick(FlowTagLayout parent, View view, int position) {
            }
        });
    }


    /**
     * 点击状态改变
     */
    public interface OnClickStateChange {
        void onItemClick(String id, String spec_text, String num, String price);

        void onItemClickNone();
    }

}
