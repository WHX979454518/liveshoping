package com.hotniao.live.fragment.home;


import android.annotation.SuppressLint;
import android.os.Build;
import android.text.TextUtils;
import android.view.View;

import androidx.annotation.RequiresApi;

import com.hn.library.base.EventBusBean;
import com.hn.library.base.baselist.BaseViewHolder;
import com.hn.library.base.baselist.CommRecyclerAdapter;
import com.hn.library.global.HnConstants;
import com.hn.library.global.HnUrl;
import com.hn.library.http.HnResponseHandler;
import com.hn.library.refresh.PtrFrameLayout;
import com.hn.library.utils.HnRefreshDirection;
import com.hn.library.utils.HnToastUtils;
import com.hn.library.view.FrescoImageView;
import com.hotniao.live.R;
import com.hotniao.live.eventbus.BigPicEvent;
import com.hotniao.live.eventbus.RefreshEvent;
import com.hotniao.live.model.HnHomeLiveModel;
import com.hotniao.livelibrary.util.HnLiveSwitchDataUitl;
import com.live.shoplib.ui.frag.BaseScollFragment;
import com.live.shoplib.widget.FullyLinearLayoutManager;
import com.loopj.android.http.RequestParams;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

/**
 * 首页热门
 * 作者：Mr.Xu
 */
@RequiresApi(api = Build.VERSION_CODES.M)
@SuppressLint("ValidFragment")
public class HomeHotFrag extends BaseScollFragment {

    public static HomeHotFrag getInstance() {

        HomeHotFrag frag = new HomeHotFrag();
        return frag;

    }

    private List<HnHomeLiveModel.DEntity.StoreEntity.ItemsEntity> mData = new ArrayList<>();

    @Override
    protected String setTAG() {
        mRecycler.setLayoutManager(new FullyLinearLayoutManager(getActivity()));
        return "热门";
    }

    @Subscribe
    public void initLayouotManager(BigPicEvent event) {
    }

    @Override
    protected CommRecyclerAdapter setAdapter() {

        EventBus.getDefault().register(this);
        mSpring.setMode(PtrFrameLayout.Mode.LOAD_MORE);
        return new CommRecyclerAdapter() {
            @Override
            protected void onBindView(BaseViewHolder holder, final int position) {
                //todo 确保之前的跳转是怎么跳的
                //LiveItemProxy.setItemView_Live(holder, mData.get(position), "热门");
                bindData(holder,position);
            }

            @Override
            protected int getLayoutID(int position) {
                return R.layout.item_hot_recommend_shop;
            }

            @Override
            public int getItemCount() {
                return mData.size();
            }
        };
    }


    private void bindData(final BaseViewHolder holder, final int position) {
        final HnHomeLiveModel.DEntity.StoreEntity.ItemsEntity itemsEntity = mData.get(position);

        FrescoImageView mIvIco = holder.getView(R.id.shop_introduce_img);
        mIvIco.setImageURI(HnUrl.setImageUri(itemsEntity.getAnchor_live_img()));
        FrescoImageView shopHead = holder.getView(R.id.shop_head_img);
        shopHead.setImageURI(HnUrl.setImageUri(itemsEntity.getIcon()));
        holder.setTextViewText(R.id.shop_name, itemsEntity.getStore_name());
        //商品总数和点赞数
        holder.setTextViewText(R.id.shop_introduce_tips, itemsEntity.getTotal_online_goods() + "件商品 " + itemsEntity.getTotal_collect() + "人收藏");
        holder.getView(R.id.enter_shop).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(itemsEntity.getUser_id())) {
                    HnLiveSwitchDataUitl.joinRoom(holder.itemView.getContext(), itemsEntity.getUser_id(), itemsEntity.getStore_id());
                } else {
                    HnLiveSwitchDataUitl.joinRoom(holder.itemView.getContext(), itemsEntity.getAnchor_user_id(), itemsEntity.getStore_id());
                }
            }
        });
    }


    @Override
    protected RequestParams setRequestParam() {
        RequestParams params = new RequestParams();
        return params;
    }

    @Override
    protected String setRequestUrl() {
        return HnUrl.Live_HOT;
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
        if (EventBus.getDefault().isRegistered(this))
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
    public void eventRefresh(EventBusBean event) {
        if (HnConstants.EventBus.RefreshLiveList == event.getType()) {
            page = 1;
            getData(HnRefreshDirection.TOP, page);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
    }
}
