package com.live.shoplib.ui.dialog;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hn.library.base.baselist.BaseViewHolder;
import com.hn.library.base.baselist.CommRecyclerAdapter;
import com.hn.library.global.HnUrl;
import com.hn.library.http.HnHttpUtils;
import com.hn.library.http.HnResponseHandler;
import com.hn.library.utils.HnToastUtils;
import com.hn.library.view.FrescoImageView;
import com.live.shoplib.R;
import com.live.shoplib.ShopActFacade;
import com.live.shoplib.bean.AddCarModel;
import com.live.shoplib.bean.GoodsAttrBean;
import com.live.shoplib.bean.GoodsCarNumModel;
import com.live.shoplib.bean.GoodsCommitModel;
import com.live.shoplib.bean.GoodsDetailsListModel;
import com.live.shoplib.bean.ShoppingCarRefreshEvent;
import com.live.shoplib.other.GoodsDetailsHelper;
import com.loopj.android.http.RequestParams;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;


/**
 * 主播端-用户端-商品；列表
 * 作者：Mr.X
 * 时间：10:48
 */
@SuppressLint("ValidFragment")
public class ShopGoodsDialog extends DialogFragment {

    private static ShopGoodsDialog dialog;
    private Activity mAct;
    private RecyclerView mRecycler;
    private ImageView mIvAddGoodsNew;
    private int mPage = 1;
    private List<GoodsAttrBean> mData = new ArrayList<>();
    private CommRecyclerAdapter mAdapter;
    private String roomId;
    private LinearLayout mLLEmpty;


    @SuppressLint("ValidFragment")
    public ShopGoodsDialog(String roomId) {
        this.roomId = roomId;
    }

