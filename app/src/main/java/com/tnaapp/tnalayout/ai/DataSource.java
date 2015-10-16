package com.tnaapp.tnalayout.ai;

import com.tnaapp.tnalayout.tien.model.Client;
import com.tnaapp.tnalayout.tien.model.Item;
import com.tnaapp.tnalayout.tien.model.RSS;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by YoungKaka on 10/15/2015.
 */
public class DataSource {
    private static final DataSource instance = new DataSource();

    public static DataSource getInstance() {
        return instance;
    }

    private DataSource() {
    }

    private RootVideo rootVideo;
    private RootNews rootNews;
    private RSS news;

    public RSS getNews() {
        return news;
    }

    public void setNews(RSS news) {
        this.news = news;
    }

    public RootNews getRootNews() {
        return rootNews;
    }

    public void setRootNews(RootNews rootNews) {
        this.rootNews = rootNews;
    }

    public RootVideo getRootVideo() {
        if (rootVideo == null) {
            rootVideo = getVideo();
        }
        return rootVideo;
    }

    private RootVideo getVideo() {
        final RootVideo[] root = new RootVideo[1];
        final Client client = new Client();
        client.setListener(new Client.Listener() {
            @Override
            public void doneExecute() {
                root[0] = MyConverter.jsonToRootVideo(client.result);
            }

            @Override
            public void preExcute() {

            }
        });
        return root[0];
    }

    public void setRootVideo(RootVideo rootVideo) {
        this.rootVideo = rootVideo;
    }

    public List<Item> searchItemNews(String text){
        List<Item> items = new ArrayList<>();
       if(news != null){
           for(Item i : news.getList()){
               if(i.getTitle().contains(text) || i.getDescription().contains(text)){
                   items.add(i);
               }
           }
       }
        return  items;
    }
}
