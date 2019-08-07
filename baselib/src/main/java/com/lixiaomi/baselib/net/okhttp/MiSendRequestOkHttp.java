package com.lixiaomi.baselib.net.okhttp;

import com.lixiaomi.baselib.config.AppConfigInIt;
import com.lixiaomi.baselib.config.AppConfigType;
import com.lixiaomi.baselib.net.MiHttpData;
import com.lixiaomi.baselib.utils.LogUtils;
import com.lixiaomi.baselib.utils.MiJsonUtil;

import java.io.File;
import java.io.IOException;
import java.util.WeakHashMap;
import java.util.concurrent.TimeUnit;

import okhttp3.CacheControl;
import okhttp3.Call;
import okhttp3.Dispatcher;
import okhttp3.FormBody;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * @describe：OkHttp发起请求<br>
 * @author：Xiaomi<br>
 * @createTime：2018/3/31<br>
 * @remarks：<br>
 * @changeTime:<br>
 */
public final class MiSendRequestOkHttp {
    private final static String TAG = MiSendRequestOkHttp.class.getSimpleName();


//    ===============================================================同步请求===========================================================================

    /**
     * 发送get请求同步请求
     *
     * @param heads     请求头
     * @param params    请求参数
     * @param url       请求地址
     * @param cacheTime 缓存时间(s)
     */
    public static Response sendGetSync(WeakHashMap<String, String> heads, WeakHashMap<String, Object> params, String url, int cacheTime) throws IOException {
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
        return okhttpSendSync(heads, null, url, cacheTime);
    }


    /**
     * 发送post同步请求，参数发送
     *
     * @param heads     请求头
     * @param params    请求参数
     * @param url       请求地址
     * @param cacheTime 缓存时间(s)
     */
    public static Response sendPostSync(WeakHashMap<String, String> heads, WeakHashMap<String, String> params, String url, int cacheTime) throws IOException {
        FormBody.Builder formBodyBuilder = new FormBody.Builder();
        if (params != null && params.size() != 0) {
            for (WeakHashMap.Entry<String, String> map : params.entrySet()) {
                formBodyBuilder.add(map.getKey(), map.getValue());
            }
        }
        FormBody formBody = formBodyBuilder.build();
        LogUtils.logd(TAG, "请求参数:" + MiJsonUtil.getJson(params));
        return okhttpSendSync(heads, formBody, url, cacheTime);
    }

    /**
     * 发送post同步请求
     *
     * @param heads     请求头
     * @param mSendBean 请求对象
     * @param url       请求地址
     * @param cacheTime 缓存时间(s)
     */
    public static Response sendPostSync(WeakHashMap<String, String> heads, Object mSendBean, String url, int cacheTime) throws IOException {
        //创建json请求体
        if (null != mSendBean) {
            RequestBody jsonBody = RequestBody.create(MiHttpData.MEDIA_TYPE_JSON, MiJsonUtil.getJson(mSendBean));
            LogUtils.logd(TAG, "请求参数:" + MiJsonUtil.getJson(mSendBean));
            return okhttpSendSync(heads, jsonBody, url, cacheTime);
        } else {
            throw new RuntimeException("mSendBean can not null");
        }
    }

    /**
     * 发送Post同步请求
     *
     * @param heads     请求头
     * @param mSendJson json格式请求内容
     * @param url       请求地址
     * @param cacheTime 缓存时间(s)
     */
    public static Response sendPostSync(WeakHashMap<String, String> heads, String mSendJson, String url, int cacheTime) throws IOException {
        //创建json请求体
        RequestBody jsonBody = RequestBody.create(MiHttpData.MEDIA_TYPE_JSON, mSendJson);
        LogUtils.logd(TAG, "请求参数:" + mSendJson);
        return okhttpSendSync(heads, jsonBody, url, cacheTime);
    }


    /**
     * 同步请求发送方法
     *
     * @param heads       请求头
     * @param requestBody 请求体
     * @param url         请求地址
     * @param cacheTime   缓存时间(s)
     */
    private static Response okhttpSendSync(WeakHashMap<String, String> heads, RequestBody requestBody, String url, int cacheTime) throws IOException {
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
        LogUtils.logd(TAG, "请求头：" + MiJsonUtil.getJson(heads));
        LogUtils.logd(TAG, "完整的url:" + url);
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
        if (cacheTime != 0) {
            //maxStale:没有超过maxStale，有/无网返回缓存数据，超过了maxStale,请求获取更新数据，请求失败返回失败
            CacheControl cacheControl = new CacheControl.Builder()
                    .maxStale(cacheTime, TimeUnit.SECONDS)
                    .build();
            requestBuilder.cacheControl(cacheControl);
        }

        Request request = requestBuilder
                .url(url)
                .tag(url)
                .build();
        Call call = MiOkHttpClient.getOkHttpClient().newCall(request);
        Response response = call.execute();
        return response;
    }

//    ===============================================================异步请求===========================================================================

