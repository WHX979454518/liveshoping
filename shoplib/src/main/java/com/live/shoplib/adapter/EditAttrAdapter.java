package com.live.shoplib.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.live.shoplib.R;

import java.util.ArrayList;
import java.util.List;

public class EditAttrAdapter extends BaseAdapter {

    private final Context mContext;
    private ArrayList<String> mData = new ArrayList<>();

    public EditAttrAdapter(Context context) {
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
    public View getView(final int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_edit_attr, null);
            ViewHolder vh = new ViewHolder();
            vh.mTvTag = convertView.findViewById(R.id.mTvTag);
            vh.mIvDel = convertView.findViewById(R.id.mIvDel);
            convertView.setTag(vh);
        }

        ViewHolder vh = (ViewHolder) convertView.getTag();

        vh.mTvTag.setText(mData.get(position));

        vh.mTvTag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                delClick.click(mData.get(position),position);
            }
        });
        vh.mIvDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                delClick.clickDel(position);
            }
        });

        return convertView;
    }

    static class ViewHolder {
        TextView mTvTag;
        ImageView mIvDel;
    }

    public void setDelClick(onDelClick delClick) {
        this.delClick = delClick;
    }

    public onDelClick delClick;

    public interface onDelClick {
        void click(String value,int pos);

        void clickDel(int pos);
    }

    public void addDatas(ArrayList<String> datas) {
        if(datas==null)return;
        mData.clear();
        mData.addAll(datas);
        notifyDataSetChanged();
    }

    public ArrayList<String> getData(){
        return mData;
    }
    public void add(String value){
        mData.add(value);
        notifyDataSetChanged();
    }
    public void remove(int pos){
        mData.remove(pos);
        notifyDataSetChanged();
    }
    public void replace(String value ,int pos){
        mData.remove(pos);
        mData.add(pos,value);
        notifyDataSetChanged();
    }

}

