package com.live.shoplib.ui;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.hn.library.base.BaseActivity;
import com.hn.library.base.baselist.BaseViewHolder;
import com.hn.library.base.baselist.CommRecyclerAdapter;
import com.hn.library.global.HnUrl;
import com.hn.library.http.HnHttpUtils;
import com.hn.library.http.HnResponseHandler;
import com.hn.library.utils.HnToastUtils;
import com.hn.library.view.FrescoImageView;
import com.live.shoplib.R;
import com.live.shoplib.ShopActFacade;
import com.live.shoplib.bean.AuthSortModel;
import com.live.shoplib.bean.SellerCenterModel;
import com.loopj.android.http.RequestParams;

import java.util.ArrayList;
import java.util.List;

/**
 * 认证类型
 * 作者：Mr.Xu
 * 时间：2018/1/3
 */
@Route(path = "/shoplib/AuthSortAct")
public class AuthSortAct extends BaseActivity {

    private RecyclerView mRecycler;

    private CommRecyclerAdapter mAdapter;
    private List<AuthSortModel.DEntity.ShopCertificationEntity> mData = new ArrayList<>();

    @Override
    public int getContentViewId() {
        return R.layout.act_auth_sort;
    }

    @Override
    public void onCreateNew(Bundle savedInstanceState) {
        setTitle("认证类型");
        setShowBack(true);
        requestData();
    }

    @Override
    public void getInitData() {

        mRecycler = findViewById(R.id.mRecycler);

        mRecycler.setLayoutManager(new LinearLayoutManager(this));
        mRecycler.setHasFixedSize(true);
        mRecycler.setAdapter(mAdapter = new CommRecyclerAdapter() {
            @Override
            protected void onBindView(BaseViewHolder holder, final int position) {
                ((FrescoImageView) holder.getView(R.id.mIvIco)).setImageURI(Uri.parse(HnUrl.setImageUrl(mData.get(position).getLogo())));
                holder.setTextViewText(R.id.mTvTitle, mData.get(position).getName());
                holder.setTextViewText(R.id.mTvContent, mData.get(position).getContent());
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ShopActFacade.openStoreAut(mData.get(position).getType());
                    }
                });
            }

            @Override
            protected int getLayoutID(int position) {
                return R.layout.item_auth_sort;
            }

            @Override
            public int getItemCount() {
                return mData.size();
            }
        });


    }


    private void requestData() {
        RequestParams param = new RequestParams();
        HnHttpUtils.postRequest(HnUrl.AUTH_SORT, param, "卖家中心", new HnResponseHandler<AuthSortModel>(AuthSortModel.class) {
            @Override
            public void hnSuccess(String response) {
                if (isFinishing() || model.getD() == null) return;
                mData.clear();
                mData.addAll(model.getD().getShop_certification());
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void hnErr(int errCode, String msg) {
                HnToastUtils.showToastShort(msg);
            }
        });
    }
}
