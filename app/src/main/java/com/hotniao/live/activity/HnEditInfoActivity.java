package com.hotniao.live.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hn.library.base.BaseActivity;
import com.hn.library.base.BaseRequestStateListener;
import com.hn.library.base.EventBusBean;
import com.hn.library.global.NetConstant;
import com.hn.library.http.HnHttpUtils;
import com.hn.library.http.HnResponseHandler;
import com.hn.library.http.UploadFileResponseModel;
import com.hn.library.loadstate.HnLoadingLayout;
import com.hn.library.utils.HnLogUtils;
import com.hn.library.utils.HnToastUtils;
import com.hn.library.view.FrescoImageView;
import com.hotniao.live.HnApplication;
import com.hotniao.live.R;
import com.hotniao.live.activity.bindPhone.HnFirstBindPhoneActivity;
import com.hotniao.live.activity.bindPhone.HnHaveBindPhoneActivity;
import com.hotniao.live.biz.user.userinfo.HnMineFragmentBiz;
import com.hn.library.global.HnConstants;
import com.hn.library.global.HnUrl;
import com.hotniao.live.dialog.HnEditSexDialog;
import com.hotniao.live.model.HnAuthDetailModel;
import com.hn.library.model.HnLoginModel;
import com.hn.library.model.HnLoginBean;
import com.hotniao.live.utils.HnUiUtils;
import com.hotniao.livelibrary.util.HnLiveLevelUtil;
import com.reslibrarytwo.HnSkinTextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.BindView;
import butterknife.OnClick;

import static com.hn.library.global.NetConstant.REQUEST_NET_ERROR;


/**
 * Copyright (C) 2017,深圳市红鸟网络科技股份有限公司 All rights reserved.
 * 项目名称：红鸟电商
 * 类描述：编辑个人资料
 * 创建人：阳石柏
 * 创建时间：2017/3/6 16:16
 * 修改人：阳石柏
 * 修改时间：2017/3/6 16:16
 * 修改备注：
 * Version:  1.0.0
 */
public class HnEditInfoActivity extends BaseActivity implements BaseRequestStateListener, HnLoadingLayout.OnReloadListener {

    @BindView(R.id.fiv_header)
    FrescoImageView fivHeader;
    @BindView(R.id.rl_header)
    RelativeLayout rlHeader;
    @BindView(R.id.tv_nick)
    TextView tvNick;
    @BindView(R.id.ll_info)
    LinearLayout llInfo;
    @BindView(R.id.loading)
    HnLoadingLayout loading;
    @BindView(R.id.mTvId)
    TextView mTvId;
    @BindView(R.id.tv_sig)
    TextView tvSig;
    @BindView(R.id.tv_sex)
    TextView tvSex;
    @BindView(R.id.mTvLv)
    HnSkinTextView mTvLv;
    @BindView(R.id.mTvAnchorLv)
    HnSkinTextView mTvAnchorLv;
    @BindView(R.id.mTvRealName)
    TextView mTvRealName;
    @BindView(R.id.mTvBind)
    TextView mTvBindView;
    @BindView(R.id.mRlAnchorLv)
    RelativeLayout mRlAnchorLv;
    @BindView(R.id.mLLAuthentication)
    LinearLayout mLLAuthentication;
    @BindView(R.id.mTvAuth)
    TextView mTvAuth;

    //我的业务逻辑类，用户处理我的相关业务
    private HnMineFragmentBiz mHnMineFragmentBiz;
    //个人用户信息数据对象
    private HnLoginBean result;
    //性别选择器
    private HnEditSexDialog mSexDialog;
    //实名认证状态  2是通过
    private String mRealNameState = "0";

