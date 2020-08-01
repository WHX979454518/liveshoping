package com.hotniao.live.fragment;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.core.widget.NestedScrollView;

import com.hn.library.HnBaseApplication;
import com.hn.library.base.BaseFragment;
import com.hn.library.base.BaseRequestStateListener;
import com.hn.library.base.EventBusBean;
import com.hn.library.global.HnConstants;
import com.hn.library.global.HnUrl;
import com.hn.library.global.NetConstant;
import com.hn.library.http.HnHttpUtils;
import com.hn.library.http.HnResponseHandler;
import com.hn.library.model.HnLoginBean;
import com.hn.library.model.HnLoginModel;
import com.hn.library.refresh.PtrClassicFrameLayout;
import com.hn.library.refresh.PtrDefaultHandler2;
import com.hn.library.refresh.PtrFrameLayout;
import com.hn.library.update.HnAppUpdateService;
import com.hn.library.utils.HnBadgeView;
import com.hn.library.utils.HnNetUtil;
import com.hn.library.utils.HnPrefUtils;
import com.hn.library.utils.HnServiceErrorUtil;
import com.hn.library.utils.HnToastUtils;
import com.hn.library.utils.HnUtils;
import com.hn.library.utils.PermissionHelper;
import com.hn.library.view.CommDialog;
import com.hn.library.view.FrescoImageView;
import com.hotniao.live.HnApplication;
import com.hotniao.live.R;
import com.hotniao.live.activity.CollectionAct;
import com.hotniao.live.activity.HnAboutActivity;
import com.hotniao.live.activity.HnAnchorAboutAct;
import com.hotniao.live.activity.HnAuthStateActivity;
import com.hotniao.live.activity.HnAuthenticationActivity;
import com.hotniao.live.activity.HnBeforeLiveSettingActivity;
import com.hotniao.live.activity.HnFeedBackActivity;
import com.hotniao.live.activity.HnMyFansActivity;
import com.hotniao.live.activity.HnMyFollowActivity;
import com.hotniao.live.activity.HnMyMessageActivity;
import com.hotniao.live.activity.HnSettingActivity;
import com.hotniao.live.activity.HnWebActivity;
import com.hotniao.live.activity.TenantsActivity;
import com.hotniao.live.activity.account.HnRechargeAct;
import com.hotniao.live.biz.user.userinfo.HnMineFragmentBiz;
import com.hotniao.live.model.HnAuthDetailModel;
import com.hotniao.live.model.HnCanLiveModel;
import com.hotniao.live.model.OrderInforModel;
import com.hotniao.live.model.PhoneModel;
import com.hotniao.live.model.SignModel;
import com.hotniao.live.model.TheStoreModel;
import com.hotniao.live.utils.BaseConfirmDialog;
import com.hotniao.live.utils.HnMyUitls;
import com.hotniao.live.utils.HnUiUtils;
import com.live.shoplib.ShopActFacade;
import com.live.shoplib.bean.SellerCenterModel;
import com.live.shoplib.ui.dialog.ShareDialog;
import com.loopj.android.http.RequestParams;
import com.reslibrarytwo.LevelView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.Serializable;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 作者：Mr.Xu
 * 时间：2017/12/18
 */
@SuppressLint("LongLogTag")
public class HnUserFragment extends BaseFragment implements BaseRequestStateListener {

