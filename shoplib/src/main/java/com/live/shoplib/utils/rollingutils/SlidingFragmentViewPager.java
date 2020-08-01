package com.live.shoplib.utils.rollingutils;

import android.content.Context;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import java.util.List;


public class SlidingFragmentViewPager extends FragmentStatePagerAdapter {
    private List<String> strings;
    private List<Fragment> fragments;
    private Fragment mCurrentFragment;

    public SlidingFragmentViewPager(FragmentManager fm, List<String> strings, List<Fragment> fragments, Context context) {
        super(fm);
        if (strings != null) {
            this.strings = strings;
        }
        this.fragments = fragments;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return strings.get(position);
    }

    @Override
    public int getCount() {
        return strings==null?0:strings.size();
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {

    }

    @Override
    public void setPrimaryItem(ViewGroup container, int position, Object object) {
        mCurrentFragment = (Fragment) object;
        super.setPrimaryItem(container, position, object);
    }


    public Fragment getCurrentFragment() {
        return mCurrentFragment;
    }
}
