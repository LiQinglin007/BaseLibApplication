package com.lixiaomi.baselib.config;

import android.content.Context;

/**
 * @describe：Application初始化项目需要调用的类<br>
 * @author：Xiaomi<br>
 * @createTime：2018/3/31<br>
 * @remarks：<br>
 * @changeTime:<br>
 */
public final class AppConfigInIt {

    /**
     * 初始化配置
     *
     * @param mContext
     * @return 返回配置对象(AppConfig)
     */
    public static AppConfig init(Context mContext) {
        AppConfig.getInstance().
                getLatteConfigs().
                put(AppConfigType.APPLICATIO_CONTEXT, mContext.getApplicationContext());
        return AppConfig.getInstance();
    }

    /**
     * 根据AppConfigType中的键获取值
     *
     * @param key
     * @param <T>
     * @return
     */
    public static <T> T getConfiguration(Object key) {
        return AppConfig.getInstance().getConfiguration(key);
    }

    /**
     * 获取全局上下文
     *
     * @return
     */
    public static Context getApplicationContext() {
        return getConfiguration(AppConfigType.APPLICATIO_CONTEXT);
    }
}
