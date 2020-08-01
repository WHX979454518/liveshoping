package com.hotniao.live.activity;

import android.os.Bundle;

import com.hn.library.base.BaseActivity;
import com.hotniao.live.R;
import com.hotniao.live.fragment.search.CollectGoodsFrag;
import com.hotniao.live.fragment.search.CollectShopFrag;
import com.hotniao.live.fragment.search.InTheStoreFragment;
import com.hotniao.live.fragment.search.MyShopFragment;
import com.reslibrarytwo.CommTabView;

import butterknife.BindView;

/**
 * 商家入驻
 */
public class TenantsActivity extends BaseActivity {
    @BindView(R.id.mCommTab)
    CommTabView mCommTab;

    @Override
    public int getContentViewId() {
        return R.layout.act_collection;
    }

    @Override
    public void onCreateNew(Bundle savedInstanceState) {
        setTitle("商家入驻");
        setShowBack(true);
        mCommTab.initColor(R.color.comm_text_color_black_hs, R.color.comm_text_color_black_hs, R.color.main_color);
        mCommTab.init(getResources().getStringArray(R.array.thestore), 0,new InTheStoreFragment(), new MyShopFragment());
    }

    @Override
    public void getInitData() {

    }
}
