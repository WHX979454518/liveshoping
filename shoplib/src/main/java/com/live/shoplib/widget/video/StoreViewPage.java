package com.live.shoplib.widget.video;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.hn.library.view.CirclePageIndicator;
import com.live.shoplib.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.OnPageChange;

/**
 * 作者：Mr.Xu
 * 时间：2018/1/2
 */
public class StoreViewPage extends FragmentPagerAdapter {

    private FragmentManager fm;
    private List<Fragment> fragments = new ArrayList<>();
    private List<String> data;
    private LinearLayout ll_dots;
    private ArrayList<ImageView> dotsList;
    private Context context;

    public StoreViewPage(FragmentManager fm, Context contexts, ViewPager pager, final LinearLayout ll_dots, String url, List<String> datas) {
        super(fm);
        this.fm = fm;
        this.context = contexts;
        this.ll_dots = ll_dots;
        ll_dots.setVisibility(View.GONE);
        this.data = datas;
        fragments.add(StortVideoFrag.newInstance(url, ""));
        for (int i = 0; i < data.size(); i++) {
            fragments.add(ImageFrag.newInstance(data.get(i)));
        }
        initDots();
        pager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @SuppressLint("LongLogTag")
            @Override
            public void onPageSelected(int pos) {
                Log.e("###############################", pos + "");
                if (pos == 0) {
                    ll_dots.setVisibility(View.GONE);
                } else {
                    ll_dots.setVisibility(View.VISIBLE);
                    for (int i = 0; i < data.size() + 1; i++) {
                        //判断小点点与当前的图片是否对应，对应设置为亮色 ，否则设置为暗色
                        if (i == pos % (data.size() + 1)) {
                            dotsList.get(i).setImageDrawable(
                                    context.getResources().getDrawable(
                                            R.drawable.dots_focuse));
                        } else {
                            dotsList.get(i).setImageDrawable(
                                    context.getResources().getDrawable(
                                            R.drawable.dots_normal));
                        }
                    }
                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }


    @Override
    public Fragment getItem(int pos) {
        return fragments.get(pos);
    }

    @Override
    public int getCount() {
        return Integer.MAX_VALUE;
    }

    @Override
    public int getItemPosition(Object object) {
        return super.getItemPosition(object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        position = position % fragments.size();
        return super.instantiateItem(container, position);
    }

    private void initDots() {
        //创建存放小点点的集合
        dotsList = new ArrayList<ImageView>();
        //每次初始化之前清空集合
        dotsList.clear();
        // 每次初始化之前  移除  布局中的所有小点
        ll_dots.removeAllViews();
        for (int i = 0; i < data.size() + 1; i++) {
            //创建小点点图片
            ImageView imageView = new ImageView(context);
            Drawable drawable = null;
            if (i == 0) {
                // 亮色图片
                drawable = context.getResources().getDrawable(R.drawable.dots_focuse);
            } else {
                drawable = context.getResources().getDrawable(R.drawable.dots_normal);
            }
            imageView.setImageDrawable(drawable);
            // 考虑屏幕适配
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(dip2px(context, 5), dip2px(context, 5));
            //设置小点点之间的间距
            params.setMargins(dip2px(context, 2), 0, dip2px(context, 2), 0);
            //将小点点添加大线性布局中
            ll_dots.addView(imageView, params);
            // 将小点的控件添加到集合中
            dotsList.add(imageView);
        }
    }

    public int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}
