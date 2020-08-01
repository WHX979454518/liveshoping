package com.hotniao.live.fragment;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.viewpager.widget.ViewPager;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.holder.Holder;
import com.bigkoo.convenientbanner.listener.OnItemClickListener;
import com.bumptech.glide.Glide;
import com.hn.library.banner.HnBannerLayout;
import com.hn.library.base.BaseFragment;
import com.hn.library.global.HnUrl;
import com.hn.library.http.HnHttpUtils;
import com.hn.library.http.HnResponseHandler;
import com.hn.library.tab.SlidingTabLayout;
import com.hn.library.utils.HnToastUtils;
import com.hotniao.live.R;
import com.hotniao.live.activity.HnSystemAct;
import com.hotniao.live.activity.HnWebActivity;
import com.hotniao.live.adapter.FindSourceScrollViewPagerAdapter;
import com.hotniao.live.model.ClassificationModel;
import com.hotniao.live.model.HnBannerModel;
import com.hotniao.live.model.SpikeTitleModel;
import com.hotniao.live.utils.HnUiUtils;
import com.live.shoplib.ShopActFacade;

import java.util.ArrayList;
import java.util.List;

/**
 * 创建人：Alan
 * 使用 LittleVideoFragment
 */
public class FindSourceFragment extends BaseFragment {

    SlidingTabLayout mSlidingTab;
    ViewPager mViewPager;
    ConvenientBanner mBanner;
    private List<String> imgUrl = new ArrayList<>();


    @Override
    public int getContentViewId() {

        return R.layout.fra_find_source_video;
    }

    @Override
    public void initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mSlidingTab = mRootView.findViewById(R.id.find_source_mSlidingTab);
        mViewPager = mRootView.findViewById(R.id.find_source_view_pager);
        mBanner = mRootView.findViewById(R.id.find_source_convenientBanner);

    }

    //todo 根据数据绑定banner
    private void bindBannerData(final List<HnBannerModel.DEntity.CarouselEntity> bannerBeen) {
        if (bannerBeen.size() == 0) {
            imgUrl.clear();
            imgUrl.add(HnBannerLayout.NO_IMAGE);
        } else {
            imgUrl.clear();
            for (int i = 0; i < bannerBeen.size(); i++) {
                imgUrl.add(HnUrl.setImageUrl(bannerBeen.get(i).getCarousel_url()));
            }
        }
        mBanner.setPages(
                new CBViewHolderCreator<NetworkImageHolderView>() {
                    @Override
                    public NetworkImageHolderView createHolder() {
                        return new NetworkImageHolderView();
                    }
                }, imgUrl).startTurning(3000)
                .setPageIndicator(new int[]{R.drawable.indicator_point_select, R.drawable.indicator_point_nomal})
                .setOnItemClickListener(new OnItemClickListener() {
                    @Override
                    public void onItemClick(int position) {
                        //TODO 轮播点击
                        if (position < 0 || position > bannerBeen.size() - 1) return;
                        HnBannerModel.DEntity.CarouselEntity bean = bannerBeen.get(position);
                        if (bean != null) {

                            if (bean.getCarousel_jump() == null || TextUtils.isEmpty(bean.getCarousel_jump().getType())) {
                                String type = bean.getCarousel_href();
                                if (!TextUtils.isEmpty(type) && type.contains("http")) {//外链
                                    HnWebActivity.luncher(mActivity, HnUiUtils.getString(R.string.app_name), bean.getCarousel_href(), HnWebActivity.Banner);
                                } else {
                                    HnToastUtils.showToastShort(getString(R.string.invalid_url));
                                }
                            } else {
                                //	goods:商品详情，user:用户详情，sys_mag:系统消息
                                switch (bean.getCarousel_jump().getType()) {
                                    case "goods":
                                        ShopActFacade.openGoodsDetails(bean.getCarousel_jump().getData().getId());
                                        break;
                                    case "store":
                                        ShopActFacade.openShopDetails(bean.getCarousel_jump().getData().getId());
                                        break;
                                    case "sys_mag":
                                        mActivity.openActivity(HnSystemAct.class);
                                        break;
                                }
                            }
                        } else {
                            HnToastUtils.showToastShort(getString(R.string.invalid_url));
                        }
                    }
                });
    }

    public class NetworkImageHolderView implements Holder<String> {
        private ImageView imageView;

        @Override
        public View createView(Context context) {
            imageView = new ImageView(context);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            return imageView;
        }

        @Override
        public void UpdateUI(Context context, int position, String data) {
            Glide.with(getContext()).load(data).placeholder(com.hn.library.R.drawable.default_banner)
                    .error(com.hn.library.R.drawable.default_banner).crossFade().into(imageView);
        }
    }

    @Override
    protected void initEvent() {

    }


    private List<ClassificationModel.DBean> categoryList = new ArrayList<>();
    @Override
    protected void initData() {
        getLittleVideoCategoryData();
//        bindData(new String[]{"精选", "家庭", "水果", "肉类", "海鲜", "肉食"});
    }


    public void getLittleVideoCategoryData() {
        //todo 待确定获取短视频分类的接口
        HnHttpUtils.postRequest(HnUrl.TRACE_LIST, null, TAG, new HnResponseHandler<ClassificationModel>(ClassificationModel.class) {
            @Override
            public void hnSuccess(String response) {
                //todo 处理返回的信息
                categoryList.clear();
                categoryList.addAll(model.getD());
                String[] titleStrings = new String[categoryList.size()];
                String[] ids = new String[categoryList.size()];
                for (int i = 0; i < categoryList.size(); i++) {
                    titleStrings[i] = categoryList.get(i).getField();
                    ids[i] = categoryList.get(i).getCategory_id();
                }
                Log.e("",""+response);
                bindData(titleStrings,ids);
            }

            @Override
            public void hnErr(int errCode, String msg) {
            }
        });
    }

    private void bindData(final String[] dataArray,String[] ids) {
        if (dataArray == null || dataArray.length == 0) {
            return;
        }
        FindSourceScrollViewPagerAdapter adapter = new FindSourceScrollViewPagerAdapter(getActivity().getSupportFragmentManager(), dataArray,ids);
        mViewPager.setAdapter(adapter);
        mSlidingTab.setViewPager(mViewPager, dataArray);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
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
        //todo 确定设置选中的index
        mViewPager.setCurrentItem(0);
    }


}
