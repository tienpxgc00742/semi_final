package com.tnaapp.tnalayout.ai;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by YoungKaka on 10/10/2015.
 */
public class News {
    private String season;
    private String title;
    private List<String> images;
    //    private List<String> videos;
    private String content;
    private List<NewsVid> videos;

    public class NewsVid {
        private String title;

        public String getTitle() {
            return this.title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        private String link;

        public String getLink() {
            return this.link;
        }

        public void setLink(String link) {
            this.link = link;
        }
    }

    public News() {
    }

    public News(String season, String title, List<String> images, List<NewsVid> videos, String content) {
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

    public List<NewsVid> getNewsVideos() {
        return this.videos;
    }

    public void setNewsVideos(List<NewsVid> newsvid) {
        this.videos = newsvid;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}

