package com.tnaapp.tnalayout.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.tnaapp.tnalayout.model.NewsItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dfChicken on 09/10/2015.
 */
public class NewsSwipeFragmentAdapter extends FragmentPagerAdapter {
    private final List<Fragment> mFragmentList = new ArrayList<>();
//    private final List<String> mFragmentTitleList = new ArrayList<>();

    public NewsSwipeFragmentAdapter(FragmentManager manager) {
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
//        mFragmentTitleList.add(title);
    }

}