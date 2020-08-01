package com.live.shoplib.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import com.hn.library.utils.HnDimenUtil;
import com.hn.library.utils.HnToastUtils;
import com.live.shoplib.R;


public class RefundDialog extends Dialog {

    public Context context;
    public EditText mEdContent;
    public TextView mTvSure;
    private static RefundDialog dialog;


    public interface OneSelDialog {
        void sureClick(String msg);
    }

    public static RefundDialog newInstance(Context context) {
        dialog = new RefundDialog(context);
        return dialog;
    }

    public RefundDialog(Context context) {
        super(context, R.style.PXDialog);
        this.context = context;
        init();
    }


    public RefundDialog showDialog() {
        if (dialog != null) {
            dialog.show();
            return dialog;
        }
        return dialog;
    }

    private void init() {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.dialog_refund, null);
        setContentView(view);
        mEdContent = view.findViewById(R.id.mEdContent);
        mTvSure = view.findViewById(R.id.mTvSure);

        Window dialogWindow = this.getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.width = HnDimenUtil.dp2px(context, -1); // 宽度
        lp.height = HnDimenUtil.dp2px(context, -1); // 高度
        dialogWindow.setAttributes(lp);
    }


    public RefundDialog setClickListen(final OneSelDialog listen) {
        //确定
        mTvSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(mEdContent.getText().toString())){
                    HnToastUtils.showToastShort("请输入拒绝理由");
                    return;
                }
                if (listen != null)
                    listen.sureClick(mEdContent.getText().toString());
                if (RefundDialog.this.isShowing())
                    RefundDialog.this.dismiss();
            }
        });
        return dialog;
    }
}
