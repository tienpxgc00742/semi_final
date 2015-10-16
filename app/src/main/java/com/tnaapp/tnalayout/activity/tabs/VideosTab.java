package com.tnaapp.tnalayout.activity.tabs;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ProgressBar;

import com.tnaapp.tnalayout.R;
import com.tnaapp.tnalayout.activity.MainActivity;
import com.tnaapp.tnalayout.adapter.ExpandableListAdapter;
import com.tnaapp.tnalayout.ai.DataSource;
import com.tnaapp.tnalayout.ai.MyConverter;
import com.tnaapp.tnalayout.ai.Response;
import com.tnaapp.tnalayout.ai.RootVideo;
import com.tnaapp.tnalayout.ai.Video;
import com.tnaapp.tnalayout.tien.model.Client;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by dfChicken on 01/10/2015.
 */
public class VideosTab extends Fragment {
    //TAB videos

    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    //header channel
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild; //data merge with header


    private ProgressBar mProgressBar;
//    private Button mDemoPlayerBtn;

    public VideosTab() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.tab_video, container, false);
        mProgressBar = (ProgressBar) view.findViewById(R.id.item_video_loading_view);
        // get the listview
        expListView = (ExpandableListView) view.findViewById(R.id.lvExp);
        // preparing list data
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();
        prepareListData();
        //init expandablelistview
        listAdapter = new ExpandableListAdapter(view.getContext(), listDataHeader, listDataChild);
        listAdapter.setSupportActivity(((MainActivity) getActivity()));
        // setting list adapter
        expListView.setAdapter(listAdapter);
//         Listview Group expanded listener
        expListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {
            }
        });

        expListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {
            @Override
            public void onGroupCollapse(int groupPosition) {
            }
        });

        // Inflate the layout for this fragment
        return view;
    }

    private void prepareListData() {
        final Client client = new Client();
        client.setListener(new Client.Listener() {
            @Override
            public void doneExecute() {
                RootVideo root = MyConverter.jsonToRootVideo(client.result);
                Log.wtf("start", client.result);
                int i = 0;
                for (Response r : root.getResponse()) {
                    List<String> video = new ArrayList<String>();
                    Log.wtf("id", r.getId());
                    listDataHeader.add(r.getId());
                    for (Video videoitem : r.getVideos()) {
                        video.add(videoitem.getTitle());
                    }
                    listDataChild.put(listDataHeader.get(i), video);
                    i++;
                }

                listAdapter.setDataHeader(listDataHeader);
                listAdapter.setDataChild(listDataChild);
                listAdapter.processSuggestData();
                expListView.setAdapter(listAdapter);
                mProgressBar.setVisibility(View.GONE);

                DataSource.getInstance().setRootVideo(root);
            }

            @Override
            public void preExcute() {
                mProgressBar.setVisibility(View.VISIBLE);
            }
        });
        client.execute("http://test-data-1094.appspot.com/data/videos", "json");

        //        // Adding child data
//        listDataHeader.add("Top 250");
//        listDataHeader.add("Now Showing");
//        listDataHeader.add("Coming Soon..");
////
//        // Adding child data
//        List<String> top250 = new ArrayList<String>();
//        top250.add("The Shawshank Redemption");
//        top250.add("The Godfather");
//        top250.add("The Godfather: Part II");
//
//        List<String> nowShowing = new ArrayList<String>();
//        nowShowing.add("The Conjuring");
//        nowShowing.add("Despicable Me 2");
//        nowShowing.add("Turbo");
//        nowShowing.add("Grown Ups 2");
//        nowShowing.add("Red 2");
//        nowShowing.add("The Wolverine");
////
//        List<String> comingSoon = new ArrayList<>();
//        comingSoon.add("2 Guns");
//
//
////
//        listDataChild.put(listDataHeader.get(0), top250); // Header, Child data
//        listDataChild.put(listDataHeader.get(1), nowShowing);
//        listDataChild.put(listDataHeader.get(2), comingSoon);
    }

}
