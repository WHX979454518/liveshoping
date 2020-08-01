package com.hotniao.live.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import com.hn.library.base.BaseActivity;
import com.hn.library.utils.HnToastUtils;
import com.hn.library.view.HnEditText;
import com.hotniao.live.R;
import com.hotniao.live.db.HnSearchHistoryHelper;
import com.hotniao.live.fragment.search.HnSearchUserFragment;
import com.hotniao.live.fragment.search.SearchGoodsFrag;
import com.hotniao.live.fragment.search.SearchShopFrag;
import com.hotniao.live.model.bean.SearchGoodsEvent;
import com.reslibrarytwo.CommTabView;

import org.greenrobot.eventbus.EventBus;


import butterknife.BindView;

/**
 * 首页-搜索（包含-商品/用户/店铺）
 * 作者：Mr.Xu
 * 时间：2017/12/7
 */
public class HnSearchGoodsAct extends BaseActivity {


    @BindView(R.id.mTvSearch)
    TextView mTvSearch;
    @BindView(R.id.mEdSearch)
    HnEditText mEdSearch;
    @BindView(R.id.mCommTab)
    CommTabView mCommTab;

    private String mSearchContent;


    public static void open(Context context, String key) {
        context.startActivity(new Intent(context, HnSearchGoodsAct.class).putExtra("key", key));
    }

    @Override
    public int getContentViewId() {
        return R.layout.act_search_goods;
    }

    @Override
    public void onCreateNew(Bundle savedInstanceState) {
        setShowTitleBar(false);
        mCommTab.initColor(R.color.main_color, R.color.comm_text_color_black_hs, R.color.main_color);
        String key = getIntent().getStringExtra("key");
        mCommTab.init(getResources().getStringArray(R.array.search_goods), 0, SearchGoodsFrag.newInstance(key),
                HnSearchUserFragment.newInstance(key), SearchShopFrag.newInstance(key));

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
                    mSearchContent = mEdSearch.getText().toString().trim();
                    //点击搜索到的数据加入本地历史数据库
                    HnSearchHistoryHelper.getInstance().insert(mSearchContent);


                    ((InputMethodManager) mEdSearch.getContext().getSystemService(Context.INPUT_METHOD_SERVICE))
                            .hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                                    InputMethodManager.HIDE_NOT_ALWAYS);

                    onFuzzySearch(mSearchContent, true);
                    return true;
                }
                return false;
            }
        });
        String key = getIntent().getStringExtra("key");
        mEdSearch.setText(key);
        if (!TextUtils.isEmpty(key)) {
            onFuzzySearch(key, false);
        }
    }

    /**
     * 模糊搜索
     */
    private void onFuzzySearch(String lastKey, boolean isClick) {

        if (TextUtils.isEmpty(lastKey)) {
            if (isClick) HnToastUtils.showCenterToast("请输入搜索内容");
            return;
        }

        EventBus.getDefault().post(new SearchGoodsEvent(lastKey));
    }
}
