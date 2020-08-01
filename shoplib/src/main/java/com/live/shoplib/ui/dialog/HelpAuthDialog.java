package com.live.shoplib.ui.dialog;


import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.hn.library.utils.HnDimenUtil;
import com.live.shoplib.R;

public class HelpAuthDialog extends Dialog {

    public Context context;
    private static HelpAuthDialog dialog;

    public static HelpAuthDialog newInstance(Context context) {
        dialog = new HelpAuthDialog(context);
        return dialog;
    }

    public HelpAuthDialog(Context context) {
        super(context, R.style.PXDialog);
        this.context = context;
        init();
    }


    private void init() {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.dialog_help_auth, null);
        setContentView(view);
        findViewById(R.id.mIvBack).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        Window dialogWindow = this.getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.width = HnDimenUtil.dp2px(context, -1); // 宽度
        lp.height = HnDimenUtil.dp2px(context,  -1); // 高度
        dialogWindow.setAttributes(lp);
    }
}
