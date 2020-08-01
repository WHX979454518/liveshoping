package com.live.shoplib.ui

import android.content.Intent
import android.os.Bundle
import com.hn.library.base.BaseActivity
import com.hn.library.global.HnUrl
import com.hn.library.http.HnHttpUtils
import com.hn.library.http.HnResponseHandler
import com.hn.library.utils.HnToastUtils
import com.live.shoplib.R
import com.live.shoplib.bean.VisHistoryMainModel
import com.loopj.android.http.RequestParams
import kotlinx.android.synthetic.main.act_his_record.*

/**
 * 访问记录
 */
class HistoryRecordAct : BaseActivity() {
    override fun onCreateNew(savedInstanceState: Bundle?) {
    }

    override fun getInitData() {
        setTitle("访问记录")
        setShowBack(true)
        getDetails()
    }

    override fun getContentViewId(): Int = R.layout.act_his_record


    fun getDetails() {
        val param = RequestParams()
        HnHttpUtils.postRequest(HnUrl.VIS_HISTORY_MAIN, param, "访问记录", object : HnResponseHandler<VisHistoryMainModel>(VisHistoryMainModel::class.java) {
            override fun hnSuccess(response: String) {
                if (mTvClient == null) return
                mTvClient.setText(model.d.total_customer)
                mTvValue.setText(model.d.total_order_price)
                mTvGlance.setText(model.d.total_return_rate)
                mTvContent1.setText("潜在客户  " + model.d.potential_customer + "人")
                mTvContent2.setText("新客户  " + model.d.new_customer + "人")
                mTvContent3.setText("回头客  " + model.d.return_customer + "人")
                mTvAdd1.setText("今日新增" + model.d.today_potential_customer + "人")
                mTvAdd2.setText("今日新增" + model.d.today_new_customer + "人")
                mTvAdd3.setText("今日新增" + model.d.today_return_customer + "人")
                mLLItem1.setOnClickListener {
                    startActivity(Intent(this@HistoryRecordAct, HistoryRecordListAct::class.java)
                            .putExtra("title", "潜在客户")
                            .putExtra("type", "1")
                    )
                }
                mLLItem2.setOnClickListener {
                    startActivity(Intent(this@HistoryRecordAct, HistoryRecordListAct::class.java)
                            .putExtra("title", "新客户")
                            .putExtra("type", "2")
                    )
                }
                mLLItem3.setOnClickListener {
                    startActivity(Intent(this@HistoryRecordAct, HistoryRecordListAct::class.java)
                            .putExtra("title", "回头客")
                            .putExtra("type", "3")
                    )
                }
            }

            override fun hnErr(errCode: Int, msg: String) {
                HnToastUtils.showToastShort(msg)
            }
        })
    }
}