package com.lixiaomi.baselib.utils;

import android.os.CountDownTimer;
import android.widget.TextView;

/**
 * @describe：短信验证码倒计时<br>
 * @author：Xiaomi<br>
 * @createTime：2018/5/9<br>
 * @remarks：统一修改变量命名规则<br>
 * @changeTime:2019/2/11<br>
 */
public class CountDownUtils {
    /**
     * 剩余时间
     */
    public static int mTime = 60;

    /**
     * 开始计时
     *
     * @param codetv 显示倒计时的tv
     */
    public static void startTime(final TextView codetv) {
        mTime = 60;
        new CountDownTimer(60 * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                String timeStr = mTime > 9 ? mTime + "" : "0" + mTime;
                codetv.setText("剩余" + timeStr + "秒");
                mTime--;
                codetv.setClickable(false);
            }

            @Override
            public void onFinish() {
                codetv.setText("获取验证码");
                codetv.setClickable(true);
            }
        }.start();
    }
}
