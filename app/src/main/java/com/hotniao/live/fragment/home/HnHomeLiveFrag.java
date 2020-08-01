package com.hotniao.live.fragment.home;

import android.annotation.SuppressLint;
import android.text.TextUtils;

import com.hn.library.base.EventBusBean;
import com.hn.library.base.baselist.BaseViewHolder;
import com.hn.library.base.baselist.CommRecyclerAdapter;
import com.hn.library.global.HnConstants;
import com.hn.library.global.HnUrl;
import com.hn.library.http.HnResponseHandler;
import com.hn.library.utils.HnRefreshDirection;
import com.hotniao.live.LiveItemProxy;
import com.hotniao.live.R;
import com.hotniao.live.model.HnHomeLiveModel;
import com.loopj.android.http.RequestParams;
import com.reslibrarytwo.CommListFragment;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

/**
 * 首页分类
 * 作者：Mr.Xu
 * 时间：2017/12/7
 */
@SuppressLint("ValidFragment")
public class HnHomeLiveFrag extends CommListFragment {

    private String type;

    private List<HnHomeLiveModel.DEntity.StoreEntity.ItemsEntity> mData = new ArrayList<>();
//    public static Fragment newInstance(String type) {
//        HnHomeLiveFrag frag = new HnHomeLiveFrag();
//        Bundle bundle = new Bundle();
//        bundle.putString("type", type);
//        frag.setArguments(bundle);
//        return frag;
//    }
    public HnHomeLiveFrag(String type){
        this.type = type;
    }

    @Override
    protected String setTAG() {
        EventBus.getDefault().register(this);
        return TextUtils.isEmpty(type)?"热门直播":"商品分类";
    }

    @Override
    protected CommRecyclerAdapter setAdapter() {
        return new CommRecyclerAdapter() {
            @Override
            protected void onBindView(BaseViewHolder holder, int position) {
                LiveItemProxy.setItemView_Live(holder, mData.get(position), TAG);
            }

            @Override
            protected int getLayoutID(int position) {
                return R.layout.item_comm_live;
            }

            @Override
            public int getItemCount() {
                return mData.size();
            }
        };
    }

    @Override
    protected RequestParams setRequestParam() {
        RequestParams params = new RequestParams();
        if (!TextUtils.isEmpty(type)) params.add("type", type);
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
                if (mActivity == null) return;
                refreshFinish();
                if (model.getD().getStore() == null) {
                    setEmpty("暂无直播", R.drawable.home_live);
                    return;
                }
                if (HnRefreshDirection.TOP == state) {
                    mData.clear();
                }
                mData.addAll(model.getD().getStore().getItems());
                if (mAdapter != null)
                    mAdapter.notifyDataSetChanged();
                setEmpty("暂无直播", R.drawable.home_live);
            }

            @Override
            public void hnErr(int errCode, String msg) {
                setEmpty("暂无直播", R.drawable.home_live);
            }
        };
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void event(EventBusBean event) {
        if (HnConstants.EventBus.RefreshLiveList == event.getType()) {
            page = 1;
            getData(HnRefreshDirection.TOP, page);
        }
    }
}
