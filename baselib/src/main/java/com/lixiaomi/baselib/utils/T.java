package com.lixiaomi.baselib.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * @describe：Toast工具类<br>
 * @author：Xiaomi<br>
 * @createTime：2016/10/11<br>
 * @remarks：统一修改变量命名规则<br>
 * @changeTime:2019/2/11<br>
 */
public class T {
    static Toast mToast;

    /**
     * 短提示
     *
     * @param mContext 上下文
     * @param msg      提示内容
     */
    public static void shortToast(Context mContext, String msg) {
        if (mToast != null) {
            mToast.cancel();
            mToast = null;
        }
        mToast = Toast.makeText(mContext, msg + "", Toast.LENGTH_SHORT);
        mToast.show();
    }

    /**
     * 长提示
     *
     * @param mContext 上下文
     * @param msg      提示内容
     */
    public static void longToast(Context mContext, String msg) {
        if (mToast != null) {
            mToast.cancel();
            mToast = null;
        }
        mToast = Toast.makeText(mContext, msg + "", Toast.LENGTH_LONG);
        mToast.show();
    }
}
