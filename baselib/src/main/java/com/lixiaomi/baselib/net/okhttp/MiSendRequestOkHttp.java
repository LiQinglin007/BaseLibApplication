package com.lixiaomi.baselib.net.okhttp;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.lixiaomi.baselib.config.AppConfigInIt;
import com.lixiaomi.baselib.config.AppConfigType;
import com.lixiaomi.baselib.net.MiHttpData;
import com.lixiaomi.baselib.utils.LogUtils;

import java.io.File;
import java.util.WeakHashMap;

import okhttp3.Call;
import okhttp3.Dispatcher;
import okhttp3.FormBody;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * @describe：OkHttp发起请求<br>
 * @author：Xiaomi<br>
 * @createTime：2018/3/31<br>
 * @remarks：<br>
 * @changeTime:<br>
 */
public final class MiSendRequestOkHttp {
    private final static String TAG = MiSendRequestOkHttp.class.getSimpleName();
    private static Gson mGson;

    static {
        mGson = new GsonBuilder().disableHtmlEscaping().create();
    }

    /**
     * 文件和post一起上传
     *
     * @param heads      请求头，可为空
     * @param params     参数  可为空
     * @param fileList   上传文件 可为空 键值对  键为web端的表单域；图片名称
     * @param url        请求地址
     * @param myCallBack 请求回调
     */
    public static void sendPost(WeakHashMap<String, String> heads, WeakHashMap<String, String> params, WeakHashMap<String, File> fileList, String url, MiOkHttpCallBack myCallBack) {
        MultipartBody.Builder multipartBody = new MultipartBody.Builder();
        multipartBody.setType(MultipartBody.FORM);
        if (fileList != null && fileList.size() != 0) {
            for (WeakHashMap.Entry<String, File> map : fileList.entrySet()) {
                //web端的表单域；图片名称；图片的RequestBody
                multipartBody.addFormDataPart("mFile", map.getKey(), RequestBody.create(MiHttpData.MEDIA_TYPE_PNG, map.getValue()));
            }
        }
        if (params != null && params.size() != 0) {
            for (WeakHashMap.Entry<String, String> map : params.entrySet()) {
                multipartBody.addFormDataPart(map.getKey(), map.getValue());
            }
        }
        RequestBody requestBody = multipartBody.build();
        LogUtils.loge(TAG, "请求参数:" + mGson.toJson(params) + " 上传文件数量：" + fileList.size());
        okhttpSend(heads, requestBody, url, myCallBack);
    }

    /**
     * 发送post请求
     *
     * @param heads      请求头
     * @param params     请求参数
     * @param url        请求地址
     * @param myCallBack 回调
     */
    public static void sendPost(WeakHashMap<String, String> heads, WeakHashMap<String, String> params, String url, MiOkHttpCallBack myCallBack) {

        FormBody.Builder formBodyBuilder = new FormBody.Builder();
        if (params != null && params.size() != 0) {
            for (WeakHashMap.Entry<String, String> map : params.entrySet()) {
                formBodyBuilder.add(map.getKey(), map.getValue());
            }
        }
        FormBody formBody = formBodyBuilder.build();
        LogUtils.loge(TAG, "请求参数:" + mGson.toJson(params));
        okhttpSend(heads, formBody, url, myCallBack);
    }

    /**
     * 发送post请求(传递json)
     *
     * @param heads       请求头
     * @param mSendBean   请求参数
     * @param url         请求地址
     * @param mOkCallBack 回调
     */
    public static void sendPost(WeakHashMap<String, String> heads, Object mSendBean, String url, MiOkHttpCallBack mOkCallBack) {
        //创建json请求体
        if (null != mSendBean) {
            RequestBody jsonBody = RequestBody.create(MiHttpData.MEDIA_TYPE_JSON, mGson.toJson(mSendBean));
            LogUtils.loge(TAG, "请求参数:" + mGson.toJson(mSendBean));
            okhttpSend(heads, jsonBody, url, mOkCallBack);
        } else {
            throw new RuntimeException("mSendBean can not null");
        }
    }

