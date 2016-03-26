package com.car.yubangapk.ui;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;

import com.car.yubangapk.adapter.TestBaseAdapter;
import com.car.yubangapk.utils.toastMgr;
import com.andy.android.yubang.R;
import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.ValueAnimator;

import java.util.WeakHashMap;

import se.emilsjolander.stickylistheaders.ExpandableStickyListHeadersListView;
import se.emilsjolander.stickylistheaders.StickyListHeadersListView;

/**
 * RegisterDetailChooseCarInfoActivity: 注册详情  选择车辆信息 提交验证资料
 * 即注册细节
 *
 * @author andyzhu
 * @version 1.0
 * @created 2016-02-26
 */
public class RegisterDetailChooseCarInfoActivity extends BaseActivity {

    private ExpandableStickyListHeadersListView mListView;
    TestBaseAdapter mTestBaseAdapter;
    WeakHashMap<View,Integer> mOriginalViewHeightPool = new WeakHashMap<View, Integer>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_register_detail_choose_car_info);

        mListView = (ExpandableStickyListHeadersListView) findViewById(R.id.register_detail_choose_car_info_stickylistheaders_list);
        //custom expand/collapse animation
        mListView.setAnimExecutor(new AnimationExecutor());
        mTestBaseAdapter = new TestBaseAdapter(this);
        mListView.setAdapter(mTestBaseAdapter);
        //
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                toastMgr.builder.display("item " + i + "clicked", 0);
            }
        });
        //
        mListView.setOnHeaderClickListener(new StickyListHeadersListView.OnHeaderClickListener() {
            @Override
            public void onHeaderClick(StickyListHeadersListView l, View header, int itemPosition, long headerId, boolean currentlySticky) {
                if (mListView.isHeaderCollapsed(headerId)) {
                    mListView.expand(headerId);
                } else {
                    mListView.collapse(headerId);
                }
            }
        });
    }

    //animation executor
    class AnimationExecutor implements ExpandableStickyListHeadersListView.IAnimationExecutor {

        @Override
        public void executeAnim(final View target, final int animType) {
            if(ExpandableStickyListHeadersListView.ANIMATION_EXPAND == animType&&target.getVisibility() == View.VISIBLE){
                return;
            }
            if(ExpandableStickyListHeadersListView.ANIMATION_COLLAPSE == animType&&target.getVisibility() != View.VISIBLE){
                return;
            }
            if(mOriginalViewHeightPool.get(target)==null){
                mOriginalViewHeightPool.put(target,target.getHeight());
            }
            final int viewHeight = mOriginalViewHeightPool.get(target);
            float animStartY = animType == ExpandableStickyListHeadersListView.ANIMATION_EXPAND ? 0f : viewHeight;
            float animEndY = animType == ExpandableStickyListHeadersListView.ANIMATION_EXPAND ? viewHeight : 0f;
            final ViewGroup.LayoutParams lp = target.getLayoutParams();
            ValueAnimator animator = ValueAnimator.ofFloat(animStartY, animEndY);
            animator.setDuration(200);
            target.setVisibility(View.VISIBLE);
            animator.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animator) {
                }

                @Override
                public void onAnimationEnd(Animator animator) {
                    if (animType == ExpandableStickyListHeadersListView.ANIMATION_EXPAND) {
                        target.setVisibility(View.VISIBLE);
                    } else {
                        target.setVisibility(View.GONE);
                    }
                    target.getLayoutParams().height = viewHeight;
                }

                @Override
                public void onAnimationCancel(Animator animator) {

                }

                @Override
                public void onAnimationRepeat(Animator animator) {

                }
            });
            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    lp.height = ((Float) valueAnimator.getAnimatedValue()).intValue();
                    target.setLayoutParams(lp);
                    target.requestLayout();
                }
            });
            animator.start();

        }
    }

}
