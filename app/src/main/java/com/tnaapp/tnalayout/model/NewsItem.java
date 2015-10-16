package com.tnaapp.tnalayout.model;

/**
 * Created by dfChicken on 08/10/2015.
 */
public class NewsItem {
    private String mNewsId;
    private String mTitle;
    private String mDes;
    private String mTime;

    public String getmHtml() {
        return mHtml;
    }

    public void setmHtml(String mHtml) {
        this.mHtml = mHtml;
    }

    private String mHtml;

    public NewsItem(String mNewsId, String mTitle, String mDes, String mThumbnail, String mHtml, String mTime) {
        this.mNewsId = mNewsId;
        this.mTitle = mTitle;
        this.mDes = mDes;
        this.mThumbnail = mThumbnail;
        this.mHtml = mHtml;
        this.mTime = mTime;
    }

    private String mThumbnail;

    public NewsItem() {
    }

    public String getId() {
        return mNewsId;
    }

    public void setId(String mNewsId) {
        this.mNewsId = mNewsId;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String name) {
        this.mTitle = name;
    }

    public String getDes() {
        return mDes;
    }

    public void setDes(String des) {
        this.mDes = des;
    }

    public String getThumbnail() {
        return mThumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.mThumbnail = thumbnail;
    }

    public String getmTime() {
        return mTime;
    }

    public void setmTime(String mTime) {
        this.mTime = mTime;
    }
}
