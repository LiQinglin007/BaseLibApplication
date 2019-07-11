package com.lixiaomi.baselib.net.retrofit;

import com.lixiaomi.baselib.config.AppConfigInIt;
import com.lixiaomi.baselib.config.AppConfigType;
import com.lixiaomi.baselib.net.okhttp.MiOkHttpClient;

import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * @describe：获取RetrofitClient<br>
 * @author：Xiaomi<br>
 * @createTime：2018/4/2<br>
 * @remarks：<br>
 * @changeTime:<br>
 */
public final class MyRestService {

    /**
     * 获取RestService
     *
     * @return
     */
    public static RestService getRestService() {
        return RestServiceHolder.REST_SERVICE;
    }

    private static final class RestServiceHolder {
        private static final RestService REST_SERVICE =
                RetrofitHolder.RETROFIT_CLIENT.create(RestService.class);
    }

    private static final class RetrofitHolder {
        private static final String BASE_URL = AppConfigInIt.getConfiguration(AppConfigType.HTTP_BASE_API);
        private static final Retrofit RETROFIT_CLIENT = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(MiOkHttpClient.getOkHttpClient())
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();
    }

}
