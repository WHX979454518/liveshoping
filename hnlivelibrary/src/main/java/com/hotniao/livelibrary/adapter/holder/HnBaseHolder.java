package com.hotniao.livelibrary.adapter.holder;

import android.view.View;
import android.widget.TextView;

import com.hotniao.livelibrary.R;
import com.hotniao.livelibrary.model.bean.HnReceiveSocketBean;
import com.hotniao.livelibrary.model.bean.ReceivedSockedBean;
import com.reslibrarytwo.HnSkinTextView;
import com.reslibrarytwo.LevelView;


/**
 * Copyright (C) 2017,深圳市红鸟网络科技股份有限公司 All rights reserved.
 * 项目名称：红鸟电商
 * 类描述：直播间消息   --- 基Holder
 * 创建人：阳石柏
 * 创建时间：2017/3/6 16:16
 * 修改人：阳石柏
 * 修改时间：2017/3/6 16:16
 * 修改备注：
 * Version:  1.0.0
 */
public class HnBaseHolder {

    private HnReceiveSocketBean mData;
    public HnSkinTextView mLevel;
    public LevelView mLevelView;
    public  TextView           mContent;

    public HnBaseHolder(View convertView) {
        mLevel = (HnSkinTextView) convertView.findViewById(R.id.tv_level);
        mLevelView =  convertView.findViewById(R.id.mLevelView);
        mContent = (TextView) convertView.findViewById(R.id.tv_content);
    }

    public void setData(HnReceiveSocketBean data) {
        mData = data;
    }

    public HnReceiveSocketBean getData() {
        return mData;
    }
}
