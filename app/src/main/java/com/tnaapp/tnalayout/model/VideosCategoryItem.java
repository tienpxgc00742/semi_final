package com.tnaapp.tnalayout.model;

import java.util.List;

/**
 * Created by dfChicken on 11/10/2015.
 */
public class VideosCategoryItem {
    public String getvId() {
        return vId;
    }

    public void setId(String vId) {
        this.vId = vId;
    }

    public String getChannel() {
        return vChannel;
    }

    public void setChannel(String vChannel) {
        this.vChannel = vChannel;
    }

    public String getDes() {
        return vDes;
    }

    public void setDes(String vDes) {
        this.vDes = vDes;
    }

    private List<VideosItem> vItems;
    private String vId;
    private String vChannel;
    private String vDes;


}
