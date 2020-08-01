package com.live.shoplib.ui;

import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.hn.library.base.BaseActivity;
import com.hn.library.base.baselist.BaseViewHolder;
import com.hn.library.base.baselist.CommRecyclerAdapter;
import com.hn.library.base.baselist.OnItemClickListener;
import com.hn.library.global.HnUrl;
import com.hn.library.http.BaseResponseModel;
import com.hn.library.http.HnHttpUtils;
import com.hn.library.http.HnResponseHandler;
import com.hn.library.utils.EncodeUtils;
import com.hn.library.utils.EncryptUtils;
import com.hn.library.utils.HnRefreshDirection;
import com.hn.library.utils.HnToastUtils;
import com.hn.library.view.CommDialog;
import com.hn.library.view.FrescoImageView;
import com.live.shoplib.R;
import com.live.shoplib.ShopActFacade;
import com.live.shoplib.bean.BackModel;
import com.live.shoplib.bean.TrackModel;
import com.loopj.android.http.RequestParams;
import com.tencent.openqq.protocol.imsdk.msg;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * create by Mr.x
 * on 4/7/2018
 */
@Route(path = "/shoplib/TrackAct")
public class TrackAct extends BaseActivity {

    private RecyclerView mRecycler;
    private TextView mTvDelete;
    private LinearLayout mLLEmpty;
    private CommRecyclerAdapter mAdapter;
    private List<TrackModel.DBean.ListBean> mData = new ArrayList<>();
    private boolean delete = false;

    @Override
    public int getContentViewId() {
        return R.layout.act_track;
    }

    @Override
    public void onCreateNew(Bundle savedInstanceState) {

        mRecycler = findViewById(R.id.mRecycler);
        mTvDelete = findViewById(R.id.mTvDelete);
        mLLEmpty = findViewById(R.id.mLLEmpty);
        setShowBack(true);
        setTitle("我的足迹");
        mSubtitle.setBackgroundDrawable(getResources().getDrawable(R.drawable.search_deleting));
        mSubtitle.setTextColor(getResources().getColor(R.color.comm_text_color_black_hs));
        mRecycler.setLayoutManager(new LinearLayoutManager(this));
        mRecycler.setAdapter(mAdapter = new CommRecyclerAdapter() {
            @Override
            protected void onBindView(BaseViewHolder holder, final int position) {
                RecyclerView mRecyclerItem = holder.getView(R.id.mRecyclerItem);
                final ItemAdapter adapter = new ItemAdapter(mData.get(position).getGoods(), position);
                mRecyclerItem.setLayoutManager(new GridLayoutManager(TrackAct.this, 3));
                mRecyclerItem.setAdapter(adapter);
                final CheckBox mBoxItemAll = holder.getView(R.id.mBoxItemAll);
                mBoxItemAll.setChecked(mData.get(position).isCheckAll());
                mBoxItemAll.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mData.get(position).setCheckAll(mBoxItemAll.isChecked(), true);
                        adapter.notifyDataSetChanged();
                    }
                });
                mBoxItemAll.setVisibility(mData.get(position).isCanCheck() ? View.VISIBLE : View.GONE);
                TextView mTvItemData = holder.getView(R.id.mTvItemData);
                mTvItemData.setText(mData.get(position).getDate());
            }

            @Override
            protected int getLayoutID(int position) {
                return R.layout.item_track_data;
            }