    @BindView(R.id.mIvIco)
    FrescoImageView mIvIco;
    @BindView(R.id.mTvFans)
    TextView mTvFans;
    @BindView(R.id.mTvCare)
    TextView mTvCare;
    @BindView(R.id.mLLBuyer)
    LinearLayout mLLBuyer;
    @BindView(R.id.mTvShopWaitPay)
    TextView mTvShopWaitPay;
    @BindView(R.id.mTvShopWaitDeliver)
    TextView mTvShopWaitDeliver;
    @BindView(R.id.mTvShopWaitGet)
    TextView mTvShopWaitGet;
    @BindView(R.id.mTvShopEvaluate)
    TextView mTvShopEvaluate;
    @BindView(R.id.mTvShopGoods)
    TextView mTvShopGoods;
    @BindView(R.id.mLLAuthentication)
    LinearLayout mLLAuthentication;
    @BindView(R.id.mLLSeller)
    LinearLayout mLLSeller;
    @BindView(R.id.mLLShopCar)
    RelativeLayout mLLShopCar;
    @BindView(R.id.mLLShopAddress)
    RelativeLayout mLLShopAddress;
    @BindView(R.id.mLLCollect)
    RelativeLayout mLLCollect;
    @BindView(R.id.mLLAccount)
    LinearLayout mLLAccount;
    @BindView(R.id.mLLEarnings)
    LinearLayout mLLEarnings;
    @BindView(R.id.mIvSetting)
    TextView mIvSetting;
    @BindView(R.id.mIvSetting2)
    ImageView mIvSetting2;
    @BindView(R.id.mIvMsg)
    ImageView mIvMsg;
    @BindView(R.id.mIvMsg2)
    ImageView mIvMsg2;
    @BindView(R.id.mScroll)
    NestedScrollView mScroll;
    @BindView(R.id.mTvName)
    TextView mTvName;
    @BindView(R.id.mIvSex)
    ImageView mIvSex;
    @BindView(R.id.mTvAuth)
    TextView mTvAuth;
    @BindView(R.id.mTvWaitPayNum)
    TextView mTvWaitPayNum;
    @BindView(R.id.mTvWaitDeliverNum)
    TextView mTvWaitDeliverNum;
    @BindView(R.id.mTvWaitGetNum)
    TextView mTvWaitGetNum;
    @BindView(R.id.mTvEvaluateNum)
    TextView mTvEvaluateNum;
    @BindView(R.id.mTvRefundNum)
    TextView mTvRefundNum;
    @BindView(R.id.mTvNewMsg)
    HnBadgeView mTvNewMsg;
    @BindView(R.id.mTvNewMsg2)
    HnBadgeView mTvNewMsg2;
    @BindView(R.id.mRefresh)
    PtrClassicFrameLayout mRefresh;
    @BindView(R.id.mLLTitle2)
    LinearLayout mLLTitle2;
    @BindView(R.id.mLLAnchorAbout)
    LinearLayout mLLAnchorAbout;
    @BindView(R.id.mIvSign)
    ImageView mIvSign;
    @BindView(R.id.mLevelView)
    LevelView mLevelView;
    @BindView(R.id.mTvTrackNum)
    TextView mTvTrackNum;

    @BindView(R.id.mhelp)
    RelativeLayout mhelp;
    @BindView(R.id.about_rl)
    RelativeLayout about_rl;
    @BindView(R.id.feedback_rl)
    RelativeLayout feedback_rl;
    @BindView(R.id.mRlUpData)
    RelativeLayout mRlUpData;
    @BindView(R.id.hotline_rl)
    RelativeLayout hotline_rl;
    @BindView(R.id.customer_service_rl)
    RelativeLayout customer_service_rl;
    private AlertDialog.Builder builder;

    @BindView(R.id.tv_tenants)
    RelativeLayout tv_tenants;

    @BindView(R.id.real_verify)
    TextView real_verify;

    @BindView(R.id.tv_begin_live)
    TextView tv_begin_live;

    @BindView(R.id.mLlFans)
    RelativeLayout mLlFans;
    @BindView(R.id.address_rl)
    RelativeLayout address_rl;
    @BindView(R.id.aboutrl_rl)
    RelativeLayout aboutrl_rl;
    @BindView(R.id.aboutnull_rl)
    RelativeLayout aboutnull_rl;








    private HnMineFragmentBiz mHnMineFragmentBiz;
    private HnLoginBean result;
    private String uid;
    private String store_id;
    private boolean can = false;
    private String state = "1";
    private long oneDay = 60 * 60 * 24 * 1000;
    private String mRealNameState;
    private ShareDialog dialog;

    @Override
    public int getContentViewId() {
        return R.layout.frag_user;
    }

