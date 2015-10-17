package com.tnaapp.tnalayout.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.tnaapp.tnalayout.R;
import com.tnaapp.tnalayout.ai.News;

import java.util.List;

/**
 * Created by dfChicken on 17/10/2015.
 */
public class VideosInNewsItemArray extends BaseAdapter {
    private Context context;
    private List<News.NewsVid> data;
    private static LayoutInflater inflater = null;

    public VideosInNewsItemArray(Context c, List<News.NewsVid> ctlist) {
        this.context = c;
        this.data = ctlist;
        inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View listView = inflater.inflate(R.layout.news_videos_item, null);

        TextView title = (TextView) listView.findViewById(R.id.newsVideosItemTitle);
        TextView channel = (TextView) listView.findViewById(R.id.newsVideosItemChannel);

        title.setText(data.get(i).getTitle());
        channel.setText("Videos " + String.valueOf(i));

        return listView;
    }
}
