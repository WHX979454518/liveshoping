package com.live.shoplib.ui.frag

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.GridLayoutManager
import com.hn.library.base.BaseFragment
import com.hn.library.base.BaseRequestStateListener
import com.hn.library.base.baselist.BaseViewHolder
import com.hn.library.base.baselist.CommRecyclerAdapter
import com.hn.library.global.HnUrl
import com.hn.library.utils.HnToastUtils
import com.hn.library.view.FrescoImageView
import com.live.shoplib.R
import com.live.shoplib.bean.GoodsAddModel
import com.live.shoplib.other.GoodsEditInterface
import com.live.shoplib.other.HnAnchorAuthenticationBiz
import kotlinx.android.synthetic.main.frag_goods_edit_step2.*


/**
 * 商品编辑step2
 */
class GoodsEditStep2Frag : BaseFragment() {


    var detailsImgs: ArrayList<String> = ArrayList()
    var bean: GoodsAddModel.DBean? = null
    var mAdapter: CommRecyclerAdapter? = null
    var listener: GoodsEditInterface? = null

    companion object {
        fun launch(store_id: String): GoodsEditStep2Frag {
            var frag = GoodsEditStep2Frag()
            var bundle = Bundle()
            bundle.putString("store_id", store_id)
            frag.arguments = bundle
            return frag
        }
    }

    fun setLisenter(i: GoodsEditInterface) {
        listener = i
    }

    override fun getContentViewId() = R.layout.frag_goods_edit_step2

    override fun initView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?) {
        if (detailsImgs.size == 0) detailsImgs!!.add("")
    }

    override fun initData() {
        initClick()
        mAdapter = object : CommRecyclerAdapter() {
            override fun onBindView(holder: BaseViewHolder?, position: Int) {
                var img = holder!!.getView<FrescoImageView>(R.id.mIvPic)
                var del = holder!!.getView<ImageView>(R.id.mIvPicDel)

                try {
                    if (TextUtils.isEmpty(detailsImgs!![position - 1])) {
                        if (!TextUtils.isEmpty(detailsImgs!![position])) {
                            del.visibility = VISIBLE
                            img.visibility = VISIBLE
                            img.setImageURI(HnUrl.setImageUri(detailsImgs!![position]))
                        } else {
                            img.visibility = GONE
                            del.visibility = GONE
                        }
                    } else {
                        img.visibility = VISIBLE
                        if (!TextUtils.isEmpty(detailsImgs!![position])) {
                            del.visibility = VISIBLE
                            img.setImageURI(HnUrl.setImageUri(detailsImgs!![position]))
                        } else {
                            del.visibility = GONE
                            img.setImageURI("")
                        }
                    }
                } catch (e: Exception) {
                    if (!TextUtils.isEmpty(detailsImgs!![position])) {
                        del.visibility = VISIBLE
                        img.visibility = VISIBLE
                        img.setImageURI(HnUrl.setImageUri(detailsImgs!![position]))
                    } else {
                        img.visibility = VISIBLE
                        img.setImageURI("")
                        del.visibility = GONE
                    }
                }
                //item图片
                img.setOnClickListener {
                    imgLister(position)
                }
                //item删除
                del.setOnClickListener {
                    detailsImgs!!.removeAt(position)
                    detailsImgs!!.add("")
                    notifyDataSetChanged()
                    checkNext()
                }
            }

            override fun getItemCount(): Int = detailsImgs.size

            override fun getLayoutID(position: Int): Int = R.layout.item_img
        }
        mImgs2Recycler.adapter = mAdapter
        mImgs2Recycler.layoutManager = GridLayoutManager(activity, 4)
    }


    fun initClick() {

    }

    //检测
    fun checkNext() {
        if (listener != null) listener!!.onNextCheck(checkCanNext(false))
    }

    //初始化设置
    fun setInitData(imgs: ArrayList<String>) {
        //替换
        detailsImgs!!.clear()
        for (i in 0..imgs.size - 1) {
            detailsImgs!!.add(imgs[i])
        }
        if (detailsImgs.size < 8) {
            detailsImgs.add("")
        }

    }

    //获取商品详情图片
    fun getGoodsPics(): String {
        return TextUtils.join(",", detailsImgs).substring(0, TextUtils.join(",", detailsImgs).length - 1)
    }

    //检测是否可以点击下一步
    fun checkCanNext(show: Boolean): Boolean {
        var i = 0
        detailsImgs!!.forEach {
            if (!TextUtils.isEmpty(it)) {
                i++
            }
        }
        if (i == 0) {
            if (show) HnToastUtils.showToastShort("请选择管理商品相册")
            return false
        }
        return true
    }


    //图片设置
    fun imgLister(pos: Int) {
        var biz = HnAnchorAuthenticationBiz(mActivity)
        biz.setLoginListener(object : BaseRequestStateListener {
            override fun requesting() {
            }

            override fun requestSuccess(type: String?, response: String?, obj: Any?) {
                HnToastUtils.showToastShort("上传成功")
                detailsImgs!![pos] = response!!
                if (detailsImgs.size < 8) {
                    detailsImgs.add("")
                }
                mAdapter!!.notifyDataSetChanged()
                checkNext()
            }

            override fun requestFail(type: String?, code: Int, msg: String?) {
                HnToastUtils.showToastShort("上传失败")
                detailsImgs!![pos] = ""
                checkNext()
            }
        })
        biz.uploadPicFile(pos.toString(), false)
    }

}