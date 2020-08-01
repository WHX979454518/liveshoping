package com.hotniao.live.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.DefaultItemAnimator;

import com.hn.library.base.baselist.BaseViewHolder;
import com.hn.library.base.baselist.CommRecyclerAdapter;
import com.hn.library.global.HnUrl;
import com.hn.library.http.HnHttpUtils;
import com.hn.library.http.HnResponseHandler;
import com.hn.library.utils.HnDateUtils;
import com.hn.library.utils.HnRefreshDirection;
import com.hn.library.utils.HnToastUtils;
import com.hn.library.utils.HnUiUtils;
import com.hn.library.view.FrescoImageView;
import com.hotniao.live.R;
import com.hotniao.live.activity.ArticleDetailsAct;
import com.hotniao.live.activity.LittleVideoDetailsAct;
import com.hotniao.live.model.LittleVideoModel;
import com.hotniao.live.model.PraiseModel;
import com.live.shoplib.bean.OrderRefreshEvent;
import com.loopj.android.http.RequestParams;
import com.reslibrarytwo.UpdateCommListFragment;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import static com.hotniao.live.model.PraiseModel.ARTICLE_TYPE;
import static com.hotniao.live.model.PraiseModel.VIDEO_TYPE;

/**
 * 秒杀或团购的额fragment
 * 作者：Alan
 * 时间：2019/6/15
 */
public class LittleVideoItemFragment extends UpdateCommListFragment {
    private static final String WORDS = "文字";
    private static final String RECOMMEND = "推荐";
    private static final String HOT = "热门";
    private static final String FOLLOW = "关注";
    private List<LittleVideoModel.LittleVideoItem> mData = new ArrayList<>();
    private List<LittleVideoModel.LittleVideoItem> mWordData = new ArrayList<>();

    private String title = "";


