package com.hotniao.livelibrary.ui.gift;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.net.Uri;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.hn.library.HnBaseApplication;
import com.hn.library.global.NetConstant;
import com.hn.library.http.HnHttpUtils;
import com.hn.library.utils.HnLogUtils;
import com.hn.library.utils.HnUiUtils;
import com.hn.library.view.FrescoImageView;
import com.hotniao.livelibrary.R;
import com.hotniao.livelibrary.biz.gift.HnGiftBiz;
import com.hotniao.livelibrary.config.HnLiveConstants;
import com.hotniao.livelibrary.giflist.HnDonwloadGiftStateListener;
import com.hotniao.livelibrary.giflist.bean.GiftListBean;
import com.hotniao.livelibrary.model.bean.HnGiftListBean;
import com.hotniao.livelibrary.model.event.HnLiveEvent;
import com.jakewharton.rxbinding2.view.RxView;
import com.loopj.android.http.FileAsyncHttpResponseHandler;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import cz.msebera.android.httpclient.Header;
import io.reactivex.android.schedulers.AndroidSchedulers;


/**
 * Copyright (C) 2017,深圳市红鸟网络科技股份有限公司
 * 项目名称：点E点
 * 类描述：私信聊天列表适配器
 * 创建人：Mr.Xu
 * 创建时间：2017/3/22 0022
 */
public class HnGiftAdapter extends RecyclerView.Adapter<HnGiftAdapter.ViewHolder> {

    private LayoutInflater mInflater;
    private List<HnGiftListBean.GiftBean.ItemsBean> mData;
    private Context mContext;
    private int mTime = 1;
    private boolean isLive = true;


    /**
     * 动画
     */
//    private AnimatorSet animatorSet;
    private ScaleAnimation mGiftAnim;

    public HnGiftAdapter(Context context, List<HnGiftListBean.GiftBean.ItemsBean> data, boolean isLive) {
        mInflater = LayoutInflater.from(context);
        this.mData = data;
        this.mContext = context;
        this.isLive = isLive;
        mGiftAnim = (ScaleAnimation) AnimationUtils.loadAnimation(context, R.anim.gift_anim);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = mInflater.inflate(R.layout.item_gift, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().post(new HnGiftEven(mData.get(position).getName(), mData.get(position).getIcon(),
                        mData.get(position).getCoin(), mData.get(position).getId()));
            }
        });
        holder.mTvGiftCoin.setText(mData.get(position).getCoin() + HnBaseApplication.getmConfig().getCoin());
        holder.mTvGiftName.setText(mData.get(position).getName());
        holder.mIvGift.setImageURI(NetConstant.setImageUri(mData.get(position).getIcon()));
        if (mData.get(position).isCheck()) {
            holder.mIvSelect.setVisibility(View.VISIBLE);
            String gif = mData.get(position).getIcon_gif();
            if (TextUtils.isEmpty(gif)) {
                holder.mIvGift.startAnimation(mGiftAnim);
            } else {
                holder.mIvGift.clearAnimation();
                String localGifPath = mData.get(position).getLocalGifPath();
                if (TextUtils.isEmpty(localGifPath)) {
                    downLoadGifPic(gif, mData.get(position).getId(), holder.mIvGift, position);
                } else {
                    if(mContext==null)return;
                    Glide.with(mContext).load(localGifPath).asGif().diskCacheStrategy(DiskCacheStrategy.SOURCE).into(holder.mIvGift);
                }
            }

        } else {
            holder.mIvSelect.setVisibility(View.INVISIBLE);
            holder.mIvGift.clearAnimation();
        }
    }


    /**
     * 清除列表选择状态
     */
    public void clearSelected() {
        for (HnGiftListBean.GiftBean.ItemsBean bean : mData) {
            bean.setCheck(false);
        }
        notifyDataSetChanged();
    }

    /**
     * 更新选中
     */
    public void updateSelected(String id) {
        for (HnGiftListBean.GiftBean.ItemsBean bean : mData) {
            if (id.equals(bean.getId())) {
                bean.setCheck(true);
            }
        }
        //刷新UI
        notifyDataSetChanged();
    }


    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView mTvGiftCoin, mTvGiftName;
        FrescoImageView mIvGift;
        RelativeLayout mLLGiftBg;
        ImageView mIvSelect;

        ViewHolder(View view) {
            super(view);
            if (view != null) {
                mTvGiftCoin = (TextView) view.findViewById(R.id.mTvGiftCoin);
                mTvGiftName = (TextView) view.findViewById(R.id.mTvGiftName);
                mIvGift = (FrescoImageView) view.findViewById(R.id.mIvGift);
                mLLGiftBg = (RelativeLayout) view.findViewById(R.id.mLLGiftBg);
                mIvSelect = (ImageView) view.findViewById(R.id.mIvSelect);
            }
        }
    }

    private void downLoadGifPic(final String giftUrl, final String id, final FrescoImageView mIvGift, final int position) {
        try {
            String giftDir = mContext.getExternalFilesDir(null).getAbsolutePath() + "/" + mContext.getPackageName() + "/gift/";
            File mainFile = new File(giftDir);
            if (!mainFile.exists()) mainFile.mkdirs();
            int index = giftUrl.lastIndexOf("/");
            String fileName = giftUrl.substring(index + 1, giftUrl.length());
            String path = giftDir + fileName;
            File downloadFile = new File(path);
            if (downloadFile.exists()) downloadFile.delete();
            downloadFile.createNewFile();
            final File finalDownloadFile = downloadFile;
            HnHttpUtils.downloadFile(true, giftUrl, new FileAsyncHttpResponseHandler(finalDownloadFile) {
                @Override
                public void onFailure(int statusCode, Header[] headers, Throwable throwable, File file) {
                    file.delete();
                    if (mContext == null) return;
                    Glide.with(mContext).load(giftUrl).asGif().diskCacheStrategy(DiskCacheStrategy.SOURCE).into(mIvGift);
                }

                @Override
                public void onSuccess(int statusCode, Header[] headers, File file) {
                    EventBus.getDefault().post(new HnLiveEvent(position, HnLiveConstants.EventBus.Download_Gift_Gif_Success, id, file.getAbsolutePath()));
                    if (mContext == null) return;
                    mData.get(position).setLocalGifPath(file.getAbsolutePath());
                    Glide.with(mContext).load(file.getAbsoluteFile()).asGif().diskCacheStrategy(DiskCacheStrategy.SOURCE).into(mIvGift);

                }
            });
        } catch (Exception e) {
            HnLogUtils.e("");
        }
    }
}