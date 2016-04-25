package com.car.yubangapk.adapter;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import com.andy.android.yubang.R;


public class ScrollTabsAdapter extends TabAdapter
{
	private Activity activity;

	DisplayMetrics dm;

	public ScrollTabsAdapter(Activity activity)
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
		LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		Button button = (Button) inflater.inflate(R.layout.my_order_header_tabs, null);

		button.setWidth(dm.widthPixels / 3);
		button.setText(tabsList.get(position));
		return button;
	}

}
