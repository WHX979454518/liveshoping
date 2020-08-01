package com.hotniao.live.activity;

import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.hn.library.http.HnResponseHandler;
import com.hn.library.utils.HnDateUtils;
import com.hn.library.utils.HnToastUtils;
import com.hn.library.utils.HnUtils;
import com.hn.library.view.FrescoImageView;
import com.hn.library.base.baselist.BaseViewHolder;
import com.hn.library.base.baselist.CommRecyclerAdapter;
import com.hn.library.utils.HnRefreshDirection;
import com.hn.library.global.HnUrl;
import com.hotniao.live.R;
import com.reslibrarytwo.CommListActivity;
import com.hotniao.live.model.HnLivePlayBackModel;
import com.hotniao.live.utils.HnUiUtils;
import com.loopj.android.http.RequestParams;

import java.util.ArrayList;
import java.util.List;

/**
 * Copyright (C) 2016,深圳市红鸟网络科技股份有限公司 All rights reserved.
 * 类描述：主播相关
 * 创建人：刘勇
 * 创建时间：2017/2/22 16:22
 * 修改人：刘勇
 * 修改时间：2017/2/22 16:34
 * 修改备注：
 * Version:  1.0.0
 */

public class HnAnchorRelatedActivity extends CommListActivity {

    private CommRecyclerAdapter mAdapter;
    private List<HnLivePlayBackModel.DBean.VideosBean.ItemsBean> mData = new ArrayList<>();

    @Override
    protected String setTitle() {
        return HnUiUtils.getString(R.string.anchor_related);
    }

    @Override
    protected CommRecyclerAdapter setAdapter() {
        setShowSubTitle(true);
        mSubtitle.setText(R.string.my_admin);
        mSubtitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity(HnMyAdminActivity.class);
            }
        });

        mAdapter = new CommRecyclerAdapter() {
            @Override
            protected void onBindView(BaseViewHolder holder, final int position) {
                ((TextView) holder.getView(R.id.mTvTime)).setText(HnDateUtils.stampToDateMm(mData.get(position).getStart_time()) + "-" + HnDateUtils.stampToDateMm(mData.get(position).getEnd_time()));
                ((TextView) holder.getView(R.id.mTvLong)).setText(mData.get(position).getTime() + "s");
                ((TextView) holder.getView(R.id.mTvTitle)).setText(TextUtils.isEmpty(mData.get(position).getTitle()) ? getString(R.string.no_title) : mData.get(position).getTitle());
                ((FrescoImageView) holder.getView(R.id.mIvImg)).setImageURI(HnUrl.setImageUri(mData.get(position).getImage_url()));
                String mCateName = "";
                for (String item : mData.get(position).getCategory_name()) {
                    mCateName = mCateName + item + "   ";
                }
                ((TextView) holder.getView(R.id.mTvType)).setText(mCateName);


                ((TextView) holder.getView(R.id.mTvNum)).setText(HnUtils.setNoPoint(mData.get(position).getOnlines()) + getString(R.string.people_look));
//                if(!TextUtils.isEmpty(mData.get(position).getOnlines())){
//                    double mOnlines = Double.parseDouble(mData.get(position).getOnlines());
//                    if (mOnlines > 10000) {
//                        String result = HnUtils.setDataDigit(mOnlines);
//                        ((TextView) holder.getView(R.id.mTvNum)).setText(result + getString(R.string.ten_thousand)+getString(R.string.people_look));
//                    } else {
//                        ((TextView) holder.getView(R.id.mTvNum)).setText(mData.get(position).getOnlines()+getString(R.string.people_look));
//                    }
//                }else {
//                    ((TextView) holder.getView(R.id.mTvNum)).setText(0+getString(R.string.people_look));
//                }

                holder.getView(R.id.mLlClick).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        HnPlayBackVideoActivity.luncher(HnAnchorRelatedActivity.this, "", mData.get(position).getPlayback_url(),"");
                    }
                });


            }

            @Override
            protected int getLayoutID(int position) {
                return R.layout.adapter_anchor_relate;
            }

            @Override
            public int getItemCount() {
                return mData.size();
            }
        };

        return mAdapter;
    }

    @Override
    protected RequestParams setRequestParam() {
        RequestParams params = new RequestParams();
        return params;
    }

    @Override
    protected String setRequestUrl() {
        return HnUrl.LIVE_PLAYBACK_ANCHOR;
    }

    @Override
    protected HnResponseHandler setResponseHandler(final HnRefreshDirection state) {
        return new HnResponseHandler<HnLivePlayBackModel>(HnLivePlayBackModel.class) {
            @Override
            public void hnSuccess(String response) {
                if (isFinishing()) return;
                refreshFinish();
                if (model.getD().getVideos() == null) {
                    setEmpty(getString(R.string.now_no_back_video), R.drawable.empty_com);
                    return;
                }
                if (HnRefreshDirection.TOP == state) {
                    mData.clear();
                }
                mData.addAll(model.getD().getVideos().getItems());
                if (mAdapter != null)
                    mAdapter.notifyDataSetChanged();
                setEmpty(getString(R.string.now_no_back_video), R.drawable.empty_com);
            }

            @Override
            public void hnErr(int errCode, String msg) {
                if (isFinishing()) return;
                refreshFinish();
                HnToastUtils.showToastShort(msg);
                setEmpty(getString(R.string.now_no_back_video), R.drawable.empty_com);
            }
        };
    }
}
