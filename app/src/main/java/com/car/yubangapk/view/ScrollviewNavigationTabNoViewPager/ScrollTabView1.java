package com.car.yubangapk.view.ScrollviewNavigationTabNoViewPager;


import android.content.Context;
import android.graphics.Color;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.andy.android.yubang.R;
import com.car.yubangapk.utils.toastMgr;
import com.car.yubangapk.view.ScrollNavigationTab.TabAdapter;


public class ScrollTabView1 extends HorizontalScrollView {

	private TabAdapter1 tabAdapter;
 
	private Context mContext;
	
	private LinearLayout container;
	
	private FragmentAdapter mFragmentAdapter;

	private OnItemClickListener mOnClickListener;

	public interface OnItemClickListener
	{
		void onTabItemClick(View view, int pos);
	}

	public void setOnItemClickListener(OnItemClickListener mOnClickListener)
	{
		this.mOnClickListener = mOnClickListener;
	}

	public ScrollTabView1(Context context) {
		this(context,null);
		// TODO Auto-generated constructor stub
	}

	public ScrollTabView1(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
		// TODO Auto-generated constructor stub
	}
	
	public ScrollTabView1(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
		mContext=context;
		
		container=new LinearLayout(mContext);
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

    public void setFragments(FragmentAdapter adapter)
    {
        this.mFragmentAdapter = adapter;
    }
    public FragmentAdapter getFragments()
    {
        return mFragmentAdapter;
    }

	
	private void initTabs(){
		 for(int i=0;i<tabAdapter.getCount();i++){
			 final int position=i;
			 final View tab=tabAdapter.getView(i);
			 container.addView(tab);
			 
			 
			 tab.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					selectedTab(position);
					if (mOnClickListener != null)
					{
						mOnClickListener.onTabItemClick(tab, position);
                        //应该是在这里才去处理显示的问题   而不是在Activity里面
					}
				}
				 
			 });
		 }


		//默认选中0
		 selectedTab(0);
	}

	
	public void selectedTab(int position){
		for(int i=0;i<container.getChildCount();i++)
		{
			container.getChildAt(i).setSelected(position == i);
			View view = container.getChildAt(i);
			TextView tabname = (TextView) view.findViewById(R.id.tab_name);
			View ind = view.findViewById(R.id.indicator1);
			if (position == i)
			{
				tabname.setTextColor(Color.parseColor("#ff0000"));
				ind.setVisibility(View.VISIBLE);
			}
			else
			{
				tabname.setTextColor(Color.parseColor("#4f4f4f"));
				ind.setVisibility(View.INVISIBLE);
			}

		}
		
		int w=container.getChildAt(0).getWidth();
		
		smoothScrollTo(w*(position-1), 0);			//不用考虑position=0,scrollTo(-x,0)相当于scrollTo(0,0)滚动不会超过视图边界

        //在这里 响应的去显示Fragment
	}



}
