package com.live.shoplib.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.hn.library.base.BaseActivity;
import com.live.shoplib.R;
import com.live.shoplib.ShopActFacade;

/**
 * 作者：Mr.Xu
 * 时间：2018/1/8
 */
@Route(path = "/shoplib/SureOrderAct")
public class SureOrderAct extends BaseActivity {

    private TextView mTvBack;
    private TextView mTvGo;


    @Override
    public int getContentViewId() {
        return R.layout.act_sure_order;
    }

    @Override
    public void onCreateNew(Bundle savedInstanceState) {

        setTitle("收货成功");
        setShowBack(true);

        final String order_id = getIntent().getStringExtra("order_id");

        mTvBack = (TextView) findViewById(R.id.mTvBack);
        mTvGo = (TextView) findViewById(R.id.mTvGo);

        mTvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mTvGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShopActFacade.openEvaPublic(order_id);
                finish();
            }
        });
    }

    @Override
    public void getInitData() {

    }
}
