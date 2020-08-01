package com.live.shoplib.ui

import android.os.Bundle
import android.text.TextUtils
import com.alibaba.android.arouter.facade.annotation.Route
import com.hn.library.base.BaseActivity
import com.hn.library.global.HnUrl
import com.hn.library.http.BaseResponseModel
import com.hn.library.http.HnHttpUtils
import com.hn.library.http.HnResponseHandler
import com.hn.library.utils.HnToastUtils
import com.live.shoplib.R
import com.live.shoplib.bean.StoreEditModel
import com.loopj.android.http.RequestParams
import kotlinx.android.synthetic.main.act_freight_set.*

/**
 * 运费设置
 */
@Route(path = "/shoplib/FreightSetAct")
class FreightSetAct : BaseActivity() {

//    companion object {
//        fun launchAct(context: Context, store_id: String) {
//            context.startActivity(Intent(context, FreightSetAct::class.java).putExtra("store_id", store_id))
//        }
//    }

    var fee = ""

    override fun getContentViewId() = R.layout.act_freight_set

    override fun onCreateNew(savedInstanceState: Bundle?) {
        setTitle("运费设置")
        setShowBack(true)
        if (TextUtils.isEmpty(intent.getStringExtra("store_id"))) {
            HnToastUtils.showToastShort("暂无店铺信息")
            finish()
            return
        }
    }

    override fun getInitData() {
        mEdPut.setText("")
        mTvSure.setOnClickListener {
            var fee = mEdPut.text.toString()
            if (TextUtils.isEmpty(fee)) {
                HnToastUtils.showCenterToast("请输入运费")
            } else {
                requestSub(fee)
            }
        }
        requestDetails()
    }


    private fun requestDetails() {
        val param = RequestParams()
        param.put("store_id", intent.getStringExtra("store_id"))
        HnHttpUtils.postRequest(HnUrl.Store_EDIT_MSG, param, "卖家中心", object : HnResponseHandler<StoreEditModel>(StoreEditModel::class.java) {
            override fun hnSuccess(response: String) {
                fee = model.d.shop_fee
                mEdPut.setText(fee)
            }

            override fun hnErr(errCode: Int, msg: String) {
                HnToastUtils.showToastShort(msg)
            }
        })
    }


    private fun requestSub(shop_fee: String) {
        val param = RequestParams()
        param.put("shop_fee", shop_fee)
        param.put("store_id", intent.getStringExtra("store_id"))
        HnHttpUtils.postRequest(HnUrl.Store_EDIT_SUB, param, "提交", object : HnResponseHandler<BaseResponseModel>(BaseResponseModel::class.java) {
            override fun hnSuccess(response: String) {
                HnToastUtils.showToastShort("提交成功")
                finish()
            }

            override fun hnErr(errCode: Int, msg: String) {
                HnToastUtils.showToastShort(msg)
            }
        })
    }
}

