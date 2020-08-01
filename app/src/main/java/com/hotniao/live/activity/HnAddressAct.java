package com.hotniao.live.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hn.library.base.BaseActivity;
import com.hn.library.base.baselist.BaseViewHolder;
import com.hn.library.base.baselist.CommRecyclerAdapter;
import com.hn.library.global.HnUrl;
import com.hn.library.http.HnHttpUtils;
import com.hn.library.http.HnResponseHandler;
import com.hn.library.utils.HnToastUtils;
import com.hotniao.live.HnMainActivity;
import com.hotniao.live.R;
import com.hotniao.live.eventbus.HomeNearEvent;
import com.hotniao.live.model.bean.AddressBean;
import com.hotniao.live.utils.HnLocationBiz;
import com.jakewharton.rxbinding2.view.RxView;
import com.loopj.android.http.RequestParams;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import io.reactivex.functions.Consumer;

/**
 * 首页-附近 设置地区
 * 作者：Mr.X
 * 时间：15:18
 */
public class HnAddressAct extends BaseActivity implements HnLocationBiz.OnLocationListener {


    @BindView(R.id.mTvCurTag)
    TextView mTvCurTag;
    @BindView(R.id.mTvCurAddress)
    TextView mTvCurAddress;
    @BindView(R.id.mRecycler)
    RecyclerView mRecycler;

    private List<AddressBean.DEntity> mDate = new ArrayList<>();
    private CommRecyclerAdapter mAdapter;
    private int type = 0;
    private String mAddress;
    private String mCity;
    private String mPro;

    @Override
    public int getContentViewId() {
        return R.layout.act_address;
    }

    @Override
    public void onCreateNew(Bundle savedInstanceState) {
        setTitle("设置地区");
        setShowBack(true);
        mBack.setImageResource(R.mipmap.ic_address_close);
        mTvCurAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String add = mTvCurAddress.getText().toString();
                if(!add.contains(".")){
                    EventBus.getDefault().post(new HomeNearEvent(add.substring(add.indexOf(" ")),add.substring(0,add.indexOf(" "))));
                    finish();

                }
            }
        });
    }

    @Override
    public void getInitData() {
        initLocation();
        mRecycler.setHasFixedSize(true);
        mRecycler.setLayoutManager(new LinearLayoutManager(this));
        mRecycler.setAdapter(mAdapter = new CommRecyclerAdapter() {
            @Override
            protected void onBindView(BaseViewHolder holder, final int position) {
                holder.setTextViewText(R.id.mTvAddress, TextUtils.isEmpty(mDate.get(position).getShortname()) ? mDate.get(position).getName() : mDate.get(position).getShortname());
                RxView.clicks(holder.getView(R.id.mTvAddress))
                        .throttleFirst(3, TimeUnit.SECONDS)
                        .subscribe(new Consumer<Object>() {
                            @Override
                            public void accept(Object o) throws Exception {
                                mTvCurAddress.setVisibility(View.GONE);
                                mTvCurTag.setVisibility(View.GONE);
                                switch (type) {
                                    case 1:
                                        requestData2(mDate.get(position).getId());
                                        mPro = mDate.get(position).getName();
                                        break;
                                    case 2:
                                        mCity = mDate.get(position).getName();
                                        EventBus.getDefault().post(new HomeNearEvent(mCity,mPro));
                                        finish();
//                                        requestData3(mDate.get(position).getId());
                                        break;
                                    case 3:
                                        EventBus.getDefault().post(new HomeNearEvent(mCity,mPro));
                                        finish();
                                        break;
                                }
                            }
                        });
            }

            @Override
            protected int getLayoutID(int position) {
                return R.layout.item_home_address;
            }

            @Override
            public int getItemCount() {
                return mDate.size();
            }
        });

        requestData();
    }

    private void requestData() {
        RequestParams param = new RequestParams();
        HnHttpUtils.postRequest(HnUrl.ADDRESS_PRO, param, TAG, new HnResponseHandler<AddressBean>(this, AddressBean.class) {
            @Override
            public void hnSuccess(String response) {
                if(mRecycler==null)return;
                mDate.clear();
                mDate.addAll(model.getD());
                mAdapter.notifyDataSetChanged();
                type = 1;
            }

            @Override
            public void hnErr(int errCode, String msg) {
                HnToastUtils.showToastShort(msg);
            }
        });
    }

    private void requestData2(String provinceId) {
        RequestParams param = new RequestParams();
        param.put("provinceId", provinceId);
        HnHttpUtils.postRequest(HnUrl.ADDRESS_CITY, param, TAG, new HnResponseHandler<AddressBean>(this, AddressBean.class) {
            @Override
            public void hnSuccess(String response) {
                if(mRecycler==null)return;
                mDate.clear();
                mDate.addAll(model.getD());
                mAdapter.notifyDataSetChanged();
                type = 2;
            }

            @Override
            public void hnErr(int errCode, String msg) {
                HnToastUtils.showToastShort(msg);
            }
        });
    }

    private void requestData3(String cityId) {
        RequestParams param = new RequestParams();
        param.put("cityId", cityId);
        HnHttpUtils.postRequest(HnUrl.ADDRESS_ARE, param, TAG, new HnResponseHandler<AddressBean>(this, AddressBean.class) {
            @Override
            public void hnSuccess(String response) {
                if(mRecycler==null)return;
                mDate.clear();
                mDate.addAll(model.getD());
                mAdapter.notifyDataSetChanged();
                type = 3;
            }

            @Override
            public void hnErr(int errCode, String msg) {
                HnToastUtils.showToastShort(msg);
            }
        });
    }


    private HnLocationBiz mHnLocationBiz;

    private void initLocation() {
        mHnLocationBiz = HnLocationBiz.getInsrance();
        mHnLocationBiz.setOnLocationListener(this);
        if (HnMainActivity.mLocEntity == null) {
            mHnLocationBiz.startLocation(this);
        } else {
            updateLocation(HnMainActivity.mLocEntity.getmProvince(), HnMainActivity.mLocEntity.getmCity());
        }

    }

    private void updateLocation(String province, String city) {
        mTvCurAddress.setText(province +" "+ city);
        if (TextUtils.isEmpty(city)) {
            mTvCurAddress.setTextColor(getResources().getColor(R.color.comm_text_color_black));
        } else {
            mTvCurAddress.setTextColor(getResources().getColor(R.color.comm_text_color_black_s));
        }
    }

    @Override
    public void onLocationSuccess(String province, String city, String address, String latitudeResult, String longitudeResult) {
        updateLocation(province, city);
    }

    @Override
    public void onLocationFail(String errorRease, int code) {
        updateLocation("无法获取定位...", "");
    }
}