    @OnClick({R.id.mLLAuthentication,R.id.rl_header, R.id.mRlNick, R.id.mRlSex, R.id.mRlIntro,
            R.id.mRlLv, R.id.mRlAnchorLv, R.id.mRlRealName, R.id.mRlBind})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.mLLAuthentication://实名
                if (result == null) return;
                switch (mRealNameState) {
                    case "1":
                    case "3":
                        openActivity(HnAuthStateActivity.class);
                        break;
                    case "2":
//                        startActivity(new Intent(this,HnAuthenticationActivity.class).putExtra("hadAuth",true));
                        break;
                    default:
                        startActivity(new Intent(this, HnAuthenticationActivity.class).putExtra("hadAuth", false));
                        break;
                }
                break;
            case R.id.rl_header://头像
                mHnMineFragmentBiz.updateHeader();
                break;
            case R.id.mRlNick://昵称
                String nick = tvNick.getText().toString();
                HnEditNickInfoActivity.launcher(this, "请输入昵称", TextUtils.isEmpty(nick) ? "" : nick, "1");
                break;
            case R.id.mRlSex://性别
                mSexDialog = HnEditSexDialog.newInstance();
                mSexDialog.show(getSupportFragmentManager(), "sex");
                break;
            case R.id.mRlIntro://签名
                String intro = HnApplication.getmUserBean().getUser_intro();
                HnEditNickInfoActivity.launcher(this, "设置您的个性签名", TextUtils.isEmpty(intro) ? "" :intro, "2");
                break;
            case R.id.mRlLv:
                HnWebActivity.luncher(HnEditInfoActivity.this,getString(R.string.user_level), HnUrl.USER_LEVEL_USER,HnWebActivity.Level);
                break;
            case R.id.mRlAnchorLv:
                HnWebActivity.luncher(HnEditInfoActivity.this,getString(R.string.zhubo_lv), HnUrl.USER_LEVEL_ANCHOR,HnWebActivity.Level);
                break;
            case R.id.mRlRealName:
                if("0".equals(mRealNameState)){
                    openActivity(HnAuthenticationActivity.class);
                }else if(!"2".equals(mRealNameState)){
                    openActivity(HnAuthStateActivity.class);
                }
                break;
            case R.id.mRlBind:
                if(TextUtils.isEmpty(result.getUser_phone())){
                    openActivity(HnFirstBindPhoneActivity.class);
                }else {
                    HnHaveBindPhoneActivity.luncher(HnEditInfoActivity.this,result.getUser_phone());
                }
                break;
        }
    }


    @Override
    public int getContentViewId() {
        return R.layout.activity_edit_info;
    }

    @Override
    public void onCreateNew(Bundle savedInstanceState) {
        loading.setStatus(HnLoadingLayout.Loading);
        loading.setOnReloadListener(this);
        setShowBack(true);
        setTitle(R.string.edit_user_info);
        EventBus.getDefault().register(this);
        mHnMineFragmentBiz = new HnMineFragmentBiz(this);
        mHnMineFragmentBiz.setBaseRequestStateListener(this);



    }

    @Override
    protected void onResume() {
        super.onResume();
        getRealNameState();
    }

    @Override
    public void getInitData() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            result = (HnLoginBean) bundle.getSerializable(HnConstants.Intent.DATA);
            if (result != null) {
                loading.setStatus(HnLoadingLayout.Success);
                updateUi();
            } else {
                mHnMineFragmentBiz.requestToUserInfo();
            }
        }
    }


    /**
     * 请求中
     */
    @Override
    public void requesting() {
        showDoing(getResources().getString(R.string.loading), null);
    }

    /**
     * 请求成功
     *
     * @param type
     * @param response
     * @param obj
     */
    @Override
    public void requestSuccess(String type, String response, Object obj) {
        if (loading == null) return;
        done();
        if (HnLoadingLayout.Success != loading.getStatus()) {
            loading.setStatus(HnLoadingLayout.Success);
        }
        if ("upload_user_header".equals(type)) {//上传七牛
            UploadFileResponseModel model = (UploadFileResponseModel) obj;
            if (model != null) {
                HnToastUtils.showToastShort(HnUiUtils.getString(R.string.upload_succeed));
                fivHeader.setImageURI(NetConstant.FILE_SERVER + model.getUrl());
                EventBus.getDefault().post(new EventBusBean(0, HnConstants.EventBus.Update_User_Header, model.getUrl()));
            }
        } else if ("user_info".equals(type)) {//获取用户信息
            HnLoginModel model = (HnLoginModel) obj;
            if (model != null && model.getD() != null && model.getD().getUser_id() != null) {
                result = model.getD();
                updateUi();
            } else {
                loading.setStatus(HnLoadingLayout.Empty);
            }
        } else if ("save_nick".equals(type)) {//保存昵称
            String nick = (String) obj;
            tvNick.setText(nick);
            EventBus.getDefault().post(new EventBusBean(0, HnConstants.EventBus.Update_User_Nick, nick));
        } else if ("save_intro".equals(type)) {//保存签名
            String intro = (String) obj;
            if (!TextUtils.isEmpty(intro)) {
                tvSig.setText(intro);
                EventBus.getDefault().post(new EventBusBean(0, HnConstants.EventBus.Update_User_Intro, intro));
            }
        } else if ("save_avator".equals(type)) {//上传头像到自己的服务器成功
            String key = (String) obj;
            HnLogUtils.i(TAG, "key：" + key);
            if (!TextUtils.isEmpty(key)) {
                HnToastUtils.showToastShort(HnUiUtils.getString(R.string.upload_succeed));
                fivHeader.setImageURI(key);
                EventBus.getDefault().post(new EventBusBean(0, HnConstants.EventBus.Update_User_Header, key));
            }
        }


    }

    /**
     * \
     * 请求失败
     *
     * @param type
     * @param code
     * @param msg
     */
    @Override
    public void requestFail(String type, int code, String msg) {
        if (loading == null) return;
        done();
        if ("upload_user_header".equals(type)) {//上传七牛
            HnToastUtils.showToastShort(HnUiUtils.getString(R.string.upload_fail));
        } else if ("user_info".equals(type)) {//获取用户信息
            if (REQUEST_NET_ERROR == code) {
                loading.setStatus(HnLoadingLayout.No_Network);
            } else {
                loading.setStatus(HnLoadingLayout.Error);
            }
        } else if ("save_nick".equals(type) || "save_intro".equals(type)) {//保存昵称/签名
            HnToastUtils.showToastShort(msg);
        } else if ("upload_pic_file".equals(type)) {//上传身份证照
            HnToastUtils.showToastShort(HnUiUtils.getString(R.string.upload_fail));
        } else if ("get_qiniu_token".equals(type)) {//获取七牛token
            HnToastUtils.showToastShort(msg);
        }
    }

    /**
     * 更新界面ui
     */
    private void updateUi() {
        if(isFinishing()||result==null)return;

        //头像
        String logo = result.getUser_avatar();
        fivHeader.setImageURI(logo);
        //昵称
        String nick = result.getUser_nickname();
        tvNick.setText(nick);
        //性别
        String sex = result.getUser_sex();
        if ("1".equals(sex)) {//男
            tvSex.setText("男");
        } else {
            tvSex.setText("女");
        }
        //签名
        String intro = result.getUser_intro();
        if (!TextUtils.isEmpty(intro)) {
            tvSig.setText(intro);
        }

        mTvId.setText(result.getUser_id());
        HnLiveLevelUtil.setAudienceLevBg(mTvLv, result.getUser_level(), true);

        if(TextUtils.isEmpty( result.getAnchor_level())||1>Integer.parseInt( result.getAnchor_level())){
            mRlAnchorLv.setVisibility(View.GONE);
        }else {
            mRlAnchorLv.setVisibility(View.GONE);
            mTvAnchorLv.setText("Lv"+ result.getAnchor_level());
        }


        if (TextUtils.isEmpty(result.getUser_phone())) {
            mTvBindView.setText(R.string.no_bind);
        } else {
            mTvBindView.setText(R.string.have_bind);
        }

    }


    @Override
    public void onReload(View v) {
        mHnMineFragmentBiz.requestToUserInfo();
    }

    @Subscribe
    public void onEventBusCallBack(EventBusBean event) {
        if (event != null) {
            if (HnConstants.EventBus.Sava_Nick.equals(event.getType())) {//更新昵称
                String nick = (String) event.getObj();
                if (TextUtils.isEmpty(nick)) return;
                mHnMineFragmentBiz.requestToSavaNick(nick);
            } else if (HnConstants.EventBus.Sava_Intro.equals(event.getType())) {//更新用户签名
                String intro = (String) event.getObj();
                mHnMineFragmentBiz.requestToSavaIntro(intro);
            } else if (HnConstants.EventBus.Update_User_Sex.equals(event.getType())) {//更新用户性别
                String sex = (String) event.getObj();
                if ("1".equals(sex)) {//男
                    tvSex.setText("男");
                } else {
                    tvSex.setText("女");
                }
            }
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if(result!=null)result.setUser_nickname(HnApplication.mUserBean.getUser_nickname());
            tvNick.setText(TextUtils.isEmpty(HnApplication.mUserBean.getUser_nickname()) ? "" : HnApplication.mUserBean.getUser_nickname());
        } else if (requestCode == 2) {
            if(result!=null)result.setUser_intro(HnApplication.mUserBean.getUser_intro());
            tvSig.setText(TextUtils.isEmpty(HnApplication.mUserBean.getUser_intro()) ? "" :HnApplication.mUserBean.getUser_intro());
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    private void getRealNameState() {
        HnHttpUtils.postRequest(HnUrl.CERTIFICATION_CHECK, null, TAG, new HnResponseHandler<HnAuthDetailModel>(HnAuthDetailModel.class) {
            @Override
            public void hnSuccess(String response) {
                if (isFinishing()) return;
                if (model.getD() == null) return;
                if (model.getC() == 0) {
                    HnAuthDetailModel.DBean d = model.getD();

                    if ("Y".equals(d.getIs_submit())) {
                        if ("C".equals(d.getUser_certification_status())) {//用户实名认证状态，C：核对中(Cache)，Y：通过，N：不通过
                            mRealNameState = "1";
                            mTvRealName.setText(R.string.applying);
                            mTvAuth.setText(R.string.applying);
                        } else if ("Y".equals(d.getUser_certification_status())) {
                            mRealNameState = "2";
                            mTvRealName.setText(R.string.certified);
                            mTvAuth.setText(R.string.certified);
                            mTvAuth.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
                        } else if ("N".equals(d.getUser_certification_status())) {
                            mRealNameState = "3";
                            mTvRealName.setText(R.string.certified_fail);
                            mTvAuth.setText(R.string.certified_fail);
                        }
                    } else {
                        mTvRealName.setText(R.string.no_apply);
                        mTvAuth.setText(R.string.no_apply);
                        mRealNameState = "0";
                    }


                } else {
                    HnToastUtils.showToastShort(model.getM());
                }
            }

            @Override
            public void hnErr(int errCode, String msg) {
                HnToastUtils.showToastShort(msg);
            }
        });
    }

}
