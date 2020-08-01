package com.live.shoplib.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.hn.library.base.baselist.BaseViewHolder;
import com.hn.library.base.baselist.CommRecyclerAdapter;
import com.hn.library.global.HnUrl;
import com.hn.library.http.BaseResponseModel;
import com.hn.library.http.HnResponseHandler;
import com.hn.library.utils.HnDateUtils;
import com.hn.library.utils.HnRefreshDirection;
import com.hn.library.utils.HnToastUtils;
import com.hn.library.view.FrescoImageView;
import com.jakewharton.rxbinding2.view.RxView;
import com.live.shoplib.R;
import com.live.shoplib.bean.GoodsEvaModel;
import com.live.shoplib.widget.StartRatingBar;
import com.loopj.android.http.RequestParams;
import com.reslibrarytwo.CommListActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * 用户评论
 * 作者：Mr.Xu
 * 时间：2017/12/18
 */
@Route(path = "/shoplib/EvaGoodsAct")
public class EvaGoodsAct extends CommListActivity {
    private CommRecyclerAdapter mAdapter;
    private List<GoodsEvaModel.DEntity.ItemsEntity> mData = new ArrayList<>();
    private String goods_id;
    private String order_id;
    private boolean isOrder;

    @Override
    protected String setTitle() {
        goods_id = getIntent().getStringExtra("goods_id");
        order_id = getIntent().getStringExtra("order_id");
        isOrder = getIntent().getBooleanExtra("isOrder", false);
        return "评论";
    }

    @Override
    protected CommRecyclerAdapter setAdapter() {
        setShowSubTitle(true);
        mSubtitle.setVisibility(View.GONE);
        return mAdapter = new CommRecyclerAdapter() {
            @Override
            protected void onBindView(final BaseViewHolder holder, final int position) {
                ((FrescoImageView) holder.getView(R.id.mIvUserIco)).setImageURI(Uri.parse(HnUrl.setImageUrl(mData.get(position).getUser_avatar())));
                holder.setTextViewText(R.id.mTvUserName, mData.get(position).getUser_name());
                ((StartRatingBar) holder.getView(R.id.mIvStart)).setCountSelected(mData.get(position).getGrade());
                holder.setTextViewText(R.id.mTvEva, mData.get(position).getContent());
                //TODO
                final List<String> imgs = mData.get(position).getImg();
                if (imgs == null || imgs.size() < 1) {
                    holder.getView(R.id.mIvEvaIco1).setVisibility(View.GONE);
                    holder.getView(R.id.mIvEvaIco2).setVisibility(View.GONE);
                    holder.getView(R.id.mIvEvaIco3).setVisibility(View.GONE);
                } else {
                    for (int i = 0; i < imgs.size(); i++) {
                        if (i == 0) {
                            holder.getView(R.id.mIvEvaIco1).setVisibility(View.VISIBLE);
                            ((FrescoImageView) holder.getView(R.id.mIvEvaIco1)).setImageURI(Uri.parse(HnUrl.setImageUrl(imgs.get(i))));
                            holder.getView(R.id.mIvEvaIco1).setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    launcher(holder.getView(R.id.mIvEvaIco1), 0, imgs);
                                }
                            });
                        } else if (i == 1) {
                            holder.getView(R.id.mIvEvaIco2).setVisibility(View.VISIBLE);
                            ((FrescoImageView) holder.getView(R.id.mIvEvaIco2)).setImageURI(Uri.parse(HnUrl.setImageUrl(imgs.get(i))));
                            holder.getView(R.id.mIvEvaIco2).setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    launcher(holder.getView(R.id.mIvEvaIco2), 1, imgs);
                                }
                            });
                        } else if (i == 2) {
                            holder.getView(R.id.mIvEvaIco3).setVisibility(View.VISIBLE);
                            ((FrescoImageView) holder.getView(R.id.mIvEvaIco3)).setImageURI(Uri.parse(HnUrl.setImageUrl(imgs.get(i))));
                            holder.getView(R.id.mIvEvaIco3).setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    launcher(holder.getView(R.id.mIvEvaIco3), 2, imgs);
                                }
                            });
                        }
                    }
                }

                //规格
                holder.setTextViewText(R.id.mTvGoodsMsg, HnDateUtils.dateFormat(mData.get(position).getCreate_time(), "yyyy-MM-dd") + "  " + mData.get(position).getGoods_spec_details());
            }

            @Override
            protected int getLayoutID(int position) {
                return R.layout.item_eva;
            }

            @Override
            public int getItemCount() {
                return mData.size();
            }
        };

    }

    private void launcher(View view, int position, List data) {
        Intent intent = new Intent(this, HnPhotoPagerActivity.class);
        Bundle args = new Bundle();
        args.putInt(HnPhotoPagerActivity.KEY_START_POS, Math.min(data.size(), Math.max(0, position)));

        int[] rt = new int[2];
        view.getLocationOnScreen(rt);

        float w = view.getMeasuredWidth();
        float h = view.getMeasuredHeight();

        float x = rt[0] + w / 2;
        float y = rt[1] + h / 2;

        args.putInt(HnPhotoPagerActivity.KEY_START_X, rt[0]);
        args.putInt(HnPhotoPagerActivity.KEY_START_Y, rt[1]);
        args.putInt(HnPhotoPagerActivity.KEY_START_W, (int) w);
        args.putInt(HnPhotoPagerActivity.KEY_START_H, (int) h);
        args.putStringArrayList(HnPhotoPagerActivity.KEY_PHOTO_LIST, (ArrayList<String>) data);

        intent.putExtras(args);
        startActivity(intent);
    }

    @Override
    protected RequestParams setRequestParam() {
        RequestParams params = new RequestParams();

        params.put(isOrder ? "order_id" : "goods_id", isOrder ? order_id : goods_id);
        return params;
    }

    @Override
    protected String setRequestUrl() {
        return isOrder ? HnUrl.ORDER_EVA : HnUrl.GOODS_EVA;
    }

    @Override
    protected HnResponseHandler setResponseHandler(final HnRefreshDirection state) {
        return new HnResponseHandler<GoodsEvaModel>(GoodsEvaModel.class) {
            @Override
            public void hnSuccess(String response) {
                if (isFinishing()) return;
                refreshFinish();
                if (model.getD().getItems() == null) {
                    setEmpty("暂无评论", R.drawable.no_comments);
                    return;
                }
                if (HnRefreshDirection.TOP == state) {
                    mData.clear();
                }
                mData.addAll(model.getD().getItems());
                if (mAdapter != null)
                    mAdapter.notifyDataSetChanged();
                setEmpty("暂无评论", R.drawable.no_comments);
            }

            @Override
            public void hnErr(int errCode, String msg) {
                if (isFinishing()) return;
                refreshFinish();
                HnToastUtils.showToastShort(msg);
                setEmpty("暂无评论", R.drawable.no_comments);
            }
        };
    }
}
