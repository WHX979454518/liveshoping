package com.hotniao.live.fragment.home;


import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.hn.library.base.EventBusBean;
import com.hn.library.base.baselist.BaseViewHolder;
import com.hn.library.base.baselist.CommRecyclerAdapter;
import com.hn.library.global.HnConstants;
import com.hn.library.global.HnUrl;
import com.hn.library.http.HnResponseHandler;
import com.hn.library.refresh.PtrFrameLayout;
import com.hn.library.utils.HnRefreshDirection;
import com.hn.library.utils.HnToastUtils;
import com.hotniao.live.LiveItemProxy;
import com.hotniao.live.R;
import com.hotniao.live.eventbus.BigPicEvent;
import com.hotniao.live.eventbus.RefreshEvent;
import com.hotniao.live.model.HnHomeLiveModel;
import com.live.shoplib.ui.frag.BaseScollFragment;
import com.loopj.android.http.RequestParams;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

/**
 * 首页关注
 * 作者：Mr.Xu
 */
@RequiresApi(api = Build.VERSION_CODES.M)
@SuppressLint("ValidFragment")
public class HomeFollowFrag extends BaseScollFragment {


    private boolean big = true;

    private List<HnHomeLiveModel.DEntity.StoreEntity.ItemsEntity> mData = new ArrayList<>();

//    public HomeFollowFrag(boolean big) {
//        this.big = big;
//    }

    public static HomeFollowFrag getInstance(boolean big) {

        HomeFollowFrag frag = new HomeFollowFrag();
        Bundle bundle = new Bundle();
        bundle.putBoolean("big", big);
        frag.setArguments(bundle);
        return frag;

    }




    @Override
    protected String setTAG() {
        return "关注";
    }

    @Override
    protected CommRecyclerAdapter setAdapter() {

        big=getArguments().getBoolean("big");

        EventBus.getDefault().register(this);
        mSpring.setMode(PtrFrameLayout.Mode.LOAD_MORE);
        return new CommRecyclerAdapter() {
            @Override
            protected void onBindView(BaseViewHolder holder, final int position) {
                LiveItemProxy.setItemView_Live(holder, mData.get(position), "关注");
            }

            @Override
            protected int getLayoutID(int position) {
                return big ? R.layout.item_comm_live : R.layout.item_comm_live_s;
            }

            @Override
            public int getItemCount() {
                return mData.size();
            }
        };
    }


    @Subscribe
    public void initLayouotManager(BigPicEvent event) {
        big = event.isBig();
        if(event.isBig()){
            mRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        }else {
            mRecycler.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        }
    }

    @Override
    protected RequestParams setRequestParam() {
        RequestParams params = new RequestParams();
        return params;
    }

    @Override
    protected String setRequestUrl() {
        return HnUrl.Follow_Live_List;
    }

    @Override
    protected HnResponseHandler setResponseHandler(final HnRefreshDirection state) {
        return new HnResponseHandler<HnHomeLiveModel>(HnHomeLiveModel.class) {
            @Override
            public void hnSuccess(String response) {
                if (mRecycler == null) return;
                refreshFinish();
                refreshComplete();
                if (model.getD().getStore().getItems() == null) {
                    setEmpty("暂无直播", R.drawable.no_goods);
                    return;
                }
                if (HnRefreshDirection.TOP == state) {
                    mData.clear();
                }
                mData.addAll(model.getD().getStore().getItems());
                if (mAdapter != null)
                    mAdapter.notifyDataSetChanged();
                setEmpty("暂无直播", R.drawable.no_goods);
            }

            @Override
            public void hnErr(int errCode, String msg) {
                if (mRecycler == null) return;
                refreshFinish();
                refreshComplete();
                HnToastUtils.showToastShort(msg);
                setEmpty("暂无直播", R.drawable.no_goods);
            }

        };
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }


    @Override
    public void pullToRefresh() {
        getData(HnRefreshDirection.TOP, page = 1);
    }

    @Override
    public void refreshComplete() {
        EventBus.getDefault().post(new RefreshEvent());
    }

    @Override
    public View getScrollableView() {
        return mRecycler;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void event(EventBusBean event) {
        if (HnConstants.EventBus.RefreshLiveList == event.getType()) {
            page = 1;
            getData(HnRefreshDirection.TOP, page);
        }
    }

}
