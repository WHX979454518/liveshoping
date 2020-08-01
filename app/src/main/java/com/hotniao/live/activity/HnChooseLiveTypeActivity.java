package com.hotniao.live.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.hn.library.base.BaseActivity;
import com.hn.library.base.baselist.BaseViewHolder;
import com.hn.library.base.baselist.CommRecyclerAdapter;
import com.hn.library.http.HnHttpUtils;
import com.hn.library.http.HnResponseHandler;
import com.hn.library.loadstate.HnLoadingLayout;
import com.hn.library.utils.HnToastUtils;
import com.hotniao.live.R;
import com.hn.library.global.HnUrl;
import com.hotniao.live.biz.home.HnHomeCate;
import com.hotniao.live.model.HnHomeLiveCateModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Copyright (C) 2016,深圳市红鸟网络科技股份有限公司 All rights reserved.
 * 类描述：主页面
 * 创建人：刘勇
 * 创建时间：2017/2/22 16:22
 * 修改人：刘勇
 * 修改时间：2017/2/22 16:34
 * 修改备注：
 * Version:  1.0.0
 */
@Route(path = "/live/chooseLiveTypeActivity")
public class HnChooseLiveTypeActivity extends BaseActivity {
    @Override
    public int getContentViewId() {
        return 0;
    }

    @Override
    public void onCreateNew(Bundle savedInstanceState) {

    }

