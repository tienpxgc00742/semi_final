package com.tnaapp.tnalayout.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.tnaapp.tnalayout.R;


import java.util.List;

/**
 * Created by dfChicken on 12/10/2015.
 */
public class VideosItemArrayAdapter extends BaseAdapter {
    private Context context;
    private List<String> data;
    private static LayoutInflater inflater = null;

    public VideosItemArrayAdapter(Context c, List<String> ctlist) {
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
        View listView = inflater.inflate(R.layout.list_videos_item, null);

        TextView title = (TextView) listView.findViewById(R.id.lblListItem);
        TextView channel = (TextView) listView.findViewById(R.id.item_channel);

        title.setText(data.get(i));

        return listView;
    }


}
