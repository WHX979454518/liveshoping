package com.hotniao.live.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.hn.library.base.BaseActivity;
import com.hn.library.base.BaseRequestStateListener;
import com.hn.library.global.NetConstant;
import com.hn.library.http.HnHttpUtils;
import com.hn.library.http.HnResponseHandler;
import com.hn.library.http.RequestParam;
import com.hn.library.refresh.PtrClassicFrameLayout;
import com.hn.library.refresh.PtrDefaultHandler2;
import com.hn.library.refresh.PtrFrameLayout;
import com.hn.library.utils.HnLogUtils;
import com.hn.library.utils.HnPrefUtils;
import com.hn.library.utils.HnToastUtils;
import com.hn.library.utils.HnUtils;
import com.hn.library.view.CommDialog;
import com.hn.library.view.HnEditText;
import com.hotniao.live.R;
import com.hotniao.live.adapter.HnSearchHistoryAdapter;
import com.hotniao.live.adapter.HnSearchMatchAdapter;
import com.hotniao.live.biz.user.follow.HnFollowBiz;
import com.hn.library.global.HnUrl;
import com.hotniao.live.db.HnSearchHistoryHelper;
import com.hotniao.live.model.HnHomeSearchModel;
import com.hotniao.livelibrary.model.event.HnFollowEvent;
import com.jakewharton.rxbinding2.widget.RxTextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;


/**
 * Copyright (C) 2017,深圳市红鸟网络科技股份有限公司 All rights reserved.
 * 项目名称：红鸟电商
 * 类描述：首页搜索
 * 创建人：刘龙龙
 * 创建时间：2017/3/6 16:16
 * 修改人：
 * 修改时间：2017/3/6 16:16
 * 修改备注：
 * Version:  1.0.0
 */
@Route(path = "/app/HnHomeSearchActivity")
public class HnHomeSearchActivity extends BaseActivity implements BaseRequestStateListener {

    @BindView(R.id.search_tv_cancel)
    TextView mSearchCancel;
    @BindView(R.id.search_tv)
    TextView mSearchTv;
    @BindView(R.id.search_et)
    HnEditText mSearchEt;
    @BindView(R.id.tv_delete)
    TextView mTvDelete;
    @BindView(R.id.recycler_search)
    RecyclerView mRecyclerSearch;
    @BindView(R.id.ptr_refresh)
    PtrClassicFrameLayout mPtrRefresh;
    @BindView(R.id.rl_delete_history)
    RelativeLayout mRlDelete;

    private String mSearchContent;

    private HnSearchHistoryAdapter mHistoryAdapter;
    private HnSearchMatchAdapter mMatchAdapter;

    private int mPage = 1;

    private HnFollowBiz mHnFollowBiz;
    private String mOwnUid;

    @Override
    public int getContentViewId() {
        return R.layout.activity_homesearch;
    }

    @Override
    public void onCreateNew(Bundle savedInstanceState) {

        setShowTitleBar(false);
        EventBus.getDefault().register(this);
        HnSearchHistoryHelper.getInstance();

        mRecyclerSearch.setLayoutManager(new LinearLayoutManager(this));

        mMatchAdapter = new HnSearchMatchAdapter();
        mHistoryAdapter = new HnSearchHistoryAdapter();

        mHnFollowBiz = new HnFollowBiz(this);
        mHnFollowBiz.setRegisterListener(this);

        mOwnUid = HnPrefUtils.getString(NetConstant.User.UID, "");

        setListener();
    }



