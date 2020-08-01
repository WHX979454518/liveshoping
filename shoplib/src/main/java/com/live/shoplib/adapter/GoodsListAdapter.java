package com.live.shoplib.adapter;

import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hn.library.global.HnUrl;
import com.hn.library.view.FrescoImageView;
import com.live.shoplib.R;
import com.live.shoplib.ShopActFacade;
import com.live.shoplib.bean.GoodsListItemBean;
import com.live.shoplib.bean.ShopItemBean.DBean.GoodsBean.ItemsBean;

import java.util.ArrayList;


/**
 * Copyright (C) 2017,深圳市红鸟网络科技股份有限公司 All rights reserved.
 * Version:  1.0.0
 */
public class GoodsListAdapter extends BaseQuickAdapter<GoodsListItemBean.DBean.GoodsBean.ItemsBean, BaseViewHolder> {

    public boolean sace = false;

    public GoodsListAdapter(ArrayList<GoodsListItemBean.DBean.GoodsBean.ItemsBean> data, boolean sace) {
        super(sace ? R.layout.item_main_shop : R.layout.item_main_shop_l, data);
        this.sace = sace;
    }

    @Override
    protected void convert(BaseViewHolder helper, final GoodsListItemBean.DBean.GoodsBean.ItemsBean item) {
        FrescoImageView mIvGoods = helper.getView(R.id.mIvGoods);
        mIvGoods.setImageURI(HnUrl.setImageUri(item.getGoods_img()));
        helper.setText(R.id.mTvTitle, item.getGoods_name());
        helper.setText(R.id.mTvPrice, item.getGoods_price());
        helper.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShopActFacade.openGoodsDetails(item.getGoods_id());
            }
        });
    }


}

