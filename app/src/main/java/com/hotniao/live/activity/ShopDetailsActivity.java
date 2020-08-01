package com.hotniao.live.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.hn.library.base.BaseActivity;
import com.hotniao.live.R;
import com.hotniao.live.model.TheStoreModel;

/**
 * 商家入驻
 */
public class ShopDetailsActivity extends BaseActivity {

    TheStoreModel.DBean.ShopBean shopBean = new TheStoreModel.DBean.ShopBean();
    TheStoreModel.DBean.UserBean userBean = new TheStoreModel.DBean.UserBean();
    @Override
    public int getContentViewId() {
        return R.layout.act_shopdetails;
    }

    @Override
    public void onCreateNew(Bundle savedInstanceState) {
        setTitle("店铺信息");
        setShowBack(true);
        }

    @Override
    public void getInitData() {

        TextView tv_shopdetails = findViewById(R.id.tv_shopdetails);
        ImageView certification_status_img = findViewById(R.id.certification_status_img);
        TextView certification_status_text = findViewById(R.id.certification_status_text);
        TextView tv_username = findViewById(R.id.tv_username);
        TextView tv_idcard = findViewById(R.id.tv_idcard);
        TextView tv_phone = findViewById(R.id.tv_phone);
        TextView tv_username4 = findViewById(R.id.tv_username4);
        TextView tv_foods = findViewById(R.id.tv_foods);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            shopBean = (TheStoreModel.DBean.ShopBean) bundle.getSerializable("shopBean");
            userBean = (TheStoreModel.DBean.UserBean) bundle.getSerializable("userBean");
//            tv_shopdetails.setText(shopBean.getStore_type_name());
            tv_shopdetails.setText(shopBean.getShop_store_name());
            tv_foods.setText(shopBean.getStore_type_name());
            if (shopBean.getStore_certification_status().equals("N")) {
                certification_status_img.setBackgroundResource(R.mipmap.unauthorized_img);
                certification_status_text.setText("认证失败");
            } else if (shopBean.getStore_certification_status().equals("C")) {
                certification_status_img.setBackgroundResource(R.mipmap.unauthorized_img);
                certification_status_text.setText("认证中");
            }else if(shopBean.getStore_certification_status().equals("Y")){
                certification_status_img.setBackgroundResource(R.mipmap.certified_img);
                certification_status_text.setText("认证成功");
                tv_username4.setVisibility(View.GONE);
                tv_foods.setVisibility(View.GONE);
            }
            if(null!=userBean){
                tv_username.setText(userBean.getRealname());
                tv_idcard.setText(userBean.getCertification_number());
                tv_phone.setText(userBean.getPhone());
            }


        }




//        //提交成功的页面
//        tv_shopdetails.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(ShopDetailsActivity.this,SubmitSuccessfullyActivity.class);
//                startActivity(intent);
//            }
//        });
    }
}
