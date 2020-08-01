package com.live.shoplib.ui

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.google.gson.Gson
import com.hn.library.base.BaseActivity
import com.hn.library.base.BaseFragment
import com.hn.library.global.HnUrl
import com.hn.library.http.BaseResponseModel
import com.hn.library.http.HnHttpUtils
import com.hn.library.http.HnResponseHandler
import com.hn.library.utils.HnLogUtils
import com.hn.library.utils.HnToastUtils
import com.live.shoplib.R
import com.live.shoplib.bean.GoodSortOptionModel
import com.live.shoplib.bean.GoodsAddModel
import com.live.shoplib.other.GoodsEditInterface
import com.live.shoplib.ui.frag.*
import kotlinx.android.synthetic.main.act_goods_edit.*
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

/**
 * 添加商品
 */
class GoodsEditAct : BaseActivity(), GoodsEditInterface {

    //AddStoreInitModel
    override fun getContentViewId(): Int = R.layout.act_goods_edit

    var isEdit = false

    var mFragments: ArrayList<BaseFragment>? = null

    var mFragmentStep1: GoodsEditStep1Frag? = null
    var mFragmentStep2: GoodsEditStep2Frag? = null
    var mFragmentStep3: GoodsEditStep3Frag? = null
    var mFragmentStep4: GoodsEditStep4Frag? = null
    var mFragmentStep5: GoodsEditStep5Frag? = null

    var column_id = ""//1
    var goods_attr = ""//2
    var goods_detail_imgs = ""//3
    var goods_img = ""//4
    var goods_imgs = ""//5
    var goods_name = ""//6
    var goods_promise = ""//7
    var goods_sn = ""//8
    var goods_stock = ""//9
    var goods_stock_notice = ""//10
    var sku = ""//11
    var skuEdit = ""
    var spec = ""//12
    var store_goods_category_id = ""//13

    var curIndex = 0

    var bean: GoodsAddModel.DBean? = null
    var mAdapter: CommFragmentAdapter? = null

    var skuObj = ""

    //第三步
    var mDataSpec: ArrayList<GoodSortOptionModel.DBean.SpecBean>? = null


    companion object {
        fun launch(context: Context, store_id: String) {
            context.startActivity(Intent(context, GoodsEditAct::class.java).putExtra("store_id", store_id))
        }

        fun launch(context: Context, store_id: String, bean: GoodsAddModel.DBean) {
            context.startActivity(Intent(context, GoodsEditAct::class.java).putExtra("store_id", store_id).putExtra("bean", bean))
        }
    }

    override fun onCreateNew(savedInstanceState: Bundle?) {
        setShowBack(true)
        setTitle("添加商品")

        mFragments = ArrayList<BaseFragment>()
        mFragmentStep1 = GoodsEditStep1Frag.launch(intent.getStringExtra("store_id"))
        mFragmentStep1!!.setLisenter(this)

        mFragmentStep2 = GoodsEditStep2Frag.launch(intent.getStringExtra("store_id"))
        mFragmentStep2!!.setLisenter(this)

        mFragmentStep3 = GoodsEditStep3Frag.launch(intent.getStringExtra("store_id"))
        mFragmentStep3!!.setLisenter(this)

        mFragmentStep4 = GoodsEditStep4Frag.launch(intent.getStringExtra("store_id"))
        mFragmentStep4!!.setLisenter(this)

        mFragmentStep5 = GoodsEditStep5Frag.launch(intent.getStringExtra("store_id"))
        mFragmentStep5!!.setLisenter(this)

        mFragments!!.add(mFragmentStep1!!)
        mFragments!!.add(mFragmentStep2!!)
        mFragments!!.add(mFragmentStep3!!)
        mFragments!!.add(mFragmentStep4!!)
        mFragments!!.add(mFragmentStep5!!)
        mStepViewPager.isEnabled = false
        mStepViewPager.offscreenPageLimit = mFragments!!.size
        mAdapter = CommFragmentAdapter(supportFragmentManager, mFragments)

        mStepViewPager.adapter = mAdapter

        try {
            bean = intent.getSerializableExtra("bean") as GoodsAddModel.DBean
            HnLogUtils.e("##############", bean.toString())
        } catch (e: Exception) {
        }
        isEdit = false
        if (bean != null) {
            setTitle("编辑商品")
            isEdit = true
            //第一步
            var imgs = bean!!.goods!!.goods_detail_imgs.split(",")
            var detailsImgs = ArrayList<String>()
            imgs.forEach {
                detailsImgs.add(it)
            }
            mFragmentStep1!!.setInitData(
                    bean!!.goods!!.goods_name,
                    bean!!.goods!!.goods_sn,
                    bean!!.goods!!.goods_stock,
                    bean!!.goods!!.goods_stock_notice,
                    bean!!.goods!!.goods_detail,
                    bean!!.goods!!.column_id,
                    bean!!.goods!!.goods_img,
                    detailsImgs
            )
            //第二步
            var goods = bean!!.goods!!.goods_imgs.split(",")
            var goodsImgs = ArrayList<String>()
            goods.forEach {
                goodsImgs.add(it)
            }
            mFragmentStep2!!.setInitData(goodsImgs)
            //第三步
            val specObj = JSONObject(Gson().toJsonTree(bean!!.spec).asJsonObject.toString())


            mFragmentStep3!!.setInitData(isEdit,
                    bean!!.goods.store_goods_category_name,
                    Gson().toJsonTree(bean!!.spec).asJsonObject.toString(),
                    Gson().toJsonTree(bean!!.goods.goods_attr).asJsonObject.toString(),
                    specObj)

            //第四步
            skuObj = Gson().toJsonTree(bean!!.sku).asJsonObject.toString()
            //第五步
            mFragmentStep5!!.setInitData(bean!!.goods.goods_promise)

            setNextStyle(true)

        }
    }

