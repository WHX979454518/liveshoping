package com.live.shoplib.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.hn.library.base.BaseActivity;
import com.live.shoplib.R;
import com.live.shoplib.ShopActFacade;

/**
 * 评价成功
 * 作者：Mr.Xu
 * 时间：2017/12/20
 */
@Route(path = "/shoplib/EvaResultAct")
public class EvaResultAct extends BaseActivity {


    private TextView mTvMsg;
    private TextView mTvOrder;
    private TextView mTvMain;

    private String order_id;

    @Override
    public int getContentViewId() {
        return R.layout.act_eva_result;
    }

    @Override
    public void onCreateNew(Bundle savedInstanceState) {
        setShowBack(true);
        setTitle("评价成功");
        order_id = getIntent().getStringExtra("order_id");
    }

    @Override
    public void getInitData() {

        mTvMsg = (TextView) findViewById(R.id.mTvMsg);
        mTvOrder = (TextView) findViewById(R.id.mTvOrder);
        mTvMain = (TextView) findViewById(R.id.mTvMain);
        mTvOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShopActFacade.openOrderDetails(order_id,false);
                finish();
            }
        });
        mTvMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
