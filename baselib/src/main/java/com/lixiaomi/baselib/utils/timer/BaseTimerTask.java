package com.lixiaomi.baselib.utils.timer;

import java.util.TimerTask;

/**
 * @describe：定时器<br>
 * @author：Xiaomi<br>
 * @createTime：2018/3/23<br>
 * @remarks：<br>
 * @changeTime:<br>
 */
public class BaseTimerTask extends TimerTask {

    private ITimerListener mITimerListener;

    public BaseTimerTask(ITimerListener iTimerListener) {
        mITimerListener = iTimerListener;
    }

    @Override
    public void run() {
        if (mITimerListener != null) {
            mITimerListener.onTimer();
        }
    }
}
