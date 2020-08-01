package com.hotniao.live.fragment;

import android.content.res.Resources;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hn.library.base.BaseFragment;
import com.hn.library.base.BaseRequestStateListener;
import com.hn.library.base.EventBusBean;
import com.hn.library.daynight.DayNight;
import com.hn.library.daynight.DayNightHelper;
import com.hn.library.loadstate.HnLoadingLayout;
import com.hn.library.utils.HnBadgeView;
import com.hn.library.utils.HnPrefUtils;
import com.hn.library.utils.HnToastUtils;
import com.hn.library.utils.HnUtils;
import com.hn.library.view.FrescoImageView;
import com.hotniao.live.HnApplication;
import com.hotniao.live.HnMainActivity;
import com.hotniao.live.R;
import com.hotniao.live.activity.HnAnchorRelatedActivity;
import com.hotniao.live.activity.HnEditInfoActivity;
import com.hotniao.live.activity.HnInviteFriendActivity;
import com.hotniao.live.activity.HnWebActivity;
import com.hotniao.live.activity.account.HnMyAccountActivity;
import com.hotniao.live.activity.HnMyMessageActivity;
import com.hotniao.live.activity.HnMyFansActivity;
import com.hotniao.live.activity.HnMyFollowActivity;
import com.hotniao.live.activity.HnMyVipMemberActivity;
import com.hotniao.live.activity.HnPlatformListActivity;
import com.hotniao.live.activity.HnSettingActivity;
import com.hotniao.live.biz.user.userinfo.HnMineFragmentBiz;
import com.hn.library.global.HnConstants;
import com.hn.library.global.HnUrl;
import com.hotniao.live.eventbus.HnSignEvent;
import com.hn.library.model.HnLoginModel;
import com.hn.library.model.HnLoginBean;
import com.reslibrarytwo.HnSkinTextView;
import com.hotniao.livelibrary.util.HnLiveLevelUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Copyright (C) 2017,深圳市红鸟网络科技股份有限公司 All rights reserved.
 * 项目名称：红鸟电商
 * 类描述：我的模块
 * 创建人：mj
 * 创建时间：2017/3/6 16:16
 * 修改人：
 * 修改时间：
 * 修改备注：
 * Version:  1.0.0
 */
public class HnMineFragment extends BaseFragment implements BaseRequestStateListener {

    @BindView(R.id.iv_skin)
    ImageView ivSkin;
    @BindView(R.id.mIvMessage)
    ImageView mIvMessage;
    @BindView(R.id.iv_icon)
    FrescoImageView ivIcon;
    @BindView(R.id.tv_nick)
    TextView tvNick;
    @BindView(R.id.iv_sex)
    ImageView ivSex;
    @BindView(R.id.tv_level)
    HnSkinTextView tvLevel;
    @BindView(R.id.ll_info)
    LinearLayout llInfo;
    @BindView(R.id.ll_care)
    LinearLayout llCare;
    @BindView(R.id.ll_fans)
    LinearLayout llFans;
    @BindView(R.id.rl_my_account)
    RelativeLayout rlMyAccount;
    @BindView(R.id.rl_help)
    RelativeLayout rlHelp;
    @BindView(R.id.mIvSign)
    ImageView mIvSign;
    @BindView(R.id.rl_set)
    RelativeLayout rlSet;
    @BindView(R.id.tv_car_number)
    TextView tvCarNumber;
    @BindView(R.id.tv_fans_number)
    TextView tvFansNumber;
    @BindView(R.id.mHnLoadingLayout)
    HnLoadingLayout mHnLoadingLayout;
    @BindView(R.id.tv_live_level)
    TextView tvLiveLevel;
    @BindView(R.id.tv_intro)
    TextView tvIntro;
    @BindView(R.id.tv_uid)
    TextView tvUid;
    @BindView(R.id.mTvPlatFormList)
    TextView mTvPlatFormList;
    @BindView(R.id.mTvAnchorRelated)
    TextView mTvAnchorRelated;
    @BindView(R.id.mTvMember)
    TextView mTvMember;
    @BindView(R.id.tv_title)
    TextView mTvTitle;
    @BindView(R.id.mRlSign)
    RelativeLayout mRlSign;
    @BindView(R.id.mRlInVite)
    RelativeLayout mRlInVite;
    @BindView(R.id.mTvSignState)
    TextView mTvSignState;
    @BindView(R.id.mTvInvite)
    TextView mTvInvite;
    @BindView(R.id.mTvSign)
    TextView mTvSign;
    @BindView(R.id.mTvRecharge)
    TextView mTvRecharge;
    @BindView(R.id.mTvNewMsg)
    HnBadgeView mTvNewMsg;
    @BindView(R.id.iv_withdrawalsuccessful)
    ImageView ivWithdrawalsuccessful;
    @BindView(R.id.textView)
    TextView textView;
    @BindView(R.id.ll_edit_user_info)
    RelativeLayout llEditUserInfo;
    @BindView(R.id.mRlCenter)
    RelativeLayout mRlCenter;
    @BindView(R.id.tv_my_account)
    HnSkinTextView tvMyAccount;
    @BindView(R.id.tv_help)
    HnSkinTextView tvHelp;
    @BindView(R.id.tv_setting)
    HnSkinTextView tvSetting;

