package com.live.shoplib.ui

import android.content.Context
import android.content.Intent
import android.os.Build
import android.text.TextUtils
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.CheckBox
import android.widget.TextView
import androidx.annotation.RequiresApi
import com.hn.library.base.baselist.BaseViewHolder
import com.hn.library.base.baselist.CommRecyclerAdapter
import com.hn.library.global.HnUrl
import com.hn.library.http.HnHttpUtils
import com.hn.library.http.HnResponseHandler
import com.hn.library.picker.address_picker.StoreGroupModel
import com.hn.library.picker.address_picker.StringOneDialog
import com.hn.library.utils.HnRefreshDirection
import com.hn.library.utils.HnToastUtils
import com.hn.library.view.FrescoImageView
import com.live.shoplib.R
import com.live.shoplib.ShopRequest
import com.live.shoplib.bean.GoodsAddModel
import com.live.shoplib.bean.GoodsListModel
import com.live.shoplib.bean.ShopLiveSearchModel
import com.loopj.android.http.RequestParams
import com.reslibrarytwo.CommListActivity
import kotlinx.android.synthetic.main.act_store_goods_list.*

/**
 * 商品列表
 */
class StoreGoodsListAct : CommListActivity() {

    private var mAdapter: CommRecyclerAdapter? = null
    private val mData = ArrayList<GoodsListModel.DEntity.ItemsEntity>()
    internal var show = false

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun setTitle(): String {
        mSubtitle.visibility = View.VISIBLE
//        mSubtitle.text = "仓库"
//        mSubtitle.setBackgroundResource(R.drawable.warehouse)
        val drawable = resources.getDrawable(R.drawable.warehouse, null)
        drawable.setBounds(0, 0, drawable.intrinsicWidth,
                drawable.intrinsicHeight)
        mSubtitle.setCompoundDrawables(null, null, drawable, null);
        mSubtitle.setOnClickListener {
            StoreGoodsListDownAct.launchAct(
                    this@StoreGoodsListAct,
                    intent.getStringExtra("column_id"),
                    intent.getStringExtra("store_id"))
        }
        return "商品列表"
    }

    override fun onResume() {
        super.onResume()
        getData(HnRefreshDirection.TOP, 1)
    }

    companion object {
        fun launchAct(context: Context, column_id: String, store_id: String) {
            context.startActivity(Intent(context, StoreGoodsListAct::class.java)
                    .putExtra("store_id", store_id)
                    .putExtra("column_id", column_id))
        }
    }

    override fun setRequestParam(): RequestParams {
        var params = RequestParams()
        params.put("column_id", intent.getStringExtra("column_id"))
        params.put("status", "1")//1：上架，0：下架，默认上架
        params.put("store_id", intent.getStringExtra("store_id"))
        return params
    }

    override fun getContentViewId(): Int {
        return R.layout.act_store_goods_list
    }

    fun initView() {
        mTv1.setText("分类至")
        mTv1.setTextColor(resources.getColor(R.color.comm_text_color_black))
        mTv2.setText("批量下架")
        mTv2.setTextColor(resources.getColor(R.color.comm_text_color_red))
        mTv1.setOnClickListener {
            calcuate1()
        }
        mTv2.setOnClickListener {
            calcuate2()
        }
    }

    override fun setAdapter(): CommRecyclerAdapter {
        initView()
        mSpring.isEnabled = false
        mAdapter = object : CommRecyclerAdapter() {
            override fun onBindView(holder: BaseViewHolder, position: Int) {
                if (position == 0) {
                    holder.getView<CheckBox>(R.id.mBoxSel).visibility = GONE
                    val mTvDel = holder.getView<TextView>(R.id.mTvDel)
                    val mTvTitle = holder.getView<TextView>(R.id.mTvTitle)
                    if (mTvDel.text.toString().contains("批量")) {
                        mTvDel.text = "批量管理"
                        mTvTitle.text = "共 " + (mData.size - 1) + " 个商品"
                    } else if (TextUtils.equals("取消", mTvDel.text.toString())) {
                        mTvTitle.text = "全选"
                    }
                    mTvDel.setOnClickListener {
                        if (TextUtils.equals("批量管理", mTvDel.text.toString())) {
                            mTvDel.text = "取消"
                            mTvTitle.text = "全选"
                            show = true
                            mLLBottom.visibility = VISIBLE
                        } else {
                            mTvDel.text = "批量管理"
                            mTvTitle.text = "共 " + (mData.size - 1) + " 个上架商品"
                            show = false
                            mLLBottom.visibility = GONE
                        }
                        mAdapter!!.notifyDataSetChanged()
                    }
                    mTvDel.text = if (show) "取消" else "批量管理"
                    mTvDel.setTextColor(resources.getColor(R.color.main_color))
                } else {
                    val mBoxSel = holder.getView<CheckBox>(R.id.mBoxSel)
                    mBoxSel.visibility = if (show) View.VISIBLE else View.GONE
                    mBoxSel.isChecked = mData[position].isCheck
                    mBoxSel.setOnClickListener {
                        mData[position].isCheck = mBoxSel.isChecked
                        notifyDataSetChanged()
                    }
                    holder.getView<FrescoImageView>(R.id.mIvGoodsIco).setImageURI(HnUrl.setImageUri(mData[position].goods_img))
                    holder.setTextViewText(R.id.mTvGoodsName, mData[position].goods_name)
                    holder.setTextViewText(R.id.mTvPrice, "¥" + mData[position].goods_price)
                    holder.setTextViewText(R.id.mTvGoodsMsg, "总销量:" + mData[position].goods_sales + "   库存:" + mData[position].goods_stock)
                    holder.itemView.setOnClickListener {
                        //TODO 商品编辑
                        requestDetails(mData[position].goods_id)
                    }
                    holder.itemView.setOnLongClickListener {
                        requestSoldOut(mData.get(position).goods_id)
                        false
                    }
                }
            }

            override fun getLayoutID(position: Int): Int {
                return if (position == 0) R.layout.item_store_group_head else R.layout.item_store_group_goods
            }

            override fun getItemCount(): Int {
                return mData.size
            }
        }
        return mAdapter as CommRecyclerAdapter
    }


