package com.live.shoplib.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.live.shoplib.R;
import com.live.shoplib.bean.GoodsAttrBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Copyright (C) 2016,深圳市红鸟网络科技股份有限公司 All rights reserved.
 * 类描述：主页面
 * 创建人：刘勇
 * 创建时间：2017/2/22 16:22
 * 修改人：刘勇
 * 修改时间：2017/2/22 16:34
 * 修改备注：
 * Version:  1.0.0
 */

public class SpikeAndGroupSelectGoodModelAdapter extends BaseAdapter {

    private final Context mContext;
    private List<GoodsAttrBean.SpecEntity.ListEntity> mDataList;
    private boolean isLive = false;
    private int mSelectItem = -1;

    public SpikeAndGroupSelectGoodModelAdapter(Context context, boolean isLive) {
        this.mContext = context;
        this.isLive = isLive;
        mDataList = new ArrayList<>();
    }

    public void setSelect(int position) {
        mSelectItem = position;
    }

    @Override
    public int getCount() {
        return mDataList.size();
    }

    @Override
    public Object getItem(int position) {
        return mDataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.adapter_select_goods_model, null);
            ViewHolder vh = new ViewHolder();
            vh.mBoxAtt = (TextView) convertView.findViewById(R.id.tv_tag);
            convertView.setTag(vh);
        }
        ViewHolder vh = (ViewHolder) convertView.getTag();

        //秒杀和团购默认给它选中所以直接设置成蓝色的背景
        if (!isLive) {
            vh.mBoxAtt.setBackgroundResource(R.drawable.shape_soild_main_4);
        }
        vh.mBoxAtt.setText(mDataList.get(position).getSpec_value());
       //秒杀和团购只是存在一种sku默认给它选中所以直接设置为true
        vh.mBoxAtt.setSelected(true);
        //秒杀和团购默认给它选中所以直接设置成白色字体
        if (!isLive) {
            vh.mBoxAtt.setTextColor(mContext.getResources().getColor(R.color.white));
        }

        return convertView;
    }

    public void addDatas(List<GoodsAttrBean.SpecEntity.ListEntity> datas) {
        mDataList.clear();
        mDataList.addAll(datas);
        notifyDataSetChanged();
    }

    static class ViewHolder {
        TextView mBoxAtt;
    }

}

