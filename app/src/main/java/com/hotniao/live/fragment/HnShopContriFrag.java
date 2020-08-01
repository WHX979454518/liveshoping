package com.hotniao.live.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.hn.library.base.BaseRequestStateListener;
import com.hn.library.base.baselist.BaseViewHolder;
import com.hn.library.base.baselist.CommRecyclerAdapter;
import com.hn.library.global.HnUrl;
import com.hn.library.global.NetConstant;
import com.hn.library.http.HnResponseHandler;
import com.hn.library.utils.HnPrefUtils;
import com.hn.library.utils.HnRefreshDirection;
import com.hn.library.utils.HnToastUtils;
import com.hn.library.utils.HnUiUtils;
import com.hotniao.live.R;
import com.hotniao.live.biz.user.follow.HnFollowBiz;
import com.hotniao.live.model.HnShopContriModel;
import com.hotniao.live.widget.ContriProxy;
import com.hotniao.livelibrary.model.HnFansContributeModel;
import com.hotniao.livelibrary.model.event.HnFollowEvent;
import com.hotniao.livelibrary.widget.dialog.HnUserDetailDialog;
import com.loopj.android.http.RequestParams;
import com.reslibrarytwo.CommListFragment;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

/**
 * 直播间剁手榜
 * 作者：Mr.Xu
 * 时间：2017/12/18
 */
public class HnShopContriFrag extends CommListFragment implements BaseRequestStateListener {

    private List<HnShopContriModel.DEntity.RankEntity.ItemsEntity> mData = new ArrayList<>();
    private HnUserDetailDialog mHnUserDetailDialog;

    private String anchor_user_id;

    public static HnShopContriFrag newInstance(String anchor_user_id) {
        HnShopContriFrag frag = new HnShopContriFrag();
        Bundle bundle = new Bundle();
        bundle.putString("id", anchor_user_id);
        frag.setArguments(bundle);
        return frag;
    }

    @Override
    protected String setTAG() {
        EventBus.getDefault().register(this);
        anchor_user_id = getArguments().getString("id");
        return "剁手榜";
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected CommRecyclerAdapter setAdapter() {
        return new CommRecyclerAdapter() {
            @Override
            protected void onBindView(BaseViewHolder holder, final int position) {
                HnFollowBiz biz = new HnFollowBiz(getActivity());
                biz.setRegisterListener(HnShopContriFrag.this);
                ContriProxy.setShopContri(holder, mData.get(position), position, biz);

                holder.getView(R.id.mIvImg).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        HnShopContriModel.DEntity.RankEntity.ItemsEntity item = mData.get(position);
                        String mUid = HnPrefUtils.getString(NetConstant.User.UID, "");
                        mHnUserDetailDialog = HnUserDetailDialog.newInstance(1, item.getUser_id(), mUid, 0);
                        mHnUserDetailDialog.setActvity(getActivity());
                        mHnUserDetailDialog.show(getChildFragmentManager(), "HnUserDetailDialog");
                    }
                });

                holder.getView(R.id.mIvImg2).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        HnShopContriModel.DEntity.RankEntity.ItemsEntity item = mData.get(position);
                        String mUid = HnPrefUtils.getString(NetConstant.User.UID, "");
                        mHnUserDetailDialog = HnUserDetailDialog.newInstance(1, item.getUser_id(), mUid, 0);
                        mHnUserDetailDialog.setActvity(getActivity());
                        mHnUserDetailDialog.show(getChildFragmentManager(), "HnUserDetailDialog");
                    }
                });

            }

            @Override
            protected int getLayoutID(int position) {
                return R.layout.item_contri;
            }

            @Override
            public int getItemCount() {
                return mData.size();
            }
        };
    }


    @Subscribe
    public void onEventBusCallBack(HnFollowEvent event) {
        if (event != null) {
            String uid = event.getUid();
            boolean isFollow = event.isFollow();
            if (!TextUtils.isEmpty(uid) && mAdapter != null) {
                for (int i = 0; i < mData.size(); i++) {
                    if (uid.equals(mData.get(i).getUser_id())) {
                        if (isFollow) {
                            mData.get(i).setIs_follow("Y");
                        } else {
                            mData.get(i).setIs_follow("N");
                        }
                        if (mAdapter != null) mAdapter.notifyDataSetChanged();
                        break;
                    }
                }
            }
        }

    }

    @Override
    protected RequestParams setRequestParam() {
        RequestParams params = new RequestParams();
        params.put("anchor_user_id", anchor_user_id);
        params.put("type", "all");
        return params;
    }

    @Override
    protected String setRequestUrl() {
        return HnUrl.CONTRI_SHOP;
    }


    @Override
    protected HnResponseHandler setResponseHandler(final HnRefreshDirection state) {
        return new HnResponseHandler<HnShopContriModel>(HnShopContriModel.class) {
            @Override
            public void hnSuccess(String response) {
                if (mActivity == null) return;
                refreshFinish();
                if (model.getD().getRank() == null) {
                    setEmpty(HnUiUtils.getString(R.string.now_no_ranking), R.drawable.empty_com);
                    return;
                }
                if (HnRefreshDirection.TOP == state) {
                    mData.clear();
                }
                mData.addAll(model.getD().getRank().getItems());
                if (mAdapter != null)
                    mAdapter.notifyDataSetChanged();
                setEmpty(HnUiUtils.getString(R.string.now_no_ranking), R.drawable.empty_com);
            }

            @Override
            public void hnErr(int errCode, String msg) {
                setEmpty(HnUiUtils.getString(R.string.now_no_ranking), R.drawable.home_no_search);
            }
        };
    }

    @Override
    public void requesting() {

    }

    @Override
    public void requestSuccess(String type, String response, Object obj) {
        if (HnFollowBiz.ADD.equals(type)) {
            int pos = (int) obj;
            mData.get(pos).setIs_follow("Y");
            if (mAdapter != null) mAdapter.notifyDataSetChanged();
//            HnToastUtils.showToastShort(getString(R.string.live_follow_success));
        } else if (HnFollowBiz.CANCLE.equals(type)) {
            int pos = (int) obj;
            mData.get(pos).setIs_follow("N");
            if (mAdapter != null) mAdapter.notifyDataSetChanged();
//            HnToastUtils.showToastShort(getString(R.string.live_cancle_follow_success));
        }
    }

    @Override
    public void requestFail(String type, int code, String msg) {
        if (HnFollowBiz.ADD.equals(type)) {
            HnToastUtils.showToastShort(msg);
        } else if (HnFollowBiz.CANCLE.equals(type)) {
            HnToastUtils.showToastShort(msg);
        }
    }
}
