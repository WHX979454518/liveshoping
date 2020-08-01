package com.hotniao.live.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.hn.library.banner.HnBannerLayout;
import com.hn.library.base.BaseActivity;
import com.hn.library.global.HnUrl;
import com.hn.library.global.NetConstant;
import com.hn.library.http.HnHttpUtils;
import com.hn.library.http.HnResponseHandler;
import com.hn.library.utils.HnDateUtils;
import com.hn.library.utils.HnPrefUtils;
import com.hn.library.utils.HnToastUtils;
import com.hn.library.utils.ShareManager;
import com.hn.library.view.FrescoImageView;
import com.hotniao.live.R;
import com.live.shoplib.ShopActFacade;
import com.live.shoplib.ShopDialogFacade;
import com.live.shoplib.ShopRequest;
import com.live.shoplib.bean.AddCarModel;
import com.live.shoplib.bean.BannerModel;
import com.live.shoplib.bean.GoodsCarNumModel;
import com.live.shoplib.bean.GoodsCommitModel;
import com.live.shoplib.bean.GoodsDetailsModel;
import com.live.shoplib.bean.ShoppingCarRefreshEvent;
import com.live.shoplib.bean.ViewPager;
import com.live.shoplib.ui.dialog.GoodsSelDialog;
import com.live.shoplib.ui.dialog.ShareDialog;
import com.hotniao.live.utils.Banner;
import com.live.shoplib.widget.DragLayout;
import com.live.shoplib.widget.StartRatingBar;
import com.loopj.android.http.RequestParams;
import com.xiao.nicevideoplayer.NiceVideoPlayer;
import com.xiao.nicevideoplayer.NiceVideoPlayerController;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


/**
 * 商品详情(第一张图片带有视频)
 */
@Route(path = "/app/GoodsHaveVideoDetailsAct")
public class GoodsHaveVideoDetailsAct extends BaseActivity {

    private RelativeLayout mTitle;
    private ImageView mIvBack;
    private TextView mTvTitle;
    private ImageView mIvBarShopCar;
    private ImageView mIvAddGoodsNew;
    private ImageView mIvBarShare;
    private HnBannerLayout mBanner;
    private TextView mTvGoodsName;
    private TextView mTvPrice;
    private TextView mTvGoodsFreight;
    private TextView mTvGoodsSell;
    private TextView mTvGoodsStore;
    private LinearLayout mLLGoodsSize;
    private LinearLayout mLLServer;
    private TextView mTvGoodsSize;
    private LinearLayout mLLGoodsAttr;
    private LinearLayout mLLComment;
    private LinearLayout mLLComment2;
    private LinearLayout mLLCommentDetails;
    private LinearLayout mLLCommmentEmpty;
    private LinearLayout tag;
    private TextView mTvCommment;
    private TextView mTvCommentPecent;
    private TextView mTvCommentState;
    private FrescoImageView mIvUserIco;
    private TextView mTvUserName;
    private StartRatingBar mIvStart;
    private TextView mTvEva;
    private FrescoImageView mIvEvaIco1;
    private FrescoImageView mIvEvaIco2;
    private FrescoImageView mIvEvaIco3;
    private TextView mTvGoodsMsg;
    private TextView mTvConnection;
    private TextView mTvShop;
    private CheckBox mBoxCollect;
    private TextView mTvShopCar;
    private TextView mTvBug;
    private TextView mTvServer1, mTvServer2, mTvServer3;
    private WebView mWebView;
    private View mViewCommLine;
    private DragLayout mDragLayout;
    private GoodsDetailsModel.DEntity bean;
    private String goods_id;
    private String num, sid, stxt;
    private boolean hint;
    private ShareManager manager;
    private ShareDialog dialog;


    NiceVideoPlayer video_player;
    //手指按下的点为(x1, y1)手指离开屏幕的点为(x2, y2)
    float x1 = 0;
    float x2 = 0;
    float y1 = 0;
    float y2 = 0;
    ScrollView crollview;
    int psotion= 0;


    private static final int UPTATE_VIEWPAGER = 0;
    public ViewPager mViewPager;
    private List<BannerModel> list;
    private BannerViewAdapter mAdapter;
    private int autoCurrIndex = 0;//设置当前 第几个图片 被选中
    private Timer timer;
    private TimerTask timerTask;
    private long period = 500000;//轮播图展示时长,默认5秒

