package com.live.shoplib.ui;

import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.hn.library.base.baselist.BaseViewHolder;
import com.hn.library.base.baselist.CommRecyclerAdapter;
import com.hn.library.global.HnUrl;
import com.hn.library.http.BaseResponseModel;
import com.hn.library.http.HnHttpUtils;
import com.hn.library.http.HnResponseHandler;
import com.hn.library.utils.HnRefreshDirection;
import com.hn.library.utils.HnToastUtils;
import com.hn.library.view.CommDialog;
import com.live.shoplib.R;
import com.live.shoplib.ShopActFacade;
import com.live.shoplib.ShopRequest;
import com.live.shoplib.bean.GoodsSpecAttrModel;
import com.live.shoplib.ui.dialog.StoreGroupDialog;
import com.loopj.android.http.RequestParams;
import com.reslibrarytwo.CommListActivity;

import java.util.ArrayList;
import java.util.List;


/**
 * 商品属性-规格
 * 作者：Mr.Xu
 * 时间：2018/1/4
 */
@Route(path = "/shoplib/GoodsAttrAct")
public class GoodsAttrAct extends CommListActivity {

    private String type = "0";
    private String tag = "商品属性";
    private CommRecyclerAdapter mAdapter;
    private List<GoodsSpecAttrModel.DBean.ListBean> mData = new ArrayList<>();

    @Override
    public int getContentViewId() {
        return R.layout.act_store_goods_list_float;
    }

    @Override
    protected String setTitle() {
        type = getIntent().getStringExtra("tag");
        tag = type.equals("0") ? "商品属性" : "商品规格";
        return tag;
    }

    boolean show = false;

    @Override
    protected CommRecyclerAdapter setAdapter() {
        mSpring.setEnabled(false);
        ImageView mIvFloat = findViewById(R.id.mIvFloat);
        mIvFloat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!show) {
                    StoreGroupDialog.newInstance(GoodsAttrAct.this)
                            .setTitles(tag)
                            .setContent("")
                            .setContentHint("请输入名称")
                            .setClickListen(new StoreGroupDialog.TwoSelDialog() {
                                @Override
                                public void leftClick() {

                                }

                                @Override
                                public void rightClick(String content) {
                                    ShopRequest.addGoodsSpceAttr(
                                            getIntent().getStringExtra("id"),
                                            content,
                                            getIntent().getStringExtra("store_id"),
                                            type,
                                            new ShopRequest.OnRespondNothing() {
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
            protected void onBindView(final BaseViewHolder holder, final int position) {
                if (position == 0) {
                    final CheckBox mBoxSel = holder.getView(R.id.mBoxSel);
                    final TextView mTvDel = holder.getView(R.id.mTvDel);
                    final TextView mTvTitle = holder.getView(R.id.mTvTitle);
                    if (TextUtils.equals("批量管理", mTvDel.getText().toString())) {
                        mData.get(0).setCheck(false);
                        mTvTitle.setText(tag + "列表");
                        mBoxSel.setVisibility(View.GONE);
                    } else if (TextUtils.equals("取消", mTvDel.getText().toString())) {
                        mBoxSel.setVisibility(View.VISIBLE);
                        mTvTitle.setText("全选");
                    } else {
                        mData.get(0).setCheck(false);
                        mTvTitle.setText(tag + "列表");
                        mBoxSel.setVisibility(View.GONE);
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
                                mTvTitle.setText(tag + "列表");
                                show = false;
                                mData.get(0).setCheck(false);
                            } else {
                                //TODO 请求
                                CommDialog.newInstance(GoodsAttrAct.this)
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
                    holder.setTextViewText(R.id.mTvName, mData.get(position).getField());
                    holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                        @Override
                        public boolean onLongClick(View v) {
                            if (!show) {
                                CommDialog.newInstance(GoodsAttrAct.this)
                                        .setTitle("提示")
                                        .setContent("确定删除类型?")
                                        .setClickListen(new CommDialog.TwoSelDialog() {
                                            @Override
                                            public void leftClick() {

                                            }

                                            @Override
                                            public void rightClick() {
                                                delShopGoodsGroup(mData.get(position).getId());
                                            }
                                        })
                                        .show();
                            }
                            return false;
                        }
                    });
                    holder.itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (!show) {
                                //TODO
                                ShopActFacade.openEditAttrSpec(getIntent().getStringExtra("store_id"),
                                        mData.get(position).getId(),
                                        mData.get(position).getValue(),
                                        mData.get(position).getField(),
                                        type);
                            }
                        }
                    });
                }
            }

            @Override
            protected int getLayoutID(int position) {
                return position == 0 ? R.layout.item_store_group_head : R.layout.item_goods_type;
            }

            @Override
            public int getItemCount() {
                return mData.size();
            }
        };

        return mAdapter;
    }

    boolean isFrist = true;

    @Override
    protected void onResume() {
        super.onResume();
        if (isFrist) {
            isFrist = false;
        } else {
            getData(HnRefreshDirection.TOP, 1);
        }
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

    private void delShopGoodsGroup(String id) {
        RequestParams param = new RequestParams();
        param.put("store_id", getIntent().getStringExtra("store_id"));
        param.put("id", id);
        param.put("type", type);
        HnHttpUtils.postRequest(HnUrl.GOODS_ATTR_SPEC_DEL, param, tag + "删除", new HnResponseHandler<BaseResponseModel>(BaseResponseModel.class) {

            @Override
            public void hnSuccess(String response) {
                if (mRecycler != null) {
                    getData(HnRefreshDirection.TOP, 1);
                    HnToastUtils.showToastShort("删除成功");
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
        params.put("id", getIntent().getStringExtra("id"));
        params.put("type", type);
        return params;
    }

    @Override
    protected String setRequestUrl() {
        return HnUrl.GOODS_SPEC_ATTR;
    }

    @Override
    protected HnResponseHandler setResponseHandler(final HnRefreshDirection state) {
        return new HnResponseHandler<GoodsSpecAttrModel>(GoodsSpecAttrModel.class) {
            @Override
            public void hnSuccess(String response) {
                if (isFinishing()) return;
                refreshFinish();
                if (model.getD() == null) {
                    setEmpty("暂无属性", R.drawable.no_columns);
                    return;
                }
                if (HnRefreshDirection.TOP == state) {
                    mData.clear();
                }
                if (model.getD().getList().size() > 0) {
                    GoodsSpecAttrModel.DBean.ListBean itemsBean = new GoodsSpecAttrModel.DBean.ListBean();
                    mData.add(itemsBean);
                }

                mData.addAll(model.getD().getList());
                if (mAdapter != null)
                    mAdapter.notifyDataSetChanged();
                setEmpty("暂无属性", R.drawable.no_columns);
            }

            @Override
            public void hnErr(int errCode, String msg) {
                if (isFinishing()) return;
                refreshFinish();
                HnToastUtils.showToastShort(msg);
                setEmpty("暂无属性", R.drawable.no_columns);
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
