package com.hotniao.live.model;

/**
 * 作者：Mr.Xu
 * 时间：2018/2/7
 */
public class HnLiveNoticeEvent {

    private String title ;
    private String content ;


    public HnLiveNoticeEvent(String title,String content) {
        this.title = title;
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
