package com.live.shoplib.ui;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.hn.library.base.baselist.BaseViewHolder;
import com.hn.library.base.baselist.CommRecyclerAdapter;
import com.hn.library.global.HnUrl;
import com.hn.library.http.BaseResponseModel;
import com.hn.library.http.HnHttpUtils;
import com.hn.library.http.HnResponseHandler;
import com.hn.library.utils.HnRefreshDirection;
import com.hn.library.utils.HnToastUtils;
import com.hn.library.view.CommDialog;
import com.jakewharton.rxbinding2.view.RxView;
import com.live.shoplib.R;
import com.live.shoplib.ShopActFacade;
import com.live.shoplib.bean.AddressSelEvent;
import com.live.shoplib.bean.MyRecAddressModel;
import com.loopj.android.http.RequestParams;
import com.reslibrarytwo.CommListActivity;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.functions.Consumer;

/**
 * 收货地址
 * 作者：Mr.Xu
 * 时间：2017/12/20
 */
@Route(path = "/shoplib/AddressReceivingAct")
public class AddressReceivingAct extends CommListActivity {

    private CommRecyclerAdapter mAdapter;
    private List<MyRecAddressModel.DEntity> mData = new ArrayList<>();
    private LinearLayout mLLAdd;
    private boolean select = false;

    @Override
    public int getContentViewId() {
        return R.layout.act_rec_address;
    }

    @Override
    protected String setTitle() {
        select = getIntent().getBooleanExtra("select", false);
        mLLAdd = findViewById(R.id.mLLAdd);
        mLLAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShopActFacade.openAddressAddEdit();
            }
        });
        return "收货地址";
    }

    @Override
    protected CommRecyclerAdapter setAdapter() {
        setShowSubTitle(true);
        mAdapter = new CommRecyclerAdapter() {
            @Override
            protected void onBindView(final BaseViewHolder holder, final int position) {
                RxView.clicks(holder.itemView)
                        .throttleFirst(2, TimeUnit.SECONDS)
                        .subscribe(new Consumer<Object>() {
                            @Override
                            public void accept(Object o) throws Exception {
                                HnToastUtils.showToastShort("item：" + position);
                            }
                        });
                holder.setTextViewText(R.id.mTvName, mData.get(position).getNick());
                holder.setTextViewText(R.id.mTvPhone, mData.get(position).getPhone());
                holder.setTextViewText(R.id.mTvAddress, "收货地址：" + mData.get(position).getProvince() + "-" +
                        mData.get(position).getCity() + "-" + mData.get(position).getArea() + "  " +
                        mData.get(position).getDetail()
                );
                //是否默认地址
                ((CheckBox) holder.getView(R.id.mBoxAddress)).setChecked(TextUtils.equals(mData.get(position).getIs_default(), "1") ? true : false);
                ((CheckBox) holder.getView(R.id.mBoxAddress)).setVisibility(TextUtils.equals(mData.get(position).getIs_default(), "1") ? View.VISIBLE : View.INVISIBLE);

                ((CheckBox) holder.getView(R.id.mBoxAddress)).setEnabled(false);

                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (select) {
                            EventBus.getDefault().post(new AddressSelEvent(mData.get(position), true));
                            finish();
                        }
                    }
                });

                holder.getView(R.id.mTvEdit).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ShopActFacade.openAddressAddEdit(mData.get(position));
                    }
                });

                holder.getView(R.id.mTvDelete).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        CommDialog.newInstance(AddressReceivingAct.this)
                                .setTitle("删除地址")
                                .setContent("是否删除收货地址?")
                                .setClickListen(new CommDialog.TwoSelDialog() {
                                    @Override
                                    public void leftClick() {

                                    }

                                    @Override
                                    public void rightClick() {
                                        deleteAddress(mData.get(position).getId(), position);
                                    }
                                })
                                .show();
                    }
                });
            }

            @Override
            protected int getLayoutID(int position) {
                return R.layout.item_rec_address;
            }

            @Override
            public int getItemCount() {
                return mData.size();
            }
        };
        return mAdapter;
    }

    @Override
    protected RequestParams setRequestParam() {
        RequestParams params = new RequestParams();
        return params;
    }

    @Override
    protected String setRequestUrl() {
        return HnUrl.REC_ADDRESS;
    }

    @Override
    protected void onResume() {
        super.onResume();
        page = 1;
        getData(HnRefreshDirection.TOP, page);
    }

    @Override
    protected HnResponseHandler setResponseHandler(final HnRefreshDirection state) {
        return new HnResponseHandler<MyRecAddressModel>(MyRecAddressModel.class) {
            @Override
            public void hnSuccess(String response) {
                if (isFinishing()) return;
                refreshFinish();
                if (model.getD() == null) {
                    setEmpty("暂无收货地址", R.drawable.home_open_position);
                    return;
                }
                if (HnRefreshDirection.TOP == state) {
                    mData.clear();
                }
                mData.addAll(model.getD());
                if (mAdapter != null)
                    mAdapter.notifyDataSetChanged();
                setEmpty("暂无收货地址", R.drawable.home_open_position);
            }

            @Override
            public void hnErr(int errCode, String msg) {
                if (isFinishing()) return;
                refreshFinish();
                HnToastUtils.showToastShort(msg);
                setEmpty("暂无收货地址", R.drawable.home_open_position);
            }
        };
    }

    public void deleteAddress(String address_id, final int pos) {
        RequestParams param = new RequestParams();
        param.put("address_id", address_id);
        HnHttpUtils.postRequest(HnUrl.DELETE_ADDRESS, param, "删除地址", new HnResponseHandler<BaseResponseModel>(BaseResponseModel.class) {
            @Override
            public void hnSuccess(String response) {
                HnToastUtils.showToastShort("删除地址成功");
                mData.remove(pos);
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void hnErr(int errCode, String msg) {
                HnToastUtils.showToastShort(msg);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mData == null || mData.size() < 1) {
            EventBus.getDefault().post(new AddressSelEvent(null, false));
        }
    }
}