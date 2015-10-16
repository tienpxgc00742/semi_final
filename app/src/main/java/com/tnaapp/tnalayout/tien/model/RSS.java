package com.tnaapp.tnalayout.tien.model;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by XTien on 10/11/2015.
 */
public class RSS {
    private List<Item> rss;

    public RSS() {
        rss = new ArrayList<Item>();
    }

    public List<Item> getList() {
        return rss;
    }

    public void setList(List<Item> rss) {
        this.rss = rss;
    }
}
