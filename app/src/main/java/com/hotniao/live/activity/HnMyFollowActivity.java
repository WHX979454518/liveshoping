package com.hotniao.live.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.hn.library.base.BaseActivity;
import com.hn.library.base.BaseRequestStateListener;
import com.hn.library.global.NetConstant;
import com.hn.library.loadstate.HnLoadingLayout;
import com.hn.library.refresh.PtrClassicFrameLayout;
import com.hn.library.refresh.PtrDefaultHandler2;
import com.hn.library.refresh.PtrFrameLayout;
import com.hn.library.utils.HnPrefUtils;
import com.hn.library.utils.HnToastUtils;
import com.hotniao.live.R;
import com.hotniao.live.adapter.HnMyFollowAdapter;
import com.hotniao.live.biz.user.follow.HnFollowBiz;
import com.hotniao.live.model.HnMyFocusModel;
import com.hotniao.live.model.bean.HnMyFocusBean;
import com.hotniao.livelibrary.model.event.HnFollowEvent;
import com.hotniao.livelibrary.widget.dialog.HnUserDetailDialog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;

import butterknife.BindView;

import static com.hn.library.global.NetConstant.REQUEST_NET_ERROR;


/**
 * Copyright (C) 2017,深圳市红鸟网络科技股份有限公司 All rights reserved.
 * 项目名称：红鸟电商
 * 类描述： 我的关注
 * 创建人：阳石柏
 * 创建时间：2017/3/6 16:16
 * 修改人：阳石柏
 * 修改时间：2017/3/6 16:16
 * 修改备注：
 * Version:  1.0.0
 */
public class HnMyFollowActivity extends BaseActivity implements HnLoadingLayout.OnReloadListener, BaseRequestStateListener {


    @BindView(R.id.recyclerview)
    RecyclerView mRecyclerview;
    @BindView(R.id.ptr_refresh)
    PtrClassicFrameLayout mRefreshView;
    @BindView(R.id.loading)
    HnLoadingLayout loading;
//    @BindView(R.id.mTvHead)
//    TextView mTvHead;

    //页数
    private int mPage = 1;
    //关注列表适配器
    private HnMyFollowAdapter mAdapter;
    //我的关注逻辑类，处理我的关注相关业务
    private HnFollowBiz mHnFollowBiz;
    //用户详情卡片
    private HnUserDetailDialog mHnUserDetailDialog;


    @Override
    public int getContentViewId() {
        return R.layout.activity_my_follow;
    }

    @Override
    public void onCreateNew(Bundle savedInstanceState) {
        setShowBack(true);
        setTitle(getString(R.string.mine_my_follow));
        EventBus.getDefault().register(this);
        loading.setStatus(HnLoadingLayout.Loading);
        loading.setEmptyImage(R.drawable.empty_com).setEmptyText(getString(R.string.now_follow_go_to_anchor));
        loading.setOnReloadListener(this);
        mHnFollowBiz = new HnFollowBiz(this);
        mHnFollowBiz.setRegisterListener(this);
        initRefreshView();
    }

    @Override
    public void getInitData() {
        mPage = 1;
        mHnFollowBiz.requestFollow(mPage);
    }

