package com.hotniao.live.activity;

import android.app.AppOpsManager;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.core.app.AppOpsManagerCompat;

import com.hn.library.base.BaseActivity;
import com.hn.library.base.BaseRequestStateListener;
import com.hn.library.global.HnUrl;
import com.hn.library.global.NetConstant;
import com.hn.library.http.HnHttpUtils;
import com.hn.library.http.HnResponseHandler;
import com.hn.library.manager.HnAppManager;
import com.hn.library.utils.HnDimenUtil;
import com.hn.library.utils.HnPrefUtils;
import com.hn.library.utils.HnServiceErrorUtil;
import com.hn.library.utils.HnToastUtils;
import com.hn.library.utils.HnUtils;
import com.hn.library.utils.PermissionHelper;
import com.hn.library.view.CommDialog;
import com.hn.library.view.FrescoImageView;
import com.hotniao.live.HnApplication;
import com.hotniao.live.HnMainActivity;
import com.hotniao.live.R;
import com.hotniao.live.biz.live.HnBeforeLiveSettingBiz;
import com.hotniao.live.model.HnCanLiveModel;
import com.hotniao.live.model.HnLocationEntity;
import com.hotniao.live.model.HnStopLiveModel;
import com.hotniao.live.utils.HnLocationBiz;
import com.hotniao.live.utils.HnUiUtils;
import com.hotniao.livelibrary.model.HnStartLiveInfoModel;
import com.hotniao.livelibrary.ui.anchor.activity.HnAnchorActivity;
import com.hotniao.livelibrary.widget.dialog.HnShareLiveDialog;
import com.reslibrarytwo.HnSkinTextView;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Copyright (C) 2017,深圳市红鸟网络科技股份有限公司 All rights reserved.
 * 项目名称：红鸟电商
 * 类描述：开播设置
 * 创建人：阳石柏
 * 创建时间：2017/3/6 16:16
 * 修改人：阳石柏
 * 修改时间：2017/3/6 16:16
 * 修改备注：
 * Version:  1.0.0
 */
public class HnBeforeLiveSettingActivity extends BaseActivity implements HnLocationBiz.OnLocationListener, BaseRequestStateListener {

    public static final int Choose_Cate_Code = 1;//选择分类
    public static final int Open_Location_Code = 2;//开启定位权限
    public static final int Open_LocationSer_Code = 3;//开启定位服务
    private static final int Open_Location = 0;//开启定位权限
    private static final int Open_LocationSer = 1;//开启定位服务
    private static UMShareAPI mShareAPI = null;
    private static ShareAction mShareAction;
    @BindView(R.id.tv_city)
    TextView tvCity;
    @BindView(R.id.mTvCoin1)
    TextView mTvCoin1;
    @BindView(R.id.mTvCoin2)
    TextView mTvCoin2;
    @BindView(R.id.iv_close)
    ImageView ivClose;
    @BindView(R.id.rl_top_con)
    RelativeLayout rlTopCon;
    @BindView(R.id.tv_title)
    EditText tvTitle;
    @BindView(R.id.fiv_add_cover)
    FrescoImageView fivAddCover;
    @BindView(R.id.ll_midd)
    LinearLayout llMidd;
    @BindView(R.id.ll_type_choose_bg)
    LinearLayout llTypeChooseBg;
    @BindView(R.id.tv_start)
    TextView tvStart;
    @BindView(R.id.iv_pic1)
    ImageView ivPic1;
    @BindView(R.id.iv_pic2)
    ImageView ivPic2;
    @BindView(R.id.iv_pic3)
    ImageView ivPic3;
    @BindView(R.id.et_moeny_1)
    EditText etMoeny1;
    @BindView(R.id.et_moeny_2)
    EditText etMoeny2;
    @BindView(R.id.mTvPayType)
    HnSkinTextView mTvPayType;
    @BindView(R.id.mTvLiveType)
    TextView mTvLiveType;
    @BindView(R.id.mRbQq)
    RadioButton mRbQq;
    @BindView(R.id.mRbWx)
    RadioButton mRbWx;
    @BindView(R.id.mRbSina)
    RadioButton mRbSina;
    @BindView(R.id.mRbFc)
    RadioButton mRbFc;
    private String TAG = "HnBeforeLiveSettingActivity";
    private String city = "", province = "";
    /**
     * 业务逻辑类 用于处理直播之前业务逻辑
     */
    private HnBeforeLiveSettingBiz mHnBeforeLiveSettingBiz;
    /**
     * 专门用于定位的工具
     */
    private HnLocationBiz mHnLocationBiz;
    /**
     * *直播收费类型，0：免费，1：VIP，2：门票，3：计时
     */
    private int choose_type = 0;
    /**
     * 图片地址
     */
    private String imgUrl;
    /**
     * 城市地址
     */
    private String cityAddress, lat, lng;
    /**
     * 是否用户点击切换城市  true：需要弹出提示框
     */
    private boolean isUserClick = false;
    /**
     * 直播类型 上一次直播id   上一次直播名称   上一次游戏直播code(如果上一次是游戏直播)
     */
    private String mLiveTypeId = "-1", mLiveType, mLiveGameCode;
    private boolean isGameLive;
    //分享
    private SHARE_MEDIA platform = null;
    /**
     * 定位是否开启  0开启
     */
    private int mLoctType = 0;

