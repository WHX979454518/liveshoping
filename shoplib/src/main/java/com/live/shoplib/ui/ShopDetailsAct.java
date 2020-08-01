package com.live.shoplib.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.hn.library.base.BaseActivity;
import com.hn.library.base.baselist.BaseViewHolder;
import com.hn.library.base.baselist.CommRecyclerAdapter;
import com.hn.library.global.HnUrl;
import com.hn.library.global.NetConstant;
import com.hn.library.http.HnHttpUtils;
import com.hn.library.http.HnResponseHandler;
import com.hn.library.refresh.PtrClassicFrameLayout;
import com.hn.library.refresh.PtrDefaultHandler;
import com.hn.library.refresh.PtrFrameLayout;
import com.hn.library.tab.SlidingTabLayout;
import com.hn.library.tab.listener.OnTabSelectListener;
import com.hn.library.utils.HnDateUtils;
import com.hn.library.utils.HnDimenUtil;
import com.hn.library.utils.HnPrefUtils;
import com.hn.library.utils.HnToastUtils;
import com.hn.library.view.FrescoImageView;
import com.live.shoplib.R;
import com.live.shoplib.ShopActFacade;
import com.live.shoplib.ShopRequest;
import com.live.shoplib.adapter.HnShopDetilsAdapter;
import com.live.shoplib.bean.BackModel;
import com.live.shoplib.bean.GoodsCarNumModel;
import com.live.shoplib.bean.StoreDetailsModel;
import com.live.shoplib.ui.dialog.ShareDialog;
import com.live.shoplib.ui.frag.AllGoodsFrag;
import com.live.shoplib.ui.frag.BaseScollFragment;
import com.live.shoplib.widget.scollorlayout.ScrollableLayout;
import com.live.shoplib.widget.video.StoreViewPage;
import com.loopj.android.http.RequestParams;

import java.util.ArrayList;
import java.util.List;


/**
 * 店铺详情
 * 作者：Mr.X
 * 时间：10:48
 * ARouter.getInstance().build("/shoplib/ShopDetailsAct").navigation();
 */
@Route(path = "/shoplib/ShopDetailsAct")
@RequiresApi(api = Build.VERSION_CODES.M)
public class ShopDetailsAct extends BaseActivity {

    private TextView mTvTitle;
    private ImageView mIvBack;
    private ImageView mIvShare, mIvBarShopCar, mIvBarBack;
    private ImageView mIvShopCats, mIvBarShare;
    private ViewPager mBannerPager;
    private FrescoImageView mIvIco;
    //    private Toolbar mToolBar;
//    private CollapsingToolbarLayout mCollapsingToolbar;
//    private AppBarLayout mAppbar;
//    private CommTabView mCommTab;
    private RelativeLayout mLLTitle;
    private RecyclerView mBackRecycler;
    private TextView mTvStoreName;
    private TextView mTvTag;
    private TextView mTvStoreNum;
    private TextView mTvSaleNum;
    private TextView mTvFanNum;
    private TextView mTvAddress;
    private TextView mTvAnno;
    private TextView mTvMore;
    private TextView mTvConnection;
    private TextView mTvShopMsg;
    private CheckBox mBoxCollect;
    private View mViewBack;
    private LinearLayout mLLBack;
    private LinearLayout mLLDot;
    private PtrClassicFrameLayout mRefresh;
    private ScrollableLayout mScrollLayout;
    private SlidingTabLayout mTab;
    private ViewPager mViewPager;
    private ImageView mIvAddGoodsNew;
    private ImageView mIvAddGoodsNew2;


    private String store_id, mUid;

    private StoreDetailsModel.DEntity bean;
    private ShareDialog dialog;


    private ArrayList<BaseScollFragment> mFragments = new ArrayList<>();

    @Override
    public int getContentViewId() {
        return R.layout.act_shop_details;
    }

    @Override
    public void onCreateNew(Bundle savedInstanceState) {
        setShowTitleBar(false);
        store_id = getIntent().getStringExtra("store_id");
        initView();
        requestData(store_id);

    }