    @Override
    public void getInitData() {

    }
//    @BindView(R.id.mTvNowType)
//    TextView mTvNowType;
//    @BindView(R.id.mRecycler)
//    RecyclerView mRecycler;
//    @BindView(R.id.mLoadingLayout)
//    HnLoadingLayout mLoadingLayout;
//    @BindView(R.id.mRG)
//    RadioGroup mRG;
//    @BindView(R.id.mRb1)
//    RadioButton mRbLive;
//    @BindView(R.id.mRb2)
//    RadioButton mRbGame;
//
//    private CommRecyclerAdapter mAdapter;
//    private List<Object> mData = new ArrayList<>();
//    private HnHomeLiveCateModel.DBean mDbean;
//    /**
//     * 分类id   分类名
//     */
//    private String mSelectItem = "-1", mCateName;
//    /**
//     * 是否游戏
//     */
//    private boolean isGame = false;
//    /**
//     * 是否选择
//     */
//    private boolean isChange = false;
//
//
//    /**
//     * 跳转到选择直播类型页面
//     *
//     * @param activity
//     * @param id       直播类型Id
//     * @param type     直播类型
//     * @param isGame   是否游戏
//     */
//    public static void luncher(Activity activity, String id, String type, boolean isGame) {
//        Intent intent = new Intent(activity, HnChooseLiveTypeActivity.class);
//        intent.putExtra("id", id);
//        intent.putExtra("type", type);
//        intent.putExtra("isGame", isGame);
//        activity.startActivityForResult(intent, HnBeforeLiveSettingActivity.Choose_Cate_Code);
//
//    }
//
//
//    @Override
//    public int getContentViewId() {
//        return R.layout.activity_choose_live_type;
//    }
//
//    @Override
//    public void onCreateNew(Bundle savedInstanceState) {
//
//        setTitle(R.string.choose_live_type);
//        setShowBack(true);
//        mLoadingLayout.setStatus(HnLoadingLayout.Loading);
//        mSelectItem = getIntent().getStringExtra("id");
//        isGame = getIntent().getBooleanExtra("isGame", false);
//        mCateName = getIntent().getStringExtra("type");
//
//        /**
//         * 如果游戏下架 先一步判断并改变数据
//         */
//
//        isGame = false;
//
//
//        if (TextUtils.isEmpty(mCateName))
//            mTvNowType.setText("无");
//        else
//            mTvNowType.setText(mCateName);
//
//        mRG.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(RadioGroup radioGroup, int i) {
//                switch (i) {
//                    case R.id.mRb1:
//                        changeData(false);
//                        break;
//                    case R.id.mRb2:
//                        changeData(true);
//                        break;
//                }
//            }
//        });
//        mBack.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                /**
//                 * 如果没有选择  并且不是游戏  则返回原来的数据
//                 * 考虑到游戏下架的情况
//                 */
//                if (!isChange && !isGame) {
//                    setResult(1, new Intent().putExtra("id", mSelectItem)
//                            .putExtra("type", mCateName).putExtra("isGame", isGame));
//                }
//                finish();
//            }
//        });
//    }
//
//    /**
//     * 根据选择类型  改变数据源
//     *
//     * @param isGame
//     */
//    private void changeData(boolean isGame) {
//        if (mDbean != null && mData != null) {
//            mData.clear();
//            this.isGame = isGame;
//            if (isGame) {
//                mData.addAll(mDbean.getGame_category());
//            } else {
//                mData.addAll(mDbean.getLive_category());
//            }
//            if (mAdapter != null) mAdapter.notifyDataSetChanged();
//        }
//
//    }
//
//    @Override
//    public void getInitData() {
//        mRecycler.setLayoutManager(new LinearLayoutManager(this));
//        mAdapter = new CommRecyclerAdapter() {
//            @Override
//            protected void onBindView(BaseViewHolder holder, final int position) {
//                if (isGame) {
//                    /**
//                     * 是游戏
//                     */
//                    final HnHomeLiveCateModel.DBean.GameCategoryBean gameCategoryBean = (HnHomeLiveCateModel.DBean.GameCategoryBean) mData.get(position);
//                    ((TextView) holder.getView(R.id.mTvType)).setText(gameCategoryBean.getGame_category_name());
//                    //需判断分类id和分类名 （ 有可能游戏分类id和娱乐分类id相同）
//                    if (mSelectItem.equals(gameCategoryBean.getGame_category_id())
//                            && gameCategoryBean.getGame_category_name().equals(mCateName))
//                        holder.getView(R.id.mIvChoose).setVisibility(View.VISIBLE);
//                    else holder.getView(R.id.mIvChoose).setVisibility(View.GONE);
//                    holder.getView(R.id.mRlItem).setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            isChange = true;
//                            isGame = true;
//                            setResult(1, new Intent().putExtra("id", gameCategoryBean.getGame_category_id())
//                                    .putExtra("type", gameCategoryBean.getGame_category_name())
//                                    .putExtra("code", gameCategoryBean.getGame_category_code()).putExtra("isGame", isGame));
//                            finish();
//                        }
//                    });
//                } else {
//                    /**
//                     * 不是游戏
//                     */
//                    final HnHomeLiveCateModel.DBean.LiveCategoryBean liveCategoryBean = (HnHomeLiveCateModel.DBean.LiveCategoryBean) mData.get(position);
//                    ((TextView) holder.getView(R.id.mTvType)).setText(liveCategoryBean.getAnchor_category_name());
//                    if (mSelectItem.equals(liveCategoryBean.getAnchor_category_id())
//                            && liveCategoryBean.getAnchor_category_name().equals(mCateName))
//                        holder.getView(R.id.mIvChoose).setVisibility(View.VISIBLE);
//                    else holder.getView(R.id.mIvChoose).setVisibility(View.GONE);
//                    holder.getView(R.id.mRlItem).setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            isChange = true;
//                            isGame = false;
//                            setResult(1, new Intent().putExtra("id", liveCategoryBean.getAnchor_category_id())
//                                    .putExtra("type", liveCategoryBean.getAnchor_category_name()).putExtra("isGame", isGame));
//                            finish();
//                        }
//                    });
//                }
//
//
//            }
//
//            @Override
//            protected int getLayoutID(int position) {
//                return R.layout.adapter_choose_live_type;
//            }
//
//            @Override
//            public int getItemCount() {
//                return mData.size();
//            }
//        };
//        mRecycler.setAdapter(mAdapter);
//        getCateData();
//    }
//
//    private void getCateData() {
//        HnHttpUtils.postRequest(HnUrl.Live_NAVBAR, null, HnUrl.Live_NAVBAR, new HnResponseHandler<HnHomeLiveCateModel>(HnHomeLiveCateModel.class) {
//            @Override
//            public void hnSuccess(String response) {
//                if (isFinishing()) return;
//                if (0 == model.getC() && model.getD() != null) {
//                    mDbean = model.getD();
//                    mData.clear();
////                    if (model.getD().getGame_category() == null || model.getD().getGame_category().size() < 1) {
//                    /**
//                     * 如果没有游戏分类  则只显示娱乐分类
//                     */
//                    isGame = false;
//                    mRG.setVisibility(View.GONE);
//                    mData.addAll(model.getD().getLive_category());
//                    mTvNowType.setGravity(Gravity.LEFT);
//                    mTvNowType.setVisibility(View.VISIBLE);
//                    if (mAdapter != null) mAdapter.notifyDataSetChanged();
////                    } else {
////                        /**
////                         * 有游戏分类  安装传进来的值判断
////                         */
////                        mRG.setVisibility(View.VISIBLE);
////                        if (isGame) {
////                            mData.addAll(model.getD().getGame_category());
////                            mRbGame.setChecked(true);
////                        } else {
////                            mData.addAll(model.getD().getLive_category());
////                            mRbLive.setChecked(true);
////                        }
////                        if (mAdapter != null) mAdapter.notifyDataSetChanged();
////                    }
//                } else {
//                    HnToastUtils.showToastShort(model.getM());
//                }
//
//                setEmpty("暂无分类", R.drawable.empty_com);
//            }
//
//            @Override
//            public void hnErr(int errCode, String msg) {
//                HnToastUtils.showToastShort(msg);
//                if (isFinishing()) return;
//
//                setEmpty("暂无分类", R.drawable.empty_com);
//            }
//        });
//    }
//
//
//    protected void setEmpty(String content, int res) {
//        if (mAdapter == null) return;
//        if (mAdapter.getItemCount() < 1) {
//            mLoadingLayout.setStatus(HnLoadingLayout.Empty);
//            mLoadingLayout.setEmptyText(content);
//        } else {
//            mLoadingLayout.setStatus(HnLoadingLayout.Success);
//        }
//    }
//
//    @Override
//    protected void onPause() {
//        super.onPause();
//
//    }
//
//
//    @Override
//    protected void onStop() {
//        super.onStop();
//
//    }
//
//
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//
//    }
}
