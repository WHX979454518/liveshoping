package com.live.shoplib.ui;

import android.app.TabActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.hn.library.base.BaseActivity;
import com.hn.library.utils.HnToastUtils;
import com.hn.library.view.HnEditText;
import com.live.shoplib.R;
import com.live.shoplib.bean.OrderRefreshEvent;
import com.live.shoplib.bean.PayFinishEvent;
import com.live.shoplib.ui.frag.OrderSortFragment;
import com.reslibrarytwo.CommTabView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;


/**
 * 商品订单
 * 作者：Mr.Xu
 * 时间：2017/12/18
 */
@Route(path = "/shoplib/ShopOrderAct")
public class ShopOrderAct extends BaseActivity {

    private TextView mTvSearch;
    private HnEditText mEdSearch;
    private CommTabView mCommTab;
    private RelativeLayout mRlTitle;
    private String mSearchContent;

    private boolean isSeller = false;
    private boolean isFirst = true;
    private int pos = 0;


    @Override
    public int getContentViewId() {
        return R.layout.act_shop_order;
    }

    @Override
    protected void onResume() {
        super.onResume();
        EventBus.getDefault().post(new OrderRefreshEvent(-1));
    }

    @Override
    public void onCreateNew(Bundle savedInstanceState) {
        EventBus.getDefault().register(this);
        isSeller = getIntent().getBooleanExtra("isSeller", false);
        pos = getIntent().getIntExtra("pos", 0);
        mTvSearch = findViewById(R.id.mTvSearch);
        mEdSearch = findViewById(R.id.mEdSearch);
        mCommTab = findViewById(R.id.mCommTab);
        mRlTitle = findViewById(R.id.mRlTitle);
        if (isSeller) {
            setShowTitleBar(false);
            mRlTitle.setVisibility(View.VISIBLE);
        } else {
            setShowTitleBar(true);
            setTitle("我的订单");
            setShowBack(true);
            mRlTitle.setVisibility(View.GONE);
        }
        mCommTab.initColor(R.color.main_color, R.color.comm_text_color_black_hs, R.color.main_color);
        mCommTab.init(getResources().getStringArray(R.array.order_goods), pos,
                OrderSortFragment.newInstance(0, isSeller),
                OrderSortFragment.newInstance(1, isSeller),
                OrderSortFragment.newInstance(2, isSeller),
                OrderSortFragment.newInstance(3, isSeller),
                OrderSortFragment.newInstance(4, isSeller));
        //0 全部订单 1 待付款 2 代发货 3 待收货 4 待评价
    }

    @Override
    public void getInitData() {
        //点击右两种状态
        mTvSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ("取消".equals(mTvSearch.getText())) {
                    finish();
                } else {
                    onFuzzySearch(mSearchContent, true);
                }
            }
        });

        //点击键盘搜索
        mEdSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    ((InputMethodManager) mEdSearch.getContext().getSystemService(Context.INPUT_METHOD_SERVICE))
                            .hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                                    InputMethodManager.HIDE_NOT_ALWAYS);
                    mSearchContent = mEdSearch.getText().toString().trim();
                    onFuzzySearch(mSearchContent, true);
                    return true;
                }
                return false;
            }
        });
        mEdSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (TextUtils.isEmpty(s.toString().trim()) && !isFirst) {
                    mSearchContent = mEdSearch.getText().toString().trim();
                    onFuzzySearch(mSearchContent, false);
                }

            }
        });
        String key = getIntent().getStringExtra("key");
        mEdSearch.setText(key);
        if (!TextUtils.isEmpty(key)) {
            onFuzzySearch(key, false);
        }
        isFirst = false;
    }

    @Subscribe
    public void onFinishEvent(PayFinishEvent event) {
        finish();
    }

    /**
     * 模糊搜索
     */
    private void onFuzzySearch(String lastKey, boolean isClick) {

        if (TextUtils.isEmpty(lastKey) && isClick) {
            HnToastUtils.showCenterToast("请输入搜索内容");
            return;
        }
        ((InputMethodManager) mEdSearch.getContext().getSystemService(Context.INPUT_METHOD_SERVICE))
                .hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);

        EventBus.getDefault().post(new OrderRefreshEvent(-2, lastKey));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
