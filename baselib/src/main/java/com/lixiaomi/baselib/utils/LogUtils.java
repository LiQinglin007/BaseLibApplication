package com.lixiaomi.baselib.utils;

import android.util.Log;

import com.lixiaomi.baselib.config.AppConfigInIt;
import com.lixiaomi.baselib.config.AppConfigType;

/**
 * @describe：打印日志工具类<br>
 * @author：Xiaomi<br>
 * @createTime：2017/5/9<br>
 * @remarks：统一修改变量命名规则<br>
 * @changeTime:2019/2/11<br>
 */
public class LogUtils {
    /**
     * 规定每段显示的长度（AndroidStudio控制台打印log的最大信息量大小为4k）
     */
    final static int MAX_LENTH = 3000;

    /**
     * 自定义TAG 日志
     *
     * @param tag
     * @param conetnt
     */
    public static void loge(String tag, String conetnt) {
        showLargeLog(tag, conetnt + "###");
    }

    public static void loge(String conetnt) {
        showLargeLog("XiaomiLibrary", conetnt + "###");
    }

    public static void loge(String tag, String... contents) {
        StringBuffer stb = new StringBuffer();
        for (String content : contents) {
            stb.append("\n");
            stb.append(content);
        }
        showLargeLog(tag, stb.toString() + "###");
    }

    public static void loge(String... contents) {
        StringBuffer stb = new StringBuffer();
        for (String content : contents) {
            stb.append("\n");
            stb.append(content);
        }
        showLargeLog("XiaomiLibrary", stb.toString() + "###");
    }

    public static void logd(String tag, String conetnt) {
        showLargeLog(tag, conetnt + "###");
    }

    public static void logd(String conetnt) {
        showLargeLog("XiaomiLibrary", conetnt + "###");
    }

    public static void logd(String tag, String... contents) {
        StringBuffer stb = new StringBuffer();
        for (String content : contents) {
            stb.append("\n");
            stb.append(content);
        }
        showLargeLog(tag, stb.toString() + "###");
    }

    public static void logd(String... contents) {
        StringBuffer stb = new StringBuffer();
        for (String content : contents) {
            stb.append("\n");
            stb.append(content);
        }
        showLargeLog("XiaomiLibrary", stb.toString() + "###");
    }

    /**
     * 分段打印出较长log文本
     *
     * @param logContent 打印文本
     * @param tag        打印log的标记
     */
    private static void showLargeLog(String tag, String logContent) {
        if (logContent.length() > MAX_LENTH) {
            String show = logContent.substring(0, MAX_LENTH);
            e(tag, show);
            /*剩余的字符串如果大于规定显示的长度，截取剩余字符串进行递归，否则打印结果*/
            if ((logContent.length() - MAX_LENTH) > MAX_LENTH) {
                String partLog = logContent.substring(MAX_LENTH, logContent.length());
                showLargeLog(partLog, tag);
            } else {
                String printLog = logContent.substring(MAX_LENTH, logContent.length());
                e(tag, printLog);
            }
        } else {
            e(tag, logContent);
        }
    }

    private static void e(String TAG, String msg) {
        try {
            if (AppConfigInIt.getConfiguration(AppConfigType.DEBUG)) {
                Log.e(TAG, msg);
            }
        } catch (NullPointerException e) {
        }
    }
}
