package com.live.shoplib.ui;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.hn.library.base.BaseActivity;
import com.hn.library.base.BaseRequestStateListener;
import com.hn.library.global.HnUrl;
import com.hn.library.http.BaseResponseModel;
import com.hn.library.http.HnHttpUtils;
import com.hn.library.http.HnResponseHandler;
import com.hn.library.picker.address_picker.City;
import com.hn.library.picker.address_picker.County;
import com.hn.library.picker.address_picker.HnAddressPickerTask;
import com.hn.library.picker.address_picker.Province;
import com.hn.library.utils.HnFileUtils;
import com.hn.library.utils.HnToastUtils;
import com.hn.library.view.FrescoImageView;
import com.hn.library.view.HnEditText;
import com.live.shoplib.R;
import com.live.shoplib.bean.StoreEditModel;
import com.live.shoplib.other.HnAnchorAuthenticationBiz;
import com.loopj.android.http.RequestParams;

import java.util.List;

/**
 * 店铺编辑
 * 作者：Mr.Xu
 * 时间：2018/1/4
 */
@Route(path = "/shoplib/StoreEditAct")
public class StoreEditAct extends BaseActivity implements BaseRequestStateListener {

    private HnEditText mEdName;
    private HnEditText mEdIntro;
    private HnEditText mEdAnno;
    private HnEditText mEdMain;
    private HnEditText mEdMain2;
    private RelativeLayout mRlImg1;
    private FrescoImageView mIvIco1;
    private RelativeLayout mRlImg2;
    private FrescoImageView mIvIco2;
    private RelativeLayout mRlImg3;
    private FrescoImageView mIvIco3;
    private RelativeLayout mRlImg4;
    private FrescoImageView mIvIco4;
    private RelativeLayout mRlImg5;
    private FrescoImageView mIvIco5;
    private ImageView mIvAddImg;
    private RelativeLayout mRlVideo;
    private FrescoImageView mIvVideo;
    private ImageView mIvAddVideo;
    private TextView mTvAddress;
    private HnEditText mEdAddress;
    private HnEditText mEdPhone;
    private HnEditText mEdPerson;
    private ImageView mIvDelete1;
    private ImageView mIvDelete2;
    private ImageView mIvDelete3;
    private ImageView mIvDelete4;
    private ImageView mIvDelete5;
    private ImageView mIvDeleteVideo;
    private LinearLayout mLLAddress;
    private String store_id;
    private StoreEditModel.DEntity bean;
    private HnAddressPickerTask mTask;
    private String mProvince = "";
    private String mCity = "";
    private String mArea = "";

    private HnAnchorAuthenticationBiz mHnAnchorAuthenticationBiz;


    public static void launchAct(Context context, String store_id) {
        context.startActivity(new Intent(context, FreightSetAct.class));
    }

    @Override
    public int getContentViewId() {
        return R.layout.act_store_edit;
    }

    @Override
    public void onCreateNew(Bundle savedInstanceState) {
        store_id = getIntent().getStringExtra("store_id");
        initView();

        mTask = new HnAddressPickerTask(this, mTvAddress);
        mTask.execute();

        mHnAnchorAuthenticationBiz = new HnAnchorAuthenticationBiz(this);
        mHnAnchorAuthenticationBiz.setLoginListener(this);
        bean = new StoreEditModel.DEntity();
    }

