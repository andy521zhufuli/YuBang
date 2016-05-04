package com.car.yubangapk.view.ScrollviewNavigationTabNoViewPager;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by andy on 16/5/3.
 */
public class FragmentAdapter
{
    private final ArrayList<Fragment> mFragments=new ArrayList<Fragment>();

    private final HashMap<String, Fragment> mFragmentMap = new HashMap<>();

    private FragmentManager mFragmentManager;

    public FragmentAdapter(FragmentManager fm)
    {
        this.mFragmentManager = fm;
    }

    public void addFragment(Fragment fg){
        mFragments.add(fg);
    }

    public void addFragment(Fragment fg, String tag)
    {
        mFragmentMap.put(tag, fg);
    }

    public Map getFragments()
    {
        return mFragmentMap;
    }


    public Fragment getItem(int position)
    {
        return mFragments.get(position);
    }

    public int getCount()
    {
        return mFragments.size();
    }

    public FragmentManager getmFragmentManager()
    {
        return mFragmentManager;
    }
}
