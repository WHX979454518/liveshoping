package com.live.shoplib.ui.frag;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hn.library.base.baselist.BaseViewHolder;
import com.hn.library.base.baselist.CommRecyclerAdapter;
import com.hn.library.global.HnUrl;
import com.hn.library.http.HnHttpUtils;
import com.hn.library.http.HnResponseHandler;
import com.hn.library.refresh.PtrFrameLayout;
import com.hn.library.utils.HnRefreshDirection;
import com.hn.library.utils.HnToastUtils;
import com.hn.library.utils.HnUiUtils;
import com.hn.library.view.CommDialog;
import com.jakewharton.rxbinding2.view.RxView;
import com.live.shoplib.R;
import com.live.shoplib.ShopActFacade;
import com.live.shoplib.ShopDialogFacade;
import com.live.shoplib.ShopRequest;
import com.live.shoplib.bean.HnOrderDetailsModel;
import com.live.shoplib.bean.HnOrderListBean;
import com.live.shoplib.bean.HnSearchOrderModel;
import com.live.shoplib.bean.OrderRefreshEvent;
import com.live.shoplib.other.GoodsOrderInject;
import com.loopj.android.http.RequestParams;
import com.reslibrarytwo.CommListFragment;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.functions.Consumer;

/**
 * 我的订单
 * 作者：Mr.Xu
 * 时间：2017/12/18
 */
public class OrderSortFragment extends CommListFragment {

    private List<HnOrderListBean> mData = new ArrayList<>();

    private int type = 0;

    private boolean isSeller = false;

