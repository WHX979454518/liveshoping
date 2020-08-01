package com.live.shoplib.ui

import com.hn.library.base.baselist.BaseViewHolder
import com.hn.library.base.baselist.CommRecyclerAdapter
import com.hn.library.global.HnUrl
import com.hn.library.http.HnResponseHandler
import com.hn.library.utils.HnRefreshDirection
import com.hn.library.utils.HnToastUtils
import com.hn.library.view.FrescoImageView
import com.live.shoplib.R
import com.live.shoplib.bean.VisHistoryModel
import com.loopj.android.http.RequestParams
import com.reslibrarytwo.CommListActivity
import java.util.*

/**
 * 访问记录
 */
class HistoryRecordListAct : CommListActivity() {

    private var mAdapter: CommRecyclerAdapter? = null

    private val mData = ArrayList<VisHistoryModel.DBean.UsersBean.ItemsBean>()

    override fun setTitle(): String = intent.getStringExtra("title")

    override fun setRequestUrl(): String = HnUrl.VIS_HISTORY

    override fun setAdapter(): CommRecyclerAdapter {
        mAdapter = object : CommRecyclerAdapter() {
            override fun onBindView(holder: BaseViewHolder, position: Int) {
                var mIvIco = holder.getView<FrescoImageView>(R.id.mIvIco)
                mIvIco.setImageURI(HnUrl.setImageUri(mData[position].user_avatar))
                holder.setTextViewText(R.id.mTvContent, mData[position].user_nickname)
            }

            override fun getLayoutID(position: Int): Int {
                return R.layout.item_his_record
            }

            override fun getItemCount(): Int {
                return mData.size
            }
        }
        return mAdapter as CommRecyclerAdapter
    }

    override fun setRequestParam(): RequestParams {
        val params = RequestParams()
        params.put("type",intent.getStringExtra("type"))
        return params
    }

    override fun setResponseHandler(state: HnRefreshDirection?): HnResponseHandler<*> {
        return object : HnResponseHandler<VisHistoryModel>(VisHistoryModel::class.java) {
            override fun hnSuccess(response: String) {
                if (isFinishing) return
                refreshFinish()
                if (model.d.users.items == null) {
                    setEmpty("还没有客户，快去开播吧！", R.drawable.empty_com)
                    return
                }
                if (HnRefreshDirection.TOP == state) {
                    mData.clear()
                }
                mData.addAll(model.d.users.items)
                if (mAdapter != null)
                    mAdapter!!.notifyDataSetChanged()
                setEmpty("还没有客户，快去开播吧！", R.drawable.empty_com)
            }

            override fun hnErr(errCode: Int, msg: String) {
                if (isFinishing) return
                refreshFinish()
                HnToastUtils.showToastShort(msg)
                setEmpty("还没有客户，快去开播吧！", R.drawable.empty_com)
            }
        }
    }

}