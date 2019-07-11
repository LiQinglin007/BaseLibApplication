package com.lixiaomi.baselibapplication.ui.mDesign.Behavior;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.View;

/**
 * 作者：dell or Xiaomi Li
 * 时间： 2018/4/18
 * 内容：
 * 最后修改：
 */
public class FloatingBehaivor extends FloatingActionButton.Behavior {

    public FloatingBehaivor(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * 页面开始滑动
     * 开始嵌套滚动的时候被调用，那么这个方法有一个boolean的返回值，是需要我们告诉CoordinatorLayout我这个Behavior要监听的滑动方向，
     * 因为我们是上下滑动时显示/隐藏FAB，所以这里我们返回return nestedScrollAxes == ViewCompat.SCROLL_AXIS_VERTICAL;
     *
     * @param coordinatorLayout
     * @param child
     * @param directTargetChild
     * @param target
     * @param axes
     * @return
     */
    @Override
    public boolean onStartNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull FloatingActionButton child, @NonNull View directTargetChild, @NonNull View target, int axes) {
//        return super.onStartNestedScroll(coordinatorLayout, child, directTargetChild, target, axes);
        return axes == ViewCompat.SCROLL_AXIS_VERTICAL;
    }

    /**
     * 正在滑动
     *
     * @param coordinatorLayout
     * @param child
     * @param target
     * @param dxConsumed
     * @param dyConsumed
     * @param dxUnconsumed
     * @param dyUnconsumed
     */
    @Override
    public void onNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull FloatingActionButton child,
                               @NonNull View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed) {
//        if (dyConsumed > 0 && dyUnconsumed == 0) {
//            System.out.println("上滑中。。。显示");
//            child.setVisibility(View.VISIBLE);
//        }
//        if (dyConsumed == 0 && dyUnconsumed > 0) {
//            child.setVisibility(View.VISIBLE);
//            System.out.println("到边界了还在上滑。。。显示");
//        }
//        if (dyConsumed < 0 && dyUnconsumed == 0) {
//            child.setVisibility(View.VISIBLE);
//            System.out.println("下滑中。。。显示不");
//        }
//        if (dyConsumed == 0 && dyUnconsumed < 0) {
//            child.setVisibility(View.GONE);
//            System.out.println("到边界了，还在下滑。。。不显示");
//        }


        if (((dyConsumed > 0 && dyUnconsumed == 0) || (dyConsumed == 0 && dyUnconsumed > 0))) {// 显示
            child.setVisibility(View.VISIBLE);
        } else if (((dyConsumed < 0 && dyUnconsumed == 0) || (dyConsumed == 0 && dyUnconsumed < 0))) {
            child.setVisibility(View.GONE);
        }

    }


    /**
     * 停止滑动
     *
     * @param coordinatorLayout
     * @param child
     * @param target
     */
    @Override
    public void onStopNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull FloatingActionButton child, @NonNull View target) {
        super.onStopNestedScroll(coordinatorLayout, child, target);
    }
}