    private void initView() {
        mEdName = (HnEditText) findViewById(R.id.mEdName);
        mEdIntro = (HnEditText) findViewById(R.id.mEdIntro);
        mEdAnno = (HnEditText) findViewById(R.id.mEdAnno);
        mEdMain = (HnEditText) findViewById(R.id.mEdMain);
        mEdMain2 = (HnEditText) findViewById(R.id.mEdMain2);
        mRlImg1 = (RelativeLayout) findViewById(R.id.mRlImg1);
        mIvIco1 = (FrescoImageView) findViewById(R.id.mIvIco1);
        mRlImg2 = (RelativeLayout) findViewById(R.id.mRlImg2);
        mIvIco2 = (FrescoImageView) findViewById(R.id.mIvIco2);
        mRlImg3 = (RelativeLayout) findViewById(R.id.mRlImg3);
        mIvIco3 = (FrescoImageView) findViewById(R.id.mIvIco3);
        mRlImg4 = (RelativeLayout) findViewById(R.id.mRlImg4);
        mIvIco4 = (FrescoImageView) findViewById(R.id.mIvIco4);
        mRlImg5 = (RelativeLayout) findViewById(R.id.mRlImg5);
        mIvIco5 = (FrescoImageView) findViewById(R.id.mIvIco5);
        mIvAddImg = (ImageView) findViewById(R.id.mIvAddImg);
        mRlVideo = (RelativeLayout) findViewById(R.id.mRlVideo);
        mIvVideo = (FrescoImageView) findViewById(R.id.mIvVideo);
        mIvAddVideo = (ImageView) findViewById(R.id.mIvAddVideo);
        mTvAddress = (TextView) findViewById(R.id.mTvAddress);
        mEdAddress = (HnEditText) findViewById(R.id.mEdAddress);
        mEdPhone = (HnEditText) findViewById(R.id.mEdPhone);
        mEdPerson = (HnEditText) findViewById(R.id.mEdPerson);
        mIvDelete1 = findViewById(R.id.mIvDelete1);
        mIvDelete2 = findViewById(R.id.mIvDelete2);
        mIvDelete3 = findViewById(R.id.mIvDelete3);
        mIvDelete4 = findViewById(R.id.mIvDelete4);
        mIvDelete5 = findViewById(R.id.mIvDelete5);
        mIvDeleteVideo = findViewById(R.id.mIvDeleteVideo);
        mLLAddress = findViewById(R.id.mLLAddress);
    }

