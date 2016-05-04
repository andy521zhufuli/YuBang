package com.car.yubangapk.view.ScrollviewNavigationTabNoViewPager;

import android.view.View;

import com.car.yubangapk.json.bean.Json2FirstPageTabsBean;

import java.util.ArrayList;
import java.util.List;

public abstract class TabAdapter1 {

	List<Json2FirstPageTabsBean> tabsList = new ArrayList<>();
	public abstract  View getView(int position);
	
	public int getCount(){
		return tabsList.size();
	}

	public List<Json2FirstPageTabsBean> getTabsList()
	{
		return tabsList;
	}
	
	public void add(Json2FirstPageTabsBean name){
		tabsList.add(name);
	}
	 
}
