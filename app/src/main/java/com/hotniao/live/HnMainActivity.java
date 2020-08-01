package com.hotniao.live;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.ScaleAnimation;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.hn.library.HnBaseApplication;
import com.hn.library.base.BaseActivity;
import com.hn.library.base.EventBusBean;
import com.hn.library.global.HnConstants;
import com.hn.library.global.HnUrl;
import com.hn.library.global.NetConstant;
import com.hn.library.http.HnHttpUtils;
import com.hn.library.http.HnResponseHandler;
import com.hn.library.manager.HnAppManager;
import com.hn.library.model.HnConfigModel;
import com.hn.library.model.HnLoginModel;
import com.hn.library.utils.HnLogUtils;
import com.hn.library.utils.HnNetUtil;
import com.hn.library.utils.HnPrefUtils;
import com.hn.library.utils.HnServiceErrorUtil;
import com.hn.library.utils.HnToastUtils;
import com.hn.library.utils.HnUtils;
import com.hn.library.utils.PermissionHelper;
import com.hn.library.view.CommDialog;
import com.hn.library.view.ImageTextButton;
import com.hotniao.live.activity.HnBeforeLiveSettingActivity;
import com.hotniao.live.activity.HnLoginActivity;
import com.hotniao.live.activity.HnWebActivity;
import com.hotniao.live.dialog.HnUpGradeDialog;
import com.hotniao.live.eventbus.HnSelectLiveCateEvent;
import com.hotniao.live.eventbus.HnSignEvent;
import com.hotniao.live.fragment.FindSourceFragment;
import com.hotniao.live.fragment.HnHomeCusFrag;
import com.hotniao.live.fragment.HnUserFragment;
import com.hotniao.live.fragment.LittleVideoFragment;
import com.hotniao.live.fragment.LiveFragment;
import com.hotniao.live.model.BackLoginEvent;
import com.hotniao.live.model.HnCanLiveModel;
import com.hotniao.live.model.HnLiveNoticeEvent;
import com.hotniao.live.model.HnLocationEntity;
import com.hotniao.live.model.HnLoginEvent;
import com.hotniao.live.model.HnNoReadMessageModel;
import com.hotniao.live.utils.HnAppConfigUtil;
import com.hotniao.live.utils.HnLocationBiz;
import com.hotniao.live.utils.HnUiUtils;
import com.hotniao.livelibrary.biz.webscoket.HnWebscoketConstants;
import com.hotniao.livelibrary.control.HnUserControl;

import com.hotniao.livelibrary.model.event.HnPrivateMsgEvent;
import com.hotniao.livelibrary.ui.HnStartServiceActivity;
import com.hotniao.livelibrary.widget.dialog.HnUserAccountForbiddenDialog;
import com.live.shoplib.ShopActFacade;
import com.live.shoplib.bean.FinishExceptMainActivityEvent;
import com.live.shoplib.bean.SellerCenterModel;
import com.live.shoplib.ui.frag.HnCarFrag;
import com.live.shoplib.ui.frag.HnShopFragment;
import com.loopj.android.http.RequestParams;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.Serializable;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;


/**
 * Copyright (C) 2017,深圳市红鸟网络科技股份有限公司 All rights reserved.
 * 项目名称：红鸟电商
 * 类描述：项目主界面
 * 创建人：阳石柏
 * 创建时间：2017/3/6 16:16
 * 修改人：阳石柏
 * 修改时间：2017/3/6 16:16
 * 修改备注：
 * Version:  1.0.0
 */
@Route(path = "/app/HnMainActivity")
public class HnMainActivity extends BaseActivity {

