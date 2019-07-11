package com.lixiaomi.baselib.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;

import java.util.Timer;
import java.util.TimerTask;

/**
 * @describe：检测网络状态<br>
 * @author：Xiaomi<br>
 * @createTime：2017/05/08<br>
 * @remarks：<br>
 * @changeTime:<br>
 */
public class NetWorkUtils {

    /**
     * wifi
     */
    public static final int WIFI = 0x1;
    /**
     * 2G
     */
    public static final int G2 = 0x2;
    /**
     * 3G
     */
    public static final int G3 = 0x3;
    /**
     * 4G
     */
    public static final int G4 = 0x4;
    /**
     * 没有网络链接
     */
    public static final int NO_CONNECT = 0x5;

    /**
     * 判断是否有网络连接
     *
     * @param context
     * @return
     */
    public static boolean isNetworkConnected(Context context) {
        if (context != null) {
            // 获取手机所有连接管理对象(包括对wi-fi,net等连接的管理)
            ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            // 获取NetworkInfo对象
            NetworkInfo networkInfo = manager.getActiveNetworkInfo();
            //判断NetworkInfo对象是否为空
            if (networkInfo != null) {
                return networkInfo.isAvailable();
            }
        }
        return false;
    }


    /**
     * 获取当前的网络状态
     *
     * @param context
     * @return wifi: 0x1<br>
     * 2G：0x2<br>
     * 3G：0x3<br>
     * 4G：0x4<br>
     * 没有网络：0x5<br>
     */
    public static int getAPNType(Context context) {
        //结果返回值
        int netType = NO_CONNECT;
        //获取手机所有连接管理对象
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        //获取NetworkInfo对象
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        //NetworkInfo对象为空 则代表没有网络
        if (networkInfo == null) {
            return netType;
        }
        //否则 NetworkInfo对象不为空 则获取该networkInfo的类型
        int nType = networkInfo.getType();
        if (nType == ConnectivityManager.TYPE_WIFI) {
            //WIFI
            netType = WIFI;
        } else if (nType == ConnectivityManager.TYPE_MOBILE) {
            int nSubType = networkInfo.getSubtype();
            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            //3G   联通的3G为UMTS或HSDPA 电信的3G为EVDO
            if (nSubType == TelephonyManager.NETWORK_TYPE_LTE
                    && !telephonyManager.isNetworkRoaming()) {
                netType = G4;
            } else if (nSubType == TelephonyManager.NETWORK_TYPE_UMTS
                    || nSubType == TelephonyManager.NETWORK_TYPE_HSDPA
                    || nSubType == TelephonyManager.NETWORK_TYPE_EVDO_0
                    && !telephonyManager.isNetworkRoaming()) {
                netType = G3;
                //2G 移动和联通的2G为GPRS或EGDE，电信的2G为CDMA
            } else if (nSubType == TelephonyManager.NETWORK_TYPE_GPRS
                    || nSubType == TelephonyManager.NETWORK_TYPE_EDGE
                    || nSubType == TelephonyManager.NETWORK_TYPE_CDMA
                    && !telephonyManager.isNetworkRoaming()) {
                netType = G2;
            } else {
                netType = G2;
            }
        }
        return netType;
    }

    /**
     * 定时器
     */
    static TimerTask timerTask;
    static Timer timer;
    static boolean flag;

    /**
     * 监听网络状态(是否有网)
     *
     * @param mContext
     * @param mNetWorkConnectListen
     */
    public static void getConnectListen(final Context mContext, final NetWorkConnectListen mNetWorkConnectListen) {
        flag = false;
        timerTask = new TimerTask() {
            @Override
            public void run() {
                boolean networkConnected = isNetworkConnected(mContext);
                if (!flag) {
                    if (networkConnected) {
                        flag = true;
                        timer.cancel();
                        timerTask.cancel();
                        mNetWorkConnectListen.connectListen();
                    }
                }
            }
        };
        timer = new Timer();
        timer.schedule(timerTask, 0, 1000);
    }


    public interface NetWorkConnectListen {
        /**
         * 监听网络的回调
         */
        void connectListen();
    }
}
