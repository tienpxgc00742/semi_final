package com.tnaapp.tnalayout.ai;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by YoungKaka on 10/10/2015.
 */
public class RootVideo {
    public List<Response> response;

    public RootVideo() {
    }

    public List<Response> getResponse() {
        return response;
    }

    public void setResponse(List<Response> response) {
        this.response = response;
    }

    public Response getResponse(String id) {
        Response res = new Response();
        for (Response r : response) {
            if (r.getId().equalsIgnoreCase(id)) {
                res = r;
                break;
            }
        }
        return res;
    }

    public List<Video> getAllVideos() {
        List<Video> videos = new ArrayList<>();
        for (Response r : response) {
            for (Video v : r.getVideos()) {
                videos.add(v);
            }
        }
        return videos;
    }
    public List<Video> searchVideo(String text) {
        List<Video> videos = new ArrayList<>();
        for (Response r : response) {
            for (Video v : r.getVideos()) {
                if (v.getTitle().toLowerCase().contains(text.trim()) || v.getDescription().contains(text.trim())) {
                    videos.add(v);
                }
            }
        }
        return videos;
    }
}
