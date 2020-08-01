package com.live.shoplib.ui.dialog;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.DialogFragment;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hn.library.global.HnUrl;
import com.hn.library.http.HnHttpUtils;
import com.hn.library.http.HnResponseHandler;
import com.hn.library.utils.HnToastUtils;
import com.hn.library.view.FrescoImageView;
import com.live.shoplib.R;
import com.live.shoplib.bean.GoodsAttrBean;
import com.live.shoplib.bean.GoodsAttrModel;
import com.live.shoplib.bean.GoodsDetailsModel;
import com.live.shoplib.other.GoodsDetailsHelper;
import com.loopj.android.http.RequestParams;


/**
 * 商品详情-选择商品
 * 作者：Mr.X
 * 时间：10:48
 */
@RequiresApi(api = Build.VERSION_CODES.HONEYCOMB)
@SuppressLint("ValidFragment")
public class GoodsSelDialog extends DialogFragment {

    private static GoodsSelDialog dialog;
    private RecyclerView mRecycler;
    private GoodsDetailsModel.DEntity bean;

    private TextView mTvPrice;
    private TextView mTvNum;
    private FrescoImageView mIvIco;
    private TextView mTvSure;
    private OnSureClick listner;
    private String mId, mSpec_text, mNum;
    private String go;

    public static GoodsSelDialog newInstance(GoodsDetailsModel.DEntity bean, String go, OnSureClick listner) {
        dialog = new GoodsSelDialog(bean, go, listner);
        return dialog;
    }


    @SuppressLint("ValidFragment")
    public GoodsSelDialog(GoodsDetailsModel.DEntity bean, String go, OnSureClick listner) {
        this.bean = bean;
        this.go = go;
        this.listner = listner;
    }

    @RequiresApi(api = Build.VERSION_CODES.HONEYCOMB)
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        View view = inflater.inflate(R.layout.dialog_good_sel, null);
        mRecycler = view.findViewById(R.id.mRecycler);
        mRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));

        mTvPrice = view.findViewById(R.id.mTvPrice);
        mTvNum = view.findViewById(R.id.mTvNum);
        mIvIco = view.findViewById(R.id.mIvIco);

        try {
            if (Float.valueOf(bean.getGoods().getGoods_price()) >= Float.valueOf(bean.getGoods().getGoods_max_price())) {
                mTvPrice.setText(bean.getGoods().getGoods_price());
            } else {
                mTvPrice.setText(bean.getGoods().getGoods_price() + "~" + bean.getGoods().getGoods_max_price());
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
            mTvPrice.setText(bean.getGoods().getGoods_price());
        }

        mTvNum.setText(bean.getGoods().getGoods_stock());
        mIvIco.setImageURI(Uri.parse(HnUrl.setImageUrl(bean.getGoods().getGoods_img())));

        mTvSure = view.findViewById(R.id.mTvSure);
        mTvSure.setEnabled(false);
        mTvSure.setTextColor(getResources().getColor(R.color.white));
        mTvSure.setBackgroundColor(getResources().getColor(R.color.comm_text_color_black_s));
        mTvSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listner != null) listner.click(mNum, mId, mSpec_text, go);
                dismiss();
            }
        });

        view.findViewById(R.id.mRlBg).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        requestData(bean.getGoods().getGoods_id());
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


    public void setData(final GoodsAttrBean data) {
        GoodsDetailsHelper helper = new GoodsDetailsHelper(false, mRecycler, data, new GoodsDetailsHelper.OnClickStateChange() {
            @Override
            public void onItemClick(String id, String spec_text, String num, String price) {
                if (TextUtils.isEmpty(mId) || !mId.equals(id)) {
                    setChangeStoreAndPrice(data, id);
                }
                mId = id;
                mSpec_text = spec_text;
                mNum = num;
                mTvSure.setEnabled(true);
                if (TextUtils.isEmpty(price)) {
                    if (Float.valueOf(bean.getGoods().getGoods_price()) >= Float.valueOf(bean.getGoods().getGoods_max_price())) {
                        mTvPrice.setText(bean.getGoods().getGoods_price());
                    } else {
                        mTvPrice.setText(bean.getGoods().getGoods_price() + "~" + bean.getGoods().getGoods_max_price());
                    }
                } else {
                    mTvPrice.setText(price);
                }
                mTvSure.setBackgroundColor(getResources().getColor(R.color.main_color));
                mTvSure.setTextColor(getResources().getColor(R.color.black));
            }

            @Override
            public void onItemClickNone() {
                try {
                    if (Float.valueOf(bean.getGoods().getGoods_price()) >= Float.valueOf(bean.getGoods().getGoods_max_price())) {
                        mTvPrice.setText(bean.getGoods().getGoods_price());
                    } else {
                        mTvPrice.setText(bean.getGoods().getGoods_price() + "~" + bean.getGoods().getGoods_max_price());
                    }
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                    mTvPrice.setText(bean.getGoods().getGoods_price());
                }
                mTvNum.setText(bean.getGoods().getGoods_stock());
                mIvIco.setImageURI(Uri.parse(HnUrl.setImageUrl(bean.getGoods().getGoods_img())));
                mTvSure.setEnabled(false);
                mTvSure.setBackgroundColor(getResources().getColor(R.color.comm_text_color_black_s));
                mTvSure.setTextColor(getResources().getColor(R.color.white));

            }
        });
    }

    /**
     * 点击类型之后改变库存和价格
     */
    private void setChangeStoreAndPrice(GoodsAttrBean data, String id) {
        if (!TextUtils.isEmpty(id) && data != null && data.getSku() != null) {
            for (int i = 0; i < data.getSku().size(); i++) {
                if (id.equals(data.getSku().get(i).getSku_id())) {
                    mTvNum.setText(data.getSku().get(i).getStock());
                    mTvPrice.setText(data.getSku().get(i).getPrice());
                }
            }

        }
    }


    private void requestData(String goods_id) {
        RequestParams param = new RequestParams();
        param.put("goods_id", goods_id);
        HnHttpUtils.postRequest(HnUrl.GOODS_ATT, param, "商品规格", new HnResponseHandler<GoodsAttrModel>(GoodsAttrModel.class) {
            @Override
            public void hnSuccess(String response) {
                if (mRecycler == null) return;
                setData(model.getD());
            }


            @Override
            public void hnErr(int errCode, String msg) {
                HnToastUtils.showToastShort(msg);
            }
        });
    }

    public interface OnSureClick {
        void click(String num, String sku_id, String spec, String go);
    }

}
