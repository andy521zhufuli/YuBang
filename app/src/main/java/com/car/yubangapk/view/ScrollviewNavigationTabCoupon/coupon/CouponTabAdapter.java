package com.car.yubangapk.view.ScrollviewNavigationTabCoupon.coupon;

import android.view.View;

import com.car.yubangapk.json.bean.Json2FirstPageTabsBean;

import java.util.ArrayList;
import java.util.List;

public abstract class CouponTabAdapter {

	List<String> tabsList = new ArrayList<>();
	public abstract  View getView(int position);
	
	public int getCount(){
		return tabsList.size();
	}

	public List<String> getTabsList()
	{
		return tabsList;
	}
	
	public void add(String name){
		tabsList.add(name);
	}
	 
}
