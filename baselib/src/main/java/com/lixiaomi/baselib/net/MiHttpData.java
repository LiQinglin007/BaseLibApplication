package com.lixiaomi.baselib.net;

import okhttp3.MediaType;

/**
 * @describe：Http常量<br>
 * @author：Xiaomi<br>
 * @createTime：2018/3/28<br>
 * @remarks：<br>
 * @changeTime:<br>
 */
public class MiHttpData {
    //
    /**
     * 本地CODE码 请求成功
     */
    public final static int LOCAL_SUCCESS = 0x1;
    /**
     * 本地CODE码 请求失败
     */
    public final static int LOCAL_FINAL = 0x2;
    /**
     * 本地CODE码 解析错误
     */
    public final static int LOCAL_PARSING_ERROR = 0x3;
    /**
     * 本地CODE码 后台数据错误（包含标签语言认为是后台数据错误）
     */
    public final static int LOCAL_DATA_ERROR = 0x4;
    /**
     * 没有网络了
     */
    public final static int LOCAL_NO_NETWORK = 0x5;

    /**
     * 创建jsonType
     */
    public final static MediaType MEDIA_TYPE_JSON = MediaType.parse("application/json; charset=utf-8");
    /**
     * 参数类型
     */
    public final static MediaType MEDIA_TYPE_PNG = MediaType.parse("application/octet-stream");

}
