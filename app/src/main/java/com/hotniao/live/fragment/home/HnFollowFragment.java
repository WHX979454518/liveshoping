package com.hotniao.live.fragment.home;

import android.content.res.Resources;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.hn.library.base.BaseFragment;
import com.hn.library.base.BaseRequestStateListener;
import com.hn.library.base.EventBusBean;
import com.hn.library.loadstate.HnLoadingLayout;
import com.hn.library.refresh.PtrClassicFrameLayout;
import com.hn.library.refresh.PtrDefaultHandler2;
import com.hn.library.refresh.PtrFrameLayout;
import com.hn.library.utils.HnToastUtils;
import com.hotniao.live.R;
import com.hotniao.live.adapter.HnHomeHotExtAdapter;
import com.hotniao.live.adapter.HnHomeHotLittleAdapter;
import com.hotniao.live.biz.home.HnFollowBiz;
import com.hn.library.global.HnConstants;
import com.hotniao.live.model.HnHomeHotModel;
import com.hotniao.live.model.HnHomeLiveModel;
import com.hotniao.live.model.bean.HnHomeHotBean;
import com.hn.library.view.HnSpacesItemDecoration;
import com.hotniao.livelibrary.model.HnLiveRoomInfoModel;
import com.reslibrarytwo.HnSkinTextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import butterknife.BindView;

import static com.hn.library.global.NetConstant.REQUEST_NET_ERROR;

/**
 * Copyright (C) 2017,深圳市红鸟网络科技股份有限公司 All rights reserved.
 * 项目名称：红鸟电商
 * 类描述：关注模块
 * 创建人：阳石柏
 * 创建时间：2017/3/6 16:16
 * 修改人：阳石柏
 * 修改时间：2017/3/6 16:16
 * 修改备注：
 * Version:  1.0.0
 */
public class HnFollowFragment extends BaseFragment implements BaseRequestStateListener, HnLoadingLayout.OnReloadListener {

    @BindView(R.id.mRlPer)
    RelativeLayout mRlPer;
    @BindView(R.id.tv_title)
    TextView mLoginTitleTv;
    @BindView(R.id.recyclerview)
    RecyclerView mRecyclerView;
    @BindView(R.id.ptr_refresh)
    PtrClassicFrameLayout mPtr;
    @BindView(R.id.loading)
    HnLoadingLayout mLoading;

    /**
     * 空布局
     */
    private View notDataView;
    private TextView tvGoNew;

    /**
     * 关注界面业务逻辑类
     */
    private HnFollowBiz mHnFollowBiz;
    /**
     * 关注列表适配器
     */
    private BaseQuickAdapter mAdapter;
    /**
     * 页数
     */
    private int mPage = 1;

    @Override
    public int getContentViewId() {
        return R.layout.fragment_follow;
    }

