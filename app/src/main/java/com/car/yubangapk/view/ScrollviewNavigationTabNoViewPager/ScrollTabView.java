package com.car.yubangapk.view.ScrollviewNavigationTabNoViewPager;


import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

import com.car.yubangapk.view.ScrollNavigationTab.TabAdapter;


public class ScrollTabView extends HorizontalScrollView {

	private TabAdapter tabAdapter;
 
	private Context mContext;
	
	private LinearLayout container;
	
	private ViewPager viewPager;
	
	public ScrollTabView(Context context) {
		this(context,null);
		// TODO Auto-generated constructor stub
	}

	public ScrollTabView(Context context, AttributeSet attrs) {
		this(context, attrs,0);
		// TODO Auto-generated constructor stub
	}
	
	public ScrollTabView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
		mContext=context;
		
		container=new LinearLayout(mContext);
		container.setOrientation(LinearLayout.HORIZONTAL);
		
		addView(container);
	}

	public TabAdapter getAdapter() {
		return tabAdapter;
	}

	public void setAdapter(TabAdapter tabAdapter) {
		this.tabAdapter = tabAdapter;
		
		
		initTabs();
	}

	
	private void initTabs(){
		 for(int i=0;i<tabAdapter.getCount();i++){
			 final int position=i;
			 View tab=tabAdapter.getView(i);
			 container.addView(tab);
			 
			 
			 tab.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					selectedTab(position);
					viewPager.setCurrentItem(position);
				}
				 
			 });
		 }


		//默认选中0
		 selectedTab(0);
	}

	
	public void selectedTab(int position){
		for(int i=0;i<container.getChildCount();i++){
			container.getChildAt(i).setSelected(position==i);
		}
		
		int w=container.getChildAt(0).getWidth();
		
		smoothScrollTo(w*(position-1), 0);			//不用考虑position=0,scrollTo(-x,0)相当于scrollTo(0,0)滚动不会超过视图边界
	}



}