    private fun requestDetails(goods_id: String) {
        val param = RequestParams()
        param.put("goods_id", goods_id)
        HnHttpUtils.postRequest(HnUrl.STORE_GOODS_INFO, param, "商品栏目列表", object : HnResponseHandler<GoodsAddModel>(GoodsAddModel::class.java) {
            override fun hnSuccess(response: String) {
                if (model.d != null) {
                    GoodsEditAct.launch(this@StoreGoodsListAct, goods_id, model.d)
                    //                    startActivity(new Intent(StoreGoodsAct.this,GoodsEditAct.class).putExtra("store_id",goods_id).putExtra("bean",model));
                }
            }

            override fun hnErr(errCode: Int, msg: String) {
                HnToastUtils.showToastShort(msg)
            }
        })
    }

    //计算请求拼接
    @Synchronized
    private fun calcuate1() {
        var reqMsg = ""
        for (i in 1 until mData.size) {
            if (mData[i].isCheck) {
                reqMsg = reqMsg + mData[i].goods_id + ","
            }
        }
        if (TextUtils.isEmpty(reqMsg)) return
        reqMsg = reqMsg.substring(0, reqMsg.length - 1)
        requestList(reqMsg)
    }

    @Synchronized
    private fun calcuate2() {
        var reqMsg = ""
        for (i in 1 until mData.size) {
            if (mData[i].isCheck) {
                reqMsg = reqMsg + mData[i].goods_id + ","
            }
        }
        if (TextUtils.isEmpty(reqMsg)) return
        reqMsg = reqMsg.substring(0, reqMsg.length - 1)
        requestSoldOut(reqMsg)
    }


    private fun requestList(good_id: String) {
        val param = RequestParams()
        HnHttpUtils.postRequest(HnUrl.STORE_GROUP_LIST, param, "商品栏目列表", object : HnResponseHandler<StoreGroupModel>(StoreGroupModel::class.java) {
            override fun hnSuccess(response: String) {
                var list = ArrayList<StoreGroupModel.DBean.ItemsBean>()
                for (i in 0 until model.d.items.size) {
                    if (!model.d.items[i].id.equals(intent.getStringExtra("column_id"))) {
                        list.add(model.d.items[i])
                    }
                }
                val dialog = StringOneDialog(this@StoreGoodsListAct, list, StringOneDialog.onCityPickedListener { bean ->
                    ShopRequest.editStoreGroupGoods(bean.id, good_id) {
                        getData(HnRefreshDirection.TOP, 1)
                    }
                })
                dialog.show()
            }

            override fun hnErr(errCode: Int, msg: String) {
                HnToastUtils.showToastShort(msg)
            }
        })
    }

    private fun requestSoldOut(goods_id: String) {
        val param = RequestParams()
        param.put("value", "0")//0下架 1上架
        param.put("goods_id", goods_id)
        HnHttpUtils.postRequest(HnUrl.GOODS_STATE, param, "商品状态", object : HnResponseHandler<ShopLiveSearchModel>(ShopLiveSearchModel::class.java) {
            override fun hnSuccess(response: String) {
                getData(HnRefreshDirection.TOP, 1)
                HnToastUtils.showToastShort("下架成功")
            }

            override fun hnErr(errCode: Int, msg: String) {
                HnToastUtils.showToastShort(msg)
            }
        })
    }


    override fun setRequestUrl(): String {
        return HnUrl.GOODS_LIST
    }

    override fun setResponseHandler(state: HnRefreshDirection): HnResponseHandler<*> {
        return object : HnResponseHandler<GoodsListModel>(GoodsListModel::class.java) {
            override fun hnSuccess(response: String) {
                if (isFinishing) return
                refreshFinish()
                if (model.d == null) {
                    setEmpty("暂无商品", R.drawable.no_columns)
                    return
                }
                if (HnRefreshDirection.TOP == state) {
                    mData.clear()
                }
                if (model.d.items.size > 0) {
                    val itemsBean = GoodsListModel.DEntity.ItemsEntity()
                    mData.add(itemsBean)
                }
                mData.addAll(model.d.items)
                if (mAdapter != null)
                    mAdapter!!.notifyDataSetChanged()
                setEmpty("暂无商品", R.drawable.no_columns)
            }

            override fun hnErr(errCode: Int, msg: String) {
                if (isFinishing) return
                refreshFinish()
                HnToastUtils.showToastShort(msg)
                setEmpty("暂无商品", R.drawable.no_columns)
            }
        }
    }

}
