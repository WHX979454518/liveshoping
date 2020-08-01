package com.hotniao.live.fragment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
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
public class ComplexLAnno extends MarqueeFactory<View, HnHomeModel.DEntity.OfficialEntity> {
    private LayoutInflater inflater;

    public ComplexLAnno(Context mContext) {
        super(mContext);
        inflater = LayoutInflater.from(mContext);
    }

    @Override
    public View generateMarqueeItemView(HnHomeModel.DEntity.OfficialEntity data) {
        LinearLayout mView = (LinearLayout) inflater.inflate(R.layout.item_anno_home, null);
        TextView mTvAnno = mView.findViewById(R.id.mTvAnno);
        mTvAnno.setText(data.getOfficial_news_title());
        return mView;
    }
}