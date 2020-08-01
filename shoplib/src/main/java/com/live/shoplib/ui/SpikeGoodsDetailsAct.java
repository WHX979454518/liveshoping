package com.live.shoplib.ui;

import android.content.Intent;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.hn.library.banner.HnBannerLayout;
import com.hn.library.base.BaseActivity;
import com.hn.library.global.HnUrl;
import com.hn.library.global.NetConstant;
import com.hn.library.http.HnHttpUtils;
import com.hn.library.http.HnResponseHandler;
import com.hn.library.utils.CommonUtils;
import com.hn.library.utils.HnDateUtils;
import com.hn.library.utils.HnPrefUtils;
import com.hn.library.utils.HnToastUtils;
import com.hn.library.utils.ShareManager;
import com.hn.library.view.ColorCountDownText;
import com.hn.library.view.FrescoImageView;
import com.live.shoplib.R;
import com.live.shoplib.ShopActFacade;
import com.live.shoplib.ShopDialogFacade;
import com.live.shoplib.ShopRequest;
import com.live.shoplib.bean.GoodsCommitModel;
import com.live.shoplib.bean.SpikeGoodsDetailsModel;
import com.live.shoplib.bean.SpikeTransationBean;
import com.live.shoplib.ui.dialog.GoodsSelDialog;
import com.live.shoplib.ui.dialog.ShareDialog;
import com.live.shoplib.widget.DragLayout;
import com.live.shoplib.widget.StartRatingBar;
import com.loopj.android.http.RequestParams;

import java.util.ArrayList;
import java.util.List;


/**
 * 商品详情
 * 作者：Mr.Xu
 * 时间：2017/12/19
 */
@Route(path = "/shoplib/SpikeGoodsDetailsAct")
public class SpikeGoodsDetailsAct extends BaseActivity {

    private RelativeLayout mTitle;
    private ImageView mIvBack;
    private TextView mTvTitle;
    private ImageView mIvBarShopCar;
    private ImageView mIvAddGoodsNew;
    private ImageView mIvBarShare;
    private HnBannerLayout mBanner;
    private TextView spike_goods_stock;
    private TextView spike_goods_old_price;
    private ColorCountDownText spike_countdown_text;
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
    private TextView spike_goods_price;
    private TextView mTvServer1, mTvServer2, mTvServer3;
    private WebView mWebView;
    private View mViewCommLine;
    private DragLayout mDragLayout;
    private SpikeGoodsDetailsModel.DEntity bean;
    private String goods_id;

    private String num, sid, stxt;

    private boolean hint;
    private ShareManager manager;
    private ShareDialog dialog;

    @Override
    public int getContentViewId() {
        return R.layout.act_spike_goods_details;
    }

    @Override
    public void onCreateNew(Bundle savedInstanceState) {
        setShowTitleBar(false);
        initId();
        mDragLayout.setOnViewChangedListener(new DragLayout.OnViewChangedListener() {
            @Override
            public void onViewChanged(boolean showTop) {
//                if (showTop) {
//                    mDragTipView.setText(getString(R.string.up_to_detail));
//                } else {
//                    mDragTipView.setText(getString(R.string.down_to_detail));
//                }
            }
        });
        DragLayout.initWebView(mWebView, null);
        manager = new ShareManager(this);
    }


    private void initId() {
        mTitle = (RelativeLayout) findViewById(R.id.mTitle);
        mIvAddGoodsNew = findViewById(R.id.mIvAddGoodsNew);
        mIvBack = (ImageView) findViewById(R.id.mIvBack);
        mTvTitle = (TextView) findViewById(R.id.mTvTitle);
        mIvBarShopCar = (ImageView) findViewById(R.id.mIvBarShopCar);
        mIvBarShare = (ImageView) findViewById(R.id.mIvBarShare);
        mBanner = (HnBannerLayout) findViewById(R.id.mBanner);
        spike_goods_price = (TextView) findViewById(R.id.spike_goods_price);
        spike_goods_stock = (TextView) findViewById(R.id.spike_goods_stock);
        spike_goods_old_price = (TextView) findViewById(R.id.spike_goods_old_price);
        spike_goods_old_price.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        spike_countdown_text = findViewById(R.id.spike_countdown_text);
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
        //秒杀商品没有加入购物车的操作-->屏蔽加入购物城的按钮
        mTvShopCar.setVisibility(View.GONE);
        getDetailsData(goods_id);
    }

