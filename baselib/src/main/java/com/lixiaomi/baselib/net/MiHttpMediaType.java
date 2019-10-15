package com.lixiaomi.baselib.net;

import okhttp3.MediaType;

/**
 * @describe：Http请求参数类型<br>
 * @author：Xiaomi<br>
 * @createTime：2018/3/28<br>
 * @remarks：<br>
 * @changeTime:<br>
 */
public class MiHttpMediaType {
    /**
     * 创建jsonType
     */
    public final static MediaType MEDIA_TYPE_JSON = MediaType.parse("application/json; charset=utf-8");
    /**
     * 参数类型
     */
    public final static MediaType MEDIA_TYPE_PNG = MediaType.parse("application/octet-stream");

}
