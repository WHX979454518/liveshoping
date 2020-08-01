package com.live.shoplib.ui;

import android.app.Dialog;
import android.graphics.Bitmap;
import android.net.Uri;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.google.gson.Gson;
import com.hn.library.base.BaseRequestStateListener;
import com.hn.library.base.baselist.BaseViewHolder;
import com.hn.library.base.baselist.CommRecyclerAdapter;
import com.hn.library.global.HnUrl;
import com.hn.library.http.BaseResponseModel;
import com.hn.library.http.HnHttpUtils;
import com.hn.library.http.HnResponseHandler;
import com.hn.library.picker.photo_picker.HnPhotoDialog;
import com.hn.library.picker.photo_picker.HnPhotoUtils;
import com.hn.library.utils.Base64;
import com.hn.library.utils.HnLogUtils;
import com.hn.library.utils.HnRefreshDirection;
import com.hn.library.utils.HnToastUtils;
import com.hn.library.utils.HnUiUtils;
import com.hn.library.view.FrescoImageView;
import com.jakewharton.rxbinding2.view.RxView;
import com.live.shoplib.R;
import com.live.shoplib.ShopActFacade;
import com.live.shoplib.ShopRequest;
import com.live.shoplib.bean.EvaPublicModel;
import com.live.shoplib.bean.EvaPublicSubBean;
import com.live.shoplib.bean.OrderRefreshEvent;
import com.live.shoplib.bean.StoreMsgModel;
import com.live.shoplib.other.HnAnchorAuthenticationBiz;
import com.live.shoplib.widget.StartRatingBar;
import com.live.shoplib.widget.control.HnUpLoadPhotoControl;
import com.loopj.android.http.RequestParams;
import com.reslibrarytwo.CommListActivity;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.functions.Consumer;

/**
 * 发表评论
 * 作者：Mr.Xu
 * 时间：2017/12/20
 */
@Route(path = "/shoplib/EvaPublicAct")
public class EvaPublicAct extends CommListActivity {
    private CommRecyclerAdapter mAdapter;
    private List<EvaPublicModel.DEntity.GoodsListEntity> mData = new ArrayList<>();
    private String order_id;
    private HnAnchorAuthenticationBiz mHnAnchorAuthenticationBiz;

    @Override
    protected String setTitle() {
        mHnAnchorAuthenticationBiz = new HnAnchorAuthenticationBiz(this);
        order_id = getIntent().getStringExtra("order_id");
        if (TextUtils.isEmpty(order_id)) {
            HnToastUtils.showToastShort("暂无记录");
            finish();
            return "";
        }
        return "发表评论";
    }

    @Override
    protected CommRecyclerAdapter setAdapter() {
        setShowSubTitle(true);
        mSubtitle.setVisibility(View.VISIBLE);
        mSubtitle.setText("发表");
        RxView.clicks(mSubtitle)
                .throttleFirst(2, TimeUnit.SECONDS)
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {
                        Gson gson = new Gson();
                        List<EvaPublicSubBean> data = new ArrayList<>();
                        for (int i = 0; i < mData.size(); i++) {

                            String imgs = mData.get(i).getImgs().toString();
                            imgs = imgs.replace("[", "");
                            imgs = imgs.replace("]", "");
                            data.add(new EvaPublicSubBean(mData.get(i).getGoods_id(),
                                    mData.get(i).getContent(),
                                    imgs,
                                    mData.get(i).getGrade(),
                                    mData.get(i).getGoods_attr()
                            ));
                        }

                        String sub = gson.toJson(data);
                        ShopRequest.annoOrder(Base64.encode(sub), order_id, new ShopRequest.OnRespondNothing() {
                            @Override
                            public void finishs() {
                                EventBus.getDefault().post(new OrderRefreshEvent(-1));
                                if (isFinishing()) return;
                                ShopActFacade.openEvaResult(order_id);
                                EvaPublicAct.this.finish();
                            }
                        });
                    }
                });
        mAdapter = new CommRecyclerAdapter() {
            @Override
            protected void onBindView(final BaseViewHolder holder, final int position) {
                //图
                ((FrescoImageView) holder.getView(R.id.mIvUserIco)).setImageURI(HnUrl.setImageUri(HnUrl.setImageUrl(mData.get(position).getGoods_img())));
                //星
                StartRatingBar mIvStart = holder.getView(R.id.mIvStart);
                mIvStart.setOnRatingChangeListener(new StartRatingBar.OnRatingChangeListener() {
                    @Override
                    public void onChange(int countSelected) {
                        mData.get(position).setGrade(countSelected + "");
                        //、5星（非常好）、4星（好）、3星（一般）、2星（差）、1星（非常差）
                        if (5 == countSelected) {
                            ((TextView) holder.getView(R.id.mTvState)).setText("非常好");
                        } else if (4 == countSelected) {
                            ((TextView) holder.getView(R.id.mTvState)).setText("好");
                        } else if (3 == countSelected) {
                            ((TextView) holder.getView(R.id.mTvState)).setText("一般");
                        } else if (2 == countSelected) {
                            ((TextView) holder.getView(R.id.mTvState)).setText("差");
                        } else if (1 == countSelected) {
                            ((TextView) holder.getView(R.id.mTvState)).setText("非常差");
                        }
                    }
                });
                //内容
                final EditText mEdContent = holder.getView(R.id.mEdContent);
                mEdContent.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        mData.get(position).setContent(mEdContent.getText().toString());
                    }
                });
                //添加
                final ImageView mIvAdd = holder.getView(R.id.mIvAddImg);
                FrescoImageView mIvIco1 = holder.getView(R.id.mIvIco1);
                FrescoImageView mIvIco2 = holder.getView(R.id.mIvIco2);
                FrescoImageView mIvIco3 = holder.getView(R.id.mIvIco3);
                RelativeLayout mRlImg1 = holder.getView(R.id.mRlImg1);
                RelativeLayout mRlImg2 = holder.getView(R.id.mRlImg2);
                RelativeLayout mRlImg3 = holder.getView(R.id.mRlImg3);
                ImageView mIvDelete1 = holder.getView(R.id.mIvDelete1);
                ImageView mIvDelete2 = holder.getView(R.id.mIvDelete2);
                ImageView mIvDelete3 = holder.getView(R.id.mIvDelete3);
                mIvDelete1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mData.get(position).getImgs().size() >= 1) {
                            mData.get(position).getImgs().remove(0);
                            mAdapter.notifyDataSetChanged();
                            mIvAdd.setVisibility(View.VISIBLE);
                        }
                    }
                });
                mIvDelete2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mData.get(position).getImgs().size() >= 2) {
                            mData.get(position).getImgs().remove(1);
                            mAdapter.notifyDataSetChanged();
                            mIvAdd.setVisibility(View.VISIBLE);
                        }
                    }
                });
                mIvDelete3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mData.get(position).getImgs().size() >= 3) {
                            mData.get(position).getImgs().remove(2);
                            mAdapter.notifyDataSetChanged();
                            mIvAdd.setVisibility(View.VISIBLE);
                        }
                    }
                });
                mIvAdd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//                        HnPhotoDialog picDialog = HnPhotoDialog.newInstance();
