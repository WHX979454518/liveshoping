package com.hotniao.live.activity;

import android.graphics.Typeface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.hn.library.base.BaseActivity;
import com.hn.library.base.BaseRequestStateListener;
import com.hn.library.loadstate.HnLoadingLayout;
import com.hn.library.utils.HnDateUtils;
import com.hn.library.utils.HnServiceErrorUtil;
import com.hn.library.utils.HnToastUtils;
import com.hn.library.view.FrescoImageView;
import com.hotniao.live.HnApplication;
import com.hotniao.live.R;
import com.hn.library.base.baselist.BaseViewHolder;
import com.hn.library.base.baselist.CommRecyclerAdapter;
import com.hotniao.live.biz.user.vip.HnVipBiz;
import com.hn.library.global.HnUrl;
import com.hn.library.view.CommDialog;
import com.hotniao.live.model.HnVipDataModel;
import com.hotniao.live.utils.HnUiUtils;
import com.hotniao.livelibrary.config.HnLiveConstants;
import com.hotniao.livelibrary.model.event.HnLiveEvent;
import com.hotniao.livelibrary.util.HnLiveLevelUtil;
import com.reslibrarytwo.HnSkinTextView;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Copyright (C) 2016,深圳市红鸟网络科技股份有限公司 All rights reserved.
 * 类描述:我的会员
 * 创建人：刘勇
 * 创建时间：2017/2/22 16:22
 * 修改人：刘勇
 * 修改时间：2017/2/22 16:34
 * 修改备注：
 * Version:  1.0.0
 */
@Route(path = "/app/HnMyVipMemberActivity")
public class HnMyVipMemberActivity extends BaseActivity implements BaseRequestStateListener {
    @BindView(R.id.activity_my_vip)
    HnLoadingLayout mHnLoadingLayout;
    @BindView(R.id.mIvImg)
    FrescoImageView mIvImg;
    @BindView(R.id.mTvName)
    TextView mTvName;
    @BindView(R.id.mTvCoin)
    TextView mTvCoin;
    @BindView(R.id.tv_level)
    HnSkinTextView mTvLevel;
    @BindView(R.id.mTvLong)
    TextView mTvLong;
    @BindView(R.id.mTvRenew)
    TextView mTvRenew;
    @BindView(R.id.mRecycler)
    RecyclerView mRecycler;
    @BindView(R.id.mRecyclerPrivilege)
    RecyclerView mRecyclerPrivilege;
    @BindView(R.id.mTvPrice)
    TextView mTvPrice;
    @BindView(R.id.mTvOpen)
    TextView mTvOpen;
    @BindView(R.id.mLlMeal)
    LinearLayout mLlMeal;
    @BindView(R.id.mRlBottom)
    RelativeLayout mRlBottom;

    //套餐   特权
    private CommRecyclerAdapter mMealAdapter;
    private CommRecyclerAdapter mPrivilegeAdapter;

    private List<HnVipDataModel.DBean.VipComboBean> mMealData = new ArrayList<>();
    private List<HnVipDataModel.DBean.VipPrivilegeBean> mPrivilegeData = new ArrayList<>();

    //选择的套餐
    private int mSelectItem = -1;
    //Vip接口
    private HnVipBiz mHnVipBizp;
    //vip数据
    private HnVipDataModel.DBean mDbean;
    //特殊字体
    private Typeface mTypeFace;

    @Override
    public int getContentViewId() {
        return R.layout.activity_my_vip_member;
    }

    @Override
    public void onCreateNew(Bundle savedInstanceState) {
        setShowTitleBar(false);
        setTitle(R.string.vip_center);
        mTypeFace = Typeface.createFromAsset(getAssets(), "fonts/I770-Sans.ttf");
        mHnVipBizp = new HnVipBiz(this);
        mHnVipBizp.setBaseRequestStateListener(this);
        //错误重新加载
        mHnLoadingLayout.setOnReloadListener(new HnLoadingLayout.OnReloadListener() {
            @Override
            public void onReload(View v) {
                mHnLoadingLayout.setStatus(HnLoadingLayout.Loading);
                mHnVipBizp.requestToVipList(HnVipBiz.Vip_Data);
            }
        });
        mTvCoin.setText(HnApplication.getmConfig().getCoin());
        mTvPrice.setTypeface(mTypeFace);
    }


    @Override
    public void getInitData() {
        initMealAdapter();
        initPrivilegeAdapter();
        mHnVipBizp.requestToVipList(HnVipBiz.Vip_Data);
    }

