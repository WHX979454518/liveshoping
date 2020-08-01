package com.live.shoplib.ui.frag;

import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.hn.library.base.BaseFragment;
import com.hn.library.base.EventBusBean;
import com.hn.library.base.baselist.BaseViewHolder;
import com.hn.library.base.baselist.CommRecyclerAdapter;
import com.hn.library.global.HnConstants;
import com.hn.library.global.HnUrl;
import com.hn.library.http.BaseResponseModel;
import com.hn.library.http.HnHttpUtils;
import com.hn.library.http.HnResponseHandler;
import com.hn.library.loadstate.HnLoadingLayout;
import com.hn.library.refresh.PtrClassicFrameLayout;
import com.hn.library.utils.HnBadgeView;
import com.hn.library.utils.HnRefreshDirection;
import com.hn.library.utils.HnToastUtils;
import com.hn.library.utils.HnUtils;
import com.hn.library.view.CommDialog;
import com.hn.library.view.FrescoImageView;
import com.live.shoplib.R;
import com.live.shoplib.ShopActFacade;
import com.live.shoplib.ShopRequest;
import com.live.shoplib.bean.GoodsCarItemBean;
import com.live.shoplib.bean.GoodsCarModel;
import com.live.shoplib.bean.GoodsCommitModel;
import com.live.shoplib.bean.ShoppingCarRefreshEvent;
import com.live.shoplib.ui.GoodsCarAct;
import com.loopj.android.http.RequestParams;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * create by Mr.x
 * on 2018/7/4
 */

public class HnCarFrag extends BaseFragment {


    //页数   个数     是否刷新
    public int page = 1;
    protected RecyclerView mRecycler;
    protected TextView mTvHead;
    protected PtrClassicFrameLayout mSpring;
    protected HnLoadingLayout mLoadingLayout;
    private CheckBox mBoxAll;
    private TextView mTvAllMoney;
    private TextView mTvBug;
    private List<GoodsCarModel.DEntity.ItemsEntity> mData = new ArrayList<>();
    private Map<String, Float> allCarId = new HashMap<>();
    private Map<String, Float> delMap = new HashMap<>();
    private String TAG = "";
    private CommRecyclerAdapter mAdapter;
    HnBadgeView mTvNewMsg;
    @Override
    public int getContentViewId() {
        return R.layout.frag_car;
    }

    @Override
    public void initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        EventBus.getDefault().register(this);
        //
        mBoxAll = mRootView.findViewById(R.id.mBoxAll);
        mTvNewMsg = mRootView.findViewById(R.id.mTvNewMsg);
        mTvAllMoney = mRootView.findViewById(R.id.mTvAllMoney);
        mTvBug = mRootView.findViewById(R.id.mTvBug);
        mRecycler = mRootView.findViewById(com.reslibrarytwo.R.id.mRecycler);
        mTvHead = mRootView.findViewById(com.reslibrarytwo.R.id.mTvHead);
        mSpring = mRootView.findViewById(com.reslibrarytwo.R.id.mRefresh);
        mLoadingLayout = mRootView.findViewById(com.reslibrarytwo.R.id.mHnLoadingLayout);

        mSpring.setEnabled(false);

        //适配器
        mRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecycler.setHasFixedSize(true);
        mRecycler.setAdapter(mAdapter);
        initEvent();

