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
     * okhttp的拦截器
     */
    HTTP_INTERCEPTOR,
    /**
     * 网络拦截器
     */
    HTTP_NETWORK_INTERCEPTOR,
    /**
     * 是否信任所有证书
     */
    HTTP_CERTIFICATE_FLAG,
    /**
     * 证书流
     */
    HTTP_CERTIFICATE_INPUT,
    /**
     * 链接失败后是否重试
     */
    HTTP_RETRY_CONNECTION,
    /**
     * 请求框架，配置的BaseUrl
     */
    HTTP_BASE_API,
    /**
     * 是否为调试模式
     */
    DEBUG
}