    private void initView() {
        mLLDot = findViewById(R.id.mLLDot);
        mLLBack = findViewById(R.id.mLLBack);
        mViewBack = findViewById(R.id.mViewBack);
        mTvTitle = findViewById(R.id.mTvTitle);
        mIvBack = findViewById(R.id.mIvBack);
        mIvShare = findViewById(R.id.mIvShare);
        mIvBarShare = findViewById(R.id.mIvBarShare);
        mIvBarShopCar = findViewById(R.id.mIvBarShopCar);
        mIvBarBack = findViewById(R.id.mIvBarBack);
        mTvConnection = findViewById(R.id.mTvConnection);
        mTvShopMsg = findViewById(R.id.mTvShopMsg);
        mBoxCollect = findViewById(R.id.mBoxCollect);
        mIvShopCats = findViewById(R.id.mIvShopCats);
        mBannerPager = findViewById(R.id.mBannerPager);
        mIvIco = findViewById(R.id.mIvIco);
        mIvIco = findViewById(R.id.mIvIco);
        mBackRecycler = findViewById(R.id.mBackRecycler);

        mLLTitle = findViewById(R.id.mTitle);
        mTvStoreName = findViewById(R.id.mTvStoreName);
        mTvTag = findViewById(R.id.mTvTag);
        mTvStoreNum = findViewById(R.id.mTvStoreNum);
        mTvSaleNum = findViewById(R.id.mTvSaleNum);
        mTvFanNum = findViewById(R.id.mTvFanNum);
        mTvAddress = findViewById(R.id.mTvAddress);
        mTvAnno = findViewById(R.id.mTvAnno);
        mTvMore = findViewById(R.id.mTvMore);
        mIvAddGoodsNew = findViewById(R.id.mIvAddGoodsNew);
        mIvAddGoodsNew2 = findViewById(R.id.mIvAddGoodsNew2);


        mRefresh = findViewById(R.id.mRefresh);
        mScrollLayout = findViewById(R.id.scrollable_layout);
        mTab = findViewById(R.id.mSlidingTab);
        mViewPager = findViewById(R.id.view_pager);
        setListener();

    }

