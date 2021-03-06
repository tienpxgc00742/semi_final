package com.tnaapp.tnalayout.activity.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.transition.Slide;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.ScaleAnimation;
import android.widget.ListView;
import android.widget.TextView;

import com.tnaapp.tnalayout.R;
import com.tnaapp.tnalayout.activity.MainActivity;
import com.tnaapp.tnalayout.adapter.NewsSwipeFragmentAdapter;
import com.tnaapp.tnalayout.adapter.VideosInNewsItemArray;
import com.tnaapp.tnalayout.ai.MyConverter;
import com.tnaapp.tnalayout.ai.News;
import com.tnaapp.tnalayout.control.DraggableViewListener;
import com.tnaapp.tnalayout.control.SlideUpViewGroup;
import com.tnaapp.tnalayout.control.video.CustomPlayerControllerVisibilityListener;
import com.tnaapp.tnalayout.control.video.CustomVideoView;
import com.tnaapp.tnalayout.control.video.DefaultCustomPlayerController;
import com.tnaapp.tnalayout.model.CustomViewPager;
import com.tnaapp.tnalayout.model.DepthPageTransformer;
import com.tnaapp.tnalayout.model.NewsFragmentItem;
import com.tnaapp.tnalayout.model.NewsItem;
import com.tnaapp.tnalayout.tien.model.Client;

import java.util.List;

/**
 * Created by dfChicken on 09/10/2015.
 */
public class SwipeNewsFragment extends Fragment implements CustomPlayerControllerVisibilityListener {
    private static CustomViewPager mViewPager;
    private List<NewsItem> mNewsItems;
    private int viewItem;
    private FloatingActionButton mFloatingActionButton;

    private NewsSwipeFragmentAdapter adapter;

    private NewsFragmentItem current;
    //media player
    private SlideUpViewGroup mSlideUpViewGroup;
    private static CustomVideoView mCustomVideoView;
    private static DefaultCustomPlayerController mController;
    private ListView newsVideosList;
    private VideosInNewsItemArray newsVidsAdapter;
    private TextView newsTitle;


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

//        ((MainActivity) getActivity()).getSupportActionBar().hide();

