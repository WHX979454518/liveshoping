package com.hotniao.live.activity;

import android.os.Bundle;

import com.hn.library.base.BaseActivity;
import com.hotniao.live.R;
import com.hotniao.live.fragment.HnConsumeHisExchargeFrag;
import com.hotniao.live.fragment.HnConsumeHisPayFrag;
import com.hotniao.live.fragment.search.CollectGoodsFrag;
import com.hotniao.live.fragment.search.CollectShopFrag;
import com.reslibrarytwo.CommTabView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 作者：Mr.Xu
 * 时间：2018/1/5
 */
public class CollectionAct extends BaseActivity {
    @BindView(R.id.mCommTab)
    CommTabView mCommTab;

    @Override
    public int getContentViewId() {
        return R.layout.act_collection;
    }

    @Override
    public void onCreateNew(Bundle savedInstanceState) {
        setTitle("我的收藏");
        setShowBack(true);
        mCommTab.initColor(R.color.main_color, R.color.comm_text_color_black_hs, R.color.main_color);
        mCommTab.init(getResources().getStringArray(R.array.collection), 0,new CollectGoodsFrag(), new CollectShopFrag());
    }

    @Override
    public void getInitData() {

    }
}