    /**
     * 开播前配置
     */
    private HnCanLiveModel.DBean mDbean;
    /**
     * 开播信息
     */
    private HnStartLiveInfoModel.DBean mLiveBean;

    /**
     * 是否进行分享
     */
    private boolean isShare = false;

    /**
     * 选择分享
     */
    private boolean shareQQ = false, shareSina = false, shareWx = false, shareFc = false;

//    private void requestCanLive() {
//        HnHttpUtils.getRequest(HnUrl.LIVE_GET_LIVE_INFO, null, HnUrl.LIVE_GET_LIVE_INFO, new HnResponseHandler<HnCanLiveModel>(this, HnCanLiveModel.class) {
//
//            @Override
//            public void hnSuccess(String response) {
//                if (isFinishing()) return;
//                if (model.getC() == 0 || model.getD() == null) {
//                    String title = tvTitle.getText().toString();
//                    String money1 = etMoeny1.getText().toString();
//                    String money2 = etMoeny2.getText().toString();
//                    mHnBeforeLiveSettingBiz.requestToStartLive(imgUrl, tvTitle.getText().toString().trim(), cityAddress, lat, lng, city, province);
//                } else {
//                }
//            }
//
//            @Override
//            public void hnErr(int errCode, String msg) {
//                //实名认证未通过
//                if (HnServiceErrorUtil.USER_CERTIFICATION_FAIL == errCode ||
//                        HnServiceErrorUtil.USER_CERTIFICATION_CHECK == errCode ||
//                        HnServiceErrorUtil.USER_NOT_CERTIFICATION == errCode) {
//                } else {
//                    CommDialog.newInstance(HnBeforeLiveSettingActivity.this)
//                            .setClickListen(new CommDialog.OneSelDialog() {
//                                @Override
//                                public void sureClick() {
//
//                                }
//                            })
//                            .setTitle("提示")
//                            .setContent(msg)
//                            .setRightText("我知道了")
//                            .show();
//                }
//
//            }
//        });
//    }
    private UMShareListener umShareListener = new UMShareListener() {
        @Override
        public void onResult(SHARE_MEDIA share_media) {
            HnShareLiveDialog.shareSuccess(HnApplication.getmUserBean().getUser_id());
        }

        @Override
        public void onError(SHARE_MEDIA share_media, Throwable throwable) {
        }

        @Override
        public void onCancel(SHARE_MEDIA share_media) {
        }
    };

