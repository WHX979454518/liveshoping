package com.hotniao.live.fragment;

import android.content.Context;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.holder.Holder;
import com.bigkoo.convenientbanner.listener.OnItemClickListener;
import com.hn.library.banner.HnBannerLayout;
import com.hn.library.base.BaseFragment;
import com.hn.library.base.baselist.BaseViewHolder;
import com.hn.library.base.baselist.CommRecyclerAdapter;
import com.hn.library.global.HnUrl;
import com.hn.library.http.HnHttpUtils;
import com.hn.library.http.HnResponseHandler;
import com.hn.library.refresh.PtrClassicFrameLayout;
import com.hn.library.refresh.PtrDefaultHandler;
import com.hn.library.refresh.PtrFrameLayout;
import com.hn.library.utils.HnToastUtils;
import com.hn.library.view.FrescoImageView;
import com.hotniao.live.R;
import com.hotniao.live.activity.GroupBuyAct;
import com.hotniao.live.activity.HnSystemAct;
import com.hotniao.live.activity.HnWebActivity;
import com.hotniao.live.activity.SpikeGoodsAct;
import com.hotniao.live.eventbus.HomeCategoryClickEvent;
import com.hotniao.live.eventbus.HomeNearEvent;
import com.hotniao.live.eventbus.RefreshEvent;
import com.hotniao.live.fragment.home.HomeHotFrag;
import com.hotniao.live.model.ConcentrationCategoryModel;
import com.hotniao.live.model.ConcentrationSpikeModel;
import com.hotniao.live.model.HnBannerModel;
import com.hotniao.live.utils.HnUiUtils;
import com.live.shoplib.ShopActFacade;
import com.live.shoplib.utils.DataTimeUtils;
import com.live.shoplib.widget.scollorlayout.ScrollableLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 作者：Mr.Xu
 * 时间：2018/2/2
 */
@RequiresApi(api = Build.VERSION_CODES.M)
public class ConcentrationFrag extends BaseFragment {


    @BindView(R.id.convenientBanner)
    ConvenientBanner mBanner;
    @BindView(R.id.goods_category_listview)
    RecyclerView goods_category_listview;
    @BindView(R.id.spike_time_period)
    RecyclerView spike_time_period;
    @BindView(R.id.spike_demo_goods_listview)
    RecyclerView spike_demo_goods;
    @BindView(R.id.hot_recommend_shop_layout)
    FrameLayout hot_recommend_shop_layout;
    @BindView(R.id.spike_layout)
    LinearLayout spike_layout;
    @BindView(R.id.scrollable_layout)
    ScrollableLayout mScrollLayout;
    @BindView(R.id.mRefresh)
    PtrClassicFrameLayout mRefresh;


    private List<String> imgUrl = new ArrayList<>();
    private CommRecyclerAdapter goodsCategoryAdapter;
    private CommRecyclerAdapter spikePeriodAdapter;
    private CommRecyclerAdapter spikeGoodsAdapter;
    private List<ConcentrationCategoryModel.GoodsCategory> categoryList = new ArrayList<>();
    private List<ConcentrationSpikeModel.SpikeGoods> spikeGoodsData = new ArrayList<>();
    private List<ConcentrationSpikeModel.SpikePeriod> spikePeriodData = new ArrayList<>();

    @Override
    public int getContentViewId() {
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        return R.layout.frg_concentration;
    }

