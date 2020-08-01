package com.live.shoplib.ui.frag;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.hn.library.base.BaseFragment;
import com.hn.library.base.EventBusBean;
import com.hn.library.base.baselist.BaseViewHolder;
import com.hn.library.base.baselist.CommRecyclerAdapter;
import com.hn.library.base.baselist.OnItemClickListener;
import com.hn.library.global.HnConstants;
import com.hn.library.global.HnUrl;
import com.hn.library.http.HnHttpUtils;
import com.hn.library.http.HnResponseHandler;
import com.hn.library.refresh.PtrClassicFrameLayout;
import com.hn.library.refresh.PtrDefaultHandler2;
import com.hn.library.refresh.PtrFrameLayout;
import com.hn.library.utils.HnBadgeView;
import com.hn.library.utils.HnRefreshDirection;
import com.hn.library.utils.HnToastUtils;
import com.hn.library.view.FrescoImageView;
import com.live.shoplib.R;
import com.live.shoplib.ShopActFacade;
import com.live.shoplib.adapter.HnShopAdapter;
import com.live.shoplib.bean.HnShopTypeBean;
import com.live.shoplib.bean.ShopItemBean;
import com.loopj.android.http.RequestParams;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;


/**
 * 作者：Mr.Xu
 * 时间：2017/12/18
 */
@SuppressLint("LongLogTag")
public class HnShopFragment extends BaseFragment {


    RecyclerView mRecyclerTab;
    CheckBox mBoxTab;
    RecyclerView mRecyclerShop;
    PtrClassicFrameLayout mRefresh;
    ImageView mIvSearch;
    ImageView mIvMsg;
    HnBadgeView mTvNewMsg;
    private ArrayList<HnShopTypeBean.DBean.ListBean> mDataTab = new ArrayList<>();
    private ArrayList<ShopItemBean.DBean.GoodsBean.ItemsBean> mDataShop = new ArrayList<>();
    private CommRecyclerAdapter mAdapterTab;
    private HnShopAdapter mAdapterShop;
    private boolean sace = false;
    private int page = 1;
    private View headView;

    @Override
    public int getContentViewId() {
        return R.layout.frag_shop;
    }

    @Override
    public void initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        EventBus.getDefault().register(this);
        mRecyclerShop = mRootView.findViewById(R.id.mRecyclerShop);
        mTvNewMsg = mRootView.findViewById(R.id.mTvNewMsg);
        mRefresh = mRootView.findViewById(R.id.mRefresh);
        mIvSearch = mRootView.findViewById(R.id.mIvSearch);
        mIvMsg = mRootView.findViewById(R.id.mIvMsg);

        mRecyclerShop.setLayoutManager(new GridLayoutManager(getActivity(), sace ? 3 : 2));
        mRecyclerShop.setAdapter(mAdapterShop = new HnShopAdapter(mDataShop, sace));

        headView = inflater.inflate(R.layout.head_shop, null);
        mRecyclerTab = headView.findViewById(R.id.mRecyclerTab);
        mBoxTab = headView.findViewById(R.id.mBoxTab);

        mAdapterShop.setHeaderView(headView);

        mBoxTab.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                sace = isChecked;
                mRecyclerShop.setLayoutManager(new GridLayoutManager(getActivity(), sace ? 3 : 2));
                mAdapterShop.removeAllHeaderView();
                mAdapterShop = new HnShopAdapter(mDataShop, sace);
                mRecyclerShop.setAdapter(mAdapterShop);
                mAdapterShop.setHeaderView(headView);
            }
        });

        //刷新监听
        mRefresh.disableWhenHorizontalMove(true);
        mRefresh.setPtrHandler(new PtrDefaultHandler2() {
            @Override
            public void onLoadMoreBegin(PtrFrameLayout frame) {
                page++;
                getData(HnRefreshDirection.BOTTOM, page);
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                page = 1;
                getData(HnRefreshDirection.TOP, page);
            }
        });

        mIvSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ARouter.getInstance().build("/app/HnHomeSearchActivity").navigation();
            }
        });
        mIvMsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ARouter.getInstance().build("/app/HnMyMessageActivity").navigation();
            }
        });
    }

    private void initTab() {
        mRecyclerTab.setLayoutManager(new GridLayoutManager(getActivity(), 4));
        mRecyclerTab.setAdapter(mAdapterTab = new CommRecyclerAdapter() {
            @Override
            protected void onBindView(BaseViewHolder holder, int position) {
                FrescoImageView mIvTab = holder.getView(R.id.mIvTab);
                if (position == mDataTab.size() - 1) {
                    mIvTab.setImageURI("res://" + getActivity().getPackageName() + "/" + R.drawable.classify_all);
                    holder.setTextViewText(R.id.mTvTab, "全部分类");
                } else {
                    mIvTab.setImageURI(HnUrl.setImageUri(mDataTab.get(position).getIcon()));
                    holder.setTextViewText(R.id.mTvTab, mDataTab.get(position).getName());
                }
            }

            @Override
            public int getItemCount() {
                return mDataTab.size();
            }

            @Override
            protected int getLayoutID(int position) {
                return R.layout.item_shop_tab;
            }
        });

        mAdapterTab.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                if (position == mDataTab.size() - 1) {
                    ShopActFacade.openShopCenter();
                } else {
                    ShopActFacade.openShopGoodsList(mDataTab.get(position).getId());
                }
            }
        });

    }

    @Override
    protected void initData() {
        getDataTab();
        getData(HnRefreshDirection.TOP, 1);
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

    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    protected void getDataTab() {
        RequestParams param = new RequestParams();
        param.put("type", "2");
        HnHttpUtils.postRequest(HnUrl.SHOP_TYPE, param, TAG, new HnResponseHandler<HnShopTypeBean>(HnShopTypeBean.class) {
            @Override
            public void hnSuccess(String response) {
                mDataTab.clear();
                mDataTab.addAll(model.getD().getList());
                mDataTab.add(new HnShopTypeBean.DBean.ListBean());
                initTab();
            }

            @Override
            public void hnErr(int errCode, String msg) {
                if (mRecyclerShop == null) return;
                HnToastUtils.showToastShort(msg);
            }
        });
    }

    protected void getData(final HnRefreshDirection state, final int page) {
        RequestParams param = new RequestParams();
        param.put("page", page + "");
        param.put("pageSize", "12");
        HnHttpUtils.postRequest(HnUrl.SHOP_RECOMMED, param, TAG, new HnResponseHandler<ShopItemBean>(ShopItemBean.class) {
            @Override
            public void hnSuccess(String response) {

                if (page == 1 && state == HnRefreshDirection.TOP) {
                    mDataShop.clear();
                    mDataShop.addAll(model.getD().getGoods().getItems());
                } else {
                    mDataShop.addAll(model.getD().getGoods().getItems());
                }

                if (page == 1 && mDataShop.size() == 0) {
                    setEmpty("暂无商品", R.drawable.no_columns);
                }
                refreshFinish();
                mAdapterShop.notifyDataSetChanged();

            }

            @Override
            public void hnErr(int errCode, String msg) {
                if (mRecyclerShop == null) return;
                refreshFinish();
                HnToastUtils.showToastShort(msg);
            }
        });

    }

    protected void refreshFinish() {
        if (mRefresh != null) mRefresh.refreshComplete();
    }

    protected void setEmpty(String content, int res) {
        if (mActivity == null) return;
    }


}