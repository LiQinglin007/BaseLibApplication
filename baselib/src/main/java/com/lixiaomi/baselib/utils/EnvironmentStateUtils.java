package com.lixiaomi.baselib.utils;

import android.os.Environment;
import android.os.StatFs;

import java.io.File;

/**
 * @describe：获取系统环境信息的工具类<br>
 * @author：Xiaomi<br>
 * @createTime：2018/5/9<br>
 * @remarks：<br>
 * @changeTime:<br>
 */
public class EnvironmentStateUtils {

    /**
     * 判断设备是否存在外部存储设备
     *
     * @return
     */
    public static boolean externalStorageIsAvailable() {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 获取外部存储设备的路径
     *
     * @return
     */
    public static File getExternalStorageDirectory() {
        return Environment.getExternalStorageDirectory();
    }

    /**
     * 获取外部存储设备的可用容量
     *
     * @return
     */
    public static long getAvailableExternalMemorySize() {
        if ((externalStorageIsAvailable())) {
            File path = Environment.getExternalStorageDirectory();
            StatFs stat = new StatFs(path.getPath());
            long blockSize = stat.getBlockSize();
            long availableBlocks = stat.getAvailableBlocks();
            return availableBlocks * blockSize;
        } else {
            return -1;
        }
    }

    /**
     * 获取外部存储设备的总容量
     *
     * @return
     */
    public static long getTotalExternalMemorySize() {
        if (externalStorageIsAvailable()) {
            File path = Environment.getExternalStorageDirectory();
            StatFs stat = new StatFs(path.getPath());
            long blockSize = stat.getBlockSize();
            long totalBlocks = stat.getBlockCount();
            return totalBlocks * blockSize;
        } else {
            return -1;
        }
    }
}