    @OnClick({R.id.fiv_add_cover, R.id.iv_pic1, R.id.iv_pic2, R.id.iv_pic3, R.id.tv_start, R.id.iv_close, R.id.tv_city,
            R.id.mTvPayType, R.id.mTvLiveType, R.id.mLlAgree, R.id.mRbWx, R.id.mRbFc, R.id.mRbQq, R.id.mRbSina})
    public void onClick(View view) {
        HnUtils.hideSoftInputFrom(HnBeforeLiveSettingActivity.this, etMoeny1, etMoeny2, tvTitle);
        switch (view.getId()) {
            case R.id.fiv_add_cover://封面
                mHnBeforeLiveSettingBiz.uploadPicFile();
                break;
            case R.id.iv_pic1://计时收费
                choose_type = 3;
                ivPic1.setBackgroundResource(R.drawable.select_confirm);
                ivPic2.setBackgroundResource(R.drawable.select_def);
                ivPic3.setBackgroundResource(R.drawable.select_def);
                break;
            case R.id.iv_pic2://门票收费
                choose_type = 2;
                ivPic1.setBackgroundResource(R.drawable.select_def);
                ivPic2.setBackgroundResource(R.drawable.select_confirm);
                ivPic3.setBackgroundResource(R.drawable.select_def);
                break;
            case R.id.iv_pic3://vip
                choose_type = 1;
                ivPic1.setBackgroundResource(R.drawable.select_def);
                ivPic2.setBackgroundResource(R.drawable.select_def);
                ivPic3.setBackgroundResource(R.drawable.select_confirm);
                break;
            case R.id.tv_start://提交
//                requestCanLive();
                mHnBeforeLiveSettingBiz.requestToStartLive(imgUrl, tvTitle.getText().toString().trim(), cityAddress, lat, lng, city, province);
                break;
            case R.id.iv_close://退出
                HnAppManager.getInstance().finishActivity();
                break;
            case R.id.tv_city://城市重新定位
                if (!isLocationEnabled()) {
                    CommDialog.newInstance(HnBeforeLiveSettingActivity.this).setClickListen(new CommDialog.TwoSelDialog() {
                        @Override
                        public void leftClick() {
                        }

                        @Override
                        public void rightClick() {
                            if (Open_Location == mLoctType) {
                                Intent intent = new Intent();
                                intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                Uri uri = Uri.fromParts("package", getPackageName(), null);
                                intent.setData(uri);
                                startActivityForResult(intent, Open_LocationSer_Code); // 设置完成后返回到原来的界面
                            } else {
                                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                                startActivityForResult(intent, Open_Location_Code); // 设置完成后返回到原来的界面
                            }
                        }
                    }).setTitle(getString(R.string.need_find_you_loction))
                            .setContent(Open_Location == mLoctType ? getString(R.string.allow_firebird_to_visit_location) : getString(R.string.open_the_location_service_to_allow_the_fire_bird_to_visit_the_location))
                            .setRightText(getString(R.string.set)).show();
                } else {
                    isUserClick = true;
                    mHnLocationBiz.startLocation(HnBeforeLiveSettingActivity.this);
                }

                break;
            case R.id.mTvPayType://选择付费免费

                showPayTypeDialog(view);
                break;
            case R.id.mTvLiveType://选择直播类型
//                HnChooseLiveTypeActivity.luncher(HnBeforeLiveSettingActivity.this, mLiveTypeId, mLiveType, isGameLive);
                break;
            case R.id.mLlAgree:
                HnWebActivity.luncher(HnBeforeLiveSettingActivity.this, getString(R.string.user_start_agree), HnUrl.LIVE_AGREEMENT, "live");
                break;
            case R.id.mRbWx:
                platform = SHARE_MEDIA.WEIXIN;
                if (shareWx) {
                    shareWx = false;
                    mRbWx.setChecked(shareWx);
                    HnPrefUtils.setString(NetConstant.LIVE_SHARE_CHOOSER, "");
                } else {
                    shareWx = true;
                    mRbWx.setChecked(shareWx);
                    HnPrefUtils.setString(NetConstant.LIVE_SHARE_CHOOSER, platform + "");
                }
                shareQQ = false;
                shareSina = false;
                shareFc = false;
                mRbQq.setChecked(false);
                mRbFc.setChecked(false);
                mRbSina.setChecked(false);
                break;
            case R.id.mRbQq:
                platform = SHARE_MEDIA.QQ;
                if (shareQQ) {
                    shareQQ = false;
                    mRbQq.setChecked(shareQQ);
                    HnPrefUtils.setString(NetConstant.LIVE_SHARE_CHOOSER, "");
                } else {
                    shareQQ = true;
                    mRbQq.setChecked(shareQQ);
                    HnPrefUtils.setString(NetConstant.LIVE_SHARE_CHOOSER, platform + "");
                }
                shareWx = false;
                shareSina = false;
                shareFc = false;
                mRbWx.setChecked(false);
                mRbFc.setChecked(false);
                mRbSina.setChecked(false);
                break;
            case R.id.mRbSina:

                platform = SHARE_MEDIA.SINA;
                if (shareSina) {
                    shareSina = false;
                    mRbSina.setChecked(shareSina);
                    HnPrefUtils.setString(NetConstant.LIVE_SHARE_CHOOSER, "");
                } else {
                    shareSina = true;
                    mRbSina.setChecked(shareSina);
                    HnPrefUtils.setString(NetConstant.LIVE_SHARE_CHOOSER, platform + "");
                }
                shareQQ = false;
                shareWx = false;
                shareFc = false;
                mRbQq.setChecked(false);
                mRbFc.setChecked(false);
                mRbWx.setChecked(false);
                break;
            case R.id.mRbFc:

                platform = SHARE_MEDIA.WEIXIN_CIRCLE;
                if (shareFc) {
                    shareFc = false;
                    mRbFc.setChecked(shareFc);
                    HnPrefUtils.setString(NetConstant.LIVE_SHARE_CHOOSER, "");
                } else {
                    shareFc = true;
                    mRbFc.setChecked(shareFc);
                    HnPrefUtils.setString(NetConstant.LIVE_SHARE_CHOOSER, platform + "");
                }
                shareQQ = false;
                shareWx = false;
                shareSina = false;
                mRbQq.setChecked(false);
                mRbSina.setChecked(false);
                mRbWx.setChecked(false);
                break;

        }
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_before_live;
    }