    private static final String[] permissions = {
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.CAPTURE_VIDEO_OUTPUT,
            Manifest.permission.CAMERA,
            Manifest.permission.RECORD_AUDIO,
            Manifest.permission.CALL_PHONE,
    };
    public static HnLocationEntity mLocEntity;
    @BindView(R.id.ib_home)
    ImageTextButton mIbHome;
    @BindView(R.id.ib_shop)
    ImageTextButton mIbShop;
    @BindView(R.id.ib_car)
    ImageTextButton mIbCar;
    @BindView(R.id.ib_mine)
    ImageTextButton mIbMine;
    @BindView(R.id.main_bar)
    LinearLayout mMainBar;
    @BindView(R.id.content_layout)
    FrameLayout mContentLayout;
    @BindView(R.id.mIvLive)
    ImageTextButton mIvLive;
    @BindView(R.id.mTvSign)
    TextView mTvSign;
    //底部标签切换fragment
    private HnHomeCusFrag mHomeFragment;
    private HnShopFragment mShopFragment;
    private LittleVideoFragment mLittleVideoFragment;
    private LiveFragment mLiveFragment;
    //这是以前的商铺的fragment
//    private HnShopFragment mLiveFragment;
    private HnUserFragment mUserFragment;
    //这是以前的购物车的fragment
//    private HnCarFrag mCarFrag;
    private FindSourceFragment mFindSourceFragment;
    private Disposable payObservable;
    private long oneDay = 60 * 60 * 24 * 1000;
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    private int color_normal, color_clicked;
    private long mLastTimes = 0;
    //定位信息
    private HnLocationBiz mHnLocationBiz;
    private ScaleAnimation anim;
    private AlertDialog dialog;

    @Override
    public int getContentViewId() {
        return R.layout.activity_main;
    }

