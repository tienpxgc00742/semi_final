package com.tnaapp.tnalayout.tien.model;

/**
 * Created by XTien on 10/11/2015.
 */
public class Item {
    public int id;
    public String title ;
    public String pubDate ;
    public String description ;
    public String summaryImg;

    public Item(int id, String title, String pubDate, String description, String summaryImg) {
        this.id = id;
        this.title = title;
        this.pubDate = pubDate;
        this.description = description;
        this.summaryImg = summaryImg;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPubDate() {
        return pubDate;
    }

    public void setPubDate(String pubDate) {
        this.pubDate = pubDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSummaryImg() {
        return summaryImg;
    }

    public void setSummaryImg(String summaryImg) {
        this.summaryImg = summaryImg;
    }
}

