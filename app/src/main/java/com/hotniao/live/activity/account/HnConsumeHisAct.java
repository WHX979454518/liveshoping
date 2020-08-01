package com.hotniao.live.activity.account;

import android.os.Bundle;

import com.hn.library.base.BaseActivity;
import com.hotniao.live.R;
import com.hotniao.live.fragment.HnConsumeHisExchargeFrag;
import com.hotniao.live.fragment.HnConsumeHisPayFrag;
import com.reslibrarytwo.CommTabView;

import butterknife.BindView;

/**
 * 消费记录
 * 作者：Mr.Xu
 * 时间：2018/1/5
 */
public class HnConsumeHisAct extends BaseActivity {
    @BindView(R.id.mCommTab)
    CommTabView mCommTab;

    @Override
    public int getContentViewId() {
        return R.layout.act_comsume_his;
    }

    @Override
    public void onCreateNew(Bundle savedInstanceState) {
        setTitle("消费记录");
        setShowBack(true);
        mCommTab.initColor(R.color.main_color, R.color.comm_text_color_black_hs, R.color.main_color);
        mCommTab.init(getResources().getStringArray(R.array.comsume_his), 0,new HnConsumeHisExchargeFrag(), new HnConsumeHisPayFrag());
    }

    @Override
    public void getInitData() {

    }

}
