package com.hotniao.live.receiver;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.google.gson.Gson;
import com.hn.library.global.HnUrl;
import com.hn.library.http.HnHttpUtils;
import com.hn.library.http.HnResponseHandler;
import com.hn.library.manager.HnAppManager;
import com.hn.library.utils.HnToastUtils;
import com.hn.library.view.CommDialog;
import com.hotniao.live.HnApplication;
import com.hotniao.live.HnMainActivity;
import com.hotniao.live.R;
import com.hotniao.live.model.HnLiveNoticeEvent;
import com.hotniao.live.model.HnLoginEvent;
import com.hotniao.livelibrary.config.HnLiveConstants;
import com.hotniao.livelibrary.model.HnJPushMessageInfo;
import com.hotniao.live.model.HnStopLiveModel;
import com.hotniao.livelibrary.model.HnJPushNotice;
import com.hotniao.livelibrary.model.event.HnLiveEvent;
import com.hotniao.livelibrary.util.HnLiveSwitchDataUitl;
import com.hotniao.live.utils.HnUiUtils;
import com.hotniao.livelibrary.ui.anchor.activity.HnAnchorActivity;
import com.hotniao.livelibrary.ui.audience.activity.HnAudienceActivity;

import org.greenrobot.eventbus.EventBus;

import cn.jpush.android.api.JPushInterface;

/**
 * 自定义接收器
 * <p>
 * 如果不定义这个 Receiver，则：
 * 1) 默认用户会打开主界面
 * 2) 接收不到自定义消息
 */
public class MyReceiver extends BroadcastReceiver {
    private static final String TAG = "JIGUANG-Example";
    private Gson mGson;

    @SuppressLint("LongLogTag")
    @Override
    public void onReceive(final Context context, Intent intent) {
        try {
            Bundle bundle = intent.getExtras();

//            Log.e("########################**" +
//                    "", bundle.getString(JPushInterface.EXTRA_EXTRA) + "+" + intent.getAction());

            Log.e("########################**", bundle.getString(JPushInterface.EXTRA_EXTRA)
                    +" \n "+"测试alter："+bundle.getString(JPushInterface.EXTRA_ALERT) +" \n "+"bundler "+bundle.toString());
            if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())) {
                String regId = bundle.getString(JPushInterface.EXTRA_REGISTRATION_ID);
                //send the Registration Id to your server...

            } else if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())) {

            } else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())) {
                if (mGson == null) mGson = new Gson();
                String extras = bundle.getString(JPushInterface.EXTRA_EXTRA);//扩展字段
                String alert = bundle.getString(JPushInterface.EXTRA_ALERT);//扩展字段
                HnJPushNotice notice = mGson.fromJson(extras, HnJPushNotice.class);
                if ("anchor_preview_live".equals(notice.getType())) {
                    EventBus.getDefault().post(new HnLiveNoticeEvent(notice.getData().getTitle(), alert));
                }
            } else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
                String extras = bundle.getString(JPushInterface.EXTRA_EXTRA);//扩展字段
                String alert = bundle.getString(JPushInterface.EXTRA_ALERT);//扩展字段

                //打开自定义的Activity
                Log.e("########################", extras +" \n "+"测试alter："+alert +" \n "+"bundler "+bundle.toString());
                if (mGson == null) mGson = new Gson();
                final HnJPushMessageInfo info = mGson.fromJson(extras, HnJPushMessageInfo.class);
                if ("anchor_start_live".equals(info.getType())) {
                    if (info.getData() == null) return;
                    if (HnAnchorActivity.mIsLiveing) {
                        EventBus.getDefault().post(new HnLiveEvent(0, HnLiveConstants.EventBus.Liveing_Click_Notify, info));
                    } else if (HnAudienceActivity.mIsLiveing) {
                        EventBus.getDefault().post(new HnLiveEvent(0, HnLiveConstants.EventBus.Look_Live_Click_Notify, info));
                    } else {
                        HnLiveSwitchDataUitl.joinRoom(context, info.getData().getAnchor_user_id(), "");
                    }
                } else if ("anchor_preview_live".equals(info.getType())) {
                    HnJPushNotice notice = mGson.fromJson(extras, HnJPushNotice.class);
                    EventBus.getDefault().post(new HnLiveNoticeEvent(notice.getData().getTitle(), alert));
                } else {
                    Intent i = new Intent(context, HnMainActivity.class);
                    i.putExtras(bundle);
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    context.startActivity(i);
                }

            } else if (JPushInterface.ACTION_RICHPUSH_CALLBACK.equals(intent.getAction())) {
                //在这里根据 JPushInterface.EXTRA_EXTRA 的内容处理代码，比如打开新的Activity， 打开一个网页等..

            } else if (JPushInterface.ACTION_CONNECTION_CHANGE.equals(intent.getAction())) {
                boolean connected = intent.getBooleanExtra(JPushInterface.EXTRA_CONNECTION_CHANGE, false);
            }
        } catch (Exception e) {

        }

    }

}
