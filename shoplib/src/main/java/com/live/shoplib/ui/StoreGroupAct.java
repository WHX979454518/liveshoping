package com.live.shoplib.ui;

import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.hn.library.base.baselist.BaseViewHolder;
import com.hn.library.base.baselist.CommRecyclerAdapter;
import com.hn.library.global.HnUrl;
import com.hn.library.http.HnHttpUtils;
import com.hn.library.http.HnResponseHandler;
import com.hn.library.picker.address_picker.StoreGroupModel;
import com.hn.library.utils.HnRefreshDirection;
import com.hn.library.utils.HnToastUtils;
import com.hn.library.view.CommDialog;
import com.live.shoplib.R;
import com.live.shoplib.ShopRequest;
import com.live.shoplib.bean.GropDelModel;
import com.live.shoplib.ui.dialog.StoreGroupDialog;
import com.loopj.android.http.RequestParams;
import com.reslibrarytwo.CommListActivity;

import java.util.ArrayList;
import java.util.List;


/**
 * 店铺栏目
 * 作者：Mr.Xu
 * 时间：2018/1/4
 */
@Route(path = "/shoplib/StoreGroupAct")
public class StoreGroupAct extends CommListActivity {

    private CommRecyclerAdapter mAdapter;
    private List<StoreGroupModel.DBean.ItemsBean> mData = new ArrayList<>();

    @Override
    public int getContentViewId() {
        return R.layout.act_store_goods_list_float;
    }

    @Override
    protected String setTitle() {
//        mSubtitle.setVisibility(View.GONE);
//        mSubtitle.setText("添加");
//        mSubtitle.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });
        return "店铺栏目";
    }

    boolean show = false;

