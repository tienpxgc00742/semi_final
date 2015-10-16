package com.tnaapp.tnalayout.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.tnaapp.tnalayout.R;
import com.tnaapp.tnalayout.ai.Video;
import com.tnaapp.tnalayout.tien.model.Item;
import com.tnaapp.tnalayout.utils.DownloadImageTask;

import java.util.List;

/**
 * Created by YoungKaka on 10/15/2015.
 */
public class SearchNewsResultAdapter extends BaseAdapter implements ISearchAdpater {

    private  List<Item> items;
    private Context context;
    public SearchNewsResultAdapter(Context context, List<Item> items) {
        this.context = context;
        this.items = items;
    }

    @Override
    public void loadNews(List<Item> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    public void loadVideos(List<Video> videos){

    }

    @Override
    public int getCount() {
        return  items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Item item = (Item) getItem(position);
        View view = convertView;
        MyHolder myHolder;
        if(view == null){
            myHolder = new MyHolder();
            view = LayoutInflater.from(context).inflate(R.layout.list_news_item,parent,false);
            myHolder.mImage = (ImageView) view.findViewById(R.id.news_img_thumbnail);
            myHolder.mTitle = (TextView) view.findViewById(R.id.news_title);
            myHolder.mDes = (TextView) view.findViewById(R.id.news_des);
           view.setTag(myHolder);
        }else  {
            myHolder = (MyHolder) view.getTag();
        }
        new DownloadImageTask(myHolder.mImage).execute(item.getSummaryImg());
        myHolder.mTitle.setText(item.getTitle());
        myHolder.mDes.setText(item.getDescription());

        return view;
    }



    private  class MyHolder {
            ImageView mImage;
            TextView mTitle, mDes;
    }

}
