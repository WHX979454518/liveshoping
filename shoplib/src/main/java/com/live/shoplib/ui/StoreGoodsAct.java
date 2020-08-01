package com.live.shoplib.ui;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.hn.library.base.baselist.BaseViewHolder;
import com.hn.library.base.baselist.CommRecyclerAdapter;
import com.hn.library.global.HnUrl;
import com.hn.library.http.HnHttpUtils;
import com.hn.library.http.HnResponseHandler;
import com.hn.library.picker.address_picker.StoreGroupModel;
import com.hn.library.picker.address_picker.StringOneDialog;
import com.hn.library.utils.HnRefreshDirection;
import com.hn.library.utils.HnToastUtils;
import com.hn.library.view.CommDialog;
import com.hn.library.view.FrescoImageView;
import com.hn.library.view.HnEditText;
import com.jakewharton.rxbinding2.view.RxView;
import com.live.shoplib.R;
import com.live.shoplib.ShopActFacade;
import com.live.shoplib.ShopRequest;
import com.live.shoplib.bean.GoodsAddModel;
import com.live.shoplib.bean.ShopLiveSearchModel;
import com.live.shoplib.bean.StoreGoodsModel;
import com.loopj.android.http.RequestParams;
import com.reslibrarytwo.CommListActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.functions.Consumer;

/**
 * 店铺商品
 * 作者：Mr.Xu
 * 时间：2018/1/4
 */
@Route(path = "/shoplib/StoreGoodsAct")
public class StoreGoodsAct extends CommListActivity {
    private CommRecyclerAdapter mAdapter;
    private List<StoreGoodsModel.DEntity.ItemsEntity> mData = new ArrayList<>();
    private String sKey;

    private RelativeLayout mRlTitle;
    private TextView mTvSearch;
    private ImageView mIvFloat;
    private HnEditText mEdSearch;


    @Override
    public int getContentViewId() {
        return R.layout.act_store_goods;
    }


    @Override
    protected String setTitle() {
        initView();
        return "店铺商品";
    }

    @Override
    protected void onResume() {
        super.onResume();
        getData(HnRefreshDirection.TOP, page = 1);
    }

