package com.hotniao.live.fragment;

import android.os.Bundle;
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
import com.hotniao.live.widget.ContriProxy;
import com.hotniao.livelibrary.model.HnFansContributeModel;
import com.hotniao.livelibrary.widget.dialog.HnUserDetailDialog;
import com.loopj.android.http.RequestParams;
import com.reslibrarytwo.CommListFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * 直播间贡献榜
 * 作者：Mr.Xu
 * 时间：2017/12/18
 */
public class HnCoinContriFrag extends CommListFragment implements BaseRequestStateListener {

    private List<HnFansContributeModel.DBean.RankBean.ItemsBean> mData = new ArrayList<>();

    private HnUserDetailDialog mHnUserDetailDialog;

    private String anchor_user_id;

    public static HnCoinContriFrag newInstance(String anchor_user_id) {
        HnCoinContriFrag frag = new HnCoinContriFrag();
        Bundle bundle = new Bundle();
        bundle.putString("id", anchor_user_id);
        frag.setArguments(bundle);
        return frag;
    }

    @Override
    protected String setTAG() {
        anchor_user_id = getArguments().getString("id");
        return "贡献";
    }

    @Override
    protected CommRecyclerAdapter setAdapter() {
        return new CommRecyclerAdapter() {
            @Override
            protected void onBindView(BaseViewHolder holder, final int position) {
                HnFollowBiz biz = new HnFollowBiz(getActivity());
                biz.setRegisterListener(HnCoinContriFrag.this);
                ContriProxy.setCoinContri(holder, mData.get(position), position, biz);

                holder.getView(R.id.mIvImg).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        HnFansContributeModel.DBean.RankBean.ItemsBean item = mData.get(position);
                        String mUid = HnPrefUtils.getString(NetConstant.User.UID, "");
                        mHnUserDetailDialog = HnUserDetailDialog.newInstance(1, item.getUser_id(), mUid, 0);
                        mHnUserDetailDialog.setActvity(getActivity());
                        mHnUserDetailDialog.show(getChildFragmentManager(), "HnUserDetailDialog");
                    }
                });
                holder.getView(R.id.mIvImg2).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        HnFansContributeModel.DBean.RankBean.ItemsBean item = mData.get(position);
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

    @Override
    protected RequestParams setRequestParam() {
        RequestParams params = new RequestParams();
        params.put("anchor_user_id", anchor_user_id);
        params.put("type", "all");
        return params;
    }

    @Override
    protected String setRequestUrl() {
        return HnUrl.LIVE_RANK_ANCHORFANS;
    }


    @Override
    protected HnResponseHandler setResponseHandler(final HnRefreshDirection state) {
        return new HnResponseHandler<HnFansContributeModel>(HnFansContributeModel.class) {
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
                if (isAdded()) {
                    setEmpty(HnUiUtils.getString(R.string.now_no_ranking), R.drawable.empty_com);
                }
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
