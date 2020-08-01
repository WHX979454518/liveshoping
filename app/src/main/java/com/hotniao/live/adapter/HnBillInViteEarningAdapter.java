package com.hotniao.live.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hn.library.utils.HnDateUtils;
import com.hn.library.utils.HnStringUtils;
import com.hn.library.utils.HnUiUtils;
import com.hn.library.utils.HnUtils;
import com.hotniao.live.HnApplication;
import com.hotniao.live.R;
import com.hotniao.live.model.HnBillInviteDetailModel;

import java.util.List;


/**
 * Copyright (C) 2017,深圳市红鸟网络科技股份有限公司 All rights reserved.
 * 类描述：邀请收益
 * 创建人：Kevin
 * 创建时间：2017/3/6 16:16
 * 修改人：Kevin
 * 修改时间：2017/3/6 16:16
 * 修改备注：
 * Version:  1.0.0
 */
public class HnBillInViteEarningAdapter extends BaseQuickAdapter<HnBillInviteDetailModel.DBean.RecordListBean.ItemsBean, BaseViewHolder> {

    public HnBillInViteEarningAdapter(List<HnBillInviteDetailModel.DBean.RecordListBean.ItemsBean> mData) {
        super(R.layout.adapter_bill_invite_earning,mData);
    }

    @Override
    protected void convert(BaseViewHolder helper, HnBillInviteDetailModel.DBean.RecordListBean.ItemsBean item) {
        String type="";
        if("1".equals(item.getUser().getInvite_level()))type="一";
        else if("2".equals(item.getUser().getInvite_level()))type="二";
        else if("3".equals(item.getUser().getInvite_level()))type="三";
        helper.setText(R.id.mTvDay,type+ HnUiUtils.getString(R.string.a_few_grade_distribution)+"-"+item.getUser().getUser_nickname()+"-"+item.getInvite().getReward_type());

        helper.setText(R.id.mTvPrice,item.getInvite().getConsume()+ HnApplication.getmConfig().getCoin());
        helper.setText(R.id.mTvTime, HnDateUtils.dateFormat(item.getInvite().getTime(),"yyyy-MM-dd HH:mm"));

    }

}

