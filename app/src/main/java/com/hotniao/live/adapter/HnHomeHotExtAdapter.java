package com.hotniao.live.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hotniao.live.LiveItemProxy;
import com.hotniao.live.R;
import com.hotniao.live.model.HnHomeLiveModel;

/**
 * Copyright (C) 2017,深圳市红鸟网络科技股份有限公司 All rights reserved.
 * 项目名称：红鸟电商
 * 类描述：首页 热门
 * 创建人：mj
 * 创建时间：2017/3/6 16:16
 * 修改人：阳石柏
 * 修改时间：2017/3/6 16:16
 * 修改备注：
 * Version:  1.0.0
 */
public class HnHomeHotExtAdapter extends BaseQuickAdapter<HnHomeLiveModel.DEntity.StoreEntity.ItemsEntity, BaseViewHolder> {


    public HnHomeHotExtAdapter() {
        super(R.layout.item_comm_live);
    }

    @Override
    protected void convert(BaseViewHolder helper, final HnHomeLiveModel.DEntity.StoreEntity.ItemsEntity item) {

        LiveItemProxy.setItemView_Live(helper,item,"热门-关注-附近");

    }



}