        mRootView.findViewById(R.id.mIvMsg).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ARouter.getInstance().build("/app/HnMyMessageActivity").navigation();
            }
        });

        //错误重新加载
        mLoadingLayout.setOnReloadListener(new HnLoadingLayout.OnReloadListener() {
            @Override
            public void onReload(View v) {
                page = 1;
                mLoadingLayout.setStatus(HnLoadingLayout.Loading);
                getData(HnRefreshDirection.BOTH, page);
            }
        });
        //
        initConfig();
        mAdapter = new CommRecyclerAdapter() {
            @Override
            protected void onBindView(BaseViewHolder holder, final int position) {
                LinearLayout mLLStore = holder.getView(R.id.mLLStore);
                RelativeLayout mRlLose = holder.getView(R.id.mRlLose);
                ImageView mIvDelete = holder.getView(R.id.mIvDelete);
                CheckBox mBoxStore = holder.getView(R.id.mBoxStore);
                FrescoImageView mIvImg = holder.getView(R.id.mIvImg);
                TextView mTvStoreName = holder.getView(R.id.mTvStoreName);
                View mLine = holder.getView(R.id.mLine);

                if (position == mData.size() - 1) {
                    mLine.setVisibility(View.GONE);
                } else {
                    mLine.setVisibility(View.VISIBLE);
                }

                if (TextUtils.isEmpty(mData.get(position).getStoreId())) {
                    mRlLose.setVisibility(View.VISIBLE);
                    mLLStore.setVisibility(View.GONE);
                    mIvDelete.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            delLostGoods(position);
                        }
                    });
                    if (mData.get(position).getList().size() == 0) {
                        mRlLose.setVisibility(View.GONE);
                    }
                } else {
                    mRlLose.setVisibility(View.GONE);
                    mLLStore.setVisibility(View.VISIBLE);
                    mIvImg.setImageURI(Uri.parse(HnUrl.setImageUrl(mData.get(position).getIcon())));
                    mTvStoreName.setText(mData.get(position).getStoreName());
                    mLLStore.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            ShopActFacade.openShopDetails(mData.get(position).getStoreId());
                        }
                    });
                }

                //TODO 设置每个店铺下的商品条目
                RecyclerView mRecycler = holder.getView(R.id.mRecycler);
                setItemRecycler(mRecycler, mData.get(position).getList(), TextUtils.isEmpty(mData.get(position).getStoreId()), position, mBoxStore);


            }

            @Override
            protected int getLayoutID(int position) {
                return R.layout.item_goods_car;
            }

            @Override
            public int getItemCount() {
                return mData.size();
            }
        };

    }

    @Override
    public void onResume() {
        super.onResume();
        page = 1;
        getData(HnRefreshDirection.TOP, page);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void msgNoReadEvent(EventBusBean event) {
        if (HnConstants.EventBus.Update_Unread_Count.equals(event.getType())) {
            int noRead = (int) event.getObj();
            if (0 < noRead) {
                mTvNewMsg.setVisibility(View.VISIBLE);
            } else {
                mTvNewMsg.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Override
    protected void initData() {

    }


    //删除过期商品
    private void delLostGoods(int position) {
        delMap.clear();
        for (int i = 0; i < mData.get(position).getList().size(); i++) {
            delMap.put(mData.get(position).getList().get(i).getCart_id(), mData.get(position).getList().get(i).getNum() * mData.get(position).getList().get(i).getPrice());
        }
        String card_id = "";
        Iterator iter = delMap.entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry entry = (Map.Entry) iter.next();
            card_id = card_id + "," + entry.getKey();
        }
        if (TextUtils.isEmpty(card_id)) return;
        ShopRequest.deleteLoseGoods(card_id.substring(1), new ShopRequest.OnRespondNothing() {
            @Override
            public void finishs() {
                getData(HnRefreshDirection.TOP, page = 1);
            }
        });
    }

    //删除过期商品
    private void delLostGoods(String card_id) {
        if (TextUtils.isEmpty(card_id)) return;
        ShopRequest.deleteLoseGoods(card_id, new ShopRequest.OnRespondNothing() {
            @Override
            public void finishs() {
                getData(HnRefreshDirection.TOP, page = 1);
            }
        });
    }

    //初始化配置
    private void initConfig() {

        mTvBug.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String card_id = "";
                Iterator iter = allCarId.entrySet().iterator();
                while (iter.hasNext()) {
                    Map.Entry entry = (Map.Entry) iter.next();
                    card_id = card_id + "," + entry.getKey();
                }
                if (TextUtils.isEmpty(card_id)) return;
                requestSubCar(card_id.substring(1));
            }
        });
        mBoxAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setBoxState(mBoxAll.isChecked());
                selAllKey(mBoxAll.isChecked());
                notifyUiChange();
                mAdapter.notifyDataSetChanged();
            }
        });
    }

    //设置所有选择的状态
    private void setBoxState(boolean check) {
        for (int i = 0; i < mData.size(); i++) {
            for (int j = 0; j < mData.get(i).getList().size(); j++) {
                if (mData.get(i).getStoreId() != null) {
                    mData.get(i).getList().get(j).setCheck(check);
                }
            }
        }
    }

    //设置每一条商品的布局设置
    public void setItemRecycler(RecyclerView itemRecycler, final List<GoodsCarItemBean> data, final boolean isLose, final int pos, final CheckBox storeBox) {
        itemRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        itemRecycler.setAdapter(new CommRecyclerAdapter() {
            @Override
            protected void onBindView(BaseViewHolder holder, final int position) {
                final CheckBox mBox = holder.getView(R.id.mBoxStore);
                FrescoImageView mIvImg = holder.getView(R.id.mIvImg);
                TextView mTvGoodsName = holder.getView(R.id.mTvGoodsName);
                TextView mTvMsg = holder.getView(R.id.mTvMsg);
                TextView mTvUnit = holder.getView(R.id.mTvUnit);
                TextView mTvMoney = holder.getView(R.id.mTvMoney);
                ImageView mIvMin = holder.getView(R.id.mIvMin);
                final TextView mTvNum = holder.getView(R.id.mTvNum);
                ImageView mIvAdd = holder.getView(R.id.mIvAdd);
                View mViewTag = holder.getView(R.id.mViewTag);

                mIvImg.setImageURI(Uri.parse(HnUrl.setImageUrl(data.get(position).getGoods_img())));
                mTvGoodsName.setText(data.get(position).getGoods_name());
                mTvMsg.setText(data.get(position).getGoods_spec_details());
                //item-总价
                mTvMoney.setText(HnUtils.setTwoPoint(data.get(position).getPrice() + ""));
                mTvNum.setText(data.get(position).getNum() + "");
                mTvUnit.setTextColor(getResources().getColor(isLose ? R.color.comm_text_color_black_s : R.color.comm_text_color_red));
                mTvMoney.setTextColor(getResources().getColor(isLose ? R.color.comm_text_color_black_s : R.color.comm_text_color_red));
                mTvGoodsName.setTextColor(getResources().getColor(isLose ? R.color.comm_text_color_black_s : R.color.comm_text_color_black));

                mIvMin.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int num = data.get(position).getNum();
                        if (num <= 1) {
                            return;
                        } else {
                            num--;
                        }
                        requestChange(mTvNum, num, position, data);
                    }
                });
                mIvAdd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int num = data.get(position).getNum();
                        num++;
                        requestChange(mTvNum, num, position, data);
                        notifyUiChange();
                    }
                });
                mBox.setVisibility(isLose ? View.INVISIBLE : View.VISIBLE);
                mTvNum.setVisibility(isLose ? View.GONE : View.VISIBLE);
                mIvAdd.setVisibility(isLose ? View.GONE : View.VISIBLE);
                mIvMin.setVisibility(isLose ? View.GONE : View.VISIBLE);
                mViewTag.setVisibility(isLose ? View.VISIBLE : View.GONE);
                mBox.setChecked(data.get(position).isCheck());
                mBox.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mBox.isChecked()) {
                            if (!checkKey(data.get(position).getCart_id())) {
                                allCarId.put(data.get(position).getCart_id(), data.get(position).getNum() * data.get(position).getPrice());
                            }
                            mData.get(pos).getList().get(position).setCheck(true);
                            //点击每一个item商品都需要对 item店铺和底部总商品勾选进行判断
                            if (checkStoreAllKey(pos)) {
                                storeBox.setChecked(true);
                                chekAllKey();
                            }
                        } else {
                            mData.get(pos).getList().get(position).setCheck(false);
                            mBoxAll.setChecked(false);
                            storeBox.setChecked(false);
                            checkKey(data.get(position).getCart_id(), true);
                        }
                        notifyUiChange();
                    }
                });

                if (!isLose) {
                    holder.itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            ShopActFacade.openGoodsDetails(data.get(pos).getGoods_id());
                        }
                    });
                }

                holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        CommDialog.newInstance(getActivity())
                                .setTitle("删除商品")
                                .setContent("是否删除" + data.get(pos).getGoods_name())
                                .setClickListen(new CommDialog.TwoSelDialog() {
                                    @Override
                                    public void leftClick() {

                                    }

                                    @Override
                                    public void rightClick() {
                                        delLostGoods(data.get(pos).getCart_id());
                                    }
                                })
                                .show();
                        return false;
                    }
                });
            }

            @Override
            protected int getLayoutID(int position) {
                return R.layout.item_goods_car_item;
            }

            @Override
            public int getItemCount() {
                return data.size();
            }
        });
        //店铺的item box
        storeBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (GoodsCarItemBean b : data) {
                    b.setCheck(storeBox.isChecked());
                }
                selStoreKey(pos, storeBox.isChecked());
                notifyUiChange();
                mAdapter.notifyDataSetChanged();
            }
        });
        storeBox.setChecked(isAll(data));
    }

    //判断店铺是否全选
    private boolean isAll(List<GoodsCarItemBean> data) {
        for (GoodsCarItemBean b : data) {
            if (b.isCheck() == false) {
                return false;
            }
        }
        return true;
    }

    //检查到 返回
    public boolean checkKey(String key) {

        return allCarId.containsKey(key);
    }

    //检查到 删除
    public boolean checkKey(String key, boolean remove) {
        for (int i = 0; i < allCarId.size(); i++) {
            if (allCarId.containsKey(key)) {
                if (remove) allCarId.remove(key);
                return true;
            }
        }
        return false;
    }

    //检查到 删除
    public boolean checkKey(String key, float price) {
        for (int i = 0; i < allCarId.size(); i++) {
            if (allCarId.containsKey(key)) {
                allCarId.put(key, price);
                return true;
            }
        }
        return false;
    }

    //检查到 删除
    public void selAllKey(boolean sel) {
        if (sel) {
            for (int i = 0; i < mData.size(); i++) {
                for (int j = 0; j < mData.get(i).getList().size(); j++) {
                    if (mData.get(i).getStoreId() != null) {
                        allCarId.put(mData.get(i).getList().get(j).getCart_id(), mData.get(i).getList().get(j).getNum() * mData.get(i).getList().get(j).getPrice());
                    }
                }
            }
        } else {
            allCarId.clear();
        }
    }

    //检查到 item所有
    public boolean checkStoreAllKey(int pos) {
        for (int i = 0; i < mData.get(pos).getList().size(); i++) {
            if (mData.get(pos).getStoreId() != null) {
                if (!mData.get(pos).getList().get(i).isCheck()) return false;
            }
        }
        return true;
    }

    //检查到 item所有
    public boolean chekAllKey() {
        for (int i = 0; i < mData.size(); i++) {
            if (!checkStoreAllKey(i)) {
                mBoxAll.setChecked(false);
                return false;
            }
        }
        mBoxAll.setChecked(true);
        return true;
    }


    //检索每一个商店的item 更改商品item的选中状态
    public void selStoreKey(int pos, boolean sel) {
        for (int i = 0; i < mData.get(pos).getList().size(); i++) {
            if (sel) {
                allCarId.put(mData.get(pos).getList().get(i).getCart_id(), mData.get(pos).getList().get(i).getNum() * mData.get(pos).getList().get(i).getPrice());
            } else {
                allCarId.remove(mData.get(pos).getList().get(i).getCart_id());
            }
        }
        chekAllKey();

    }

    //修改购物车
    private void requestChange(final TextView textView, final int num, final int pos, final List<GoodsCarItemBean> list) {
        RequestParams param = new RequestParams();
        param.put("sku_id", list.get(pos).getSku_id());
        param.put("cart_id", list.get(pos).getCart_id());
        param.put("num", num + "");
        HnHttpUtils.postRequest(HnUrl.GOODS_CAR_EDIT, param, "修改购物车", new HnResponseHandler<BaseResponseModel>(BaseResponseModel.class) {
            @Override
            public void hnSuccess(String response) {
                if (textView == null) return;
                list.get(pos).setNum(num);
                textView.setText(num + "");
                if (checkKey(list.get(pos).getCart_id(), list.get(pos).getNum() * list.get(pos).getPrice())) {
                    notifyUiChange();
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
                ShopActFacade.openGoodsCommit(model);
            }

            @Override
            public void hnErr(int errCode, String msg) {
                HnToastUtils.showToastShort(msg);
            }
        });
    }

    //更新底部数据
    private void notifyUiChange() {
        if (mData == null || mData.size() < 1) {
            mBoxAll.setChecked(false);
            mTvAllMoney.setText(0 + "");
            mTvBug.setEnabled(true);
            mTvBug.setText("结算");
            return;
        }

        int numSize = allCarId.size();
        float allPrice = 0;
        Iterator iter = allCarId.entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry entry = (Map.Entry) iter.next();
            Float val = (Float) entry.getValue();
            allPrice = allPrice + val;
        }
        mTvAllMoney.setText(allPrice + "");
        if (numSize > 0) {
            mTvBug.setEnabled(true);
            mTvBug.setText("结算（" + numSize + "）");
        } else {
            mTvBug.setEnabled(false);
            mTvBug.setText("结算");
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onRefreshCarData(ShoppingCarRefreshEvent event) {
        getData(HnRefreshDirection.TOP, page = 1);
    }

    @Override
    public void onDestroyView() {
        EventBus.getDefault().unregister(this);
        super.onDestroyView();
    }

    protected void getData(HnRefreshDirection state, int page) {
        RequestParams param = setRequestParam();
        this.page = page;
        param.put("page", page + "");
        HnHttpUtils.postRequest(setRequestUrl(), param, TAG, setResponseHandler(state));
    }

    protected RequestParams setRequestParam() {
        return new RequestParams();
    }

    protected String setRequestUrl() {
        return HnUrl.GOODS_CAR;
    }

    protected HnResponseHandler setResponseHandler(final HnRefreshDirection state) {
        return new HnResponseHandler<GoodsCarModel>(GoodsCarModel.class) {
            @Override
            public void hnSuccess(String response) {
                refreshFinish();

                if (HnRefreshDirection.TOP == state) {
                    mData.clear();
                }
                if (model.getD().getItems() == null) {
                    notifyUiChange();
                    setEmpty("购物车为空", R.drawable.no_shopping);
                    return;
                }

                mData.addAll(model.getD().getItems());

                List<GoodsCarItemBean> loseData = model.getD().getFailure();
                if (loseData != null) {
                    GoodsCarModel.DEntity.ItemsEntity bean = new GoodsCarModel.DEntity.ItemsEntity();
                    bean.setList(loseData);
                    mData.add(bean);
                }


                notifyUiChange();
                if (mAdapter != null)
                    mAdapter.notifyDataSetChanged();
                setEmpty("购物车为空", R.drawable.no_shopping);
            }

            @Override
            public void hnErr(int errCode, String msg) {
                refreshFinish();
                HnToastUtils.showToastShort(msg);
                setEmpty("购物车为空", R.drawable.no_shopping);
            }
        };
    }

    protected void setEmpty(String content, int res) {
        if (mAdapter == null) return;
        if (mAdapter.getItemCount() < 1) {
            mLoadingLayout.setStatus(HnLoadingLayout.Empty);
            mLoadingLayout.setEmptyText(content).setEmptyImage(res);
        } else {
            mLoadingLayout.setStatus(HnLoadingLayout.Success);
        }
    }

    protected void refreshFinish() {
        if (mSpring != null) mSpring.refreshComplete();
    }
}
