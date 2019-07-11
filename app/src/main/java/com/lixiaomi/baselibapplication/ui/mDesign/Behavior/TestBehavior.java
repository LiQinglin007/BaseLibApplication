package com.lixiaomi.baselibapplication.ui.mDesign.Behavior;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * 作者：dell or Xiaomi Li
 * 时间： 2018/4/18
 * 内容：子view
 * 最后修改：
 */

public class TestBehavior extends CoordinatorLayout.Behavior<TextView> {

    public TestBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, TextView child, View dependency) {
//        return super.layoutDependsOn(parent, child, dependency);
        return dependency instanceof Button;
    }


    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, TextView child, View dependency) {
//        return super.onDependentViewChanged(parent, child, dependency);
        child.setX(dependency.getX() + 200);
        child.setY(dependency.getY() + 200);
        child.setText("TextView在跟随移动");
        return true;
    }
}
