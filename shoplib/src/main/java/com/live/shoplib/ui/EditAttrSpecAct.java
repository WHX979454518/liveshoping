package com.live.shoplib.ui;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.hn.library.base.BaseActivity;
import com.hn.library.global.HnUrl;
import com.hn.library.http.BaseResponseModel;
import com.hn.library.http.HnHttpUtils;
import com.hn.library.http.HnResponseHandler;
import com.hn.library.utils.HnToastUtils;
import com.hn.library.view.CommDialog;
import com.hn.library.view.HnEditText;
import com.live.shoplib.R;
import com.live.shoplib.adapter.EditAttrAdapter;
import com.live.shoplib.adapter.GoodsSortItemAdapter;
import com.live.shoplib.ui.dialog.StoreGroupDialog;
import com.live.shoplib.widget.FlowTag.FlowTagLayout;
import com.loopj.android.http.RequestParams;

import java.util.ArrayList;

/**
 * create by Mr.x
 * on 2018/5/3
 */
@Route(path = "/shoplib/EditAttrSpecAct")
public class EditAttrSpecAct extends BaseActivity implements EditAttrAdapter.onDelClick {

    public FlowTagLayout mSpecRecycler;
    public EditAttrAdapter mAdapter;
    public HnEditText mTvValue;
    public TextView mTvTag, mTvSave;
    public ArrayList<String> mData = new ArrayList<>();
    public String type;
    public boolean edit = false;
    public boolean edit2 = false;
    public String name;

    @Override
    public int getContentViewId() {
        return R.layout.act_edit_attr;
    }

    @SuppressLint("WrongViewCast")
    @Override
    public void onCreateNew(Bundle savedInstanceState) {

        setShowSubTitle(true);
        mSubtitle.setTextColor(getResources().getColor(R.color.main_color));
        mSubtitle.setText("增加值");

        mData = getIntent().getStringArrayListExtra("data");
        type = getIntent().getStringExtra("type");

        setTitle("0".equals(type) ? "编辑属性" : "编辑规格");
        setShowBack(true);

        mSpecRecycler = findViewById(R.id.mSpecRecycler);
        mTvTag = findViewById(R.id.mTvTag);
        mTvValue = findViewById(R.id.mTvValue);
        mTvSave = findViewById(R.id.mTvSave);

        mTvTag.setText("0".equals(type) ? "属性名称：" : "规格名称：");
        name = getIntent().getStringExtra("value");
        mTvValue.setText(name);

        mAdapter = new EditAttrAdapter(this);
        mAdapter.setDelClick(this);
        mSpecRecycler.setAdapter(mAdapter);
        mAdapter.addDatas(mData);


    }

    @Override
    public void getInitData() {
        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setBack();
            }
        });
        mSubtitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StoreGroupDialog.newInstance(EditAttrSpecAct.this)
                        .setTitles("0".equals(type) ? "属性值" : "规格值")
                        .setContent("")
                        .setClickListen(new StoreGroupDialog.TwoSelDialog() {
                            @Override
                            public void leftClick() {

                            }

                            @Override
                            public void rightClick(String content) {
                                mAdapter.add(content);
                            }
                        })
                        .show();
            }
        });
        mTvSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String temp = TextUtils.join(";", mAdapter.getData());
                if (TextUtils.isEmpty(mTvValue.getText().toString())) {
                    HnToastUtils.showToastShort("请输入名称");
                    return;
                }
                if (TextUtils.isEmpty(temp)) {
                    HnToastUtils.showToastShort("请输入值");
                    return;
                }
                requestEdit(mTvValue.getText().toString(), temp);
            }
        });

        mTvValue.addTextChangedListener(mTextWatcher);
    }

    @Override
    public void click(String value, final int pos) {
        StoreGroupDialog.newInstance(this)
                .setTitles("0".equals(type) ? "属性值" : "规格值")
                .setContent(value)
                .setClickListen(new StoreGroupDialog.TwoSelDialog() {
                    @Override
                    public void leftClick() {

                    }

                    @Override
                    public void rightClick(String content) {
                        mAdapter.replace(content, pos);
                    }
                })
                .show();
    }

    @Override
    public void clickDel(int pos) {
        try {
            edit = true;
            mAdapter.remove(pos);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void requestEdit(String name, final String value) {
        RequestParams param = new RequestParams();
        param.put("id", getIntent().getStringExtra("id"));
        param.put("store_id", getIntent().getStringExtra("store_id"));
        param.put("type", type);
        param.put("name", name);
        HnHttpUtils.postRequest(HnUrl.EDIT_ATTR_NAME, param, "保存", new HnResponseHandler<BaseResponseModel>(BaseResponseModel.class) {
            @Override
            public void hnSuccess(String response) {
                requestEditValue(value);
                edit2 = false;
            }

            @Override
            public void hnErr(int errCode, String msg) {
                requestEditValue(value);
            }
        });
    }

    private void requestEditValue(final String value) {
        RequestParams param = new RequestParams();
        param.put("id", getIntent().getStringExtra("id"));
        param.put("store_id", getIntent().getStringExtra("store_id"));
        param.put("type", type);
        param.put("value", value);
        HnHttpUtils.postRequest(HnUrl.EDIT_ATTR_SPEC, param, "保存", new HnResponseHandler<BaseResponseModel>(BaseResponseModel.class) {
            @Override
            public void hnSuccess(String response) {
                HnToastUtils.showToastShort("保存成功");
                name = value;
                edit = false;
                finish();
            }

            @Override
            public void hnErr(int errCode, String msg) {

            }
        });
    }


    private void setBack() {
        //TODO
        if (edit || edit2) {
            CommDialog.newInstance(this)
                    .setTitle("提示")
                    .setContent("你还没保存设置，确认退出吗？")
                    .setClickListen(new CommDialog.TwoSelDialog() {
                        @Override
                        public void leftClick() {

                        }

                        @Override
                        public void rightClick() {
                            finish();
                        }
                    })
                    .show();
        } else {
            finish();
        }
    }

    @Override
    public void onBackPressed() {
        setBack();
    }

    TextWatcher mTextWatcher = new TextWatcher() {
        private CharSequence temp;

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            temp = s;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            //TextView.setText(s);//将输入的内容实时显示
        }

        @Override
        public void afterTextChanged(Editable s) {
            edit2 = !TextUtils.equals(temp, name);
        }
    };
}
