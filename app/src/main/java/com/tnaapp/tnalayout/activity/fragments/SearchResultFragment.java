package com.tnaapp.tnalayout.activity.fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tnaapp.tnalayout.R;
import com.tnaapp.tnalayout.activity.MainActivity;
import com.tnaapp.tnalayout.utils.T;

/**
 * Created by YoungKaka on 10/14/2015.
 */
public class SearchResultFragment  extends Fragment {
    private Context context;
    private String query = "";
    private TextView tv;
    public SearchResultFragment() {
        // Required empty public constructor
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
        if(tv!= null){
           tv.setText(query);
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        context = getActivity();
        View rootView = inflater.inflate(R.layout.fragment_search_result, container, false);
         tv = (TextView) rootView.findViewById(R.id.label);
        tv.setText(query);
        // Inflate the layout for this fragment
        return rootView;
    }



    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onResume() {
        super.onResume();
        getView().setFocusableInTouchMode(true);
        getView().requestFocus();
        getView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK) {
                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);
                    fragmentTransaction.replace(R.id.container_body, MainActivity.mHomeFragment);
                    fragmentTransaction.commit();
                    MainActivity.mFragmentDrawer.selectItem(0);
                    MainActivity.mToolbar.setTitle(getResources().getString(R.string.title_home));
                    return true;
                }
                return false;
            }
        });
    }
}