package com.hotniao.live.activity;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.VideoView;

import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.live.shoplib.bean.BannerModel;
import com.xiao.nicevideoplayer.NiceVideoPlayer;
import com.xiao.nicevideoplayer.NiceVideoPlayerController;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alan on 2019/8/1.
 */
public class BannerViewAdapter extends PagerAdapter {

    private Context context;
    private List<BannerModel> listBean;

    public BannerViewAdapter(Activity context, List<BannerModel> list) {
//        this.context = context.getApplicationContext();
        this.context = context;
        if (list == null || list.size() == 0) {
            this.listBean = new ArrayList<>();
        } else {
            this.listBean = list;
        }
    }


    @Override
    public Object instantiateItem(final ViewGroup container, final int position) {
        final ImageView imageView = new ImageView(context);
        final NiceVideoPlayer videoView = new NiceVideoPlayer(context);
        NiceVideoPlayerController controller = new NiceVideoPlayerController(context);
        controller.setTitle("");
        videoView.setController(controller);

        if (listBean.get(position).getUrlType() == 0) {//图片
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            Glide.with(context).load(listBean.get(position).getBannerUrl())
//                    .skipMemoryCache(true)
                    .into(imageView);
            videoView.pause();
        } else {//视频
            videoView.getmController().setImage(listBean.get(position).getBannerName());
            videoView.setUp(listBean.get(position).getBannerUrl(), null);
//            videoView.start();
//            final VideoView videoView = new VideoView(context);
//            videoView.setVideoURI(Uri.parse(listBean.get(position).getBannerUrl()));
            //开始播放
//            videoView.start();
        }

        if(listBean.get(position).getUrlType() == 0){
            container.addView(imageView);
            return imageView;
        }else {
            container.addView(videoView);
            return videoView;
        }


    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return listBean.size();
    }


    @Override
    public boolean isViewFromObject(View view, Object obj) {
        // TODO Auto-generated method stub
        return view == (View) obj;
    }


}