    @Override
    public void onCreateNew(Bundle savedInstanceState) {
        mShareAPI = UMShareAPI.get(this);
        mShareAction = new ShareAction(this);

        mDbean = (HnCanLiveModel.DBean) getIntent().getSerializableExtra("bean");
        String title = getIntent().getStringExtra("title");


//        mTvCoin1.setText(HnApplication.getmConfig().getCoin());
//        mTvCoin2.setText(HnApplication.getmConfig().getCoin());

        tvTitle.setText(HnPrefUtils.getString("title", ""));
        if (!TextUtils.isEmpty(title)) {
            tvTitle.setText(title);
        }
        //初始化视图
        initView();
        initLocation();
        /**
         * 判断是否是游戏还是娱乐分类
         */
//        if (mDbean != null) {
//            if ("0".equals(mDbean.getAnchor().getAnchor_category_id())) {
//                mLiveType = mDbean.getAnchor().getGame_category_name();
//                mLiveTypeId = mDbean.getAnchor().getGame_category_id();
//                mLiveGameCode = mDbean.getAnchor().getGame_category_code();
//                isGameLive = true;
//            } else {
//                mLiveType = mDbean.getAnchor().getAnchor_category_name();
//                mLiveTypeId = mDbean.getAnchor().getAnchor_category_id();
//                isGameLive = false;
//            }
//            mTvLiveType.setText(mLiveType);
//        }

    }

