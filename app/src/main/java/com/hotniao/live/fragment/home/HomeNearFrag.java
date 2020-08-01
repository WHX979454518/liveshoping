package com.hotniao.live.fragment.home;


import android.annotation.SuppressLint;
import android.app.AppOpsManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import androidx.annotation.RequiresApi;
import androidx.core.app.AppOpsManagerCompat;
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
import com.hotniao.live.HnMainActivity;
import com.hotniao.live.LiveItemProxy;
import com.hotniao.live.R;
import com.hotniao.live.eventbus.BigPicEvent;
import com.hotniao.live.eventbus.HomeNearEvent;
import com.hotniao.live.eventbus.RefreshEvent;
import com.hotniao.live.model.HnHomeLiveModel;
import com.hotniao.live.utils.HnLocationBiz;
import com.hn.library.utils.PermissionHelper;
import com.live.shoplib.ui.frag.BaseScollFragment;
import com.loopj.android.http.RequestParams;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

/**
 * 首页附近
 * 作者：Mr.Xu
 */
@RequiresApi(api = Build.VERSION_CODES.M)
@SuppressLint("ValidFragment")
public class HomeNearFrag extends BaseScollFragment implements HnLocationBiz.OnLocationListener {
    private static final int Open_Location = 0;//开启定位权限
    private static final int Open_LocationSer = 1;//开启定位服务
    private int mClickType = 2;//空数据点击类型
    private boolean big = true;
    private List<HnHomeLiveModel.DEntity.StoreEntity.ItemsEntity> mData = new ArrayList<>();
    private String mCity, mPro;
    private HnLocationBiz mHnLocationBiz;

//    public HomeNearFrag(boolean big) {
//        this.big = big;
//    }

    public static HomeNearFrag getInstance(boolean big) {
        HomeNearFrag frag = new HomeNearFrag();
        Bundle bundle = new Bundle();
        bundle.putBoolean("big", big);
        frag.setArguments(bundle);
        return frag;
    }

    @Override
    protected String setTAG() {
        return "附近";
    }

    @Override
    protected CommRecyclerAdapter setAdapter() {

        big=getArguments().getBoolean("big");

        EventBus.getDefault().register(this);
        mSpring.setMode(PtrFrameLayout.Mode.LOAD_MORE);
        page = 1;
        if (HnMainActivity.mLocEntity != null) {
            mCity = HnMainActivity.mLocEntity.getmCity();
            mPro = HnMainActivity.mLocEntity.getmProvince();
        }
        initLocation();
        return new CommRecyclerAdapter() {
            @Override
            protected void onBindView(BaseViewHolder holder, final int position) {
                LiveItemProxy.setItemView_Live(holder, mData.get(position), "附近");
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

    @Override
    protected RequestParams setRequestParam() {
        RequestParams params = new RequestParams();
        if (!TextUtils.isEmpty(mCity)) {
            params.put("city", mCity.trim());
        }else {
            return null;
        }
        if (!TextUtils.isEmpty(mPro))
            params.put("province", mPro.trim());
        return params;
    }

    @Override
    protected String setRequestUrl() {
        return HnUrl.NEAR_Live_List;
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
                    if (!isLocationEnabled()) {
                        int msgRes = mClickType == Open_Location ?
                                R.string.you_not_open_location_jurisdiction : R.string.you_not_open_location_service;
                        setEmpty(getResources().getString(msgRes), R.drawable.home_open_position);
                    } else {
                        setEmpty(getResources().getString(R.string.near_no_live), R.drawable.home_live);
                    }
                    return;
                }
                if (HnRefreshDirection.TOP == state) {
                    mData.clear();
                }
                mData.addAll(model.getD().getStore().getItems());
                if (mAdapter != null)
                    mAdapter.notifyDataSetChanged();
                if (!isLocationEnabled()) {
                    int msgRes = mClickType == Open_Location ?
                            R.string.you_not_open_location_jurisdiction : R.string.you_not_open_location_service;
                    setEmpty(getResources().getString(msgRes), R.drawable.home_open_position);
                } else {
                    setEmpty(getResources().getString(R.string.near_no_live), R.drawable.home_live);
                }
            }

            @Override
            public void hnErr(int errCode, String msg) {
                if (mRecycler == null) return;
                refreshFinish();
                refreshComplete();
//                HnToastUtils.showToastShort(msg);
                if (!isLocationEnabled()) {
                    int msgRes = mClickType == Open_Location ?
                            R.string.you_not_open_location_jurisdiction : R.string.you_not_open_location_service;
                    setEmpty(getResources().getString(msgRes), R.drawable.home_open_position);
                } else {
                    setEmpty(getResources().getString(R.string.near_no_live), R.drawable.home_live);
                }
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


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (0 == requestCode || 1 == requestCode) initLocation();
    }

    private void initLocation() {
        if (HnMainActivity.mLocEntity == null) {
            mHnLocationBiz = HnLocationBiz.getInsrance();
            mHnLocationBiz.setOnLocationListener(this);
            mHnLocationBiz.startLocation(mActivity);
        }
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void initLayouotManalager(BigPicEvent event) {
        big = event.isBig();
        if(event.isBig()){
            mRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        }else {
            mRecycler.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        }
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void event(HomeNearEvent event) {
        page = 1;
        mCity = event.getCity();
        mPro = event.getPro();
        getData(HnRefreshDirection.TOP, page);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void event(EventBusBean event) {
        if (HnConstants.EventBus.RefreshLiveList == event.getType()) {
            page = 1;
            getData(HnRefreshDirection.TOP, page);
        }
    }

    @Override
    public void onLocationSuccess(String province, String city, String address, String latitudeResult, String longitudeResult) {
        mCity = city;
        mPro = province;
//        if (Open_Location == mClickType || Open_LocationSer == mClickType) {
            getData(HnRefreshDirection.TOP, page);
//        }
    }

    @Override
    public void onLocationFail(String errorRease, int code) {

    }

    public boolean isLocationEnabled() {
        if (!PermissionHelper.isLocServiceEnable(mActivity)) {//检测是否开启定位服务
            mClickType = Open_LocationSer;
            return false;
        } else {//检测用户是否将当前应用的定位权限拒绝
            int checkResult = PermissionHelper.checkOp(mActivity, 2, AppOpsManager.OPSTR_FINE_LOCATION);//其中2代表AppOpsManager.OP_GPS，如果要判断悬浮框权限，第二个参数需换成24即AppOpsManager。OP_SYSTEM_ALERT_WINDOW及，第三个参数需要换成AppOpsManager.OPSTR_SYSTEM_ALERT_WINDOW
            int checkResult2 = PermissionHelper.checkOp(mActivity, 1, AppOpsManager.OPSTR_FINE_LOCATION);
            if (AppOpsManagerCompat.MODE_IGNORED == checkResult || AppOpsManagerCompat.MODE_IGNORED == checkResult2) {
                mClickType = Open_Location;
                return false;
            }
        }
        return true;
    }
}
