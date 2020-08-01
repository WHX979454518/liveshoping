package com.live.shoplib.ui.frag

import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import androidx.recyclerview.widget.GridLayoutManager
import com.hn.library.base.BaseFragment
import com.hn.library.base.BaseRequestStateListener
import com.hn.library.base.baselist.BaseViewHolder
import com.hn.library.base.baselist.CommRecyclerAdapter
import com.hn.library.global.HnUrl
import com.hn.library.http.HnHttpUtils
import com.hn.library.http.HnResponseHandler
import com.hn.library.picker.address_picker.StoreGroupModel
import com.hn.library.picker.address_picker.StringOneDialog
import com.hn.library.utils.HnToastUtils
import com.hn.library.view.FrescoImageView
import com.live.shoplib.R
import com.live.shoplib.bean.GoodsAddModel
import com.live.shoplib.other.GoodsEditInterface
import com.live.shoplib.other.HnAnchorAuthenticationBiz
import com.loopj.android.http.RequestParams
import kotlinx.android.synthetic.main.frag_goods_edit_step1.*


/**
 * 商品编辑step1
 */
class GoodsEditStep1Frag : BaseFragment() {

    var nameStr = ""
    var idStr = ""
    var wareNumStr = ""
    var warnNumStr = ""
    var column_id = ""
    var goodsImg = ""
    var h5Url = ""
    var groupTypeStr = ""
    var detailsImgs: ArrayList<String> = ArrayList()
    var bean: GoodsAddModel.DBean? = null
    var mAdapter: CommRecyclerAdapter? = null
    var listener: GoodsEditInterface? = null

    companion object {
        fun launch(store_id: String): GoodsEditStep1Frag {
            var frag = GoodsEditStep1Frag()
            var bundle = Bundle()
            bundle.putString("store_id", store_id)
            frag.arguments = bundle
            return frag
        }
    }

    fun setLisenter(i: GoodsEditInterface) {
        listener = i
    }

    override fun getContentViewId() = R.layout.frag_goods_edit_step1

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
        mImgsRecycler.adapter = mAdapter
        mImgsRecycler.layoutManager = GridLayoutManager(activity, 4)

        mEdName.setText(nameStr)
        mEdGoodsId.setText(idStr)
        mEdWare.setText(wareNumStr)
        mEdWarningNum.setText(warnNumStr)
        mTvGroupType.setText(groupTypeStr)
        if (TextUtils.isEmpty(goodsImg)) {
            mIvGoodsPicDel.visibility = GONE
        } else {
            mIvGoodsPicDel.visibility = VISIBLE
            mIvGoodsPic.setImageURI(HnUrl.setImageUri(goodsImg))
        }
        mAdapter!!.notifyDataSetChanged()

        var temp = true

        for (i in 0 until detailsImgs.size) {
            if (!TextUtils.isEmpty(detailsImgs[i])) temp = false
        }

