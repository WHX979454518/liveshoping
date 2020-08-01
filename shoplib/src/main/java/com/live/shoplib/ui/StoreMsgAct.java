package com.live.shoplib.ui;

import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.hn.library.base.BaseActivity;
import com.hn.library.global.HnUrl;
import com.hn.library.http.HnHttpUtils;
import com.hn.library.http.HnResponseHandler;
import com.hn.library.utils.HnToastUtils;
import com.hn.library.view.FrescoImageView;
import com.live.shoplib.R;
import com.live.shoplib.bean.StoreMsgModel;
import com.loopj.android.http.RequestParams;

/**
 * 店铺信息
 * 作者：Mr.Xu
 * 时间：2018/1/3
 */
@Route(path = "/shoplib/StoreMsgAct")
public class StoreMsgAct extends BaseActivity {

    private RelativeLayout mTitle;
    private ImageView mIvBarBack;
    private TextView mTvTitle;
    private FrescoImageView mIvIco;
    private TextView mTvName;
    private ImageView mTvTag;
    private TextView mTvStoreNum;
    private TextView mTvSaleNum;
    private TextView mTvFanNum;
    private TextView mTvAuth;
    private TextView mTvIntro;
    private TextView mTvSort;
    private TextView mTvPhone;
    private LinearLayout mLLLencese;
    private ImageView mTvApt;
    private LinearLayout mLLLencese2;
    private FrescoImageView mIvLicense1;
    private FrescoImageView mIvLicense2;


    private String store_id;

    @Override
    public int getContentViewId() {
        store_id = getIntent().getStringExtra("store_id");
        return R.layout.act_store_msg;
    }

    @Override
    public void onCreateNew(Bundle savedInstanceState) {
        setShowTitleBar(false);
        mTitle = (RelativeLayout) findViewById(R.id.mTitle);
        mIvBarBack = (ImageView) findViewById(R.id.mIvBarBack);
        mTvTitle = (TextView) findViewById(R.id.mTvTitle);
        mIvIco = (FrescoImageView) findViewById(R.id.mIvIco);
        mTvName = (TextView) findViewById(R.id.mTvName);
        mTvTag = (ImageView) findViewById(R.id.mTvTag);
        mTvStoreNum = (TextView) findViewById(R.id.mTvStoreNum);
        mTvSaleNum = (TextView) findViewById(R.id.mTvSaleNum);
        mTvFanNum = (TextView) findViewById(R.id.mTvFanNum);
        mTvAuth = (TextView) findViewById(R.id.mTvAuth);
        mTvIntro = (TextView) findViewById(R.id.mTvIntro);
        mTvSort = (TextView) findViewById(R.id.mTvSort);
        mTvPhone = (TextView) findViewById(R.id.mTvPhone);
        mLLLencese = (LinearLayout) findViewById(R.id.mLLLencese);
        mTvApt = (ImageView) findViewById(R.id.mTvApt);
        mLLLencese2 = (LinearLayout) findViewById(R.id.mLLLencese2);
        mIvLicense1 = (FrescoImageView) findViewById(R.id.mIvLicense1);
        mIvLicense2 = (FrescoImageView) findViewById(R.id.mIvLicense2);

        mIvBarBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void getInitData() {

        requestMsg(store_id);
    }


    private void requestMsg(String store_id) {
        RequestParams param = new RequestParams();
        param.put("store_id", store_id);
        HnHttpUtils.postRequest(HnUrl.STORE_MSG, param, "店铺信息", new HnResponseHandler<StoreMsgModel>(StoreMsgModel.class) {
            @Override
            public void hnSuccess(String response) {

                mIvIco.setImageURI(Uri.parse(HnUrl.setImageUrl(model.getD().getStore().getIcon())));
                mTvName.setText(model.getD().getStore().getName());
                mTvTag.setImageResource(TextUtils.equals("user_shop", model.getD().getStore().getCertification_type()) ? R.drawable.individual_stores : R.drawable.flagship_stores);
                mTvAuth.setText(TextUtils.equals("user_shop", model.getD().getStore().getCertification_type()) ? "直营店" : "加盟店");

                mTvStoreNum.setText(model.getD().getStore().getGoods_num());
                mTvSaleNum.setText(model.getD().getStore().getGoods_sales());
                mTvFanNum.setText(model.getD().getStore().getTotal_collect());
                mTvSort.setText(model.getD().getStore().getTypeName());
                mTvIntro.setText(model.getD().getStore().getIntro());
                //是否打开资质校验 1 打开 0 不开打
                if (TextUtils.equals("1", model.getD().getStore().getIs_open())) {
                    mIvLicense1.setImageURI(Uri.parse(HnUrl.setImageUrl(model.getD().getStore().getShop_business_licence_img())));
                    //食品可能不存在
                    if (TextUtils.isEmpty(HnUrl.setImageUrl(model.getD().getStore().getShop_food_circulation_img()))) {
                        mIvLicense2.setVisibility(View.GONE);
                    } else {
                        mIvLicense2.setVisibility(View.VISIBLE);
                        mIvLicense2.setImageURI(Uri.parse(HnUrl.setImageUrl(model.getD().getStore().getShop_food_circulation_img())));
                    }
                    //收缩
                    mTvApt.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (mLLLencese2.getVisibility() == View.VISIBLE) {
                                mTvApt.setImageResource(R.drawable.shop_qualification_upper);
                                mLLLencese2.setVisibility(View.GONE);
                            } else {
                                mTvApt.setImageResource(R.drawable.shop_qualification_lower);
                                mLLLencese2.setVisibility(View.VISIBLE);
                            }
                        }
                    });

                } else {
                    mLLLencese.setVisibility(View.GONE);
                    mLLLencese2.setVisibility(View.GONE);
                }


                if (TextUtils.isEmpty(model.getD().getStore().getShop_business_licence_img())) {
                    mLLLencese.setVisibility(View.GONE);
                    mLLLencese2.setVisibility(View.GONE);
                }

                mTvPhone.setText(model.getD().getStore().getUser_phone());

            }


            @Override
            public void hnErr(int errCode, String msg) {
                HnToastUtils.showToastShort(msg);
            }
        });
    }
}
