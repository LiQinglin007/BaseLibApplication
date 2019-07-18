package com.lixiaomi.baselib.net;

import android.text.TextUtils;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @describe：<br>
 * @author：Xiaomi<br>
 * @createTime：2019/7/16<br>
 * @remarks：<br>
 * @changeTime:<br>
 */
public class NetCacheInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Response originResponse = chain.proceed(request);
        if (!TextUtils.isEmpty(request.header("Cache-Control"))) {
            originResponse = originResponse.newBuilder()
                    .removeHeader("pragma")
                    .header("Cache-Control", request.header("Cache-Control"))
                    .build();
        }
        return originResponse;
    }
}