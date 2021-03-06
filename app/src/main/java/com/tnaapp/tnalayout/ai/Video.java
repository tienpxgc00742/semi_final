package com.tnaapp.tnalayout.ai;

/**
 * Created by YoungKaka on 10/10/2015.
 */
public class Video
{
    private int id;
    private String title;
    private String description;
    private String source;
    private String img;

    public Video() {
    }

    public Video(int id,String title, String description, String source,String img) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.source = source;
        this.img = img;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