    public static LittleVideoItemFragment newInstance(String flag) {
        LittleVideoItemFragment fragment = new LittleVideoItemFragment();
        Bundle bundle = new Bundle();
        //todo 分类的相关标识获取
        bundle.putString("title", flag);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected String setTAG() {
        EventBus.getDefault().register(this);
        title = getArguments().getString("title");
        initView();
        return "短视频";
    }

    private void initView() {
        mRecycler.setBackgroundColor(getResources().getColor(R.color.live_recycle_ckg));
        ((DefaultItemAnimator) mRecycler.getItemAnimator()).setSupportsChangeAnimations(false);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
    }

    private boolean isOnlyWordContent() {
        return WORDS.equals(title);
    }


    private String isRecommend() {
        return RECOMMEND.equals(title) ? "1" : "0";
    }

    private String isFollow() {
        return FOLLOW.equals(title) ? "1" : "0";
    }

    @Override
    protected CommRecyclerAdapter setAdapter() {
        return new CommRecyclerAdapter() {
            @Override
            protected void onBindView(BaseViewHolder holder, final int position) {
                bindView(holder, position);
            }

            @Override
            protected int getLayoutID(int position) {
                return R.layout.item_little_video;
            }

            @Override
            public int getItemCount() {
                return isOnlyWordContent() ? mWordData.size() : mData.size();
            }
        };
    }

    private void bindView(BaseViewHolder holder, final int position) {
        bindData(holder, position);
    }

    private void bindData(BaseViewHolder holder, final int position) {
        if (isOnlyWordContent()) {
            bindTextData(holder, position);
        } else {
            bindVideoData(holder, position);
        }
    }

    private void praiseOrCancelPraise(final boolean isPraise, final String type, String id, final int position) {
        RequestParams params = new RequestParams();
        params.put("type", type);
        params.put("id", id);
        HnHttpUtils.postRequest(isPraise ? HnUrl.LITTLE_VIDEO_PRAISE : HnUrl.LITTLE_VIDEO_CANCEL_PRAISE, params, "取消点赞短视频或者文章", new HnResponseHandler<PraiseModel>(PraiseModel.class) {
            @Override
            public void hnSuccess(String response) {
                refreshPraiseUI(type, position);
                HnToastUtils.showToastShort(isPraise ? "点赞成功" : "取消点赞成功");
            }

            @Override
            public void hnErr(int errCode, String msg) {
            }
        });
    }

    private void refreshPraiseUI(String type, int position) {
        if (type.equals(ARTICLE_TYPE)) {
            changeItemData(mWordData, position);
        } else {
            changeItemData(mData, position);
        }
        mAdapter.notifyItemChanged(position);
    }

    private void changeItemData(List<LittleVideoModel.LittleVideoItem> mData, int position) {
        mData.get(position).setHas_praise(mData.get(position).getHas_praise() == 1 ? 0 : 1);
        boolean isPraise = mData.get(position).getHas_praise() == 1;
        int praiseNumber = isPraise ? Integer.valueOf(mData.get(position).getPraise_num()) + 1 :
                Integer.valueOf(mData.get(position).getPraise_num()) - 1;
        mData.get(position).setPraise_num(praiseNumber + "");
    }

    private void bindVideoData(BaseViewHolder holder, final int position) {
        final LittleVideoModel.LittleVideoItem itemData = mData.get(position);

        holder.getView(R.id.article_content_layout).setVisibility(View.GONE);
        holder.getView(R.id.video_click_layout).setVisibility(View.VISIBLE);

        ((FrescoImageView) holder.getView(R.id.little_video_introduce_img)).setImageURI(HnUrl.setImageUri(itemData.getFace()));
        ((TextView) holder.getView(R.id.little_video_title)).setText(itemData.getTitle());
        ((FrescoImageView) holder.getView(R.id.user_head_img)).setImageURI(HnUrl.setImageUri(itemData.getUser_avatar()));
        ((TextView) holder.getView(R.id.user_nickname)).setText(itemData.getUser_nickname());
        ((TextView) holder.getView(R.id.little_video_comment_number)).setText(itemData.getComment_num());
        ((TextView) holder.getView(R.id.little_video_like_number)).setText(itemData.getPraise_num());
        ((ImageView) holder.getView(R.id.little_video_like)).setImageDrawable(getResources().getDrawable(
                itemData.getHas_praise() == 1 ? R.drawable.little_video_like : R.drawable.little_video_unlike));

        ((TextView) holder.getView(R.id.little_video_publish_data)).setText(HnDateUtils.getMouthAndDayByTimeStamp(Long.valueOf(itemData.getCreate_time())));
        holder.getView(R.id.video_click_layout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LittleVideoDetailsAct.open(getContext(),itemData.getVideos_id());
            }
        });
        holder.getView(R.id.little_video_comment).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LittleVideoDetailsAct.open(getContext(),itemData.getVideos_id());
            }
        });

        holder.getView(R.id.little_video_like).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                praiseOrCancelPraise(itemData.getHas_praise() != 1, VIDEO_TYPE, itemData.getVideos_id(), position);
            }
        });
    }

    private void bindTextData(BaseViewHolder holder, final int position) {
        final LittleVideoModel.LittleVideoItem itemData = mWordData.get(position);

        holder.getView(R.id.little_video_title).setVisibility(View.GONE);
        holder.getView(R.id.video_click_layout).setVisibility(View.GONE);
        holder.getView(R.id.article_content_layout).setVisibility(View.VISIBLE);

        ((FrescoImageView) holder.getView(R.id.user_head_img)).setImageURI(HnUrl.setImageUri(itemData.getUser_avatar()));
        ((TextView) holder.getView(R.id.article_content_layout)).setText(itemData.getContent());
        ((TextView) holder.getView(R.id.user_nickname)).setText(itemData.getUser_nickname());
        ((TextView) holder.getView(R.id.little_video_comment_number)).setText(itemData.getComment_num());
        ((TextView) holder.getView(R.id.little_video_like_number)).setText(itemData.getPraise_num());
        ((TextView) holder.getView(R.id.little_video_publish_data)).setText(HnDateUtils.getMouthAndDayByTimeStamp(Long.valueOf(itemData.getCreate_time())));
        ((ImageView) holder.getView(R.id.little_video_like)).setImageDrawable(getResources().getDrawable(
                itemData.getHas_praise() == 1 ? R.drawable.little_video_like : R.drawable.little_video_unlike));

        holder.getView(R.id.article_content_layout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArticleDetailsAct.open(getContext(), itemData.getArticle_id());
            }
        });
        holder.getView(R.id.little_video_comment).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArticleDetailsAct.open(getContext(), itemData.getArticle_id());
            }
        });
        holder.getView(R.id.little_video_like).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                praiseOrCancelPraise(itemData.getHas_praise() != 1, ARTICLE_TYPE, itemData.getArticle_id(), position);
            }
        });
    }

    @Override
    protected RequestParams setRequestParam() {
        RequestParams params = new RequestParams();
        params.put("is_recommend", isRecommend());
        params.put("is_follow", isFollow());
        return params;
    }

    @Override
    protected String setRequestUrl() {
        return isOnlyWordContent() ? HnUrl.LITTLE_VIDEO_WORD_LIST : HnUrl.LITTLE_VIDEO_LIST;
    }


    @Override
    protected HnResponseHandler setResponseHandler(final HnRefreshDirection state) {
        return new HnResponseHandler<LittleVideoModel>(LittleVideoModel.class) {
            @Override
            public void hnSuccess(String response) {

                if (mActivity == null) return;
                refreshFinish();
                if (model.getD().getItems() == null) {
                    setEmpty(HnUiUtils.getString(R.string.not_little_video_content), R.drawable.empty_com);
                    return;
                }
                if (HnRefreshDirection.BOTTOM != state) {
                    if (isOnlyWordContent()) {
                        mWordData.clear();
                    } else {
                        mData.clear();
                    }

                }
                if (isOnlyWordContent()) {
                    mWordData.addAll(model.getD().getItems());
                } else {
                    mData.addAll(model.getD().getItems());
                }

                if (mAdapter != null)
                    mAdapter.notifyDataSetChanged();
                setEmpty(HnUiUtils.getString(R.string.not_little_video_content), R.drawable.empty_com);

            }

            @Override
            public void hnErr(int errCode, String msg) {
                refreshFinish();
                setEmpty(HnUiUtils.getString(R.string.not_little_video_content), R.drawable.empty_com);

            }
        };
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(OrderRefreshEvent event) {

    }

}
