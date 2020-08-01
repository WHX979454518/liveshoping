package com.hotniao.live.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.hn.library.base.BaseActivity;
import com.hotniao.live.R;
import com.reslibrarytwo.CommTabView;

import butterknife.BindView;

/**
 * 提交成功
 */
public class SubmitSuccessfullyActivity extends BaseActivity {

    @Override
    public int getContentViewId() {
        return R.layout.act_submitsuccessfully;
    }

    @Override
    public void onCreateNew(Bundle savedInstanceState) {
        setTitle("提交成功");
        setShowBack(true);
        }

    @Override
    public void getInitData() {
        TextView tv_back = findViewById(R.id.tv_back);
        tv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
