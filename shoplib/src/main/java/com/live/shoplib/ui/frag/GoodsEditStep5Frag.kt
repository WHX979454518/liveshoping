package com.live.shoplib.ui.frag

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.recyclerview.widget.LinearLayoutManager
import com.hn.library.base.BaseFragment
import com.hn.library.base.baselist.BaseViewHolder
import com.hn.library.base.baselist.CommRecyclerAdapter
import com.hn.library.global.HnUrl
import com.hn.library.http.HnHttpUtils
import com.hn.library.http.HnResponseHandler
import com.hn.library.utils.HnToastUtils
import com.live.shoplib.R
import com.live.shoplib.bean.GoodServerModel
import com.live.shoplib.bean.GoodsAddModel
import com.live.shoplib.other.GoodsEditInterface
import com.loopj.android.http.RequestParams
import kotlinx.android.synthetic.main.frag_goods_edit_step4.*
import org.json.JSONArray


/**
 * 商品编辑step5
 */
class GoodsEditStep5Frag : BaseFragment() {


    var bean: GoodsAddModel.DBean? = null
    var mAdapter: CommRecyclerAdapter? = null
    var listener: GoodsEditInterface? = null

    var mData: ArrayList<GoodServerModel.DBean.PromisesBean> = ArrayList()
    var mEditData: ArrayList<String> = ArrayList()

    fun getPromise(): String {
        var promise = ArrayList<String>()
        mData.forEach {
            if (it.isCheck) {
                promise.add(it.name)
            }
        }
        return JSONArray(promise).toString()
    }

    fun setInitData(edit: ArrayList<String>) {
        mEditData = edit
    }


    companion object {
        fun launch(store_id: String): GoodsEditStep5Frag {
            var frag = GoodsEditStep5Frag()
            var bundle = Bundle()
            bundle.putString("store_id", store_id)
            frag.arguments = bundle
            return frag
        }
    }

    fun setLisenter(i: GoodsEditInterface) {
        listener = i
    }

    override fun getContentViewId() = R.layout.frag_goods_edit_step4

    override fun initView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?) {}

    override fun initData() {
        mRecycler.layoutManager = LinearLayoutManager(activity)
        mAdapter = object : CommRecyclerAdapter() {
            override fun getItemCount(): Int = mData!!.size

            override fun getLayoutID(position: Int): Int = R.layout.item_server

            override fun onBindView(holder: BaseViewHolder?, position: Int) {
                var mBoxItem = holder!!.getView<CheckBox>(R.id.mBoxItem)
                mBoxItem.isChecked = mData[position].isCheck
                mBoxItem.setText(mData[position].name)
                mBoxItem.setOnClickListener {
                    if (mBoxItem.isChecked) {
                        mBoxItem.isChecked = true
                        mData[position].isCheck = true
                    } else {
                        mBoxItem.isChecked = false
                        mData[position].isCheck = false
                    }
                    checkNext()
                }
            }
        }
        mRecycler.adapter = mAdapter

        requestData()
    }

    fun requestData() {
        val param = RequestParams()
        HnHttpUtils.postRequest(HnUrl.GOODS_SERVER, param, "服务", object : HnResponseHandler<GoodServerModel>(GoodServerModel::class.java) {
            override fun hnSuccess(response: String) {
                mData.clear()
                mData.addAll(model.d.promises)
                for (i in 0 until mData.size) {
                    for (j in 0 until mEditData.size) {
                        if (TextUtils.equals(mData[i].name, mEditData[j])) {
                            mData[i].isCheck = true
                            continue
                        }
                    }
                }
                mAdapter!!.notifyDataSetChanged()
            }

            override fun hnErr(errCode: Int, msg: String) {
                HnToastUtils.showToastShort(msg)
            }
        })
    }

    //检测
    fun checkNext() {
        if (listener != null) listener!!.onNextCheck(checkCanNext(false))
    }


    //检测是否可以点击下一步
    fun checkCanNext(show: Boolean): Boolean {
        for (i in 0 until mData.size) {
            if (mData[i].isCheck) {
                return true
            }
        }
        return false
    }

}