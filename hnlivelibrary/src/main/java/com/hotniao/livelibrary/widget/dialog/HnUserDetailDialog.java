package com.hotniao.livelibrary.widget.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.DialogFragment;

import com.alibaba.android.arouter.launcher.ARouter;
import com.hn.library.base.BaseRequestStateListener;
import com.hn.library.global.NetConstant;
import com.hn.library.utils.ClipBoardUtil;
import com.hn.library.utils.HnToastUtils;
import com.hn.library.utils.HnUiUtils;
import com.hn.library.utils.HnUtils;
import com.hn.library.view.CommDialog;
import com.hn.library.view.FrescoImageView;
import com.hotniao.livelibrary.R;
import com.hotniao.livelibrary.biz.HnUserDetailBiz;
import com.hotniao.livelibrary.config.HnLiveConstants;
import com.hotniao.livelibrary.model.HnUserInfoDetailModel;
import com.hotniao.livelibrary.model.bean.HnUserInfoDetailBean;
import com.hotniao.livelibrary.model.event.HnFollowEvent;
import com.hotniao.livelibrary.model.event.HnLiveEvent;
import com.hotniao.livelibrary.util.HnLiveLevelUtil;
import com.hotniao.livelibrary.widget.dialog.privateLetter.HnPrivateLetterDetailDialog;
import com.live.shoplib.ShopActFacade;
import com.reslibrarytwo.HnSkinTextView;
import com.reslibrarytwo.LevelView;

import org.greenrobot.eventbus.EventBus;


/**
 * Copyright (C) 2017,深圳市红鸟网络科技股份有限公司 All rights reserved.
 * 项目名称：红鸟电商
 * 类描述：直播间  --  用户详情卡片
 * 创建人：阳石柏
 * 创建时间：2017/3/6 16:16
 * 修改人：阳石柏
 * 修改时间：2017/3/6 16:16
 * 修改备注：
 * Version:  1.0.0
 */
public class HnUserDetailDialog extends DialogFragment implements View.OnClickListener, BaseRequestStateListener {

    public ICancelDialog icancel;
    private FrescoImageView ivHeaderIcon;
    private ImageView ivVip, mIvUserType, mIvBgType;
    private ImageView mIvCancle;
    private TextView tvNick, mTvReport, mTvAdmin, mTvRank;
    private ImageView ivSex;
    private HnSkinTextView tvLiveLevel;
    private TextView tvUNumber;
    private TextView tvIntro;
    private TextView tvCareNumber;
    private TextView tvFansNuumber;
    private HnSkinTextView tvCare;
    private TextView tvChat;
    private TextView tvShop;
    private TextView mTvBlack;
    private LinearLayout llBg, mLlAnchor;
    private LevelView mLevelAnchor, mLevelUser;
    private View viewLine, mViewLine2, mViewLine3;
    /**
     * 用于区别是从那个界面进入的， 0 从私聊界面进入，点击私聊，返回即可  1 从其他界面进入，点击私聊进入聊天界面 2  从直播间进入，点击私聊弹出dialog
     */
    private int from = -1;
    /**
     * 查看的用户id
     */
    private String uid;
    /**
     * 自己的id
     */
    private String myUid;
    /**
     * 房间Id
     */
    private String mAnchorId;
    /**
     * 是否房管
     */
    private String mIsAdmin;
    /**
     * 用户信息数据
     */
    private HnUserInfoDetailBean result;
    /**
     * 业务逻辑类
     */
    private HnUserDetailBiz mHnUserDetailBiz;
    /**
     * 是否已关注
     */
    private boolean isCared = false;
    /**
     * 0 显示在中间   1 显示在底部
     */
    private int showType = 0;
    private Activity mActivity;
    /**
     * 对话框是否退出
     */
    private boolean isonDismiss = false;