    @Override
    public void initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setListener();
        setGoodsCategoryRecycle();
        initSpikeLayout();
        setSpikePeriodRecycle();
        setSpikeGoodsRecycle();
        setShopRecommend();
    }

    private void initSpikeLayout() {
        spike_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SpikeGoodsAct.open(getContext());
            }
        });
    }

    private void setSpikeGoodsRecycle() {
        spike_demo_goods.setLayoutManager(new GridLayoutManager(getActivity(), 4));
        spike_demo_goods.setAdapter(spikeGoodsAdapter = new CommRecyclerAdapter() {

            @Override
            protected void onBindView(BaseViewHolder holder, final int position) {
                ConcentrationSpikeModel.SpikeGoods itemData = spikeGoodsData.get(position);

                FrescoImageView category_icon_img = holder.getView(R.id.goods_img);
                category_icon_img.setImageURI(HnUrl.setImageUri(itemData.getGoods_img()));

                holder.setTextViewText(R.id.goods_price, "￥" + itemData.getSec_price());
                holder.setTextViewText(R.id.goods_old_price, "￥" + itemData.getGoods_price());
                ((TextView) holder.getView(R.id.goods_old_price)).getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
            }

            @Override
            protected int getLayoutID(int position) {
                return R.layout.item_home_seckill_goods;
            }

            @Override
            public int getItemCount() {
                return spikeGoodsData.size();
            }
        });
        spikeGoodsAdapter.setOnItemClickListener(new com.hn.library.base.baselist.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                SpikeGoodsAct.open(getContext());
            }
        });
    }

    private void setSpikePeriodRecycle() {
        spike_time_period.setLayoutManager(new GridLayoutManager(getActivity(), 5));
        spike_time_period.setAdapter(spikePeriodAdapter = new CommRecyclerAdapter() {

            @Override
            protected void onBindView(BaseViewHolder holder, final int position) {
                ConcentrationSpikeModel.SpikePeriod itemData = spikePeriodData.get(position);
                holder.setTextViewText(R.id.start_time, DataTimeUtils.timeParseHour(Long.valueOf(itemData.getStarttime())));
                holder.setTextViewText(R.id.state, itemData.getSpikeState());
                if ("进行中".equals(itemData.getSpikeState())) {
                    ((TextView) holder.getView(R.id.start_time)).setTextColor(getResources().getColor(R.color.concentration_time_selected_color));
                    holder.getView(R.id.indicator).setVisibility(View.VISIBLE);
                } else {
                    ((TextView) holder.getView(R.id.start_time)).setTextColor(getResources().getColor(R.color.concentration_time_unselect_color));
                    holder.getView(R.id.indicator).setVisibility(View.GONE);
                }
            }

            @Override
            protected int getLayoutID(int position) {
                return R.layout.item_spike_period_layout;
            }

            @Override
            public int getItemCount() {
                return spikePeriodData.size();
            }
        });
        spikePeriodAdapter.setOnItemClickListener(new com.hn.library.base.baselist.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                SpikeGoodsAct.open(getContext());
            }
        });
    }

    private void setShopRecommend() {
        HomeHotFrag fragment;
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        try {
            fragment = HomeHotFrag.getInstance();
            transaction.addToBackStack(null);
            transaction.add(R.id.hot_recommend_shop_layout, fragment);
        } catch (Exception e) {
            return;
        }
        transaction.show(fragment);
        transaction.commitAllowingStateLoss();
        mScrollLayout.getHelper().setCurrentScrollableContainer(fragment);

    }

    private void setGoodsCategoryRecycle() {
        goods_category_listview.setLayoutManager(new GridLayoutManager(getActivity(), 5));
        goods_category_listview.setAdapter(goodsCategoryAdapter = new CommRecyclerAdapter() {

            @Override
            protected void onBindView(BaseViewHolder holder, final int position) {
                ConcentrationCategoryModel.GoodsCategory itemData = categoryList.get(position);
                if ("团购".equals(itemData.getName())) {
                    FrescoImageView category_icon_img = holder.getView(R.id.category_icon_img);
                    category_icon_img.setImageURI(Uri.parse("res:///" + R.drawable.goods_category_groupbuy));
                    holder.setTextViewText(R.id.category_name, itemData.getName());
                    return;
                }
                FrescoImageView category_icon_img = holder.getView(R.id.category_icon_img);
                category_icon_img.setImageURI(HnUrl.setImageUri(itemData.getIcon()));
                holder.setTextViewText(R.id.category_name, itemData.getName());

            }

            @Override
            protected int getLayoutID(int position) {
                return R.layout.item_home_concentration_category;
            }

            @Override
            public int getItemCount() {
                return categoryList.size();
            }
        });

        goodsCategoryAdapter.setOnItemClickListener(new com.hn.library.base.baselist.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                ConcentrationCategoryModel.GoodsCategory itemData = categoryList.get(position);
                if ("团购".equals(itemData.getName())) {
                    GroupBuyAct.open(getContext());
                } else {
                    EventBus.getDefault().post(new HomeCategoryClickEvent(itemData.getName()));
                }

            }
        });
    }

    @Override
    protected void initEvent() {

    }

    @Override
    protected void initData() {
        requestData();

    }

    private void setListener() {
        //刷新监听
        mRefresh.setEnabledNextPtrAtOnce(true);
        mRefresh.setKeepHeaderWhenRefresh(false);
        //todo  确定以后这个是否能满足要求
        mRefresh.setMode(PtrFrameLayout.Mode.NONE);
        mRefresh.disableWhenHorizontalMove(true);
        mRefresh.setPtrHandler(new PtrDefaultHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                requestData();
            }

            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                if (mScrollLayout.isCanPullToRefresh()) {
                    return PtrDefaultHandler.checkContentCanBePulledDown(frame, content, header);
                }
                return false;
            }
        });
        mScrollLayout.setOnScrollListener(new ScrollableLayout.OnScrollListener() {
            @Override
            public void onScroll(int currentY, int maxY) {
                float a = (float) (currentY * 0.5);
                float factor = 10 * currentY * 0.1f / maxY;
            }
        });

    }

    @Subscribe
    public void refreshComplete(RefreshEvent event) {
        if (getActivity().isFinishing()) return;
        if (mRefresh != null) mRefresh.refreshComplete();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void event(HomeNearEvent event) {
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    public void setBanner(final List<HnBannerModel.DEntity.CarouselEntity> bannerBeen) {
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
                .setPageIndicator(new int[]{R.drawable.indicator_point_nomal, R.drawable.new_indicator_point_select,})
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
        getBannerData();
        getCategoryData();
        getSpikeData();
    }


    public void getBannerData() {
        HnHttpUtils.postRequest(HnUrl.BANNER, null, TAG, new HnResponseHandler<HnBannerModel>(HnBannerModel.class) {
            @Override
            public void hnSuccess(String response) {
                if (model.getD().getCarousel() == null) return;
                setBanner(model.getD().getCarousel());
            }

            @Override
            public void hnErr(int errCode, String msg) {
                HnToastUtils.showToastShort(msg);
            }
        });
    }


    public void getCategoryData() {
        HnHttpUtils.postRequest(HnUrl.CONCENTRATION_GOODS_CATEGORY, null, TAG, new HnResponseHandler<ConcentrationCategoryModel>(ConcentrationCategoryModel.class) {
            @Override
            public void hnSuccess(String response) {
                if (goodsCategoryAdapter == null) return;
                categoryList.clear();
                categoryList.addAll(model.getD());
                ConcentrationCategoryModel.GoodsCategory goodsCategory = new ConcentrationCategoryModel.GoodsCategory();
                goodsCategory.setName("团购");
                categoryList.add(goodsCategory);
                goodsCategoryAdapter.notifyDataSetChanged();
            }

            @Override
            public void hnErr(int errCode, String msg) {
            }
        });
    }

    public void getSpikeData() {
        HnHttpUtils.postRequest(HnUrl.SPIKE_AND_GROUPBUY, null, TAG, new HnResponseHandler<ConcentrationSpikeModel>(ConcentrationSpikeModel.class) {
            @Override
            public void hnSuccess(String response) {
                if (spikePeriodAdapter == null || spikeGoodsAdapter == null) return;
                spikeGoodsData.clear();
                spikePeriodData.clear();
                spikeGoodsData.addAll(model.getD().getSeckill());
                spikePeriodData.addAll(model.getD().getTime_list());
                spikePeriodAdapter.notifyDataSetChanged();
                spikeGoodsAdapter.notifyDataSetChanged();
            }

            @Override
            public void hnErr(int errCode, String msg) {
            }
        });
    }

}
