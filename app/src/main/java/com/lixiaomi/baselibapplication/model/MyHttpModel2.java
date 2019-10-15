package com.lixiaomi.baselibapplication.model;

import com.lixiaomi.baselib.net.okhttp.MiSendRequestOkHttp;
import com.lixiaomi.baselib.utils.LogUtils;
import com.lixiaomi.baselibapplication.http.HttpUtils;
import com.lixiaomi.mvplib.base.BasePresenterCallBack;
import com.lixiaomi.mvplib.base.MiHttpCallBack;

import org.jetbrains.annotations.NotNull;


/**
 * @describe：<br>
 * @author：Xiaomi<br>
 * @createTime：2019/4/2<br>
 * @remarks：<br>
 * @changeTime:<br>
 */
public class MyHttpModel2 implements IMyHttpModel2 {

    @Override
    public void login(final BasePresenterCallBack myPresenterCallBack) {
        final String mAesPassword = HttpUtils.getAESPassword();
        MiSendRequestOkHttp.sendGet(HttpUtils.getHeads(mAesPassword),null, "https://www.baidu.com", 0, new MiHttpCallBack(myPresenterCallBack) {
            @Override
            public void onSuccess(@NotNull String response) {
                LogUtils.loge("URL:http://hz.zhcun.cn:/api/ApiUser/Login" + " response:" + response);
                myPresenterCallBack.success(response);
            }
        });
    }
}
