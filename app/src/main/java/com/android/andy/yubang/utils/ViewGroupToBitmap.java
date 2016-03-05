package com.android.andy.yubang.utils;


import android.graphics.Bitmap;
import android.view.View;

/**
 * 用来把复杂view  转换成bitmap的 这是给百度地图用的
 */
public class ViewGroupToBitmap {

    public ViewGroupToBitmap(){}

    public Bitmap getViewBitmap(View addViewContent) {


        addViewContent.setDrawingCacheEnabled(true);

        addViewContent.measure(
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        addViewContent.layout(0, 0,
                addViewContent.getMeasuredWidth(),
                addViewContent.getMeasuredHeight());

        addViewContent.buildDrawingCache();
        Bitmap cacheBitmap = addViewContent.getDrawingCache();
        Bitmap bitmap = Bitmap.createBitmap(cacheBitmap);

        return bitmap;
    }
}