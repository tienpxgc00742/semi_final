package com.tnaapp.tnalayout.adapter;

import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import java.util.Collections;
import java.util.List;

import com.tnaapp.tnalayout.R;
import com.tnaapp.tnalayout.model.NavigationDrawerItem;

/**
 * Created by dfChicken on 01/10/2015.
 */

public class NavigationDrawerAdapter extends RecyclerView.Adapter<NavigationDrawerAdapter.ViewHolder> {
    List<NavigationDrawerItem> mData = Collections.emptyList();
    private int mSelectedPosition;
    private int mTouchedPosition = -1;

    public NavigationDrawerAdapter(List<NavigationDrawerItem> data) {
        this.mData = data;
    }

    public void delete(int position) {
        mData.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.nav_drawer_row, viewGroup, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        NavigationDrawerItem current = mData.get(position);
        viewHolder.title.setText(current.getTitle());
        //đặt icon
        switch (position) {
            case 0:
                //fragment home
                viewHolder.icon.setImageResource(R.drawable.ic_home_grey);
                break;
            case 1:
                //fragment history
                viewHolder.icon.setImageResource(R.drawable.ic_starball_grey);
                break;
            case 2:
                //fragment history
                viewHolder.icon.setImageResource(R.drawable.ic_history_grey);
                break;
            case 3:
                //fragment history
                viewHolder.icon.setImageResource(R.drawable.ic_guide_grey);
                break;
            case 4:
                //fragment about
                viewHolder.icon.setImageResource(R.drawable.ic_settings_grey);
                break;
            case 5:
                //fragment about
                viewHolder.icon.setImageResource(R.drawable.ic_info_grey);
                break;
        }
        //đặt phân cách - separator
//        setSeparatorBelow(viewHolder,position,new int[] {1});
        if(position==2){
            viewHolder.separator.setVisibility(View.VISIBLE);
        } else {
            viewHolder.separator.setVisibility(View.INVISIBLE);
        }
        //đặt hightlight
        if (mSelectedPosition == position || mTouchedPosition == position) {
//            viewHolder.itemView.setSelected(true);
            viewHolder.title.setTypeface(null, Typeface.BOLD);
        } else {
//            viewHolder.itemView.setSelected(false);
            viewHolder.title.setTypeface(null, Typeface.NORMAL);
        }
    }
//
//    public void setSeparatorBelow(ViewHolder viewHolder, int eachPoint, int[] positionArray){
//        viewHolder.separator.setVisibility(View.INVISIBLE);
//        for(int i=0;i<positionArray.length;i++){
//            if(eachPoint == positionArray[i]){
//                viewHolder.separator.setVisibility(View.VISIBLE);
//            }
//        }
//    }

    public void selectPosition(int position) {
        int lastPosition = mSelectedPosition;
        mSelectedPosition = position;
        notifyItemChanged(lastPosition);
        notifyItemChanged(position);
    }

    @Override
    public int getItemCount() {
        return  mData.size() ;
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        RelativeLayout mItemView;
        TextView title;
        ImageView icon;
        View separator;
        public ViewHolder(View itemView) {
            super(itemView);
            mItemView = (RelativeLayout) itemView.findViewById(R.id.itemView);
            title = (TextView) itemView.findViewById(R.id.title);
            icon = (ImageView) itemView.findViewById(R.id.rowIcon);
            separator = itemView.findViewById(R.id.list_item_separator);
        }

    }

}