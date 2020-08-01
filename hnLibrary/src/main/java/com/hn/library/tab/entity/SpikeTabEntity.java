package com.hn.library.tab.entity;

/**
 * Created by Alan on 2019/6/14.
 */
public class SpikeTabEntity {
   private Long  startTime = 0L;
   private Long endTime = 0L;
   private String  title;
   private String  secondTitle;

    public SpikeTabEntity(String title, String secondTitle) {
        this.title = title;
        this.secondTitle = secondTitle;
    }

    public Long getEndTime() {
        return endTime;
    }

    public void setEndTime(Long endTime) {
        this.endTime = endTime;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSecondTitle() {
        return secondTitle;
    }

    public void setSecondTitle(String secondTitle) {
        this.secondTitle = secondTitle;
    }
}
