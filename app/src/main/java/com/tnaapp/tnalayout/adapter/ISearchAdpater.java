package com.tnaapp.tnalayout.adapter;

import com.tnaapp.tnalayout.ai.Video;
import com.tnaapp.tnalayout.tien.model.Item;

import java.util.List;

/**
 * Created by YoungKaka on 10/15/2015.
 */
public interface ISearchAdpater {
    public void loadVideos(List<Video> videos);
    public void loadNews(List<Item> news);
}
