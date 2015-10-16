package com.tnaapp.tnalayout.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tnaapp.tnalayout.R;
import com.tnaapp.tnalayout.model.NewsItem;
import com.tnaapp.tnalayout.utils.DownloadImageTask;

import java.util.List;

/**
 * Created by dfChicken on 08/10/2015.
 */
public class NewsRecyclerAdapter extends RecyclerView.Adapter<NewsRecyclerAdapter.ViewHolder> {
    List<NewsItem> mNewsItems;

    public NewsRecyclerAdapter(List<NewsItem> newsListItem) {
        super();
        this.mNewsItems = newsListItem;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.list_news_item, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int i) {
        final NewsItem mNewsItem = mNewsItems.get(i);
//        viewHolder.mNewsLayout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//
//            }
//        });
        viewHolder.mNewsTitle.setText(mNewsItem.getTitle());
        viewHolder.mNewsDes.setText(mNewsItem.getDes());
        viewHolder.mNewsTime.setText(mNewsItem.getmTime());
        new DownloadImageTask(viewHolder.mNewsImgThumbnail).execute(mNewsItem.getThumbnail());

    }

    @Override
    public int getItemCount() {
        return mNewsItems.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        public RelativeLayout mNewsLayout;
        public ImageView mNewsImgThumbnail;
        public TextView mNewsTitle;
        public TextView mNewsTime;
        public TextView mNewsDes;

        public ViewHolder(View itemView) {
            super(itemView);
            mNewsLayout = (RelativeLayout) itemView.findViewById(R.id.news_item_layout);
            mNewsImgThumbnail = (ImageView) itemView.findViewById(R.id.news_img_thumbnail);
            mNewsTitle = (TextView) itemView.findViewById(R.id.news_title);
            mNewsDes = (TextView) itemView.findViewById(R.id.news_des);
            mNewsTime = (TextView) itemView.findViewById(R.id.news_time);
        }
    }
}

