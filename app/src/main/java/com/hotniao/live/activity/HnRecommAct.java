package com.hotniao.live.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.hn.library.base.BaseActivity;
import com.hn.library.global.HnUrl;
import com.hn.library.http.HnHttpUtils;
import com.hn.library.http.HnResponseHandler;
import com.hn.library.loadstate.HnLoadingLayout;
import com.hn.library.utils.HnToastUtils;
import com.hotniao.live.R;
import com.hotniao.live.fragment.HnRecommFrag;
import com.hotniao.live.model.RecommModel;
import com.loopj.android.http.RequestParams;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 推薦商鋪
 * 作者：Mr.Xu
 * 时间：2018/2/2
 */
public class HnRecommAct extends BaseActivity {
    @BindView(R.id.mViewPager)
    ViewPager mViewPager;
    @BindView(R.id.mLoading)
    HnLoadingLayout mLoading;
    private MyViewPagerAdapter mAdapter;
    private List<Fragment> fragments = new ArrayList<>();
    private String id;

    public static void open(Context context, String id) {
        context.startActivity(new Intent(context, HnRecommAct.class).putExtra("id", id));
    }

    @Override
    public int getContentViewId() {
        return R.layout.act_recomm;
    }

    @Override
    public void onCreateNew(Bundle savedInstanceState) {
        setShowBack(true);
        setTitle("推荐店铺");
        mLoading.setEmptyText("暂无店铺");
        mLoading.setEmptyImage(R.drawable.no_goods);
        id = getIntent().getStringExtra("id");
        if (TextUtils.isEmpty(id)) {
            HnToastUtils.showToastShort("暂无推荐信息");
            finish();
            return;
        }
    }

    @Override
    public void getInitData() {
        initViews();
        getDate();
    }

    private void initViews() {
        mViewPager.setPageTransformer(true, new ZoomOutPageTransformer());
        mAdapter = new MyViewPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mAdapter);
    }


    public void getDate() {
        RequestParams params = new RequestParams();
        params.put("id",id);
        HnHttpUtils.postRequest(HnUrl.RECOMMEND_STORE, params, TAG, new HnResponseHandler<RecommModel>(RecommModel.class) {
            @Override
            public void hnSuccess(String response) {
                if (isFinishing() || model.getD() == null) return;
                fragments.clear();
                for (int i = 0; i < model.getD().getItems().size(); i++) {
                    fragments.add(new HnRecommFrag(model.getD().getItems().get(i)));
                }
                mAdapter.notifyDataSetChanged();


                if (fragments.size() == 0) {
                    setLoadViewState(HnLoadingLayout.Empty, mLoading);
                } else {
                    mViewPager.setOffscreenPageLimit(fragments.size());
                    setLoadViewState(HnLoadingLayout.Success, mLoading);
                }
            }

            @Override
            public void hnErr(int errCode, String msg) {
                HnToastUtils.showToastShort(msg);
            }
        });
    }

    class MyViewPagerAdapter extends FragmentStatePagerAdapter {
        public MyViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }
    }


    public static class ZoomOutPageTransformer implements ViewPager.PageTransformer {
        private static final float MIN_SCALE = 0.9f;
        private static float defaultScale = 0.9f;

        public void transformPage(View view, float position) {
            int pageWidth = view.getWidth();
            int pageHeight = view.getHeight();

            if (position < -1) {
                view.setScaleX(defaultScale);
                view.setScaleY(defaultScale);
            } else if (position <= 1) {
                float scaleFactor = Math.max(MIN_SCALE, 1 - Math.abs(position));
                float vertMargin = pageHeight * (1 - scaleFactor) / 2;
                float horzMargin = pageWidth * (1 - scaleFactor) / 2;
                if (position < 0) {
                    view.setTranslationX(horzMargin - vertMargin / 2);
                } else {
                    view.setTranslationX(-horzMargin + vertMargin / 2);
                }
                view.setScaleX(scaleFactor);
                view.setScaleY(scaleFactor);
            } else {
                view.setScaleX(defaultScale);
                view.setScaleY(defaultScale);
            }
        }
    }

}