    @Override
    public void initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        mHnMineFragmentBiz = new HnMineFragmentBiz(mActivity);
        mHnMineFragmentBiz.setBaseRequestStateListener(this);

    }

    @SuppressLint("NewApi")
    @Override
    protected void initData() {


        //判断是否已经商铺认证了，来确定能不能开直播
        getShopRealNameState();

        mHnMineFragmentBiz.requestToUserInfo();
        mHnMineFragmentBiz.requestToOrderInfo();
        mRefresh.setMode(PtrFrameLayout.Mode.REFRESH);
        mRefresh.setEnabledNextPtrAtOnce(true);
        mRefresh.setKeepHeaderWhenRefresh(true);
        mRefresh.disableWhenHorizontalMove(true);
        mRefresh.setPtrHandler(new PtrDefaultHandler2() {
            @Override
            public void onLoadMoreBegin(PtrFrameLayout ptrFrameLayout) {
                if (mRefresh != null) mRefresh.refreshComplete();
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout ptrFrameLayout) {
                if (mRefresh != null) mRefresh.refreshComplete();
                if (mHnMineFragmentBiz != null) {
                    mHnMineFragmentBiz.requestToUserInfo();
                    mHnMineFragmentBiz.requestToOrderInfo();
                    getRealNameState();
                }
            }
        });
        mScroll.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (scrollY > 150) {
//                    mLLTitle2.setVisibility(View.VISIBLE);
                } else {
                    mLLTitle2.setVisibility(View.GONE);
                }
            }
        });
//        long curDate = System.currentTimeMillis();
//        long hisDate = HnPrefUtils.getLong("date", 0);
//        long among = curDate - hisDate;
//        if (hisDate == 0) {
//            mIvSign.setVisibility(View.VISIBLE);
//        } else {
//            Log.e("##########################", among + " 剩余：" + (hisDate % (60 * 60 * 24 * 1000)));
//            if (among > 60 * 60 * 24 * 1000 || among >= hisDate % oneDay) {
//                //记录当前时间
//                mIvSign.setVisibility(View.VISIBLE);
//            } else {
//                mIvSign.setVisibility(View.GONE);
//            }
//        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mHnMineFragmentBiz != null) {
            state = HnPrefUtils.getString(NetConstant.User.STORE_STATE, "1");
            mHnMineFragmentBiz.requestToUserInfo();
            mHnMineFragmentBiz.requestToOrderInfo();
            getRealNameState();
            getSign();
        }
        //判断是否已经商铺认证了，来确定能不能开直播
        getShopRealNameState();
    }


    @OnClick({R.id.mLLLevel,R.id.mLLAnchorAbout, R.id.mIvSetting2, R.id.mLlFans, R.id.mLFouces,R.id.mLLTrack, R.id.mLLBuyer,
            R.id.mLLAuthentication, R.id.mLLSeller, R.id.mLLShopCar, R.id.mLLShopAddress,
            R.id.mLLCollect, R.id.mLLAccount, R.id.mLLEarnings, R.id.mIvSetting, R.id.mIvMsg, R.id.mIvMsg2,
            R.id.mRlPay, R.id.mRlDeliver, R.id.mRlGet, R.id.mRlEvaluate, R.id.mRlRefund, R.id.mLLSign,R.id.tv_tenants,R.id.real_verify
    ,R.id.mhelp,R.id.about_rl,R.id.feedback_rl,R.id.mRlUpData,R.id.hotline_rl,R.id.customer_service_rl,R.id.tv_begin_live,R.id.share_app,
            R.id.address_rl,R.id.aboutrl_rl})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.mLLLevel:
                HnWebActivity.luncher(mActivity,getString(R.string.user_level), HnUrl.USER_LEVEL_USER,HnWebActivity.Level);
                break;
            case R.id.mLLSign:
                long curDate = System.currentTimeMillis();
                HnPrefUtils.setLong("date", curDate);
                HnWebActivity.luncher(mActivity, getString(R.string.my_sign_in), HnUrl.USER_SIGNIN_DETAIL, HnWebActivity.Sign);
                break;
            case R.id.mLLAnchorAbout://主播相关
                mActivity.openActivity(HnAnchorAboutAct.class);
                break;
            case R.id.mLlFans://粉丝
                mActivity.openActivity(HnMyFansActivity.class);
                break;
            case R.id.mLFouces://关注
                mActivity.openActivity(HnMyFollowActivity.class);
                break;
