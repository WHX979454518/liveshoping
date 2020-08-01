package com.live.shoplib.ui.frag

import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.hn.library.base.BaseFragment
import com.hn.library.base.baselist.BaseViewHolder
import com.hn.library.base.baselist.CommRecyclerAdapter
import com.hn.library.utils.HnToastUtils
import com.live.shoplib.R
import com.live.shoplib.adapter.GoodsSortItemAdapter
import com.live.shoplib.bean.GoodSortOptionModel
import com.live.shoplib.bean.GoodsAddModel
import com.live.shoplib.bean.GoodsEditValueBean
import com.live.shoplib.bean.GoodsTemp4Bean
import com.live.shoplib.other.GoodsEditInterface
import com.live.shoplib.ui.dialog.GoodsValueDialog
import com.live.shoplib.widget.FlowTag.FlowTagLayout
import kotlinx.android.synthetic.main.frag_goods_edit_step4.*


/**
 * 商品编辑step4
 */
class GoodsEditStep4Frag : BaseFragment() {


    var bean: GoodsAddModel.DBean? = null
    var mAdapter: CommRecyclerAdapter? = null
    var listener: GoodsEditInterface? = null

    var mDataSpec: ArrayList<GoodSortOptionModel.DBean.SpecBean>? = null

    var mListData = ArrayList<GoodsEditValueBean>()


    companion object {
        fun launch(store_id: String): GoodsEditStep4Frag {
            var frag = GoodsEditStep4Frag()
            var bundle = Bundle()
            bundle.putString("store_id", store_id)
            frag.arguments = bundle
            return frag
        }
    }

    fun setLisenter(i: GoodsEditInterface) {
        listener = i
    }

    fun getSku(): String {
        var temp = "{"
        for (x in 0 until mListData.size) {
            temp = temp + "\"" + mListData[x].attr_name + "\":{" + "\"price\":\"" + mListData[x].price + "\"," + "\"stock\":\"" + mListData[x].ware + "\"" + "},"

        }
        temp = temp.substring(0, temp.length - 1)
        temp = temp + "}"
        return temp
    }


    fun getSkuEdit(): String {
        var temp = "{"
        for (x in 0 until mListData.size) {
            temp = temp + "\"" + mListData[x].edit_key + "\":{" + "\"price\":\"" + mListData[x].price + "\"," + "\"stock\":\"" + mListData[x].ware + "\"" + "},"

        }
        temp = temp.substring(0, temp.length - 1)
        temp = temp + "}"
        return temp
    }

    fun getSpec(): String {
        var temp = "{"
        for (x in 0 until mDataSpec!!.size) {
            temp = temp + "\"" + mDataSpec!![x].field + "\":["
            for (y in 0 until mDataSpec!![x].option.size) {
                temp = temp + "\"" + mDataSpec!![x].option[y] + "\","
            }
            temp = temp.substring(0, temp.length - 1)
            temp = temp + "],"
        }
        temp = temp.substring(0, temp.length - 1)
        temp = temp + "}"
        return temp
    }

    fun dealData(data: ArrayList<GoodSortOptionModel.DBean.SpecBean>): ArrayList<ArrayList<String>> {
        var res = ArrayList<ArrayList<String>>()
        for (x in 0 until data.size) {
            var temp = ArrayList<String>()
            for (y in 0 until data[x].option.size) {
                temp.add(data[x].option[y])
                if (!res.contains(temp)) res.add(temp)
            }
        }
        return res
    }

    fun dealDataAttr(data: ArrayList<GoodSortOptionModel.DBean.SpecBean>): ArrayList<ArrayList<String>> {
        var res = ArrayList<ArrayList<String>>()
        for (x in 0 until data.size) {
            var temp = ArrayList<String>()
            for (y in 0 until data[x].attr_option.size) {
                temp.add(data[x].attr_option[y])
                if (!res.contains(temp)) res.add(temp)
            }
        }
        return res
    }

