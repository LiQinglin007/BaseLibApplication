package com.lixiaomi.baselib.config;

/**
 * @describe：项目初始化配置类型<br>
 * @author：Xiaomi<br>
 * @createTime：2018/3/31<br>
 * @remarks：<br>
 * @changeTime:<br>
 */
public enum AppConfigType {
    /**
     * sharedPreference文件名
     */
    SHARED_PREFERENCES,
    /**
     * 上下文
     */
    APPLICATIO_CONTEXT,
    /**
     * 文件根目录
     */
    BASE_FILE,
    /**
     * 是否初始化完成的标志位
     */
    CONFIGREADY,
    /**
     * 网络请求配置
     */
    HTTP_CONFIG,
    /**
     * 是否为调试模式
     */
    DEBUG
}
