package com.tnaapp.tnalayout.activity.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.tnaapp.tnalayout.R;
import com.tnaapp.tnalayout.activity.MainActivity;
import com.tnaapp.tnalayout.adapter.ISearchAdpater;
import com.tnaapp.tnalayout.adapter.SearchNewsResultAdapter;
import com.tnaapp.tnalayout.adapter.SearchVideoResultAdapter;
import com.tnaapp.tnalayout.ai.DataSource;
import com.tnaapp.tnalayout.ai.MyConverter;
import com.tnaapp.tnalayout.ai.News;
import com.tnaapp.tnalayout.ai.RootVideo;
import com.tnaapp.tnalayout.ai.Video;
import com.tnaapp.tnalayout.model.NewsFragmentItem;
import com.tnaapp.tnalayout.model.NewsItem;
import com.tnaapp.tnalayout.tien.model.Client;
import com.tnaapp.tnalayout.tien.model.Item;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by YoungKaka on 10/14/2015.
 */
public class SearchResultFragment extends Fragment implements AdapterView.OnItemClickListener {

    private ListView listView;
    private Context context;
    private String query = "";
    private int currentTab = 0;
    private ISearchAdpater adapter;
    private List<Item> searchItems;
    private List<NewsItem> newsItemList;
    private List<Video> videoList;
    private String[] titles;
    public SearchResultFragment() {
        newsItemList = new ArrayList<>();
        for(Item i : DataSource.getInstance().getNews().getList())
        {
            NewsItem items = new NewsItem();
            //items.setId("coincard");
            items.setTitle(i.getTitle());
            items.setDes(i.getDescription());
            items.setThumbnail(i.getSummaryImg());
            items.setId(String.valueOf(i.getId()));
            newsItemList.add(items);
        }
    }


    public String getQuery() {
        return query;
    }

    public void setQuery(String query,int currentTab) {
        this.query = query;
        this.currentTab  = currentTab;
        handleQuery(query);
    }

    private void handleQuery(String text){
        if(adapter != null){
           if(currentTab == 1){
               List<Video> videos = searchVideos(text);
               adapter.loadVideos(videos);
           }else if(currentTab == 0){
                List<Item> items = searchItems(text);
               adapter.loadNews(items);
               searchItems = items;
           }
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    private List<Item> searchItems(String text){
        List<Item> items = DataSource.getInstance().searchItemNews(text);
        return items;
    }
    private List<Video> searchVideos(String text){
        RootVideo rootVideo = DataSource.getInstance().getRootVideo();
        List<Video> videos = rootVideo.searchVideo(text);
        return  videos;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        context = getActivity();
        View rootView = inflater.inflate(R.layout.fragment_search_result, container, false);
        listView = (ListView) rootView.findViewById(R.id.listResult);

            if(currentTab == 1){
                adapter = new SearchVideoResultAdapter(getActivity(),searchVideos(query));
                listView.setAdapter((SearchVideoResultAdapter)adapter);
                videoList = DataSource.getInstance().getRootVideo().getAllVideos();

            }else  if (currentTab == 0){
                List<Item> items = searchItems(query);
                adapter = new SearchNewsResultAdapter(getActivity(),items);
                listView.setAdapter((SearchNewsResultAdapter)adapter);
                searchItems = items;
            }
        listView.setOnItemClickListener(this);
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

    @Override
    public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
        if(adapter instanceof  SearchNewsResultAdapter){
            hanldeNewsResult(position);
        }else if(adapter instanceof SearchVideoResultAdapter){
            handleVideosResult(position);
        }
    }

    private void handleVideosResult(int position) {
        MainActivity mainActivity = (MainActivity) getActivity();
        Video video = videoList.get(position);
        List<String> s = new ArrayList<>();
        s.add("Good");
        if(mainActivity!=null){ //cẩn thận kiểm tra null vẫn hơn -_-
            mainActivity.reloadFloatVideoPlayer(video.getSource());
            mainActivity.getVideosChannelFragment().setChannelData(video.getDescription());
          mainActivity.getVideosChannelFragment().setVideosData(s);
            mainActivity.loadChannelForPlayer();
        }
    }

    private void hanldeNewsResult(final int position){
        final SwipeNewsFragment mSwipeNewsFragment = new SwipeNewsFragment();
        mSwipeNewsFragment.setNewsItems(newsItemList);
        mSwipeNewsFragment.setDefaultViewItem(position);

        FragmentManager fragmentManager  = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);
        fragmentTransaction.replace(R.id.container_body, mSwipeNewsFragment);
        fragmentTransaction.commit();
        ((MainActivity) getActivity()).getSupportActionBar().setTitle(getResources().getString(R.string.tab_news));
//                        ((MainActivity) getActivity()).getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        final Client client = new Client();
        client.setListener(new Client.Listener() {
            @Override
            public void doneExecute() {
                Log.wtf("RS: ", client.result);
                News news = MyConverter.jsonToRootNew(client.result);
                Log.wtf("NEW:",news.getContent());
                NewsFragmentItem current = (NewsFragmentItem) mSwipeNewsFragment.getAdapter().getItem(position);
                current.getWebView().loadData(news.getContent(), "text/html; charset=UTF-8", null);
            }

            @Override
            public void preExcute() {

            }
        });
        client.execute("http://104.155.237.47/web/api/data?id="+newsItemList.get(position).getId(),"json");
    }
}