    @Override
    public void initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        mLoading.setStatus(HnLoadingLayout.Loading);
        mLoading.setOnReloadListener(this);
        mHnFollowBiz = new HnFollowBiz(mActivity);
        mHnFollowBiz.setBaseRequestStateListener(this);
        //初始化适配器
        initAdapter();

    }


    @Override
    protected void initData() {
        mPage = 1;
        mHnFollowBiz.requestToFollowList(mPage);

    }

    @Override
    protected void initEvent() {
        mPtr.setPtrHandler(new PtrDefaultHandler2() {
            @Override
            public void onLoadMoreBegin(PtrFrameLayout frame) {
                mPage += 1;
                mHnFollowBiz.requestToFollowList(mPage);
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                mPage = 1;
                mHnFollowBiz.requestToFollowList(mPage);
            }
        });

    }


    @Override
    public void requesting() {
        // mActivity.showDoing(getResources().getString(R.string.loading),null);
    }

    @Override
    public void requestSuccess(String type, String response, Object obj) {
        if (mActivity == null || mLoading == null) return;
        if ("follow_live_list".equals(type)) {
            HnHomeLiveModel model = (HnHomeLiveModel) obj;
            mActivity.setLoadViewState(HnLoadingLayout.Success, mLoading);
            mActivity.closeRefresh(mPtr);
            if (model.getD() != null && model.getD().getStore() != null) {
                updateUI(model.getD().getStore().getItems());
            } else {
                setEmpty();
            }
        } else if ("join_live_room".equals(type)) {//进入直播间
            HnLiveRoomInfoModel model = (HnLiveRoomInfoModel) obj;
            if (model != null && model.getD() != null) {//通过arouter框架进行跳转 进入用户直播间
                Bundle bundle = new Bundle();
                bundle.putParcelable("data", model.getD());
                ARouter.getInstance().build("/live/HnAudienceActivity").with(bundle).navigation();
            }
        }

    }


    @Override
    public void requestFail(String type, int code, String msg) {
        if (mLoading == null) return;
        if ("follow_live_list".equals(type)) {
            mActivity.closeRefresh(mPtr);
            if (mPage == 1) {
                setEmpty();
            } else {
                setEmpty();
                HnToastUtils.showToastShort(msg);
            }
        } else if ("join_live_room".equals(type)) {//进入直播间
            HnToastUtils.showToastShort(msg);
        }
    }

    private void setEmpty() {
        if (mAdapter != null && mAdapter.getItemCount() < 1) {
            mActivity.setLoadViewState(HnLoadingLayout.Success, mLoading);
            mAdapter.setNewData(null);
            mAdapter.setEmptyView(notDataView);
        }
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


    /**
     * 更新界面ui
     *
     * @param items
     */
    private void updateUI(List<HnHomeLiveModel.DEntity.StoreEntity.ItemsEntity> items) {
//        if (items != null && mAdapter != null) {
//            if (items.size() == 0) {
//
//            } else if (items.size() > 1 && mAdapter.getItemCount() < 2) {
//                mAdapter = new HnHomeHotLittleAdapter();
//                mRecyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
//                mRecyclerView.setAdapter(mAdapter);
//            } else if (items.size() < 2 && mAdapter.getItemCount() > 1) {
//                mAdapter = new HnHomeHotExtAdapter();
//                mRecyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
//                mRecyclerView.setAdapter(mAdapter);
//            }
//
//        }

        if (items != null && items.size() > 0) {
            if (mPage == 1) {
                mAdapter.setNewData(items);
            } else {
                mAdapter.addData(items);
            }
        } else {
            if (mPage == 1) {
                mAdapter.setNewData(null);
                mAdapter.setEmptyView(notDataView);
            }
        }

    }


    /**
     * 初始化适配器
     */
    private void initAdapter() {
        mAdapter = new HnHomeHotExtAdapter();
        //空页面
        notDataView = mActivity.getLayoutInflater().inflate(R.layout.layout_empty_follow_fragment, (ViewGroup) mRecyclerView.getParent(), false);
        HnSkinTextView mTvEmpty = (HnSkinTextView) notDataView.findViewById(R.id.mTvEmpty);
        mTvEmpty.setText(R.string.near_no_live);
        mTvEmpty.setTopDrawable(R.drawable.home_no_attention);
        tvGoNew = (TextView) notDataView.findViewById(R.id.tv_go_hot);
        tvGoNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().post(new EventBusBean(0, HnConstants.EventBus.Switch_Fragment, 0));

            }
        });
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
//        mRecyclerView.addItemDecoration(new HnSpacesItemDecoration(6, false));
        mRecyclerView.setAdapter(mAdapter);

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

        Resources.Theme theme = mActivity.getTheme();
        Resources resources = getResources();

        theme.resolveAttribute(R.attr.pageBg_color, background, true);
        theme.resolveAttribute(R.attr.item_bg_color, item_color, true);
        theme.resolveAttribute(R.attr.text_color_333, textColor333, true);

        //根布局背景色
        mLoading.setBackgroundResource(background.resourceId);
        //标题背景色
        mLoginTitleTv.setBackgroundResource(item_color.resourceId);
        //标题字体颜色
        mLoginTitleTv.setTextColor(resources.getColor(textColor333.resourceId));
        mRlPer.setBackgroundResource(background.resourceId);
        //重新创建
        initData();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void event(EventBusBean event) {
        if (HnConstants.EventBus.RefreshLiveList == event.getType()) {
            mPage = 1;
            mHnFollowBiz.requestToFollowList(mPage);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
