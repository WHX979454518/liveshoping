package com.live.shoplib.bean;

import com.hn.library.http.BaseResponseModel;

import java.util.List;

/**
 * 作者：Mr.Xu
 * 时间：2017/12/14
 */
public class GoodsDetailsListModel extends BaseResponseModel {


    /**
     * d : {"items":[{"goods_id":"100117","goods_name":"红米5 Plus","goods_img":"http://dev.static.fireniao.com/image/20171226/1514256058917136.jpg","goods_price":"999.00","store_id":"7","spec":[{"group":"颜色","list":[{"id":"368","spec_value":"白色","spec_image":"","is_select":"Y"}]},{"group":"尺寸","list":[{"id":"369","spec_value":"大","spec_image":"","is_select":"N"},{"id":"370","spec_value":"中","spec_image":"","is_select":"Y"}]},{"group":"舒适","list":[{"id":"371","spec_value":"舒服","spec_image":"","is_select":"Y"}]}],"sku":"","spec_sku":""}],"page":1,"pagesize":10,"pagetotal":1,"total":2,"prev":1,"next":1}
     */

    private DEntity d;

    public void setD(DEntity d) {
        this.d = d;
    }

    public DEntity getD() {
        return d;
    }

    public static class DEntity {
        /**
         * items : [{"goods_id":"100117","goods_name":"红米5 Plus","goods_img":"http://dev.static.fireniao.com/image/20171226/1514256058917136.jpg","goods_price":"999.00","store_id":"7","spec":[{"group":"颜色","list":[{"id":"368","spec_value":"白色","spec_image":"","is_select":"Y"}]},{"group":"尺寸","list":[{"id":"369","spec_value":"大","spec_image":"","is_select":"N"},{"id":"370","spec_value":"中","spec_image":"","is_select":"Y"}]},{"group":"舒适","list":[{"id":"371","spec_value":"舒服","spec_image":"","is_select":"Y"}]}],"sku":"","spec_sku":""}]
         * page : 1
         * pagesize : 10
         * pagetotal : 1
         * total : 2
         * prev : 1
         * next : 1
         */

        private int page;
        private int pagesize;
        private int pagetotal;
        private int total;
        private int prev;
        private int next;
        private List<GoodsAttrBean> items;

        public void setPage(int page) {
            this.page = page;
        }

        public void setPagesize(int pagesize) {
            this.pagesize = pagesize;
        }

        public void setPagetotal(int pagetotal) {
            this.pagetotal = pagetotal;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public void setPrev(int prev) {
            this.prev = prev;
        }

        public void setNext(int next) {
            this.next = next;
        }

        public void setItems(List<GoodsAttrBean> items) {
            this.items = items;
        }

        public int getPage() {
            return page;
        }

        public int getPagesize() {
            return pagesize;
        }

        public int getPagetotal() {
            return pagetotal;
        }

        public int getTotal() {
            return total;
        }

        public int getPrev() {
            return prev;
        }

        public int getNext() {
            return next;
        }

        public List<GoodsAttrBean> getItems() {
            return items;
        }
    }
}