    /**
     * 套餐
     */
    private void initMealAdapter() {
        mRecycler.setLayoutManager(new GridLayoutManager(this, 2));
        mRecycler.setHasFixedSize(true);

        mMealAdapter = new CommRecyclerAdapter() {
            @Override
            protected void onBindView(BaseViewHolder holder, final int position) {
                if (mSelectItem == position) {
                    holder.getView(R.id.mLlPer).setBackgroundResource(R.drawable.shap_orange_raudio_4);
                    holder.getView(R.id.mTvTime).setBackgroundResource(R.drawable.shap_bg_yellow_raudio_right4);
                    holder.getView(R.id.mTvPrice).setSelected(true);
                    holder.getView(R.id.mTvPrice1).setSelected(true);
                    holder.getView(R.id.mTvTime).setSelected(true);
                } else {

                    holder.getView(R.id.mLlPer).setBackgroundResource(R.drawable.shap_gray_raudio_4);
                    holder.getView(R.id.mTvTime).setBackgroundResource(R.drawable.shap_bg_raudio_right4);
                    holder.getView(R.id.mTvPrice).setSelected(false);
                    holder.getView(R.id.mTvPrice1).setSelected(false);
                    holder.getView(R.id.mTvTime).setSelected(false);
                }
                TextView mTvCoin = (TextView) holder.getView(R.id.mTvPrice);
                mTvCoin.setTypeface(mTypeFace);
                if (!TextUtils.isEmpty(mMealData.get(position).getCombo_fee())) {
                    String combo_fee = mMealData.get(position).getCombo_fee();
                    int i = (new Double(Double.parseDouble(combo_fee))).intValue();
                    mTvCoin.setText(i + "");
                }

                ((TextView) holder.getView(R.id.mTvTime)).setText(mMealData.get(position).getCombo_month() + HnUiUtils.getString(R.string.one_month));
                ((TextView) holder.getView(R.id.mTvPrice1)).setText(HnApplication.getmConfig().getCoin());
                holder.getView(R.id.mLlPer).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mSelectItem = position;
                        mMealAdapter.notifyDataSetChanged();
                        mTvPrice.setText(mMealData.get(position).getCombo_fee());
                    }
                });
            }

            @Override
            protected int getLayoutID(int position) {
                return R.layout.adapter_vip_member_meal;
            }

            @Override
            public int getItemCount() {
                return mMealData.size();
            }
        };

        mRecycler.setAdapter(mMealAdapter);
    }

    /**
     * 特权
     */
    private void initPrivilegeAdapter() {
        GridLayoutManager manager = new GridLayoutManager(this, 3);
        mRecyclerPrivilege.setLayoutManager(manager);
        mRecyclerPrivilege.setHasFixedSize(true);
        mPrivilegeAdapter = new CommRecyclerAdapter() {
            @Override
            protected void onBindView(BaseViewHolder holder, int position) {
                ((FrescoImageView) holder.getView(R.id.mIvImg)).setImageURI(HnUrl.setImageUri(mPrivilegeData.get(position).getLogo()));
                ((TextView) holder.getView(R.id.mTvName)).setText(mPrivilegeData.get(position).getName());
            }

            @Override
            protected int getLayoutID(int position) {
                return R.layout.adapter_vip_member_privilege;
            }

            @Override
            public int getItemCount() {
                return mPrivilegeData.size();
            }
        };
        mRecyclerPrivilege.setAdapter(mPrivilegeAdapter);
    }


    @OnClick({R.id.mIvBack, R.id.mTvRenew, R.id.mTvOpen})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.mIvBack:
                finish();
                break;
            case R.id.mTvRenew://续费
                break;
            case R.id.mTvOpen://开通
                if (mDbean == null || mDbean.getUser() == null || mDbean.getVip_combo() == null)
                    return;
                if (-1 == mSelectItem) {
                    HnToastUtils.showToastShort(getString(R.string.please_vip_meal));
                    return;
                }
                CommDialog.newInstance(HnMyVipMemberActivity.this).setClickListen(new CommDialog.TwoSelDialog() {
                    @Override
                    public void leftClick() {
                    }

                    @Override
                    public void rightClick() {
                        if (Double.parseDouble(mMealData.get(mSelectItem).getCombo_fee()) > Double.parseDouble(mDbean.getUser().getUser_coin())) {
                            notEnough();
                        } else {
                            mHnVipBizp.requestToBuyVip(mMealData.get(mSelectItem).getCombo_id(), mDbean.getOrder_id());
                        }
                    }
                }).setTitle(getString(R.string.buy_Vip)).setContent(getString(R.string.can_sure_buy_vip))
                        .setRightText(getString(R.string.buy)).show();

                break;
        }
    }


    @Override
    public void requesting() {
        showDoing(getResources().getString(R.string.loading), null);
    }

    @Override
    public void requestSuccess(String type, String response, Object obj) {
        if (mHnLoadingLayout == null) return;
        done();
        if (HnVipBiz.Vip_Data.equals(type)) {//获取vip列表
            setLoadViewState(HnLoadingLayout.Success, mHnLoadingLayout);
            HnVipDataModel model = (HnVipDataModel) obj;
            if (model != null && model.getD() != null || model.getD().getUser() == null) {
                setShowBack(false);
                setShowTitleBar(false);
                updataUI(model.getD());
            } else {
                setLoadViewState(HnLoadingLayout.Error, mHnLoadingLayout);
                setShowTitleBar(true);
                setShowBack(true);
                setTitle(R.string.vip_center);


            }
        } else if (HnVipBiz.Buy_Vip.equals(type)) {
            mHnVipBizp.requestToVipList(HnVipBiz.Vip_Data_Refresh);

            EventBus.getDefault().post(new HnLiveEvent(0, HnLiveConstants.EventBus.Buy_VIP_Success, null));

        } else if (HnVipBiz.Vip_Data_Refresh.equals(type)) {
            HnVipDataModel model = (HnVipDataModel) obj;
            if (model != null && model.getD() != null || model.getD().getUser() == null) {
                updataUI(model.getD());

                CommDialog.newInstance(HnMyVipMemberActivity.this).setClickListen(new CommDialog.TwoSelDialog() {
                    @Override
                    public void leftClick() {
                    }

                    @Override
                    public void rightClick() {
                    }
                }).setTitle(getString(R.string.buy_Vip))
                        .setContent(getString(R.string.buy_vip_success_long_to) +
                                HnDateUtils.stampToDate(model.getD().getUser().getUser_member_expire_time()))
                        .show();
            }

        }
    }

    /**
     * 更新数据
     *
     * @param d
     */
    private void updataUI(HnVipDataModel.DBean d) {
        mDbean = d;
        if (d.getUser() != null) {
            mIvImg.setImageURI(HnUrl.setImageUri(d.getUser().getUser_avatar()));
            mTvName.setText(d.getUser().getUser_nickname());
            if ("N".equals(d.getUser().getUser_is_member())) {
                mTvLong.setText(R.string.not_open_vip);
                mTvOpen.setText(R.string.now_to_open);
            } else {
                mTvLong.setText(HnUiUtils.getString(R.string.app_vip_validity_to) + HnDateUtils.stampToDate(d.getUser().getUser_member_expire_time()));
                mTvOpen.setText(R.string.now_renew);
            }
            HnLiveLevelUtil.setAudienceLevBg(mTvLevel, d.getUser().getUser_level(), true);
        }

        if (d.getVip_combo() != null) {
            mMealData.clear();
            mMealData.addAll(d.getVip_combo());
            if (mMealData.size() > 0) {
                mSelectItem = 0;
                mTvPrice.setText(mMealData.get(0).getCombo_fee());
            }

            if (mMealAdapter != null) mMealAdapter.notifyDataSetChanged();
        }
        if (d.getVip_privilege() != null) {
            mPrivilegeData.clear();
            mPrivilegeData.addAll(d.getVip_privilege());
            if (mPrivilegeAdapter != null) mPrivilegeAdapter.notifyDataSetChanged();
        }

    }


    @Override
    public void requestFail(String type, int code, String msg) {
        if (mHnLoadingLayout == null) return;
        done();
        if (HnVipBiz.Vip_Data.equals(type)) {//获取vip列表
            HnToastUtils.showToastShort(msg);
        } else if (HnVipBiz.Buy_Vip.equals(type)) {//购买vip
            if (code == HnServiceErrorUtil.USER_COIN_NOT_ENOUGH) {
                notEnough();
            } else {
                HnToastUtils.showToastShort(msg);
            }
        }
    }

    /**
     * 余额不足弹窗
     */
    private void notEnough() {
        CommDialog.newInstance(HnMyVipMemberActivity.this).setClickListen(new CommDialog.TwoSelDialog() {
            @Override
            public void leftClick() {
            }

            @Override
            public void rightClick() {
            }
        }).setTitle(getString(R.string.buy_Vip))
                .setContent(String.format(HnUiUtils.getString(R.string.your_coin_samll_to_recharge), HnApplication.getmConfig().getCoin()))
                .setRightText(getString(R.string.now_recharge)).show();
    }
}
