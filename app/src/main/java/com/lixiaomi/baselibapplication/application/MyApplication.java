package com.lixiaomi.baselibapplication.application;

import android.app.Activity;
import android.app.Application;

import com.lixiaomi.baselib.config.AppConfigInIt;
import com.lixiaomi.baselib.net.HttpConfig;
import com.lixiaomi.baselibapplication.utils.greendaoUtils.DBManager;
import com.tencent.smtt.sdk.QbSdk;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

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
        ArrayList<String> urlList=new ArrayList<>();
        urlList.add("https://www.baidu.com");
        HttpConfig configData = new HttpConfig("http://home.hbhanzhi.com:7056/", null, null,
                true, null, true, 401, "链接超时，请重新登陆",
                200, 0, "<html>", urlList);
        AppConfigInIt.init(this)
                //设置调试模式，默认false
                .withDebug(true)
                //配置SharedPreferences
                .withSharedPreferences(getSharedPreferences(SharedPreferences, Activity.MODE_PRIVATE))
                //默认文件根地址
                .withBaseFile("com.xiaomi.lib")
                .withHttpConfig(configData)
                .configure();
        //初始化数据库
        DBManager.getInstance().init(getApplicationContext(), DBName);
        //初始化TBS服务
        QbSdk.initX5Environment(this, null);
        QbSdk.setDownloadWithoutWifi(true);//设置支持非Wifi下载
    }
}