    /**
     * 设置搜索监听器
     */
    private void setListener() {


        mPtrRefresh.setPtrHandler(new PtrDefaultHandler2() {
            @Override
            public void onLoadMoreBegin(PtrFrameLayout ptrFrameLayout) {
                onFuzzySearch(mSearchContent);
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout ptrFrameLayout) {

            }
        });

//
//        RxTextView.textChanges(mSearchEt)
//                .debounce(200, TimeUnit.MILLISECONDS)
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Consumer<CharSequence>() {
//                    @Override
//                    public void accept(CharSequence charSequence) throws Exception {
//                        mSearchContent = charSequence.toString();
//                        HnLogUtils.d("mSearchContent= " + mSearchContent);
//
//                        boolean isEmpty = TextUtils.isEmpty(mSearchContent);
//                        mRlDelete.setVisibility(isEmpty ? View.VISIBLE : View.GONE);
//                        mPtrRefresh.setMode(isEmpty ? PtrFrameLayout.Mode.NONE : PtrFrameLayout.Mode.LOAD_MORE);
//                        if (isEmpty) {
//                            getInitData();
//                            return;
//                        }
//
//                        mRecyclerSearch.setAdapter(mMatchAdapter);
//                        mPage = 1;
//                        onFuzzySearch(mSearchContent);
//                    }
//                });


        // 搜索历史记录条目点击
        mHistoryAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                String item = (String) adapter.getItem(position);
                mSearchEt.setText(item);
                //点击搜索到的数据加入本地历史数据库
                HnSearchHistoryHelper.getInstance().insert(item);

                ((InputMethodManager) mSearchEt.getContext().getSystemService(Context.INPUT_METHOD_SERVICE))
                        .hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                                InputMethodManager.HIDE_NOT_ALWAYS);
                mSearchContent = mSearchEt.getText().toString().trim();
                if(TextUtils.isEmpty(mSearchContent)){
                    HnToastUtils.showToastShort("请输入搜索内容");
                    return;
                }
                onFuzzySearch(mSearchContent);
                HnSearchHistoryHelper.getInstance().insert(mSearchContent);
                HnSearchGoodsAct.open(HnHomeSearchActivity.this, mSearchContent);

            }
        });


        //搜索结果条目点击
        mMatchAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                HnHomeSearchModel.DBean.ItemsBean item = (HnHomeSearchModel.DBean.ItemsBean) adapter.getItem(position);

                //点击搜索到的数据加入本地历史数据库
                HnSearchHistoryHelper.getInstance().insert(item.getContent());
                HnSearchGoodsAct.open(HnHomeSearchActivity.this, item.getContent());

            }
        });

        mSearchEt.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                                                @Override
                                                public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                                                    if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                                                        ((InputMethodManager) mSearchEt.getContext().getSystemService(Context.INPUT_METHOD_SERVICE))
                                                                .hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                                                                        InputMethodManager.HIDE_NOT_ALWAYS);
                                                        mSearchContent = mSearchEt.getText().toString().trim();
                                                        if(TextUtils.isEmpty(mSearchContent)){
                                                            HnToastUtils.showToastShort("请输入搜索内容");
                                                            return false;
                                                        }
                                                        onFuzzySearch(mSearchContent);
                                                        HnSearchHistoryHelper.getInstance().insert(mSearchContent);
                                                        HnSearchGoodsAct.open(HnHomeSearchActivity.this, mSearchContent);

                                                        return true;
                                                    }
                                                    return false;
                                                }
                                            }

        );


    }


    @Override
    public void getInitData() {

        mRecyclerSearch.setAdapter(mHistoryAdapter);

    }

    @Override
    protected void onResume() {
        super.onResume();
        ArrayList<String> historyLists = HnSearchHistoryHelper.getInstance().getHistoryLists();

        mHistoryAdapter.setNewData(historyLists);

    }

    @OnClick({R.id.search_tv_cancel, R.id.search_tv, R.id.tv_delete})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.search_tv_cancel:
                finish();
                break;
            case R.id.search_tv:
                HnUtils.hideSoftInputFrom(mSearchEt, this);
                mSearchContent = mSearchEt.getText().toString().trim();
                onFuzzySearch(mSearchContent);
                HnSearchHistoryHelper.getInstance().insert(mSearchContent);
                break;
            case R.id.tv_delete:
                CommDialog.newInstance(HnHomeSearchActivity.this).setClickListen(new CommDialog.TwoSelDialog() {
                    @Override
                    public void leftClick() {

                    }

                    @Override
                    public void rightClick() {
                        HnSearchHistoryHelper.getInstance().clearDataBase();
                        mHistoryAdapter.setNewData(null);
                    }
                }).setTitle(getString(R.string.search_history)).setContent(getString(R.string.sure_clear_search_history_record)).show();


                break;
        }
    }


    /**
     * 模糊搜索
     *
     * @param lastKey
     */
    private void onFuzzySearch(String lastKey) {
        if (TextUtils.isEmpty(lastKey)) return;
        RequestParam param = new RequestParam();
        if(TextUtils.isEmpty(lastKey)){
            HnToastUtils.showToastShort("请输入搜索内容");
            return;
        }
        param.put("kw", lastKey);
        param.put("page", mPage + "");
        param.put("pagesize", 20 + "");
        HnHttpUtils.getRequest(HnUrl.SEARCH_GET_RECOMMEND, param, TAG, new HnResponseHandler<HnHomeSearchModel>(this, HnHomeSearchModel.class) {
            @Override
            public void hnSuccess(String response) {
                if (isFinishing()) return;
                try {
                    closeRefresh(mPtrRefresh);
                    List<HnHomeSearchModel.DBean.ItemsBean> items = model.getD().getItems();
                    mMatchAdapter.setKeyword(mSearchContent);
                    if (mPage == 1) {
                        mMatchAdapter.setNewData(items);
                    } else {
                        mMatchAdapter.addData(items);
                    }
                    mPage++;

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void hnErr(int errCode, String msg) {
                closeRefresh(mPtrRefresh);
            }
        });

    }


    @Override
    public void requesting() {

    }

    @Override
    public void requestSuccess(String type, String response, Object obj) {

        HnLogUtils.d("type=" + type + " response=" + response);
        if ("cancelFollow".equals(type)) {//取消关注
            int pos = (int) obj;
//            mMatchAdapter.getItem(pos).is_follow = "0";
            mMatchAdapter.notifyItemChanged(pos);
        } else if ("addFollow".equals(type)) {//添加关注
            int pos = (int) obj;
//            mMatchAdapter.getItem(pos).is_follow = "1";
            mMatchAdapter.notifyItemChanged(pos);
        }

    }

    @Override
    public void requestFail(String type, int code, String msg) {
    }


    @Subscribe
    public void onEventBusCallBack(HnFollowEvent event) {
        if (event != null) {
            String uid = event.getUid();
            boolean isFollow = event.isFollow();
//            if (!TextUtils.isEmpty(uid) && mMatchAdapter != null) {
//                List< HnHomeSearchModel.DBean.ItemsBean> items = mMatchAdapter.getData();
//                for (int i = 0; i < items.size(); i++) {
//                    if (uid.equals(items.get(i).id)) {
//                        if (isFollow) {
//                            items.get(i).is_follow = "1";
//                        } else {
//                            items.get(i).is_follow = "0";
//                        }
//                        mMatchAdapter.setNewData(items);
//                        break;
//                    }
//                }
//            }
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}