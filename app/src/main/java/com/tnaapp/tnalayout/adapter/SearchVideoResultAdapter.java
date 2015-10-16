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

import java.util.List;

/**
 * Created by YoungKaka on 10/15/2015.
 */
public class SearchVideoResultAdapter extends BaseAdapter implements ISearchAdpater{

    private  List<Video> videos;
    private Context context;
    public SearchVideoResultAdapter(Context context, List<Video> videos) {
        this.context = context;
        this.videos = videos;
    }

    @Override
    public void loadNews(List<Item> news) {

    }

    public void loadVideos(List<Video> videos){
        this.videos = videos;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return videos.size();
    }

    @Override
    public Object getItem(int position) {
        return videos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Video video = (Video) getItem(position);
        View view = convertView;
        MyHolder myHolder;
        if(view == null){
            myHolder = new MyHolder();
            view = LayoutInflater.from(context).inflate(R.layout.list_videos_item,parent,false);
            myHolder.mImage = (ImageView) view.findViewById(R.id.thumbnail);
            myHolder.mTitle = (TextView) view.findViewById(R.id.lblListItem);
            myHolder.mChannel = (TextView) view.findViewById(R.id.item_channel);
           view.setTag(myHolder);
        }else  {
            myHolder = (MyHolder) view.getTag();
        }
       // new DownloadImageTask(myHolder.mImage).execute(video.)
        myHolder.mTitle.setText(video.getTitle());
        myHolder.mChannel.setText(video.getDescription());

        return view;
    }



    private  class MyHolder {
            ImageView mImage;
            TextView mTitle, mChannel;
    }

}
