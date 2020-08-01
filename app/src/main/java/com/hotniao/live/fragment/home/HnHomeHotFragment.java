package com.hotniao.live.fragment.home;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.holder.Holder;
import com.bigkoo.convenientbanner.listener.OnItemClickListener;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.hn.library.banner.HnBannerLayout;
import com.hn.library.base.BaseFragment;
import com.hn.library.base.BaseRequestStateListener;
import com.hn.library.base.EventBusBean;
import com.hn.library.daynight.DayNightHelper;
import com.hn.library.loadstate.HnLoadingLayout;
import com.hn.library.refresh.PtrClassicFrameLayout;
import com.hn.library.refresh.PtrDefaultHandler2;
import com.hn.library.refresh.PtrFrameLayout;
import com.hn.library.utils.HnToastUtils;
import com.hotniao.live.R;
import com.hotniao.live.activity.HnSystemAct;
import com.hotniao.live.activity.HnWebActivity;
import com.hotniao.live.adapter.HnHomeHotExtAdapter;
import com.hotniao.live.adapter.HnHomeHotLittleAdapter;
import com.hotniao.live.biz.home.HnHomeBiz;
import com.hn.library.global.HnConstants;
import com.hn.library.global.HnUrl;
import com.hotniao.live.eventbus.HnSelectLiveCateEvent;
import com.hotniao.live.model.HnBannerModel;
import com.hotniao.live.model.HnHomeLiveModel;
import com.hotniao.live.model.HnHomeLiveModel;
import com.hotniao.live.model.bean.HnHomeHotBean;
import com.hotniao.live.utils.HnUiUtils;
import com.hn.library.view.HnSpacesItemDecoration;
import com.hotniao.livelibrary.model.HnLiveRoomInfoModel;
import com.live.shoplib.ShopActFacade;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Copyright (C) 2017,深圳市红鸟网络科技股份有限公司 All rights reserved.
 * 项目名称：红鸟电商
 * 类描述：首页 热门
 * 创建人：mj
 * 创建时间：2017/3/6 16:16
 * 修改人：mj
 * 修改时间：2017/3/6 16:16
 * 修改备注：
 * Version:  1.0.0
 */
public class HnHomeHotFragment extends BaseFragment implements HnLoadingLayout.OnReloadListener, BaseRequestStateListener {

    @BindView(R.id.recyclerview)
    RecyclerView mRecyclerView;
    @BindView(R.id.ptr_refresh)
    PtrClassicFrameLayout mPtr;
    @BindView(R.id.loading)
    HnLoadingLayout mLoading;
    @BindView(R.id.mRlPer)
    RelativeLayout mRlPer;

    /**
     * 头部布局  广告位
     */
    private View mHeaderView;
    private RelativeLayout mRlEmpty;
    private ConvenientBanner mBanner;
    /**
     * 页数
     */
    private int mPage = 1;
    /**
     * 业务逻辑类，处理首页业务
     */
    private HnHomeBiz mHnHomeBiz;

    /**
     * 热门直播列表适配器
     */
    private BaseQuickAdapter mAdapter;
    /**
     * 直播列表数据源
     */
    private List<HnHomeLiveModel.DEntity.StoreEntity.ItemsEntity> mDatas = new ArrayList<>();
    /**
     * 广告数据源
     */
    private List<String> imgUrl = new ArrayList<>();
    /**
     * 用于标识是切换大图模式还是侠拼图模式
     */
    private boolean mIsLittleMode = false;
    /**
     * 标识符  0 热门 1 最新
     */
    private DayNightHelper mDayNightHelper;


    public static HnHomeHotFragment getInstance() {
        HnHomeHotFragment fragment = new HnHomeHotFragment();
        return fragment;
    }

    @Override
    public int getContentViewId() {
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        return R.layout.common_loading_layout;
    }

