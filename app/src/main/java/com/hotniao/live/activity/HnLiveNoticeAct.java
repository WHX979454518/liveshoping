package com.hotniao.live.activity;

import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;

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
import com.hotniao.live.model.HnHomeModel;
import com.hotniao.live.model.LiveRecommendModel;
import com.hotniao.live.model.OfficeAnnoModel;
import com.hotniao.livelibrary.util.DataTimeUtils;
import com.hotniao.livelibrary.widget.dialog.HnUserDetailDialog;
import com.live.shoplib.ShopActFacade;
import com.live.shoplib.bean.GoodsEvaModel;
import com.loopj.android.http.RequestParams;
import com.reslibrarytwo.CommListActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * 直播预告
 * 作者：Mr.Xu
 * 时间：2018/2/2
 */
public class HnLiveNoticeAct extends CommListActivity {

    private CommRecyclerAdapter mAdapter;
    private List<LiveRecommendModel.DEntity.ItmesEntity> mData = new ArrayList<>();
    private List<String> mTag = new ArrayList<>();

    @Override
    protected String setTitle() {
        mTag = getIntent().getStringArrayListExtra("list");
        return "直播预告";
    }

    @Override
    protected CommRecyclerAdapter setAdapter() {
        setShowSubTitle(true);
        mSubtitle.setVisibility(View.GONE);
        return mAdapter = new CommRecyclerAdapter() {
            @Override
            protected void onBindView(final BaseViewHolder holder, final int position) {
                if(TextUtils.isEmpty(mData.get(position).getStore_name())){
                    holder.setTextViewText(R.id.mTvName, mData.get(position).getUser_nickname());
                }else {
                    holder.setTextViewText(R.id.mTvName, mData.get(position).getStore_name());
                }
                holder.setTextViewText(R.id.mTvTitle, mData.get(position).getLive_preview_title());
//                holder.setTextViewText(R.id.mTvData, HnDateUtils.dateFormat(mData.get(position).getLive_preview_time(), "yyyy-MM-dd HH:mm"));

                String date = mData.get(position).getLive_preview_time();
                if (!TextUtils.isEmpty(date)) {
                    if (DataTimeUtils.IsToday(Long.parseLong(date))) {
                        holder.setTextViewText(R.id.mTvData, getResources().getString(R.string.day) + HnDateUtils.dateFormat(date, "HH:mm"));
                    } else if (DataTimeUtils.IsYesterday(Long.parseLong(date))) {
                        holder.setTextViewText(R.id.mTvData, getResources().getString(R.string.yesteday) + HnDateUtils.dateFormat(date, "HH:mm"));
                    } else {
                        holder.setTextViewText(R.id.mTvData, HnDateUtils.dateFormat(date, "yyyy-MM-dd HH:mm"));
                    }
                }

                final CheckBox mBoxCare = holder.getView(R.id.mBoxCare);
                mBoxCare.setChecked(!checkBox(mData.get(position).getLive_preview_id()));
                mBoxCare.setText(!checkBox(mData.get(position).getLive_preview_id()) ? "开播提醒" : "取消提醒");
                mBoxCare.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//                        mBoxCare.setChecked(!mBoxCare.isChecked());
                        setNotice(mData.get(position).getLive_preview_id(), mBoxCare.isChecked() ? "0" : "1", mBoxCare);
                    }
                });

                FrescoImageView mIvIco = holder.getView(R.id.mIvIco);
                mIvIco.setImageURI(HnUrl.setImageUri(mData.get(position).getUser_avatar()));

                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!TextUtils.isEmpty(mData.get(position).getStore_id())) {
                            ShopActFacade.openShopDetails(mData.get(position).getStore_id());
                        } else {
                            String mUid = HnPrefUtils.getString(NetConstant.User.UID, "");
                            HnUserDetailDialog mHnUserDetailDialog =
                                    HnUserDetailDialog.newInstance(1, mData.get(position).getUser_id(), mUid, 0);
                            mHnUserDetailDialog.setActvity(HnLiveNoticeAct.this);
                            mHnUserDetailDialog.show(getSupportFragmentManager(), "HnUserDetailDialog");
                        }
                    }
                });
            }

            @Override
            protected int getLayoutID(int position) {
                return R.layout.item_live_notice;
            }

            @Override
            public int getItemCount() {
                return mData.size();
            }
        };

    }

    public boolean checkBox(String id) {
        for (int i = 0; i < mTag.size(); i++) {
            if (TextUtils.equals(mTag.get(i), id)) return true;
        }
        return false;
    }

    public void setNotice(final String id, final String type, final CheckBox box) {
        RequestParams params = new RequestParams();
        params.put("id", id);
        params.put("type", type);//	1 添加 0 取消
        HnHttpUtils.postRequest(HnUrl.SET_LIVE_NOTICE, params, "预告设置", new HnResponseHandler<BaseResponseModel>(BaseResponseModel.class) {
            @Override
            public void hnSuccess(String response) {
                if (box == null) return;
                if ("0".equals(type)) {
                    box.setChecked(true);
                    box.setText("开播提醒");
                    if (checkBox(id)) mTag.remove(id);
                } else {
                    box.setChecked(false);
                    box.setText("取消提醒");
                    if (!checkBox(id)) mTag.add(id);
                }
            }

            @Override
            public void hnErr(int errCode, String msg) {
                HnToastUtils.showToastShort(msg);
                if ("0".equals(type)) {
                    box.setChecked(false);
                    box.setText("取消提醒");
                } else {
                    box.setChecked(true);
                    box.setText("开播提醒");
                    if (checkBox(id)) mTag.remove(id);
                }
//                box.setChecked(!TextUtils.equals("1", type));
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
        return HnUrl.LIVE_RECOMMEND;
    }

    @Override
    protected HnResponseHandler setResponseHandler(final HnRefreshDirection state) {
        return new HnResponseHandler<LiveRecommendModel>(LiveRecommendModel.class) {
            @Override
            public void hnSuccess(String response) {
                if (isFinishing()) return;
                refreshFinish();
                if (model.getD().getItmes() == null) {
                    setEmpty("暂无预告", R.drawable.no_comments);
                    return;
                }
                if (HnRefreshDirection.TOP == state) {
                    mData.clear();
                }
                mData.addAll(model.getD().getItmes());
                if (mAdapter != null)
                    mAdapter.notifyDataSetChanged();
                setEmpty("暂无预告", R.drawable.no_comments);
            }

            @Override
            public void hnErr(int errCode, String msg) {
                if (isFinishing()) return;
                refreshFinish();
                HnToastUtils.showToastShort(msg);
                setEmpty("暂无预告", R.drawable.no_comments);
            }
        };
    }
}