    override fun getInitData() {
        //回退
        mBack.setOnClickListener {
            mTvNext.setText("下一步")
            when (curIndex) {
                0 -> finish()
                1 -> {
                    curIndex = 0
                    switchItem(0)
                    mStepViewPager.setCurrentItem(0)
                    mFragmentStep1!!.checkNext()
                }
                2 -> {
                    curIndex = 1
                    switchItem(1)
                    mStepViewPager.setCurrentItem(1)
                    mFragmentStep2!!.checkNext()
                }
                3 -> {
                    curIndex = 2
                    switchItem(2)
                    mStepViewPager.setCurrentItem(2)
                    mFragmentStep3!!.checkNext(isEdit)
                }
                4 -> {
                    curIndex = 3
                    switchItem(3)
                    mStepViewPager.setCurrentItem(3)
                    mFragmentStep4!!.checkNext()
                }
            }
        }
        //下一步
        mTvNext.setOnClickListener {
            mTvNext.setText("下一步")
            when (curIndex) {
                0 -> {//界面一跳界面二
                    column_id = mFragmentStep1!!.getGroupTypeId()//1
                    goods_detail_imgs = mFragmentStep1!!.getGoodsDetailsPics()//3
                    goods_img = mFragmentStep1!!.getGoodsPic()//4
                    goods_name = mFragmentStep1!!.getGoodsName()//6
                    goods_sn = mFragmentStep1!!.getGoodsId()//8
                    goods_stock = mFragmentStep1!!.getWareNum()//9
                    goods_stock_notice = mFragmentStep1!!.getWarningNum()//10

                    curIndex = 1
                    switchItem(1)
                    mStepViewPager.setCurrentItem(1)
                    mFragmentStep2!!.checkNext()
                }
                1 -> {//界面二跳界面三
                    goods_imgs = mFragmentStep2!!.getGoodsPics()//5
                    curIndex = 2
                    switchItem(2)
                    mStepViewPager.setCurrentItem(2)
                    mFragmentStep3!!.checkNext(isEdit)
                }
                2 -> {//界面三跳界面四
                    goods_attr = mFragmentStep3!!.getAttr()//2
                    store_goods_category_id = mFragmentStep3!!.getStoreGoodsCategoryId()//13
                    mDataSpec = mFragmentStep3!!.getSpecData()

                    curIndex = 3
                    switchItem(3)
                    mStepViewPager.setCurrentItem(3)

                    if (!TextUtils.isEmpty(skuObj)) {
                        mFragmentStep4!!.setInitData(this!!.mDataSpec!!, skuObj)
                    } else {
                        mFragmentStep4!!.setInitData(this!!.mDataSpec!!)
                    }
                    mFragmentStep3!!.checkNext(isEdit)
                }
                3 -> {//界面四跳界面五
                    sku = mFragmentStep4!!.getSku()//11
                    if (isEdit) skuEdit = mFragmentStep4!!.getSkuEdit()
                    spec = mFragmentStep4!!.getSpec()//12

                    curIndex = 4
                    switchItem(4)
                    mStepViewPager.setCurrentItem(4)
                    mTvNext.setText("确认")
                    mFragmentStep4!!.checkNext()
                }
                4 -> {
                    goods_promise = mFragmentStep5!!.getPromise()//7
                    //完成
                    if (isEdit) {
                        requestEdit()
                    } else {
                        requestAdd()
                    }
                    mFragmentStep5!!.checkNext()
                }
            }
        }
    }

