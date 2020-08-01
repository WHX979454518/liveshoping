package com.hotniao.live.activity;

import android.text.TextUtils;
import android.view.View;

import com.hn.library.base.baselist.BaseViewHolder;
import com.hn.library.base.baselist.CommRecyclerAdapter;
import com.hn.library.global.HnUrl;
import com.hn.library.http.HnResponseHandler;
import com.hn.library.utils.HnRefreshDirection;
import com.hn.library.utils.HnToastUtils;
import com.hotniao.live.R;
import com.hotniao.live.eventbus.StoreAutEvent;
import com.hotniao.live.model.bean.StoreAutListModel;
import com.loopj.android.http.RequestParams;
import com.reslibrarytwo.CommListActivity;
import com.reslibrarytwo.HnSkinTextView;
import com.tencent.openqq.protocol.imsdk.msg;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：Mr.Xu
 * 时间：2017/12/26
 */
public class StoreAutListAct extends CommListActivity {

    private CommRecyclerAdapter mAdapter;
    private List<StoreAutListModel.DEntity> mData = new ArrayList<>();
    private String id, type;

    @Override
    protected String setTitle() {
        id = getIntent().getStringExtra("id");
        type = getIntent().getStringExtra("type");
        return "选择店铺分类";
    }

    @Override
    protected CommRecyclerAdapter setAdapter() {
        mSpring.setEnabled(false);
        return mAdapter = new CommRecyclerAdapter() {
            @Override
            protected void onBindView(BaseViewHolder holder, final int position) {
                HnSkinTextView textView = holder.getView(R.id.mTvMsg);
                textView.setRightDrawable(mData.get(position).isCheck() ? R.drawable.channel_selection : 0);
                textView.setText(mData.get(position).getName());
                textView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        resetItem();
                        mData.get(position).setCheck(true);
                        id = mData.get(position).getId();
                        type = mData.get(position).getName();
                        mAdapter.notifyDataSetChanged();
                        EventBus.getDefault().post(new StoreAutEvent(id, type, mData.get(position).getName(),mData.get(position).getIs_food()));
                        finish();
                    }
                });
            }

            @Override
            protected int getLayoutID(int position) {
                return R.layout.item_aut_str;
            }

            @Override
            public int getItemCount() {
                return mData.size();
            }
        };
    }


    private void resetItem() {
        for (StoreAutListModel.DEntity entity : mData) {
            entity.setCheck(false);
        }
    }

    @Override
    protected RequestParams setRequestParam() {
        RequestParams params = new RequestParams();
        return params;
    }

    @Override
    protected String setRequestUrl() {
        return HnUrl.STORE_AUT_LIST;
    }

    @Override
    protected HnResponseHandler setResponseHandler(final HnRefreshDirection state) {
        return new HnResponseHandler<StoreAutListModel>(StoreAutListModel.class) {
            @Override
            public void hnSuccess(String response) {
                if (isFinishing()) return;
                refreshFinish();
                if (model.getD() == null) {
                    setEmpty("暂无评论", com.live.shoplib.R.drawable.empty_com);
                    return;
                }
                if (HnRefreshDirection.TOP == state) {
                    mData.clear();
                }
                mData.addAll(model.getD());

                for (StoreAutListModel.DEntity entity : mData) {
                    if (TextUtils.equals(id, entity.getId())) {
                        entity.setCheck(true);
                    }
                }

                if (mAdapter != null)
                    mAdapter.notifyDataSetChanged();
                setEmpty("暂无评论", com.live.shoplib.R.drawable.empty_com);
            }

            @Override
            public void hnErr(int errCode, String msg) {
                if (isFinishing()) return;
                refreshFinish();
                HnToastUtils.showToastShort(msg);
                setEmpty("暂无评论", com.live.shoplib.R.drawable.empty_com);
            }
        };
    }
}
