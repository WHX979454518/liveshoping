package com.hotniao.live.adapter;

import android.text.TextUtils;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hn.library.view.FrescoImageView;
import com.hotniao.live.R;
import com.hn.library.global.HnUrl;
import com.hotniao.live.model.bean.HnMyFocusBean;
import com.hotniao.live.utils.HnUiUtils;
import com.hotniao.livelibrary.util.HnLiveLevelUtil;
import com.reslibrarytwo.HnSkinTextView;

/**
 * Copyright (C) 2017,深圳市红鸟网络科技股份有限公司 All rights reserved.
 * 项目名称：红鸟电商
 * 类描述： 我的关注
 * 创建人：阳石柏
 * 创建时间：2017/3/6 16:16
 * 修改人：阳石柏
 * 修改时间：2017/3/6 16:16
 * 修改备注：
 * Version:  1.0.0
 */
public class HnMyFollowAdapter extends BaseQuickAdapter<HnMyFocusBean.FollowsBean.ItemsBean, BaseViewHolder> {

    public HnMyFollowAdapter() {
        super(R.layout.adapter_search_user);
    }


    @Override
    protected void convert(BaseViewHolder holder, HnMyFocusBean.FollowsBean.ItemsBean dataBean) {
        holder.addOnClickListener(R.id.mTvFocus);

        ((FrescoImageView) holder.getView(R.id.fiv_header)).setImageURI(HnUrl.setImageUri(dataBean.getUser_avatar()));
        ((TextView) holder.getView(R.id.tv_name)).setText(dataBean.getUser_nickname());
//        ((TextView) holder.getView(R.id.tv_des)).setText(HnUiUtils.getString(R.string.fans_m) +dataBean.getUser_fans_total());
//        ((TextView) holder.getView(R.id.tv_des)).setText(dataBean.getUser_intro());
        ((TextView) holder.getView(R.id.tv_des)).setText(TextUtils.isEmpty(dataBean.getUser_intro())?"Ta好像忘记签名了~":dataBean.getUser_intro());
        //我的等级
        HnSkinTextView tvUserLevel=holder.getView(R.id.tv_user_level);
        HnLiveLevelUtil.setAudienceLevBg(tvUserLevel,dataBean.getUser_level(),true);
        final TextView mTvFocus = holder.getView(R.id.mTvFocus);
        if ("Y".equals(dataBean.getIs_follow())) {
            mTvFocus.setText(R.string.main_follow_on);
            mTvFocus.setSelected(false);
        } else {
            mTvFocus.setText(R.string.add_follow);
            mTvFocus.setSelected(true);
        }

    }
}