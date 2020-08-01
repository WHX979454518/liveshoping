package com.live.shoplib.ui.frag

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.google.gson.JsonArray
import com.google.gson.JsonParser
import com.google.gson.reflect.TypeToken
import com.hn.library.base.BaseFragment
import com.hn.library.base.baselist.BaseViewHolder
import com.hn.library.base.baselist.CommRecyclerAdapter
import com.hn.library.global.HnUrl
import com.hn.library.http.HnHttpUtils
import com.hn.library.http.HnResponseHandler
import com.hn.library.utils.HnToastUtils
import com.live.shoplib.R
import com.live.shoplib.adapter.GoodsSortAdapter
import com.live.shoplib.bean.GoodSortModel
import com.live.shoplib.bean.GoodSortOptionModel
import com.live.shoplib.bean.GoodsAddModel
import com.live.shoplib.bean.GoodsTemp3Bean
import com.live.shoplib.other.GoodsEditInterface
import com.live.shoplib.ui.dialog.GoodSortDialog
import com.live.shoplib.ui.dialog.GoodsAttrDialog
import com.live.shoplib.ui.dialog.StoreGroupDialog
import com.live.shoplib.widget.FlowTag.FlowTagLayout
import com.loopj.android.http.RequestParams
import kotlinx.android.synthetic.main.frag_goods_edit_step3.*
import org.json.JSONArray
import org.json.JSONObject


/**
 * 商品编辑step3
 */
class GoodsEditStep3Frag : BaseFragment() {

    var bean: GoodsAddModel.DBean? = null
    var listener: GoodsEditInterface? = null
    var mAdapterSpec: CommRecyclerAdapter? = null
    var mAdapterAttr: CommRecyclerAdapter? = null
    var mDataSpec: ArrayList<GoodSortOptionModel.DBean.SpecBean>? = null
    var mTempSpec: ArrayList<GoodSortOptionModel.DBean.SpecBean> = ArrayList()
    var mEditS: ArrayList<GoodSortOptionModel.DBean.SpecBean> = ArrayList()
    var mDataAttr: ArrayList<GoodSortOptionModel.DBean.AttrBean>? = null
    var mTempAttr: ArrayList<GoodSortOptionModel.DBean.AttrBean> = ArrayList()
    var mEditA: ArrayList<GoodSortOptionModel.DBean.AttrBean> = ArrayList()
    var store_goods_category_id = ""
    var mCurAttr: ArrayList<String> = ArrayList()
    var mCurAttrOption: ArrayList<String> = ArrayList()
    var specObj: JSONObject? = null
    var isEdit = false
    var categoryNameStr = ""
    fun getAttr(): String {
        var temp = "{"
        for (x in 0 until mCurAttr.size) {
            temp = temp + "\"" + mCurAttr[x] + "\":\"" + mCurAttrOption[x] + "\","
        }
        temp = temp.substring(0, temp.length - 1)
        temp = temp + "}"
        return temp
    }

    fun getStoreGoodsCategoryId() = store_goods_category_id

    //传入底层，计算选中
    fun getSpecData(): ArrayList<GoodSortOptionModel.DBean.SpecBean> {
        var temp = ArrayList<GoodSortOptionModel.DBean.SpecBean>()
        for (i in 0 until mTempSpec.size) {
            temp.add(GoodSortOptionModel.DBean.SpecBean())
            temp[i].field = mTempSpec[i].field
            temp[i].category_id = mTempSpec[i].category_id
            temp[i].id = mTempSpec[i].id
            temp[i].option = ArrayList<String>()
            temp[i].attr_option = ArrayList<String>()
            for (j in 0 until mTempSpec[i].option.size) {
                if (isCheckTemp(i, j)) {
                    temp[i].option.add(mTempSpec[i].option[j].substring(1, mTempSpec[i].option[j].length))
                    temp[i].attr_option.add(temp[i].field + "_" + mTempSpec[i].option[j].substring(1, mTempSpec[i].option[j].length))
                }
            }
        }
        return temp
    }


