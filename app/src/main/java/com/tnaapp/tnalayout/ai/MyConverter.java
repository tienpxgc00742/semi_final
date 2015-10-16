package com.tnaapp.tnalayout.ai;

import android.util.Log;

import com.google.gson.Gson;
import com.tnaapp.tnalayout.tien.model.Item;
import com.tnaapp.tnalayout.tien.model.RSS;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by YoungKaka on 10/10/2015.
 */
public class MyConverter {
    public static News jsonToRootNew(String json) {
        News news = new News();
        try {
            news = new Gson().fromJson(json, News.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return news;
    }

    public static RSS jsonToRSS(String json) {
        RSS rss = new RSS();
        try {
            //rss = new Gson().fromJson(json, RSS.class);
            JSONArray arrayResponse = new JSONArray(json);
            for(int i=0; i<arrayResponse.length(); i++)
            {
                JSONObject obj = arrayResponse.getJSONObject(i);
                Log.wtf("STRING:", String.valueOf(obj.toString()));
                Log.wtf("ID:", String.valueOf(obj.getString("title") + "-" + obj.names().toString()));
                String test1 = obj.getString("pubDate");
                String test2 = obj.optString("description");
                String test3 = obj.optString("summaryImg");
                String test4 = obj.optString("pubDate");
                rss.getList().add(new Item(Integer.parseInt(obj.getString("id")), obj.getString("title"), obj.getString("pubDate"), obj.getString("description"), obj.getString("summaryImg")));
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.wtf("","ERROR");
        }
        return rss;
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

    public static News readXML(String xml) {

        return null;
    }
}
