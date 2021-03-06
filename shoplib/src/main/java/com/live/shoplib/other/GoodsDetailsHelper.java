package com.live.shoplib.other;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
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
import com.live.shoplib.adapter.SelectGoodModelAdapter;
import com.live.shoplib.bean.GoodsAttrBean;
import com.live.shoplib.widget.FlowTag.FlowTagLayout;
import com.live.shoplib.widget.FlowTag.OnTagClickListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * 处理商品-属性参数
 * 作者：Mr.Xu
 * 时间：2017/12/14
 */
public class GoodsDetailsHelper {

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

    public GoodsDetailsHelper(boolean isLive, RecyclerView recyclerView, GoodsAttrBean mData, OnClickStateChange stateChange) {
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
                                HnToastUtils.showToastShort("已达最大库存");
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
        final SelectGoodModelAdapter mAdapter = new SelectGoodModelAdapter(mContext, isLive);
        mFlowLayout.setAdapter(mAdapter);
        mAdapter.addDatas(list);
        mFlowLayout.setOnTagClickListener(new OnTagClickListener() {
            @Override
            public void onItemClick(FlowTagLayout parent, View view, int position) {
                setItemListClick(list, position, group, cur);
            }
        });
    }

    private void setItemListClick(List<GoodsAttrBean.SpecEntity.ListEntity> list, int position, String group, int cur) {
        //设置反选中
        list.get(position).setCheck(!list.get(position).isCheck());
        //计算数据
        checkData(group, list.get(position).getId(), cur, !list.get(position).isCheck());
    }

    @SuppressLint("LongLogTag")
    private void isFinishCheck() {
        if (mSet.size() >= mData.getSpec().size()) {
            ArrayList<String> list = new ArrayList();
            //已达道选中最大
            for (int i = 0; i < mSet.size(); i++) {
                for (int j = 0; j < mData.getSpec().size(); j++) {
                    if (mSet.get(i).containsKey(mData.getSpec().get(j).getGroup())) {
                        list.add(mSet.get(i).get(mData.getSpec().get(j).getGroup()));
                    }
                }
            }
            Collections.sort(list, new Comparator<String>() {
                @Override
                public int compare(String o1, String o2) {
                    if (TextUtils.isEmpty(o1) || TextUtils.isEmpty(o2)) return 0;
                    return Integer.valueOf(o1) - Integer.valueOf(o2);
                }
            });
            String value = list.toString();
            Log.e("$$$$$$$$$$$$$$$$$$$$", value);//[310, 321, 330]
            value = value.replace("[", "");
            value = value.replace("]", "");
            value = value.replace(",", ":");
            value = value.replace(" ", "");
            Log.e("$$$$$$$$$$$$$$$$$$$$-----", value);//[310, 321, 330]
            String all = "";
            for (int i = 0; i < mData.getSku().size(); i++) {
                if (TextUtils.equals(mData.getSku().get(i).getIds(), value)) {
                    id = mData.getSku().get(i).getSku_id();
                    spec_text = mData.getSku().get(i).getSpec_text();
                    all = mData.getSku().get(i).getStock();
                    num = mData.getNum() + "";
                    price = mData.getSku().get(i).getPrice();
                    stateChange.onItemClick(id, spec_text, num,price);
                    if (!TextUtils.isEmpty(all)) {
                        mData.setNum(1);
                        mData.setNum_all(Integer.valueOf(all));
                    }
                    return;
                }else {
                    spec_text = "";
                    num = "";
                    id = "";
                    price = "";
                }
                stateChange.onItemClick(id, spec_text, num,price);
            }
        } else {
            stateChange.onItemClickNone();
            //未达到商品属性选择
        }
    }

    /**
     * 计算算法
     *
     * @param group 组名如 颜色、大小
     * @param value TODO 所需要的值
     * @param cur   当前选中位置
     * @param check 是否选中 以前的状态
     */
    private void checkData(String group, String value, int cur, boolean check) {
        /*################ 获取匹配字符串 #################*/
        Map<String, String> map = new HashMap();
        map.put(group, value);
//        String match = ((LinkedTreeMap) mData.getSpec_sku()).get(value).toString();
        /*############### 计算状态 ################*/
        boolean hadAdd = hadKey(group);
        //是否选中
        if (!check) {
            String valueTemp = getValue4Set(group);
            addSet(check, map, group);//添加的值
            //是否有选过这个组
            if (hadAdd) {//当前组存在以前选过
                //把以前的值设置为可选
                for (int z = 0; z < mData.getSpec().get(cur).getList().size(); z++) {//只需遍历当前组
                    if (valueTemp.equals(mData.getSpec().get(cur).getList().get(z).getId())) {//匹配到以前的值设置为可选状态
                        mData.getSpec().get(cur).getList().get(z).setCheck(false);
                    }
                }
            }
        } else {
            if (mSet.contains(map)) mSet.remove(map);
        }
        initData();
        isFinishCheck();
    }


    public boolean hadKey(String group) {
        for (int i = 0; i < mSet.size(); i++) {
            if (mSet.get(i).containsKey(group)) {
                return true;
            }
        }
        return false;
    }


    public synchronized void addSet(boolean check, Map<String, String> map, String group) {
        if (check) {
        } else {
            for (int i = 0; i < mSet.size(); i++) {
                if (mSet.get(i).containsKey(group)) {
                    mSet.remove(mSet.get(i));
                }
            }
            mSet.add(map);
        }
    }

    private synchronized String getValue4Set(String group) {
        Iterator<Map<String, String>> it = mSet.iterator();
        while (it.hasNext()) {
            Map<String, String> map = it.next();
            if (map.containsKey(group)) {
                return map.get(group);
            }
        }
        return "";
    }

    /**
     * 点击状态改变
     */
    public interface OnClickStateChange {
        void onItemClick(String id, String spec_text, String num,String price);

        void onItemClickNone();
    }

}
