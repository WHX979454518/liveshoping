package com.hotniao.live;

import android.net.Uri;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseViewHolder;
import com.hn.library.global.HnUrl;
import com.hn.library.view.FrescoImageView;
import com.hotniao.live.model.HnHomeLiveModel;
import com.hotniao.live.model.bean.HnHomeHotBean;
import com.hn.library.utils.StringUtils;
import com.hotniao.livelibrary.util.HnLiveSwitchDataUitl;
import com.jakewharton.rxbinding2.view.RxView;

import java.util.concurrent.TimeUnit;

import io.reactivex.functions.Consumer;

/**
 * 进入直播间中介类
 * item_comm_live 布局配置
 * 作者：Mr.Xu
 * 时间：2017/12/8
 */
public class LiveItemProxy {

    /**
     * 直播类型的数据结构 适配一
     */
    public static void setItemView_Live(final BaseViewHolder helper, final HnHomeLiveModel.DEntity.StoreEntity.ItemsEntity item, String tag) {

        String[] strings = item.getTable().split(",");
        String top1 = "", top2= "";
        if (strings.length >= 1) {
            top1 = strings[0];
        }
        if (strings.length >= 2) {
            top2 = strings[1];
        }

//        String top1 = item.getTable().contains(",") ? item.getTable().substring(0, item.getTable().indexOf(",")) : item.getTable();
//        String top2 = item.getTable().contains(",") ? item.getTable().substring(item.getTable().indexOf(",")) : "";

        setItemView((FrescoImageView) helper.getView(R.id.mIvIco), item.getAnchor_live_img(),
                (TextView) helper.getView(R.id.mTvTitle), item.getAnchor_live_title(),
                (TextView) helper.getView(R.id.mTvTop1), top1,
                (TextView) helper.getView(R.id.mTvTop2), top2,
                (TextView) helper.getView(R.id.mTvAddress), item.getAnchor_local(),
                (TextView) helper.getView(R.id.mTvState), item.getAnchor_is_live(),
                (TextView) helper.getView(R.id.mTvNum), item.getAnchor_live_onlines(),
                tag);

        RxView.clicks(helper.itemView).throttleFirst(2, TimeUnit.SECONDS).subscribe(new Consumer<Object>() {
            @Override
            public void accept(Object o) throws Exception {
                if (!TextUtils.isEmpty(item.getAnchor_id())) {
                    HnLiveSwitchDataUitl.joinRoom(helper.itemView.getContext(), item.getAnchor_id(), item.getStore_id());
                } else if (!TextUtils.isEmpty(item.getUser_id())) {
                    HnLiveSwitchDataUitl.joinRoom(helper.itemView.getContext(), item.getUser_id(), item.getStore_id());
                } else {
                    HnLiveSwitchDataUitl.joinRoom(helper.itemView.getContext(), item.getAnchor_user_id(), item.getStore_id());
                }
            }
        });
    }

    /**
     * 适配方法二
     */
    public static void setItemView_Live(
            final com.hn.library.base.baselist.BaseViewHolder helper,
            final HnHomeLiveModel.DEntity.StoreEntity.ItemsEntity item,
            String tag) {
        String[] strings = item.getTable().split(",");
        String top1 = "", top2= "";
        if (strings.length >= 1) {
            top1 = strings[0];
        }
        if (strings.length >= 2) {
            top2 = strings[1];
        }

//        String top1 = item.getTable().contains(",") ? item.getTable().substring(0, item.getTable().indexOf(",")) : item.getTable();
//        String top2 = item.getTable().contains(",") ? item.getTable().substring(item.getTable().indexOf(",")) : "";

        setItemView(
                (FrescoImageView) helper.getView(R.id.mIvIco), item.getAnchor_live_img(),
                (TextView) helper.getView(R.id.mTvTitle), item.getAnchor_live_title(),
                (TextView) helper.getView(R.id.mTvTop1), top1,
                (TextView) helper.getView(R.id.mTvTop2), top2,
                (TextView) helper.getView(R.id.mTvAddress), item.getAnchor_local(),
                (TextView) helper.getView(R.id.mTvState), item.getAnchor_is_live(),
                (TextView) helper.getView(R.id.mTvNum), item.getAnchor_live_onlines(),
                tag);

        RxView.clicks(helper.itemView).throttleFirst(2, TimeUnit.SECONDS).subscribe(new Consumer<Object>() {
            @Override
            public void accept(Object o) throws Exception {
//                if (!TextUtils.isEmpty(item.getAnchor_id())) {
//                    HnLiveSwitchDataUitl.joinRoom(helper.itemView.getContext(), item.getAnchor_id(), item.getStore_id());
//                } else
                    if (!TextUtils.isEmpty(item.getUser_id())) {
                    HnLiveSwitchDataUitl.joinRoom(helper.itemView.getContext(), item.getUser_id(), item.getStore_id());
                } else {
                    HnLiveSwitchDataUitl.joinRoom(helper.itemView.getContext(), item.getAnchor_user_id(), item.getStore_id());
                }
            }
        });
    }

    /**
     * tag是做区分
     */
    public static void setItemView(FrescoImageView ivIco, String url,
                                   TextView tvTitle, String title,
                                   TextView tvTop1, String top1,
                                   TextView tvTop2, String top2,
                                   TextView tvAdd, String address,
                                   TextView tvState, String state,
                                   TextView tvNum, String num,
                                   String tag) {
        //封面
        ivIco.setImageURI(HnUrl.setImageUri(url));
        //标题
        tvTitle.setText(title);

        //话题
        if (TextUtils.isEmpty(top1)) {
            tvTop1.setVisibility(View.GONE);
        } else {
            tvTop1.setVisibility(View.VISIBLE);
            tvTop1.setText(top1);
        }
        if (TextUtils.isEmpty(top2)) {
            tvTop2.setVisibility(View.GONE);
        } else {
            tvTop2.setVisibility(View.VISIBLE);
            tvTop2.setText(top2);
        }
        //地址
        tvAdd.setText(TextUtils.isEmpty(address) ? "未知" : address);
        //区分
        if (tag.equals("推荐")) {
            tvNum.setVisibility(View.GONE);
        } else {
            //人数
            StringUtils.setNum(tvNum, num + "人");
        }

        //类型
        if ("Y".equals(state)) {
            tvState.setVisibility(View.VISIBLE);
            tvNum.setVisibility(View.VISIBLE);
        } else {
            tvState.setVisibility(View.GONE);
            tvNum.setVisibility(View.GONE);
        }
    }


}
