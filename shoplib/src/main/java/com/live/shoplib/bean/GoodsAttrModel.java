package com.live.shoplib.bean;

import com.hn.library.http.BaseResponseModel;

import java.util.List;

/**
 * 作者：Mr.Xu
 * 时间：2018/1/2
 */
public class GoodsAttrModel extends BaseResponseModel{


    /**
     * d : {"goods_id":"100120","goods_img":"http://dev.static.fireniao.com/image/20171226/1514256203820352.png","goods_name":"衣服1","goods_price":"1.00","sku":[{"spec_ids":["372","375","377"],"ids":"372:375:377","price":"1.00","sku_id":"25","spec_text":"颜色:白色;尺寸:大;舒适:舒服","stock":"100"},{"spec_ids":["372","375","377"],"ids":"372:376:377","price":"1.00","sku_id":"27","spec_text":"颜色:白色;尺寸:小;舒适:舒服","stock":"100"},{"spec_ids":["372","375","377"],"ids":"372:376:378","price":"1.00","sku_id":"28","spec_text":"颜色:白色;尺寸:小;舒适:好舒服","stock":"100"},{"spec_ids":["372","375","377"],"ids":"373:375:377","price":"1.00","sku_id":"29","spec_text":"颜色:红色;尺寸:大;舒适:舒服","stock":"100"},{"spec_ids":["372","375","377"],"ids":"373:375:378","price":"1.00","sku_id":"30","spec_text":"颜色:红色;尺寸:大;舒适:好舒服","stock":"100"},{"spec_ids":["372","375","377"],"ids":"373:376:377","price":"1.00","sku_id":"31","spec_text":"颜色:红色;尺寸:小;舒适:舒服","stock":"100"},{"spec_ids":["372","375","377"],"ids":"374:375:377","price":"1.00","sku_id":"33","spec_text":"颜色:绿色;尺寸:大;舒适:舒服","stock":"100"},{"spec_ids":["372","375","377"],"ids":"374:375:378","price":"1.00","sku_id":"34","spec_text":"颜色:绿色;尺寸:大;舒适:好舒服","stock":"100"},{"spec_ids":["372","375","377"],"ids":"374:376:377","price":"1.00","sku_id":"35","spec_text":"颜色:绿色;尺寸:小;舒适:舒服","stock":"100"},{"spec_ids":["372","375","377"],"ids":"374:376:378","price":"1.00","sku_id":"36","spec_text":"颜色:绿色;尺寸:小;舒适:好舒服","stock":"100"}],"spec":[{"list":[{"id":"372","is_select":"N","spec_image":"","spec_value":"白色"}],"group":"颜色"},{"list":[{"id":"373","is_select":"N","spec_image":"","spec_value":"红色"}],"group":"尺寸"},{"list":[{"id":"374","is_select":"N","spec_image":"","spec_value":"绿色"}],"group":"舒适"}]}
     */

    private GoodsAttrBean d;

    public void setD(GoodsAttrBean d) {
        this.d = d;
    }

    public GoodsAttrBean getD() {
        return d;
    }

}