    /**
     * 初始化视图
     */
    private void initView() {
        setShowTitleBar(false);
        //默认免费直播
        choose_type = 0;
        llTypeChooseBg.setVisibility(View.INVISIBLE);
        //初始化业务逻辑类
        mHnBeforeLiveSettingBiz = new HnBeforeLiveSettingBiz(this);
        mHnBeforeLiveSettingBiz.setBaseRequestStateListener(this);


        /**
         * 设置分享初始值
         */
        String mShareType = HnPrefUtils.getString(NetConstant.LIVE_SHARE_CHOOSER, "");
        if ((SHARE_MEDIA.QQ + "").equals(mShareType)) {
            platform = SHARE_MEDIA.QQ;
            mRbQq.setChecked(true);
            shareQQ = true;
        } else if ((SHARE_MEDIA.WEIXIN + "").equals(mShareType)) {
            platform = SHARE_MEDIA.WEIXIN;
            mRbWx.setChecked(true);
            shareWx = true;
        } else if ((SHARE_MEDIA.SINA + "").equals(mShareType)) {
            platform = SHARE_MEDIA.SINA;
            mRbSina.setChecked(true);
            shareSina = true;
        } else if ((SHARE_MEDIA.WEIXIN_CIRCLE + "").equals(mShareType)) {
            platform = SHARE_MEDIA.WEIXIN_CIRCLE;
            mRbFc.setChecked(true);
            shareFc = true;
        } else if (HnPrefUtils.getBoolean(NetConstant.LIVE_SHARE_CHOOSER_FIRST, true)) {
            platform = SHARE_MEDIA.QQ;
            mRbQq.setChecked(true);
            shareQQ = true;
            HnPrefUtils.setString(NetConstant.LIVE_SHARE_CHOOSER, platform + "");
        }

    }

