package com.tnaapp.tnalayout.activity.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tnaapp.tnalayout.R;
import com.tnaapp.tnalayout.adapter.VideosItemArrayAdapter;
import com.tnaapp.tnalayout.model.ExpandedListView;

import java.util.List;

/**
 * Created by dfChicken on 14/10/2015.
 */
public class VideosChannelFragment extends Fragment {

    private String mChannelData;
    private List<String> mVideosData;
    ExpandedListView mExpandedListView;
    VideosItemArrayAdapter listAdapter;
//    public String getChannelData() {
//        return mChannelData;
//    }

    public void setChannelData(String mChannelData) {
        this.mChannelData = mChannelData;
    }

//    public List<String> getVideosData() {
//        return mVideosData;
//    }

    public void setVideosData(List<String> mVideosData) {
        this.mVideosData = mVideosData;
    }

    public VideosChannelFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_videos_channel, container, false);
        mExpandedListView = (ExpandedListView) rootView.findViewById(R.id.videosDataListFrag);
        listAdapter = new VideosItemArrayAdapter(rootView.getContext(), mVideosData);
        mExpandedListView.setAdapter(listAdapter);
        // Inflate the layout for this fragment
        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

}