    @Override
    protected CommRecyclerAdapter setAdapter() {
        mSpring.setEnabled(false);
        ImageView mIvFloat = findViewById(R.id.mIvFloat);
        mIvFloat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!show) {
                    StoreGroupDialog.newInstance(StoreGroupAct.this)
                            .setContent("")
                            .setClickListen(new StoreGroupDialog.TwoSelDialog() {
                                @Override
                                public void leftClick() {

                                }

                                @Override
                                public void rightClick(String content) {
                                    ShopRequest.addStoreGroup(content, new ShopRequest.OnRespondNothing() {
                                        @Override
                                        public void finishs() {
                                            getData(HnRefreshDirection.TOP, page = 1);
                                        }
                                    });
                                }
                            })
                            .show();
                }
            }
        });
        mAdapter = new CommRecyclerAdapter() {
            @Override
            protected void onBindView(BaseViewHolder holder, final int position) {
                if (position == 0) {
                    final CheckBox mBoxSel = holder.getView(R.id.mBoxSel);
                    final TextView mTvDel = holder.getView(R.id.mTvDel);
                    final TextView mTvTitle = holder.getView(R.id.mTvTitle);
                    if (TextUtils.equals("批量管理", mTvDel.getText().toString())) {
                        mData.get(0).setCheck(false);
                        mTvTitle.setText("共 " + (mData.size() - 1) + " 个栏目");
                        mBoxSel.setVisibility(View.GONE);
                    } else if (TextUtils.equals("取消", mTvDel.getText().toString())) {
                        mBoxSel.setVisibility(View.VISIBLE);
                        mTvTitle.setText("全选");
                    }
                    mTvDel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (TextUtils.equals("批量管理", mTvDel.getText().toString())) {
                                mTvDel.setText("取消");
                                mTvTitle.setText("全选");
                                show = true;
                                mData.get(0).setCheck(false);
                            } else if (TextUtils.equals("取消", mTvDel.getText().toString())) {
                                mTvDel.setText("批量管理");
                                mTvTitle.setText("共 " + (mData.size() - 1) + " 个栏目");
                                show = false;
                                mData.get(0).setCheck(false);
                            } else {
                                //TODO 请求
                                CommDialog.newInstance(StoreGroupAct.this)
                                        .setTitle("提示")
                                        .setContent("确定删除类型?")
                                        .setClickListen(new CommDialog.TwoSelDialog() {
                                            @Override
                                            public void leftClick() {

                                            }

                                            @Override
                                            public void rightClick() {
                                                calcuate();
                                            }
                                        })
                                        .show();
                            }
                            mAdapter.notifyDataSetChanged();
                        }
                    });
                    if (checkSelOnce()) {
                        mTvDel.setText("删除");
                        mTvDel.setTextColor(getResources().getColor(R.color.comm_text_color_red));
                    } else {
                        mTvDel.setText(show ? "取消" : "批量管理");
                        mTvDel.setTextColor(getResources().getColor(R.color.main_color));
                    }
                    mBoxSel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mData.get(0).setCheck(mBoxSel.isChecked());
                            setSelAll(mBoxSel.isChecked());
                            notifyDataSetChanged();
                        }
                    });
                    mBoxSel.setChecked(mData.get(0).isCheck());
                } else {
                    final CheckBox mBoxSel = holder.getView(R.id.mBoxSel);
                    mBoxSel.setVisibility(show ? View.VISIBLE : View.GONE);
                    mBoxSel.setChecked(mData.get(position).isCheck());
                    mBoxSel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mData.get(position).setCheck(mBoxSel.isChecked());
                            mData.get(0).setCheck(checkSel());
                            notifyDataSetChanged();
                        }
                    });
                    holder.setTextViewText(R.id.mTvName, mData.get(position).getName());
                    holder.setTextViewText(R.id.mTvNum, mData.get(position).getNum() + "件商品");
                    holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                        @Override
                        public boolean onLongClick(View v) {
                            if (!show) {
                                StoreGroupDialog.newInstance(StoreGroupAct.this)
                                        .setContent(mData.get(position).getName())
                                        .setClickListen(new StoreGroupDialog.TwoSelDialog() {
                                            @Override
                                            public void leftClick() {

                                            }

                                            @Override
                                            public void rightClick(String content) {
                                                ShopRequest.editStoreGroup(mData.get(position).getId(), content, new ShopRequest.OnRespondNothing() {
                                                    @Override
                                                    public void finishs() {
                                                        getData(HnRefreshDirection.TOP, page = 1);
                                                    }
                                                });
                                            }
                                        }).show();
                            }
                            return false;
                        }
                    });
                    holder.itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (!show) {
                                StoreGoodsListAct.Companion.launchAct(
                                        StoreGroupAct.this,
                                        mData.get(position).getId(),
                                        getIntent().getStringExtra("store_id"));
                            }
                        }
                    });
                }
            }

            @Override
            protected int getLayoutID(int position) {
                return position == 0 ? R.layout.item_store_group_head : R.layout.item_store_group;
            }

            @Override
            public int getItemCount() {
                return mData.size();
            }
        };

        return mAdapter;
    }

    @Override
    protected void onResume() {
        super.onResume();
        getData(HnRefreshDirection.TOP, 1);
    }

    //计算请求拼接
    private synchronized void calcuate() {
        String reqMsg = "";
        for (int i = 1; i < mData.size(); i++) {
            if (mData.get(i).isCheck()) {
                reqMsg = reqMsg + mData.get(i).getId() + ",";
            }
        }
        reqMsg = reqMsg.substring(0, reqMsg.length() - 1);
        if (TextUtils.isEmpty(reqMsg)) return;
        delShopGoodsGroup(reqMsg);
    }

    private void delShopGoodsGroup(String column_id) {
        RequestParams param = new RequestParams();
        param.put("column_id", column_id);
        HnHttpUtils.postRequest(HnUrl.GOODS_GROUP_DEL, param, "栏目删除", new HnResponseHandler<GropDelModel>(GropDelModel.class) {
            @Override
            public void hnSuccess(String response) {
                if (mRecycler != null) {
                    getData(HnRefreshDirection.TOP, 1);
                    CommDialog.newInstance(StoreGroupAct.this)
                            .setTitle("提示")
                            .setContent("删除成功：" + model.getD().getSuccess_total() + "，删除失败：" + model.getD().getFail_total())
                            .setClickListen(new CommDialog.TwoSelDialog() {
                                @Override
                                public void leftClick() {

                                }

                                @Override
                                public void rightClick() {

                                }
                            })
                            .show();
                }
            }

            @Override
            public void hnErr(int errCode, String msg) {
                HnToastUtils.showToastShort(msg);
            }
        });
    }


    @Override
    protected RequestParams setRequestParam() {
        RequestParams params = new RequestParams();
        return params;
    }

    @Override
    protected String setRequestUrl() {
        return HnUrl.STORE_GROUP_LIST;
    }

    @Override
    protected HnResponseHandler setResponseHandler(final HnRefreshDirection state) {
        return new HnResponseHandler<StoreGroupModel>(StoreGroupModel.class) {
            @Override
            public void hnSuccess(String response) {
                if (isFinishing()) return;
                refreshFinish();
                if (model.getD() == null) {
                    setEmpty("暂无栏目", R.drawable.no_columns);
                    return;
                }
                if (HnRefreshDirection.TOP == state) {
                    mData.clear();
                }
                if (model.getD().getItems().size() > 0) {
                    StoreGroupModel.DBean.ItemsBean itemsBean = new StoreGroupModel.DBean.ItemsBean();
                    mData.add(itemsBean);
                }

                mData.addAll(model.getD().getItems());
                if (mAdapter != null)
                    mAdapter.notifyDataSetChanged();
                setEmpty("暂无栏目", R.drawable.no_columns);
            }

            @Override
            public void hnErr(int errCode, String msg) {
                if (isFinishing()) return;
                refreshFinish();
                HnToastUtils.showToastShort(msg);
                setEmpty("暂无栏目", R.drawable.no_columns);
            }
        };
    }

    public void setSelAll(boolean ck) {
        for (int i = 1; i < mData.size(); i++) {
            mData.get(i).setCheck(ck);
        }
    }

    public boolean checkSel() {
        for (int i = 1; i < mData.size(); i++) {
            if (!mData.get(i).isCheck()) {
                return false;
            }
        }
        return true;
    }

    public boolean checkSelOnce() {
        for (int i = 1; i < mData.size(); i++) {
            if (mData.get(i).isCheck()) {
                return true;
            }
        }
        return false;
    }
}
