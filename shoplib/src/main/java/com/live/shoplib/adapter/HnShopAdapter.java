package com.live.shoplib.adapter;

import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hn.library.global.HnUrl;
import com.hn.library.view.FrescoImageView;
import com.live.shoplib.R;
import com.live.shoplib.ShopActFacade;
import com.live.shoplib.bean.ShopItemBean.DBean.GoodsBean.ItemsBean;

import java.util.ArrayList;


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
public class HnShopAdapter extends BaseQuickAdapter<ItemsBean, BaseViewHolder> {

    public boolean sace = false;

    public HnShopAdapter(ArrayList<ItemsBean> data, boolean sace) {
        super(sace ? R.layout.item_main_shop : R.layout.item_main_shop_l, data);
        this.sace = sace;
    }


    @Override
    protected void convert(BaseViewHolder helper, final ItemsBean item) {

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

