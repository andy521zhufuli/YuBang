package com.android.andy.yubang.util;


import android.graphics.Bitmap;
import android.view.View;

/**
 * Created by andy on 16/3/4.
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