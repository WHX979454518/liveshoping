package com.live.shoplib.ui;

import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.hn.library.base.BaseActivity;
import com.hn.library.base.baselist.BaseViewHolder;
import com.hn.library.base.baselist.CommRecyclerAdapter;
import com.hn.library.global.HnUrl;
import com.hn.library.http.HnHttpUtils;
import com.hn.library.http.HnResponseHandler;
import com.hn.library.utils.Base64;
import com.hn.library.utils.HnPrefUtils;
import com.hn.library.utils.HnToastUtils;
import com.hn.library.view.FrescoImageView;
import com.live.shoplib.R;
import com.live.shoplib.ShopActFacade;
import com.live.shoplib.bean.AddressSelEvent;
import com.live.shoplib.bean.GoodsCommitModel;
import com.live.shoplib.bean.GroupBuySubGoodsModel;
import com.live.shoplib.bean.GroupBuyTransationBean;
import com.live.shoplib.bean.MyRecAddressModel;
import com.live.shoplib.bean.ShoppingCarRefreshEvent;
import com.live.shoplib.bean.SpikeTransationBean;
import com.live.shoplib.bean.SubGoodsModel;
import com.live.shoplib.other.GoodsOrderInject;
import com.loopj.android.http.RequestParams;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

/**
 * 提交订单
 * 作者：Mr.Xu
 * 时间：2017/12/28
 */
@Route(path = "/shoplib/GoodsCommitAct")
public class GoodsCommitAct extends BaseActivity {


    public static final String COMMIT_TYPE = "COMMIT_TYPE";
    public static final int NORMAL_COMMIT_TYPE = 0;
    public static final int SPIKE_COMMIT_TYPE = 1;
    public static final int GROUP_BUY_COMMIT_TYPE = 2;

    private List<GoodsCommitModel.DEntity.OrderDetailsEntity> mData = new ArrayList<>();
    private GoodsCommitModel bean;
    private SpikeTransationBean spikeBean;
    private GroupBuyTransationBean groupBean;
    private int commitType;

    private RecyclerView mRecycler;
    private TextView mTvAllMoney;
    private TextView group_buy_tips_layout;
    private TextView mTvCommit;

    private TextView mTvGetName;
    private TextView mTvGetPhone;
    private TextView mTvGetAddress;
    private TextView mTvNone;
    private LinearLayout mLLAddress;
    private LinearLayout mLlGetAddress;

    private String address_id = "";

    @Override
    public int getContentViewId() {
        return R.layout.act_goods_commit;
    }