        final View decorView = getActivity().getWindow().getDecorView();
        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);

        mFloatingActionButton = (FloatingActionButton) rootView.findViewById(R.id.fab_news);
        mFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("onClickFAB", "true");
                mSlideUpViewGroup.setVisibility(View.VISIBLE);
                mSlideUpViewGroup.maximize();
            }
        });

        mViewPager = (CustomViewPager) rootView.findViewById(R.id.viewpager_news);
        mViewPager.setPageTransformer(true, new DepthPageTransformer());
        if (mNewsItems != null) {
            setupViewPager(mViewPager);
            jumpToNews(viewItem);
        }
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                //không cho bấm vào nút float khi đang chuyển trang
                mFloatingActionButton.setEnabled(false);
            }

            @Override
            public void onPageSelected(final int arg0) {
                current = (NewsFragmentItem) adapter.getItem(arg0);
                final Client client = new Client();
                client.setListener(new Client.Listener() {
                    @Override
                    public void doneExecute() {
                        News news = MyConverter.jsonToRootNew(client.result);
                        Log.wtf("SWIPE: ", current.getNewsItem().getId());
                        current.getWebView().loadData(news.getContent(), "text/html; charset=UTF-8", null);
//                        getVideoInNews(current.getNewsItem().getId());
                        if (news.getNewsVideos() != null && news.getNewsVideos().size() > 0) { //khi có video
                            showFloatButton(); // hiện nút float
                            mFloatingActionButton.setEnabled(true); //cho phép bấm vào nút float
                            Log.wtf("VideosInNews", String.valueOf(news.getNewsVideos().size()));
                            setupNewsVideosPlayer(news.getNewsVideos().get(0).getLink());
                            newsVidsAdapter = new VideosInNewsItemArray(getContext(),news.getNewsVideos());
                            newsTitle.setText(current.getNewsItem().getTitle());
                            newsVideosList.setAdapter(newsVidsAdapter);

                        } else { //khi đéo
                            hideFloatButton();
                        }
                    }

                    @Override
                    public void preExcute() {
                    }
                });
                client.execute("http://104.155.237.47/web/api/data?id=" + current.getNewsItem().getId(), "json");
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                if(state ==0){
                    mFloatingActionButton.setEnabled(true);
                }
            }
        });
        //player
        mSlideUpViewGroup = (SlideUpViewGroup) rootView.findViewById(R.id.newsVideosLayout);
        mSlideUpViewGroup.minimize();
        mCustomVideoView = (CustomVideoView) rootView.findViewById(R.id.newsVideosPlayer);
        mController = (DefaultCustomPlayerController) rootView.findViewById(R.id.newsVideosController);
        mController.setVisibilityListener(this);
        mSlideUpViewGroup.setDraggableViewListener(new DraggableViewListener() {
            @Override
            public void onMaximized() {
                Log.d("DraggableView", "Maximized");
            }

            @Override
            public void onMinimized() {
                mCustomVideoView.stopPlayback();
                Log.d("DraggableView", "Minimized");
            }
        });

        newsVideosList = (ListView) rootView.findViewById(R.id.newsVideosList);
        newsTitle = (TextView) rootView.findViewById(R.id.newsTitle);

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
        getView().setFocusableInTouchMode(true);
        getView().requestFocus();
        getView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                final View decorView = getActivity().getWindow().getDecorView();
                decorView.setSystemUiVisibility(getView().SYSTEM_UI_FLAG_VISIBLE);
                if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK) {
                    if(MainActivity.mHomeFragment != null){
                        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);
                        fragmentTransaction.replace(R.id.container_body, MainActivity.mHomeFragment);
                        fragmentTransaction.commit();
                        MainActivity.mFragmentDrawer.selectItem(0);
                        MainActivity.mToolbar.setTitle(getResources().getString(R.string.title_home));
                        return true;
                    }
                }
                return false;
            }
        });
    }

    public void setupNewsVideosPlayer(String url) {
        mCustomVideoView.setMediaController(mController);
        mCustomVideoView.setOnPlayStateListener(mController);
        mCustomVideoView.setVideo(url, DefaultCustomPlayerController.DEFAULT_VIDEO_START);
        mCustomVideoView.start();
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


    //hiệu ứng float button thần thánh
    //mệt vcc
    public void hideFloatButton() {
        mFloatingActionButton.clearAnimation();
        // Scale down animation
        //hide button
        final ScaleAnimation shrink = new ScaleAnimation(1f, 0.2f, 1f, 0.2f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        shrink.setDuration(150);     // animation duration in milliseconds
        shrink.setInterpolator(new DecelerateInterpolator());
        shrink.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                mFloatingActionButton.setVisibility(View.GONE); //ẩn hẳn sau khi thu nhỏ
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
        if (mFloatingActionButton.getVisibility() == View.VISIBLE) {
            if (isFloatButtonScrollHide) { //nếu đang ẩn ở dưới
                mFloatingActionButton.setVisibility(View.GONE); //ẩn hẳn luôn
            } else { //nếu ko
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mFloatingActionButton.startAnimation(shrink); //thu bé lại rồi cũng ẩn (ở trên có đặt gone sau khi hết animation "onAnimationEnd" )
                    }
                }, 200);
            }
        }
    }

    private boolean isNewsVideosAvailable;

    public void showFloatButton() {
        mFloatingActionButton.clearAnimation();
        // Scale down animation
        //hide button
        final ScaleAnimation expand = new ScaleAnimation(0.2f, 1f, 0.2f, 1f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        expand.setDuration(100);     // animation duration in milliseconds
        expand.setInterpolator(new AccelerateInterpolator());
        expand.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                mFloatingActionButton.setVisibility(View.VISIBLE); //nếu bị ẩn, hiện ra
            }

            @Override
            public void onAnimationEnd(Animation animation) {
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
        if (mFloatingActionButton.getVisibility() == View.GONE || mFloatingActionButton.getVisibility() == View.INVISIBLE) {
            if (isFloatButtonScrollHide) { //nếu đang ẩn ở dưới
                onSwipeDown(); //đưa lên trên
                mFloatingActionButton.startAnimation(expand); //rồi phình to
            } else { //nếu đéo
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mFloatingActionButton.startAnimation(expand); //vẫn phình to=))
                    }
                }, 200);
            }
        } else if (mFloatingActionButton.getVisibility() == View.VISIBLE && isFloatButtonScrollHide) {
            onSwipeDown();
            mFloatingActionButton.startAnimation(expand);
        }
    }

    public void enableFloatButton() {
        if (mFloatingActionButton != null) {
            mFloatingActionButton.setEnabled(true);
        }
    }

    //kiểm tra nếu button bị ẩn xuống dưới
    private boolean isFloatButtonScrollHide = false;

    public void onSwipeUp() {
        isFloatButtonScrollHide = true; //ẩn xuống dưới
        Log.d("onSwipeDown", "show float button up." + isFloatButtonScrollHide);
        mFloatingActionButton.animate().translationY(mFloatingActionButton.getHeight() * 1.5f).setInterpolator(new AccelerateInterpolator(2)).start();
    }

    public void onSwipeDown() {
        isFloatButtonScrollHide = false; //ko ẩn xuống dưới
        Log.d("onSwipeDown", "hide float button down." + isFloatButtonScrollHide);
        mFloatingActionButton.animate().translationY(0).setInterpolator(new DecelerateInterpolator(2)).start();
    }

    @Override
    public void onControlsVisibilityChange(boolean value) {

    }

    @Override
    public void requestFullScreen() {

    }
}
