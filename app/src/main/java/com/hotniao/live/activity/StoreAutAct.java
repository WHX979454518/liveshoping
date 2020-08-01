package com.hotniao.live.activity;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.hn.library.base.BaseActivity;
import com.hn.library.base.BaseRequestStateListener;
import com.hn.library.global.HnUrl;
import com.hn.library.http.BaseResponseModel;
import com.hn.library.http.HnHttpUtils;
import com.hn.library.http.HnResponseHandler;
import com.hn.library.manager.HnAppManager;
import com.hn.library.utils.HnLogUtils;
import com.hn.library.utils.HnRegexUtils;
import com.hn.library.utils.HnToastUtils;
import com.hn.library.view.FrescoImageView;
import com.hotniao.live.R;
import com.hotniao.live.biz.live.anchorAuth.HnAnchorAuthenticationBiz;
import com.hotniao.live.eventbus.StoreAutEvent;
import com.hotniao.live.model.AuthDetailsModel;
import com.hotniao.live.model.AuthStoreMsgModel;
import com.hotniao.live.model.FinishEvent;
import com.hotniao.live.model.HnAuthDetailModel;
import com.hotniao.live.model.TheStoreModel;
import com.hotniao.live.utils.HnUiUtils;
import com.live.shoplib.ShopActFacade;
import com.live.shoplib.ui.AuthSortAct;
import com.live.shoplib.ui.dialog.HelpAuthDialog;
import com.loopj.android.http.RequestParams;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 店铺认证
 * 作者：Mr.Xu
 * 时间：2017/12/25
 */
@Route(path = "/app/StoreAutAct")
public class StoreAutAct extends BaseActivity implements BaseRequestStateListener {

    @BindView(R.id.mTvState)
    TextView mTvState;
    @BindView(R.id.mTvAutMsg)
    TextView mTvAutMsg;
    @BindView(R.id.mState1)
    LinearLayout mState1;
    @BindView(R.id.mState2)
    TextView mState2;
    @BindView(R.id.mEdName)
    EditText mEdName;
    @BindView(R.id.mEdPhone)
    EditText mEdPhone;
    @BindView(R.id.mEdIdNumber)
    EditText mEdIdNumber;
    @BindView(R.id.mIvId1)
    FrescoImageView mIvId1;
    @BindView(R.id.mIvId2)
    FrescoImageView mIvId2;
    @BindView(R.id.mTvSort)
    TextView mTvSort;
    @BindView(R.id.mLLSort)
    LinearLayout mLLSort;
    @BindView(R.id.mIvCert1)
    FrescoImageView mIvCert1;
    @BindView(R.id.mIvCert2)
    FrescoImageView mIvCert2;
    @BindView(R.id.mTvHelp)
    TextView mTvHelp;

    @BindView(R.id.mTvFoods)
    TextView mTvFoods;
    @BindView(R.id.mLLFoods)
    LinearLayout mLLFoods;
    @BindView(R.id.mLLPermit)
    LinearLayout mLLPermit;

    @BindView(R.id.mBoxSure)
    CheckBox mBoxSure;


    @BindView(R.id.tv_sendcode)
    TextView tv_sendcode;
    @BindView(R.id.tv_shopname)
    EditText tv_shopname;
    @BindView(R.id.et_phone_verify_code)
    TextView et_phone_verify_code;



    @BindView(R.id.tv_submit_certification)
    TextView tv_submit_certification;
    TimeCount mTime;

    @BindView(R.id.show_rl)
    RelativeLayout show_rl;
    @BindView(R.id.edshow_rl)
    ScrollView edshow_rl;

    @BindView(R.id.tv_backtv)
    TextView tv_backtv;








    private final static String select_one = "select_one";
    private final static String select_two = "select_two";
    private final static String select_three = "select_three";
    private final static String select_four = "select_four";
    //身份证正面照
    private String onePath;
    //身份证反面照
    private String twoPath;
    private String threePath;
    private String fourPath;

