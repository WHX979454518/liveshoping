package com.hotniao.live.widget;

import android.net.Uri;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hn.library.base.BaseRequestStateListener;
import com.hn.library.base.baselist.BaseViewHolder;
import com.hn.library.global.HnUrl;
import com.hn.library.global.NetConstant;
import com.hn.library.utils.HnPrefUtils;
import com.hn.library.view.FrescoImageView;
import com.hotniao.live.R;
import com.hotniao.live.biz.user.follow.HnFollowBiz;
import com.hotniao.live.model.HnShopContriModel;
import com.hotniao.livelibrary.model.HnFansContributeModel;

/**
 * 作者：Mr.Xu
 * 时间：2017/12/18
 */
public class ContriProxy {


    public static void setCoinContri(BaseViewHolder holder, HnFansContributeModel.DBean.RankBean.ItemsBean itemsEntity, int position, HnFollowBiz biz) {
        setView(holder,
                itemsEntity.getUser_id(),
                itemsEntity.getIs_follow().equals("Y"),
                position,
                itemsEntity.getUser_avatar(),
                itemsEntity.getUser_nickname(),
                itemsEntity.getLive_gift_coin(),
                "金币",
                "Y".equals(itemsEntity.getIs_follow()),
                itemsEntity.getUser_id(),
                biz);
    }

    public static void setShopContri(BaseViewHolder holder, HnShopContriModel.DEntity.RankEntity.ItemsEntity itemsEntity, int position, HnFollowBiz biz) {
        setView(holder,
                itemsEntity.getUser_id(),
                itemsEntity.getIs_follow().equals("Y"),
                position,
                itemsEntity.getUser_avatar(),
                itemsEntity.getUser_nickname(),
                itemsEntity.getOrder_total(),
                "单",
                "Y".equals(itemsEntity.getIs_follow()),
                itemsEntity.getUser_id(),
                biz);
    }

    public static void setView(BaseViewHolder holder, String id, final boolean isCare, final int position, String url, String name,
                               String value, String unit, boolean care, final String uid, final HnFollowBiz mHnFollowBiz) {

        TextView mTvIndex = holder.getView(R.id.mTvIndex);
        FrescoImageView mIvImg = holder.getView(R.id.mIvImg);
        FrescoImageView mIvImg2 = holder.getView(R.id.mIvImg2);
        ImageView mIvLevelTag = holder.getView(R.id.mIvLevelTag);
        TextView mTvName = holder.getView(R.id.mTvName);
        TextView mTvUnit = holder.getView(R.id.mTvUnit);
        RelativeLayout mLLA = holder.getView(R.id.mLLA);
        final TextView mTvFocus = holder.getView(R.id.mTvFocus);

        mTvUnit.setText(value + unit);
        mTvName.setText(name);
        if (position <= 2) {
            mLLA.setVisibility(View.VISIBLE);
            mTvIndex.setVisibility(View.GONE);
            mIvImg2.setVisibility(View.GONE);
            mIvLevelTag.setVisibility(View.VISIBLE);
            mIvImg.setImageURI(Uri.parse(HnUrl.setImageUrl(url)));
            if (position == 0) mIvLevelTag.setImageResource(R.drawable.no1);
            else if (position == 1) mIvLevelTag.setImageResource(R.drawable.no2);
            else mIvLevelTag.setImageResource(R.drawable.no3);
            //自己不显示
            if (TextUtils.equals(id, HnPrefUtils.getString(NetConstant.User.UID, ""))) {
                mTvFocus.setVisibility(View.GONE);
            } else {
                mTvFocus.setVisibility(View.VISIBLE);
            }

            if (care) {
                mTvFocus.setText(R.string.main_follow_on);
                mTvFocus.setSelected(false);
            } else {
                mTvFocus.setText(R.string.add_follow);
                mTvFocus.setSelected(true);
            }
            mTvFocus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (isCare) {
                        mHnFollowBiz.cancelFollow(uid, position);
                    } else {
                        mHnFollowBiz.addFollow(uid, position);
                    }
                }
            });
        } else {
            mLLA.setVisibility(View.GONE);
            mIvImg2.setImageURI(Uri.parse(HnUrl.setImageUrl(url)));
            mIvImg2.setVisibility(View.VISIBLE);
            mTvIndex.setVisibility(View.VISIBLE);
            mIvLevelTag.setVisibility(View.GONE);
            mTvIndex.setText((position + 1) + "");
            mTvFocus.setVisibility(View.GONE);
        }
    }
}
