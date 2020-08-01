package com.live.shoplib.ui.dialog;


import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.ColorRes;

import com.hn.library.utils.HnDimenUtil;
import com.hn.library.utils.HnToastUtils;
import com.live.shoplib.R;

public class StoreGroupDialog extends Dialog {

    public final static int ONE_STATE = 1;
    public final static int TWO_STATE = 2;
    public Context context;
    public TextView mTvLeft, mTvRight;
    public EditText mTvName;
    public TextView hint;
    private int State = 1;
    private StoreGroupDialog.TwoSelDialog mTwoSelDialog;
    private StoreGroupDialog.OneSelDialog mOneSelDialog;
    private static StoreGroupDialog dialog;

    public interface TwoSelDialog {
        void leftClick();

        void rightClick(String str);
    }

    public interface OneSelDialog {
        void sureClick();
    }

    public static StoreGroupDialog newInstance(Context context) {
        dialog = new StoreGroupDialog(context);
        return dialog;
    }

    public StoreGroupDialog(Context context) {
        super(context, com.hn.library.R.style.PXDialog);
        this.context = context;
        this.State = TWO_STATE;
        init();
    }


    public StoreGroupDialog showDialog() {
        if (dialog != null) {
            dialog.show();
            return dialog;
        }
        return dialog;
    }


    private void init() {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.dialog_store_group, null);
        setContentView(view);
        mTvName = (EditText) findViewById(R.id.mTvName);
        hint = findViewById(R.id.hint);
        mTvLeft = (TextView) findViewById(R.id.mTvLeft);
        mTvRight = (TextView) findViewById(R.id.mTvRight);


        Window dialogWindow = this.getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.width = HnDimenUtil.dp2px(context, 300); // 宽度
        dialogWindow.setAttributes(lp);
    }

    //设置标题
    public StoreGroupDialog setCanceledOnOutside(boolean isCancle) {
        if (dialog != null) {
            this.setCanceledOnTouchOutside(isCancle);
            return dialog;
        }
        return null;
    }


    //设置内容
    public StoreGroupDialog setTitles(String title) {
        if (hint != null) {
            hint.setText(title);
            return dialog;
        }
        return null;
    }

    //设置内容
    public StoreGroupDialog setContent(String content) {
        if (mTvName != null) {
            mTvName.setText(content);
            mTvName.setSelection(mTvName.getText().length());
            return dialog;
        }
        return null;
    }

    //设置内容
    public StoreGroupDialog setContentHint(String hint) {
        if (mTvName != null) {
            mTvName.setHint(hint);
            return dialog;
        }
        return null;
    }

    //设置左边文本 默认-取消
    public StoreGroupDialog setLeftText(String leftText) {
        if (mTvLeft != null) {
            mTvLeft.setText(leftText);
            return dialog;
        }
        return null;
    }

    //设置右边文本 默认-确认
    public StoreGroupDialog setRightText(String rightText) {
        if (mTvRight != null) {
            mTvRight.setText(rightText);
            return dialog;
        }
        return null;
    }

    //设置右边文本 默认-确认
    public StoreGroupDialog setRightText(String rightText, @ColorRes int color) {
        if (mTvRight != null) {
            mTvRight.setText(rightText);
            mTvRight.setTextColor(context.getResources().getColor(color));
            return dialog;
        }
        return null;
    }

    public StoreGroupDialog setClickListen(final StoreGroupDialog.OneSelDialog mOneSelDialog) {
        this.mOneSelDialog = mOneSelDialog;
        this.State = ONE_STATE;
        mTvLeft.setVisibility(View.GONE);
        //确定
        mTvRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOneSelDialog != null)
                    mOneSelDialog.sureClick();
                if (StoreGroupDialog.this.isShowing()) {
                    StoreGroupDialog.this.dismiss();
                    InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.showSoftInput(mTvName, 0);
                }
            }
        });
        return dialog;
    }

    public StoreGroupDialog setClickListen(final StoreGroupDialog.TwoSelDialog mTwoSelDialog) {
        this.mTwoSelDialog = mTwoSelDialog;
        this.State = TWO_STATE;
        //取消
        mTvLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mTwoSelDialog != null)
                    mTwoSelDialog.leftClick();
                if (StoreGroupDialog.this.isShowing()) {
                    StoreGroupDialog.this.dismiss();
                    InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.showSoftInput(mTvName, 0);
                }
            }
        });
        //确定
        mTvRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mTwoSelDialog != null) {
                    if (TextUtils.isEmpty(mTvName.getText().toString())) {
                        HnToastUtils.showToastShort("请输入内容");
                        return;
                    }
                    mTwoSelDialog.rightClick(mTvName.getText().toString());
                }
                if (StoreGroupDialog.this.isShowing()) {
                    StoreGroupDialog.this.dismiss();
                    InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.showSoftInput(mTvName, 0);
                }
            }
        });
        return dialog;
    }

    public void showKeyboard() {
        //其中editText为dialog中的输入框的 EditText
        if (mTvName != null) {
            //调用系统输入法
            InputMethodManager inputManager = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.showSoftInput(mTvName, 0);
        }
    }

    @Override
    public void show() {
        super.show();
    }


    //    @Override
//    public void show() {
//        super.show();
//        showKeyboard();
//    }
}
