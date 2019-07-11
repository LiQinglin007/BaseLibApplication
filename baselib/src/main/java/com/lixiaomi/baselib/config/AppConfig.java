package com.lixiaomi.baselib.config;

import android.content.SharedPreferences;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

import okhttp3.Interceptor;


/**
 * @describe：配置对象<br>
 * @author：Xiaomi<br>
 * @createTime：2018/3/31<br>
 * @remarks：<br>
 * @changeTime:<br>
 */
public final class AppConfig {

    /**
     * 键值对集合
     */
    public final static HashMap<Object, Object> APP_CONFIGS = new HashMap<>();

    /**
     * okhttp的拦截器集合
     */
    private static final ArrayList<Interceptor> INTERCEPTORS = new ArrayList<>();

    public static AppConfig getInstance() {
        return SingletonHolder.INSTANCE;
    }

    private static class SingletonHolder {
        //静态初始化器，由JVM来保证线程安全
        //这里使用final 关键字 ，因为这个只在这里使用，其他的情况也不会变，为了以后不会误操作发生错误

        private static final AppConfig INSTANCE = new AppConfig();
    }

    final HashMap<Object, Object> getLatteConfigs() {
        return APP_CONFIGS;
    }

    /**
     * 配置SharedPreferences
     *
     * @param sharedPreferences
     * @return
     */
    public final AppConfig withSharedPreferences(SharedPreferences sharedPreferences) {
        APP_CONFIGS.put(AppConfigType.SHARED_PREFERENCES, sharedPreferences);
        return this;
    }


    /**
     * 配置文件根目录
     *
     * @param baseFile
     * @return
     */
    public final AppConfig withBaseFile(String baseFile) {
        APP_CONFIGS.put(AppConfigType.BASE_FILE, baseFile);
        return this;
    }

    /**
     * 配置okhttp拦截器
     *
     * @param interceptor
     * @return
     */
    public final AppConfig withHttpInterceptors(Interceptor interceptor) {
        INTERCEPTORS.add(interceptor);
        APP_CONFIGS.put(AppConfigType.HTTP_INTERCEPTOR, INTERCEPTORS);
        return this;
    }

    /**
     * 配置是否信任所有证书,用于https请求，如果不信任，请构建自己服务器的公钥传递进来
     *
     * @param certificateFlag
     * @param inputStream  不信任所有证书，传递自己服务器的公钥进来.cer文件
     * @return
     */
    public final AppConfig withHttpCertificateFlag(boolean certificateFlag,InputStream inputStream) {
        APP_CONFIGS.put(AppConfigType.HTTP_CERTIFICATE_FLAG, certificateFlag);
        APP_CONFIGS.put(AppConfigType.HTTP_CERTIFICATE_INPUT, inputStream);
        return this;
    }



    /**
     * 配置okhttp拦截器
     *
     * @param interceptorList
     * @return
     */
    public final AppConfig withHttpInterceptors(ArrayList<Interceptor> interceptorList) {
        INTERCEPTORS.addAll(interceptorList);
        APP_CONFIGS.put(AppConfigType.HTTP_INTERCEPTOR, INTERCEPTORS);
        return this;
    }


    /**
     * 配置网络请求主机地址
     *
     * @param baseUrl 如使用retrofit,需以"/"结尾
     * @return
     */
    public final AppConfig withBaseUrl(String baseUrl) {
        APP_CONFIGS.put(AppConfigType.HTTP_BASE_API, baseUrl);
        return this;
    }

    /**
     * 是否为调试状态
     *
     * @param debug
     * @return
     */
    public final AppConfig withDebug(boolean debug) {
        APP_CONFIGS.put(AppConfigType.DEBUG, debug);
        return this;
    }

    /**
     * 结束配置，可以在这里去做初始化数据库等操作
     */
    public final void configure() {
        APP_CONFIGS.put(AppConfigType.CONFIGREADY, true);
    }


    /**
     * 检查初始化状态是否完成
     *
     * @return
     */
    private static final void checkConfiguration() {
        Object o = APP_CONFIGS.get(AppConfigType.CONFIGREADY);
        if (o != null) {
            if (!(boolean) o) {
                //如果没有完成，但是要着急去做下边的操作，就在这里抛出一个异常
                throw new RuntimeException("Configuration is not ready,call configure");
            }
        } else {
            throw new RuntimeException("Configuration is not ready,call configure");
        }

    }

    /**
     * 获取键对应的值
     *
     * @param key
     * @param <T>
     * @return
     */
    final <T> T getConfiguration(Object key) {
        checkConfiguration();
        return (T) APP_CONFIGS.get(key);
    }
}
