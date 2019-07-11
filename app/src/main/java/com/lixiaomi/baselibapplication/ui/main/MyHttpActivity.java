package com.lixiaomi.baselibapplication.ui.main;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.lixiaomi.baselib.net.okhttp.MiOkHttpCallBack;
import com.lixiaomi.baselib.net.okhttp.MiSendRequestOkHttp;
import com.lixiaomi.baselib.utils.LogUtils;
import com.lixiaomi.baselibapplication.R;
import com.lixiaomi.baselibapplication.persenter.MyHttpPersenter;
import com.lixiaomi.baselibapplication.ui.baseui.XMBaseActivity;


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
                MiSendRequestOkHttp.sendGet(null, null, "https://hz.zhcun.cn:55015/api/home/getnowtime", new MiOkHttpCallBack() {
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