    public void onGoodsDetailsClick(View v) {
        if (v == mTvBug) {
            ShopDialogFacade.showSpikeGoodsSel(bean, getFragmentManager(), "购买", sureClick);
        } else if (v == mTvShopCar) {
//            ShopDialogFacade.showSpikeGoodsSel(bean, getFragmentManager(), "加入", sureClick);
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
//            ShopDialogFacade.showGoodsAttrPro(getFragmentManager(), bean, "服务说明");
        } else if (v == mLLGoodsSize) {//规格
            //秒杀不能选规格
//            ShopDialogFacade.showSpikeGoodsSel(bean, getFragmentManager(), "", sureClick);
        } else if (v == mLLGoodsAttr) {//参数
            if (bean == null) return;
            ShopDialogFacade.showSpikeGoodsAttrPro(getFragmentManager(), bean, "产品参数");
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
            if (!CommonUtils.hasItem(bean.getSku()) || bean.getSku().get(0) == null){
                HnToastUtils.showToastShort("数据信息不完整");
                return;
            }
            SpikeTransationBean spikeTransationBean = new SpikeTransationBean();
            spikeTransationBean.setSku_id(bean.getSku().get(0).getSku_id());
            spikeTransationBean.setSpec_sku(bean.getSku().get(0).getSpec_text());
            spikeTransationBean.setSpec_ids(bean.getSku().get(0).getSpec_ids());

            spikeTransationBean.setNum(num);
            spikeTransationBean.setSec_goods_id(sid);
            spikeTransationBean.setGoods_id(bean.getGoods().getGoods_id());
            spikeTransationBean.setSec_price(bean.getSeckill_info().getSec_price());
            spikeTransationBean.setStore_id(bean.getGoods().getStore_id());

            GoodsCommitModel.DEntity.OrderDetailsEntity orderDetailsEntity = new GoodsCommitModel.DEntity.OrderDetailsEntity();
            orderDetailsEntity.setTotal_price(Float.valueOf(bean.getSeckill_info().getSec_price()) * Integer.parseInt(num));
            orderDetailsEntity.setShop_fee(bean.getGoods().getShop_fee());
            orderDetailsEntity.setStore_id(bean.getGoods().getStore_id());
            orderDetailsEntity.setStore_name(bean.getDialogue().getName());
            orderDetailsEntity.setStore_icon(bean.getDialogue().getIcon());

            List<GoodsCommitModel.DEntity.OrderDetailsEntity.ListEntity> list = new ArrayList<>();
            GoodsCommitModel.DEntity.OrderDetailsEntity.ListEntity listEntity = new GoodsCommitModel.DEntity.OrderDetailsEntity.ListEntity();
            listEntity.setGoods_id(bean.getGoods().getGoods_id());
            listEntity.setGoods_img(bean.getGoods().getGoods_img());
            listEntity.setGoods_name(bean.getGoods().getGoods_name());
            listEntity.setNum(num);
            if (CommonUtils.hasItem(bean.getSku())){
                listEntity.setSpec_sku(bean.getSku().get(0).getSpec_text());
            }
            listEntity.setPrice(bean.getSeckill_info().getSec_price());
            list.add(listEntity);
            orderDetailsEntity.setList(list);

            spikeTransationBean.setOrder_details(orderDetailsEntity);
            ShopActFacade.openSpikeGoodsCommit(spikeTransationBean);
        }
    };




    //商品详情
    public void getDetailsData(String goods_id) {
        RequestParams param = new RequestParams();
        param.put("sec_goods_id", goods_id);
        HnHttpUtils.postRequest(HnUrl.SPIKE_GOODS_DETAILs, param, "秒杀商品详情", new HnResponseHandler<SpikeGoodsDetailsModel>(SpikeGoodsDetailsModel.class) {
            @Override
            public void hnSuccess(String response) {
                if (model.getD() == null || isFinishing()) return;
                bean = model.getD();
                //轮播图
                mBanner.setViewUrls(model.getD().getGoods().getGoods_imgs());

                //商品价格
                spike_goods_price.setText("¥" + model.getD().getSeckill_info().getSec_price());
                spike_goods_old_price.setText("¥" + model.getD().getGoods().getGoods_price());
                spike_goods_stock.setText("剩余" + model.getD().getSeckill_info().getSec_goods_stock() + "件");
                spike_countdown_text.setCountTime(Long.valueOf(model.getD().getSeckill_info().getEnd_time())
                        - HnDateUtils.getNowTimeStamp());
                if (Integer.parseInt(model.getD().getSeckill_info().getSec_goods_stock()) <= 0) {
                    spike_goods_price.setText("已售罄");
                }

                //运费
                if (TextUtils.equals(model.getD().getGoods().getShop_fee(), "0")) {
                    mTvGoodsFreight.setText("包邮");
                } else {
                    mTvGoodsFreight.setText("运费：¥" + model.getD().getGoods().getShop_fee());
                }
                //销量
                mTvGoodsSell.setText(model.getD().getGoods().getGoods_sales());
                //库存
                mTvGoodsStore.setText(model.getD().getSeckill_info().getSec_goods_stock());
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
                //下架商品或者售罄
                if (TextUtils.equals("0", model.getD().getGoods().getGoods_state()) || TextUtils.equals("0", model.getD().getDialogue().getStatus())||TextUtils.equals("0", model.getD().getSeckill_info().getSec_goods_stock())) { // 商品状态 0下架，1正常
                    mTvShopCar.setEnabled(false);
                    mTvBug.setEnabled(false);
                    mTvShopCar.setBackgroundColor(getResources().getColor(R.color.color_999999_tran));
                    mTvBug.setBackgroundColor(getResources().getColor(R.color.color_999999_tran));
                } else {
                    mTvShopCar.setEnabled(true);
                    mTvBug.setEnabled(true);
                    mTvShopCar.setBackgroundColor(getResources().getColor(R.color.main_color));
                    mTvBug.setBackgroundColor(getResources().getColor(R.color.main_color));
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


    public class JsInteration {
        @JavascriptInterface
        public void openGoods(String goods_id) {
//            HnToastUtils.showToastShort(goods_id);
            ShopActFacade.openGoodsDetails(goods_id);
        }
    }

}
