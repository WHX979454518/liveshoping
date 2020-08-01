package com.live.shoplib.bean;

import com.hn.library.http.BaseResponseModel;

import java.util.List;
import java.util.List;

/**
 * create by Mr.x
 * on 2018/6/29
 */
public class HnShopSortBean extends BaseResponseModel {


    /**
     * d : {"List":[{"two_list":[{"id":"16","name":"分类2_1","pid":"15","three_list":[{"icon":"http://redbird-zbds-1252571077.image.myqcloud.com/image/20180630/1530320975341587.png","id":"17","name":"分类2_1_1","pid":"16"}]}],"id":"15","name":"分类2"},{"two_list":[{"id":"16","name":"分类2_1","pid":"15","three_list":[{"icon":"http://redbird-zbds-1252571077.image.myqcloud.com/image/20180630/1530320975341587.png","id":"17","name":"分类2_1_1","pid":"16"}]}],"id":"18","name":"分类3"}]}
     */

    private DBean d;

    public DBean getD() {
        return d;
    }

    public void setD(DBean d) {
        this.d = d;
    }

    public static class DBean {
        private List<ListBean> list;

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> List) {
            this.list = List;
        }

        public static class ListBean {
            /**
             * two_list : [{"id":"16","name":"分类2_1","pid":"15","three_list":[{"icon":"http://redbird-zbds-1252571077.image.myqcloud.com/image/20180630/1530320975341587.png","id":"17","name":"分类2_1_1","pid":"16"}]}]
             * id : 15
             * name : 分类2
             */

            private String id;
            private String name;
            private boolean check;
            private List<TwoListBean> two_list;

            public boolean isCheck() {
                return check;
            }

            public void setCheck(boolean check) {
                this.check = check;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public List<TwoListBean> getTwo_list() {
                return two_list;
            }

            public void setTwo_list(List<TwoListBean> two_list) {
                this.two_list = two_list;
            }

            public static class TwoListBean {
                /**
                 * id : 16
                 * name : 分类2_1
                 * pid : 15
                 * three_list : [{"icon":"http://redbird-zbds-1252571077.image.myqcloud.com/image/20180630/1530320975341587.png","id":"17","name":"分类2_1_1","pid":"16"}]
                 */

                private String id;
                private String name;
                private String pid;
                private List<ThreeListBean> three_list;

                public String getId() {
                    return id;
                }

                public void setId(String id) {
                    this.id = id;
                }

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public String getPid() {
                    return pid;
                }

                public void setPid(String pid) {
                    this.pid = pid;
                }

                public List<ThreeListBean> getThree_list() {
                    return three_list;
                }

                public void setThree_list(List<ThreeListBean> three_list) {
                    this.three_list = three_list;
                }

                public static class ThreeListBean {
                    /**
                     * icon : http://redbird-zbds-1252571077.image.myqcloud.com/image/20180630/1530320975341587.png
                     * id : 17
                     * name : 分类2_1_1
                     * pid : 16
                     */

                    private String icon;
                    private String id;
                    private String name;
                    private String pid;

                    public String getIcon() {
                        return icon;
                    }

                    public void setIcon(String icon) {
                        this.icon = icon;
                    }

                    public String getId() {
                        return id;
                    }

                    public void setId(String id) {
                        this.id = id;
                    }

                    public String getName() {
                        return name;
                    }

                    public void setName(String name) {
                        this.name = name;
                    }

                    public String getPid() {
                        return pid;
                    }

                    public void setPid(String pid) {
                        this.pid = pid;
                    }
                }
            }
        }
    }
}
