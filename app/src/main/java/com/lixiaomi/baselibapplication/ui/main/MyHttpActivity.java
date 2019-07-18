package com.lixiaomi.baselibapplication.ui.main;

import android.Manifest;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lixiaomi.baselib.net.okhttp.MiOkHttpCallBack;
import com.lixiaomi.baselib.net.okhttp.MiSendRequestOkHttp;
import com.lixiaomi.baselib.utils.LogUtils;
import com.lixiaomi.baselib.utils.PermissionsUtil;
import com.lixiaomi.baselibapplication.R;
import com.lixiaomi.baselibapplication.bean.Team;
import com.lixiaomi.baselibapplication.bean.UserBean;
import com.lixiaomi.baselibapplication.persenter.MyHttpPersenter;
import com.lixiaomi.baselibapplication.ui.baseui.XMBaseActivity;

import java.util.ArrayList;
import java.util.WeakHashMap;
import java.util.concurrent.TimeUnit;

import okhttp3.CacheControl;
import retrofit2.http.GET;


/**
 * Description: <br>
 *
 * @author : dell - XiaomiLi<br>
 * Date: 2018-07-20<br>
 * Time: 18:52<br>
 * UpdateDescription：<br>
 */
public class MyHttpActivity extends XMBaseActivity<IMyHttpActivity, MyHttpPersenter> implements IMyHttpActivity {

    @Override
    protected Object setLayout() {
        return R.layout.activity_http;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        findViewById(R.id.mvp_test).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPersenter.login("admin", "123456");
            }
        });
        findViewById(R.id.mvp_test2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                mPersenter.login2();
//                MiSendRequestOkHttp.sendGet(null, null, "https://home.hbhanzhi.com:55015/api/home/getnowtime", new MiOkHttpCallBack() {
//                WeakHashMap<String,String> header=new WeakHashMap<>();
//                header.put("Cache-Control", "max-age=237");
//                GET http://gma.alicdn.com/bao/uploaded/i4/TB1RLJiOXXXXXXiaFXXYXGcGpXX_160x160.jpg_.webp HTTP/1.1
//                1、max-age是啥，maxStale是啥，他们的区别是啥？
//                maxAge和maxStale的区别在于：
//                maxAge:没有超出maxAge,不管怎么样都是返回缓存数据，超过了maxAge,发起新的请求获取数据更新，请求失败返回缓存数据。
//                maxStale:没有超过maxStale，不管怎么样都返回缓存数据，超过了maxStale,发起请求获取更新数据，请求失败返回失败

                MiSendRequestOkHttp.sendGet(null, null, "http://baidu.com", 0, new MiOkHttpCallBack() {
                    @Override
                    public void onSuccess(int code, String response) {
                        LogUtils.loge("code:" + code + "response:" + response);
                    }

                    @Override
                    public void onFailure(Throwable e) {
                        LogUtils.loge("e:" + e.toString());
                    }
                });

            }
        });

        String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
        PermissionsUtil.getPermission(MyHttpActivity.this, permissions, "读写sd卡权限", new PermissionsUtil.PermissionCallBack() {
            @Override
            public void onSuccess() {
                LogUtils.loge("申请权限成功");
            }

            @Override
            public void onFail() {

            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionsUtil.onRequestPermissionsResult(MyHttpActivity.this, requestCode, permissions, grantResults);
    }

    @Override
    public void setData(int code, String response) {
        ((TextView) findViewById(R.id.response_tv)).setText("code: " + code + "\nresponse: " + response);
    }

    @Override
    protected MyHttpPersenter createPersenter() {
        return new MyHttpPersenter();
    }

    @Override
    protected int setStatusBarColor() {
        return R.color.default_color;
    }


    @Override
    public void startLoading() {
        showLoading();
    }

    @Override
    public void stopLoading() {
        hineLoading();
    }
}