//                        picDialog.setOnImageCallBack(new HnPhotoDialog.OnImageCallBack() {
//                            @Override
//                            public void onImage(Bitmap bitmap, Uri uri) {
//                                if (bitmap != null) {
//                                    uploadImg(bitmap, position);
//                                }
//                            }
//                        });
//                        picDialog.show(getFragmentManager(), "phone");

                        mHnAnchorAuthenticationBiz.uploadPicFile("img", new BaseRequestStateListener() {
                            @Override
                            public void requesting() {
                                showDoing(getResources().getString(R.string.loading), null);
                            }

                            @Override
                            public void requestSuccess(String type, String response, Object obj) {
                                done();
                                HnToastUtils.showToastShort("上传成功");
                                String key = response;
                                mData.get(position).getImgs().add(key);
                                mAdapter.notifyDataSetChanged();
                            }

                            @Override
                            public void requestFail(String type, int code, String msg) {
                                done();
                                HnToastUtils.showToastShort("上传失败");
                            }
                        });

                    }
                });
                setImages(mIvAdd, mRlImg1, mIvIco1, mRlImg2, mIvIco2, mRlImg3, mIvIco3, mData.get(position).getImgs());
            }

            @Override
            protected int getLayoutID(int position) {
                return R.layout.item_eva_public;
            }

            @Override
            public int getItemCount() {
                return mData.size();
            }
        };

        return mAdapter;
    }


    //处理三张图
    private void setImages(ImageView mIvAdd, RelativeLayout mRlImg1, FrescoImageView mIvIco1, RelativeLayout mRlImg2, FrescoImageView mIvIco2,
                           RelativeLayout mRlImg3, FrescoImageView mIvIco3, List<String> imgs) {
        if (imgs.size() == 0) {
            mRlImg1.setVisibility(View.GONE);
            mRlImg2.setVisibility(View.GONE);
            mRlImg3.setVisibility(View.GONE);
            mIvAdd.setVisibility(View.VISIBLE);
        }
        if (imgs.size() >= 1) {
            mRlImg1.setVisibility(View.VISIBLE);
            mIvIco1.setImageURI(Uri.parse(HnUrl.setImageUrl(imgs.get(0))));
        } else {
            mRlImg1.setVisibility(View.GONE);
        }

        if (imgs.size() >= 2) {
            mRlImg2.setVisibility(View.VISIBLE);
            mIvIco2.setImageURI(Uri.parse(HnUrl.setImageUrl(imgs.get(1))));
        } else {
            mRlImg2.setVisibility(View.GONE);
        }

        if (imgs.size() >= 3) {
            mRlImg3.setVisibility(View.VISIBLE);
            mIvIco3.setImageURI(Uri.parse(HnUrl.setImageUrl(imgs.get(2))));
            mIvAdd.setVisibility(View.GONE);
        } else {
            mRlImg3.setVisibility(View.GONE);
        }
    }

    @Override
    protected RequestParams setRequestParam() {
        RequestParams params = new RequestParams();
        params.put("order_id", order_id);
        return params;
    }

    @Override
    protected String setRequestUrl() {
        return HnUrl.EVA_PUBLIC_LIST;
    }

    @Override
    protected HnResponseHandler setResponseHandler(final HnRefreshDirection state) {
        return new HnResponseHandler<EvaPublicModel>(EvaPublicModel.class) {
            @Override
            public void hnSuccess(String response) {
                if (isFinishing()) return;
                refreshFinish();
                if (model.getD() == null) {
                    setEmpty("暂无评价", R.drawable.no_comments);
                    return;
                }
                if (HnRefreshDirection.TOP == state) {
                    mData.clear();
                }
                mData.addAll(model.getD().getGoods_list());
                if (mAdapter != null)
                    mAdapter.notifyDataSetChanged();
                setEmpty("暂无评价", R.drawable.no_comments);
            }

            @Override
            public void hnErr(int errCode, String msg) {
                if (isFinishing()) return;
                refreshFinish();
                HnToastUtils.showToastShort(msg);
                setEmpty("暂无评论", R.drawable.no_comments);
            }
        };
    }

}
