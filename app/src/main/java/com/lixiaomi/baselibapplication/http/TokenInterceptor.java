package com.lixiaomi.baselibapplication.http;

import com.google.gson.Gson;
import com.lixiaomi.baselib.config.AppConfigInIt;
import com.lixiaomi.baselib.config.AppConfigType;
import com.lixiaomi.baselib.net.MiHttpData;
import com.lixiaomi.baselib.utils.LogUtils;
import com.lixiaomi.baselib.utils.PreferenceUtils;
import com.lixiaomi.baselibapplication.bean.LoginBean;
import com.lixiaomi.baselibapplication.bean.SendLogin;
import java.io.IOException;

import okhttp3.Call;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * @describe：自动刷新token拦截器,只拦截一次，重新请求还是401的话，就回到回调里边了<br>
 * @author：Xiaomi<br>
 * @createTime：2019/3/15<br>
 * @remarks：<br>
 * @changeTime:<br>
 */
public class TokenInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Response response = chain.proceed(request);
        LogUtils.loge("response.code=" + response.code());

        if (isTokenExpired(response)) {
            //根据和服务端的约定判断token过期
            LogUtils.loge("静默自动刷新Token,然后重新请求数据");
            //同步请求方式，获取最新的Token
            String newToken = getNewToken();
            //使用新的Token，创建新的请求
            Request newRequest = chain.request()
                    .newBuilder()
                    .removeHeader("TOKEN")
                    .header("TOKEN", newToken)
                    .build();
            //重新请求
            return chain.proceed(newRequest);
        }
        return response;
    }

    /**
     * 根据Response，判断Token是否失效
     *
     * @param response
     * @return
     */
    private boolean isTokenExpired(Response response) {
        //token失效的状态码
        if (response.code() == 401) {
            return true;
        }
        return false;
    }

    /**
     * 同步请求方式，获取最新的Token
     *
     * @return
     */
    private String getNewToken() throws IOException {
        //todo 使用同步方式请求数据
        RequestBody jsonBody = RequestBody.create(MiHttpData.MEDIA_TYPE_JSON,
                new Gson().toJson(new SendLogin("admin", "123456", 1)));
        // 通过一个特定的接口获取新的token，此处要用到同步的retrofit请求
        //1.定义一个client
        OkHttpClient okHttpClient = new OkHttpClient();
        Request.Builder requestBuilder = new Request.Builder();
        //2.定义一个request
        String baseUrl = AppConfigInIt.getConfiguration(AppConfigType.HTTP_BASE_API);
        Request request = new Request.Builder().url(baseUrl + "user/login").post(jsonBody).build();
        //3.使用client去请求
        Call call = okHttpClient.newCall(request);
        //4.获得返回结果
        String result = call.execute().body().string();
        LoginBean loginBean = new Gson().fromJson(result, LoginBean.class);
        if (loginBean != null) {
            if (loginBean.getCode() == 0) {
                PreferenceUtils.setString("token", loginBean.getData().getToken());
                return loginBean.getData().getToken();
            }
        }
        return "";
    }
}