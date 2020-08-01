package com.hotniao.live.activity;

import android.view.View;

import com.hn.library.base.baselist.BaseViewHolder;
import com.hn.library.base.baselist.CommRecyclerAdapter;
import com.hn.library.global.HnUrl;
import com.hn.library.http.HnResponseHandler;
import com.hn.library.utils.HnDateUtils;
import com.hn.library.utils.HnRefreshDirection;
import com.hn.library.utils.HnToastUtils;
import com.hn.library.view.FrescoImageView;
import com.hotniao.live.R;
import com.hotniao.live.model.OfficeAnnoModel;
import com.live.shoplib.bean.GoodsEvaModel;
import com.loopj.android.http.RequestParams;
import com.reslibrarytwo.CommListActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * 官方公告
 * 作者：Mr.Xu
 * 时间：2018/2/2
 */
public class HnOfficeAnnoAct extends CommListActivity {
    private CommRecyclerAdapter mAdapter;
    private List<OfficeAnnoModel.DEntity.ItemsEntity> mData = new ArrayList<>();

    @Override
    protected String setTitle() {
        return "官方公告";
    }

    @Override
    protected CommRecyclerAdapter setAdapter() {
        setShowSubTitle(true);
        mSubtitle.setVisibility(View.GONE);
        return mAdapter = new CommRecyclerAdapter() {
            @Override
            protected void onBindView(final BaseViewHolder holder, final int position) {
                //TODO
                holder.setTextViewText(R.id.mTvTitle, mData.get(position).getOfficial_news_title());
                holder.setTextViewText(R.id.mTvDate,
                        HnDateUtils.dateFormat(mData.get(position).getOfficial_news_update_time(), "yyyy-MM-dd HH:mm"));
                FrescoImageView mIvIco = holder.getView(R.id.mIvIco);
                mIvIco.setImageURI(HnUrl.setImageUri(mData.get(position).getOfficial_news_logo()));
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //TODO
                        String url = mData.get(position).getShare();
                        url = url.replace("\\/","/");
                        HnWebActivity.luncher(HnOfficeAnnoAct.this, "公告详情", url, HnWebActivity.Recomm);
                    }
                });
            }

            @Override
            protected int getLayoutID(int position) {
                return R.layout.item_office_anno;
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
        return params;
    }

    @Override
    protected String setRequestUrl() {
        return HnUrl.OFFICE_ANNO;
    }

    @Override
    protected HnResponseHandler setResponseHandler(final HnRefreshDirection state) {
        return new HnResponseHandler<OfficeAnnoModel>(OfficeAnnoModel.class) {
            @Override
            public void hnSuccess(String response) {
                if (isFinishing()) return;
                refreshFinish();
                if (model.getD().getItems() == null) {
                    setEmpty("暂无公告", R.drawable.no_comments);
                    return;
                }
                if (HnRefreshDirection.TOP == state) {
                    mData.clear();
                }
                mData.addAll(model.getD().getItems());
                if (mAdapter != null)
                    mAdapter.notifyDataSetChanged();
                setEmpty("暂无公告", R.drawable.no_comments);
            }

            @Override
            public void hnErr(int errCode, String msg) {
                if (isFinishing()) return;
                refreshFinish();
                HnToastUtils.showToastShort(msg);
                setEmpty("暂无公告", R.drawable.no_comments);
            }
        };
    }
}
