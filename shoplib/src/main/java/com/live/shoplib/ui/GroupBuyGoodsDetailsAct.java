package com.live.shoplib.ui;

import android.annotation.SuppressLint;
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
import com.hn.library.view.FrescoImageView;
import com.live.shoplib.R;
import com.live.shoplib.ShopActFacade;
import com.live.shoplib.ShopDialogFacade;
import com.live.shoplib.ShopRequest;
import com.live.shoplib.bean.AddCarModel;
import com.live.shoplib.bean.GoodsCarNumModel;
import com.live.shoplib.bean.GoodsCommitModel;
import com.live.shoplib.bean.GoodsDetailsModel;
import com.live.shoplib.bean.GroupBuyTransationBean;
import com.live.shoplib.bean.NeedToFinishGroupModel;
import com.live.shoplib.bean.ShoppingCarRefreshEvent;
import com.live.shoplib.bean.SpikeGoodsDetailsModel;
import com.live.shoplib.ui.dialog.GoodsSelDialog;
import com.live.shoplib.ui.dialog.JoinGroupBuyGoodsSelDialog;
import com.live.shoplib.ui.dialog.ShareDialog;
import com.live.shoplib.utils.CountDownView;
import com.live.shoplib.widget.DragLayout;
import com.live.shoplib.widget.StartRatingBar;
import com.loopj.android.http.RequestParams;

import org.greenrobot.eventbus.EventBus;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * 商品详情
 * 作者：Mr.Xu
 * 时间：2017/12/19
 */
@Route(path = "/shoplib/GroupBuyGoodsDetailsAct")
public class GroupBuyGoodsDetailsAct extends BaseActivity {

    private RelativeLayout mTitle;
    private ImageView mIvBack;
    private TextView mTvTitle;
    private ImageView mIvBarShopCar;
    private ImageView mIvAddGoodsNew;
    private ImageView mIvBarShare;
    private HnBannerLayout mBanner;

    private TextView mTvPrice;
    private TextView group_buy_old_price;
    private TextView group_buy_person_number;
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
    private LinearLayout mLLGoodsGroupBuyInfo;
    private TextView mTvBuySelf;
    private TextView start_group_info;
    private TextView start_group_name;
    private TextView start_group_btn;
    private FrescoImageView start_group_head;
    private TextView mTvBuyGroup;
    private TextView mTvServer1, mTvServer2, mTvServer3;
    private WebView mWebView;
    private View mViewCommLine;
    private DragLayout mDragLayout;
    private SpikeGoodsDetailsModel.DEntity bean;
    private GoodsDetailsModel.DEntity normalBean;
    private String goods_id;
    private String group_buy_id;

    private String num, sid, stxt;

    private boolean hint;
    private ShareManager manager;
    private ShareDialog dialog;


    private CountDownView countDown;

    @Override
    public int getContentViewId() {
        return R.layout.act_group_buy_goods_details;
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

        mTvPrice = (TextView) findViewById(R.id.mTvPrice);
        group_buy_old_price = (TextView) findViewById(R.id.group_buy_old_price);
        group_buy_old_price.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        group_buy_person_number = (TextView) findViewById(R.id.group_buy_person_number);
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
        mLLGoodsGroupBuyInfo = findViewById(R.id.mLLGoodsGroupBuyInfo);
        mTvBuySelf = (TextView) findViewById(R.id.mTvShopCar);
        start_group_info = (TextView) findViewById(R.id.start_group_info);
        start_group_name = (TextView) findViewById(R.id.start_group_name);
        start_group_btn = (TextView) findViewById(R.id.start_group_btn);
        start_group_head = findViewById(R.id.start_group_head);
        mTvBuyGroup = (TextView) findViewById(R.id.mTvBug);
        mWebView = (WebView) findViewById(R.id.mWebView);
        mDragLayout = (DragLayout) findViewById(R.id.mDragLayout);
        mTvServer1 = (TextView) findViewById(R.id.mTvServer1);
        mTvServer2 = (TextView) findViewById(R.id.mTvServer2);
        mTvServer3 = (TextView) findViewById(R.id.mTvServer3);
        mViewCommLine = findViewById(R.id.mViewCommLine);
        tag = findViewById(R.id.tag);


        countDown = findViewById(R.id.count_down);
    }

