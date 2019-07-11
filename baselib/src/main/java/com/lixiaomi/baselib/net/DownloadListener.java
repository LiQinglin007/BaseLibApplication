package com.lixiaomi.baselib.net;

/**
 * @describe：下载接口<br>
 * @author：Xiaomi<br>
 * @createTime：2018/3/28<br>
 * @remarks：<br>
 * @changeTime:<br>
 */
public interface DownloadListener {
    /**
     * 开始下载
     */
    void downStart();

    /**
     * 下载进度，和速度
     *
     * @param progress
     * @param speed
     */
    void downProgress(int progress, long speed);

    /**
     * 下载完成
     */
    void downSuccess();

    /**
     * 下载失败
     *
     * @param failedDesc
     */
    void downFailed(String failedDesc);
}
