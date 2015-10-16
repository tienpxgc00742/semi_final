package com.tnaapp.tnalayout.activity.fragments;

import android.app.Activity;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.GestureDetector;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.ScaleAnimation;
import android.webkit.WebView;
import android.widget.MediaController;
import android.widget.VideoView;

import com.tnaapp.tnalayout.R;
import com.tnaapp.tnalayout.activity.MainActivity;
import com.tnaapp.tnalayout.adapter.NewsSwipeFragmentAdapter;
import com.tnaapp.tnalayout.ai.MyConverter;
import com.tnaapp.tnalayout.ai.News;
import com.tnaapp.tnalayout.control.SlideUpViewGroup;
import com.tnaapp.tnalayout.model.CustomViewPager;
import com.tnaapp.tnalayout.model.DepthPageTransformer;
import com.tnaapp.tnalayout.model.NewsFragmentItem;
import com.tnaapp.tnalayout.model.NewsItem;
import com.tnaapp.tnalayout.tien.model.Client;

import java.util.List;

/**
 * Created by dfChicken on 09/10/2015.
 */
public class SwipeNewsFragment extends Fragment implements CustomViewPager.CustomViewPagerListener {
    private static CustomViewPager mViewPager;
    private List<NewsItem> mNewsItems;
    private int viewItem;
    private FloatingActionButton mFloatingActionButton;
    private SlideUpViewGroup mSlideUpViewGroup;
    private NewsSwipeFragmentAdapter adapter;
    public List<NewsItem> getNewsItems() {
        return mNewsItems;
    }


    public void setNewsItems(List<NewsItem> mNewsItems) {
        this.mNewsItems = mNewsItems;
    }

    public void setDefaultViewItem(int position) {
        if (position >= 0) {
            this.viewItem = position;
            return;
        }
        this.viewItem = 0;
    }
    public NewsSwipeFragmentAdapter getAdapter() {
        return adapter;
    }
    public SwipeNewsFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_swipe_news, container, false);
        final View decorView = getActivity().getWindow().getDecorView();
        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);

        mViewPager = (CustomViewPager) rootView.findViewById(R.id.viewpager_news);
        mViewPager.setPageTransformer(true, new DepthPageTransformer());
        if (mNewsItems != null) {
            setupViewPager(mViewPager);
            jumpToNews(viewItem);
        }
        mViewPager.setCustomViewPagerListener(this);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (currentPosition < position) {
                    Log.d("onPageScrolled", "Swipe Left to " + position);
                    hideFloatButton();
                } else if (currentPosition > position) {
                    Log.d("onPageScrolled", "Swipe Right to " + position);
                    showFloatButton();
                }
                currentPosition = position; // Update current position
            }

            @Override
            public void onPageSelected(final int arg0) {
                final NewsFragmentItem current = (NewsFragmentItem) adapter.getItem(arg0);
                final Client client = new Client();
                client.setListener(new Client.Listener() {
                    @Override
                    public void doneExecute() {
                        News news = MyConverter.jsonToRootNew(client.result);
                        Log.wtf("SWIPE: ", current.getNewsItem().getId());
                        current.getWebView().loadData(news.getContent(), "text/html; charset=UTF-8", null);
                    }

                    @Override
                    public void preExcute() {

                    }
                });
                client.execute("http://104.155.237.47/web/api/data?id=" + current.getNewsItem().getId(), "json");
            }

            @Override
            public void onPageScrollStateChanged(int state) {
//                Log.d("onPageScrollChanged", "State: " + state);
            }
        });
        mSlideUpViewGroup = (SlideUpViewGroup) rootView.findViewById(R.id.newsVideosLayout);
        mSlideUpViewGroup.minimize();

        mFloatingActionButton = (FloatingActionButton) rootView.findViewById(R.id.fab_news);
        mFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("onClickFAB", "true");
                mSlideUpViewGroup.setVisibility(View.VISIBLE);
                mSlideUpViewGroup.maximize();
            }
        });
        // Inflate the layout for this fragment
        return rootView;
    }

    private int position = 0;

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
        getView().setFocusableInTouchMode(true);
        getView().requestFocus();
        getView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                final View decorView = getActivity().getWindow().getDecorView();
                decorView.setSystemUiVisibility(getView().SYSTEM_UI_FLAG_VISIBLE);
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

    private void setupViewPager(ViewPager viewPager) {
        adapter = new NewsSwipeFragmentAdapter(getChildFragmentManager());
        Log.d("mNewsItems", String.valueOf(mNewsItems.size()));
        for (int i = 0; i < mNewsItems.size(); i++) {
            NewsFragmentItem newsFragmentItem = new NewsFragmentItem();
            newsFragmentItem.setNewsItem(mNewsItems.get(i));
            adapter.addFragment(newsFragmentItem);
        }
        viewPager.setAdapter(adapter);
    }

    private void jumpToNews(int position) {
        Log.d("jumpToNews", String.valueOf(position));
        mViewPager.setCurrentItem(position);
    }

    private int currentPosition;

    public void hideFloatButton() {
        mFloatingActionButton.clearAnimation();
        // Scale down animation
        //hide button
        ScaleAnimation shrink = new ScaleAnimation(1f, 0.2f, 1f, 0.2f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        shrink.setDuration(150);     // animation duration in milliseconds
        shrink.setInterpolator(new DecelerateInterpolator());
        shrink.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                mFloatingActionButton.setVisibility(View.GONE);
                isFloatButtonVisible = false;
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
        mFloatingActionButton.startAnimation(shrink);

    }

    private boolean isFloatButtonVisible;

    public void showFloatButton() {
        mFloatingActionButton.clearAnimation();
        // Scale down animation
        //hide button
        ScaleAnimation expand = new ScaleAnimation(0.2f, 1f, 0.2f, 1f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        expand.setDuration(100);     // animation duration in milliseconds
        expand.setInterpolator(new AccelerateInterpolator());
        expand.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                mFloatingActionButton.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
        if (isFloatButtonVisible == false) {
            mFloatingActionButton.startAnimation(expand);
        }
    }


    @Override
    public void onSwipeUp() {
        mFloatingActionButton.animate().translationY(mFloatingActionButton.getHeight() * 1.5f).setInterpolator(new AccelerateInterpolator(2)).start();
    }

    @Override
    public void onSwipeDown() {
        mFloatingActionButton.animate().translationY(0).setInterpolator(new DecelerateInterpolator(2)).start();
    }
}