    /**
     * 初始化定位
     *
     * @author hongming.wang
     * @since 2.8.0
     */
    private void initLocation() {
        mHnLocationBiz = HnLocationBiz.getInsrance();
        mHnLocationBiz.setOnLocationListener(this);
        if (HnMainActivity.mLocEntity == null) {
            mHnLocationBiz.startLocation(this);
        } else {
            updateLocation(HnMainActivity.mLocEntity.getmProvince(), HnMainActivity.mLocEntity.getmCity());
            lat = HnMainActivity.mLocEntity.getmLat();
            lng = HnMainActivity.mLocEntity.getmLng();
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (isShare) startLive();
    }

    @Override
    public void onLocationSuccess(final String province, final String city, String address, String latitudeResult, String longitudeResult) {
        if (isFinishing()) return;
        HnMainActivity.mLocEntity = new HnLocationEntity(latitudeResult, longitudeResult, city, province);
        this.city = city;
        this.province = province;
        lat = latitudeResult;
        lng = longitudeResult;
        if (isUserClick) {//用户点击城市定位
            isUserClick = false;
            CommDialog.newInstance(HnBeforeLiveSettingActivity.this).setClickListen(new CommDialog.TwoSelDialog() {
                @Override
                public void leftClick() {
                    tvCity.setText("未知");
                }

                @Override
                public void rightClick() {
                    updateLocation(province, city);
                }
            }).setTitle(getString(R.string.location)).setContent(getString(R.string.location_you_here) + province + " " + city + getString(R.string.is_sure_user)).setRightText(getString(R.string.useed)).show();
        } else {//自动定位
            updateLocation(province, city);
        }
    }

    @Override
    public void onLocationFail(String errorRease, int code) {
        if (isFinishing()) return;
        HnToastUtils.showToastShort(HnUiUtils.getString(R.string.loca_fail));
        isUserClick = false;
    }

    @Override
    public void requesting() {
        showDoing(HnUiUtils.getString(R.string.loading), null);
    }

    @Override
    public void requestSuccess(String type, String response, Object obj) {
        done();
        if (isFinishing()) return;
        if ("upload_pic_file".equals(type)) {//上传图片
            imgUrl = response;
            HnPrefUtils.setString("def_img", imgUrl);
            if (!TextUtils.isEmpty(imgUrl) && fivAddCover != null) {
                fivAddCover.setImageURI(Uri.parse(HnUrl.setImageUrl(imgUrl)));
            }
        } else if ("start_live".equals(type)) {//开始直播
            HnPrefUtils.setBoolean(NetConstant.LIVE_SHARE_CHOOSER_FIRST, false);
            HnStartLiveInfoModel model = (HnStartLiveInfoModel) obj;
            if (model != null && model.getD() != null) {
                mLiveBean = model.getD();
                /**
                 * 如果选择了分享 则先进入分享  返回之后直播  否则 直接进入直播
                 */
                if (!shareSina && !shareWx && !shareQQ) {
                    startLive();
                } else {

                    CommDialog.newInstance(HnBeforeLiveSettingActivity.this).setClickListen(new CommDialog.TwoSelDialog() {
                        @Override
                        public void leftClick() {
                            startLive();
                        }

                        @Override
                        public void rightClick() {
                            if(true) {
                                HnToastUtils.showToastShort("演示平台暂未开通此功能");
                                startLive();
                                return;
                            }
                            if (TextUtils.isEmpty(imgUrl))
                                imgUrl = HnUrl.setImageUrl(HnApplication.getmUserBean().getUser_avatar());
                            else imgUrl = HnUrl.setImageUrl(imgUrl);
                            if (TextUtils.isEmpty(imgUrl)) imgUrl = HnUrl.DEFRLT_IMG;
                            isShare = true;
                            mShareAction.setPlatform(platform)
                                    .withTargetUrl(mDbean.getShare_url())
                                    .withMedia(new UMImage(HnBeforeLiveSettingActivity.this, imgUrl))
                                    .withText(String.format(HnUiUtils.getString(R.string.live_share_content), HnApplication.getmUserBean().getUser_nickname(), HnApplication.getmUserBean().getUser_id()))
                                    .withTitle(getString(R.string.live_share_title)).setCallback(umShareListener).share();
                        }
                    }).setTitle(getString(R.string.live_share)).setContent(String.format(getString(R.string.live_share_content_dialog),
                            platform == SHARE_MEDIA.WEIXIN ? "微信" : platform == SHARE_MEDIA.QQ ? "QQ" : "新浪微博")).setCanceledOnOutside(false).show();
                }
            }
        }
    }

    /**
     * 进入直播间
     */
    private void startLive() {
        if (isFinishing()) return;
        if (mLiveBean == null) return;
        isShare = false;
        /**设置 分享地址   是否游戏  游戏名称  游戏code*/
//        mLiveBean.setShare_url(mDbean.getShare_url());
//        mLiveBean.setGame(isGameLive);
//        mLiveBean.setGameName(mLiveType);
//        mLiveBean.setGameCode(mLiveGameCode);

        Bundle bundle = new Bundle();
        bundle.putSerializable("data", mLiveBean);
        bundle.putInt("live_type", choose_type);
        bundle.putString("share", mDbean.getShare_url());
        startActivity(new Intent(this, HnAnchorActivity.class).putExtras(bundle));
        finish();
    }

    @Override
    public void requestFail(String type, int code, String msg) {
        done();
        if (isFinishing()) return;
        if ("upload_pic_file".equals(type)) {//上传图片
            HnToastUtils.showToastShort(msg);
        } else if ("start_live".equals(type)) {//开始直播
            HnPrefUtils.setBoolean(NetConstant.LIVE_SHARE_CHOOSER_FIRST, false);
            if (code == HnServiceErrorUtil.ANCHOR_FORBID) {//该用户的账号被禁播
                CommDialog.newInstance(HnBeforeLiveSettingActivity.this).setClickListen(new CommDialog.OneSelDialog() {
                    @Override
                    public void sureClick() {
                    }
                }).setTitle(getString(R.string.live_start)).setContent(getString(R.string.ban_hint)).show();
            } else if (HnServiceErrorUtil.LIVE_START_ERROR == code) {
                HnToastUtils.showToastShort(msg);
                HnHttpUtils.getRequest(HnUrl.Stop_Live, null, TAG, new HnResponseHandler<HnStopLiveModel>(this, HnStopLiveModel.class) {
                    @Override
                    public void hnSuccess(String response) {
                    }

                    @Override
                    public void hnErr(int errCode, String msg) {
                    }
                });
            } else {
                HnToastUtils.showToastShort(msg);
            }
        }

    }

    @Override
    public void getInitData() {

    }

    /**
     * 更新定位地点
     *
     * @param province
     * @param city
     */
    private void updateLocation(String province, String city) {
        if (tvCity == null) return;
        this.cityAddress = province + city;
        tvCity.setText(city);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    /**
     * 显示付费方式弹窗
     */

    private void showPayTypeDialog(View v) {
        View view = LayoutInflater.from(this).inflate(R.layout.popupwindow_live_pay_type, null);
        final PopupWindow popupWindow = new PopupWindow(view, HnDimenUtil.dp2px(this, 120), HnDimenUtil.dp2px(this, 107));
        final TextView mTvFree = (TextView) view.findViewById(R.id.mTvFree);
        final TextView mTvPay = (TextView) view.findViewById(R.id.mTvPay);
        if (choose_type != 0) {
            mTvFree.setSelected(false);
            mTvPay.setSelected(true);
        } else {
            mTvFree.setSelected(true);
            mTvPay.setSelected(false);
        }

        mTvFree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//免费
                choose_type = 0;
                llTypeChooseBg.setVisibility(View.INVISIBLE);

                mTvPayType.setText(R.string.need_no_charge);
                mTvFree.setSelected(true);
                mTvPay.setSelected(false);
                popupWindow.dismiss();
            }
        });
        mTvPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//收费
                choose_type = 3;
                llTypeChooseBg.setVisibility(View.VISIBLE);
                etMoeny1.setText("");
                etMoeny2.setText("");

