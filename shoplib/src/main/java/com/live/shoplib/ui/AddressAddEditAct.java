package com.live.shoplib.ui;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.hn.library.base.BaseActivity;
import com.hn.library.global.HnUrl;
import com.hn.library.http.BaseResponseModel;
import com.hn.library.http.HnHttpUtils;
import com.hn.library.http.HnResponseHandler;
import com.hn.library.picker.address_picker.City;
import com.hn.library.picker.address_picker.County;
import com.hn.library.picker.address_picker.HnAddressPickerTask;
import com.hn.library.picker.address_picker.Province;
import com.hn.library.utils.HnToastUtils;
import com.live.shoplib.R;
import com.live.shoplib.bean.MyRecAddressModel;
import com.loopj.android.http.RequestParams;

/**
 * 新加/编辑地址
 * 作者：Mr.Xu
 * 时间：2017/12/20
 */
@Route(path = "/shoplib/AddressAddEditAct")
public class AddressAddEditAct extends BaseActivity {

    private EditText mEdName;
    private EditText mEdPhone;
    private TextView mEdArea;
    private EditText mEdAddress;
    private LinearLayout mLLDef;
    private CheckBox mBoxDef;

    private MyRecAddressModel.DEntity bean;
    private HnAddressPickerTask mTask;
    private String mProvince = "";
    private String mCity = "";
    private String mArea = "";

    @Override
    public int getContentViewId() {
        return R.layout.act_add_edit_address;
    }

    @Override
    public void onCreateNew(Bundle savedInstanceState) {
        setShowBack(true);


        setShowSubTitle(true);
        mSubtitle.setVisibility(View.VISIBLE);
        mEdName = (EditText) findViewById(R.id.mEdName);
        mEdPhone = (EditText) findViewById(R.id.mEdPhone);
        mEdArea = (TextView) findViewById(R.id.mEdArea);
        mEdAddress = (EditText) findViewById(R.id.mEdAddress);
        mLLDef = (LinearLayout) findViewById(R.id.mLLDef);
        mBoxDef = (CheckBox) findViewById(R.id.mBoxDef);

        try {
            bean = (MyRecAddressModel.DEntity) getIntent().getSerializableExtra("bean");
            mEdName.setText(bean.getNick());
            mEdArea.setText(bean.getProvince() + "-" + bean.getCity() + "-" + bean.getArea());
            mEdAddress.setText(bean.getDetail());
            mEdPhone.setText(bean.getPhone());
            if (TextUtils.equals("1", bean.getIs_default())) {
//                mLLDef.setVisibility(View.GONE);
                mBoxDef.setChecked(true);
            } else {
                mBoxDef.setChecked(false);
                mLLDef.setVisibility(View.VISIBLE);
            }
            mProvince = bean.getProvince();
            mCity = bean.getCity();
            mArea = bean.getArea();
        } catch (Exception e) {
            e.printStackTrace();

        }
        mBoxDef.setChecked(bean == null ? true : false);
        setTitle(bean != null ? "编辑地址" : "添加新地址");
        mSubtitle.setText("保存");

        mLLDef.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBoxDef.setChecked(!mBoxDef.isChecked());
            }
        });
        mEdArea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
                                mEdArea.setText("");
                            } else {
                                mProvince = provinceName;
                                mCity = cityName;
                                mArea = CountName;
                                mEdArea.setText(provinceName + "-" + cityName + "-" + CountName);
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
                                mEdArea.setText("");
                            } else {
                                mProvince = provinceName;
                                mCity = cityName;
                                mArea = CountName;
                                mEdArea.setText(provinceName + "-" + cityName + "-" + CountName);
                            }
                        }
                    });
                }
            }
        });
        mTask = new HnAddressPickerTask(this, mEdArea);
        mTask.execute();
    }

    @Override
    public void getInitData() {
        mSubtitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = mEdName.getText().toString();
                String address = mEdAddress.getText().toString();
                String phone = mEdPhone.getText().toString();
                String area = mEdArea.getText().toString();
                String def;
                if (bean!=null && bean.getIs_default().equals("1")) {
                    def = "1";
                } else {
                    def = mBoxDef.isChecked() ? "1" : "0";
                }
                if (TextUtils.isEmpty(name)) {
                    HnToastUtils.showToastShort("请填写真实姓名");
                    return;
                }
                if (TextUtils.isEmpty(phone)) {
                    HnToastUtils.showToastShort("请输入手机号");
                    return;
                }
                if (TextUtils.isEmpty(area)) {
                    HnToastUtils.showToastShort("请选择地区");
                    return;
                }
                if (TextUtils.isEmpty(address)) {
                    HnToastUtils.showToastShort("请填写详细地址");
                    return;
                }
                if (!phone.matches("^1[3-8]\\d{9}$")) {
                    HnToastUtils.showToastShort("请输入正确的手机号");
                    return;
                }
                if (bean == null) {
                    addAddress(mProvince, mArea, mCity, address, def, name, phone);
                } else {
                    if (bean != null) {
                        if (TextUtils.equals(bean.getNick(), name) &&
                                TextUtils.equals(bean.getArea(), mArea) &&
                                TextUtils.equals(bean.getCity(), mCity) &&
                                TextUtils.equals(bean.getProvince(), mProvince) &&
                                TextUtils.equals(bean.getDetail(), address) &&
                                TextUtils.equals(bean.getIs_default(), def) &&
                                TextUtils.equals(bean.getPhone(), phone)) {
                            finish();
                            return;
                        }
                    }
                    editAddress(mProvince, mArea, mCity, address, def, name, phone, bean.getId());
                }
            }
        });

    }

    //是否设置为默认地址 1 or 0
    public void addAddress(String province, String area, String city, String detail,
                           String is_default, String nick, String phone) {
        RequestParams param = new RequestParams();
        param.put("province", province);
        param.put("area", area);
        param.put("city", city);
        param.put("detail", detail);
        param.put("is_default", is_default);
        param.put("nick", nick);
        param.put("phone", phone);
        HnHttpUtils.postRequest(HnUrl.ADDRESS_ADD, param, "地址添加", new HnResponseHandler<BaseResponseModel>(BaseResponseModel.class) {
            @Override
            public void hnSuccess(String response) {
                HnToastUtils.showToastShort("添加成功");
                finish();
            }

            @Override
            public void hnErr(int errCode, String msg) {
                HnToastUtils.showToastShort(msg);
            }
        });
    }

    //是否设置为默认地址 1 or 0
    public void editAddress(String province, String area, String city, String detail,
                            String is_default, String nick, String phone, String address_id) {
        RequestParams param = new RequestParams();
        param.put("address_id", address_id);
        param.put("province", province);
        param.put("area", area);
        param.put("city", city);
        param.put("detail", detail);
        param.put("is_default", is_default);
        param.put("nick", nick);
        param.put("phone", phone);
        HnHttpUtils.postRequest(HnUrl.ADDRESS_EDIT, param, "地址编辑", new HnResponseHandler<BaseResponseModel>(BaseResponseModel.class) {
            @Override
            public void hnSuccess(String response) {
                HnToastUtils.showToastShort("保存成功");
                finish();
            }

            @Override
            public void hnErr(int errCode, String msg) {
                HnToastUtils.showToastShort("保存失败");
            }
        });
    }
}