    class CommFragmentAdapter(fm: FragmentManager, val mFragment: List<BaseFragment>?) : FragmentPagerAdapter(fm) {

        private var isCanScroll = false

        override fun getItem(position: Int): Fragment {
            return mFragment!![position]
        }

        override fun getCount(): Int {
            return mFragment?.size ?: 0
        }

        /**
         * 设置其是否能滑动换页
         * @param isCanScroll false 不能换页， true 可以滑动换页
         */
        fun setScanScroll(isCanScroll: Boolean) {
            this.isCanScroll = isCanScroll
        }


    }

    @SuppressLint("ResourceAsColor")
    override fun onNextCheck(next: Boolean) {
        HnLogUtils.e("########", ""+next)
        setNextStyle(next)
    }

    fun setNextStyle(next: Boolean) {
        if (next) {
            mTvNext.isEnabled = true
            mTvNext.setBackgroundColor(resources.getColor(R.color.main_color))
        } else {
            mTvNext.isEnabled = false
            mTvNext.setBackgroundColor(resources.getColor(R.color.comm_text_color_black_s))
        }
    }


    fun switchItem(index: Int) {
        when (index) {
            0 -> {
                mTv2.setBackgroundResource(com.reslibrarytwo.R.drawable.shap_cricle_gray)
                mTv3.setBackgroundResource(com.reslibrarytwo.R.drawable.shap_cricle_gray)
                mTv4.setBackgroundResource(com.reslibrarytwo.R.drawable.shap_cricle_gray)
                mTv5.setBackgroundResource(com.reslibrarytwo.R.drawable.shap_cricle_gray)
                mView2.setBackgroundResource(com.reslibrarytwo.R.color.shop_edit)
                mView3.setBackgroundResource(com.reslibrarytwo.R.color.shop_edit)
                mView4.setBackgroundResource(com.reslibrarytwo.R.color.shop_edit)
                mView5.setBackgroundResource(com.reslibrarytwo.R.color.shop_edit)
            }
            1 -> {
                mTv2.setBackgroundResource(com.reslibrarytwo.R.drawable.shap_cricle)
                mTv3.setBackgroundResource(com.reslibrarytwo.R.drawable.shap_cricle_gray)
                mTv4.setBackgroundResource(com.reslibrarytwo.R.drawable.shap_cricle_gray)
                mTv5.setBackgroundResource(com.reslibrarytwo.R.drawable.shap_cricle_gray)
                mView2.setBackgroundResource(com.reslibrarytwo.R.color.main_color)
                mView3.setBackgroundResource(com.reslibrarytwo.R.color.shop_edit)
                mView4.setBackgroundResource(com.reslibrarytwo.R.color.shop_edit)
                mView5.setBackgroundResource(com.reslibrarytwo.R.color.shop_edit)
            }
            2 -> {
                mTv2.setBackgroundResource(com.reslibrarytwo.R.drawable.shap_cricle)
                mTv3.setBackgroundResource(com.reslibrarytwo.R.drawable.shap_cricle)
                mTv4.setBackgroundResource(com.reslibrarytwo.R.drawable.shap_cricle_gray)
                mTv5.setBackgroundResource(com.reslibrarytwo.R.drawable.shap_cricle_gray)
                mView2.setBackgroundResource(com.reslibrarytwo.R.color.main_color)
                mView3.setBackgroundResource(com.reslibrarytwo.R.color.main_color)
                mView4.setBackgroundResource(com.reslibrarytwo.R.color.shop_edit)
                mView5.setBackgroundResource(com.reslibrarytwo.R.color.shop_edit)
            }
            3 -> {
                mTv2.setBackgroundResource(com.reslibrarytwo.R.drawable.shap_cricle)
                mTv3.setBackgroundResource(com.reslibrarytwo.R.drawable.shap_cricle)
                mTv4.setBackgroundResource(com.reslibrarytwo.R.drawable.shap_cricle)
                mTv5.setBackgroundResource(com.reslibrarytwo.R.drawable.shap_cricle_gray)
                mView2.setBackgroundResource(com.reslibrarytwo.R.color.main_color)
                mView3.setBackgroundResource(com.reslibrarytwo.R.color.main_color)
                mView4.setBackgroundResource(com.reslibrarytwo.R.color.main_color)
                mView5.setBackgroundResource(com.reslibrarytwo.R.color.shop_edit)
            }
            4 -> {
                mTv2.setBackgroundResource(com.reslibrarytwo.R.drawable.shap_cricle)
                mTv3.setBackgroundResource(com.reslibrarytwo.R.drawable.shap_cricle)
                mTv4.setBackgroundResource(com.reslibrarytwo.R.drawable.shap_cricle)
                mTv5.setBackgroundResource(com.reslibrarytwo.R.drawable.shap_cricle)
                mView2.setBackgroundResource(com.reslibrarytwo.R.color.main_color)
                mView3.setBackgroundResource(com.reslibrarytwo.R.color.main_color)
                mView4.setBackgroundResource(com.reslibrarytwo.R.color.main_color)
                mView5.setBackgroundResource(com.reslibrarytwo.R.color.main_color)
            }
        }
    }

