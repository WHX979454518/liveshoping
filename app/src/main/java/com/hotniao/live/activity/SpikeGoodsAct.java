package com.hotniao.live.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.viewpager.widget.ViewPager;

import com.hn.library.base.BaseActivity;
import com.hn.library.global.HnUrl;
import com.hn.library.http.HnHttpUtils;
import com.hn.library.http.HnResponseHandler;
import com.hn.library.utils.HnDateUtils;
import com.hn.library.view.ColorCountDownText;
import com.hotniao.live.R;
import com.hotniao.live.adapter.SpikeScrollViewPagerAdapter;
import com.hotniao.live.model.SpikeTitleModel;
import com.hotniao.live.widget.SpikeSlidingTabLayout;
import com.live.shoplib.bean.OrderRefreshEvent;
import com.live.shoplib.bean.PayFinishEvent;
import com.live.shoplib.ui.frag.BaseScollFragment;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;



/**
 * 秒杀
 * 作者：Alan
 * 时间：2017/12/18
 */

public class SpikeGoodsAct extends BaseActivity {

    SpikeSlidingTabLayout mSlidingTab;
    RelativeLayout spike_tips_layout;
    TextView spike_type_text;
    TextView spike_count_down_time_tips;
    ColorCountDownText spike_count_down_time;
    ViewPager mViewPager;
    SpikeScrollViewPagerAdapter adapter;


    private boolean isSeller = false;
    private int pos = 0;

    private List<BaseScollFragment> mFragments = new ArrayList<>();
    public static void open(Context context) {
        context.startActivity(new Intent(context, SpikeGoodsAct.class));
    }

    @Override
    public int getContentViewId() {
        return R.layout.act_spike_goods;
    }

    @Override
    protected void onResume() {
        super.onResume();
        EventBus.getDefault().post(new OrderRefreshEvent(-1));
    }


    @Override
    public void onCreateNew(Bundle savedInstanceState) {
        setShowTitleBar(false);
        EventBus.getDefault().register(this);
        isSeller = getIntent().getBooleanExtra("isSeller", false);
        pos = getIntent().getIntExtra("pos", 0);
        initViews();
    }

    private void initViews() {
        findViewById(R.id.mIvBack).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mSlidingTab = findViewById(R.id.spike_mSlidingTab);
        spike_tips_layout = findViewById(R.id.spike_tips_layout);
        spike_type_text = findViewById(R.id.spike_type_text);
        spike_count_down_time = findViewById(R.id.spike_count_down_time);
        spike_count_down_time_tips = findViewById(R.id.spike_count_down_time_tips);
        mViewPager = findViewById(R.id.spike_view_pager);
    }


    @Override
    public void getInitData() {
        getSpikeAndGroupBuyData();
    }


    public void getSpikeAndGroupBuyData() {

        HnHttpUtils.postRequest(HnUrl.SPIKE_TIME_LIST, null, TAG, new HnResponseHandler<SpikeTitleModel>(SpikeTitleModel.class) {
            @Override
            public void hnSuccess(String response) {
                SpikeTitleModel.SpikeTitleItem[] strings = new SpikeTitleModel.SpikeTitleItem[model.getD().size()];
                bindData(model.getD().toArray(strings));
            }

            @Override
            public void hnErr(int errCode, String msg) {
            }
        });
    }

    private void bindData(final SpikeTitleModel.SpikeTitleItem[] dataArray) {
        if (dataArray == null || dataArray.length == 0) {
            return;
        }

        SpikeScrollViewPagerAdapter adapter = new SpikeScrollViewPagerAdapter(getSupportFragmentManager(), dataArray);

        mViewPager.setOffscreenPageLimit(mFragments.size());
        mViewPager.setAdapter(adapter);
        mSlidingTab.setViewPager(mViewPager, dataArray);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                //为了解决显示第一页时，界面上的倒计时没有刷新的问题
                if (position == 0) {
                    refreshCountDownInfo(dataArray, position);
                }
            }

            @Override
            public void onPageSelected(int position) {
                refreshCountDownInfo(dataArray, position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        mViewPager.setCurrentItem(getCurrentPosition(dataArray));
    }

    private void refreshCountDownInfo(SpikeTitleModel.SpikeTitleItem[] mTitle, int position) {

        if ("进行中".equals(mTitle[position].getSecondTitle())) {
            spike_type_text.setText("限时抢购");
            spike_count_down_time_tips.setText("距结束还剩");
            spike_count_down_time.setCountTime(mTitle[position].getEndtime() - HnDateUtils.getNowTimeStamp());
        }
        if ("即将开场".equals(mTitle[position].getSecondTitle())) {
            spike_type_text.setText("好戏马上开始");
            spike_count_down_time_tips.setText("距开始还有");
            spike_count_down_time.setCountTime(mTitle[position].getStarttime() - HnDateUtils.getNowTimeStamp());
        }
        if ("已结束".equals(mTitle[position].getSecondTitle())) {
            spike_type_text.setText("已结束");
            spike_count_down_time_tips.setText("");
            spike_count_down_time.setCountTime(0);
        }

    }


    private int getCurrentPosition(SpikeTitleModel.SpikeTitleItem[] dataArray) {
        for (int i = 0; i < dataArray.length; i++)
            if (dataArray[i].getSecondTitle().equals("进行中")) {
                return i;
            }
        return 0;
    }

    @Subscribe
    public void onFinishEvent(PayFinishEvent event) {
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
