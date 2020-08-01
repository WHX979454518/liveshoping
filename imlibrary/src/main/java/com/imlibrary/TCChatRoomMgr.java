package com.imlibrary;

import android.text.TextUtils;
import android.util.Log;

import com.imlibrary.constant.TCConstants;
import com.imlibrary.constant.TLSConstant;
import com.imlibrary.loginAndRegister.TCLoginMgr;
import com.tencent.TIMCallBack;
import com.tencent.TIMConversation;
import com.tencent.TIMConversationType;
import com.tencent.TIMElem;
import com.tencent.TIMElemType;
import com.tencent.TIMGroupManager;
import com.tencent.TIMGroupSystemElem;
import com.tencent.TIMGroupSystemElemType;
import com.tencent.TIMManager;
import com.tencent.TIMMessage;
import com.tencent.TIMMessageListener;
import com.tencent.TIMTextElem;
import com.tencent.TIMUserProfile;
import com.tencent.TIMValueCallBack;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.List;

/**
 * Created by teckjiang on 2016/8/4
 */
public class TCChatRoomMgr implements TIMMessageListener {

    public static final String TAG = TCChatRoomMgr.class.getSimpleName();
    private TIMConversation mGroupConversation;
    private TCChatRoomListener mTCChatRoomListener;
    private static String mRoomId;


    private TCChatRoomMgr() {

    }

//    private static class TCChatRoomMgrHolder {
//        private static
//    }

    public static TCChatRoomMgr getInstance(String roomId) {
        mRoomId = roomId;
        TCChatRoomMgr instance = new TCChatRoomMgr();
        return instance;
    }


    /**
     * 发送消息
     *
     * @param cmd   控制符（代表不同的消息类型）具体查看TCContants.IMCMD开头变量
     * @param param 参数
     */
    private void sendMessage(int cmd, String param) {

        sendMessage(cmd, param, new TIMValueCallBack<TIMMessage>() {
            @Override
            public void onError(int i, String s) {
                if (null != mTCChatRoomListener)
                    mTCChatRoomListener.onSendMsgCallback(-1, null);
            }

            @Override
            public void onSuccess(TIMMessage timMessage) {
                if (null != mTCChatRoomListener)
                    mTCChatRoomListener.onSendMsgCallback(0, timMessage);
            }
        });
    }

    /**
     * 观看者退出退出群组接口
     *
     * @param roomId 群组Id
     */
    public void quitGroup(final String roomId) {
//        sendMessage(TLSConstant.IMCMD_EXIT_LIVE, "");
        TIMGroupManager.getInstance().quitGroup(roomId, new TIMCallBack() {
            @Override
            public void onError(int i, String s) {
                Log.d(TAG, "quitGroup failed, code:" + i + ",msg:" + s);
//                    if(null != mTCChatRoomListener) {
//                        mTCChatRoomListener.onQuitGroupCallback(i, s);
//                    }
//                mTCChatRoomListener = null;
//                mGroupConversation = null;
                //TIMManager.getInstance().deleteConversation(TIMConversationType.Group, roomId);
            }

            @Override
            public void onSuccess() {
                Log.d(TAG, "quitGroup success, groupid:" + roomId);
            }
        });
    }

    /**
     * 主播创建直播群组接口
     */
    public void createGroup() {
        //在特殊情况下未接收到kick out消息下会导致创建群组失败，在登录前做监测
        checkLoginState(new TCLoginMgr.TCLoginCallback() {
            @Override
            public void onSuccess() {
                TCLoginMgr.getInstance().removeTCLoginCallback();
                //用户登录，创建直播间
                TIMGroupManager.getInstance().createAVChatroomGroup("TVShow", new TIMValueCallBack<String>() {
                    @Override
                    public void onError(int code, String msg) {
                        if (null != mTCChatRoomListener)
                            mTCChatRoomListener.onJoinGroupCallback(code, msg);
                    }

                    @Override
                    public void onSuccess(String roomId) {
                        mRoomId = roomId;
                        mGroupConversation = TIMManager.getInstance().getConversation(TIMConversationType.Group, roomId);
                        if (null != mTCChatRoomListener)
                            mTCChatRoomListener.onJoinGroupCallback(0, roomId);
                    }
                });
            }

            @Override
            public void onFailure(int code, String msg) {
                TCLoginMgr.getInstance().removeTCLoginCallback();
                if (null != mTCChatRoomListener)
                    mTCChatRoomListener.onJoinGroupCallback(TCConstants.NO_LOGIN_CACHE, "no login cache, user has been kicked out");
            }
        });

    }