    @SuppressLint("LongLogTag")
    @Override
    public void onCreateNew(Bundle savedInstanceState) {
        Log.e("################################", "main create");
        setShowTitleBar(false);
        EventBus.getDefault().register(this);
        //缩放动画
        anim = (ScaleAnimation) AnimationUtils.loadAnimation(this, R.anim.bottom_icon);
        //资源
        Resources resources = getResources();
        color_normal = mIsDay ? resources.getColor(R.color.color_999999) : resources.getColor(R.color.color_999999);
        color_clicked = getResources().getColor(R.color.main_color);

        //启动服务连接webscoket服务器
        String webscketUrl = HnPrefUtils.getString(NetConstant.User.Webscket_Url, "");
        Bundle bundle = new Bundle();
        bundle.putString("websocket_url", webscketUrl);
        bundle.putString("type", HnStartServiceActivity.WEBSCOKET_SERVICE);
        startActivity(new Intent(this, HnStartServiceActivity.class).putExtras(bundle));
        //每3分钟刷新
        payObservable = Observable.interval(0, 3 * 60 * 1000, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        EventBus.getDefault().post(new EventBusBean(0, HnConstants.EventBus.RefreshLiveList, ""));
                    }
                });
        /**
         * 注册极光别名
         */
        if (HnApplication.getmUserBean() == null) {
            getUserInfo(1);
        } else {
            HnApplication.login(HnApplication.getmUserBean().getUser_id());
        }
        //版本检测
        requestToCheckVersion();
        initLocation();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            // 检查该权限是否已经获取
            int i = ContextCompat.checkSelfPermission(this, permissions[0]);
            // 权限是否已经 授权 GRANTED---授权  DINIED---拒绝
            if (i != PackageManager.PERMISSION_GRANTED) {
                // 如果没有授予该权限，就去提示用户请求
                showDialogTipUserRequestPermission();
            }
        }
    }

    @SuppressLint("LongLogTag")
    @Override
    public void getInitData() {
        fragmentManager = getSupportFragmentManager();
        mIbHome.performClick();

    }

    @Override
    protected void onResume() {
        super.onResume();
        getNoReadMessage();

        /**
         * 配置为null  ，再次获取一次
         */
        if (HnApplication.getmConfig() == null) {
            HnAppConfigUtil.getConfig();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void receivePrivateEvent(HnPrivateMsgEvent event) {
        if (event != null) {
            if (HnWebscoketConstants.Send_Pri_Msg.equals(event.getType())) {
                getNoReadMessage();
            }
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
        mHnLocationBiz.startLocation(this);
        mHnLocationBiz.setOnLocationListener(new HnLocationBiz.OnLocationListener() {
            @Override
            public void onLocationSuccess(String province, String city, String address, String latitudeResult, String longitudeResult) {
                mLocEntity = new HnLocationEntity(latitudeResult, longitudeResult, city, province);
            }

            @Override
            public void onLocationFail(String errorRease, int code) {
            }
        });
    }

    @SuppressLint("LongLogTag")
    @OnClick({R.id.ib_home, R.id.ib_car, R.id.ib_shop, R.id.mIvLive, R.id.ib_mine, R.id.mTvSign})
    public void onClick(View view) {
        fragmentTransaction = fragmentManager.beginTransaction();
        switch (view.getId()) {
            case R.id.ib_home: //首页
                hideFragments(fragmentTransaction);
                clickTabHomeLayout();
                mIbHome.startAnimation(anim);
                break;
            case R.id.ib_shop: //商场
                hideFragments(fragmentTransaction);
                clickTabLittleVideoLayout();
                mIbShop.startAnimation(anim);
                break;
            case R.id.mIvLive: //直播
                //todo  待修改成展示直播列表的fragment
                //clickTabLiveLayout("");
                hideFragments(fragmentTransaction);
                clickTabLiveLayout();
                mIvLive.startAnimation(anim);
                break;
            case R.id.ib_car:
                hideFragments(fragmentTransaction);
                clickTabCarLayout();
                mIbCar.startAnimation(anim);
                break;
            case R.id.ib_mine: //我的
                hideFragments(fragmentTransaction);
                clickTabMineLayout();
                mIbMine.startAnimation(anim);
                getNoReadMessage();
                getUserInfo(2);


                break;
            case R.id.mTvSign:
                break;
        }

        fragmentTransaction.commitAllowingStateLoss();

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onNoticeLive(final HnLiveNoticeEvent event) {
        CommDialog.newInstance(this)
                .setTitle("开播提醒")
                .setContent(event.getContent())
                .setClickListen(new CommDialog.TwoSelDialog() {
                    @Override
                    public void leftClick() {

                    }

                    @Override
                    public void rightClick() {
                        clickTabLiveLayout(event.getTitle());
                    }
                })
                .setRightText("立即开播")
                .show();
    }

    /**
     * 隐藏所有显示的界面
     */

    private void hideFragments(FragmentTransaction transaction) {
        if (mHomeFragment != null) {
            transaction.hide(mHomeFragment);
        }
        if (mUserFragment != null) {
            transaction.hide(mUserFragment);
        }
        if (mLittleVideoFragment != null) {
            transaction.hide(mLittleVideoFragment);
        }
        if (mLiveFragment != null) {
            transaction.hide(mLiveFragment);
        }
        if (mFindSourceFragment != null) {
            transaction.hide(mFindSourceFragment);
        }
    }

    /**
     * 进入商城fragment
     */
    @SuppressLint("NewApi")
    private void clickTabHomeLayout() {
        if (mHomeFragment == null) {
            // mHomeFragment，则创建一个并添加到界面上
            mHomeFragment = new HnHomeCusFrag();
            fragmentTransaction.add(R.id.content_layout, mHomeFragment);
        } else {
            // homeFrag不为空，则直接将它显示出来
            fragmentTransaction.show(mHomeFragment);
        }
        updateMenu(mIbHome);
    }

    /**
     * 进入直播fragment
     * todo   待修改成展示直播列表的fragment
     */
    private void clickTabLiveLayout() {
        if (mLiveFragment == null) {
            mLiveFragment = new LiveFragment();
            fragmentTransaction.add(R.id.content_layout, mLiveFragment);
        } else {
            fragmentTransaction.show(mLiveFragment);
        }
        updateMenu(mIvLive);
    }

    /**
     * 进入我的fragment
     */
    private void clickTabMineLayout() {
        if (mUserFragment == null) {
            mUserFragment = new HnUserFragment();
            fragmentTransaction.add(R.id.content_layout, mUserFragment);
        } else {
            fragmentTransaction.show(mUserFragment);
        }
        updateMenu(mIbMine);
    }

    /**
     * 进入我的购物车
     */
    private void clickTabCarLayout() {
        if (mFindSourceFragment == null) {
            mFindSourceFragment = new FindSourceFragment();
            fragmentTransaction.add(R.id.content_layout, mFindSourceFragment);
        } else {
            fragmentTransaction.show(mFindSourceFragment);
        }
        updateMenu(mIbCar);
    }

    /**
     * 进入商场
     */
    private void clickTabLittleVideoLayout() {
        if (mLittleVideoFragment == null) {
            mLittleVideoFragment = new LittleVideoFragment();
            fragmentTransaction.add(R.id.content_layout, mLittleVideoFragment);
        } else {
            fragmentTransaction.show(mLittleVideoFragment);
        }
        updateMenu(mIbShop);
    }

    /**
     * 更新菜单状态
     */
    public void updateMenu(ImageTextButton button) {
        //首页
        if (mIbHome != null && button != mIbHome) {
            mIbHome.changeState(R.mipmap.shop_un_selected, color_normal);
        } else {
            mIbHome.changeState(R.mipmap.shop_selected, color_clicked);
        }
        //商城
        if (mIbShop != null && button != mIbShop) {
            mIbShop.changeState(R.mipmap.little_movie_un_selected, color_normal);
        } else {
            mIbShop.changeState(R.mipmap.little_movie_selected, color_clicked);
        }

        //直播
        if (mIvLive != null && button != mIvLive) {
            mIvLive.changeState(R.mipmap.live_un_selected, color_normal);
        } else {
            mIvLive.changeState(R.mipmap.live_selected, color_clicked);
        }
        //购物车
        if (mIbCar != null && button != mIbCar) {
            mIbCar.changeState(R.mipmap.source_un_selected, color_normal);
        } else {
            mIbCar.changeState(R.mipmap.source_selected, color_clicked);
        }
        //我的
        if (mIbMine != null && button != mIbMine) {
            mIbMine.changeState(R.mipmap.mine_un_selected, color_normal);
        } else {
            mIbMine.changeState(R.mipmap.mine_selected, color_clicked);
        }
    }

    /**
     * 进入直播fragment
     * todo 这里是开直播的界面，以后可能会用到
     */
    private void clickTabLiveLayout(final String title) {
        int netWorkState = HnNetUtil.getNetWorkState(this);
        if (netWorkState == HnNetUtil.NETWORK_WIFI) {//是否wifi
            checkPermission(title);
        } else if (netWorkState == HnNetUtil.NETWORK_MOBILE) {//是否流量
            CommDialog.newInstance(this).setClickListen(new CommDialog.TwoSelDialog() {
                @Override
                public void leftClick() {
                    checkPermission(title);
                }

                @Override
                public void rightClick() {
                    HnNetUtil.openWirelessSettings(HnMainActivity.this);

                }
            }).setTitle(HnUiUtils.getString(R.string.prompt)).setContent(HnUiUtils.getString(R.string.no_wifi))
                    .setRightText(HnUiUtils.getString(R.string.to_set)).setLeftText(HnUiUtils.getString(R.string.live_continue_play)).show();

        } else if (netWorkState == HnNetUtil.NETWORK_NONE) {//没有网络
            CommDialog.newInstance(HnMainActivity.this).setClickListen(new CommDialog.TwoSelDialog() {
                @Override
                public void leftClick() {
                }

                @Override
                public void rightClick() {
                    HnNetUtil.openWirelessSettings(HnMainActivity.this);
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
        HnHttpUtils.getRequest(HnUrl.LIVE_GET_LIVE_INFO, null, HnUrl.LIVE_GET_LIVE_INFO, new HnResponseHandler<HnCanLiveModel>(this, HnCanLiveModel.class) {

            @Override
            public void hnSuccess(String response) {
                if (isFinishing()) return;
                if (model.getC() == 0 || model.getD() == null) {
                    startActivity(new Intent(HnMainActivity.this, HnBeforeLiveSettingActivity.class)
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
                    startActivity(new Intent(HnMainActivity.this, HnBeforeLiveSettingActivity.class));
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
                if (isFinishing() || model.getD() == null) return;
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

    @Override
    public void onBackPressed() {
        if (System.currentTimeMillis() - mLastTimes > 1000) {
            mLastTimes = System.currentTimeMillis();
            HnToastUtils.showToastShort("再按一次退出");
        } else {
            EventBus.getDefault().post(new EventBusBean(0, "stop_websocket_service", null));
            finish();
            HnAppManager.getInstance().exit();
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Subscribe
    public void onBackLogin(BackLoginEvent event) {
        openActivity(HnLoginEvent.class);
        finish();
    }

    @Subscribe
    public void onEventBusCallBack(EventBusBean event) {
        if (event != null) {
            if (HnConstants.EventBus.Switch_Fragment.equals(event.getType())) {//切换到热门
                fragmentManager = getSupportFragmentManager();
                mIbHome.performClick();
                EventBus.getDefault().post(new HnSelectLiveCateEvent(HnSelectLiveCateEvent.REFRESH_UI, "", 0));
            } else if (HnConstants.EventBus.LoginFailure.equals(event.getType())) {//登录状态失效
                HnLoginActivity.luncher(HnMainActivity.this, true);
                HnAppManager.getInstance().finishOthersActivity(HnLoginActivity.class);
            }
        }
    }
    @Subscribe
    public void onFinishExceptMainActivity(FinishExceptMainActivityEvent event) {
        HnAppManager.getInstance().finishOthersActivity(HnMainActivity.class);
    }
    @SuppressLint("LongLogTag")
    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e("################################", "main destory");
        EventBus.getDefault().post(new EventBusBean(0, "stop_websocket_service", null));
        EventBus.getDefault().unregister(this);
    }

    /**
     * 根据主题刷新tabbar
     */
    public void refreshTabBar() {
        TypedValue item_bg_color = new TypedValue();
        getTheme().resolveAttribute(R.attr.item_bg_color, item_bg_color, true);
        mMainBar.setBackgroundResource(item_bg_color.resourceId);
        //更换图标
        boolean isDay = mDayNightHelper.isDay();
        Resources resources = getResources();
        color_normal = isDay ? resources.getColor(R.color.black_tran) : resources.getColor(R.color.white);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            boolean mUser_Forbidden = bundle.getBoolean("User_Forbidden");
            if (mUser_Forbidden) {
                HnUserAccountForbiddenDialog dialog = HnUserAccountForbiddenDialog.getInstance();
                dialog.show(getSupportFragmentManager(), "HnUserAccountForbiddenDialog");
            }
        }
    }

    /**
     * 更新FragmentUI
     */
    public void updateFragmentUI() {
        refreshTabBar();
    }

    /**
     * 网络请求：版本检测
     */
    public void requestToCheckVersion() {
        HnHttpUtils.postRequest(HnUrl.USER_APP_CONFIG, null, TAG, new HnResponseHandler<HnConfigModel>(this, HnConfigModel.class) {
                    @Override
                    public void hnSuccess(String response) {
                        if (model.getC() == 0) {
                            //保存全局配置信息
                            if (model.getD() != null) {
                                HnPrefUtils.setString(HnConstants.Setting.USER_CONFIG_MSG, response);
                                HnBaseApplication.setmConfig(model.getD());
                                //版本更新
                                if (model.getD().getVersion() != null) {
                                    String version = model.getD().getVersion().getCode();
                                    String downloadUrl = model.getD().getVersion().getDownload_url();
                                    int currentCode = HnUtils.getVersionCode(HnMainActivity.this);
                                    if (!TextUtils.isEmpty(version)) {
                                        if (currentCode < Integer.valueOf(version)) {
                                            HnUpGradeDialog mHnUpGradeDialog = HnUpGradeDialog.newInstance(downloadUrl, true, model.getD().getVersion().getIs_force(), model.getD().getVersion().getContent());
                                            mHnUpGradeDialog.show(getFragmentManager(), "HnUpGradeDialog");
                                        }
                                    }
                                }
                            }
                        }
                    }

                    @Override
                    public void hnErr(int errCode, String msg) {
                        HnLogUtils.i(TAG, "检测版本失败：" + msg);
                    }
                }
        );
    }

    /**
     * 获取未读消息数
     */
    private void getNoReadMessage() {
        HnHttpUtils.postRequest(HnUrl.USER_CHAT_UNREAD, null, HnUrl.USER_CHAT_UNREAD, new HnResponseHandler<HnNoReadMessageModel>(HnNoReadMessageModel.class) {
            @Override
            public void hnSuccess(String response) {
                if (isFinishing()) return;
                try {
                    if (model.getC() == 0 && model.getD().getUnread() != null) {
                        HnNoReadMessageModel.DBean.UnreadBean bean = model.getD().getUnread();
                        HnPrefUtils.setString(NetConstant.User.Unread_Count, bean.getUser_chat());
                        EventBus.getDefault().post(new EventBusBean(1, HnConstants.EventBus.Update_Unread_Count, TextUtils.isEmpty(bean.getTotal()) ? 0 : Integer.parseInt(bean.getTotal())));
                    }
                } catch (Exception e) {

                }
            }

            @Override
            public void hnErr(int errCode, String msg) {

            }
        });
    }

    /*########################################################################################*/

    /***
     * 如果个人信息缺失 在获取一次
     */
    private void getUserInfo(final int type) {
        HnUserControl.getProfile(new HnUserControl.OnUserInfoListener() {
            @Override
            public void onSuccess(String uid, HnLoginModel model, String response) {
                if (type == 1) {
                    HnApplication.login(uid);
                }

            }

            @Override
            public void onError(int errCode, String msg) {
            }
        });
    }

    // 提示用户该请求权限的弹出框
    private void showDialogTipUserRequestPermission() {
        new AlertDialog.Builder(this)
                .setTitle("相机权限、读写权限")
                .setMessage("由于需要摄像，需要开启相机权限\n" +
                        "存储文件，需要开启读写权限\n" +
                        "否则无法正常使用")
                .setPositiveButton("立即开启", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startRequestPermission();
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        HnToastUtils.showCenterToast("请允许权限开启");
                        finish();
                    }
                }).setCancelable(false).show();
    }

    // 开始提交请求权限
    private void startRequestPermission() {
        ActivityCompat.requestPermissions(this, permissions, 321);
    }

    // 用户权限 申请 的回调方法
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 321) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    // 判断用户是否 点击了不再提醒。(检测该权限是否还可以申请)
                    boolean b = shouldShowRequestPermissionRationale(permissions[0]);
                    if (!b) {
                        // 用户还是想用我的 APP 的
                        // 提示用户去应用设置界面手动开启权限
                        showDialogTipUserGoToAppSettting();
                    } else
                        HnToastUtils.showCenterToast("请允许权限开启");
                    finish();
                } else {
                    Toast.makeText(this, "权限获取成功", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    // 提示用户去应用设置界面手动开启权限
    private void showDialogTipUserGoToAppSettting() {
        dialog = new AlertDialog.Builder(this)
                .setTitle("权限不可用")
                .setMessage("请在-应用设置-权限-中，手动开启权限")
                .setPositiveButton("去开启", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // 跳转到应用设置界面
                        goToAppSetting();
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        HnToastUtils.showCenterToast("请允许权限开启");
                        finish();
                    }
                }).setCancelable(false).show();
    }

    // 跳转到当前应用的设置界面
    private void goToAppSetting() {
        Intent intent = new Intent();
        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", getPackageName(), null);
        intent.setData(uri);
        startActivityForResult(intent, 123);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 123) {
            if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                // 检查该权限是否已经获取
                int i = ContextCompat.checkSelfPermission(this, permissions[0]);
                // 权限是否已经 授权 GRANTED---授权  DINIED---拒绝
                if (i != PackageManager.PERMISSION_GRANTED) {
                    // 提示用户应该去应用设置界面手动开启权限
                    showDialogTipUserGoToAppSettting();
                } else {
                    if (dialog != null && dialog.isShowing()) {
                        dialog.dismiss();
                    }
                    Toast.makeText(this, "权限获取成功", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

}
