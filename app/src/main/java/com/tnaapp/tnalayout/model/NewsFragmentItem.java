package com.tnaapp.tnalayout.model;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.tnaapp.tnalayout.R;
import com.tnaapp.tnalayout.activity.MainActivity;
import com.tnaapp.tnalayout.activity.fragments.SwipeNewsFragment;
import com.tnaapp.tnalayout.utils.DownloadImageTask;

/**
 * Created by dfChicken on 09/10/2015.
 */
public class NewsFragmentItem extends Fragment implements CustomWebView.CustomWebViewListener {

    private NewsItem mNewsItem;
    private ImageView mImage;
    private TextView mTitle, mContent, mTime;
    private CustomWebView mWebView;

    public CustomWebView getWebView() {
        return mWebView;
    }

    public void setWebView(CustomWebView mWebView) {
        this.mWebView = mWebView;
    }

    public NewsFragmentItem() {
    }

    public NewsItem getNewsItem() {
        return mNewsItem;
    }

    public String getNewsItemId() {
        if (mNewsItem != null) {
            return mNewsItem.getId();
        }
        return "";
    }

    public void setNewsItem(NewsItem mNewsItem) {
        this.mNewsItem = mNewsItem;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.news_fragment_item, container, false);
        mTitle = (TextView) rootView.findViewById(R.id.news_title_swipe);
        mContent = (TextView) rootView.findViewById(R.id.news_content_swipe);
        mWebView = (CustomWebView) rootView.findViewById(R.id.webView);
        mWebView.setCustomWebViewListener(this);
        if (this.mNewsItem != null) {
            mTitle.setText(mNewsItem.getTitle());
            mContent.setText(mNewsItem.getDes());
            mWebView.loadData(mNewsItem.getmHtml(), "text/html; charset=UTF-8", null);
        }
        mWebView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("onClickWebView", "true");
            }
        });
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

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onSwipeUp() {
        ((SwipeNewsFragment)getParentFragment()).onSwipeUp();
    }

    @Override
    public void onSwipeDown() {
        ((SwipeNewsFragment)getParentFragment()).onSwipeDown();
    }
}
