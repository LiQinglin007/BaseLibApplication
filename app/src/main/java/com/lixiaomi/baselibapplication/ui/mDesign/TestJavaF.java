package com.lixiaomi.baselibapplication.ui.mDesign;

import android.support.annotation.NonNull;
import android.support.v4.view.NestedScrollingParent;
import android.view.View;

/**
 * 作者：dell or Xiaomi Li
 * 时间： 2018/4/18
 * 内容：
 * 最后修改：
 */

public class TestJavaF implements NestedScrollingParent {
    @Override
    public boolean onStartNestedScroll(@NonNull View child, @NonNull View target, int axes) {
        return false;
    }

    @Override
    public void onNestedScrollAccepted(@NonNull View child, @NonNull View target, int axes) {

    }

    @Override
    public void onStopNestedScroll(@NonNull View target) {

    }

    @Override
    public void onNestedScroll(@NonNull View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed) {

    }

    @Override
    public void onNestedPreScroll(@NonNull View target, int dx, int dy, @NonNull int[] consumed) {

    }

    @Override
    public boolean onNestedFling(@NonNull View target, float velocityX, float velocityY, boolean consumed) {
        return false;
    }

    @Override
    public boolean onNestedPreFling(@NonNull View target, float velocityX, float velocityY) {
        return false;
    }

    @Override
    public int getNestedScrollAxes() {
        return 0;
    }
}
