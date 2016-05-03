package com.car.yubangapk.view.ScrollviewNavigationTabNoViewPager;

import android.app.Activity;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.andy.android.yubang.R;



public class ScrollTabsAdapter1 extends TabAdapter1
{
	private Activity activity;

	DisplayMetrics dm;

	public ScrollTabsAdapter1(Activity activity)
	{
		super();
		// TODO Auto-generated constructor stub
		this.activity = activity;

		dm = new DisplayMetrics();
		activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
	}

	@Override
	public View getView(int position)
	{
		// TODO Auto-generated method stub
		LayoutInflater inflater = LayoutInflater.from(activity);
		View view = inflater.inflate(R.layout.first_page_header_tabs, null);
		RelativeLayout relativeLayout = (RelativeLayout) view.findViewById(R.id.my_order_layout_1);
		TextView tab_name = (TextView) view.findViewById(R.id.tab_name);
		View indicator1 = view.findViewById(R.id.indicator1);
		tab_name.setWidth(dm.widthPixels / 3);
		tab_name.setText(tabsList.get(position).getTabDisplayName());
		indicator1.setMinimumWidth(dm.widthPixels / 3);
		return view;
	}

}