            @Override
            public int getItemCount() {
                return mData.size();
            }
        });

        mSubtitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!delete) {
                    mSubtitle.setBackgroundDrawable(null);
                    mSubtitle.setText("完成");
                    delete = true;
                    for (int i = 0; i < mData.size(); i++) {
                        mData.get(i).setCanCheck(true);
                    }
                    mTvDelete.setVisibility(View.VISIBLE);
                } else {
                    mSubtitle.setBackgroundDrawable(getResources().getDrawable(R.drawable.search_deleting));
                    mSubtitle.setText("");
                    delete = false;
                    for (int i = 0; i < mData.size(); i++) {
                        mData.get(i).setCanCheck(false);
                    }
                    mTvDelete.setVisibility(View.GONE);
                }
                mAdapter.notifyDataSetChanged();
            }
        });

        mTvDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean hadChange = false;
                JsonObject object = new JsonObject();
                for (int i = 0; i < mData.size(); i++) {
                    JsonArray jsonArray = new JsonArray();
                    for (int j = 0; j < mData.get(i).getGoods().size(); j++) {
                        if (!mData.get(i).getGoods().get(j).getCheck()) {
                            jsonArray.add(Integer.valueOf(mData.get(i).getGoods().get(j).getGoods_id()));
                        }else {
                            hadChange = true;
                        }
                    }
                    if (mData.size() > 0) {
                        object.add(mData.get(i).getGoods().get(0).getDate_type(), jsonArray);
                    }
                }


                if (hadChange) {
                    deleteTrack(getBase64(object.toString()));
                } else {
                    HnToastUtils.showToastShort("请选择删除商品");
                }

            }
        });
    }

    public static String getBase64(String str) {
        String result = "";
        if( str != null) {
            try {
                result = new String(Base64.encode(str.getBytes("utf-8"), Base64.NO_WRAP),"utf-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    @Override
    public void getInitData() {
        getData();
    }

    protected void deleteTrack(String data) {
        RequestParams param = new RequestParams();
        param.put("data", data);
        HnHttpUtils.postRequest(HnUrl.TRACK_DELETE, param, TAG, new HnResponseHandler<BaseResponseModel>(BaseResponseModel.class) {
            @Override
            public void hnSuccess(String response) {
                if (isFinishing()) return;
                mSubtitle.setBackgroundDrawable(getResources().getDrawable(R.drawable.search_deleting));
                mSubtitle.setText("");
                delete = false;
                mTvDelete.setVisibility(View.GONE);
                getData();
            }

            @Override
            public void hnErr(int errCode, String msg) {
                if (isFinishing()) return;
                HnToastUtils.showToastShort(msg);
            }
        });
    }

    protected void getData() {
        RequestParams param = new RequestParams();
        HnHttpUtils.postRequest(HnUrl.TRACK, param, TAG, new HnResponseHandler<TrackModel>(TrackModel.class) {
            @Override
            public void hnSuccess(String response) {
                if (isFinishing()) return;
                mSubtitle.setVisibility(View.VISIBLE);
                mData.clear();
                mData.addAll(model.getD().getList());
                mAdapter.notifyDataSetChanged();
                if (mData.size() == 0) {
                    mSubtitle.setVisibility(View.GONE);
                    mLLEmpty.setVisibility(View.VISIBLE);
                    mRecycler.setVisibility(View.GONE);
                }
            }

            @Override
            public void hnErr(int errCode, String msg) {
                if (isFinishing()) return;
                HnToastUtils.showToastShort(msg);
                mLLEmpty.setVisibility(View.VISIBLE);
                mRecycler.setVisibility(View.GONE);
            }
        });
    }


    class ItemAdapter extends CommRecyclerAdapter {

        private List<TrackModel.DBean.ListBean.GoodsBean> mDataItem = new ArrayList<>();
        private int index;

        public ItemAdapter(List<TrackModel.DBean.ListBean.GoodsBean> mItemData, int index) {
            this.mDataItem = mItemData;
            this.index = index;
        }

        @Override
        protected void onBindView(BaseViewHolder holder, final int position) {
            FrescoImageView mIvIco = holder.getView(R.id.mIvIcon);
            mIvIco.setImageURI(HnUrl.setImageUri(mDataItem.get(position).getGoods_img()));

            final CheckBox mBoxItem = holder.getView(R.id.mBoxItem);
            mBoxItem.setChecked(mDataItem.get(position).getCheck());

            TextView mTvPrice = holder.getView(R.id.mTvPrice);
            mTvPrice.setText(mDataItem.get(position).getGoods_price());

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(mData.get(index).isCanCheck()) {
                        mDataItem.get(position).setCheck(!mDataItem.get(position).getCheck());
                        mBoxItem.setChecked(mDataItem.get(position).getCheck());

                        boolean temp = true;
                        for (int i = 0; i < mDataItem.size(); i++) {
                            temp = mDataItem.get(i).getCheck();
                            if (!temp) {
                                mData.get(index).setCheckAll(false, false);
                                mAdapter.notifyDataSetChanged();
                                return;
                            }
                            if (temp && i == mDataItem.size() - 1) {
                                mData.get(index).setCheckAll(true, false);
                                mAdapter.notifyDataSetChanged();
                                return;
                            }
                        }
                    }else {
                        ShopActFacade.openGoodsDetails(mData.get(index).getGoods().get(position).getGoods_id());
                    }

                }
            });
            mBoxItem.setVisibility(mData.get(index).isCanCheck() ? View.VISIBLE : View.GONE);
        }

        @Override
        protected int getLayoutID(int position) {
            return R.layout.item_track_details;
        }

        @Override
        public int getItemCount() {
            return mDataItem.size();
        }
    }
}
