package com.hotniao.live.adapter;

import com.alibaba.android.arouter.utils.TextUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hn.library.utils.HnDateUtils;
import com.hn.library.utils.HnStringUtils;
import com.hotniao.live.HnApplication;
import com.hotniao.live.R;
import com.hotniao.live.model.HnExchangeListModel;
import com.hotniao.live.model.HnReceiveGiftLogModel;

import java.util.List;


/**
 * Copyright (C) 2017,深圳市红鸟网络科技股份有限公司 All rights reserved.
 * 项目名称：红鸟电商
 * 类描述：账单明细 -- 收礼
 * 创建人：Kevin
 * 创建时间：2017/3/6 16:16
 * 修改人：Kevin
 * 修改时间：2017/3/6 16:16
 * 修改备注：
 * Version:  1.0.0
 */
public class HnExchangeAdapter extends BaseQuickAdapter<HnExchangeListModel.DEntity.ItemsEntity, BaseViewHolder> {

    public HnExchangeAdapter(List<HnExchangeListModel.DEntity.ItemsEntity> data) {
        super(R.layout.adapter_bill_receive_gift, data);
    }


    @Override
    protected void convert(BaseViewHolder helper, HnExchangeListModel.DEntity.ItemsEntity item) {

        //昵称
        helper.setText(R.id.mTvPrice, item.getExchange_dot() + "金币");
        //礼物名
        helper.setText(R.id.mTvContent, "兑换" + item.getExchange_coin() + "银币");

        //时间
        String add_time = item.getCreate_time();
        if (!TextUtils.isEmpty(add_time)) {
            helper.setText(R.id.mTvTime, HnDateUtils.dateFormat(add_time, "yyyy-MM-dd HH:mm"));
        } else {
            helper.setText(R.id.mTvTime, "");
        }

    }

}

