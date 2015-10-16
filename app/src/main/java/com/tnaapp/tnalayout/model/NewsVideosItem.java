package com.tnaapp.tnalayout.model;

import java.util.ArrayList;

/**
 * Created by dfChicken on 16/10/2015.
 */
public class NewsVideosItem {
    private ArrayList<NewsVid> newsvid;

    public ArrayList<NewsVid> getNewsVideos() {
        return this.newsvid;
    }

    public void setNewsVideos(ArrayList<NewsVid> newsvid) {
        this.newsvid = newsvid;
    }

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
}