    /**
     * 发送
     *
     * @param heads
     * @param mSendJson
     * @param url
     * @param mOkCallBack
     */
    public static void sendPost(WeakHashMap<String, String> heads, String mSendJson, String url, MiOkHttpCallBack mOkCallBack) {
        //创建json请求体
        RequestBody jsonBody = RequestBody.create(MiHttpData.MEDIA_TYPE_JSON, mSendJson);
        LogUtils.loge(TAG, "请求参数:" + mSendJson);
        okhttpSend(heads, jsonBody, url, mOkCallBack);
    }

    /**
     * 发送get请求
     *
     * @param heads      请求头
     * @param params     请求参数
     * @param url        请求地址
     * @param myCallBack 回调
     */
    public static void sendGet(WeakHashMap<String, String> heads, WeakHashMap<String, Object> params, String url, MiOkHttpCallBack myCallBack) {
        StringBuilder sendUrl = new StringBuilder(url);
        String substring = sendUrl.substring(sendUrl.length() - 1, sendUrl.length());
        //判断传进来的url最后一位是不是？
        if (!"?".equals(substring)) {
            sendUrl.append("?");
        }
        if (params != null && params.size() != 0) {
            for (WeakHashMap.Entry<String, Object> map : params.entrySet()) {
                sendUrl.append(map.getKey());
                sendUrl.append("=");
                sendUrl.append(map.getValue());
                sendUrl.append("&");
            }
            url = sendUrl.substring(0, sendUrl.length() - 1);
        }
        okhttpSend(heads, null, url, myCallBack);
    }

    private static void okhttpSend(WeakHashMap<String, String> heads, RequestBody requestBody, String url, MiOkHttpCallBack myCallBack) {
        //如果请求地址中包含“http://” 或者“ https://” 就认位是一个完整的请求地址，这里就不进行拼接了
        //不是完整的请求地址，就用BaseUrl去拼接一下
        if (!url.contains("http://") && !url.contains("https://")) {
            try {
                String baseUrl = AppConfigInIt.getConfiguration(AppConfigType.HTTP_BASE_API);
                url = baseUrl + url;
            } catch (NullPointerException e) {
                LogUtils.loge("BaseUrl不能为NULL,请先在Application中配置BaseUrl.");
            }
        }
        LogUtils.loge(TAG, "请求头：" + mGson.toJson(heads));
        LogUtils.loge(TAG, "完整的url:" + url);
        Request.Builder requestBuilder = new Request.Builder();
        if (heads != null && heads.size() != 0) {
            for (WeakHashMap.Entry<String, String> map : heads.entrySet()) {
                requestBuilder.addHeader(map.getKey(), map.getValue());
            }
        }
        //如果请求体是空的，那就是get请求，否则是post请求
        if (requestBody != null) {
            requestBuilder.post(requestBody);
        }
        Request request = requestBuilder.
                url(url).
                tag(url).
                build();
        Call call = MiOkHttpClient.getOkHttpClient().newCall(request);
        call.enqueue(myCallBack);
    }


    /**
     * 通过url取消一个请求
     *
     * @param url 请求地址当作tag
     */
    public static void cancel(String url) {
        try {
            if (!url.contains("http://") && !url.contains("https://")) {
                String baseUrl = AppConfigInIt.getConfiguration(AppConfigType.HTTP_BASE_API);
                url = baseUrl + url;
            }
            Dispatcher dispatcher = MiOkHttpClient.getOkHttpClient().dispatcher();
            for (Call call : dispatcher.runningCalls()) {
                String tag = (String) call.request().tag();
                if (url.equals(tag)) {
                    call.cancel();
                }
            }
        } catch (NullPointerException e) {
            LogUtils.loge("BaseUrl不能为NULL,请先在Application中配置BaseUrl.");
        }
    }
}
