package com.live.shoplib.ui.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hn.library.base.baselist.BaseViewHolder;
import com.hn.library.base.baselist.CommRecyclerAdapter;
import com.hn.library.global.HnUrl;
import com.hn.library.http.BaseResponseModel;
import com.hn.library.http.HnHttpUtils;
import com.hn.library.http.HnResponseHandler;
import com.hn.library.refresh.PtrClassicFrameLayout;
import com.hn.library.refresh.PtrDefaultHandler2;
import com.hn.library.refresh.PtrFrameLayout;
import com.hn.library.utils.HnRefreshDirection;
import com.hn.library.utils.HnToastUtils;
import com.hn.library.utils.StringUtils;
import com.hn.library.view.FrescoImageView;
import com.hn.library.view.HnEditText;
import com.jakewharton.rxbinding2.view.RxView;
import com.live.shoplib.R;
import com.live.shoplib.bean.ShopLiveSearchModel;
import com.loopj.android.http.RequestParams;
import com.tencent.openqq.protocol.imsdk.msg;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.functions.Consumer;


/**
 * 主播端-直播间-商品搜索
 * 作者：Mr.X
 * 时间：10:48
 */
public class ShopSearchDialog extends DialogFragment {

    private static ShopSearchDialog dialog;
    private Activity mAct;
    private HnEditText mEdSearch;
    private TextView mTvSearch;
    private LinearLayout mLLEmpty;
    private View mEmptyView;
    private PtrClassicFrameLayout mRefresh;
    private RecyclerView mRecycler;
    private int mPage = 1;
    private List<ShopLiveSearchModel.DEntity.GoodsEntity.ItemsEntity> mData = new ArrayList<>();
    private CommRecyclerAdapter mAdapter;

    public static ShopSearchDialog newInstance() {
        dialog = new ShopSearchDialog();
        return dialog;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAct = getActivity();
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View view = View.inflate(mAct, R.layout.dialog_shop_search, null);
        mEdSearch = view.findViewById(R.id.mEdSearch);
        mTvSearch = view.findViewById(R.id.mTvSearch);
        mLLEmpty = view.findViewById(R.id.mLLEmpty);
        mRefresh = view.findViewById(R.id.mRefresh);
        mRecycler = view.findViewById(R.id.mRecycler);
        mEmptyView = view.findViewById(R.id.mEmptyView);
        initRecycler();
        initData();
        initEvent();
        Dialog dialog = new Dialog(mAct, R.style.PXDialog);
        dialog.setContentView(view);
        Window window = dialog.getWindow();
        window.setGravity(Gravity.BOTTOM);
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = (mAct.getWindowManager().getDefaultDisplay().getWidth());
        params.height = (mAct.getWindowManager().getDefaultDisplay().getHeight());
        window.setAttributes(params);
        return dialog;
    }

