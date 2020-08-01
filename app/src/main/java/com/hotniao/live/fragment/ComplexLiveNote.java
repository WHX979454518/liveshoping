package com.hotniao.live.fragment;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hn.library.global.HnUrl;
import com.hn.library.utils.HnDateUtils;
import com.hn.library.view.FrescoImageView;
import com.hotniao.live.R;
import com.hotniao.live.model.HnHomeModel;
import com.reslibrarytwo.marquee.MarqueeFactory;

/**
 * 作者：Mr.Xu
 * 时间：2018/2/6
 */
public class ComplexLiveNote extends MarqueeFactory<View, HnHomeModel.DEntity.PreviewEntity> {
    private LayoutInflater inflater;

    public ComplexLiveNote(Context mContext) {
        super(mContext);
        inflater = LayoutInflater.from(mContext);
    }

    @Override
    public View generateMarqueeItemView(HnHomeModel.DEntity.PreviewEntity data) {
        LinearLayout mView = (LinearLayout) inflater.inflate(R.layout.item_live_notice_home, null);
        FrescoImageView mIvIco = mView.findViewById(R.id.mIvIco);
        mIvIco.setImageURI(HnUrl.setImageUri(data.getUser_avatar()));
        TextView mTvName = mView.findViewById(R.id.mTvName);

        if (TextUtils.isEmpty(data.getStore_name())) {
            mTvName.setText(data.getUser_nickname());
        } else {
            mTvName.setText(data.getStore_name());
        }
        TextView mTvTitle = mView.findViewById(R.id.mTvTitle);
        mTvTitle.setText(data.getLive_preview_title());
        TextView mTvTime = mView.findViewById(R.id.mTvTime);
        mTvTime.setText(HnDateUtils.dateFormat(data.getLive_preview_time(), "HH:mm"));
        return mView;
    }
}