package com.gohouse.gohouse.bean;

public class RecTitleBean extends RecommendBean {

    private String title;

    public RecTitleBean(String title) {
        this.title = title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    @Override
    public int getItemType() {
        return titleType;
    }
}
