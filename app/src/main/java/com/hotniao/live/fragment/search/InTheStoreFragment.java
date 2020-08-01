package com.hotniao.live.fragment.search;


import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hn.library.base.HnViewPagerBaseFragment;
import com.hn.library.base.baselist.BaseViewHolder;
import com.hn.library.base.baselist.CommRecyclerAdapter;
import com.hn.library.global.HnConstants;
import com.hn.library.global.HnUrl;
import com.hn.library.global.NetConstant;
import com.hn.library.http.HnHttpUtils;
import com.hn.library.http.HnResponseHandler;
import com.hn.library.loadstate.HnLoadingLayout;
import com.hn.library.refresh.PtrClassicFrameLayout;
import com.hn.library.refresh.PtrDefaultHandler2;
import com.hn.library.refresh.PtrFrameLayout;
import com.hn.library.utils.HnPrefUtils;
import com.hn.library.utils.HnRefreshDirection;
import com.hn.library.utils.HnToastUtils;
import com.hn.library.utils.StringUtils;
import com.hn.library.view.FrescoImageView;
import com.hn.library.view.HnRecyclerGridDecoration;
import com.hotniao.live.R;
import com.hotniao.live.activity.ShopDetailsActivity;
import com.hotniao.live.activity.StoreAutAct;
import com.hotniao.live.activity.TenantsActivity;
import com.hotniao.live.model.CollectGoodsModel;
import com.hotniao.live.model.PlayBackModel;
import com.hotniao.live.model.TheStoreModel;
import com.live.shoplib.ShopActFacade;
import com.loopj.android.http.RequestParams;
import com.reslibrarytwo.CommListFragment;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 入驻商铺
 */
public class InTheStoreFragment extends CommListFragment {
    RelativeLayout cd_directly;
    RelativeLayout cd_tojoin;
    RelativeLayout cd_noting;



    @Override
    public int getContentViewId() {
        return R.layout.frag_inthestore;
    }


    @Override
    public void initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        cd_directly = rootView.findViewById(R.id.cd_directly);
        cd_tojoin = rootView.findViewById(R.id.cd_tojoin);
        cd_noting  = rootView.findViewById(R.id.cd_noting);

        RequestParams params = new RequestParams();
        HnHttpUtils.postRequest(HnUrl.CERTIFICATION_DETAIL, params, "商家入驻", new HnResponseHandler<TheStoreModel>(TheStoreModel.class) {
            @Override
            public void hnSuccess(String response) {
                if (mActivity == null) return;
                refreshFinish();

                if (model.getD().getUser() == null) {
                    cd_directly.setVisibility(View.GONE);
                    cd_tojoin.setVisibility(View.GONE);
                    cd_noting.setVisibility(View.VISIBLE);
                    return;
                } else if(model.getD().getUser() == null&&response.equals("{}")){

                }
                else {
                    cd_directly.setVisibility(View.VISIBLE);
                    cd_tojoin.setVisibility(View.VISIBLE);
                    cd_noting.setVisibility(View.GONE);
                }


                cd_directly.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //判断是否认证成功
                        if(null!=model.getD().getShop().getStore_certification_status()){
                            if(model.getD().getShop().getStore_certification_status().equals("Y")){
                                HnToastUtils.showToastShort("您已经成功入驻,无需再认证!");
                            }else {
                                Intent intentone = new Intent(getActivity(), StoreAutAct.class);
                                intentone.putExtra("certification_type","user_shop");
                                startActivity(intentone);
                            }
                        }else {
                            Intent intentone = new Intent(getActivity(), StoreAutAct.class);
                            intentone.putExtra("certification_type","user_shop");
                            startActivity(intentone);
                        }

                    }
                });
                cd_tojoin.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //判断是否认证成功
                        if(null!=model.getD().getShop().getStore_certification_status()){
                            if(model.getD().getShop().getStore_certification_status().equals("Y")){
                                HnToastUtils.showToastShort("您已经成功入驻,无需再认证!");
                            }else {
                                Intent intenttwo = new Intent(getActivity(), StoreAutAct.class);
                                intenttwo.putExtra("certification_type","join_shop");
                                startActivity(intenttwo);
                            }
                        }else {
                            Intent intenttwo = new Intent(getActivity(), StoreAutAct.class);
                            intenttwo.putExtra("certification_type","join_shop");
                            startActivity(intenttwo);
                        }
                    }
                });
            }

            @Override
            public void hnErr(int errCode, String msg) {
                HnToastUtils.showToastShort(msg);
            }
        });

    }

    @Override
    public void fetchData() {

    }

    @Override
    protected String setTAG() {
        return null;
    }

    @Override
    protected CommRecyclerAdapter setAdapter() {
        return null;
    }

    @Override
    protected RequestParams setRequestParam() {
        return null;
    }

    @Override
    protected String setRequestUrl() {
        return null;
    }

    @Override
    protected HnResponseHandler setResponseHandler(HnRefreshDirection state) {
        return null;
    }
}