    private void setListener() {
        //刷新监听
        mRefresh.setEnabledNextPtrAtOnce(true);
        mRefresh.setKeepHeaderWhenRefresh(true);
        mRefresh.disableWhenHorizontalMove(true);
        mRefresh.setPtrHandler(new PtrDefaultHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                if (mLLTitle != null) mLLTitle.setVisibility(View.GONE);
                if (mFragments.size() > mViewPager.getCurrentItem()) {
                    mFragments.get(mViewPager.getCurrentItem()).pullToRefresh();
                }

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
                mLLTitle.setVisibility(factor > 0.8 ? View.VISIBLE : View.GONE);
            }
        });

        mTab.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {

            }

            @Override
            public void onTabReselect(int position) {

            }
        });
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mScrollLayout.getHelper().setCurrentScrollableContainer(mFragments.get(position));

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


    }

    public void refreshComplete() {
        if (isFinishing()) return;
        if (mLLTitle != null && mLLTitle.getVisibility() != View.VISIBLE)
            mLLTitle.setVisibility(View.GONE);
        if (mRefresh != null) mRefresh.refreshComplete();

    }

    @Override
    public void getInitData() {
        if (bean == null) return;
        mTvMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShopActFacade.openBackList(bean.getDialogue().getStore_uid(),"产品溯源");
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (dialog != null)
            dialog.setActivityResult(requestCode, resultCode, data);
    }


    public void onShopDetailsClick(View v) {
        if (v == mIvBack || v == mIvBarBack) {//退出
            finish();
        } else if (v == mIvShare || v == mIvBarShare) {//分享
            if (bean == null) return;
            dialog = new ShareDialog(ShareDialog.Type.Store, getSupportFragmentManager())
                    .setStoreShare(bean.getName(), bean.getIcon(), bean.getShare())
                    .show();
        } else if (v == mIvShopCats || v == mIvBarShopCar) {//购物车
            ShopActFacade.openGoodsCar();
        } else if (v == mTvConnection) {//客服
            if (bean == null) return;
//            ShopActFacade.openPrivateChat(bean.getDialogue().getId(), bean.getDialogue().getName(), bean.getDialogue().getCharId());
            String uid_own = HnPrefUtils.getString(NetConstant.User.UID, "");
            boolean isSeller = false;
            if (TextUtils.equals(uid_own, bean.getUser_id())) {
                HnToastUtils.showToastShort("无法与自己聊天");
                isSeller = true;
                return;
            }
            ShopActFacade.openPrivateChat(
                    isSeller ? bean.getDialogue().getId() : bean.getDialogue().getStore_uid(),
                    isSeller ? bean.getDialogue().getUser_name() : bean.getDialogue().getName(),
                    bean.getDialogue().getCharId());
        } else if (v == mTvShopMsg) {//店铺信息
            ShopActFacade.openStoreMsg(store_id);
        } else if (v == mBoxCollect) {//收藏
            if (bean == null) return;
            String uid_own = HnPrefUtils.getString(NetConstant.User.UID, "");
            boolean isSeller = false;
            if (TextUtils.equals(uid_own, bean.getUser_id())) {
                HnToastUtils.showToastShort("不能收藏自己哦~");
                mBoxCollect.setChecked(false);
                isSeller = true;
                return;
            }
            mBoxCollect.setEnabled(false);
            ShopRequest.collectShop(store_id, mBoxCollect.isChecked() ? "1" : "0", new ShopRequest.OnRespondNothing() {
                @Override
                public void finishs() {
                    mBoxCollect.setEnabled(true);
                    bean.setIs_collect(TextUtils.equals("Y", bean.getIs_collect()) ? "N" : "Y");
                    HnToastUtils.showToastShort(mBoxCollect.isChecked() ? "收藏成功" : "取消收藏成功");
                }
            });
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    mBoxCollect.setEnabled(true);
                }
            }, 3000);
        } else if (v == mTvMore) {
            if (bean == null) return;
            ShopActFacade.openBackList(mUid,"产品溯源");
        }
    }


    public void initTab(final List<StoreDetailsModel.DEntity.ColumnEntity> data, String store_id) {
        List<String> strs = new ArrayList<>();

        for (int i = 0; i < data.size(); i++) {
            strs.add(data.get(i).getName());
            mFragments.add(AllGoodsFrag.newInstance(data.get(i).getId(), store_id));
        }
        HnShopDetilsAdapter adapter = new HnShopDetilsAdapter(getSupportFragmentManager(), mFragments,strs);
        mViewPager.setOffscreenPageLimit(mFragments.size());
        mViewPager.setAdapter(adapter);
        mTab.setViewPager(mViewPager);
        mScrollLayout.getHelper().setCurrentScrollableContainer(mFragments.get(0));
        mViewPager.setCurrentItem(0);

    }

    //店铺信息
    private void requestData(final String store_id) {
        RequestParams param = new RequestParams();
        param.put("store_id", store_id);
        HnHttpUtils.postRequest(HnUrl.STORE_DETAILS, param, "店铺详情", new HnResponseHandler<StoreDetailsModel>(StoreDetailsModel.class) {
            @Override
            public void hnSuccess(String response) {
                if (isFinishing()) return;
                initBanner(model.getD().getVideo(), model.getD().getImg());
                mIvIco.setImageURI(Uri.parse(HnUrl.setImageUrl(model.getD().getIcon())));
                mTvTitle.setText(model.getD().getName());
                mTvStoreName.setText(model.getD().getName());
                mTvTag.setVisibility(TextUtils.equals(model.getD().getLive_status(), "Y") ? View.VISIBLE : View.GONE);
                mTvStoreNum.setText(model.getD().getGoods_num());
                mTvSaleNum.setText(model.getD().getGoods_sales());
                mTvFanNum.setText(model.getD().getTotal_collect());
                mTvAddress.setText(model.getD().getProvince() + " " + model.getD().getCity() + " " +
                        model.getD().getDistrict() + " " + model.getD().getAddress());

                if (TextUtils.isEmpty(model.getD().getNotice())) {
                    mTvAnno.setText("暂无公告");
                } else {
                    mTvAnno.setText(model.getD().getNotice());
                }

                initTab(model.getD().getColumn(), store_id);
                bean = model.getD();
                //收藏
                mBoxCollect.setChecked(TextUtils.equals("Y", model.getD().getIs_collect()));
                mUid = model.getD().getDialogue().getStore_uid();
                requestBack(model.getD().getDialogue().getStore_uid());

            }

            @Override
            public void hnErr(int errCode, String msg) {
                HnToastUtils.showToastShort(msg);
            }
        });
    }

    //店铺信息
    private void requestBack(final String user_id) {
        RequestParams param = new RequestParams();
        param.put("user_id", user_id);
        param.put("page", "1");
        param.put("pagesize", "3");
        HnHttpUtils.postRequest(HnUrl.LIVE_PLAYBACK_ANCHOR, param, "回放", new HnResponseHandler<BackModel>(BackModel.class) {
            @Override
            public void hnSuccess(String response) {
                if (isFinishing()) return;
                final List<BackModel.DEntity.VideosEntity.ItemsEntity> data = model.getD().getVideos().getItems();
                if (data.size() < 1) {
                    mScrollLayout.setMaxScroll(HnDimenUtil.dp2px(ShopDetailsAct.this, 301));
                    mBackRecycler.setVisibility(View.GONE);
                    mLLBack.setVisibility(View.GONE);
                    mViewBack.setVisibility(View.GONE);
                    return;
                }

                mBackRecycler.setHasFixedSize(true);
                mBackRecycler.setLayoutManager(new GridLayoutManager(ShopDetailsAct.this, 3));
                mBackRecycler.setAdapter(new CommRecyclerAdapter() {
                    @Override
                    protected void onBindView(BaseViewHolder holder, final int position) {
                        ((FrescoImageView) holder.getView(R.id.mIvIco))
                                .setImageURI(HnUrl.setImageUrl(data.get(position).getImage_url()));


                        holder.setTextViewText(R.id.mTvNum, data.get(position).getOnlines() + "人");
                        holder.setTextViewText(R.id.mTvDate, HnDateUtils.dateFormat(data.get(position).getStart_time(), "MM-dd HH:mm"));
                        holder.itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                ShopActFacade.openPlayBack(user_id, data.get(position).getPlayback_url(), data.get(position).getShare());
                            }
                        });
                    }

                    @Override
                    protected int getLayoutID(int position) {
                        return R.layout.item_back_shop_deatil;
                    }

                    @Override
                    public int getItemCount() {
                        return data.size();
                    }
                });
            }

            @Override
            public void hnErr(int errCode, String msg) {
                HnToastUtils.showToastShort(msg);
            }
        });
    }


    public void initBanner(String url, List<String> data) {
        if (mBannerPager == null) return;
        mBannerPager.setAdapter(new StoreViewPage(getSupportFragmentManager(), this, mBannerPager, mLLDot, url, data));
    }

    @Override
    protected void onResume() {
        super.onResume();
        requestGetCarNum();
    }

    //获取购物车数量
    private void requestGetCarNum() {
        HnHttpUtils.postRequest(HnUrl.GOODS_CAR_NUM, null, "购物车数量", new HnResponseHandler<GoodsCarNumModel>(GoodsCarNumModel.class) {
            @Override
            public void hnSuccess(String response) {
                if (isFinishing()) return;
                if (model.getD() != null && model.getD().getNum() != 0) {
                    if (mIvAddGoodsNew != null) mIvAddGoodsNew.setVisibility(View.VISIBLE);
                    if (mIvAddGoodsNew2 != null) mIvAddGoodsNew2.setVisibility(View.VISIBLE);
                } else {
                    if (mIvAddGoodsNew != null) mIvAddGoodsNew.setVisibility(View.GONE);
                    if (mIvAddGoodsNew2 != null) mIvAddGoodsNew2.setVisibility(View.GONE);
                }
            }

            @Override
            public void hnErr(int errCode, String msg) {
                if (mIvAddGoodsNew != null) mIvAddGoodsNew.setVisibility(View.GONE);
                if (mIvAddGoodsNew2 != null) mIvAddGoodsNew2.setVisibility(View.GONE);
            }
        });
    }

}
