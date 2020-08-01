package com.live.shoplib.ui.dialog;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.RequiresApi;

import com.hn.library.utils.HnToastUtils;
import com.live.shoplib.R;


/**
 * 作者：Mr.X
 * 时间：10:48
 */
@RequiresApi(api = Build.VERSION_CODES.HONEYCOMB)
@SuppressLint("ValidFragment")
public class GoodsValueDialog extends DialogFragment {

    private RelativeLayout mRlBg;
    private TextView mTvSure;
    private EditText mEdWare;
    private EditText mEdPrice;
    private OnValueSet onValueSet;
    private String ware, value;

    public GoodsValueDialog(String ware, String value, OnValueSet onValueSet) {
        this.onValueSet = onValueSet;
        this.ware = TextUtils.isEmpty(ware) ? "" : ware;
        this.value = TextUtils.isEmpty(value) ? "" : value;
    }

//    public GoodsValueDialog initSetting(String ware, String value, OnValueSet onValueSet) {
//        if (mEdWare == null || onValueSet == null) return null;
//        if(TextUtils.isEmpty(ware)) ware = "0";
//        if(TextUtils.isEmpty(value)) value = "0";
//        mEdWare.setText(ware);
//        mEdPrice.setText(value);
//        this.onValueSet = onValueSet;
//        return this;
//    }

    @RequiresApi(api = Build.VERSION_CODES.HONEYCOMB)
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        View view = inflater.inflate(R.layout.dialog_goods_value, null);

        mTvSure = view.findViewById(R.id.mTvSure);
        mRlBg = view.findViewById(R.id.mRlBg);
        mEdPrice = view.findViewById(R.id.mEdPrice);
        mEdWare = view.findViewById(R.id.mEdWare);
        mEdWare.setText(ware);
        mEdPrice.setText(value);
        mRlBg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        mTvSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(mEdWare.getText().toString())) {
                    HnToastUtils.showToastShort("请输入商品数量");
                    return;
                }
                if (TextUtils.isEmpty(mEdPrice.getText().toString())) {
                    HnToastUtils.showToastShort("请输入商品价格");
                    return;
                }
                if (onValueSet != null)
                    onValueSet.onValue(mEdWare.getText().toString(), mEdPrice.getText().toString());
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


    public interface OnValueSet {
        void onValue(String ware, String value);
    }

}
