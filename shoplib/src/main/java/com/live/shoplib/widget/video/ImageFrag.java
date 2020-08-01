package com.live.shoplib.widget.video;

import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.hn.library.base.BaseFragment;
import com.hn.library.global.HnUrl;
import com.hn.library.view.FrescoImageView;
import com.live.shoplib.R;

/**
 * 作者：Mr.Xu
 * 时间：2018/1/2
 */
public class ImageFrag extends BaseFragment {

    private FrescoImageView mIvIco;
    private String img;

    public static Fragment newInstance(String img) {
        ImageFrag frag = new ImageFrag();
        Bundle bundle = new Bundle();
        bundle.putString("img", img);
        frag.setArguments(bundle);
        return frag;
    }

    @Override
    public int getContentViewId() {
        return R.layout.frag_img;
    }

    @Override
    public void initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mIvIco = mRootView.findViewById(R.id.mIvIco);
        img = getArguments().getString("img");
    }

    @Override
    protected void initData() {
        mIvIco.setImageURI(Uri.parse(HnUrl.setImageUrl(img)));
    }
}
