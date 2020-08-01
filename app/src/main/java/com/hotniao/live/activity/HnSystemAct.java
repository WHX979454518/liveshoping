package com.hotniao.live.activity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.google.gson.Gson;
import com.hn.library.base.EventBusBean;
import com.hn.library.base.baselist.BaseViewHolder;
import com.hn.library.base.baselist.CommRecyclerAdapter;
import com.hn.library.global.HnConstants;
import com.hn.library.global.HnUrl;
import com.hn.library.http.HnResponseHandler;
import com.hn.library.refresh.PtrFrameLayout;
import com.hn.library.utils.HnDateUtils;
import com.hn.library.utils.HnRefreshDirection;
import com.hn.library.utils.HnToastUtils;
import com.hn.library.view.FrescoImageView;
import com.hotniao.live.R;
import com.hotniao.live.activity.withdraw.HnWithDrawDetailActivity;
import com.hotniao.live.adapter.HnSystemMsgAdapter;
import com.hotniao.live.model.GetSystemMsgModel;
import com.hotniao.live.model.HnSysMsgDetailInfo;
import com.hotniao.live.model.bean.GetSystemMsg;
import com.hotniao.live.utils.HnUiUtils;
import com.hotniao.livelibrary.config.HnLiveConstants;
import com.hotniao.livelibrary.model.event.HnLiveEvent;
import com.hotniao.livelibrary.model.event.HnReceiverSysMsgEvent;
import com.hotniao.livelibrary.util.DataTimeUtils;
import com.loopj.android.http.RequestParams;
import com.reslibrarytwo.CommListActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：Mr.Xu
 * 时间：2018/1/23
 */
@Route(path = "/app/HnSystemAct")
public class HnSystemAct extends CommListActivity {

    private CommRecyclerAdapter mAdapter;
    private List<GetSystemMsg.SystemDialogEntity> mData = new ArrayList<>();
    private String mDialogId;
    private String unread_msg = "0";

    @Override
    protected String setTitle() {
        if(!EventBus.getDefault().isRegistered(this))EventBus.getDefault().register(this);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            unread_msg = bundle.getString("unread_msg");
        }
        return "系统消息";
    }

    @Override
    protected CommRecyclerAdapter setAdapter() {
        setShowSubTitle(true);
        mSubtitle.setVisibility(View.GONE);
        return mAdapter = new CommRecyclerAdapter() {
            @Override
            protected void onBindView(final BaseViewHolder holder, final int position) {

                TextView mUserTime = holder.getView(R.id.user_time);
                TextView mTvDes = holder.getView(R.id.tv_des);
                TextView mUserContent = holder.getView(R.id.user_content);
                FrescoImageView mUserHeader = holder.getView(R.id.user_header);

                if (mUserTime.getVisibility() == View.GONE) {
                    mUserTime.setVisibility(View.VISIBLE);
                }

                String update_time = mData.get(position).getTime();
                if (!TextUtils.isEmpty(update_time)) {
                    long l = Long.parseLong(update_time);
                    mUserTime.setText(DataTimeUtils.getTimestampString(l * 1000));
                }

                mUserHeader.setImageURI(Uri.parse("res:///" + R.drawable.system_image));
                mTvDes.setVisibility(View.GONE);

                try {
                    final HnSysMsgDetailInfo info = new Gson().fromJson(mData.get(position).getMsg(), HnSysMsgDetailInfo.class);
                    if (info != null) {
                        mUserContent.setText(info.getData().getContent());
                        mUserContent.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                if ("follow".equals(info.getType())) {

                                } else if ("certification".equals(info.getType())) {
                                    startActivity(new Intent(HnSystemAct.this, HnAuthStateActivity.class));
                                } else if ("withdraw".equals(info.getType())) {
                                    HnWithDrawDetailActivity.luncher(HnSystemAct.this, info.getData().getWithdraw_log_id(), HnWithDrawDetailActivity.Detail);
                                } else if ("general".equals(info.getType())) {
                                    if (!TextUtils.isEmpty(info.getData().getUrl()))
                                        HnWebActivity.luncher(HnSystemAct.this, HnUiUtils.getString(R.string.app_name)
                                                , info.getData().getUrl(), HnWebActivity.Banner);
                                }
                            }
                        });
                    }
                } catch (Exception e) {
                    mUserContent.setText(mData.get(position).getMsg());
                }
            }

            @Override
            protected int getLayoutID(int position) {
                return R.layout.item_system_msg;
            }

            @Override
            public int getItemCount() {
                return mData.size();
            }
        };

    }

    @Override
    protected RequestParams setRequestParam() {
        mSpring.setMode(PtrFrameLayout.Mode.BOTH);
        RequestParams params = new RequestParams();
        params.put("type", "system");
//        if (!TextUtils.isEmpty(mDialogId)) params.put("dialog_id", mDialogId);
        return params;
    }

    @Override
    protected String setRequestUrl() {
        return HnUrl.SYSTEM_MESSAGE;
    }



    @Override
    protected HnResponseHandler setResponseHandler(final HnRefreshDirection state) {
        return new HnResponseHandler<GetSystemMsgModel>(GetSystemMsgModel.class) {
            @Override
            public void hnSuccess(String response) {
                if (isFinishing()) return;
                refreshFinish();
                if (model.getD().getSystem_dialog() == null) {
                    setEmpty("暂无消息", com.live.shoplib.R.drawable.empty_com);
                    return;
                }
//                if (model.getD().getSystem_dialog().size() > 0)
//                    mDialogId = model.getD().getSystem_dialog().get(model.getD().getSystem_dialog().size() - 1).getDialog_id();
//                else mDialogId = "";
                if (HnRefreshDirection.TOP == state) {
                    mData.clear();
                }
                mData.addAll(model.getD().getSystem_dialog());
                if (mAdapter != null)
                    mAdapter.notifyDataSetChanged();
                setEmpty("暂无消息", com.live.shoplib.R.drawable.empty_com);
            }

            @Override
            public void hnErr(int errCode, String msg) {
                if (isFinishing()) return;
                refreshFinish();
                HnToastUtils.showToastShort(msg);
                setEmpty("暂无消息", com.live.shoplib.R.drawable.empty_com);
            }
        };
    }

    /**
     * 接收到webscoket推送过来的系统消息数据
     *
     * @param event
     */
    @Subscribe
    public void receiverSystemMsgEvent(final HnReceiverSysMsgEvent event) {
        if (event != null) {
            if ("system_msg".equals(event.getType())) {
                getInitData();
            }
        }

    }

    @Override
    protected void onDestroy() {
        //该通知用于直播间
        EventBus.getDefault().post(new HnLiveEvent(0, HnLiveConstants.EventBus.Clear_Unread, unread_msg));
        EventBus.getDefault().post(new HnLiveEvent(0, HnLiveConstants.EventBus.Reset_Data, 0));
        //该通知用于消息fragment
        EventBus.getDefault().post(new EventBusBean(0, HnConstants.EventBus.Clean_Unread, "0"));
//        mHnSystemMsgBiz.requestToExitSysMsgDetail();
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
