package com.live.shoplib.ui.dialog;


import android.app.Dialog;
import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.TextView;

import com.hn.library.global.HnUrl;
import com.hn.library.http.HnHttpUtils;
import com.hn.library.http.HnResponseHandler;
import com.hn.library.utils.HnDimenUtil;
import com.hn.library.utils.HnToastUtils;
import com.hn.library.utils.HnUtils;
import com.live.shoplib.R;
import com.live.shoplib.bean.RuleModel;
import com.live.shoplib.bean.SellerCenterModel;
import com.loopj.android.http.RequestParams;

public class HelpWithdrawDialog extends Dialog {

    public Context context;
    private static HelpWithdrawDialog dialog;
    private WebView mWebView;
    public static HelpWithdrawDialog newInstance(Context context) {
        dialog = new HelpWithdrawDialog(context);
        return dialog;
    }

    public HelpWithdrawDialog(Context context) {
        super(context, R.style.PXDialog);
        this.context = context;
        init();
    }


    private void init() {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.dialog_help_withdraw, null);
        setContentView(view);
        mWebView = view.findViewById(R.id.mWebView);
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
        requestData();
    }


    private void requestData() {
        RequestParams param = new RequestParams();
        HnHttpUtils.postRequest(HnUrl.RULE_SELL, param, "规则", new HnResponseHandler<RuleModel>(RuleModel.class) {
            @Override
            public void hnSuccess(String response) {
                if(mWebView==null)return;
                mWebView.loadDataWithBaseURL(null, model.getD().getContent(), "text/html", "utf-8", null);
            }

            @Override
            public void hnErr(int errCode, String msg) {
                HnToastUtils.showToastShort(msg);
                dismiss();
            }
        });
    }
}
