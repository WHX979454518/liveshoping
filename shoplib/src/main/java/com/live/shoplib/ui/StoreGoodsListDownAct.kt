package com.live.shoplib.ui

import android.content.Context
import android.content.Intent
import android.text.TextUtils
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.CheckBox
import android.widget.TextView
import com.hn.library.base.baselist.BaseViewHolder
import com.hn.library.base.baselist.CommRecyclerAdapter
import com.hn.library.global.HnUrl
import com.hn.library.http.HnHttpUtils
import com.hn.library.http.HnResponseHandler
import com.hn.library.utils.HnRefreshDirection
import com.hn.library.utils.HnToastUtils
import com.hn.library.view.CommDialog
import com.hn.library.view.FrescoImageView
import com.live.shoplib.R
import com.live.shoplib.ShopRequest
import com.live.shoplib.bean.GoodsListModel
import com.live.shoplib.bean.ShopLiveSearchModel
import com.loopj.android.http.RequestParams
import com.reslibrarytwo.CommListActivity
import kotlinx.android.synthetic.main.act_store_goods_list.*
import java.util.*

/**
 * 仓库列表
 */
class StoreGoodsListDownAct : CommListActivity() {

    private var mAdapter: CommRecyclerAdapter? = null
    private val mData = ArrayList<GoodsListModel.DEntity.ItemsEntity>()
    internal var show = false

    override fun setTitle(): String {
        mSubtitle.visibility = View.GONE
        mSubtitle.setOnClickListener {

        }
        return "仓库列表"
    }

    companion object {
        fun launchAct(context: Context, column_id: String, store_id: String) {
            context.startActivity(Intent(context, StoreGoodsListDownAct::class.java)
                    .putExtra("store_id", store_id)
                    .putExtra("column_id", column_id))
        }
    }

    override fun setRequestParam(): RequestParams {
        var params = RequestParams()
        params.put("column_id", intent.getStringExtra("column_id"))
        params.put("status", "0")//1：上架，0：下架，默认上架
        params.put("store_id", intent.getStringExtra("store_id"))
        return params
    }

    override fun getContentViewId(): Int {
        return R.layout.act_store_goods_list
    }

    fun initView() {
        mTv1.setText("删除")
        mTv1.setTextColor(resources.getColor(R.color.comm_text_color_black))
        mTv2.setText("批量上架")
        mTv2.setTextColor(resources.getColor(R.color.main_color))
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
                        mTvTitle.text = "共 " + (mData.size - 1) + " 个下架商品"
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
                            mTvTitle.text = "共 " + (mData.size - 1) + " 个商品"
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
                    holder.itemView.setOnLongClickListener {
                        requestSoldUp(mData.get(position).goods_id)
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
        var dialog = CommDialog(this@StoreGoodsListDownAct)
        dialog.setContent("确认删除吗？")
        dialog.setClickListen(object : CommDialog.TwoSelDialog {
            override fun leftClick() {

            }

            override fun rightClick() {
                ShopRequest.delShopGoods(reqMsg) {
                    getData(HnRefreshDirection.TOP, 1)
                }
            }

        })
        dialog.show()
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
        requestSoldUp(reqMsg)
    }


    private fun requestSoldUp(goods_id: String) {
        val param = RequestParams()
        param.put("value", "1")//0下架 1上架
        param.put("goods_id", goods_id)
        HnHttpUtils.postRequest(HnUrl.GOODS_STATE, param, "商品状态", object : HnResponseHandler<ShopLiveSearchModel>(ShopLiveSearchModel::class.java) {
            override fun hnSuccess(response: String) {
                getData(HnRefreshDirection.TOP, 1)
                HnToastUtils.showToastShort("上架成功")
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