    public static OrderSortFragment newInstance(int type, boolean isSeller) {
        OrderSortFragment fragment = new OrderSortFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("type", type);
        bundle.putBoolean("isSeller", isSeller);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected String setTAG() {
        EventBus.getDefault().register(this);
        type = getArguments().getInt("type");
        isSeller = getArguments().getBoolean("isSeller");
        return "我的订单";
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected CommRecyclerAdapter setAdapter() {
        return new CommRecyclerAdapter() {
            @Override
            protected void onBindView(BaseViewHolder holder, final int position) {
                TextView mTvCancel = holder.getView(R.id.mTvCancel);//删除
                TextView mTvDelete = holder.getView(R.id.mTvDelete);//取消
                TextView mTvEva = holder.getView(R.id.mTvEva);//评论
                TextView mTvQuery = holder.getView(R.id.mTvQuery);//查看物流
                TextView mTvPay = holder.getView(R.id.mTvPay);//去支付
                TextView mTvSure = holder.getView(R.id.mTvSure);//确认
                TextView mTvToSend = holder.getView(R.id.mTvToSend);//去发货
                /*隐藏*/
                mTvCancel.setVisibility(View.GONE);
                mTvDelete.setVisibility(View.GONE);
                mTvEva.setVisibility(View.GONE);
                mTvQuery.setVisibility(View.GONE);
                mTvPay.setVisibility(View.GONE);
                mTvSure.setVisibility(View.GONE);
                mTvToSend.setVisibility(View.GONE);
                //删除订单
                RxView.clicks(mTvDelete)
                        .throttleFirst(2, TimeUnit.SECONDS)
                        .subscribe(new Consumer<Object>() {
                            @Override
                            public void accept(Object o) throws Exception {
                                CommDialog.newInstance(getActivity())
                                        .setTitle("删除订单")
                                        .setContent("是否删除订单")
                                        .setClickListen(new CommDialog.TwoSelDialog() {
                                            @Override
                                            public void leftClick() {

                                            }

                                            @Override
                                            public void rightClick() {
                                                ShopRequest.deleteOrder(mData.get(position).getOrder_id(), new ShopRequest.OnRespondNothing() {
                                                    @Override
                                                    public void finishs() {
                                                        EventBus.getDefault().post(new OrderRefreshEvent(-1));
                                                    }
                                                });
                                            }
                                        })
                                        .show();
                            }
                        });
                //取消订单
                RxView.clicks(mTvCancel)
                        .throttleFirst(2, TimeUnit.SECONDS)
                        .subscribe(new Consumer<Object>() {
                            @Override
                            public void accept(Object o) throws Exception {
                                CommDialog.newInstance(getActivity())
                                        .setTitle("取消订单")
                                        .setContent("是否确认取消订单")
                                        .setClickListen(new CommDialog.TwoSelDialog() {
                                            @Override
                                            public void leftClick() {

                                            }

                                            @Override
                                            public void rightClick() {
                                                ShopRequest.cancelOrder(mData.get(position).getOrder_id(), new ShopRequest.OnRespondNothing() {
                                                    @Override
                                                    public void finishs() {
                                                        EventBus.getDefault().post(new OrderRefreshEvent(-1));
                                                        HnToastUtils.showToastShort("订单取消成功");
                                                    }
                                                });
                                            }
                                        })
                                        .show();
                            }
                        });
                //去评论
                RxView.clicks(mTvEva)
                        .throttleFirst(2, TimeUnit.SECONDS)
                        .subscribe(new Consumer<Object>() {
                            @Override
                            public void accept(Object o) throws Exception {
                                //评价
                                ShopActFacade.openEvaPublic(mData.get(position).getOrder_id());
                            }
                        });
                //确认收货
                RxView.clicks(mTvSure)
                        .throttleFirst(2, TimeUnit.SECONDS)
                        .subscribe(new Consumer<Object>() {
                            @Override
                            public void accept(Object o) throws Exception {
                                //判断特殊状态 1 为退款中 2 为退款已完成
//                                int state = GoodsOrderInject.calculateState(mData.get(position).getOrder_status(), mData.get(position).getPay_status(), mData.get(position).getShipping_status());
                                boolean isRefend = false;
                                for (int i = 0; i < mData.get(position).getDetails().size(); i++) {
                                    if ("1".equals(mData.get(position).getDetails().get(i).getStatus())) {
                                        isRefend = true;
                                    }
                                }
                                if (isRefend) {
                                    CommDialog.newInstance(getActivity())
                                            .setTitle("提示")
                                            .setContent("该订单中存在退款中的商品，确认收货会关闭退款")
                                            .setRightText("确认收货")
                                            .setClickListen(new CommDialog.TwoSelDialog() {
                                                @Override
                                                public void leftClick() {

                                                }

                                                @Override
                                                public void rightClick() {
                                                    ShopRequest.sureOrder(mData.get(position).getOrder_id(), new ShopRequest.OnRespondNothing() {
                                                        @Override
                                                        public void finishs() {
                                                            ShopActFacade.openSureOrder(mData.get(position).getOrder_id());
                                                            mActivity.finish();
                                                        }
                                                    });
                                                }
                                            })
                                            .show();
                                } else {
                                    CommDialog.newInstance(getActivity())
                                            .setTitle("确认收货")
                                            .setContent("是否确认收货该商品")
                                            .setRightText("确定")
                                            .setClickListen(new CommDialog.TwoSelDialog() {
                                                @Override
                                                public void leftClick() {

                                                }

                                                @Override
                                                public void rightClick() {
                                                    ShopRequest.sureOrder(mData.get(position).getOrder_id(), new ShopRequest.OnRespondNothing() {
                                                        @Override
                                                        public void finishs() {
                                                            ShopActFacade.openSureOrder(mData.get(position).getOrder_id());
                                                            mActivity.finish();
                                                        }
                                                    });
                                                }
                                            })
                                            .show();
                                }

                            }
                        });
                //查看物流
                RxView.clicks(mTvQuery)
                        .throttleFirst(2, TimeUnit.SECONDS)
                        .subscribe(new Consumer<Object>() {
                            @Override
                            public void accept(Object o) throws Exception {
                                ShopActFacade.openGoodsLogistics(mData.get(position).getOrder_id());
                            }
                        });
                //去支付
                RxView.clicks(mTvPay)
                        .throttleFirst(2, TimeUnit.SECONDS)
                        .subscribe(new Consumer<Object>() {
                            @Override
                            public void accept(Object o) throws Exception {
                                if (TextUtils.equals(mData.get(position).getStore_status(), "0")) {
                                    HnToastUtils.showToastShort("店铺已被冻结，暂时无法支付");
                                    return;
                                }
                                ShopActFacade.openPayDetails(mData.get(position).getOrder_id(), mData.get(position).getOrder_amount());
                            }
                        });
                //去发货
                RxView.clicks(mTvToSend)
                        .throttleFirst(2, TimeUnit.SECONDS)
                        .subscribe(new Consumer<Object>() {
                            @Override
                            public void accept(Object o) throws Exception {
                                boolean isRefend = false;
                                for (int i = 0; i < mData.get(position).getDetails().size(); i++) {
                                    if ("1".equals(mData.get(position).getDetails().get(i).getStatus())) {
                                        isRefend = true;
                                    }
                                }
                                if (isRefend) {
                                    CommDialog.newInstance(getActivity())
                                            .setTitle("提示")
                                            .setContent("订单存在审核退款商品，是否确认发货？")
                                            .setRightText("确定")
                                            .setClickListen(new CommDialog.TwoSelDialog() {
                                                @Override
                                                public void leftClick() {

                                                }

                                                @Override
                                                public void rightClick() {
                                                    ShopDialogFacade.showLogistrics(getActivity().getFragmentManager(),
                                                            mData.get(position).getOrder_id(), "", 2);
                                                }
                                            })
                                            .show();
                                } else {
                                    ShopDialogFacade.showLogistrics(getActivity().getFragmentManager(),
                                            mData.get(position).getOrder_id(), "", 2);
                                }
                            }
                        });
                RecyclerView mGoodsRecycler = holder.getView(R.id.mGoodsRecycler);
                mGoodsRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
                mGoodsRecycler.setAdapter(new ItemAdapter(mData.get(position).getDetails()));
                /*区分*/
                int state = GoodsOrderInject.calculateState(Integer.parseInt(mData.get(position).getOrder_status()), Integer.parseInt(mData.get(position).getPay_status()), Integer.parseInt(mData.get(position).getShipping_status()));
                GoodsOrderInject.setUserView(holder.itemView, mData.get(position).getIcon(), mData.get(position).getName(), state);
                switch (state) {
                    case GoodsOrderInject.ORDER_PAY://待付款
                        if (isSeller) {
                            //不显示
                        } else {
                            mTvCancel.setVisibility(View.VISIBLE);
                            mTvPay.setVisibility(View.VISIBLE);
                        }
                        break;
                    case GoodsOrderInject.ORDER_SEND://待发货
                        if (isSeller) {
                            mTvToSend.setVisibility(View.VISIBLE);
                        }
                        break;
                    case GoodsOrderInject.ORDER_GET://待收货
                        if (isSeller) {
                            mTvQuery.setVisibility(View.VISIBLE);
                        } else {
                            mTvQuery.setVisibility(View.VISIBLE);
                            mTvSure.setVisibility(View.VISIBLE);
                        }
                        break;
                    case GoodsOrderInject.ORDER_EVA://待评价
                        if (isSeller) {
                            mTvEva.setVisibility(View.GONE);
                        } else {
                            mTvEva.setVisibility(View.VISIBLE);
                        }
                        break;
                    case GoodsOrderInject.ORDER_REFUND://退款中
//                        mTvQuery.setVisibility(View.VISIBLE);
                        mTvDelete.setVisibility(View.VISIBLE);
                        break;
                    case GoodsOrderInject.ORDER_CANCEL://已取消
                    case GoodsOrderInject.ORDER_NULL://已无效
                        mTvDelete.setVisibility(View.VISIBLE);
                        break;
                }
                holder.setTextViewText(R.id.mTvStateMsg, "共" + mData.get(position).getGoods_num() + "件商品  " +
                        "实付：¥" + mData.get(position).getOrder_amount());

                holder.getView(R.id.mLLStore).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ShopActFacade.openShopDetails(mData.get(position).getStore_id());
                    }
                });

            }

            @Override
            protected int getLayoutID(int position) {
                return R.layout.item_order_sort;
            }

            @Override
            public int getItemCount() {
                return mData.size();
            }
        };
    }


    class ItemAdapter extends CommRecyclerAdapter {

        private List<HnOrderListBean.DetailsEntity> date = new ArrayList<>();

        public ItemAdapter(List<HnOrderListBean.DetailsEntity> date) {
            this.date = date;
        }

        @Override
        protected void onBindView(BaseViewHolder holder, final int position) {
            boolean isEque = true;
            try {
                isEque = Integer.valueOf(date.get(position).getGoods_number()) == Integer.valueOf(date.get(position).getRefund_number());
            } catch (NumberFormatException e) {
                isEque = true;
                e.printStackTrace();
            }
            GoodsOrderInject.setGoodsView(holder.itemView, date.get(position).getGoods_img(), date.get(position).getGoods_name(), date.get(position).getGoods_attr(),
                    date.get(position).getGoods_price(), date.get(position).getGoods_number(), date.get(position).getStatus(),
                    isEque);
            RxView.clicks(holder.itemView)
                    .throttleFirst(2, TimeUnit.SECONDS)
                    .subscribe(new Consumer<Object>() {
                        @Override
                        public void accept(Object o) throws Exception {
                            ShopActFacade.openOrderDetails(date.get(position).getOrder_id(), isSeller);
                        }
                    });
        }

        @Override
        protected int getLayoutID(int position) {
            return R.layout.item_order_goods_msg;
        }

        @Override
        public int getItemCount() {
            return date.size();
        }
    }


    /**
     * 0 全部订单 1 待付款 2 代发货 3 待收货 4 待评价
     */
    @Override
    protected RequestParams setRequestParam() {
        RequestParams params = new RequestParams();
        params.put("type", type + "");
        return params;
    }

    @Override
    protected String setRequestUrl() {
        return isSeller ? HnUrl.ORDER_GOODS_SHOP : HnUrl.ORDER_GOODS;
    }

    @Override
    protected HnResponseHandler setResponseHandler(final HnRefreshDirection state) {
        return new HnResponseHandler<HnOrderDetailsModel>(HnOrderDetailsModel.class) {
            @Override
            public void hnSuccess(String response) {
                if (mActivity == null) return;
                refreshFinish();
                if (model.getD().getItems() == null) {
                    setEmpty(HnUiUtils.getString(R.string.you_not_hava_orde), R.drawable.empty_com);
                    return;
                }
                if (HnRefreshDirection.TOP == state) {
                    mData.clear();
                }
                mData.addAll(model.getD().getItems());
                if (mAdapter != null)
                    mAdapter.notifyDataSetChanged();
                setEmpty(HnUiUtils.getString(R.string.you_not_hava_orde), R.drawable.empty_com);
            }

            @Override
            public void hnErr(int errCode, String msg) {
                refreshFinish();
                setEmpty(HnUiUtils.getString(R.string.you_not_hava_orde), R.drawable.empty_com);
            }
        };
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(OrderRefreshEvent event) {
        if (event.getPos() == -1 || event.getPos() == type) {
            mSpring.setMode(PtrFrameLayout.Mode.BOTH);
            getData(HnRefreshDirection.TOP, page = 1);
        } else if (event.getPos() == -2) {
            if (TextUtils.isEmpty(event.getKeys())) {
                mSpring.setMode(PtrFrameLayout.Mode.BOTH);
                getData(HnRefreshDirection.TOP, page = 1);
            } else {
                searchData(HnRefreshDirection.TOP, event.getKeys());
            }

        }
    }

    private void searchData(final HnRefreshDirection state, String sKey) {
        this.page = 1;
        mSpring.setMode(PtrFrameLayout.Mode.NONE);
        RequestParams param = setRequestParam();
        param.put("sKey", sKey + "");
        HnHttpUtils.postRequest(HnUrl.ORDER_GOODS_SHOP_SEARCH, param, TAG, new HnResponseHandler<HnSearchOrderModel>(HnSearchOrderModel.class) {
            @Override
            public void hnSuccess(String response) {
                if (mActivity == null) return;
                refreshFinish();
                if (model.getD() == null) {
                    setEmpty(HnUiUtils.getString(R.string.you_not_hava_orde), R.drawable.empty_com);
                    return;
                }
                if (HnRefreshDirection.TOP == state) {
                    mData.clear();
                }
                mData.addAll(model.getD());
                if (mAdapter != null)
                    mAdapter.notifyDataSetChanged();
                setEmpty(HnUiUtils.getString(R.string.you_not_hava_orde), R.drawable.empty_com);
            }

            @Override
            public void hnErr(int errCode, String msg) {
                if (mActivity == null) return;
                mData.clear();
                mAdapter.notifyDataSetChanged();
                refreshFinish();
                setEmpty(HnUiUtils.getString(R.string.you_not_hava_orde), R.drawable.empty_com);
            }
        });


    }


}
