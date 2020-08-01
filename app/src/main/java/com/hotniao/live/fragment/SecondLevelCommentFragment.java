package com.hotniao.live.fragment;

import android.os.Bundle;
import android.widget.TextView;

import com.hn.library.base.baselist.BaseViewHolder;
import com.hn.library.base.baselist.CommRecyclerAdapter;
import com.hn.library.global.HnUrl;
import com.hn.library.http.HnResponseHandler;
import com.hn.library.refresh.PtrFrameLayout;
import com.hn.library.utils.HnDateUtils;
import com.hn.library.utils.HnRefreshDirection;
import com.hn.library.view.FrescoImageView;
import com.hotniao.live.R;
import com.hotniao.live.eventbus.RefreshCommentEvent;
import com.hotniao.live.model.LittleVideoFirstCommentModel;
import com.loopj.android.http.RequestParams;
import com.reslibrarytwo.UpdateCommListFragment;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import static com.hotniao.live.model.LittleVideoFirstCommentModel.COMMENT_TYPE;

/**
 * 一级评论fragment
 * 作者：Alan
 * 时间：2019/6/15
 */
public class SecondLevelCommentFragment extends UpdateCommListFragment {

    private List<LittleVideoFirstCommentModel.FirstCommentItem> mData = new ArrayList<>();
    private String commentId = "";

    public static SecondLevelCommentFragment newInstance(String commentId) {
        SecondLevelCommentFragment fragment = new SecondLevelCommentFragment();
        Bundle bundle = new Bundle();
        bundle.putString("commentId", commentId);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected String setTAG() {
        EventBus.getDefault().register(this);
        commentId = getArguments().getString("commentId");
        setMode(PtrFrameLayout.Mode.LOAD_MORE);
        initRecycleView();
        return "二级评论";
    }

    private void initRecycleView() {
        mRecycler.setBackgroundColor(getResources().getColor(R.color.white));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
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
                return R.layout.item_second_level_comment;
            }

            @Override
            public int getItemCount() {
               return mData.size();

            }
        };
    }

    private void bindView(BaseViewHolder holder, final int position) {
        final LittleVideoFirstCommentModel.FirstCommentItem itemData = mData.get(position);

        ((FrescoImageView) holder.getView(R.id.comment_publisher_head_img)).setImageURI(HnUrl.setImageUri(itemData.getUser_avatar()));
        ((TextView) holder.getView(R.id.comment_publisher_username)).setText(itemData.getUser_nickname());
        ((TextView) holder.getView(R.id.comment_time)).setText(HnDateUtils.getMouthAndDayByTimeStamp(Long.valueOf(itemData.getCreate_time())));
        ((TextView) holder.getView(R.id.comment_content)).setText(itemData.getComment());

    }


    @Override
    protected RequestParams setRequestParam() {
        RequestParams params = new RequestParams();
        params.put("id", commentId);
        params.put("type", COMMENT_TYPE);
        return params;
    }

    @Override
    protected String setRequestUrl() {
        return HnUrl.LITTLE_VIDEO_COMMENT_LIST;
    }



    @Override
    protected HnResponseHandler setResponseHandler(final HnRefreshDirection state) {
        return new HnResponseHandler<LittleVideoFirstCommentModel>(LittleVideoFirstCommentModel.class) {
            @Override
            public void hnSuccess(String response) {

                if (mActivity == null) return;
                refreshFinish();
                if (model.getD().getItems() == null) {
                    return;
                }
                if (HnRefreshDirection.BOTTOM != state) {
                    mData.clear();
                }
                mData.addAll(model.getD().getItems());

                if (mAdapter != null)
                    mAdapter.notifyDataSetChanged();

            }

            @Override
            public void hnErr(int errCode, String msg) {
                refreshFinish();


            }
        };
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(RefreshCommentEvent event) {
        if (commentId.equals(event.getId()) && LittleVideoFirstCommentModel.COMMENT_TYPE.equals(event.getType())) {
            //当有新评论增加的时候，客户端主动拉去最新的评论信息
            getData(HnRefreshDirection.TOP, 1);
        }
    }

}
