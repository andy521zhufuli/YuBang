package com.car.yubangapk.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.GridView;

/**
 * Created by andy on 16/2/24.
 * 禁止滚动的GridView
 */
public class ForbiddenScrollGridView extends GridView {
    public ForbiddenScrollGridView(Context context) {
        super(context);
    }
    public ForbiddenScrollGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ForbiddenScrollGridView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    //通过重新dispatchTouchEvent方法来禁止滑动
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        //这里不消化滑动时间  返回给父亲去处理
        if(ev.getAction() == MotionEvent.ACTION_MOVE){
            return true;//禁止Gridview进行滑动
        }
        return super.dispatchTouchEvent(ev);
    }
}
