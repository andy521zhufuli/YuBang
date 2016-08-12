package com.tec.android.utils;

import android.app.Activity;
import android.util.DisplayMetrics;

/**
 * 屏幕信息工具类: 还需要再修改, 因为此项目只需要得到webView的宽和高
 * @author andyzhu
 * @version 1.0
 * @created 2015-07-31
 */

public class ScreenInfo
{

	private Activity activity;
	private int width;
	private int height;
	private float density;
	private int densityDpi;

	public ScreenInfo()
	{

	}

	public ScreenInfo(final Activity activity)
	{
		this.activity = activity;
		init();
	}

	public Activity getActivity()
	{
		return activity;
	}

	private void init()
	{
		final DisplayMetrics metrics = new DisplayMetrics();
		activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
		width = metrics.widthPixels;
		height = metrics.heightPixels;
		density = metrics.density;
		densityDpi = metrics.densityDpi;
	}

	public void setActivity(Activity activity)
	{
		this.activity = activity;
	}

	public int getWidth()
	{
		return width;
	}

	public void setWidth(int width)
	{
		this.width = width;
	}

	public int getHeight()
	{
		return height;
	}

	public void setHeight(int height)
	{
		this.height = height;
	}

	public float getDensity()
	{
		return density;
	}

	public void setDensity(float density)
	{
		this.density = density;
	}

	public int getDensityDpi()
	{
		return densityDpi;
	}

	public void setDensityDpi(int densityDpi)
	{
		this.densityDpi = densityDpi;
	}

}
