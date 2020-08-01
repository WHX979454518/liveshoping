package com.hotniao.live.activity;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.preference.Preference;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.TimePickerView;
import com.hn.library.base.BaseActivity;
import com.hn.library.global.HnUrl;
import com.hn.library.global.NetConstant;
import com.hn.library.http.BaseResponseModel;
import com.hn.library.http.HnHttpUtils;
import com.hn.library.http.HnResponseHandler;
import com.hn.library.utils.HnPrefUtils;
import com.hn.library.utils.HnToastUtils;
import com.hn.library.utils.HnUtils;
import com.hn.library.view.CommDialog;
import com.hotniao.live.R;
import com.live.shoplib.ShopActFacade;
import com.loopj.android.http.RequestParams;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 作者：Mr.Xu
 * 时间：2018/2/3
 */
public class HnAnchorAboutAct extends BaseActivity {
    @BindView(R.id.mLLBack)
    LinearLayout mLLBack;
    @BindView(R.id.mLLRoom)
    LinearLayout mLLRoom;
    @BindView(R.id.mLLNotice)
    LinearLayout mLLNotice;
    @BindView(R.id.mEdTitle)
    EditText mEdTitle;
    @BindView(R.id.mTvDate)
    TextView mTvDate;
    @BindView(R.id.mTvSure)
    TextView mTvSure;
    @BindView(R.id.mRlBg)
    RelativeLayout mRlBg;

    private String longTime = "";

    @Override
    public int getContentViewId() {
        return R.layout.act_anchor_about;
    }

    @Override
    public void onCreateNew(Bundle savedInstanceState) {
        setTitle("主播相关");
        setShowBack(true);
    }

    @Override
    public void getInitData() {

    }


    @TargetApi(Build.VERSION_CODES.M)
    @OnClick({R.id.mLLBack, R.id.mLLRoom, R.id.mLLNotice, R.id.mTvDate, R.id.mTvSure, R.id.mRlBg})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.mLLBack:
                ShopActFacade.openBackList(HnPrefUtils.getString(NetConstant.User.UID, ""), "直播回放");
                break;
            case R.id.mLLRoom:
                ShopActFacade.openMyAdminActivity();
                break;
            case R.id.mLLNotice:
                mRlBg.setVisibility(View.VISIBLE);
                mEdTitle.setText("");
                mTvDate.setText("请选择直播时间");
                break;
            case R.id.mTvDate:
                hideSoftKeyBoard(mRlBg, this);
                showDate();
                break;
            case R.id.mTvSure:
                if (TextUtils.isEmpty(mEdTitle.getText().toString().trim())) {
                    HnToastUtils.showToastShort("请输入直播标题");
                    return;
                }
                if (!TextUtils.isEmpty(longTime) && longTime.length() > 10) {
                    longTime = longTime.substring(0, 10);
                }
                if (TextUtils.isEmpty(longTime) || longTime.length() != 10) {
                    HnToastUtils.showToastShort("请选择日期");
                    return;
                }
                setNotice(longTime, mEdTitle.getText().toString());
                break;
            case R.id.mRlBg:
                mRlBg.setVisibility(View.GONE);
                hideSoftKeyBoard(mRlBg, this);
                break;
        }
    }


    public void setNotice(String live_time, String title) {
        RequestParams params = new RequestParams();
        params.put("live_time", live_time);
        params.put("title", title);
        HnHttpUtils.postRequest(HnUrl.ADD_LIVE_NOTICE, params, "预告添加", new HnResponseHandler<BaseResponseModel>(BaseResponseModel.class) {
            @Override
            public void hnSuccess(String response) {
                mRlBg.setVisibility(View.GONE);
                CommDialog.newInstance(HnAnchorAboutAct.this)
                        .setTitle("提示")
                        .setContent("预约申请提交成功，审核结果我们将以系统消息的形式通知~")
                        .setRightText("我知道了")
                        .setClickListen(new CommDialog.OneSelDialog() {
                            @Override
                            public void sureClick() {

                            }
                        })
                        .showDialog();
            }

            @Override
            public void hnErr(int errCode, String msg) {
                HnToastUtils.showToastShort(msg);
            }
        });
    }


    private void showDate() {
        Calendar startDate = Calendar.getInstance();
        long curt = System.currentTimeMillis();
        String curDate = HnUtils.getDateToString_1(curt);
        String replace = curDate.replace("-", "");
        String year = replace.substring(0, 4);
        String month = replace.substring(4, 6);
        startDate.set(Integer.parseInt(year) - 10, Integer.parseInt(month) - 1, 01);
        Calendar endDate = Calendar.getInstance();
        endDate.set(Integer.parseInt(year), Integer.parseInt(month) - 1, 30);
        TimePickerView view1 = new TimePickerView.Builder(this, new TimePickerView.OnTimeSelectListener() {

            @Override
            public void onTimeSelect(Date date, View v) {
                if (mTvDate != null) {
                    mTvDate.setText(new SimpleDateFormat("yyyy年MM月dd日 HH:mm").format(date));
                    longTime = date.getTime() + "";
                }
            }

            @Override
            public void onAllSelect(View v) {

            }
        })
                .setType(new boolean[]{false, true, true, true, true, false})
                .setLabel("", "月", "日", "", "", "")
                .isCenterLabel(false)
                .setContentSize(21)
                .setRangDate(startDate, endDate)
                .setDate(Calendar.getInstance())
                .setDecorView(null)
                .build();
        view1.setAllGone();
        view1.show();
    }

    public void hideSoftKeyBoard(View view, Context context) {
        InputMethodManager manager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        manager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

}