    private void initView() {
        setShowTitleBar(false);
        mRlTitle = findViewById(R.id.mRlTitle);
        mTvSearch = findViewById(R.id.mTvSearch);
        mEdSearch = findViewById(R.id.mEdSearch);
        mIvFloat = findViewById(R.id.mIvFloat);
        mIvFloat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GoodsEditAct.Companion.launch(StoreGoodsAct.this, "");
            }
        });
        mEdSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    ((InputMethodManager) mEdSearch.getContext().getSystemService(Context.INPUT_METHOD_SERVICE))
                            .hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                                    InputMethodManager.HIDE_NOT_ALWAYS);
                    sKey = mEdSearch.getText().toString().trim();
                    getData(HnRefreshDirection.TOP, page = 1);
                    return true;
                }
                return false;
            }
        });

        mTvSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    @Override
    protected CommRecyclerAdapter setAdapter() {
        setShowSubTitle(true);
        mSubtitle.setVisibility(View.GONE);
        return mAdapter = new CommRecyclerAdapter() {
            @Override
            protected void onBindView(BaseViewHolder holder, final int position) {

                FrescoImageView mIvGoodsIco = holder.getView(R.id.mIvGoodsIco);
                mIvGoodsIco.setImageURI(Uri.parse(HnUrl.setImageUrl(mData.get(position).getGoods_img())));

                holder.setTextViewText(R.id.mTvGoodsName, mData.get(position).getGoods_name());
                holder.setTextViewText(R.id.mTvPrice, mData.get(position).getGoods_price());

                holder.getView(R.id.mTvGoodsNum).setVisibility(View.GONE);
                holder.getView(R.id.mTvGoodsMsg).setVisibility(View.GONE);
                holder.getView(R.id.mTvGoodsRefend).setVisibility(View.GONE);
                holder.getView(R.id.mTvGoodsTag).setVisibility(View.GONE);
                holder.getView(R.id.mTvTag).setVisibility(View.GONE);

                TextView mTvEdit = holder.getView(R.id.mTvEdit);
                TextView mTvGoodsEdit = holder.getView(R.id.mTvGoodsEdit);
                TextView mTvUpDown = holder.getView(R.id.mTvUpDown);
                RxView.clicks(mTvEdit)
                        .throttleFirst(2, TimeUnit.SECONDS)
                        .subscribe(new Consumer<Object>() {
                            @Override
                            public void accept(Object o) throws Exception {
                                requestList(mData.get(position).getGoods_id());
                            }
                        });
                RxView.clicks(mTvGoodsEdit)
                        .throttleFirst(2, TimeUnit.SECONDS)
                        .subscribe(new Consumer<Object>() {
                            @Override
                            public void accept(Object o) throws Exception {
                                requestDetails(mData.get(position).getGoods_id());
                            }
                        });
                mTvUpDown.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        CommDialog.newInstance(StoreGoodsAct.this)
                                .setContent(TextUtils.equals("1", mData.get(position).getGoods_state()) ? "是否确认下架该商品" : "是否确认上架该商品")
                                .setTitle("提示")
                                .setClickListen(new CommDialog.TwoSelDialog() {
                                    @Override
                                    public void leftClick() {

                                    }

                                    @Override
                                    public void rightClick() {
                                        if (TextUtils.equals("1", mData.get(position).getGoods_state())) {
                                            requestSoldOut(mData.get(position).getGoods_id(), "0", position);
                                        } else {
                                            requestSoldOut(mData.get(position).getGoods_id(), "1", position);
                                        }
                                    }
                                })
                                .show();
                    }
                });


                if (TextUtils.equals("1", mData.get(position).getGoods_state())) {
                    mTvUpDown.setText("下架");
                    mTvUpDown.setBackgroundResource(R.drawable.shape_stoke_red_30);
                    mTvUpDown.setTextColor(getResources().getColor(R.color.comm_text_color_red));
                } else {
                    mTvUpDown.setText("上架");
                    mTvUpDown.setTextColor(getResources().getColor(R.color.main_color));
                    mTvUpDown.setBackgroundResource(R.drawable.shape_stoke_main_30);
                }

                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ShopActFacade.openGoodsDetails(mData.get(position).getGoods_id(), true);
                    }
                });

            }

            @Override
            protected int getLayoutID(int position) {
                return R.layout.item_store_goods;
            }

            @Override
            public int getItemCount() {
                return mData.size();
            }
        };

    }

    /**
     * 商品下架
     */
    private void requestSoldOut(String goods_id, final String value, final int pos) {
        RequestParams param = new RequestParams();
        param.put("value", value);//0下架 1上架
        param.put("goods_id", goods_id);
        HnHttpUtils.postRequest(HnUrl.GOODS_STATE, param, "商品状态", new HnResponseHandler<ShopLiveSearchModel>(ShopLiveSearchModel.class) {
            @Override
            public void hnSuccess(String response) {
                mData.get(pos).setGoods_state(value);
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void hnErr(int errCode, String msg) {
                HnToastUtils.showToastShort(msg);
            }
        });
    }


    private void requestList(final String good_id) {
        RequestParams param = new RequestParams();
        HnHttpUtils.postRequest(HnUrl.STORE_GROUP_LIST, param, "商品栏目列表", new HnResponseHandler<StoreGroupModel>(StoreGroupModel.class) {
            @Override
            public void hnSuccess(String response) {
                StringOneDialog dialog = new StringOneDialog(StoreGoodsAct.this, model.getD().getItems(), new StringOneDialog.onCityPickedListener() {
                    @Override
                    public void onPicked(StoreGroupModel.DBean.ItemsBean bean) {
                        ShopRequest.editStoreGroupGoods(bean.getId(), good_id, new ShopRequest.OnRespondNothing() {
                            @Override
                            public void finishs() {
                                getData(HnRefreshDirection.TOP, page = 1);
                            }
                        });
                    }
                });
                dialog.show();
            }

            @Override
            public void hnErr(int errCode, String msg) {
                HnToastUtils.showToastShort(msg);
            }
        });
    }


    private void requestDetails(final String goods_id) {
        RequestParams param = new RequestParams();
        param.put("goods_id", goods_id);
        HnHttpUtils.postRequest(HnUrl.STORE_GOODS_INFO, param, "商品栏目列表", new HnResponseHandler<GoodsAddModel>(GoodsAddModel.class) {
            @Override
            public void hnSuccess(String response) {
                if (model.getD() != null) {
                    GoodsEditAct.Companion.launch(StoreGoodsAct.this, goods_id, model.getD());
//                    startActivity(new Intent(StoreGoodsAct.this,GoodsEditAct.class).putExtra("store_id",goods_id).putExtra("bean",model));
                }
            }

            @Override
            public void hnErr(int errCode, String msg) {
                HnToastUtils.showToastShort(msg);
            }
        });
    }


    @Override
    protected RequestParams setRequestParam() {
        RequestParams params = new RequestParams();
        if (!TextUtils.isEmpty(sKey)) params.put("sKey", sKey);
        return params;
    }

    @Override
    protected String setRequestUrl() {
        return HnUrl.STORE_GOODS_LIST;
    }

    @Override
    protected HnResponseHandler setResponseHandler(final HnRefreshDirection state) {
        return new HnResponseHandler<StoreGoodsModel>(StoreGoodsModel.class) {
            @Override
            public void hnSuccess(String response) {
                if (isFinishing()) return;
                refreshFinish();
                if (model.getD().getItems() == null) {
                    setEmpty("暂无商品", R.drawable.empty_com);
                    return;
                }
                if (HnRefreshDirection.TOP == state) {
                    mData.clear();
                }
                mData.addAll(model.getD().getItems());
                if (mAdapter != null)
                    mAdapter.notifyDataSetChanged();
                setEmpty("暂无商品", R.drawable.empty_com);
            }

            @Override
            public void hnErr(int errCode, String msg) {
                if (isFinishing()) return;
                refreshFinish();
                HnToastUtils.showToastShort(msg);
                setEmpty("暂无商品", R.drawable.empty_com);
            }
        };
    }
}
