package com.tnaapp.tnalayout.activity;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FragmentDrawer.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FragmentDrawer#newInstance} factory method to
 * create an instance of this fragment.
 */

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;

import com.tnaapp.tnalayout.R;
import com.tnaapp.tnalayout.adapter.NavigationDrawerAdapter;
import com.tnaapp.tnalayout.model.NavigationDrawerItem;
import com.tnaapp.tnalayout.model.RoundedImageView;

/**
 * Created by dfChicken on 02/10/2015.
 */

public class FragmentDrawer extends Fragment {
    private static String TAG = FragmentDrawer.class.getSimpleName();

    private RecyclerView mDrawerList;
    private ActionBarDrawerToggle mActionBarDrawerToggle;
    private DrawerLayout mDrawerLayout;
    private NavigationDrawerAdapter mNavigationDrawerAdapter;
    private View mFragmentContainerView;
    private FragmentDrawerListener mFragmentDrawerListener;
    public static TextView mUserName;
    public static RoundedImageView mUserImage;
    private ImageView userIcon;
    private static String[] titles = null;
    private RelativeLayout mLoginLayout;
    private ObjectAnimator mLoginLayoutAnimation = null;

    public FragmentDrawer() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflating view layout
        View layout = inflater.inflate(R.layout.fragment_navigation_drawer, container, false);
        mDrawerList = (RecyclerView) layout.findViewById(R.id.drawerList);

        mNavigationDrawerAdapter = new NavigationDrawerAdapter(getData());
        mDrawerList.setAdapter(mNavigationDrawerAdapter);
        mDrawerList.setLayoutManager(new LinearLayoutManager(getActivity()));
        mDrawerList.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), mDrawerList, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
                mFragmentDrawerListener.onDrawerItemSelected(view, position);
                mDrawerLayout.closeDrawer(mFragmentContainerView);
            }

            @Override
            public void onLongClick(View view, int position) {
//                mFragmentDrawerListener.onDrawerItemSelected(view, position);
//                mDrawerLayout.closeDrawer(mFragmentContainerView);
            }
        }));
        mLoginLayout = (RelativeLayout) layout.findViewById(R.id.login_layout);
        //ẩn login layout
        setShowHideLoginLayout(true);
        userIcon = (ImageView) layout.findViewById(R.id.user_icon);
        userIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setShowHideLoginLayout(mLoginLayout.isShown());
            }
        });
        mUserName = (TextView) layout.findViewById(R.id.user_name);
        mUserImage = (RoundedImageView)layout.findViewById(R.id.user_icon);

        return layout;
    }

    public void setFragmentDrawerListener(FragmentDrawerListener listener) {
        this.mFragmentDrawerListener = listener;
    }

    public interface FragmentDrawerListener {
        public void onDrawerItemSelected(View view, int position);
    }

    public static List<NavigationDrawerItem> getData() {
        List<NavigationDrawerItem> data = new ArrayList<>();

        // preparing navigation drawer items
        for (int i = 0; i < titles.length; i++) {
            NavigationDrawerItem navItem = new NavigationDrawerItem();
            navItem.setTitle(titles[i]);
            data.add(navItem);
        }
        return data;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // drawer labels
        titles = getActivity().getResources().getStringArray(R.array.nav_drawer_labels);
    }


    //đặt animation cho login layout - login button
    public void setShowHideLoginLayout(boolean b) {
        if (b) {
            mLoginLayoutAnimation = ObjectAnimator.ofFloat(
                    mLoginLayout, "alpha", 0.0f).setDuration(250);
        } else {
            mLoginLayout.setVisibility(View.VISIBLE);
            mLoginLayoutAnimation = ObjectAnimator.ofFloat(
                    mLoginLayout, "alpha", 1.0f).setDuration(250);
        }
        mLoginLayoutAnimation.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                if (mLoginLayout.getAlpha() <= 0.0f) {
                    mLoginLayout.setVisibility(View.GONE);
                } else {
                    mLoginLayout.setVisibility(View.VISIBLE);
                }
            }
        });
        mLoginLayoutAnimation.start();
    }

    public void setUp(int fragmentId, DrawerLayout drawerLayout, final Toolbar toolbar) {
        mFragmentContainerView = getActivity().findViewById(fragmentId);
        mDrawerLayout = drawerLayout;
        mActionBarDrawerToggle = new ActionBarDrawerToggle(getActivity(), drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close) {

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                if(!isAdded()) return;
                getActivity().invalidateOptionsMenu();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                if(!isAdded()) return;
                getActivity().invalidateOptionsMenu();
                //ẩn login layout khi đóng fragment
                setShowHideLoginLayout(true);
            }
        };

        mDrawerLayout.post(new Runnable() {
            @Override
            public void run() {
                mActionBarDrawerToggle.syncState();
            }
        });
        mDrawerLayout.setDrawerListener(mActionBarDrawerToggle);
    }

    public void openDrawer() {
        new Handler().postDelayed(new Runnable() {
            public void run() {
                mDrawerLayout.openDrawer(mFragmentContainerView);
            }
        }, 0);
    }

    public void closeDrawer() {
        new Handler().postDelayed(new Runnable() {
            public void run() {
                mDrawerLayout.closeDrawer(mFragmentContainerView);
            }
        }, 0);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    public void selectItem(int position) {
        if (mDrawerLayout != null) {
            mDrawerLayout.closeDrawer(mFragmentContainerView);
        }
        ((NavigationDrawerAdapter) mDrawerList.getAdapter()).selectPosition(position);
    }

    public boolean isDrawerOpen() {
        return mDrawerLayout != null && mDrawerLayout.isDrawerOpen(mFragmentContainerView);
    }

    public DrawerLayout getDrawerLayout() {
        return mDrawerLayout;
    }

    public void setDrawerLayout(DrawerLayout drawerLayout) {
        mDrawerLayout = drawerLayout;
    }

    private interface ClickListener {
        void onClick(View view, int position);

        void onLongClick(View view, int position);
    }

    static class RecyclerTouchListener implements RecyclerView.OnItemTouchListener {

        private GestureDetector gestureDetector;
        private ClickListener clickListener;

        public RecyclerTouchListener(Context context, final RecyclerView recyclerView, final ClickListener clickListener) {
            this.clickListener = clickListener;
            gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }

                @Override
                public void onLongPress(MotionEvent e) {
                    View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
                    if (child != null && clickListener != null) {
                        clickListener.onLongClick(child, recyclerView.getChildPosition(child));
                    }
                }
            });
        }

        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
            View child = rv.findChildViewUnder(e.getX(), e.getY());
            if (child != null && clickListener != null && gestureDetector.onTouchEvent(e)) {
                clickListener.onClick(child, rv.getChildPosition(child));
            }
            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {
        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

        }
    }

}
