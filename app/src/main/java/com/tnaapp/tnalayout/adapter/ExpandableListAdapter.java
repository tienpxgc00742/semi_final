package com.tnaapp.tnalayout.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.tnaapp.tnalayout.R;
import com.tnaapp.tnalayout.activity.MainActivity;
import com.tnaapp.tnalayout.activity.fragments.VideosChannelFragment;
import com.tnaapp.tnalayout.ai.Video;
import com.tnaapp.tnalayout.model.ExpandedListView;
import com.tnaapp.tnalayout.tien.box.DatabaseHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by dfChicken on 11/10/2015.
 */
public class ExpandableListAdapter extends BaseExpandableListAdapter {
    private int _SUGGEST_NUMBER = 3;

    public Activity getActivity() {
        return _activity;
    }

    public void setActivity(Activity _activity) {
        this._activity = _activity;
    }

    public AppCompatActivity getSupportActivity() {
        return _appCompatActivity;
    }

    public void setSupportActivity(AppCompatActivity _appCompatActivity) {
        this._appCompatActivity = _appCompatActivity;
    }

    private Activity _activity;
    private AppCompatActivity _appCompatActivity;

    private Context _context;
    private List<String> _listDataHeader; // header titles - đưa String về itemvideos
    // dữ liệu con theo dạng header - title
    private HashMap<String, List<String>> _listDataChild;
    private HashMap<String, List<String>> _listDataSuggest;
    private VideosChannelFragment mVideosChannelFragmentOnAdapter;

    public ExpandableListAdapter(Context context, List<String> listDataHeader,
                                 HashMap<String, List<String>> listChildData) {
        this._context = context;
        this._listDataHeader = listDataHeader;
        this._listDataChild = listChildData;
        processSuggestData();
    }

    public void setDataChild(HashMap<String, List<String>> listDataChild) {
        this._listDataChild = listDataChild;
    }

    public void setDataHeader(List<String> listDataHeader) {
        this._listDataHeader = listDataHeader;
    }

    //chuẩn bị data cho suggest list
    public void processSuggestData() {
        try {
            HashMap<String, List<String>> _listDataSuggestTemp = new HashMap<>();
            for (int i = 0; i < _listDataHeader.size(); i++) {
                String _dataHeader = _listDataHeader.get(i);
                List<String> _listStringTemp = _listDataChild.get(_listDataHeader.get(i));
                int j = 0;
                List<String> _listStringSuggest = new ArrayList<>();
                if (_listDataChild.get(_listDataHeader.get(i)).size() > _SUGGEST_NUMBER) {
                    for (j = 0; j < _SUGGEST_NUMBER; j++) {
                        _listStringSuggest.add(j, _listStringTemp.get(j));
                        _listDataSuggestTemp.put(_dataHeader, _listStringSuggest);
                    }
                } else if (_listDataChild.get(_listDataHeader.get(i)).size() > 1 && _listDataChild.get(_listDataHeader.get(i)).size() <= _SUGGEST_NUMBER) {
                    for (j = 0; j < _SUGGEST_NUMBER - 1; j++) {
                        _listStringSuggest.add(j, _listStringTemp.get(j));
                        _listDataSuggestTemp.put(_dataHeader, _listStringSuggest);
                    }
                }
            }
            this._listDataSuggest = _listDataSuggestTemp;
            Log.d("processSuggestData", _listDataSuggest.toString());
        } catch (NullPointerException e) {
            Log.d("processSuggestData", "skip null data");
        }
    }

    @Override
    public Object getChild(int groupPosition, int childPosititon) {
        return this._listDataChild.get(this._listDataHeader.get(groupPosition))
                .get(childPosititon);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(final int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {
        final String childText = (String) getChild(groupPosition, childPosition);

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.list_videos_item, null);
        }

        //sự kiện Click cho list đã mở rộng
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("onChildClick", childText);
                if((MainActivity) getSupportActivity()!=null){ //cẩn thận kiểm tra null vẫn hơn -_-
                    ((MainActivity) getSupportActivity()).reloadFloatVideoPlayer("http://rmcdn.2mdn.net/MotifFiles/html/1248596/android_1330378998288.mp4");
                    ((MainActivity) getSupportActivity()).getVideosChannelFragment().setChannelData(getGroup(groupPosition).toString());
                    ((MainActivity) getSupportActivity()).getVideosChannelFragment().setVideosData(_listDataChild.get(getGroup(groupPosition).toString()));
                    ((MainActivity) getSupportActivity()).loadChannelForPlayer();
                    //Save History
//                    DatabaseHandler db = new DatabaseHandler(view.getContext());
//                    db.addHistory(new Video());
                }
            }
        });

        TextView txtListChild = (TextView) convertView
                .findViewById(R.id.lblListItem);
        txtListChild.setText(childText);
        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return this._listDataChild.get(this._listDataHeader.get(groupPosition))
                .size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this._listDataHeader.get(groupPosition);

    }

    @Override
    public int getGroupCount() {
        return this._listDataHeader.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public void onGroupExpanded(int groupPosition) {
        super.onGroupExpanded(groupPosition);
    }

    @Override
    public View getGroupView(final int groupPosition, final boolean isExpanded,
                             View convertView, final ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) _context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.list_videos_channel, null);
        }
        String headerTitle = (String) getGroup(groupPosition);
        List<String> _filterData = _listDataSuggest.get(headerTitle);
        final List<String> _suggestData = new ArrayList<>();
        if (_filterData != null) {
            for (int i = 0; i < _filterData.size(); i++) {
                _suggestData.add(_filterData.get(i));
            }
        }

        //list suggest
        ExpandedListView suggestList = (ExpandedListView) convertView.findViewById(R.id.suggestedHeaderList);

        //adapter
        VideosItemArrayAdapter listAdapter;
        listAdapter = new VideosItemArrayAdapter(convertView.getContext(), _suggestData);
        suggestList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Log.d("onChildClick", _suggestData.get(i));
                ((MainActivity) getSupportActivity()).reloadFloatVideoPlayer("http://clips.vorwaerts-gmbh.de/big_buck_bunny.mp4");
            }
        });
        suggestList.setAdapter(listAdapter);
        //nút expand
        final ImageButton btnExpand = (ImageButton) convertView.findViewById(R.id.btnExpand);
        final View finalConvertView = convertView;
        btnExpand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isExpanded) ((ExpandableListView) parent).collapseGroup(groupPosition);
                else ((ExpandableListView) parent).expandGroup(groupPosition, true);
            }
        });
        int btnExpandedVisibility = (!isExpanded) ? View.VISIBLE : View.GONE;
        btnExpand.setVisibility(btnExpandedVisibility); //ẩn nút expand
        suggestList.setVisibility(btnExpandedVisibility); //ẩn suggest list sau khi nhấn expand
        //các thuộc tính view
        TextView lblListHeader = (TextView) convertView
                .findViewById(R.id.lblListHeader);
        lblListHeader.setText(headerTitle);

        lblListHeader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(
                        finalConvertView.getContext(), "Header clicked", Toast.LENGTH_SHORT)
                        .show();
            }
        });

        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