    /**
     * 我的业务逻辑类，用户处理我的相关业务
     */
    private HnMineFragmentBiz mHnMineFragmentBiz;
    /**
     * 个人用户信息数据对象
     */
    private HnLoginBean result;
    /**
     * 用户id
     */
    private String uid;

    /**
     * 条目存储容器
     */
    private List<RelativeLayout> mRlItemList;
    /**
     * 文字颜色为333的存储容器
     */
    private List<TextView> mText333List;
    /**
     * 文字颜色为666的存储容器
     */
    private List<TextView> mText666List;


    private Bundle bundle;
    private DayNightHelper mDayNightHelper;

    @OnClick({R.id.tv_uid, R.id.mIvMessage, R.id.mRlHead, R.id.ll_care, R.id.ll_fans, R.id.rl_my_account,
            R.id.rl_help, R.id.rl_set, R.id.iv_skin, R.id.mTvPlatFormList,
            R.id.mTvAnchorRelated, R.id.mTvMember, R.id.mIvEdit, R.id.mTvRecharge, R.id.mRlInVite, R.id.mRlSign})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.mRlSign://
                HnWebActivity.luncher(mActivity, getString(R.string.my_sign_in), HnUrl.USER_SIGNIN_DETAIL, HnWebActivity.Sign);
                break;
            case R.id.mTvRecharge://充值
                break;
            case R.id.ll_care://关注
                mActivity.openActivity(HnMyFollowActivity.class);
                break;
            case R.id.ll_fans://粉丝
                mActivity.openActivity(HnMyFansActivity.class);
                break;
            case R.id.rl_my_account://我的账户
                if (result == null) return;
                HnMyAccountActivity.luncher(mActivity, result.getUser_coin());
                break;
            case R.id.rl_help://帮助反馈
                HnWebActivity.luncher(mActivity, getString(R.string.help_and_feekback), HnUrl.USER_HELP_HOTQUESTION, HnWebActivity.Help);
                break;
            case R.id.rl_set://设置
                bundle = new Bundle();
                bundle.putString(HnConstants.Intent.DATA, uid);
                mActivity.openActivity(HnSettingActivity.class, bundle);
                break;
            case R.id.mIvMessage://消息
                mActivity.openActivity(HnMyMessageActivity.class);
                break;
            case R.id.mIvEdit://编辑用户资料
            case R.id.mRlHead://编辑用户资料
                bundle = new Bundle();
                if (result != null) {
                    bundle.putSerializable(HnConstants.Intent.DATA, result);
                }
                mActivity.openActivity(HnEditInfoActivity.class, bundle);
                break;
            case R.id.iv_skin://切换皮肤模式
                mActivity.showAnimation();

                boolean isDay = mDayNightHelper.isDay();
                mDayNightHelper.setMode(isDay ? DayNight.NIGHT : DayNight.DAY);
                mActivity.setTheme(isDay ? R.style.NightTheme : R.style.DayTheme);

