package com.tnaapp.tnalayout.ai;

import java.util.List;

/**
 * Created by YoungKaka on 10/10/2015.
 */
public class News
{
    private String season;
    private String title;
    private List<String> images;
    private List<String> videos;
    private String content;

    public News() {
    }

    public News(String season, String title, List<String> images, List<String> videos, String content) {
        this.season = season;
        this.title = title;
        this.images = images;
        this.videos = videos;
        this.content = content;
    }

    public String getSeason() {
        return season;
    }

    public void setSeason(String season) {
        this.season = season;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public List<String> getVideos() {
        return videos;
    }

    public void setVideos(List<String> videos) {
        this.videos = videos;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}