    fun setInitData(edit: Boolean,
                    category_name: String,
                    specStr: String,
                    attrStr: String,
                    spec: JSONObject) {
        isEdit = edit
        specObj = spec
        categoryNameStr = category_name

        val gson = Gson()
        var i = -1
        val parser = JsonParser()
        val jsonObj = parser.parse(specStr).asJsonObject
        val it = jsonObj.entrySet().iterator()
        while (it.hasNext()) {
            val entry = it.next() as java.util.Map.Entry<*, *>
            i++
            mEditS.add(GoodSortOptionModel.DBean.SpecBean())
            mEditS[i].field = entry.key as String
            mEditS[i].option = ArrayList()
            var temp = entry.value as JsonArray
            for (x in 0 until temp.size()) {
                var obj = gson.fromJson(temp[x], GoodsTemp3Bean::class.java)
                mEditS[i].option.add(obj.spec_value)
            }
        }
        val type2 = object : TypeToken<Map<String, String>>() {}.type
        val map2 = gson.fromJson<Map<String, String>>(attrStr, type2)
        i = -1
        for ((key, value) in map2) {
            i++
            mEditA.add(GoodSortOptionModel.DBean.AttrBean())
            mEditA[i].field = key
            mEditA[i].option = ArrayList()
            mEditA[i].option.add(value)
        }

    }

    //编辑商品-初始化数据
    fun initSet() {
        for (x in 0 until mDataSpec!!.size) {
            var json = specObj!!.get(mDataSpec!![x].field) as JSONArray
            for (y in 0 until json.length()) {
                val gson = Gson()
                val bean = gson.fromJson<GoodsTemp3Bean>(json[y].toString(), GoodsTemp3Bean::class.java)
//                if (!mCurAttr.contains(bean.spec_group)) mCurAttr.add(bean.spec_group)
                //避免重复，每个规格item都有他的属性
                if (mDataSpec!![x].option.toString().contains(bean.spec_value)) {
                    change(x, y, true)
                }
            }
        }
        for (x in 0 until mDataAttr!!.size) {
            mCurAttrOption.add(mEditA!![x].option[0])
            mDataAttr!![x].temp = mEditA!![x].option[0]
        }
        mAdapterSpec!!.notifyDataSetChanged()
        mAdapterAttr!!.notifyDataSetChanged()
        checkNext()
    }

    //构造
    companion object {
        fun launch(store_id: String): GoodsEditStep3Frag {
            var frag = GoodsEditStep3Frag()
            var bundle = Bundle()
            bundle.putString("store_id", store_id)
            frag.arguments = bundle
            return frag
        }
    }

    //设置监听
    fun setLisenter(i: GoodsEditInterface) {
        listener = i
    }

    override fun getContentViewId() = R.layout.frag_goods_edit_step3

