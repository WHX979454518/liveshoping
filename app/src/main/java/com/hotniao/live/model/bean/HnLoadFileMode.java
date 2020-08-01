package com.hotniao.live.model.bean;

/**
 * 作者：Mr.Xu
 * 时间：2017/12/25
 */
public class HnLoadFileMode {

    /**
     * name : 14684020985759.png
     * originalName : 1.png
     * size : 108408
     * state : SUCCESS
     * type : .png
     * url : ./20160713/14684020985759.png
     */

    private String name;
    private String originalName;
    private int    size;
    private String state;
    private String type;
    private String url;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOriginalName() {
        return originalName;
    }

    public void setOriginalName(String originalName) {
        this.originalName = originalName;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
