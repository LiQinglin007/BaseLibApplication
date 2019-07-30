package com.lixiaomi.baselibapplication.application;

import android.app.Activity;
import android.app.Application;

import com.lixiaomi.baselib.config.AppConfigInIt;
import com.lixiaomi.baselibapplication.http.TokenInterceptor;
import com.lixiaomi.baselibapplication.utils.greendaoUtils.DBManager;
import com.tencent.smtt.sdk.QbSdk;

import java.io.IOException;
import java.io.InputStream;

/**
 * 作者：dell or Xiaomi Li
 * 时间： 2018/3/31
 * 内容：
 * 最后修改：
 */

public class MyApplication extends Application {
    private final String DBName = "XiaoMiLib.db";
    private final String SharedPreferences = "XiaoMiLibShare";

    @Override
    public void onCreate() {
        super.onCreate();
        InputStream open = null;
        try {
            //请将证书放在assets目录下
            open = getAssets().open("12306.cer");
        } catch (IOException e) {

        }
        AppConfigInIt.init(this)
                //设置调试模式，默认false
                .withDebug(true)
                //配置SharedPreferences
                .withSharedPreferences(getSharedPreferences(SharedPreferences, Activity.MODE_PRIVATE))
                //默认文件根地址
                .withBaseFile("com.xiaomi.lib")
                //baseUrl
                .withBaseUrl("http://home.hbhanzhi.com:7056/")
                //添加拦截器
                .withHttpInterceptors(new TokenInterceptor())
//                .withHttpNetWorkInterceptors(new NetCacheInterceptor())
                //是否信任全部证书,不信任全部，则传进去证书流
//                .withHttpCertificateFlag(open != null ? false : true, open)
                .withHttpCertificateFlag(true,null)
                //连接失败后是否重连
                .withHttpRetryConnection(true)
                .configure();
        //初始化数据库
        DBManager.getInstance().init(getApplicationContext(), DBName);
        //初始化TBS服务
        QbSdk.initX5Environment(this, null);
        QbSdk.setDownloadWithoutWifi(true);//设置支持非Wifi下载
    }
}
