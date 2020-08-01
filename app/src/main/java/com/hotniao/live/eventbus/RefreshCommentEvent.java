package com.hotniao.live.eventbus;

/**
 * Created by Administrator on 2018/4/13.
 */

public class RefreshCommentEvent {

    public RefreshCommentEvent(String id, String type) {
        this.id = id;
        this.type = type;
    }

    private String id;
    private String type;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
