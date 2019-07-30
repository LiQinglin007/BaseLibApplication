package com.lixiaomi.baselibapplication.ui.launcher;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;


import com.lixiaomi.baselib.utils.MiFinalData;
import com.lixiaomi.baselib.utils.PreferenceUtils;
import com.lixiaomi.baselib.utils.timer.BaseTimerTask;
import com.lixiaomi.baselib.utils.timer.ITimerListener;
import com.lixiaomi.baselibapplication.R;
import com.lixiaomi.baselibapplication.ui.baseui.XMBaseActivity;
import com.lixiaomi.baselibapplication.ui.main.MainActivity;
import com.lixiaomi.mvplib.base.BasePresenter;

import java.text.MessageFormat;
import java.util.Timer;

/**
 * 作者：dell or Xiaomi Li
 * 时间： 2018/4/10
 * 内容：启动页
 * 最后修改：
 */

public class LauncherActivity extends XMBaseActivity implements ITimerListener, View.OnClickListener {
    AppCompatTextView mTimerTv;
    private Timer mTimer = null;
    int mCount = 5;//倒计时时间

    @Override
    protected Object setLayout() {
        return R.layout.activity_launcher;
    }

    @Override
    protected int setStatusBarColor() {
        return 0;
    }


    @Override
    protected BasePresenter createPersenter() {
        return null;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        mTimerTv = findViewById(R.id.tv_launcher_timer);
        mTimerTv.setOnClickListener(this);
        init();
    }

    private void init() {
        mTimer = new Timer();
        final BaseTimerTask baseTimerTask = new BaseTimerTask(this);
        mTimer.schedule(baseTimerTask, 0, 1000);
    }

    @Override
    public void onTimer() {
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (mTimerTv != null) {
                    mTimerTv.setText(MessageFormat.format("跳过\n{0}s", mCount));
                    mCount--;
                    if (mCount <= 0) {
                        if (mTimer != null) {
                            mTimer.cancel();
                            mTimer = null;
                            checkIsShowScroll();
                        }
                    }
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_launcher_timer:
                if (mTimer != null) {
                    mTimer.cancel();
                    mTimer = null;
                    checkIsShowScroll();
                }
                break;
            default:
                break;
        }
    }

    //判断是否显示滑动启动页
    private void checkIsShowScroll() {
        boolean aBoolean = PreferenceUtils.getBoolean(MiFinalData.IS_OPEN_APP, false);
        if (aBoolean) {
            startActivity(new Intent(LauncherActivity.this, MainActivity.class));
        } else {
            startActivity(new Intent(LauncherActivity.this, BannerActivity.class));
        }
        finish();
    }
}
