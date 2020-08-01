package com.hotniao.live.fragment;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.DrawableRes;
import androidx.annotation.RequiresApi;
import androidx.viewpager.widget.ViewPager;

import com.bigkoo.convenientbanner.holder.Holder;
import com.hn.library.base.BaseFragment;
import com.hn.library.base.EventBusBean;
import com.hn.library.global.HnConstants;
import com.hn.library.global.HnUrl;
import com.hn.library.http.HnHttpUtils;
import com.hn.library.http.HnResponseHandler;
import com.hn.library.tab.SlidingTabLayout;
import com.hn.library.tab.listener.OnTabSelectListener;
import com.hn.library.utils.CommonUtils;
import com.hn.library.utils.HnBadgeView;
import com.hn.library.view.FrescoImageView;
import com.hotniao.live.R;
import com.hotniao.live.activity.HnHomeSearchActivity;
import com.hotniao.live.activity.HnMyMessageActivity;
import com.hotniao.live.adapter.TopBannerViewPagerAdapter;
import com.hotniao.live.eventbus.HomeCategoryClickEvent;
import com.hotniao.live.eventbus.RefreshEvent;
import com.hotniao.live.model.ConcentrationCategoryModel;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 作者：Mr.Xu
 * 时间：2018/2/2
 */
@RequiresApi(api = Build.VERSION_CODES.M)
public class HnHomeCusFrag extends BaseFragment implements ViewPager.OnPageChangeListener {


    @BindView(R.id.mRlSearch)
    View mRlSearch;
    @BindView(R.id.mIvMsg)
    ImageView mIvMsg;
    @BindView(R.id.mTvNewMsg)
    HnBadgeView mTvNewMsg;
    @BindView(R.id.home_sliding_tab)
    SlidingTabLayout home_sliding_tab;
    @BindView(R.id.goods_list_viewpager)
    ViewPager goods_list_viewpager;

    private List<ConcentrationCategoryModel.GoodsCategory> categoryList = new ArrayList<>();
    private static final int INVALID_POSITION = -1;


    @Override
    public int getContentViewId() {
        EventBus.getDefault().register(this);
        return R.layout.frag_home;
    }

    @Override
    public void initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    }


    @Override
    protected void initEvent() {
//        mSlidingTab.setOnTabSelectListener(new OnTabSelectListener() {
//            @Override
//            public void onTabSelect(int position) {
//                goods_list_viewpager.setCurrentItem(position);
//            }
//
//            @Override
//            public void onTabReselect(int position) {
//
//            }
//        });
//        goods_list_viewpager.addOnPageChangeListener(this);
    }

    @Override
    protected void initData() {
        requestData();
    }

    private void initTopTab(final String[] dataArray, String[] ids) {
        if (dataArray == null || dataArray.length == 0) {
            return;
        }
        TopBannerViewPagerAdapter adapter = new TopBannerViewPagerAdapter(getChildFragmentManager(), dataArray, ids);
        goods_list_viewpager.setAdapter(adapter);
        home_sliding_tab.setViewPager(goods_list_viewpager, dataArray);
        home_sliding_tab.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
            }

            @Override
            public void onTabReselect(int position) {

            }
        });
        goods_list_viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        goods_list_viewpager.setOffscreenPageLimit(dataArray.length);
        //todo 确定设置选中的index
        goods_list_viewpager.setCurrentItem(0);

    }




    @Subscribe
    public void refreshComplete(RefreshEvent event) {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }


    public void setRightDrawable(TextView textView, @DrawableRes int rightRes) {
        textView.setCompoundDrawablesWithIntrinsicBounds(0, 0, rightRes, 0);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }


    @OnClick({R.id.mRlSearch, R.id.mIvMsg})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.mRlSearch:
                mActivity.openActivity(HnHomeSearchActivity.class);
                break;
            case R.id.mIvMsg:
                mActivity.openActivity(HnMyMessageActivity.class);
                break;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void msgNoReadEvent(EventBusBean event) {
        if (HnConstants.EventBus.Update_Unread_Count.equals(event.getType())) {
            int noRead = (int) event.getObj();
            if (0 < noRead) {
                mTvNewMsg.setVisibility(View.VISIBLE);
            } else {
                mTvNewMsg.setVisibility(View.GONE);
            }
        }
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void switchCategoryEvent(HomeCategoryClickEvent event) {
        int position = getPositionByCategoryName(event.getCategory());
        if (position != INVALID_POSITION) {
            //因为上面的tab在前面多加了一个精选，所以将position加1才能定位到相应的item
            goods_list_viewpager.setCurrentItem(position + 1);
        }
    }

    private int getPositionByCategoryName(String category) {
        for (int i = 0; i < categoryList.size(); i++) {
            if (categoryList.get(i).getName().equals(category)) {
                return i;
            }
        }
        return INVALID_POSITION;
    }

    public class NetworkImageHolderView implements Holder<String> {
        private FrescoImageView imageView;

        @Override
        public View createView(Context context) {
            imageView = (FrescoImageView) LayoutInflater.from(getContext()).inflate(R.layout.banner_item, null);
            return imageView;
        }

        @Override
        public void UpdateUI(Context context, int position, String data) {
            imageView.setImageURI(data);
        }
    }

    public void requestData() {
        getCategoryData();
    }

    public void getCategoryData() {
        HnHttpUtils.postRequest(HnUrl.CONCENTRATION_GOODS_CATEGORY, null, TAG, new HnResponseHandler<ConcentrationCategoryModel>(ConcentrationCategoryModel.class) {
            @Override
            public void hnSuccess(String response) {
                if (!CommonUtils.hasItem(model.getD())) {
                    return;
                }
                categoryList.clear();
                categoryList.addAll(model.getD());
                String[] titleStrings = new String[categoryList.size() + 1];
                String[] ids = new String[categoryList.size()];
                // 添加精选
                titleStrings[0] = "精选";
                for (int i = 0; i < categoryList.size(); i++) {
                    titleStrings[i + 1] = categoryList.get(i).getName();
                    ids[i] = categoryList.get(i).getId();
                }
                initTopTab(titleStrings, ids);
            }

            @Override
            public void hnErr(int errCode, String msg) {
            }
        });
    }


}
