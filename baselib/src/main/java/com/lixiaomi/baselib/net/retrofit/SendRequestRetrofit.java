package com.lixiaomi.baselib.net.retrofit;


import com.google.gson.Gson;
import com.lixiaomi.baselib.config.AppConfigInIt;
import com.lixiaomi.baselib.config.AppConfigType;
import com.lixiaomi.baselib.net.HttpConfig;
import com.lixiaomi.baselib.net.MiHttpMediaType;
import com.lixiaomi.baselib.net.okhttp.MiOkHttpClient;

import java.io.File;
import java.util.WeakHashMap;

import okhttp3.Dispatcher;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;

/**
 * @describe：发起请求<br>
 * @author：Xiaomi<br>
 * @createTime：2018/4/2<br>
 * @remarks：<br>
 * @changeTime:<br>
 */
public final class SendRequestRetrofit {


    /**
     * 没有请求头，文件+参数
     *
     * @param params        请求参数
     * @param fileArrayList 文件 键值对  键为web端的表单域；图片名称
     * @param url           请求地址
     * @param myCallBack    回调
     */
    public static void senPost(WeakHashMap<String, Object> params, WeakHashMap<String, File> fileArrayList, String url, MyRetrofitCallBack myCallBack) {
        Call<String> call = null;
        RestService restService = MyRestService.getRestService();
        if (fileArrayList == null || fileArrayList.size() == 0) {
            //没有文件就上传参数
            call = restService.post(url, params);
        } else {//有文件遍历
            WeakHashMap<String, MultipartBody.Part> partWeakHashMap = getPartWeakHashMap(fileArrayList);
            call = restService.post(url, params, partWeakHashMap);
        }
        call.enqueue(myCallBack);
    }

    /**
     * 有请求头  文件+参数
     *
     * @param heads         请求头
     * @param params        请求参数
     * @param fileArrayList 文件 键值对  键为web端的表单域；图片名称
     * @param url           请求地址
     * @param myCallBack    回调
     */
    public static void sendPost(WeakHashMap<String, String> heads, WeakHashMap<String, Object> params, WeakHashMap<String, File> fileArrayList, String url, MyRetrofitCallBack myCallBack) {
        Call<String> call = null;
        RestService restService = MyRestService.getRestService();
        if (fileArrayList == null || fileArrayList.size() == 0) {
            //没有文件就上传参数
            call = restService.post(heads, url, params);
        } else {//有文件遍历
            WeakHashMap<String, MultipartBody.Part> partWeakHashMap = getPartWeakHashMap(fileArrayList);
            call = restService.post(heads, url, params, partWeakHashMap);
        }
        call.enqueue(myCallBack);
    }

    /**
     * 文件批量上传
     *
     * @param heads         请求头
     * @param fileArrayList 文件 键值对  键为web端的表单域；图片名称
     * @param url           请求地址
     * @param myCallBack    回调
     */
    public static void uploadFile(WeakHashMap<String, String> heads, WeakHashMap<String, File> fileArrayList, String url, MyRetrofitCallBack myCallBack) {
        Call<String> call = null;
        RestService restService = MyRestService.getRestService();
        WeakHashMap<String, MultipartBody.Part> partWeakHashMap = getPartWeakHashMap(fileArrayList);
        if (heads != null) {
            call = restService.upload(heads, url, partWeakHashMap);
        } else {
            call = restService.upload(url, partWeakHashMap);
        }
        call.enqueue(myCallBack);
    }


    /**
     * 文件上传
     *
     * @param heads      请求头  可为空
     * @param mFile      文件
     * @param url        请求地址
     * @param myCallBack 回调
     */
    public static void uploadFile(WeakHashMap<String, String> heads, File mFile, String url, MyRetrofitCallBack myCallBack) {
        Call<String> call = null;
        RestService restService = MyRestService.getRestService();
        final RequestBody requestBody =
                RequestBody.create(MediaType.parse(MultipartBody.FORM.toString()), mFile);
        final MultipartBody.Part body =
                MultipartBody.Part.createFormData("file", mFile.getName(), requestBody);
        if (heads != null) {
            call = restService.upload(heads, url, body);
        } else {
            call = restService.upload(url, body);
        }
        call.enqueue(myCallBack);
    }

    /**
     * post 请求头+参数
     *
     * @param heads      请求头
     * @param params     请求参数
     * @param url        请求地址
     * @param myCallBack 回调
     */
    public static void sendPost(WeakHashMap<String, String> heads, WeakHashMap<String, Object> params, String url, MyRetrofitCallBack myCallBack) {
        Call<String> call = null;
        RestService restService = MyRestService.getRestService();
        if (heads != null) {
            call = restService.post(heads, url, params);
        } else {
            call = restService.post(url, params);
        }
        call.enqueue(myCallBack);
    }

    /**
     * 发送post请求(传递json)
     *
     * @param heads      请求头
     * @param mSendBean  要发送的对象
     * @param url        请求地址
     * @param myCallBack 请求回调
     */
    public static void sendPost(WeakHashMap<String, String> heads, Object mSendBean, String url, MyRetrofitCallBack myCallBack) {
        Call<String> call = null;
        RestService restService = MyRestService.getRestService();
        RequestBody mRequestBody = RequestBody.create(MiHttpMediaType.MEDIA_TYPE_JSON, new Gson().toJson(mSendBean));
        if (heads != null) {
            call = restService.postBody(heads, url, mRequestBody);
        } else {
            call = restService.postBody(url, mRequestBody);
        }
        call.enqueue(myCallBack);
    }

    /**
     * 发送get请求
     *
     * @param heads      请求头
     * @param url        请求地址
     * @param params     请求参数
     * @param myCallBack 请求回调
     */
    public static void sendGet(WeakHashMap<String, String> heads, String url, WeakHashMap<String, Object> params, MyRetrofitCallBack myCallBack) {
        Call<String> call = null;
        RestService restService = MyRestService.getRestService();
        if (heads != null) {
            if (params != null) {
                call = restService.get(heads, url, params);
            } else {
                call = restService.get(heads, url);
            }
        } else {
            if (params != null) {
                call = restService.get(url, params);
            } else {
                call = restService.get(url);
            }
        }
        call.enqueue(myCallBack);
    }


    /**
     * 通过url取消一个请求
     *
     * @param url 请求地址当作tag
     */
    public static void cancel(String url) {
        if (!url.contains("http://") && !url.contains("https://")) {
            String baseUrl = ((HttpConfig)AppConfigInIt.getConfiguration(AppConfigType.HTTP_CONFIG)).getHTTP_BASE_API();
            url = baseUrl + url;
        }
        Dispatcher dispatcher = MiOkHttpClient.getOkHttpClient().dispatcher();
        for (okhttp3.Call call : dispatcher.runningCalls()) {
            String tag = (String) call.request().tag();
            if (url.equals(tag)) {
                call.cancel();
            }
        }
    }

    private static WeakHashMap<String, MultipartBody.Part> getPartWeakHashMap(WeakHashMap<String, File> fileArrayList) {
        WeakHashMap<String, MultipartBody.Part> partWeakHashMap = new WeakHashMap<>();
        if (fileArrayList != null && fileArrayList.size() != 0) {
            for (WeakHashMap.Entry<String, File> map : fileArrayList.entrySet()) {
                final RequestBody requestBody =
                        RequestBody.create(MediaType.parse(MultipartBody.FORM.toString()), map.getValue());
                final MultipartBody.Part body =
                        MultipartBody.Part.createFormData("file", map.getKey(), requestBody);
                partWeakHashMap.put(map.getKey(), body);
            }
        }
        return partWeakHashMap;
    }
}