    @Override
    public void onCreateNew(Bundle savedInstanceState) {
        EventBus.getDefault().register(this);
        setTitle("确认订单");
        setShowBack(true);

        bean = (GoodsCommitModel) getIntent().getSerializableExtra("bean");
        spikeBean = (SpikeTransationBean) getIntent().getSerializableExtra("spikeBean");
        groupBean = (GroupBuyTransationBean) getIntent().getSerializableExtra("groupBean");
        commitType = getIntent().getIntExtra(COMMIT_TYPE, NORMAL_COMMIT_TYPE);

        mTvGetName = (TextView) findViewById(R.id.mTvGetName);
        group_buy_tips_layout = (TextView) findViewById(R.id.group_buy_tips_layout);
        mTvGetPhone = (TextView) findViewById(R.id.mTvGetPhone);
        mTvGetAddress = (TextView) findViewById(R.id.mTvGetAddress);
        mTvNone = (TextView) findViewById(R.id.mTvNone);
        mRecycler = (RecyclerView) findViewById(R.id.mRecycler);
        mTvAllMoney = (TextView) findViewById(R.id.mTvAllMoney);
        mTvCommit = (TextView) findViewById(R.id.mTvCommit);
        mLLAddress = (LinearLayout) findViewById(R.id.mLLAddress);
        mLlGetAddress = (LinearLayout) findViewById(R.id.mLlGetAddress);
        initData();



        mLLAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShopActFacade.openAddressReceiving(true);
            }
        });
        requestAddress();
        mRecycler.setLayoutManager(new LinearLayoutManager(this));
        mRecycler.setAdapter(new CommRecyclerAdapter() {
            @Override
            protected void onBindView(BaseViewHolder holder, final int position) {
                FrescoImageView mIvStore = holder.getView(R.id.mIvStore);
                TextView mTvStoreName = holder.getView(R.id.mTvStoreName);
                RecyclerView mRecycler = holder.getView(R.id.mRecycler);
                TextView mTvMoney = holder.getView(R.id.mTvMoney);
                TextView mTvCarriage = holder.getView(R.id.mTvCarriage);
                if(TextUtils.equals("0",mData.get(position).getShop_fee())){
                    mTvCarriage.setText("包邮");
                }else {
                    mTvCarriage.setText("快递费" + mData.get(position).getShop_fee() + "元");
                }
                mIvStore.setImageURI(Uri.parse(HnUrl.setImageUrl(mData.get(position).getStore_icon())));
                mTvStoreName.setText(mData.get(position).getStore_name());
                mTvMoney.setText(mData.get(position).getTotal_price() + "");

                final EditText mEdContent = holder.getView(R.id.mEdContent);
                mEdContent.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                    }

                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        mData.get(position).setContent(mEdContent.getText().toString());
                    }
                });
                setItemGoods(mRecycler, mData.get(position).getList());
            }

            @Override
            protected int getLayoutID(int position) {
                return R.layout.item_commit;
            }

            @Override
            public int getItemCount() {
                return mData.size();
            }
        });
    }

    private void initData() {
        switch (commitType) {
            case NORMAL_COMMIT_TYPE:
                mData = bean.getD().getOrder_details();
                mTvAllMoney.setText(bean.getD().getOrder_price() + "");
                break;
            case SPIKE_COMMIT_TYPE:
                List<GoodsCommitModel.DEntity.OrderDetailsEntity> spikeList = new ArrayList<>();
                spikeList.add(spikeBean.getOrder_details());
                mTvAllMoney.setText(spikeBean.getOrder_details().getTotal_price() + "");
                mData = spikeList;
                break;
            case GROUP_BUY_COMMIT_TYPE:
                group_buy_tips_layout.setVisibility(View.VISIBLE);
                List<GoodsCommitModel.DEntity.OrderDetailsEntity> groupList = new ArrayList<>();
                groupList.add(groupBean.getOrder_details());
                mTvAllMoney.setText(groupBean.getOrder_details().getTotal_price() + "");
                mData = groupList;
                break;

        }
    }


    public void setItemGoods(RecyclerView recyclerView, final List<GoodsCommitModel.DEntity.OrderDetailsEntity.ListEntity> list) {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(new CommRecyclerAdapter() {
            @Override
            protected void onBindView(BaseViewHolder holder, int position) {
                GoodsOrderInject.setGoodsView(holder.itemView,
                        list.get(position).getGoods_img(), list.get(position).getGoods_name(),
                        list.get(position).getSpec_sku(), list.get(position).getPrice(),
                        list.get(position).getNum(), -1+"",true);
            }

            @Override
            protected int getLayoutID(int position) {
                return R.layout.item_order_goods_msg;
            }

            @Override
            public int getItemCount() {
                return list.size();
            }
        });
    }

    @Override
    public void getInitData() {
        mTvCommit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (commitType) {
                    case NORMAL_COMMIT_TYPE:
                        requestSub(address_id);
                        break;
                    case SPIKE_COMMIT_TYPE:
                        requestSpikeSub(address_id);
                        break;
                    case GROUP_BUY_COMMIT_TYPE:
                        requestGroupBuySub(address_id);
                        break;
                }
            }
        });
    }


    //提交普通购物车
    private void requestSub(String address_id) {
        String postscript = "{";
        for (int i = 0; i < mData.size(); i++) {
            postscript = postscript + "\"" + mData.get(i).getStore_id() + "\":\"" + mData.get(i).getContent() + "\",";
        }
        postscript = postscript.substring(0, postscript.length() - 1) + "}";
        if (bean == null || TextUtils.isEmpty(postscript)) {
            HnToastUtils.showToastShort("暂无记录");
            return;
        }
        if (TextUtils.isEmpty(address_id)) {
            HnToastUtils.showToastShort("请选择收货地址");
            return;
        }
        RequestParams param = new RequestParams();
        param.put("cartList", bean.getD().getCartList());
        param.put("postscript", Base64.encode(postscript));
        param.put("address_id", address_id);
        HnHttpUtils.postRequest(HnUrl.ORDER_SUB, param, "提交购物车", new HnResponseHandler<SubGoodsModel>(SubGoodsModel.class) {
            @Override
            public void hnSuccess(String response) {
                EventBus.getDefault().post(new ShoppingCarRefreshEvent());
                ShopActFacade.openPayDetails(model.getD().getOrder_id(), bean.getD().getOrder_price() + "");
                finish();
            }

            @Override
            public void hnErr(int errCode, String msg) {
                HnToastUtils.showToastShort(msg);
            }
        });
    }

    //提交秒杀订单
    private void requestSpikeSub(String address_id) {

        String postscript = "{";
        for (int i = 0; i < mData.size(); i++) {
            postscript = postscript + "\"" + mData.get(i).getStore_id() + "\":\"" + mData.get(i).getContent() + "\",";
        }
        postscript = postscript.substring(0, postscript.length() - 1) + "}";
        if (spikeBean == null) {
            HnToastUtils.showToastShort("暂无记录");
            return;
        }
        if (TextUtils.isEmpty(address_id)) {
            HnToastUtils.showToastShort("请选择收货地址");
            return;
        }
        RequestParams param = new RequestParams();
        param.put("sec_goods_id", spikeBean.getSec_goods_id());
        param.put("postscript", Base64.encode(postscript));
        param.put("store_id", spikeBean.getStore_id());
        param.put("num", spikeBean.getNum());
        param.put("sec_price", spikeBean.getSec_price());
        param.put("spec_sku", spikeBean.getSpec_sku());
        param.put("spec_ids", spikeBean.getSpec_ids());
        param.put("sku_id", spikeBean.getSku_id());
        param.put("address_id", address_id);
        HnHttpUtils.postRequest(HnUrl.SPIKE_ORDER_SUB, param, "提交秒杀订单", new HnResponseHandler<SubGoodsModel>(SubGoodsModel.class) {
            @Override
            public void hnSuccess(String response) {
                EventBus.getDefault().post(new ShoppingCarRefreshEvent());
                ShopActFacade.openPayDetails(model.getD().getOrder_id(), spikeBean.getOrder_details().getTotal_price() + "");
                finish();
            }

            @Override
            public void hnErr(int errCode, String msg) {
                HnToastUtils.showToastShort(msg);
            }
        });
    }

    //提交团购订单
    private void requestGroupBuySub(String address_id) {
        String postscript = "{";
        for (int i = 0; i < mData.size(); i++) {
            postscript = postscript + "\"" + mData.get(i).getStore_id() + "\":\"" + mData.get(i).getContent() + "\",";
        }
        postscript = postscript.substring(0, postscript.length() - 1) + "}";
        if (groupBean == null) {
            HnToastUtils.showToastShort("暂无记录");
            return;
        }
        if (TextUtils.isEmpty(address_id)) {
            HnToastUtils.showToastShort("请选择收货地址");
            return;
        }
        RequestParams param = new RequestParams();
        param.put("postscript", Base64.encode(postscript));
        param.put("group_buy_id", groupBean.getGroup_buy_id());
        param.put("group_order_id", groupBean.getGroup_order_id());
        param.put("store_id", groupBean.getStore_id());
        param.put("num", groupBean.getNum());
        param.put("group_price", groupBean.getGroup_price());
        param.put("spec_sku", groupBean.getSpec_sku());
        param.put("spec_ids", groupBean.getSpec_ids());
        param.put("sku_id", groupBean.getSku_id());
        param.put("address_id", address_id);
        HnHttpUtils.postRequest(HnUrl.GROUP_BUY_ORDER_SUB, param, "提交团购订单", new HnResponseHandler<GroupBuySubGoodsModel>(GroupBuySubGoodsModel.class) {
            @Override
            public void hnSuccess(String response) {

                EventBus.getDefault().post(new ShoppingCarRefreshEvent());
                HnPrefUtils.setString(model.getD().getOrder_id(), model.getD().getGroup_order_id());
                ShopActFacade.openPayDetails(model.getD().getOrder_id(), groupBean.getOrder_details().getTotal_price() + "", true);
                finish();
            }

            @Override
            public void hnErr(int errCode, String msg) {
                HnToastUtils.showToastShort(msg);
            }
        });
    }

    //获取默认地址
    private void requestAddress() {
        RequestParams param = new RequestParams();
        param.put("type", "1");
        HnHttpUtils.postRequest(HnUrl.REC_ADDRESS, param, "默认地址", new HnResponseHandler<MyRecAddressModel>(MyRecAddressModel.class) {
            @Override
            public void hnSuccess(String response) {
                if (model.getD() == null || model.getD().size() == 0) return;
                address_id = model.getD().get(0).getId();
                mTvNone.setText("");
                mTvGetAddress.setText(model.getD().get(0).getProvince() + "-" +
                        model.getD().get(0).getCity() + "-" + model.getD().get(0).getArea() + "  " +
                        model.getD().get(0).getDetail());
                mLlGetAddress.setVisibility(View.VISIBLE);
                mTvGetName.setText(model.getD().get(0).getNick());
                mTvGetPhone.setText(model.getD().get(0).getPhone());
            }

            @Override
            public void hnErr(int errCode, String msg) {
                HnToastUtils.showToastShort(msg);
            }
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(AddressSelEvent event) {
        if (event.isType()) {
            address_id = event.getdEntity().getId();
            mLlGetAddress.setVisibility(View.VISIBLE);
            mTvNone.setText("");
            mTvGetAddress.setText(event.getdEntity().getProvince() + "-" +
                    event.getdEntity().getCity() + "-" + event.getdEntity().getArea() + "  " +
                    event.getdEntity().getDetail());
            mTvGetName.setText("收货人：" + event.getdEntity().getNick());
            mTvGetPhone.setText(event.getdEntity().getPhone());
        } else {
            address_id = "";
            mTvNone.setText("请选择地址");
            mLlGetAddress.setVisibility(View.GONE);
            mTvGetName.setText("");
            mTvGetPhone.setText("");
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