    override fun initView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?) {}

    override fun initData() {
        if (!isEdit) {//添加商品的操作-非编辑则禁止
            mTvSort.setOnClickListener {
                requestSort()
            }
        } else {//先有数据后布局生成之后才填充
            mTvSort.setText(categoryNameStr)
            mCurAttr.clear()
            mCurAttrOption.clear()
            mTempSpec.clear()
            mTempAttr.clear()
            initRecycler(mEditS, mEditA)
            initSet()
            checkNext()
        }
    }

    //检测
    fun checkNext() {
        if (listener != null) listener!!.onNextCheck(checkCanNext(false, false))
    }

    fun checkNext(edit: Boolean) {
        if (listener != null) listener!!.onNextCheck(checkCanNext(false, edit))
    }


    //检测是否可以点击下一步
    fun checkCanNext(show: Boolean, edit: Boolean): Boolean {
        if (edit) return true
        try {
            for (x in 0..mDataSpec!!.size - 1) {
                for (i in 0..mDataSpec!![x].option.size - 1) {
                    if (isCheck(x, i)) {
                        if (!mTempSpec.contains(mDataSpec!![x])) {
                            mTempSpec.add(mDataSpec!![x])
                        }
                        break
                    } else {
                        mTempSpec.remove(mDataSpec!![x])
                    }
                }
            }

            if (mDataSpec!!.size != mTempSpec.size) {
                if (show) HnToastUtils.showToastShort("请选择商品规格")
                return false
            }
            if (mDataAttr!!.size != mCurAttrOption.size) {
                if (show) HnToastUtils.showToastShort("请选择商品属性")
                return false
            }
        } catch (e: Exception) {
            return false
        }
        return canNext
    }


    fun requestSort() {
        val param = RequestParams()
        HnHttpUtils.postRequest(HnUrl.GOODS_SORT, param, "商品分类", object : HnResponseHandler<GoodSortModel>(GoodSortModel::class.java) {
            override fun hnSuccess(response: String) {

                if (model.d.category.size == 0) {
                    HnToastUtils.showToastShort("你还没有设置类型，请先添加")
                    return
                }
                val dialog = GoodSortDialog(activity, model.d.category, GoodSortDialog.onCityPickedListener { bean ->
                    if (mTvSort != null) {
                        mTvSort.setText(bean.name)
                        requestSortOption(bean.id)
                        store_goods_category_id = bean.id
                    }
                })
                dialog.show()
            }

            override fun hnErr(errCode: Int, msg: String) {
                HnToastUtils.showToastShort(msg)
            }
        })
    }

    var canNext = true

    fun requestSortOption(category_id: String) {
        val param = RequestParams()
        param.put("category_id", category_id)
        HnHttpUtils.postRequest(HnUrl.GOODS_SORT_OPTION, param, "商品分类", object : HnResponseHandler<GoodSortOptionModel>(GoodSortOptionModel::class.java) {
            override fun hnSuccess(response: String) {
                //TODO 显示
                mCurAttr.clear()
                mCurAttrOption.clear()
                mTempSpec.clear()
                mTempAttr.clear()

                if (model.d.spec.size == 0 || model.d.attr.size == 0) {
                    HnToastUtils.showToastShort("请先完善类型设置")
                    canNext = false
                } else {
                    canNext = true
                }

                for(i in 0 until model.d.attr.size){
                    if(model.d.attr[i].option.size==0) {
                        HnToastUtils.showToastShort("请先完善规格的值设置")
                        return
                    }
                }
                for(i in 0 until model.d.spec.size){
                    if(model.d.spec[i].option.size==0) {
                        HnToastUtils.showToastShort("请先完善属性的值设置")
                        return
                    }
                }



                initRecycler(model.d.spec as ArrayList<GoodSortOptionModel.DBean.SpecBean>,
                        model.d.attr as ArrayList<GoodSortOptionModel.DBean.AttrBean>)
                checkNext()
            }

            override fun hnErr(errCode: Int, msg: String) {
                HnToastUtils.showToastShort(msg)
            }
        })
    }


    //初始化列表生成-两个数组是第三步传递过来，或则编辑添加生成
    fun initRecycler(data1: ArrayList<GoodSortOptionModel.DBean.SpecBean>, data2: ArrayList<GoodSortOptionModel.DBean.AttrBean>) {
        mDataSpec = data1
        for (x in 0..mDataSpec!!.size - 1) {
            for (i in 0..mDataSpec!![x].option.size - 1) {
                mDataSpec!![x].option[i] = "-" + mDataSpec!![x].option[i]
            }
        }
        mDataAttr = data2
        mRecyclerSpec.layoutManager = LinearLayoutManager(activity)
        mRecyclerAttr.layoutManager = LinearLayoutManager(activity)
        mAdapterSpec = object : CommRecyclerAdapter() {
            override fun getItemCount(): Int = mDataSpec!!.size

            override fun getLayoutID(position: Int): Int = R.layout.item_goods_edit_spec

            override fun onBindView(holder: BaseViewHolder?, position: Int) {
                val spec = holder!!.getView<FlowTagLayout>(R.id.mSpecRecycler)
                holder.setTextViewText(R.id.mTvSpec, mDataSpec!![position].field)
                initList(spec, position)
            }
        }
        mRecyclerSpec.adapter = mAdapterSpec
        for (i in 0 until mDataAttr!!.size) {
            mCurAttr.add(mDataAttr!![i].field)
        }
        mAdapterAttr = object : CommRecyclerAdapter() {
            override fun getItemCount(): Int = mDataAttr!!.size

            override fun getLayoutID(position: Int): Int = R.layout.item_goods_edit_attr

            override fun onBindView(holder: BaseViewHolder?, position: Int) {
                holder!!.setTextViewText(R.id.mTvAttrName, mDataAttr!![position].field)
                var mTvAttrOption = holder.getView<TextView>(R.id.mTvAttrOption)
                mTvAttrOption.setText(mDataAttr!![position].temp)
                holder.itemView.setOnClickListener {
                    if (isEdit) {
                        StoreGroupDialog.newInstance(activity)
                                .setTitles("编辑属性")
                                .setContent(mDataAttr!![position].temp)!!
                                .setClickListen(object : StoreGroupDialog.TwoSelDialog {
                                    override fun leftClick() {

                                    }

                                    override fun rightClick(content: String) {
                                        mDataAttr!![position].temp = content
                                        if (!mCurAttrOption.contains(mDataAttr!![position].temp)) {
                                            mCurAttrOption.removeAt(position)
                                            mCurAttrOption.add(position, mDataAttr!![position].temp)
                                            notifyDataSetChanged()
                                        }
                                    }
                                }).show()
                    } else {
                        val dialog = GoodsAttrDialog(activity, mDataAttr!![position].option, GoodsAttrDialog.onCityPickedListener { bean ->
                            if (mTvAttrOption != null) {
                                if (!mCurAttrOption.contains(bean)) {
                                    mDataAttr!![position].temp = bean
                                    mCurAttrOption.add(mDataAttr!![position].temp)
                                    notifyDataSetChanged()
                                }
                            }
                            checkNext()
                        })
                        dialog.show()
                    }
                }
            }
        }
        mRecyclerAttr.adapter = mAdapterAttr

        mTvGoodsSpec.visibility = if (canNext) View.VISIBLE else View.GONE

    }


    //属性规格
    fun initList(mFlowLayout: FlowTagLayout, pos: Int) {
        val mAdapter = GoodsSortAdapter(activity)
        mFlowLayout.adapter = mAdapter
        mAdapter.addDatas(mDataSpec!![pos].option)
        if (isEdit) {
            mFlowLayout.isEnabled = false
        } else {
            mFlowLayout.isEnabled = true
            mFlowLayout.setOnTagClickListener { parent, view, position ->
                change(pos, position, false)
                mAdapterSpec!!.notifyDataSetChanged()
                checkNext()
            }
        }
    }

    fun change(pos: Int, position: Int, set: Boolean) {
        if (isCheck(pos, position) && !set) {
            mDataSpec!![pos].option[position] = "-" + mDataSpec!![pos].option[position].substring(1, mDataSpec!![pos].option[position].length)
        } else {
            mDataSpec!![pos].option[position] = "*" + mDataSpec!![pos].option[position].substring(1, mDataSpec!![pos].option[position].length)
        }
    }

    //自定义选择 - 未选 * 选中
    fun isCheck(pos: Int, position: Int) = !TextUtils.equals("-", mDataSpec!![pos].option[position].get(0).toString())

    fun isCheckTemp(pos: Int, position: Int) = !TextUtils.equals("-", mTempSpec!![pos].option[position].get(0).toString())


}