    fun requestAdd() {
        val jsonObject = JSONObject()
        val json_attr = JSONObject(goods_attr)
        val json_spec = JSONObject(spec)
        val json_sku = JSONObject(sku)
        val json_promise = JSONArray(goods_promise)
        try {
            jsonObject.put("column_id", column_id)
            jsonObject.put("goods_attr", json_attr)
            jsonObject.put("goods_detail_imgs", goods_detail_imgs)
            jsonObject.put("goods_img", goods_img)
            jsonObject.put("goods_imgs", goods_imgs)
            jsonObject.put("goods_name", goods_name)
            jsonObject.put("goods_promise", json_promise)
            jsonObject.put("goods_sn", goods_sn)
            jsonObject.put("goods_stock", goods_stock)
            jsonObject.put("goods_stock_notice", goods_stock_notice)
            jsonObject.put("spec", json_spec)
            jsonObject.put("sku", json_sku)
            jsonObject.put("store_goods_category_id", store_goods_category_id)
        } catch (e: JSONException) {
            e.printStackTrace()
            HnLogUtils.e("##########################", "" + e.message)
        }
        HnHttpUtils.postRequestJsonStr(this@GoodsEditAct, HnUrl.STORE_ADD_INIT, jsonObject.toString(), "商品添加", object : HnResponseHandler<BaseResponseModel>(BaseResponseModel::class.java) {
            override fun hnSuccess(response: String) {
                HnToastUtils.showToastShort("添加成功")
                finish()
            }

            override fun hnErr(errCode: Int, msg: String) {
                HnToastUtils.showToastShort(msg)
            }
        })
    }

    fun requestEdit() {
        val jsonObject = JSONObject()
        val goodsObj = JSONObject()
        val json_attr = JSONObject(goods_attr)
        val json_sku = JSONObject(skuEdit)
        val json_promise = JSONArray(goods_promise)
        try {
            goodsObj.put("goods_id", intent.getStringExtra("store_id"))//
            goodsObj.put("column_id", column_id)//
            goodsObj.put("goods_attr", json_attr)//
            goodsObj.put("goods_detail_imgs", goods_detail_imgs)//
            goodsObj.put("goods_img", goods_img)//
            goodsObj.put("goods_imgs", goods_imgs)//
            goodsObj.put("goods_name", goods_name)//
            goodsObj.put("goods_promise", json_promise)//
            goodsObj.put("goods_sn", goods_sn)//
            goodsObj.put("goods_stock", goods_stock)//
            goodsObj.put("goods_stock_notice", goods_stock_notice)//
            jsonObject.put("sku", json_sku)
            jsonObject.put("goods", goodsObj)
        } catch (e: JSONException) {
            e.printStackTrace()
            HnLogUtils.e("##########################", "" + e.message)
        }
        HnHttpUtils.postRequestJsonStr(this@GoodsEditAct, HnUrl.STORE_EDIT_INIT, jsonObject.toString(), "商品编辑", object : HnResponseHandler<BaseResponseModel>(BaseResponseModel::class.java) {
            override fun hnSuccess(response: String) {
                HnToastUtils.showToastShort("编辑成功")
                finish()
            }

            override fun hnErr(errCode: Int, msg: String) {
                HnToastUtils.showToastShort(msg)
            }
        })
    }

}