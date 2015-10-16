package com.tnaapp.tnalayout.ai;


import com.google.gson.Gson;

/**
 * Created by YoungKaka on 10/10/2015.
 */
public class JsonConverter {
    public static RootNews jsonToRootNew(String json){
        RootNews rootNews = new RootNews();
        try {
            rootNews = new Gson().fromJson(json,RootNews.class);
        }catch (Exception e){
            e.printStackTrace();
        }
        return rootNews;
    }


    public static RootVideo jsonToRootVideo(String json){
        RootVideo rootVideo = new RootVideo();
        try {
            rootVideo = new Gson().fromJson(json,RootVideo.class);
        }catch (Exception e){
            e.printStackTrace();
        }
        return rootVideo;
    }
}
