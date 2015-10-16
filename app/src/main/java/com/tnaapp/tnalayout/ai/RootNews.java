package com.tnaapp.tnalayout.ai;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by YoungKaka on 10/10/2015.
 */
public class RootNews {
    private List<News> news;

    public RootNews() {
    }

    public List<News> getNews() {
        return news;
    }

    public void setNews(List<News> news) {
        this.news = news;
    }

    public News getNews(String session){
        News ne = new News();
        for(News n : news){
            if(n.getSeason().equalsIgnoreCase(session)){
                ne = n;
                break;
            }
        }
        return ne;
    }

    public List<News> searchNews(String text){
        List<News> list = new ArrayList<>();
           for (News n : news){
               if(n.getTitle().toLowerCase().contains(text.trim()) || n.getContent().contains(text.trim())){
                   list.add(n);
               }
           }
        return list;
    }
}
