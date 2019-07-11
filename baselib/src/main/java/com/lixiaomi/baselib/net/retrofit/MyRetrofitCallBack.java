package com.lixiaomi.baselib.net.retrofit;

import android.os.Handler;
import android.os.Looper;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @describe：请求回调<br>
 * @author：Xiaomi<br>
 * @createTime：2018/4/2<br>
 * @remarks：<br>
 * @changeTime:<br>
 */
public abstract class MyRetrofitCallBack implements Callback {
    Handler handler = new Handler(Looper.getMainLooper());

    @Override
    public void onResponse(Call call, final Response response) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                onSuccess(response.code(), response.body().toString());
            }
        });
    }

    @Override
    public void onFailure(Call call, final Throwable t) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                onFailure(t);
            }
        });
    }

    /**
     * 请求成功
     *
     * @param code
     * @param response
     */
    public abstract void onSuccess(int code, String response);

    /**
     * 请求失败
     *
     * @param e
     */
    public abstract void onFailure(Throwable e);
}