    /**
     * 登录态检测
     * 在进行createRoom/joinGroup操作时检测用户是否处于登录态，若不处于登录态执行login操作
     *
     * @param tcLoginCallback 登录回调
     */
    private void checkLoginState(TCLoginMgr.TCLoginCallback tcLoginCallback) {

        TCLoginMgr tcLoginMgr = TCLoginMgr.getInstance();
        if (TextUtils.isEmpty(TIMManager.getInstance().getLoginUser())) {
            tcLoginMgr.setTCLoginCallback(tcLoginCallback);
            tcLoginMgr.checkCacheAndLogin();
        } else {
            //已经处于登录态直接进行回调
            if (null != tcLoginCallback)
                tcLoginCallback.onSuccess();
        }
    }


    /**
     * 主播端退出前删除直播群组
     */
    public void deleteGroup() {
        sendMessage(TCConstants.IMCMD_EXIT_LIVE, "");
        if (mRoomId == null)
            return;
        TIMManager.getInstance().deleteConversation(TIMConversationType.Group, mRoomId);
        TIMGroupManager.getInstance().deleteGroup(mRoomId, new TIMCallBack() {
            @Override
            public void onError(int code, String msg) {
                Log.d(TAG, "delete av group failed. code: " + code + " errmsg: " + msg);
//                if (null != mTCChatRoomListener)
//                    mTCChatRoomListener.onQuitGroupCallback(code, msg);
                mRoomId = null;
                mTCChatRoomListener = null;
            }

            @Override
            public void onSuccess() {
                Log.d(TAG, "delete av group succ. groupid: " + mRoomId);
//                if (null != mTCChatRoomListener)
//                    mTCChatRoomListener.onQuitGroupCallback(0, mRoomId);
                mRoomId = null;
                mTCChatRoomListener = null;
            }
        });
    }

    /**
     * 加入群组
     *
     * @param roomId 群组ID
     */
    public void joinGroup(final String roomId) {
        //在特殊情况下未接收到kick out消息下会导致创建群组失败，在登录前做监测
        checkLoginState(new TCLoginMgr.TCLoginCallback() {
            @Override
            public void onSuccess() {
                TCLoginMgr.getInstance().removeTCLoginCallback();
                //用户登录，加入房间
                mRoomId = roomId;
                TIMGroupManager.getInstance().applyJoinGroup(roomId, "", new TIMCallBack() {
                    @Override
                    public void onError(int i, String s) {
                        Log.d(TAG, "joingroup failed, code:" + i + ",msg:" + s);
                        mRoomId = null;
                        if (null != mTCChatRoomListener)
                            mTCChatRoomListener.onJoinGroupCallback(i, s);
                        else
                            Log.d(TAG, "mPlayerListener not init");
                    }

                    @Override
                    public void onSuccess() {
                        Log.d(TAG, "joingroup success, groupid:" + roomId);
                        mGroupConversation = TIMManager.getInstance().getConversation(TIMConversationType.Group, roomId);
                        //加入群组成功后，发送加入消息
//                        sendMessage(TCConstants.IMCMD_ENTER_LIVE, "");
                        if (null != mTCChatRoomListener) {
                            mTCChatRoomListener.onJoinGroupCallback(0, roomId);
                        } else {
                            Log.d(TAG, "mPlayerListener not init");
                        }
                    }
                });
            }

            @Override
            public void onFailure(int code, String msg) {
                Log.d(TAG, "relogin fail. code: " + code + " errmsg: " + msg);
                TCLoginMgr.getInstance().imLogin(TCConstants.getIdentify(), TCConstants.getUserSig(), TCConstants.getAppid(), TCConstants.getType());
                TCLoginMgr.getInstance().removeTCLoginCallback();
                if (null != mTCChatRoomListener)
                    mTCChatRoomListener.onJoinGroupCallback(TCConstants.NO_LOGIN_CACHE, "no login cache, user has been kicked out");


            }
        });

    }

    /**
     * IMSDK消息回调接口
     *
     * @param list 消息列表
     * @return 默认情况下所有消息监听器都将按添加顺序被回调一次
     * 除非用户在OnNewMessages回调中返回true
     * 此时将不再继续回调下一个消息监听器，此处默认返回false
     */
    @Override
    public boolean onNewMessages(List<TIMMessage> list) {
        parseIMMessage(list);
        return false;
    }


    /**
     * 注入消息回调监听类
     * 需要实现并注入TCChatRoomListener才能获取相应消息回调
     *
     * @param listener
     */
    public void setMessageListener(TCChatRoomListener listener) {
        mTCChatRoomListener = listener;
        TIMManager.getInstance().addMessageListener(this);
    }

