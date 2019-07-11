package com.lixiaomi.baselibapplication.model;

import com.google.gson.Gson;
import com.lixiaomi.baselib.base.MyPresenterCallBack;
import com.lixiaomi.baselib.net.okhttp.MiOkHttpCallBack;
import com.lixiaomi.baselib.net.okhttp.MiSendRequestOkHttp;
import com.lixiaomi.baselib.utils.LogUtils;
import com.lixiaomi.baselibapplication.bean.SendUserLoginBean;
import com.lixiaomi.baselibapplication.http.HttpUtils;
import com.lixiaomi.baselibapplication.utils.aes_rsa_utils.AESUtil;


/**
 * @describe：<br>
 * @author：Xiaomi<br>
 * @createTime：2019/4/2<br>
 * @remarks：<br>
 * @changeTime:<br>
 */
public class MyHttpModel2 implements IMyHttpModel2 {

    @Override
    public void login(final MyPresenterCallBack myPresenterCallBack) {
        String sendJson = new Gson().toJson(new SendUserLoginBean("15284224244", "1234567", "7741128"));
        final String mAesPassword = HttpUtils.getAESPassword();
        LogUtils.loge("加密前请求参数:" + sendJson);
        LogUtils.loge("mAesPassword:" + mAesPassword);
        MiSendRequestOkHttp.sendPost(HttpUtils.getHeads(mAesPassword), AESUtil.encrypt(sendJson, mAesPassword), "http://hz.zhcun.cn:/api/ApiUser/Login", new MiOkHttpCallBack() {
            @Override
            public void onSuccess(int code, String response) {
                LogUtils.loge("解密前：response:" + response);
                String s = AESUtil.desEncrypt(response, mAesPassword);
                LogUtils.loge("URL:http://hz.zhcun.cn:/api/ApiUser/Login"  + "code:" + code + " response:" + s);
                myPresenterCallBack.success(code, s);
            }

            @Override
            public void onFailure(Throwable e) {
                myPresenterCallBack.failure(e);
            }
        });

    }
}
