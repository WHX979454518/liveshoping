package com.live.shoplib.ui;

import android.content.Context;
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
import com.hn.library.refresh.PtrDefaultHandler2;
import com.hn.library.refresh.PtrFrameLayout;
import com.hn.library.utils.HnRefreshDirection;
import com.hn.library.utils.HnToastUtils;
import com.hn.library.view.HnEditText;
import com.jakewharton.rxbinding2.view.RxView;
import com.live.shoplib.R;
import com.live.shoplib.ShopActFacade;
import com.live.shoplib.bean.OrderRefreshEvent;
import com.live.shoplib.bean.RefundListModel;
import com.live.shoplib.other.GoodsOrderInject;
import com.loopj.android.http.RequestParams;
import com.reslibrarytwo.CommListActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.functions.Consumer;

/**
 * 售后 - 两端
 * 作者：Mr.Xu
 * 时间：2017/12/18
 */
@Route(path = "/shoplib/RefundGoodsAct")
public class RefundGoodsAct extends CommListActivity {
    private CommRecyclerAdapter mAdapter;
    private List<RefundListModel.DEntity.ItemsEntity> mData = new ArrayList<>();
    private String store_id;
    private ImageView mIvBack;
    private RelativeLayout mRlTitle;
    private HnEditText mEdSearch;
    private String mSearchContent;
    @Override
    protected String setTitle() {
        store_id = getIntent().getStringExtra("store_id");
        return "售后";
    }

    @Override
    public int getContentViewId() {
        return R.layout.refund_list;
    }

    @Override
    protected void initEvent() {
        mSpring.setMode(PtrFrameLayout.Mode.REFRESH);
        mSpring.setPtrHandler(new PtrDefaultHandler2() {
            @Override
            public void onLoadMoreBegin(PtrFrameLayout ptrFrameLayout) {
                page = page + 1;
                mSearchContent = mEdSearch.getText().toString().trim();
                getData2(HnRefreshDirection.BOTH, page,mSearchContent);
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout ptrFrameLayout) {
                page = 1;
                mSearchContent = mEdSearch.getText().toString().trim();
                getData2(HnRefreshDirection.TOP, page,mSearchContent);
            }
        });
    }

    @Override
    protected CommRecyclerAdapter setAdapter() {
        EventBus.getDefault().register(this);
        setShowSubTitle(true);
        setDif();
        mSubtitle.setVisibility(View.GONE);
        mAdapter = new CommRecyclerAdapter() {
            @Override
            protected void onBindView(BaseViewHolder holder, final int position) {
                RxView.clicks(holder.itemView)
                        .throttleFirst(2, TimeUnit.SECONDS)
                        .subscribe(new Consumer<Object>() {
                            @Override
                            public void accept(Object o) throws Exception {
                                ShopActFacade.openRefundDetails(mData.get(position).getId(), store_id);
                            }
                        });
                //日期
                holder.setTextViewText(R.id.mTvDate, mData.get(position).getAdd_time());
                //1买家已申请 2卖家同意 3 等待买家退货 4 自己取消 5 商家拒绝 6 退款成功
                TextView mTvState = holder.getView(R.id.mTvState);
                boolean isSeller = (!TextUtils.isEmpty(store_id) && !TextUtils.equals("0", store_id));
                switch (mData.get(position).getStatus()) {
                    case "1":
                        mTvState.setText(isSeller ? "申请退款" : "等待卖家处理");
                        break;
                    case "2":
                        mTvState.setText(isSeller ? "等待买家退货" : "卖家同意，请退货");
                        break;
                    case "3":
                        mTvState.setText(isSeller ? "确认收货" : "等待卖家退款");
                        break;
                    case "4":
                        mTvState.setText("订单取消");
                        break;
                    case "5":
                        mTvState.setText(isSeller ? "退款关闭" : "退款关闭");
                        break;
                    case "6":
                        mTvState.setText("退款成功");
                        break;
                    case "7":
                        mTvState.setText("退款关闭");
                        break;
                }
                //商品详情
                GoodsOrderInject.setGoodsView(holder.itemView,
                        mData.get(position).getGoods_img(),
                        mData.get(position).getGoods_name(),
                        mData.get(position).getGoods_attr(),
                        mData.get(position).getGoods_price(),
                        mData.get(position).getNum(), -1 + "", true);
                holder.setTextViewText(R.id.mTvStateMsg, "退款金额：¥" + mData.get(position).getOrder_amount());
            }

            @Override
            protected int getLayoutID(int position) {
                return R.layout.item_refund;
            }

            @Override
            public int getItemCount() {
                return mData.size();
            }
        };
        return mAdapter;
    }

    private void setDif() {
        boolean isSeller = (!TextUtils.isEmpty(store_id) && !TextUtils.equals("0", store_id));

        setShowTitleBar(!isSeller);

        mIvBack = findViewById(R.id.mIvBack);
        mIvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mRlTitle = findViewById(R.id.mRlTitle);
        mRlTitle.setVisibility(isSeller ? View.VISIBLE : View.GONE);

        mEdSearch = findViewById(R.id.mEdSearch);
        //点击键盘搜索
        mEdSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    ((InputMethodManager) mEdSearch.getContext().getSystemService(Context.INPUT_METHOD_SERVICE))
                            .hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                                    InputMethodManager.HIDE_NOT_ALWAYS);
                    mSearchContent = mEdSearch.getText().toString().trim();
                    getData2(HnRefreshDirection.TOP, 1, mSearchContent);
                    return true;
                }
                return false;
            }
        });
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(OrderRefreshEvent event) {
        if (isFinishing()) return;
        mSearchContent = mEdSearch.getText().toString().trim();
        getData2(HnRefreshDirection.TOP, 1, mSearchContent);
    }


    protected void getData2(HnRefreshDirection state, int page, String sky) {
        RequestParams param = setRequestParam();
        this.page = page;
        param.put("page", page + "");
        param.put("pagesize", pageSize + "");
        if (!TextUtils.isEmpty(sky)) param.put("sKey", sky);
        HnHttpUtils.postRequest(setRequestUrl(), param, "", setResponseHandler(state));
    }

    @Override
    protected RequestParams setRequestParam() {
        RequestParams params = new RequestParams();
        if (!TextUtils.isEmpty(store_id)) params.put("store_id", store_id);
        return params;
    }

    @Override
    protected String setRequestUrl() {
        return TextUtils.isEmpty(store_id) ? HnUrl.REFUN_LIST : HnUrl.REFUN_LIST_SHOP;
    }

    @Override
    protected HnResponseHandler setResponseHandler(final HnRefreshDirection state) {
        return new HnResponseHandler<RefundListModel>(RefundListModel.class) {
            @Override
            public void hnSuccess(String response) {
                if (isFinishing()) return;
                refreshFinish();
                if (model.getD().getItems() == null) {
                    setEmpty("暂无数据", R.drawable.empty_com);
                    return;
                }
                if (HnRefreshDirection.TOP == state) {
                    mData.clear();
                }
                mData.addAll(model.getD().getItems());
                if (mAdapter != null)
                    mAdapter.notifyDataSetChanged();
                setEmpty("暂无数据", R.drawable.empty_com);
            }

            @Override
            public void hnErr(int errCode, String msg) {
                if (isFinishing()) return;
                refreshFinish();
                HnToastUtils.showToastShort(msg);
                setEmpty("暂无评论", R.drawable.empty_com);
            }
        };
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