    @Override
    public void getInitData() {
        setTitle("店铺编辑");
        setShowBack(true);
        mSubtitle.setVisibility(View.VISIBLE);
        mSubtitle.setText("提交");
        mSubtitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String address, province, city, district, img = "", video, intro, name, notice, table, table2, phone, real_name;

                address = mEdAddress.getText().toString();
                province = mProvince;
                city = mCity;
                district = mArea;
                if (bean != null) {
                    for (String str : bean.getImg()) {
                        img = img + "," + str;
                    }
                    if (img.contains(",")) img = img.substring(1, img.length());
                }
                video = bean.getVideo();
                intro = mEdIntro.getText().toString();
                name = mEdName.getText().toString();
                notice = mEdAnno.getText().toString();
                table = mEdMain.getText().toString();
                table2 = mEdMain2.getText().toString();
                phone = mEdPhone.getText().toString();
                real_name = mEdPerson.getText().toString();
                if (TextUtils.isEmpty(name)) {
                    HnToastUtils.showToastShort("请输入店铺名称");
                    return;
                }
                if (TextUtils.isEmpty(intro)) {
                    HnToastUtils.showToastShort("简单介绍下自己的小店吧~");
                    return;
                }
                if (TextUtils.isEmpty(notice)) {
                    HnToastUtils.showToastShort("请输入店铺公告");
                    return;
                }
                if (TextUtils.isEmpty(table) && TextUtils.isEmpty(table2)) {
                    HnToastUtils.showToastShort("请输入主营");
                    return;
                }
                if (TextUtils.isEmpty(img)) {
                    HnToastUtils.showToastShort("请选择图片");
                    return;
                }
                if (TextUtils.isEmpty(province)) {
                    HnToastUtils.showToastShort("请选择区域");
                    return;
                }
                if (TextUtils.isEmpty(address)) {
                    HnToastUtils.showToastShort("请输入详细地址");
                    return;
                }
                if (TextUtils.isEmpty(phone)) {
                    HnToastUtils.showToastShort("请输入电话号码");
                    return;
                }
                if (TextUtils.isEmpty(real_name)) {
                    HnToastUtils.showToastShort("请输入联系人");
                    return;
                }


                if (TextUtils.isEmpty(table) && !TextUtils.isEmpty(table2)) {
                    table = table2;
                } else if (!TextUtils.isEmpty(table2)) {
                    table = table + "," + table2;
                }
                requestSub(address, province, city, district, img, video, intro, name, notice, table, phone, real_name);

            }
        });
        //请求数据
        requestDetails(store_id);
    }


    public void onStoreEditClick(View view) {
        if (bean == null) return;
        if (view == mIvAddImg) {
            mHnAnchorAuthenticationBiz.uploadPicFile("img");
        } else if (view == mIvAddVideo) {
            mHnAnchorAuthenticationBiz.uploadPicFileVideo("video");
        } else if (view == mIvDeleteVideo) {
            bean.setVideo("");
            mRlVideo.setVisibility(View.GONE);
            mIvAddVideo.setVisibility(View.VISIBLE);
        } else if (view == mRlImg1) {//图1
            mHnAnchorAuthenticationBiz.uploadPicFile("1");
        } else if (view == mIvDelete1) {
            bean.getImg().remove(0);
            setImgList(bean.getImg());
        } else if (view == mRlImg2) {//图2
            mHnAnchorAuthenticationBiz.uploadPicFile("2");
        } else if (view == mIvDelete2) {
            bean.getImg().remove(1);
            setImgList(bean.getImg());
        } else if (view == mRlImg3) {//图3
            mHnAnchorAuthenticationBiz.uploadPicFile("3");
        } else if (view == mIvDelete3) {
            bean.getImg().remove(2);
            setImgList(bean.getImg());
        } else if (view == mRlImg4) {//图4
            mHnAnchorAuthenticationBiz.uploadPicFile("4");
        } else if (view == mIvDelete4) {
            bean.getImg().remove(3);
            setImgList(bean.getImg());
        } else if (view == mRlImg5) {//图5
            mHnAnchorAuthenticationBiz.uploadPicFile("5");
        } else if (view == mIvDelete5) {
            bean.getImg().remove(4);
            setImgList(bean.getImg());
        } else if (view == mLLAddress) {
            Province selectProvince = mTask.getSelectProvince();
            City selectCity = mTask.getSelectCity();
            County selectCounty = mTask.getSelectCounty();
            if (selectProvince != null && selectCity != null && selectCounty != null) {
                mTask.showAddressDialog(selectProvince, selectCity, selectCounty, true, new HnAddressPickerTask.onPickedListener() {

                    @Override
                    public void onPicked(String provinceName, String cityName, String CountName) {
                        if (TextUtils.equals(provinceName, "保密")) {
                            mProvince = "";
                            mCity = "";
                            mArea = "";
                            mTvAddress.setText("");
                        } else {
                            mProvince = provinceName;
                            mCity = cityName;
                            mArea = CountName;
                            mTvAddress.setText(provinceName + "-" + cityName + "-" + CountName);
                        }
                    }
                });
            } else {
                try {
                    if (mArea.contains("-")) {
                        String[] strs = mArea.split("-");
                        mProvince = strs[0];
                        mCity = strs[1];
                        mArea = strs[2];
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                mTask.showAddressDialog(new Province(mProvince), new City(mCity), new County(mArea), true, new HnAddressPickerTask.onPickedListener() {
                    @Override
                    public void onPicked(String provinceName, String cityName, String CountName) {
                        if (TextUtils.equals(provinceName, "保密")) {
                            mProvince = "";
                            mCity = "";
                            mArea = "";
                            mTvAddress.setText("");
                        } else {
                            mProvince = provinceName;
                            mCity = cityName;
                            mArea = CountName;
                            mTvAddress.setText(provinceName + "-" + cityName + "-" + CountName);
                        }
                    }
                });
            }
        }
    }


    private void requestSub(String address, String province, String city, String district,
                            String img, String video,
                            String intro, String name, String notice, String table,
                            String phone, String real_name) {
        RequestParams param = new RequestParams();
        param.put("video", video);
        param.put("table", table);
        param.put("store_id", store_id);
        param.put("real_name", real_name);
        param.put("province", province);
        param.put("phone", phone);
        param.put("notice", notice);
        param.put("name", name);
        param.put("address", address);
        param.put("city", city);
        param.put("district", district);
        param.put("img", img);
        param.put("intro", intro);
        HnHttpUtils.postRequest(HnUrl.Store_EDIT_SUB, param, "提交", new HnResponseHandler<BaseResponseModel>(BaseResponseModel.class) {
            @Override
            public void hnSuccess(String response) {
                HnToastUtils.showToastShort("提交成功");
                finish();
            }

            @Override
            public void hnErr(int errCode, String msg) {
                HnToastUtils.showToastShort(msg);
            }
        });
    }

    private void requestDetails(String store_id) {
        RequestParams param = new RequestParams();
        param.put("store_id", store_id);
        HnHttpUtils.postRequest(HnUrl.Store_EDIT_MSG, param, "卖家中心", new HnResponseHandler<StoreEditModel>(StoreEditModel.class) {
            @Override
            public void hnSuccess(String response) {
                if (isFinishing()) return;

                bean = model.getD();

                mEdName.setText(model.getD().getName());
                mEdName.setSelection(mEdName.getText().toString().length());
                mEdIntro.setText(model.getD().getIntro());
                mEdAnno.setText(model.getD().getNotice());


                if (model.getD().getTable() != null)
                    for (int i = 0; i < model.getD().getTable().size(); i++) {
                        if (i == 0) mEdMain.setText(model.getD().getTable().get(i));
                        if (i == 1) mEdMain2.setText(model.getD().getTable().get(i));
                    }


                mEdPhone.setText(model.getD().getPhone());
                mEdPerson.setText(model.getD().getRealName());

                mProvince = model.getD().getProvince();
                mCity = model.getD().getCity();
                mArea = model.getD().getDistrict();

                mEdAddress.setText(model.getD().getAddress());
                mTvAddress.setText(model.getD().getProvince() + "-" + model.getD().getCity() + "-" + model.getD().getDistrict());
                setImgList(model.getD().getImg());
                //视频
                if (!TextUtils.isEmpty(model.getD().getVideo())) {
                    mRlVideo.setVisibility(View.VISIBLE);
                    mIvVideo.setImageBitmap(HnFileUtils.createVideoThumbnail(model.getD().getVideo(), 200, 200));
//                    mIvVideo.setImageURI();
                }
            }

            @Override
            public void hnErr(int errCode, String msg) {
                HnToastUtils.showToastShort(msg);
            }
        });
    }

    public void setImgList(List<String> data) {
        mIvAddImg.setVisibility(View.VISIBLE);
        //图片
        if (data.size() >= 1 && !TextUtils.isEmpty(data.get(0))) {
            mRlImg1.setVisibility(View.VISIBLE);
            mIvIco1.setImageURI(Uri.parse(HnUrl.setImageUrl(data.get(0))));
        } else {
            mRlImg1.setVisibility(View.GONE);
        }
        if (data.size() >= 2) {
            mRlImg2.setVisibility(View.VISIBLE);
            mIvIco2.setImageURI(Uri.parse(HnUrl.setImageUrl(data.get(1))));
        } else {
            mRlImg2.setVisibility(View.GONE);
        }
        if (data.size() >= 3) {
            mRlImg3.setVisibility(View.VISIBLE);
            mIvIco3.setImageURI(Uri.parse(HnUrl.setImageUrl(data.get(2))));
        } else {
            mRlImg3.setVisibility(View.GONE);
        }
        if (data.size() >= 4) {
            mRlImg4.setVisibility(View.VISIBLE);
            mIvIco4.setImageURI(Uri.parse(HnUrl.setImageUrl(data.get(3))));
        } else {
            mRlImg4.setVisibility(View.GONE);
        }
        if (data.size() >= 5) {
            mRlImg5.setVisibility(View.VISIBLE);
            mIvIco5.setImageURI(Uri.parse(HnUrl.setImageUrl(data.get(4))));
            mIvAddImg.setVisibility(View.GONE);
        } else {
            mRlImg5.setVisibility(View.GONE);
        }
    }

    @Override
    public void requesting() {
        showDoing(getResources().getString(R.string.loading), null);
    }

    @Override
    public void requestSuccess(String type, String response, Object obj) {
        done();
        HnToastUtils.showToastShort("上传成功");
        String key = response;
        String select = (String) obj;
        if (!TextUtils.isEmpty(key)) {
            if (select.equals("img")) {
                bean.getImg().add(key);
                setImgList(bean.getImg());
            } else if (select.equals("1")) {
                bean.getImg().remove(0);
                bean.getImg().add(0, key);
                setImgList(bean.getImg());
            } else if (select.equals("2")) {
                bean.getImg().remove(1);
                bean.getImg().add(1, key);
                setImgList(bean.getImg());
            } else if (select.equals("3")) {
                bean.getImg().remove(2);
                bean.getImg().add(2, key);
                setImgList(bean.getImg());
            } else if (select.equals("4")) {
                bean.getImg().remove(3);
                bean.getImg().add(3, key);
                setImgList(bean.getImg());
            } else if (select.equals("5")) {
                bean.getImg().remove(4);
                bean.getImg().add(4, key);
                setImgList(bean.getImg());
            } else if (select.equals("video")) {
                bean.setVideo(key);
                mIvAddVideo.setVisibility(View.GONE);
                mRlVideo.setVisibility(View.VISIBLE);
                mIvVideo.setImageBitmap(HnFileUtils.createVideoThumbnail(key, 200, 200));
            }
        }
    }


    @Override
    public void requestFail(String type, int code, String msg) {
        done();
        HnToastUtils.showToastShort("上传失败");
    }
}