    /**
     * 发送get请求异步请求
     *
     * @param heads      请求头
     * @param params     请求参数
     * @param url        请求地址
     * @param cacheTime  缓存时间(s)
     * @param myCallBack 回调
     */
    public static void sendGet(WeakHashMap<String, String> heads, WeakHashMap<String, Object> params, String url, int cacheTime, MiOkHttpCallBack myCallBack) {
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
        okhttpSend(heads, null, url, cacheTime, myCallBack);
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
    public static void sendPost(WeakHashMap<String, String> heads, WeakHashMap<String, String> params, WeakHashMap<String, File> fileList,
                                String url, MiOkHttpCallBack myCallBack) {
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
        LogUtils.logd(TAG, "请求参数:" + MiJsonUtil.getJson(params) + " 上传文件数量：" + fileList.size());
        okhttpSend(heads, requestBody, url, 0, myCallBack);
    }

    /**
     * 发送post请求，参数发送
     *
     * @param heads      请求头
     * @param params     请求参数
     * @param url        请求地址
     * @param cacheTime  缓存时间(s)
     * @param myCallBack 回调
     */
    public static void sendPost(WeakHashMap<String, String> heads, WeakHashMap<String, String> params, String url, int cacheTime, MiOkHttpCallBack myCallBack) {
        FormBody.Builder formBodyBuilder = new FormBody.Builder();
        if (params != null && params.size() != 0) {
            for (WeakHashMap.Entry<String, String> map : params.entrySet()) {
                formBodyBuilder.add(map.getKey(), map.getValue());
            }
        }
        FormBody formBody = formBodyBuilder.build();
        LogUtils.logd(TAG, "请求参数:" + MiJsonUtil.getJson(params));
        okhttpSend(heads, formBody, url, cacheTime, myCallBack);
    }

    /**
     * 发送post异步请求
     *
     * @param heads       请求头
     * @param mSendBean   请求对象
     * @param url         请求地址
     * @param cacheTime   缓存时间(s)
     * @param mOkCallBack 回调
     */
    public static void sendPost(WeakHashMap<String, String> heads, Object mSendBean, String url, int cacheTime, MiOkHttpCallBack mOkCallBack) {
        //创建json请求体
        if (null != mSendBean) {
            RequestBody jsonBody = RequestBody.create(MiHttpData.MEDIA_TYPE_JSON, MiJsonUtil.getJson(mSendBean));
            LogUtils.logd(TAG, "请求参数:" + MiJsonUtil.getJson(mSendBean));
            okhttpSend(heads, jsonBody, url, cacheTime, mOkCallBack);
        } else {
            throw new RuntimeException("mSendBean can not null");
        }
    }

    /**
     * 发送Post异步请求
     *
     * @param heads       请求头
     * @param mSendJson   json格式请求内容
     * @param url         请求地址
     * @param cacheTime   缓存时间(s)
     * @param mOkCallBack 回调
     */
    public static void sendPost(WeakHashMap<String, String> heads, String mSendJson, String url, int cacheTime, MiOkHttpCallBack mOkCallBack) {
        //创建json请求体
        RequestBody jsonBody = RequestBody.create(MiHttpData.MEDIA_TYPE_JSON, mSendJson);
        LogUtils.logd(TAG, "请求参数:" + mSendJson);
        okhttpSend(heads, jsonBody, url, cacheTime, mOkCallBack);
    }


    /**
     * 异步请求发送方法
     *
     * @param heads       请求头
     * @param requestBody 请求体
     * @param url         请求地址
     * @param cacheTime   缓存时间(s)
     * @param myCallBack  回调
     */
    private static void okhttpSend(WeakHashMap<String, String> heads, RequestBody requestBody, String url, int cacheTime, MiOkHttpCallBack myCallBack) {
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
        LogUtils.logd(TAG, "请求头：" + MiJsonUtil.getJson(heads));
        LogUtils.logd(TAG, "完整的url:" + url);
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
        if (cacheTime != 0) {
            //maxStale:没有超过maxStale，有/无网返回缓存数据，超过了maxStale,请求获取更新数据，请求失败返回失败
            CacheControl cacheControl = new CacheControl.Builder()
                    .maxStale(cacheTime, TimeUnit.SECONDS)
                    .build();
            requestBuilder.cacheControl(cacheControl);
        }
        Request request = requestBuilder
                .url(url)
                .tag(url)
                .build();
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
                    LogUtils.loge("取消请求：" + tag);
                }
            }
        } catch (NullPointerException e) {
            LogUtils.loge("BaseUrl不能为NULL,请先在Application中配置BaseUrl.");
        }
    }
}
