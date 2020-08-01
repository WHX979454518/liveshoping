package com.live.shoplib.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;

import com.alibaba.android.arouter.facade.annotation.Route;

@Route(path = "/shoplib/TeKotlinAct")
public class TeKotlinAct extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        switch (intent.getStringExtra("tag")) {
            case "HistoryRecordAct":
                intent.setClass(this, HistoryRecordAct.class);
                break;
            case "FreightSetAct":
                intent.setClass(this, FreightSetAct.class);
        }
        startActivity(intent);
        finish();
    }
}
