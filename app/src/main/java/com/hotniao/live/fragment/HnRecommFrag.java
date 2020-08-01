package com.hotniao.live.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hn.library.base.BaseFragment;
import com.hn.library.base.baselist.BaseViewHolder;
import com.hn.library.base.baselist.CommRecyclerAdapter;
import com.hn.library.global.HnUrl;
import com.hn.library.global.NetConstant;
import com.hn.library.utils.HnPrefUtils;
import com.hn.library.view.FrescoImageView;
import com.hotniao.live.R;
import com.hotniao.live.model.RecommModel;
import com.hotniao.livelibrary.util.HnLiveSwitchDataUitl;
import com.live.shoplib.ShopActFacade;
import com.reslibrarytwo.LevelView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 作者：Mr.Xu
 * 时间：2018/2/2
 */
@SuppressLint("ValidFragment")
public class HnRecommFrag extends BaseFragment {

    @BindView(R.id.mIvIco)
    FrescoImageView mIvIco;
    @BindView(R.id.mIvLogo)
    FrescoImageView mIvLogo;
    @BindView(R.id.mRecycler)
    RecyclerView mRecycler;
    @BindView(R.id.mTvName)
    TextView mTvName;
    @BindView(R.id.mTvAddress)
    TextView mTvAddress;
    @BindView(R.id.mTvSale)
    TextView mTvSale;
    @BindView(R.id.mTvFans)
    TextView mTvFans;
    @BindView(R.id.mTvIntro)
    TextView mTvIntro;
    @BindView(R.id.mTvState)
    TextView mTvState;
    @BindView(R.id.mLevelView)
    LevelView mLevelView;

    private RecommModel.DEntity.ItemsEntity mBean;

    public HnRecommFrag(RecommModel.DEntity.ItemsEntity bean) {
        this.mBean = bean;
    }

    @Override
    public int getContentViewId() {
        return R.layout.frag_recomm;
    }

    @Override
    public void initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mIvIco.setImageURI(HnUrl.setImageUri(mBean.getIcon()));
        mIvLogo.setImageURI(HnUrl.setImageUri(mBean.getImg()));
        mTvName.setText(mBean.getName());
        mTvAddress.setText(mBean.getCity());
        try {
            mLevelView.setLevelAnchor(Integer.valueOf(mBean.getAnchor_level()));
        } catch (NumberFormatException e) {
            mLevelView.setLevelAnchor(1);
            e.printStackTrace();
        }
        mTvFans.setText(mBean.getTotal_collect() + "粉丝");
        mTvSale.setText(mBean.getTotal_order() + "单");
        mTvIntro.setText(mBean.getIntro());
        mTvState.setVisibility(TextUtils.equals("Y", mBean.getAnchor_is_live()) ? View.VISIBLE : View.GONE);
        mIvLogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.equals("Y", mBean.getAnchor_is_live())){
                    HnLiveSwitchDataUitl.joinRoom(getContext(), mBean.getUser_id(), mBean.getStore_id());
                }else {
                    ShopActFacade.openShopDetails(mBean.getStore_id());
                }
            }
        });
    }

    @Override
    protected void initData() {
        mRecycler.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        mRecycler.setHasFixedSize(true);
        mRecycler.setAdapter(new CommRecyclerAdapter() {
            @Override
            protected void onBindView(BaseViewHolder holder, final int position) {
                FrescoImageView mIvIco = holder.getView(R.id.mIvIco);
                mIvIco.setImageURI(HnUrl.setImageUri(mBean.getGoods_list().get(position).getGoods_img()));
                holder.setTextViewText(R.id.mTvPrice, "¥" + mBean.getGoods_list().get(position).getGoods_price());

                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ShopActFacade.openGoodsDetails(mBean.getGoods_list().get(position).getGoods_id());
                    }
                });

            }

            @Override
            protected int getLayoutID(int position) {
                return R.layout.item_recomm;
            }

            @Override
            public int getItemCount() {
                return mBean.getGoods_list().size();
            }
        });
    }

}
