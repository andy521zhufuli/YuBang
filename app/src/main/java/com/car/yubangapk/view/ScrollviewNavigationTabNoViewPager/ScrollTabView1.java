package com.car.yubangapk.view.ScrollviewNavigationTabNoViewPager;


import android.content.Context;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.andy.android.yubang.R;
import com.car.yubangapk.json.bean.Json2FirstPageShopBean;
import com.car.yubangapk.json.bean.Json2FirstPageTabsBean;
import com.car.yubangapk.ui.firstpagefragment.FirstPageFragment;
import com.car.yubangapk.utils.toastMgr;
import com.car.yubangapk.view.ScrollNavigationTab.TabAdapter;

import java.util.Iterator;
import java.util.List;
import java.util.Map;


public class ScrollTabView1 extends HorizontalScrollView {

    private TabAdapter1 tabAdapter;

    private Context mContext;

    private LinearLayout container;

    private FragmentAdapter mFragmentAdapter;

    private OnItemClickListener mOnClickListener;

    public interface OnItemClickListener {
        void onTabItemClick(TabAdapter1 tabAdapter, int pos);
    }

    public void setOnItemClickListener(OnItemClickListener mOnClickListener) {
        this.mOnClickListener = mOnClickListener;
    }

    public ScrollTabView1(Context context) {
        this(context, null);

    }

    public ScrollTabView1(Context context, AttributeSet attrs) {
        this(context, attrs, 0);

    }

    public ScrollTabView1(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        mContext = context;
        container = new LinearLayout(mContext);
        container.setOrientation(LinearLayout.HORIZONTAL);
        addView(container);
    }

    public TabAdapter1 getAdapter() {
        return tabAdapter;
    }

    public void setAdapter(TabAdapter1 tabAdapter) {
        this.tabAdapter = tabAdapter;

        initTabs();
    }

    public void setFragments(FragmentAdapter adapter) {
        this.mFragmentAdapter = adapter;
    }

    public FragmentAdapter getFragments() {
        return mFragmentAdapter;
    }


    private void initTabs() {
        for (int i = 0; i < tabAdapter.getCount(); i++) {
            final int position = i;
            final View tab = tabAdapter.getView(i);
            container.addView(tab);

            tab.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    selectedTab(position);
                    if (mOnClickListener != null)
                    {
                        mOnClickListener.onTabItemClick(tabAdapter, position);
                    }
                }

            });
        }


        //默认选中0
        selectedTab(0);


        //默认是第一个  也就是第0个按钮是选中的
        if (mOnClickListener != null  )
        {
            mOnClickListener.onTabItemClick(tabAdapter, 0);
        }

    }


    public void selectedTab(int position) {
        for (int i = 0; i < container.getChildCount(); i++) {
            container.getChildAt(i).setSelected(position == i);
            View view = container.getChildAt(i);
            TextView tabname = (TextView) view.findViewById(R.id.tab_name);
            View ind = view.findViewById(R.id.indicator1);
            if (position == i) {
                tabname.setTextColor(Color.parseColor("#009140"));
                ind.setVisibility(View.VISIBLE);
            } else {
                tabname.setTextColor(Color.parseColor("#4f4f4f"));
                ind.setVisibility(View.INVISIBLE);
            }

        }

        int w = container.getChildAt(0).getWidth();

        smoothScrollTo(w * (position - 1), 0);            //不用考虑position=0,scrollTo(-x,0)相当于scrollTo(0,0)滚动不会超过视图边界


        //在这里 响应的去显示Fragment
        //selectFragment(position);

    }

    private void selectFragment(int position)
    {

        Json2FirstPageTabsBean listTabs = tabAdapter.tabsList.get(position);
        String tag = tabAdapter.tabsList.get(position).getTabDisplayName();
        FragmentManager fragmentManager = mFragmentAdapter.getmFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        hideFragment(transaction, tag);
        Fragment fragment1 = getFragmentByTag(tag);
        if (fragment1 == null)
        {
            Fragment fragment = new FirstPageFragment(listTabs, tag);
            mFragmentAdapter.addFragment(fragment, tag);
            transaction.add(R.id.firstpage_framlayout, fragment);
        }
        else
        {
            transaction.show(fragment1);
        }
        transaction.commit();
    }


    /**
     *  用于每一显示不同的Fragment时候隐藏之前的所有可能显示的Fragment
     *  @param transaction
     *           事物
     */
    private void hideFragment(FragmentTransaction transaction, String tag)
    {
        Iterator iter = mFragmentAdapter.getFragments().entrySet().iterator();
        //hide 所有的
        while (iter.hasNext())
        {
            Map.Entry entry = (Map.Entry) iter.next();

            Fragment firstFragment = (Fragment) entry.getValue();
            if (firstFragment != null)
            {
                transaction.replace(R.id.firstpage_framlayout, firstFragment);
//                transaction.hide(firstFragment);
            }
        }
    }



    private Fragment getFragmentByTag(String tag){
        Fragment firstFragment = (Fragment) mFragmentAdapter.getFragments().get(tag);
        return firstFragment;
    }
}
