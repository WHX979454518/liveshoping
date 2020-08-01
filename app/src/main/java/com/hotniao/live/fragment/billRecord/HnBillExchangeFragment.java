package com.hotniao.live.fragment.billRecord;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hn.library.base.BaseFragment;
import com.hn.library.global.HnUrl;
import com.hn.library.http.HnHttpUtils;
import com.hn.library.http.HnResponseHandler;
import com.hn.library.http.RequestParam;
import com.hn.library.loadstate.HnLoadingLayout;
import com.hn.library.refresh.PtrClassicFrameLayout;
import com.hn.library.refresh.PtrDefaultHandler2;
import com.hn.library.refresh.PtrFrameLayout;
import com.hn.library.utils.HnUtils;
import com.hotniao.live.HnApplication;
import com.hotniao.live.R;
import com.hotniao.live.adapter.HnExchangeAdapter;
import com.hotniao.live.dialog.HnEarningTotalTypePopWindow;
import com.hotniao.live.model.HnExchangeListModel;
import com.hotniao.live.model.HnReceiveGiftLogModel;
import com.reslibrarytwo.HnSkinTextView;
import com.tencent.openqq.protocol.imsdk.msg;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

import static com.hn.library.global.NetConstant.RESPONSE_CODE_BAD;

/**
 * 兑换详情
 */
public class HnBillExchangeFragment extends BaseFragment {


    @BindView(R.id.mHnLoadingLayout)
    HnLoadingLayout mHnLoadingLayout;
    @BindView(R.id.recyclerview)
    RecyclerView mLvBillRec;
    @BindView(R.id.ptr_refresh)
    PtrClassicFrameLayout mSwipeRefresh;

    /**
     * 提现记录列表适配器
     */
    private HnExchangeAdapter mReceiveAdapter;
    /**
     * 页数
     */
    private int mPage = 1;
    /**
     * 时间类型
     */
    private String mDateType = HnEarningTotalTypePopWindow.DAY;

    private HnEarningTotalTypePopWindow mPopWindow;
    private TextView mTvTotal, mTvEmpty;
    private HnSkinTextView mTvType;

    private List<HnExchangeListModel.DEntity.ItemsEntity> mData = new ArrayList<>();

    public static HnBillExchangeFragment newInstance() {

        Bundle args = new Bundle();
        HnBillExchangeFragment fragment = new HnBillExchangeFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public int getContentViewId() {
        return R.layout.framgnet_bill_earning;
    }

    @Override
    public void initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //初始化适配器
        mReceiveAdapter = new HnExchangeAdapter(mData);
        mLvBillRec.setLayoutManager(new LinearLayoutManager(mActivity));
        mLvBillRec.setHasFixedSize(true);
        mLvBillRec.setAdapter(mReceiveAdapter);

        mHnLoadingLayout.setStatus(HnLoadingLayout.Loading);
        addHead();
    }


    /**
     * 添加头部
     */
    private void addHead() {
        View view = LayoutInflater.from(mActivity).inflate(R.layout.head_earning_total, null);
        mTvTotal = (TextView) view.findViewById(R.id.mTvTotal);
        mTvEmpty = (TextView) view.findViewById(R.id.mTvEmpty);
        mTvType = (HnSkinTextView) view.findViewById(R.id.mTvType);

        if (mPopWindow == null) {
            mPopWindow = new HnEarningTotalTypePopWindow(mActivity);
            mPopWindow.setOnItemClickListener(new HnEarningTotalTypePopWindow.OnItemClickListener() {
                @Override
                public void itemClick(String name, String type) {
                    mTvType.setText(name);
                    mDateType = type;
                    mPage = 1;
                    mHnLoadingLayout.setStatus(HnLoadingLayout.Loading);
                    initData();
                }

                @Override
                public void dismissLis() {
                    mTvType.setRightDrawable(R.drawable.account_lower);
                }
            });
        }
        mTvType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (mPopWindow == null) {
                    mPopWindow = new HnEarningTotalTypePopWindow(mActivity);
                }
                mPopWindow.showUp(v);
                mTvType.setRightDrawable(R.drawable.account_upper);

            }
        });
        mReceiveAdapter.addHeaderView(view);

    }

    @Override
    protected void initData() {

        RequestParam param = new RequestParam();
        param.put("page", mPage + "");
        param.put("date_type", mDateType + "");
        HnHttpUtils.getRequest(HnUrl.EXCHANGE_HIS, param, TAG, new HnResponseHandler<HnExchangeListModel>(mActivity, HnExchangeListModel.class) {

            @Override
            public void hnSuccess(String response) {

                try {
                    mSwipeRefresh.refreshComplete();
                    if (model.getC() == 0) {
                        mTvTotal.setText(HnUtils.setTwoPoints(model.getD().getTotal_dot()));
                        mHnLoadingLayout.setStatus(HnLoadingLayout.Success);
                        if (mPage == 1) {
                            mData.clear();
                        }
                        mData.addAll(model.getD().getItems());
                        if (mReceiveAdapter != null)
                            mReceiveAdapter.notifyDataSetChanged();
                        setEmpty();
                    }

                } catch (Exception e) {
                    hnErr(RESPONSE_CODE_BAD, "");
                    setEmpty();
                }


            }

            @Override
            public void hnErr(int errCode, String msg) {
                if (mActivity == null) return;
                mSwipeRefresh.refreshComplete();
                setEmpty();
            }
        });
    }

    private void setEmpty() {
        if (mTvEmpty == null) return;
        mHnLoadingLayout.setStatus(HnLoadingLayout.Success);
        mTvEmpty.setVisibility(mData.size() < 1 ? View.VISIBLE : View.GONE);
    }

    @Override
    protected void initEvent() {

        //刷新处理
        mSwipeRefresh.setPtrHandler(new PtrDefaultHandler2() {
            @Override
            public void onLoadMoreBegin(PtrFrameLayout frame) {
                mPage += 1;
                initData();
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                mPage = 1;
                initData();
            }
        });

        //错误重新加载
        mHnLoadingLayout.setOnReloadListener(new HnLoadingLayout.OnReloadListener() {
            @Override
            public void onReload(View v) {
                mPage = 1;
                mHnLoadingLayout.setStatus(HnLoadingLayout.Loading);
                initData();
            }
        });
    }


}
