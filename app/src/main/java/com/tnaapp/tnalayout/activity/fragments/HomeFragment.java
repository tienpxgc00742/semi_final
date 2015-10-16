package com.tnaapp.tnalayout.activity.fragments;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.DynamicDrawableSpan;
import android.text.style.ImageSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tnaapp.tnalayout.R;
import com.tnaapp.tnalayout.activity.tabs.HomeTab;
import com.tnaapp.tnalayout.activity.MainActivity;
import com.tnaapp.tnalayout.activity.tabs.NewsTab;
import com.tnaapp.tnalayout.activity.tabs.VideosTab;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dfChicken on 01/10/2015.
 */

public class HomeFragment extends Fragment {
    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private String[] titles = null;
    private int mCurrentTab = ((MainActivity) getActivity()).mCurrentTabSelected;

    public HomeFragment() {
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
//        setRetainInstance(true);
        if(savedInstanceState!=null){
            mCurrentTab = savedInstanceState.getInt("currentTab");
            Log.d("mCurrentTabCreate", String.valueOf(mCurrentTab));
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //code tối ưu - get mảng các label của tab
        titles = getActivity().getResources().getStringArray(R.array.tab_drawer_labels);
    }

    private void setupViewPager(ViewPager viewPager) {
        //sử dụng getChildFragmentManager để điều khiển tab fragment bên trong parent fragment
        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());
        adapter.addFragment(new HomeTab());
        adapter.addFragment(new VideosTab());
        adapter.addFragment(new NewsTab());
        viewPager.setAdapter(adapter);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        Drawable tabIcon;
        private final List<Fragment> mFragmentList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment) {
            mFragmentList.add(fragment);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    tabIcon = getResources().getDrawable(R.drawable.ic_tab_home);
                    break;
                case 1:
                    tabIcon = getResources().getDrawable(R.drawable.ic_tab_videos);
                    break;
                case 2:
                    tabIcon = getResources().getDrawable(R.drawable.ic_tab_news);
                    break;
            }
            SpannableStringBuilder sb = new SpannableStringBuilder("cc"); //đặt ký tự để thay thế
            try {
                tabIcon.setBounds(0, 0, tabIcon.getIntrinsicWidth(), tabIcon.getIntrinsicHeight());
                ImageSpan span = new ImageSpan(tabIcon, DynamicDrawableSpan.ALIGN_BASELINE);
                sb.setSpan(span, 0, 2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                // số 2 ở trên là số ký tự thay thế bằng drawable -> "cc"
            } catch (Exception e) {
                Log.d("Error: ", "set TAB icon error!");
            }
            return sb;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);

        mViewPager = (ViewPager) rootView.findViewById(R.id.viewpager);
        setupViewPager(mViewPager);

        mViewPager.setCurrentItem(mCurrentTab);

        mTabLayout = (TabLayout) rootView.findViewById(R.id.tabs);
        mTabLayout.setupWithViewPager(mViewPager);
        //thay tên Title mỗi lần chuyển tab
        //sử dụng ViewPagerOnTabSelectedListener vì nếu dùng OnTabSelectedListener thì phương thức OnTabSelected mặc định sẽ bị override bởi ViewPager
        mTabLayout.setOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager) {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                super.onTabSelected(tab);
//                String title = null;
                int position = tab.getPosition();
//                switch (position) {
//                    case 0:
//                        title = getString(R.string.tab_home);
//                        break;
//                    case 1:
//                        title = getString(R.string.tab_videos);
//                        break;
//                    case 2:
//                        title = getString(R.string.tab_news);
//                        break;
//                }
                //thay title khi chuyển tab
                getActionBar().setTitle(titles[position]);
                ((MainActivity) getActivity()).mCurrentTabSelected = position;
//                mCurrentTab = position;
//                Log.d("mCurrentTabOnClick", String.valueOf(mCurrentTab));
            }
        });

        checkIfNoInternetConnection(rootView);

        return rootView;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("currentTab", mCurrentTab);
        Log.d("mCurrentTabSave", String.valueOf(mCurrentTab));
    }

    // kiểm tra kết nối mạng
    public void checkIfNoInternetConnection(final View rootView) {
        NetworkInfo info = ((ConnectivityManager) getContext().getSystemService(getContext().CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
        if (info == null) {
            Snackbar snack = Snackbar.make(rootView, getResources().getString(R.string.warning_no_network), Snackbar.LENGTH_INDEFINITE)
                    .setAction(getResources().getString(R.string.refresh), new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            checkIfNoInternetConnection(rootView);
                        }
                    });
            snack.getView().setBackgroundColor(getResources().getColor(R.color.colorPrimary));
            snack.show();
        } else {
            // nếu có kết nối mạng
            // load lại user information
            MainActivity.initLoggedInSession(null);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        Log.d("HomeFragment", "Initialized Home Fragment");

    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.d("HomeFragment", "Desynchronized Home Fragment");
    }

    //lấy action bar từ main activity
    private ActionBar getActionBar() {
        return ((AppCompatActivity) getActivity()).getSupportActionBar();
    }

}