    @Override
    public void initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        mLoading.setStatus(HnLoadingLayout.Loading);
        mLoading.setEmptyImage(R.drawable.home_live).setEmptyText("暂无直播");
        mLoading.setOnReloadListener(this);
        mHnHomeBiz = new HnHomeBiz(mActivity);
        mHnHomeBiz.setBaseRequestStateListener(this);
        //初始化适配器
        initAdapter();
        //事件监听
        initEvent();
        mDayNightHelper = new DayNightHelper();
    }

    @Override
    protected void initData() {
        mPage = 1;
        mHnHomeBiz.requestToHotList(mPage);
        mHnHomeBiz.getBanner();
        refreshUI();
    }

    /**
     * 初始化适配器
     */
    private void initAdapter() {
//        mRecyclerView.addItemDecoration(new HnSpacesItemDecoration(6, true));
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        mAdapter = new HnHomeHotExtAdapter();
        mRecyclerView.setAdapter(mAdapter);

        mHeaderView = mActivity.getLayoutInflater().inflate(R.layout.bannerview, null);
        mRlEmpty = (RelativeLayout) mHeaderView.findViewById(R.id.rl_empty);
        mBanner = (ConvenientBanner) mHeaderView.findViewById(R.id.convenientBanner);

        mAdapter.addHeaderView(mHeaderView);

    }

    /**
     * 事件监听
     */
    protected void initEvent() {
        //刷新监听
        mPtr.disableWhenHorizontalMove(true);
        mPtr.setPtrHandler(new PtrDefaultHandler2() {
            @Override
            public void onLoadMoreBegin(PtrFrameLayout frame) {
                mPage = mPage + 1;
                mHnHomeBiz.requestToHotList(mPage);
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                mPage = 1;
                mHnHomeBiz.requestToHotList(mPage);
                mHnHomeBiz.getBanner();
            }
        });

    }


    /**
     * 重新加载
     *
     * @param v
     */
    @Override
    public void onReload(View v) {
        initData();
    }

    @Override
    public void requesting() {

    }

    @Override
    public void requestSuccess(String type, String response, Object obj) {
        if (mActivity == null || mLoading == null) return;
        if ("hot_list".equals(type)) {
            HnHomeLiveModel model = (HnHomeLiveModel) obj;
            mActivity.setLoadViewState(HnLoadingLayout.Success, mLoading);
            mActivity.closeRefresh(mPtr);
            if (model != null && model.getD() != null) {
                updateUI(model.getD().getStore());
            } else {
                if (mAdapter != null && mAdapter.getItemCount() < 1)
                    mRlEmpty.setVisibility(View.VISIBLE);
                mActivity.setLoadViewState(HnLoadingLayout.Success, mLoading);
            }
        } else if ("join_live_room".equals(type)) {//进入直播间
            HnLiveRoomInfoModel model = (HnLiveRoomInfoModel) obj;
            if (model != null && model.getD() != null) {//通过arouter框架进行跳转 进入用户直播间
                Bundle bundle = new Bundle();
                bundle.putParcelable("data", model.getD());
                ARouter.getInstance().build("/live/HnAudienceActivity").with(bundle).navigation();
            }
        } else if ("banner_data".equals(type)) {
            HnBannerModel model = (HnBannerModel) obj;
            if (model.getD().getCarousel() == null) return;
            initViewpager(model.getD().getCarousel());
        }
    }

    @Override
    public void requestFail(String type, int code, String msg) {
        if (mLoading == null) return;
        if ("hot_list".equals(type)) {
            mActivity.closeRefresh(mPtr);
            mActivity.setLoadViewState(HnLoadingLayout.Success, mLoading);
            if (mAdapter != null && mAdapter.getItemCount() < 1)
                mRlEmpty.setVisibility(View.VISIBLE);
            HnToastUtils.showToastShort(msg);
        } else if ("join_live_room".equals(type)) {//进入直播间
            HnToastUtils.showToastShort(msg);
        } else if ("banner_data".equals(type)) {//获取广告
            HnToastUtils.showToastShort(msg);
        }

    }


    /**
     * 更新界面数据
     *
     * @param data 首页数据
     */
    private void updateUI(HnHomeLiveModel.DEntity.StoreEntity data) {
        //直播列表
        List<HnHomeLiveModel.DEntity.StoreEntity.ItemsEntity> lives = data.getItems();
        if (lives != null && lives.size() > 0) {
            showSuccessView();
            if (mPage == 1) {
                mDatas.clear();
                mAdapter.setNewData(lives);
            } else {
                mAdapter.addData(lives);
            }
            mDatas.addAll(lives);
        } else {
            if (mPage == 1 || mAdapter.getItemCount() < 1) {
                mDatas.clear();
                mAdapter.setNewData(mDatas);
                showEmptyView();
            }
        }


    }

    /**
     * 设置bannner
     *
     * @param bannerBeen
     */
    private void initViewpager(final List<HnBannerModel.DEntity.CarouselEntity> bannerBeen) {
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

    /**
     * 有直播列表数据时调用
     */
    public void showSuccessView() {
        mRlEmpty.setVisibility(View.GONE);
    }

    /**
     * 没有直播列表数据时调用
     */
    public void showEmptyView() {
        mRlEmpty.setVisibility(View.VISIBLE);
    }

    /**
     * 刷新UI界面
     */
    public void refreshUI() {
        //背景色
        TypedValue background = new TypedValue();
        //字体颜色#333333
        TypedValue textColor333 = new TypedValue();
        //条目背景颜色
        TypedValue item_color = new TypedValue();
        Resources.Theme theme = getActivity().getTheme();
        theme.resolveAttribute(R.attr.pageBg_color, background, true);
        theme.resolveAttribute(R.attr.item_bg_color, item_color, true);
        theme.resolveAttribute(R.attr.text_color_333, textColor333, true);
        mRlPer.setBackgroundResource(background.resourceId);
        mHeaderView.setBackgroundResource(background.resourceId);
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void eventSelectCate(HnSelectLiveCateEvent event) {
        if (HnSelectLiveCateEvent.REFRESH_UI == event.getType()) {
            refreshUI();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void eventRefresh(EventBusBean event) {
        if (HnConstants.EventBus.RefreshLiveList == event.getType()) {
            mPage = 1;
            mHnHomeBiz.requestToHotList(mPage);
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
