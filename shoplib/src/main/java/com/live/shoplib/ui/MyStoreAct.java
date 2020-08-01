package com.live.shoplib.ui;

import android.os.Bundle;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.hn.library.base.BaseActivity;
import com.live.shoplib.R;
import com.live.shoplib.ShopActFacade;

/**
 * create by Mr.x
 * on 2018/5/3
 */
@Route(path = "/shoplib/MyStoreAct")
public class MyStoreAct extends BaseActivity {
    @Override
    public int getContentViewId() {
        return R.layout.act_my_store;
    }

    @Override
    public void onCreateNew(Bundle savedInstanceState) {
        setShowBack(true);
        setTitle("我的店铺");
    }

    @Override
    public void getInitData() {
        findViewById(R.id.mTvGoods).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShopActFacade.openStoreGoods();
            }
        });
        findViewById(R.id.mTvType).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShopActFacade.openGoodsType(getIntent().getStringExtra("store_id"));
            }
        });
    }
}
