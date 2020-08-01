package com.hn.library.picker.address_picker;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.widget.TextView;

import com.hn.library.R;
import com.hn.library.picker.wheel.OnWheelChangedListener;
import com.hn.library.picker.wheel.OnWheelClickedListener;
import com.hn.library.picker.wheel.WheelView;
import com.hn.library.picker.wheel.adapter.AddressWheelTextAdapter;
import com.hn.library.utils.HnLogUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 文本单选
 * 作者：Mr.X
 * 时间：16:46
 */
public class StringOneDialog extends Dialog {


    private Activity mContext;
    private ArrayList<StoreGroupModel.DBean.ItemsBean> mData = new ArrayList<>();
    AddressWheelTextAdapter provinceAdapter;
    WheelView countiesWheel;

    public interface onCityPickedListener {
        void onPicked(StoreGroupModel.DBean.ItemsBean msg);
    }


    public StringOneDialog(Activity context, List<StoreGroupModel.DBean.ItemsBean> data, final onCityPickedListener listener) {
        super(context);
        mContext = context;
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getWindow().setGravity(Gravity.BOTTOM);
        getWindow().setBackgroundDrawable(
                new ColorDrawable(Color.parseColor("#00000000")));
        getWindow().setWindowAnimations(R.style.AnimBottom);
        View rootView = getLayoutInflater().inflate(R.layout.dialog_string_one, null);
        int screenWidth = mContext.getWindowManager().getDefaultDisplay().getWidth();
        LayoutParams params = new LayoutParams(screenWidth, LayoutParams.MATCH_PARENT);
        super.setContentView(rootView, params);

        mData.addAll(data);
        initViews();

        View done = findViewById(R.id.done);
        done.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onPicked(mData.get(countiesWheel.getCurrentItem()));
                }
                dismiss();
            }
        });

        View cancel = findViewById(R.id.cancel);
        cancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                dismiss();
            }
        });


    }


    private void initViews() {

        countiesWheel = (WheelView) findViewById(R.id.wheel);

        provinceAdapter = new AddressWheelTextAdapter(mContext, R.layout.wheel_text) {
            @Override
            public int getItemsCount() {

                return mData.size();
            }

            @Override
            protected CharSequence getItemText(int index) {
                return mData.get(index).getName();
            }
        };

        countiesWheel.setViewAdapter(provinceAdapter);
        countiesWheel.setCyclic(false);
        countiesWheel.setVisibleItems(5);

        OnWheelClickedListener clickListener = new OnWheelClickedListener() {

            @Override
            public void onItemClicked(WheelView wheel, int itemIndex) {
                if (itemIndex != wheel.getCurrentItem()) {
                    wheel.setCurrentItem(itemIndex, true, 500);
                }
            }
        };

        countiesWheel.addClickingListener(clickListener);

    }
}
