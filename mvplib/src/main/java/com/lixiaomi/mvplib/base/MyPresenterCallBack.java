package com.lixiaomi.mvplib.base;

/**
 * @describe：model请求后的回调<br>
 * @author：Xiaomi<br>
 * @createTime：2019/4/2<br>
 * @remarks：<br>
 * @changeTime:<br>
 */
public interface MyPresenterCallBack {
    void success(int code, String response);

    void failure(Throwable e);
}
