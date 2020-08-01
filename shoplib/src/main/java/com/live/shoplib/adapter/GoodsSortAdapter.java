package com.live.shoplib.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.live.shoplib.R;

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
public class GoodsSortAdapter extends BaseAdapter {

    private final Context mContext;
    private List<String> mData = new ArrayList<>();

    public GoodsSortAdapter(Context context) {
        this.mContext = context;
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int position) {
        return mData.get(position);
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
            vh.mBoxAtt = convertView.findViewById(R.id.tv_tag);
            convertView.setTag(vh);
        }
        ViewHolder vh = (ViewHolder) convertView.getTag();

        vh.mBoxAtt.setBackgroundResource(R.drawable.sel_item);
        vh.mBoxAtt.setText(getOption(mData.get(position)));

        vh.mBoxAtt.setSelected(checkOption(mData.get(position)));
        vh.mBoxAtt.setTextColor(mContext.getResources().getColor(checkOption(mData.get(position)) ? R.color.white : R.color.comm_text_color_black));

        return convertView;
    }

    public void addDatas(ArrayList<String> datas) {
        if(datas==null)return;
        mData.clear();
        mData.addAll(datas);
        notifyDataSetChanged();
    }

    static class ViewHolder {
        TextView mBoxAtt;
    }

    public String getOption(String option) {
        return option.substring(1, option.length());
    }

    public boolean checkOption(String option) {
        try {
            return TextUtils.equals("*", option.substring(0, 1));
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }

}