    public static HnUserDetailDialog newInstance(int from, String uid, String myUid, int showType) {
        Bundle bundle = new Bundle();
        bundle.putString("uid", uid);
        bundle.putString("my_uid", myUid);
        bundle.putInt("from", from);
        bundle.putInt("showType", showType);
        HnUserDetailDialog sDialog = new HnUserDetailDialog();
        sDialog.setArguments(bundle);
        return sDialog;
    }

    /**
     * @param from     0 从私聊界面进入，点击私聊，返回即可  1 从其他界面进入，点击私聊进入聊天界面 2  从直播间进入，点击私聊弹出dialog
     * @param uid      用户id
     * @param myUid    我的Id
     * @param showType 显示位置 0 显示在中间   1 显示在底部
     * @param anchorId 主播Id
     * @param isAdmin  是否管理员
     * @return
     */
    public static HnUserDetailDialog newInstance(int from, String uid, String myUid, int showType, String anchorId, String isAdmin) {
        Bundle bundle = new Bundle();
        bundle.putString("uid", uid);
        bundle.putString("my_uid", myUid);
        bundle.putInt("from", from);
        bundle.putInt("showType", showType);
        bundle.putString("mAnchorId", anchorId);
        bundle.putString("isAdmin", isAdmin);
        HnUserDetailDialog sDialog = new HnUserDetailDialog();
        sDialog.setArguments(bundle);
        return sDialog;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = getActivity();
        Bundle bundle = getArguments();
        if (bundle != null) {
            uid = bundle.getString("uid");
            myUid = bundle.getString("my_uid");
            from = bundle.getInt("from");
            showType = bundle.getInt("showType");
            mAnchorId = bundle.getString("mAnchorId");
            mIsAdmin = bundle.getString("isAdmin");
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View view = View.inflate(mActivity, R.layout.live_dialog_user_detail_layout, null);
        Dialog dialog = null;
        if (from == 2) {
            dialog = new Dialog(mActivity, R.style.BottomDialog);
        } else {
            dialog = new Dialog(mActivity, R.style.live_Dialog_Style);
        }
        dialog.setContentView(view);
        dialog.setCanceledOnTouchOutside(true);
        Window alertWindow = dialog.getWindow();
        WindowManager.LayoutParams params = alertWindow.getAttributes();
        if (showType == 0) {
            alertWindow.setGravity(Gravity.CENTER);
        } else {
            alertWindow.setGravity(Gravity.BOTTOM);
        }
        params.width = (mActivity.getWindowManager().getDefaultDisplay().getWidth());
        alertWindow.setAttributes(params);
        //初始化组件
        initView(view);
        //初始化数据
        initData();
        return dialog;
    }

    /**
     * 初始化视图
     *
     * @param view
     */
    private void initView(View view) {

        llBg = (LinearLayout) view.findViewById(R.id.ll_btn_bg);
        mLlAnchor = (LinearLayout) view.findViewById(R.id.mLlAnchor);
        mIvBgType = (ImageView) view.findViewById(R.id.mIvBgType);
        mIvUserType = (ImageView) view.findViewById(R.id.mIvUserType);
        viewLine = (View) view.findViewById(R.id.view_line);
        mViewLine2 = (View) view.findViewById(R.id.mViewLine2);
        mViewLine3 = (View) view.findViewById(R.id.mViewLine3);

        /**关闭*/
        mIvCancle = (ImageView) view.findViewById(R.id.mIvCancle);
        mIvCancle.setOnClickListener(this);


        /**举报 管理*/
        mTvReport = (TextView) view.findViewById(R.id.mTvReport);
        mTvAdmin = (TextView) view.findViewById(R.id.mTvAdmin);
        mTvReport.setOnClickListener(this);
        mTvAdmin.setOnClickListener(this);

        /**id  sex   lv   简介  name   vip  头像  排名*/
        ivHeaderIcon = (FrescoImageView) view.findViewById(R.id.anchor_head_img);
        ivVip = (ImageView) view.findViewById(R.id.mIvVip);
        tvNick = (TextView) view.findViewById(R.id.tv_nick);
        mTvRank = (TextView) view.findViewById(R.id.mTvRank);
        ivSex = (ImageView) view.findViewById(R.id.iv_sex);
//        tvUserLevel = (HnSkinTextView) view.findViewById(R.id.tv_level); TODO
        tvLiveLevel = (HnSkinTextView) view.findViewById(R.id.tv_live_level);
        mLevelAnchor = view.findViewById(R.id.mLevelAnchor);
        mLevelUser = view.findViewById(R.id.mLevelUser);
        tvUNumber = (TextView) view.findViewById(R.id.tv_uid);
        tvUNumber.setOnClickListener(this);
        tvIntro = (TextView) view.findViewById(R.id.tv_intro);

        /**关注等数据*/
        tvCareNumber = (TextView) view.findViewById(R.id.tv_care_numer);
        tvFansNuumber = (TextView) view.findViewById(R.id.tv_fan_number);

        /**关注私聊*/
        tvCare = (HnSkinTextView) view.findViewById(R.id.tv_care);
        tvCare.setOnClickListener(this);
        tvCare.setEnabled(false);
        tvChat = (TextView) view.findViewById(R.id.tv_chat);
        tvChat.setOnClickListener(this);
        tvChat.setEnabled(false);

        tvShop = (TextView) view.findViewById(R.id.tv_shop);
        tvShop.setOnClickListener(this);
        tvShop.setEnabled(true);


        mTvBlack = (TextView) view.findViewById(R.id.mTvBlack);
        mTvBlack.setOnClickListener(this);
        mTvBlack.setEnabled(true);


        if (from == 2) {
            /**直播间进入*/
            mIvCancle.setVisibility(View.GONE);
            if (uid.equals(mAnchorId)) {//用户是否主播
                mTvReport.setVisibility(View.VISIBLE);
                mTvAdmin.setVisibility(View.GONE);
            } else if (myUid.equals(mAnchorId)) {//我是否主播
                mTvReport.setVisibility(View.GONE);
                mTvAdmin.setVisibility(View.VISIBLE);
            } else if ("Y".equals(mIsAdmin)) {//我是否管理员
                if (uid.equals(mAnchorId)) mTvReport.setVisibility(View.VISIBLE);
                else mTvReport.setVisibility(View.GONE);
                mTvAdmin.setVisibility(View.VISIBLE);
            } else {
                mTvReport.setVisibility(View.GONE);
                mTvAdmin.setVisibility(View.GONE);
            }


        } else {
            /**非直播间*/
            mIvCancle.setVisibility(View.VISIBLE);
            mTvReport.setVisibility(View.GONE);
            mTvAdmin.setVisibility(View.GONE);
        }


    }

    /**
     * 初始化组件
     */
    private void initData() {
        mHnUserDetailBiz.requestToUserDetail(uid, mAnchorId);
    }

    public void setActvity(Activity activity) {
        this.mActivity = activity;
        mHnUserDetailBiz = new HnUserDetailBiz(mActivity);
        mHnUserDetailBiz.setBaseRequestStateListener(this);
    }

    @Override
    public void requesting() {

    }

    @Override
    public void requestSuccess(String type, String response, Object obj) {
        if (mActivity == null || ivHeaderIcon == null || isonDismiss) return;
        if ("user_info_detail".equals(type)) {
            HnUserInfoDetailModel model = (HnUserInfoDetailModel) obj;
            if (model != null && model.getD() != null && model.getD() != null) {
                updateUI(model.getD());
            }
        } else if ("follow".equals(type)) {
            tvCare.setEnabled(true);
            if (isCared) {
                isCared = false;
                tvCare.setText(R.string.live__add_cancle_care);
                tvCare.setSelected(true);
                tvCare.setLeftDrawable(R.drawable.personal_data_add);
                HnToastUtils.showToastShort(getString(R.string.live_cancle_follow_success));
                EventBus.getDefault().post(new HnLiveEvent(1, HnLiveConstants.EventBus.Follow, uid));
            } else {
                isCared = true;
                tvCare.setText(R.string.live_cancle_care);
                tvCare.setSelected(false);
                tvCare.setLeftDrawable(R.drawable.personal_data_tick);
                HnToastUtils.showToastShort(getString(R.string.live_follow_success));
                EventBus.getDefault().post(new HnLiveEvent(0, HnLiveConstants.EventBus.Follow, uid));
            }
            EventBus.getDefault().post(new HnFollowEvent(uid, isCared));
        } else if ("black_del".equals(type)) {
            mTvBlack.setText("拉黑");
            tvCare.setText(R.string.live_add_care);
            tvCare.setSelected(true);
            tvCare.setLeftDrawable(R.drawable.personal_data_add);
        } else if ("black_add".equals(type)) {
            mTvBlack.setText("取消拉黑");
        }
    }

    @Override
    public void requestFail(String type, int code, String msg) {
        if (mActivity == null || ivHeaderIcon == null || isonDismiss) return;
        if ("user_info_detail".equals(type)) {
            HnToastUtils.showToastShort(msg);
        } else if ("follow".equals(type)) {
            tvCare.setEnabled(true);
            HnToastUtils.showToastShort(msg);
        } else if ("black_del".equals(type)) {
            HnToastUtils.showToastShort(msg);
            mTvBlack.setText("取消拉黑");
        } else if ("black_add".equals(type)) {
            mTvBlack.setText("拉黑");
            HnToastUtils.showToastShort(msg);
        }
    }

    /**
     * 更新界面数据
     *
     * @param data
     */
    private void updateUI(HnUserInfoDetailBean data) {
        if (mActivity == null) return;
        this.result = data;
        String id = data.getUser_id();
        if (myUid.equals(id)) {/**自己 不可关注和私信  无管理举报*/
            llBg.setVisibility(View.GONE);
            viewLine.setVisibility(View.INVISIBLE);
            mTvReport.setVisibility(View.GONE);
            mTvAdmin.setVisibility(View.GONE);
        } else {
            llBg.setVisibility(View.VISIBLE);
            viewLine.setVisibility(View.VISIBLE);
            tvCare.setEnabled(true);
            tvChat.setEnabled(true);
        }

        if (TextUtils.equals(result.getIs_black(), "Y")) {
            mTvBlack.setText("取消拉黑");
        } else {
            mTvBlack.setText("拉黑");
        }

        /**直播间进入 管理员不能相互操作*/
        if (!myUid.equals(mAnchorId) && "Y".equals(data.getIs_anchor_admin())) {
            mTvReport.setVisibility(View.GONE);
            mTvAdmin.setVisibility(View.GONE);
        }

        if (TextUtils.isEmpty(result.getStore_id()) || TextUtils.equals("0", result.getStore_id())) {
            tvShop.setVisibility(View.GONE);
            mViewLine3.setVisibility(View.GONE);
            mLevelAnchor.setVisibility(View.GONE);
        } else {
            tvShop.setVisibility(View.VISIBLE);
            mViewLine3.setVisibility(View.VISIBLE);

            mLevelAnchor.setVisibility(View.VISIBLE);
            mLevelAnchor.setLevelAnchor(Integer.valueOf(result.getAnchor_level()));
        }

        try {
            mLevelUser.setLevel(Integer.valueOf(result.getUser_level()));
        } catch (NumberFormatException e) {
            mLevelUser.setLevel(0);
            e.printStackTrace();
        }
        //头像
        String avator = data.getUser_avatar();
        ivHeaderIcon.setImageURI(NetConstant.setImageUri(avator));
        //name
        String nick = data.getUser_nickname();
        tvNick.setText(nick);
        //性别
        String sex = data.getUser_sex();
        if ("1".equals(sex)) {//男
            ivSex.setImageResource(R.drawable.man);
        } else {
            ivSex.setImageResource(R.drawable.girl);
        }
        //我的等级
//        String userLvel = data.getUser_level();
//        HnLiveLevelUtil.setAudienceLevBg(tvUserLevel, userLvel, true);
        //主播等级
        String liveLevel = data.getAnchor_level();
        if (TextUtils.isEmpty(liveLevel) || 1 > Integer.parseInt(liveLevel)) {
            tvLiveLevel.setVisibility(View.GONE);
            mTvRank.setVisibility(View.GONE);
        } else {
            tvLiveLevel.setVisibility(View.VISIBLE);
            tvLiveLevel.setText("Lv" + liveLevel);
            mTvRank.setVisibility(View.VISIBLE);
            if (TextUtils.isEmpty(data.getAnchor_ranking()) || 1 > Integer.parseInt(data.getAnchor_ranking())) {
                mTvRank.setText(HnUiUtils.getString(R.string.live_hot_anchor_rank_no) + "200+" + getString(R.string.live_ranking));
            } else {
                mTvRank.setText(HnUiUtils.getString(R.string.live_hot_anchor_rank_no) + data.getAnchor_ranking() + getString(R.string.live_ranking));
            }
        }

        //是否是vip
        String isVip = data.getUser_is_member();
        if (TextUtils.isEmpty(isVip) || "N".contains(isVip)) {
            ivVip.setVisibility(View.GONE);
        } else {
            ivVip.setVisibility(View.VISIBLE);
        }

        //签名
        String intro = data.getUser_intro();
        if (!TextUtils.isEmpty(intro)) {
            tvIntro.setText(intro);
        } else {
            if (myUid.equals(id)) {//自己
                tvIntro.setText(getString(R.string.live_owner_no_intro));
            } else {
                tvIntro.setText(getString(R.string.live_user_no_intro));
            }
        }

        //关注
        String careNumber = data.getUser_follow_total();
        tvCareNumber.setText(HnUtils.setNoPoint(careNumber));

        //粉丝
        String fans = data.getUser_fans_total();
        tvFansNuumber.setText(HnUtils.setNoPoint(fans));

        //收礼
        String receiverGift = data.getUser_collect_total();

        //送礼
        String sendGift = data.getUser_consume_total();

        //id
        uid = data.getUser_id();
        if (tvUNumber == null) return;
        tvUNumber.setText(getResources().getString(R.string.live_u_hao) + uid);
        //是否已关注
        String isFollow = data.getIs_follow();
        if ("N".equals(isFollow)) {
            isCared = false;
            tvCare.setText(R.string.live_add_care);
            tvCare.setSelected(true);
            tvCare.setLeftDrawable(R.drawable.personal_data_add);
        } else {
            isCared = true;
            tvCare.setText(R.string.live_cancle_care);
            tvCare.setLeftDrawable(R.drawable.personal_data_tick);
            tvCare.setSelected(false);
        }
        /**判断会员等级*/
        if (!TextUtils.isEmpty(isVip) && "Y".contains(isVip)) {
            mIvUserType.setImageResource(R.drawable.member_information);
            mIvBgType.setImageResource(R.drawable.member_information_round);
            viewLine.setBackgroundColor(0xfffce4c5);
            mViewLine2.setBackgroundColor(0xfffce4c5);
            mViewLine3.setBackgroundColor(0xfffce4c5);
            mIvBgType.setVisibility(View.VISIBLE);
            mIvUserType.setVisibility(View.VISIBLE);
        } else if (!TextUtils.isEmpty(data.getIs_card_effect()) && "Y".contains(data.getIs_card_effect())) {
            mIvUserType.setImageResource(R.drawable.advanced_special_effects);
            mIvBgType.setImageResource(R.drawable.advanced_special_effects_round);
            viewLine.setBackgroundColor(0xfff9e2e5);
            mViewLine2.setBackgroundColor(0xfff9e2e5);
            mViewLine3.setBackgroundColor(0xfff9e2e5);
            mIvBgType.setVisibility(View.VISIBLE);
            mIvUserType.setVisibility(View.VISIBLE);
        } else {
            mIvBgType.setVisibility(View.GONE);
            mIvUserType.setVisibility(View.GONE);
        }

        if (tvLiveLevel.getVisibility() != View.VISIBLE && ivVip.getVisibility() != View.VISIBLE && mTvRank.getVisibility() != View.VISIBLE)
            mLlAnchor.setVisibility(View.GONE);
        else mLlAnchor.setVisibility(View.GONE);

    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.mIvCancle) {//退出
            if (icancel != null) icancel.onCancelDialog();
            dismiss();
        } else if (i == R.id.mTvBlack) {
            if (TextUtils.equals("拉黑", mTvBlack.getText().toString().trim())) {
                CommDialog.newInstance(getActivity())
                        .setTitle("提示")
                        .setContent("拉黑将使双方无法关注且无法私信")
                        .setRightText("拉黑")
                        .setClickListen(new CommDialog.TwoSelDialog() {
                            @Override
                            public void leftClick() {

                            }

                            @Override
                            public void rightClick() {
                                mHnUserDetailBiz.setBackAdd(result.getUser_id());
                            }
                        })
                        .show();
            } else {
                mHnUserDetailBiz.setBackDel(result.getUser_id());
            }
        } else if (i == R.id.tv_care) {//关注
            tvCare.setEnabled(false);
            mHnUserDetailBiz.requestToFollow(isCared, uid, mAnchorId);
        } else if (i == R.id.tv_shop) {//商场
            if (result == null) return;
            if (TextUtils.equals("1", result.getStore_status())) {
                ShopActFacade.openShopDetails(result.getStore_id());
            } else {
                HnToastUtils.showCenterToast("店铺已被冻结");
            }
        } else if (i == R.id.tv_chat) {//私信
            if (result == null) return;
            if (from == 1) {//进入私聊
                Bundle bundle = new Bundle();
                bundle.putString(HnLiveConstants.Intent.DATA, result.getUser_id());
                bundle.putString(HnLiveConstants.Intent.Name, result.getUser_nickname());
                bundle.putString(HnLiveConstants.Intent.ChatRoomId, result.getChat_room_id());
                ARouter.getInstance().build("/app/HnPrivateChatActivity").with(bundle).navigation();
            } else if (from == 2) {
                if (result == null || mActivity == null) return;
                if (!TextUtils.isEmpty(result.getUser_id())) {
                    HnPrivateLetterDetailDialog mHnPrivateLetterDetailDialog = HnPrivateLetterDetailDialog.getInstance(result.getUser_id(), result.getUser_nickname(), "0", result.getChat_room_id());
                    mHnPrivateLetterDetailDialog.show(getActivity().getSupportFragmentManager(), "HnPrivateLetterDetailDialog");
                    EventBus.getDefault().post(new HnLiveEvent(0, HnLiveConstants.EventBus.Chatting_Uid, result.getUser_id()));
                }
                dismiss();
            } else {
                dismiss();
            }
        } else if (i == R.id.tv_uid) {//复制u号
            if (result == null) return;
            ClipBoardUtil.to(result.getUser_id());
            HnToastUtils.showToastShort(getResources().getString(R.string.live_id_clip));
        } else if (i == R.id.mTvReport) {//举报
            HnReportDialog.newInstance(mAnchorId).show(mActivity.getFragmentManager(), "report");
            dismiss();
        } else if (i == R.id.mTvAdmin) {//管理
            if (result == null) return;
            HnAdminDialog.newInstance(result.getUser_nickname(), uid, result.getIs_anchor_admin(), mAnchorId, mAnchorId == myUid ? true : false).show(mActivity.getFragmentManager(), "report");
            dismiss();
        }
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        isonDismiss = true;
        mActivity = null;
    }

    public void setOnCancel(ICancelDialog icancel) {
        this.icancel = icancel;
    }

    public interface ICancelDialog {
        void onCancelDialog();
    }

}
