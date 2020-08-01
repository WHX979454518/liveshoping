package com.hotniao.live.fragment;

import android.net.Uri;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hn.library.base.baselist.BaseViewHolder;
import com.hn.library.base.baselist.CommRecyclerAdapter;
import com.hn.library.base.baselist.OnItemClickListener;
import com.hn.library.global.HnUrl;
import com.hn.library.http.HnResponseHandler;
import com.hn.library.utils.HnRefreshDirection;
import com.hn.library.utils.HnUiUtils;
import com.hn.library.view.FrescoImageView;
import com.hn.library.view.HnRecyclerGridDecoration;
import com.hotniao.live.R;
import com.hotniao.live.model.LiveListModel;
import com.hotniao.livelibrary.util.HnLiveSwitchDataUitl;
import com.live.shoplib.bean.OrderRefreshEvent;
import com.loopj.android.http.RequestParams;
import com.reslibrarytwo.UpdateCommListFragment;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

/**
 * 直播fragment
 * 作者：Alan
 * 时间：2019/6/15
 */
public class LiveFragment extends UpdateCommListFragment {

    private List<LiveListModel.LiveItem> mData = new ArrayList<>();

    @Override
    protected String setTAG() {
        EventBus.getDefault().register(this);
        setGridManager(true);
        initView();
        return "直播";
    }

    private void initView() {
        rootView.findViewById(R.id.title_split).setVisibility(View.VISIBLE);
        mRecycler.setBackgroundColor(getResources().getColor(R.color.live_recycle_ckg));
        mRecycler.addItemDecoration(new HnRecyclerGridDecoration(6));

        LinearLayout content_layout = rootView.findViewById(R.id.content_layout);
        TextView title = new TextView(getContext());
        title.setGravity(Gravity.CENTER_VERTICAL);
        title.setText("直播");
        title.setTextSize(TypedValue.COMPLEX_UNIT_PX,getResources().getDimension(R.dimen.dp_18));
        title.setTextColor(getResources().getColor(R.color.live_title_color));
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, getResources().getDimensionPixelOffset(R.dimen.dp_50));
        params.gravity = Gravity.CENTER;
        title.setLayoutParams(params);
        content_layout.addView(title, 0);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected CommRecyclerAdapter setAdapter() {
        CommRecyclerAdapter recyclerAdapter =
                new CommRecyclerAdapter() {
            @Override
            protected void onBindView(BaseViewHolder holder, final int position) {
                bindView(holder, position);
            }

            @Override
            protected int getLayoutID(int position) {
                return R.layout.item_live;
            }

            @Override
            public int getItemCount() {
                return mData.size();
            }
        };



        return recyclerAdapter;
    }

    private void bindView(final BaseViewHolder holder, final int position) {
        final LiveListModel.LiveItem item= mData.get(position);
        ((TextView)holder.getView(R.id.live_watch_person_number)).setText(item.getAnchor_live_onlines()+"人观看");
        //封面图
        ((FrescoImageView) holder.getView(R.id.little_video_introduce_img)).setImageURI(Uri.parse(HnUrl.setImageUrl(item.getAnchor_live_img())));
        //直播者头像
        ((FrescoImageView) holder.getView(R.id.live_head_img)).setImageURI(Uri.parse(HnUrl.setImageUrl(item.getIcon())));
        //店铺名字
        ((TextView)holder.getView(R.id.live_shop_name)).setText(item.getName());
        //直播标题
        ((TextView)holder.getView(R.id.live_title)).setText(item.getAnchor_live_title());
        //直播商品名称
        ((TextView)holder.getView(R.id.live_goods_name)).setText(item.getGoods_name());
        //直播商品价格
        ((TextView)holder.getView(R.id.live_goods_price)).setText("￥"+item.getGoods_price());
        //直播点赞数
        ((TextView)holder.getView(R.id.live_like_number)).setText(item.getAnchor_live_like_total());
        //直播状态
        ((TextView) holder.getView(R.id.live_state)).setText(item.getAnchor_is_live() ? "•直播中" : "•已结束");

        holder.getmConvertView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(item.getUser_id())) {
                    HnLiveSwitchDataUitl.joinRoom(holder.itemView.getContext(), item.getUser_id(), item.getStore_id());
                }
            }
        });
    }


    @Override
    protected RequestParams setRequestParam() {
        RequestParams params = new RequestParams();
        return params;
    }

    @Override
    protected String setRequestUrl() {
        return HnUrl.LIVE_LIST;
    }

    @Override
    protected HnResponseHandler setResponseHandler(final HnRefreshDirection state) {
        return new HnResponseHandler<LiveListModel>(LiveListModel.class) {
            @Override
            public void hnSuccess(String response) {

                if (mActivity == null) return;
                refreshFinish();
                if (model.getD().getStore().getItems() == null) {
                    setEmpty(HnUiUtils.getString(R.string.not_live), R.drawable.empty_com);
                    return;
                }
                if (HnRefreshDirection.BOTTOM != state) {
                    mData.clear();
                }
                mData.addAll(model.getD().getStore().getItems());

                if (mAdapter != null)
                    mAdapter.notifyDataSetChanged();
                setEmpty(HnUiUtils.getString(R.string.not_live), R.drawable.empty_com);

            }

            @Override
            public void hnErr(int errCode, String msg) {
                refreshFinish();
                //todo 确定没有直播的图片
                setEmpty(HnUiUtils.getString(R.string.not_live), R.drawable.empty_com);

            }
        };
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }


    //todo 确定这里要不要做监听
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(OrderRefreshEvent event) {

    }

}
