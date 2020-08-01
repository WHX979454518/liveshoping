package com.live.shoplib.ui.dialog;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
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
import com.live.shoplib.bean.LogisticsModel;
import com.live.shoplib.bean.OrderRefreshEvent;
import com.live.shoplib.bean.ShopLiveSearchModel;
import com.loopj.android.http.RequestParams;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.functions.Consumer;


/**
 * 作者：Mr.X
 * 时间：10:48
 */
@RequiresApi(api = Build.VERSION_CODES.HONEYCOMB)
@SuppressLint("ValidFragment")
public class LogisticsDialog extends DialogFragment {

    private static LogisticsDialog dialog;

    private RelativeLayout mRlBg;
    private TextView mTvLogName;
    private EditText mEdId;
    private TextView mTvSure;
    private RecyclerView mRecycler;
    private String order_id;
    private String refend_id;
    private String code_id;
    private String shippe_id;
    private int  type;//1 退货  2   发货
    private List<LogisticsModel.DEntity> mData = new ArrayList<>();
    private CommRecyclerAdapter mAdapter;
    private boolean isRefund = false;

    public static LogisticsDialog newInstance(String order_id, String refend_id, int type) {
        dialog = new LogisticsDialog(order_id, refend_id,type);
        return dialog;
    }


    @SuppressLint("ValidFragment")
    public LogisticsDialog(String order_id, String refend_id, int type) {
        this.order_id = order_id;
        this.refend_id = refend_id;
        this.type=type;
        if (!TextUtils.isEmpty(refend_id)) isRefund = true;
    }

    @RequiresApi(api = Build.VERSION_CODES.HONEYCOMB)
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        View view = inflater.inflate(R.layout.dialog_log, null);

        mRlBg = view.findViewById(R.id.mRlBg);
        mTvLogName = view.findViewById(R.id.mTvLogName);
        mEdId = view.findViewById(R.id.mEdId);
        mTvSure = view.findViewById(R.id.mTvSure);
        mRecycler = view.findViewById(R.id.mRecycler);
        if(1==type){
            mTvLogName.setText("请选择退货物流");
        }else {
            mTvLogName.setText("请选择发货物流");
        }

        mRlBg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mRecycler.getVisibility() == View.VISIBLE) {
                    mRecycler.setVisibility(View.GONE);
                } else {
                    dismiss();
                }
            }
        });

        mTvLogName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mRecycler.setVisibility(View.VISIBLE);
            }
        });
        mRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecycler.setAdapter(mAdapter = new CommRecyclerAdapter() {
            @Override
            protected void onBindView(BaseViewHolder holder, final int position) {
                final TextView mTvMsg = holder.getView(R.id.mTvMsg);
                mTvMsg.setText(mData.get(position).getName());
                mTvMsg.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mRecycler.setVisibility(View.GONE);
                        shippe_id = mData.get(position).getCode();
                        mTvLogName.setText(mData.get(position).getName());
                    }
                });
            }

            @Override
            protected int getLayoutID(int position) {
                return R.layout.item_log_msg;
            }

            @Override
            public int getItemCount() {
                return mData.size();
            }
        });

        mTvSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                code_id = mEdId.getText().toString().trim();
                if (TextUtils.isEmpty(order_id)&&TextUtils.isEmpty(refend_id)) {
                    HnToastUtils.showToastShort("暂无退货编号");
                    return;
                } else if (TextUtils.isEmpty(shippe_id)) {
                    HnToastUtils.showToastShort("请选择物流");
                    return;
                } else if (TextUtils.isEmpty(code_id)) {
                    HnToastUtils.showToastShort("请填写单号");
                    return;
                } else {
                    requestRefend(code_id, order_id, refend_id, shippe_id);
                }
            }
        });
        requestData();
        Dialog dialog = new Dialog(getActivity(), R.style.PXDialog);
        dialog.setContentView(view);
        Window window = dialog.getWindow();
        window.setGravity(Gravity.BOTTOM);
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = -1;
        params.height = -1;
        window.setAttributes(params);
        return dialog;
    }


    private void requestData() {
        RequestParams param = new RequestParams();
        HnHttpUtils.postRequest(HnUrl.REFUND_COMPANY, param, "物流公司", new HnResponseHandler<LogisticsModel>(LogisticsModel.class) {
            @Override
            public void hnSuccess(String response) {
                if (mRecycler == null || model.getD() == null) return;
                mData.addAll(model.getD());
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void hnErr(int errCode, String msg) {
                HnToastUtils.showToastShort(msg);
            }
        });
    }

    private void requestRefend(String code, String order_id, String refend_id, String shipper_code) {
        RequestParams param = new RequestParams();
        param.put("code", code);
        if (!isRefund) param.put("order_id", order_id);
        if (isRefund) param.put("refund_id", refend_id);
        param.put("shipper_code", shipper_code);
        HnHttpUtils.postRequest(isRefund ? HnUrl.REFUND_GOODS_SHOP : HnUrl.SEND_GOODS, param, "提交", new HnResponseHandler<BaseResponseModel>(BaseResponseModel.class) {
            @Override
            public void hnSuccess(String response) {
                HnToastUtils.showToastShort("提交成功");
                EventBus.getDefault().post(new OrderRefreshEvent(-1));
                dismiss();
            }

            @Override
            public void hnErr(int errCode, String msg) {
                HnToastUtils.showToastShort(msg);
            }
        });
    }


}
