package com.live.shoplib.other;

import android.text.TextUtils;

import com.live.shoplib.bean.GoodsAttrBean;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：Mr.Xu
 * 时间：2017/12/27
 */
public class AttrCalculate {
    //基础数据
    private GoodsAttrBean mData;
    private List<GoodsAttrBean.SkuEntity> mSkuDate;
    private List<GoodsAttrBean.SpecEntity> mSpecDate;

    private String[] mUserSelList;

    //多少组数就多少组
    private int groupNum = 0;
    private List<List<List<String>>> mGroupMatchList = new ArrayList<>();

    /**
     * 初始化数组
     * @param groupNum
     */
    public AttrCalculate(int groupNum,GoodsAttrBean mData) {
        this.groupNum = groupNum;
        for (int i = 0; i < groupNum; i++) {
            List<List<String>> groupMatchs = new ArrayList<>();
            mGroupMatchList.add(groupMatchs);
        }
        mUserSelList = new String[groupNum];
        this.mData = mData;
        mSkuDate = mData.getSku();
        mSpecDate = mData.getSpec();
    }


    private void clickItem(int verIndex,int horIndx){
        //赋值
        mUserSelList[verIndex] = mData.getSpec().get(verIndex).getList().get(horIndx).getId();

        for (int i = 0; i < mSkuDate.size(); i++) {//遍历{[1.4.7],[1.4.8]}
            for (int j = 0; j < mSkuDate.get(i).getSpec_ids().size(); j++) {//遍历[1.4.7]
                if(TextUtils.equals(mUserSelList[verIndex],mSkuDate.get(i).getSpec_ids().get(j))){//存储当前组的所有情况
                    mGroupMatchList.get(verIndex).add(mSkuDate.get(i).getSpec_ids());
                }
            }
        }

        for (int i = 0; i < mGroupMatchList.get(verIndex).size(); i++) {
            for (int j = 0; j < mGroupMatchList.get(verIndex).get(i).size(); j++) {

            }
        }

    }







}