    private void initEvent() {
        mEmptyView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        mEdSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    ((InputMethodManager) mEdSearch.getContext().getSystemService(Context.INPUT_METHOD_SERVICE))
                            .hideSoftInputFromWindow(mAct.getCurrentFocus().getWindowToken(),
                                    InputMethodManager.HIDE_NOT_ALWAYS);
                    String search = mEdSearch.getText().toString().trim();
                    requestData(search, HnRefreshDirection.TOP);
                    return true;
                }
                return false;
            }
        });
        mTvSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String search = mEdSearch.getText().toString().trim();
                if (TextUtils.isEmpty(search)) {
                    HnToastUtils.showToastShort("请输入搜索内容");
                } else {
                    mPage = 1;
                    requestData(search, HnRefreshDirection.TOP);
                }
            }
        });
        mRefresh.disableWhenHorizontalMove(true);
        mRefresh.setPtrHandler(new PtrDefaultHandler2() {
            @Override
            public void onLoadMoreBegin(PtrFrameLayout frame) {
                mPage = mPage + 1;
                requestData("", HnRefreshDirection.BOTH);
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                mPage = 1;
                requestData("", HnRefreshDirection.TOP);
            }
        });

    }

    private void initRecycler() {
        mRecycler.setLayoutManager(new LinearLayoutManager(mAct));
        mRecycler.setHasFixedSize(true);
        mRecycler.setAdapter(mAdapter = new CommRecyclerAdapter() {
            @Override
            protected void onBindView(BaseViewHolder holder, final int position) {
                //商品图
                ((FrescoImageView) holder.getView(R.id.mIvIco)).setImageURI(Uri.parse(HnUrl.setImageUrl(mData.get(position).getGoods_img())));
                //商品名
                holder.setTextViewText(R.id.mTvGoodsName, mData.get(position).getGoods_name());
                //价格
                StringUtils.setNum(holder.getView(R.id.mTvPrice), mData.get(position).getGoods_price());
                TextView mTvGoodsDown = holder.getView(R.id.mTvGoodsDown);
                //下架
                RxView.clicks(mTvGoodsDown)
                        .throttleFirst(5, TimeUnit.SECONDS)
                        .subscribe(new Consumer<Object>() {
                            @Override
                            public void accept(Object o) throws Exception {
                                HnToastUtils.showToastShort("下架");
                                requestSoldOut(mData.get(position).getGoods_id(), position);
                            }
                        });
                //推荐
                RxView.clicks(holder.getView(R.id.mTvGoodsUp))
                        .throttleFirst(2, TimeUnit.SECONDS)
                        .subscribe(new Consumer<Object>() {
                            @Override
                            public void accept(Object o) throws Exception {
                                requestRecommend(mData.get(position).getGoods_id());
                            }
                        });

                if (TextUtils.equals(mData.get(position).getGoods_live_recommend(), "0")) {
                    mTvGoodsDown.setVisibility(View.VISIBLE);
                } else {
                    mTvGoodsDown.setVisibility(View.GONE);
                }

            }

            @Override
            protected int getLayoutID(int position) {
                return R.layout.item_goods_search;
            }

            @Override
            public int getItemCount() {
                return mData.size();
            }
        });

    }


    private void initData() {
        mPage = 1;
        mData.clear();
        mEdSearch.setText("");
        mLLEmpty.setVisibility(View.VISIBLE);
        requestData("", HnRefreshDirection.BOTH);
    }

    /**
     * 搜索商品
     */
    private void requestData(String sKey, final HnRefreshDirection state) {
        RequestParams param = new RequestParams();
        if (!TextUtils.isEmpty(sKey)) param.put("sKey", sKey);
        param.put("page", mPage);
        param.put("pageSize", "10");
        HnHttpUtils.postRequest(HnUrl.SEACH_ANCHOR_GOODS, param, "主播搜索", new HnResponseHandler<ShopLiveSearchModel>(ShopLiveSearchModel.class) {
            @Override
            public void hnSuccess(String response) {
                if (mAct == null) return;
                refreshFinish();
                if (model.getD().getGoods() == null) {
                    setEmpty();
                    return;
                }
                if (HnRefreshDirection.TOP == state) {
                    mData.clear();
                }
                mData.addAll(model.getD().getGoods().getItems());
                if (mAdapter != null)
                    mAdapter.notifyDataSetChanged();
                if (mData.size() == 0) {
                    setEmpty();
                } else {
                    mLLEmpty.setVisibility(View.GONE);
                }
            }


            @Override
            public void hnErr(int errCode, String msg) {
                HnToastUtils.showToastShort(msg);
                setEmpty();
            }
        });
    }

    /**
     * 商品推荐
     */
    private void requestRecommend(String goods_id) {
        RequestParams param = new RequestParams();
        param.put("goods_id", goods_id);
        HnHttpUtils.postRequest(HnUrl.GOODS_RECOMMEND, param, "推荐商品", new HnResponseHandler<BaseResponseModel>(BaseResponseModel.class) {
            @Override
            public void hnSuccess(String response) {
                dismiss();
            }

            @Override
            public void hnErr(int errCode, String msg) {
                HnToastUtils.showToastShort(msg);
            }
        });
    }

    /**
     * 商品下架
     */
    private void requestSoldOut(String goods_id, final int pos) {
        RequestParams param = new RequestParams();
        param.put("value", "0");//0下架 1上架
        param.put("goods_id", goods_id);
        HnHttpUtils.postRequest(HnUrl.GOODS_STATE, param, "商品状态", new HnResponseHandler<ShopLiveSearchModel>(ShopLiveSearchModel.class) {
            @Override
            public void hnSuccess(String response) {
                mData.remove(pos);
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void hnErr(int errCode, String msg) {
                HnToastUtils.showToastShort(msg);
            }
        });
    }

    private void setEmpty() {
        mLLEmpty.setVisibility(View.VISIBLE);
    }

    protected void refreshFinish() {
        if (mRefresh != null) mRefresh.refreshComplete();
    }

}
