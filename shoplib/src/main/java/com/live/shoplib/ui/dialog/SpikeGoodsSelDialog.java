package com.live.shoplib.ui.dialog;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Dialog;
import android.app.DialogFragment;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hn.library.base.baselist.BaseViewHolder;
import com.hn.library.base.baselist.CommRecyclerAdapter;
import com.hn.library.global.HnUrl;
import com.hn.library.utils.CommonUtils;
import com.hn.library.utils.HnToastUtils;
import com.hn.library.view.FrescoImageView;
import com.live.shoplib.R;
import com.live.shoplib.bean.GoodsAttrBean;
import com.live.shoplib.bean.SpikeGoodsDetailsModel;



/**
 * 商品详情-选择商品
 * 作者：Mr.X
 * 时间：10:48
 */
@RequiresApi(api = Build.VERSION_CODES.HONEYCOMB)
@SuppressLint("ValidFragment")
public class SpikeGoodsSelDialog extends DialogFragment {

    private static SpikeGoodsSelDialog dialog;

    private SpikeGoodsDetailsModel.DEntity bean;

    private TextView mTvPrice;
    private TextView mTvNum;

    private RecyclerView spec_recycler_view;
    private TextView mWillBuyNum;
    private ImageView mIvMin;
    private ImageView mIvAdd;
    private TextView buy_limit_number;
    private FrescoImageView mIvIco;
    private TextView mTvSure;
    private GoodsSelDialog.OnSureClick listner;
    private String mId, mSpec_text, mNum;
    private String go;

    public static SpikeGoodsSelDialog newInstance(SpikeGoodsDetailsModel.DEntity bean, String go, GoodsSelDialog.OnSureClick listner) {
        dialog = new SpikeGoodsSelDialog(bean, go, listner);
        return dialog;
    }


    @SuppressLint("ValidFragment")
    public SpikeGoodsSelDialog(SpikeGoodsDetailsModel.DEntity bean, String go, GoodsSelDialog.OnSureClick listner) {
        this.bean = bean;
        this.go = go;
        this.listner = listner;
    }

    @TargetApi(Build.VERSION_CODES.M)
    @RequiresApi(api = Build.VERSION_CODES.HONEYCOMB)
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        View view = inflater.inflate(R.layout.spike_group_buy_dialog_good_sel, null);

        mTvPrice = view.findViewById(R.id.mTvPrice);
        mIvMin = view.findViewById(R.id.mIvMin);
        mTvNum = view.findViewById(R.id.mTvNum);
        mIvIco = view.findViewById(R.id.mIvIco);
        mIvAdd = view.findViewById(R.id.mIvAdd);
        buy_limit_number = view.findViewById(R.id.buy_limit_number);
        mWillBuyNum = view.findViewById(R.id.mWillBuyNum);

        mTvPrice.setText(bean.getSeckill_info().getSec_price());

        mTvNum.setText(bean.getSeckill_info().getSec_goods_stock());
        mIvIco.setImageURI(Uri.parse(HnUrl.setImageUrl(bean.getGoods().getGoods_img())));

        mTvSure = view.findViewById(R.id.mTvSure);
        mTvSure.setEnabled(true);
        mTvSure.setBackgroundColor(getResources().getColor(R.color.main_color));
        mTvSure.setTextColor(getResources().getColor(R.color.black));
        mTvSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listner != null) listner.click(mNum, mId, mSpec_text, go);
                dismiss();
            }
        });
        setData(getGoodsAttrBean());
        initRecyclerView(view);


        view.findViewById(R.id.mRlBg).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
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

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void initRecyclerView(View view) {
        spec_recycler_view = view.findViewById(R.id.spec_recycler_view);
        spec_recycler_view.setLayoutManager(new LinearLayoutManager(getContext()));
        CommRecyclerAdapter adapter = new CommRecyclerAdapter() {
            @Override
            protected void onBindView(BaseViewHolder holder, int position) {
                GoodsAttrBean.SpecEntity itemData = bean.getSpec().get(position);
                ((TextView) holder.getView(R.id.mTvSecName)).setText(itemData.getGroup());
                if (CommonUtils.hasItem(itemData.getList())) {
                    ((TextView) holder.getView(R.id.tv_tag)).setText(itemData.getList().get(0).getSpec_value());
                    holder.getView(R.id.tv_tag).setBackground(getResources().getDrawable(R.drawable.shape_soild_main_4));
                }
            }

            @Override
            protected int getLayoutID(int position) {
                return R.layout.spike_group_spec_item;
            }

            @Override
            public int getItemCount() {
                return bean.getSpec().size();
            }
        };
        spec_recycler_view.setAdapter(adapter);
    }

    private GoodsAttrBean getGoodsAttrBean() {
        GoodsAttrBean goodsAttrBean = new GoodsAttrBean();
        goodsAttrBean.setGoods_id(bean.getSeckill_info().getSec_goods_id());
        goodsAttrBean.setGoods_img(bean.getGoods().getGoods_img());
        goodsAttrBean.setGoods_name(bean.getGoods().getGoods_name());
        goodsAttrBean.setGoods_price(bean.getSeckill_info().getSec_price());
        goodsAttrBean.setStore_id(bean.getGoods().getStore_id());
        //里面使用的sku_id我们团购商品这里特殊处理设置sku_id为seckill_info().getGoods_id
        if (bean.getSku() != null && bean.getSku().size() > 0 && bean.getSku().get(0) != null) {
            bean.getSku().get(0).setSku_id(bean.getSeckill_info().getSec_goods_id());
        }
        goodsAttrBean.setSku(bean.getSku());
        goodsAttrBean.setSpec(bean.getSpec());
        goodsAttrBean.setNum(1);
        goodsAttrBean.setNum_all(Integer.parseInt(bean.getSeckill_info().getSingle_limit()));
        return goodsAttrBean;
    }


    public void setData(final GoodsAttrBean data) {
        mId = data.getGoods_id();
        mNum = data.getNum() + "";
        if (CommonUtils.hasItem(data.getSpec()) &&
                CommonUtils.hasItem(data.getSku()) &&
                CommonUtils.hasItem(data.getSpec()) &&
                data.getSku().get(0) != null &&
                CommonUtils.hasItem(data.getSpec().get(0).getList())
        ) {
            mSpec_text = data.getSku().get(0).getSpec_text();
            mWillBuyNum.setText(data.getNum() + "");
            buy_limit_number.setText("单个账号最大购买量："+data.getNum_all());
            mIvMin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (data.getNum() == 1) {
                        return;
                    }
                    data.setNum(data.getNum() - 1);
                    mNum = data.getNum() + "";
                    mWillBuyNum.setText(data.getNum() + "");

                }
            });
            mIvAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (data.getNum() >= data.getNum_all()) {
                        HnToastUtils.showToastShort("已达最大购买量");
                        return;
                    }
                    data.setNum(data.getNum() + 1);
                    mNum = data.getNum() + "";
                    mWillBuyNum.setText(data.getNum() + "");
                }
            });
        }
    }
}