//            case R.id.mLLSell://卖出
//                if (TextUtils.equals(state, "1")) {//店铺是否冻结 0 冻结 1正常
//                    if (!TextUtils.isEmpty(store_id) && !TextUtils.equals("0", store_id)) {
//                        ShopActFacade.openShopOrder(true, 0);
//                    } else {
//                        HnToastUtils.showToastShort("请先进行店铺认证");
//                    }
//                } else {
//                    HnToastUtils.showToastShort("店铺已冻结，暂时无法操作，请联系平台");
//                }
//                break;
//            case R.id.mLLBuy://买入
            case R.id.mLLBuyer://买家订单
                ShopActFacade.openShopOrder(false, 0);
                break;
            case R.id.mLLTrack://足迹
                ShopActFacade.openTrack();
                break;
            case R.id.mRlPay://待付款
                ShopActFacade.openShopOrder(false, 1);
                break;
            case R.id.mRlDeliver://待发货
                ShopActFacade.openShopOrder(false, 2);
                break;
            case R.id.mRlGet://待收货
                ShopActFacade.openShopOrder(false, 3);
                break;
            case R.id.mRlEvaluate://待评价
                ShopActFacade.openShopOrder(false, 4);
//                startActivity(new Intent(getActivity(), StoreAutAct.class));
                break;
            case R.id.mRlRefund://售后
                ShopActFacade.openRefundGoods();
                break;
            case R.id.mLLAuthentication://实名认证
                if (result == null) return;
                switch (mRealNameState) {
                    case "1":
                    case "3":
                        mActivity.openActivity(HnAuthStateActivity.class);
                        break;
                    case "2":
                        startActivity(new Intent(mActivity, HnAuthenticationActivity.class).putExtra("hadAuth", true));
                        break;
                    default:
                        startActivity(new Intent(mActivity, HnAuthenticationActivity.class).putExtra("hadAuth", false));
                        break;
                }
                break;

            case R.id.real_verify://实名认证
                startActivity(new Intent(mActivity, HnAuthenticationActivity.class).putExtra("hadAuth", false));
                break;
            case R.id.mLLSeller://卖家中心
                if (TextUtils.equals(state, "1")) {//店铺是否冻结 0 冻结 1正常
                    if (can) ShopActFacade.openSellerCenter(store_id);
                } else {
                    HnToastUtils.showToastShort("店铺已冻结，暂时无法操作，请联系平台");
                }
                break;
            case R.id.mLLShopCar://购物车
                ShopActFacade.openGoodsCar();
                break;
            case R.id.mLLShopAddress://收货地址
                ShopActFacade.openAddressReceiving();
                break;
            case R.id.address_rl://收货地址
                ShopActFacade.openAddressReceiving();
                break;
            case R.id.mLLCollect://收藏
                mActivity.openActivity(CollectionAct.class);
                break;
            case R.id.mLLAccount://帐户
                mActivity.openActivity(HnRechargeAct.class);
                break;
            case R.id.mLLEarnings://收益
                ShopActFacade.openEarning();
                break;
            case R.id.mIvSetting://设置
            case R.id.mIvSetting2://设置
                Bundle bundle = new Bundle();
                bundle.putString(HnConstants.Intent.DATA, uid);
                bundle.putSerializable("bean", result);
                mActivity.openActivity(HnSettingActivity.class, bundle);
                break;
            case R.id.mIvMsg://消息
            case R.id.mIvMsg2://消息
                mActivity.openActivity(HnMyMessageActivity.class);
                break;
            case R.id.tv_tenants://商家入驻
                mActivity.openActivity(TenantsActivity.class);
                break;
            case R.id.mhelp://帮助中心
                HnWebActivity.luncher(getActivity(), getString(R.string.help_and_feekback), HnUrl.USER_HELP_HOTQUESTION, HnWebActivity.Help);
                break;
            case R.id.about_rl://关于我们
                mActivity.openActivity(HnAboutActivity.class);
                break;
            case R.id.aboutrl_rl://关于我们
                mActivity.openActivity(HnAboutActivity.class);
                break;
            case R.id.feedback_rl://反馈
                mActivity.openActivity(HnFeedBackActivity.class);
                break;

            case R.id.tv_begin_live://开始直播
                clickTabLiveLayout("");
                break;

            case R.id.mRlUpData://版本信息
                if (Double.parseDouble(HnBaseApplication.getmConfig().getVersion().getCode()) > HnUtils.getVersionCode(getActivity())) {
                    updataVer();
                } else {
                    HnToastUtils.showToastShort(getString(R.string.is_new_v));
                }
                break;
            case R.id.hotline_rl://平台热线
                RequestParams params = new RequestParams();
                HnHttpUtils.postRequest(HnUrl.SERVICE_PHONE, params, "打电话", new HnResponseHandler<PhoneModel>(PhoneModel.class) {
                    @Override
                    public void hnSuccess(String response) {
                        if (mActivity == null) return;
                        if(null!=model.getD()){
                            final String phoneModel = model.getD().get(0).getService_phone();

                            BaseConfirmDialog.Companion.newInstance()
//                                    .title("拨打电话吗？")
                                    .content(phoneModel)
                                    .confirmText("确定")
                                    .cancleText("取消")
                                    .showDialog(getActivity(), new BaseConfirmDialog.onBtClick() {
                                        @Override
                                        public void onConfirm() {
                                            callPhone(phoneModel);
                                        }

                                        @Override
                                        public void onCancle() {

                                        }
                                    });
                        }
                    }

                    @Override
                    public void hnErr(int errCode, String msg) {
                        HnToastUtils.showToastShort(msg);
                    }
                });
                break;
            case R.id.customer_service_rl://客服电话
                RequestParams param = new RequestParams();
                HnHttpUtils.postRequest(HnUrl.SERVICE_PHONE, param, "打电话", new HnResponseHandler<PhoneModel>(PhoneModel.class) {
                    @Override
                    public void hnSuccess(String response) {
                        if (mActivity == null) return;
                        if(null!=model.getD()){
                            final String phoneModel = model.getD().get(0).getService_phone();
                            BaseConfirmDialog.Companion.newInstance()
                                    .title("拨打电话吗？")
                                    .isShowtitle(false)
                                    .content(phoneModel)
                                    .confirmText("确定")
                                    .cancleText("取消")
                                    .showDialog(getActivity(), new BaseConfirmDialog.onBtClick() {
                                        @Override
                                        public void onConfirm() {
                                            callPhone(phoneModel);
                                        }

                                        @Override
                                        public void onCancle() {

                                        }
                                    });
                        }
                    }

                    @Override
                    public void hnErr(int errCode, String msg) {
                        HnToastUtils.showToastShort(msg);
                    }
                });
                break;
            case R.id.share_app:
                dialog = new ShareDialog(ShareDialog.Type.Back, getChildFragmentManager())
                        .setGoodsShare("name",
                                "img",
                                "url")
                        .show();
                break;



        }
    }

    private void getRealNameState() {
        HnHttpUtils.postRequest(HnUrl.CERTIFICATION_CHECK, null, TAG, new HnResponseHandler<HnAuthDetailModel>(HnAuthDetailModel.class) {
            @Override
            public void hnSuccess(String response) {
                if (getActivity() == null) return;
                if (model.getD() == null) return;
                if (model.getC() == 0) {
                    HnAuthDetailModel.DBean d = model.getD();
                    if ("Y".equals(d.getIs_submit())) {
                        if ("C".equals(d.getUser_certification_status())) {//用户实名认证状态，C：核对中(Cache)，Y：通过，N：不通过
                            mRealNameState = "1";
                            mTvAuth.setText(R.string.applying);
                        } else if ("Y".equals(d.getUser_certification_status())) {
                            mRealNameState = "2";
                            mTvAuth.setText(R.string.certified);
                            mLLAnchorAbout.setVisibility(View.GONE);
                        } else if ("N".equals(d.getUser_certification_status())) {
                            mRealNameState = "3";
                            mTvAuth.setText(R.string.certified_fail);
                        }
                    } else {
                        mTvAuth.setText(R.string.no_apply);
                        mRealNameState = "0";
                    }
                    HnPrefUtils.setString(NetConstant.User.Auth, mRealNameState);
                } else {
                    HnToastUtils.showToastShort(model.getM());
                }
            }

            @Override
            public void hnErr(int errCode, String msg) {
                HnToastUtils.showToastShort(msg);
            }
        });
    }

    private void getSign() {
        HnHttpUtils.postRequest(HnUrl.IS_SIGN, null, TAG, new HnResponseHandler<SignModel>(SignModel.class) {
            @Override
            public void hnSuccess(String response) {
                try {
                    if (model.getD().getUser_signin().getIs_signin().equals("Y")) {
                        mIvSign.setVisibility(View.GONE);
                    } else {
                        mIvSign.setVisibility(View.VISIBLE);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void hnErr(int errCode, String msg) {
                HnToastUtils.showToastShort(msg);
            }
        });
    }

    @Override
    public void requesting() {

    }

    @Override
    public void requestSuccess(String type, String response, Object obj) {
        if (mLLAccount == null) return;
        if (TextUtils.equals(type, "user_info")) {
            HnLoginModel model = (HnLoginModel) obj;
            if (model != null && model.getD() != null && model.getD().getUser_id() != null) {
                updateUi(model.getD());
            }
        } else if (TextUtils.equals(type, "order_info")) {
            OrderInforModel model = (OrderInforModel) obj;

            if (getActivity().isFinishing() || model.getD() == null) return;
//            mTvBugNum.setText(model.getD().getProfile().getBuy() + "");
//            mTvSellNum.setText(model.getD().getProfile().getSale() + "");
            store_id = model.getD().getProfile().getStoreId();
            can = true;

            if (model.getD().getOrder() != null) {
                OrderInforModel.DEntity.OrderEntity order = model.getD().getOrder();
                setOrderNum(mTvWaitPayNum, order.getNon_payment());
                setOrderNum(mTvWaitDeliverNum, order.getDrop_shipping());
                setOrderNum(mTvWaitGetNum, order.getWait_receiving());
                setOrderNum(mTvEvaluateNum, order.getReceived());
                setOrderNum(mTvRefundNum, order.getRefund());
            }

        }
    }

    private void setOrderNum(TextView tv, int num) {
        if (0 < num) {
            tv.setVisibility(View.VISIBLE);
            if (num > 99) tv.setText("99+");
            else tv.setText(num + "");
        } else {
            tv.setVisibility(View.GONE);
        }
    }

    @Override
    public void requestFail(String type, int code, String msg) {
        if (mLLAccount == null) return;
        HnToastUtils.showToastShort(msg);
    }

    private void updateUi(HnLoginBean result) {
        if (mActivity == null || result == null) return;
        try {
            this.result = result;
            try {
                mLevelView.setLevel(Integer.valueOf(result.getUser_level()));
            } catch (NumberFormatException e) {
                e.printStackTrace();
                mLevelView.setLevel(1);
            }
            mTvTrackNum.setText(result.getTrack());
            //头像
            String logo = result.getUser_avatar();
            mIvIco.setImageURI(Uri.parse(logo));
            //昵称
            String nick = result.getUser_nickname();
            mTvName.setText(nick);
            //性别
            String sex = result.getUser_sex();
            if ("1".equals(sex)) {//男
                mIvSex.setImageResource(R.mipmap.man);
            } else {
                mIvSex.setImageResource(R.mipmap.girl);
            }
            //我的等级
            String userLvel = result.getUser_level();
//            HnLiveLevelUtil.setAudienceLevBg(tvLevel, userLvel, true);
            //主播等级
            String liveLevel = result.getAnchor_level();
            if (TextUtils.isEmpty(liveLevel) || Integer.parseInt(liveLevel) < 1) {
//                tvLiveLevel.setVisibility(View.GONE);
//                mTvAnchorRelated.setVisibility(View.GONE);
            } else {
//                tvLiveLevel.setVisibility(View.GONE);
//                mTvAnchorRelated.setVisibility(View.VISIBLE);
//                tvLiveLevel.setText(liveLevel);
            }
            //签名
            String intro = result.getUser_intro();
            if (!TextUtils.isEmpty(intro)) {
//                tvIntro.setText(intro);
            }
            //id
            uid = result.getUser_id();
//            tvUid.setText(getString(R.string.u_hao) + uid);
            //关注
            String careNumber = result.getUser_follow_total();
            mTvCare.setText(HnUtils.setNoPoint(careNumber));
            //粉丝
            String fans = result.getUser_fans_total();
            mTvFans.setText(HnUtils.setNoPoint(fans));

            //vip
            String vip_expire = result.getUser_is_member();
            if (!TextUtils.isEmpty(vip_expire) && !"Y".equals(vip_expire)) {
//                ivWithdrawalsuccessful.setVisibility(View.GONE);
            } else {
//                ivWithdrawalsuccessful.setVisibility(View.GONE);
            }
            //实名认证


        } catch (Exception e) {
        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void msgNoReadEvent(EventBusBean event) {
        if (HnConstants.EventBus.Update_Unread_Count.equals(event.getType())) {
            int noRead = (int) event.getObj();
            if (0 < noRead) {
                mTvNewMsg.setVisibility(View.VISIBLE);
                mTvNewMsg2.setVisibility(View.VISIBLE);
            } else {
                mTvNewMsg.setVisibility(View.GONE);
                mTvNewMsg2.setVisibility(View.GONE);
            }
        }

    }


    /**
     * 更新版本
     */
    private void updataVer() {
        CommDialog.newInstance(getActivity())
                .setClickListen(new CommDialog.TwoSelDialog() {
                    @Override
                    public void leftClick() {
                    }

                    @Override
                    public void rightClick() {
                        if (!HnMyUitls.isDownloadManagerAvailable(getActivity())) {
                            HnMyUitls.openDownloadManager(getActivity());
                            return;
                        }
                        getActivity().startService(new Intent(getActivity(), HnAppUpdateService.class)
                                .putExtra("downLoadUrl", HnApplication.getmConfig().getVersion().getDownload_url()));
                    }
                })
                .setTitle("检测到最新版本，是否立即更新")
                .show();
    }


    /**
     * 拨打电话（直接拨打电话）
     * @param phoneNum 电话号码
     */
    public void callPhone(String phoneNum){
        Intent intent = new Intent(Intent.ACTION_CALL);
        Uri data = Uri.parse("tel:" + phoneNum);
        intent.setData(data);
        startActivity(intent);
    }

    private void getShopRealNameState() {
        HnHttpUtils.postRequest(HnUrl.CERTIFICATION_DETAIL, null, TAG, new HnResponseHandler<TheStoreModel>(TheStoreModel.class) {
            @Override
            public void hnSuccess(String response) {
                if (model.getD() == null) return;
                if (model.getC() == 0) {
                    TheStoreModel.DBean.ShopBean shopBean =model.getD().getShop();
                    if (null!=shopBean&&("Y".equals(shopBean.getStore_certification_status()) && "Y".equals(shopBean.getStore_certification_status()))) {
//                        getRealNameDetails();
                        tv_begin_live.setVisibility(View.VISIBLE);
                        mLlFans.setVisibility(View.VISIBLE);
                        mLLShopAddress.setVisibility(View.GONE);
                        address_rl.setVisibility(View.VISIBLE);
                        aboutrl_rl.setVisibility(View.VISIBLE);
                        aboutnull_rl.setVisibility(View.GONE);
                        about_rl.setVisibility(View.GONE);
                    }else {
                        tv_begin_live.setVisibility(View.GONE);
                        mLlFans.setVisibility(View.GONE);
                        mLLShopAddress.setVisibility(View.VISIBLE);
                        address_rl.setVisibility(View.GONE);
                        aboutrl_rl.setVisibility(View.GONE);
                        aboutnull_rl.setVisibility(View.VISIBLE);
                        about_rl.setVisibility(View.VISIBLE);
                    }
                } else {
                    HnToastUtils.showToastShort(model.getM());
                }
            }

            @Override
            public void hnErr(int errCode, String msg) {
                HnToastUtils.showToastShort(msg);
            }
        });
    }

    private void clickTabLiveLayout(final String title) {
        int netWorkState = HnNetUtil.getNetWorkState(getActivity());
        if (netWorkState == HnNetUtil.NETWORK_WIFI) {//是否wifi
            checkPermission(title);
        } else if (netWorkState == HnNetUtil.NETWORK_MOBILE) {//是否流量
            CommDialog.newInstance(getActivity()).setClickListen(new CommDialog.TwoSelDialog() {
                @Override
                public void leftClick() {
                    checkPermission(title);
                }

                @Override
                public void rightClick() {
                    HnNetUtil.openWirelessSettings(getActivity());

                }
            }).setTitle(HnUiUtils.getString(R.string.prompt)).setContent(HnUiUtils.getString(R.string.no_wifi))
                    .setRightText(HnUiUtils.getString(R.string.to_set)).setLeftText(HnUiUtils.getString(R.string.live_continue_play)).show();

        } else if (netWorkState == HnNetUtil.NETWORK_NONE) {//没有网络
            CommDialog.newInstance(getActivity()).setClickListen(new CommDialog.TwoSelDialog() {
                @Override
                public void leftClick() {
                }

                @Override
                public void rightClick() {
                    HnNetUtil.openWirelessSettings(getActivity());
                }
            }).setTitle(HnUiUtils.getString(R.string.prompt)).setContent(HnUiUtils.getString(R.string.no_network)).setRightText(HnUiUtils.getString(R.string.to_set)).show();
        }
    }
    /**
     * 检查权限是否打开
     */
    private void checkPermission(String title) {
        if (PermissionHelper.isCameraUseable() && PermissionHelper.isAudioRecordable()) {
            requestCanLive(title);
        } else {
            HnToastUtils.showToastShort("请开启相机或录音权限");
        }
    }

    /**
     * 是否能够直播
     */
    private void requestCanLive(final String title) {
        HnHttpUtils.getRequest(HnUrl.LIVE_GET_LIVE_INFO, null, HnUrl.LIVE_GET_LIVE_INFO, new HnResponseHandler<HnCanLiveModel>(mActivity, HnCanLiveModel.class) {

            @Override
            public void hnSuccess(String response) {
//                if (isFinishing()) return;
                if (model.getC() == 0 || model.getD() == null) {
                    startActivity(new Intent(getActivity(), HnBeforeLiveSettingActivity.class)
                            .putExtra("title", title)
                            .putExtra("bean", (Serializable) model.getD()));
                } else {
                    requestData();
                }
            }

            @Override
            public void hnErr(int errCode, String msg) {
                //实名认证未通过
                if (HnServiceErrorUtil.USER_CERTIFICATION_FAIL == errCode ||
                        HnServiceErrorUtil.USER_CERTIFICATION_CHECK == errCode ||
                        HnServiceErrorUtil.USER_NOT_CERTIFICATION == errCode) {
                    requestData();
                } else {
                    startActivity(new Intent(getActivity(), HnBeforeLiveSettingActivity.class));
                }

            }
        });
    }

    //TODO 检测实名认证-个人换成店铺认证
    private void requestData() {
        RequestParams param = new RequestParams();
        HnHttpUtils.postRequest(HnUrl.SELL_CENTER, param, "卖家中心", new HnResponseHandler<SellerCenterModel>(SellerCenterModel.class) {
            @Override
            public void hnSuccess(String response) {
//                if (isFinishing() || model.getD() == null) return;
                boolean authDetails = false;
                String authDetailsStu = model.getD().getStore().getStore_certification_status();
                switch (model.getD().getStore().getStore_certification_status()) {
                    case "Y"://通过
                        authDetails = true;//恭喜您，认证审核成功
                        break;
                    case "N"://拒绝
                        authDetails = false;//很遗憾，认证审核不通过
                        break;
                    case "C"://审核中
                        authDetails = true;//认证审核中，请耐心等待
                        break;
                    case "Z"://不存在
                        authDetails = false;//需要通过认证后才能开店直播
                        break;
                }
                if (authDetails) {
                    if (!"Y".equals(authDetailsStu))
                        ShopActFacade.openStoreAut("");
                } else {
                    if ("N".equals(authDetailsStu)) {//拒绝
                        ShopActFacade.openStoreAut("");
                    } else {
                        ShopActFacade.openAuthSort();
                    }
                }
            }

            @Override
            public void hnErr(int errCode, String msg) {
                HnToastUtils.showToastShort(msg);
            }
        });
    }
}