    private Banner banner;
    private List<String> bannerlist;


    //定时轮播图片，需要在主线程里面修改 UI
    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case UPTATE_VIEWPAGER:
                    if (msg.arg1 != 0) {
//                        mViewPager.setCurrentItem(msg.arg1);
                        mViewPager.setCurrentItem(msg.arg1, false);
                    } else {
                        //false 当从末页调到首页时，不显示翻页动画效果，
                        mViewPager.setCurrentItem(msg.arg1, false);
                    }
                    break;
            }
        }
    };


    @Override
    public int getContentViewId() {
        return R.layout.actact_goodvideos_details;
    }

    @Override
    public void onCreateNew(Bundle savedInstanceState) {
        setShowTitleBar(false);
        initId();
        mDragLayout.setOnViewChangedListener(new DragLayout.OnViewChangedListener() {
            @Override
            public void onViewChanged(boolean showTop) {
                if (showTop) {
//                    mDragTipView.setText(getString(R.string.up_to_detail));
                } else {
//                    mDragTipView.setText(getString(R.string.down_to_detail));
                }
            }
        });
        DragLayout.initWebView(mWebView, null);
        manager = new ShareManager(this);
    }

    int positionsss = 0;

    private void initId() {
        mTitle = (RelativeLayout) findViewById(R.id.mTitle);
        mIvAddGoodsNew = findViewById(R.id.mIvAddGoodsNew);
        mIvBack = (ImageView) findViewById(R.id.mIvBack);
        mTvTitle = (TextView) findViewById(R.id.mTvTitle);
        mIvBarShopCar = (ImageView) findViewById(R.id.mIvBarShopCar);
        mIvBarShare = (ImageView) findViewById(R.id.mIvBarShare);
        mBanner = (HnBannerLayout) findViewById(R.id.mBanner);
        mTvGoodsName = (TextView) findViewById(R.id.mTvGoodsName);
        mTvPrice = (TextView) findViewById(R.id.mTvPrice);
        mTvGoodsFreight = (TextView) findViewById(R.id.mTvGoodsFreight);
        mTvGoodsSell = (TextView) findViewById(R.id.mTvGoodsSell);
        mTvGoodsStore = (TextView) findViewById(R.id.mTvGoodsStore);
        mLLGoodsSize = (LinearLayout) findViewById(R.id.mLLGoodsSize);
        mTvGoodsSize = (TextView) findViewById(R.id.mTvGoodsSize);
        mLLGoodsAttr = (LinearLayout) findViewById(R.id.mLLGoodsAttr);
        mLLComment = (LinearLayout) findViewById(R.id.mLLComment);
        mLLComment2 = (LinearLayout) findViewById(R.id.mLLComment2);
        mLLCommentDetails = (LinearLayout) findViewById(R.id.mLLCommentDetails);
        mLLCommmentEmpty = (LinearLayout) findViewById(R.id.mLLCommmentEmpty);
        mLLServer = (LinearLayout) findViewById(R.id.mLLServer);
        mTvCommment = (TextView) findViewById(R.id.mTvCommment);
        mTvCommentPecent = (TextView) findViewById(R.id.mTvCommentPecent);
        mTvCommentState = (TextView) findViewById(R.id.mTvCommentState);
        mIvUserIco = (FrescoImageView) findViewById(R.id.mIvUserIco);
        mTvUserName = (TextView) findViewById(R.id.mTvUserName);
        mIvStart = findViewById(R.id.mIvStart);
        mTvEva = (TextView) findViewById(R.id.mTvEva);
        mIvEvaIco1 = (FrescoImageView) findViewById(R.id.mIvEvaIco1);
        mIvEvaIco2 = (FrescoImageView) findViewById(R.id.mIvEvaIco2);
        mIvEvaIco3 = (FrescoImageView) findViewById(R.id.mIvEvaIco3);
        mTvGoodsMsg = (TextView) findViewById(R.id.mTvGoodsMsg);
        mTvConnection = (TextView) findViewById(R.id.mTvConnection);
        mTvShop = (TextView) findViewById(R.id.mTvShop);
        mBoxCollect = findViewById(R.id.mBoxCollect);
        mTvShopCar = (TextView) findViewById(R.id.mTvShopCar);
        mTvBug = (TextView) findViewById(R.id.mTvBug);
        mWebView = (WebView) findViewById(R.id.mWebView);
        mDragLayout = (DragLayout) findViewById(R.id.mDragLayout);
        mTvServer1 = (TextView) findViewById(R.id.mTvServer1);
        mTvServer2 = (TextView) findViewById(R.id.mTvServer2);
        mTvServer3 = (TextView) findViewById(R.id.mTvServer3);
        mViewCommLine = findViewById(R.id.mViewCommLine);
        tag = findViewById(R.id.tag);


        mViewPager =   findViewById(com.hotniao.live.R.id.mmViewPager);
//        initData();
        video_player = findViewById(com.hotniao.live.R.id.video_player);
        crollview = findViewById(R.id.crollview);

    }

    @Override
    public void getInitData() {
        goods_id = getIntent().getStringExtra("goods_id");
        hint = getIntent().getBooleanExtra("hint", false);
        if (TextUtils.isEmpty(goods_id)) {
            finish();
            return;
        }
        if (hint) tag.setVisibility(View.GONE);
        getDetailsData(goods_id);
    }

    public void onGoodsDetailsClick(View v) {
        if (v == mTvBug) {
            ShopDialogFacade.showGoodsSel(bean, getFragmentManager(), "购买", sureClick);
        } else if (v == mTvShopCar) {
            ShopDialogFacade.showGoodsSel(bean, getFragmentManager(), "加入", sureClick);
        } else if (v == mBoxCollect) {//收藏
            if (bean == null) return;
            mBoxCollect.setEnabled(false);
            ShopRequest.collectGoods(bean.getGoods().getGoods_id(), mBoxCollect.isChecked() ? "1" : "0", new ShopRequest.OnRespondNothing() {
                @Override
                public void finishs() {
                    mBoxCollect.setEnabled(true);
                    bean.setIs_collect(TextUtils.equals("Y", bean.getIs_collect()) ? "N" : "Y");
                }
            });
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    mBoxCollect.setEnabled(true);
                }
            }, 3000);
        } else if (v == mTvShop) {//店铺
            ShopActFacade.openShopDetails(bean.getGoods().getStore_id());
        } else if (v == mTvConnection) {//客服
            if (bean == null) return;
            String uid_own = HnPrefUtils.getString(NetConstant.User.UID, "");
            if (TextUtils.equals(uid_own, bean.getDialogue().getStore_uid())) {
                HnToastUtils.showToastShort("无法与自己聊天");
                return;
            }
            ShopActFacade.openPrivateChat(bean.getDialogue().getStore_uid(), bean.getDialogue().getName(), bean.getDialogue().getCharId());
        } else if (v == mIvBarShare) {//分享
            if (bean == null) return;
            dialog = new ShareDialog(ShareDialog.Type.Goods, getSupportFragmentManager())
                    .setGoodsShare(bean.getGoods().getGoods_name(), bean.getGoods().getGoods_img(), bean.getShare())
                    .show();
        } else if (v == mIvBarShopCar) {//购物车
            ShopActFacade.openGoodsCar();
        } else if (v == mIvBack) {//退出
            finish();
        } else if (v == mLLServer) {//服务
            if (bean == null) return;
            ShopDialogFacade.showGoodsAttrPro(getFragmentManager(), bean, "服务说明");
        } else if (v == mLLGoodsSize) {//规格
            ShopDialogFacade.showGoodsSel(bean, getFragmentManager(), "", sureClick);
        } else if (v == mLLGoodsAttr) {//参数
            if (bean == null) return;
            ShopDialogFacade.showGoodsAttrPro(getFragmentManager(), bean, "产品参数");
        } else if (v == mLLComment || v == mLLComment2) {//评论
            if (bean == null) return;
            ShopActFacade.openEvaGoods(bean.getGoods().getGoods_id());
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (dialog != null)
            dialog.setActivityResult(requestCode, resultCode, data);
    }

    GoodsSelDialog.OnSureClick sureClick = new GoodsSelDialog.OnSureClick() {
        @Override
        public void click(String n, String i, String s, String go) {
            num = n;
            sid = i;
            stxt = s;
            mTvGoodsSize.setText(s);
            if (TextUtils.equals(go, "购买")) {
                requestAddCar(num, sid, stxt, "fast");
            } else if (TextUtils.equals(go, "加入")) {
                requestAddCar(num, sid, stxt, "normal");
            } else {

            }
            requestGetCarNum();
        }
    };

    private void requestAddCar(String num, String sku_id, String spec, final String type) {
        RequestParams param = new RequestParams();
        param.put("num", num);
        param.put("sku_id", sku_id);
        param.put("type", type);
        param.put("spec", spec);
        HnHttpUtils.postRequest(HnUrl.ADD_CAR_ORD, param, "添加购物车", new HnResponseHandler<AddCarModel>(AddCarModel.class) {
            @Override
            public void hnSuccess(String response) {
                if (type.equals("normal")) {
                    HnToastUtils.showToastShort("成功加入购物车");
                    EventBus.getDefault().post(new ShoppingCarRefreshEvent());
                } else {
                    requestSubCar(model.getD().getCart_id());
                }
            }


            @Override
            public void hnErr(int errCode, String msg) {
                HnToastUtils.showToastShort(msg);
            }
        });
    }


    //修改购物车
    private void requestSubCar(String cartList) {
        RequestParams param = new RequestParams();
        param.put("cartList", cartList);
        HnHttpUtils.postRequest(HnUrl.SUB_CAR, param, "提交购物车", new HnResponseHandler<GoodsCommitModel>(GoodsCommitModel.class) {
            @Override
            public void hnSuccess(String response) {
                if (isFinishing()) return;

                ShopActFacade.openGoodsCommit(model);
            }

            @Override
            public void hnErr(int errCode, String msg) {
                HnToastUtils.showToastShort(msg);
            }
        });
    }

    //商品详情
    public void getDetailsData(String goods_id) {
        RequestParams param = new RequestParams();
        param.put("goods_id", goods_id);
        HnHttpUtils.postRequest(HnUrl.GOODS_DETAILs, param, "商品详情", new HnResponseHandler<GoodsDetailsModel>(GoodsDetailsModel.class) {
            @Override
            public void hnSuccess(String response) {
                if (model.getD() == null || isFinishing()) return;


                banner = (Banner) findViewById(R.id.banner);




//                initData();
                list = new ArrayList<>();
                bannerlist = new ArrayList<>();
                BannerModel listBean = new BannerModel();
                //判断有没有视频，如果有视频则添加到集合，没有则不草坐
                if(!model.getD().getGoods().getTrace_url().equals("")||!model.getD().getGoods().getTrace_id().equals("")){
                    listBean.setBannerName(model.getD().getGoods().getGoods_img());//视频的封面
//                listBean.setBannerUrl("http://clips.vorwaerts-gmbh.de/big_buck_bunny.mp4");
//                    listBean.setBannerUrl("http://dianyidian-1259301974.file.myqcloud.com/video/20190729/1564369190434910.mp4");
                    listBean.setBannerUrl(model.getD().getGoods().getTrace_url());
                    listBean.setPlayTime(270000);
                    listBean.setUrlType(1);//图片类型 视频
                    list.add(listBean);


                    bannerlist.add(model.getD().getGoods().getTrace_url());
                }
                for (int i = 0; i < model.getD().getGoods().getGoods_imgs().size(); i++) {
                    BannerModel listBean1 = new BannerModel();
//                    listBean1.setBannerName("");
                    listBean1.setBannerUrl(model.getD().getGoods().getGoods_imgs().get(i));
                    listBean1.setPlayTime(270000);
                    listBean1.setUrlType(0);//图片类型 图片
                    list.add(listBean1);

                    bannerlist.add(model.getD().getGoods().getGoods_imgs().get(i));
                }

                banner.setDataList(bannerlist);
                banner.setImgDelyed(200000);
                banner.startBanner();
                banner.startAutoPlay();
                banner.isAutoPlay = false;



//                autoBanner();



                bean = model.getD();
                //轮播图
//                mBanner.setViewUrls(model.getD().getGoods().getGoods_imgs());
                //禁止Banner自动滑动
                psotion = mBanner.currentPosition;

//                video_player.setVisibility(View.VISIBLE);
//                mBanner.setVisibility(View.GONE);

                mBanner.isAutoPlay = false;
                //商品名
                mTvGoodsName.setText(model.getD().getGoods().getGoods_name());
                //商品价格
                try {
                    if (Float.valueOf(model.getD().getGoods().getGoods_price()) >= Float.valueOf(model.getD().getGoods().getGoods_max_price())) {
                        mTvPrice.setText("¥" + model.getD().getGoods().getGoods_price());
                    } else {
                        mTvPrice.setText("¥" + model.getD().getGoods().getGoods_price() + "~" + model.getD().getGoods().getGoods_max_price());
                    }
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                    mTvPrice.setText("¥" + model.getD().getGoods().getGoods_price());
                }
                //运费
                if (TextUtils.equals(model.getD().getGoods().getShop_fee(), "0")) {
                    mTvGoodsFreight.setText("包邮");
                } else {
                    mTvGoodsFreight.setText("运费：¥"+model.getD().getGoods().getShop_fee());
                }
                //销量
                mTvGoodsSell.setText(model.getD().getGoods().getGoods_sales());
                //库存
                mTvGoodsStore.setText(model.getD().getGoods().getGoods_stock());
                //收藏
                mBoxCollect.setChecked(TextUtils.equals("Y", model.getD().getIs_collect()));
                //%
                if (model.getD().getGrade().getTotal() != 0) {
                    int pecent = model.getD().getGrade().getGood() * 100 / model.getD().getGrade().getTotal();
                    mTvCommentPecent.setText(pecent + "%");
                } else {
                    mTvCommentPecent.setText("");
                }
                //评论
                if (model.getD().getComment() == null || model.getD().getComment().getUser_name() == null) {
                    mLLCommentDetails.setVisibility(View.GONE);
                    mLLComment.setVisibility(View.GONE);
                    mViewCommLine.setVisibility(View.GONE);
                    mLLCommmentEmpty.setVisibility(View.VISIBLE);
                } else {
                    mTvCommment.setText("评论（" + model.getD().getGrade().getTotal() + "）");
                    mIvUserIco.setImageURI(Uri.parse(HnUrl.setImageUrl(model.getD().getComment().getUser_avatar())));
                    mTvUserName.setText(model.getD().getComment().getUser_name());
                    mIvStart.setCountSelected(model.getD().getComment().getGrade());
                    mTvEva.setText(model.getD().getComment().getContent());
                    mTvGoodsMsg.setText(HnDateUtils.dateFormat(model.getD().getComment().getCreate_time(), "yyyy-MM-dd") + "  " + model.getD().getComment().getAttr());
                    mLLCommmentEmpty.setVisibility(View.GONE);
                }
                //
                List<String> imgs = model.getD().getComment().getImg();
                if (imgs == null || imgs.size() < 1) {
                    mIvEvaIco1.setVisibility(View.GONE);
                    mIvEvaIco2.setVisibility(View.GONE);
                    mIvEvaIco3.setVisibility(View.GONE);
                } else {
                    for (int i = 0; i < imgs.size(); i++) {
                        if (i == 0) {
                            mIvEvaIco1.setVisibility(View.VISIBLE);
                            mIvEvaIco1.setImageURI(HnUrl.setImageUri(imgs.get(0)));
                        } else if (i == 1) {
                            mIvEvaIco2.setVisibility(View.VISIBLE);
                            mIvEvaIco2.setImageURI(HnUrl.setImageUri(imgs.get(1)));
                        } else if (i == 2) {
                            mIvEvaIco3.setVisibility(View.VISIBLE);
                            mIvEvaIco3.setImageURI(HnUrl.setImageUri(imgs.get(2)));
                        }
                    }
                }
                //下架商品
                if (TextUtils.equals("0", model.getD().getGoods().getGoods_state()) || TextUtils.equals("0", model.getD().getDialogue().getStatus())) { // 商品状态 0下架，1正常
                    mTvShopCar.setEnabled(false);
                    mTvBug.setEnabled(false);
                    mTvShopCar.setBackgroundColor(getResources().getColor(R.color.color_999999_tran));
                    mTvBug.setBackgroundColor(getResources().getColor(R.color.color_999999_tran));
                } else {
                    mTvShopCar.setEnabled(true);
                    mTvBug.setEnabled(true);
                    mTvShopCar.setBackgroundColor(getResources().getColor(R.color.main_color));
                    mTvBug.setBackgroundColor(getResources().getColor(R.color.goods_details_3));
                }
                //服务
                if (model.getD().getGoods().getGoods_promise().size() >= 1) {
                    mTvServer1.setVisibility(View.VISIBLE);
                    mTvServer1.setText(model.getD().getGoods().getGoods_promise().get(0));
                } else {
                    mLLServer.setVisibility(View.GONE);
                }
                if (model.getD().getGoods().getGoods_promise().size() >= 2) {
                    mTvServer2.setVisibility(View.VISIBLE);
                    mTvServer2.setText(model.getD().getGoods().getGoods_promise().get(1));
                }
                if (model.getD().getGoods().getGoods_promise().size() >= 3) {
                    mTvServer3.setVisibility(View.VISIBLE);
                    mTvServer3.setText(model.getD().getGoods().getGoods_promise().get(2));
                }
//                mWebView.loadDataWithBaseURL(null, model.getD().getGoods().getGoods_detail(),
//                        "text/html", "utf-8", null);
                mWebView.loadUrl(model.getD().getDetails_share());
                WebSettings webSettings = mWebView.getSettings();
                webSettings.setJavaScriptEnabled(true);
                mWebView.addJavascriptInterface(new JsInteration(), "android");
            }

            @Override
            public void hnErr(int errCode, String msg) {
                HnToastUtils.showToastShort(msg);
            }
        });
    }


    @Override
    protected void onResume() {
        super.onResume();
        requestGetCarNum();
    }

    //获取购物车数量
    private void requestGetCarNum() {
        HnHttpUtils.postRequest(HnUrl.GOODS_CAR_NUM, null, "购物车数量", new HnResponseHandler<GoodsCarNumModel>(GoodsCarNumModel.class) {
            @Override
            public void hnSuccess(String response) {
                if (isFinishing()) return;
                if (model.getD() != null && model.getD().getNum() != 0) {
                    if (mIvAddGoodsNew != null) mIvAddGoodsNew.setVisibility(View.VISIBLE);
                } else {
                    if (mIvAddGoodsNew != null) mIvAddGoodsNew.setVisibility(View.GONE);
                }
            }

            @Override
            public void hnErr(int errCode, String msg) {
                if (mIvAddGoodsNew != null) mIvAddGoodsNew.setVisibility(View.GONE);
            }
        });
    }

    public class JsInteration {
        @JavascriptInterface
        public void openGoods(String goods_id) {
//            HnToastUtils.showToastShort(goods_id);
            ShopActFacade.openGoodsDetails(goods_id);
        }
    }

    private void initPlayer() {
        NiceVideoPlayerController controller = new NiceVideoPlayerController(this);
        controller.setTitle("");
        video_player.setController(controller);
    }
    private void playVideo(String coverUrl, String videoUrl) {
        if (TextUtils.isEmpty(videoUrl) || TextUtils.isEmpty(coverUrl)) return;
        video_player.getmController().setImage(coverUrl);
        video_player.setUp(videoUrl, null);
        video_player.start();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
//        EventBus.getDefault().unregister(this);
//        video_player.release();

        if (timerTask != null) {
            timerTask.cancel();
            timerTask = null;
        }
        if (timer != null) {
            timer.cancel();
            timer = null;
        }

        banner.destroy();

    }

    private void autoBanner(){
        mViewPager.setOffscreenPageLimit(0);
        mAdapter = new BannerViewAdapter(this,list);
        mViewPager.setAdapter(mAdapter);
//        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
//            @Override
//            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//
//            }
//
//            @Override
//            public void onPageSelected(int position) {
//                autoCurrIndex = position;//动态设定轮播图每一页的停留时间
//                period = list.get(position).getPlayTime();
//                if (timer != null) {//每次改变都需要重新创建定时器
//                    timer.cancel();
//                    timer = null;
//                    timer = new Timer();
//                    if (timerTask != null) {
//                        timerTask.cancel();
//                        timerTask = null;
//                        createTimerTask();
//                    }
//                    timer.schedule(timerTask, period, period);
//                }
//
//                if(position !=0){
//                    Message message = new Message();
//                    message.what = 2;
////                    myHandler.sendMessage(message);
//                }
//
//
//            }
//
//            @Override
//            public void onPageScrollStateChanged(int state) {
//
//            }
//        });


        createTimerTask();//创建定时器

        timer = new Timer();
        timer.schedule(timerTask, 500000, period);

    }


    public void createTimerTask(){
        timerTask = new TimerTask() {
            @Override
            public void run() {
                Message message = new Message();
                message.what = UPTATE_VIEWPAGER;
                if (autoCurrIndex == list.size() - 1) {
                    autoCurrIndex = -1;
                }
                message.arg1 = autoCurrIndex + 1;
                mHandler.sendMessage(message);
            }
        };
    }


    protected void onPause() {
        super.onPause();
        banner.onpause();
    }


}