    /**
     * 列表
     */
    private void initRefreshView() {
        mAdapter = new HnMyFollowAdapter();
        mRecyclerview.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerview.setHasFixedSize(true);
        mRecyclerview.setAdapter(mAdapter);
        mRefreshView.setPtrHandler(new PtrDefaultHandler2() {
            @Override
            public void onLoadMoreBegin(PtrFrameLayout frame) {
                mPage++;
                mHnFollowBiz.requestFollow(mPage);

            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                mPage = 1;
                mHnFollowBiz.requestFollow(mPage);

            }
        });
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                HnMyFocusBean.FollowsBean.ItemsBean item = (HnMyFocusBean.FollowsBean.ItemsBean) adapter.getItem(position);
                String mUid = HnPrefUtils.getString(NetConstant.User.UID, "");
                mHnUserDetailDialog = HnUserDetailDialog.newInstance(1, item.getUser_id(), mUid, 0);
                mHnUserDetailDialog.setActvity(HnMyFollowActivity.this);
                mHnUserDetailDialog.setOnCancel(new HnUserDetailDialog.ICancelDialog() {
                    @Override
                    public void onCancelDialog() {
                        mPage = 1;
                        mHnFollowBiz.requestFollow(mPage);
                    }
                });
                mHnUserDetailDialog.show(getSupportFragmentManager(), "HnUserDetailDialog");
            }
        });

        mAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, final int position) {
                HnMyFocusBean.FollowsBean.ItemsBean item = (HnMyFocusBean.FollowsBean.ItemsBean) adapter.getItem(position);
                if (item == null)
                    return;
                final String uid = item.getUser_id();
                if (view.getId() == R.id.mTvFocus) {
                    if ("Y".equals(item.getIs_follow())) {
                        mHnFollowBiz.cancelFollow(uid, position);

                    } else if ("N".equals(item.getIs_follow())) {
                        mHnFollowBiz.addFollow(uid, position);
                    }
                }
            }
        });
    }


    /**
     * 请求中
     */
    @Override
    public void requesting() {
        showDoing(getResources().getString(R.string.loading), null);

    }

    /**
     * 请求成功
     *
     * @param type
     * @param response
     * @param obj
     */
    @Override
    public void requestSuccess(String type, String response, Object obj) {
        if (loading == null) return;
        done();
        setLoadViewState(HnLoadingLayout.Success, loading);
        if ("follow_list".equals(type)) {//关注列表
            closeRefresh(mRefreshView);
            HnMyFocusModel model = (HnMyFocusModel) obj;

//            if (model != null && model.getD() != null && "Y".equals(model.getD().getIs_remind())) {
//                mTvHead.setVisibility(View.GONE);
//            } else {
//                mTvHead.setVisibility(View.VISIBLE);
//            }
            if (model != null && model.getD() != null && model.getD().getFollows().getItems() != null && model.getD().getFollows().getItems().size() > 0) {
                List<HnMyFocusBean.FollowsBean.ItemsBean> items = model.getD().getFollows().getItems();

                if (mPage == 1) {
                    mAdapter.setNewData(items);
                } else {
                    mAdapter.addData(items);
                }
            } else {
                if (mPage == 1) {
                    setLoadViewState(HnLoadingLayout.Empty, loading);
                }
            }
        } else if ("cancelFollow".equals(type)) {//取消关注
            int pos = (int) obj;
//            mAdapter.getItem(pos).setIs_follow("N");
//            mAdapter.notifyItemChanged(pos);
            mAdapter.remove(pos);
            HnToastUtils.showToastShort(getString(R.string.live_cancle_follow_success));
        } else if ("addFollow".equals(type)) {//添加关注
            int pos = (int) obj;
            mAdapter.getItem(pos).setIs_follow("Y");
            mAdapter.notifyItemChanged(pos);
            HnToastUtils.showToastShort(getString(R.string.live_follow_success));
        }
    }

    /**
     * 请求失败
     *
     * @param type
     * @param code
     * @param msg
     */
    @Override
    public void requestFail(String type, int code, String msg) {
        if (loading == null) return;
        done();
        if ("follow_list".equals(type)) {//关注列表
            HnToastUtils.showToastShort(msg);
            if (mPage == 1) {
                if (code == REQUEST_NET_ERROR) {
                    setLoadViewState(HnLoadingLayout.No_Network, loading);
                } else {
                    setLoadViewState(HnLoadingLayout.Empty, loading);
                }
            }

        } else {

            HnToastUtils.showToastShort(msg);
        }
    }


    /**
     * 重新加载
     *
     * @param v
     */
    @Override
    public void onReload(View v) {
        getInitData();
    }


    @Subscribe
    public void onEventBusCallBack(HnFollowEvent event) {
        if (event != null) {
            String uid = event.getUid();
            boolean isFollow = event.isFollow();
            if (!TextUtils.isEmpty(uid) && mAdapter != null) {
                List<HnMyFocusBean.FollowsBean.ItemsBean> items = mAdapter.getData();
                for (int i = 0; i < items.size(); i++) {
                    if (uid.equals(items.get(i).getUser_id())) {
                        if (isFollow) {
                            items.get(i).setIs_follow("Y");
                        } else {
                            items.get(i).setIs_follow("N");
                        }
                        mAdapter.setNewData(items);
                        break;
                    }
                }
            }
        }

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