    @Override
    public void getInitData() {
        initIntentData();
        if (TextUtils.isEmpty(goods_id) || TextUtils.isEmpty(group_buy_id)) {
            finish();
            return;
        }

        if (hint) tag.setVisibility(View.GONE);
        //  团购里面没有点赞的功能-->隐藏点赞按钮
        mBoxCollect.setVisibility(View.GONE);
        getDetailsData(group_buy_id);
        getNormalDetailsData(goods_id);
        getNeedToFinishGroupList();
    }

    private void initIntentData() {
        group_buy_id = getIntent().getStringExtra("group_buy_id");
        goods_id = getIntent().getStringExtra("goods_id");
        hint = getIntent().getBooleanExtra("hint", false);
        //处理从外部网页启动发过来的数据
        Uri uri = getIntent().getData();
        if (uri != null && !TextUtils.isEmpty(uri.getQueryParameter("group_buy_id")) && !TextUtils.isEmpty(uri.getQueryParameter("goods_id"))) {
            group_buy_id = uri.getQueryParameter("group_buy_id");
            goods_id = uri.getQueryParameter("goods_id");
        }
    }

    public void onGoodsDetailsClick(View v) {
        if (v == mTvBuyGroup) {
            ShopDialogFacade.showGroupBuyGoodsSel(bean, getFragmentManager(), "购买", startGroupBuySureClick);
        } else if (v == mTvBuySelf) {
            ShopDialogFacade.showGoodsSel(normalBean, getFragmentManager(), "购买", sureClick);

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
//            ShopDialogFacade.showSpikeGoodsAttrPro(getFragmentManager(), bean, "服务说明");
        } else if (v == mLLGoodsSize) {//规格
//            ShopDialogFacade.showGroupBuyGoodsSel(bean, getFragmentManager(), "", sureClick);
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
            if (TextUtils.equals(go, "购买")) {
                requestAddCar(num, sid, stxt, "fast");
            } else if (TextUtils.equals(go, "加入")) {
                requestAddCar(num, sid, stxt, "normal");
            } else {

            }
            requestGetCarNum();
        }
    };

    GoodsSelDialog.OnSureClick startGroupBuySureClick = new GoodsSelDialog.OnSureClick() {
        @Override
        public void click(String n, String i, String s, String go) {
            num = n;
            sid = i;
            stxt = s;
            mTvGoodsSize.setText(s);
            if (!CommonUtils.hasItem(bean.getSku()) || bean.getSku().get(0) == null) {
                HnToastUtils.showToastShort("数据信息不完整");
                return;
            }
            GroupBuyTransationBean groupBuyTransationBean = new GroupBuyTransationBean();
            groupBuyTransationBean.setGroup_buy_id(sid);
            groupBuyTransationBean.setNum(num);
            groupBuyTransationBean.setStore_id(bean.getGoods().getStore_id());
            groupBuyTransationBean.setGoods_id(bean.getGoods().getGoods_id());
            groupBuyTransationBean.setGroup_price(bean.getGroup_goods().getGroup_price());
            groupBuyTransationBean.setSku_id(bean.getSku().get(0).getSku_id());
            groupBuyTransationBean.setSpec_sku(bean.getSku().get(0).getSpec_text());
            groupBuyTransationBean.setSpec_ids(bean.getSku().get(0).getSpec_ids());

            GoodsCommitModel.DEntity.OrderDetailsEntity orderDetailsEntity = new GoodsCommitModel.DEntity.OrderDetailsEntity();
            orderDetailsEntity.setTotal_price(Float.valueOf(bean.getGroup_goods().getGroup_price()) * Integer.parseInt(num));
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
            listEntity.setPrice(bean.getGroup_goods().getGroup_price());
            if (CommonUtils.hasItem(bean.getSku())){
                listEntity.setSpec_sku(bean.getSku().get(0).getSpec_text());
            }
            list.add(listEntity);
            orderDetailsEntity.setList(list);
            groupBuyTransationBean.setOrder_details(orderDetailsEntity);
            ShopActFacade.openGroupBuyGoodsCommit(groupBuyTransationBean);

        }
    };