    private HnAnchorAuthenticationBiz mHnAnchorAuthenticationBiz;

    private String storeId, storeName;
    private boolean again;
    private String certification_type;

    @Override
    public int getContentViewId() {
        return R.layout.act_shop_aut;
    }

    @SuppressLint("WrongViewCast")
    @Override
    public void onCreateNew(final Bundle savedInstanceState) {
        EventBus.getDefault().register(this);
        certification_type = getIntent().getStringExtra("certification_type");
        again = getIntent().getBooleanExtra("again", false);
        setTitle("店铺认证");
        setShowBack(true);
        mSubtitle.setVisibility(View.VISIBLE);
        mTime = new TimeCount(60000,1000);
        mSubtitle.setText("");
        mBoxSure.setChecked(true);
        mBoxSure.setEnabled(true);
        mSubtitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mSubtitle.getText().toString().equals("提交")) {
//                    String number = mEdIdNumber.getText().toString();
//                    String phone = mEdPhone.getText().toString();
//                    String name = mEdName.getText().toString();
//                    if (TextUtils.isEmpty(name)) {
//                        HnToastUtils.showToastShort("请输入您的姓名");
//                        return;
//                    }
//                    if (TextUtils.isEmpty(phone)) {
//                        HnToastUtils.showToastShort("请输入您的手机号码");
//                        return;
//                    }
//                    if (!HnRegexUtils.isMobileExact(phone)) {
//                        HnToastUtils.showToastShort("请输入正确的手机号码");
//                        return;
//                    }
//                    if (TextUtils.isEmpty(number)) {
//                        HnToastUtils.showToastShort("请输入您的身份证号");
//                        return;
//                    }
//                    if (TextUtils.isEmpty(onePath)) {
//                        HnToastUtils.showToastShort("请上传身份证正面照");
//                        return;
//                    }
//                    if (TextUtils.isEmpty(twoPath)) {
//                        HnToastUtils.showToastShort("请上传身份证反面照");
//                        return;
//                    }
//                    if (!"user_shop".equals(certification_type)) {
//                        if (TextUtils.isEmpty(threePath)) {
//                            HnToastUtils.showToastShort("请上传营业执照");
//                            return;
//                        }
//                    }
//
//                    if (!TextUtils.isEmpty(storeName) && storeName.contains("食品")) {
//                        if (TextUtils.isEmpty(fourPath)) {
//                            HnToastUtils.showToastShort("请上传食品流通许可证");
//                            return;
//                        }
//                    }
//
//                    if (!HnRegexUtils.isIDCard18(number)) {
//                        HnToastUtils.showToastShort(HnUiUtils.getString(R.string.incorr_idcard));
//                        return;
//                    }
//
//                    if (!mBoxSure.isChecked()) {
//                        HnToastUtils.showToastShort("请先阅读并同意协议");
//                        return;
//                    }
//
//                    request(onePath, twoPath, threePath, fourPath,
//                            number, phone, name,
//                            storeId, storeName, certification_type);
                } else {
                    ShopActFacade.openStoreAut("", true);
                }

            }
        });
        mHnAnchorAuthenticationBiz = new HnAnchorAuthenticationBiz(this);
        mHnAnchorAuthenticationBiz.setLoginListener(this);


        if (TextUtils.isEmpty(certification_type)) {
            //TODO 说明是已申请认证的
            mSubtitle.setVisibility(View.GONE);
            mState2.setVisibility(View.GONE);
            mEdName.setEnabled(false);
            mBoxSure.setChecked(true);
            mBoxSure.setEnabled(false);
            mEdPhone.setEnabled(false);
            mEdIdNumber.setEnabled(false);
            mIvId1.setEnabled(false);
            mIvId2.setEnabled(false);
            mIvCert1.setEnabled(true);
            mLLSort.setEnabled(true);
            mIvCert2.setEnabled(false);
//            requestMsg();
//            getRealNameState();
        }
        if ("user_shop".equals(certification_type)) {
            mLLPermit.setVisibility(View.GONE);
        }else {
            mLLPermit.setVisibility(View.GONE);
        }

        getRealNameState();

    }


    private void getRealNameState() {
        HnHttpUtils.postRequest(HnUrl.CERTIFICATION_DETAIL, null, TAG, new HnResponseHandler<TheStoreModel>(TheStoreModel.class) {
            @Override
            public void hnSuccess(String response) {
                if (isFinishing()) return;
                if (model.getD() == null) return;
                if (model.getC() == 0) {
                    TheStoreModel.DBean.ShopBean shopBean =model.getD().getShop();
                    if (null!=shopBean&&("Y".equals(shopBean.getStore_certification_status()) && "Y".equals(shopBean.getStore_certification_status()))) {
//                        getRealNameDetails();
                        if (isFinishing() || model.getD() == null) return;
                        if(null!=shopBean){
                            tv_shopname.setText(model.getD().getShop().getShop_store_name());
                        }
                        mEdName.setText(model.getD().getUser().getRealname());
                        mEdName.setEnabled(false);
                        mEdPhone.setText(model.getD().getUser().getPhone());
                        mEdPhone.setEnabled(false);
                        mEdIdNumber.setText(model.getD().getUser().getCertification_number());
                        mEdIdNumber.setEnabled(false);
                        mIvId1.setImageURI(Uri.parse(HnUrl.setImageUrl(model.getD().getUser().getFront_img())));
                        onePath = HnUrl.setImageUrl(model.getD().getUser().getFront_img());
                        twoPath = HnUrl.setImageUrl(model.getD().getUser().getBack_img());
                        mIvId2.setImageURI(Uri.parse(HnUrl.setImageUrl(model.getD().getUser().getBack_img())));
                    }
                    else if(null!=shopBean&&(("C".equals(shopBean.getStore_certification_status())))){
                        show_rl.setVisibility(View.VISIBLE);
                        edshow_rl.setVisibility(View.GONE);
                    }else if(("N".equals(shopBean.getStore_certification_status()))){
                        show_rl.setVisibility(View.GONE);
                        edshow_rl.setVisibility(View.VISIBLE);
                        tv_submit_certification.setVisibility(View.VISIBLE);
                        tv_shopname.setEnabled(true);
                        mEdName.setEnabled(true);
                        mEdPhone.setEnabled(true);
                        mEdIdNumber.setEnabled(true);
                        mIvId1.setEnabled(true);
                        mIvId2.setEnabled(true);
                    }
                    else {
                        tv_shopname.setEnabled(true);
                        mEdName.setEnabled(true);
                        mEdPhone.setEnabled(true);
                        mEdIdNumber.setEnabled(true);
                        mIvId1.setEnabled(true);
                        mIvId2.setEnabled(true);
                        tv_submit_certification.setVisibility(View.VISIBLE);
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

    @OnClick({R.id.mIvId1, R.id.mIvId2, R.id.mLLSort, R.id.mIvCert1, R.id.mIvCert2, R.id.mTvHelp, R.id.mTvSure,R.id.tv_sendcode,R.id.tv_submit_certification,R.id.tv_backtv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.mTvSure:
                HnWebActivity.luncher(this, "电商商家协议", HnUrl.STORE_AGREEMENT, HnWebActivity.Comm);
                break;
            case R.id.mTvHelp:
                HelpAuthDialog.newInstance(this).show();
                break;
            case R.id.mIvId1:
                mHnAnchorAuthenticationBiz.uploadPicFile(select_one);
                break;
            case R.id.mIvId2:
                mHnAnchorAuthenticationBiz.uploadPicFile(select_two);
                break;
            case R.id.mIvCert1:
                mHnAnchorAuthenticationBiz.uploadPicFile(select_three);
                break;
            case R.id.mIvCert2:
                mHnAnchorAuthenticationBiz.uploadPicFile(select_four);
                break;
            case R.id.mLLSort:
                startActivity(new Intent(this, StoreAutListAct.class).putExtra("id", storeId).putExtra("type", storeName));
                break;
            case R.id.tv_sendcode:
                if (TextUtils.isEmpty(mEdPhone.getText().toString().trim()) || !mEdPhone.getText().toString().trim().matches("^1[3-8]\\d{9}$")) {
                    HnToastUtils.showToastShort(R.string.log_input_phone);
                    return;
                }else {
                    sendSMS(mEdPhone.getText().toString().trim());
                }
                break;
            case R.id.tv_backtv:
               finish();
                break;
            case R.id.tv_submit_certification:
                String number = mEdIdNumber.getText().toString();
                String phone = mEdPhone.getText().toString();
                String name = mEdName.getText().toString();
                if (TextUtils.isEmpty(name)) {
                    HnToastUtils.showToastShort("请输入您的姓名");
                    return;
                }
                if (TextUtils.isEmpty(phone)) {
                    HnToastUtils.showToastShort("请输入您的手机号码");
                    return;
                }
                if (!HnRegexUtils.isMobileExact(phone)) {
                    HnToastUtils.showToastShort("请输入正确的手机号码");
                    return;
                }
                if (TextUtils.isEmpty(number)) {
                    HnToastUtils.showToastShort("请输入您的身份证号");
                    return;
                }
                if (TextUtils.isEmpty(onePath)) {
                    HnToastUtils.showToastShort("请上传身份证正面照");
                    return;
                }
                if (TextUtils.isEmpty(twoPath)) {
                    HnToastUtils.showToastShort("请上传身份证反面照");
                    return;
                }
                if (!"user_shop".equals(certification_type)) {
                    if (TextUtils.isEmpty(threePath)) {
                        HnToastUtils.showToastShort("请上传营业执照");
                        return;
                    }
                }

                if (!TextUtils.isEmpty(storeName) && storeName.contains("食品")) {
                    if (TextUtils.isEmpty(fourPath)) {
                        HnToastUtils.showToastShort("请上传食品流通许可证");
                        return;
                    }
                }

                if (!HnRegexUtils.isIDCard18(number)) {
                    HnToastUtils.showToastShort(HnUiUtils.getString(R.string.incorr_idcard));
                    return;
                }

                if (!mBoxSure.isChecked()) {
                    HnToastUtils.showToastShort("请先阅读并同意协议");
                    return;
                }

                request(tv_shopname.getText().toString(),et_phone_verify_code.getText().toString(),"0",
                        onePath, twoPath, threePath,
//                        fourPath,
                        number, phone, name,
                        storeId, storeName, certification_type);
                break;
        }
    }

    @Override
    public void getInitData() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        mTime.cancel();
    }

    @Override
    public void requesting() {
        showDoing(getResources().getString(R.string.loading), null);
    }

    @Override
    public void requestSuccess(String type, String response, Object obj) {
        done();
        if ("Commit_Anchor_Apply".equals(type)) {//主播申请
            openActivity(HnAuthStateActivity.class);
            HnAppManager.getInstance().finishActivity(HnBeforeLiveSettingActivity.class);
            finish();
        } else if ("upload_pic_file".equals(type)) {//上传身份证照
            HnToastUtils.showToastShort(com.hotniao.live.utils.HnUiUtils.getString(R.string.upload_succeed));
            String key = response;
            String select = (String) obj;
            HnLogUtils.i(TAG, "key：" + key);
            if (!TextUtils.isEmpty(key)) {
                updateUI(select, key);
            }
        }
    }

    @Override
    public void requestFail(String type, int code, String msg) {
        done();
        if ("Commit_Anchor_Apply".equals(type)) {//主播申请
            HnToastUtils.showToastShort(msg);
        } else if ("upload_pic_file".equals(type)) {//上传身份证照
            HnToastUtils.showToastShort(com.hotniao.live.utils.HnUiUtils.getString(R.string.upload_fail));
        } else if ("get_qiniu_token".equals(type)) {//获取七牛token
            HnToastUtils.showToastShort(msg);
        }
    }

    private void updateUI(String select, String key) {
        switch (select) {
            case "select_one"://身份证正面照
                if (!TextUtils.isEmpty(key)) {
                    mIvId1.setImageURI(Uri.parse(key));
                    onePath = key;
                }
                break;
            case "select_two"://身份证反面照
                if (!TextUtils.isEmpty(key)) {
                    mIvId2.setImageURI(Uri.parse(key));
                    twoPath = key;
                }
                break;
            case "select_three":
                if (!TextUtils.isEmpty(key)) {
                    mIvCert1.setImageURI(key);
                    threePath = key;
                }
                break;
            case "select_four":
                if (!TextUtils.isEmpty(key)) {
                    mIvCert2.setImageURI(key);
                    fourPath = key;
                }
                break;
        }
    }

    private void request(String store_name,String code,String is_food,
                        String front_img, String back_img,
                         String business_licence_img,
//                         String food_circulation_img,
                         String number, String phone, String realname,
                         String store_type_id, String store_type_name,
                         String certification_type) {
        RequestParams param = new RequestParams();
        param.put("store_name", store_name);
        param.put("code", code);
        param.put("is_food", is_food);


        param.put("business_licence_img", business_licence_img);
        param.put("front_img", front_img);
        param.put("back_img", back_img);
        param.put("certification_type", certification_type);
//        if (!TextUtils.isEmpty(food_circulation_img))
//            param.put("food_circulation_img", food_circulation_img);
        param.put("number", number);
        param.put("phone", phone);
        param.put("realname", realname);
        param.put("store_type_id", store_type_id);
        param.put("store_type_name", store_type_name);
        HnHttpUtils.postRequest(HnUrl.AUTH_STORE, param, TAG, new HnResponseHandler<BaseResponseModel>(BaseResponseModel.class) {
            @Override
            public void hnSuccess(String response) {
                HnToastUtils.showToastShort("提交申请成功，预计审核时间需要1-3个工作日，请耐心等待");
                HnAppManager.getInstance().finishActivity(AuthSortAct.class);
                EventBus.getDefault().post(new FinishEvent());
                Intent intent = new Intent(StoreAutAct.this,SubmitSuccessfullyActivity.class);
                startActivity(intent);
            }

            @Override
            public void hnErr(int errCode, String msg) {
                HnToastUtils.showToastShort(msg);
            }
        });
    }


    private void getRealNameDetails() {
        HnHttpUtils.postRequest(HnUrl.CERTIFICATION_DETAIL, null, TAG, new HnResponseHandler<TheStoreModel>(TheStoreModel.class) {
            @Override
            public void hnSuccess(String response) {
                if (isFinishing() || model.getD() == null) return;
                mEdName.setText(model.getD().getUser().getRealname());
                mEdName.setEnabled(false);
                mEdPhone.setText(model.getD().getUser().getPhone());
                mEdPhone.setEnabled(false);
                mEdIdNumber.setText(model.getD().getUser().getCertification_number());
                mEdIdNumber.setEnabled(false);
                mIvId1.setImageURI(Uri.parse(HnUrl.setImageUrl(model.getD().getUser().getFront_img())));
                onePath = HnUrl.setImageUrl(model.getD().getUser().getFront_img());
                twoPath = HnUrl.setImageUrl(model.getD().getUser().getBack_img());
                mIvId2.setImageURI(Uri.parse(HnUrl.setImageUrl(model.getD().getUser().getBack_img())));
            }

            @Override
            public void hnErr(int errCode, String msg) {
                HnToastUtils.showToastShort(msg);
            }
        });
    }


    private void requestMsg() {
        RequestParams param = new RequestParams();
        HnHttpUtils.postRequest(HnUrl.AUTH_STORE_MSG, param, TAG, new HnResponseHandler<AuthStoreMsgModel>(AuthStoreMsgModel.class) {
            @Override
            public void hnSuccess(String response) {

                if (isFinishing() || model.getD() == null || model.getD().getUser() == null || model.getD().getShop() == null)
                    return;
                certification_type = model.getD().getShop().getShop_certification_type();
                onePath = model.getD().getUser().getFront_img();
                if (!TextUtils.isEmpty(onePath)) {
                    mIvId1.setImageURI(Uri.parse(HnUrl.setImageUrl(onePath)));
                    mIvId1.setEnabled(false);
                }

                twoPath = model.getD().getUser().getBack_img();
                if (!TextUtils.isEmpty(twoPath)) {
                    mIvId2.setImageURI(Uri.parse(HnUrl.setImageUrl(twoPath)));
                    mIvId2.setEnabled(false);
                }
                if (!"user_shop".equals(certification_type)) {
                    threePath = model.getD().getShop().getBusiness_licence_img();
                    if (!TextUtils.isEmpty(threePath)) {
                        mIvCert1.setImageURI(Uri.parse(HnUrl.setImageUrl(threePath)));
                        mIvCert1.setEnabled(false);
                    } else {
                        mLLPermit.setVisibility(View.GONE);
                    }
                } else {
                    mLLPermit.setVisibility(View.GONE);
                }


                fourPath = model.getD().getShop().getFood_circulation_img();
                if (!TextUtils.isEmpty(fourPath)) {
                    mIvCert2.setImageURI(Uri.parse(HnUrl.setImageUrl(fourPath)));
                    mIvCert2.setEnabled(false);
                }


                String idStr = model.getD().getUser().getCertification_number();
                if (!TextUtils.isEmpty(idStr)) {
                    mEdIdNumber.setText(idStr);
                    mEdIdNumber.setEnabled(false);
                }

                String phoneStr = model.getD().getUser().getPhone();
                if (!TextUtils.isEmpty(phoneStr)) {
                    mEdPhone.setText(phoneStr);
                    mEdPhone.setEnabled(false);
                }

                String nameStr = model.getD().getUser().getRealname();
                if (!TextUtils.isEmpty(nameStr)) {
                    mEdName.setText(nameStr);
                    mEdName.setEnabled(false);
                }

                String storeStr = model.getD().getShop().getStore_type_name();
                if (!TextUtils.isEmpty(storeStr)) {
                    mTvSort.setText(storeStr);
                    storeName = storeStr;
                    mTvSort.setEnabled(false);
                }
                if (storeStr!=null && storeStr.contains("食品")) {
                    mLLFoods.setVisibility(View.VISIBLE);
                    mTvFoods.setVisibility(View.VISIBLE);
                }

                String storeIdStr = model.getD().getShop().getStore_type_id();
                if (!TextUtils.isEmpty(storeIdStr)) {
                    storeId = storeIdStr;
                }

                String refund = model.getD().getShop().getStore_certification_result();
                if(null!=model.getD().getShop().getStore_certification_status()){
                    switch (model.getD().getShop().getStore_certification_status()) {
                        case "Y"://通过
                            mState1.setVisibility(View.VISIBLE);
                            mState2.setVisibility(View.GONE);
                            mTvState.setText("恭喜您，审核成功");
                            mTvAutMsg.setText("审核时间:" + model.getD().getShop().getTime());
                            mSubtitle.setVisibility(View.GONE);
                            break;
                        case "N"://拒绝
                            if (again) {
                                mSubtitle.setVisibility(View.VISIBLE);
                                mState2.setVisibility(View.VISIBLE);
                                mEdName.setEnabled(true);
                                mEdPhone.setEnabled(true);
                                mEdIdNumber.setEnabled(true);
                                mIvId1.setEnabled(true);
                                mIvId2.setEnabled(true);
                                mIvCert1.setEnabled(true);
                                mLLSort.setEnabled(true);
                                mIvCert2.setEnabled(true);
                            } else {
                                mState1.setVisibility(View.VISIBLE);
                                mState2.setVisibility(View.GONE);
                                mEdName.setEnabled(false);
                                mEdPhone.setEnabled(false);
                                mEdIdNumber.setEnabled(false);
                                mIvId1.setEnabled(false);
                                mIvId2.setEnabled(false);
                                mIvCert1.setEnabled(false);
                                mLLSort.setEnabled(false);
                                mIvCert2.setEnabled(false);

                                mTvState.setText("很遗憾，认证审核不通过");
                                mTvAutMsg.setText("原因:" + refund);
                                mSubtitle.setText("再次提交");
                                mSubtitle.setVisibility(View.VISIBLE);
                            }
                            break;
                        case "C"://审核中
                            mState1.setVisibility(View.VISIBLE);
                            mState2.setVisibility(View.GONE);
                            mEdName.setFocusable(false);
                            mEdPhone.setFocusable(false);
                            mEdIdNumber.setFocusable(false);

                            mTvState.setText("认证审核中，请耐心等待");
                            mTvAutMsg.setText("预计审核时间:" + model.getD().getShop().getPredict_time());
                            mSubtitle.setVisibility(View.GONE);
                            break;
                    }
                }

            }

            @Override
            public void hnErr(int errCode, String msg) {
                HnToastUtils.showToastShort(msg);
            }
        });
    }

    @Subscribe
    public void onFinishEvent(FinishEvent event) {
        finish();
    }

    @Subscribe
    public void onEvent(StoreAutEvent event) {
        storeId = event.getId();
        storeName = event.getType();
        mTvSort.setText(event.getName());

        if (event.getIsFood().equals("1")) {
            mLLPermit.setVisibility(View.VISIBLE);
//            mLLFoods.setVisibility(View.VISIBLE);
//            mTvFoods.setVisibility(View.VISIBLE);
        } else {
//            mLLFoods.setVisibility(View.GONE);
//            mTvFoods.setVisibility(View.GONE);
            mLLPermit.setVisibility(View.GONE);
        }

    }


    /**
     * Title:
     * Description: 倒计时
     */
    class TimeCount extends CountDownTimer {
        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long l) {
            tv_sendcode.setClickable(false);
//            tv_sendcode.setBackgroundColor(ContextCompat.getColor(HnAuthenticationActivity.this,R.color.color_666666));
            tv_sendcode.setText(l/1000+"s");
        }

        @Override
        public void onFinish() {
            tv_sendcode.setClickable(true);
//            tv_sendcode.setBackgroundColor(ContextCompat.getColor(HnAuthenticationActivity.this,R.color.color_666666));
            tv_sendcode.setText("重新获取验证码");
        }
    }

    /**
     * @param phone//手机号
     */
    private void sendSMS(String phone) {
        RequestParams mParam = new RequestParams();
        mParam.put("phone", phone);//用户名
        HnLogUtils.e(mParam.toString());
        HnHttpUtils.postRequest(HnUrl.CERTIFICATION_REGISTER, mParam, "VERIFY_CODE_REGISTER", new HnResponseHandler<BaseResponseModel>(this, BaseResponseModel.class) {
            @Override
            public void hnSuccess(String response) {
                if (isFinishing()) return;
                mTime.start();
                HnToastUtils.showToastShort("发送短信成功，请注意接收~");
            }

            @Override
            public void hnErr(int errCode, String msg) {
                HnToastUtils.showToastShort(msg);
            }
        });
    }


}
