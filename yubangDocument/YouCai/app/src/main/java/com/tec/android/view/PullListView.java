package com.tec.android.view;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ListView;

import com.handmark.pulltorefresh.library.extras.PullToRefreshWebView2;

/**
 * 下拉时具有弹性的ListView
 *
 * @author andyzhu
 * @version 1.0
 * @created 2015-07-30
 */

public class PullListView extends ListView implements OnScrollListener{

    private static final String TAG = "PullListView";

    //下拉因子,实现下拉时的延迟效果
    private static final float PULL_FACTOR = 0.6F;

    //回弹时每次减少的高度
    private static final int PULL_BACK_REDUCE_STEP = 1;

    //回弹时递减headview高度的频率, 注意以纳秒为单位
    private static final int PULL_BACK_TASK_PERIOD = 700;


    //记录下拉的起始点
    private boolean isXiaLaRecored;
    //记录上拉的起始点
    private boolean isShangLaRecored;

    //记录刚开始下拉时的触摸位置的Y坐标
    private int xiaLaStartY;
    //记录刚开始上拉时的触摸位置的Y坐标
    private int shangLaStartY;

    //第一个可见条目的索引
    private int firstItemIndex;
    //最后一个课件条目的索引
    private boolean lastItemIndex;

    //用于实现下拉弹性效果的headerView
    private View headerView;
    //用于实现上拉弹性效果的footerView
    private View footerView;

    private int currentScrollState;

    //实现回弹效果的调度器
    private ScheduledExecutorService  schedulor;

    //实现回弹效果的handler,用于递减headview的高度并请求重绘
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what)
            {
                case 1:
                    AbsListView.LayoutParams params = (LayoutParams) headerView.getLayoutParams();

                    //递减高度
                    params.height -= PULL_BACK_REDUCE_STEP;

                    headerView.setLayoutParams(params);

                    //重绘
                    headerView.invalidate();

                    //停止回弹时递减headView高度的任务
                    if(params.height <= 0){
                        schedulor.shutdownNow();
                    }
                    break;
                case 2:
                    AbsListView.LayoutParams params1 = (LayoutParams) footerView.getLayoutParams();

                    //递减高度
                    params1.height -= PULL_BACK_REDUCE_STEP;

                    footerView.setLayoutParams(params1);

                    //重绘
                    footerView.invalidate();

                    //停止回弹时递减headView高度的任务
                    if(params1.height <= 0){
                        schedulor.shutdownNow();
                    }
                    break;
                default:
                    break;
            }
        }
    };

    /**
     * 构造函数
     * @param context
     */
    public PullListView(Context context) {
        super(context);

        init();

    }

    /**
     * 构造函数
     * @param context
     * @param attr
     */
    public PullListView(Context context, AttributeSet attr) {
        super(context, attr);

        init();
    }


    /**
     * 初始化
     */
    private void init() {
        //监听滚动状态
        setOnScrollListener(this);

        //创建PullListView的headview
        headerView = new View(this.getContext());
        //创建PullListView的footerview
        footerView = new View(this.getContext());

        //默认白色背景,可以改变颜色, 也可以设置背景图片
        headerView.setBackgroundColor(Color.WHITE);
        footerView.setBackgroundColor(Color.WHITE);

        //默认高度为0 宽度为充满父控件
        headerView.setLayoutParams(new AbsListView.LayoutParams(LayoutParams.FILL_PARENT, 0));
        footerView.setLayoutParams(new AbsListView.LayoutParams(LayoutParams.FILL_PARENT, 0));

        //父类方法
        this.addHeaderView(headerView);
        this.addFooterView(footerView);
    }



    /**
     * 覆盖onTouchEvent方法,实现下拉回弹效果
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:

                //记录下拉起点状态
                if (firstItemIndex == 0 ) {
                    isXiaLaRecored = true;
                    xiaLaStartY = (int) event.getY();
                }
//                if (lastItemIndex)
//                {
//                    isShangLaRecored = true;
//                    shangLaStartY = (int) event.getY();
//                }

                break;

            case MotionEvent.ACTION_CANCEL:

            case MotionEvent.ACTION_UP:


                if(!isXiaLaRecored){
                    break;
                }
                else {

                    //以一定的频率递减headview的高度,实现平滑回弹
                    schedulor = Executors.newScheduledThreadPool(1);
                    schedulor.scheduleAtFixedRate(new Runnable() {

                        @Override
                        public void run() {
                            Message msg = new Message();
                            msg.what = 1;
                            handler.sendMessage(msg);

                        }
                    }, 0, PULL_BACK_TASK_PERIOD, TimeUnit.NANOSECONDS);

                    isXiaLaRecored = false;
                }
//                if (!isShangLaRecored)
//                {
//                    break;
//                }
//                else
//                {
//                    schedulor = Executors.newScheduledThreadPool(1);
//                    schedulor.scheduleAtFixedRate(new Runnable() {
//                        @Override
//                        public void run() {
//                            Message msg = new Message();
//                            msg.what = 1;
//                            handler.sendMessage(msg);
//                        }
//                    },0, PULL_BACK_TASK_PERIOD, TimeUnit.NANOSECONDS);
//                    isShangLaRecored = false;
//                }
                break;


            case MotionEvent.ACTION_MOVE:

                if (!isXiaLaRecored && firstItemIndex == 0 ) {
                    isXiaLaRecored = true;
                    xiaLaStartY = (int) event.getY();
                }
//                if (!isShangLaRecored && lastItemIndex)
//                {
//                    isShangLaRecored = true;
//                    shangLaStartY = (int) event.getY();
//                }

                if(!isXiaLaRecored){
                    break;
                }
                else
                {
                    int tempY = (int) event.getY();
                    int moveY = tempY - xiaLaStartY;
                    //moveY < 0 表示向下滑动
                    //moveY > 0 表示向上滑动
                    if (moveY < 0) {
                        isXiaLaRecored = false;
                        break;
                    }

                    headerView.setLayoutParams(new AbsListView.LayoutParams(LayoutParams.FILL_PARENT, (int) (moveY * PULL_FACTOR)));
                    headerView.invalidate();
                }

//                if (!isShangLaRecored)
//                {
//                    break;
//                }
//                else
//                {
//                    int tempY = (int) event.getY();
//                    int moveY = tempY - shangLaStartY;
//                    if (moveY > 0)
//                    {
//                        isShangLaRecored = false;
//                        break;
//                    }
//                    footerView.setLayoutParams(new AbsListView.LayoutParams(LayoutParams.FILL_PARENT, (int) (moveY * PULL_FACTOR)));
//                    footerView.invalidate();
//                }
                break;
        }
        return super.onTouchEvent(event);
    }



    public void onScroll(AbsListView view, int firstVisiableItem,
                         int visibleItemCount, int totalItemCount) {
        firstItemIndex = firstVisiableItem;
        //总的数目 - 可见的数目, 如果等于第一个可见的  那么就是list已经到底了
        if( (totalItemCount - visibleItemCount) == firstVisiableItem)
        {
            lastItemIndex = true;
        }
        else
        {
            lastItemIndex = false;//否则 就没有到底
        }

    }

    public void onScrollStateChanged(AbsListView view, int scrollState) {
        currentScrollState = scrollState;
    }

}