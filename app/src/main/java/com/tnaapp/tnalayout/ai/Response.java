package com.tnaapp.tnalayout.ai;

import java.util.List;

/**
 * Created by YoungKaka on 10/10/2015.
 */
public class Response
{
    public String id;
    public List<Video> videos;

    public Response() {
    }

    public Response(String id, List<Video> videos) {
        this.id = id;
        this.videos = videos;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<Video> getVideos() {
        return videos;
    }

    public void setVideos(List<Video> videos) {
        this.videos = videos;
    }
}