    JoinGroupBuyGoodsSelDialog.OnSureClick joinGroupBuySureClick = new JoinGroupBuyGoodsSelDialog.OnSureClick() {
        @Override
        public void click(String n, String i, String s, String go,String groupBuyOrderId) {
            num = n;
            sid = i;
            stxt = s;
            mTvGoodsSize.setText(s);

            GroupBuyTransationBean groupBuyTransationBean = new GroupBuyTransationBean();
            groupBuyTransationBean.setGroup_buy_id(sid);
            groupBuyTransationBean.setGroup_order_id(groupBuyOrderId);
            groupBuyTransationBean.setNum(num);
            groupBuyTransationBean.setStore_id(bean.getGoods().getStore_id());
            groupBuyTransationBean.setGoods_id(bean.getGoods().getGoods_id());
            groupBuyTransationBean.setGroup_price(bean.getGroup_goods().getGroup_price());
            groupBuyTransationBean.setSku_id(bean.getSku().get(0).getSku_id());
            groupBuyTransationBean.setSpec_sku(bean.getSku().get(0).getSpec_text());
            groupBuyTransationBean.setSpec_ids(bean.getSku().get(0).getSpec_ids());

            GoodsCommitModel.DEntity.OrderDetailsEntity orderDetailsEntity = new GoodsCommitModel.DEntity.OrderDetailsEntity();
            orderDetailsEntity.setTotal_price(Float.valueOf(bean.getGroup_goods().getGroup_price()) * Integer.parseInt(num));
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
            listEntity.setPrice(bean.getGroup_goods().getGroup_price());
            list.add(listEntity);
            orderDetailsEntity.setList(list);
            groupBuyTransationBean.setOrder_details(orderDetailsEntity);
            ShopActFacade.openGroupBuyGoodsCommit(groupBuyTransationBean);

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
    public void getDetailsData(String group_buy_id) {
        RequestParams param = new RequestParams();
        param.put("group_buy_id", group_buy_id);
        HnHttpUtils.postRequest(HnUrl.GROUP_BUY_GOODS_DETAILs, param, "商品详情", new HnResponseHandler<SpikeGoodsDetailsModel>(SpikeGoodsDetailsModel.class) {
            @Override
            public void hnSuccess(String response) {
                if (model.getD() == null || isFinishing()) return;
                bean = model.getD();
                //轮播图
                mBanner.setViewUrls(model.getD().getGoods().getGoods_imgs());

                //商品价格
                mTvPrice.setText("¥" + model.getD().getGroup_goods().getGroup_price());
                group_buy_old_price.setText("单独购买：￥" + model.getD().getGoods().getGoods_price());
                group_buy_person_number.setText(model.getD().getGroup_goods().getGroup_condition() + "人团");

                //运费
                if (TextUtils.equals(model.getD().getGoods().getShop_fee(), "0")) {
                    mTvGoodsFreight.setText("包邮");
                } else {
                    mTvGoodsFreight.setText("运费：¥" + model.getD().getGoods().getShop_fee());
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
                //下架商品或者库存为0
                if (TextUtils.equals("0", model.getD().getGoods().getGoods_state()) || TextUtils.equals("0", model.getD().getDialogue().getStatus()) || TextUtils.equals("0", model.getD().getGroup_goods().getGroup_goods_stock())) { // 商品状态 0下架，1正常
                    mTvBuySelf.setEnabled(false);
                    mTvBuyGroup.setEnabled(false);
                    mTvBuySelf.setBackgroundColor(getResources().getColor(R.color.color_999999_tran));
                    mTvBuyGroup.setBackgroundColor(getResources().getColor(R.color.color_999999_tran));
                } else {
                    mTvBuySelf.setEnabled(true);
                    mTvBuyGroup.setEnabled(true);
                    mTvBuySelf.setBackgroundColor(getResources().getColor(R.color.goods_details_6));
                    mTvBuyGroup.setBackgroundColor(getResources().getColor(R.color.main_color));
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

                //将webView加载数据部分放到商品详情老接口加载
//                mWebView.loadDataWithBaseURL(null, model.getD().getGoods().getGoods_detail(),
//                        "text/html", "utf-8", null);
                mWebView.loadUrl(model.getD().getDetails_share());
                WebSettings webSettings = mWebView.getSettings();
                webSettings.setJavaScriptEnabled(true);
                mWebView.addJavascriptInterface(new JsInteration(), "android");

                //綁定单独购买和开团购买的价格
                mTvBuySelf.setText(String.format(getResources().getString(R.string.buy_self_btn_string), "￥" + model.getD().getGoods().getGoods_price()));
                mTvBuyGroup.setText(String.format(getResources().getString(R.string.buy_group_buy_btn_string), "￥" + model.getD().getGroup_goods().getGroup_price()));
            }

            @Override
            public void hnErr(int errCode, String msg) {
                HnToastUtils.showToastShort(msg);
            }
        });
    }


    //商品详情
    public void getNormalDetailsData(String goods_id) {
        RequestParams param = new RequestParams();
        param.put("goods_id", goods_id);
        HnHttpUtils.postRequest(HnUrl.GOODS_DETAILs, param, "商品详情", new HnResponseHandler<GoodsDetailsModel>(GoodsDetailsModel.class) {
            @Override
            public void hnSuccess(String response) {
                if (model.getD() == null || isFinishing()) return;
                normalBean = model.getD();


            }

            @Override
            public void hnErr(int errCode, String msg) {
                HnToastUtils.showToastShort(msg);
            }
        });
    }

    //获取待成团列表
    public void getNeedToFinishGroupList() {
        RequestParams param = new RequestParams();
        param.add("group_buy_id",group_buy_id);
        HnHttpUtils.postRequest(HnUrl.NEED_TO_FINISH_GROUP_LIST, param, "获取待成团列表", new HnResponseHandler<NeedToFinishGroupModel>(NeedToFinishGroupModel.class) {
            @Override
            public void hnSuccess(String response) {
                if (model.getD() == null || model.getD().getGroup_order_ids() == null || isFinishing()){
                    return;
                }
                bindNeedToFinishGroupData(model.getD().getGroup_order_ids().get(0));
            }

            @Override
            public void hnErr(int errCode, String msg) {
                HnToastUtils.showToastShort(msg);
            }
        });
    }

    @SuppressLint("StringFormatMatches")
    private void bindNeedToFinishGroupData(final NeedToFinishGroupModel.DEntity.GroupListItem item) {
        mLLGoodsGroupBuyInfo.setVisibility(View.VISIBLE);
        start_group_head.setImageURI(Uri.parse(HnUrl.setImageUrl(item.getUser_info().getUser_avatar())));
        start_group_name.setText(item.getUser_info().getUser_nickname());
        start_group_info.setText(String.format(getResources().getString(R.string.need_to_finish_group_tips), String.valueOf(item.getLast_num()), HnDateUtils.getFinishTime(item.getFinish_time())));
        start_group_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                joinGroupBuy(item.getGroup_order_id());
            }
        });

        //倒计时
        countdown(item);

    }


    private void joinGroupBuy(String groupBuyOrderId) {
        ShopDialogFacade.showJoinGroupBuyGoodsSel(bean, getFragmentManager(), "购买",groupBuyOrderId, joinGroupBuySureClick);
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


    public static String formatData(String dataFormat, long timeStamp) {
        if (timeStamp == 0) {
            return "";
        }
        timeStamp = timeStamp;
        String result = "";
        SimpleDateFormat format = new SimpleDateFormat(dataFormat);
        result = format.format(new Date(timeStamp));
        return result;
    }

    public void countdown(final NeedToFinishGroupModel.DEntity.GroupListItem item) {
        // 获取当前时间
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date1 = formatter.format(currentTime);
        // 获取服务器返回的时间戳 转换成"yyyy-MM-dd HH:mm:ss"
        String date2 = formatData("yyyy-MM-dd HH:mm:ss", item.getFinish_time());
        // 计算的时间差
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date d1 = df.parse(date1);
            Date d2 = df.parse(date2);
            long diff = d2.getTime() - d1.getTime();// 这样得到的差值是微秒级别
            change((int) diff/1000);
            countDown.initTime(h, d, s);
            countDown.start();
        } catch (Exception e) {
        }


    }
    int h = 0;
    int d = 0;
    int s = 0;
    public void change(int second) {
        int temp = second % 3600;
        if (second > 3600) {
            h = second / 3600;
            if (temp != 0) {
                if (temp > 60) {
                    d = temp / 60;
                    if (temp % 60 != 0) {
                        s = temp % 60;
                    }
                } else {
                    s = temp;
                }
            }
        } else {
            d = second / 60;
            if (second % 60 != 0) {
                s = second % 60;
            }
        }
    }
}
