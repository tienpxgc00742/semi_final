package com.tnaapp.tnalayout.activity.tabs;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.tnaapp.tnalayout.R;
import com.tnaapp.tnalayout.activity.MainActivity;
import com.tnaapp.tnalayout.activity.fragments.SwipeNewsFragment;
import com.tnaapp.tnalayout.adapter.RecyclerItemClickListener;
import com.tnaapp.tnalayout.ai.DataSource;
import com.tnaapp.tnalayout.ai.MyConverter;
import com.tnaapp.tnalayout.ai.News;
import com.tnaapp.tnalayout.model.NewsFragmentItem;
import com.tnaapp.tnalayout.model.NewsItem;
import com.tnaapp.tnalayout.adapter.NewsRecyclerAdapter;
import com.tnaapp.tnalayout.tien.box.TTool;
import com.tnaapp.tnalayout.tien.model.Client;
import com.tnaapp.tnalayout.tien.model.Item;
import com.tnaapp.tnalayout.tien.model.RSS;
import com.tnaapp.tnalayout.utils.T;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dfChicken on 01/10/2015.
 */
public class HomeTab extends Fragment {
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView.Adapter mAdapter;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    public static SwipeNewsFragment mSwipeNewsFragment;
    private ProgressBar mProgressBar;
    public int getmCurrentViewIndex() {
        return mCurrentViewIndex;
    }

    private int mCurrentViewIndex;

    // TAB Trang chá»§
    public HomeTab() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.tab_home, container, false);

        mProgressBar = (ProgressBar) view.findViewById(R.id.item_video_loading_view);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        // xXx
        final List<NewsItem> mItemDemo = new ArrayList<>();
        NewsItem items = new NewsItem();
        final Client client = new Client();
        TTool.setRunnerActivity(((MainActivity) getActivity()));
        client.setListener(new Client.Listener() {
            @Override
            public void doneExecute() {
                RSS rss = MyConverter.jsonToRSS(client.result);
                Log.wtf("RSS", client.result);
                if(client.result != null)
                {
                    Log.wtf("Size: ",String.valueOf(rss.getList().size()));
                    for(Item i : rss.getList())
                    {
                        NewsItem items = new NewsItem();
                        //items.setId("coincard");
                        items.setTitle(i.getTitle());
                        items.setDes(i.getDescription());
                        items.setThumbnail(i.getSummaryImg());
                        items.setId(String.valueOf(i.getId()));
                        items.setmTime(TTool.calTime("2015-10-16 10:05:27"));
                        mItemDemo.add(items);
                    }
                    mAdapter = new NewsRecyclerAdapter(mItemDemo);

                    mSwipeNewsFragment = new SwipeNewsFragment();
                    mSwipeNewsFragment.setNewsItems(mItemDemo);

                    mRecyclerView.setAdapter(mAdapter);

                    mProgressBar.setVisibility(View.GONE);
                    DataSource.getInstance().setNews(rss);
                }
                else
                {

                }
            }

            @Override
            public void preExcute() {
                mProgressBar.setVisibility(View.VISIBLE);
            }
        });
        client.execute("http://104.155.237.47/web/api/pull","json");
        mRecyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(getContext(), mRecyclerView, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view,final int position) {
                        mSwipeNewsFragment.setDefaultViewItem(position);
                        FragmentManager fragmentManager = getParentFragment().getFragmentManager();
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
                                Log.wtf("NEW:", news.getContent());
                                NewsFragmentItem current = (NewsFragmentItem) mSwipeNewsFragment.getAdapter().getItem(position);
                                current.getWebView().loadData(news.getContent(), "text/html; charset=UTF-8", null);
                            }

                            @Override
                            public void preExcute() {

                            }
                        });
                        client.execute("http://104.155.237.47/web/api/data?id="+mItemDemo.get(position).getId(),"json");
                    }

                    @Override
                    public void onItemLongClick(View view, int position) {
                        T.s(view.getContext(), mItemDemo.get(position).getId());
                    }
                })
        );

        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeRefreshLayout);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Refresh items
                refreshItems();
            }
        });
        mSwipeRefreshLayout.setEnabled(true);
        // xXx
        return view;
    }

    void refreshItems() {
        // Load items
        // ...

        // Load complete
        onItemsLoadComplete();
    }

    void onItemsLoadComplete() {
        // Update the adapter and notify data set changed
        // ...

        // Stop refresh animation
        mSwipeRefreshLayout.setRefreshing(false);
    }

}
