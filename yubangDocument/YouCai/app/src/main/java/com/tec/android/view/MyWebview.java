package com.tec.android.view;

import android.content.Context;
import android.util.AttributeSet;
import android.webkit.WebView;

/**
 * Created by andy on 15/8/15.
 */
public class MyWebview extends WebView {

    ScrollInterface scrollInterface;
    OnScrollListener onScrollListener;
    public MyWebview(Context context) {
        super(context);
    }

    public MyWebview(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyWebview(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        scrollInterface.onSChanged(l, t, oldt, oldt);
    }

    public void setOnCustomScroolChangeListener(ScrollInterface t){
        this.scrollInterface = t;
    }

    public void setOnScrollListener(OnScrollListener onScrollListener) {
        this.onScrollListener = onScrollListener;
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        if (onScrollListener != null) {
            onScrollListener.onScroll(this, getScrollX(), getScrollY());
        }
    }

    public interface ScrollInterface {
        public void onSChanged(int l, int t, int oldl, int oldt) ;
    }
    public interface OnScrollListener {
        void onScroll(WebView wv, int scrollX, int scrollY);
    }





}

