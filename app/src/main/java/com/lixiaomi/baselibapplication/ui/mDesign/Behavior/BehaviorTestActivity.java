package com.lixiaomi.baselibapplication.ui.mDesign.Behavior;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.lixiaomi.baselibapplication.R;
import com.lixiaomi.baselibapplication.ui.baseui.XMBaseActivity;
import com.lixiaomi.mvplib.base.BasePresenter;


/**
 * 作者：dell or Xiaomi Li
 * 时间： 2018/4/18
 * 内容：
 * 最后修改：
 */

public class BehaviorTestActivity extends XMBaseActivity {
    Button mButton;
    TextView mTextView;

    @Override
    protected Object setLayout() {
        return R.layout.activity_behavior;
    }



    @Override
    protected BasePresenter createPersenter() {
        return null;
    }

    @Override
    protected int setStatusBarColor() {
        return R.color.default_color;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        mButton = findViewById(R.id.behavior_but);
        mTextView = findViewById(R.id.behavior_tv);

        mButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_MOVE:
                        v.setX(event.getRawX() - v.getWidth() / 2);
                        v.setY(event.getRawY() - v.getHeight() / 2);
                        break;
                }
                return false;
            }
        });
    }
}
