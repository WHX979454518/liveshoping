package com.live.shoplib.ui.dialog;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hn.library.base.baselist.BaseViewHolder;
import com.hn.library.base.baselist.CommRecyclerAdapter;
import com.live.shoplib.R;
import com.live.shoplib.bean.GoodsDetailsModel;
import com.live.shoplib.bean.SpikeGoodsDetailsModel;
import com.reslibrarytwo.HnSkinTextView;


/**
 * 商品详情-商品属性，商品服务
 * 作者：Mr.X
 * 时间：10:48
 */
@RequiresApi(api = Build.VERSION_CODES.HONEYCOMB)
@SuppressLint("ValidFragment")
public class SpikeAttrDialog extends DialogFragment {

    private static SpikeAttrDialog dialog;
    private TextView mTvTitle, mTvNext;
    private RecyclerView mRecycler;
    private SpikeGoodsDetailsModel.DEntity bean;
    private String title;

    public static SpikeAttrDialog newInstance(SpikeGoodsDetailsModel.DEntity bean, String title) {
        dialog = new SpikeAttrDialog(bean, title);
        return dialog;
    }


    @SuppressLint("ValidFragment")
    public SpikeAttrDialog(SpikeGoodsDetailsModel.DEntity bean, String title) {
        this.title = title;
        this.bean = bean;
    }

    @RequiresApi(api = Build.VERSION_CODES.HONEYCOMB)
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        View view = inflater.inflate(R.layout.dialog_attr_pro, null);
        mRecycler = view.findViewById(R.id.mRecycler);
        mTvNext = view.findViewById(R.id.mTvNext);
        mTvTitle = view.findViewById(R.id.mTvTitle);
        mTvNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        view.findViewById(R.id.mRlBg).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        mTvTitle.setText(title);
        mRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecycler.setAdapter(new CommRecyclerAdapter() {
            @Override
            protected void onBindView(BaseViewHolder holder, final int position) {
                TextView mTvTag1 = holder.getView(R.id.mTvTag1);
                HnSkinTextView mTvTag2 = holder.getView(R.id.mTvTag2);
                if ("产品参数".equals(title)) {
                    try {
                        String msg = bean.getGoods().getGoods_attr().get(position);
                        mTvTag1.setVisibility(View.VISIBLE);
                        mTvTag1.setText(msg.substring(0, msg.indexOf(":")));
                        mTvTag2.setText(msg.substring(msg.indexOf(":") + 1));
                    } catch (Exception e) {
                        e.printStackTrace();
                        mTvTag1.setVisibility(View.GONE);
                        mTvTag2.setText("暂无参数");
                    }
                } else {
                    mTvTag1.setVisibility(View.GONE);
                    mTvTag2.setText(bean.getGoods().getGoods_promise().get(position));
                    mTvTag2.setLeftDrawable(R.drawable.products_selected);

                }
            }

            @Override
            protected int getLayoutID(int position) {
                return R.layout.item_attr_str;
            }

            @Override
            public int getItemCount() {
                return "产品参数".equals(title) ? bean.getGoods().getGoods_attr().size() : bean.getGoods().getGoods_promise().size();
            }
        });

        Dialog dialog = new Dialog(getActivity(), R.style.PXDialog);
        dialog.setContentView(view);
        Window window = dialog.getWindow();
        window.setGravity(Gravity.BOTTOM);
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = -1;
        params.height = -1;
        window.setAttributes(params);
        return dialog;
    }


}