    public static ShopGoodsDialog newInstance(String roomId) {
        dialog = new ShopGoodsDialog(roomId);
        return dialog;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAct = getActivity();
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View view = View.inflate(mAct, R.layout.dialog_shop_goods, null);
        mRecycler = view.findViewById(R.id.mRecycler);
        mIvAddGoodsNew = view.findViewById(R.id.mIvAddGoodsNew);
        mLLEmpty = view.findViewById(R.id.mLLEmpty);

        view.findViewById(R.id.mIvAddCar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShopActFacade.openGoodsCar();
                dismiss();
            }
        });
        view.findViewById(R.id.mRlEmpty).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        initRecycler();
        initData();
        Dialog dialog = new Dialog(mAct, R.style.PXDialog);
        dialog.setContentView(view);
        Window window = dialog.getWindow();
        window.setGravity(Gravity.BOTTOM);
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = (mAct.getWindowManager().getDefaultDisplay().getWidth());
        params.height = (mAct.getWindowManager().getDefaultDisplay().getHeight());
        window.setAttributes(params);
        return dialog;
    }


    private void initRecycler() {
        mRecycler.setLayoutManager(new LinearLayoutManager(mAct, LinearLayoutManager.HORIZONTAL, false));
        mRecycler.setHasFixedSize(true);
        mRecycler.setAdapter(mAdapter = new CommRecyclerAdapter() {

            @TargetApi(Build.VERSION_CODES.M)
            @Override
            protected void onBindView(BaseViewHolder holder, final int position) {
                RecyclerView recyclerView = holder.getView(R.id.mRecycler);
                final TextView mTvCar = holder.getView(R.id.mTvCar);
                final TextView mTvBug = holder.getView(R.id.mTvBug);
                final TextView mTvPrice = holder.getView(R.id.mTvPrice);
                mTvPrice.setText(mData.get(position).getGoods_price());

                GoodsDetailsHelper helper = new GoodsDetailsHelper(true, recyclerView, mData.get(position), new GoodsDetailsHelper.OnClickStateChange() {
                    @Override
                    public void onItemClick(String id, String spec_text, String num, String price) {
                        mData.get(position).setSku_id(id);
                        mData.get(position).setSpec_text(spec_text);
                        if(TextUtils.isEmpty(price)){
                            mTvPrice.setText(mData.get(position).getGoods_price());
                        }else {
                            mTvPrice.setText(price);
                        }
                        if (TextUtils.isEmpty(num)) {
                            mData.get(position).setNum(0);
                        } else {
                            try {
                                mData.get(position).setNum(Integer.valueOf(num));
                            } catch (NumberFormatException e) {
                                e.printStackTrace();
                                mData.get(position).setNum(0);
                            }
                        }
                        if (TextUtils.isEmpty(id)) {
                            mTvCar.setEnabled(false);
                            mTvBug.setEnabled(false);
                        } else {
                            mTvCar.setEnabled(true);
                            mTvBug.setEnabled(true);
                        }

                    }

                    @Override
                    public void onItemClickNone() {
                        mTvCar.setEnabled(false);
                        mTvBug.setEnabled(false);
                        mTvPrice.setText(mData.get(position).getGoods_price());
                    }
                });

                ((FrescoImageView) holder.getView(R.id.mIvIco)).setImageURI(Uri.parse(HnUrl.setImageUrl(mData.get(position).getGoods_img())));
                holder.setTextViewText(R.id.mTvGoodsName, mData.get(position).getGoods_name());

                mTvCar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        requestAddCar(mData.get(position).getNum() + "", mData.get(position).getSku_id(), mData.get(position).getSpec_text(), "normal");
                    }
                });
                mTvBug.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        requestAddCar(mData.get(position).getNum() + "", mData.get(position).getSku_id(), mData.get(position).getSpec_text(), "fast");
                    }
                });

                holder.getView(R.id.mLLGoods).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ShopActFacade.openGoodsDetails(mData.get(position).getGoods_id());
                    }
                });
            }

            @Override
            protected int getLayoutID(int position) {
                return R.layout.item_goods_live;
            }

            @Override
            public int getItemCount() {
                return mData.size();
            }
        });

    }

    private void initData() {
        mPage = 1;
        mData.clear();
        requestData(roomId);
        requestGetCarNum();
        mAdapter.notifyDataSetChanged();
    }

    /**
     * 用户商品
     */
    private void requestData(String roomId) {
        RequestParams param = new RequestParams();
        param.put("roomId", roomId);
        param.put("page", mPage);
        param.put("pageSize", "10");
        HnHttpUtils.postRequest(HnUrl.AUD_LIVE_GOODS, param, "用户商品", new HnResponseHandler<GoodsDetailsListModel>(GoodsDetailsListModel.class) {
            @Override
            public void hnSuccess(String response) {
                if (mAct == null) return;
                if (model.getD().getItems() == null) {
                    return;
                }
                mData.addAll(model.getD().getItems());
                if (mAdapter != null)
                    mAdapter.notifyDataSetChanged();
                if (mData.size() == 0) {
                    mLLEmpty.setVisibility(View.VISIBLE);
                    mRecycler.setVisibility(View.GONE);
                } else {
                    mLLEmpty.setVisibility(View.GONE);
                    mRecycler.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void hnErr(int errCode, String msg) {
                HnToastUtils.showToastShort(msg);
            }
        });
    }

    /**
     * 添加购物车
     */
    private void requestAddCar(String num, String sku_id, String spec, final String type) {
        RequestParams param = new RequestParams();
        param.put("num", num);
        param.put("sku_id", sku_id);
        param.put("type", type);
        param.put("spec", spec);
        HnHttpUtils.postRequest(HnUrl.ADD_CAR_ORD, param, "添加购物车", new HnResponseHandler<AddCarModel>(AddCarModel.class) {
            @Override
            public void hnSuccess(String response) {
                if (mAct == null) return;
                if (type.equals("normal")) {
                    HnToastUtils.showToastShort("添加成功");
                    EventBus.getDefault().post(new ShoppingCarRefreshEvent());
                    requestGetCarNum();
                } else {
                    dismiss();
                    requestSubCar(model.getD().getCart_id());
                }
            }


            @Override
            public void hnErr(int errCode, String msg) {
                HnToastUtils.showToastShort(msg);
            }
        });
    }


    //修改购物车
    private void requestSubCar(String cartList) {
        RequestParams param = new RequestParams();
        param.put("cartList", cartList);
        HnHttpUtils.postRequest(HnUrl.SUB_CAR, param, "提交购物车", new HnResponseHandler<GoodsCommitModel>(GoodsCommitModel.class) {
            @Override
            public void hnSuccess(String response) {
                if (mAct == null) return;
                ShopActFacade.openGoodsCommit(model);
            }

            @Override
            public void hnErr(int errCode, String msg) {
                HnToastUtils.showToastShort(msg);
            }
        });
    }

    //获取购物车数量
    private void requestGetCarNum() {
        HnHttpUtils.postRequest(HnUrl.GOODS_CAR_NUM, null, "购物车数量", new HnResponseHandler<GoodsCarNumModel>(GoodsCarNumModel.class) {
            @Override
            public void hnSuccess(String response) {
                if (mAct == null) return;
                if (model.getD() != null && model.getD().getNum() != 0) {
                    if (mIvAddGoodsNew != null) mIvAddGoodsNew.setVisibility(View.VISIBLE);
                } else {
                    if (mIvAddGoodsNew != null) mIvAddGoodsNew.setVisibility(View.GONE);
                }
            }

            @Override
            public void hnErr(int errCode, String msg) {
                if (mIvAddGoodsNew != null) mIvAddGoodsNew.setVisibility(View.GONE);
            }
        });
    }
}
