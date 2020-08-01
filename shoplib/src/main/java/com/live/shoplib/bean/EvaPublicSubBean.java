package com.live.shoplib.bean;

/**
 * 作者：Mr.Xu
 * 时间：2018/1/9
 */
public class EvaPublicSubBean {
    public EvaPublicSubBean(String goods_id, String content, String img, String grade, String goods_attr) {
        this.goods_id = goods_id;
        this.content = content;
        this.img = img;
        this.grade = grade;
        this.goods_attr = goods_attr;
    }

    /**
     * goods_id : 100090
     * content : 我的评论
     * img : 1,2,3
     * grade : 5
     * goods_attr : 内存:2G 颜色:红色
     */

    private String goods_id;
    private String content;
    private String img;
    private String grade;
    private String goods_attr;
}