                mTvPayType.setText(R.string.need_charge);
                mTvFree.setSelected(false);
                mTvPay.setSelected(true);
                popupWindow.dismiss();
            }
        });

        mTvPayType.setLeftDrawable(R.drawable.upper);
        // 允许点击外部消失
        popupWindow.setBackgroundDrawable(new BitmapDrawable());//注意这里如果不设置，下面的setOutsideTouchable(true);允许点击外部消失会失效
        popupWindow.setOutsideTouchable(true);   //设置外部点击关闭ppw窗口
        popupWindow.setFocusable(true);
        backgroundAlpha(0.5f);

        //添加pop窗口关闭事件
        popupWindow.setOnDismissListener(new poponDismissListener());
        popupWindow.showAsDropDown(v, 0, 20);    // 以触发弹出窗的view为基准，出现在view的正下方，弹出的pop_view左上角正对view的左下角  偏移量默认为0,0
    }

    /**
     * 设置添加屏幕的背景透明度
     *
     * @param bgAlpha
     */
    public void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = bgAlpha; //0.0-1.0
        getWindow().setAttributes(lp);
    }

    /**
     * @param requestCode
     * @param resultCode
     * @param data        requestCode
     *                    = 1  选择分类返回
     *                    =2 ，3  进入设置开启定位返回
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Choose_Cate_Code) {
            if (data != null) {
                mLiveTypeId = data.getStringExtra("id");
                mLiveType = data.getStringExtra("type");
                isGameLive = data.getBooleanExtra("isGame", false);
                if (isGameLive) {
                    mLiveGameCode = data.getStringExtra("code");
                }
                mTvLiveType.setText(mLiveType);
            }
        } else if (Open_LocationSer_Code == requestCode || Open_Location_Code == requestCode) {
            initLocation();
        }
        mShareAPI.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * 判断定位
     *
     * @return
     */
    public boolean isLocationEnabled() {

        if (!PermissionHelper.isLocServiceEnable(this)) {//检测是否开启定位服务
            mLoctType = Open_LocationSer;
            return false;
        } else {//检测用户是否将当前应用的定位权限拒绝
            int checkResult = PermissionHelper.checkOp(this, 2, AppOpsManager.OPSTR_FINE_LOCATION);//其中2代表AppOpsManager.OP_GPS，如果要判断悬浮框权限，第二个参数需换成24即AppOpsManager。OP_SYSTEM_ALERT_WINDOW及，第三个参数需要换成AppOpsManager.OPSTR_SYSTEM_ALERT_WINDOW
            int checkResult2 = PermissionHelper.checkOp(this, 1, AppOpsManager.OPSTR_FINE_LOCATION);
            if (AppOpsManagerCompat.MODE_IGNORED == checkResult || AppOpsManagerCompat.MODE_IGNORED == checkResult2) {
                mLoctType = Open_Location;
                return false;
            }
        }
        return true;
    }

    /**
     * popwindow消失监听
     */
    class poponDismissListener implements PopupWindow.OnDismissListener {
        @Override
        public void onDismiss() {
            backgroundAlpha(1f);
            mTvPayType.setLeftDrawable(R.drawable.lower);
        }

    }


}
