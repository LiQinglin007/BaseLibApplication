package com.lixiaomi.mvplib.base;


/**
 * @describe：MVP架构BaseVeiw<br>
 * @author：Xiaomi<br>
 * @createTime：2018/4/1<br>
 * @remarks：<br>
 * @changeTime:<br>
 */
public interface BaseView {
    /**
     * 显示加载动画
     */
    void startLoading();

    /**
     * 关闭加载动画
     */
    void stopLoading();
}
