package com.hotniao.live.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.hn.library.base.baselist.BaseViewHolder;
import com.hn.library.base.baselist.CommRecyclerAdapter;
import com.hn.library.global.HnUrl;
import com.hn.library.http.HnResponseHandler;
import com.hn.library.refresh.PtrFrameLayout;
import com.hn.library.utils.HnRefreshDirection;
import com.hn.library.utils.HnUiUtils;
import com.hn.library.view.FrescoImageView;
import com.hn.library.view.HnRecyclerGridDecoration;
import com.hotniao.live.R;
import com.hotniao.live.activity.HnAboutActivity;
import com.hotniao.live.model.GoodsTypeListModel;
import com.hotniao.live.model.SpikeListModel;
import com.live.shoplib.ShopActFacade;
import com.live.shoplib.bean.OrderRefreshEvent;
import com.live.shoplib.ui.GoodsDetailsAct;
import com.loopj.android.http.RequestParams;
import com.reslibrarytwo.UpdateCommListFragment;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

/**
 * 溯源fragment
 * 作者：Alan
 * 时间：2019/6/15
 */
public class FindSourceItemFragment extends UpdateCommListFragment {

    private List<GoodsTypeListModel.DBean.GoodsBean.ItemsBean> mData = new ArrayList<>();
    //    private String[] goodsTitleArray = new String[]{"新鲜的牛油果", "8424大西瓜", "龙泉大水蜜桃", "重庆红心鸡血橙子", "新疆水晶葡萄","日本神户牛排","小清新蛋糕"};
//    private String[] goodsPriceArray = new String[]{"38.90", "4.50", "9.80", "6.90", "10.99","1200.00","133.99"};
//    private int[] goodsDrawableArray = new int[]{
//            R.drawable.find_source_avocado,
//            R.drawable.find_source_watermelon,
//            R.drawable.find_source_honeypeach,
//            R.drawable.find_source_orange,
//            R.drawable.find_source_grape,
//            R.drawable.find_source_beef,
//            R.drawable.find_source_cake,
//    };
    private String flag = "";

    public static FindSourceItemFragment newInstance(String flag) {
        FindSourceItemFragment fragment = new FindSourceItemFragment();
        Bundle bundle = new Bundle();
        //todo 分类的相关标识获取
        bundle.putString("flag", flag);

        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected String setTAG() {
        EventBus.getDefault().register(this);
        flag = getArguments().getString("flag");
        setMode(PtrFrameLayout.Mode.LOAD_MORE);
        initRecycleView();
        return "溯源item";
    }

    private void initRecycleView() {
        setGridManager(true);
        mRecycler.setBackgroundColor(getResources().getColor(R.color.live_recycle_ckg));
        mRecycler.addItemDecoration(new HnRecyclerGridDecoration(6));
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
                return R.layout.item_find_source;
            }

            @Override
            public int getItemCount() {
                return mData.size();
//                return 7;
            }
        };
    }

    private void bindView(BaseViewHolder holder, final int position) {
        //todo 绑定item的数据
        ((FrescoImageView) holder.getView(R.id.find_source_introduce_img)).setImageURI(HnUrl.setImageUri(mData.get(position).getGoods_img()));
        ((TextView) holder.getView(R.id.find_source_goods_title)).setText(mData.get(position).getGoods_name());
        ((TextView) holder.getView(R.id.find_source_goods_price)).setText("￥" + mData.get(position).getGoods_price());
        ((FrescoImageView) holder.getView(R.id.find_source_introduce_img)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShopActFacade.openGoodsVideoDetails(mData.get(position).getGoods_id());
//                ShopActFacade.openGoodsVideoDetails("22");
            }
        });
    }


    @Override
    protected RequestParams setRequestParam() {
        RequestParams params = new RequestParams();
        params.put("id", flag);
        return params;
    }

    @Override
    protected String setRequestUrl() {
        return HnUrl.GOODSTYPELIST;
    }


    @Override
    protected HnResponseHandler setResponseHandler(final HnRefreshDirection state) {
        return new HnResponseHandler<GoodsTypeListModel>(GoodsTypeListModel.class) {
            @Override
            public void hnSuccess(String response) {

                if (mActivity == null) return;
                refreshFinish();
                if (model.getD().getGoods() == null) {
                    setEmpty(HnUiUtils.getString(R.string.not_little_video_content), R.drawable.empty_com);
                    return;
                }
                if (HnRefreshDirection.BOTTOM != state) {
                    mData.clear();
                }
                mData.addAll(model.getD().getGoods().getItems());

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


    //todo 确定这里要不要做监听
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(OrderRefreshEvent event) {

    }

}
