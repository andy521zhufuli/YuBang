package com.car.yubangapk.view.ScrollviewNavigationTabNoViewPager;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import java.util.ArrayList;

/**
 * Created by andy on 16/5/3.
 */
public class FragmentAdapter
{
    private final ArrayList<Fragment> mFragments=new ArrayList<Fragment>();
    private FragmentManager mFragmentManager;

    public FragmentAdapter(FragmentManager fm)
    {
        this.mFragmentManager = fm;
    }

    public void addFragment(Fragment fg){
        mFragments.add(fg);
    }
    public Fragment getItem(int position)
    {
        return mFragments.get(position);
    }

    public int getCount()
    {
        return mFragments.size();
    }
}
