package com.hotniao.live.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.hn.library.base.BaseActivity;
import com.hn.library.base.BaseRequestStateListener;
import com.hn.library.base.baselist.BaseViewHolder;
import com.hn.library.base.baselist.CommRecyclerAdapter;
import com.hn.library.global.HnUrl;
import com.hn.library.global.NetConstant;
import com.hn.library.http.BaseResponseModel;
import com.hn.library.http.HnHttpUtils;
import com.hn.library.http.HnResponseHandler;
import com.hn.library.utils.HnDateUtils;
import com.hn.library.utils.HnPrefUtils;
import com.hn.library.utils.HnRefreshDirection;
import com.hn.library.utils.HnToastUtils;
import com.hn.library.view.FrescoImageView;
import com.hotniao.live.R;
import com.hotniao.live.model.BlackListModel;
import com.hotniao.live.model.HnRecommTagModel;
import com.hotniao.livelibrary.widget.dialog.HnUserDetailDialog;
import com.live.shoplib.ShopActFacade;
import com.live.shoplib.bean.GoodsEvaModel;
import com.live.shoplib.ui.HnPhotoPagerActivity;
import com.live.shoplib.widget.StartRatingBar;
import com.loopj.android.http.RequestParams;
import com.reslibrarytwo.CommListActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * 黑名单
 * 作者：Mr.Xu
 * 时间：2018/2/3
 */
public class HnBlackListAct extends CommListActivity {

    private CommRecyclerAdapter mAdapter;
    private List<BlackListModel.DEntity.BlacksEntity.ItemsEntity> mData = new ArrayList<>();

    @Override
    protected String setTitle() {
        return "黑名单";
    }

    @Override
    protected CommRecyclerAdapter setAdapter() {
        setShowSubTitle(true);
        mSubtitle.setVisibility(View.GONE);
        return mAdapter = new CommRecyclerAdapter() {
            @Override
            protected void onBindView(final BaseViewHolder holder, final int position) {
                FrescoImageView mIvIco = holder.getView(R.id.mIvIco);
                final CheckBox mBoxBlack = holder.getView(R.id.mBoxBlack);
                mBoxBlack.setChecked(TextUtils.equals("Y", mData.get(position).getIs_black()));
                mBoxBlack.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mBoxBlack.setChecked(true);
                        setBack(mData.get(position).getUser_id());
                    }
                });
                mIvIco.setImageURI(HnUrl.setImageUri(mData.get(position).getUser_avatar()));
                holder.setTextViewText(R.id.mTvName, mData.get(position).getUser_nickname());
//                holder.setTextViewText(R.id.mTvTitle, mData.get(position).getUser_intro());
                holder.setTextViewText(R.id.mTv1, mData.get(position).getUser_follow_total());
                holder.setTextViewText(R.id.mTv2, mData.get(position).getUser_fans_total());
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!TextUtils.isEmpty(mData.get(position).getStore_id())) {
                            ShopActFacade.openShopDetails(mData.get(position).getStore_id());
                        } else {
                            String mUid = HnPrefUtils.getString(NetConstant.User.UID, "");

                            HnUserDetailDialog mHnUserDetailDialog =
                                    HnUserDetailDialog.newInstance(1, mData.get(position).getUser_id(), mUid, 0);
                            mHnUserDetailDialog.setActvity(HnBlackListAct.this);
                            mHnUserDetailDialog.show(getSupportFragmentManager(), "HnUserDetailDialog");
                        }
                    }
                });
            }

            @Override
            protected int getLayoutID(int position) {
                return R.layout.item_black_list;
            }

            @Override
            public int getItemCount() {
                return mData.size();
            }
        };

    }

    public void setBack(String user_id) {
        RequestParams params = new RequestParams();
        params.put("user_id", user_id);
        HnHttpUtils.postRequest(HnUrl.SET_BLACK_DEL, params, "取消黑名单", new HnResponseHandler<BaseResponseModel>(BaseResponseModel.class) {
            @Override
            public void hnSuccess(String response) {
                if (isFinishing()) return;
                HnToastUtils.showToastShort("取消成功");
                getData(HnRefreshDirection.TOP, page = 1);
            }

            @Override
            public void hnErr(int errCode, String msg) {
                HnToastUtils.showToastShort(msg);
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
        return HnUrl.BLACK_LIST;
    }

    @Override
    protected HnResponseHandler setResponseHandler(final HnRefreshDirection state) {
        return new HnResponseHandler<BlackListModel>(BlackListModel.class) {
            @Override
            public void hnSuccess(String response) {
                if (isFinishing()) return;
                refreshFinish();
                if (model.getD().getBlacks().getItems() == null) {
                    setEmpty("暂无黑名单~", com.live.shoplib.R.drawable.no_record);
                    return;
                }
                if (HnRefreshDirection.TOP == state) {
                    mData.clear();
                }
                mData.addAll(model.getD().getBlacks().getItems());
                if (mAdapter != null)
                    mAdapter.notifyDataSetChanged();
                setEmpty("暂无黑名单~", com.live.shoplib.R.drawable.no_record);
            }

            @Override
            public void hnErr(int errCode, String msg) {
                if (isFinishing()) return;
                refreshFinish();
                HnToastUtils.showToastShort(msg);
                setEmpty("暂无黑名单~", com.live.shoplib.R.drawable.no_record);
            }
        };
    }
}
