package com.live.shoplib.ui;

import android.net.Uri;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.hn.library.base.baselist.BaseViewHolder;
import com.hn.library.base.baselist.CommRecyclerAdapter;
import com.hn.library.global.HnUrl;
import com.hn.library.http.HnResponseHandler;
import com.hn.library.utils.HnDateUtils;
import com.hn.library.utils.HnRefreshDirection;
import com.hn.library.utils.HnToastUtils;
import com.hn.library.view.FrescoImageView;
import com.live.shoplib.R;
import com.live.shoplib.ShopActFacade;
import com.live.shoplib.bean.BackModel;
import com.loopj.android.http.RequestParams;
import com.reslibrarytwo.CommListActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：Mr.Xu
 * 时间：2018/1/3
 */
@Route(path = "/shoplib/BackListAct")
public class BackListAct extends CommListActivity {

    private CommRecyclerAdapter mAdapter;
    private List<BackModel.DEntity.VideosEntity.ItemsEntity> mData = new ArrayList<>();
    private String user_id,title;

    @Override
    protected String setTitle() {
        setGridManager(true);
        user_id = getIntent().getStringExtra("user_id");
        title = getIntent().getStringExtra("title");
        return title;
    }

    @Override
    protected CommRecyclerAdapter setAdapter() {
        return mAdapter = new CommRecyclerAdapter() {
            @Override
            protected void onBindView(BaseViewHolder holder, final int position) {
                ((FrescoImageView) holder.getView(R.id.mIvIco))
                        .setImageURI(Uri.parse(HnUrl.setImageUrl(mData.get(position).getImage_url())));
                holder.setTextViewText(R.id.mTvNum, mData.get(position).getOnlines() + "人");
                holder.setTextViewText(R.id.mTvDate, HnDateUtils.dateFormat(mData.get(position).getStart_time(), "MM-dd HH:mm"));
                holder.setTextViewText(R.id.mTvTitle, mData.get(position).getTitle());
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ShopActFacade.openPlayBack(user_id, mData.get(position).getPlayback_url(), mData.get(position).getShare());
                    }
                });
            }

            @Override
            protected int getLayoutID(int position) {
                return R.layout.item_back;
            }

            @Override
            public int getItemCount() {
                return mData.size();
            }
        };

    }

    @Override
    protected RequestParams setRequestParam() {
        RequestParams params = new RequestParams();
        params.put("user_id", user_id);
        return params;
    }

    @Override
    protected String setRequestUrl() {
        return HnUrl.LIVE_PLAYBACK_ANCHOR;
    }

    @Override
    protected HnResponseHandler setResponseHandler(final HnRefreshDirection state) {
        return new HnResponseHandler<BackModel>(BackModel.class) {
            @Override
            public void hnSuccess(String response) {
                if (isFinishing()) return;
                refreshFinish();
                if (model.getD().getVideos().getItems() == null) {
                    setEmpty("暂无视频", R.drawable.empty_com);
                    return;
                }
                if (HnRefreshDirection.TOP == state) {
                    mData.clear();
                }
                mData.addAll(model.getD().getVideos().getItems());
                if (mAdapter != null)
                    mAdapter.notifyDataSetChanged();
                setEmpty("暂无视频", R.drawable.empty_com);
            }

            @Override
            public void hnErr(int errCode, String msg) {
                if (isFinishing()) return;
                refreshFinish();
                HnToastUtils.showToastShort(msg);
                setEmpty("暂无视频", R.drawable.empty_com);
            }
        };
    }
}