        //H5
        if (!TextUtils.isEmpty(h5Url) && temp) {
            mWebView.visibility = VISIBLE
            if (h5Url.contains("<")) {
                mWebView.scrollBarStyle
                mWebView.loadDataWithBaseURL(null, h5Url, "text/html", "utf-8", null)
            } else {
                mWebView.loadUrl(h5Url)
            }
        } else {
            mWebView.visibility = GONE
        }
    }


    fun initClick() {
        //商品分类
        mTvGroupType.setOnClickListener {
            requestList()
        }
        //商品图片
        mIvGoodsPic.setOnClickListener {
            imgLister(-1)
        }
        //删除
        mIvGoodsPicDel.setOnClickListener {
            goodsImg = ""
            mIvGoodsPic.setImageURI(goodsImg)
            mIvGoodsPicDel.visibility = GONE
        }
        //商品名检测
        checkEdLister(mEdName, false, object : checkEdLister.setOnNext {
            override fun check() {
                checkNext()
            }
        }).set()
        //商品货号检测
        checkEdLister(mEdGoodsId, false, object : checkEdLister.setOnNext {
            override fun check() {
                checkNext()
            }
        }).set()
        //商品库存检测
        checkEdLister(mEdWare, false, object : checkEdLister.setOnNext {
            override fun check() {
                checkNext()
            }
        }).set()
        //库存预警值检测
        checkEdLister(mEdWarningNum, true, object : checkEdLister.setOnNext {
            override fun check() {
                checkNext()
            }
        }).set()
    }

    //检测
    fun checkNext() {
        if (listener != null) listener!!.onNextCheck(checkCanNext(false))
    }

    //初始化设置
    fun setInitData(name: String, goodsId: String, wareNum: String, warningNum: String, h5Urlx: String,
                    groupId: String, goodsImgx: String, imgs: ArrayList<String>) {

        nameStr = name
        h5Url = h5Urlx
        idStr = goodsId
        wareNumStr = wareNum
        warnNumStr = warningNum
        column_id = groupId
        goodsImg = goodsImgx
        //替换
        detailsImgs!!.clear()
        for (i in 0..imgs.size - 1) {
            detailsImgs!!.add(imgs[i])
        }
        if (detailsImgs.size < 8) {
            detailsImgs.add("")
        }
        requestList(column_id)
    }

    //获取商品名
    fun getGoodsName() = if (mEdName == null) "" else mEdName.text.toString()

    //获取商品id
    fun getGoodsId() = if (mEdGoodsId == null) "" else mEdGoodsId.text.toString()

    //获取库存数
    fun getWareNum() = if (mEdWare == null) "" else mEdWare.text.toString()

    //获取警告数
    fun getWarningNum() = if (mEdWarningNum == null) "" else mEdWarningNum.text.toString()

    //获取分组
    fun getGroupTypeId() = column_id

    //获取商品图片
    fun getGoodsPic() = goodsImg

    //获取商品详情图片
    fun getGoodsDetailsPics(): String {
        return TextUtils.join(",", detailsImgs).substring(0, TextUtils.join(",", detailsImgs).length - 1)
    }

    //检测是否可以点击下一步
    fun checkCanNext(show: Boolean): Boolean {
        if (TextUtils.isEmpty(getGoodsName())) {
            if (show) HnToastUtils.showToastShort("请输入商品名称")
            return false
        }
        if (TextUtils.isEmpty(getGoodsId())) {
            if (show) HnToastUtils.showToastShort("请输入商品货号")
            return false
        }
        if (TextUtils.isEmpty(getWareNum())) {
            if (show) HnToastUtils.showToastShort("请输入商品库存")
            return false
        }
        if (TextUtils.isEmpty(getWarningNum())) {
            if (show) HnToastUtils.showToastShort("请输入库存预警值")
            return false
        }
        if (TextUtils.isEmpty(getGroupTypeId())) {
            if (show) HnToastUtils.showToastShort("请选择商品所属栏目")
            return false
        }
        if (TextUtils.isEmpty(getGoodsPic())) {
            if (show) HnToastUtils.showToastShort("请选择商品图片")
            return false
        }
        var i = 0
        detailsImgs!!.forEach {
            if (!TextUtils.isEmpty(it)) {
                i++
            }
        }
        if (i == 0) {
            if (show) HnToastUtils.showToastShort("请选择商品详情图片")
            return false
        }
        return true
    }

    //检测监听-内部类
    class checkEdLister(var editText: EditText, var filter: Boolean, var next: setOnNext) {

        fun set() {
            editText.addTextChangedListener(mTextWatcher)
        }

        var mTextWatcher: TextWatcher = object : TextWatcher {
            private var temp: CharSequence? = null

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                temp = s
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

            override fun afterTextChanged(s: Editable) {
                try {
                    if (!TextUtils.isEmpty(editText.text.toString())) {
                        if (filter && editText.text.toString().toInt() > 255) {
                            editText.setText("255")
                        }
                    }
                } catch (e: Exception) {
                    editText.setText(s)
                }
                next.check()
            }
        }

        interface setOnNext {
            fun check()
        }

    }

    //图片设置
    fun imgLister(pos: Int) {
        var biz = HnAnchorAuthenticationBiz(mActivity)
        biz.setLoginListener(object : BaseRequestStateListener {
            override fun requesting() {
            }

            override fun requestSuccess(type: String?, response: String?, obj: Any?) {
                HnToastUtils.showToastShort("上传成功")
                if (pos == -1) {
                    goodsImg = response!!
                    mIvGoodsPic.setImageURI(HnUrl.setImageUri(response))
                    mIvGoodsPicDel.visibility = VISIBLE
                } else {
                    detailsImgs!![pos] = response!!
                    if (detailsImgs.size < 8) {
                        detailsImgs.add("")
                    }
                    mAdapter!!.notifyDataSetChanged()
                }
                checkNext()
            }

            override fun requestFail(type: String?, code: Int, msg: String?) {
                HnToastUtils.showToastShort("上传失败")
                if (pos == -1) {
                    goodsImg = ""
                    mIvGoodsPicDel.visibility = GONE
                } else {
                    detailsImgs!![pos] = ""
                }
                checkNext()
            }
        })
        biz.uploadPicFile(pos.toString(), false)
    }

    //栏目列表
    private fun requestList() {
        val param = RequestParams()
        HnHttpUtils.postRequest(HnUrl.STORE_GROUP_LIST, param, "商品栏目列表", object : HnResponseHandler<StoreGroupModel>(StoreGroupModel::class.java) {
            override fun hnSuccess(response: String) {

                if (model.d.items.size == 0) {
                    HnToastUtils.showToastShort("你还没有设置栏目，请先添加。")
                    return
                }

                val dialog = StringOneDialog(activity, model.d.items, StringOneDialog.onCityPickedListener { bean ->
                    if (mTvGroupType != null) {
                        column_id = bean.id
                        mTvGroupType.setText(bean.name)
                        checkNext()
                    }
                })
                dialog.show()
            }

            override fun hnErr(errCode: Int, msg: String) {
                HnToastUtils.showToastShort(msg)
            }
        })
    }

    private fun requestList(id: String) {
        val param = RequestParams()
        HnHttpUtils.postRequest(HnUrl.STORE_GROUP_LIST, param, "商品栏目列表", object : HnResponseHandler<StoreGroupModel>(StoreGroupModel::class.java) {
            override fun hnSuccess(response: String) {
                for (i in 0 until model.d.items.size) {
                    if (model.d.items[i].id.equals(id)) {
                        groupTypeStr = model.d.items[i].name
                        if (mTvGroupType != null) mTvGroupType.setText(groupTypeStr)
                        break
                    }
                }
            }

            override fun hnErr(errCode: Int, msg: String) {
                HnToastUtils.showToastShort(msg)
            }
        })
    }

}