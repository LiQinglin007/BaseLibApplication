package com.lixiaomi.mvplib.base;

/**
 * @describe：model请求后的回调<br>
 * @author：Xiaomi<br>
 * @createTime：2019/4/2<br>
 * @remarks：<br>
 * @changeTime:<br>
 */
public interface MyPresenterCallBack {
    /**
     * 请求成功
     *
     * @param code
     * @param response
     */
    void success(int code, String response);

    /**
     * 出现错误
     * 在onResponse方法中，例如出现"<html>"等问题，在该方法中返回
     *
     * @param message
     */
    void error(String message);

    /**
     * 失败
     *
     * @param e
     */
    void failure(Throwable e);
}