                refreshUI();
                ((HnMainActivity) mActivity).updateFragmentUI();
                break;
            case R.id.tv_uid://优号复制
//                if (result == null) return;
//                ClipBoardUtil.to(result.getUser_id());
//                HnToastUtils.showToastShort(HnUiUtils.getString(R.string.id_clip));
                break;
            case R.id.mTvPlatFormList://红鸟电商排行
                mActivity.openActivity(HnPlatformListActivity.class);
                break;
            case R.id.mTvAnchorRelated://主播相关
                mActivity.openActivity(HnAnchorRelatedActivity.class);
                break;
            case R.id.mTvMember://我的会员
                mActivity.openActivity(HnMyVipMemberActivity.class);
                break;
            case R.id.mRlInVite://邀请
                mActivity.openActivity(HnInviteFriendActivity.class);
                break;
        }
    }

    @Override
    public int getContentViewId() {
        return R.layout.mine_framgnet;
    }

    @Override
    public void initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        mHnLoadingLayout.setStatus(HnLoadingLayout.Success);
        mHnMineFragmentBiz = new HnMineFragmentBiz(mActivity);
        mHnMineFragmentBiz.setBaseRequestStateListener(this);
        mDayNightHelper = new DayNightHelper();
        if(HnApplication.getmUserBean()!=null){
            updateUi(HnApplication.getmUserBean());
        }
    }

    @Override
    protected void initData() {
        mRlItemList = new ArrayList<>();
        mText333List = new ArrayList<>();
        mText666List = new ArrayList<>();

        // Item
        mRlItemList.add(rlMyAccount);
        mRlItemList.add(rlHelp);
        mRlItemList.add(rlSet);
        mRlItemList.add(mRlInVite);
        mRlItemList.add(mRlSign);

        //文字颜色为#333333
        mText333List.add(tvNick);
        mText333List.add(tvCarNumber);
        mText333List.add(tvFansNumber);
        mText333List.add(mTvPlatFormList);
        mText333List.add(mTvAnchorRelated);
        mText333List.add(mTvMember);
        mText333List.add(tvMyAccount);
        mText333List.add(tvHelp);
        mText333List.add(tvSetting);
        mText333List.add(mTvTitle);
        mText333List.add(mTvInvite);
        mText333List.add(mTvSign);
        mText333List.add(tvUid);


        refreshUI();

        mHnMineFragmentBiz.requestToUserInfo();
    }


    @Override
    public void requesting() {

    }

    /**
     * 请求成功:获取用户信息
     *
     * @param type
     * @param response
     * @param obj
     */
    @Override
    public void requestSuccess(String type, String response, Object obj) {
        if (mHnLoadingLayout == null) return;
        HnLoginModel model = (HnLoginModel) obj;
        if (model != null && model.getD() != null && model.getD().getUser_id() != null) {
            updateUi(model.getD());
        }
    }


    /**
     * 请求失败:获取用户信息
     *
     * @param type
     * @param
     * @param
     */
    @Override
    public void requestFail(String type, int code, String msg) {
        if (mHnLoadingLayout == null) return;
        HnToastUtils.showToastShort(msg);
    }

    /**
     * 更新界面数据
     *
     * @param result
     */
    private void updateUi(HnLoginBean result) {
        if (mActivity == null || result == null) return;
        try {
            this.result = result;

            HnPrefUtils.setString("def_img", result.getUser_avatar());
            //头像
            String logo = result.getUser_avatar();
            ivIcon.setImageURI(Uri.parse(logo));
            //昵称
            String nick = result.getUser_nickname();
            tvNick.setText(nick);
            //性别
            String sex = result.getUser_sex();
            if ("1".equals(sex)) {//男
                ivSex.setImageResource(R.mipmap.man);
            } else {
                ivSex.setImageResource(R.mipmap.girl);
            }
            //我的等级
            String userLvel = result.getUser_level();
            HnLiveLevelUtil.setAudienceLevBg(tvLevel, userLvel, true);
            //主播等级
            String liveLevel = result.getAnchor_level();
            if (TextUtils.isEmpty(liveLevel) || Integer.parseInt(liveLevel) < 1) {
                tvLiveLevel.setVisibility(View.GONE);
                mTvAnchorRelated.setVisibility(View.GONE);
            } else {
                tvLiveLevel.setVisibility(View.GONE);
                mTvAnchorRelated.setVisibility(View.VISIBLE);
                tvLiveLevel.setText(liveLevel);
            }
            //签名
            String intro = result.getUser_intro();
            if (!TextUtils.isEmpty(intro)) {
                tvIntro.setText(intro);
            }
            //id
            uid = result.getUser_id();
            tvUid.setText(getString(R.string.u_hao) + uid);
            //关注
            String careNumber = result.getUser_follow_total();
            tvCarNumber.setText(HnUtils.setNoPoint(careNumber));
            //粉丝
            String fans = result.getUser_fans_total();
            tvFansNumber.setText(HnUtils.setNoPoint(fans));

            //vip
            String vip_expire = result.getUser_is_member();
            if (!TextUtils.isEmpty(vip_expire) && !"Y".equals(vip_expire)) {
                ivWithdrawalsuccessful.setVisibility(View.GONE);
            } else {
                ivWithdrawalsuccessful.setVisibility(View.GONE);
            }
            //实名认证
        } catch (Exception e) {
        }
    }


    @Override
    public void onResume() {
        super.onResume();
        if (mHnMineFragmentBiz != null) {
            mHnMineFragmentBiz.requestToUserInfo();
        }
    }


    /**
     * 刷新UI界面
     */
    private void refreshUI() {
        //背景色
        TypedValue background = new TypedValue();
        //字体颜色#333333
        TypedValue textColor333 = new TypedValue();
        //字体颜色#666666
        TypedValue textColor666 = new TypedValue();
        //条目背景颜色
        TypedValue item_color = new TypedValue();
        Resources.Theme theme = mActivity.getTheme();
        theme.resolveAttribute(R.attr.pageBg_color, background, true);
        theme.resolveAttribute(R.attr.item_bg_color, item_color, true);
        theme.resolveAttribute(R.attr.text_color_333, textColor333, true);
        theme.resolveAttribute(R.attr.text_color_666, textColor666, true);

        //根布局背景色
        mHnLoadingLayout.setBackgroundResource(background.resourceId);
        //头布局背景色
        llEditUserInfo.setBackgroundResource(item_color.resourceId);
        mRlCenter.setBackgroundResource(item_color.resourceId);
        //Item 背景色
        for (RelativeLayout layout : mRlItemList) {
            layout.setBackgroundResource(item_color.resourceId);
        }
        mTvPlatFormList.setBackgroundResource(item_color.resourceId);
        mTvAnchorRelated.setBackgroundResource(item_color.resourceId);
        mTvMember.setBackgroundResource(item_color.resourceId);


        Resources resources = getResources();
        // 颜色为333的文字
        for (TextView textView : mText333List) {
            textView.setTextColor(resources.getColor(textColor333.resourceId));
        }
        // 颜色为666的文字
        for (TextView textView : mText666List) {
            textView.setTextColor(resources.getColor(textColor666.resourceId));
        }

        //更换图标
        boolean isDay = mDayNightHelper.isDay();
//        ivSkin.setImageResource(isDay?R.mipmap.skin:R.mipmap.skin_dark);
//        ivEdit.setImageResource(isDay?R.mipmap.data_compile:R.mipmap.data_compile_dark);
//        tvMyProfile.setLeftDrawable(isDay?R.mipmap.icon_allearnings:R.mipmap.icon_allearnings_dark);
//        tvMyAccount.setLeftDrawable(isDay?R.mipmap.icon_account:R.mipmap.icon_account_dark);
//        tvMyVip.setLeftDrawable(isDay?R.mipmap.icon_vip:R.mipmap.icon_vip_dark);
//        tvAccountDetail.setLeftDrawable(isDay?R.mipmap.icon_bill:R.mipmap.icon_bill_dark);
//        tvMyAuthentication.setLeftDrawable(isDay?R.mipmap.icon_renzheng:R.mipmap.icon_renzheng_dark);
//        tvHelp.setLeftDrawable(isDay?R.mipmap.couple_back:R.mipmap.couple_back_dark);
//        tvSetting.setLeftDrawable(isDay?R.mipmap.icon_set:R.mipmap.icon_set_dark);


        //刷新 StatusBar
        TypedValue typedValue = new TypedValue();
        theme.resolveAttribute(R.attr.colorPrimaryDark, typedValue, true);
        if (Build.VERSION.SDK_INT >= 21) {
            mActivity.getWindow().setStatusBarColor(getResources().getColor(typedValue.resourceId));
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void signEvent(HnSignEvent event) {
        if (mTvSignState == null) return;
        if (event.isSign()) {
            mTvSignState.setText(R.string.signed);
            mIvSign.setVisibility(View.GONE);
        } else {
            mTvSignState.setText(R.string.no_sign);
            mIvSign.setVisibility(View.VISIBLE);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void msgNoReadEvent(EventBusBean event) {
        if (HnConstants.EventBus.Update_Unread_Count.equals(event.getType())) {
            int noRead = (int) event.getObj();
            if (0 < noRead)
                mTvNewMsg.setVisibility(View.VISIBLE);
            else mTvNewMsg.setVisibility(View.GONE);
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