    fun recursionData(cur: ArrayList<ArrayList<String>>, attr: ArrayList<ArrayList<String>>) {
        if (cur.size == 0 || cur.size < 2) {
            for (x in 0 until cur[0].size) {
                mListData.add(GoodsEditValueBean())
                mListData[x].name = cur[0][x].split(":") as java.util.ArrayList<String>
                mListData[x].attr_name = attr[0][x]
                mListData[x].attr_edit = attr[0][x].replace(":", ";")
                mListData[x].attr_edit = mListData[x].attr_edit.replace("_", ":")
            }
            return
        }

        var newArray: ArrayList<String> = ArrayList()
        var newArray2: ArrayList<String> = ArrayList()
        var temp1: ArrayList<String> = cur.removeAt(0)
        var temp2: ArrayList<String> = cur.removeAt(0)
        var temp3: ArrayList<String> = attr.removeAt(0)
        var temp4: ArrayList<String> = attr.removeAt(0)
        for (x in 0 until temp1.size) {
            for (y in 0 until temp2.size) {
                newArray.add(temp1[x] + ":" + temp2[y])
                newArray2.add(temp3[x] + ":" + temp4[y])
            }
        }
        cur.add(0, newArray)
        attr.add(0, newArray2)
        recursionData(cur, attr)
    }

    override fun getContentViewId() = R.layout.frag_goods_edit_step4

    override fun initView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?) {}

    override fun initData() {
        mRecycler.layoutManager = LinearLayoutManager(activity)
        mAdapter = object : CommRecyclerAdapter() {
            override fun getItemCount(): Int = mListData!!.size

            override fun getLayoutID(position: Int): Int = R.layout.item_goods_add

            override fun onBindView(holder: BaseViewHolder?, position: Int) {
                val spec = holder!!.getView<FlowTagLayout>(R.id.mSpecRecycler)
                var mTvWare = holder!!.getView<TextView>(R.id.mTvWare)
                var mTvPrice = holder!!.getView<TextView>(R.id.mTvPrice)
                var mLLitem = holder!!.getView<LinearLayout>(R.id.mLLitem)
                initList(spec, position)
                if (TextUtils.isEmpty(mListData[position].price)) {
                    mTvWare.visibility = View.GONE
                    mTvPrice.setText("设置价格")
                } else {
                    mTvWare.visibility = View.VISIBLE
                    mTvWare.setText("库存：" + mListData[position].ware + "\t\t\t\t价格：")
                    mTvPrice.setText(mListData[position].price)
                }
                mLLitem.setOnClickListener {
                    var dialog = GoodsValueDialog(mListData[position].ware, mListData[position].price, object : GoodsValueDialog.OnValueSet {
                        override fun onValue(ware: String?, value: String?) {
                            mListData[position].ware = ware
                            mListData[position].price = value
                            notifyDataSetChanged()
                            checkNext()
                        }
                    })
                    dialog.show(getActivity()!!.getFragmentManager(), "")
                }
            }
        }
        mRecycler.adapter = mAdapter
    }

    fun initList(mFlowLayout: FlowTagLayout, pos: Int) {
        val mAdapter = GoodsSortItemAdapter(activity)
        mFlowLayout.adapter = mAdapter
        mFlowLayout.isEnabled = false
        mAdapter.addDatas(mListData[pos].name)
    }


    //检测
    fun checkNext() {
        if (listener != null) listener!!.onNextCheck(checkCanNext(false))
    }

    //初始化设置
    fun setInitData(data: ArrayList<GoodSortOptionModel.DBean.SpecBean>) {
        mDataSpec = data
        recursionData(dealData(this!!.mDataSpec!!), dealDataAttr(this!!.mDataSpec!!))
        mAdapter!!.notifyDataSetChanged()
    }

    //初始化设置  - - - TODO
    fun setInitData(data: ArrayList<GoodSortOptionModel.DBean.SpecBean>, sku: String) {
        mDataSpec = data
        recursionData(dealData(this!!.mDataSpec!!), dealDataAttr(this!!.mDataSpec!!))
        val gson = Gson()
        val type = object : TypeToken<Map<String, GoodsTemp4Bean>>() {}.type
        val map = gson.fromJson<Map<String, GoodsTemp4Bean>>(sku, type)
        for ((key, value) in map) {
            for (x in 0 until mListData.size) {
                if (TextUtils.equals(mListData[x].attr_edit, value.spec_text)) {
                    mListData[x].price = value.price
                    mListData[x].ware = value.stock
                    mListData[x].edit_key = value.spec_ids
                }
            }
        }
        mAdapter!!.notifyDataSetChanged()
        checkNext()
    }

    //检测是否可以点击下一步
    fun checkCanNext(show: Boolean): Boolean {
        for (i in 0 until mListData.size) {
            if (TextUtils.isEmpty(mListData[i].price) || TextUtils.isEmpty(mListData[i].ware)) {
                if (show) HnToastUtils.showToastShort("请设置库存和价格")
                return false
            }
        }
        return true
    }

}