    /**
     * 解析TIM消息列表
     *
     * @param list 消息列表
     */
    private void parseIMMessage(List<TIMMessage> list) {

        if (list.size() > 0) {
            if (mGroupConversation != null)
                mGroupConversation.setReadMessage(list.get(0));
            Log.d(TAG, "TcIm parse-- " + list.get(0).timestamp());
        }
//        if (!bNeverLoadMore && (tlist.size() < mLoadMsgNum))
//            bMore = false;

        for (int i = list.size() - 1; i >= 0; i--) {
            TIMMessage currMsg = list.get(i);

            for (int j = 0; j < currMsg.getElementCount(); j++) {
                if (currMsg.getElement(j) == null)
                    continue;
                TIMElem elem = currMsg.getElement(j);
                TIMElemType type = elem.getType();
                String sendId = currMsg.getSender();
                TIMUserProfile timUserProfile = currMsg.getSenderProfile();

                //定制消息 -- 已弃用（不支持敏感词过滤）
                if (type == TIMElemType.Custom) {
                    continue;
                }

                //系统消息
                if (type == TIMElemType.GroupSystem) {
                    if (TIMGroupSystemElemType.TIM_GROUP_SYSTEM_DELETE_GROUP_TYPE == ((TIMGroupSystemElem) elem).getSubtype()) {
                        //群被解散
                        if (null != mTCChatRoomListener)
                            mTCChatRoomListener.onGroupDelete(((TIMGroupSystemElem) elem).getGroupId());
                    }
                }

                //其他群消息过滤

                if (currMsg.getConversation() != null && currMsg.getConversation().getPeer() != null)
                    //对于群组消息，过滤非本群的消息
                    if (TIMConversationType.Group == currMsg.getConversation().getType()
                            && mRoomId != null
                            && !mRoomId.equals(currMsg.getConversation().getPeer())) {
                        continue;
                    }

                //最后处理文本消息
                if (type == TIMElemType.Text) {
                    handleCustomTextMsg(elem, timUserProfile);
                }
            }
        }
    }

    /**
     * 退出房间
     */
    public void removeMsgListener() {
        mTCChatRoomListener = null;
        mGroupConversation = null;
        TIMManager.getInstance().removeMessageListener(this);
        TIMManager.getInstance().deleteConversation(TIMConversationType.Group, mRoomId);
    }

    /**
     * 发送消息
     *
     * @param msg              TIM消息
     * @param timValueCallBack 发送消息回调类
     */
    private void sendTIMMessage(TIMMessage msg, TIMValueCallBack<TIMMessage> timValueCallBack) {
        if (mGroupConversation != null)
            mGroupConversation.sendMessage(msg, timValueCallBack);
    }

    private void sendMessage(int cmd, String param, TIMValueCallBack<TIMMessage> timValueCallBack) {

        JSONObject sendJson = new JSONObject();
        String cmds = sendJson.toString();

        if (mGroupConversation != null)
            Log.i(TAG, "send cmd : " + cmd + "|" + cmds + "|" + mGroupConversation.toString());

        TIMMessage msg = new TIMMessage();

//        if (cmd == TCConstants.IMCMD_PAILN_TEXT) {

        TIMTextElem elem = new TIMTextElem();
        elem.setText(cmds);

        if (msg.addElement(elem) != 0) {
            Log.d(TAG, "addElement failed");
            return;
        }

        sendTIMMessage(msg, timValueCallBack);

    }

    /**
     * 处理定制消息 赞 加入 退出 弹幕 并执行相关回调
     *
     * @param elem 消息体
     */
    private void handleCustomTextMsg(TIMElem elem, TIMUserProfile timUserProfile) {
        //String customText = new String(((TIMCustomElem) elem).getData(), "UTF-8");
        if (elem.getType() != TIMElemType.Text)
            return;
        String jsonString = ((TIMTextElem) elem).getText();
        if (null != mTCChatRoomListener)
            mTCChatRoomListener.onReceiveMsg(0, jsonString);

    }

    /**
     * 消息循环监听类
     */
    public interface TCChatRoomListener {

        /**
         * 加入群组回调
         *
         * @param code 错误码，成功时返回0，失败时返回相应错误码
         * @param msg  返回信息，成功时返回群组Id，失败时返回相应错误信息
         */
        void onJoinGroupCallback(int code, String msg);

        //void onGetGroupMembersList(int code, List<TIMUserProfile> result);

        /**
         * 发送消息结果回调
         *
         * @param code       错误码，成功时返回0，失败时返回相应错误码
         * @param timMessage 发送的TIM消息
         */
        void onSendMsgCallback(int code, TIMMessage timMessage);

        /**
         * 接受消息监听接口
         * 文本消息回调
         *
         * @param type    消息类型
         * @param content 内容
         */
        void onReceiveMsg(int type, String content);

        /**
         * 群组删除回调，在主播群组解散时被调用
         */
        void onGroupDelete(String groupId);
    }
}
