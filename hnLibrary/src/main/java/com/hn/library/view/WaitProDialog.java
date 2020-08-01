package com.hn.library.view;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.hn.library.R;
import com.hn.library.utils.HnDimenUtil;

public class WaitProDialog extends Dialog {

    public Context context;
    private static WaitProDialog dialog;
    private TextView mTvPrecent;

    public static WaitProDialog newInstance(Context context) {
        dialog = new WaitProDialog(context);
        return dialog;
    }

    public WaitProDialog(Context context) {
        super(context, R.style.PXDialog);
        this.context = context;
        init();
    }


    private void init() {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.dialog_wait, null);
        mTvPrecent = view.findViewById(R.id.mTvPrecent);
        setContentView(view);
        Window dialogWindow = this.getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.width = HnDimenUtil.dp2px(context, 300); // 宽度
        dialogWindow.setAttributes(lp);
    }


    public void setPrecent(int precent) {
        if (mTvPrecent != null) mTvPrecent.setText(precent + "%");
    }

}
