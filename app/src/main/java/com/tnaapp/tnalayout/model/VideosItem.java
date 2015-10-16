package com.tnaapp.tnalayout.model;

/**
 * Created by dfChicken on 11/10/2015.
 */
public class VideosItem {
    private String vItemTitle;

    public String getItemDes() {
        return vItemDes;
    }

    public void setItemDes(String vItemDes) {
        this.vItemDes = vItemDes;
    }

    public String getUrl() {
        return vUrl;
    }

    public void setUrl(String vUrl) {
        this.vUrl = vUrl;
    }

    public String getOtherContent() {
        return vOtherContent;
    }

    public void setOtherContent(String vOtherContent) {
        this.vOtherContent = vOtherContent;
    }

    public String getItemTitle() {
        return vItemTitle;
    }

    public void setItemTitle(String vItemTitle) {
        this.vItemTitle = vItemTitle;
    }

    private String vItemDes;
    private String vUrl;
    private String vOtherContent;

    public String getItemThumb() {
        return vItemThumb;
    }

    public void setItemThumb(String vItemThumb) {
        this.vItemThumb = vItemThumb;
    }

    private String vItemThumb;
}
